<%@page import="com.model.Pago"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.util.Locale"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Pago> pagos =
            (List<Pago>) request.getAttribute("pagos");

    if (pagos == null) {

        response.sendRedirect(
                request.getContextPath()
                + "/PagoController?accion=listar"
        );

        return;
    }

    /*
     * FORMATO DE MONTOS
     *
     * Ejemplo:
     *
     * 50000.00  -> 50.000
     * 85000.00  -> 85.000
     * 120000.00 -> 120.000
     */

    DecimalFormatSymbols simbolosMonto =
            DecimalFormatSymbols.getInstance(Locale.GERMANY);

    DecimalFormat formatoMonto =
            new DecimalFormat(
                    "#,##0",
                    simbolosMonto
            );
%>


<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Pagos | JMQ SYSTEM</title>


    <style>

        /* ESTILO GENERAL */





        /* TABLA */

        .table-pagos {

            width: 100% !important;

            margin-bottom: 0;

            font-size: 13px;

        }


        .table-pagos th,
        .table-pagos td {

            vertical-align: middle;

            padding: 0.4rem 0.35rem;

            line-height: 1.25;
        }


        .table-pagos thead th {


            font-weight: 600;

            white-space: nowrap;

            text-align: center;
        }


        /* COLUMNAS */

        .col-acciones {
            width: 124px;
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

            min-width: 140px;

            white-space: normal;

            word-break: normal;

            overflow-wrap: break-word;
        }


        .cell-nowrap {

            white-space: nowrap;
        }


        /* MONTO */

        .monto-pago {

            text-align: right;

            white-space: nowrap;
        }


        /* ACCIONES */

        .acciones-pago {

            text-align: center;

            white-space: nowrap;
        }


        .btn-action {

            width: 26px;

            height: 26px;

            padding: 0;

            margin: 1px;

            display: inline-flex;

            align-items: center;

            justify-content: center;

            vertical-align: middle;
        }


        .btn-action i {

            font-size: 11px;
        }


        /* ESTADOS */

        .badge-estado {


            padding: 0.35em 0.55em;

            white-space: nowrap;
        }


        /* BOTONES SUPERIORES */



        /* FILTRO */









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


        /* DATOS DE FACTURA */

        .datos-factura {

            background: #f8f9fa;

            border: 1px solid #dee2e6;

            border-radius: 4px;

            padding: 12px;

            margin-bottom: 15px;
        }


        .datos-factura-titulo {

            font-size: 13px;

            font-weight: 600;

            margin-bottom: 10px;

            color: #343a40;
        }


        .dato-label {

            font-size: 11px;

            color: #6c757d;

            margin-bottom: 2px;
        }


        .dato-valor {

            font-size: 13px;

            font-weight: 500;

            min-height: 20px;
        }


        /* RESULTADO DE BÚSQUEDA */

        .resultado-factura {

            display: none;

            margin-top: 10px;

            margin-bottom: 15px;
        }


        .mensaje-factura {

            font-size: 12px;

            margin-top: 5px;

            display: none;
        }


        /* INPUT FACTURA */

        .grupo-factura {

            margin-bottom: 12px;
        }


        .grupo-factura .form-control {

            height: 38px;
        }


        .btn-buscar-factura {

            height: 38px;

            min-width: 110px;
        }


        /* CAMPOS SOLO LECTURA */

        .campo-factura {

            background-color: #f8f9fa !important;

            cursor: not-allowed;
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
                    <i class="fas fa-credit-card"></i>
                    Gestión de Pagos
                </h3>

                <div>

                    <!-- NUEVO PAGO -->
                    <button type="button"
                            class="btn btn-success btn-sm"
                            data-toggle="modal"
                            data-target="#modalNuevoPago"
                            onclick="prepararNuevoPago()">
                        <i class="fas fa-plus"></i>
                        Nuevo Pago
                    </button>

                    <!-- REPORTE GENERAL -->
                    <a href="<%= request.getContextPath() %>/PagoController?accion=reporteGeneral"
                       target="_blank"
                       class="btn btn-info btn-sm">
                        <i class="fas fa-file-pdf"></i>
                        Reporte General
                    </a>

                </div>

            </div>

        </div>

        <div class="card-body">

            <!-- TABLA DE PAGOS -->

            <div class="table-responsive">

                <table id="tablaListadoPagos" class="table table-bordered table-hover table-pagos">


                    <colgroup>

                        <col>

                        <col>

                        <col>

                        <col>

                        <col>

                        <col>

                        <col>

                        <col>

                        <col>

                        <col class="col-acciones">

                    </colgroup>


                    <thead>

                    <tr>

                        <th>ID</th>

                        <th>Factura</th>

                        <th>Cliente</th>

                        <th>Fecha</th>

                        <th>Período</th>

                        <th>Monto</th>

                        <th>Método</th>

                        <th>Recibo</th>

                        <th>Estado</th>

                        <th>Acciones</th>

                    </tr>

                    </thead>


                    <tbody id="tablaPagos">


                    <%

                        if (!pagos.isEmpty()) {

                            for (Pago p : pagos) {

                    %>


                    <tr>


                        <!-- ID -->

                        <td class="cell-center">

                            <%= p.getIdPago() %>

                        </td>


                        <!-- FACTURA -->

                        <td class="cell-center cell-nowrap">

                            <%= p.getNumeroFactura() != null
                                    ? p.getNumeroFactura()
                                    : "-" %>

                        </td>


                        <!-- CLIENTE -->

                        <td class="cell-wrap">

                            <%= p.getNombreCompletoCliente() != null
                                    ? p.getNombreCompletoCliente()
                                    : "-" %>

                        </td>


                        <!-- FECHA -->

                        <td class="cell-center cell-nowrap">

                            <%= p.getFechaPago() != null
                                    ? p.getFechaPago()
                                    : "-" %>

                        </td>


                        <!-- PERÍODO -->

                        <td class="cell-center cell-nowrap">

                            <%= p.getPeriodo() != null
                                    ? p.getPeriodo()
                                    : "-" %>

                        </td>


                        <!-- MONTO -->

                        <td class="monto-pago">

                            Gs.

                            <%= p.getMonto() != null
                                    ? formatoMonto.format(
                                            p.getMonto()
                                      )
                                    : "0" %>

                        </td>


                        <!-- MÉTODO -->

                        <td class="cell-center cell-nowrap">

                            <%= p.getMetodoPago() != null
                                    ? p.getMetodoPago()
                                    : "-" %>

                        </td>


                        <!-- RECIBO -->

                        <td class="cell-center cell-nowrap">

                            <%= p.getNumeroRecibo() != null
                                    ? p.getNumeroRecibo()
                                    : "-" %>

                        </td>


                        <!-- ESTADO -->

                        <td class="cell-center">

                            <%

                                if ("PAGADO".equals(
                                        p.getEstado())) {

                            %>

                            <span class="badge badge-success badge-estado">

                                PAGADO

                            </span>

                            <%

                                } else {

                            %>

                            <span class="badge badge-danger badge-estado">

                                ANULADO

                            </span>

                            <%

                                }

                            %>

                        </td>


                        <!-- ACCIONES -->

                        <td class="acciones-pago">


                            <!-- MODIFICAR -->

                            <button type="button"
                                    class="btn btn-warning btn-sm btn-action"
                                    title="Modificar pago"
                                    onclick="editarPago(
                                        '<%= p.getIdPago() %>',
                                        '<%= p.getIdFactura() %>',
                                        '<%= safeJS(p.getNumeroFactura()) %>',
                                        '<%= safeJS(p.getNombreCompletoCliente()) %>',
                                        '<%= safeJS(p.getPeriodoFactura()) %>',
                                        '<%= p.getTotalFactura() != null ? p.getTotalFactura() : "" %>',
                                        '<%= safeJS(p.getEstadoFactura()) %>',
                                        '<%= p.getFechaPago() != null ? p.getFechaPago() : "" %>',
                                        '<%= safeJS(p.getPeriodo()) %>',
                                        '<%= p.getMonto() != null ? p.getMonto() : "" %>',
                                        '<%= safeJS(p.getMetodoPago()) %>',
                                        '<%= safeJS(p.getNumeroRecibo()) %>',
                                        '<%= safeJS(p.getObservaciones()) %>'
                                    )">

                                <i class="fas fa-edit"></i>

                            </button>


                            <!-- REPORTE INDIVIDUAL -->

                            <a href="<%= request.getContextPath() %>/PagoController?accion=reporteIndividual&idPago=<%= p.getIdPago() %>"
                               target="_blank"
                               class="btn btn-info btn-sm btn-action"
                               title="Ver comprobante de pago">

                                <i class="fas fa-eye"></i>

                            </a>


                            <a href="<%= request.getContextPath() %>/FacturaController?accion=reporteIndividual&id_factura=<%= p.getIdFactura() %>"
                               target="_blank"
                               class="btn btn-secondary btn-sm btn-action"
                               title="Ver factura">

                                <i class="fas fa-file-invoice"></i>

                            </a>


                            <%

                                if ("PAGADO".equals(
                                        p.getEstado())) {

                            %>


                            <!-- ANULAR -->

                            <a href="<%= request.getContextPath() %>/PagoController?accion=anular&idPago=<%= p.getIdPago() %>"
                               class="btn btn-danger btn-sm btn-action"
                               title="Anular pago"
                               onclick="return confirm('¿Desea anular este pago?');">

                                <i class="fas fa-ban"></i>

                            </a>


                            <%

                                } else {

                            %>


                            <!-- ACTIVAR -->

                            <a href="<%= request.getContextPath() %>/PagoController?accion=activar&idPago=<%= p.getIdPago() %>"
                               class="btn btn-success btn-sm btn-action"
                               title="Activar pago"
                               onclick="return confirm('¿Desea marcar nuevamente este pago como PAGADO?');">

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


                    <tr id="filaSinPagos">

                        <td colspan="10"
                            class="text-center">

                            No existen pagos registrados.

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


<!-- MODAL NUEVO PAGO -->

<div class="modal fade"
     id="modalNuevoPago"
     tabindex="-1">

    <div class="modal-dialog modal-lg">

        <div class="modal-content">


            <form method="post"
                  action="<%= request.getContextPath() %>/PagoController"
                  onsubmit="return validarPagoNuevo();">


                <input type="hidden"
                       name="accion"
                       value="insertar">


                <input type="hidden"
                       name="idFactura"
                       id="nuevoIdFactura">


                <!-- ENCABEZADO -->

                <div class="modal-header bg-primary">

                    <h5 class="modal-title text-white">

                        <i class="fas fa-credit-card"></i>

                        Registrar Pago

                    </h5>


                    <button type="button"
                            class="close text-white"
                            data-dismiss="modal">

                        &times;

                    </button>

                </div>


                <div class="modal-body">


                    <!-- BÚSQUEDA DE FACTURA -->

                    <div class="grupo-factura">

                        <label>

                            Factura *

                        </label>


                        <div class="input-group">

                            <input type="text"
                                   id="nuevoNumeroFactura"
                                   class="form-control"
                                   placeholder="Ingrese el N.º de factura"
                                   maxlength="50"
                                   autocomplete="off">


                            <div class="input-group-append">

                                <button type="button"
                                        class="btn btn-primary btn-buscar-factura"
                                        onclick="buscarFacturaNuevo()">

                                    <i class="fas fa-search"></i>

                                    Buscar

                                </button>

                            </div>

                        </div>


                        <div id="mensajeFacturaNuevo"
                             class="mensaje-factura">
                        </div>

                    </div>


                    <!-- DATOS DE LA FACTURA -->

                    <div id="resultadoFacturaNuevo"
                         class="resultado-factura">


                        <div class="datos-factura">


                            <div class="datos-factura-titulo">

                                <i class="fas fa-file-invoice"></i>

                                Datos de la factura

                            </div>


                            <div class="form-row">


                                <!-- NÚMERO -->

                                <div class="form-group col-md-4">

                                    <div class="dato-label">

                                        N.º Factura

                                    </div>

                                    <div id="nuevoDatoNumeroFactura"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                                <!-- CLIENTE -->

                                <div class="form-group col-md-5">

                                    <div class="dato-label">

                                        Cliente

                                    </div>

                                    <div id="nuevoDatoCliente"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                                <!-- ESTADO -->

                                <div class="form-group col-md-3">

                                    <div class="dato-label">

                                        Estado

                                    </div>

                                    <div id="nuevoDatoEstado"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                            </div>


                            <div class="form-row">


                                <!-- PERÍODO -->

                                <div class="form-group col-md-4">

                                    <div class="dato-label">

                                        Período de factura

                                    </div>

                                    <div id="nuevoDatoPeriodo"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                                <!-- TARIFA -->

                                <div class="form-group col-md-5">

                                    <div class="dato-label">

                                        Tarifa(s)

                                    </div>

                                    <div id="nuevoDatoTarifa"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                                <!-- TOTAL -->

                                <div class="form-group col-md-3">

                                    <div class="dato-label">

                                        Total factura

                                    </div>

                                    <div id="nuevoDatoTotal"
                                         class="dato-valor">

                                        Gs. 0

                                    </div>

                                </div>


                            </div>


                        </div>

                    </div>


                    <!-- FECHA / PERÍODO / MONTO -->

                    <div class="form-row">


                        <div class="form-group col-md-4">

                            <label>

                                Fecha de Pago *

                            </label>

                            <input type="date"
                                   name="fechaPago"
                                   id="nuevoFechaPago"
                                   class="form-control"
                                   required>

                        </div>


                        <div class="form-group col-md-4">

                            <label>

                                Período

                            </label>

                            <input type="month"
                                   id="nuevoPeriodoMes"
                                   class="form-control">

                            <input type="hidden"
                                   name="periodo"
                                   id="nuevoPeriodo">

                        </div>


                        <div class="form-group col-md-4">

                            <label>

                                Monto *

                            </label>

                            <input type="number"
                                   name="monto"
                                   id="nuevoMonto"
                                   class="form-control"
                                   min="0.01"
                                   step="0.01"
                                   required>

                        </div>

                    </div>


                    <!-- MÉTODO / RECIBO -->

                    <div class="form-row">


                        <div class="form-group col-md-6">

                            <label>

                                Método de Pago *

                            </label>

                            <select name="metodoPago"
                                    id="nuevoMetodoPago"
                                    class="form-control"
                                    required>

                                <option value="EFECTIVO">

                                    Efectivo

                                </option>

                                <option value="TRANSFERENCIA">

                                    Transferencia

                                </option>

                                <option value="TARJETA">

                                    Tarjeta

                                </option>

                                <option value="OTRO">

                                    Otro

                                </option>

                            </select>

                        </div>


                        <div class="form-group col-md-6">

                            <label>

                                Número de Recibo

                            </label>

                            <input type="text"
                                   name="numeroRecibo"
                                   id="nuevoNumeroRecibo"
                                   class="form-control"
                                   maxlength="50"
                                   placeholder="Se genera automáticamente"
                                   readonly>

                        </div>

                    </div>


                    <!-- OBSERVACIONES -->

                    <div class="form-group">

                        <label>

                            Observaciones

                        </label>

                        <textarea name="observaciones"
                                  class="form-control"
                                  maxlength="255"
                                  rows="2"></textarea>

                    </div>


                </div>


                <!-- PIE -->

                <div class="modal-footer">

                    <button type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal">

                        Cancelar

                    </button>


                    <button type="submit"
                            class="btn btn-primary">

                        <i class="fas fa-save"></i>

                        Registrar Pago

                    </button>

                </div>


            </form>

        </div>

    </div>

</div>


<!-- MODAL EDITAR PAGO -->

<div class="modal fade"
     id="modalEditarPago"
     tabindex="-1">

    <div class="modal-dialog modal-lg">

        <div class="modal-content">


            <form method="post"
                  action="<%= request.getContextPath() %>/PagoController"
                  onsubmit="return validarPagoEditar();">


                <input type="hidden"
                       name="accion"
                       value="actualizar">


                <input type="hidden"
                       name="idPago"
                       id="editIdPago">


                <input type="hidden"
                       name="idFactura"
                       id="editIdFactura">


                <!-- ENCABEZADO -->

                <div class="modal-header bg-warning">

                    <h5 class="modal-title">

                        <i class="fas fa-edit"></i>

                        Modificar Pago

                    </h5>


                    <button type="button"
                            class="close"
                            data-dismiss="modal">

                        &times;

                    </button>

                </div>


                <div class="modal-body">


                    <!-- FACTURA -->

                    <div class="grupo-factura">

                        <label>

                            Factura *

                        </label>


                        <div class="input-group">

                            <input type="text"
                                   id="editNumeroFactura"
                                   class="form-control"
                                   placeholder="Ingrese el N.º de factura"
                                   maxlength="50"
                                   autocomplete="off">


                            <div class="input-group-append">

                                <button type="button"
                                        class="btn btn-primary btn-buscar-factura"
                                        onclick="buscarFacturaEditar()">

                                    <i class="fas fa-search"></i>

                                    Buscar

                                </button>

                            </div>

                        </div>


                        <div id="mensajeFacturaEditar"
                             class="mensaje-factura">
                        </div>

                    </div>


                    <!-- DATOS DE FACTURA -->

                    <div id="resultadoFacturaEditar"
                         class="resultado-factura">


                        <div class="datos-factura">


                            <div class="datos-factura-titulo">

                                <i class="fas fa-file-invoice"></i>

                                Datos de la factura

                            </div>


                            <div class="form-row">


                                <div class="form-group col-md-4">

                                    <div class="dato-label">

                                        N.º Factura

                                    </div>

                                    <div id="editDatoNumeroFactura"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                                <div class="form-group col-md-5">

                                    <div class="dato-label">

                                        Cliente

                                    </div>

                                    <div id="editDatoCliente"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                                <div class="form-group col-md-3">

                                    <div class="dato-label">

                                        Estado

                                    </div>

                                    <div id="editDatoEstado"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                            </div>


                            <div class="form-row">


                                <div class="form-group col-md-4">

                                    <div class="dato-label">

                                        Período de factura

                                    </div>

                                    <div id="editDatoPeriodo"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                                <div class="form-group col-md-5">

                                    <div class="dato-label">

                                        Tarifa(s)

                                    </div>

                                    <div id="editDatoTarifa"
                                         class="dato-valor">

                                        -

                                    </div>

                                </div>


                                <div class="form-group col-md-3">

                                    <div class="dato-label">

                                        Total factura

                                    </div>

                                    <div id="editDatoTotal"
                                         class="dato-valor">

                                        Gs. 0

                                    </div>

                                </div>


                            </div>


                        </div>

                    </div>


                    <!-- FECHA / PERÍODO / MONTO -->

                    <div class="form-row">


                        <div class="form-group col-md-4">

                            <label>

                                Fecha de Pago *

                            </label>

                            <input type="date"
                                   name="fechaPago"
                                   id="editFechaPago"
                                   class="form-control"
                                   required>

                        </div>


                        <div class="form-group col-md-4">

                            <label>

                                Período

                            </label>

                            <input type="month"
                                   id="editPeriodoMes"
                                   class="form-control">

                            <input type="hidden"
                                   name="periodo"
                                   id="editPeriodo">

                        </div>


                        <div class="form-group col-md-4">

                            <label>

                                Monto *

                            </label>

                            <input type="number"
                                   name="monto"
                                   id="editMonto"
                                   class="form-control"
                                   min="0.01"
                                   step="0.01"
                                   required>

                        </div>

                    </div>


                    <!-- MÉTODO / RECIBO -->

                    <div class="form-row">


                        <div class="form-group col-md-6">

                            <label>

                                Método de Pago *

                            </label>

                            <select name="metodoPago"
                                    id="editMetodoPago"
                                    class="form-control"
                                    required>

                                <option value="EFECTIVO">

                                    Efectivo

                                </option>

                                <option value="TRANSFERENCIA">

                                    Transferencia

                                </option>

                                <option value="TARJETA">

                                    Tarjeta

                                </option>

                                <option value="OTRO">

                                    Otro

                                </option>

                            </select>

                        </div>


                        <div class="form-group col-md-6">

                            <label>

                                Número de Recibo

                            </label>

                            <input type="text"
                                   name="numeroRecibo"
                                   id="editNumeroRecibo"
                                   class="form-control"
                                   maxlength="50"
                                   readonly>

                        </div>

                    </div>


                    <!-- OBSERVACIONES -->

                    <div class="form-group">

                        <label>

                            Observaciones

                        </label>

                        <textarea name="observaciones"
                                  id="editObservaciones"
                                  class="form-control"
                                  maxlength="255"
                                  rows="2"></textarea>

                    </div>


                </div>


                <!-- PIE -->

                <div class="modal-footer">

                    <button type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal">

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

    // CONTEXTO

    // var (no const): la página puede cargarse varias veces dentro del dashboard
    var contexto =
            "<%= request.getContextPath() %>";


    // TABLA DE PAGOS (misma configuración que el resto del sistema)

    $("#filaSinPagos").remove();

    $("#tablaListadoPagos").DataTable({
        responsive: true,
        autoWidth: false,
        order: [[0, "desc"]]
    });


    // PREPARAR NUEVO PAGO

    function prepararNuevoPago() {

        document.getElementById(
                "nuevoIdFactura"
        ).value = "";


        document.getElementById(
                "nuevoNumeroFactura"
        ).value = "";


        ponerPeriodo(
                "nuevo",
                ""
        );


        document.getElementById(
                "nuevoMonto"
        ).value = "";


        document.getElementById(
                "resultadoFacturaNuevo"
        ).style.display = "none";


        ocultarMensajeFactura(
                "mensajeFacturaNuevo"
        );


        const fecha =
                new Date();


        const anio =
                fecha.getFullYear();


        const mes =
                String(
                        fecha.getMonth() + 1
                ).padStart(2, "0");


        const dia =
                String(
                        fecha.getDate()
                ).padStart(2, "0");


        document.getElementById(
                "nuevoFechaPago"
        ).value =
                anio
                + "-"
                + mes
                + "-"
                + dia;

    }


    // BUSCAR FACTURA - NUEVO PAGO

    function buscarFacturaNuevo() {

        const numero =
                document.getElementById(
                        "nuevoNumeroFactura"
                ).value.trim();


        if (numero === "") {

            mostrarMensajeFactura(
                    "mensajeFacturaNuevo",
                    "Debe ingresar el número de factura.",
                    "danger"
            );

            return;
        }


        const boton =
                event ? event.currentTarget : null;


        if (boton) {

            boton.disabled = true;

        }


        $.ajax({

            url:
                    contexto
                    + "/PagoController",

            type: "POST",

            data: {

                accion: "buscarFactura",

                numeroFactura: numero

            },

            dataType: "json",

            success: function (data) {

                if (!data.ok) {

                    document.getElementById(
                            "nuevoIdFactura"
                    ).value = "";


                    document.getElementById(
                            "resultadoFacturaNuevo"
                    ).style.display = "none";


                    mostrarMensajeFactura(
                            "mensajeFacturaNuevo",
                            data.mensaje
                                    || "No se encontró la factura.",
                            "danger"
                    );

                    return;
                }


                cargarDatosFacturaNuevo(
                        data
                );

            },

            error: function () {

                mostrarMensajeFactura(
                        "mensajeFacturaNuevo",
                        "Ocurrió un error al consultar la factura.",
                        "danger"
                );

            },

            complete: function () {

                if (boton) {

                    boton.disabled = false;

                }

            }

        });

    }


    // CARGAR DATOS FACTURA NUEVO

    function cargarDatosFacturaNuevo(data) {

        document.getElementById(
                "nuevoIdFactura"
        ).value =
                data.idFactura;


        document.getElementById(
                "nuevoNumeroFactura"
        ).value =
                data.numeroFactura || "";


        document.getElementById(
                "nuevoDatoNumeroFactura"
        ).textContent =
                data.numeroFactura || "-";


        document.getElementById(
                "nuevoDatoCliente"
        ).textContent =
                construirNombreCliente(
                        data
                );


        document.getElementById(
                "nuevoDatoPeriodo"
        ).textContent =
                data.periodoFactura || "-";


        document.getElementById(
                "nuevoDatoTarifa"
        ).textContent =
                data.nombreTarifa || "-";


        document.getElementById(
                "nuevoDatoEstado"
        ).textContent =
                data.estadoFactura || "-";


        document.getElementById(
                "nuevoDatoTotal"
        ).textContent =
                "Gs. "
                + formatoNumero(
                        data.totalFactura
                );


        /*
         * El período del pago se completa
         * inicialmente con el período de la factura.
         */

        ponerPeriodo(
                "nuevo",
                data.periodoFactura
        );


        /*
         * El monto se completa inicialmente
         * con el total de la factura.
         */

        document.getElementById(
                "nuevoMonto"
        ).value =
                data.totalFactura || "";


        document.getElementById(
                "resultadoFacturaNuevo"
        ).style.display = "block";


        ocultarMensajeFactura(
                "mensajeFacturaNuevo"
        );


        /*
         * Si la factura está anulada,
         * mostramos advertencia.
         */

        if (
                String(
                        data.estadoFactura || ""
                ).toUpperCase()
                === "ANULADA"
        ) {

            mostrarMensajeFactura(
                    "mensajeFacturaNuevo",
                    "La factura está ANULADA y no puede recibir pagos.",
                    "danger"
            );

        } else {

            mostrarMensajeFactura(
                    "mensajeFacturaNuevo",
                    "Factura encontrada correctamente.",
                    "success"
            );

        }

    }


    // BUSCAR FACTURA - EDITAR

    function buscarFacturaEditar() {

        const numero =
                document.getElementById(
                        "editNumeroFactura"
                ).value.trim();


        if (numero === "") {

            mostrarMensajeFactura(
                    "mensajeFacturaEditar",
                    "Debe ingresar el número de factura.",
                    "danger"
            );

            return;
        }


        $.ajax({

            url:
                    contexto
                    + "/PagoController",

            type: "POST",

            data: {

                accion: "buscarFactura",

                numeroFactura: numero

            },

            dataType: "json",

            success: function (data) {

                if (!data.ok) {

                    document.getElementById(
                            "editIdFactura"
                    ).value = "";


                    document.getElementById(
                            "resultadoFacturaEditar"
                    ).style.display = "none";


                    mostrarMensajeFactura(
                            "mensajeFacturaEditar",
                            data.mensaje
                                    || "No se encontró la factura.",
                            "danger"
                    );

                    return;
                }


                cargarDatosFacturaEditar(
                        data
                );

            },

            error: function () {

                mostrarMensajeFactura(
                        "mensajeFacturaEditar",
                        "Ocurrió un error al consultar la factura.",
                        "danger"
                );

            }

        });

    }


    // CARGAR DATOS FACTURA EDITAR

    function cargarDatosFacturaEditar(data) {

        document.getElementById(
                "editIdFactura"
        ).value =
                data.idFactura;


        document.getElementById(
                "editNumeroFactura"
        ).value =
                data.numeroFactura || "";


        document.getElementById(
                "editDatoNumeroFactura"
        ).textContent =
                data.numeroFactura || "-";


        document.getElementById(
                "editDatoCliente"
        ).textContent =
                construirNombreCliente(
                        data
                );


        document.getElementById(
                "editDatoPeriodo"
        ).textContent =
                data.periodoFactura || "-";


        document.getElementById(
                "editDatoTarifa"
        ).textContent =
                data.nombreTarifa || "-";


        document.getElementById(
                "editDatoEstado"
        ).textContent =
                data.estadoFactura || "-";


        document.getElementById(
                "editDatoTotal"
        ).textContent =
                "Gs. "
                + formatoNumero(
                        data.totalFactura
                );


        ponerPeriodo(
                "edit",
                data.periodoFactura
        );


        document.getElementById(
                "resultadoFacturaEditar"
        ).style.display = "block";


        ocultarMensajeFactura(
                "mensajeFacturaEditar"
        );


        if (
                String(
                        data.estadoFactura || ""
                ).toUpperCase()
                === "ANULADA"
        ) {

            mostrarMensajeFactura(
                    "mensajeFacturaEditar",
                    "La factura está ANULADA y no puede recibir pagos.",
                    "danger"
            );

        } else {

            mostrarMensajeFactura(
                    "mensajeFacturaEditar",
                    "Factura encontrada correctamente.",
                    "success"
            );

        }

    }


    // EDITAR PAGO

    function editarPago(
            idPago,
            idFactura,
            numeroFactura,
            nombreCliente,
            periodoFactura,
            totalFactura,
            estadoFactura,
            fechaPago,
            periodo,
            monto,
            metodoPago,
            numeroRecibo,
            observaciones
            ) {


        document.getElementById(
                "editIdPago"
        ).value =
                idPago;


        document.getElementById(
                "editIdFactura"
        ).value =
                idFactura;


        document.getElementById(
                "editNumeroFactura"
        ).value =
                numeroFactura || "";


        document.getElementById(
                "editDatoNumeroFactura"
        ).textContent =
                numeroFactura || "-";


        document.getElementById(
                "editDatoCliente"
        ).textContent =
                nombreCliente || "-";


        document.getElementById(
                "editDatoPeriodo"
        ).textContent =
                periodoFactura || "-";


        document.getElementById(
                "editDatoTarifa"
        ).textContent =
                "-";


        document.getElementById(
                "editDatoEstado"
        ).textContent =
                estadoFactura || "-";


        document.getElementById(
                "editDatoTotal"
        ).textContent =
                "Gs. "
                + formatoNumero(
                        totalFactura
                );


        document.getElementById(
                "editFechaPago"
        ).value =
                fechaPago || "";


        ponerPeriodo(
                "edit",
                periodo
        );


        document.getElementById(
                "editMonto"
        ).value =
                monto || "";


        document.getElementById(
                "editMetodoPago"
        ).value =
                metodoPago || "EFECTIVO";


        document.getElementById(
                "editNumeroRecibo"
        ).value =
                numeroRecibo || "";


        document.getElementById(
                "editObservaciones"
        ).value =
                observaciones || "";


        document.getElementById(
                "resultadoFacturaEditar"
        ).style.display = "block";


        ocultarMensajeFactura(
                "mensajeFacturaEditar"
        );


        if (
                String(
                        estadoFactura || ""
                ).toUpperCase()
                === "ANULADA"
        ) {

            mostrarMensajeFactura(
                    "mensajeFacturaEditar",
                    "La factura está ANULADA y no puede recibir pagos.",
                    "danger"
            );

        } else {

            mostrarMensajeFactura(
                    "mensajeFacturaEditar",
                    "Factura asociada al pago.",
                    "success"
            );

        }


        $("#modalEditarPago").modal("show");

    }


    // VALIDAR NUEVO PAGO

    function validarPagoNuevo() {

        const idFactura =
                document.getElementById(
                        "nuevoIdFactura"
                ).value;


        if (!idFactura) {

            alert(
                    "Debe buscar y seleccionar una factura antes de registrar el pago."
            );

            return false;
        }


        const monto =
                parseFloat(
                        document.getElementById(
                                "nuevoMonto"
                        ).value
                );


        if (
                isNaN(monto)
                || monto <= 0
        ) {

            alert(
                    "El monto del pago debe ser mayor a cero."
            );

            return false;
        }


        tomarPeriodo(
                "nuevo"
        );


        return true;

    }


    // VALIDAR EDICIÓN

    function validarPagoEditar() {

        const idFactura =
                document.getElementById(
                        "editIdFactura"
                ).value;


        if (!idFactura) {

            alert(
                    "Debe seleccionar una factura válida."
            );

            return false;
        }


        const monto =
                parseFloat(
                        document.getElementById(
                                "editMonto"
                        ).value
                );


        if (
                isNaN(monto)
                || monto <= 0
        ) {

            alert(
                    "El monto del pago debe ser mayor a cero."
            );

            return false;
        }


        tomarPeriodo(
                "edit"
        );


        return true;

    }


    function ponerPeriodo(prefijo, texto) {

        const valor =
                String(texto || "").trim();

        const partes =
                valor.match(/^(\d{1,2})\/(\d{4})$/);

        document.getElementById(
                prefijo + "PeriodoMes"
        ).value =
                partes
                        ? partes[2] + "-" + partes[1].padStart(2, "0")
                        : "";

        document.getElementById(
                prefijo + "Periodo"
        ).value =
                partes
                        ? partes[1].padStart(2, "0") + "/" + partes[2]
                        : valor;

    }


    function tomarPeriodo(prefijo) {

        const mes =
                document.getElementById(
                        prefijo + "PeriodoMes"
                ).value;

        if (mes) {

            document.getElementById(
                    prefijo + "Periodo"
            ).value =
                    mes.substring(5, 7) + "/" + mes.substring(0, 4);

        }

    }


    // CONSTRUIR NOMBRE CLIENTE

    function construirNombreCliente(data) {

        const nombre =
                data.nombreCliente || "";


        const apellido =
                data.apellidoCliente || "";


        const completo =
                (
                    nombre
                    + " "
                    + apellido
                ).trim();


        if (completo !== "") {

            return completo;

        }


        if (data.cedulaCliente) {

            return data.cedulaCliente;

        }


        return "-";

    }


    // FORMATEAR NÚMERO

    function formatoNumero(valor) {

        if (
                valor === null
                || valor === undefined
                || valor === ""
        ) {

            return "0";

        }


        const numero =
                Number(valor);


        if (isNaN(numero)) {

            return "0";

        }


        return numero.toLocaleString(
                "es-PY",
                {
                    minimumFractionDigits: 0,
                    maximumFractionDigits: 0
                }
        );

    }


    // MOSTRAR MENSAJE FACTURA

    function mostrarMensajeFactura(
            id,
            mensaje,
            tipo
            ) {

        const elemento =
                document.getElementById(id);


        elemento.textContent =
                mensaje;


        elemento.className =
                "mensaje-factura text-"
                + tipo;


        elemento.style.display =
                "block";

    }


    // OCULTAR MENSAJE FACTURA

    function ocultarMensajeFactura(
            id
            ) {

        const elemento =
                document.getElementById(id);


        elemento.textContent =
                "";


        elemento.style.display =
                "none";

    }


    // ENTER EN BÚSQUEDA DE FACTURA NUEVA

    document.getElementById(
            "nuevoNumeroFactura"
    ).addEventListener(
            "keypress",
            function (e) {

                if (e.key === "Enter") {

                    e.preventDefault();

                    buscarFacturaNuevo();

                }

            }
    );


    // ENTER EN BÚSQUEDA DE FACTURA EDICIÓN

    document.getElementById(
            "editNumeroFactura"
    ).addEventListener(
            "keypress",
            function (e) {

                if (e.key === "Enter") {

                    e.preventDefault();

                    buscarFacturaEditar();

                }

            }
    );

</script>


</body>

</html>


<%!

    // SEGURIDAD PARA JAVASCRIPT

    private String safeJS(String texto) {

        if (texto == null) {

            return "";

        }

        return texto

                .replace("\\", "\\\\")

                .replace("'", "\\'")

                .replace("\"", "\\\"")

                .replace("\r", "")

                .replace("\n", " ");

    }

%>