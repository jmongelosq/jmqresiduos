<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <div class="d-flex justify-content-between align-items-center">

            <h3 class="card-title">

                <i class="fas fa-id-badge"></i>

                Gestión de Cargos

            </h3>


            <div>

                <!-- NUEVO CARGO -->

                <button
                    type="button"
                    class="btn btn-success btn-sm"
                    onclick="nuevoCargo()">

                    <i class="fas fa-plus"></i>

                    Nuevo Cargo

                </button>


                <!-- REPORTE GENERAL -->

                <a
                    href="reporte_general.jsp?modulo=cargo"
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

        <table
            id="tablaCargos"
            class="table table-bordered table-striped table-hover"
            style="width:100%">

            <thead>

                <tr>

                    <th>
                        ID
                    </th>

                    <th>
                        Nombre del Cargo
                    </th>

                    <th>
                        Descripción
                    </th>

                    <th>
                        Estado
                    </th>

                    <th>
                        Acciones
                    </th>

                </tr>

            </thead>


            <tbody id="tbodyCargos">

            </tbody>

        </table>

    </div>

</div>


<!-- MODAL NUEVO / MODIFICAR -->

<div
    class="modal fade"
    id="modalCargo"
    tabindex="-1"
    role="dialog"
    aria-hidden="true">

    <div
        class="modal-dialog"
        role="document">

        <div class="modal-content">


            <!-- ENCABEZADO MODAL -->

            <div class="modal-header bg-primary text-white">

                <h5 class="modal-title">

                    <i class="fas fa-id-badge"></i>

                    <span id="tituloModalCargo">
                        Nuevo Cargo
                    </span>

                </h5>


                <button
                    type="button"
                    class="close text-white"
                    data-dismiss="modal">

                    <span>
                        &times;
                    </span>

                </button>

            </div>


            <!-- CUERPO MODAL -->

            <div class="modal-body">

                <form id="formCargo">

                    <input
                        type="hidden"
                        id="id_cargo"
                        name="id_cargo">


                    <!-- NOMBRE -->

                    <div class="form-group">

                        <label for="nombre_cargo">

                            Nombre del Cargo

                        </label>


                        <input
                            type="text"
                            class="form-control"
                            id="nombre_cargo"
                            name="nombre_cargo"
                            maxlength="100"
                            required>

                    </div>


                    <!-- DESCRIPCIÓN -->

                    <div class="form-group">

                        <label for="descripcion">

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

                    <div
                        class="form-group"
                        id="grupoEstado"
                        style="display:none;">

                        <label for="estado">

                            Estado

                        </label>


                        <select
                            class="form-control"
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


            <!-- FOOTER MODAL -->

            <div class="modal-footer">

                <button
                    type="button"
                    class="btn btn-secondary"
                    data-dismiss="modal">

                    <i class="fas fa-times"></i>

                    Cancelar

                </button>


                <button
                    type="button"
                    class="btn btn-primary"
                    onclick="guardarCargo()">

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
 * Igual que Personal.
 *
 * Este JSP se carga dinámicamente dentro de dashboard.jsp,
 * por eso ejecutamos directamente listarCargos().
 */

listarCargos();


/* LISTAR CARGOS */

function listarCargos() {

    $.ajax({

        url: 'CargoController',

        type: 'POST',

        data: {
            action: 'listar'
        },


        success: function (data) {

            var tbody =
                $('#tbodyCargos');



            /*
             * Destruir DataTable anterior
             */

            if (
                $.fn.DataTable &&
                $.fn.DataTable.isDataTable(
                    '#tablaCargos'
                )
            ) {

                $('#tablaCargos')
                    .DataTable()
                    .destroy();

            }

            tbody.empty();


            /*
             * El CargoController devuelve
             * las filas <tr>.
             *
             * Las colocamos dentro del tbody.
             */

            tbody.html(data);


            /*
             * Inicializar DataTable
             */

            $('#tablaCargos').DataTable({

                language: {

                    search:
                        "Buscar:",

                    lengthMenu:
                        "Mostrar _MENU_ registros",

                    info:
                        "Mostrando _START_ a _END_ de _TOTAL_ registros",

                    infoEmpty:
                        "Mostrando 0 a 0 de 0 registros",

                    zeroRecords:
                        "No se encontraron registros",

                    paginate: {

                        first:
                            "Primero",

                        last:
                            "Último",

                        next:
                            "Siguiente",

                        previous:
                            "Anterior"

                    }

                },

                pageLength: 10,

                responsive: true,

                autoWidth: false

            });

        },


        error: function (xhr) {

            console.error(
                'Error Cargos:',
                xhr.responseText
            );


            if (
                typeof Swal !==
                'undefined'
            ) {

                Swal.fire(
                    'Error',
                    'No se pudieron cargar los cargos.',
                    'error'
                );

            } else {

                alert(
                    'No se pudieron cargar los cargos.'
                );

            }

        }

    });

}


/* NUEVO CARGO */

function nuevoCargo() {

    $('#formCargo')[0].reset();


    $('#id_cargo')
        .val('');


    $('#estado')
        .val('true');


    $('#tituloModalCargo')
        .text(
            'Nuevo Cargo'
        );


    $('#grupoEstado')
        .hide();


    $('#modalCargo')
        .modal('show');

}


/* MODIFICAR CARGO */

function editarCargo(
    id,
    nombre,
    descripcion,
    estado
) {

    $('#id_cargo')
        .val(id);


    $('#nombre_cargo')
        .val(nombre);


    $('#descripcion')
        .val(descripcion);


    $('#estado')
        .val(
            estado
                ? 'true'
                : 'false'
        );


    $('#tituloModalCargo')
        .text(
            'Modificar Cargo'
        );


    $('#grupoEstado')
        .show();


    $('#modalCargo')
        .modal('show');

}


/* GUARDAR CARGO */

function guardarCargo() {

    var id =
        $('#id_cargo')
            .val();


    var nombre =
        $('#nombre_cargo')
            .val()
            .trim();


    var descripcion =
        $('#descripcion')
            .val()
            .trim();


    if (nombre === '') {

        Swal.fire(
            'Atención',
            'Debe ingresar el nombre del cargo.',
            'warning'
        );

        $('#nombre_cargo')
            .focus();

        return;

    }


    var accion =
        id === ''
            ? 'insertar'
            : 'actualizar';


    $.ajax({

        url: 'CargoController',

        type: 'POST',

        data: {

            action: accion,

            id_cargo: id,

            nombre_cargo: nombre,

            descripcion: descripcion,

            estado:
                $('#estado')
                    .val()

        },


        success: function (respuesta) {

            if (
                respuesta.trim()
                ===
                'success'
            ) {

                $('#modalCargo')
                    .modal('hide');


                Swal.fire({

                    icon:
                        'success',

                    title:
                        'Correcto',

                    text:
                        accion === 'insertar'
                            ? 'Cargo registrado correctamente.'
                            : 'Cargo actualizado correctamente.',

                    timer:
                        1500,

                    showConfirmButton:
                        false

                });


                /*
                 * Recargar inmediatamente
                 * la tabla.
                 */

                listarCargos();

            } else {

                Swal.fire(
                    'Error',
                    'No se pudo guardar el cargo.',
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
                'No se pudo guardar el cargo.',
                'error'
            );

        }

    });

}


/* ELIMINAR - BAJA LÓGICA */

function eliminarCargo(id) {

    Swal.fire({

        title:
            '¿Eliminar cargo?',

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

        if (
            !result.isConfirmed
        ) {

            return;

        }


        $.ajax({

            url:
                'CargoController',

            type:
                'POST',

            data: {

                action:
                    'Borrar',

                id:
                    id

            },


            success: function (respuesta) {

                if (String(respuesta).trim() !== 'success') {

                    Swal.fire(
                        'Error',
                        'No se pudo eliminar el cargo.',
                        'error'
                    );

                    return;
                }

                Swal.fire({

                    icon:
                        'success',

                    title:
                        'Eliminado',

                    text:
                        'El cargo pasó a estado INACTIVO.',

                    timer:
                        1500,

                    showConfirmButton:
                        false

                });


                listarCargos();

            },


            error: function (xhr) {

                console.error(
                    xhr.responseText
                );


                Swal.fire(
                    'Error',
                    'No se pudo eliminar el cargo.',
                    'error'
                );

            }

        });

    });

}


/* REPORTE INDIVIDUAL */

function reporteIndividual(id) {

    window.open(

        'CargoController?action=reporteIndividual&idCargo='
        + id,

        '_blank'

    );

}

</script>