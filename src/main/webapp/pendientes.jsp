<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Pendientes | JMQ SYSTEM</title>


    <style>

        /* CONTENEDOR PRINCIPAL */

        .pendientes-container {

            width: 100%;

            padding: 0;

            margin: 0;


            font-size: 14px;

        }


        /* ENCABEZADO */









        /* TARJETAS DE RESUMEN */

        .resumen-container {

            display: flex;

            flex-wrap: wrap;

            margin-left: -5px;

            margin-right: -5px;

            margin-bottom: 15px;

        }


        .resumen-col {

            padding-left: 5px;

            padding-right: 5px;

            flex: 1;

            min-width: 220px;

        }


        .resumen-card {

            border-radius: 5px;

            min-height: 82px;

            padding: 12px 15px;

            background: #ffffff;

            border: 1px solid #dee2e6;

            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);

            position: relative;

            overflow: hidden;

        }


        .resumen-card .resumen-icon {

            position: absolute;

            right: 14px;

            top: 15px;

            font-size: 27px;

            opacity: .16;

        }


        .resumen-label {

            display: block;

            font-size: 11px;

            font-weight: 600;

            text-transform: uppercase;

            color: #6c757d;

            margin-bottom: 5px;

        }


        .resumen-value {

            display: block;

            font-size: 19px;

            font-weight: 700;

            color: #343a40;

        }


        /* TOTAL FACTURADO */

        .resumen-card.facturado {

            border-left: 4px solid #007bff;

            background: #eef5ff;

        }


        .resumen-card.facturado .resumen-icon {

            color: #007bff;

        }


        /* TOTAL PAGADO */

        .resumen-card.pagado {

            border-left: 4px solid #28a745;

            background: #eef9f1;

        }


        .resumen-card.pagado .resumen-icon {

            color: #28a745;

        }


        /* SALDO PENDIENTE */

        .resumen-card.saldo {

            border-left: 4px solid #dc3545;

            background: #fff1f1;

        }


        .resumen-card.saldo .resumen-icon {

            color: #dc3545;

        }


        /* TABLA */

        .tabla-contenedor {

            width: 100%;

            overflow-x: auto;

        }


        #tablaPendientes {

            width: 100% !important;

            font-size: 13px;

        }


        #tablaPendientes thead th {


            font-weight: 600;

            vertical-align: middle;

            white-space: nowrap;

            padding: 8px 6px;

        }


        #tablaPendientes tbody td {

            font-size: 13px;

            vertical-align: middle;

            padding: 7px 6px;

        }


        #tablaPendientes tbody tr:hover {

            background-color: #f8f9fa;

        }


        .cell-center {

            text-align: center;

        }


        .cell-right {

            text-align: right;

            white-space: nowrap;

        }


        .cell-wrap {

            white-space: normal;

            min-width: 150px;

        }


        /* ESTADOS */

        .estado-badge {

            display: inline-block;

            padding: 4px 7px;

            border-radius: 4px;

            font-size: 9px;

            font-weight: 700;

            line-height: 1;

            white-space: nowrap;

        }


        .estado-pendiente {

            background-color: #ffc107;

            color: #212529;

        }


        .estado-vencido {

            background-color: #dc3545;

            color: #ffffff;

        }


        .estado-pagado {

            background-color: #28a745;

            color: #ffffff;

        }


        /* CONDICIÓN */

        .condicion-badge {

            display: inline-block;

            padding: 3px 6px;

            border-radius: 3px;

            font-size: 9px;

            font-weight: 600;

            white-space: nowrap;

        }


        .condicion-contado {

            background-color: #e9ecef;

            color: #495057;

        }


        .condicion-credito {

            background-color: #dbeafe;

            color: #1d4ed8;

        }


        /* SALDO */

        .saldo-pendiente {

            font-weight: 700;

            color: #dc3545;

        }


        .saldo-pagado {

            font-weight: 700;

            color: #28a745;

        }


        /* BOTÓN VER */

        .btn-ver-pendiente {

            width: 30px;

            height: 30px;

            padding: 0;

            display: inline-flex;

            align-items: center;

            justify-content: center;

            font-size: 12px;

        }


        /* BOTÓN REPORTE */



        /* MODAL */

        .modal-header {

            padding: 10px 15px;

        }


        .modal-header .modal-title {

            font-size: 16px;

            font-weight: 600;

        }


        .modal-body {

            font-size: 13px;

        }


        .detalle-label {

            font-size: 11px;

            font-weight: 600;

            color: #6c757d;

            text-transform: uppercase;

            margin-bottom: 2px;

        }


        .detalle-value {

            font-size: 13px;

            font-weight: 500;

            color: #343a40;

            margin-bottom: 10px;

        }


        .detalle-total {

            font-size: 16px;

            font-weight: 700;

        }


        .detalle-saldo {

            font-size: 16px;

            font-weight: 700;

            color: #dc3545;

        }


        /* RESPONSIVE */

        @media (max-width: 768px) {

            .resumen-col {

                flex: 100%;

                margin-bottom: 8px;

            }



        }

    </style>

</head>


<body>


<div class="pendientes-container">


    <div class="card">

    <div class="card-header">

        <div class="d-flex justify-content-between align-items-center">

            <h3 class="card-title">
                <i class="fas fa-file-invoice"></i>
                Gestión de Pendientes
            </h3>

            <div>

                <!-- REPORTE GENERAL -->
                <a href="<%= request.getContextPath() %>/reporte_general.jsp?modulo=pendientes"
                   target="_blank"
                   class="btn btn-info btn-sm">
                    <i class="fas fa-file-pdf"></i>
                    Reporte General
                </a>

            </div>

        </div>

    </div>

    <div class="card-body">

    <!-- RESUMEN -->

    <div class="resumen-container">


        <!-- TOTAL FACTURADO -->

        <div class="resumen-col">

            <div class="resumen-card facturado">

                <span class="resumen-label">

                    Total Facturado

                </span>


                <span class="resumen-value"
                      id="totalFacturado">

                    Gs. 0

                </span>


                <i class="fas fa-file-invoice-dollar resumen-icon"></i>

            </div>

        </div>


        <!-- TOTAL PAGADO -->

        <div class="resumen-col">

            <div class="resumen-card pagado">

                <span class="resumen-label">

                    Total Pagado

                </span>


                <span class="resumen-value"
                      id="totalPagado">

                    Gs. 0

                </span>


                <i class="fas fa-money-bill-wave resumen-icon"></i>

            </div>

        </div>


        <!-- SALDO PENDIENTE -->

        <div class="resumen-col">

            <div class="resumen-card saldo">

                <span class="resumen-label">

                    Saldo Pendiente

                </span>


                <span class="resumen-value"
                      id="totalSaldoPendiente">

                    Gs. 0

                </span>


                <i class="fas fa-exclamation-circle resumen-icon"></i>

            </div>

        </div>


    </div>


    <!-- TABLA -->

    <div class="tabla-contenedor">

                <table id="tablaPendientes"
                       class="table table-bordered table-hover">

                    <thead>

                        <tr>

                            <th>ID</th>

                            <th>Factura</th>

                            <th>Cliente</th>

                            <th>Emisión</th>

                            <th>Vencimiento</th>

                            <th>Condición</th>

                            <th>Total</th>

                            <th>Pagado</th>

                            <th>Saldo</th>

                            <th>Estado</th>

                            <th>Acciones</th>

                        </tr>

                    </thead>


                    <tbody id="tbodyPendientes">

                    </tbody>

                </table>

            </div>

    </div>

    </div>


</div>


<!-- MODAL DETALLE -->

<div class="modal fade"
     id="modalDetallePendiente"
     tabindex="-1"
     role="dialog"
     aria-labelledby="modalDetallePendienteLabel"
     aria-hidden="true">


    <div class="modal-dialog modal-lg"
         role="document">


        <div class="modal-content">


            <div class="modal-header">

                <h5 class="modal-title"
                    id="modalDetallePendienteLabel">

                    <i class="fas fa-file-invoice"></i>

                    Detalle de Factura

                </h5>


                <button type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-label="Cerrar">

                    <span aria-hidden="true">

                        &times;

                    </span>

                </button>

            </div>


            <div class="modal-body">


                <div class="row">


                    <!-- FACTURA -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Factura

                        </div>

                        <div class="detalle-value"
                             id="detalleFactura">

                            -

                        </div>

                    </div>


                    <!-- CLIENTE -->

                    <div class="col-md-8">

                        <div class="detalle-label">

                            Cliente

                        </div>

                        <div class="detalle-value"
                             id="detalleCliente">

                            -

                        </div>

                    </div>


                    <!-- FECHA EMISIÓN -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Fecha de emisión

                        </div>

                        <div class="detalle-value"
                             id="detalleFechaEmision">

                            -

                        </div>

                    </div>


                    <!-- VENCIMIENTO -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Fecha de vencimiento

                        </div>

                        <div class="detalle-value"
                             id="detalleFechaVencimiento">

                            -

                        </div>

                    </div>


                    <!-- PERIODO -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Periodo

                        </div>

                        <div class="detalle-value"
                             id="detallePeriodo">

                            -

                        </div>

                    </div>


                    <!-- CONDICIÓN -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Condición

                        </div>

                        <div class="detalle-value"
                             id="detalleCondicion">

                            -

                        </div>

                    </div>


                    <!-- PLAZO -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Plazo de crédito

                        </div>

                        <div class="detalle-value"
                             id="detallePlazo">

                            -

                        </div>

                    </div>


                    <!-- DÍAS VENCIDO -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Días vencido

                        </div>

                        <div class="detalle-value"
                             id="detalleDiasVencido">

                            -

                        </div>

                    </div>


                </div>


                <hr>


                <div class="row">


                    <!-- TOTAL -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Total factura

                        </div>

                        <div class="detalle-total"
                             id="detalleTotal">

                            Gs. 0

                        </div>

                    </div>


                    <!-- PAGADO -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Total pagado

                        </div>

                        <div class="detalle-total text-success"
                             id="detallePagado">

                            Gs. 0

                        </div>

                    </div>


                    <!-- SALDO -->

                    <div class="col-md-4">

                        <div class="detalle-label">

                            Saldo pendiente

                        </div>

                        <div class="detalle-saldo"
                             id="detalleSaldo">

                            Gs. 0

                        </div>

                    </div>


                </div>


                <hr>


                <div class="row">


                    <div class="col-md-6">

                        <div class="detalle-label">

                            Estado de factura

                        </div>

                        <div class="detalle-value"
                             id="detalleEstadoFactura">

                            -

                        </div>

                    </div>


                    <div class="col-md-6">

                        <div class="detalle-label">

                            Estado de pendiente

                        </div>

                        <div class="detalle-value"
                             id="detalleEstadoPendiente">

                            -

                        </div>

                    </div>


                </div>


            </div>


            <div class="modal-footer">

                <a href="#"
                   id="btnVerFacturaPendiente"
                   target="_blank"
                   class="btn btn-primary btn-sm">

                    <i class="fas fa-file-invoice"></i>

                    Ver factura

                </a>

                <button type="button"
                        class="btn btn-secondary btn-sm"
                        data-dismiss="modal">

                    <i class="fas fa-times"></i>

                    Cerrar

                </button>

            </div>


        </div>

    </div>

</div>


<script>

    /* VARIABLE GLOBAL */

    var tablaPendientes = null;


    /* NORMALIZAR TEXTO */

    function normalizarTexto(texto) {

        if (
            texto === null ||
            texto === undefined
        ) {

            return '';

        }


        return String(texto)

            .normalize('NFD')

            .replace(/[\u0300-\u036f]/g, '')

            .toLowerCase()

            .trim();

    }


    /* ESCAPAR HTML */

    function escapeHtml(texto) {

        if (
            texto === null ||
            texto === undefined
        ) {

            return '';

        }


        return String(texto)

            .replace(/&/g, '&amp;')

            .replace(/</g, '&lt;')

            .replace(/>/g, '&gt;')

            .replace(/"/g, '&quot;')

            .replace(/'/g, '&#039;');

    }


    /* FORMATEAR MONTO */

    function formatearMonto(valor) {

        var numero = parseFloat(valor);


        if (
            isNaN(numero)
        ) {

            numero = 0;

        }


        return 'Gs. ' +

            new Intl.NumberFormat(

                'es-PY',

                {

                    maximumFractionDigits: 0,

                    minimumFractionDigits: 0

                }

            ).format(numero);

    }


    /* FORMATEAR FECHA */

    function formatearFecha(fecha) {

        if (
            !fecha
        ) {

            return '-';

        }


        var texto = String(fecha);


        if (
            texto.indexOf('-') !== -1
        ) {

            var partes = texto.split('-');


            if (
                partes.length >= 3
            ) {

                return partes[2].substring(0, 2) +

                    '/' +

                    partes[1] +

                    '/' +

                    partes[0];

            }

        }


        return texto;

    }


    /* OBTENER NOMBRE DEL CLIENTE */

    function obtenerNombreCliente(pendiente) {

        var nombre =

            pendiente.nombreCliente ||

            pendiente.nombre_cliente ||

            '';


        var apellido =

            pendiente.apellidoCliente ||

            pendiente.apellido_cliente ||

            '';


        return (

            String(nombre) +

            ' ' +

            String(apellido)

        ).trim();

    }


    /* OBTENER VALOR */

    function obtenerValor(objeto, camel, snake) {

        if (
            objeto &&
            objeto[camel] !== undefined &&
            objeto[camel] !== null
        ) {

            return objeto[camel];

        }


        if (
            objeto &&
            objeto[snake] !== undefined &&
            objeto[snake] !== null
        ) {

            return objeto[snake];

        }


        return '';

    }


    /* CARGAR PENDIENTES */

    function cargarPendientes() {

        $.ajax({

            url: 'PendienteController',

            type: 'GET',

            dataType: 'json',

            data: {

                accion: 'listar'

            },


            success: function(respuesta) {

                if (
                    !respuesta ||
                    respuesta.success !== true
                ) {

                    mostrarError(

                        respuesta &&

                        respuesta.message

                            ? respuesta.message

                            : 'No se pudieron cargar los pendientes.'

                    );

                    return;

                }


                actualizarTotales(

                    respuesta

                );


                construirTabla(

                    respuesta.data || []

                );

            },


            error: function(xhr) {

                console.error(

                    'Error al cargar pendientes:',

                    xhr.responseText

                );


                mostrarError(

                    'No se pudieron cargar los pendientes.'

                );

            }

        });

    }


    /* ACTUALIZAR TOTALES */

    function actualizarTotales(respuesta) {

        $('#totalFacturado').text(

            formatearMonto(

                respuesta.totalFacturado

            )

        );


        $('#totalPagado').text(

            formatearMonto(

                respuesta.totalPagado

            )

        );


        $('#totalSaldoPendiente').text(

            formatearMonto(

                respuesta.totalSaldoPendiente

            )

        );

    }


    /* CONSTRUIR TABLA */

    function construirTabla(datos) {

        var tbody =

            $('#tbodyPendientes');


        if (
            $.fn.DataTable.isDataTable(
                '#tablaPendientes'
            )
        ) {

            $('#tablaPendientes')

                .DataTable()

                .destroy();

        }


        tbody.empty();


        $.each(

            datos,

            function(index, pendiente) {


                var idFactura =

                    obtenerValor(

                        pendiente,

                        'idFactura',

                        'id_factura'

                    );


                var numeroFactura =

                    obtenerValor(

                        pendiente,

                        'numeroFactura',

                        'numero_factura'

                    );


                var fechaEmision =

                    obtenerValor(

                        pendiente,

                        'fechaEmision',

                        'fecha_emision'

                    );


                var condicion =

                    obtenerValor(

                        pendiente,

                        'condicionVenta',

                        'condicion_venta'

                    );


                var fechaVencimiento =

                    obtenerValor(

                        pendiente,

                        'fechaVencimiento',

                        'fecha_vencimiento'

                    );


                var totalFactura =

                    obtenerValor(

                        pendiente,

                        'totalFactura',

                        'total_factura'

                    );


                var totalPagado =

                    obtenerValor(

                        pendiente,

                        'totalPagado',

                        'total_pagado'

                    );


                var saldoPendiente =

                    obtenerValor(

                        pendiente,

                        'saldoPendiente',

                        'saldo_pendiente'

                    );


                var estadoPendiente =

                    obtenerValor(

                        pendiente,

                        'estadoPendiente',

                        'estado_pendiente'

                    );


                var cliente =

                    obtenerNombreCliente(

                        pendiente

                    );


                var condicionTexto =

                    String(

                        condicion || ''

                    ).toUpperCase();


                var estadoTexto =

                    String(

                        estadoPendiente || ''

                    ).toUpperCase();


                /* ESTADO */

                var estadoBadge = '';


                if (
                    estadoTexto === 'PAGADO'
                ) {

                    estadoBadge =

                        '<span class="estado-badge estado-pagado">' +

                        'PAGADO' +

                        '</span>';

                }

                else if (
                    estadoTexto === 'VENCIDO'
                ) {

                    estadoBadge =

                        '<span class="estado-badge estado-vencido">' +

                        'VENCIDO' +

                        '</span>';

                }

                else {

                    estadoBadge =

                        '<span class="estado-badge estado-pendiente">' +

                        'PENDIENTE' +

                        '</span>';

                }


                /* CONDICIÓN */

                var condicionBadge = '';


                if (
                    condicionTexto === 'CREDITO' ||
                    condicionTexto === 'CRÉDITO'
                ) {

                    condicionBadge =

                        '<span class="condicion-badge condicion-credito">' +

                        'CRÉDITO' +

                        '</span>';

                }

                else {

                    condicionBadge =

                        '<span class="condicion-badge condicion-contado">' +

                        'CONTADO' +

                        '</span>';

                }


                /* COLOR SALDO */

                var claseSaldo =

                    estadoTexto === 'PAGADO'

                        ? 'saldo-pagado'

                        : 'saldo-pendiente';


                /* FILA */

                tbody.append(

                    '<tr>' +

                        '<td class="cell-center">' +

                            escapeHtml(idFactura) +

                        '</td>' +


                        '<td class="cell-center">' +

                            escapeHtml(numeroFactura) +

                        '</td>' +


                        '<td class="cell-wrap">' +

                            escapeHtml(cliente) +

                        '</td>' +


                        '<td class="cell-center">' +

                            formatearFecha(

                                fechaEmision

                            ) +

                        '</td>' +


                        '<td class="cell-center">' +

                            formatearFecha(

                                fechaVencimiento

                            ) +

                        '</td>' +


                        '<td class="cell-center">' +

                            condicionBadge +

                        '</td>' +


                        '<td class="cell-right">' +

                            formatearMonto(

                                totalFactura

                            ) +

                        '</td>' +


                        '<td class="cell-right">' +

                            formatearMonto(

                                totalPagado

                            ) +

                        '</td>' +


                        '<td class="cell-right ' +

                            claseSaldo +

                            '">' +

                            formatearMonto(

                                saldoPendiente

                            ) +

                        '</td>' +


                        '<td class="cell-center">' +

                            estadoBadge +

                        '</td>' +


                        '<td class="cell-center">' +

                            '<button type="button" ' +

                                'class="btn btn-info btn-sm btn-ver-pendiente" ' +

                                'title="Ver detalle" ' +

                                'onclick="verPendiente(' +

                                    Number(idFactura) +

                                ')">' +

                                '<i class="fas fa-eye"></i>' +

                            '</button>' +

                        '</td>' +


                    '</tr>'

                );

            }

        );


        inicializarTablaPendientes();

    }


    /* INICIALIZAR DATATABLE */

    function inicializarTablaPendientes(intentos) {

        if (
            intentos === undefined ||
            intentos === null
        ) {

            intentos = 0;

        }


        var tabla =

            document.getElementById(

                'tablaPendientes'

            );


        if (!tabla) {

            if (
                intentos < 50
            ) {

                setTimeout(

                    function() {

                        inicializarTablaPendientes(

                            intentos + 1

                        );

                    },

                    100

                );

            }

            return;

        }


        if (
            !window.jQuery ||
            !$.fn ||
            !$.fn.DataTable
        ) {

            if (
                intentos < 50
            ) {

                setTimeout(

                    function() {

                        inicializarTablaPendientes(

                            intentos + 1

                        );

                    },

                    100

                );

            }

            return;

        }


        /* BUSCADOR NORMALIZADO */

        if (
            $.fn.dataTable &&
            $.fn.dataTable.ext &&
            $.fn.dataTable.ext.type &&
            $.fn.dataTable.ext.type.search
        ) {

            $.fn.dataTable.ext.type.search.string =

                function(data) {

                    if (
                        data === null ||
                        data === undefined
                    ) {

                        return '';

                    }


                    return normalizarTexto(

                        String(data)

                            .replace(

                                /<[^>]*>/g,

                                ' '

                            )

                    );

                };

        }


        tablaPendientes =

            $('#tablaPendientes').DataTable({

                pageLength: 10,


                lengthMenu: [

                    [10, 25, 50, 100],

                    [10, 25, 50, 100]

                ],


                order: [],


                responsive: true,


                columnDefs: [

                    {

                        orderable: false,

                        searchable: false,

                        targets: 10

                    }

                ],


                language: {

                    decimal: ',',

                    thousands: '.',


                    processing: 'Procesando...',


                    search: 'Buscar:',


                    lengthMenu:

                        'Mostrar _MENU_ registros',


                    info:

                        'Mostrando _START_ a _END_ de _TOTAL_ registros',


                    infoEmpty:

                        'Mostrando 0 a 0 de 0 registros',


                    infoFiltered:

                        '(filtrado de _MAX_ registros)',


                    loadingRecords:

                        'Cargando...',


                    zeroRecords:

                        'No se encontraron registros',


                    emptyTable:

                        'No existen pendientes registrados',


                    paginate: {

                        first: 'Primero',

                        previous: 'Anterior',

                        next: 'Siguiente',

                        last: 'Último'

                    },


                    aria: {

                        sortAscending:

                            ': activar para ordenar la columna de manera ascendente',


                        sortDescending:

                            ': activar para ordenar la columna de manera descendente'

                    }

                }

            });


        /* BUSCADOR PERSONALIZADO */

        var input =

            $('#tablaPendientes_filter input');


        input

            .off(

                'input.pendientesNormalizada'

            )


            .on(

                'input.pendientesNormalizada',

                function() {

                    var valor =

                        normalizarTexto(

                            $(this).val()

                        );


                    tablaPendientes.search(

                        valor

                    ).draw();

                }

            );

    }


    /* VER PENDIENTE */

    function verPendiente(idFactura) {

        if (
            !idFactura
        ) {

            return;

        }


        $.ajax({

            url: 'PendienteController',

            type: 'GET',

            dataType: 'json',

            data: {

                accion: 'buscar',

                idFactura: idFactura

            },


            success: function(respuesta) {

                if (
                    !respuesta ||
                    respuesta.success !== true ||
                    !respuesta.data
                ) {

                    mostrarError(

                        respuesta &&

                        respuesta.message

                            ? respuesta.message

                            : 'No se pudo obtener el detalle.'

                    );

                    return;

                }


                mostrarDetalle(

                    respuesta.data

                );

            },


            error: function(xhr) {

                console.error(

                    'Error al consultar pendiente:',

                    xhr.responseText

                );


                mostrarError(

                    'No se pudo obtener el detalle de la factura.'

                );

            }

        });

    }


    /* MOSTRAR DETALLE */

    function mostrarDetalle(pendiente) {

        var idFactura =

            obtenerValor(

                pendiente,

                'idFactura',

                'id_factura'

            );


        if (idFactura) {

            $('#btnVerFacturaPendiente')
                .attr(
                    'href',
                    'FacturaController?accion=reporteIndividual&id_factura=' +
                    encodeURIComponent(idFactura)
                )
                .show();

        } else {

            $('#btnVerFacturaPendiente').hide();

        }


        var numeroFactura =

            obtenerValor(

                pendiente,

                'numeroFactura',

                'numero_factura'

            );


        var fechaEmision =

            obtenerValor(

                pendiente,

                'fechaEmision',

                'fecha_emision'

            );


        var fechaVencimiento =

            obtenerValor(

                pendiente,

                'fechaVencimiento',

                'fecha_vencimiento'

            );


        var periodo =

            obtenerValor(

                pendiente,

                'periodo',

                'periodo'

            );


        var condicion =

            obtenerValor(

                pendiente,

                'condicionVenta',

                'condicion_venta'

            );


        var plazoCredito =

            obtenerValor(

                pendiente,

                'plazoCredito',

                'plazo_credito'

            );


        var totalFactura =

            obtenerValor(

                pendiente,

                'totalFactura',

                'total_factura'

            );


        var totalPagado =

            obtenerValor(

                pendiente,

                'totalPagado',

                'total_pagado'

            );


        var saldoPendiente =

            obtenerValor(

                pendiente,

                'saldoPendiente',

                'saldo_pendiente'

            );


        var estadoFactura =

            obtenerValor(

                pendiente,

                'estadoFactura',

                'estado_factura'

            );


        var estadoPendiente =

            obtenerValor(

                pendiente,

                'estadoPendiente',

                'estado_pendiente'

            );


        var diasVencido =

            obtenerValor(

                pendiente,

                'diasVencido',

                'dias_vencido'

            );


        $('#detalleFactura').text(

            numeroFactura

                ? numeroFactura

                : idFactura

        );


        $('#detalleCliente').text(

            obtenerNombreCliente(

                pendiente

            ) || '-'

        );


        $('#detalleFechaEmision').text(

            formatearFecha(

                fechaEmision

            )

        );


        $('#detalleFechaVencimiento').text(

            formatearFecha(

                fechaVencimiento

            )

        );


        $('#detallePeriodo').text(

            periodo || '-'

        );


        $('#detalleCondicion').text(

            condicion || '-'

        );


        if (

            String(condicion).toUpperCase() ===

            'CREDITO'

        ) {

            $('#detallePlazo').text(

                plazoCredito

                    ? plazoCredito + ' días'

                    : '-'

            );

        }

        else if (

            String(condicion).toUpperCase() ===

            'CRÉDITO'

        ) {

            $('#detallePlazo').text(

                plazoCredito

                    ? plazoCredito + ' días'

                    : '-'

            );

        }

        else {

            $('#detallePlazo').text(

                'No aplica'

            );

        }


        $('#detalleDiasVencido').text(

            parseInt(

                diasVencido || 0,

                10

            ) > 0

                ? diasVencido + ' días'

                : '0 días'

        );


        $('#detalleTotal').text(

            formatearMonto(

                totalFactura

            )

        );


        $('#detallePagado').text(

            formatearMonto(

                totalPagado

            )

        );


        $('#detalleSaldo').text(

            formatearMonto(

                saldoPendiente

            )

        );


        $('#detalleEstadoFactura').text(

            estadoFactura || '-'

        );


        $('#detalleEstadoPendiente').text(

            estadoPendiente || '-'

        );


        $('#modalDetallePendiente').modal(

            'show'

        );

    }


    /* MENSAJE DE ERROR */

    function mostrarError(mensaje) {

        alert(

            mensaje ||

            'Ocurrió un error.'

        );

    }


    /* INICIALIZAR */

    $(document).ready(

        function() {

            cargarPendientes();

        }

    );

</script>


</body>

</html>