<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .informes-container {
        padding: 5px 0 20px 0;
    }

    .informes-banner {
        background: linear-gradient(135deg, #1F3864 0%, #2E75B6 100%);
        color: #ffffff;
        border-radius: 10px;
        padding: 20px 24px;
        margin-bottom: 22px;
        position: relative;
        overflow: hidden;
        box-shadow: 0 4px 14px rgba(31, 56, 100, 0.25);
    }

    .informes-banner::after {
        content: "";
        position: absolute;
        right: -50px;
        top: -70px;
        width: 220px;
        height: 220px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.07);
    }

    .informes-banner h3 {
        margin: 0;
        font-size: 1.5rem;
        font-weight: 600;
    }

    .informes-banner p {
        margin: 4px 0 0 0;
        opacity: 0.88;
        font-size: 0.92rem;
    }

    .informes-banner .banner-icono {
        width: 56px;
        height: 56px;
        min-width: 56px;
        border-radius: 14px;
        background: rgba(255, 255, 255, 0.16);
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 24px;
        margin-right: 16px;
    }

    .informes-banner .etiqueta {
        display: inline-block;
        background: rgba(255, 255, 255, 0.15);
        border: 1px solid rgba(255, 255, 255, 0.3);
        border-radius: 20px;
        padding: 3px 12px;
        font-size: 0.78rem;
        margin: 4px 0 0 6px;
        position: relative;
        z-index: 1;
    }

    .informes-seccion {
        margin-bottom: 26px;
    }

    .informes-seccion-titulo {
        display: flex;
        align-items: center;
        font-size: 1rem;
        font-weight: 700;
        color: #1F3864;
        margin-bottom: 12px;
        padding-bottom: 8px;
        border-bottom: 2px solid #e3e9f2;
    }

    .informes-seccion-titulo .icono-seccion {
        width: 30px;
        height: 30px;
        border-radius: 8px;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        color: #ffffff;
        font-size: 14px;
        margin-right: 10px;
    }

    .operaciones .icono-seccion {
        background: #28a745;
    }

    .financiero .icono-seccion {
        background: #6610f2;
    }

    .informes-seccion-titulo .cantidad {
        margin-left: auto;
        font-size: 0.75rem;
        font-weight: 600;
        color: #6c757d;
    }

    .informes-grid {
        display: flex;
        flex-wrap: wrap;
        margin: -7px;
    }

    .informe-col {
        width: 25%;
        padding: 7px;
    }

    .informe-card {
        --color: #007bff;
        border: 1px solid #e3e9f2;
        border-left: 4px solid var(--color);
        border-radius: 8px;
        background: #ffffff;
        height: 100%;
        min-height: 120px;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .informe-card:hover {
        box-shadow: 0 8px 20px rgba(31, 56, 100, 0.14);
        transform: translateY(-3px);
    }

    .informe-card-body {
        padding: 18px 16px;
        display: flex;
        align-items: center;
        min-height: 120px;
    }

    .informe-icon {
        width: 50px;
        height: 50px;
        min-width: 50px;
        border-radius: 12px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 14px;
        font-size: 21px;
        color: #ffffff;
        background: var(--color);
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
    }

    .informe-info {
        flex: 1;
    }

    .informe-info h5 {
        margin: 0 0 4px 0;
        font-size: 15px;
        font-weight: 700;
        color: #1F3864;
    }

    .informe-info p {
        margin: 0;
        font-size: 12px;
        color: #6c757d;
        line-height: 1.4;
    }

    .informe-arrow {
        position: absolute;
        right: 12px;
        bottom: 10px;
        color: var(--color);
        font-size: 12px;
        opacity: 0.6;
        transition: opacity 0.2s ease, transform 0.2s ease;
    }

    .informe-card:hover .informe-arrow {
        opacity: 1;
        transform: translateX(3px);
    }

    .color-primary { --color: #007bff; }
    .color-info { --color: #17a2b8; }
    .color-success { --color: #28a745; }
    .color-teal { --color: #20c997; }
    .color-warning { --color: #f39c12; }
    .color-indigo { --color: #6610f2; }
    .color-olive { --color: #3d9970; }
    .color-danger { --color: #dc3545; }

    [id^="panelInforme"] > .card {
        border-top: 3px solid #1F3864;
        border-radius: 8px;
        box-shadow: 0 4px 14px rgba(31, 56, 100, 0.1);
    }

    [id^="panelInforme"] > .card > .card-header {
        background: #f4f7fb;
    }

    .panel-informe-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: wrap;
        gap: 10px;
    }

    .panel-informe-header h5 {
        margin: 0;
        font-size: 16px;
        font-weight: 700;
        color: #1F3864;
    }

    .filtros-informe {
        background: #f8fafc;
        border: 1px solid #e3e9f2;
        border-radius: 8px;
        padding: 18px;
        margin-bottom: 15px;
    }

    .filtros-informe label {
        font-size: 13px;
        font-weight: 600;
        color: #1F3864;
    }

    .filtros-informe .form-control {
        font-size: 13px;
    }

    .resultado-informe {
        margin-top: 15px;
    }

    .resultado-informe thead th {
        background: #1F3864;
        color: #ffffff;
        font-weight: 600;
        font-size: 13px;
        border-color: #1F3864;
    }

    @media (max-width: 1200px) {
        .informe-col {
            width: 33.333333%;
        }
    }

    @media (max-width: 768px) {
        .informe-col {
            width: 50%;
        }
    }

    @media (max-width: 576px) {
        .informe-col {
            width: 100%;
        }
    }
</style>

<div class="container-fluid informes-container">

    <div class="informes-banner">
        <div class="row align-items-center">
            <div class="col-lg-7 d-flex align-items-center mb-2 mb-lg-0">
                <div class="banner-icono">
                    <i class="fas fa-chart-bar"></i>
                </div>
                <div>
                    <h3>Informes</h3>
                    <p>Consulta, análisis y generación de informes de los procesos de JMQ SYSTEM.</p>
                </div>
            </div>
            <div class="col-lg-5 text-lg-right">
                <span class="etiqueta"><i class="fas fa-file-pdf mr-1"></i> PDF con JasperReports</span>
                <span class="etiqueta"><i class="fas fa-calendar-alt mr-1"></i> Filtros por fecha</span>
                <span class="etiqueta"><i class="fas fa-search mr-1"></i> Informes individuales</span>
            </div>
        </div>
    </div>

    <!-- OPERACIONES -->
    <div class="informes-seccion operaciones">

        <div class="informes-seccion-titulo">
            <span class="icono-seccion"><i class="fas fa-truck"></i></span>
            Operaciones
            <span class="cantidad">4 informes</span>
        </div>

        <div class="informes-grid">

            <!-- SERVICIOS -->
            <div class="informe-col">
                <div class="informe-card color-primary" onclick="mostrarInformeServicios()">
                    <div class="informe-card-body">
                        <div class="informe-icon">
                            <i class="fas fa-concierge-bell"></i>
                        </div>
                        <div class="informe-info">
                            <h5>Servicios</h5>
                            <p>Consulta de servicios contratados y su vigencia.</p>
                        </div>
                    </div>
                    <div class="informe-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </div>
            </div>

            <!-- ASIGNACIONES -->
            <div class="informe-col">
                <div class="informe-card color-info" onclick="mostrarInformeAsignaciones()">
                    <div class="informe-card-body">
                        <div class="informe-icon">
                            <i class="fas fa-clipboard-list"></i>
                        </div>
                        <div class="informe-info">
                            <h5>Asignaciones</h5>
                            <p>Informes sobre las asignaciones realizadas.</p>
                        </div>
                    </div>
                    <div class="informe-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </div>
            </div>

            <!-- RECOLECCIONES -->
            <div class="informe-col">
                <div class="informe-card color-success" onclick="mostrarInformeRecolecciones()">
                    <div class="informe-card-body">
                        <div class="informe-icon">
                            <i class="fas fa-recycle"></i>
                        </div>
                        <div class="informe-info">
                            <h5>Recolecciones</h5>
                            <p>Informes de recolecciones realizadas por período.</p>
                        </div>
                    </div>
                    <div class="informe-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </div>
            </div>

            <!-- CLASIFICACIÓN DE RESIDUOS -->
            <div class="informe-col">
                <div class="informe-card color-teal" onclick="mostrarInformeClasificaciones()">
                    <div class="informe-card-body">
                        <div class="informe-icon">
                            <i class="fas fa-recycle"></i>
                        </div>
                        <div class="informe-info">
                            <h5>Clasificación de Residuos</h5>
                            <p>Informes del proceso de clasificación de residuos.</p>
                        </div>
                    </div>
                    <div class="informe-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <!-- PANEL DE ASIGNACIONES -->
    <div id="panelInformeAsignaciones" style="display:none; margin-top:10px;">

        <div class="card">

            <div class="card-header">
                <div class="panel-informe-header">
                    <h5>
                        <i class="fas fa-clipboard-list mr-2"></i>
                        Informe de Asignaciones
                    </h5>

                    <button type="button"
                            class="btn btn-sm btn-outline-secondary"
                            onclick="cerrarInformeAsignaciones()">
                        <i class="fas fa-times"></i>
                        Cerrar
                    </button>
                </div>
            </div>

            <div class="card-body">

                <!-- INFORME GENERAL POR PERÍODO -->
                <div class="filtros-informe">
                    <div class="row align-items-end">

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaDesdeAsignacionInforme">
                                    <i class="fas fa-calendar-day mr-1"></i>
                                    Fecha Inicio
                                </label>
                                <input type="date"
                                       id="fechaDesdeAsignacionInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaHastaAsignacionInforme">
                                    <i class="fas fa-calendar-check mr-1"></i>
                                    Fecha Fin
                                </label>
                                <input type="date"
                                       id="fechaHastaAsignacionInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <button type="button"
                                    class="btn btn-primary btn-block"
                                    onclick="generarInformeGeneralAsignaciones()">
                                <i class="fas fa-file-pdf mr-1"></i>
                                Generar Informe General
                            </button>
                        </div>

                    </div>
                </div>

                <!-- BÚSQUEDA INDIVIDUAL -->
                <div class="mt-4">

                    <h6 class="font-weight-bold mb-3">
                        <i class="fas fa-search mr-1"></i>
                        Buscar Asignaciones
                    </h6>

                    <div class="filtros-informe">

                        <div class="row">

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="idAsignacionInforme">ID</label>
                                    <input type="number"
                                           id="idAsignacionInforme"
                                           class="form-control"
                                           placeholder="ID">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="fechaAsignacionInforme">Fecha</label>
                                    <input type="date"
                                           id="fechaAsignacionInforme"
                                           class="form-control">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="rutaAsignacionInforme">Ruta</label>
                                    <select id="rutaAsignacionInforme"
                                            class="form-control">
                                        <option value="">Todas</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="vehiculoAsignacionInforme">Vehículo</label>
                                    <select id="vehiculoAsignacionInforme"
                                            class="form-control">
                                        <option value="">Todos</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="personalAsignacionInforme">Personal</label>
                                    <select id="personalAsignacionInforme"
                                            class="form-control">
                                        <option value="">Todos</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="estadoAsignacionInforme">Estado</label>
                                    <select id="estadoAsignacionInforme"
                                            class="form-control">
                                        <option value="">Todos</option>
                                        <option value="PROGRAMADA">Programada</option>
                                        <option value="EN CURSO">En curso</option>
                                        <option value="FINALIZADA">Finalizada</option>
                                        <option value="CANCELADA">Cancelada</option>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-12 text-right">

                                <button type="button"
                                        class="btn btn-primary"
                                        onclick="buscarAsignacionesInforme()">
                                    <i class="fas fa-search mr-1"></i>
                                    Buscar
                                </button>

                                <button type="button"
                                        class="btn btn-secondary"
                                        onclick="limpiarFiltrosAsignaciones()">
                                    <i class="fas fa-eraser mr-1"></i>
                                    Limpiar
                                </button>

                            </div>

                        </div>

                    </div>

                    <div id="resultadoInformeAsignaciones"
                         class="resultado-informe">
                    </div>

                </div>

            </div>
        </div>
    </div>

    <!-- PANEL DE RECOLECCIONES -->
    <div id="panelInformeRecolecciones" style="display:none; margin-top:10px;">

        <div class="card">

            <div class="card-header">
                <div class="panel-informe-header">
                    <h5>
                        <i class="fas fa-recycle mr-2"></i>
                        Informe de Recolecciones
                    </h5>

                    <button type="button"
                            class="btn btn-sm btn-outline-secondary"
                            onclick="cerrarInformeRecolecciones()">
                        <i class="fas fa-times"></i>
                        Cerrar
                    </button>
                </div>
            </div>

            <div class="card-body">

                <div class="filtros-informe">
                    <div class="row align-items-end">

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaDesdeRecoleccionInforme">
                                    <i class="fas fa-calendar-day mr-1"></i>
                                    Fecha Inicio
                                </label>
                                <input type="date" id="fechaDesdeRecoleccionInforme" class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaHastaRecoleccionInforme">
                                    <i class="fas fa-calendar-check mr-1"></i>
                                    Fecha Fin
                                </label>
                                <input type="date" id="fechaHastaRecoleccionInforme" class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <button type="button" class="btn btn-primary btn-block" onclick="generarInformeGeneralRecolecciones()">
                                <i class="fas fa-file-pdf mr-1"></i>
                                Generar Informe General
                            </button>
                        </div>

                    </div>
                </div>

                <div class="mt-4">

                    <h6 class="font-weight-bold mb-3">
                        <i class="fas fa-search mr-1"></i>
                        Buscar Recolecciones
                    </h6>

                    <div class="filtros-informe">

                        <div class="row">

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="idRecoleccionInforme">ID</label>
                                    <input type="number" id="idRecoleccionInforme" class="form-control" placeholder="ID">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="fechaRecoleccionInforme">Fecha</label>
                                    <input type="date" id="fechaRecoleccionInforme" class="form-control">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="rutaRecoleccionInforme">Ruta</label>
                                    <select id="rutaRecoleccionInforme" class="form-control">
                                        <option value="">Todas</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="vehiculoRecoleccionInforme">Vehículo</label>
                                    <select id="vehiculoRecoleccionInforme" class="form-control">
                                        <option value="">Todos</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="personalRecoleccionInforme">Personal</label>
                                    <select id="personalRecoleccionInforme" class="form-control">
                                        <option value="">Todos</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="estadoRecoleccionInforme">Estado</label>
                                    <select id="estadoRecoleccionInforme" class="form-control">
                                        <option value="">Todos</option>
                                        <option value="REGISTRADA">Registrada</option>
                                        <option value="VALIDADA">Validada</option>
                                        <option value="ANULADA">Anulada</option>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-12 text-right">
                                <button type="button" class="btn btn-primary" onclick="buscarRecoleccionesInforme()">
                                    <i class="fas fa-search mr-1"></i>
                                    Buscar
                                </button>

                                <button type="button" class="btn btn-secondary" onclick="limpiarFiltrosRecolecciones()">
                                    <i class="fas fa-eraser mr-1"></i>
                                    Limpiar
                                </button>
                            </div>
                        </div>

                    </div>

                    <div id="resultadoInformeRecolecciones" class="resultado-informe"></div>

                </div>

            </div>
        </div>
    </div>

    <!-- PANEL DE CLASIFICACIÓN DE RESIDUOS -->
    <div id="panelInformeClasificaciones" style="display:none; margin-top:10px;">

        <div class="card">

            <div class="card-header">
                <div class="panel-informe-header">
                    <h5>
                        <i class="fas fa-recycle mr-2"></i>
                        Informe de Clasificación de Residuos
                    </h5>

                    <button type="button"
                            class="btn btn-sm btn-outline-secondary"
                            onclick="cerrarInformeClasificaciones()">
                        <i class="fas fa-times"></i>
                        Cerrar
                    </button>
                </div>
            </div>

            <div class="card-body">

                <div class="filtros-informe">
                    <div class="row align-items-end">

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaDesdeClasificacionInforme">
                                    <i class="fas fa-calendar-day mr-1"></i>
                                    Fecha Inicio
                                </label>
                                <input type="date"
                                       id="fechaDesdeClasificacionInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaHastaClasificacionInforme">
                                    <i class="fas fa-calendar-check mr-1"></i>
                                    Fecha Fin
                                </label>
                                <input type="date"
                                       id="fechaHastaClasificacionInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <button type="button"
                                    class="btn btn-primary btn-block"
                                    onclick="generarInformeGeneralClasificaciones()">
                                <i class="fas fa-file-pdf mr-1"></i>
                                Generar Informe General
                            </button>
                        </div>

                    </div>
                </div>

                <div class="mt-4">

                    <h6 class="font-weight-bold mb-3">
                        <i class="fas fa-search mr-1"></i>
                        Buscar Clasificaciones
                    </h6>

                    <div class="filtros-informe">

                        <div class="row">

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="idClasificacionInforme">ID Clasificación</label>
                                    <input type="number"
                                           id="idClasificacionInforme"
                                           class="form-control"
                                           placeholder="ID">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="idRecoleccionClasificacionInforme">Recolección</label>
                                    <input type="number"
                                           id="idRecoleccionClasificacionInforme"
                                           class="form-control"
                                           placeholder="ID Recolección">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="tipoClasificacionInforme">Tipo de Residuo</label>
                                    <input type="text"
                                           id="tipoClasificacionInforme"
                                           class="form-control"
                                           placeholder="Tipo de residuo">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="fechaClasificacionInforme">Fecha</label>
                                    <input type="date"
                                           id="fechaClasificacionInforme"
                                           class="form-control">
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="estadoClasificacionInforme">Estado</label>
                                    <select id="estadoClasificacionInforme" class="form-control">
                                        <option value="">Todos</option>
                                        <option value="true">Activo</option>
                                        <option value="false">Inactivo</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-9 text-right align-self-end">
                                <button type="button"
                                        class="btn btn-primary"
                                        onclick="buscarClasificacionesInforme()">
                                    <i class="fas fa-search mr-1"></i>
                                    Buscar
                                </button>

                                <button type="button"
                                        class="btn btn-secondary"
                                        onclick="limpiarFiltrosClasificaciones()">
                                    <i class="fas fa-eraser mr-1"></i>
                                    Limpiar
                                </button>
                            </div>

                        </div>

                    </div>

                    <div id="resultadoInformeClasificaciones" class="resultado-informe"></div>

                </div>

            </div>
        </div>
    </div>

    <!-- PANEL DE FACTURACIÓN -->
    <div id="panelInformeFacturacion" style="display:none; margin-top:10px;">

        <div class="card">

            <div class="card-header">
                <div class="panel-informe-header">
                    <h5>
                        <i class="fas fa-file-invoice mr-2"></i>
                        Informe de Facturación
                    </h5>

                    <button type="button"
                            class="btn btn-sm btn-outline-secondary"
                            onclick="cerrarInformeFacturacion()">
                        <i class="fas fa-times"></i>
                        Cerrar
                    </button>
                </div>
            </div>

            <div class="card-body">

                <div class="filtros-informe">
                    <div class="row align-items-end">

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaDesdeFacturacionInforme">
                                    <i class="fas fa-calendar-day mr-1"></i>
                                    Fecha Inicio
                                </label>
                                <input type="date"
                                       id="fechaDesdeFacturacionInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaHastaFacturacionInforme">
                                    <i class="fas fa-calendar-check mr-1"></i>
                                    Fecha Fin
                                </label>
                                <input type="date"
                                       id="fechaHastaFacturacionInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <button type="button"
                                    class="btn btn-primary btn-block"
                                    onclick="generarInformeGeneralFacturacion()">
                                <i class="fas fa-file-pdf mr-1"></i>
                                Generar Informe General
                            </button>
                        </div>

                    </div>
                </div>

                <div class="mt-4">

                    <h6 class="font-weight-bold mb-3">
                        <i class="fas fa-search mr-1"></i>
                        Buscar Facturas
                    </h6>

                    <div class="filtros-informe">

                        <div class="row">

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="idFacturaInforme">ID Factura</label>
                                    <input type="number"
                                           id="idFacturaInforme"
                                           class="form-control"
                                           placeholder="ID">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="numeroFacturaInforme">N° Factura</label>
                                    <input type="text"
                                           id="numeroFacturaInforme"
                                           class="form-control"
                                           placeholder="N° Factura">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="clienteFacturaInforme">Cliente</label>
                                    <input type="text"
                                           id="clienteFacturaInforme"
                                           class="form-control"
                                           placeholder="Nombre o apellido">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="estadoFacturaInforme">Estado</label>
                                    <select id="estadoFacturaInforme"
                                            class="form-control">
                                        <option value="">Todos</option>
                                        <option value="EMITIDA">Emitida</option>
                                        <option value="PENDIENTE">Pendiente</option>
                                        <option value="PAGADA">Pagada</option>
                                        <option value="ANULADA">Anulada</option>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="fechaFacturaInforme">Fecha Emisión</label>
                                    <input type="date"
                                           id="fechaFacturaInforme"
                                           class="form-control">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="periodoFacturaInforme">Período</label>
                                    <input type="text"
                                           id="periodoFacturaInforme"
                                           class="form-control"
                                           placeholder="Período">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="condicionVentaFacturaInforme">Condición de Venta</label>
                                    <input type="text"
                                           id="condicionVentaFacturaInforme"
                                           class="form-control"
                                           placeholder="Contado / Crédito">
                                </div>
                            </div>

                            <div class="col-md-3 text-right d-flex align-items-end justify-content-end">
                                <button type="button"
                                        class="btn btn-primary mr-2"
                                        onclick="buscarFacturasInforme()">
                                    <i class="fas fa-search mr-1"></i>
                                    Buscar
                                </button>

                                <button type="button"
                                        class="btn btn-secondary"
                                        onclick="limpiarFiltrosFacturacion()">
                                    <i class="fas fa-eraser mr-1"></i>
                                    Limpiar
                                </button>
                            </div>

                        </div>

                    </div>

                    <div id="resultadoInformeFacturacion"
                         class="resultado-informe">
                    </div>

                </div>

            </div>
        </div>
    </div>

    <!-- PANEL DE INFORME DE PAGOS -->
    <div id="panelInformePagos" style="display:none; margin-top:10px;">

        <div class="card card-primary">

            <div class="card-header">
                <h3 class="card-title">
                    <i class="fas fa-money-bill-wave mr-1"></i>
                    Informe de Pagos
                </h3>

                <div class="card-tools">
                    <button type="button"
                            class="btn btn-tool"
                            onclick="cerrarInformePagos()"
                            title="Cerrar">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            </div>

            <div class="card-body">

                <div class="filtros-informe">
                    <div class="row align-items-end">

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaDesdePagosInforme">
                                    <i class="fas fa-calendar-day mr-1"></i>
                                    Fecha Inicio
                                </label>
                                <input type="date"
                                       id="fechaDesdePagosInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaHastaPagosInforme">
                                    <i class="fas fa-calendar-check mr-1"></i>
                                    Fecha Fin
                                </label>
                                <input type="date"
                                       id="fechaHastaPagosInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <button type="button"
                                    class="btn btn-primary btn-block"
                                    onclick="generarInformeGeneralPagos()">
                                <i class="fas fa-file-pdf mr-1"></i>
                                Generar Informe General
                            </button>
                        </div>

                    </div>
                </div>

                <div class="mt-4">

                    <h6 class="font-weight-bold mb-3">
                        <i class="fas fa-search mr-1"></i>
                        Buscar Pagos
                    </h6>

                    <div class="filtros-informe">

                        <div class="row">

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="idPagoInforme">ID Pago</label>
                                    <input type="number"
                                           id="idPagoInforme"
                                           class="form-control"
                                           placeholder="ID">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="idFacturaPagoInforme">ID Factura</label>
                                    <input type="number"
                                           id="idFacturaPagoInforme"
                                           class="form-control"
                                           placeholder="ID Factura">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="numeroFacturaPagoInforme">N° Factura</label>
                                    <input type="text"
                                           id="numeroFacturaPagoInforme"
                                           class="form-control"
                                           placeholder="N° Factura">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="numeroReciboPagoInforme">N° Recibo</label>
                                    <input type="text"
                                           id="numeroReciboPagoInforme"
                                           class="form-control"
                                           placeholder="N° Recibo">
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="clientePagoInforme">Cliente</label>
                                    <input type="text"
                                           id="clientePagoInforme"
                                           class="form-control"
                                           placeholder="Nombre o apellido">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="fechaPagoInforme">Fecha Pago</label>
                                    <input type="date"
                                           id="fechaPagoInforme"
                                           class="form-control">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="periodoPagoInforme">Período</label>
                                    <input type="text"
                                           id="periodoPagoInforme"
                                           class="form-control"
                                           placeholder="Período">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="metodoPagoInforme">Método</label>
                                    <input type="text"
                                           id="metodoPagoInforme"
                                           class="form-control"
                                           placeholder="Método">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="estadoPagoInforme">Estado</label>
                                    <select id="estadoPagoInforme"
                                            class="form-control">
                                        <option value="">Todos</option>
                                        <option value="PAGADO">Pagado</option>
                                        <option value="ANULADO">Anulado</option>
                                        <option value="PENDIENTE">Pendiente</option>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="row justify-content-end">
                            <div class="col-md-3 text-right d-flex align-items-end justify-content-end">
                                <button type="button"
                                        class="btn btn-primary mr-2"
                                        onclick="buscarPagosInforme()">
                                    <i class="fas fa-search mr-1"></i>
                                    Buscar
                                </button>

                                <button type="button"
                                        class="btn btn-secondary"
                                        onclick="limpiarFiltrosPagos()">
                                    <i class="fas fa-eraser mr-1"></i>
                                    Limpiar
                                </button>
                            </div>
                        </div>

                    </div>

                    <div id="resultadoInformePagos"
                         class="resultado-informe">
                    </div>

                </div>

            </div>
        </div>
    </div>


    <!-- PANEL DE INFORME DE PENDIENTES -->
    <div id="panelInformePendientes" style="display:none; margin-top:10px;">

        <div class="card card-primary">

            <div class="card-header">
                <h3 class="card-title">
                    <i class="fas fa-clock mr-1"></i>
                    Informe de Pendientes
                </h3>

                <div class="card-tools">
                    <button type="button"
                            class="btn btn-tool"
                            onclick="cerrarInformePendientes()"
                            title="Cerrar">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            </div>

            <div class="card-body">

                <div class="filtros-informe">
                    <div class="row align-items-end">

                        <div class="col-md-3">
                            <div class="form-group mb-md-0">
                                <label>
                                    <i class="fas fa-info-circle mr-1"></i>
                                    Estado de cuenta
                                </label>
                                <input type="text"
                                       class="form-control"
                                       value="PENDIENTE"
                                       readonly>
                            </div>
                        </div>

                        <div class="col-md-2">
                            <div class="form-group mb-md-0">
                                <label for="fechaInicioPendienteInforme">
                                    Fecha de Inicio
                                </label>
                                <input type="date"
                                       id="fechaInicioPendienteInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-2">
                            <div class="form-group mb-md-0">
                                <label for="fechaFinPendienteInforme">
                                    Fecha de Fin
                                </label>
                                <input type="date"
                                       id="fechaFinPendienteInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-5">
                            <button type="button"
                                    class="btn btn-primary btn-block"
                                    onclick="generarInformeGeneralPendientes()">
                                <i class="fas fa-file-pdf mr-1"></i>
                                Generar Informe General
                            </button>
                        </div>

                    </div>
                </div>

                <div class="mt-4">

                    <h6 class="font-weight-bold mb-3">
                        <i class="fas fa-search mr-1"></i>
                        Buscar Pendientes
                    </h6>

                    <div class="filtros-informe">

                        <div class="row">
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="idClientePendienteInforme">ID Cliente</label>
                                    <input type="number"
                                           id="idClientePendienteInforme"
                                           class="form-control"
                                           placeholder="ID">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="clientePendienteInforme">Cliente</label>
                                    <input type="text"
                                           id="clientePendienteInforme"
                                           class="form-control"
                                           placeholder="Nombre o apellido">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="cedulaPendienteInforme">Cédula</label>
                                    <input type="text"
                                           id="cedulaPendienteInforme"
                                           class="form-control"
                                           placeholder="Cédula">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="barrioPendienteInforme">Barrio</label>
                                    <input type="text"
                                           id="barrioPendienteInforme"
                                           class="form-control"
                                           placeholder="Barrio">
                                </div>
                            </div>
                        </div>

                        <div class="row justify-content-end">
                            <div class="col-md-3 text-right d-flex align-items-end justify-content-end">
                                <button type="button"
                                        class="btn btn-primary mr-2"
                                        onclick="buscarPendientesInforme()">
                                    <i class="fas fa-search mr-1"></i>
                                    Buscar
                                </button>

                                <button type="button"
                                        class="btn btn-secondary"
                                        onclick="limpiarFiltrosPendientes()">
                                    <i class="fas fa-eraser mr-1"></i>
                                    Limpiar
                                </button>
                            </div>
                        </div>

                    </div>

                    <div id="resultadoInformePendientes"
                         class="resultado-informe">
                    </div>

                </div>

            </div>
        </div>
    </div>

    <!-- GESTIÓN FINANCIERA -->
    <div class="informes-seccion financiero">

        <div class="informes-seccion-titulo">
            <span class="icono-seccion"><i class="fas fa-file-invoice-dollar"></i></span>
            Gestión Financiera
            <span class="cantidad">4 informes</span>
        </div>

        <div class="informes-grid">

            <!-- TARIFAS -->
            <div class="informe-col">
                <div class="informe-card color-warning" onclick="mostrarInformeTarifas()">
                    <div class="informe-card-body">
                        <div class="informe-icon">
                            <i class="fas fa-tags"></i>
                        </div>
                        <div class="informe-info">
                            <h5>Tarifas</h5>
                            <p>Informes de tarifas y vigencias registradas.</p>
                        </div>
                    </div>
                    <div class="informe-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </div>
            </div>

            <!-- FACTURACIÓN -->
            <div class="informe-col">
                <div class="informe-card color-indigo" onclick="mostrarInformeFacturacion()">
                    <div class="informe-card-body">
                        <div class="informe-icon">
                            <i class="fas fa-file-invoice"></i>
                        </div>
                        <div class="informe-info">
                            <h5>Facturación</h5>
                            <p>Informes del proceso de facturación y comprobantes.</p>
                        </div>
                    </div>
                    <div class="informe-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </div>
            </div>

            <!-- PAGOS -->
            <div class="informe-col">
                <div class="informe-card color-olive" onclick="mostrarInformePagos()">
                    <div class="informe-card-body">
                        <div class="informe-icon">
                            <i class="fas fa-money-bill-wave"></i>
                        </div>
                        <div class="informe-info">
                            <h5>Pagos</h5>
                            <p>Informes de pagos realizados y métodos utilizados.</p>
                        </div>
                    </div>
                    <div class="informe-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </div>
            </div>

            <!-- PENDIENTES -->
            <div class="informe-col">
                <div class="informe-card color-danger" onclick="mostrarInformePendientes()">
                    <div class="informe-card-body">
                        <div class="informe-icon">
                            <i class="fas fa-clock"></i>
                        </div>
                        <div class="informe-info">
                            <h5>Pendientes</h5>
                            <p>Informes de facturas pendientes y vencidas.</p>
                        </div>
                    </div>
                    <div class="informe-arrow">
                        <i class="fas fa-chevron-right"></i>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <!-- PANEL DE SERVICIOS -->
    <div id="panelInformeServicios" style="display:none; margin-top:10px;">

        <div class="card">

            <div class="card-header">
                <div class="panel-informe-header">
                    <h5>
                        <i class="fas fa-concierge-bell mr-2"></i>
                        Informe de Servicios
                    </h5>

                    <button type="button"
                            class="btn btn-sm btn-outline-secondary"
                            onclick="cerrarInformeServicios()">
                        <i class="fas fa-times"></i>
                        Cerrar
                    </button>
                </div>
            </div>

            <div class="card-body">

                <!-- INFORME GENERAL POR PERÍODO -->
                <div class="filtros-informe">
                    <div class="row align-items-end">

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaDesdeServicioInforme">
                                    <i class="fas fa-calendar-day mr-1"></i>
                                    Fecha Inicio
                                </label>
                                <input type="date"
                                       id="fechaDesdeServicioInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaHastaServicioInforme">
                                    <i class="fas fa-calendar-check mr-1"></i>
                                    Fecha Fin
                                </label>
                                <input type="date"
                                       id="fechaHastaServicioInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <button type="button"
                                    class="btn btn-primary btn-block"
                                    onclick="generarInformeGeneralServicios()">
                                <i class="fas fa-file-pdf mr-1"></i>
                                Generar Informe General
                            </button>
                        </div>

                    </div>
                </div>

                <!-- BÚSQUEDA INDIVIDUAL -->
                <div class="mt-4">

                    <h6 class="font-weight-bold mb-3">
                        <i class="fas fa-search mr-1"></i>
                        Buscar Servicios
                    </h6>

                    <div class="filtros-informe">

                        <div class="row">

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="idServicioInforme">ID</label>
                                    <input type="number"
                                           id="idServicioInforme"
                                           class="form-control"
                                           placeholder="ID">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="tipoServicioInforme">Tipo de Servicio</label>
                                    <input type="text"
                                           id="tipoServicioInforme"
                                           class="form-control"
                                           placeholder="Tipo">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="clienteServicioInforme">Cliente</label>
                                    <select id="clienteServicioInforme"
                                            class="form-control">
                                        <option value="">Todos</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="barrioServicioInforme">Barrio</label>
                                    <select id="barrioServicioInforme"
                                            class="form-control">
                                        <option value="">Todos</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="frecuenciaServicioInforme">Frecuencia</label>
                                    <select id="frecuenciaServicioInforme"
                                            class="form-control">
                                        <option value="">Todas</option>
                                        <option value="DIARIA">Diaria</option>
                                        <option value="SEMANAL">Semanal</option>
                                        <option value="QUINCENAL">Quincenal</option>
                                        <option value="MENSUAL">Mensual</option>
                                        <option value="BIMENSUAL">Bimensual</option>
                                        <option value="TRIMESTRAL">Trimestral</option>
                                        <option value="SEMESTRAL">Semestral</option>
                                        <option value="ANUAL">Anual</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="estadoServicioInforme">Estado</label>
                                    <select id="estadoServicioInforme"
                                            class="form-control">
                                        <option value="">Todos</option>
                                        <option value="ACTIVO">Activo</option>
                                        <option value="SUSPENDIDO">Suspendido</option>
                                        <option value="CANCELADO">Cancelado</option>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-12 text-right">
                                <button type="button"
                                        class="btn btn-primary"
                                        onclick="buscarServiciosInforme()">
                                    <i class="fas fa-search mr-1"></i>
                                    Buscar
                                </button>

                                <button type="button"
                                        class="btn btn-secondary"
                                        onclick="limpiarFiltrosServicios()">
                                    <i class="fas fa-eraser mr-1"></i>
                                    Limpiar
                                </button>
                            </div>
                        </div>

                    </div>

                    <div id="resultadoInformeServicios"
                         class="resultado-informe">
                    </div>

                </div>

            </div>
        </div>
    </div>

    <!-- PANEL DE TARIFAS -->
    <div id="panelInformeTarifas" style="display:none; margin-top:10px;">

        <div class="card">

            <div class="card-header">
                <div class="panel-informe-header">
                    <h5>
                        <i class="fas fa-tags mr-2"></i>
                        Informe de Tarifas
                    </h5>

                    <button type="button"
                            class="btn btn-sm btn-outline-secondary"
                            onclick="cerrarInformeTarifas()">
                        <i class="fas fa-times"></i>
                        Cerrar
                    </button>
                </div>
            </div>

            <div class="card-body">

                <!-- INFORME GENERAL POR PERÍODO -->
                <div class="filtros-informe">
                    <div class="row align-items-end">

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaDesdeTarifaInforme">
                                    <i class="fas fa-calendar-day mr-1"></i>
                                    Fecha Inicio
                                </label>
                                <input type="date"
                                       id="fechaDesdeTarifaInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group mb-md-0">
                                <label for="fechaHastaTarifaInforme">
                                    <i class="fas fa-calendar-check mr-1"></i>
                                    Fecha Fin
                                </label>
                                <input type="date"
                                       id="fechaHastaTarifaInforme"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <button type="button"
                                    class="btn btn-primary btn-block"
                                    onclick="generarInformeGeneralTarifas()">
                                <i class="fas fa-file-pdf mr-1"></i>
                                Generar Informe General
                            </button>
                        </div>

                    </div>

                    <small class="text-muted d-block mt-2">
                        Incluye las tarifas que estuvieron vigentes en algún momento del período.
                    </small>
                </div>

                <!-- BÚSQUEDA INDIVIDUAL -->
                <div class="mt-4">

                    <h6 class="font-weight-bold mb-3">
                        <i class="fas fa-search mr-1"></i>
                        Buscar Tarifas
                    </h6>

                    <div class="filtros-informe">

                        <div class="row">

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="idTarifaInforme">ID</label>
                                    <input type="number"
                                           id="idTarifaInforme"
                                           class="form-control"
                                           placeholder="ID">
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="nombreTarifaInforme">Nombre</label>
                                    <input type="text"
                                           id="nombreTarifaInforme"
                                           class="form-control"
                                           placeholder="Nombre de la tarifa">
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="periodicidadTarifaInforme">Periodicidad</label>
                                    <select id="periodicidadTarifaInforme"
                                            class="form-control">
                                        <option value="">Todas</option>
                                        <option value="MENSUAL">Mensual</option>
                                        <option value="TRIMESTRAL">Trimestral</option>
                                        <option value="SEMESTRAL">Semestral</option>
                                        <option value="ANUAL">Anual</option>
                                        <option value="UNICO">Único</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="estadoTarifaInforme">Estado</label>
                                    <select id="estadoTarifaInforme"
                                            class="form-control">
                                        <option value="">Todos</option>
                                        <option value="ACTIVO">Activo</option>
                                        <option value="INACTIVO">Inactivo</option>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-12 text-right">
                                <button type="button"
                                        class="btn btn-primary"
                                        onclick="buscarTarifasInforme()">
                                    <i class="fas fa-search mr-1"></i>
                                    Buscar
                                </button>

                                <button type="button"
                                        class="btn btn-secondary"
                                        onclick="limpiarFiltrosTarifas()">
                                    <i class="fas fa-eraser mr-1"></i>
                                    Limpiar
                                </button>
                            </div>
                        </div>

                    </div>

                    <div id="resultadoInformeTarifas"
                         class="resultado-informe">
                    </div>

                </div>

            </div>
        </div>
    </div>

</div>

<script>

    /* TARIFAS */

    function mostrarInformeTarifas() {

        $("[id^='panelInforme']").not("#panelInformeTarifas").slideUp(150);

        $("#panelInformeTarifas").slideDown(200);

        $("html, body").animate({
            scrollTop: $("#panelInformeTarifas").offset().top - 70
        }, 300);
    }

    function cerrarInformeTarifas() {
        $("#panelInformeTarifas").slideUp(200);
        $("#resultadoInformeTarifas").html("");
    }

    function generarInformeGeneralTarifas() {

        var desde = $("#fechaDesdeTarifaInforme").val();
        var hasta = $("#fechaHastaTarifaInforme").val();

        if (!desde || !hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Inicio y la Fecha Fin."
            });
            return;
        }

        if (desde > hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fechas incorrectas",
                text: "La Fecha Inicio no puede ser posterior a la Fecha Fin."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=tarifas" +
            "&desde=" + encodeURIComponent(desde) +
            "&hasta=" + encodeURIComponent(hasta);

        window.open(url, "_blank");
    }

    function buscarTarifasInforme() {

        $("#resultadoInformeTarifas").html(
            '<div class="text-center p-4">' +
                '<i class="fas fa-spinner fa-spin fa-2x"></i>' +
                '<div class="mt-2 text-muted">Buscando tarifas...</div>' +
            '</div>'
        );

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "buscarTarifasInforme",
                id_tarifa: $("#idTarifaInforme").val(),
                nombre: $("#nombreTarifaInforme").val(),
                periodicidad: $("#periodicidadTarifaInforme").val(),
                estado: $("#estadoTarifaInforme").val()
            },

            success: function(html) {
                $("#resultadoInformeTarifas").html(html);
            },

            error: function(xhr) {
                $("#resultadoInformeTarifas").html(
                    '<div class="alert alert-danger">' +
                        '<i class="fas fa-exclamation-triangle mr-1"></i>' +
                        'Error al realizar la búsqueda.' +
                    '</div>'
                );
                console.error(xhr.responseText);
            }
        });
    }

    function generarInformeIndividualTarifa() {

        var seleccionado =
            $('input[name="tarifaSeleccionada"]:checked');

        if (seleccionado.length === 0) {
            Swal.fire({
                icon: "warning",
                title: "Seleccione una tarifa",
                text: "Debe seleccionar una tarifa para generar el informe individual."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=tarifaIndividual" +
            "&idTarifa=" + encodeURIComponent(seleccionado.val());

        window.open(url, "_blank");
    }

    function limpiarFiltrosTarifas() {
        $("#idTarifaInforme").val("");
        $("#nombreTarifaInforme").val("");
        $("#periodicidadTarifaInforme").val("");
        $("#estadoTarifaInforme").val("");
        $("#resultadoInformeTarifas").html("");
    }

    /* SERVICIOS */

    function mostrarInformeServicios() {

        $("#panelInformeTarifas").slideUp(150);


        $("#panelInformeAsignaciones").slideUp(150);

        $("#panelInformeServicios").slideDown(200);

        cargarFiltrosServicios();

        $("html, body").animate({
            scrollTop: $("#panelInformeServicios").offset().top - 70
        }, 300);
    }

    function cerrarInformeServicios() {
        $("#panelInformeServicios").slideUp(200);
        $("#resultadoInformeServicios").html("");
    }

    function cargarFiltrosServicios() {

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "filtrosServicios"
            },
            dataType: "json",

            success: function(data) {

                var selectCliente = $("#clienteServicioInforme");
                var selectBarrio = $("#barrioServicioInforme");

                selectCliente.empty();
                selectCliente.append('<option value="">Todos</option>');

                if (data.clientes) {
                    $.each(data.clientes, function(index, item) {
                        selectCliente.append(
                            $("<option>", {
                                value: item.id,
                                text: item.nombre
                            })
                        );
                    });
                }

                selectBarrio.empty();
                selectBarrio.append('<option value="">Todos</option>');

                if (data.barrios) {
                    $.each(data.barrios, function(index, item) {
                        selectBarrio.append(
                            $("<option>", {
                                value: item.id,
                                text: item.nombre
                            })
                        );
                    });
                }
            },

            error: function(xhr) {
                console.error("Error al cargar filtros de servicios:", xhr.responseText);

                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "No se pudieron cargar los filtros de servicios."
                });
            }
        });
    }

    function generarInformeGeneralServicios() {

        var desde = $("#fechaDesdeServicioInforme").val();
        var hasta = $("#fechaHastaServicioInforme").val();

        if (!desde) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Inicio."
            });
            return;
        }

        if (!hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Fin."
            });
            return;
        }

        if (desde > hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fechas incorrectas",
                text: "La Fecha Inicio no puede ser posterior a la Fecha Fin."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=servicios" +
            "&desde=" + encodeURIComponent(desde) +
            "&hasta=" + encodeURIComponent(hasta);

        window.open(url, "_blank");
    }

    function buscarServiciosInforme() {

        var id = $("#idServicioInforme").val();
        var tipo = $("#tipoServicioInforme").val();
        var cliente = $("#clienteServicioInforme").val();
        var barrio = $("#barrioServicioInforme").val();
        var frecuencia = $("#frecuenciaServicioInforme").val();
        var estado = $("#estadoServicioInforme").val();

        $("#resultadoInformeServicios").html(
            '<div class="text-center p-4">' +
                '<i class="fas fa-spinner fa-spin fa-2x"></i>' +
                '<div class="mt-2 text-muted">Buscando servicios...</div>' +
            '</div>'
        );

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "buscarServiciosInforme",
                id_servicio: id,
                tipo_servicio: tipo,
                id_cliente: cliente,
                id_barrio: barrio,
                frecuencia: frecuencia,
                estado: estado
            },

            success: function(html) {
                $("#resultadoInformeServicios").html(html);
            },

            error: function(xhr) {
                $("#resultadoInformeServicios").html(
                    '<div class="alert alert-danger">' +
                        '<i class="fas fa-exclamation-triangle mr-1"></i>' +
                        'Error al realizar la búsqueda.' +
                    '</div>'
                );
                console.error(xhr.responseText);
            }
        });
    }

    function generarInformeIndividualServicio() {

        var seleccionado =
            $('input[name="servicioSeleccionado"]:checked');

        if (seleccionado.length === 0) {
            Swal.fire({
                icon: "warning",
                title: "Seleccione un servicio",
                text: "Debe seleccionar un servicio para generar el informe individual."
            });
            return;
        }

        var id = seleccionado.val();

        var url =
            "InformeController" +
            "?action=servicioIndividual" +
            "&idServicio=" + encodeURIComponent(id);

        window.open(url, "_blank");
    }

    function limpiarFiltrosServicios() {
        $("#idServicioInforme").val("");
        $("#tipoServicioInforme").val("");
        $("#clienteServicioInforme").val("");
        $("#barrioServicioInforme").val("");
        $("#frecuenciaServicioInforme").val("");
        $("#estadoServicioInforme").val("");
        $("#resultadoInformeServicios").html("");
    }

    /* ASIGNACIONES */

    function mostrarInformeAsignaciones() {

        $("#panelInformeTarifas").slideUp(150);


        $("#panelInformeServicios").slideUp(150);

        $("#panelInformeAsignaciones").slideDown(200);

        cargarFiltrosAsignaciones();

        $("html, body").animate({
            scrollTop: $("#panelInformeAsignaciones").offset().top - 70
        }, 300);
    }

    function cerrarInformeAsignaciones() {

        $("#panelInformeAsignaciones").slideUp(200);

        $("#resultadoInformeAsignaciones").html("");
    }

    function cargarFiltrosAsignaciones() {

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "filtrosAsignaciones"
            },
            dataType: "json",

            success: function(data) {

                var selectRuta = $("#rutaAsignacionInforme");
                var selectVehiculo = $("#vehiculoAsignacionInforme");
                var selectPersonal = $("#personalAsignacionInforme");

                selectRuta.empty();
                selectRuta.append('<option value="">Todas</option>');

                if (data.rutas) {
                    $.each(data.rutas, function(index, item) {
                        selectRuta.append(
                            $("<option>", {
                                value: item.id,
                                text: item.nombre
                            })
                        );
                    });
                }

                selectVehiculo.empty();
                selectVehiculo.append('<option value="">Todos</option>');

                if (data.vehiculos) {
                    $.each(data.vehiculos, function(index, item) {
                        selectVehiculo.append(
                            $("<option>", {
                                value: item.id,
                                text: item.nombre
                            })
                        );
                    });
                }

                selectPersonal.empty();
                selectPersonal.append('<option value="">Todos</option>');

                if (data.personal) {
                    $.each(data.personal, function(index, item) {
                        selectPersonal.append(
                            $("<option>", {
                                value: item.id,
                                text: item.nombre
                            })
                        );
                    });
                }
            },

            error: function(xhr) {

                console.error(
                    "Error al cargar filtros de asignaciones:",
                    xhr.responseText
                );

                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "No se pudieron cargar los filtros de asignaciones."
                });
            }
        });
    }

    function generarInformeGeneralAsignaciones() {

        var desde = $("#fechaDesdeAsignacionInforme").val();
        var hasta = $("#fechaHastaAsignacionInforme").val();

        if (!desde) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Inicio."
            });
            return;
        }

        if (!hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Fin."
            });
            return;
        }

        if (desde > hasta) {
            Swal.fire({
                icon: "warning",
                title: "Rango de fechas inválido",
                text: "La Fecha Inicio no puede ser posterior a la Fecha Fin."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=asignaciones" +
            "&desde=" + encodeURIComponent(desde) +
            "&hasta=" + encodeURIComponent(hasta);

        window.open(url, "_blank");
    }

    function buscarAsignacionesInforme() {

        var id = $("#idAsignacionInforme").val();
        var fecha = $("#fechaAsignacionInforme").val();
        var ruta = $("#rutaAsignacionInforme").val();
        var vehiculo = $("#vehiculoAsignacionInforme").val();
        var personal = $("#personalAsignacionInforme").val();
        var estado = $("#estadoAsignacionInforme").val();

        $("#resultadoInformeAsignaciones").html(
            '<div class="text-center p-4">' +
                '<i class="fas fa-spinner fa-spin fa-2x"></i>' +
                '<div class="mt-2 text-muted">Buscando asignaciones...</div>' +
            '</div>'
        );

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "buscarAsignacionesInforme",
                id_asignacion: id,
                fecha: fecha,
                id_ruta: ruta,
                id_vehiculo: vehiculo,
                id_personal: personal,
                estado: estado
            },

            success: function(html) {
                $("#resultadoInformeAsignaciones").html(html);
            },

            error: function(xhr) {

                $("#resultadoInformeAsignaciones").html(
                    '<div class="alert alert-danger">' +
                        '<i class="fas fa-exclamation-triangle mr-1"></i>' +
                        'Error al realizar la búsqueda.' +
                    '</div>'
                );

                console.error(xhr.responseText);
            }
        });
    }

    function generarInformeIndividualAsignacion() {

        var seleccionado =
            $('input[name="asignacionSeleccionada"]:checked');

        if (seleccionado.length === 0) {

            Swal.fire({
                icon: "warning",
                title: "Seleccione una asignación",
                text: "Debe seleccionar una asignación para generar el informe individual."
            });

            return;
        }

        var id = seleccionado.val();

        var url =
            "InformeController" +
            "?action=asignacionIndividual" +
            "&idAsignacion=" +
            encodeURIComponent(id);

        window.open(url, "_blank");
    }

    function limpiarFiltrosAsignaciones() {

        $("#fechaDesdeAsignacionInforme").val("");
        $("#fechaHastaAsignacionInforme").val("");
        $("#idAsignacionInforme").val("");
        $("#fechaAsignacionInforme").val("");
        $("#rutaAsignacionInforme").val("");
        $("#vehiculoAsignacionInforme").val("");
        $("#personalAsignacionInforme").val("");
        $("#estadoAsignacionInforme").val("");
        $("#resultadoInformeAsignaciones").html("");
    }


    /* RECOLECCIONES */

    function mostrarInformeRecolecciones() {

        $("#panelInformeTarifas").slideUp(150);


        $("#panelInformeServicios").slideUp(150);
        $("#panelInformeAsignaciones").slideUp(150);

        $("#panelInformeRecolecciones").slideDown(200);

        cargarFiltrosRecolecciones();

        $("html, body").animate({
            scrollTop: $("#panelInformeRecolecciones").offset().top - 70
        }, 300);
    }

    function cerrarInformeRecolecciones() {
        $("#panelInformeRecolecciones").slideUp(200);
        $("#resultadoInformeRecolecciones").html("");
    }

    function cargarFiltrosRecolecciones() {

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "filtrosRecolecciones"
            },
            dataType: "json",

            success: function(data) {

                var selectRuta = $("#rutaRecoleccionInforme");
                var selectVehiculo = $("#vehiculoRecoleccionInforme");
                var selectPersonal = $("#personalRecoleccionInforme");

                selectRuta.empty();
                selectRuta.append('<option value="">Todas</option>');

                if (data.rutas) {
                    $.each(data.rutas, function(index, item) {
                        selectRuta.append(
                            $("<option>", {
                                value: item.id,
                                text: item.nombre
                            })
                        );
                    });
                }

                selectVehiculo.empty();
                selectVehiculo.append('<option value="">Todos</option>');

                if (data.vehiculos) {
                    $.each(data.vehiculos, function(index, item) {
                        selectVehiculo.append(
                            $("<option>", {
                                value: item.id,
                                text: item.nombre
                            })
                        );
                    });
                }

                selectPersonal.empty();
                selectPersonal.append('<option value="">Todos</option>');

                if (data.personal) {
                    $.each(data.personal, function(index, item) {
                        selectPersonal.append(
                            $("<option>", {
                                value: item.id,
                                text: item.nombre
                            })
                        );
                    });
                }
            },

            error: function(xhr) {
                console.error("Error al cargar filtros de recolecciones:", xhr.responseText);

                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "No se pudieron cargar los filtros de recolecciones."
                });
            }
        });
    }

    function generarInformeGeneralRecolecciones() {

        var desde = $("#fechaDesdeRecoleccionInforme").val();
        var hasta = $("#fechaHastaRecoleccionInforme").val();

        if (!desde) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Inicio."
            });
            return;
        }

        if (!hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Fin."
            });
            return;
        }

        if (desde > hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fechas incorrectas",
                text: "La Fecha Inicio no puede ser posterior a la Fecha Fin."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=recolecciones" +
            "&desde=" + encodeURIComponent(desde) +
            "&hasta=" + encodeURIComponent(hasta);

        window.open(url, "_blank");
    }

    function buscarRecoleccionesInforme() {

        var id = $("#idRecoleccionInforme").val();
        var fecha = $("#fechaRecoleccionInforme").val();
        var ruta = $("#rutaRecoleccionInforme").val();
        var vehiculo = $("#vehiculoRecoleccionInforme").val();
        var personal = $("#personalRecoleccionInforme").val();
        var estado = $("#estadoRecoleccionInforme").val();

        $("#resultadoInformeRecolecciones").html(
            '<div class="text-center p-4">' +
                '<i class="fas fa-spinner fa-spin fa-2x"></i>' +
                '<div class="mt-2 text-muted">Buscando recolecciones...</div>' +
            '</div>'
        );

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "buscarRecoleccionesInforme",
                id_recoleccion: id,
                fecha: fecha,
                id_ruta: ruta,
                id_vehiculo: vehiculo,
                id_personal: personal,
                estado: estado
            },

            success: function(html) {
                $("#resultadoInformeRecolecciones").html(html);
            },

            error: function(xhr) {
                $("#resultadoInformeRecolecciones").html(
                    '<div class="alert alert-danger">' +
                        '<i class="fas fa-exclamation-triangle mr-1"></i>' +
                        'Error al realizar la búsqueda.' +
                    '</div>'
                );
                console.error(xhr.responseText);
            }
        });
    }

    function generarInformeIndividual() {

        var seleccionado =
            $('input[name="recoleccionSeleccionada"]:checked');

        if (seleccionado.length === 0) {
            Swal.fire({
                icon: "warning",
                title: "Seleccione una recolección",
                text: "Debe seleccionar una recolección para generar el informe individual."
            });
            return;
        }

        var id = seleccionado.val();

        var url =
            "InformeController" +
            "?action=recoleccionIndividual" +
            "&id_recoleccion=" + encodeURIComponent(id);

        window.open(url, "_blank");
    }

    function limpiarFiltrosRecolecciones() {
        $("#fechaDesdeRecoleccionInforme").val("");
        $("#fechaHastaRecoleccionInforme").val("");
        $("#idRecoleccionInforme").val("");
        $("#fechaRecoleccionInforme").val("");
        $("#rutaRecoleccionInforme").val("");
        $("#vehiculoRecoleccionInforme").val("");
        $("#personalRecoleccionInforme").val("");
        $("#estadoRecoleccionInforme").val("");
        $("#resultadoInformeRecolecciones").html("");
    }

    /* CLASIFICACIÓN DE RESIDUOS */

    function mostrarInformeClasificaciones() {

        $("#panelInformeTarifas").slideUp(150);


        $("#panelInformeServicios").slideUp(150);
        $("#panelInformeAsignaciones").slideUp(150);
        $("#panelInformeRecolecciones").slideUp(150);

        $("#panelInformeClasificaciones").slideDown(200);

        $("html, body").animate({
            scrollTop: $("#panelInformeClasificaciones").offset().top - 70
        }, 300);
    }

    function cerrarInformeClasificaciones() {
        $("#panelInformeClasificaciones").slideUp(200);
        $("#resultadoInformeClasificaciones").html("");
    }

    function generarInformeGeneralClasificaciones() {

        var desde = $("#fechaDesdeClasificacionInforme").val();
        var hasta = $("#fechaHastaClasificacionInforme").val();

        if (!desde) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Inicio."
            });
            return;
        }

        if (!hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Fin."
            });
            return;
        }

        if (desde > hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fechas incorrectas",
                text: "La Fecha Inicio no puede ser posterior a la Fecha Fin."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=clasificaciones" +
            "&desde=" + encodeURIComponent(desde) +
            "&hasta=" + encodeURIComponent(hasta);

        window.open(url, "_blank");
    }

    function buscarClasificacionesInforme() {

        var id = $("#idClasificacionInforme").val();
        var idRecoleccion = $("#idRecoleccionClasificacionInforme").val();
        var tipo = $("#tipoClasificacionInforme").val();
        var fecha = $("#fechaClasificacionInforme").val();
        var estado = $("#estadoClasificacionInforme").val();

        $("#resultadoInformeClasificaciones").html(
            '<div class="text-center p-4">' +
                '<i class="fas fa-spinner fa-spin fa-2x"></i>' +
                '<div class="mt-2 text-muted">Buscando clasificaciones...</div>' +
            '</div>'
        );

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "buscarClasificacionesInforme",
                id_clasificacion: id,
                id_recoleccion: idRecoleccion,
                tipo_residuo: tipo,
                fecha: fecha,
                estado: estado
            },

            success: function(html) {
                $("#resultadoInformeClasificaciones").html(html);
            },

            error: function(xhr) {
                $("#resultadoInformeClasificaciones").html(
                    '<div class="alert alert-danger">' +
                        '<i class="fas fa-exclamation-triangle mr-1"></i>' +
                        'Error al realizar la búsqueda.' +
                    '</div>'
                );
                console.error(xhr.responseText);
            }
        });
    }

    function generarInformeIndividualClasificacion() {

        var seleccionado =
            $('input[name="clasificacionSeleccionada"]:checked');

        if (seleccionado.length === 0) {
            Swal.fire({
                icon: "warning",
                title: "Seleccione una clasificación",
                text: "Debe seleccionar una clasificación para generar el informe individual."
            });
            return;
        }

        var id = seleccionado.val();

        var url =
            "InformeController" +
            "?action=clasificacionIndividual" +
            "&id_clasificacion=" + encodeURIComponent(id);

        window.open(url, "_blank");
    }

    function limpiarFiltrosClasificaciones() {
        $("#fechaDesdeClasificacionInforme").val("");
        $("#fechaHastaClasificacionInforme").val("");
        $("#idClasificacionInforme").val("");
        $("#idRecoleccionClasificacionInforme").val("");
        $("#tipoClasificacionInforme").val("");
        $("#fechaClasificacionInforme").val("");
        $("#estadoClasificacionInforme").val("");
        $("#resultadoInformeClasificaciones").html("");
    }

    /* FACTURACIÓN */

    function mostrarInformeFacturacion() {

        $("#panelInformeTarifas").slideUp(150);


        $("#panelInformeServicios").slideUp(150);
        $("#panelInformeAsignaciones").slideUp(150);
        $("#panelInformeRecolecciones").slideUp(150);
        $("#panelInformeClasificaciones").slideUp(150);
        $("#panelInformePagos").slideUp(150);
        $("#panelInformeFacturacion").slideDown(200);

        $("html, body").animate({
            scrollTop: $("#panelInformeFacturacion").offset().top - 70
        }, 300);
    }

    function cerrarInformeFacturacion() {
        $("#panelInformeFacturacion").slideUp(200);
        $("#resultadoInformeFacturacion").html("");
    }

    function generarInformeGeneralFacturacion() {

        var desde = $("#fechaDesdeFacturacionInforme").val();
        var hasta = $("#fechaHastaFacturacionInforme").val();

        if (!desde) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Inicio."
            });
            return;
        }

        if (!hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Fin."
            });
            return;
        }

        if (desde > hasta) {
            Swal.fire({
                icon: "warning",
                title: "Rango de fechas inválido",
                text: "La Fecha Inicio no puede ser posterior a la Fecha Fin."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=facturacion" +
            "&desde=" + encodeURIComponent(desde) +
            "&hasta=" + encodeURIComponent(hasta);

        window.open(url, "_blank");
    }

    function buscarFacturasInforme() {

        var id = $("#idFacturaInforme").val();
        var numero = $("#numeroFacturaInforme").val();
        var cliente = $("#clienteFacturaInforme").val();
        var estado = $("#estadoFacturaInforme").val();
        var fecha = $("#fechaFacturaInforme").val();
        var periodo = $("#periodoFacturaInforme").val();
        var condicion = $("#condicionVentaFacturaInforme").val();

        $("#resultadoInformeFacturacion").html(
            '<div class="text-center p-4">' +
                '<i class="fas fa-spinner fa-spin fa-2x"></i>' +
                '<div class="mt-2 text-muted">Buscando facturas...</div>' +
            '</div>'
        );

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "buscarFacturasInforme",
                id_factura: id,
                numero_factura: numero,
                cliente: cliente,
                estado: estado,
                fecha_emision: fecha,
                periodo: periodo,
                condicion_venta: condicion
            },

            success: function(html) {
                $("#resultadoInformeFacturacion").html(html);
            },

            error: function(xhr) {
                $("#resultadoInformeFacturacion").html(
                    '<div class="alert alert-danger">' +
                        '<i class="fas fa-exclamation-triangle mr-1"></i>' +
                        'Error al realizar la búsqueda.' +
                    '</div>'
                );
                console.error(xhr.responseText);
            }
        });
    }

    function generarInformeIndividualFactura() {

        var seleccionado =
            $('input[name="facturaSeleccionada"]:checked');

        if (seleccionado.length === 0) {
            Swal.fire({
                icon: "warning",
                title: "Seleccione una factura",
                text: "Debe seleccionar una factura para generar el informe individual."
            });
            return;
        }

        var id = seleccionado.val();

        var url =
            "InformeController" +
            "?action=facturaIndividual" +
            "&id_factura=" + encodeURIComponent(id);

        window.open(url, "_blank");
    }

    function limpiarFiltrosFacturacion() {
        $("#fechaDesdeFacturacionInforme").val("");
        $("#fechaHastaFacturacionInforme").val("");
        $("#idFacturaInforme").val("");
        $("#numeroFacturaInforme").val("");
        $("#clienteFacturaInforme").val("");
        $("#estadoFacturaInforme").val("");
        $("#fechaFacturaInforme").val("");
        $("#periodoFacturaInforme").val("");
        $("#condicionVentaFacturaInforme").val("");
        $("#resultadoInformeFacturacion").html("");
    }

    /* PAGOS */

    function mostrarInformePagos() {

        $("#panelInformeTarifas").slideUp(150);


        $("#panelInformeServicios").slideUp(150);
        $("#panelInformeAsignaciones").slideUp(150);
        $("#panelInformeRecolecciones").slideUp(150);
        $("#panelInformeClasificaciones").slideUp(150);
        $("#panelInformeFacturacion").slideUp(150);
        $("#panelInformePagos").slideDown(200);

        $("html, body").animate({
            scrollTop: $("#panelInformePagos").offset().top - 70
        }, 300);
    }

    function cerrarInformePagos() {
        $("#panelInformePagos").slideUp(200);
        $("#resultadoInformePagos").html("");
    }

    function generarInformeGeneralPagos() {

        var desde = $("#fechaDesdePagosInforme").val();
        var hasta = $("#fechaHastaPagosInforme").val();

        if (!desde) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Inicio."
            });
            return;
        }

        if (!hasta) {
            Swal.fire({
                icon: "warning",
                title: "Fecha requerida",
                text: "Debe seleccionar la Fecha Fin."
            });
            return;
        }

        if (desde > hasta) {
            Swal.fire({
                icon: "warning",
                title: "Rango de fechas inválido",
                text: "La Fecha Inicio no puede ser posterior a la Fecha Fin."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=pagos" +
            "&desde=" + encodeURIComponent(desde) +
            "&hasta=" + encodeURIComponent(hasta);

        window.open(url, "_blank");
    }

    function buscarPagosInforme() {

        var id = $("#idPagoInforme").val();
        var idFactura = $("#idFacturaPagoInforme").val();
        var numeroFactura = $("#numeroFacturaPagoInforme").val();
        var numeroRecibo = $("#numeroReciboPagoInforme").val();
        var cliente = $("#clientePagoInforme").val();
        var fecha = $("#fechaPagoInforme").val();
        var periodo = $("#periodoPagoInforme").val();
        var metodoPago = $("#metodoPagoInforme").val();
        var estado = $("#estadoPagoInforme").val();

        $("#resultadoInformePagos").html(
            '<div class="text-center p-4">' +
                '<i class="fas fa-spinner fa-spin fa-2x"></i>' +
                '<div class="mt-2 text-muted">Buscando pagos...</div>' +
            '</div>'
        );

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "buscarPagosInforme",
                id_pago: id,
                id_factura: idFactura,
                numero_factura: numeroFactura,
                numero_recibo: numeroRecibo,
                cliente: cliente,
                fecha_pago: fecha,
                periodo: periodo,
                metodo_pago: metodoPago,
                estado: estado
            },

            success: function(html) {
                $("#resultadoInformePagos").html(html);
            },

            error: function(xhr) {
                $("#resultadoInformePagos").html(
                    '<div class="alert alert-danger">' +
                        '<i class="fas fa-exclamation-triangle mr-1"></i>' +
                        'Error al realizar la búsqueda.' +
                    '</div>'
                );
                console.error(xhr.responseText);
            }
        });
    }

    function generarInformeIndividualPago() {

        var seleccionado =
            $('input[name="pagoSeleccionado"]:checked');

        if (seleccionado.length === 0) {
            Swal.fire({
                icon: "warning",
                title: "Seleccione un pago",
                text: "Debe seleccionar un pago para generar el informe individual."
            });
            return;
        }

        var id = seleccionado.val();

        var url =
            "InformeController" +
            "?action=pagoIndividual" +
            "&id_pago=" + encodeURIComponent(id);

        window.open(url, "_blank");
    }

    function limpiarFiltrosPagos() {
        $("#fechaDesdePagosInforme").val("");
        $("#fechaHastaPagosInforme").val("");
        $("#idPagoInforme").val("");
        $("#idFacturaPagoInforme").val("");
        $("#numeroFacturaPagoInforme").val("");
        $("#numeroReciboPagoInforme").val("");
        $("#clientePagoInforme").val("");
        $("#fechaPagoInforme").val("");
        $("#periodoPagoInforme").val("");
        $("#metodoPagoInforme").val("");
        $("#estadoPagoInforme").val("");
        $("#resultadoInformePagos").html("");
    }

    /* PROCESOS A IMPLEMENTAR EN LA SIGUIENTE ETAPA */


    /* PENDIENTES */

    function mostrarInformePendientes() {

        $("#panelInformeTarifas").slideUp(150);


        $("#panelInformeServicios").slideUp(150);
        $("#panelInformeAsignaciones").slideUp(150);
        $("#panelInformeRecolecciones").slideUp(150);
        $("#panelInformeClasificaciones").slideUp(150);
        $("#panelInformeFacturacion").slideUp(150);
        $("#panelInformePagos").slideUp(150);
        $("#panelInformePendientes").slideDown(200);

        $("html, body").animate({
            scrollTop: $("#panelInformePendientes").offset().top - 70
        }, 300);
    }

    function cerrarInformePendientes() {
        $("#panelInformePendientes").slideUp(200);
        $("#resultadoInformePendientes").html("");
    }

    function generarInformeGeneralPendientes() {

        var fechaInicio = $("#fechaInicioPendienteInforme").val();
        var fechaFin = $("#fechaFinPendienteInforme").val();

        if (fechaInicio && fechaFin && fechaInicio > fechaFin) {
            Swal.fire({
                icon: "warning",
                title: "Rango de fechas inválido",
                text: "La Fecha de Inicio no puede ser posterior a la Fecha de Fin."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=pendientes" +
            "&fecha_inicio=" + encodeURIComponent(fechaInicio) +
            "&fecha_fin=" + encodeURIComponent(fechaFin);

        window.open(url, "_blank");
    }

    function buscarPendientesInforme() {

        var fechaInicio = $("#fechaInicioPendienteInforme").val();
        var fechaFin = $("#fechaFinPendienteInforme").val();
        var idCliente = $("#idClientePendienteInforme").val();
        var cliente = $("#clientePendienteInforme").val();
        var cedula = $("#cedulaPendienteInforme").val();
        var barrio = $("#barrioPendienteInforme").val();

        if (fechaInicio && fechaFin && fechaInicio > fechaFin) {
            Swal.fire({
                icon: "warning",
                title: "Rango de fechas inválido",
                text: "La Fecha de Inicio no puede ser posterior a la Fecha de Fin."
            });
            return;
        }

        $("#resultadoInformePendientes").html(
            '<div class="text-center p-4">' +
                '<i class="fas fa-spinner fa-spin fa-2x"></i>' +
                '<div class="mt-2 text-muted">Buscando pendientes...</div>' +
            '</div>'
        );

        $.ajax({
            url: "InformeController",
            type: "POST",
            data: {
                action: "buscarPendientesInforme",
                fecha_inicio: fechaInicio,
                fecha_fin: fechaFin,
                id_cliente: idCliente,
                cliente: cliente,
                cedula: cedula,
                barrio: barrio
            },

            success: function(html) {
                $("#resultadoInformePendientes").html(html);
            },

            error: function(xhr) {
                $("#resultadoInformePendientes").html(
                    '<div class="alert alert-danger">' +
                        '<i class="fas fa-exclamation-triangle mr-1"></i>' +
                        'Error al realizar la búsqueda.' +
                    '</div>'
                );
                console.error(xhr.responseText);
            }
        });
    }

    function generarInformeIndividualPendiente() {

        var seleccionado =
            $('input[name="pendienteSeleccionado"]:checked');

        if (seleccionado.length === 0) {
            Swal.fire({
                icon: "warning",
                title: "Seleccione un cliente",
                text: "Debe seleccionar un cliente pendiente para generar el informe individual."
            });
            return;
        }

        var id = seleccionado.val();

        var fechaInicio = $("#fechaInicioPendienteInforme").val();
        var fechaFin = $("#fechaFinPendienteInforme").val();

        if (fechaInicio && fechaFin && fechaInicio > fechaFin) {
            Swal.fire({
                icon: "warning",
                title: "Rango de fechas inválido",
                text: "La Fecha de Inicio no puede ser posterior a la Fecha de Fin."
            });
            return;
        }

        var url =
            "InformeController" +
            "?action=pendienteIndividual" +
            "&id_cliente=" + encodeURIComponent(id) +
            "&fecha_inicio=" + encodeURIComponent(fechaInicio) +
            "&fecha_fin=" + encodeURIComponent(fechaFin);

        window.open(url, "_blank");
    }

    function limpiarFiltrosPendientes() {
        $("#fechaInicioPendienteInforme").val("");
        $("#fechaFinPendienteInforme").val("");
        $("#idClientePendienteInforme").val("");
        $("#clientePendienteInforme").val("");
        $("#cedulaPendienteInforme").val("");
        $("#barrioPendienteInforme").val("");
        $("#resultadoInformePendientes").html("");
    }


</script>