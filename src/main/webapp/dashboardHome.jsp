<%@page contentType="text/html;charset=UTF-8"%>

<%
    // OBTENER DATOS DEL DASHBOARD

    Number totalUsuariosObj =
            (Number) request.getAttribute("totalUsuarios");

    Number totalPersonalObj =
            (Number) request.getAttribute("totalPersonal");

    Number totalClientesObj =
            (Number) request.getAttribute("totalClientes");

    Number totalVehiculosObj =
            (Number) request.getAttribute("totalVehiculos");

    Number totalRutasObj =
            (Number) request.getAttribute("totalRutas");

    Number totalTiposObj =
            (Number) request.getAttribute("totalTiposResiduos");

    Number totalAsignacionesObj =
            (Number) request.getAttribute("totalAsignaciones");

    Number totalRecoleccionesObj =
            (Number) request.getAttribute("totalRecolecciones");

    Number totalKgObj =
            (Number) request.getAttribute("totalResiduosKg");


    Number disponiblesObj =
            (Number) request.getAttribute("vehiculosDisponibles");

    Number asignadosObj =
            (Number) request.getAttribute("vehiculosAsignados");

    Number mantenimientoObj =
            (Number) request.getAttribute("vehiculosMantenimiento");

    Number fueraServicioObj =
            (Number) request.getAttribute("vehiculosFueraServicio");


    // CONVERTIR VALORES

    int totalUsuarios =
            totalUsuariosObj != null
                    ? totalUsuariosObj.intValue()
                    : 0;

    int totalPersonal =
            totalPersonalObj != null
                    ? totalPersonalObj.intValue()
                    : 0;

    int totalClientes =
            totalClientesObj != null
                    ? totalClientesObj.intValue()
                    : 0;

    int totalVehiculos =
            totalVehiculosObj != null
                    ? totalVehiculosObj.intValue()
                    : 0;

    int totalRutas =
            totalRutasObj != null
                    ? totalRutasObj.intValue()
                    : 0;

    int totalTipos =
            totalTiposObj != null
                    ? totalTiposObj.intValue()
                    : 0;

    int totalAsignaciones =
            totalAsignacionesObj != null
                    ? totalAsignacionesObj.intValue()
                    : 0;

    int totalRecolecciones =
            totalRecoleccionesObj != null
                    ? totalRecoleccionesObj.intValue()
                    : 0;

    double totalKg =
            totalKgObj != null
                    ? totalKgObj.doubleValue()
                    : 0;


    // VEHÍCULOS POR ESTADO

    int vehiculosDisponibles =
            disponiblesObj != null
                    ? disponiblesObj.intValue()
                    : 0;

    int vehiculosAsignados =
            asignadosObj != null
                    ? asignadosObj.intValue()
                    : 0;

    int vehiculosMantenimiento =
            mantenimientoObj != null
                    ? mantenimientoObj.intValue()
                    : 0;

    int vehiculosFueraServicio =
            fueraServicioObj != null
                    ? fueraServicioObj.intValue()
                    : 0;


    int totalEstadosVehiculos =
            vehiculosDisponibles
            + vehiculosAsignados
            + vehiculosMantenimiento
            + vehiculosFueraServicio;


    // VALOR MÁXIMO PARA LAS BARRAS

    int maxResumen = Math.max(
            Math.max(totalUsuarios, totalPersonal),
            Math.max(
                    Math.max(totalClientes, totalVehiculos),
                    Math.max(
                            Math.max(totalRutas, totalTipos),
                            Math.max(
                                    totalAsignaciones,
                                    totalRecolecciones
                            )
                    )
            )
    );

    if (maxResumen <= 0) {
        maxResumen = 1;
    }


    // PORCENTAJES DEL GRÁFICO CIRCULAR

    double porcentajeDisponibles = 0;
    double porcentajeAsignados = 0;
    double porcentajeMantenimiento = 0;
    double porcentajeFueraServicio = 0;

    if (totalEstadosVehiculos > 0) {

        porcentajeDisponibles =
                (vehiculosDisponibles * 100.0)
                / totalEstadosVehiculos;

        porcentajeAsignados =
                (vehiculosAsignados * 100.0)
                / totalEstadosVehiculos;

        porcentajeMantenimiento =
                (vehiculosMantenimiento * 100.0)
                / totalEstadosVehiculos;

        porcentajeFueraServicio =
                (vehiculosFueraServicio * 100.0)
                / totalEstadosVehiculos;
    }


    // ÁNGULOS DEL GRÁFICO CIRCULAR

    double gradosDisponibles =
            porcentajeDisponibles * 3.6;

    double gradosAsignados =
            porcentajeAsignados * 3.6;

    double gradosMantenimiento =
            porcentajeMantenimiento * 3.6;

    double gradosFueraServicio =
            porcentajeFueraServicio * 3.6;


    double inicioAsignados =
            gradosDisponibles;

    double inicioMantenimiento =
            gradosDisponibles
            + gradosAsignados;

    double inicioFueraServicio =
            gradosDisponibles
            + gradosAsignados
            + gradosMantenimiento;

%>


<%
    // Datos para la bienvenida y formatos
    java.text.DecimalFormat formatoNumero = new java.text.DecimalFormat(
            "#,##0", new java.text.DecimalFormatSymbols(new java.util.Locale("es", "PY")));

    String nombreUsuario = session.getAttribute("nombre") != null
            ? String.valueOf(session.getAttribute("nombre"))
            : String.valueOf(session.getAttribute("usuario"));

    String fechaHoy = java.time.LocalDate.now().format(
            java.time.format.DateTimeFormatter.ofPattern(
                    "EEEE, d 'de' MMMM 'de' yyyy", new java.util.Locale("es", "PY")));
    fechaHoy = fechaHoy.substring(0, 1).toUpperCase() + fechaHoy.substring(1);

    int porcentajeOperativo = totalVehiculos > 0
            ? (int) Math.round((vehiculosDisponibles + vehiculosAsignados) * 100.0 / Math.max(totalEstadosVehiculos, 1))
            : 0;
%>

<style>
    .inicio-banner {
        background: linear-gradient(135deg, #1F3864 0%, #2E75B6 100%);
        color: #ffffff;
        border: 0;
        border-radius: 10px;
        overflow: hidden;
        position: relative;
    }

    .inicio-banner::after {
        content: "";
        position: absolute;
        right: -60px;
        top: -60px;
        width: 240px;
        height: 240px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.07);
    }

    .inicio-banner .card-body {
        padding: 22px 26px;
        position: relative;
        z-index: 1;
    }

    .inicio-logo {
        width: 74px;
        height: 74px;
        min-width: 74px;
        border-radius: 50%;
        background: #ffffff;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 18px;
        box-shadow: 0 4px 14px rgba(0, 0, 0, 0.18);
    }

    .inicio-logo img {
        width: 56px;
        height: auto;
    }

    .inicio-banner h2 {
        font-size: 1.55rem;
        font-weight: 600;
        margin: 0 0 4px 0;
    }

    .inicio-banner .fecha {
        font-size: 0.9rem;
        opacity: 0.85;
    }

    .inicio-banner .descripcion {
        font-size: 0.92rem;
        opacity: 0.9;
        margin-top: 6px;
    }

    .inicio-banner .btn-acceso {
        background: rgba(255, 255, 255, 0.15);
        border: 1px solid rgba(255, 255, 255, 0.35);
        color: #ffffff;
        margin: 4px 0 4px 6px;
    }

    .inicio-banner .btn-acceso:hover {
        background: #ffffff;
        color: #1F3864;
    }

    .inicio-titulo-seccion {
        font-size: 0.82rem;
        font-weight: 700;
        letter-spacing: 0.06em;
        text-transform: uppercase;
        color: #6c757d;
        margin: 6px 0 10px 2px;
    }

    .small-box .icon > i {
        font-size: 64px;
    }

    .small-box h3 small {
        font-size: 1rem;
        font-weight: 600;
    }

    .info-box {
        min-height: 82px;
    }

    .info-box .info-box-number {
        font-size: 1.35rem;
    }

    .inicio-donut {
        width: 170px;
        height: 170px;
        border-radius: 50%;
        margin: 0 auto;
        position: relative;
    }

    .inicio-donut::after {
        content: "";
        position: absolute;
        inset: 26px;
        background: #ffffff;
        border-radius: 50%;
    }

    .inicio-donut .centro {
        position: absolute;
        inset: 0;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        z-index: 1;
        line-height: 1.1;
    }

    .inicio-donut .centro b {
        font-size: 1.8rem;
        color: #1F3864;
    }

    .inicio-donut .centro span {
        font-size: 0.75rem;
        color: #6c757d;
    }

    .progress-group .progress-text {
        font-weight: 600;
    }

    .inicio-accesos .btn-app {
        min-width: 96px;
        height: 72px;
        margin: 0 8px 10px 0;
        border-radius: 8px;
        color: #1F3864;
        background: #f8f9fa;
    }

    .inicio-accesos .btn-app:hover {
        background: #1F3864;
        color: #ffffff;
        border-color: #1F3864;
    }

    .inicio-accesos .btn-app > .fas {
        font-size: 22px;
    }
</style>

<section class="content inicio">
    <div class="container-fluid">

        <!-- BIENVENIDA -->
        <div class="card inicio-banner mb-4">
            <div class="card-body">
                <div class="row align-items-center">
                    <div class="col-lg-7 d-flex align-items-center mb-3 mb-lg-0">
                        <div class="inicio-logo">
                            <img src="dist/img/jmmqlogo.png" alt="JMQ System">
                        </div>
                        <div>
                            <h2>Bienvenido/a, <%= nombreUsuario %></h2>
                            <div class="fecha">
                                <i class="far fa-calendar-alt mr-1"></i>
                                <%= fechaHoy %>
                            </div>
                            <div class="descripcion">
                                Sistema de gestión de recolección de residuos de la ciudad de Villa Elisa.
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-5 text-lg-right">
                        <a href="#" class="btn btn-sm btn-acceso" data-ir="recolecciones.jsp">
                            <i class="fas fa-recycle mr-1"></i> Recolecciones
                        </a>
                        <a href="#" class="btn btn-sm btn-acceso" data-ir="facturacion.jsp">
                            <i class="fas fa-file-invoice-dollar mr-1"></i> Facturación
                        </a>
                        <a href="#" class="btn btn-sm btn-acceso" data-ir="informes.jsp">
                            <i class="fas fa-chart-bar mr-1"></i> Informes
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- INDICADORES PRINCIPALES -->
        <div class="inicio-titulo-seccion">Operación</div>
        <div class="row">

            <div class="col-lg-3 col-md-6">
                <div class="small-box bg-gradient-success">
                    <div class="inner">
                        <h3><%= formatoNumero.format(totalRecolecciones) %></h3>
                        <p>Recolecciones registradas</p>
                    </div>
                    <div class="icon"><i class="fas fa-recycle"></i></div>
                    <a href="#" class="small-box-footer" data-ir="recolecciones.jsp">
                        Ver recolecciones <i class="fas fa-arrow-circle-right"></i>
                    </a>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="small-box bg-gradient-info">
                    <div class="inner">
                        <h3><%= formatoNumero.format(totalKg) %> <small>kg</small></h3>
                        <p>Residuos recolectados</p>
                    </div>
                    <div class="icon"><i class="fas fa-weight-hanging"></i></div>
                    <a href="#" class="small-box-footer" data-ir="clasificacion_residuos.jsp">
                        Ver clasificación <i class="fas fa-arrow-circle-right"></i>
                    </a>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="small-box bg-gradient-primary">
                    <div class="inner">
                        <h3><%= formatoNumero.format(totalClientes) %></h3>
                        <p>Clientes activos</p>
                    </div>
                    <div class="icon"><i class="fas fa-users"></i></div>
                    <a href="#" class="small-box-footer" data-ir="clientes.jsp">
                        Ver clientes <i class="fas fa-arrow-circle-right"></i>
                    </a>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="small-box bg-gradient-warning">
                    <div class="inner">
                        <h3><%= formatoNumero.format(totalVehiculos) %></h3>
                        <p>Vehículos registrados</p>
                    </div>
                    <div class="icon"><i class="fas fa-truck"></i></div>
                    <a href="#" class="small-box-footer" data-ir="vehiculos.jsp">
                        Ver vehículos <i class="fas fa-arrow-circle-right"></i>
                    </a>
                </div>
            </div>

        </div>

        <!-- INDICADORES SECUNDARIOS -->
        <div class="inicio-titulo-seccion">Recursos</div>
        <div class="row">

            <div class="col-lg-3 col-md-6">
                <div class="info-box shadow-sm">
                    <span class="info-box-icon bg-indigo elevation-1"><i class="fas fa-user-tie"></i></span>
                    <div class="info-box-content">
                        <span class="info-box-text">Personal activo</span>
                        <span class="info-box-number"><%= formatoNumero.format(totalPersonal) %></span>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="info-box shadow-sm">
                    <span class="info-box-icon bg-teal elevation-1"><i class="fas fa-route"></i></span>
                    <div class="info-box-content">
                        <span class="info-box-text">Rutas activas</span>
                        <span class="info-box-number"><%= formatoNumero.format(totalRutas) %></span>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="info-box shadow-sm">
                    <span class="info-box-icon bg-purple elevation-1"><i class="fas fa-tasks"></i></span>
                    <div class="info-box-content">
                        <span class="info-box-text">Asignaciones</span>
                        <span class="info-box-number"><%= formatoNumero.format(totalAsignaciones) %></span>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="info-box shadow-sm">
                    <span class="info-box-icon bg-orange elevation-1"><i class="fas fa-dumpster"></i></span>
                    <div class="info-box-content">
                        <span class="info-box-text">Tipos de residuos</span>
                        <span class="info-box-number"><%= formatoNumero.format(totalTipos) %></span>
                    </div>
                </div>
            </div>

        </div>

        <!-- FLOTA Y RESUMEN -->
        <div class="row">

            <div class="col-lg-5 d-flex">
                <div class="card card-outline card-primary flex-fill">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-truck-moving mr-1"></i>
                            Estado de la flota
                        </h3>
                        <div class="card-tools">
                            <span class="badge badge-success"><%= porcentajeOperativo %>% operativa</span>
                        </div>
                    </div>
                    <div class="card-body d-flex align-items-center">
                        <div class="row align-items-center w-100 mx-0">
                            <div class="col-sm-5 mb-3 mb-sm-0">
                                <div class="inicio-donut"
                                     style="background: conic-gradient(
                                        #28a745 0deg <%= gradosDisponibles %>deg,
                                        #007bff <%= inicioAsignados %>deg <%= inicioAsignados + gradosAsignados %>deg,
                                        #ffc107 <%= inicioMantenimiento %>deg <%= inicioMantenimiento + gradosMantenimiento %>deg,
                                        #dc3545 <%= inicioFueraServicio %>deg 360deg);">
                                    <div class="centro">
                                        <b><%= totalEstadosVehiculos %></b>
                                        <span>vehículos</span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-7 pl-sm-4">
                                <div class="progress-group">
                                    <span class="progress-text">Disponibles</span>
                                    <span class="float-right"><b><%= vehiculosDisponibles %></b>/<%= totalEstadosVehiculos %></span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-success" style="width: <%= Math.round(porcentajeDisponibles) %>%"></div>
                                    </div>
                                </div>
                                <div class="progress-group">
                                    <span class="progress-text">Asignados</span>
                                    <span class="float-right"><b><%= vehiculosAsignados %></b>/<%= totalEstadosVehiculos %></span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-primary" style="width: <%= Math.round(porcentajeAsignados) %>%"></div>
                                    </div>
                                </div>
                                <div class="progress-group">
                                    <span class="progress-text">En mantenimiento</span>
                                    <span class="float-right"><b><%= vehiculosMantenimiento %></b>/<%= totalEstadosVehiculos %></span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-warning" style="width: <%= Math.round(porcentajeMantenimiento) %>%"></div>
                                    </div>
                                </div>
                                <div class="progress-group mb-0">
                                    <span class="progress-text">Fuera de servicio</span>
                                    <span class="float-right"><b><%= vehiculosFueraServicio %></b>/<%= totalEstadosVehiculos %></span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-danger" style="width: <%= Math.round(porcentajeFueraServicio) %>%"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-7 d-flex">
                <div class="card card-outline card-success flex-fill">
                    <div class="card-header">
                        <h3 class="card-title">
                            <i class="fas fa-chart-bar mr-1"></i>
                            Resumen del sistema
                        </h3>
                    </div>
                    <div class="card-body">
<%
    String[][] resumen = {
        {"Usuarios", String.valueOf(totalUsuarios), "bg-danger", "fa-user-shield"},
        {"Personal", String.valueOf(totalPersonal), "bg-indigo", "fa-user-tie"},
        {"Clientes", String.valueOf(totalClientes), "bg-primary", "fa-users"},
        {"Vehículos", String.valueOf(totalVehiculos), "bg-warning", "fa-truck"},
        {"Rutas", String.valueOf(totalRutas), "bg-teal", "fa-route"},
        {"Tipos de residuos", String.valueOf(totalTipos), "bg-orange", "fa-dumpster"},
        {"Asignaciones", String.valueOf(totalAsignaciones), "bg-purple", "fa-tasks"},
        {"Recolecciones", String.valueOf(totalRecolecciones), "bg-success", "fa-recycle"}
    };
    for (String[] item : resumen) {
        int valor = Integer.parseInt(item[1]);
        long ancho = Math.round(valor * 100.0 / maxResumen);
%>
                        <div class="progress-group">
                            <span class="progress-text">
                                <i class="fas <%= item[3] %> text-muted mr-1"></i>
                                <%= item[0] %>
                            </span>
                            <span class="float-right"><b><%= valor %></b></span>
                            <div class="progress progress-sm">
                                <div class="progress-bar <%= item[2] %>" style="width: <%= ancho %>%"></div>
                            </div>
                        </div>
<%
    }
%>
                    </div>
                </div>
            </div>

        </div>

        <!-- ACCESOS RÁPIDOS -->
        <div class="card card-outline card-secondary inicio-accesos">
            <div class="card-header">
                <h3 class="card-title">
                    <i class="fas fa-bolt mr-1"></i>
                    Accesos rápidos
                </h3>
            </div>
            <div class="card-body pb-1">
                <a href="#" class="btn btn-app" data-ir="clientes.jsp"><i class="fas fa-users"></i> Clientes</a>
                <a href="#" class="btn btn-app" data-ir="servicios.jsp"><i class="fas fa-concierge-bell"></i> Servicios</a>
                <a href="#" class="btn btn-app" data-ir="asignaciones.jsp"><i class="fas fa-tasks"></i> Asignaciones</a>
                <a href="#" class="btn btn-app" data-ir="recolecciones.jsp"><i class="fas fa-recycle"></i> Recolecciones</a>
                <a href="#" class="btn btn-app" data-ir="facturacion.jsp"><i class="fas fa-file-invoice-dollar"></i> Facturación</a>
                <a href="#" class="btn btn-app" data-ir="pagos.jsp"><i class="fas fa-money-bill-wave"></i> Pagos</a>
                <a href="#" class="btn btn-app" data-ir="pendientes.jsp"><i class="fas fa-clock"></i> Pendientes</a>
                <a href="#" class="btn btn-app" data-ir="informes.jsp"><i class="fas fa-chart-bar"></i> Informes</a>
            </div>
        </div>

    </div>
</section>

<script>
    // Los accesos del inicio abren el módulo dentro del dashboard
    $(document)
        .off("click.inicio")
        .on("click.inicio", "[data-ir]", function (e) {
            e.preventDefault();
            navegar($(this).attr("data-ir"), true);
        });

    $("[data-ir]").each(function () {
        if (!$(".nav-link[data-url='" + $(this).attr("data-ir") + "']").length) {
            $(this).hide();
        }
    });
</script>
