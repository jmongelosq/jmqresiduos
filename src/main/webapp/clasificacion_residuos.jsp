<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Clasificación de Residuos | JMQ SYSTEM</title>


    <style>

        .clasificaciones-page { font-size: 14px; }

        .clasificaciones-page .table-clasificaciones {
            width: 100%; table-layout: fixed; margin-bottom: 0; font-size: 14px !important;
        }
        .clasificaciones-page .table-clasificaciones th,
        .clasificaciones-page .table-clasificaciones td {
            vertical-align: middle; padding: 0.60rem 0.45rem; line-height: 1.35;
        }
        .clasificaciones-page .table-clasificaciones thead th { font-weight: 600; white-space: nowrap; text-align: center;
        }
        .clasificaciones-page .table-clasificaciones tbody td { font-size: 14px !important; }

        .acciones-clasificacion { text-align: center; white-space: nowrap; }
        .btn-action { width: 30px; height: 30px; padding: 0; margin: 1px; display: inline-flex; align-items: center; justify-content: center; vertical-align: middle; }
        .btn-action i { font-size: 12px; }
        .badge-estado { padding: 0.35em 0.55em; white-space: nowrap; }


        #tablaClasificaciones_wrapper .dataTables_length { text-align: left; }
        #tablaClasificaciones_wrapper .dataTables_info { text-align: left; }
        #tablaClasificaciones_wrapper .dataTables_paginate { text-align: right; }

        .modal label { font-weight: 500; font-size: 13px; }
        .modal .form-control { font-size: 14px; }
        .modal .btn { font-size: 14px; }

    </style>

</head>


<body>


<div class="clasificaciones-page">


<!-- TÍTULO -->

<!-- CONTENIDO -->

<section class="content">

    <div class="container-fluid">


        <div class="card">


            <!-- CABECERA -->

            <div class="card-header">

                <h3 class="card-title">

                    <i class="fas fa-recycle"></i>

                    Gestión de Clasificación de Residuos

                </h3>


                <div class="card-tools">


                    <!-- NUEVA CLASIFICACIÓN -->

                    <button
                        type="button"
                        class="btn btn-success btn-sm mr-1"
                        onclick="nuevaClasificacion()">

                        <i class="fas fa-plus"></i>

                        Nueva Clasificación

                    </button>


                    <!-- REPORTE GENERAL -->

                    <a
                        href="<%= request.getContextPath() %>/reporte_general.jsp?modulo=clasificacion_residuos"
                        target="_blank"
                        class="btn btn-info btn-sm">

                        <i class="fas fa-file-pdf"></i>

                        Reporte General

                    </a>


                </div>

            </div>


            <!-- CUERPO -->

            <div class="card-body">


                <div id="contenedorClasificaciones">

                    <div class="text-center p-3">

                        <i class="fas fa-spinner fa-spin"></i>

                        Cargando...

                    </div>

                </div>


            </div>

        </div>


    </div>

</section>


<!-- MODAL NUEVA CLASIFICACIÓN -->

<div
    class="modal fade"
    id="modalNuevaClasificacion"
    tabindex="-1"
    role="dialog"
    aria-hidden="true">


    <div
        class="modal-dialog modal-lg"
        role="document">


        <div class="modal-content">


            <!-- CABECERA -->

            <div class="modal-header bg-success">

                <h5 class="modal-title text-white">

                    <i class="fas fa-plus-circle"></i>

                    Nueva Clasificación

                </h5>


                <button
                    type="button"
                    class="close text-white"
                    data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <!-- FORMULARIO -->

            <div class="modal-body">

                <form id="formNuevaClasificacion">


                    <div class="row">


                        <!-- RECOLECCIÓN -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Recolección

                                    <span class="text-danger">*</span>

                                </label>


                                <select
                                    id="nuevoIdRecoleccion"
                                    name="id_recoleccion"
                                    class="form-control"
                                    required>

                                    <option value="0">

                                        Seleccione una recolección

                                    </option>

                                </select>

                            </div>

                        </div>


                        <!-- TIPO -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Tipo de Residuo

                                    <span class="text-danger">*</span>

                                </label>


                                <select
                                    id="nuevoIdTipo"
                                    name="id_tipo"
                                    class="form-control"
                                    required>

                                    <option value="0">

                                        Seleccione un tipo de residuo

                                    </option>

                                </select>

                            </div>

                        </div>


                    </div>


                    <div class="row">


                        <!-- CANTIDAD -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Cantidad clasificada (kg)

                                    <span class="text-danger">*</span>

                                </label>


                                <input
                                    type="number"
                                    id="nuevoCantidadKg"
                                    name="cantidad_kg"
                                    class="form-control"
                                    min="0.01"
                                    step="0.01"
                                    required>

                            </div>

                        </div>


                        <!-- UNIDAD -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Unidad
                                </label>


                                <input
                                    type="text"
                                    class="form-control"
                                    value="Kilogramos (kg)"
                                    readonly>

                            </div>

                        </div>


                    </div>


                    <!-- OBSERVACIONES -->

                    <div class="form-group">

                        <label>
                            Observaciones
                        </label>


                        <textarea
                            id="nuevoObservaciones"
                            name="observaciones"
                            class="form-control"
                            rows="3"
                            maxlength="255"
                            placeholder="Ingrese observaciones de la clasificación"></textarea>

                    </div>


                </form>

            </div>


            <!-- PIE -->

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
                    class="btn btn-success"
                    onclick="guardarClasificacion()">

                    <i class="fas fa-save"></i>

                    Guardar

                </button>


            </div>

        </div>

    </div>

</div>


<!-- MODAL MODIFICAR -->

<div
    class="modal fade"
    id="modalEditarClasificacion"
    tabindex="-1"
    role="dialog"
    aria-hidden="true">


    <div
        class="modal-dialog modal-lg"
        role="document">


        <div class="modal-content">


            <!-- CABECERA -->

            <div class="modal-header bg-warning">

                <h5 class="modal-title">

                    <i class="fas fa-edit"></i>

                    Modificar Clasificación

                </h5>


                <button
                    type="button"
                    class="close"
                    data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <!-- FORMULARIO -->

            <div class="modal-body">

                <form id="formEditarClasificacion">


                    <input
                        type="hidden"
                        id="editIdClasificacion"
                        name="id_clasificacion">


                    <div class="row">


                        <!-- RECOLECCIÓN -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Recolección

                                    <span class="text-danger">*</span>

                                </label>


                                <select
                                    id="editIdRecoleccion"
                                    name="id_recoleccion"
                                    class="form-control"
                                    required>

                                    <option value="0">

                                        Seleccione una recolección

                                    </option>

                                </select>

                            </div>

                        </div>


                        <!-- TIPO -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Tipo de Residuo

                                    <span class="text-danger">*</span>

                                </label>


                                <select
                                    id="editIdTipo"
                                    name="id_tipo"
                                    class="form-control"
                                    required>

                                    <option value="0">

                                        Seleccione un tipo de residuo

                                    </option>

                                </select>

                            </div>

                        </div>


                    </div>


                    <!-- CANTIDAD -->

                    <div class="form-group">

                        <label>

                            Cantidad clasificada (kg)

                            <span class="text-danger">*</span>

                        </label>


                        <input
                            type="number"
                            id="editCantidadKg"
                            name="cantidad_kg"
                            class="form-control"
                            min="0.01"
                            step="0.01"
                            required>

                    </div>


                    <!-- OBSERVACIONES -->

                    <div class="form-group">

                        <label>
                            Observaciones
                        </label>


                        <textarea
                            id="editObservaciones"
                            name="observaciones"
                            class="form-control"
                            rows="3"
                            maxlength="255"></textarea>

                    </div>


                </form>

            </div>


            <!-- PIE -->

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
                    class="btn btn-warning"
                    onclick="actualizarClasificacion()">

                    <i class="fas fa-save"></i>

                    Actualizar

                </button>


            </div>

        </div>

    </div>

</div>


<!-- JAVASCRIPT -->

<script>


/* INICIO */

/*
    NORMALIZACIÓN DE BÚSQUEDA DATATABLES
    Permite buscar:
    - Mayúsculas / minúsculas
    - Con / sin acentos
    - Coincidencias parciales
*/

(function () {

    if ($.fn.dataTable &&
        $.fn.dataTable.ext &&
        $.fn.dataTable.ext.type) {

        $.fn.dataTable.ext.type.search.string = function (data) {

            if (data === null ||
                data === undefined) {

                return "";
            }

            return String(data)

                    .normalize("NFD")

                    .replace(/[\u0300-\u036f]/g, "")

                    .toLowerCase()

                    .replace(/\s+/g, " ")

                    .trim();
        };
    }

})();


$(document).ready(function () {

    listarClasificaciones();

    cargarRecolecciones(
        "#nuevoIdRecoleccion"
    );

    cargarRecolecciones(
        "#editIdRecoleccion"
    );

    cargarTiposResiduos(
        "#nuevoIdTipo"
    );

    cargarTiposResiduos(
        "#editIdTipo"
    );

});


/* REPORTE GENERAL */

function reporteGeneralClasificaciones() {

    window.open(

        "<%= request.getContextPath() %>/reporte_general.jsp?modulo=clasificacion_residuos",

        "_blank"

    );

}


/* NORMALIZAR BÚSQUEDA */

function normalizarTextoBusqueda(valor) {

    if (valor === null || valor === undefined) {
        return "";
    }

    return String(valor)
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .toLowerCase()
        .replace(/\s+/g, " ")
        .trim();

}


/* LISTAR */

function listarClasificaciones() {


    $.ajax({

        url:
            "<%= request.getContextPath() %>/ClasificacionResiduoController",

        type: "POST",

        data: {

            action: "listar"

        },


        success: function (data) {


            $("#contenedorClasificaciones")
                    .html(data);


            if ($("#tablaClasificaciones").length) {


                var tablaClasificaciones =
                    $("#tablaClasificaciones").DataTable({

                        destroy: true,

                        responsive: true,

                        autoWidth: false,

                        pageLength: 10,

                        order: [[0, "desc"]],

                        columnDefs: [{
                            targets: "_all",
                            render: function (data, type) {
                                if (type === "filter") {
                                    var texto = $("<div>")
                                        .html(data == null ? "" : data)
                                        .text();
                                    return normalizarTextoBusqueda(texto);
                                }
                                return data;
                            }
                        }],


                        language: {

                        decimal: ",",

                        thousands: ".",


                        processing:
                            "Procesando...",


                        search:
                            "Buscar:",

                        searchPlaceholder:
                            "Buscar por recolección, fecha, tipo, cantidad, observación o estado...",


                        lengthMenu:
                            "Mostrar _MENU_ registros",


                        info:
                            "Mostrando _START_ a _END_ de _TOTAL_ registros",


                        infoEmpty:
                            "Mostrando 0 a 0 de 0 registros",


                        infoFiltered:
                            "(filtrado de _MAX_ registros totales)",


                        loadingRecords:
                            "Cargando...",


                        zeroRecords:
                            "No se encontraron registros",


                        emptyTable:
                            "No hay datos disponibles en la tabla",


                        paginate: {

                            first:
                                "Primero",

                            previous:
                                "Anterior",

                            next:
                                "Siguiente",

                            last:
                                "Último"

                        },


                        aria: {

                            sortAscending:
                                ": activar para ordenar la columna de manera ascendente",

                            sortDescending:
                                ": activar para ordenar la columna de manera descendente"

                        }

                    }

                });



            }

        },


        error: function () {


            $("#contenedorClasificaciones")
                    .html(

                        "<div class='alert alert-danger'>" +

                        "No se pudieron cargar las clasificaciones." +

                        "</div>"

                    );

        }

    });

}


/* NUEVA CLASIFICACIÓN */

function nuevaClasificacion() {


    $("#formNuevaClasificacion")[0]
            .reset();


    $("#nuevoIdRecoleccion")
            .val("0");


    $("#nuevoIdTipo")
            .val("0");


    cargarRecolecciones(
        "#nuevoIdRecoleccion"
    );


    cargarTiposResiduos(
        "#nuevoIdTipo"
    );


    $("#modalNuevaClasificacion")
            .modal("show");

}


/* CARGAR RECOLECCIONES */

function cargarRecolecciones(selector) {


    $.ajax({

        url:
            "<%= request.getContextPath() %>/ClasificacionResiduoController",

        type: "POST",


        data: {

            action:
                "listarRecolecciones"

        },


        success: function (data) {

            $(selector)
                    .html(data);

        },


        error: function () {

            $(selector)
                    .html(

                        "<option value='0'>" +

                        "No se pudieron cargar las recolecciones" +

                        "</option>"

                    );

        }

    });

}


/* CARGAR TIPOS DE RESIDUOS */

function cargarTiposResiduos(selector) {


    $.ajax({

        url:
            "<%= request.getContextPath() %>/ClasificacionResiduoController",

        type: "POST",


        data: {

            action:
                "listarTipos"

        },


        success: function (data) {

            $(selector)
                    .html(data);

        },


        error: function () {

            $(selector)
                    .html(

                        "<option value='0'>" +

                        "No se pudieron cargar los tipos de residuos" +

                        "</option>"

                    );

        }

    });

}


/* GUARDAR */

function guardarClasificacion() {


    const formulario =
            document.getElementById(
                    "formNuevaClasificacion"
            );


    if (!formulario.checkValidity()) {

        formulario.reportValidity();

        return;

    }


    $.ajax({

        url:
            "<%= request.getContextPath() %>/ClasificacionResiduoController",

        type: "POST",


        data:
            $("#formNuevaClasificacion")
                    .serialize() +

            "&action=insertar",


        success: function (respuesta) {


            if (respuesta.trim() === "ok") {


                $("#modalNuevaClasificacion")
                        .modal("hide");


                Swal.fire({

                    icon:
                        "success",

                    title:
                        "Clasificación registrada",

                    text:
                        "La clasificación fue registrada correctamente.",

                    timer:
                        1800,

                    showConfirmButton:
                        false

                });


                listarClasificaciones();


            } else {


                Swal.fire({

                    icon:
                        "error",

                    title:
                        "No se pudo registrar",

                    text:
                        "Verifique que la cantidad clasificada no supere la cantidad recolectada y que el tipo de residuo no esté repetido."

                });

            }

        },


        error: function () {


            Swal.fire({

                icon:
                    "error",

                title:
                    "Error",

                text:
                    "Ocurrió un error al registrar la clasificación."

            });

        }

    });

}


/* EDITAR */

function editarClasificacion(
        id,
        idRecoleccion,
        idTipo,
        cantidad,
        observaciones) {


    $("#editIdClasificacion")
            .val(id);


    cargarRecolecciones(
        "#editIdRecoleccion"
    );


    cargarTiposResiduos(
        "#editIdTipo"
    );


    setTimeout(function () {


        $("#editIdRecoleccion")
                .val(idRecoleccion);


        $("#editIdTipo")
                .val(idTipo);


        $("#editCantidadKg")
                .val(cantidad);


        $("#editObservaciones")
                .val(
                    observaciones || ""
                );


        $("#modalEditarClasificacion")
                .modal("show");


    }, 300);

}


/* ACTUALIZAR */

function actualizarClasificacion() {


    const formulario =
            document.getElementById(
                    "formEditarClasificacion"
            );


    if (!formulario.checkValidity()) {

        formulario.reportValidity();

        return;

    }


    $.ajax({

        url:
            "<%= request.getContextPath() %>/ClasificacionResiduoController",

        type: "POST",


        data:
            $("#formEditarClasificacion")
                    .serialize() +

            "&action=actualizar",


        success: function (respuesta) {


            if (respuesta.trim() === "ok") {


                $("#modalEditarClasificacion")
                        .modal("hide");


                Swal.fire({

                    icon:
                        "success",

                    title:
                        "Clasificación actualizada",

                    text:
                        "Los datos fueron actualizados correctamente.",

                    timer:
                        1800,

                    showConfirmButton:
                        false

                });


                listarClasificaciones();


            } else {


                Swal.fire({

                    icon:
                        "error",

                    title:
                        "No se pudo actualizar",

                    text:
                        "Verifique la cantidad y el tipo de residuo."

                });

            }

        },


        error: function () {


            Swal.fire({

                icon:
                    "error",

                title:
                    "Error",

                text:
                    "Ocurrió un error al actualizar."

            });

        }

    });

}


/* VER */

function verClasificacion(id) {


    $.ajax({

        url:
            "<%= request.getContextPath() %>/ClasificacionResiduoController",

        type: "POST",

        dataType: "json",


        data: {

            action:
                "buscar",

            id_clasificacion:
                id

        },


        success: function (data) {


            if (data.error) {


                Swal.fire({

                    icon:
                        "error",

                    title:
                        "Error",

                    text:
                        "No se encontró la clasificación."

                });

                return;

            }


            let cantidad =
                Number(
                    data.cantidad_kg
                );


            let cantidadFormateada =
                Number.isInteger(cantidad)
                ? cantidad.toString()
                : cantidad.toString();


            Swal.fire({

                title:
                    "Detalle de Clasificación",


                html:

                    "<div class='text-left'>" +

                    "<p><strong>ID:</strong> " +
                    data.id_clasificacion +
                    "</p>" +

                    "<p><strong>Recolección:</strong> #" +
                    data.id_recoleccion +
                    "</p>" +

                    "<p><strong>Tipo de Residuo:</strong> " +
                    data.id_tipo +
                    "</p>" +

                    "<p><strong>Cantidad:</strong> " +
                    cantidadFormateada +
                    " kg</p>" +

                    "<p><strong>Observaciones:</strong> " +
                    (data.observaciones || "-") +
                    "</p>" +

                    "</div>",


                confirmButtonText:
                    "Cerrar"

            });

        },


        error: function () {


            Swal.fire({

                icon:
                    "error",

                title:
                    "Error",

                text:
                    "No se pudo consultar la clasificación."

            });

        }

    });

}


/* DESACTIVAR */

function eliminarClasificacion(id) {


    Swal.fire({

        title:
            "¿Desactivar clasificación?",

        text:
            "La clasificación quedará inactiva.",

        icon:
            "warning",

        showCancelButton:
            true,

        confirmButtonText:
            "Sí, desactivar",

        cancelButtonText:
            "Cancelar"

    }).then(function (resultado) {


        if (resultado.isConfirmed) {


            $.ajax({

                url:
                    "<%= request.getContextPath() %>/ClasificacionResiduoController",

                type: "POST",


                data: {

                    action:
                        "eliminar",

                    id_clasificacion:
                        id

                },


                success: function (respuesta) {


                    if (respuesta.trim() === "ok") {


                        Swal.fire({

                            icon:
                                "success",

                            title:
                                "Clasificación desactivada",

                            text:
                                "La clasificación fue desactivada correctamente.",

                            timer:
                                1500,

                            showConfirmButton:
                                false

                        });


                        listarClasificaciones();


                    } else {


                        Swal.fire({

                            icon:
                                "error",

                            title:
                                "Error",

                            text:
                                "No se pudo desactivar la clasificación."

                        });

                    }

                },


                error: function () {


                    Swal.fire({

                        icon:
                            "error",

                        title:
                            "Error",

                        text:
                            "Ocurrió un error al desactivar la clasificación."

                    });

                }

            });

        }

    });

}


/* ACTIVAR */

function activarClasificacion(id) {


    Swal.fire({

        title:
            "¿Activar clasificación?",

        text:
            "La clasificación volverá a estar activa.",

        icon:
            "question",

        showCancelButton:
            true,

        confirmButtonText:
            "Sí, activar",

        cancelButtonText:
            "Cancelar"

    }).then(function (resultado) {


        if (resultado.isConfirmed) {


            $.ajax({

                url:
                    "<%= request.getContextPath() %>/ClasificacionResiduoController",

                type: "POST",


                data: {

                    action:
                        "activar",

                    id_clasificacion:
                        id

                },


                success: function (respuesta) {


                    if (respuesta.trim() === "ok") {


                        Swal.fire({

                            icon:
                                "success",

                            title:
                                "Clasificación activada",

                            text:
                                "La clasificación fue activada correctamente.",

                            timer:
                                1500,

                            showConfirmButton:
                                false

                        });


                        listarClasificaciones();


                    } else {


                        Swal.fire({

                            icon:
                                "error",

                            title:
                                "Error",

                            text:
                                "No se pudo activar la clasificación."

                        });

                    }

                },


                error: function () {


                    Swal.fire({

                        icon:
                            "error",

                        title:
                            "Error",

                        text:
                            "Ocurrió un error al activar la clasificación."

                    });

                }

            });

        }

    });

}


</script>


</div>

</body>

</html>