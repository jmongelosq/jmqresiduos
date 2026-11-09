<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <div class="d-flex justify-content-between align-items-center">

            <h3 class="card-title">
                <i class="fas fa-users"></i>
                Gestión de Personal
            </h3>

            <div>

                <button type="button"
                        class="btn btn-success btn-sm"
                        onclick="nuevoPersonal()">

                    <i class="fas fa-plus"></i>
                    Nuevo Personal

                </button>

                <a href="reporte_general.jsp?modulo=personal"
                   target="_blank"
                   class="btn btn-info btn-sm">

                    <i class="fas fa-file-pdf"></i>
                    Reporte General

                </a>

            </div>

        </div>

    </div>


    <!-- TABLA -->

    <div class="card-body">

        <table id="tablaPersonal"
               class="table table-bordered table-striped table-hover"
               style="width:100%">

            <thead>

                <tr>

                    <th>ID</th>

                    <th>Nombre</th>

                    <th>Apellido</th>

                    <th>Cédula</th>

                    <th>Teléfono</th>

                    <th>Cargo</th>

                    <th>Barrio</th>

                    <th>Estado</th>

                    <th>Acciones</th>

                </tr>

            </thead>

            <tbody id="tbodyPersonal"></tbody>

        </table>

    </div>

</div>


<!-- MODAL NUEVO / MODIFICAR -->

<div class="modal fade"
     id="modalPersonal"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog modal-lg"
         role="document">

        <div class="modal-content">

            <div class="modal-header bg-primary text-white">

                <h5 class="modal-title">

                    <i class="fas fa-user"></i>

                    <span id="tituloModalPersonal">
                        Nuevo Personal
                    </span>

                </h5>

                <button type="button"
                        class="close text-white"
                        data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <div class="modal-body">

                <form id="formPersonal">

                    <input type="hidden"
                           id="id_personal"
                           name="id_personal">


                    <!-- NOMBRE / APELLIDO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>Nombre</label>

                                <input type="text"
                                       class="form-control"
                                       id="nombre"
                                       name="nombre"
                                       required>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>Apellido</label>

                                <input type="text"
                                       class="form-control"
                                       id="apellido"
                                       name="apellido"
                                       required>

                            </div>

                        </div>

                    </div>


                    <!-- CEDULA / TELEFONO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>Cédula</label>

                                <input type="text"
                                       class="form-control"
                                       id="cedula"
                                       name="cedula"
                                       required>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>Teléfono</label>

                                <input type="text"
                                       class="form-control"
                                       id="telefono"
                                       name="telefono">

                            </div>

                        </div>

                    </div>


                    <!-- DIRECCION / CORREO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>Dirección</label>

                                <input type="text"
                                       class="form-control"
                                       id="direccion"
                                       name="direccion">

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>Correo</label>

                                <input type="email"
                                       class="form-control"
                                       id="correo"
                                       name="correo">

                            </div>

                        </div>

                    </div>


                    <!-- CARGO / BARRIO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>Cargo</label>

                                <select class="form-control"
                                        id="id_cargo"
                                        name="id_cargo"
                                        required>

                                    <option value="">
                                        Seleccione un cargo
                                    </option>

                                </select>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>Barrio</label>

                                <select class="form-control"
                                        id="id_barrios"
                                        name="id_barrios"
                                        required>

                                    <option value="">
                                        Seleccione un barrio
                                    </option>

                                </select>

                            </div>

                        </div>

                    </div>


                    <!-- ESTADO -->

                    <div class="form-group">

                        <label>Estado</label>

                        <select class="form-control"
                                id="estado"
                                name="estado">

                            <option value="ACTIVO">
                                ACTIVO
                            </option>

                            <option value="INACTIVO">
                                INACTIVO
                            </option>

                        </select>

                    </div>

                </form>

            </div>


            <div class="modal-footer">

                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    <i class="fas fa-times"></i>
                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-primary"
                        onclick="guardarPersonal()">

                    <i class="fas fa-save"></i>
                    Guardar

                </button>

            </div>

        </div>

    </div>

</div>


<script>

/* INICIALIZACIÓN */

/*
 * IMPORTANTE:
 * Este JSP se carga dentro de dashboard.jsp mediante AJAX.
 *
 * Por eso NO usamos:
 *
 * $(document).ready(...)
 *
 * porque el documento principal ya está cargado cuando
 * este contenido llega.
 *
 * Ejecutamos directamente cargarPersonal().
 */

cargarPersonal();


/* NORMALIZAR ESTADO */

function obtenerEstadoPersonal(valor) {

    if (valor === null || valor === undefined) {

        return 'SIN ESTADO';

    }

    var estado = String(valor)
        .trim()
        .toLowerCase();


    /*
     * PostgreSQL / JDBC puede devolver:
     *
     * t
     * true
     * activo
     * a
     * 1
     */

    if (
        estado === 't' ||
        estado === 'true' ||
        estado === 'activo' ||
        estado === 'a' ||
        estado === '1'
    ) {

        return 'ACTIVO';

    }


    /*
     * Valores que representan INACTIVO
     */

    if (
        estado === 'f' ||
        estado === 'false' ||
        estado === 'inactivo' ||
        estado === 'i' ||
        estado === '0'
    ) {

        return 'INACTIVO';

    }


    return String(valor).toUpperCase();

}


/* CARGAR PERSONAL */

function cargarPersonal() {

    $.ajax({

        url: 'PersonalController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var tbody = $('#tbodyPersonal');



            /*
             * Destruir DataTable anterior
             */

            if (
                $.fn.DataTable &&
                $.fn.DataTable.isDataTable('#tablaPersonal')
            ) {

                $('#tablaPersonal')
                    .DataTable()
                    .destroy();

            }

            tbody.empty();


            $.each(data, function (i, p) {

                var estado =
                    obtenerEstadoPersonal(p.estado);


                var claseEstado =
                    estado === 'ACTIVO'
                        ? 'badge-success'
                        : 'badge-danger';


                var fila = '';


                fila += '<tr>';


                /* ID */

                fila += '<td>';

                fila +=
                    p.id_personal != null
                        ? p.id_personal
                        : '';

                fila += '</td>';


                /* NOMBRE */

                fila += '<td>';

                fila +=
                    p.nombre != null
                        ? p.nombre
                        : '';

                fila += '</td>';


                /* APELLIDO */

                fila += '<td>';

                fila +=
                    p.apellido != null
                        ? p.apellido
                        : '';

                fila += '</td>';


                /* CEDULA */

                fila += '<td>';

                fila +=
                    p.cedula != null
                        ? p.cedula
                        : '';

                fila += '</td>';


                /* TELEFONO */

                fila += '<td>';

                fila +=
                    p.telefono != null
                        ? p.telefono
                        : '';

                fila += '</td>';


                /* CARGO */

                fila += '<td>';

                fila +=
                    p.nombre_cargo != null
                        ? p.nombre_cargo
                        : '';

                fila += '</td>';


                /* BARRIO */

                fila += '<td>';

                fila +=
                    p.nombre_barrio != null
                        ? p.nombre_barrio
                        : '';

                fila += '</td>';


                /* ESTADO */

                fila += '<td>';

                fila +=
                    '<span class="badge '
                    + claseEstado
                    + '">';

                fila += estado;

                fila += '</span>';

                fila += '</td>';


                /* ACCIONES */

                fila += '<td class="text-center">';


                /* MODIFICAR */

                fila +=
                    '<button type="button" '
                    + 'class="btn btn-warning btn-sm mr-1" '
                    + 'title="Modificar" '
                    + 'onclick="editarPersonal('
                    + p.id_personal
                    + ')">';

                fila +=
                    '<i class="fas fa-edit"></i>';

                fila += '</button>';


                /* VER */

                fila +=
                    '<a href="PersonalController?accion='
                    + 'reporteIndividual&id='
                    + p.id_personal
                    + '" '
                    + 'target="_blank" '
                    + 'class="btn btn-info btn-sm mr-1" '
                    + 'title="Ver">';

                fila +=
                    '<i class="fas fa-eye"></i>';

                fila += '</a>';


                /* ELIMINAR */

                fila +=
                    '<button type="button" '
                    + 'class="btn btn-danger btn-sm" '
                    + 'title="Eliminar" '
                    + 'onclick="eliminarPersonal('
                    + p.id_personal
                    + ')">';

                fila +=
                    '<i class="fas fa-trash"></i>';

                fila += '</button>';


                fila += '</td>';

                fila += '</tr>';


                tbody.append(fila);

            });


            /*
             * Inicializar DataTable
             */

            $('#tablaPersonal').DataTable({

                language: {

                    search: "Buscar:",

                    lengthMenu:
                        "Mostrar _MENU_ registros",

                    info:
                        "Mostrando _START_ a _END_ de _TOTAL_ registros",

                    infoEmpty:
                        "Mostrando 0 a 0 de 0 registros",

                    zeroRecords:
                        "No se encontraron registros",

                    paginate: {

                        first: "Primero",

                        last: "Último",

                        next: "Siguiente",

                        previous: "Anterior"

                    }

                },

                pageLength: 10,

                responsive: true,

                autoWidth: false

            });

        },


        error: function (xhr) {

            console.error(
                'Error Personal:',
                xhr.responseText
            );


            if (typeof Swal !== 'undefined') {

                Swal.fire(
                    'Error',
                    'No se pudieron cargar los registros de personal.',
                    'error'
                );

            } else {

                alert(
                    'No se pudieron cargar los registros de personal.'
                );

            }

        }

    });

}


/* NUEVO PERSONAL */

function nuevoPersonal() {

    $('#formPersonal')[0].reset();

    $('#id_personal').val('');

    $('#tituloModalPersonal')
        .text('Nuevo Personal');


    cargarCargos();

    cargarBarrios();


    $('#modalPersonal').modal('show');

}


/* MODIFICAR PERSONAL */

function editarPersonal(id) {

    $.ajax({

        url: 'PersonalController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var persona = null;


            $.each(data, function (i, p) {

                if (
                    parseInt(p.id_personal)
                    ===
                    parseInt(id)
                ) {

                    persona = p;

                    return false;

                }

            });


            if (persona === null) {

                Swal.fire(
                    'Error',
                    'No se encontró el personal seleccionado.',
                    'error'
                );

                return;

            }


            $('#id_personal')
                .val(persona.id_personal);


            $('#nombre')
                .val(persona.nombre || '');


            $('#apellido')
                .val(persona.apellido || '');


            $('#cedula')
                .val(persona.cedula || '');


            $('#telefono')
                .val(persona.telefono || '');


            $('#direccion')
                .val(persona.direccion || '');


            $('#correo')
                .val(persona.correo || '');


            $('#estado')
                .val(
                    obtenerEstadoPersonal(persona.estado)
                );


            $('#tituloModalPersonal')
                .text('Modificar Personal');


            cargarCargos(function () {

                $('#id_cargo')
                    .val(persona.id_cargo);

            });


            cargarBarrios(function () {

                $('#id_barrios')
                    .val(persona.id_barrios);

            });


            $('#modalPersonal').modal('show');

        },


        error: function (xhr) {

            console.error(
                xhr.responseText
            );


            Swal.fire(
                'Error',
                'No se pudo obtener el personal.',
                'error'
            );

        }

    });

}


/* CARGAR CARGOS */

function cargarCargos(callback) {

    $.ajax({

        url: 'CargoController',

        type: 'POST',

        data: {
            action: 'listarOptions'
        },

        dataType: 'html',

        success: function (data) {

            // El controlador devuelve las opciones ya armadas
            $('#id_cargo').html(data);

            if (
                typeof callback === 'function'
            ) {

                callback();

            }

        },

        error: function (xhr) {

            console.error(
                'Error cargos:',
                xhr.responseText
            );

        }

    });

}


/* CARGAR BARRIOS */

function cargarBarrios(callback) {

    $.ajax({

        url: 'BarrioController',

        type: 'POST',

        data: {
            accion: 'listarOptions'
        },

        dataType: 'json',

        success: function (data) {

            var select =
                $('#id_barrios');

            select.empty();


            select.append(
                '<option value="">'
                + 'Seleccione un barrio'
                + '</option>'
            );


            $.each(data, function (i, b) {

                select.append(

                    '<option value="'
                    + b.id_barrio
                    + '">'
                    + (b.nombre || '')
                    + '</option>'

                );

            });


            if (
                typeof callback === 'function'
            ) {

                callback();

            }

        },

        error: function (xhr) {

            console.error(
                'Error barrios:',
                xhr.responseText
            );

        }

    });

}


/* GUARDAR PERSONAL */

function guardarPersonal() {

    var id =
        $('#id_personal').val();


    var accion =
        id === ''
            ? 'insertar'
            : 'actualizar';


    var datos =
        $('#formPersonal').serialize();


    datos +=
        '&accion=' + accion;


    $.ajax({

        url: 'PersonalController',

        type: 'POST',

        data: datos,

        success: function () {

            $('#modalPersonal')
                .modal('hide');


            Swal.fire({

                icon: 'success',

                title: 'Correcto',

                text:
                    accion === 'insertar'
                        ? 'Personal registrado correctamente.'
                        : 'Personal actualizado correctamente.',

                timer: 1500,

                showConfirmButton: false

            });


            /*
             * Recargar inmediatamente la tabla.
             */

            cargarPersonal();

        },


        error: function (xhr) {

            console.error(
                xhr.responseText
            );


            Swal.fire(
                'Error',
                xhr.status === 409
                    ? xhr.responseText
                    : 'No se pudo guardar el personal.',
                'error'
            );

        }

    });

}


/* ELIMINAR - BAJA LÓGICA */

function eliminarPersonal(id) {

    Swal.fire({

        title:
            '¿Eliminar personal?',

        text:
            'El registro no será borrado físicamente. '
            + 'Pasará a estado INACTIVO.',

        icon:
            'warning',

        showCancelButton:
            true,

        confirmButtonColor:
            '#dc3545',

        cancelButtonColor:
            '#6c757d',

        confirmButtonText:
            'Sí, eliminar',

        cancelButtonText:
            'Cancelar'

    }).then(function (result) {

        if (!result.isConfirmed) {

            return;

        }


        $.ajax({

            url: 'PersonalController',

            type: 'POST',

            data: {

                accion: 'Borrar',

                id: id

            },

            success: function () {

                Swal.fire({

                    icon:
                        'success',

                    title:
                        'Eliminado',

                    text:
                        'El personal pasó a estado INACTIVO.',

                    timer:
                        1500,

                    showConfirmButton:
                        false

                });


                cargarPersonal();

            },


            error: function (xhr) {

                console.error(
                    xhr.responseText
                );


                Swal.fire(
                    'Error',
                    'No se pudo eliminar el personal.',
                    'error'
                );

            }

        });

    });

}

</script>