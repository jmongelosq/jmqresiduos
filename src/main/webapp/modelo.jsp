<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <div class="d-flex justify-content-between align-items-center">

            <h3 class="card-title">

                <i class="fas fa-truck"></i>

                Gestión de Modelos

            </h3>


            <div>

                <!-- NUEVO MODELO -->

                <button type="button"
                        class="btn btn-success btn-sm"
                        onclick="nuevoModelo()">

                    <i class="fas fa-plus"></i>

                    Nuevo Modelo

                </button>


                <!-- REPORTE GENERAL -->

                <a href="reporte_general.jsp?modulo=modelo"
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

        <table id="tablaModelos"
               class="table table-bordered table-striped table-hover"
               style="width:100%">

            <thead>

                <tr>

                    <th>ID</th>

                    <th>Nombre del Modelo</th>

                    <th>Descripción</th>

                    <th>Estado</th>

                    <th>Acciones</th>

                </tr>

            </thead>


            <tbody id="tbodyModelos"></tbody>

        </table>

    </div>

</div>


<!-- MODAL NUEVO / MODIFICAR -->

<div class="modal fade"
     id="modalModelo"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog"
         role="document">

        <div class="modal-content">


            <!-- HEADER -->

            <div class="modal-header bg-primary text-white">

                <h5 class="modal-title">

                    <i class="fas fa-truck"></i>

                    <span id="tituloModalModelo">
                        Nuevo Modelo
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

                <form id="formModelo">

                    <input type="hidden"
                           id="id_modelo"
                           name="id_modelo">


                    <!-- NOMBRE -->

                    <div class="form-group">

                        <label>
                            Nombre del Modelo
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

                        <textarea class="form-control"
                                  id="descripcion"
                                  name="descripcion"
                                  rows="3"
                                  maxlength="255"></textarea>

                    </div>


                    <!-- ESTADO -->

                    <div class="form-group"
                         id="grupoEstado"
                         style="display:none;">

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
                        onclick="guardarModelo()">

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
 * Por eso NO utilizamos $(document).ready().
 */

cargarModelos();


/* CARGAR MODELOS */

function cargarModelos() {

    $.ajax({

        url: 'ModeloController',

        type: 'POST',

        data: {
            action: 'listar'
        },

        success: function (data) {

            var tbody =
                $('#tbodyModelos');



            /* DESTRUIR DATATABLE ANTERIOR */

            if (
                $.fn.DataTable &&
                $.fn.DataTable.isDataTable(
                    '#tablaModelos'
                )
            ) {

                $('#tablaModelos')
                    .DataTable()
                    .destroy();
            }

            tbody.empty();


            /*
             * El Controller devuelve las filas HTML.
             */

            tbody.html(data);


            /* DATATABLE */

            $('#tablaModelos').DataTable({

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
                'Error Modelos:',
                xhr.responseText
            );


            if (
                typeof Swal !== 'undefined'
            ) {

                Swal.fire(
                    'Error',
                    'No se pudieron cargar los modelos.',
                    'error'
                );

            } else {

                alert(
                    'No se pudieron cargar los modelos.'
                );
            }
        }

    });

}


/* NUEVO MODELO */

function nuevoModelo() {

    $('#formModelo')[0].reset();


    $('#id_modelo')
        .val('');


    $('#estado')
        .val('true');


    $('#tituloModalModelo')
        .text('Nuevo Modelo');


    $('#grupoEstado')
        .hide();


    $('#modalModelo')
        .modal('show');
}


/* MODIFICAR MODELO */

function editarModelo(
        id,
        nombre,
        descripcion,
        estado) {


    $('#id_modelo')
        .val(id);


    $('#nombre')
        .val(nombre);


    $('#descripcion')
        .val(descripcion);


    $('#estado')
        .val(
            estado
                ? 'true'
                : 'false'
        );


    $('#tituloModalModelo')
        .text('Modificar Modelo');


    $('#grupoEstado')
        .show();


    $('#modalModelo')
        .modal('show');
}


/* GUARDAR MODELO */

function guardarModelo() {

    var id =
        $('#id_modelo')
            .val();


    var nombre =
        $('#nombre')
            .val()
            .trim();


    var descripcion =
        $('#descripcion')
            .val()
            .trim();


    if (nombre === '') {

        Swal.fire(
            'Atención',
            'Debe ingresar el nombre del modelo.',
            'warning'
        );

        $('#nombre')
            .focus();

        return;
    }


    var action =
        id === ''
            ? 'insertar'
            : 'actualizar';


    $.ajax({

        url: 'ModeloController',

        type: 'POST',

        data: {

            action: action,

            id_modelo: id,

            nombre: nombre,

            descripcion: descripcion,

            estado: $('#estado').val()

        },


        success: function (data) {

            if (
                data.trim() ===
                'success'
            ) {

                $('#modalModelo')
                    .modal('hide');


                Swal.fire({

                    icon: 'success',

                    title: 'Correcto',

                    text:
                        action === 'insertar'
                            ? 'Modelo registrado correctamente.'
                            : 'Modelo actualizado correctamente.',

                    timer: 1500,

                    showConfirmButton: false

                });


                cargarModelos();

            } else {

                Swal.fire(
                    'Error',
                    'No se pudo guardar el modelo.',
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

function eliminarModelo(id) {

    Swal.fire({

        title:
            '¿Eliminar modelo?',

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

            url: 'ModeloController',

            type: 'POST',

            data: {

                action: 'Borrar',

                id: id

            },


            success: function (data) {

                if (
                    data.trim() ===
                    'success'
                ) {

                    Swal.fire({

                        icon:
                            'success',

                        title:
                            'Eliminado',

                        text:
                            'El modelo pasó a estado INACTIVO.',

                        timer:
                            1500,

                        showConfirmButton:
                            false

                    });


                    cargarModelos();

                } else {

                    Swal.fire(
                        'Error',
                        'No se pudo eliminar el modelo.',
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


/* REPORTE INDIVIDUAL */

function reporteIndividual(id) {

    window.open(

        'ModeloController?action=reporteIndividual'
        + '&idModelo='
        + id,

        '_blank'

    );
}

</script>