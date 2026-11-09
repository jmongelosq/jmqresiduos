<%@page import="com.model.Tarifa"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.util.Locale"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    /*
     * VERIFICAR SI LA PÁGINA FUE CARGADA DESDE EL CONTROLLER
     */

    List<Tarifa> tarifas =
            (List<Tarifa>) request.getAttribute("tarifas");

    if (tarifas == null) {

        response.sendRedirect(
                request.getContextPath()
                + "/TarifaController?accion=listar"
        );

        return;
    }
%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Tarifas | JMQ SYSTEM</title>


    <style>

        /* ESTILO GENERAL */





        /* TABLA */

        .table-tarifas {
            width: 100%;
            table-layout: fixed;
            margin-bottom: 0;
            font-size: 14px;
        }

        .table-tarifas th,
        .table-tarifas td {
            vertical-align: middle;
            padding: 0.45rem 0.45rem;
            line-height: 1.25;
        }

        .table-tarifas thead th {
            font-weight: 600;
            white-space: nowrap;
            text-align: center;
        }


        /* COLUMNAS */

        .col-id {
            width: 4%;
        }

        .col-nombre {
            width: 16%;
        }

        .col-descripcion {
            width: 24%;
        }

        .col-monto {
            width: 10%;
        }

        .col-periodicidad {
            width: 10%;
        }

        .col-inicio {
            width: 10%;
        }

        .col-fin {
            width: 8%;
        }

        .col-estado {
            width: 7%;
        }

        .col-acciones {
            width: 11%;
        }


        /* TEXTO */

        .cell-center {
            text-align: center;
            white-space: nowrap;
        }

        .cell-right {
            text-align: right;
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


        /* MONTO */

        .monto-tarifa {
            white-space: nowrap;
            text-align: right;
        }


        /* ACCIONES */

        .acciones-tarifa {
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
            font-size: 13px;
        }


        /* ESTADOS */

        .badge-estado {
            padding: 0.35em 0.55em;
            white-space: nowrap;
        }


        /* BOTONES SUPERIORES */



        /* DATATABLES */


        .dataTables_wrapper .dataTables_length,
        .dataTables_wrapper .dataTables_filter {
            margin-bottom: 10px;
        }

        .dataTables_wrapper .dataTables_length label,
        .dataTables_wrapper .dataTables_filter label {
            font-size: 13px;
            margin-bottom: 0;
        }

        .dataTables_wrapper .dataTables_length select {
            font-size: 13px;
            height: 32px;
            padding: 2px 25px 2px 8px;
        }

        .dataTables_wrapper .dataTables_filter input {
            font-size: 13px;
            height: 32px;
            margin-left: 5px;
            padding: 4px 8px;
        }

        .dataTables_wrapper .dataTables_info {
            font-size: 13px;
            padding-top: 8px;
        }

        .dataTables_wrapper .dataTables_paginate {
            font-size: 13px;
            padding-top: 5px;
        }

        .dataTables_wrapper .pagination {
            margin-bottom: 0;
        }

        .dataTables_wrapper .page-link {
            font-size: 13px;
            padding: 5px 9px;
        }


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


        /* CONTROL MANUAL DEL MODAL */

        .modal-manual {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1050;
            width: 100%;
            height: 100%;
            overflow-x: hidden;
            overflow-y: auto;
            outline: 0;
        }

        .modal-manual.mostrar {
            display: block;
        }

        .modal-manual .modal-dialog {
            margin: 1.75rem auto;
            pointer-events: none;
        }

        .modal-manual .modal-content {
            pointer-events: auto;
        }

        .modal-backdrop-manual {
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1040;
            width: 100vw;
            height: 100vh;
            background-color: #000;
            opacity: 0.5;
        }

        body.modal-abierto {
            overflow: hidden;
        }


        /* AJUSTES DATATABLES - ACCIONES */

        table.dataTable tbody td {
            vertical-align: middle;
        }

        table.dataTable thead th {
            vertical-align: middle;
        }

    </style>

</head>


<body>

<div class="container-fluid mt-3">

    <div class="card">

        <!-- ENCABEZADO -->

        <div class="card-header">

            <div class="d-flex justify-content-between align-items-center">

                <h3 class="card-title">
                    <i class="fas fa-money-bill-wave"></i>
                    Gestión de Tarifas
                </h3>

                <div>

                    <!-- NUEVA TARIFA -->
                    <button type="button"
                            class="btn btn-success btn-sm"
                            id="btnNuevaTarifa">
                        <i class="fas fa-plus"></i>
                        Nueva Tarifa
                    </button>

                    <!-- REPORTE GENERAL -->
                    <a href="<%= request.getContextPath() %>/TarifaController?accion=reporteGeneral"
                       target="_blank"
                       class="btn btn-info btn-sm">
                        <i class="fas fa-file-pdf"></i>
                        Reporte General
                    </a>

                </div>

            </div>

        </div>

        <div class="card-body">

            <!-- TABLA DE TARIFAS -->

            <div class="table-responsive">

                <table id="tablaTarifas"
                       class="table table-bordered table-hover table-tarifas">

                    <colgroup>

                        <col class="col-id">
                        <col class="col-nombre">
                        <col class="col-descripcion">
                        <col class="col-monto">
                        <col class="col-periodicidad">
                        <col class="col-inicio">
                        <col class="col-fin">
                        <col class="col-estado">
                        <col class="col-acciones">

                    </colgroup>


                    <thead>

                    <tr>

                        <th>ID</th>

                        <th>Nombre</th>

                        <th>Descripción</th>

                        <th>Monto</th>

                        <th>Periodicidad</th>

                        <th>Fecha Inicio</th>

                        <th>Fecha Fin</th>

                        <th>Estado</th>

                        <th>Acciones</th>

                    </tr>

                    </thead>


                    <tbody>

                    <%
                        if (!tarifas.isEmpty()) {

                            for (Tarifa t : tarifas) {
                    %>

                    <tr>

                        <!-- ID -->

                        <td class="cell-center">

                            <%= t.getIdTarifa() %>

                        </td>


                        <!-- NOMBRE -->

                        <td class="cell-wrap">

                            <%= t.getNombre() %>

                        </td>


                        <!-- DESCRIPCIÓN -->

                        <td class="cell-wrap">

                            <%= t.getDescripcion() != null
                                    ? t.getDescripcion()
                                    : "" %>

                        </td>


                        <!-- MONTO -->

                        <td class="monto-tarifa">

                            <%
                                DecimalFormatSymbols simbolos =
                                        DecimalFormatSymbols.getInstance(
                                                Locale.GERMANY
                                        );

                                DecimalFormat formatoMonto =
                                        new DecimalFormat(
                                                "#,##0",
                                                simbolos
                                        );
                            %>

                            Gs.

                            <%= t.getMonto() != null
                                    ? formatoMonto.format(t.getMonto())
                                    : "0" %>

                        </td>


                        <!-- PERIODICIDAD -->

                        <td class="cell-center">

                            <%= t.getPeriodicidad() %>

                        </td>


                        <!-- FECHA INICIO -->

                        <td class="cell-center">

                            <%= t.getFechaInicio() != null
                                    ? t.getFechaInicio()
                                    : "" %>

                        </td>


                        <!-- FECHA FIN -->

                        <td class="cell-center">

                            <%= t.getFechaFin() != null
                                    ? t.getFechaFin()
                                    : "-" %>

                        </td>


                        <!-- ESTADO -->

                        <td class="cell-center">

                            <%
                                if (t.isEstado()) {
                            %>

                            <span class="badge badge-success badge-estado">

                                ACTIVA

                            </span>

                            <%
                                } else {
                            %>

                            <span class="badge badge-danger badge-estado">

                                INACTIVA

                            </span>

                            <%
                                }
                            %>

                        </td>


                        <!-- ACCIONES -->

                        <td class="acciones-tarifa">

                            <!-- MODIFICAR -->

                            <button type="button"
                                    class="btn btn-warning btn-sm btn-action btn-editar-tarifa"
                                    title="Modificar tarifa"

                                    data-id="<%= t.getIdTarifa() %>"

                                    data-nombre="<%= safeHTML(t.getNombre()) %>"

                                    data-descripcion="<%= safeHTML(t.getDescripcion()) %>"

                                    data-monto="<%= t.getMonto() != null
                                            ? t.getMonto().stripTrailingZeros().toPlainString()
                                            : "" %>"

                                    data-periodicidad="<%= safeHTML(t.getPeriodicidad()) %>"

                                    data-fecha-inicio="<%= t.getFechaInicio() != null
                                            ? t.getFechaInicio()
                                            : "" %>"

                                    data-fecha-fin="<%= t.getFechaFin() != null
                                            ? t.getFechaFin()
                                            : "" %>">

                                <i class="fas fa-edit"></i>

                            </button>


                            <!-- REPORTE INDIVIDUAL -->

                            <a href="<%= request.getContextPath() %>/TarifaController?accion=reporteIndividual&idTarifa=<%= t.getIdTarifa() %>"
                               target="_blank"
                               class="btn btn-info btn-sm btn-action"
                               title="Ver reporte individual">

                                <i class="fas fa-eye"></i>

                            </a>


                            <%
                                if (t.isEstado()) {
                            %>


                            <!-- DESACTIVAR -->

                            <a href="<%= request.getContextPath() %>/TarifaController?accion=desactivar&idTarifa=<%= t.getIdTarifa() %>"
                               class="btn btn-danger btn-sm btn-action"
                               title="Desactivar tarifa"
                               onclick="return confirm('¿Desea desactivar esta tarifa?');">

                                <i class="fas fa-ban"></i>

                            </a>


                            <%
                                } else {
                            %>


                            <!-- ACTIVAR -->

                            <a href="<%= request.getContextPath() %>/TarifaController?accion=activar&idTarifa=<%= t.getIdTarifa() %>"
                               class="btn btn-success btn-sm btn-action"
                               title="Activar tarifa">

                                <i class="fas fa-check"></i>

                            </a>


                            <%
                                }
                            %>

                        </td>

                    </tr>

                    <%
                            }

                        } else {
                    %>

                    <tr>

                        <td colspan="9"
                            class="text-center">

                            No existen tarifas registradas.

                        </td>

                    </tr>

                    <%
                        }
                    %>

                    </tbody>

                </table>

            </div>

        </div>

    </div>

</div>


<!-- MODAL NUEVA TARIFA -->

<div class="modal modal-manual"
     id="modalNuevaTarifa"
     tabindex="-1"
     role="dialog"
     aria-labelledby="modalNuevaTarifaLabel"
     aria-hidden="true">

    <div class="modal-dialog modal-lg"
         role="document">

        <div class="modal-content">

            <form method="post"
                  action="<%= request.getContextPath() %>/TarifaController">

                <input type="hidden"
                       name="accion"
                       value="insertar">


                <div class="modal-header bg-primary">

                    <h5 class="modal-title text-white"
                        id="modalNuevaTarifaLabel">

                        <i class="fas fa-money-bill-wave"></i>

                        Nueva Tarifa

                    </h5>


                    <button type="button"
                            class="close text-white"
                            id="cerrarNuevaTarifa"
                            aria-label="Cerrar">

                        <span aria-hidden="true">
                            &times;
                        </span>

                    </button>

                </div>


                <div class="modal-body">

                    <!-- NOMBRE / MONTO -->

                    <div class="form-row">

                        <div class="form-group col-md-6">

                            <label>
                                Nombre *
                            </label>

                            <input type="text"
                                   name="nombre"
                                   class="form-control"
                                   maxlength="120"
                                   required>

                        </div>


                        <div class="form-group col-md-6">

                            <label>
                                Monto *
                            </label>

                            <input type="number"
                                   name="monto"
                                   class="form-control"
                                   min="0"
                                   step="1"
                                   inputmode="numeric"
                                   required>

                        </div>

                    </div>


                    <!-- DESCRIPCIÓN -->

                    <div class="form-group">

                        <label>
                            Descripción
                        </label>

                        <textarea name="descripcion"
                                  class="form-control"
                                  maxlength="255"
                                  rows="2"></textarea>

                    </div>


                    <!-- PERIODICIDAD / FECHAS -->

                    <div class="form-row">

                        <div class="form-group col-md-4">

                            <label>
                                Periodicidad *
                            </label>

                            <select name="periodicidad"
                                    class="form-control"
                                    required>

                                <option value="MENSUAL">
                                    Mensual
                                </option>

                                <option value="TRIMESTRAL">
                                    Trimestral
                                </option>

                                <option value="SEMESTRAL">
                                    Semestral
                                </option>

                                <option value="ANUAL">
                                    Anual
                                </option>

                                <option value="UNICO">
                                    Único
                                </option>

                            </select>

                        </div>


                        <div class="form-group col-md-4">

                            <label>
                                Fecha Inicio *
                            </label>

                            <input type="date"
                                   name="fechaInicio"
                                   class="form-control"
                                   required>

                        </div>


                        <div class="form-group col-md-4">

                            <label>
                                Fecha Fin
                            </label>

                            <input type="date"
                                   name="fechaFin"
                                   class="form-control">

                        </div>

                    </div>

                </div>


                <div class="modal-footer">

                    <button type="button"
                            class="btn btn-secondary"
                            id="cerrarNuevaTarifaFooter">

                        Cancelar

                    </button>


                    <button type="submit"
                            class="btn btn-primary">

                        <i class="fas fa-save"></i>

                        Guardar

                    </button>

                </div>

            </form>

        </div>

    </div>

</div>


<!-- MODAL MODIFICAR TARIFA -->

<div class="modal modal-manual"
     id="modalEditarTarifa"
     tabindex="-1"
     role="dialog"
     aria-labelledby="modalEditarTarifaLabel"
     aria-hidden="true">

    <div class="modal-dialog modal-lg"
         role="document">

        <div class="modal-content">

            <form method="post"
                  action="<%= request.getContextPath() %>/TarifaController">

                <input type="hidden"
                       name="accion"
                       value="actualizar">


                <input type="hidden"
                       id="editIdTarifa"
                       name="idTarifa">


                <div class="modal-header bg-warning">

                    <h5 class="modal-title"
                        id="modalEditarTarifaLabel">

                        <i class="fas fa-edit"></i>

                        Modificar Tarifa

                    </h5>


                    <button type="button"
                            class="close"
                            id="cerrarEditarTarifa"
                            aria-label="Cerrar">

                        <span aria-hidden="true">
                            &times;
                        </span>

                    </button>

                </div>


                <div class="modal-body">

                    <!-- NOMBRE / MONTO -->

                    <div class="form-row">

                        <div class="form-group col-md-6">

                            <label>
                                Nombre *
                            </label>

                            <input type="text"
                                   id="editNombre"
                                   name="nombre"
                                   class="form-control"
                                   maxlength="120"
                                   required>

                        </div>


                        <div class="form-group col-md-6">

                            <label>
                                Monto *
                            </label>

                            <input type="number"
                                   id="editMonto"
                                   name="monto"
                                   class="form-control"
                                   min="0"
                                   step="1"
                                   inputmode="numeric"
                                   required>

                        </div>

                    </div>


                    <!-- DESCRIPCIÓN -->

                    <div class="form-group">

                        <label>
                            Descripción
                        </label>

                        <textarea id="editDescripcion"
                                  name="descripcion"
                                  class="form-control"
                                  maxlength="255"
                                  rows="2"></textarea>

                    </div>


                    <!-- PERIODICIDAD / FECHAS -->

                    <div class="form-row">

                        <div class="form-group col-md-4">

                            <label>
                                Periodicidad *
                            </label>

                            <select id="editPeriodicidad"
                                    name="periodicidad"
                                    class="form-control"
                                    required>

                                <option value="MENSUAL">
                                    Mensual
                                </option>

                                <option value="TRIMESTRAL">
                                    Trimestral
                                </option>

                                <option value="SEMESTRAL">
                                    Semestral
                                </option>

                                <option value="ANUAL">
                                    Anual
                                </option>

                                <option value="UNICO">
                                    Único
                                </option>

                            </select>

                        </div>


                        <div class="form-group col-md-4">

                            <label>
                                Fecha Inicio *
                            </label>

                            <input type="date"
                                   id="editFechaInicio"
                                   name="fechaInicio"
                                   class="form-control"
                                   required>

                        </div>


                        <div class="form-group col-md-4">

                            <label>
                                Fecha Fin
                            </label>

                            <input type="date"
                                   id="editFechaFin"
                                   name="fechaFin"
                                   class="form-control">

                        </div>

                    </div>

                </div>


                <div class="modal-footer">

                    <button type="button"
                            class="btn btn-secondary"
                            id="cancelarEditarTarifa">

                        Cancelar

                    </button>


                    <button type="submit"
                            class="btn btn-warning">

                        <i class="fas fa-save"></i>

                        Actualizar

                    </button>

                </div>

            </form>

        </div>

    </div>

</div>


<script>

    /*
     * DATATABLES
     */

    $(document).ready(function () {

        $('#tablaTarifas').DataTable({

            /*
             * Mostrar 10 registros inicialmente.
             */

            pageLength: 10,

            /*
             * Opciones del selector.
             */

            lengthMenu: [
                [10, 25, 50, 100],
                [10, 25, 50, 100]
            ],

            /*
             * No ordenar inicialmente por ninguna columna.
             * Conservamos el orden entregado por la base de datos.
             */

            order: [],

            /*
             * Desactivar ordenamiento en Acciones.
             */

            columnDefs: [
                {
                    orderable: false,
                    searchable: false,
                    targets: 8
                }
            ],

            /*
             * Traducción completa al español.
             */

            language: {

                decimal: ",",

                thousands: ".",

                processing: "Procesando...",

                search: "Buscar:",

                lengthMenu:
                        "Mostrar _MENU_ registros",

                info:
                        "Mostrando _START_ a _END_ de _TOTAL_ registros",

                infoEmpty:
                        "Mostrando 0 a 0 de 0 registros",

                infoFiltered:
                        "(filtrado de _MAX_ registros)",

                infoPostFix: "",

                loadingRecords:
                        "Cargando...",

                zeroRecords:
                        "No se encontraron registros",

                emptyTable:
                        "No existen tarifas registradas",

                paginate: {

                    first: "Primero",

                    previous: "Anterior",

                    next: "Siguiente",

                    last: "Último"

                },

                aria: {

                    sortAscending:
                            ": activar para ordenar la columna de manera ascendente",

                    sortDescending:
                            ": activar para ordenar la columna de manera descendente"

                }

            }

        });

    });


    /*
     * MODALES MANUALES
     *
     * No utilizamos:
     *
     *     $("#modalEditarTarifa").modal("show");
     *
     * para evitar el conflicto anterior con Bootstrap.
     */

    var modalEditar =
            document.getElementById(
                    "modalEditarTarifa"
            );

    var modalNueva =
            document.getElementById(
                    "modalNuevaTarifa"
            );


    /*
     * CREAR FONDO
     */

    function crearBackdrop() {

        var existente =
                document.getElementById(
                        "modalBackdropTarifa"
                );

        if (existente) {
            return;
        }

        var backdrop =
                document.createElement("div");

        backdrop.id =
                "modalBackdropTarifa";

        backdrop.className =
                "modal-backdrop-manual";

        document.body.appendChild(
                backdrop
        );
    }


    /*
     * ELIMINAR FONDO
     */

    function eliminarBackdrop() {

        var backdrop =
                document.getElementById(
                        "modalBackdropTarifa"
                );

        if (backdrop) {

            backdrop.remove();

        }

    }


    /*
     * ABRIR MODAL
     */

    function abrirModal(modal) {

        if (!modal) {
            return;
        }

        crearBackdrop();

        modal.classList.add(
                "mostrar"
        );

        modal.setAttribute(
                "aria-hidden",
                "false"
        );

        document.body.classList.add(
                "modal-abierto"
        );

    }


    /*
     * CERRAR MODAL
     */

    function cerrarModal(modal) {

        if (!modal) {
            return;
        }

        modal.classList.remove(
                "mostrar"
        );

        modal.setAttribute(
                "aria-hidden",
                "true"
        );

        document.body.classList.remove(
                "modal-abierto"
        );

        eliminarBackdrop();

    }


    /*
     * NUEVA TARIFA
     */

    var btnNuevaTarifa =
            document.getElementById(
                    "btnNuevaTarifa"
            );


    if (btnNuevaTarifa) {

        btnNuevaTarifa.addEventListener(
                "click",
                function () {

                    abrirModal(
                            modalNueva
                    );

                }
        );

    }


    /*
     * CERRAR NUEVA TARIFA
     */

    var cerrarNuevaTarifa =
            document.getElementById(
                    "cerrarNuevaTarifa"
            );


    var cerrarNuevaTarifaFooter =
            document.getElementById(
                    "cerrarNuevaTarifaFooter"
            );


    if (cerrarNuevaTarifa) {

        cerrarNuevaTarifa.addEventListener(
                "click",
                function () {

                    cerrarModal(
                            modalNueva
                    );

                }
        );

    }


    if (cerrarNuevaTarifaFooter) {

        cerrarNuevaTarifaFooter.addEventListener(
                "click",
                function () {

                    cerrarModal(
                            modalNueva
                    );

                }
        );

    }


    /*
     * MODIFICAR TARIFA
     */

    document.addEventListener(
            "click",
            function (event) {

                var boton =
                        event.target.closest(
                                ".btn-editar-tarifa"
                        );

                if (!boton) {
                    return;
                }


                event.preventDefault();


                /*
                 * OBTENER DATOS
                 */

                var id =
                        boton.getAttribute(
                                "data-id"
                        );

                var nombre =
                        boton.getAttribute(
                                "data-nombre"
                        );

                var descripcion =
                        boton.getAttribute(
                                "data-descripcion"
                        );

                var monto =
                        boton.getAttribute(
                                "data-monto"
                        );

                var periodicidad =
                        boton.getAttribute(
                                "data-periodicidad"
                        );

                var fechaInicio =
                        boton.getAttribute(
                                "data-fecha-inicio"
                        );

                var fechaFin =
                        boton.getAttribute(
                                "data-fecha-fin"
                        );


                /*
                 * NORMALIZAR MONTO
                 */

                if (
                        monto !== null &&
                        monto !== ""
                ) {

                    var numeroMonto =
                            Number(monto);

                    if (
                            !isNaN(numeroMonto)
                    ) {

                        monto =
                                String(
                                        numeroMonto
                                );

                    }

                }


                /*
                 * CARGAR FORMULARIO
                 */

                document.getElementById(
                        "editIdTarifa"
                ).value =
                        id || "";


                document.getElementById(
                        "editNombre"
                ).value =
                        nombre || "";


                document.getElementById(
                        "editDescripcion"
                ).value =
                        descripcion || "";


                document.getElementById(
                        "editMonto"
                ).value =
                        monto || "";


                document.getElementById(
                        "editPeriodicidad"
                ).value =
                        periodicidad || "MENSUAL";


                document.getElementById(
                        "editFechaInicio"
                ).value =
                        fechaInicio || "";


                document.getElementById(
                        "editFechaFin"
                ).value =
                        fechaFin || "";


                /*
                 * ABRIR MODAL
                 */

                abrirModal(
                        modalEditar
                );

            }
    );


    /*
     * CERRAR MODIFICAR
     */

    var cerrarEditarTarifa =
            document.getElementById(
                    "cerrarEditarTarifa"
            );


    var cancelarEditarTarifa =
            document.getElementById(
                    "cancelarEditarTarifa"
            );


    if (cerrarEditarTarifa) {

        cerrarEditarTarifa.addEventListener(
                "click",
                function () {

                    cerrarModal(
                            modalEditar
                    );

                }
        );

    }


    if (cancelarEditarTarifa) {

        cancelarEditarTarifa.addEventListener(
                "click",
                function () {

                    cerrarModal(
                            modalEditar
                    );

                }
        );

    }


    /*
     * CERRAR HACIENDO CLICK EN EL FONDO
     */

    if (modalEditar) {

        modalEditar.addEventListener(
                "click",
                function (event) {

                    if (
                            event.target === modalEditar
                    ) {

                        cerrarModal(
                                modalEditar
                        );

                    }

                }
        );

    }


    if (modalNueva) {

        modalNueva.addEventListener(
                "click",
                function (event) {

                    if (
                            event.target === modalNueva
                    ) {

                        cerrarModal(
                                modalNueva
                        );

                    }

                }
        );

    }


    /*
     * ESC PARA CERRAR
     */

    document.addEventListener(
            "keydown",
            function (event) {

                if (
                        event.key === "Escape"
                ) {

                    if (
                            modalEditar &&
                            modalEditar.classList.contains(
                                    "mostrar"
                            )
                    ) {

                        cerrarModal(
                                modalEditar
                        );

                    }


                    if (
                            modalNueva &&
                            modalNueva.classList.contains(
                                    "mostrar"
                            )
                    ) {

                        cerrarModal(
                                modalNueva
                        );

                    }

                }

            }
    );

</script>


</body>

</html>


<%!

    // SEGURIDAD PARA ATRIBUTOS HTML

    private String safeHTML(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("'", "&#39;")
                .replace("\r", "")
                .replace("\n", " ");
    }

%>