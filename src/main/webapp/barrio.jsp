<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <div class="d-flex justify-content-between align-items-center">

            <h3 class="card-title">

                <i class="fas fa-map-marker-alt"></i>

                Gestión de Barrios

            </h3>


            <div>

                <!-- NUEVO BARRIO -->

                <button type="button"
                        class="btn btn-success btn-sm"
                        onclick="nuevoBarrio()">

                    <i class="fas fa-plus"></i>

                    Nuevo Barrio

                </button>


                <!-- REPORTE GENERAL -->

                <a href="reporte_general.jsp?modulo=barrio"
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

        <table id="tablaBarrios"
               class="table table-bordered table-striped table-hover"
               style="width:100%">

            <thead>

                <tr>

                    <th>ID</th>

                    <th>Nombre del Barrio</th>

                    <th>Descripción</th>

                    <th>Estado</th>

                    <th>Acciones</th>

                </tr>

            </thead>


            <tbody id="tbodyBarrios"></tbody>

        </table>

    </div>

</div>


<!-- MODAL NUEVO / MODIFICAR -->

<div class="modal fade"
     id="modalBarrio"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog"
         role="document">

        <div class="modal-content">


            <!-- HEADER -->

            <div class="modal-header bg-primary text-white">

                <h5 class="modal-title">

                    <i class="fas fa-map-marker-alt"></i>

                    <span id="tituloModalBarrio">
                        Nuevo Barrio
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

                <form id="formBarrio">


                    <input type="hidden"
                           id="id_barrio"
                           name="id_barrio">


                    <!-- NOMBRE -->

                    <div class="form-group">

                        <label>
                            Nombre del Barrio
                        </label>

                        <input type="text"
                               class="form-control"
                               id="nombre"
                               name="nombre"
                               maxlength="100"
                               required>

                    </div>


                    <!-- DESCRIPCIÓN -->

                    <div class="form-group">

                        <label>
                            Descripción
                        </label>

                        <textarea
                            class="form-control"
                            id="descripcion"
                            name="descripcion"
                            rows="3"
                            maxlength="255"></textarea>

                    </div>


                    <!-- ESTADO -->

                    <div class="form-group">

                        <label>
                            Estado
                        </label>

                        <select class="form-control"
                                id="estado"
                                name="estado">

                            <option value="true">
                                ACTIVO
                            </option>

                            <option value="false">
                                INACTIVO
                            </option>

                        </select>

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
                        onclick="guardarBarrio()">

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
 * Este JSP se carga dentro de dashboard.jsp mediante AJAX.
 *
 * Por eso no utilizamos $(document).ready().
 */

cargarBarrios();


/* CARGAR BARRIOS */

function cargarBarrios() {

    $.ajax({

        url: 'BarrioController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var tbody =
                $('#tbodyBarrios');


            /*
             * Destruir DataTable anterior
             */

            if (
                $.fn.DataTable &&
                $.fn.DataTable.isDataTable(
                    '#tablaBarrios'
                )
            ) {

                $('#tablaBarrios')
                    .DataTable()
                    .destroy();

            }

            tbody.empty();


            /*
             * Cargar registros
             */

            $.each(data, function (i, b) {

                var estado =
                    b.estado === true ||
                    b.estado === 'true' ||
                    b.estado === 't' ||
                    b.estado === 'ACTIVO'
                        ? 'ACTIVO'
                        : 'INACTIVO';


                var claseEstado =
                    estado === 'ACTIVO'
                        ? 'badge-success'
                        : 'badge-danger';


                var fila = '';


                fila += '<tr>';


                /* ID */

                fila += '<td>';

                fila +=
                    b.id_barrio != null
                        ? b.id_barrio
                        : '';

                fila += '</td>';


                /* NOMBRE */

                fila += '<td>';

                fila +=
                    b.nombre != null
                        ? b.nombre
                        : '';

                fila += '</td>';


                /* DESCRIPCIÓN */

                fila += '<td>';

                fila +=
                    b.descripcion != null
                        ? b.descripcion
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

                fila +=
                    '<td class="text-center">';


                /* MODIFICAR */

                fila +=
                    '<button type="button" '
                    + 'class="btn btn-warning btn-sm mr-1" '
                    + 'title="Modificar" '
                    + 'onclick="editarBarrio('
                    + b.id_barrio
                    + ')">';

                fila +=
                    '<i class="fas fa-edit"></i>';

                fila += '</button>';


                /* VER */

                fila +=
                    '<a href="BarrioController?'
                    + 'action=reporteIndividual'
                    + '&idBarrio='
                    + b.id_barrio
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
                    + 'onclick="eliminarBarrio('
                    + b.id_barrio
                    + ')">';

                fila +=
                    '<i class="fas fa-trash"></i>';

                fila += '</button>';


                fila += '</td>';


                fila += '</tr>';


                tbody.append(fila);

            });


            /*
             * DATATABLE
             */

            $('#tablaBarrios').DataTable({

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
                'Error Barrios:',
                xhr.responseText
            );


            if (
                typeof Swal !== 'undefined'
            ) {

                Swal.fire(
                    'Error',
                    'No se pudieron cargar los barrios.',
                    'error'
                );

            } else {

                alert(
                    'No se pudieron cargar los barrios.'
                );

            }

        }

    });

}


/* NUEVO BARRIO */

function nuevoBarrio() {

    $('#formBarrio')[0].reset();


    $('#id_barrio')
        .val('');


    $('#estado')
        .val('true');


    $('#tituloModalBarrio')
        .text('Nuevo Barrio');


    $('#modalBarrio')
        .modal('show');

}


/* MODIFICAR BARRIO */

function editarBarrio(id) {

    $.ajax({

        url: 'BarrioController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var barrio =
                null;


            $.each(data, function (i, b) {

                if (
                    parseInt(
                        b.id_barrio
                    )
                    ===
                    parseInt(id)
                ) {

                    barrio = b;

                    return false;

                }

            });


            if (barrio === null) {

                Swal.fire(
                    'Error',
                    'No se encontró el barrio seleccionado.',
                    'error'
                );

                return;

            }


            $('#id_barrio')
                .val(
                    barrio.id_barrio
                );


            $('#nombre')
                .val(
                    barrio.nombre || ''
                );


            $('#descripcion')
                .val(
                    barrio.descripcion || ''
                );


            $('#estado')
                .val(
                    barrio.estado === true ||
                    barrio.estado === 'true' ||
                    barrio.estado === 't'
                        ? 'true'
                        : 'false'
                );


            $('#tituloModalBarrio')
                .text('Modificar Barrio');


            $('#modalBarrio')
                .modal('show');

        },


        error: function (xhr) {

            console.error(
                xhr.responseText
            );


            Swal.fire(
                'Error',
                'No se pudo obtener el barrio.',
                'error'
            );

        }

    });

}


/* GUARDAR BARRIO */

function guardarBarrio() {

    var id =
        $('#id_barrio')
            .val();


    var accion =
        id === ''
            ? 'insertar'
            : 'actualizar';


    var nombre =
        $('#nombre')
            .val()
            .trim();


    if (nombre === '') {

        Swal.fire(
            'Atención',
            'Debe ingresar el nombre del barrio.',
            'warning'
        );

        $('#nombre')
            .focus();

        return;

    }


    var datos =
        $('#formBarrio')
            .serialize();


    datos +=
        '&accion=' + accion;


    $.ajax({

        url: 'BarrioController',

        type: 'POST',

        data: datos,

        dataType: 'json',

        success: function (respuesta) {

            if (
                respuesta.success
            ) {

                $('#modalBarrio')
                    .modal('hide');


                Swal.fire({

                    icon: 'success',

                    title: 'Correcto',

                    text:
                        accion === 'insertar'
                            ? 'Barrio registrado correctamente.'
                            : 'Barrio actualizado correctamente.',

                    timer: 1500,

                    showConfirmButton: false

                });


                cargarBarrios();

            } else {

                Swal.fire(
                    'Error',
                    'No se pudo guardar el barrio.',
                    'error'
                );

            }

        },


        error: function (xhr) {

            console.error(
                xhr.responseText
            );


            Swal.fire(
                'Error',
                'Error al comunicarse con el servidor.',
                'error'
            );

        }

    });

}


/* ELIMINAR - BAJA LÓGICA */

function eliminarBarrio(id) {

    Swal.fire({

        title:
            '¿Eliminar barrio?',

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

            url: 'BarrioController',

            type: 'POST',

            data: {

                accion: 'Borrar',

                id: id

            },

            dataType: 'json',

            success: function (respuesta) {

                if (
                    respuesta.success
                ) {

                    Swal.fire({

                        icon:
                            'success',

                        title:
                            'Eliminado',

                        text:
                            'El barrio pasó a estado INACTIVO.',

                        timer:
                            1500,

                        showConfirmButton:
                            false

                    });


                    cargarBarrios();

                } else {

                    Swal.fire(
                        'Error',
                        'No se pudo eliminar el barrio.',
                        'error'
                    );

                }

            },


            error: function (xhr) {

                console.error(
                    xhr.responseText
                );


                Swal.fire(
                    'Error',
                    'No se pudo procesar la baja.',
                    'error'
                );

            }

        });

    });

}

</script>