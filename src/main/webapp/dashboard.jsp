<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.config.Permisos"%>

<%
    // VALIDACIÓN DE SESIÓN

    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String usuarioSesion = String.valueOf(
            session.getAttribute("usuario")
    );

    Object rolSesion = session.getAttribute("rol");
%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Inicio | JMQ SYSTEM</title>

    <link rel="icon"
          type="image/x-icon"
          href="favicon.ico">


    <!-- ADMINLTE -->

    <link rel="stylesheet"
          href="dist/css/adminlte.min.css">


    <!-- FONT AWESOME -->

    <link rel="stylesheet"
          href="plugins/fontawesome-free/css/all.min.css">


    <!-- DATATABLES -->

    <link rel="stylesheet"
          href="plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">

    <link rel="stylesheet"
          href="plugins/datatables-responsive/css/responsive.bootstrap4.min.css">


    <!-- DATATABLES BUTTONS -->

    <link rel="stylesheet"
          href="plugins/datatables-buttons/css/buttons.bootstrap4.min.css">

    <link rel="stylesheet"
          href="plugins/sweetalert2/sweetalert2.min.css">


    <!-- ESTILOS PROPIOS -->

    <style>

        body {
            font-size: 14px;
        }

        .content-wrapper {
            min-height: calc(100vh - 57px);
        }

        #contenidoPrincipal {
            min-height: 500px;
        }

        .brand-link {
            text-align: center;
        }

        .brand-text {
            font-weight: 600;
        }

        .nav-header {
            font-size: 11px;
            font-weight: bold;
        }

        .nav-link {
            cursor: pointer;
        }

        .table-responsive {
            overflow-x: auto;
        }

        .loading-container {
            text-align: center;
            padding: 60px 20px;
        }

        /* Etiquetas de estado iguales en todas las tablas */
        #contenidoPrincipal table .badge {
            text-transform: uppercase;
            font-size: 75%;
        }

        /* Buscador de las tablas */
        #contenidoPrincipal .dataTables_filter input {
            width: 180px;
            max-width: 100%;
        }

        /* Botones de exportación de las tablas */
        #contenidoPrincipal .dt-buttons .btn {
            font-size: 13px;
        }

        .loading-container i {
            font-size: 40px;
            margin-bottom: 15px;
        }

    </style>

</head>


<body class="hold-transition sidebar-mini layout-fixed">


<div class="wrapper">


    <!-- NAVBAR -->

    <nav class="main-header navbar navbar-expand navbar-white navbar-light">


        <ul class="navbar-nav">


            <!-- BOTÓN SIDEBAR -->

            <li class="nav-item">

                <a class="nav-link"
                   data-widget="pushmenu"
                   href="#"
                   role="button">

                    <i class="fas fa-bars"></i>

                </a>

            </li>


            <!-- INICIO -->

            <li class="nav-item d-none d-sm-inline-block">

                <a href="#"
                   class="nav-link"
                   data-url="DashboardController">

                    <i class="fas fa-home"></i>

                    Inicio

                </a>

            </li>


        </ul>


        <!-- USUARIO -->

        <ul class="navbar-nav ml-auto">

            <li class="nav-item">

                <span class="nav-link">

                    <i class="fas fa-user-circle"></i>

                    <%= usuarioSesion %>

                </span>

            </li>

        </ul>


    </nav>


    <!-- SIDEBAR -->

    <aside class="main-sidebar sidebar-dark-primary elevation-4">


        <!-- LOGO -->

        <a href="#"
           class="brand-link"
           data-url="DashboardController">

            <span class="brand-text">

                JMQ SYSTEM

            </span>

        </a>


        <!-- SIDEBAR -->

        <div class="sidebar">


            <!-- USUARIO -->

            <div class="user-panel mt-3 pb-3 mb-3 d-flex">


                <div class="image">

                    <i class="fas fa-user-circle fa-2x text-white"></i>

                </div>


                <div class="info">

                    <a href="#"
                       class="d-block">

                        <%= usuarioSesion %>

                    </a>

                    <small class="d-block text-muted">

                        <%= rolSesion == null ? "" : rolSesion %>

                    </small>

                </div>


            </div>


            <!-- MENÚ -->

            <nav class="mt-2">


                <ul class="nav nav-pills nav-sidebar flex-column"
                    data-widget="treeview"
                    role="menu"
                    data-accordion="false">


                    <!-- INICIO -->

                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="DashboardController">

                            <i class="nav-icon fas fa-home"></i>

                            <p>
                                Inicio
                            </p>

                        </a>

                    </li>


                    <!-- REFERENCIALES -->

                    <% if (Permisos.puedeVerAlguno(rolSesion, "usuarios", "roles", "personal", "cargo", "clientes", "barrio", "tipos_residuos", "vehiculos", "modelo", "rutas")) { %>
                    <li class="nav-header">

                        REFERENCIALES

                    </li>
                    <% } %>


                    <!-- USUARIOS -->

                    <% if (Permisos.puedeVer(rolSesion, "usuarios")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="usuarios.jsp">

                            <i class="nav-icon fas fa-users-cog"></i>

                            <p>
                                Usuarios
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- ROLES -->

                    <% if (Permisos.puedeVer(rolSesion, "roles")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="roles.jsp">

                            <i class="nav-icon fas fa-user-tag"></i>

                            <p>
                                Roles
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- PERSONAL -->

                    <% if (Permisos.puedeVer(rolSesion, "personal")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="personal.jsp">

                            <i class="nav-icon fas fa-user-tie"></i>

                            <p>
                                Personal
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- CARGOS -->

                    <% if (Permisos.puedeVer(rolSesion, "cargo")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="cargo.jsp">

                            <i class="nav-icon fas fa-id-badge"></i>

                            <p>
                                Cargos
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- CLIENTES -->

                    <% if (Permisos.puedeVer(rolSesion, "clientes")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="clientes.jsp">

                            <i class="nav-icon fas fa-user"></i>

                            <p>
                                Clientes
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- BARRIOS -->

                    <% if (Permisos.puedeVer(rolSesion, "barrio")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="barrio.jsp">

                            <i class="nav-icon fas fa-map-marker-alt"></i>

                            <p>
                                Barrios
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- TIPOS DE RESIDUOS -->

                    <% if (Permisos.puedeVer(rolSesion, "tipos_residuos")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="tipos_residuos.jsp">

                            <i class="nav-icon fas fa-recycle"></i>

                            <p>
                                Tipos de Residuos
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- VEHÍCULOS -->

                    <% if (Permisos.puedeVer(rolSesion, "vehiculos")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="vehiculos.jsp">

                            <i class="nav-icon fas fa-truck"></i>

                            <p>
                                Vehículos
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- MODELOS -->

                    <% if (Permisos.puedeVer(rolSesion, "modelo")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="modelo.jsp">

                            <i class="nav-icon fas fa-car"></i>

                            <p>
                                Modelos
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- RUTAS -->

                    <% if (Permisos.puedeVer(rolSesion, "rutas")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="rutas.jsp">

                            <i class="nav-icon fas fa-route"></i>

                            <p>
                                Rutas
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- OPERACIONES -->

                    <% if (Permisos.puedeVerAlguno(rolSesion, "servicios", "asignaciones", "recolecciones", "clasificacion_residuos")) { %>
                    <li class="nav-header">

                        OPERACIONES

                    </li>
                    <% } %>


                    <!-- SERVICIOS -->

                    <% if (Permisos.puedeVer(rolSesion, "servicios")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="servicios.jsp">

                            <i class="nav-icon fas fa-concierge-bell"></i>

                            <p>
                                Servicios
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- ASIGNACIONES -->

                    <% if (Permisos.puedeVer(rolSesion, "asignaciones")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="asignaciones.jsp">

                            <i class="nav-icon fas fa-tasks"></i>

                            <p>
                                Asignaciones
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- RECOLECCIONES -->

                    <% if (Permisos.puedeVer(rolSesion, "recolecciones")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="recolecciones.jsp">

                            <i class="nav-icon fas fa-trash-alt"></i>

                            <p>
                                Recolecciones
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- CLASIFICACIÓN DE RESIDUOS -->

                    <% if (Permisos.puedeVer(rolSesion, "clasificacion_residuos")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="clasificacion_residuos.jsp">

                            <i class="nav-icon fas fa-recycle"></i>

                            <p>
                                Clasificación de Residuos
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- GESTIÓN FINANCIERA -->

                    <% if (Permisos.puedeVerAlguno(rolSesion, "tarifas", "facturacion", "pagos", "pendientes")) { %>
                    <li class="nav-header">

                        GESTIÓN FINANCIERA

                    </li>
                    <% } %>


                    <!-- TARIFAS -->

                    <% if (Permisos.puedeVer(rolSesion, "tarifas")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="tarifas.jsp">

                            <i class="nav-icon fas fa-tags"></i>

                            <p>
                                Tarifas
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- FACTURACIÓN -->

                    <% if (Permisos.puedeVer(rolSesion, "facturacion")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="facturacion.jsp">

                            <i class="nav-icon fas fa-file-invoice-dollar"></i>

                            <p>
                                Facturación
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- PAGOS -->

                    <% if (Permisos.puedeVer(rolSesion, "pagos")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="pagos.jsp">

                            <i class="nav-icon fas fa-money-bill-wave"></i>

                            <p>
                                Pagos
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- PENDIENTES -->

                    <% if (Permisos.puedeVer(rolSesion, "pendientes")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="pendientes.jsp">

                            <i class="nav-icon fas fa-file-invoice"></i>

                            <p>
                                Pendientes
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- INFORMES -->

                    <% if (Permisos.puedeVerAlguno(rolSesion, "informes")) { %>
                    <li class="nav-header">

                        INFORMES

                    </li>
                    <% } %>


                    <!-- INFORMES -->

                    <% if (Permisos.puedeVer(rolSesion, "informes")) { %>
                    <li class="nav-item">

                        <a href="#"
                           class="nav-link"
                           data-url="informes.jsp">

                            <i class="nav-icon fas fa-chart-bar"></i>

                            <p>
                                Informes
                            </p>

                        </a>

                    </li>
                    <% } %>


                    <!-- SISTEMA -->

                    <li class="nav-header">

                        SISTEMA

                    </li>


                    <!-- CERRAR SESIÓN -->

                    <li class="nav-item">

                        <a href="LogoutController"
                           class="nav-link">

                            <i class="nav-icon fas fa-sign-out-alt"></i>

                            <p>
                                Cerrar Sesión
                            </p>

                        </a>

                    </li>


                </ul>


            </nav>


        </div>


    </aside>


    <!-- CONTENIDO PRINCIPAL -->

    <div class="content-wrapper">


        <section class="content pt-3">


            <div id="contenidoPrincipal">


                <div class="loading-container">


                    <i class="fas fa-spinner fa-spin"></i>


                    <p>
                        Cargando sistema...
                    </p>


                </div>


            </div>


        </section>


    </div>


    <!-- FOOTER -->

    <footer class="main-footer">


        <strong>

            JMQ SYSTEM

        </strong>


        &nbsp;|&nbsp;


        Gestión de Residuos de la Ciudad de Villa Elisa – Paraguay


        <div class="float-right d-none d-sm-inline-block">

            <b>Sistema</b> 1.0

        </div>


    </footer>


</div>


<!-- JAVASCRIPT -->


<script src="plugins/jquery/jquery.min.js"></script>


<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>


<script src="dist/js/adminlte.min.js"></script>


<!-- DATATABLES -->

<script src="plugins/datatables/jquery.dataTables.min.js"></script>

<script src="plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>


<script src="plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>

<script src="plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>


<!-- DATATABLES BUTTONS -->

<script src="plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>

<script src="plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>


<script src="plugins/jszip/jszip.min.js"></script>


<script src="plugins/pdfmake/pdfmake.min.js"></script>


<script src="plugins/pdfmake/vfs_fonts.js"></script>


<script src="plugins/datatables-buttons/js/buttons.html5.min.js"></script>


<script src="plugins/datatables-buttons/js/buttons.print.min.js"></script>


<!-- SweetAlert2: se carga una sola vez para todos los módulos -->

<script src="plugins/sweetalert2/sweetalert2.all.min.js"></script>


<!-- CONFIGURACIÓN COMÚN DE TODAS LAS TABLAS -->

<script>

    // Todas las tablas del sistema tienen la misma estructura:
    // "Mostrar N registros" | botones de exportación | Buscar
    // tabla
    // información de registros | paginación

    (function () {

        // No se exporta la columna "Acciones"
        function columnasExportables(indice, datos, celda) {
            return $(celda).text().trim().toLowerCase() !== "acciones";
        }

        var opcionesExportar = {
            columns: columnasExportables
        };

        $.extend(true, $.fn.dataTable.defaults, {

            dom:
                "<'row align-items-center mb-2'" +
                    "<'col-sm-12 col-md-3'l>" +
                    "<'col-sm-12 col-md-5 text-center'B>" +
                    "<'col-sm-12 col-md-4'f>" +
                ">" +
                "<'row'<'col-sm-12'tr>>" +
                "<'row'" +
                    "<'col-sm-12 col-md-5'i>" +
                    "<'col-sm-12 col-md-7'p>" +
                ">",

            lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Todos"]],

            pageLength: 10,

            buttons: [
                { extend: "copy",  text: "Copiar", exportOptions: opcionesExportar },
                { extend: "csv",   text: "CSV",    exportOptions: opcionesExportar },
                { extend: "excel", text: "Excel",  exportOptions: opcionesExportar },
                { extend: "pdf",   text: "PDF",    exportOptions: opcionesExportar,
                  orientation: "landscape" },
                { extend: "print", text: "Imprimir", exportOptions: opcionesExportar }
            ],

            language: {
                lengthMenu: "Mostrar _MENU_ registros",
                search: "Buscar:",
                info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
                infoEmpty: "Mostrando 0 a 0 de 0 registros",
                infoFiltered: "(filtrado de _MAX_ registros)",
                zeroRecords: "No se encontraron registros",
                emptyTable: "No hay registros",
                paginate: {
                    first: "Primero",
                    last: "Último",
                    next: "Siguiente",
                    previous: "Anterior"
                },
                buttons: {
                    copyTitle: "Copiado",
                    copySuccess: {
                        _: "%d registros copiados",
                        1: "1 registro copiado"
                    }
                }
            }
        });

    })();

</script>


<!-- JAVASCRIPT DE NAVEGACIÓN -->

<script>

    // CARGAR CONTENIDO

    // Si la sesión venció, el filtro responde 401 y se vuelve al login
    $(document).ajaxError(function(event, xhr) {

        if (xhr.status === 401) {

            window.location.href = "login.jsp";

        }

    });


    function cargarContenido(url) {

        if (!url) {

            return;

        }


        $("#contenidoPrincipal").html(

            '<div class="loading-container">' +

                '<i class="fas fa-spinner fa-spin"></i>' +

                '<p>Cargando...</p>' +

            '</div>'

        );


        $.ajax({

            url: url,

            type: "GET",


            success: function(data) {


                $("#contenidoPrincipal").html(data);


                inicializarContenido();


            },


            error: function(xhr) {


                if (xhr.status === 403) {

                    $("#contenidoPrincipal").html(

                        '<div class="alert alert-warning">' +

                            '<h5><i class="fas fa-lock"></i> Acceso restringido</h5>' +

                            '<p>Su rol no tiene permiso para acceder a esta sección.</p>' +

                        '</div>'

                    );

                    return;

                }


                console.error(

                    "Error al cargar: " + url,

                    xhr.status,

                    xhr.responseText

                );


                $("#contenidoPrincipal").html(

                    '<div class="alert alert-danger">' +

                        '<h5>' +

                            '<i class="fas fa-exclamation-triangle"></i> ' +

                            'Error al cargar el contenido' +

                        '</h5>' +

                        '<p>' +

                            'No fue posible cargar la sección solicitada.' +

                        '</p>' +

                    '</div>'

                );


            }

        });

    }


    // INICIALIZAR CONTENIDO

    function inicializarContenido() {

        /*
         * Las vistas se cargan dinámicamente dentro de
         * #contenidoPrincipal.
         *
         * Cada vista debe tener la oportunidad de inicializar
         * sus propios componentes después de ser insertada.
         */

        if ($.fn.DataTable) {

            /*
             * DATATABLES GENERALES
             *
             * Se mantienen para las pantallas que utilizan
             * la clase .dataTable.
             */

            $(".dataTable").each(function() {

                if (!$.fn.DataTable.isDataTable(this)) {

                    $(this).DataTable({

                        responsive: true,

                        autoWidth: false,

                        language: {

                            url:
                                "plugins/datatables/i18n/es-ES.json"

                        }

                    });

                }

            });


            /*
             * FACTURACIÓN
             *
             * La vista de Facturación administra su propio DataTable.
             * No lo inicializamos desde el Dashboard para evitar
             * duplicar buscador, selector y paginación al volver
             * al módulo después de navegar a otra sección.
             */


        }

    }


    // NAVEGACIÓN
    //
    // Cada módulo se carga dentro del dashboard y queda registrado en el
    // historial del navegador (dashboard.jsp#clientes, #pagos, ...).
    // Así los botones Atrás / Adelante se mueven entre módulos del sistema
    // y no vuelven al login mientras la sesión esté abierta.

    var URL_INICIO = "DashboardController";


    function nombreModulo(url) {

        if (!url || url === URL_INICIO) {
            return "inicio";
        }

        return url.replace(/\.jsp$/, "");
    }


    function urlDeModulo(nombre) {

        if (!nombre || nombre === "inicio") {
            return URL_INICIO;
        }

        var enlace = $(".nav-link[data-url='" + nombre + ".jsp']");

        return enlace.length ? enlace.attr("data-url") : URL_INICIO;
    }


    function marcarActivo(url) {

        $(".nav-link[data-url]").removeClass("active");

        $(".nav-link[data-url='" + url + "']").addClass("active");
    }


    function tituloModulo(url) {

        if (!url || url === URL_INICIO) {
            return "Inicio";
        }

        var texto = $(".nav-link[data-url='" + url + "'] p").first().text().trim();

        return texto || "Inicio";
    }


    function navegar(url, agregarAlHistorial) {

        marcarActivo(url);

        document.title = tituloModulo(url) + " | JMQ SYSTEM";

        cargarContenido(url);

        if (agregarAlHistorial) {
            history.pushState({ url: url }, "", "dashboard.jsp#" + nombreModulo(url));
        }
    }


    // Clic en el menú
    $(document).on("click", ".nav-link[data-url]", function(e) {

        e.preventDefault();

        var url = $(this).attr("data-url");

        if (url) {
            navegar(url, true);
        }
    });


    // Botones Atrás / Adelante del navegador
    window.addEventListener("popstate", function(e) {

        var url = (e.state && e.state.url)
                ? e.state.url
                : urlDeModulo(location.hash.substring(1));

        navegar(url, false);
    });


    // Si el navegador muestra esta página desde su caché (por ejemplo,
    // con Atrás después de cerrar sesión), se recarga para que el
    // servidor verifique la sesión.
    window.addEventListener("pageshow", function(e) {

        if (e.persisted) {
            location.reload();
        }
    });


    // Al iniciar: abre el módulo indicado en la dirección
    // (dashboard.jsp#pagos o dashboard.jsp?modulo=pagos) o el inicio.
    $(document).ready(function() {

        var parametros = new URLSearchParams(location.search);

        var nombre = parametros.get("modulo") || location.hash.substring(1);

        var url = urlDeModulo(nombre);

        history.replaceState({ url: url }, "", "dashboard.jsp#" + nombreModulo(url));

        navegar(url, false);
    });


</script>


</body>

</html>