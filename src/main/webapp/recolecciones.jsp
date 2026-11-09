<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Recolecciones | JMQ SYSTEM</title>

    <style>

        /* ESTÁNDAR VISUAL DEL MÓDULO */

        .recolecciones-page {
            font-size: 14px;
        }


        /* TABLA */

        .recolecciones-page .table-recolecciones {
            width: 100%;
            table-layout: fixed;
            margin-bottom: 0;
            font-size: 14px !important;
        }

        .recolecciones-page .table-recolecciones th,
        .recolecciones-page .table-recolecciones td {
            vertical-align: middle;
            padding: 0.60rem 0.45rem;
            line-height: 1.35;
        }

        .recolecciones-page .table-recolecciones thead th {
            font-weight: 600;
            white-space: nowrap;
            text-align: center;
        }

        .recolecciones-page .table-recolecciones tbody td {
            font-size: 14px !important;
        }

        /* COLUMNAS */

        .col-id {
            width: 4%;
        }

        .col-fecha {
            width: 10%;
        }

        .col-hora {
            width: 8%;
        }

        .col-ruta {
            width: 14%;
        }

        .col-vehiculo {
            width: 9%;
        }

        .col-personal {
            width: 13%;
        }

        .col-cantidad {
            width: 8%;
        }

        .col-unidad {
            width: 6%;
        }

        .col-estado {
            width: 8%;
        }

        .col-acciones {
            width: 12%;
        }

        /* TEXTO */

        .cell-center {
            text-align: center;
            white-space: nowrap;
        }

        .cell-wrap {
            white-space: normal;
            word-break: normal;
            overflow-wrap: break-word;
        }

        .cell-nowrap {
            white-space: nowrap;
        }

        /* ACCIONES */

        .acciones-recoleccion {
            text-align: center;
            white-space: nowrap;
        }

        .btn-action {
            width: 30px;
            height: 30px;
            padding: 0;
            margin: 1px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            vertical-align: middle;
        }

        .btn-action i {
            font-size: 12px;
        }

        /* ESTADOS */

        .badge-estado {
            padding: 0.35em 0.55em;
            white-space: nowrap;
        }

        /* BUSCADOR */





        /* MODALES */

        .modal label {
            font-weight: 500;
            font-size: 13px;
        }

        .modal .form-control {
            font-size: 14px;
        }

        .modal .btn {
            font-size: 14px;
        }

        .ayuda-fecha {
            font-size: 11px;
            color: #6c757d;
            margin-top: 3px;
        }

    </style>

</head>


<body>


<div class="recolecciones-page">


    <!-- ENCABEZADO -->

    <!-- CONTENIDO -->

    <section class="content">

        <div class="container-fluid">


            <div class="card">


                <!-- CABECERA -->

                <div class="card-header">


                    <h3 class="card-title">

                        <i class="fas fa-recycle"></i>

                        Gestión de Recolecciones

                    </h3>


                    <div class="card-tools">


                        <!-- NUEVA RECOLECCIÓN -->

                        <button type="button"
                                class="btn btn-success btn-sm mr-1"
                                onclick="nuevaRecoleccion()">

                            <i class="fas fa-plus"></i>

                            Nueva Recolección

                        </button>


                        <!-- REPORTE GENERAL -->

                        <a href="<%= request.getContextPath() %>/reporte_general.jsp?modulo=recolecciones"
                           target="_blank"
                           class="btn btn-info btn-sm">

                            <i class="fas fa-file-pdf"></i>

                            Reporte General

                        </a>


                    </div>


                </div>


                <!-- CUERPO -->

                <div class="card-body">


                    <!-- TABLA -->

                    <div class="table-responsive">

                        <div id="contenedorRecolecciones">

                            <div class="text-center p-4">

                                <i class="fas fa-spinner fa-spin"></i>

                                Cargando recolecciones...

                            </div>

                        </div>

                    </div>


                </div>


            </div>


        </div>

    </section>


</div>


<!-- MODAL NUEVA RECOLECCIÓN -->

<div class="modal fade"
     id="modalNuevaRecoleccion"
     tabindex="-1">

    <div class="modal-dialog modal-lg">

        <div class="modal-content">


            <div class="modal-header bg-primary">

                <h5 class="modal-title text-white">

                    <i class="fas fa-plus-circle"></i>

                    Nueva Recolección

                </h5>


                <button type="button"
                        class="close text-white"
                        data-dismiss="modal">

                    &times;

                </button>

            </div>


            <div class="modal-body">


                <form id="formNuevaRecoleccion">


                    <!-- ASIGNACIÓN -->

                    <div class="form-group">

                        <label>

                            Asignación *

                        </label>


                        <select id="nuevoIdAsignacion"
                                name="id_asignacion"
                                class="form-control"
                                required>

                            <option value="">

                                Cargando asignaciones...

                            </option>

                        </select>

                        <div class="ayuda-fecha">

                            La fecha se obtiene automáticamente de la asignación seleccionada.

                        </div>

                    </div>


                    <!-- FECHA / HORAS -->

                    <div class="form-row">


                        <div class="form-group col-md-4">

                            <label>

                                Fecha *

                            </label>


                            <input type="date"
                                   id="nuevoFecha"
                                   name="fecha"
                                   class="form-control"
                                   readonly
                                   required>

                        </div>


                        <div class="form-group col-md-4">

                            <label>

                                Hora de inicio

                            </label>


                            <input type="time"
                                   id="nuevoHoraInicio"
                                   name="hora_inicio"
                                   class="form-control">

                        </div>


                        <div class="form-group col-md-4">

                            <label>

                                Hora de finalización

                            </label>


                            <input type="time"
                                   id="nuevoHoraFin"
                                   name="hora_fin"
                                   class="form-control">

                        </div>


                    </div>


                    <!-- CANTIDAD / UNIDAD -->

                    <div class="form-row">


                        <div class="form-group col-md-7">

                            <label>

                                Cantidad recolectada *

                            </label>


                            <input type="number"
                                   id="nuevoCantidad"
                                   name="cantidad_recolectada"
                                   class="form-control"
                                   min="0.01"
                                   step="0.01"
                                   required>

                        </div>


                        <div class="form-group col-md-5">

                            <label>

                                Unidad de medida *

                            </label>


                            <select id="nuevoUnidad"
                                    name="unidad_medida"
                                    class="form-control"
                                    required>

                                <option value="kg">

                                    Kilogramos (kg)

                                </option>


                                <option value="ton">

                                    Toneladas (ton)

                                </option>

                            </select>

                        </div>


                    </div>


                    <!-- OBSERVACIONES -->

                    <div class="form-group">

                        <label>

                            Observaciones

                        </label>


                        <textarea id="nuevoObservaciones"
                                  name="observaciones"
                                  class="form-control"
                                  rows="2"
                                  maxlength="255"
                                  placeholder="Ingrese observaciones de la recolección"></textarea>

                    </div>


                    <!-- CLASIFICACIÓN DE RESIDUOS (DETALLE) -->

                    <hr>

                    <h6 class="font-weight-bold">

                        <i class="fas fa-recycle"></i>

                        Clasificación de residuos *

                    </h6>

                    <div class="ayuda-fecha mb-2">

                        Se guarda junto con la recolección. Si ocurre un error, no se registra nada.

                    </div>


                    <div class="form-row align-items-end">

                        <div class="form-group col-md-4">

                            <label>Tipo de residuo</label>

                            <select id="detalleTipo"
                                    class="form-control">

                                <option value="0">Cargando tipos...</option>

                            </select>

                        </div>


                        <div class="form-group col-md-3">

                            <label>Cantidad (kg)</label>

                            <input type="number"
                                   id="detalleCantidad"
                                   class="form-control"
                                   min="0.01"
                                   step="0.01">

                        </div>


                        <div class="form-group col-md-3">

                            <label>Observación</label>

                            <input type="text"
                                   id="detalleObservacion"
                                   class="form-control"
                                   maxlength="255">

                        </div>


                        <div class="form-group col-md-2">

                            <button type="button"
                                    class="btn btn-success btn-block"
                                    onclick="agregarClasificacion()">

                                <i class="fas fa-plus"></i>

                                Agregar

                            </button>

                        </div>

                    </div>


                    <table class="table table-sm table-bordered">

                        <thead class="thead-light">

                            <tr>
                                <th>Tipo de residuo</th>
                                <th class="text-right">Cantidad (kg)</th>
                                <th>Observación</th>
                                <th class="text-center">Quitar</th>
                            </tr>

                        </thead>

                        <tbody id="tablaDetalleClasificacion">
                        </tbody>

                        <tfoot>

                            <tr>
                                <th class="text-right">Total clasificado</th>
                                <th class="text-right" id="totalClasificado">0.00</th>
                                <th colspan="2"></th>
                            </tr>

                        </tfoot>

                    </table>


                </form>


            </div>


            <div class="modal-footer">


                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-primary"
                        onclick="guardarRecoleccion()">

                    <i class="fas fa-save"></i>

                    Registrar Recolección

                </button>


            </div>


        </div>

    </div>

</div>


<!-- MODAL EDITAR RECOLECCIÓN -->

<div class="modal fade"
     id="modalEditarRecoleccion"
     tabindex="-1">

    <div class="modal-dialog modal-lg">

        <div class="modal-content">


            <div class="modal-header bg-warning">

                <h5 class="modal-title">

                    <i class="fas fa-edit"></i>

                    Modificar Recolección

                </h5>


                <button type="button"
                        class="close"
                        data-dismiss="modal">

                    &times;

                </button>

            </div>


            <div class="modal-body">


                <form id="formEditarRecoleccion">


                    <input type="hidden"
                           id="editIdRecoleccion"
                           name="id_recoleccion">


                    <!-- ASIGNACIÓN -->

                    <div class="form-group">

                        <label>

                            Asignación *

                        </label>


                        <select id="editIdAsignacion"
                                name="id_asignacion"
                                class="form-control"
                                required>

                        </select>

                        <div class="ayuda-fecha">

                            La fecha corresponde a la asignación seleccionada.

                        </div>

                    </div>


                    <!-- FECHA / HORAS -->

                    <div class="form-row">


                        <div class="form-group col-md-4">

                            <label>

                                Fecha *

                            </label>


                            <input type="date"
                                   id="editFecha"
                                   name="fecha"
                                   class="form-control"
                                   readonly
                                   required>

                        </div>


                        <div class="form-group col-md-4">

                            <label>

                                Hora de inicio

                            </label>


                            <input type="time"
                                   id="editHoraInicio"
                                   name="hora_inicio"
                                   class="form-control">

                        </div>


                        <div class="form-group col-md-4">

                            <label>

                                Hora de finalización

                            </label>


                            <input type="time"
                                   id="editHoraFin"
                                   name="hora_fin"
                                   class="form-control">

                        </div>


                    </div>


                    <!-- CANTIDAD / UNIDAD -->

                    <div class="form-row">


                        <div class="form-group col-md-7">

                            <label>

                                Cantidad recolectada *

                            </label>


                            <input type="number"
                                   id="editCantidad"
                                   name="cantidad_recolectada"
                                   class="form-control"
                                   min="0.01"
                                   step="0.01"
                                   required>

                        </div>


                        <div class="form-group col-md-5">

                            <label>

                                Unidad de medida *

                            </label>


                            <select id="editUnidad"
                                    name="unidad_medida"
                                    class="form-control"
                                    required>

                                <option value="kg">

                                    Kilogramos (kg)

                                </option>


                                <option value="ton">

                                    Toneladas (ton)

                                </option>

                            </select>

                        </div>


                    </div>


                    <!-- OBSERVACIONES -->

                    <div class="form-group">

                        <label>

                            Observaciones

                        </label>


                        <textarea id="editObservaciones"
                                  name="observaciones"
                                  class="form-control"
                                  rows="2"
                                  maxlength="255"></textarea>

                    </div>


                </form>


            </div>


            <div class="modal-footer">


                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-warning"
                        onclick="actualizarRecoleccion()">

                    <i class="fas fa-save"></i>

                    Actualizar

                </button>


            </div>


        </div>

    </div>

</div>


<script>


    /* INICIO */

    $(document).ready(function () {

        listarRecolecciones();

        cargarAsignaciones("#nuevoIdAsignacion");

        cargarAsignaciones("#editIdAsignacion");

    });


    /* LISTAR RECOLECCIONES */

    function listarRecolecciones() {

        $.ajax({

            url:
                "<%= request.getContextPath() %>/RecoleccionController",

            type: "POST",

            data: {
                action: "listar"
            },

            success: function (data) {

                if ($.fn.DataTable.isDataTable("#tablaRecolecciones")) {
                    $("#tablaRecolecciones").DataTable().destroy();
                }

                $("#contenedorRecolecciones")
                        .html(data);

                $("#tablaRecolecciones").DataTable({
                    responsive: true,
                    autoWidth: false,
                    order: [[0, "desc"]]
                });

            },

            error: function () {

                $("#contenedorRecolecciones")
                        .html(

                            "<div class='alert alert-danger'>" +

                            "No se pudieron cargar las recolecciones." +

                            "</div>"

                        );

            }

        });

    }




    /* NUEVA RECOLECCIÓN */

    function nuevaRecoleccion() {

        $("#formNuevaRecoleccion")[0].reset();

        $("#nuevoFecha").val("");

        $("#nuevoHoraInicio").val("");

        $("#nuevoHoraFin").val("");

        $("#nuevoCantidad").val("");

        $("#nuevoUnidad").val("kg");

        clasificaciones = [];

        mostrarClasificaciones();

        cargarTiposResiduo();

        cargarAsignaciones(
            "#nuevoIdAsignacion",
            true
        );

        $("#modalNuevaRecoleccion")
                .modal("show");

    }


    /*
        CLASIFICACIONES DE LA NUEVA RECOLECCIÓN (DETALLE)
        Se arman en memoria y se envían junto con la cabecera.
    */

    var clasificaciones = [];


    function cargarTiposResiduo() {

        $.ajax({

            url:
                "<%= request.getContextPath() %>/ClasificacionResiduoController",

            type: "POST",

            data: {
                action: "listarTipos"
            },

            success: function (data) {

                $("#detalleTipo").html(data);

            },

            error: function () {

                $("#detalleTipo").html(
                    "<option value='0'>No se pudieron cargar los tipos</option>"
                );

            }

        });

    }


    function agregarClasificacion() {

        const idTipo =
                parseInt($("#detalleTipo").val(), 10);

        const nombreTipo =
                $("#detalleTipo option:selected").text().trim();

        const cantidad =
                parseFloat($("#detalleCantidad").val());

        const observacion =
                $("#detalleObservacion").val().trim();


        if (!idTipo || idTipo <= 0) {

            Swal.fire("Atención", "Seleccione un tipo de residuo.", "warning");

            return;

        }


        if (isNaN(cantidad) || cantidad <= 0) {

            Swal.fire("Atención", "La cantidad debe ser mayor que cero.", "warning");

            return;

        }


        const repetido =
                clasificaciones.some(function (c) {
                    return c.idTipo === idTipo;
                });

        if (repetido) {

            Swal.fire("Atención", "Ese tipo de residuo ya fue agregado.", "warning");

            return;

        }


        clasificaciones.push({
            idTipo: idTipo,
            nombreTipo: nombreTipo,
            cantidad: cantidad,
            observacion: observacion
        });


        $("#detalleTipo").val("0");

        $("#detalleCantidad").val("");

        $("#detalleObservacion").val("");

        mostrarClasificaciones();

    }


    function quitarClasificacion(indice) {

        clasificaciones.splice(indice, 1);

        mostrarClasificaciones();

    }


    function mostrarClasificaciones() {

        let filas = "";

        let total = 0;


        clasificaciones.forEach(function (c, i) {

            total += c.cantidad;

            filas +=
                "<tr>" +
                    "<td>" + $("<div>").text(c.nombreTipo).html() + "</td>" +
                    "<td class='text-right'>" + c.cantidad.toFixed(2) + "</td>" +
                    "<td>" + $("<div>").text(c.observacion).html() + "</td>" +
                    "<td class='text-center'>" +
                        "<button type='button' class='btn btn-danger btn-sm' " +
                        "onclick='quitarClasificacion(" + i + ")'>" +
                        "<i class='fas fa-trash'></i>" +
                        "</button>" +
                    "</td>" +
                "</tr>";

        });


        if (clasificaciones.length === 0) {

            filas =
                "<tr><td colspan='4' class='text-center text-muted'>" +
                "Sin clasificaciones agregadas" +
                "</td></tr>";

        }


        $("#tablaDetalleClasificacion").html(filas);

        $("#totalClasificado").text(total.toFixed(2));

    }


    function parametrosClasificaciones() {

        let datos = "";

        clasificaciones.forEach(function (c) {

            datos +=
                "&detalle_tipo=" + encodeURIComponent(c.idTipo) +
                "&detalle_cantidad=" + encodeURIComponent(c.cantidad) +
                "&detalle_observaciones=" + encodeURIComponent(c.observacion);

        });

        return datos;

    }


    /* CARGAR ASIGNACIONES */

    function cargarAsignaciones(
            selector,
            limpiarFecha) {

        $.ajax({

            url:
                "<%= request.getContextPath() %>/RecoleccionController",

            type: "POST",

            data: {
                action: "listarAsignaciones"
            },

            success: function (data) {

                $(selector).html(data);


                if (limpiarFecha === true) {

                    if (selector === "#nuevoIdAsignacion") {

                        $("#nuevoFecha").val("");

                    }

                }

            },

            error: function () {

                $(selector).html(

                    "<option value='0'>" +

                    "No se pudieron cargar las asignaciones" +

                    "</option>"

                );

            }

        });

    }


    /* CAMBIO DE ASIGNACIÓN */

    $(document).on(
        "change",
        "#nuevoIdAsignacion",
        function () {

            const fecha =
                    $(this)
                    .find(":selected")
                    .data("fecha");


            if (fecha) {

                $("#nuevoFecha")
                        .val(fecha);

            } else {

                $("#nuevoFecha")
                        .val("");

            }

        }
    );


    $(document).on(
        "change",
        "#editIdAsignacion",
        function () {

            const fecha =
                    $(this)
                    .find(":selected")
                    .data("fecha");


            if (fecha) {

                $("#editFecha")
                        .val(fecha);

            }

        }
    );


    /* GUARDAR */

    function guardarRecoleccion() {

        const formulario =
                document.getElementById(
                    "formNuevaRecoleccion"
                );


        if (!formulario.checkValidity()) {

            formulario.reportValidity();

            return;

        }


        if (clasificaciones.length === 0) {

            Swal.fire(
                "Atención",
                "Debe agregar al menos una clasificación de residuos.",
                "warning"
            );

            return;

        }


        $.ajax({

            url:
                "<%= request.getContextPath() %>/RecoleccionController",

            type: "POST",

            data:
                $("#formNuevaRecoleccion")
                    .serialize() +
                "&action=insertar" +
                parametrosClasificaciones(),


            success: function (respuesta) {

                if (respuesta.trim() === "ok") {

                    $("#modalNuevaRecoleccion")
                            .modal("hide");


                    Swal.fire({

                        icon: "success",

                        title:
                            "Recolección registrada",

                        text:
                            "La recolección fue registrada correctamente.",

                        timer: 1800,

                        showConfirmButton: false

                    });


                    listarRecolecciones();


                } else {

                    Swal.fire(

                        "Error",

                        respuesta.trim(),

                        "error"

                    );

                }

            },


            error: function () {

                Swal.fire(

                    "Error",

                    "Ocurrió un error al registrar la recolección.",

                    "error"

                );

            }

        });

    }


    /* EDITAR */

    function editarRecoleccion(id) {

        $.ajax({

            url:
                "<%= request.getContextPath() %>/RecoleccionController",

            type: "POST",

            data: {

                action: "buscar",

                id_recoleccion: id

            },


            dataType: "json",


            success: function (data) {

                if (!data.success) {

                    Swal.fire(

                        "Error",

                        "No se encontró la recolección.",

                        "error"

                    );

                    return;

                }


                $("#editIdRecoleccion")
                        .val(
                            data.id_recoleccion
                        );


                $("#editIdAsignacion")
                        .val(
                            data.id_asignacion
                        );


                $("#editFecha")
                        .val(
                            data.fecha
                        );


                $("#editHoraInicio")
                        .val(

                            data.hora_inicio

                            ?

                            data.hora_inicio.substring(
                                0,
                                5
                            )

                            :

                            ""

                        );


                $("#editHoraFin")
                        .val(

                            data.hora_fin

                            ?

                            data.hora_fin.substring(
                                0,
                                5
                            )

                            :

                            ""

                        );


                $("#editCantidad")
                        .val(
                            data.cantidad_recolectada
                        );


                $("#editUnidad")
                        .val(

                            String(
                                data.unidad_medida
                            ).toLowerCase()

                        );


                $("#editObservaciones")
                        .val(
                            data.observaciones
                        );


                cargarAsignaciones(
                    "#editIdAsignacion"
                );


                setTimeout(function () {

                    $("#editIdAsignacion")
                            .val(
                                data.id_asignacion
                            );

                    const fecha =
                            $("#editIdAsignacion")
                            .find(":selected")
                            .data("fecha");

                    if (fecha) {

                        $("#editFecha")
                                .val(fecha);

                    }

                }, 300);


                $("#modalEditarRecoleccion")
                        .modal("show");

            },


            error: function () {

                Swal.fire(

                    "Error",

                    "No se pudo consultar la recolección.",

                    "error"

                );

            }

        });

    }


    /* ACTUALIZAR */

    function actualizarRecoleccion() {

        const formulario =
                document.getElementById(
                    "formEditarRecoleccion"
                );


        if (!formulario.checkValidity()) {

            formulario.reportValidity();

            return;

        }


        $.ajax({

            url:
                "<%= request.getContextPath() %>/RecoleccionController",

            type: "POST",

            data:
                $("#formEditarRecoleccion")
                    .serialize() +
                "&action=actualizar",


            success: function (respuesta) {

                if (respuesta.trim() === "ok") {

                    $("#modalEditarRecoleccion")
                            .modal("hide");


                    Swal.fire({

                        icon: "success",

                        title:
                            "Recolección actualizada",

                        text:
                            "Los datos fueron actualizados correctamente.",

                        timer: 1800,

                        showConfirmButton: false

                    });


                    listarRecolecciones();


                } else {

                    Swal.fire(

                        "Error",

                        respuesta.trim(),

                        "error"

                    );

                }

            },


            error: function () {

                Swal.fire(

                    "Error",

                    "Ocurrió un error al actualizar la recolección.",

                    "error"

                );

            }

        });

    }


    /* DESACTIVAR */

    function desactivarRecoleccion(id) {

        Swal.fire({

            title:
                "¿Desactivar recolección?",

            text:
                "La recolección quedará inactiva.",

            icon:
                "warning",

            showCancelButton:
                true,

            confirmButtonText:
                "Sí, desactivar",

            cancelButtonText:
                "Cancelar"

        }).then(function (resultado) {

            if (!resultado.isConfirmed) {

                return;

            }


            $.ajax({

                url:
                    "<%= request.getContextPath() %>/RecoleccionController",

                type:
                    "POST",

                data: {

                    action:
                        "desactivar",

                    id_recoleccion:
                        id

                },


                success:
                    function (respuesta) {

                        if (
                            respuesta.trim() ===
                            "ok"
                        ) {

                            Swal.fire({

                                icon:
                                    "success",

                                title:
                                    "Desactivada",

                                text:
                                    "La recolección fue desactivada correctamente.",

                                timer:
                                    1600,

                                showConfirmButton:
                                    false

                            });


                            listarRecolecciones();


                        } else {

                            Swal.fire(

                                "Error",

                                respuesta.trim(),

                                "error"

                            );

                        }

                    },


                error:
                    function () {

                        Swal.fire(

                            "Error",

                            "No se pudo desactivar la recolección.",

                            "error"

                        );

                    }

            });

        });

    }


    /* ACTIVAR */

    function activarRecoleccion(id) {

        Swal.fire({

            title:
                "¿Activar recolección?",

            text:
                "La recolección volverá a estar activa.",

            icon:
                "question",

            showCancelButton:
                true,

            confirmButtonText:
                "Sí, activar",

            cancelButtonText:
                "Cancelar"

        }).then(function (resultado) {

            if (!resultado.isConfirmed) {

                return;

            }


            $.ajax({

                url:
                    "<%= request.getContextPath() %>/RecoleccionController",

                type:
                    "POST",

                data: {

                    action:
                        "activar",

                    id_recoleccion:
                        id

                },


                success:
                    function (respuesta) {

                        if (
                            respuesta.trim() ===
                            "ok"
                        ) {

                            Swal.fire({

                                icon:
                                    "success",

                                title:
                                    "Activada",

                                text:
                                    "La recolección fue activada correctamente.",

                                timer:
                                    1600,

                                showConfirmButton:
                                    false

                            });


                            listarRecolecciones();


                        } else {

                            Swal.fire(

                                "Error",

                                respuesta.trim(),

                                "error"

                            );

                        }

                    },


                error:
                    function () {

                        Swal.fire(

                            "Error",

                            "No se pudo activar la recolección.",

                            "error"

                        );

                    }

            });

        });

    }

</script>

</body>

</html>