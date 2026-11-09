<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <div class="d-flex justify-content-between align-items-center">

            <h3 class="card-title">
                <i class="fas fa-users"></i>
                Gestión de Clientes
            </h3>

            <div>

                <!-- NUEVO CLIENTE -->

                <button type="button"
                        class="btn btn-success btn-sm"
                        onclick="nuevoCliente()">

                    <i class="fas fa-plus"></i>
                    Nuevo Cliente

                </button>


                <!-- REPORTE GENERAL -->

                <a href="reporte_general.jsp?modulo=clientes"
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

        <table id="tablaClientes"
               class="table table-bordered table-striped table-hover"
               style="width:100%">

            <thead>

                <tr>

                    <th>ID</th>

                    <th>Nombre</th>

                    <th>Apellido</th>

                    <th>Cédula</th>

                    <th>RUC</th>

                    <th>Teléfono</th>

                    <th>Correo</th>

                    <th>Barrio</th>

                    <th>Estado</th>

                    <th>Acciones</th>

                </tr>

            </thead>

            <tbody id="tbodyClientes"></tbody>

        </table>

    </div>

</div>


<!-- MODAL NUEVO / MODIFICAR CLIENTE -->

<div class="modal fade"
     id="modalCliente"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog modal-lg"
         role="document">

        <div class="modal-content">

            <!-- HEADER -->

            <div class="modal-header bg-primary text-white">

                <h5 class="modal-title">

                    <i class="fas fa-user"></i>

                    <span id="tituloModalCliente">
                        Nuevo Cliente
                    </span>

                </h5>

                <button type="button"
                        class="close text-white"
                        data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <!-- BODY -->

            <div class="modal-body">

                <form id="formCliente">


                    <input type="hidden"
                           id="id_cliente"
                           name="id_cliente">


                    <!-- NOMBRE / APELLIDO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Nombre
                                </label>

                                <input type="text"
                                       class="form-control"
                                       id="nombre"
                                       name="nombre"
                                       required>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Apellido
                                </label>

                                <input type="text"
                                       class="form-control"
                                       id="apellido"
                                       name="apellido"
                                       required>

                            </div>

                        </div>

                    </div>


                    <!-- CÉDULA / RUC -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Cédula
                                </label>

                                <input type="text"
                                       class="form-control"
                                       id="cedula"
                                       name="cedula"
                                       required>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    RUC
                                    <small class="text-muted">
                                        (Opcional)
                                    </small>
                                </label>

                                <input type="text"
                                       class="form-control"
                                       id="ruc"
                                       name="ruc"
                                       placeholder="Ej.: 80012345-6">

                            </div>

                        </div>

                    </div>


                    <!-- TELÉFONO / CORREO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Teléfono
                                </label>

                                <input type="text"
                                       class="form-control"
                                       id="telefono"
                                       name="telefono">

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Correo
                                </label>

                                <input type="email"
                                       class="form-control"
                                       id="correo"
                                       name="correo">

                            </div>

                        </div>

                    </div>


                    <!-- DIRECCIÓN -->

                    <div class="row">

                        <div class="col-md-12">

                            <div class="form-group">

                                <label>
                                    Dirección
                                </label>

                                <input type="text"
                                       class="form-control"
                                       id="direccion"
                                       name="direccion"
                                       required>

                            </div>

                        </div>

                    </div>


                    <!-- BARRIO -->

                    <div class="row">

                        <div class="col-md-12">

                            <div class="form-group">

                                <label>
                                    Barrio
                                </label>

                                <select class="form-control"
                                        id="id_barrio"
                                        name="id_barrio"
                                        required>

                                    <option value="">
                                        Seleccione un barrio
                                    </option>

                                </select>

                            </div>

                        </div>

                    </div>


                </form>

            </div>


            <!-- FOOTER -->

            <div class="modal-footer">

                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    <i class="fas fa-times"></i>
                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-primary"
                        onclick="guardarCliente()">

                    <i class="fas fa-save"></i>
                    Guardar

                </button>

            </div>

        </div>

    </div>

</div>


<script>

/* INICIALIZACIÓN */

cargarClientes();

cargarBarrios();


/* NORMALIZAR ESTADO */

function obtenerEstadoCliente(valor) {

    if (valor === null || valor === undefined) {

        return 'SIN ESTADO';
    }

    var estado = String(valor)
        .trim()
        .toLowerCase();


    if (
        estado === 't' ||
        estado === 'true' ||
        estado === 'activo' ||
        estado === 'a' ||
        estado === '1'
    ) {

        return 'ACTIVO';
    }


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


/* CARGAR CLIENTES */

function cargarClientes() {

    $.ajax({

        url: 'ClienteController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var tbody = $('#tbodyClientes');



            if (
                $.fn.DataTable &&
                $.fn.DataTable.isDataTable('#tablaClientes')
            ) {

                $('#tablaClientes')
                    .DataTable()
                    .destroy();

            }

            tbody.empty();


            $.each(data, function (i, c) {

                var estado =
                    obtenerEstadoCliente(c.estado);


                var claseEstado =
                    estado === 'ACTIVO'
                        ? 'badge-success'
                        : 'badge-danger';


                var fila = '';


                fila += '<tr>';


                /* ID */

                fila += '<td>';

                fila +=
                    c.id_cliente != null
                        ? c.id_cliente
                        : '';

                fila += '</td>';


                /* NOMBRE */

                fila += '<td>';

                fila +=
                    c.nombre != null
                        ? c.nombre
                        : '';

                fila += '</td>';


                /* APELLIDO */

                fila += '<td>';

                fila +=
                    c.apellido != null
                        ? c.apellido
                        : '';

                fila += '</td>';


                /* CÉDULA */

                fila += '<td>';

                fila +=
                    c.cedula != null
                        ? c.cedula
                        : '';

                fila += '</td>';


                /* RUC */

                fila += '<td>';

                fila +=
                    c.ruc != null && c.ruc !== ''
                        ? c.ruc
                        : '—';

                fila += '</td>';


                /* TELÉFONO */

                fila += '<td>';

                fila +=
                    c.telefono != null
                        ? c.telefono
                        : '';

                fila += '</td>';


                /* CORREO */

                fila += '<td>';

                fila +=
                    c.correo != null
                        ? c.correo
                        : '';

                fila += '</td>';


                /* BARRIO */

                fila += '<td>';

                fila +=
                    c.nombre_barrio != null
                        ? c.nombre_barrio
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
                    + 'onclick="editarCliente('
                    + c.id_cliente
                    + ')">';

                fila +=
                    '<i class="fas fa-edit"></i>';

                fila += '</button>';


                /* VER */

                fila +=
                    '<a href="ClienteController?accion='
                    + 'reporteIndividual&id='
                    + c.id_cliente
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
                    + 'onclick="eliminarCliente('
                    + c.id_cliente
                    + ')">';

                fila +=
                    '<i class="fas fa-trash"></i>';

                fila += '</button>';


                fila += '</td>';

                fila += '</tr>';


                tbody.append(fila);

            });


            /* * DATATABLE */

            $('#tablaClientes').DataTable({

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
                'Error Clientes:',
                xhr.responseText
            );


            if (typeof Swal !== 'undefined') {

                Swal.fire(
                    'Error',
                    'No se pudieron cargar los registros de clientes.',
                    'error'
                );

            } else {

                alert(
                    'No se pudieron cargar los registros de clientes.'
                );

            }

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
                $('#id_barrio');

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


/* NUEVO CLIENTE */

function nuevoCliente() {

    $('#formCliente')[0].reset();

    $('#id_cliente').val('');

    $('#tituloModalCliente')
        .text('Nuevo Cliente');


    cargarBarrios();


    $('#modalCliente').modal('show');

}


/* MODIFICAR CLIENTE */

function editarCliente(id) {

    $.ajax({

        url: 'ClienteController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var cliente = null;


            $.each(data, function (i, c) {

                if (
                    parseInt(c.id_cliente)
                    ===
                    parseInt(id)
                ) {

                    cliente = c;

                    return false;

                }

            });


            if (cliente === null) {

                Swal.fire(
                    'Error',
                    'No se encontró el cliente seleccionado.',
                    'error'
                );

                return;

            }


            $('#id_cliente')
                .val(cliente.id_cliente);


            $('#nombre')
                .val(cliente.nombre || '');


            $('#apellido')
                .val(cliente.apellido || '');


            $('#cedula')
                .val(cliente.cedula || '');


            $('#ruc')
                .val(cliente.ruc || '');


            $('#telefono')
                .val(cliente.telefono || '');


            $('#direccion')
                .val(cliente.direccion || '');


            $('#correo')
                .val(cliente.correo || '');


            $('#tituloModalCliente')
                .text('Modificar Cliente');


            cargarBarrios(function () {

                $('#id_barrio')
                    .val(cliente.id_barrio);

            });


            $('#modalCliente').modal('show');

        },


        error: function (xhr) {

            console.error(
                xhr.responseText
            );


            Swal.fire(
                'Error',
                'No se pudo obtener el cliente.',
                'error'
            );

        }

    });

}


/* GUARDAR CLIENTE */

function guardarCliente() {

    var id =
        $('#id_cliente').val();


    var accion =
        id === ''
            ? 'insertar'
            : 'actualizar';


    var datos =
        $('#formCliente').serialize();


    datos +=
        '&accion=' + accion;


    $.ajax({

        url: 'ClienteController',

        type: 'POST',

        data: datos,

        success: function () {

            $('#modalCliente')
                .modal('hide');


            Swal.fire({

                icon: 'success',

                title: 'Correcto',

                text:
                    accion === 'insertar'
                        ? 'Cliente registrado correctamente.'
                        : 'Cliente actualizado correctamente.',

                timer: 1500,

                showConfirmButton: false

            });


            cargarClientes();

        },


        error: function (xhr) {

            console.error(
                xhr.responseText
            );


            Swal.fire(
                'Error',
                'No se pudo guardar el cliente.',
                'error'
            );

        }

    });

}


/* ELIMINAR - BAJA LÓGICA */

function eliminarCliente(id) {

    Swal.fire({

        title:
            '¿Eliminar cliente?',

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

            url: 'ClienteController',

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
                        'El cliente pasó a estado INACTIVO.',

                    timer:
                        1500,

                    showConfirmButton:
                        false

                });


                cargarClientes();

            },


            error: function (xhr) {

                console.error(
                    xhr.responseText
                );


                Swal.fire(
                    'Error',
                    'No se pudo eliminar el cliente.',
                    'error'
                );

            }

        });

    });

}

</script>