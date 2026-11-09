<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <div class="d-flex justify-content-between align-items-center">

            <h3 class="card-title">

                <i class="fas fa-recycle"></i>

                Gestión de Tipos de Residuos

            </h3>


            <div>

                <!-- NUEVO TIPO DE RESIDUO -->

                <button type="button"
                        class="btn btn-success btn-sm"
                        onclick="nuevoTipoResiduo()">

                    <i class="fas fa-plus"></i>

                    Nuevo Tipo de Residuo

                </button>


                <!-- REPORTE GENERAL -->

                <a href="reporte_general.jsp?modulo=tipos_residuos"
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

        <table id="tablaTiposResiduos"
               class="table table-bordered table-striped table-hover"
               style="width:100%">

            <thead>

                <tr>

                    <th>ID</th>

                    <th>Tipo de Residuo</th>

                    <th>Descripción</th>

                    <th>Estado</th>

                    <th>Acciones</th>

                </tr>

            </thead>


            <tbody id="tbodyTiposResiduos"></tbody>

        </table>

    </div>

</div>


<!-- MODAL NUEVO / MODIFICAR -->

<div class="modal fade"
     id="modalTipoResiduo"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog"
         role="document">

        <div class="modal-content">


            <!-- HEADER -->

            <div class="modal-header bg-primary text-white">

                <h5 class="modal-title">

                    <i class="fas fa-recycle"></i>

                    <span id="tituloModalTipoResiduo">
                        Nuevo Tipo de Residuo
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

                <form id="formTipoResiduo">


                    <input type="hidden"
                           id="id_tipo"
                           name="id_tipo">


                    <!-- NOMBRE -->

                    <div class="form-group">

                        <label>
                            Tipo de Residuo
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
                        onclick="guardarTipoResiduo()">

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

cargarTiposResiduos();


/* CARGAR TIPOS DE RESIDUOS */

function cargarTiposResiduos() {

    $.ajax({

        url: 'TipoResiduoController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var tbody =
                $('#tbodyTiposResiduos');


            /*
             * Destruir DataTable anterior
             */

            if (
                $.fn.DataTable &&
                $.fn.DataTable.isDataTable(
                    '#tablaTiposResiduos'
                )
            ) {

                $('#tablaTiposResiduos')
                    .DataTable()
                    .destroy();

            }

            tbody.empty();


            /*
             * Cargar registros
             */

            $.each(data, function (i, tipo) {

                var estado =
                    tipo.estado === true ||
                    tipo.estado === 'true' ||
                    tipo.estado === 't' ||
                    tipo.estado === 'ACTIVO'
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
                    tipo.id_tipo != null
                        ? tipo.id_tipo
                        : '';

                fila += '</td>';


                /* NOMBRE */

                fila += '<td>';

                fila +=
                    tipo.nombre != null
                        ? tipo.nombre
                        : '';

                fila += '</td>';


                /* DESCRIPCIÓN */

                fila += '<td>';

                fila +=
                    tipo.descripcion != null
                        ? tipo.descripcion
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
                    + 'onclick="editarTipoResiduo('
                    + tipo.id_tipo
                    + ')">';

                fila +=
                    '<i class="fas fa-edit"></i>';

                fila += '</button>';


                /* VER */

                fila +=
                    '<a href="TipoResiduoController?'
                    + 'action=reporteIndividual'
                    + '&idTipo='
                    + tipo.id_tipo
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
                    + 'onclick="eliminarTipoResiduo('
                    + tipo.id_tipo
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

            $('#tablaTiposResiduos').DataTable({

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
                'Error Tipos de Residuos:',
                xhr.responseText
            );


            if (
                typeof Swal !== 'undefined'
            ) {

                Swal.fire(
                    'Error',
                    'No se pudieron cargar los tipos de residuos.',
                    'error'
                );

            } else {

                alert(
                    'No se pudieron cargar los tipos de residuos.'
                );

            }

        }

    });

}


/* NUEVO TIPO DE RESIDUO */

function nuevoTipoResiduo() {

    $('#formTipoResiduo')[0].reset();


    $('#id_tipo')
        .val('');


    $('#estado')
        .val('true');


    $('#tituloModalTipoResiduo')
        .text('Nuevo Tipo de Residuo');


    $('#modalTipoResiduo')
        .modal('show');

}


/* MODIFICAR TIPO DE RESIDUO */

function editarTipoResiduo(id) {

    $.ajax({

        url: 'TipoResiduoController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var tipo =
                null;


            $.each(data, function (i, t) {

                if (
                    parseInt(
                        t.id_tipo
                    )
                    ===
                    parseInt(id)
                ) {

                    tipo = t;

                    return false;

                }

            });


            if (tipo === null) {

                Swal.fire(
                    'Error',
                    'No se encontró el tipo de residuo seleccionado.',
                    'error'
                );

                return;

            }


            $('#id_tipo')
                .val(
                    tipo.id_tipo
                );


            $('#nombre')
                .val(
                    tipo.nombre || ''
                );


            $('#descripcion')
                .val(
                    tipo.descripcion || ''
                );


            $('#estado')
                .val(
                    tipo.estado === true ||
                    tipo.estado === 'true' ||
                    tipo.estado === 't'
                        ? 'true'
                        : 'false'
                );


            $('#tituloModalTipoResiduo')
                .text('Modificar Tipo de Residuo');


            $('#modalTipoResiduo')
                .modal('show');

        },


        error: function (xhr) {

            console.error(
                xhr.responseText
            );


            Swal.fire(
                'Error',
                'No se pudo obtener el tipo de residuo.',
                'error'
            );

        }

    });

}


/* GUARDAR TIPO DE RESIDUO */

function guardarTipoResiduo() {

    var id =
        $('#id_tipo')
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
            'Debe ingresar el nombre del tipo de residuo.',
            'warning'
        );

        $('#nombre')
            .focus();

        return;

    }


    var datos =
        $('#formTipoResiduo')
            .serialize();


    datos +=
        '&accion=' + accion;


    $.ajax({

        url: 'TipoResiduoController',

        type: 'POST',

        data: datos,

        dataType: 'json',

        success: function (respuesta) {

            if (
                respuesta.success
            ) {

                $('#modalTipoResiduo')
                    .modal('hide');


                Swal.fire({

                    icon: 'success',

                    title: 'Correcto',

                    text:
                        accion === 'insertar'
                            ? 'Tipo de residuo registrado correctamente.'
                            : 'Tipo de residuo actualizado correctamente.',

                    timer: 1500,

                    showConfirmButton: false

                });


                cargarTiposResiduos();

            } else {

                Swal.fire(
                    'Error',
                    'No se pudo guardar el tipo de residuo.',
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

function eliminarTipoResiduo(id) {

    Swal.fire({

        title:
            '¿Eliminar tipo de residuo?',

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

            url: 'TipoResiduoController',

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
                            'El tipo de residuo pasó a estado INACTIVO.',

                        timer:
                            1500,

                        showConfirmButton:
                            false

                    });


                    cargarTiposResiduos();

                } else {

                    Swal.fire(
                        'Error',
                        'No se pudo eliminar el tipo de residuo.',
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