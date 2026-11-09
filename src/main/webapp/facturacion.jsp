<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<style>

#seccionListadoFactura,
#seccionFormularioFactura {
    font-size: 14px;
}





.btn-nueva-factura {
    height: 36px;
    font-size: 14px;
    padding: 6px 14px;
}

#seccionFormularioFactura .card-header {
    background: #007bff;
    color: white;
    padding: 10px 15px;
}

#seccionFormularioFactura .card-title {
    font-size: 16px;
    margin: 0;
}

#seccionFormularioFactura label {
    font-size: 13px;
    font-weight: 600;
}

#seccionFormularioFactura .form-control,
#seccionFormularioFactura select {
    font-size: 14px;
}

.campo-automatico {
    background: #e9ecef !important;
    color: #495057 !important;
    cursor: not-allowed !important;
}

.ayuda-automatico {
    display: block;
    font-size: 11px;
    color: #6c757d;
    margin-top: 3px;
}

.campo-vencimiento {
    background: #e9ecef !important;
    color: #495057 !important;
    cursor: not-allowed !important;
}

.bloque-credito.deshabilitado {
    opacity: 0.65;
}

#resultadosClientesFactura {
    font-size: 13px;
    border: 1px solid #dee2e6;
    border-radius: 4px;
    background: #ffffff;
    box-shadow: 0 3px 8px rgba(0,0,0,0.12);
    position: absolute;
    left: 0;
    right: 0;
    z-index: 2000;
}

#resultadosClientesFactura .cliente-factura-item {
    padding: 9px 10px;
    cursor: pointer;
    border-bottom: 1px solid #eeeeee;
}

#resultadosClientesFactura .cliente-factura-item:hover {
    background: #f1f5f9;
}

#resultadosServiciosFactura {
    font-size: 13px;
    border: 1px solid #dee2e6;
    border-radius: 4px;
    background: #ffffff;
    box-shadow: 0 3px 8px rgba(0,0,0,0.12);
    position: relative;
    z-index: 1500;
}

#resultadosServiciosFactura .servicio-factura-item {
    padding: 9px 10px;
    cursor: pointer;
    border-bottom: 1px solid #eeeeee;
}

#resultadosServiciosFactura .servicio-factura-item:hover {
    background: #f1f5f9;
}

.servicio-seleccionado-factura {
    min-height: 20px;
}

.contenedor-buscador-cliente {
    position: relative;
}

#clienteSeleccionadoFactura {
    min-height: 20px;
}

/* TABLA DE FACTURAS — MISMO MODELO VISUAL DE TARIFAS */

#tablaFacturas {
    width: 100%;
    table-layout: fixed;
    margin-bottom: 0;
    font-size: 14px;
}

#tablaFacturas th,
#tablaFacturas td {
    vertical-align: middle;
    padding: 0.45rem 0.45rem;
    line-height: 1.25;
}

#tablaFacturas thead th {
    font-weight: 600;
    white-space: nowrap;
    text-align: center;
}

.col-factura-id { width: 5%; }
.col-factura-numero { width: 13%; }
.col-factura-cliente { width: 19%; }
.col-factura-fecha { width: 10%; }
.col-factura-periodo { width: 9%; }
.col-factura-condicion { width: 10%; }
.col-factura-vencimiento { width: 11%; }
.col-factura-total { width: 10%; }
.col-factura-estado { width: 7%; }
.col-factura-acciones { width: 12%; }

#tablaFacturas .cell-center {
    text-align: center;
    white-space: nowrap;
}

#tablaFacturas .cell-right {
    text-align: right;
    white-space: nowrap;
}

#tablaFacturas .cell-wrap {
    white-space: normal;
    word-break: normal;
    overflow-wrap: break-word;
}

#tablaFacturas .acciones-factura {
    text-align: center;
    white-space: nowrap;
}

#tablaFacturas .btn-action {
    width: 30px;
    height: 30px;
    padding: 0;
    margin: 1px;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    vertical-align: middle;
}

#tablaFacturas .btn-action i {
    font-size: 13px;
}

#tablaFacturas .badge-estado {
    font-size: 11px;
    padding: 0.35em 0.55em;
    white-space: nowrap;
}

#tablaDetalle {
    font-size: 12px;
}

#tablaDetalle thead th {
    font-size: 12px;
    vertical-align: middle;
}

#tablaFacturas .btn,
#tablaDetalle .btn {
    width: 30px;
    height: 30px;
    padding: 0;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    font-size: 12px;
}

#tablaFacturas .badge {
    font-size: 9px;
    padding: 4px 7px;
}

#totalGeneral {
    font-size: 20px;
    color: #17365D;
}

#verTotal {
    font-size: 22px;
    font-weight: 700;
    color: #17365D;
}

#modalFactura {
    font-size: 14px;
}

#modalFactura .modal-title {
    font-size: 16px;
}

#modalFactura .table th,
#modalFactura .table td {
    font-size: 12px;
}

.dataTables_wrapper {
    font-size: 13px;
}

.dataTables_wrapper .dataTables_length,
.dataTables_wrapper .dataTables_filter {
    font-size: 13px;
    margin-bottom: 10px;
}

.dataTables_wrapper .dataTables_length label,
.dataTables_wrapper .dataTables_filter label {
    font-size: 13px;
    margin-bottom: 0;
}

.dataTables_wrapper .dataTables_length select,
.dataTables_wrapper .dataTables_filter input {
    font-size: 13px;
    height: 32px;
    padding: 4px 8px;
}

.dataTables_wrapper .dataTables_info,
.dataTables_wrapper .dataTables_paginate {
    font-size: 13px;
}

.dataTables_wrapper .dataTables_paginate .paginate_button {
    font-size: 13px !important;
}

/* BARRA DE ACCIONES Y DATATABLES */



.dataTables_wrapper .dataTables_filter {
    float: right;
    text-align: right;
    margin-bottom: 10px;
}

.dataTables_wrapper .dataTables_length {
    float: left;
    margin-bottom: 10px;
}

.dataTables_wrapper .dataTables_filter input {
    margin-left: 6px;
    width: 260px;
    height: 36px;
    border: 1px solid #ced4da;
    border-radius: 4px;
    padding: 6px 10px;
}

.dataTables_wrapper .dataTables_length select {
    height: 36px;
    border: 1px solid #ced4da;
    border-radius: 4px;
    padding: 4px 28px 4px 8px;
}

.dataTables_wrapper .dataTables_info {
    padding-top: 10px;
}

#tablaFacturas .btn-action {
    width: 30px;
    height: 30px;
    padding: 0;
    margin: 1px;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    vertical-align: middle;
}

#tablaFacturas .btn-action i {
    font-size: 12px;
}

.facturacion-separador {
    border-top: 1px solid #dee2e6;
    margin: 18px 0;
}


@media (max-width: 767.98px) {

    .dataTables_wrapper .dataTables_length,
    .dataTables_wrapper .dataTables_filter {
        float: none;
        text-align: left;
        width: 100%;
    }

    .dataTables_wrapper .dataTables_filter input {
        width: calc(100% - 72px);
        max-width: 100%;
    }

}

</style>


<section class="content"
         id="seccionListadoFactura">

    <div class="container-fluid">

        <div class="card">

            <div class="card-header">

                <div class="d-flex justify-content-between align-items-center">

                    <h3 class="card-title">
                        <i class="fas fa-file-invoice-dollar"></i>
                        Gestión de Facturación
                    </h3>

                    <div>

                        <!-- NUEVA FACTURA -->
                        <button type="button"
                                class="btn btn-success btn-sm"
                                onclick="nuevaFactura()">
                            <i class="fas fa-plus"></i>
                            Nueva Factura
                        </button>

                    </div>

                </div>

            </div>

            <div class="card-body">

                <div class="table-responsive">

                    <table id="tablaFacturas"
                           class="table table-bordered table-hover"
                           style="width:100%;">

                        <colgroup>
                            <col class="col-factura-id">
                            <col class="col-factura-numero">
                            <col class="col-factura-cliente">
                            <col class="col-factura-fecha">
                            <col class="col-factura-periodo">
                            <col class="col-factura-condicion">
                            <col class="col-factura-vencimiento">
                            <col class="col-factura-total">
                            <col class="col-factura-estado">
                            <col class="col-factura-acciones">
                        </colgroup>

                        <thead>

                            <tr>

                                <th>ID</th>

                                <th>N° Factura</th>

                                <th>Cliente</th>

                                <th>Fecha</th>

                                <th>Período</th>

                                <th>Condición</th>

                                <th>Vencimiento</th>

                                <th>Total</th>

                                <th>Estado</th>

                                <th>Acciones</th>

                            </tr>

                        </thead>


                        <tbody id="tbodyFacturas">
                        </tbody>

                    </table>

                </div>

            </div>

        </div>

    </div>

</section>


<section class="content"
         id="seccionFormularioFactura"
         style="display:none;">

    <div class="container-fluid">

        <form id="formFactura">

            <div class="card">

                <div class="card-header">

                    <h3 class="card-title">

                        <i class="fas fa-file-invoice"></i>

                        Nueva Factura

                    </h3>

                </div>


                <div class="card-body">

                    <div class="row">

                        <div class="col-md-4">

                            <label>

                                Cliente
                                <span class="text-danger">*</span>

                            </label>


                            <input type="hidden"
                                   id="id_cliente"
                                   name="id_cliente">


                            <div class="contenedor-buscador-cliente">

                                <div class="input-group">

                                    <input type="text"
                                           id="buscarClienteFactura"
                                           class="form-control"
                                           placeholder="Buscar por nombre, cédula o RUC..."
                                           autocomplete="off">

                                    <div class="input-group-append">

                                        <button type="button"
                                                class="btn btn-primary"
                                                id="btnBuscarClienteFactura"
                                                title="Buscar cliente">

                                            <i class="fas fa-search"></i>

                                        </button>

                                    </div>

                                </div>


                                <div id="resultadosClientesFactura"
                                     style="display:none;">

                                </div>

                            </div>


                            <div id="clienteSeleccionadoFactura"
                                 class="small text-muted mt-1">

                                Ningún cliente seleccionado

                            </div>

                        </div>


                        <div class="col-md-4">

                            <label>
                                Timbrado
                            </label>

                            <input type="text"
                                   id="timbrado_visual"
                                   class="form-control campo-automatico"
                                   value="80641827"
                                   readonly>

                            <input type="hidden"
                                   id="timbrado"
                                   name="timbrado"
                                   value="80641827">

                            <small class="ayuda-automatico">

                                Dato ficticio para uso académico

                            </small>

                        </div>


                        <div class="col-md-4">

                            <label>
                                N° de Factura
                            </label>

                            <input type="text"
                                   id="numero_factura_visual"
                                   class="form-control campo-automatico"
                                   readonly>

                            <input type="hidden"
                                   id="numero_factura"
                                   name="numero_factura">

                            <small class="ayuda-automatico">

                                Generado automáticamente

                            </small>

                        </div>

                    </div>


                    <div class="row mt-3">

                        <div class="col-md-3">

                            <label>
                                Fecha inicio timbrado
                            </label>

                            <input type="text"
                                   id="fecha_inicio_visual"
                                   class="form-control campo-automatico"
                                   readonly>

                            <input type="hidden"
                                   id="fecha_inicio_timbrado"
                                   name="fecha_inicio_timbrado">

                        </div>


                        <div class="col-md-3">

                            <label>
                                Fecha fin timbrado
                            </label>

                            <input type="text"
                                   id="fecha_fin_visual"
                                   class="form-control campo-automatico"
                                   readonly>

                            <input type="hidden"
                                   id="fecha_fin_timbrado"
                                   name="fecha_fin_timbrado">

                        </div>


                        <div class="col-md-4">

                            <label>
                                Establecimiento / Servicio
                                <span class="text-danger">*</span>
                            </label>

                            <input type="hidden"
                                   id="id_servicio"
                                   name="id_servicio">

                            <select id="selectServicioFactura"
                                    class="form-control"
                                    disabled>

                                <option value="">
                                    Seleccione primero un cliente
                                </option>

                            </select>

                            <div id="servicioSeleccionadoFactura"
                                 class="small text-muted mt-1 servicio-seleccionado-factura">

                                Ningún establecimiento seleccionado

                            </div>

                        </div>


                        <div class="col-md-2">

                            <label>
                                Código Establecimiento
                            </label>

                            <input type="text"
                                   id="establecimiento_visual"
                                   class="form-control campo-automatico"
                                   value="001"
                                   readonly>

                            <input type="hidden"
                                   id="establecimiento"
                                   name="establecimiento"
                                   value="001">

                        </div>


                        <div class="col-md-2">

                            <label>
                                Punto Expedición
                            </label>

                            <input type="text"
                                   id="punto_expedicion_visual"
                                   class="form-control campo-automatico"
                                   value="001"
                                   readonly>

                            <input type="hidden"
                                   id="punto_expedicion"
                                   name="punto_expedicion"
                                   value="001">

                        </div>


                        <div class="col-md-2">

                            <label>
                                Condición
                            </label>

                            <select id="condicion_venta"
                                    name="condicion_venta"
                                    class="form-control">

                                <option value="CREDITO" selected>
                                    CRÉDITO
                                </option>

                                <option value="CONTADO">
                                    CONTADO
                                </option>

                            </select>

                        </div>

                    </div>


                    <div class="row mt-3 bloque-credito"
                         id="bloqueDatosCredito">

                        <div class="col-md-3">

                            <label>
                                Plazo de crédito
                            </label>

                            <div class="input-group">

                                <input type="number"
                                       id="plazo_credito"
                                       name="plazo_credito"
                                       class="form-control"
                                       value="30"
                                       min="0"
                                       step="1">

                                <div class="input-group-append">

                                    <span class="input-group-text">
                                        días
                                    </span>

                                </div>

                            </div>

                            <small class="ayuda-automatico">
                                Se utiliza para calcular automáticamente el vencimiento.
                            </small>

                        </div>


                        <div class="col-md-3">

                            <label>
                                Fecha de vencimiento
                            </label>

                            <input type="text"
                                   id="fecha_vencimiento_visual"
                                   class="form-control campo-vencimiento"
                                   readonly>

                            <input type="hidden"
                                   id="fecha_vencimiento"
                                   name="fecha_vencimiento">

                            <small class="ayuda-automatico">
                                Calculada según la condición y el plazo.
                            </small>

                        </div>

                    </div>


                    <div class="row mt-3">

                        <div class="col-md-4">

                            <label>
                                Período
                            </label>

                            <input type="text"
                                   id="periodo"
                                   name="periodo"
                                   class="form-control"
                                   placeholder="Ej.: Septiembre 2026">

                        </div>


                        <div class="col-md-8">

                            <label>
                                Observaciones
                            </label>

                            <input type="text"
                                   id="observaciones"
                                   name="observaciones"
                                   class="form-control"
                                   placeholder="Observaciones de la factura">

                        </div>

                    </div>


                    <hr class="facturacion-separador">


                    <h5 style="color:#17365D;font-weight:bold;">

                        <i class="fas fa-shopping-cart"></i>

                        Detalle de la Factura

                    </h5>


                    <div class="row align-items-end mt-3">

                        <div class="col-md-4">

                            <label>
                                Tarifa / Servicio
                            </label>

                            <select id="selectTarifa"
                                    class="form-control">

                                <option value="">
                                    Seleccione una tarifa
                                </option>

                            </select>

                        </div>


                        <div class="col-md-3">

                            <label>
                                Descripción
                            </label>

                            <input type="text"
                                   id="descripcionDetalle"
                                   class="form-control">

                        </div>


                        <div class="col-md-1">

                            <label>
                                Cant.
                            </label>

                            <input type="number"
                                   id="cantidadDetalle"
                                   class="form-control"
                                   value="1"
                                   min="0.01"
                                   step="0.01">

                        </div>


                        <div class="col-md-2">

                            <label>
                                Precio
                            </label>

                            <input type="number"
                                   id="precioDetalle"
                                   class="form-control"
                                   value="0"
                                   min="0"
                                   step="0.01">

                        </div>


                        <div class="col-md-1">

                            <label>
                                IVA
                            </label>

                            <select id="ivaDetalle"
                                    class="form-control">

                                <option value="10">
                                    10%
                                </option>

                                <option value="5">
                                    5%
                                </option>

                            </select>

                        </div>


                        <div class="col-md-1">

                            <button type="button"
                                    id="btnAgregarDetalle"
                                    class="btn btn-success btn-block"
                                    title="Agregar detalle">

                                <i class="fas fa-plus"></i>

                            </button>

                        </div>

                    </div>


                    <div class="table-responsive mt-3">

                        <table id="tablaDetalle"
                               class="table table-bordered">

                            <thead style="background:#17365D;color:white;">

                                <tr>

                                    <th>#</th>

                                    <th>Descripción</th>

                                    <th>Cantidad</th>

                                    <th>Precio Unit.</th>

                                    <th>IVA</th>

                                    <th>Total</th>

                                    <th>Acción</th>

                                </tr>

                            </thead>


                            <tbody id="tbodyDetalle">
                            </tbody>

                        </table>

                    </div>


                    <div class="row justify-content-end">

                        <div class="col-md-4">

                            <div class="card"
                                 style="background:#f8f9fa;">

                                <div class="card-body">

                                    <div class="d-flex justify-content-between">

                                        <span>
                                            Subtotal:
                                        </span>

                                        <strong id="totalSubtotal">
                                            Gs. 0
                                        </strong>

                                    </div>


                                    <div class="d-flex justify-content-between mt-2">

                                        <span>
                                            IVA 5%:
                                        </span>

                                        <strong id="totalIva5">
                                            Gs. 0
                                        </strong>

                                    </div>


                                    <div class="d-flex justify-content-between mt-2">

                                        <span>
                                            IVA 10%:
                                        </span>

                                        <strong id="totalIva10">
                                            Gs. 0
                                        </strong>

                                    </div>


                                    <hr>


                                    <div class="d-flex justify-content-between">

                                        <strong style="font-size:20px;color:#17365D;">
                                            TOTAL:
                                        </strong>

                                        <strong id="totalGeneral">
                                            Gs. 0
                                        </strong>

                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>

                </div>


                <div class="card-footer text-right">

                    <button type="button"
                            class="btn btn-secondary"
                            id="btnCancelarFactura">

                        <i class="fas fa-times"></i>

                        Cancelar

                    </button>


                    <button type="submit"
                            class="btn btn-primary"
                            id="btnGuardarFactura">

                        <i class="fas fa-save"></i>

                        Guardar Factura

                    </button>

                </div>

            </div>

        </form>

    </div>

</section>


<div class="modal fade"
     id="modalFactura"
     tabindex="-1">

    <div class="modal-dialog modal-lg">

        <div class="modal-content">

            <div class="modal-header"
                 style="background:#17365D;color:white;">

                <h5 class="modal-title">

                    <i class="fas fa-file-invoice"></i>

                    Detalle de Factura

                </h5>


                <button type="button"
                        class="close text-white"
                        data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <div class="modal-body">

                <div class="row">

                    <div class="col-md-6">

                        <strong>
                            N° Factura:
                        </strong>

                        <div id="verNumeroFactura"></div>

                    </div>


                    <div class="col-md-6">

                        <strong>
                            Estado:
                        </strong>

                        <div id="verEstadoFactura"></div>

                    </div>

                </div>


                <hr>


                <div class="row">

                    <div class="col-md-6">

                        <strong>
                            Cliente:
                        </strong>

                        <div id="verClienteFactura"></div>

                    </div>


                    <div class="col-md-3">

                        <strong>
                            Cédula:
                        </strong>

                        <div id="verCedulaFactura"></div>

                    </div>


                    <div class="col-md-3">

                        <strong>
                            Fecha:
                        </strong>

                        <div id="verFechaFactura"></div>

                    </div>

                </div>


                <hr>


                <div class="row">

                    <div class="col-md-6">

                        <strong>
                            Establecimiento:
                        </strong>

                        <div id="verEstablecimientoFactura"></div>

                    </div>


                    <div class="col-md-6">

                        <strong>
                            Dirección:
                        </strong>

                        <div id="verDireccionEstablecimientoFactura"></div>

                    </div>

                </div>


                <hr>


                <div class="table-responsive">

                    <table class="table table-bordered table-sm">

                        <thead style="background:#17365D;color:white;">

                            <tr>

                                <th>
                                    Descripción
                                </th>

                                <th>
                                    Cantidad
                                </th>

                                <th>
                                    Precio
                                </th>

                                <th>
                                    IVA
                                </th>

                                <th>
                                    Total
                                </th>

                            </tr>

                        </thead>


                        <tbody id="tbodyVerDetalle">
                        </tbody>

                    </table>

                </div>


                <div class="text-right">

                    <p>

                        <strong>Subtotal:</strong>

                        <span id="verSubtotal"></span>

                    </p>


                    <p>

                        <strong>IVA 5%:</strong>

                        <span id="verIva5"></span>

                    </p>


                    <p>

                        <strong>IVA 10%:</strong>

                        <span id="verIva10"></span>

                    </p>


                    <h3 style="color:#17365D;">

                        TOTAL:

                        <span id="verTotal"></span>

                    </h3>

                </div>

            </div>


            <div class="modal-footer">
<button type="button"
                        class="btn btn-success"
                        id="btnPagarFactura">

                    <i class="fas fa-check"></i>

                    Marcar pagada

                </button>


                <button type="button"
                        class="btn btn-danger"
                        id="btnAnularFactura">

                    <i class="fas fa-ban"></i>

                    Anular

                </button>


                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    Cerrar

                </button>

            </div>

        </div>

    </div>

</div>


<div class="modal fade"
     id="modalMensajeFactura"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog modal-dialog-centered"
         role="document">

        <div class="modal-content">

            <div class="modal-header"
                 id="modalMensajeFacturaHeader">

                <h5 class="modal-title"
                    id="modalMensajeFacturaTitulo">

                    <i class="fas fa-info-circle"></i>

                    Atención

                </h5>


                <button type="button"
                        class="close text-white"
                        data-dismiss="modal"
                        aria-label="Cerrar">

                    <span aria-hidden="true">&times;</span>

                </button>

            </div>


            <div class="modal-body"
                 id="modalMensajeFacturaTexto">

            </div>


            <div class="modal-footer"
                 id="modalMensajeFacturaFooter">

                <button type="button"
                        class="btn btn-secondary"
                        id="btnCancelarMensajeFactura"
                        data-dismiss="modal">

                    <i class="fas fa-times"></i>

                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-primary"
                        id="btnAceptarMensajeFactura">

                    <i class="fas fa-check"></i>

                    Aceptar

                </button>

            </div>

        </div>

    </div>

</div>


<script>

var facturaSeleccionada = null;

var detallesFactura = [];

var temporizadorBusquedaCliente = null;


function configurarModalMensajeFactura(tipo) {

    var header =
        $('#modalMensajeFacturaHeader');

    var titulo =
        $('#modalMensajeFacturaTitulo');


    header
        .removeClass(
            'bg-primary bg-success bg-warning bg-danger'
        );


    titulo
        .find('i')
        .removeClass(
            'fa-info-circle fa-check-circle fa-exclamation-triangle fa-times-circle fa-question-circle'
        );


    if (tipo === 'success') {

        header.addClass('bg-success text-white');

        titulo
            .find('i')
            .addClass('fa-check-circle');

    }
    else if (tipo === 'warning') {

        header.addClass('bg-warning text-dark');

        titulo
            .find('i')
            .addClass('fa-exclamation-triangle');

    }
    else if (tipo === 'error') {

        header.addClass('bg-danger text-white');

        titulo
            .find('i')
            .addClass('fa-times-circle');

    }
    else if (tipo === 'question') {

        header.addClass('bg-primary text-white');

        titulo
            .find('i')
            .addClass('fa-question-circle');

    }
    else {

        header.addClass('bg-primary text-white');

        titulo
            .find('i')
            .addClass('fa-info-circle');

    }

}


function mostrarAlertaFactura(
    titulo,
    mensaje,
    tipo,
    tiempo
) {

    configurarModalMensajeFactura(
        tipo || 'info'
    );


    $('#modalMensajeFacturaTitulo')
        .contents()
        .filter(function() {

            return this.nodeType === 3;

        })
        .remove();


    $('#modalMensajeFacturaTitulo')
        .append(
            document.createTextNode(
                ' ' + titulo
            )
        );


    $('#modalMensajeFacturaTexto')
        .html(
            escapeHtml(mensaje || '')
                .replace(/\n/g, '<br>')
        );


    $('#btnCancelarMensajeFactura')
        .hide();


    $('#btnAceptarMensajeFactura')
        .show()
        .text('Aceptar');


    $('#modalMensajeFactura')
        .modal('show');


    if (tiempo) {

        setTimeout(
            function() {

                $('#modalMensajeFactura')
                    .modal('hide');

            },
            tiempo
        );

    }

}


function mostrarConfirmacionFactura(
    titulo,
    mensaje
) {

    return new Promise(
        function(resolve) {

            configurarModalMensajeFactura(
                'question'
            );


            $('#modalMensajeFacturaTitulo')
                .contents()
                .filter(function() {

                    return this.nodeType === 3;

                })
                .remove();


            $('#modalMensajeFacturaTitulo')
                .append(
                    document.createTextNode(
                        ' ' + titulo
                    )
                );


            $('#modalMensajeFacturaTexto')
                .html(
                    escapeHtml(mensaje || '')
                        .replace(/\n/g, '<br>')
                );


            $('#btnCancelarMensajeFactura')
                .show()
                .html(
                    '<i class="fas fa-times"></i> Cancelar'
                );


            $('#btnAceptarMensajeFactura')
                .show()
                .removeClass('btn-primary')
                .addClass('btn-primary')
                .html(
                    '<i class="fas fa-check"></i> Sí, guardar'
                );


            var confirmado = false;


            $('#btnAceptarMensajeFactura')
                .off('click.confirmarFactura')
                .on(
                    'click.confirmarFactura',
                    function() {

                        confirmado = true;

                        $('#modalMensajeFactura')
                            .modal('hide');

                    }
                );


            $('#btnCancelarMensajeFactura')
                .off('click.confirmarFactura')
                .on(
                    'click.confirmarFactura',
                    function() {

                        confirmado = false;

                        $('#modalMensajeFactura')
                            .modal('hide');

                    }
                );


            $('#modalMensajeFactura')
                .off('hidden.bs.modal.confirmarFactura')
                .on(
                    'hidden.bs.modal.confirmarFactura',
                    function() {

                        $('#btnAceptarMensajeFactura')
                            .off('click.confirmarFactura');

                        $('#btnCancelarMensajeFactura')
                            .off('click.confirmarFactura');

                        resolve(confirmado);

                    }
                );


            $('#modalMensajeFactura')
                .modal('show');

        }
    );

}


function iniciarFacturacion() {

    configurarDatosFicticios();

    cargarFacturas();

    cargarTarifas();

    obtenerNumeroFactura();

}


iniciarFacturacion();


function nuevaFactura() {

    $('#formFactura')[0].reset();

    $('#id_cliente').val('');

    $('#buscarClienteFactura').val('');

    $('#clienteSeleccionadoFactura')
        .text('Ningún cliente seleccionado');

    $('#resultadosClientesFactura')
        .hide()
        .empty();

    $('#id_servicio').val('');

    $('#selectServicioFactura')
        .empty()
        .append(
            '<option value="">Seleccione primero un cliente</option>'
        )
        .prop('disabled', true);

    $('#servicioSeleccionadoFactura')
        .html(
            '<span class="text-muted">Ningún establecimiento seleccionado</span>'
        );

    detallesFactura = [];

    $('#tbodyDetalle').empty();

    $('#totalSubtotal').text('Gs. 0');

    $('#totalIva5').text('Gs. 0');

    $('#totalIva10').text('Gs. 0');

    $('#totalGeneral').text('Gs. 0');

    $('#cantidadDetalle').val('1');

    $('#precioDetalle').val('0');

    $('#ivaDetalle').val('10');

    $('#selectTarifa').val('');

    $('#descripcionDetalle').val('');

    $('#condicion_venta').val('CREDITO');
    $('#plazo_credito').val('30');
    actualizarDatosCredito();

    configurarDatosFicticios();

    obtenerNumeroFactura();

    $('#seccionListadoFactura').hide();

    $('#seccionFormularioFactura').show();

}


$('#btnCancelarFactura').on(
    'click',
    function() {

        $('#seccionFormularioFactura').hide();

        $('#seccionListadoFactura').show();

    }
);


function obtenerFechaHoyLocal() {

    var hoy = new Date();

    return (
        hoy.getFullYear() +
        '-' +
        String(hoy.getMonth() + 1).padStart(2, '0') +
        '-' +
        String(hoy.getDate()).padStart(2, '0')
    );

}


function sumarDiasFecha(fechaTexto, dias) {

    var partes = String(fechaTexto).substring(0, 10).split('-');

    if (partes.length !== 3) {
        return '';
    }

    var fecha = new Date(
        parseInt(partes[0], 10),
        parseInt(partes[1], 10) - 1,
        parseInt(partes[2], 10)
    );

    fecha.setDate(
        fecha.getDate() + parseInt(dias || 0, 10)
    );

    return (
        fecha.getFullYear() +
        '-' +
        String(fecha.getMonth() + 1).padStart(2, '0') +
        '-' +
        String(fecha.getDate()).padStart(2, '0')
    );

}


function actualizarDatosCredito() {

    var condicion =
        $('#condicion_venta').val();

    var plazo =
        parseInt(
            $('#plazo_credito').val(),
            10
        );

    if (isNaN(plazo) || plazo < 0) {
        plazo = 0;
    }

    var fechaEmision =
        obtenerFechaHoyLocal();

    var fechaVencimiento =
        fechaEmision;

    if (condicion === 'CREDITO') {

        $('#bloqueDatosCredito')
            .removeClass('deshabilitado');

        $('#plazo_credito')
            .prop('disabled', false);

        fechaVencimiento =
            sumarDiasFecha(
                fechaEmision,
                plazo
            );

    } else {

        $('#bloqueDatosCredito')
            .addClass('deshabilitado');

        $('#plazo_credito')
            .val('0')
            .prop('disabled', true);

        fechaVencimiento =
            fechaEmision;

    }

    $('#fecha_vencimiento')
        .val(fechaVencimiento);

    $('#fecha_vencimiento_visual')
        .val(
            formatearFecha(
                fechaVencimiento
            )
        );

}


$('#condicion_venta').on(
    'change',
    function() {
        actualizarDatosCredito();
    }
);


$('#plazo_credito').on(
    'input',
    function() {
        actualizarDatosCredito();
    }
);


function configurarDatosFicticios() {

    var hoy = new Date();

    var inicio = new Date(
        hoy.getFullYear(),
        0,
        1
    );

    var fin = new Date(
        hoy.getFullYear() + 1,
        11,
        31
    );


    var fechaInicio =
        convertirFechaInput(inicio);

    var fechaFin =
        convertirFechaInput(fin);


    $('#timbrado')
        .val('80641827');

    $('#timbrado_visual')
        .val('80641827');


    $('#establecimiento')
        .val('001');

    $('#establecimiento_visual')
        .val('001');


    $('#punto_expedicion')
        .val('001');

    $('#punto_expedicion_visual')
        .val('001');


    $('#fecha_inicio_timbrado')
        .val(fechaInicio);

    $('#fecha_fin_timbrado')
        .val(fechaFin);


    $('#fecha_inicio_visual')
        .val(formatearFecha(fechaInicio));

    $('#fecha_fin_visual')
        .val(formatearFecha(fechaFin));

    actualizarDatosCredito();

}


function convertirFechaInput(fecha) {

    var año =
        fecha.getFullYear();

    var mes =
        String(
            fecha.getMonth() + 1
        ).padStart(2, '0');

    var dia =
        String(
            fecha.getDate()
        ).padStart(2, '0');


    return año + '-' +
           mes + '-' +
           dia;

}


function normalizarTexto(texto) {

    if (
        texto === null ||
        texto === undefined
    ) {

        return '';

    }


    return String(texto)
        .normalize('NFD')
        .replace(
            /[\u0300-\u036f]/g,
            ''
        )
        .toLowerCase()
        .trim();

}


$('#buscarClienteFactura').on(
    'input',
    function() {

        var criterio =
            $(this)
                .val()
                .trim();


        $('#id_cliente').val('');


        $('#clienteSeleccionadoFactura')
            .html(
                '<span class="text-muted">' +
                'Ningún cliente seleccionado' +
                '</span>'
            );


        clearTimeout(
            temporizadorBusquedaCliente
        );


        if (criterio.length < 2) {

            $('#resultadosClientesFactura')
                .hide()
                .empty();

            return;

        }


        temporizadorBusquedaCliente =
            setTimeout(
                function() {

                    buscarClientesFactura(
                        criterio
                    );

                },
                300
            );

    }
);


$('#btnBuscarClienteFactura').on(
    'click',
    function() {

        var criterio =
            $('#buscarClienteFactura')
                .val()
                .trim();


        if (criterio.length < 2) {

            alert(
                'Ingrese al menos 2 caracteres para buscar un cliente.'
            );

            return;

        }


        buscarClientesFactura(
            criterio
        );

    }
);


function buscarClientesFactura(criterio) {

    $('#btnBuscarClienteFactura')
        .prop(
            'disabled',
            true
        );


    $.ajax({

        url: 'FacturaController',

        type: 'GET',

        data: {

            accion: 'buscarClientes',

            criterio: criterio

        },

        dataType: 'json',


        success: function(data) {

            mostrarResultadosClientes(
                data,
                criterio
            );

        },


        error: function(xhr) {

            console.error(
                xhr.responseText
            );


            $('#resultadosClientesFactura')
                .html(

                    '<div class="p-2 text-danger">' +

                        '<i class="fas fa-exclamation-triangle"></i> ' +

                        'No se pudieron consultar los clientes.' +

                    '</div>'

                )
                .show();

        },


        complete: function() {

            $('#btnBuscarClienteFactura')
                .prop(
                    'disabled',
                    false
                );

        }

    });

}


function mostrarResultadosClientes(
    data,
    criterio
) {

    var contenedor =
        $('#resultadosClientesFactura');


    contenedor.empty();


    if (
        !data ||
        data.length === 0
    ) {

        contenedor.html(

            '<div class="p-2 text-muted">' +

                '<i class="fas fa-search"></i> ' +

                'No se encontraron clientes para "' +

                escapeHtml(criterio) +

                '".' +

            '</div>'

        ).show();

        return;

    }


    $.each(

        data,

        function(i, cliente) {

            var nombre =
                cliente.nombre || '';

            var apellido =
                cliente.apellido || '';

            var cedula =
                cliente.cedula || '';

            var ruc =
                cliente.ruc || '';

            var direccion =
                cliente.direccion || '';


            var nombreCompleto =
                (
                    nombre +
                    ' ' +
                    apellido
                ).trim();


            var identificacion = '';


            if (cedula) {

                identificacion =
                    cedula;

            }


            if (
                ruc &&
                identificacion
            ) {

                identificacion +=
                    ' / ' + ruc;

            }

            else if (ruc) {

                identificacion =
                    ruc;

            }


            var elemento =
                $('<button>');


            elemento.attr(
                'type',
                'button'
            );


            elemento.addClass(
                'list-group-item list-group-item-action cliente-factura-item'
            );


            elemento.attr(
                'data-id',
                cliente.id_cliente
            );


            elemento.data(
                'cliente',
                cliente
            );


            elemento.html(

                '<strong>' +

                    escapeHtml(
                        nombreCompleto
                    ) +

                '</strong>' +

                (

                    identificacion

                        ?

                        '<span class="text-muted"> - ' +

                        escapeHtml(identificacion) +

                        '</span>'

                        :

                        ''

                ) +

                (

                    direccion

                        ?

                        '<small class="d-block text-muted">' +

                        escapeHtml(direccion) +

                        '</small>'

                        :

                        ''

                )

            );


            contenedor.append(
                elemento
            );

        }

    );


    contenedor.show();

}


$(document).on(
    'click',
    '.cliente-factura-item',
    function() {

        var cliente =
            $(this).data('cliente');


        if (!cliente) {

            return;

        }


        var nombreCompleto =
            (
                (cliente.nombre || '') +
                ' ' +
                (cliente.apellido || '')
            ).trim();


        $('#id_cliente')
            .val(
                cliente.id_cliente
            );


        $('#buscarClienteFactura')
            .val(
                nombreCompleto
            );


        $('#clienteSeleccionadoFactura')
            .html(

                '<span class="text-success">' +

                    '<i class="fas fa-check-circle"></i> ' +

                    'Cliente seleccionado: <strong>' +

                        escapeHtml(
                            nombreCompleto
                        ) +

                    '</strong>' +

                '</span>'

            );


        $('#resultadosClientesFactura')
            .hide()
            .empty();

        cargarServiciosCliente(
            cliente.id_cliente
        );

    }
);


$(document).on(
    'click',
    function(e) {

        if (
            !$(e.target).closest(
                '.contenedor-buscador-cliente'
            ).length
        ) {

            $('#resultadosClientesFactura')
                .hide();

        }

    }
);


function cargarServiciosCliente(idCliente) {

    if (!idCliente) {

        $('#id_servicio').val('');

        $('#selectServicioFactura')
            .empty()
            .append(
                '<option value="">Seleccione primero un cliente</option>'
            )
            .prop('disabled', true);

        $('#servicioSeleccionadoFactura')
            .html(
                '<span class="text-muted">Ningún establecimiento seleccionado</span>'
            );

        return;
    }

    $('#selectServicioFactura')
        .prop('disabled', true)
        .empty()
        .append(
            '<option value="">Cargando establecimientos...</option>'
        );

    $('#id_servicio').val('');

    $('#servicioSeleccionadoFactura')
        .html(
            '<span class="text-muted">' +
            '<i class="fas fa-spinner fa-spin"></i> ' +
            'Consultando establecimientos del cliente...' +
            '</span>'
        );

    $.ajax({

        url: 'FacturaController',

        type: 'GET',

        data: {

            accion: 'serviciosPorCliente',

            id_cliente: idCliente

        },

        dataType: 'json',

        success: function(data) {

            var select =
                $('#selectServicioFactura');

            select.empty();

            select.append(
                '<option value="">' +
                    'Seleccione un establecimiento' +
                '</option>'
            );

            if (!data || data.length === 0) {

                select
                    .prop('disabled', true)
                    .empty()
                    .append(
                        '<option value="">' +
                            'El cliente no tiene establecimientos activos' +
                        '</option>'
                    );

                $('#servicioSeleccionadoFactura')
                    .html(
                        '<span class="text-danger">' +
                        '<i class="fas fa-exclamation-triangle"></i> ' +
                        'El cliente no tiene establecimientos/servicios activos registrados.' +
                        '</span>'
                    );

                return;
            }

            $.each(
                data,
                function(i, servicio) {

                    var nombre =
                        servicio.nombre_establecimiento || '';

                    var direccion =
                        servicio.direccion_servicio || '';

                    var tarifa =
                        servicio.nombre_tarifa || '';

                    var monto =
                        parseFloat(
                            servicio.monto_tarifa || 0
                        );

                    var texto =
                        nombre;

                    if (direccion) {

                        texto +=
                            ' - ' + direccion;
                    }

                    if (tarifa) {

                        texto +=
                            ' | ' + tarifa +
                            ' (' +
                            formatearMonto(monto) +
                            ')';
                    }

                    var option =
                        $('<option>');

                    option.val(
                        servicio.id_servicio
                    );

                    option.attr(
                        'data-servicio',
                        JSON.stringify(servicio)
                    );

                    option.text(texto);

                    select.append(option);

                }
            );

            select.prop(
                'disabled',
                false
            );

            if (data.length === 1) {

                select.val(
                    data[0].id_servicio
                );

                seleccionarServicioFactura(
                    data[0]
                );
            }

        },

        error: function(xhr) {

            console.error(
                xhr.responseText
            );

            $('#selectServicioFactura')
                .prop('disabled', true)
                .empty()
                .append(
                    '<option value="">' +
                        'No se pudieron cargar los establecimientos' +
                    '</option>'
                );

            $('#servicioSeleccionadoFactura')
                .html(
                    '<span class="text-danger">' +
                    '<i class="fas fa-exclamation-triangle"></i> ' +
                    'No se pudieron consultar los establecimientos del cliente.' +
                    '</span>'
                );

        }

    });
}


$('#selectServicioFactura').on(
    'change',
    function() {

        var idServicio =
            $(this).val();

        if (!idServicio) {

            $('#id_servicio').val('');

            $('#servicioSeleccionadoFactura')
                .html(
                    '<span class="text-muted">' +
                    'Ningún establecimiento seleccionado' +
                    '</span>'
                );

            return;
        }

        var option =
            $(this)
                .find('option:selected');

        var servicio = null;

        try {

            servicio =
                JSON.parse(
                    option.attr('data-servicio') || '{}'
                );

        } catch (e) {

            servicio = null;
        }

        if (servicio) {

            seleccionarServicioFactura(
                servicio
            );
        }

    }
);


function seleccionarServicioFactura(servicio) {

    if (!servicio || !servicio.id_servicio) {

        return;
    }

    $('#id_servicio')
        .val(
            servicio.id_servicio
        );

    var nombre =
        servicio.nombre_establecimiento || '';

    var direccion =
        servicio.direccion_servicio || '';

    var barrio =
        servicio.nombre_barrio || '';

    var tarifa =
        servicio.nombre_tarifa || '';

    var monto =
        parseFloat(
            servicio.monto_tarifa || 0
        );

    var detalle = '';

    if (nombre) {

        detalle +=
            '<strong>' +
            escapeHtml(nombre) +
            '</strong>';
    }

    if (direccion) {

        detalle +=
            '<br><span class="text-muted">' +
            '<i class="fas fa-map-marker-alt"></i> ' +
            escapeHtml(direccion) +
            '</span>';
    }

    if (barrio) {

        detalle +=
            ' <span class="text-muted">(' +
            escapeHtml(barrio) +
            ')</span>';
    }

    if (tarifa) {

        detalle +=
            '<br><span class="text-primary">' +
            '<i class="fas fa-tag"></i> ' +
            escapeHtml(tarifa) +
            ' - ' +
            escapeHtml(formatearMonto(monto)) +
            '</span>';
    }

    $('#servicioSeleccionadoFactura')
        .html(
            '<span class="text-success">' +
            '<i class="fas fa-check-circle"></i> ' +
            detalle +
            '</span>'
        );

    /*
     * La estructura actual de servicio no posee todavía
     * un código fiscal separado para el establecimiento.
     * Se conserva 001 como código académico actual.
     */
    $('#establecimiento')
        .val('001');

    $('#establecimiento_visual')
        .val('001');

    if (servicio.id_tarifa) {

        var tarifaEncontrada =
            $('#selectTarifa option[value="' +
            servicio.id_tarifa +
            '"]');

        if (tarifaEncontrada.length) {

            $('#selectTarifa')
                .val(
                    servicio.id_tarifa
                )
                .trigger('change');

        } else {

            cargarTarifas(function() {

                $('#selectTarifa')
                    .val(
                        servicio.id_tarifa
                    )
                    .trigger('change');

            });
        }
    }
}


function cargarTarifas(callback) {

    $.ajax({

        url: 'FacturaController',

        type: 'GET',

        data: {

            accion: 'tarifasActivas'

        },

        dataType: 'json',


        success: function(data) {

            var select =
                $('#selectTarifa');


            select.empty();


            select.append(

                '<option value="">' +

                    'Seleccione una tarifa' +

                '</option>'

            );


            $.each(

                data,

                function(i, tarifa) {

                    var descripcion =
                        tarifa.descripcion || '';

                    var monto =
                        parseFloat(
                            tarifa.monto || 0
                        );


                    var option =
                        $('<option>');


                    option.val(
                        tarifa.id_tarifa
                    );


                    option.attr(
                        'data-descripcion',
                        descripcion
                    );


                    option.attr(
                        'data-precio',
                        monto
                    );


                    option.text(

                        (

                            tarifa.nombre ||

                            ''

                        ) +

                        ' - ' +

                        formatearMonto(
                            monto
                        )

                    );


                    select.append(
                        option
                    );

                }

            );

            if (typeof callback === 'function') {

                callback();

            }

        },


        error: function(xhr) {

            console.error(
                xhr.responseText
            );


            alert(
                'No se pudieron cargar las tarifas.'
            );

        }

    });

}


$('#selectTarifa').on(
    'change',
    function() {

        var option =
            $(this)
                .find('option:selected');


        $('#descripcionDetalle')
            .val(
                option.attr(
                    'data-descripcion'
                ) || ''
            );


        $('#precioDetalle')
            .val(
                option.attr(
                    'data-precio'
                ) || '0'
            );

    }
);


function obtenerNumeroFactura() {

    $.ajax({

        url: 'FacturaController',

        type: 'GET',

        data: {

            accion: 'siguienteNumero'

        },

        dataType: 'json',


        success: function(data) {

            if (
                data &&
                data.success &&
                data.numero_factura
            ) {

                var numero =
                    data.numero_factura;


                $('#numero_factura')
                    .val(numero);


                $('#numero_factura_visual')
                    .val(

                        '001-001-' +

                        numero

                    );

            }

            else {

                establecerNumeroTemporal();

            }

        },


        error: function(xhr) {

            console.error(
                xhr.responseText
            );

            establecerNumeroTemporal();

        }

    });

}


function establecerNumeroTemporal() {

    $('#numero_factura')
        .val('0000001');


    $('#numero_factura_visual')
        .val(
            '001-001-0000001'
        );

}


$('#btnAgregarDetalle').on(
    'click',
    function() {

        agregarDetalle();

    }
);


function agregarDetalle() {

    var idTarifa =
        $('#selectTarifa').val();


    var descripcion =
        $('#descripcionDetalle')
            .val()
            .trim();


    var cantidad =
        parseFloat(
            $('#cantidadDetalle').val()
        );


    var precio =
        parseFloat(
            $('#precioDetalle').val()
        );


    var iva =
        parseFloat(
            $('#ivaDetalle').val()
        );


    if (!idTarifa) {

        alert(
            'Seleccione una tarifa o servicio.'
        );

        return;

    }


    if (!descripcion) {

        alert(
            'Ingrese la descripción.'
        );

        return;

    }


    if (
        isNaN(cantidad) ||
        cantidad <= 0
    ) {

        alert(
            'Ingrese una cantidad válida.'
        );

        return;

    }


    if (
        isNaN(precio) ||
        precio < 0
    ) {

        alert(
            'Ingrese un precio válido.'
        );

        return;

    }


    detallesFactura.push({

        id_tarifa:
            idTarifa,

        descripcion:
            descripcion,

        cantidad:
            cantidad,

        precio_unitario:
            precio,

        porcentaje_iva:
            iva

    });


    $('#selectTarifa').val('');

    $('#descripcionDetalle').val('');

    $('#cantidadDetalle').val('1');

    $('#precioDetalle').val('0');

    $('#ivaDetalle').val('10');


    renderizarDetalles();

}


function renderizarDetalles() {

    var tbody =
        $('#tbodyDetalle');


    tbody.empty();


    var subtotal = 0;

    var iva5 = 0;

    var iva10 = 0;

    var totalGeneral = 0;


    $.each(

        detallesFactura,

        function(i, detalle) {

            var totalLinea =
                detalle.cantidad *
                detalle.precio_unitario;


            var montoIva = 0;


            if (
                detalle.porcentaje_iva == 5
            ) {

                montoIva =
                    totalLinea *
                    5 /
                    105;


                iva5 +=
                    montoIva;

            }

            else if (
                detalle.porcentaje_iva == 10
            ) {

                montoIva =
                    totalLinea *
                    10 /
                    110;


                iva10 +=
                    montoIva;

            }


            subtotal +=
                totalLinea -
                montoIva;


            totalGeneral +=
                totalLinea;


            tbody.append(

                '<tr>' +

                    '<td>' +

                        (i + 1) +

                    '</td>' +

                    '<td>' +

                        escapeHtml(
                            detalle.descripcion
                        ) +

                    '</td>' +

                    '<td class="cell-right">' +

                        formatearNumero(
                            detalle.cantidad
                        ) +

                    '</td>' +

                    '<td class="text-right">' +

                        formatearMonto(
                            detalle.precio_unitario
                        ) +

                    '</td>' +

                    '<td class="text-center">' +

                        detalle.porcentaje_iva +

                        '%' +

                    '</td>' +

                    '<td class="text-right">' +

                        formatearMonto(
                            totalLinea
                        ) +

                    '</td>' +

                    '<td class="text-center">' +

                        '<button type="button" ' +

                                'class="btn btn-danger btn-action" ' +

                                'title="Eliminar" ' +

                                'onclick="eliminarDetalle(' +

                                i +

                                ')">' +

                            '<i class="fas fa-trash"></i>' +

                        '</button>' +

                    '</td>' +

                '</tr>'

            );

        }

    );


    $('#totalSubtotal')
        .text(
            formatearMonto(
                subtotal
            )
        );


    $('#totalIva5')
        .text(
            formatearMonto(
                iva5
            )
        );


    $('#totalIva10')
        .text(
            formatearMonto(
                iva10
            )
        );


    $('#totalGeneral')
        .text(
            formatearMonto(
                totalGeneral
            )
        );

}


function eliminarDetalle(index) {

    detallesFactura.splice(
        index,
        1
    );


    renderizarDetalles();

}


$('#formFactura').on(
    'submit',
    function(e) {

        e.preventDefault();


        if (!$('#id_cliente').val()) {

            mostrarAlertaFactura(

                'Atención',

                'Debe seleccionar un cliente.',

                'warning'

            );


            $('#buscarClienteFactura').focus();


            return;

        }


        if (!$('#id_servicio').val()) {

            mostrarAlertaFactura(

                'Atención',

                'Debe seleccionar un establecimiento/servicio del cliente.',

                'warning'

            );

            $('#selectServicioFactura').focus();

            return;

        }


        if (
            detallesFactura.length === 0
        ) {

            mostrarAlertaFactura(

                'Atención',

                'Debe agregar al menos un detalle a la factura.',

                'warning'

            );


            return;

        }


        configurarDatosFicticios();

        actualizarDatosCredito();

        if (
            $('#condicion_venta').val() === 'CREDITO' &&
            (
                !$('#plazo_credito').val() ||
                parseInt($('#plazo_credito').val(), 10) <= 0
            )
        ) {

            mostrarAlertaFactura(
                'Atención',
                'Para una factura a crédito debe ingresar un plazo de crédito mayor a 0 días.',
                'warning'
            );

            $('#plazo_credito').focus();

            return;

        }


        if (
            !$('#numero_factura').val()
        ) {

            mostrarAlertaFactura(

                'Atención',

                'No se pudo generar el número de factura.',

                'warning'

            );


            obtenerNumeroFactura();


            return;

        }


        mostrarConfirmacionFactura(

            '¿Registrar factura?',

            'Se registrará la factura con los datos ingresados.'

        ).then(function(confirmado) {

            if (!confirmado) {

                return;

            }


            var formData =

                new FormData(

                    document.getElementById('formFactura')

                );


            formData.append(

                'accion',

                'guardar'

            );


            detallesFactura.forEach(

                function(detalle) {

                    formData.append(

                        'id_tarifa[]',

                        detalle.id_tarifa

                    );


                    formData.append(

                        'descripcion[]',

                        detalle.descripcion

                    );


                    formData.append(

                        'cantidad[]',

                        detalle.cantidad

                    );


                    formData.append(

                        'precio_unitario[]',

                        detalle.precio_unitario

                    );


                    formData.append(

                        'porcentaje_iva[]',

                        detalle.porcentaje_iva

                    );

                }

            );


            $('#btnGuardarFactura')
                .prop(
                    'disabled',
                    true
                )
                .html(
                    '<i class="fas fa-spinner fa-spin"></i> Guardando...'
                );


            $.ajax({

                url: 'FacturaController',

                type: 'POST',

                data: formData,

                processData: false,

                contentType: false,

                dataType: 'json',


                success: function(data) {

                    if (data.success) {

                        $('#seccionFormularioFactura')
                            .hide();


                        $('#seccionListadoFactura')
                            .show();


                        cargarFacturas();


                        mostrarAlertaFactura(

                            '¡Correcto!',

                            'Factura registrada correctamente. N° de factura: 001-001-' +

                            (data.numero_factura || ''),

                            'success',

                            2200

                        );

                    }

                    else {

                        mostrarAlertaFactura(

                            'Error',

                            data.mensaje ||

                            'No se pudo guardar la factura.',

                            'error'

                        );

                    }

                },


                error: function(xhr) {

                    console.error(
                        xhr.responseText
                    );


                    var mensaje =

                        'Ocurrió un error al guardar la factura.';


                    try {

                        var respuesta =

                            JSON.parse(

                                xhr.responseText

                            );


                        if (
                            respuesta.mensaje
                        ) {

                            mensaje =

                                respuesta.mensaje;

                        }

                    }

                    catch (e) {
                    }


                    mostrarAlertaFactura(

                        'Error',

                        mensaje,

                        'error'

                    );

                },


                complete: function() {

                    $('#btnGuardarFactura')
                        .prop(
                            'disabled',
                            false
                        )
                        .html(
                            '<i class="fas fa-save"></i> Guardar Factura'
                        );

                }

            });

        });

    }
);


/* INICIALIZAR DATATABLE DE FACTURAS */

function inicializarTablaFacturas(intentos) {

    if (intentos === undefined || intentos === null) {
        intentos = 0;
    }

    var tabla = document.getElementById('tablaFacturas');

    if (!tabla) {
        if (intentos < 50) {
            setTimeout(function() {
                inicializarTablaFacturas(intentos + 1);
            }, 100);
        }
        return;
    }

    if (!window.jQuery || !$.fn || !$.fn.DataTable) {
        if (intentos < 50) {
            setTimeout(function() {
                inicializarTablaFacturas(intentos + 1);
            }, 100);
        }
        return;
    }

    if ($.fn.DataTable.isDataTable(tabla)) {
        return;
    }

    /*
     * NORMALIZAR DATOS PARA EL BUSCADOR
     * Permite buscar sin diferenciar mayúsculas, minúsculas ni acentos.
     */

    if (
        $.fn.dataTable &&
        $.fn.dataTable.ext &&
        $.fn.dataTable.ext.type &&
        $.fn.dataTable.ext.type.search
    ) {

        $.fn.dataTable.ext.type.search.string = function(data) {

            if (
                data === null ||
                data === undefined
            ) {
                return '';
            }

            return normalizarTexto(
                String(data).replace(/<[^>]*>/g, ' ')
            );

        };

    }

    $('#tablaFacturas').DataTable({

        pageLength: 10,

        lengthMenu: [
            [10, 25, 50, 100],
            [10, 25, 50, 100]
        ],

        order: [],

        columnDefs: [
            {
                orderable: false,
                searchable: false,
                targets: 9
            }
        ],

        language: {

            decimal: ',',
            thousands: '.',
            processing: 'Procesando...',
            search: 'Buscar:',
            lengthMenu: 'Mostrar _MENU_ registros',
            info: 'Mostrando _START_ a _END_ de _TOTAL_ registros',
            infoEmpty: 'Mostrando 0 a 0 de 0 registros',
            infoFiltered: '(filtrado de _MAX_ registros)',
            infoPostFix: '',
            loadingRecords: 'Cargando...',
            zeroRecords: 'No se encontraron registros',
            emptyTable: 'No existen facturas registradas',

            paginate: {
                first: 'Primero',
                previous: 'Anterior',
                next: 'Siguiente',
                last: 'Último'
            },

            aria: {
                sortAscending: ': activar para ordenar la columna de manera ascendente',
                sortDescending: ': activar para ordenar la columna de manera descendente'
            }
        }
    });

    /*
     * BUSCADOR DE FACTURAS
     * Búsqueda independiente de mayúsculas, minúsculas y acentos.
     *
     * IMPORTANTE:
     * No utilizamos el buscador interno de DataTables para comparar
     * el texto, porque por ejemplo "cre" no coincide con "CRÉDITO"
     * cuando DataTables compara directamente el contenido original.
     *
     * El filtro personalizado compara ambos textos normalizados.
     * De esta manera funcionan: José / jose / JOSÉ / JOSE,
     * Crédito / credito / CREDITO / CRÉDITO, etc.
     */

    var tablaFacturas = $('#tablaFacturas').DataTable();

    if (!window._jmqFiltroFacturacionRegistrado) {

        $.fn.dataTable.ext.search.push(function(
            settings,
            data,
            dataIndex,
            rowData
        ) {

            if (
                !settings.nTable ||
                settings.nTable.id !== 'tablaFacturas'
            ) {
                return true;
            }

            var campoBusqueda =
                $('#tablaFacturas_filter input');

            if (!campoBusqueda.length) {
                return true;
            }

            var criterio =
                normalizarTexto(
                    campoBusqueda.val() || ''
                );

            if (criterio === '') {
                return true;
            }

            var contenidoFila =
                Array.isArray(data)
                    ? data.join(' ')
                    : String(data || '');

            contenidoFila =
                contenidoFila.replace(
                    /<[^>]*>/g,
                    ' '
                );

            contenidoFila =
                normalizarTexto(
                    contenidoFila
                );

            return contenidoFila.indexOf(
                criterio
            ) !== -1;

        });

        window._jmqFiltroFacturacionRegistrado = true;

    }

    /*
     * Quitamos solamente el evento de búsqueda automática de
     * DataTables para que nuestro filtro normalizado sea quien
     * controle la búsqueda de esta tabla. No se modifica ningún
     * otro comportamiento de la tabla.
     */

    $('#tablaFacturas_filter input')
        .off('.DT')
        .off('input.facturacionNormalizada')
        .on('input.facturacionNormalizada', function() {

            /*
             * Dejamos vacío el criterio interno de DataTables.
             * El filtro personalizado utiliza el contenido real
             * del buscador y aplica la normalización.
             */

            tablaFacturas
                .search('')
                .draw();

        });
}

function formatearCondicionFactura(factura) {

    var condicion =
        String(factura.condicion_venta || '')
            .trim()
            .toUpperCase();

    if (condicion === 'CREDITO') {

        var plazo =
            parseInt(factura.plazo_credito || 0, 10);

        return '<span class="badge badge-warning">' +
                'CRÉDITO' +
                (plazo > 0 ? ' ' + plazo + ' días' : '') +
            '</span>';
    }

    return '<span class="badge badge-secondary">CONTADO</span>';
}


function cargarFacturas() {

    $.ajax({

        url: 'FacturaController',

        type: 'GET',

        data: {

            accion: 'listar'

        },

        dataType: 'json',


        success: function(data) {

            if (
                window.jQuery &&
                $.fn &&
                $.fn.DataTable &&
                $.fn.DataTable.isDataTable(
                    '#tablaFacturas'
                )
            ) {

                $('#tablaFacturas')
                    .DataTable()
                    .destroy();

            }


            var tbody =
                $('#tbodyFacturas');


            tbody.empty();


            $.each(

                data,

                function(i, factura) {

                    var numero =

                        '001-001-' +

                        (
                            factura.numero_factura ||
                            ''
                        );


                    var estado =

                        (
                            factura.estado ||
                            ''
                        ).toUpperCase();


                    var badge;


                    if (
                        estado === 'PAGADA'
                    ) {

                        badge =

                            '<span class="badge badge-success">' +

                            'PAGADA' +

                            '</span>';

                    }

                    else if (
                        estado === 'ANULADA'
                    ) {

                        badge =

                            '<span class="badge badge-danger">' +

                            'ANULADA' +

                            '</span>';

                    }

                    else {

                        badge =

                            '<span class="badge badge-info">' +

                            'EMITIDA' +

                            '</span>';

                    }


                    var botones =

                        '<button type="button" ' +

                                'class="btn btn-info btn-action" ' +

                                'title="Ver / imprimir factura" ' +

                                'onclick="verFactura(' +

                                factura.id_factura +

                                ')">' +

                            '<i class="fas fa-eye"></i>' +

                        '</button>';


                    if (
                        estado !== 'PAGADA' &&
                        estado !== 'ANULADA'
                    ) {

                        botones +=

                            '<button type="button" ' +

                                    'class="btn btn-success btn-action" ' +

                                    'title="Marcar pagada" ' +

                                    'onclick="marcarPagadaDesdeLista(' +

                                    factura.id_factura +

                                    ')">' +

                                '<i class="fas fa-check"></i>' +

                            '</button>' +


                            '<button type="button" ' +

                                    'class="btn btn-danger btn-action" ' +

                                    'title="Anular" ' +

                                    'onclick="anularDesdeLista(' +

                                    factura.id_factura +

                                    ')">' +

                                '<i class="fas fa-ban"></i>' +

                            '</button>';

                    }


                    var nombre =

                        factura.nombre_cliente ||

                        '';


                    var apellido =

                        factura.apellido_cliente ||

                        '';


                    tbody.append(

                        '<tr>' +

                            '<td>' +

                                (

                                    factura.id_factura ||

                                    ''

                                ) +

                            '</td>' +

                            '<td class="cell-center">' +

                                escapeHtml(numero) +

                            '</td>' +

                            '<td class="cell-wrap">' +

                                escapeHtml(

                                    (

                                        nombre +

                                        ' ' +

                                        apellido

                                    ).trim()

                                ) +

                            '</td>' +

                            '<td class="cell-center">' +

                                formatearFecha(

                                    obtenerFechaFactura(

                                        factura

                                    )

                                ) +

                            '</td>' +

                            '<td class="cell-center">' +

                                escapeHtml(

                                    factura.periodo ||

                                    ''

                                ) +

                            '</td>' +

                            '<td class="cell-center">' +

                                formatearCondicionFactura(factura) +

                            '</td>' +

                            '<td class="cell-center">' +

                                formatearFecha(

                                    factura.fecha_vencimiento ||

                                    ''

                                ) +

                            '</td>' +

                            '<td class="text-right">' +

                                formatearMonto(

                                    factura.total

                                ) +

                            '</td>' +

                            '<td class="text-center">' +

                                badge +

                            '</td>' +

                            '<td class="text-center">' +

                                botones +

                            '</td>' +

                        '</tr>'

                    );

                }

            );


            /*
             * La vista se carga dinámicamente dentro del dashboard.
             * Esperamos a que DataTables esté disponible antes de inicializar.
             */
            inicializarTablaFacturas(0);

            /*
             * Segunda verificación: al cargar esta vista dentro del dashboard
             * mediante AJAX, dejamos un intento adicional por si DataTables
             * todavía está terminando de estar disponible.
             */
            setTimeout(function() {

                var tablaFacturas =
                    document.getElementById('tablaFacturas');

                if (
                    tablaFacturas &&
                    window.jQuery &&
                    $.fn &&
                    $.fn.DataTable &&
                    !$.fn.DataTable.isDataTable(tablaFacturas)
                ) {
                    inicializarTablaFacturas(0);
                }

            }, 250);


        },


        error: function(xhr) {

            console.error(
                xhr.responseText
            );


            alert(
                'No se pudieron cargar las facturas.'
            );

        }

    });

}


function verFactura(id) {

    if (!id) {
        return;
    }

    abrirPDFFactura(id);

}


function abrirPDFFactura(id) {

    if (!id) {

        return;

    }


    window.open(

        'FacturaController' +

        '?accion=reporteIndividual' +

        '&id_factura=' +

        encodeURIComponent(id),

        '_blank'

    );

}


$('#btnPagarFactura').on(
    'click',
    function() {

        if (
            facturaSeleccionada
        ) {

            marcarPagadaDesdeLista(

                facturaSeleccionada

            );

        }

    }
);


function marcarPagadaDesdeLista(id) {

    if (
        !confirm(
            '¿Desea marcar esta factura como PAGADA?'
        )
    ) {

        return;

    }


    $.ajax({

        url: 'FacturaController',

        type: 'POST',

        data: {

            accion: 'marcarPagada',

            id_factura: id

        },

        dataType: 'json',


        success: function(data) {

            if (data.success) {

                $('#modalFactura')
                    .modal('hide');


                cargarFacturas();


                alert(
                    'Factura marcada como PAGADA.'
                );

            }

            else {

                alert(

                    data.mensaje ||

                    'No se pudo actualizar la factura.'

                );

            }

        },


        error: function(xhr) {

            console.error(
                xhr.responseText
            );


            alert(
                'Ocurrió un error al actualizar la factura.'
            );

        }

    });

}


$('#btnAnularFactura').on(
    'click',
    function() {

        if (
            facturaSeleccionada
        ) {

            anularDesdeLista(

                facturaSeleccionada

            );

        }

    }
);


function anularDesdeLista(id) {

    if (
        !confirm(
            '¿Está seguro de ANULAR esta factura?'
        )
    ) {

        return;

    }


    $.ajax({

        url: 'FacturaController',

        type: 'POST',

        data: {

            accion: 'anular',

            id_factura: id

        },

        dataType: 'json',


        success: function(data) {

            if (data.success) {

                $('#modalFactura')
                    .modal('hide');


                cargarFacturas();


                alert(
                    'Factura anulada correctamente.'
                );

            }

            else {

                alert(

                    data.mensaje ||

                    'No se pudo anular la factura.'

                );

            }

        },


        error: function(xhr) {

            console.error(
                xhr.responseText
            );


            alert(
                'Ocurrió un error al anular la factura.'
            );

        }

    });

}


function formatearNumero(valor) {

    var numero =
        parseFloat(valor);


    if (isNaN(numero)) {

        numero = 0;

    }


    return numero.toLocaleString(

        'es-PY',

        {

            minimumFractionDigits: 0,

            maximumFractionDigits: 2

        }

    );

}


function formatearMonto(valor) {

    var numero =
        parseFloat(valor);


    if (isNaN(numero)) {

        numero = 0;

    }


    return 'Gs. ' +

        numero.toLocaleString(

            'es-PY',

            {

                minimumFractionDigits: 0,

                maximumFractionDigits: 2

            }

        );

}


function obtenerFechaFactura(factura) {

    if (!factura) {

        return '';

    }


    if (
        factura.fecha_emision
    ) {

        return factura.fecha_emision;

    }


    if (
        factura.fecha
    ) {

        return factura.fecha;

    }


    if (
        factura.fecha_factura
    ) {

        return factura.fecha_factura;

    }


    return '';

}


function formatearFecha(fecha) {

    if (
        fecha === null ||
        fecha === undefined ||
        fecha === ''
    ) {

        return '';

    }


    var texto =
        String(fecha);


    if (
        /^\d{4}-\d{2}-\d{2}/.test(
            texto
        )
    ) {

        var partes =
            texto
                .substring(0, 10)
                .split('-');


        return (

            partes[2] +

            '/' +

            partes[1] +

            '/' +

            partes[0]

        );

    }


    return texto;

}


function escapeHtml(text) {

    if (
        text === null ||
        text === undefined
    ) {

        return '';

    }


    return String(text)

        .replace(
            /&/g,
            '&amp;'
        )

        .replace(
            /</g,
            '&lt;'
        )

        .replace(
            />/g,
            '&gt;'
        )

        .replace(
            /"/g,
            '&quot;'
        )

        .replace(
            /'/g,
            '&#039;'
        );

}

</script>