<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section class="content">
    <div class="container-fluid">

        <div class="card">

            <!-- CABECERA -->

            <div class="card-header">

                <h3 class="card-title">
                    <i class="fas fa-concierge-bell"></i>
                    Gestión de Servicios
                </h3>

                <div class="card-tools">

                    <button type="button"
                            class="btn btn-success btn-sm mr-1"
                            onclick="nuevoServicio()">

                        <i class="fas fa-plus"></i>
                        Nuevo Servicio

                    </button>

                    <a href="reporte_general.jsp?modulo=servicios"
                       target="_blank"
                       class="btn btn-info btn-sm">

                        <i class="fas fa-file-pdf"></i>
                        Reporte General

                    </a>

                </div>

            </div>

            <!-- CUERPO -->

            <div class="card-body">

                <div class="table-responsive">

                    <table id="tablaServicios"
                           class="table table-bordered table-striped table-hover">

                        <thead>

                            <tr>

                                <th class="text-center">
                                    ID
                                </th>

                                <th>
                                    Cliente
                                </th>

                                <th>
                                    Tipo
                                </th>

                                <th>
                                    Establecimiento
                                </th>

                                <th>
                                    Barrio
                                </th>

                                <th>
                                    Frecuencia
                                </th>

                                <th>
                                    Tarifa
                                </th>

                                <th class="text-center">
                                    Estado
                                </th>

                                <th class="text-center">
                                    Acciones
                                </th>

                            </tr>

                        </thead>

                        <tbody id="tbodyServicios">
                        </tbody>

                    </table>

                </div>

            </div>

        </div>

    </div>
</section>


<!-- MODAL SERVICIO -->

<div class="modal fade"
     id="modalServicio"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog modal-lg"
         role="document">

        <div class="modal-content">

            <div class="modal-header bg-primary">

                <h4 class="modal-title text-white"
                    id="tituloModalServicio">

                    <i class="fas fa-plus-circle"></i>
                    Nuevo Servicio

                </h4>

                <button type="button"
                        class="close text-white"
                        data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <div class="modal-body">

                <form id="formServicio">

                    <input type="hidden"
                           id="id_servicio"
                           name="id_servicio">


                    <!-- CLIENTE / TIPO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Cliente
                                    <span class="text-danger">*</span>
                                </label>

                                <input type="hidden"
                                       id="id_cliente"
                                       name="id_cliente">

                                <div style="position: relative;">

                                    <input type="text"
                                           id="buscar_cliente"
                                           class="form-control"
                                           autocomplete="off"
                                           placeholder="Buscar por nombre, Cédula o RUC">

                                    <div id="resultadosClientes"
                                         class="list-group"
                                         style="display:none;
                                                position:absolute;
                                                top:100%;
                                                left:0;
                                                right:0;
                                                z-index:1055;
                                                max-height:240px;
                                                overflow-y:auto;
                                                background:#ffffff;
                                                border:1px solid #ced4da;
                                                box-shadow:0 3px 8px rgba(0,0,0,.15);">
                                    </div>

                                </div>

                                <small class="form-text text-muted">
                                    Puede buscar por nombre, Cédula o RUC.
                                </small>

                                <div id="clienteSeleccionado"
                                     class="mt-2"
                                     style="display:none;">
                                </div>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Tipo de Servicio
                                    <span class="text-danger">*</span>
                                </label>

                                <select id="tipo_servicio"
                                        name="tipo_servicio"
                                        class="form-control"
                                        required>

                                    <option value="">
                                        Seleccione un tipo
                                    </option>

                                    <option value="DOMICILIARIO">
                                        Domiciliario
                                    </option>

                                    <option value="COMERCIAL">
                                        Comercial
                                    </option>

                                    <option value="INDUSTRIAL">
                                        Industrial
                                    </option>

                                    <option value="INSTITUCIONAL">
                                        Institucional
                                    </option>

                                    <option value="ESPECIAL">
                                        Especial
                                    </option>

                                </select>

                            </div>

                        </div>

                    </div>


                    <!-- ESTABLECIMIENTO / BARRIO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Establecimiento
                                    <span class="text-danger">*</span>
                                </label>

                                <input type="text"
                                       id="nombre_establecimiento"
                                       name="nombre_establecimiento"
                                       class="form-control"
                                       maxlength="150"
                                       placeholder="Ej.: Casa, Local Comercial, Sucursal 1"
                                       required>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Barrio
                                    <span class="text-danger">*</span>
                                </label>

                                <select id="id_barrio"
                                        name="id_barrio"
                                        class="form-control"
                                        required>

                                    <option value="">
                                        Seleccione un barrio
                                    </option>

                                </select>

                            </div>

                        </div>

                    </div>


                    <!-- DIRECCIÓN -->

                    <div class="form-group">

                        <label>
                            Dirección del Servicio
                            <span class="text-danger">*</span>
                        </label>

                        <input type="text"
                               id="direccion_servicio"
                               name="direccion_servicio"
                               class="form-control"
                               maxlength="255"
                               placeholder="Ingrese la dirección donde se presta el servicio"
                               required>

                    </div>


                    <!-- FRECUENCIA / TARIFA -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Frecuencia
                                    <span class="text-danger">*</span>
                                </label>

                                <select id="frecuencia"
                                        name="frecuencia"
                                        class="form-control"
                                        required>

                                    <option value="MENSUAL">
                                        Mensual
                                    </option>

                                    <option value="QUINCENAL">
                                        Quincenal
                                    </option>

                                    <option value="SEMANAL">
                                        Semanal
                                    </option>

                                    <option value="DIARIA">
                                        Diaria
                                    </option>

                                </select>

                                <small class="form-text text-muted">
                                    Frecuencia correspondiente al servicio.
                                </small>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Tarifa
                                    <span class="text-danger">*</span>
                                </label>

                                <select id="id_tarifa"
                                        name="id_tarifa"
                                        class="form-control"
                                        required>

                                    <option value="">
                                        Seleccione una tarifa
                                    </option>

                                </select>

                                <small class="form-text text-muted">
                                    La tarifa determina el importe utilizado posteriormente para la facturación.
                                </small>

                            </div>

                        </div>

                    </div>


                    <!-- FECHAS -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Fecha de Inicio
                                    <span class="text-danger">*</span>
                                </label>

                                <input type="date"
                                       id="fecha_inicio"
                                       name="fecha_inicio"
                                       class="form-control"
                                       required>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Fecha de Fin
                                </label>

                                <input type="date"
                                       id="fecha_fin"
                                       name="fecha_fin"
                                       class="form-control">

                                <small class="form-text text-muted">
                                    Dejar vacío mientras el servicio continúe activo.
                                </small>

                            </div>

                        </div>

                    </div>


                    <!-- ESTADO -->

                    <div class="form-group">

                        <label>
                            Estado
                            <span class="text-danger">*</span>
                        </label>

                        <select id="estado"
                                name="estado"
                                class="form-control"
                                required>

                            <option value="ACTIVO">
                                ACTIVO
                            </option>

                            <option value="SUSPENDIDO">
                                SUSPENDIDO
                            </option>

                            <option value="CANCELADO">
                                CANCELADO
                            </option>

                        </select>

                    </div>


                    <!-- OBSERVACIONES -->

                    <div class="form-group">

                        <label>
                            Observaciones
                        </label>

                        <textarea id="observaciones"
                                  name="observaciones"
                                  class="form-control"
                                  rows="3"
                                  maxlength="255"
                                  placeholder="Ingrese observaciones del servicio"></textarea>

                    </div>

                </form>

            </div>


            <div class="modal-footer">

                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    <i class="fas fa-times"></i>
                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-primary"
                        onclick="guardarServicio()">

                    <i class="fas fa-save"></i>
                    Guardar

                </button>

            </div>

        </div>

    </div>

</div>


<!-- JAVASCRIPT -->

<script src="<%= request.getContextPath() %>/plugins/sweetalert2/sweetalert2.all.min.js"></script>

<script>

$(document).ready(function () {

    cargarServicios();

    inicializarBuscadorCliente();

});


/* CARGAR SERVICIOS */

function cargarServicios() {

    $.ajax({

        url: "ServicioController",

        type: "POST",

        data: {
            accion: "listar"
        },

        dataType: "json",

        success: function (data) {

            var tbody =
                $("#tbodyServicios");


            if ($.fn.DataTable.isDataTable(
                    "#tablaServicios"
                )) {

                $("#tablaServicios")
                    .DataTable()
                    .destroy();

            }


            tbody.empty();


            $.each(
                data,
                function (i, s) {

                    var fila = "";

                    fila += "<tr>";


                    // ID

                    fila +=
                        "<td class='text-center'>"
                        + s.id_servicio
                        + "</td>";


                    // CLIENTE

                    fila +=
                        "<td>"
                        + escapeHtml(
                            s.nombre_cliente
                          )
                        + "</td>";


                    // TIPO

                    fila +=
                        "<td>"
                        + formatearTipo(
                            s.tipo_servicio
                          )
                        + "</td>";


                    // ESTABLECIMIENTO

                    fila +=
                        "<td>"
                        + escapeHtml(
                            s.nombre_establecimiento
                          )
                        + "</td>";


                    // BARRIO

                    fila +=
                        "<td>"
                        + escapeHtml(
                            s.nombre_barrio
                          )
                        + "</td>";


                    // FRECUENCIA

                    fila +=
                        "<td>"
                        + formatearFrecuencia(
                            s.frecuencia
                          )
                        + "</td>";


                    // TARIFA

                    fila +=
                        "<td>"
                        + escapeHtml(
                            s.nombre_tarifa
                          )
                        + "</td>";


                    // ESTADO

                    fila +=
                        "<td class='text-center'>"
                        + badgeEstado(
                            s.estado
                          )
                        + "</td>";


                    // ACCIONES

                    fila +=
                        "<td class='text-center text-nowrap'>";


                    // MODIFICAR

                    fila +=
                        "<button type='button' "
                        + "class='btn btn-warning btn-sm mr-1' "
                        + "title='Modificar' "
                        + "onclick='editarServicio("
                        + s.id_servicio
                        + ")'>"
                        + "<i class='fas fa-edit'></i>"
                        + "</button>";


                    // VER INDIVIDUAL
                    // IMPORTANTE: OJO, NO PDF

                    fila +=
                        "<a href='ServicioController?accion=reporteIndividual&id="
                        + s.id_servicio
                        + "' "
                        + "target='_blank' "
                        + "class='btn btn-info btn-sm mr-1' "
                        + "title='Ver individual'>"
                        + "<i class='fas fa-eye'></i>"
                        + "</a>";


                    // ESTADO ACTIVO

                    if (s.estado === "ACTIVO") {

                        fila +=
                            "<button type='button' "
                            + "class='btn btn-secondary btn-sm mr-1' "
                            + "title='Suspender' "
                            + "onclick='suspenderServicio("
                            + s.id_servicio
                            + ")'>"
                            + "<i class='fas fa-pause'></i>"
                            + "</button>";


                        fila +=
                            "<button type='button' "
                            + "class='btn btn-danger btn-sm' "
                            + "title='Cancelar' "
                            + "onclick='cancelarServicio("
                            + s.id_servicio
                            + ")'>"
                            + "<i class='fas fa-times'></i>"
                            + "</button>";

                    }


                    // ESTADO SUSPENDIDO

                    else if (
                        s.estado === "SUSPENDIDO"
                    ) {

                        fila +=
                            "<button type='button' "
                            + "class='btn btn-success btn-sm mr-1' "
                            + "title='Activar' "
                            + "onclick='activarServicio("
                            + s.id_servicio
                            + ")'>"
                            + "<i class='fas fa-check'></i>"
                            + "</button>";


                        fila +=
                            "<button type='button' "
                            + "class='btn btn-danger btn-sm' "
                            + "title='Cancelar' "
                            + "onclick='cancelarServicio("
                            + s.id_servicio
                            + ")'>"
                            + "<i class='fas fa-times'></i>"
                            + "</button>";

                    }


                    // CANCELADO

                    else {

                        fila +=
                            "<span class='badge badge-dark'>"
                            + "Cancelado"
                            + "</span>";

                    }


                    fila += "</td>";

                    fila += "</tr>";


                    tbody.append(fila);

                }
            );


            $("#tablaServicios").DataTable({

                destroy: true,

                responsive: false,

                autoWidth: false,

                pageLength: 10,

                order: [
                    [0, "asc"]
                ],

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

                    emptyTable:
                        "No hay datos disponibles en la tabla",

                    infoFiltered:
                        "(filtrado de _MAX_ registros totales)",

                    loadingRecords:
                        "Cargando...",

                    processing:
                        "Procesando...",

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

                }

            });

        },


        error: function (xhr) {

            console.error(
                "Error al cargar servicios:",
                xhr.responseText
            );


            Swal.fire(
                "Error",
                "No se pudieron cargar los servicios.",
                "error"
            );

        }

    });

}


/* NUEVO SERVICIO */

function nuevoServicio() {

    $("#formServicio")[0].reset();


    $("#id_servicio")
        .val("");


    $("#tituloModalServicio")
        .html(
            '<i class="fas fa-plus-circle"></i> Nuevo Servicio'
        );


    $("#estado")
        .val("ACTIVO");


    $("#frecuencia")
        .val("MENSUAL");


    $("#fecha_inicio")
        .val(obtenerFechaActual());


    $("#fecha_fin")
        .val("");


    limpiarBuscadorCliente();

    cargarBarrios();

    cargarTarifas();


    $("#modalServicio")
        .modal("show");

}


/* BUSCADOR DE CLIENTES */

var temporizadorBusquedaCliente = null;


function inicializarBuscadorCliente() {

    $("#buscar_cliente")
        .off("input")
        .on("input", function () {

            var termino =
                $(this).val().trim();


            $("#id_cliente")
                .val("");


            $("#clienteSeleccionado")
                .hide()
                .html("");


            clearTimeout(
                temporizadorBusquedaCliente
            );


            if (termino.length < 2) {

                ocultarResultadosClientes();

                return;

            }


            temporizadorBusquedaCliente =
                setTimeout(
                    function () {

                        buscarClientes(
                            termino
                        );

                    },
                    250
                );

        });


    $(document)
        .off("click.buscadorCliente")
        .on(
            "click.buscadorCliente",
            function (e) {

                if (
                    !$(e.target).closest(
                        "#buscar_cliente, #resultadosClientes"
                    ).length
                ) {

                    ocultarResultadosClientes();

                }

            }
        );

}


function buscarClientes(
        termino,
        idCliente,
        callback) {

    $.ajax({

        url:
            "ServicioController",

        type:
            "POST",

        data: {

            accion:
                "buscarClientes",

            termino:
                termino || "",

            id_cliente:
                idCliente || ""

        },

        dataType:
            "json",

        success: function (data) {

            if (
                typeof callback ===
                "function"
            ) {

                callback(data);

                return;

            }


            mostrarResultadosClientes(
                data
            );

        },

        error: function (xhr) {

            console.error(
                "Error al buscar clientes:",
                xhr.responseText
            );


            ocultarResultadosClientes();


            Swal.fire(
                "Error",
                "No se pudo realizar la búsqueda de clientes.",
                "error"
            );

        }

    });

}


function mostrarResultadosClientes(
        data) {

    var contenedor =
        $("#resultadosClientes");


    contenedor.empty();


    if (
        !data ||
        data.length === 0
    ) {

        contenedor.append(
            '<div class="list-group-item text-muted">' +
                '<i class="fas fa-info-circle mr-1"></i>' +
                'No se encontraron clientes.' +
            '</div>'
        );


        contenedor.show();

        return;

    }


    $.each(
        data,
        function (i, cliente) {

            var cedula =
                cliente.cedula
                || "Sin Cédula";


            var ruc =
                cliente.ruc
                || "Sin RUC";


            var item =
                $('<button type="button" ' +
                    'class="list-group-item list-group-item-action">' +
                    '<div class="font-weight-bold"></div>' +
                    '<small class="text-muted"></small>' +
                  '</button>');


            item.find("div")
                .text(
                    cliente.nombre
                );


            item.find("small")
                .html(
                    "CI: " +
                    escapeHtml(cedula) +
                    " &nbsp; | &nbsp; RUC: " +
                    escapeHtml(ruc)
                );


            item.on(
                "click",
                function () {

                    seleccionarCliente(
                        cliente
                    );

                }
            );


            contenedor.append(
                item
            );

        }
    );


    contenedor.show();

}


function seleccionarCliente(
        cliente) {

    $("#id_cliente")
        .val(
            cliente.id_cliente
        );


    $("#buscar_cliente")
        .val(
            cliente.nombre
        );


    var cedula =
        cliente.cedula
        || "Sin Cédula";


    var ruc =
        cliente.ruc
        || "Sin RUC";


    $("#clienteSeleccionado")
        .html(
            '<span class="badge badge-success p-2">' +
                '<i class="fas fa-check mr-1"></i>' +
                'Cliente seleccionado: ' +
                escapeHtml(cliente.nombre) +
            '</span>' +
            '<div class="small text-muted mt-1">' +
                'CI: ' +
                escapeHtml(cedula) +
                ' &nbsp; | &nbsp; RUC: ' +
                escapeHtml(ruc) +
            '</div>'
        )
        .show();


    ocultarResultadosClientes();

}


function cargarClientePorId(
        idCliente) {

    buscarClientes(
        "",
        idCliente,
        function (data) {

            if (
                data &&
                data.length > 0
            ) {

                seleccionarCliente(
                    data[0]
                );

            } else {

                $("#id_cliente")
                    .val(idCliente);

            }

        }
    );

}


function limpiarBuscadorCliente() {

    $("#id_cliente")
        .val("");


    $("#buscar_cliente")
        .val("");


    $("#clienteSeleccionado")
        .hide()
        .html("");


    ocultarResultadosClientes();

}


function ocultarResultadosClientes() {

    $("#resultadosClientes")
        .hide()
        .empty();

}


/* CARGAR BARRIOS */

function cargarBarrios(callback) {

    $.ajax({

        url: "ServicioController",

        type: "POST",

        data: {
            accion: "listarBarrios"
        },

        dataType: "json",

        success: function (data) {

            var select =
                $("#id_barrio");


            select.empty();


            select.append(
                '<option value="">'
                + 'Seleccione un barrio'
                + '</option>'
            );


            $.each(
                data,
                function (i, b) {

                    select.append(
                        '<option value="'
                        + b.id_barrio
                        + '">'
                        + escapeHtml(b.nombre)
                        + '</option>'
                    );

                }
            );


            if (typeof callback === "function") {

                callback();

            }

        },

        error: function (xhr) {

            console.error(
                "Error al cargar barrios:",
                xhr.responseText
            );


            Swal.fire(
                "Error",
                "No se pudieron cargar los barrios.",
                "error"
            );

        }

    });

}


/* CARGAR TARIFAS */

function cargarTarifas(callback) {

    $.ajax({

        url: "ServicioController",

        type: "POST",

        data: {
            accion: "listarTarifas"
        },

        dataType: "json",

        success: function (data) {

            var select =
                $("#id_tarifa");


            select.empty();


            select.append(
                '<option value="">'
                + 'Seleccione una tarifa'
                + '</option>'
            );


            $.each(
                data,
                function (i, t) {

                    var monto =
                        formatearMonto(
                            t.monto
                        );


                    var periodicidad =
                        t.periodicidad
                        ? " - "
                          + formatearFrecuencia(
                                t.periodicidad
                            )
                        : "";


                    select.append(
                        '<option value="'
                        + t.id_tarifa
                        + '">'
                        + escapeHtml(t.nombre)
                        + " - Gs. "
                        + monto
                        + periodicidad
                        + '</option>'
                    );

                }
            );


            if (typeof callback === "function") {

                callback();

            }

        },

        error: function (xhr) {

            console.error(
                "Error al cargar tarifas:",
                xhr.responseText
            );


            Swal.fire(
                "Error",
                "No se pudieron cargar las tarifas.",
                "error"
            );

        }

    });

}


/* EDITAR SERVICIO */

function editarServicio(id) {

    $.ajax({

        url: "ServicioController",

        type: "POST",

        data: {

            accion: "buscar",

            id: id

        },

        dataType: "json",

        success: function (servicio) {

            if (
                !servicio ||
                !servicio.id_servicio
            ) {

                Swal.fire(
                    "Error",
                    "No se encontró el servicio seleccionado.",
                    "error"
                );

                return;

            }


            $("#id_servicio")
                .val(
                    servicio.id_servicio
                );


            $("#tipo_servicio")
                .val(
                    servicio.tipo_servicio
                );


            $("#nombre_establecimiento")
                .val(
                    servicio.nombre_establecimiento || ""
                );


            $("#direccion_servicio")
                .val(
                    servicio.direccion_servicio || ""
                );


            $("#frecuencia")
                .val(
                    servicio.frecuencia || "MENSUAL"
                );


            $("#fecha_inicio")
                .val(
                    servicio.fecha_inicio || ""
                );


            $("#fecha_fin")
                .val(
                    servicio.fecha_fin || ""
                );


            $("#estado")
                .val(
                    servicio.estado || "ACTIVO"
                );


            $("#observaciones")
                .val(
                    servicio.observaciones || ""
                );


            $("#tituloModalServicio")
                .html(
                    '<i class="fas fa-edit"></i> Modificar Servicio'
                );


            cargarClientePorId(
                servicio.id_cliente
            );


            cargarBarrios(
                function () {

                    $("#id_barrio")
                        .val(
                            servicio.id_barrio
                        );

                }
            );


            cargarTarifas(
                function () {

                    $("#id_tarifa")
                        .val(
                            servicio.id_tarifa
                        );

                }
            );


            $("#modalServicio")
                .modal("show");

        },

        error: function (xhr) {

            console.error(
                "Error:",
                xhr.responseText
            );


            Swal.fire(
                "Error",
                "No se pudo obtener el servicio.",
                "error"
            );

        }

    });

}


/* GUARDAR SERVICIO */

function guardarServicio() {

    var formulario =
        $("#formServicio")[0];


    var idCliente =
        $("#id_cliente").val();


    if (!idCliente) {

        Swal.fire(
            "Atención",
            "Debe buscar y seleccionar un cliente.",
            "warning"
        );


        $("#buscar_cliente").focus();

        return;

    }


    if (!formulario.checkValidity()) {

        formulario.reportValidity();

        return;

    }


    var id =
        $("#id_servicio")
            .val();


    var accion =
        id === ""
        ? "insertar"
        : "actualizar";


    var datos =
        $("#formServicio")
            .serialize();


    datos +=
        "&accion="
        + accion;


    $.ajax({

        url: "ServicioController",

        type: "POST",

        data: datos,

        success: function (respuesta) {

            if ($.trim(respuesta) === "ok") {

                $("#modalServicio")
                    .modal("hide");


                Swal.fire({

                    icon: "success",

                    title: "Correcto",

                    text:
                        accion === "insertar"
                        ? "Servicio registrado correctamente."
                        : "Servicio actualizado correctamente.",

                    timer: 1500,

                    showConfirmButton: false

                });


                cargarServicios();

            } else {

                Swal.fire(
                    "Error",
                    respuesta,
                    "error"
                );

            }

        },

        error: function (xhr) {

            console.error(
                "Error al guardar:",
                xhr.responseText
            );


            Swal.fire(
                "Error",
                xhr.responseText ||
                "No se pudo guardar el servicio.",
                "error"
            );

        }

    });

}


/* CANCELAR SERVICIO */

function cancelarServicio(id) {

    Swal.fire({

        title: "¿Cancelar servicio?",

        text:
            "El servicio quedará registrado como CANCELADO.",

        icon: "warning",

        showCancelButton: true,

        confirmButtonText: "Sí, cancelar",

        cancelButtonText: "No"

    }).then(function (result) {

        if (!result.isConfirmed) {

            return;

        }


        $.ajax({

            url: "ServicioController",

            type: "POST",

            data: {

                accion: "eliminar",

                id: id

            },

            success: function (respuesta) {

                if ($.trim(respuesta) === "ok") {

                    Swal.fire({

                        icon: "success",

                        title: "Correcto",

                        text:
                            "Servicio cancelado correctamente.",

                        timer: 1500,

                        showConfirmButton: false

                    });


                    cargarServicios();

                } else {

                    Swal.fire(
                        "Error",
                        respuesta,
                        "error"
                    );

                }

            },

            error: function (xhr) {

                Swal.fire(
                    "Error",
                    xhr.responseText ||
                    "No se pudo cancelar el servicio.",
                    "error"
                );

            }

        });

    });

}


/* SUSPENDER SERVICIO */

function suspenderServicio(id) {

    Swal.fire({

        title: "¿Suspender servicio?",

        text:
            "El servicio quedará temporalmente suspendido.",

        icon: "warning",

        showCancelButton: true,

        confirmButtonText: "Sí, suspender",

        cancelButtonText: "No"

    }).then(function (result) {

        if (!result.isConfirmed) {

            return;

        }


        $.ajax({

            url: "ServicioController",

            type: "POST",

            data: {

                accion: "suspender",

                id: id

            },

            success: function (respuesta) {

                if ($.trim(respuesta) === "ok") {

                    Swal.fire({

                        icon: "success",

                        title: "Correcto",

                        text:
                            "Servicio suspendido correctamente.",

                        timer: 1500,

                        showConfirmButton: false

                    });


                    cargarServicios();

                } else {

                    Swal.fire(
                        "Error",
                        respuesta,
                        "error"
                    );

                }

            },

            error: function (xhr) {

                Swal.fire(
                    "Error",
                    xhr.responseText ||
                    "No se pudo suspender el servicio.",
                    "error"
                );

            }

        });

    });

}


/* ACTIVAR SERVICIO */

function activarServicio(id) {

    Swal.fire({

        title: "¿Activar servicio?",

        text:
            "El servicio volverá a estar ACTIVO.",

        icon: "question",

        showCancelButton: true,

        confirmButtonText: "Sí, activar",

        cancelButtonText: "No"

    }).then(function (result) {

        if (!result.isConfirmed) {

            return;

        }


        $.ajax({

            url: "ServicioController",

            type: "POST",

            data: {

                accion: "activar",

                id: id

            },

            success: function (respuesta) {

                if ($.trim(respuesta) === "ok") {

                    Swal.fire({

                        icon: "success",

                        title: "Correcto",

                        text:
                            "Servicio activado correctamente.",

                        timer: 1500,

                        showConfirmButton: false

                    });


                    cargarServicios();

                } else {

                    Swal.fire(
                        "Error",
                        respuesta,
                        "error"
                    );

                }

            },

            error: function (xhr) {

                Swal.fire(
                    "Error",
                    xhr.responseText ||
                    "No se pudo activar el servicio.",
                    "error"
                );

            }

        });

    });

}


/* FORMATEAR TIPO */

function formatearTipo(valor) {

    if (!valor) {

        return "";

    }


    var mapa = {

        "DOMICILIARIO": "Domiciliario",

        "COMERCIAL": "Comercial",

        "INDUSTRIAL": "Industrial",

        "INSTITUCIONAL": "Institucional",

        "ESPECIAL": "Especial"

    };


    return mapa[valor] ||
           valor;

}


/* FORMATEAR FRECUENCIA */

function formatearFrecuencia(valor) {

    if (!valor) {

        return "";

    }


    var mapa = {

        "MENSUAL": "Mensual",

        "QUINCENAL": "Quincenal",

        "SEMANAL": "Semanal",

        "DIARIA": "Diaria",

        "TRIMESTRAL": "Trimestral",

        "SEMESTRAL": "Semestral",

        "ANUAL": "Anual",

        "UNICO": "Único"

    };


    return mapa[valor] ||
           valor;

}


/* FORMATEAR MONTO */

function formatearMonto(valor) {

    var numero =
        parseFloat(valor);


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


/* BADGE ESTADO */

function badgeEstado(estado) {

    if (estado === "ACTIVO") {

        return '<span class="badge badge-success">'
               + "ACTIVO"
               + "</span>";

    }


    if (estado === "SUSPENDIDO") {

        return '<span class="badge badge-warning">'
               + "SUSPENDIDO"
               + "</span>";

    }


    return '<span class="badge badge-dark">'
           + "CANCELADO"
           + "</span>";

}


/* FECHA ACTUAL */

function obtenerFechaActual() {

    var hoy =
        new Date();


    var anio =
        hoy.getFullYear();


    var mes =
        String(
            hoy.getMonth() + 1
        ).padStart(2, "0");


    var dia =
        String(
            hoy.getDate()
        ).padStart(2, "0");


    return anio
           + "-"
           + mes
           + "-"
           + dia;

}


/* ESCAPE HTML */

function escapeHtml(valor) {

    if (
        valor === null ||
        valor === undefined
    ) {

        return "";

    }


    return String(valor)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");

}

</script>