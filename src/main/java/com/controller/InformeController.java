package com.controller;

import com.config.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@WebServlet(
        name = "InformeController",
        urlPatterns = {"/InformeController"}
)
public class InformeController extends HttpServlet {

    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // SERVICIOS

        if ("servicios".equals(action)) {
            generarInformeServicios(request, response);
            return;
        }

        if ("servicioIndividual".equals(action)) {
            generarInformeServicioIndividual(request, response);
            return;
        }

        // TARIFAS

        if ("tarifas".equals(action)) {
            generarInformeTarifas(request, response);
            return;
        }

        if ("tarifaIndividual".equals(action)) {
            generarInformeTarifaIndividual(request, response);
            return;
        }

        // ASIGNACIONES

        if ("asignaciones".equals(action)) {
            generarInformeAsignaciones(request, response);
            return;
        }

        if ("asignacionIndividual".equals(action)) {
            generarInformeAsignacionIndividual(request, response);
            return;
        }

        // RECOLECCIONES

        if ("recolecciones".equals(action)) {
            generarInformeRecolecciones(request, response);
            return;
        }

        if ("recoleccionIndividual".equals(action)) {
            generarInformeRecoleccionIndividual(request, response);
            return;
        }

        // CLASIFICACIÓN DE RESIDUOS

        if ("clasificaciones".equals(action)) {
            generarInformeClasificaciones(request, response);
            return;
        }

        if ("clasificacionIndividual".equals(action)) {
            generarInformeClasificacionIndividual(request, response);
            return;
        }

        // FACTURACIÓN

        if ("facturacion".equals(action)) {
            generarInformeFacturacion(request, response);
            return;
        }

        if ("facturaIndividual".equals(action)) {
            generarInformeFacturaIndividual(request, response);
            return;
        }

        // PAGOS

        if ("pagos".equals(action)) {
            generarInformePagos(request, response);
            return;
        }

        if ("pagoIndividual".equals(action)) {
            generarInformePagoIndividual(request, response);
            return;
        }

        // PENDIENTES

        if ("pendientes".equals(action)) {
            generarInformePendientes(request, response);
            return;
        }

        if ("pendienteIndividual".equals(action)) {
            generarInformePendienteIndividual(request, response);
            return;
        }

        // RESIDUOS

        if ("residuos".equals(action)) {
            generarInformeResiduos(request, response);
            return;
        }

        if ("tipoResiduoIndividual".equals(action)) {
            generarInformeTipoResiduoIndividual(request, response);
            return;
        }

        // VEHÍCULOS

        if ("vehiculos".equals(action)) {
            generarInformeVehiculos(request, response);
            return;
        }

        if ("vehiculoIndividual".equals(action)) {
            generarInformeVehiculoIndividual(request, response);
            return;
        }

        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Acción de informe no válida"
        );
    }

    // POST

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        // SERVICIOS

        if ("filtrosServicios".equals(action)) {
            cargarFiltrosServicios(request, response);
            return;
        }

        if ("buscarServiciosInforme".equals(action)) {
            buscarServiciosInforme(request, response);
            return;
        }

        // TARIFAS

        if ("buscarTarifasInforme".equals(action)) {
            buscarTarifasInforme(request, response);
            return;
        }

        // ASIGNACIONES

        if ("filtrosAsignaciones".equals(action)) {
            cargarFiltrosAsignaciones(request, response);
            return;
        }

        if ("buscarAsignacionesInforme".equals(action)) {
            buscarAsignacionesInforme(request, response);
            return;
        }

        // RECOLECCIONES

        if ("filtrosRecolecciones".equals(action)) {
            cargarFiltrosRecolecciones(request, response);
            return;
        }

        if ("buscarRecoleccionesInforme".equals(action)) {
            buscarRecoleccionesInforme(request, response);
            return;
        }

        // CLASIFICACIÓN DE RESIDUOS

        if ("buscarClasificacionesInforme".equals(action)) {
            buscarClasificacionesInforme(request, response);
            return;
        }

        // FACTURACIÓN

        if ("buscarFacturasInforme".equals(action)) {
            buscarFacturasInforme(request, response);
            return;
        }

        // PAGOS

        if ("buscarPagosInforme".equals(action)) {
            buscarPagosInforme(request, response);
            return;
        }

        // PENDIENTES

        if ("buscarPendientesInforme".equals(action)) {
            buscarPendientesInforme(request, response);
            return;
        }

        // RESIDUOS

        // RESIDUOS

        if ("buscarTiposResiduosInforme".equals(action)) {
            buscarTiposResiduosInforme(request, response);
            return;
        }

        // VEHÍCULOS

        if ("filtrosVehiculos".equals(action)) {
            cargarFiltrosVehiculos(request, response);
            return;
        }

        if ("buscarVehiculosInforme".equals(action)) {
            buscarVehiculosInforme(request, response);
            return;
        }

        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Acción de informe no válida"
        );
    }


    // INFORME GENERAL DE SERVICIOS

    private void generarInformeServicios(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String desdeParametro = request.getParameter("desde");
        String hastaParametro = request.getParameter("hasta");

        try {

            if (desdeParametro == null || desdeParametro.trim().isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Inicio.");
            }

            if (hastaParametro == null || hastaParametro.trim().isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Fin.");
            }

            Date fechaDesde = Date.valueOf(desdeParametro);
            Date fechaHasta = Date.valueOf(hastaParametro);

            if (fechaDesde.after(fechaHasta)) {
                throw new Exception("La Fecha Inicio no puede ser posterior a la Fecha Fin.");
            }

            String rutaReporte = "/WEB-INF/reportes/informe_servicios.jrxml";
            InputStream inputStream = getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte);
            }

            JasperReport jasperReport;
            try (InputStream reporteStream = inputStream) {
                jasperReport = JasperCompileManager.compileReport(reporteStream);
            }

            InputStream logoStream = getServletContext().getResourceAsStream("/WEB-INF/reportes/logo.png");
            if (logoStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png");
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("DESDE", fechaDesde);
            parametros.put("HASTA", fechaHasta);
            parametros.put("LOGO", logoStream);
            parametros.put("USUARIO_IMPRESION", obtenerUsuarioSesion(request));

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {
                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        con
                );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_servicios.pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // INFORME INDIVIDUAL DE SERVICIO

    private void generarInformeServicioIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String idTexto = limpiar(request.getParameter("idServicio"));

            if (idTexto.isEmpty()) {
                idTexto = limpiar(request.getParameter("id_servicio"));
            }

            if (idTexto.isEmpty()) {
                idTexto = limpiar(request.getParameter("id"));
            }

            if (idTexto.isEmpty()) {
                throw new Exception("No se recibió el ID del servicio.");
            }

            int idServicio = Integer.parseInt(idTexto);

            String rutaReporte = "/WEB-INF/reportes/informe_servicio_individual.jrxml";
            InputStream inputStream = getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte);
            }

            JasperReport jasperReport;
            try (InputStream reporteStream = inputStream) {
                jasperReport = JasperCompileManager.compileReport(reporteStream);
            }

            InputStream logoStream = getServletContext().getResourceAsStream("/WEB-INF/reportes/logo.png");
            if (logoStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png");
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idServicio", idServicio);
            parametros.put("LOGO", logoStream);
            parametros.put("USUARIO_IMPRESION", obtenerUsuarioSesion(request));

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {
                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        con
                );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_servicio_" + idServicio + ".pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // FILTROS DE SERVICIOS

    private void cargarFiltrosServicios(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        try {
            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {

                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                List<Map<String, String>> clientes = new ArrayList<>();
                List<Map<String, String>> barrios = new ArrayList<>();

                String sqlClientes =
                        "SELECT id_cliente, nombre || ' ' || apellido AS nombre " +
                        "FROM clientes WHERE estado = TRUE ORDER BY nombre, apellido";

                try (PreparedStatement ps = con.prepareStatement(sqlClientes);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, String> item = new HashMap<>();
                        item.put("id", String.valueOf(rs.getInt("id_cliente")));
                        item.put("nombre", rs.getString("nombre"));
                        clientes.add(item);
                    }
                }

                String sqlBarrios =
                        "SELECT id_barrio, nombre FROM barrio " +
                        "WHERE estado = TRUE ORDER BY nombre";

                try (PreparedStatement ps = con.prepareStatement(sqlBarrios);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, String> item = new HashMap<>();
                        item.put("id", String.valueOf(rs.getInt("id_barrio")));
                        item.put("nombre", rs.getString("nombre"));
                        barrios.add(item);
                    }
                }

                StringBuilder json = new StringBuilder();
                json.append("{");
                json.append("\"clientes\":").append(listaAJson(clientes)).append(",");
                json.append("\"barrios\":").append(listaAJson(barrios));
                json.append("}");

                response.getWriter().write(json.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    "{\"error\":\"" +
                    escaparJson(e.getMessage() != null ? e.getMessage() : "Error al cargar filtros") +
                    "\"}"
            );
        }
    }

    // BUSCAR SERVICIOS PARA INFORME INDIVIDUAL

    private void buscarServiciosInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        String id = limpiar(request.getParameter("id_servicio"));
        String tipo = limpiar(request.getParameter("tipo_servicio"));
        String cliente = limpiar(request.getParameter("id_cliente"));
        String barrio = limpiar(request.getParameter("id_barrio"));
        String frecuencia = limpiar(request.getParameter("frecuencia"));
        String estado = limpiar(request.getParameter("estado"));

        try {
            StringBuilder sql = new StringBuilder();
            sql.append(
                    "SELECT s.id_servicio, " +
                    "s.tipo_servicio, " +
                    "s.nombre_establecimiento, " +
                    "s.direccion_servicio, " +
                    "c.nombre || ' ' || c.apellido AS nombre_cliente, " +
                    "b.nombre AS nombre_barrio, " +
                    "s.frecuencia, s.fecha_inicio, s.fecha_fin, s.estado " +
                    "FROM servicio s " +
                    "INNER JOIN clientes c ON s.id_cliente = c.id_cliente " +
                    "LEFT JOIN barrio b ON s.id_barrio = b.id_barrio " +
                    "WHERE 1 = 1 "
            );

            List<Object> parametros = new ArrayList<>();

            if (!id.isEmpty()) {
                try {
                    sql.append("AND s.id_servicio = ? ");
                    parametros.add(Integer.parseInt(id));
                } catch (NumberFormatException e) {
                    throw new Exception("El ID del servicio debe ser numérico.");
                }
            }

            if (!tipo.isEmpty()) {
                sql.append("AND LOWER(s.tipo_servicio) LIKE LOWER(?) ");
                parametros.add("%" + tipo + "%");
            }

            if (!cliente.isEmpty()) {
                try {
                    sql.append("AND s.id_cliente = ? ");
                    parametros.add(Integer.parseInt(cliente));
                } catch (NumberFormatException e) {
                    throw new Exception("El cliente seleccionado no es válido.");
                }
            }

            if (!barrio.isEmpty()) {
                try {
                    sql.append("AND s.id_barrio = ? ");
                    parametros.add(Integer.parseInt(barrio));
                } catch (NumberFormatException e) {
                    throw new Exception("El barrio seleccionado no es válido.");
                }
            }

            if (!frecuencia.isEmpty()) {
                sql.append("AND s.frecuencia = ? ");
                parametros.add(frecuencia);
            }

            if (!estado.isEmpty()) {
                sql.append("AND s.estado = ? ");
                parametros.add(estado);
            }

            sql.append("ORDER BY s.id_servicio DESC");

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {
                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
                    for (int i = 0; i < parametros.size(); i++) {
                        Object valor = parametros.get(i);
                        if (valor instanceof Integer) {
                            ps.setInt(i + 1, (Integer) valor);
                        } else {
                            ps.setString(i + 1, String.valueOf(valor));
                        }
                    }

                    try (ResultSet rs = ps.executeQuery()) {
                        StringBuilder html = new StringBuilder();
                        html.append("<div class=\"table-responsive\">");
                        html.append("<table class=\"table table-bordered table-hover table-striped\">");
                        html.append("<thead><tr>");
                        html.append("<th style=\"width:50px;\">Sel.</th>");
                        html.append("<th>ID</th>");
                        html.append("<th>Cliente</th>");
                        html.append("<th>Tipo de Servicio</th>");
                        html.append("<th>Establecimiento</th>");
                        html.append("<th>Barrio</th>");
                        html.append("<th>Frecuencia</th>");
                        html.append("<th>Fecha Inicio</th>");
                        html.append("<th>Fecha Fin</th>");
                        html.append("<th>Estado</th>");
                        html.append("</tr></thead><tbody>");

                        int cantidadResultados = 0;

                        while (rs.next()) {
                            cantidadResultados++;
                            int idServicio = rs.getInt("id_servicio");
                            String estadoResultado = rs.getString("estado");

                            html.append("<tr>");
                            html.append("<td class=\"text-center\"><input type=\"radio\" name=\"servicioSeleccionado\" value=\"")
                                    .append(idServicio)
                                    .append("\"></td>");
                            html.append("<td>").append(idServicio).append("</td>");
                            html.append("<td>").append(escaparHtml(rs.getString("nombre_cliente"))).append("</td>");
                            html.append("<td>").append(escaparHtml(rs.getString("tipo_servicio"))).append("</td>");
                            html.append("<td>").append(escaparHtml(rs.getString("nombre_establecimiento"))).append("</td>");
                            html.append("<td>").append(escaparHtml(rs.getString("nombre_barrio"))).append("</td>");
                            html.append("<td>").append(escaparHtml(rs.getString("frecuencia"))).append("</td>");
                            html.append("<td>").append(escaparHtml(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toString() : "")).append("</td>");
                            html.append("<td>").append(escaparHtml(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toString() : "")).append("</td>");
                            html.append("<td class=\"text-center\"><span class=\"badge badge-secondary\">")
                                    .append(escaparHtml(estadoResultado))
                                    .append("</span></td>");
                            html.append("</tr>");
                        }

                        html.append("</tbody></table></div>");

                        if (cantidadResultados == 0) {
                            html.append(
                                    "<div class=\"alert alert-info mt-3\">" +
                                    "<i class=\"fas fa-info-circle mr-1\"></i>" +
                                    "No se encontraron servicios con los filtros seleccionados." +
                                    "</div>"
                            );
                        } else {
                            html.append(
                                    "<div class=\"mt-3 text-right\">" +
                                    "<button type=\"button\" class=\"btn btn-primary\" onclick=\"generarInformeIndividualServicio()\">" +
                                    "<i class=\"fas fa-file-pdf mr-1\"></i> Generar Informe Individual" +
                                    "</button></div>"
                            );
                        }

                        response.getWriter().write(html.toString());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    "<div class=\"alert alert-danger\">" +
                    "<i class=\"fas fa-exclamation-triangle mr-1\"></i>" +
                    escaparHtml(e.getMessage() != null ? e.getMessage() : "Error al realizar la búsqueda.") +
                    "</div>"
            );
        }
    }


    // INFORME GENERAL DE TARIFAS
    // Lista las tarifas que estuvieron vigentes en algún momento del período:
    // empezaron antes del fin del período y no terminaron antes de su inicio.

    private void generarInformeTarifas(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String desdeParametro = request.getParameter("desde");
        String hastaParametro = request.getParameter("hasta");

        try {

            if (desdeParametro == null || desdeParametro.trim().isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Inicio.");
            }

            if (hastaParametro == null || hastaParametro.trim().isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Fin.");
            }

            Date fechaDesde = Date.valueOf(desdeParametro);
            Date fechaHasta = Date.valueOf(hastaParametro);

            if (fechaDesde.after(fechaHasta)) {
                throw new Exception("La Fecha Inicio no puede ser posterior a la Fecha Fin.");
            }

            String rutaReporte = "/WEB-INF/reportes/informe_tarifas.jrxml";
            InputStream inputStream = getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte);
            }

            JasperReport jasperReport;
            try (InputStream reporteStream = inputStream) {
                jasperReport = JasperCompileManager.compileReport(reporteStream);
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("DESDE", fechaDesde);
            parametros.put("HASTA", fechaHasta);
            parametros.put("LOGO", getServletContext().getResourceAsStream("/WEB-INF/reportes/logo.png"));
            parametros.put("USUARIO_IMPRESION", obtenerUsuarioSesion(request));

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {
                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        con
                );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_tarifas.pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // INFORME INDIVIDUAL DE TARIFA

    private void generarInformeTarifaIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String idTexto = limpiar(request.getParameter("idTarifa"));

            if (idTexto.isEmpty()) {
                throw new Exception("No se recibió el ID de la tarifa.");
            }

            int idTarifa = Integer.parseInt(idTexto);

            String rutaReporte = "/WEB-INF/reportes/informe_tarifa_individual.jrxml";
            InputStream inputStream = getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte);
            }

            JasperReport jasperReport;
            try (InputStream reporteStream = inputStream) {
                jasperReport = JasperCompileManager.compileReport(reporteStream);
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_TARIFA", idTarifa);
            parametros.put("LOGO", getServletContext().getResourceAsStream("/WEB-INF/reportes/logo.png"));
            parametros.put("USUARIO_IMPRESION", obtenerUsuarioSesion(request));

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {
                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        con
                );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_tarifa_" + idTarifa + ".pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // BUSCAR TARIFAS PARA INFORME INDIVIDUAL

    private void buscarTarifasInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        String id = limpiar(request.getParameter("id_tarifa"));
        String nombre = limpiar(request.getParameter("nombre"));
        String periodicidad = limpiar(request.getParameter("periodicidad"));
        String estado = limpiar(request.getParameter("estado"));

        try {
            StringBuilder sql = new StringBuilder();
            sql.append(
                    "SELECT id_tarifa, nombre, descripcion, monto, periodicidad, " +
                    "fecha_inicio, fecha_fin, estado " +
                    "FROM tarifa WHERE 1 = 1 "
            );

            List<Object> parametros = new ArrayList<>();

            if (!id.isEmpty()) {
                try {
                    sql.append("AND id_tarifa = ? ");
                    parametros.add(Integer.parseInt(id));
                } catch (NumberFormatException e) {
                    throw new Exception("El ID de la tarifa debe ser numérico.");
                }
            }

            if (!nombre.isEmpty()) {
                sql.append("AND LOWER(nombre) LIKE LOWER(?) ");
                parametros.add("%" + nombre + "%");
            }

            if (!periodicidad.isEmpty()) {
                sql.append("AND periodicidad = ? ");
                parametros.add(periodicidad);
            }

            if ("ACTIVO".equals(estado)) {
                sql.append("AND estado = TRUE ");
            } else if ("INACTIVO".equals(estado)) {
                sql.append("AND estado = FALSE ");
            }

            sql.append("ORDER BY id_tarifa DESC");

            java.text.DecimalFormat formatoMonto = new java.text.DecimalFormat(
                    "#,##0",
                    new java.text.DecimalFormatSymbols(new java.util.Locale("es", "PY"))
            );

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {
                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
                    for (int i = 0; i < parametros.size(); i++) {
                        Object valor = parametros.get(i);
                        if (valor instanceof Integer) {
                            ps.setInt(i + 1, (Integer) valor);
                        } else {
                            ps.setString(i + 1, String.valueOf(valor));
                        }
                    }

                    try (ResultSet rs = ps.executeQuery()) {
                        StringBuilder html = new StringBuilder();
                        html.append("<div class=\"table-responsive\">");
                        html.append("<table class=\"table table-bordered table-hover table-striped\">");
                        html.append("<thead><tr>");
                        html.append("<th style=\"width:50px;\">Sel.</th>");
                        html.append("<th>ID</th>");
                        html.append("<th>Nombre</th>");
                        html.append("<th>Monto</th>");
                        html.append("<th>Periodicidad</th>");
                        html.append("<th>Fecha Inicio</th>");
                        html.append("<th>Fecha Fin</th>");
                        html.append("<th>Estado</th>");
                        html.append("</tr></thead><tbody>");

                        int cantidadResultados = 0;

                        while (rs.next()) {
                            cantidadResultados++;
                            int idTarifa = rs.getInt("id_tarifa");
                            boolean activa = rs.getBoolean("estado");

                            html.append("<tr>");
                            html.append("<td class=\"text-center\"><input type=\"radio\" name=\"tarifaSeleccionada\" value=\"")
                                    .append(idTarifa)
                                    .append("\"></td>");
                            html.append("<td>").append(idTarifa).append("</td>");
                            html.append("<td>").append(escaparHtml(rs.getString("nombre"))).append("</td>");
                            html.append("<td class=\"text-right\">Gs. ")
                                    .append(rs.getBigDecimal("monto") != null ? formatoMonto.format(rs.getBigDecimal("monto")) : "")
                                    .append("</td>");
                            html.append("<td>").append(escaparHtml(rs.getString("periodicidad"))).append("</td>");
                            html.append("<td>").append(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toString() : "").append("</td>");
                            html.append("<td>").append(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toString() : "Sin fin").append("</td>");
                            html.append("<td class=\"text-center\"><span class=\"badge ")
                                    .append(activa ? "badge-success" : "badge-secondary")
                                    .append("\">")
                                    .append(activa ? "ACTIVO" : "INACTIVO")
                                    .append("</span></td>");
                            html.append("</tr>");
                        }

                        html.append("</tbody></table></div>");

                        if (cantidadResultados == 0) {
                            html.append(
                                    "<div class=\"alert alert-info mt-3\">" +
                                    "<i class=\"fas fa-info-circle mr-1\"></i>" +
                                    "No se encontraron tarifas con los filtros seleccionados." +
                                    "</div>"
                            );
                        } else {
                            html.append(
                                    "<div class=\"mt-3 text-right\">" +
                                    "<button type=\"button\" class=\"btn btn-primary\" onclick=\"generarInformeIndividualTarifa()\">" +
                                    "<i class=\"fas fa-file-pdf mr-1\"></i> Generar Informe Individual" +
                                    "</button></div>"
                            );
                        }

                        response.getWriter().write(html.toString());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    "<div class=\"alert alert-danger\">" +
                    "<i class=\"fas fa-exclamation-triangle mr-1\"></i>" +
                    escaparHtml(e.getMessage() != null ? e.getMessage() : "Error al realizar la búsqueda.") +
                    "</div>"
            );
        }
    }


    // INFORME GENERAL DE ASIGNACIONES

    private void generarInformeAsignaciones(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String desdeParametro = request.getParameter("desde");
        String hastaParametro = request.getParameter("hasta");

        try {

            if (desdeParametro == null || desdeParametro.trim().isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Inicio.");
            }

            if (hastaParametro == null || hastaParametro.trim().isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Fin.");
            }

            Date fechaDesde = Date.valueOf(desdeParametro);
            Date fechaHasta = Date.valueOf(hastaParametro);

            if (fechaDesde.after(fechaHasta)) {
                throw new Exception("La Fecha Inicio no puede ser posterior a la Fecha Fin.");
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_asignacion_listado.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(
                            rutaReporte
                    );

            if (inputStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: "
                        + rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream = inputStream) {
                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put("DESDE", fechaDesde);
            parametros.put("HASTA", fechaHasta);
            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(request)
            );

            InputStream logoStream =
                    getServletContext().getResourceAsStream(
                            "/WEB-INF/reportes/logo.png"
                    );

            if (logoStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png"
                );
            }

            parametros.put(
                    "LOGO",
                    logoStream
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {
                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();

                response.setContentType(
                        "application/pdf"
                );

                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=asignaciones.pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    e
            );
        }
    }

    // INFORME INDIVIDUAL DE ASIGNACIÓN

    private void generarInformeAsignacionIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String idTexto =
                    limpiar(request.getParameter("idAsignacion"));

            if (idTexto.isEmpty()) {
                idTexto =
                        limpiar(
                                request.getParameter(
                                        "id_asignacion"
                                )
                        );
            }

            if (idTexto.isEmpty()) {
                throw new Exception(
                        "No se recibió el ID de la asignación."
                );
            }

            int idAsignacion;

            try {
                idAsignacion =
                        Integer.parseInt(idTexto);
            } catch (NumberFormatException e) {
                throw new Exception(
                        "El ID de la asignación debe ser numérico."
                );
            }

            if (idAsignacion <= 0) {
                throw new Exception(
                        "El ID de la asignación debe ser mayor que cero."
                );
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_asignacion_individual.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(
                            rutaReporte
                    );

            if (inputStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: "
                        + rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream = inputStream) {
                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "idAsignacion",
                    idAsignacion
            );

            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(request)
            );

            InputStream logoStream =
                    getServletContext().getResourceAsStream(
                            "/WEB-INF/reportes/logo.png"
                    );

            if (logoStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png"
                );
            }

            parametros.put(
                    "LOGO",
                    logoStream
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {
                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();

                response.setContentType(
                        "application/pdf"
                );

                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=asignacion_"
                        + idAsignacion
                        + ".pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    e
            );
        }
    }

    // FILTROS DE ASIGNACIONES

    private void cargarFiltrosAsignaciones(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        try {

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {
                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                List<Map<String, String>> rutas =
                        new ArrayList<>();

                List<Map<String, String>> vehiculos =
                        new ArrayList<>();

                List<Map<String, String>> personal =
                        new ArrayList<>();

                String sqlRutas =
                        "SELECT id_ruta, nombre "
                        + "FROM ruta "
                        + "ORDER BY nombre";

                try (PreparedStatement ps =
                             con.prepareStatement(sqlRutas);
                     ResultSet rs =
                             ps.executeQuery()) {

                    while (rs.next()) {

                        Map<String, String> item =
                                new HashMap<>();

                        item.put(
                                "id",
                                String.valueOf(
                                        rs.getInt("id_ruta")
                                )
                        );

                        item.put(
                                "nombre",
                                rs.getString("nombre")
                        );

                        rutas.add(item);
                    }
                }

                String sqlVehiculos =
                        "SELECT id_vehiculo, chapa "
                        + "FROM vehiculo "
                        + "ORDER BY chapa";

                try (PreparedStatement ps =
                             con.prepareStatement(sqlVehiculos);
                     ResultSet rs =
                             ps.executeQuery()) {

                    while (rs.next()) {

                        Map<String, String> item =
                                new HashMap<>();

                        item.put(
                                "id",
                                String.valueOf(
                                        rs.getInt("id_vehiculo")
                                )
                        );

                        item.put(
                                "nombre",
                                rs.getString("chapa")
                        );

                        vehiculos.add(item);
                    }
                }

                String sqlPersonal =
                        "SELECT id_personal, "
                        + "nombre || ' ' || apellido AS nombre_personal "
                        + "FROM personal "
                        + "ORDER BY nombre, apellido";

                try (PreparedStatement ps =
                             con.prepareStatement(sqlPersonal);
                     ResultSet rs =
                             ps.executeQuery()) {

                    while (rs.next()) {

                        Map<String, String> item =
                                new HashMap<>();

                        item.put(
                                "id",
                                String.valueOf(
                                        rs.getInt("id_personal")
                                )
                        );

                        item.put(
                                "nombre",
                                rs.getString("nombre_personal")
                        );

                        personal.add(item);
                    }
                }

                StringBuilder json =
                        new StringBuilder();

                json.append("{");
                json.append("\"rutas\":");
                json.append(listaAJson(rutas));
                json.append(",");

                json.append("\"vehiculos\":");
                json.append(listaAJson(vehiculos));
                json.append(",");

                json.append("\"personal\":");
                json.append(listaAJson(personal));
                json.append("}");

                response.getWriter().write(
                        json.toString()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(
                    "{\"error\":\""
                    + escaparJson(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Error al cargar filtros"
                    )
                    + "\"}"
            );
        }
    }

    // BUSCAR ASIGNACIONES PARA INFORME INDIVIDUAL

    private void buscarAsignacionesInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        String id =
                limpiar(
                        request.getParameter(
                                "id_asignacion"
                        )
                );

        String fecha =
                limpiar(
                        request.getParameter(
                                "fecha"
                        )
                );

        String idRuta =
                limpiar(
                        request.getParameter(
                                "id_ruta"
                        )
                );

        String idVehiculo =
                limpiar(
                        request.getParameter(
                                "id_vehiculo"
                        )
                );

        String idPersonal =
                limpiar(
                        request.getParameter(
                                "id_personal"
                        )
                );

        String estado =
                limpiar(
                        request.getParameter(
                                "estado"
                        )
                );

        try {

            StringBuilder sql =
                    new StringBuilder();

            sql.append(
                    "SELECT "
                    + "a.id_asignacion, "
                    + "a.fecha, "
                    + "a.hora_salida, "
                    + "a.hora_regreso, "
                    + "r.nombre AS nombre_ruta, "
                    + "v.chapa, "
                    + "p.nombre || ' ' || p.apellido AS nombre_personal, "
                    + "a.estado "
                    + "FROM asignacion a "
                    + "INNER JOIN ruta r "
                    + "ON a.id_ruta = r.id_ruta "
                    + "INNER JOIN vehiculo v "
                    + "ON a.id_vehiculo = v.id_vehiculo "
                    + "INNER JOIN personal p "
                    + "ON a.id_personal_responsable = p.id_personal "
                    + "WHERE 1 = 1 "
            );

            List<Object> parametros =
                    new ArrayList<>();

            if (!id.isEmpty()) {

                try {

                    sql.append(
                            "AND a.id_asignacion = ? "
                    );

                    parametros.add(
                            Integer.parseInt(id)
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El ID de la asignación debe ser numérico."
                    );
                }
            }

            if (!fecha.isEmpty()) {

                try {

                    sql.append(
                            "AND a.fecha = ? "
                    );

                    parametros.add(
                            Date.valueOf(fecha)
                    );

                } catch (IllegalArgumentException e) {

                    throw new Exception(
                            "La fecha seleccionada no es válida."
                    );
                }
            }

            if (!idRuta.isEmpty()) {

                try {

                    sql.append(
                            "AND a.id_ruta = ? "
                    );

                    parametros.add(
                            Integer.parseInt(idRuta)
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "La ruta seleccionada no es válida."
                    );
                }
            }

            if (!idVehiculo.isEmpty()) {

                try {

                    sql.append(
                            "AND a.id_vehiculo = ? "
                    );

                    parametros.add(
                            Integer.parseInt(idVehiculo)
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El vehículo seleccionado no es válido."
                    );
                }
            }

            if (!idPersonal.isEmpty()) {

                try {

                    sql.append(
                            "AND a.id_personal_responsable = ? "
                    );

                    parametros.add(
                            Integer.parseInt(idPersonal)
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El personal seleccionado no es válido."
                    );
                }
            }

            if (!estado.isEmpty()) {

                sql.append(
                        "AND UPPER(a.estado) = UPPER(?) "
                );

                parametros.add(
                        estado
                );
            }

            sql.append(
                    "ORDER BY a.fecha DESC, "
                    + "a.hora_salida DESC NULLS LAST, "
                    + "a.id_asignacion DESC"
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {
                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     sql.toString()
                             )) {

                    for (int i = 0;
                         i < parametros.size();
                         i++) {

                        Object valor =
                                parametros.get(i);

                        if (valor instanceof Integer) {

                            ps.setInt(
                                    i + 1,
                                    (Integer) valor
                            );

                        } else if (valor instanceof Date) {

                            ps.setDate(
                                    i + 1,
                                    (Date) valor
                            );

                        } else {

                            ps.setString(
                                    i + 1,
                                    String.valueOf(valor)
                            );
                        }
                    }

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        StringBuilder html =
                                new StringBuilder();

                        html.append(
                                "<div class=\"table-responsive\">"
                        );

                        html.append(
                                "<table class=\"table table-bordered "
                                + "table-hover table-striped\">"
                        );

                        html.append("<thead><tr>");

                        html.append(
                                "<th style=\"width:50px;\">Sel.</th>"
                        );

                        html.append("<th>ID</th>");
                        html.append("<th>Fecha</th>");
                        html.append("<th>Hora Salida</th>");
                        html.append("<th>Hora Regreso</th>");
                        html.append("<th>Ruta</th>");
                        html.append("<th>Vehículo</th>");
                        html.append("<th>Personal</th>");
                        html.append("<th>Estado</th>");

                        html.append("</tr></thead>");
                        html.append("<tbody>");

                        int cantidadResultados = 0;

                        while (rs.next()) {

                            cantidadResultados++;

                            int idAsignacion =
                                    rs.getInt(
                                            "id_asignacion"
                                    );

                            Date fechaResultado =
                                    rs.getDate(
                                            "fecha"
                                    );

                            java.sql.Time horaSalida =
                                    rs.getTime(
                                            "hora_salida"
                                    );

                            java.sql.Time horaRegreso =
                                    rs.getTime(
                                            "hora_regreso"
                                    );

                            String ruta =
                                    rs.getString(
                                            "nombre_ruta"
                                    );

                            String chapa =
                                    rs.getString(
                                            "chapa"
                                    );

                            String personal =
                                    rs.getString(
                                            "nombre_personal"
                                    );

                            String estadoResultado =
                                    rs.getString(
                                            "estado"
                                    );

                            html.append("<tr>");

                            html.append(
                                    "<td class=\"text-center\">"
                            );

                            html.append(
                                    "<input type=\"radio\" "
                                    + "name=\"asignacionSeleccionada\" "
                                    + "value=\""
                                    + idAsignacion
                                    + "\">"
                            );

                            html.append("</td>");

                            html.append("<td>");
                            html.append(idAsignacion);
                            html.append("</td>");

                            html.append("<td>");
                            html.append(
                                    escaparHtml(
                                            fechaResultado != null
                                                    ? fechaResultado.toString()
                                                    : ""
                                    )
                            );
                            html.append("</td>");

                            html.append("<td>");
                            html.append(
                                    escaparHtml(
                                            horaSalida != null
                                                    ? horaSalida.toString().substring(0, 5)
                                                    : ""
                                    )
                            );
                            html.append("</td>");

                            html.append("<td>");
                            html.append(
                                    escaparHtml(
                                            horaRegreso != null
                                                    ? horaRegreso.toString().substring(0, 5)
                                                    : ""
                                    )
                            );
                            html.append("</td>");

                            html.append("<td>");
                            html.append(
                                    escaparHtml(ruta)
                            );
                            html.append("</td>");

                            html.append("<td>");
                            html.append(
                                    escaparHtml(chapa)
                            );
                            html.append("</td>");

                            html.append("<td>");
                            html.append(
                                    escaparHtml(personal)
                            );
                            html.append("</td>");

                            html.append(
                                    "<td class=\"text-center\">"
                            );

                            String claseEstado =
                                    "badge-secondary";

                            if ("PROGRAMADA".equalsIgnoreCase(
                                    estadoResultado)) {

                                claseEstado =
                                        "badge-info";

                            } else if ("EN CURSO".equalsIgnoreCase(
                                    estadoResultado)) {

                                claseEstado =
                                        "badge-warning";

                            } else if ("FINALIZADA".equalsIgnoreCase(
                                    estadoResultado)) {

                                claseEstado =
                                        "badge-success";

                            } else if ("CANCELADA".equalsIgnoreCase(
                                    estadoResultado)) {

                                claseEstado =
                                        "badge-danger";
                            }

                            html.append(
                                    "<span class=\"badge "
                                    + claseEstado
                                    + "\">"
                            );

                            html.append(
                                    escaparHtml(
                                            estadoResultado
                                    )
                            );

                            html.append("</span>");
                            html.append("</td>");

                            html.append("</tr>");
                        }

                        html.append("</tbody>");
                        html.append("</table>");
                        html.append("</div>");

                        if (cantidadResultados == 0) {

                            response.getWriter().write(
                                    "<div class=\"alert alert-warning\">"
                                    + "<i class=\"fas fa-search\"></i> "
                                    + "No se encontraron asignaciones "
                                    + "con los filtros seleccionados."
                                    + "</div>"
                            );

                            return;
                        }

                        html.append(
                                "<div class=\"mt-3 d-flex "
                                + "justify-content-between "
                                + "align-items-center\">"
                        );

                        html.append(
                                "<span class=\"text-muted\">"
                                + "<i class=\"fas fa-info-circle\"></i> "
                                + cantidadResultados
                                + " resultado(s) encontrado(s). "
                                + "Seleccione una asignación para generar "
                                + "el informe individual."
                                + "</span>"
                        );

                        html.append(
                                "<button type=\"button\" "
                                + "class=\"btn btn-primary\" "
                                + "onclick=\"generarInformeIndividualAsignacion()\">"
                        );

                        html.append(
                                "<i class=\"fas fa-file-pdf\"></i> "
                                + "Generar Informe Individual"
                        );

                        html.append("</button>");
                        html.append("</div>");

                        response.getWriter().write(
                                html.toString()
                        );
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(
                    "<div class=\"alert alert-danger\">"
                    + "<h5>"
                    + "<i class=\"fas fa-exclamation-triangle\"></i> "
                    + "Error al realizar la búsqueda"
                    + "</h5>"
                    + "<p>"
                    + escaparHtml(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Error desconocido."
                    )
                    + "</p>"
                    + "</div>"
            );
        }
    }

    // INFORME GENERAL DE RECOLECCIONES

    private void generarInformeRecolecciones(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String desdeParametro = request.getParameter("desde");
        String hastaParametro = request.getParameter("hasta");

        try {

            if (desdeParametro == null ||
                desdeParametro.trim().isEmpty()) {

                throw new Exception(
                        "Debe seleccionar la Fecha Inicio."
                );
            }

            if (hastaParametro == null ||
                hastaParametro.trim().isEmpty()) {

                throw new Exception(
                        "Debe seleccionar la Fecha Fin."
                );
            }

            Date fechaDesde = Date.valueOf(desdeParametro);
            Date fechaHasta = Date.valueOf(hastaParametro);

            if (fechaDesde.after(fechaHasta)) {

                throw new Exception(
                        "La Fecha Inicio no puede ser posterior a la Fecha Fin."
                );
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_recolecciones.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {

                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: "
                        + rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream = inputStream) {

                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            InputStream logoStream =
                    getServletContext().getResourceAsStream(
                            "/WEB-INF/reportes/logo.png"
                    );

            if (logoStream == null) {

                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: "
                        + "/WEB-INF/reportes/logo.png"
                );
            }

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "DESDE",
                    fechaDesde
            );

            parametros.put(
                    "HASTA",
                    fechaHasta
            );

            parametros.put(
                    "LOGO",
                    logoStream
            );

            parametros.put(
                    "USUARIO_IMPRESION",
                    obtenerUsuarioSesion(request)
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();

                response.setContentType(
                        "application/pdf"
                );

                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_recolecciones.pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    e
            );
        }
    }

    // CARGAR FILTROS DE RECOLECCIONES

    private void cargarFiltrosRecolecciones(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        try {

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                List<Map<String, String>> rutas =
                        new ArrayList<>();

                List<Map<String, String>> vehiculos =
                        new ArrayList<>();

                List<Map<String, String>> personal =
                        new ArrayList<>();

                // RUTAS

                String sqlRutas =
                        "SELECT id_ruta, nombre " +
                        "FROM ruta " +
                        "ORDER BY nombre";

                try (PreparedStatement ps =
                             con.prepareStatement(sqlRutas);
                     ResultSet rs =
                             ps.executeQuery()) {

                    while (rs.next()) {

                        Map<String, String> item =
                                new HashMap<>();

                        item.put(
                                "id",
                                String.valueOf(
                                        rs.getInt("id_ruta")
                                )
                        );

                        item.put(
                                "nombre",
                                rs.getString("nombre")
                        );

                        rutas.add(item);
                    }
                }

                // VEHÍCULOS

                String sqlVehiculos =
                        "SELECT id_vehiculo, chapa " +
                        "FROM vehiculo " +
                        "ORDER BY chapa";

                try (PreparedStatement ps =
                             con.prepareStatement(sqlVehiculos);
                     ResultSet rs =
                             ps.executeQuery()) {

                    while (rs.next()) {

                        Map<String, String> item =
                                new HashMap<>();

                        item.put(
                                "id",
                                String.valueOf(
                                        rs.getInt("id_vehiculo")
                                )
                        );

                        item.put(
                                "nombre",
                                rs.getString("chapa")
                        );

                        vehiculos.add(item);
                    }
                }

                // PERSONAL

                String sqlPersonal =
                        "SELECT " +
                        "id_personal, " +
                        "nombre || ' ' || apellido AS nombre_personal " +
                        "FROM personal " +
                        "ORDER BY nombre, apellido";

                try (PreparedStatement ps =
                             con.prepareStatement(sqlPersonal);
                     ResultSet rs =
                             ps.executeQuery()) {

                    while (rs.next()) {

                        Map<String, String> item =
                                new HashMap<>();

                        item.put(
                                "id",
                                String.valueOf(
                                        rs.getInt("id_personal")
                                )
                        );

                        item.put(
                                "nombre",
                                rs.getString("nombre_personal")
                        );

                        personal.add(item);
                    }
                }

                StringBuilder json =
                        new StringBuilder();

                json.append("{");

                json.append("\"rutas\":");
                json.append(listaAJson(rutas));
                json.append(",");

                json.append("\"vehiculos\":");
                json.append(listaAJson(vehiculos));
                json.append(",");

                json.append("\"personal\":");
                json.append(listaAJson(personal));

                json.append("}");

                response.getWriter().write(
                        json.toString()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(
                    "{\"error\":\"" +
                    escaparJson(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Error al cargar filtros"
                    ) +
                    "\"}"
            );
        }
    }

    // BUSCAR RECOLECCIONES PARA INFORME INDIVIDUAL

    private void buscarRecoleccionesInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        String idRecoleccion =
                limpiar(
                        request.getParameter(
                                "id_recoleccion"
                        )
                );

        String fecha =
                limpiar(
                        request.getParameter(
                                "fecha"
                        )
                );

        String idRuta =
                limpiar(
                        request.getParameter(
                                "id_ruta"
                        )
                );

        String idVehiculo =
                limpiar(
                        request.getParameter(
                                "id_vehiculo"
                        )
                );

        String idPersonal =
                limpiar(
                        request.getParameter(
                                "id_personal"
                        )
                );

        String estado =
                limpiar(
                        request.getParameter(
                                "estado"
                        )
                );

        try {

            StringBuilder sql =
                    new StringBuilder();

            sql.append(
                    "SELECT " +
                    "r.id_recoleccion, " +
                    "r.fecha, " +
                    "ru.nombre AS nombre_ruta, " +
                    "v.chapa, " +
                    "p.nombre || ' ' || p.apellido AS nombre_personal, " +
                    "r.cantidad_recolectada, " +
                    "r.unidad_medida, " +
                    "r.estado, " +
                    "r.observaciones " +
                    "FROM recoleccion r " +
                    "INNER JOIN asignacion a " +
                    "ON r.id_asignacion = a.id_asignacion " +
                    "INNER JOIN ruta ru " +
                    "ON a.id_ruta = ru.id_ruta " +
                    "INNER JOIN vehiculo v " +
                    "ON a.id_vehiculo = v.id_vehiculo " +
                    "INNER JOIN personal p " +
                    "ON a.id_personal_responsable = p.id_personal " +
                    "WHERE 1 = 1 "
            );

            List<Object> parametros =
                    new ArrayList<>();

            // ID

            if (!idRecoleccion.isEmpty()) {

                try {

                    sql.append(
                            "AND r.id_recoleccion = ? "
                    );

                    parametros.add(
                            Integer.parseInt(
                                    idRecoleccion
                            )
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El ID de recolección debe ser numérico."
                    );
                }
            }

            // FECHA

            if (!fecha.isEmpty()) {

                try {

                    sql.append(
                            "AND r.fecha = ? "
                    );

                    parametros.add(
                            Date.valueOf(fecha)
                    );

                } catch (IllegalArgumentException e) {

                    throw new Exception(
                            "La fecha seleccionada no es válida."
                    );
                }
            }

            // RUTA

            if (!idRuta.isEmpty()) {

                try {

                    sql.append(
                            "AND a.id_ruta = ? "
                    );

                    parametros.add(
                            Integer.parseInt(
                                    idRuta
                            )
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "La ruta seleccionada no es válida."
                    );
                }
            }

            // VEHÍCULO

            if (!idVehiculo.isEmpty()) {

                try {

                    sql.append(
                            "AND a.id_vehiculo = ? "
                    );

                    parametros.add(
                            Integer.parseInt(
                                    idVehiculo
                            )
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El vehículo seleccionado no es válido."
                    );
                }
            }

            // PERSONAL

            if (!idPersonal.isEmpty()) {

                try {

                    sql.append(
                            "AND a.id_personal_responsable = ? "
                    );

                    parametros.add(
                            Integer.parseInt(
                                    idPersonal
                            )
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El personal seleccionado no es válido."
                    );
                }
            }

            // ESTADO

            if (!estado.isEmpty()) {

                sql.append(
                        "AND r.estado = ? "
                );

                parametros.add(
                        estado
                );
            }

            sql.append(
                    "ORDER BY r.fecha DESC, " +
                    "r.id_recoleccion DESC"
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     sql.toString()
                             )) {

                    for (int i = 0;
                         i < parametros.size();
                         i++) {

                        Object valor =
                                parametros.get(i);

                        if (valor instanceof Integer) {

                            ps.setInt(
                                    i + 1,
                                    (Integer) valor
                            );

                        } else if (valor instanceof Date) {

                            ps.setDate(
                                    i + 1,
                                    (Date) valor
                            );

                        } else {

                            ps.setString(
                                    i + 1,
                                    String.valueOf(
                                            valor
                                    )
                            );
                        }
                    }

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        StringBuilder html =
                                new StringBuilder();

                        html.append(
                                "<div class=\"table-responsive\">"
                        );

                        html.append(
                                "<table class=\"table table-bordered " +
                                "table-hover table-striped\">"
                        );

                        html.append("<thead>");
                        html.append("<tr>");

                        html.append(
                                "<th style=\"width:50px;\">"
                        );

                        html.append("Sel.");

                        html.append("</th>");

                        html.append("<th>ID</th>");
                        html.append("<th>Fecha</th>");
                        html.append("<th>Ruta</th>");
                        html.append("<th>Vehículo</th>");
                        html.append("<th>Personal</th>");
                        html.append("<th>Cantidad</th>");
                        html.append("<th>Unidad</th>");
                        html.append("<th>Estado</th>");

                        html.append("</tr>");
                        html.append("</thead>");

                        html.append("<tbody>");

                        int cantidadResultados = 0;

                        while (rs.next()) {

                            cantidadResultados++;

                            int id =
                                    rs.getInt(
                                            "id_recoleccion"
                                    );

                            Date fechaResultado =
                                    rs.getDate(
                                            "fecha"
                                    );

                            String ruta =
                                    rs.getString(
                                            "nombre_ruta"
                                    );

                            String chapa =
                                    rs.getString(
                                            "chapa"
                                    );

                            String personal =
                                    rs.getString(
                                            "nombre_personal"
                                    );

                            double cantidad =
                                    rs.getDouble(
                                            "cantidad_recolectada"
                                    );

                            String unidad =
                                    rs.getString(
                                            "unidad_medida"
                                    );

                            String estadoResultado =
                                    rs.getString(
                                            "estado"
                                    );

                            html.append("<tr>");

                            html.append(
                                    "<td class=\"text-center\">"
                            );

                            html.append(
                                    "<input type=\"radio\" " +
                                    "name=\"recoleccionSeleccionada\" " +
                                    "value=\"" +
                                    id +
                                    "\">"
                            );

                            html.append("</td>");

                            html.append("<td>");
                            html.append(id);
                            html.append("</td>");

                            html.append("<td>");

                            html.append(
                                    escaparHtml(
                                            fechaResultado != null
                                                    ? fechaResultado.toString()
                                                    : ""
                                    )
                            );

                            html.append("</td>");

                            html.append("<td>");

                            html.append(
                                    escaparHtml(
                                            ruta
                                    )
                            );

                            html.append("</td>");

                            html.append("<td>");

                            html.append(
                                    escaparHtml(
                                            chapa
                                    )
                            );

                            html.append("</td>");

                            html.append("<td>");

                            html.append(
                                    escaparHtml(
                                            personal
                                    )
                            );

                            html.append("</td>");

                            html.append("<td>");

                            if (cantidad ==
                                Math.floor(cantidad)) {

                                html.append(
                                        String.format(
                                                "%.0f",
                                                cantidad
                                        )
                                );

                            } else {

                                html.append(
                                        String.valueOf(
                                                cantidad
                                        )
                                );
                            }

                            html.append("</td>");

                            html.append("<td>");

                            html.append(
                                    escaparHtml(
                                            unidad
                                    )
                            );

                            html.append("</td>");

                            html.append(
                                    "<td class=\"text-center\">"
                            );

                            String claseEstado =
                                    "badge-secondary";

                            if ("REGISTRADA".equalsIgnoreCase(
                                    estadoResultado)) {

                                claseEstado =
                                        "badge-info";

                            } else if ("VALIDADA".equalsIgnoreCase(
                                    estadoResultado)) {

                                claseEstado =
                                        "badge-success";

                            } else if ("ANULADA".equalsIgnoreCase(
                                    estadoResultado)) {

                                claseEstado =
                                        "badge-danger";
                            }

                            html.append(
                                    "<span class=\"badge " +
                                    claseEstado +
                                    "\">"
                            );

                            html.append(
                                    escaparHtml(
                                            estadoResultado
                                    )
                            );

                            html.append("</span>");

                            html.append("</td>");

                            html.append("</tr>");
                        }

                        html.append("</tbody>");
                        html.append("</table>");
                        html.append("</div>");

                        if (cantidadResultados == 0) {

                            response.getWriter().write(

                                    "<div class=\"alert alert-warning\">" +

                                    "<i class=\"fas fa-search\"></i> " +

                                    "No se encontraron recolecciones " +
                                    "con los filtros seleccionados." +

                                    "</div>"
                            );

                            return;
                        }

                        html.append(
                                "<div class=\"mt-3 d-flex " +
                                "justify-content-between " +
                                "align-items-center\">"
                        );

                        html.append(
                                "<span class=\"text-muted\">" +
                                "<i class=\"fas fa-info-circle\"></i> " +
                                cantidadResultados +
                                " resultado(s) encontrado(s). " +
                                "Seleccione una recolección para generar " +
                                "el informe individual." +
                                "</span>"
                        );

                        html.append(
                                "<button type=\"button\" " +
                                "class=\"btn btn-primary\" " +
                                "onclick=\"generarInformeIndividual()\">"
                        );

                        html.append(
                                "<i class=\"fas fa-file-pdf\"></i> " +
                                "Generar Informe Individual"
                        );

                        html.append("</button>");

                        html.append("</div>");

                        response.getWriter().write(
                                html.toString()
                        );
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(

                    "<div class=\"alert alert-danger\">" +

                    "<h5>" +

                    "<i class=\"fas fa-exclamation-triangle\"></i> " +

                    "Error al realizar la búsqueda" +

                    "</h5>" +

                    "<p>" +

                    escaparHtml(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Error desconocido."
                    ) +

                    "</p>" +

                    "</div>"
            );
        }
    }

    // INFORME GENERAL DE CLASIFICACIONES DE RESIDUOS

    private void generarInformeClasificaciones(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String desdeParametro = request.getParameter("desde");
        String hastaParametro = request.getParameter("hasta");

        try {

            if (desdeParametro == null || desdeParametro.trim().isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Inicio.");
            }

            if (hastaParametro == null || hastaParametro.trim().isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Fin.");
            }

            Date fechaDesde = Date.valueOf(desdeParametro);
            Date fechaHasta = Date.valueOf(hastaParametro);

            if (fechaDesde.after(fechaHasta)) {
                throw new Exception("La Fecha Inicio no puede ser posterior a la Fecha Fin.");
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_clasificaciones.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte);
            }

            JasperReport jasperReport;

            try (InputStream reporteStream = inputStream) {
                jasperReport = JasperCompileManager.compileReport(reporteStream);
            }

            InputStream logoStream =
                    getServletContext().getResourceAsStream(
                            "/WEB-INF/reportes/logo.png"
                    );

            if (logoStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png"
                );
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("DESDE", fechaDesde);
            parametros.put("HASTA", fechaHasta);
            parametros.put("LOGO", logoStream);
            parametros.put("USUARIO_IMPRESION", obtenerUsuarioSesion(request));

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {

                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_clasificaciones.pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // BUSCAR CLASIFICACIONES PARA INFORME INDIVIDUAL

    private void buscarClasificacionesInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        String idClasificacion = limpiar(request.getParameter("id_clasificacion"));
        String idRecoleccion = limpiar(request.getParameter("id_recoleccion"));
        String tipoResiduo = limpiar(request.getParameter("tipo_residuo"));
        String fecha = limpiar(request.getParameter("fecha"));
        String estado = limpiar(request.getParameter("estado"));

        try {

            StringBuilder sql = new StringBuilder();

            sql.append(
                    "SELECT " +
                    "c.id_clasificacion, " +
                    "c.id_recoleccion, " +
                    "r.fecha, " +
                    "t.nombre AS nombre_tipo, " +
                    "c.cantidad_kg, " +
                    "c.observaciones, " +
                    "c.estado " +
                    "FROM clasificacion_residuo c " +
                    "INNER JOIN recoleccion r ON c.id_recoleccion = r.id_recoleccion " +
                    "INNER JOIN tipos_residuos t ON c.id_tipo = t.id_tipo " +
                    "WHERE 1 = 1 "
            );

            List<Object> parametros = new ArrayList<>();

            if (!idClasificacion.isEmpty()) {
                try {
                    sql.append("AND c.id_clasificacion = ? ");
                    parametros.add(Integer.parseInt(idClasificacion));
                } catch (NumberFormatException e) {
                    throw new Exception("El ID de clasificación debe ser numérico.");
                }
            }

            if (!idRecoleccion.isEmpty()) {
                try {
                    sql.append("AND c.id_recoleccion = ? ");
                    parametros.add(Integer.parseInt(idRecoleccion));
                } catch (NumberFormatException e) {
                    throw new Exception("El ID de recolección debe ser numérico.");
                }
            }

            if (!tipoResiduo.isEmpty()) {
                sql.append("AND LOWER(t.nombre) LIKE LOWER(?) ");
                parametros.add("%" + tipoResiduo + "%");
            }

            if (!fecha.isEmpty()) {
                try {
                    sql.append("AND r.fecha = ? ");
                    parametros.add(Date.valueOf(fecha));
                } catch (IllegalArgumentException e) {
                    throw new Exception("La fecha seleccionada no es válida.");
                }
            }

            if (!estado.isEmpty()) {
                if (!"true".equalsIgnoreCase(estado) &&
                    !"false".equalsIgnoreCase(estado)) {
                    throw new Exception("El estado seleccionado no es válido.");
                }

                sql.append("AND c.estado = ? ");
                parametros.add(Boolean.parseBoolean(estado));
            }

            sql.append(
                    "ORDER BY r.fecha DESC, c.id_clasificacion DESC"
            );

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {

                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                try (PreparedStatement ps = con.prepareStatement(sql.toString())) {

                    for (int i = 0; i < parametros.size(); i++) {
                        Object valor = parametros.get(i);

                        if (valor instanceof Integer) {
                            ps.setInt(i + 1, (Integer) valor);
                        } else if (valor instanceof Date) {
                            ps.setDate(i + 1, (Date) valor);
                        } else if (valor instanceof Boolean) {
                            ps.setBoolean(i + 1, (Boolean) valor);
                        } else {
                            ps.setString(i + 1, String.valueOf(valor));
                        }
                    }

                    try (ResultSet rs = ps.executeQuery()) {

                        StringBuilder html = new StringBuilder();

                        html.append("<div class=\"table-responsive\">");
                        html.append("<table class=\"table table-bordered table-hover table-striped\">");
                        html.append("<thead><tr>");
                        html.append("<th style=\"width:50px;\">Sel.</th>");
                        html.append("<th>ID</th>");
                        html.append("<th>Recolección</th>");
                        html.append("<th>Fecha</th>");
                        html.append("<th>Tipo de Residuo</th>");
                        html.append("<th>Cantidad</th>");
                        html.append("<th>Observaciones</th>");
                        html.append("<th>Estado</th>");
                        html.append("</tr></thead><tbody>");

                        int cantidadResultados = 0;

                        while (rs.next()) {

                            cantidadResultados++;

                            int id = rs.getInt("id_clasificacion");
                            int idRec = rs.getInt("id_recoleccion");
                            Date fechaResultado = rs.getDate("fecha");
                            String tipo = rs.getString("nombre_tipo");
                            double cantidad = rs.getDouble("cantidad_kg");
                            String observaciones = rs.getString("observaciones");
                            boolean activo = rs.getBoolean("estado");

                            html.append("<tr>");
                            html.append("<td class=\"text-center\">");
                            html.append(
                                    "<input type=\"radio\" " +
                                    "name=\"clasificacionSeleccionada\" " +
                                    "value=\"" + id + "\">"
                            );
                            html.append("</td>");

                            html.append("<td>").append(id).append("</td>");
                            html.append("<td>").append(idRec).append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(
                                            fechaResultado != null
                                                    ? fechaResultado.toString()
                                                    : ""
                                    ))
                                    .append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(tipo))
                                    .append("</td>");

                            html.append("<td>");
                            if (cantidad == Math.floor(cantidad)) {
                                html.append(String.format("%.0f", cantidad));
                            } else {
                                html.append(String.valueOf(cantidad));
                            }
                            html.append(" kg</td>");

                            html.append("<td>")
                                    .append(escaparHtml(observaciones))
                                    .append("</td>");

                            html.append("<td class=\"text-center\">");
                            html.append(
                                    "<span class=\"badge " +
                                    (activo ? "badge-success" : "badge-danger") +
                                    "\">" +
                                    (activo ? "ACTIVO" : "INACTIVO") +
                                    "</span>"
                            );
                            html.append("</td>");
                            html.append("</tr>");
                        }

                        html.append("</tbody></table></div>");

                        if (cantidadResultados == 0) {
                            response.getWriter().write(
                                    "<div class=\"alert alert-warning\">" +
                                    "<i class=\"fas fa-search\"></i> " +
                                    "No se encontraron clasificaciones con los filtros seleccionados." +
                                    "</div>"
                            );
                            return;
                        }

                        html.append(
                                "<div class=\"mt-3 d-flex justify-content-between " +
                                "align-items-center\">"
                        );

                        html.append(
                                "<span class=\"text-muted\">" +
                                "<i class=\"fas fa-info-circle\"></i> " +
                                cantidadResultados +
                                " resultado(s) encontrado(s). Seleccione una clasificación para generar el informe individual." +
                                "</span>"
                        );

                        html.append(
                                "<button type=\"button\" " +
                                "class=\"btn btn-primary\" " +
                                "onclick=\"generarInformeIndividualClasificacion()\">"
                        );

                        html.append(
                                "<i class=\"fas fa-file-pdf\"></i> Generar Informe Individual"
                        );

                        html.append("</button></div>");

                        response.getWriter().write(html.toString());
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    "<div class=\"alert alert-danger\">" +
                    "<h5><i class=\"fas fa-exclamation-triangle\"></i> Error al realizar la búsqueda</h5>" +
                    "<p>" +
                    escaparHtml(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Error desconocido."
                    ) +
                    "</p></div>"
            );
        }
    }

    // INFORME INDIVIDUAL DE CLASIFICACIÓN DE RESIDUOS

    private void generarInformeClasificacionIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParametro = limpiar(request.getParameter("id_clasificacion"));

        if (idParametro.isEmpty()) {
            idParametro = limpiar(request.getParameter("idClasificacion"));
        }

        try {

            if (idParametro.isEmpty()) {
                throw new Exception("Debe seleccionar una clasificación de residuos.");
            }

            int idClasificacion;

            try {
                idClasificacion = Integer.parseInt(idParametro);
            } catch (NumberFormatException e) {
                throw new Exception("El ID de la clasificación no es válido.");
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_clasificacion_individual.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte);
            }

            JasperReport jasperReport;

            try (InputStream reporteStream = inputStream) {
                jasperReport = JasperCompileManager.compileReport(reporteStream);
            }

            InputStream logoStream =
                    getServletContext().getResourceAsStream(
                            "/WEB-INF/reportes/logo.png"
                    );

            if (logoStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png"
                );
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_CLASIFICACION", idClasificacion);
            parametros.put("LOGO", logoStream);
            parametros.put("USUARIO_IMPRESION", obtenerUsuarioSesion(request));

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {

                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_clasificacion_" +
                        idClasificacion +
                        ".pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // INFORME GENERAL DE FACTURACIÓN

    private void generarInformeFacturacion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String desdeParametro = limpiar(request.getParameter("desde"));
        String hastaParametro = limpiar(request.getParameter("hasta"));

        try {

            if (desdeParametro.isEmpty()) {
                throw new Exception(
                        "Debe seleccionar la Fecha Inicio."
                );
            }

            if (hastaParametro.isEmpty()) {
                throw new Exception(
                        "Debe seleccionar la Fecha Fin."
                );
            }

            Date fechaDesde = Date.valueOf(desdeParametro);
            Date fechaHasta = Date.valueOf(hastaParametro);

            if (fechaDesde.after(fechaHasta)) {
                throw new Exception(
                        "La Fecha Inicio no puede ser posterior a la Fecha Fin."
                );
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_facturacion.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(
                            rutaReporte
                    );

            if (inputStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: "
                        + rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream = inputStream) {
                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            InputStream logoStream =
                    getServletContext().getResourceAsStream(
                            "/WEB-INF/reportes/logo.png"
                    );

            if (logoStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: "
                        + "/WEB-INF/reportes/logo.png"
                );
            }

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put("DESDE", fechaDesde);
            parametros.put("HASTA", fechaHasta);
            parametros.put("LOGO", logoStream);
            parametros.put(
                    "USUARIO_IMPRESION",
                    obtenerUsuarioSesion(request)
            );

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {

                if (con == null) {
                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_facturacion.pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // BUSCAR FACTURAS PARA INFORME INDIVIDUAL

    private void buscarFacturasInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        String idFactura = limpiar(request.getParameter("id_factura"));
        String numeroFactura = limpiar(request.getParameter("numero_factura"));
        String cliente = limpiar(request.getParameter("cliente"));
        String estado = limpiar(request.getParameter("estado"));
        String fecha = limpiar(request.getParameter("fecha_emision"));
        String periodo = limpiar(request.getParameter("periodo"));
        String condicionVenta = limpiar(request.getParameter("condicion_venta"));

        try {

            StringBuilder sql = new StringBuilder();

            sql.append(
                    "SELECT " +
                    "f.id_factura, " +
                    "f.numero_factura, " +
                    "f.fecha_emision, " +
                    "f.periodo, " +
                    "f.condicion_venta, " +
                    "f.subtotal, " +
                    "f.iva_5, " +
                    "f.iva_10, " +
                    "f.total, " +
                    "f.estado, " +
                    "c.nombre || ' ' || c.apellido AS nombre_cliente " +
                    "FROM factura f " +
                    "INNER JOIN clientes c " +
                    "ON f.id_cliente = c.id_cliente " +
                    "WHERE 1 = 1 "
            );

            List<Object> parametros = new ArrayList<>();

            if (!idFactura.isEmpty()) {
                try {
                    sql.append("AND f.id_factura = ? ");
                    parametros.add(Integer.parseInt(idFactura));
                } catch (NumberFormatException e) {
                    throw new Exception(
                            "El ID de factura debe ser numérico."
                    );
                }
            }

            if (!numeroFactura.isEmpty()) {
                sql.append("AND LOWER(f.numero_factura) LIKE LOWER(?) ");
                parametros.add("%" + numeroFactura + "%");
            }

            if (!cliente.isEmpty()) {
                sql.append(
                        "AND LOWER(COALESCE(c.nombre, '') || ' ' || " +
                        "COALESCE(c.apellido, '')) LIKE LOWER(?) "
                );
                parametros.add("%" + cliente + "%");
            }

            if (!estado.isEmpty()) {
                sql.append("AND f.estado = ? ");
                parametros.add(estado);
            }

            if (!fecha.isEmpty()) {
                try {
                    sql.append("AND f.fecha_emision = ? ");
                    parametros.add(Date.valueOf(fecha));
                } catch (IllegalArgumentException e) {
                    throw new Exception(
                            "La fecha de emisión seleccionada no es válida."
                    );
                }
            }

            if (!periodo.isEmpty()) {
                sql.append("AND LOWER(COALESCE(f.periodo, '')) LIKE LOWER(?) ");
                parametros.add("%" + periodo + "%");
            }

            if (!condicionVenta.isEmpty()) {
                sql.append("AND LOWER(COALESCE(f.condicion_venta, '')) LIKE LOWER(?) ");
                parametros.add("%" + condicionVenta + "%");
            }

            sql.append(
                    "ORDER BY f.fecha_emision DESC, f.id_factura DESC"
            );

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql.toString())) {

                for (int i = 0; i < parametros.size(); i++) {
                    ps.setObject(i + 1, parametros.get(i));
                }

                try (ResultSet rs = ps.executeQuery()) {

                    StringBuilder html = new StringBuilder();

                    html.append(
                            "<div class=\"table-responsive\">"
                    );

                    html.append(
                            "<table id=\"tablaFacturasInforme\" " +
                            "class=\"table table-bordered table-hover\" " +
                            "style=\"width:100%;\">"
                    );

                    html.append("<thead class=\"thead-dark\"><tr>");
                    html.append("<th style=\"width:45px;\">Sel.</th>");
                    html.append("<th>ID</th>");
                    html.append("<th>N° Factura</th>");
                    html.append("<th>Cliente</th>");
                    html.append("<th>Fecha</th>");
                    html.append("<th>Período</th>");
                    html.append("<th>Total</th>");
                    html.append("<th>Estado</th>");
                    html.append("</tr></thead><tbody>");

                    boolean hayResultados = false;

                    while (rs.next()) {

                        hayResultados = true;

                        String estadoFactura =
                                rs.getString("estado");

                        String claseEstado = "secondary";

                        if ("PAGADA".equalsIgnoreCase(estadoFactura)) {
                            claseEstado = "success";
                        } else if ("ANULADA".equalsIgnoreCase(estadoFactura)) {
                            claseEstado = "danger";
                        } else if ("PENDIENTE".equalsIgnoreCase(estadoFactura)
                                || "EMITIDA".equalsIgnoreCase(estadoFactura)) {
                            claseEstado = "warning";
                        }

                        html.append("<tr>");

                        html.append(
                                "<td class=\"text-center\">" +
                                "<input type=\"radio\" " +
                                "name=\"facturaSeleccionada\" " +
                                "value=\"" + rs.getInt("id_factura") + "\">" +
                                "</td>"
                        );

                        html.append(
                                "<td class=\"text-center\">" +
                                rs.getInt("id_factura") +
                                "</td>"
                        );

                        html.append(
                                "<td>" +
                                escaparHtml(rs.getString("numero_factura")) +
                                "</td>"
                        );

                        html.append(
                                "<td>" +
                                escaparHtml(rs.getString("nombre_cliente")) +
                                "</td>"
                        );

                        Date fechaEmision =
                                rs.getDate("fecha_emision");

                        html.append(
                                "<td class=\"text-center\">" +
                                (fechaEmision == null
                                        ? ""
                                        : new java.text.SimpleDateFormat(
                                                "dd/MM/yyyy"
                                        ).format(fechaEmision)) +
                                "</td>"
                        );

                        html.append(
                                "<td>" +
                                escaparHtml(rs.getString("periodo")) +
                                "</td>"
                        );

                        html.append(
                                "<td class=\"text-right\">" +
                                formatearNumeroInforme(rs.getBigDecimal("total")) +
                                "</td>"
                        );

                        html.append(
                                "<td class=\"text-center\">" +
                                "<span class=\"badge badge-" +
                                claseEstado +
                                "\">" +
                                escaparHtml(estadoFactura) +
                                "</span></td>"
                        );

                        html.append("</tr>");
                    }

                    html.append("</tbody></table></div>");

                    if (!hayResultados) {
                        html.setLength(0);
                        html.append(
                                "<div class=\"alert alert-info\">" +
                                "<i class=\"fas fa-info-circle mr-1\"></i>" +
                                "No se encontraron facturas con los filtros seleccionados." +
                                "</div>"
                        );
                    } else {
                        html.append(
                                "<div class=\"text-right mt-3\">" +
                                "<button type=\"button\" " +
                                "class=\"btn btn-primary\" " +
                                "onclick=\"generarInformeIndividualFactura()\">" +
                                "<i class=\"fas fa-file-pdf mr-1\"></i> " +
                                "Generar Informe Individual" +
                                "</button></div>"
                        );
                    }

                    response.getWriter().write(html.toString());
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(
                    "<div class=\"alert alert-danger\">" +
                    "<h5><i class=\"fas fa-exclamation-triangle\"></i> " +
                    "Error al realizar la búsqueda</h5>" +
                    "<p>" +
                    escaparHtml(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Error desconocido."
                    ) +
                    "</p></div>"
            );
        }
    }

    // INFORME INDIVIDUAL DE FACTURA

    private void generarInformeFacturaIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParametro = limpiar(
                request.getParameter("id_factura")
        );

        if (idParametro.isEmpty()) {
            idParametro = limpiar(
                    request.getParameter("idFactura")
            );
        }

        try {

            if (idParametro.isEmpty()) {
                throw new Exception(
                        "Debe seleccionar una factura."
                );
            }

            int idFactura;

            try {
                idFactura = Integer.parseInt(idParametro);
            } catch (NumberFormatException e) {
                throw new Exception(
                        "El ID de la factura no es válido."
                );
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_factura_individual.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(
                            rutaReporte
                    );

            if (inputStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: "
                        + rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream = inputStream) {
                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            InputStream logoStream =
                    getServletContext().getResourceAsStream(
                            "/WEB-INF/reportes/logo.png"
                    );

            if (logoStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: "
                        + "/WEB-INF/reportes/logo.png"
                );
            }

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put("ID_FACTURA", idFactura);
            parametros.put("LOGO", logoStream);
            parametros.put(
                    "USUARIO_IMPRESION",
                    obtenerUsuarioSesion(request)
            );

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {

                if (con == null) {
                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_factura_" +
                        idFactura +
                        ".pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // FORMATEAR NÚMERO PARA RESULTADOS HTML

    private String formatearNumeroInforme(
            java.math.BigDecimal valor) {

        if (valor == null) {
            return "";
        }

        return new java.text.DecimalFormat(
                "#0.##"
        ).format(valor);
    }

    // INFORME GENERAL DE PAGOS

    private void generarInformePagos(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String desdeParametro = limpiar(request.getParameter("desde"));
        String hastaParametro = limpiar(request.getParameter("hasta"));

        try {
            if (desdeParametro.isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Inicio.");
            }
            if (hastaParametro.isEmpty()) {
                throw new Exception("Debe seleccionar la Fecha Fin.");
            }

            Date fechaDesde = Date.valueOf(desdeParametro);
            Date fechaHasta = Date.valueOf(hastaParametro);

            if (fechaDesde.after(fechaHasta)) {
                throw new Exception("La Fecha Inicio no puede ser posterior a la Fecha Fin.");
            }

            String rutaReporte = "/WEB-INF/reportes/informe_pagos.jrxml";
            InputStream inputStream = getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte);
            }

            JasperReport jasperReport;
            try (InputStream reporteStream = inputStream) {
                jasperReport = JasperCompileManager.compileReport(reporteStream);
            }

            InputStream logoStream = getServletContext().getResourceAsStream("/WEB-INF/reportes/logo.png");
            if (logoStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png");
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("DESDE", fechaDesde);
            parametros.put("HASTA", fechaHasta);
            parametros.put("LOGO", logoStream);
            parametros.put("USUARIO_IMPRESION", obtenerUsuarioSesion(request));

            Conexion conexion = new Conexion();
            try (Connection con = conexion.getConexion()) {
                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        con
                );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_pagos.pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // BUSCAR PAGOS PARA INFORME INDIVIDUAL

    private void buscarPagosInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        String id = limpiar(request.getParameter("id_pago"));
        String idFactura = limpiar(request.getParameter("id_factura"));
        String numeroFactura = limpiar(request.getParameter("numero_factura"));
        String numeroRecibo = limpiar(request.getParameter("numero_recibo"));
        String cliente = limpiar(request.getParameter("cliente"));
        String fecha = limpiar(request.getParameter("fecha_pago"));
        String periodo = limpiar(request.getParameter("periodo"));
        String metodoPago = limpiar(request.getParameter("metodo_pago"));
        String estado = limpiar(request.getParameter("estado"));

        try {
            StringBuilder sql = new StringBuilder();
            sql.append(
                    "SELECT p.id_pago, p.id_factura, p.fecha_pago, p.periodo, " +
                    "p.monto, p.metodo_pago, p.numero_recibo, p.estado, " +
                    "c.nombre || ' ' || c.apellido AS nombre_cliente, " +
                    "f.numero_factura " +
                    "FROM pago p " +
                    "LEFT JOIN factura f ON p.id_factura = f.id_factura " +
                    "LEFT JOIN clientes c ON f.id_cliente = c.id_cliente " +
                    "WHERE 1 = 1 "
            );

            List<Object> parametros = new ArrayList<>();

            if (!id.isEmpty()) {
                try {
                    sql.append("AND p.id_pago = ? ");
                    parametros.add(Integer.parseInt(id));
                } catch (NumberFormatException e) {
                    throw new Exception("El ID del pago debe ser numérico.");
                }
            }

            if (!idFactura.isEmpty()) {
                try {
                    sql.append("AND p.id_factura = ? ");
                    parametros.add(Integer.parseInt(idFactura));
                } catch (NumberFormatException e) {
                    throw new Exception("El ID de la factura debe ser numérico.");
                }
            }

            if (!numeroFactura.isEmpty()) {
                sql.append("AND LOWER(f.numero_factura) LIKE LOWER(?) ");
                parametros.add("%" + numeroFactura + "%");
            }

            if (!numeroRecibo.isEmpty()) {
                sql.append("AND LOWER(COALESCE(p.numero_recibo, '')) LIKE LOWER(?) ");
                parametros.add("%" + numeroRecibo + "%");
            }

            if (!cliente.isEmpty()) {
                sql.append(
                        "AND LOWER(COALESCE(c.nombre, '') || ' ' || COALESCE(c.apellido, '')) LIKE LOWER(?) "
                );
                parametros.add("%" + cliente + "%");
            }

            if (!fecha.isEmpty()) {
                try {
                    sql.append("AND p.fecha_pago = ? ");
                    parametros.add(Date.valueOf(fecha));
                } catch (IllegalArgumentException e) {
                    throw new Exception("La fecha de pago no es válida.");
                }
            }

            if (!periodo.isEmpty()) {
                sql.append("AND LOWER(COALESCE(p.periodo, '')) LIKE LOWER(?) ");
                parametros.add("%" + periodo + "%");
            }

            if (!metodoPago.isEmpty()) {
                sql.append("AND LOWER(COALESCE(p.metodo_pago, '')) LIKE LOWER(?) ");
                parametros.add("%" + metodoPago + "%");
            }

            if (!estado.isEmpty()) {
                sql.append("AND p.estado = ? ");
                parametros.add(estado);
            }

            sql.append("ORDER BY p.fecha_pago DESC, p.id_pago DESC");

            Conexion conexion = new Conexion();
            try (Connection con = conexion.getConexion()) {
                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
                    for (int i = 0; i < parametros.size(); i++) {
                        Object valor = parametros.get(i);
                        if (valor instanceof Integer) {
                            ps.setInt(i + 1, (Integer) valor);
                        } else if (valor instanceof Date) {
                            ps.setDate(i + 1, (Date) valor);
                        } else {
                            ps.setString(i + 1, String.valueOf(valor));
                        }
                    }

                    try (ResultSet rs = ps.executeQuery()) {
                        StringBuilder html = new StringBuilder();
                        html.append("<div class=\"table-responsive\">");
                        html.append("<table class=\"table table-bordered table-hover table-striped\">");
                        html.append("<thead><tr>");
                        html.append("<th style=\"width:50px;\">Sel.</th>");
                        html.append("<th>ID</th>");
                        html.append("<th>Recibo</th>");
                        html.append("<th>Factura</th>");
                        html.append("<th>Cliente</th>");
                        html.append("<th>Fecha</th>");
                        html.append("<th>Período</th>");
                        html.append("<th>Monto</th>");
                        html.append("<th>Método</th>");
                        html.append("<th>Estado</th>");
                        html.append("</tr></thead><tbody>");

                        boolean hayResultados = false;

                        while (rs.next()) {
                            hayResultados = true;

                            String estadoPago = rs.getString("estado");
                            String claseEstado = "secondary";

                            if ("PAGADO".equalsIgnoreCase(estadoPago)) {
                                claseEstado = "success";
                            } else if ("ANULADO".equalsIgnoreCase(estadoPago)) {
                                claseEstado = "danger";
                            } else if ("PENDIENTE".equalsIgnoreCase(estadoPago)) {
                                claseEstado = "warning";
                            }

                            Date fechaPago = rs.getDate("fecha_pago");

                            html.append("<tr>");
                            html.append(
                                    "<td class=\"text-center\"><input type=\"radio\" " +
                                    "name=\"pagoSeleccionado\" value=\"" +
                                    rs.getInt("id_pago") + "\"></td>"
                            );
                            html.append("<td class=\"text-center\">")
                                    .append(rs.getInt("id_pago"))
                                    .append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(rs.getString("numero_recibo")))
                                    .append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(rs.getString("numero_factura")))
                                    .append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(rs.getString("nombre_cliente")))
                                    .append("</td>");
                            html.append("<td class=\"text-center\">")
                                    .append(fechaPago == null
                                            ? ""
                                            : new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaPago))
                                    .append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(rs.getString("periodo")))
                                    .append("</td>");
                            html.append("<td class=\"text-right\">")
                                    .append(formatearNumeroInforme(rs.getBigDecimal("monto")))
                                    .append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(rs.getString("metodo_pago")))
                                    .append("</td>");
                            html.append(
                                    "<td class=\"text-center\"><span class=\"badge badge-" +
                                    claseEstado + "\">" +
                                    escaparHtml(estadoPago) +
                                    "</span></td>"
                            );
                            html.append("</tr>");
                        }

                        html.append("</tbody></table></div>");

                        if (!hayResultados) {
                            html.setLength(0);
                            html.append(
                                    "<div class=\"alert alert-info\">" +
                                    "<i class=\"fas fa-info-circle mr-1\"></i>" +
                                    "No se encontraron pagos con los filtros seleccionados." +
                                    "</div>"
                            );
                        } else {
                            html.append(
                                    "<div class=\"text-right mt-3\"><button type=\"button\" " +
                                    "class=\"btn btn-primary\" onclick=\"generarInformeIndividualPago()\">" +
                                    "<i class=\"fas fa-file-pdf mr-1\"></i> Generar Informe Individual" +
                                    "</button></div>"
                            );
                        }

                        response.getWriter().write(html.toString());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    "<div class=\"alert alert-danger\"><h5>" +
                    "<i class=\"fas fa-exclamation-triangle\"></i> Error al realizar la búsqueda</h5>" +
                    "<p>" +
                    escaparHtml(e.getMessage() != null ? e.getMessage() : "Error desconocido.") +
                    "</p></div>"
            );
        }
    }

    // INFORME INDIVIDUAL DE PAGO

    private void generarInformePagoIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParametro = limpiar(request.getParameter("id_pago"));
        if (idParametro.isEmpty()) {
            idParametro = limpiar(request.getParameter("idPago"));
        }

        try {
            if (idParametro.isEmpty()) {
                throw new Exception("Debe seleccionar un pago.");
            }

            int idPago;
            try {
                idPago = Integer.parseInt(idParametro);
            } catch (NumberFormatException e) {
                throw new Exception("El ID del pago no es válido.");
            }

            if (idPago <= 0) {
                throw new Exception("El ID del pago debe ser mayor que cero.");
            }

            String rutaReporte = "/WEB-INF/reportes/informe_pago_individual.jrxml";
            InputStream inputStream = getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte);
            }

            JasperReport jasperReport;
            try (InputStream reporteStream = inputStream) {
                jasperReport = JasperCompileManager.compileReport(reporteStream);
            }

            InputStream logoStream = getServletContext().getResourceAsStream("/WEB-INF/reportes/logo.png");
            if (logoStream == null) {
                throw new Exception("NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png");
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_PAGO", idPago);
            parametros.put("LOGO", logoStream);
            parametros.put("USUARIO_IMPRESION", obtenerUsuarioSesion(request));

            Conexion conexion = new Conexion();
            try (Connection con = conexion.getConexion()) {
                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        con
                );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_pago_" + idPago + ".pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // INFORME GENERAL DE RESIDUOS

    private void generarInformeResiduos(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String rutaReporte =
                    "/WEB-INF/reportes/informe_residuos.jrxml";

            InputStream inputStream =
                    getServletContext()
                            .getResourceAsStream(
                                    rutaReporte
                            );

            if (inputStream == null) {

                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: "
                        + rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream =
                         inputStream) {

                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            String logoPath =
                    getServletContext()
                            .getRealPath(
                                    "/WEB-INF/reportes/logo.png"
                            );

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(request)
            );

            parametros.put(
                    "logoPath",
                    logoPath != null
                            ? logoPath
                            : ""
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();

                response.setContentType(
                        "application/pdf"
                );

                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_residuos.pdf"
                );

                JasperExportManager
                        .exportReportToPdfStream(
                                jasperPrint,
                                response.getOutputStream()
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    e
            );
        }
    }

    // BUSCAR TIPOS DE RESIDUOS

    private void buscarTiposResiduosInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        String idTipo =
                limpiar(
                        request.getParameter(
                                "id_tipo"
                        )
                );

        String nombre =
                limpiar(
                        request.getParameter(
                                "nombre"
                        )
                );

        String descripcion =
                limpiar(
                        request.getParameter(
                                "descripcion"
                        )
                );

        String estado =
                limpiar(
                        request.getParameter(
                                "estado"
                        )
                );

        try {

            StringBuilder sql =
                    new StringBuilder();

            sql.append(
                    "SELECT " +
                    "id_tipo, " +
                    "nombre, " +
                    "descripcion, " +
                    "estado " +
                    "FROM tipos_residuos " +
                    "WHERE 1 = 1 "
            );

            List<Object> parametros =
                    new ArrayList<>();

            // ID

            if (!idTipo.isEmpty()) {

                try {

                    sql.append(
                            "AND id_tipo = ? "
                    );

                    parametros.add(
                            Integer.parseInt(
                                    idTipo
                            )
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El ID de tipo de residuo debe ser numérico."
                    );
                }
            }

            // NOMBRE

            if (!nombre.isEmpty()) {

                sql.append(
                        "AND LOWER(nombre) LIKE LOWER(?) "
                );

                parametros.add(
                        "%" + nombre + "%"
                );
            }

            // DESCRIPCIÓN

            if (!descripcion.isEmpty()) {

                sql.append(
                        "AND LOWER(COALESCE(descripcion, '')) " +
                        "LIKE LOWER(?) "
                );

                parametros.add(
                        "%" + descripcion + "%"
                );
            }

            // ESTADO

            if (!estado.isEmpty()) {

                if (!"true".equalsIgnoreCase(estado) &&
                    !"false".equalsIgnoreCase(estado)) {

                    throw new Exception(
                            "El estado seleccionado no es válido."
                    );
                }

                sql.append(
                        "AND estado = ? "
                );

                parametros.add(
                        Boolean.parseBoolean(
                                estado
                        )
                );
            }

            sql.append(
                    "ORDER BY id_tipo"
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     sql.toString()
                             )) {

                    for (int i = 0;
                         i < parametros.size();
                         i++) {

                        Object valor =
                                parametros.get(i);

                        if (valor instanceof Integer) {

                            ps.setInt(
                                    i + 1,
                                    (Integer) valor
                            );

                        } else if (valor instanceof Boolean) {

                            ps.setBoolean(
                                    i + 1,
                                    (Boolean) valor
                            );

                        } else {

                            ps.setString(
                                    i + 1,
                                    String.valueOf(
                                            valor
                                    )
                            );
                        }
                    }

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        StringBuilder html =
                                new StringBuilder();

                        html.append(
                                "<div class=\"table-responsive\">"
                        );

                        html.append(
                                "<table class=\"table table-bordered " +
                                "table-hover table-striped\">"
                        );

                        html.append("<thead>");
                        html.append("<tr>");

                        html.append(
                                "<th style=\"width:50px;\">"
                        );

                        html.append("Sel.");

                        html.append("</th>");

                        html.append("<th>ID</th>");
                        html.append("<th>Nombre</th>");
                        html.append("<th>Descripción</th>");
                        html.append("<th>Estado</th>");

                        html.append("</tr>");
                        html.append("</thead>");

                        html.append("<tbody>");

                        int cantidadResultados = 0;

                        while (rs.next()) {

                            cantidadResultados++;

                            int id =
                                    rs.getInt(
                                            "id_tipo"
                                    );

                            String nombreResultado =
                                    rs.getString(
                                            "nombre"
                                    );

                            String descripcionResultado =
                                    rs.getString(
                                            "descripcion"
                                    );

                            boolean estadoResultado =
                                    rs.getBoolean(
                                            "estado"
                                    );

                            html.append("<tr>");

                            // RADIO

                            html.append(
                                    "<td class=\"text-center\">"
                            );

                            html.append(
                                    "<input type=\"radio\" " +
                                    "name=\"tipoResiduoSeleccionado\" " +
                                    "value=\"" +
                                    id +
                                    "\">"
                            );

                            html.append("</td>");

                            // ID

                            html.append("<td>");

                            html.append(
                                    id
                            );

                            html.append("</td>");

                            // NOMBRE

                            html.append("<td>");

                            html.append(
                                    escaparHtml(
                                            nombreResultado
                                    )
                            );

                            html.append("</td>");

                            // DESCRIPCIÓN

                            html.append("<td>");

                            html.append(
                                    escaparHtml(
                                            descripcionResultado
                                    )
                            );

                            html.append("</td>");

                            // ESTADO

                            html.append(
                                    "<td class=\"text-center\">"
                            );

                            String claseEstado =
                                    estadoResultado
                                            ? "badge-success"
                                            : "badge-secondary";

                            String textoEstado =
                                    estadoResultado
                                            ? "Activo"
                                            : "Inactivo";

                            html.append(
                                    "<span class=\"badge " +
                                    claseEstado +
                                    "\">"
                            );

                            html.append(
                                    textoEstado
                            );

                            html.append("</span>");

                            html.append("</td>");

                            html.append("</tr>");
                        }

                        html.append("</tbody>");
                        html.append("</table>");
                        html.append("</div>");

                        // SIN RESULTADOS

                        if (cantidadResultados == 0) {

                            response.getWriter().write(

                                    "<div class=\"alert alert-warning\">" +

                                    "<i class=\"fas fa-search\"></i> " +

                                    "No se encontraron tipos de residuos " +
                                    "con los filtros seleccionados." +

                                    "</div>"
                            );

                            return;
                        }

                        // INFORMACIÓN + BOTÓN INDIVIDUAL

                        html.append(
                                "<div class=\"mt-3 d-flex " +
                                "justify-content-between " +
                                "align-items-center\">"
                        );

                        html.append(
                                "<span class=\"text-muted\">" +
                                "<i class=\"fas fa-info-circle\"></i> " +
                                cantidadResultados +
                                " resultado(s) encontrado(s). " +
                                "Seleccione un tipo de residuo para generar " +
                                "el informe individual." +
                                "</span>"
                        );

                        html.append(
                                "<button type=\"button\" " +
                                "class=\"btn btn-primary\" " +
                                "onclick=\"generarInformeIndividualResiduo()\">"
                        );

                        html.append(
                                "<i class=\"fas fa-file-pdf\"></i> " +
                                "Generar Informe Individual"
                        );

                        html.append("</button>");

                        html.append("</div>");

                        response.getWriter().write(
                                html.toString()
                        );
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(

                    "<div class=\"alert alert-danger\">" +

                    "<h5>" +

                    "<i class=\"fas fa-exclamation-triangle\"></i> " +

                    "Error al realizar la búsqueda" +

                    "</h5>" +

                    "<p>" +

                    escaparHtml(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Error desconocido."
                    ) +

                    "</p>" +

                    "</div>"
            );
        }
    }

    // INFORME GENERAL DE VEHÍCULOS

    private void generarInformeVehiculos(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String desdeParametro = request.getParameter("desde");
        String hastaParametro = request.getParameter("hasta");

        try {

            if (desdeParametro == null ||
                desdeParametro.trim().isEmpty()) {

                throw new Exception(
                        "Debe seleccionar la Fecha Inicio."
                );
            }

            if (hastaParametro == null ||
                hastaParametro.trim().isEmpty()) {

                throw new Exception(
                        "Debe seleccionar la Fecha Fin."
                );
            }

            Date fechaDesde = Date.valueOf(desdeParametro);
            Date fechaHasta = Date.valueOf(hastaParametro);

            if (fechaDesde.after(fechaHasta)) {

                throw new Exception(
                        "La Fecha Inicio no puede ser posterior a la Fecha Fin."
                );
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_vehiculos.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(
                            rutaReporte
                    );

            if (inputStream == null) {

                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: " +
                        rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream = inputStream) {

                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "DESDE",
                    fechaDesde
            );

            parametros.put(
                    "HASTA",
                    fechaHasta
            );

            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(request)
            );

            parametros.put(
                    "logoPath",
                    getServletContext().getRealPath(
                            "/WEB-INF/reportes/logo.png"
                    )
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();

                response.setContentType(
                        "application/pdf"
                );

                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_vehiculos.pdf"
                );

                JasperExportManager
                        .exportReportToPdfStream(
                                jasperPrint,
                                response.getOutputStream()
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    e
            );
        }
    }


    // CARGAR FILTROS DE VEHÍCULOS

    private void cargarFiltrosVehiculos(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        try {

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                List<Map<String, String>> modelos =
                        new ArrayList<>();

                String sqlModelos =
                        "SELECT id_modelo, nombre_modelo " +
                        "FROM modelo " +
                        "ORDER BY nombre_modelo";

                try (PreparedStatement ps =
                             con.prepareStatement(sqlModelos);
                     ResultSet rs =
                             ps.executeQuery()) {

                    while (rs.next()) {

                        Map<String, String> item =
                                new HashMap<>();

                        item.put(
                                "id",
                                String.valueOf(
                                        rs.getInt("id_modelo")
                                )
                        );

                        item.put(
                                "nombre",
                                rs.getString("nombre_modelo")
                        );

                        modelos.add(item);
                    }
                }

                StringBuilder json =
                        new StringBuilder();

                json.append("{");
                json.append("\"modelos\":");
                json.append(listaAJson(modelos));
                json.append("}");

                response.getWriter().write(
                        json.toString()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(
                    "{\"error\":\"" +
                    escaparJson(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Error al cargar modelos"
                    ) +
                    "\"}"
            );
        }
    }


    // BUSCAR VEHÍCULOS PARA INFORME INDIVIDUAL

    private void buscarVehiculosInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        String idVehiculo =
                limpiar(request.getParameter("id_vehiculo"));

        String chapa =
                limpiar(request.getParameter("chapa"));

        String marca =
                limpiar(request.getParameter("marca"));

        String anio =
                limpiar(request.getParameter("anio"));

        String idModelo =
                limpiar(request.getParameter("id_modelo"));

        String estado =
                limpiar(request.getParameter("estado"));

        try {

            StringBuilder sql =
                    new StringBuilder();

            sql.append(
                    "SELECT " +
                    "v.id_vehiculo, " +
                    "v.chapa, " +
                    "m.marca, " +
                    "m.nombre_modelo, " +
                    "v.anio, " +
                    "v.capacidad, " +
                    "v.estado " +
                    "FROM vehiculo v " +
                    "LEFT JOIN modelo m " +
                    "ON v.id_modelo = m.id_modelo " +
                    "WHERE 1 = 1 "
            );

            List<Object> parametros =
                    new ArrayList<>();

            if (!idVehiculo.isEmpty()) {

                try {

                    sql.append(
                            "AND v.id_vehiculo = ? "
                    );

                    parametros.add(
                            Integer.parseInt(idVehiculo)
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El ID del vehículo debe ser numérico."
                    );
                }
            }

            if (!chapa.isEmpty()) {

                sql.append(
                        "AND v.chapa ILIKE ? "
                );

                parametros.add(
                        "%" + chapa + "%"
                );
            }

            if (!marca.isEmpty()) {

                sql.append(
                        "AND m.marca ILIKE ? "
                );

                parametros.add(
                        "%" + marca + "%"
                );
            }

            if (!anio.isEmpty()) {

                try {

                    sql.append(
                            "AND v.anio = ? "
                    );

                    parametros.add(
                            Integer.parseInt(anio)
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El año del vehículo debe ser numérico."
                    );
                }
            }

            if (!idModelo.isEmpty()) {

                try {

                    sql.append(
                            "AND v.id_modelo = ? "
                    );

                    parametros.add(
                            Integer.parseInt(idModelo)
                    );

                } catch (NumberFormatException e) {

                    throw new Exception(
                            "El modelo seleccionado no es válido."
                    );
                }
            }

            if (!estado.isEmpty()) {

                if (!"ACTIVO".equalsIgnoreCase(estado) &&
                    !"INACTIVO".equalsIgnoreCase(estado)) {

                    throw new Exception(
                            "El estado seleccionado no es válido."
                    );
                }

                sql.append(
                        "AND UPPER(v.estado) = UPPER(?) "
                );

                parametros.add(estado);
            }

            sql.append(
                    "ORDER BY v.id_vehiculo"
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     sql.toString()
                             )) {

                    for (int i = 0;
                         i < parametros.size();
                         i++) {

                        Object valor =
                                parametros.get(i);

                        if (valor instanceof Integer) {

                            ps.setInt(
                                    i + 1,
                                    (Integer) valor
                            );

                        } else {

                            ps.setString(
                                    i + 1,
                                    String.valueOf(valor)
                            );
                        }
                    }

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        StringBuilder html =
                                new StringBuilder();

                        html.append(
                                "<div class=\"table-responsive\">"
                        );

                        html.append(
                                "<table class=\"table table-bordered " +
                                "table-hover table-striped\">"
                        );

                        html.append("<thead><tr>");
                        html.append(
                                "<th style=\"width:50px;\">Sel.</th>"
                        );
                        html.append("<th>ID</th>");
                        html.append("<th>Chapa</th>");
                        html.append("<th>Marca</th>");
                        html.append("<th>Modelo</th>");
                        html.append("<th>Año</th>");
                        html.append("<th>Capacidad</th>");
                        html.append("<th>Estado</th>");
                        html.append("</tr></thead>");
                        html.append("<tbody>");

                        int cantidadResultados = 0;

                        while (rs.next()) {

                            cantidadResultados++;

                            int id =
                                    rs.getInt("id_vehiculo");

                            String chapaResultado =
                                    rs.getString("chapa");

                            String marcaResultado =
                                    rs.getString("marca");

                            String modeloResultado =
                                    rs.getString("nombre_modelo");

                            int anioResultado =
                                    rs.getInt("anio");

                            double capacidad =
                                    rs.getDouble("capacidad");

                            String estadoResultado =
                                    rs.getString("estado");

                            html.append("<tr>");

                            html.append(
                                    "<td class=\"text-center\">" +
                                    "<input type=\"radio\" " +
                                    "name=\"vehiculoSeleccionado\" " +
                                    "value=\"" + id + "\">" +
                                    "</td>"
                            );

                            html.append("<td>");
                            html.append(id);
                            html.append("</td>");

                            html.append("<td>");
                            html.append(escaparHtml(chapaResultado));
                            html.append("</td>");

                            html.append("<td>");
                            html.append(escaparHtml(marcaResultado));
                            html.append("</td>");

                            html.append("<td>");
                            html.append(escaparHtml(modeloResultado));
                            html.append("</td>");

                            html.append("<td>");
                            html.append(anioResultado);
                            html.append("</td>");

                            html.append("<td>");

                            if (capacidad == Math.floor(capacidad)) {
                                html.append(
                                        String.format("%.0f", capacidad)
                                );
                            } else {
                                html.append(
                                        String.valueOf(capacidad)
                                );
                            }

                            html.append("</td>");

                            html.append(
                                    "<td class=\"text-center\">"
                            );

                            String claseEstado =
                                    "ACTIVO".equalsIgnoreCase(
                                            estadoResultado
                                    )
                                            ? "badge-success"
                                            : "badge-danger";

                            html.append(
                                    "<span class=\"badge " +
                                    claseEstado +
                                    "\">"
                            );

                            html.append(
                                    escaparHtml(estadoResultado)
                            );

                            html.append("</span>");
                            html.append("</td>");

                            html.append("</tr>");
                        }

                        html.append("</tbody></table></div>");

                        if (cantidadResultados == 0) {

                            response.getWriter().write(
                                    "<div class=\"alert alert-warning\">" +
                                    "<i class=\"fas fa-search\"></i> " +
                                    "No se encontraron vehículos con los filtros seleccionados." +
                                    "</div>"
                            );

                            return;
                        }

                        html.append(
                                "<div class=\"mt-3 d-flex " +
                                "justify-content-between align-items-center\">"
                        );

                        html.append(
                                "<span class=\"text-muted\">" +
                                "<i class=\"fas fa-info-circle\"></i> " +
                                cantidadResultados +
                                " resultado(s) encontrado(s). " +
                                "Seleccione un vehículo para generar el informe individual." +
                                "</span>"
                        );

                        html.append(
                                "<button type=\"button\" " +
                                "class=\"btn btn-primary\" " +
                                "onclick=\"generarInformeIndividualVehiculo()\">"
                        );

                        html.append(
                                "<i class=\"fas fa-file-pdf\"></i> " +
                                "Generar Informe Individual"
                        );

                        html.append("</button>");
                        html.append("</div>");

                        response.getWriter().write(
                                html.toString()
                        );
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(
                    "<div class=\"alert alert-danger\">" +
                    "<h5>" +
                    "<i class=\"fas fa-exclamation-triangle\"></i> " +
                    "Error al realizar la búsqueda" +
                    "</h5>" +
                    "<p>" +
                    escaparHtml(
                            e.getMessage() != null
                                    ? e.getMessage()
                                    : "Error desconocido."
                    ) +
                    "</p>" +
                    "</div>"
            );
        }
    }


    // INFORME INDIVIDUAL DE VEHÍCULO

    private void generarInformeVehiculoIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParametro =
                request.getParameter("idVehiculo");

        try {

            if (idParametro == null ||
                idParametro.trim().isEmpty()) {

                throw new Exception(
                        "Debe seleccionar un vehículo."
                );
            }

            int idVehiculo;

            try {

                idVehiculo =
                        Integer.parseInt(
                                idParametro.trim()
                        );

            } catch (NumberFormatException e) {

                throw new Exception(
                        "El ID del vehículo no es válido."
                );
            }

            if (idVehiculo <= 0) {

                throw new Exception(
                        "El ID del vehículo debe ser mayor que cero."
                );
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_vehiculo_individual.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(
                            rutaReporte
                    );

            if (inputStream == null) {

                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: " +
                        rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream = inputStream) {

                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "idVehiculo",
                    idVehiculo
            );

            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(request)
            );

            parametros.put(
                    "logoPath",
                    getServletContext().getRealPath(
                            "/WEB-INF/reportes/logo.png"
                    )
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();

                response.setContentType(
                        "application/pdf"
                );

                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_vehiculo_" +
                        idVehiculo +
                        ".pdf"
                );

                JasperExportManager
                        .exportReportToPdfStream(
                                jasperPrint,
                                response.getOutputStream()
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    e
            );
        }
    }


    // INFORME INDIVIDUAL DE TIPO DE RESIDUO

    private void generarInformeTipoResiduoIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParametro =
                request.getParameter(
                        "idTipo"
                );

        try {

            if (idParametro == null ||
                idParametro.trim().isEmpty()) {

                throw new Exception(
                        "Debe seleccionar un tipo de residuo."
                );
            }

            int idTipo;

            try {

                idTipo =
                        Integer.parseInt(
                                idParametro
                        );

            } catch (NumberFormatException e) {

                throw new Exception(
                        "El ID del tipo de residuo no es válido."
                );
            }

            String rutaReporte =
                    "/WEB-INF/reportes/" +
                    "informe_tipo_residuo_individual.jrxml";

            InputStream inputStream =
                    getServletContext()
                            .getResourceAsStream(
                                    rutaReporte
                            );

            if (inputStream == null) {

                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: "
                        + rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream =
                         inputStream) {

                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            String logoPath =
                    getServletContext()
                            .getRealPath(
                                    "/WEB-INF/reportes/logo.png"
                            );

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "idTipo",
                    idTipo
            );

            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(request)
            );

            parametros.put(
                    "logoPath",
                    logoPath != null
                            ? logoPath
                            : ""
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();

                response.setContentType(
                        "application/pdf"
                );

                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=" +
                        "informe_tipo_residuo_" +
                        idTipo +
                        ".pdf"
                );

                JasperExportManager
                        .exportReportToPdfStream(
                                jasperPrint,
                                response.getOutputStream()
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    e
            );
        }
    }

    // INFORME INDIVIDUAL PDF DE RECOLECCIÓN

    private void generarInformeRecoleccionIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParametro =
                request.getParameter(
                        "id_recoleccion"
                );

        try {

            if (idParametro == null ||
                idParametro.trim().isEmpty()) {

                throw new Exception(
                        "Debe seleccionar una recolección."
                );
            }

            int idRecoleccion;

            try {

                idRecoleccion =
                        Integer.parseInt(
                                idParametro
                        );

            } catch (NumberFormatException e) {

                throw new Exception(
                        "El ID de recolección no es válido."
                );
            }

            String rutaReporte =
                    "/WEB-INF/reportes/" +
                    "informe_recoleccion_individual.jrxml";

            InputStream inputStream =
                    getServletContext()
                            .getResourceAsStream(
                                    rutaReporte
                            );

            if (inputStream == null) {

                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: " +
                        rutaReporte
                );
            }

            JasperReport jasperReport;

            try (InputStream reporteStream =
                         inputStream) {

                jasperReport =
                        JasperCompileManager.compileReport(
                                reporteStream
                        );
            }

            InputStream logoStream =
                    getServletContext()
                            .getResourceAsStream(
                                    "/WEB-INF/reportes/logo.png"
                            );

            if (logoStream == null) {

                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: " +
                        "/WEB-INF/reportes/logo.png"
                );
            }

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "ID_RECOLECCION",
                    idRecoleccion
            );

            parametros.put(
                    "LOGO",
                    logoStream
            );

            parametros.put(
                    "USUARIO_IMPRESION",
                    obtenerUsuarioSesion(request)
            );

            Conexion conexion =
                    new Conexion();

            try (Connection con =
                         conexion.getConexion()) {

                if (con == null) {

                    throw new Exception(
                            "La conexión a PostgreSQL es NULL."
                    );
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();

                response.setContentType(
                        "application/pdf"
                );

                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=" +
                        "informe_recoleccion_" +
                        idRecoleccion +
                        ".pdf"
                );

                JasperExportManager
                        .exportReportToPdfStream(
                                jasperPrint,
                                response.getOutputStream()
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    e
            );
        }
    }


    // INFORME GENERAL DE PENDIENTES

    private void generarInformePendientes(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {
            String fechaInicioTexto = limpiar(request.getParameter("fecha_inicio"));
            String fechaFinTexto = limpiar(request.getParameter("fecha_fin"));

            Date fechaInicio = null;
            Date fechaFin = null;

            if (!fechaInicioTexto.isEmpty()) {
                fechaInicio = Date.valueOf(fechaInicioTexto);
            }

            if (!fechaFinTexto.isEmpty()) {
                fechaFin = Date.valueOf(fechaFinTexto);
            }

            if (fechaInicio != null && fechaFin != null && fechaInicio.after(fechaFin)) {
                throw new Exception("La Fecha de Inicio no puede ser posterior a la Fecha de Fin.");
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_pendientes.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte
                );
            }

            JasperReport jasperReport;
            try (InputStream reporteStream = inputStream) {
                jasperReport =
                        JasperCompileManager.compileReport(reporteStream);
            }

            InputStream logoStream =
                    getServletContext().getResourceAsStream(
                            "/WEB-INF/reportes/logo.png"
                    );

            if (logoStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png"
                );
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("LOGO", logoStream);
            parametros.put("FECHA_INICIO", fechaInicio);
            parametros.put("FECHA_FIN", fechaFin);
            parametros.put(
                    "USUARIO_IMPRESION",
                    obtenerUsuarioSesion(request)
            );

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {

                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_pendientes.pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // BUSCAR PENDIENTES PARA INFORME INDIVIDUAL

    private void buscarPendientesInforme(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        String idCliente = limpiar(request.getParameter("id_cliente"));
        String cliente = limpiar(request.getParameter("cliente"));
        String cedula = limpiar(request.getParameter("cedula"));
        String barrio = limpiar(request.getParameter("barrio"));
        String fechaInicioTexto = limpiar(request.getParameter("fecha_inicio"));
        String fechaFinTexto = limpiar(request.getParameter("fecha_fin"));

        try {
            Date fechaInicio = null;
            Date fechaFin = null;

            if (!fechaInicioTexto.isEmpty()) {
                fechaInicio = Date.valueOf(fechaInicioTexto);
            }

            if (!fechaFinTexto.isEmpty()) {
                fechaFin = Date.valueOf(fechaFinTexto);
            }

            if (fechaInicio != null && fechaFin != null && fechaInicio.after(fechaFin)) {
                throw new Exception("La Fecha de Inicio no puede ser posterior a la Fecha de Fin.");
            }

            StringBuilder sql = new StringBuilder();
            sql.append(
                    "SELECT c.id_cliente, c.nombre, c.apellido, c.cedula, c.direccion, " +
                    "b.nombre AS barrio, " +
                    "COALESCE(f.total_facturado, 0) AS total_facturado, " +
                    "COALESCE(p.total_pagado, 0) AS total_pagado, " +
                    "CASE WHEN COALESCE(f.total_facturado, 0) > COALESCE(p.total_pagado, 0) " +
                    "THEN COALESCE(f.total_facturado, 0) - COALESCE(p.total_pagado, 0) ELSE 0 END AS saldo_pendiente, " +
                    "CASE WHEN COALESCE(p.total_pagado, 0) > COALESCE(f.total_facturado, 0) " +
                    "THEN COALESCE(p.total_pagado, 0) - COALESCE(f.total_facturado, 0) ELSE 0 END AS excedente, " +
                    "CASE " +
                    "WHEN COALESCE(f.total_facturado, 0) = 0 AND COALESCE(p.total_pagado, 0) = 0 THEN 'SIN MOVIMIENTOS' " +
                    "WHEN COALESCE(f.total_facturado, 0) = 0 AND COALESCE(p.total_pagado, 0) > 0 THEN 'SIN FACTURA' " +
                    "WHEN COALESCE(p.total_pagado, 0) = 0 THEN 'PENDIENTE' " +
                    "WHEN COALESCE(p.total_pagado, 0) < COALESCE(f.total_facturado, 0) THEN 'PENDIENTE' " +
                    "WHEN COALESCE(p.total_pagado, 0) = COALESCE(f.total_facturado, 0) THEN 'PAGADO' " +
                    "WHEN COALESCE(p.total_pagado, 0) > COALESCE(f.total_facturado, 0) THEN 'PAGO SUPERIOR' " +
                    "ELSE 'SIN MOVIMIENTOS' END AS estado_cuenta " +
                    "FROM clientes c " +
                    "LEFT JOIN barrio b ON c.id_barrio = b.id_barrio " +
                    "LEFT JOIN ( " +
                    "SELECT factura.id_cliente, SUM(factura.total) AS total_facturado " +
                    "FROM factura " +
                    "WHERE factura.estado IN ('EMITIDA', 'PAGADA') " +
                    "AND (CAST(? AS DATE) IS NULL OR factura.fecha_emision >= CAST(? AS DATE)) " +
                    "AND (CAST(? AS DATE) IS NULL OR factura.fecha_emision <= CAST(? AS DATE)) " +
                    "GROUP BY factura.id_cliente " +
                    ") f ON c.id_cliente = f.id_cliente " +
                    "LEFT JOIN ( " +
                    "SELECT factura.id_cliente, SUM(pago.monto) AS total_pagado " +
                    "FROM pago INNER JOIN factura ON factura.id_factura = pago.id_factura " +
                    "WHERE pago.estado = 'PAGADO' " +
                    "AND (CAST(? AS DATE) IS NULL OR factura.fecha_emision >= CAST(? AS DATE)) " +
                    "AND (CAST(? AS DATE) IS NULL OR factura.fecha_emision <= CAST(? AS DATE)) " +
                    "GROUP BY factura.id_cliente " +
                    ") p ON c.id_cliente = p.id_cliente " +
                    "WHERE 1=1 "
            );

            List<Object> parametros = new ArrayList<>();

            // Fechas de emisión de las facturas.
            parametros.add(fechaInicio);
            parametros.add(fechaInicio);
            parametros.add(fechaFin);
            parametros.add(fechaFin);
            parametros.add(fechaInicio);
            parametros.add(fechaInicio);
            parametros.add(fechaFin);
            parametros.add(fechaFin);

            sql.append(
                    "AND (CASE " +
                    "WHEN COALESCE(f.total_facturado, 0) = 0 AND COALESCE(p.total_pagado, 0) = 0 THEN 'SIN MOVIMIENTOS' " +
                    "WHEN COALESCE(f.total_facturado, 0) = 0 AND COALESCE(p.total_pagado, 0) > 0 THEN 'SIN FACTURA' " +
                    "WHEN COALESCE(p.total_pagado, 0) = 0 THEN 'PENDIENTE' " +
                    "WHEN COALESCE(p.total_pagado, 0) < COALESCE(f.total_facturado, 0) THEN 'PENDIENTE' " +
                    "WHEN COALESCE(p.total_pagado, 0) = COALESCE(f.total_facturado, 0) THEN 'PAGADO' " +
                    "WHEN COALESCE(p.total_pagado, 0) > COALESCE(f.total_facturado, 0) THEN 'PAGO SUPERIOR' " +
                    "ELSE 'SIN MOVIMIENTOS' END) = 'PENDIENTE' "
            );

            if (!idCliente.isEmpty()) {
                try {
                    sql.append("AND c.id_cliente = ? ");
                    parametros.add(Integer.parseInt(idCliente));
                } catch (NumberFormatException e) {
                    throw new Exception("El ID del cliente debe ser numérico.");
                }
            }

            if (!cliente.isEmpty()) {
                sql.append(
                        "AND LOWER(COALESCE(c.nombre, '') || ' ' || COALESCE(c.apellido, '')) LIKE LOWER(?) "
                );
                parametros.add("%" + cliente + "%");
            }

            if (!cedula.isEmpty()) {
                sql.append("AND LOWER(COALESCE(c.cedula, '')) LIKE LOWER(?) ");
                parametros.add("%" + cedula + "%");
            }

            if (!barrio.isEmpty()) {
                sql.append("AND LOWER(COALESCE(b.nombre, '')) LIKE LOWER(?) ");
                parametros.add("%" + barrio + "%");
            }

            sql.append("ORDER BY c.apellido, c.nombre, c.id_cliente");

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {

                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                try (PreparedStatement ps = con.prepareStatement(sql.toString())) {

                    for (int i = 0; i < parametros.size(); i++) {
                        Object valor = parametros.get(i);
                        if (valor == null) {
                            ps.setNull(i + 1, java.sql.Types.DATE);
                        } else if (valor instanceof Date) {
                            ps.setDate(i + 1, (Date) valor);
                        } else if (valor instanceof Integer) {
                            ps.setInt(i + 1, (Integer) valor);
                        } else {
                            ps.setString(i + 1, String.valueOf(valor));
                        }
                    }

                    try (ResultSet rs = ps.executeQuery()) {

                        StringBuilder html = new StringBuilder();
                        html.append("<div class=\"table-responsive\">");
                        html.append("<table class=\"table table-bordered table-hover table-striped\">");
                        html.append("<thead><tr>");
                        html.append("<th style=\"width:50px;\">Sel.</th>");
                        html.append("<th>ID</th>");
                        html.append("<th>Cliente</th>");
                        html.append("<th>Cédula</th>");
                        html.append("<th>Barrio</th>");
                        html.append("<th>Total Facturado</th>");
                        html.append("<th>Total Pagado</th>");
                        html.append("<th>Saldo Pendiente</th>");
                        html.append("<th>Estado</th>");
                        html.append("</tr></thead><tbody>");

                        boolean hayResultados = false;

                        while (rs.next()) {
                            hayResultados = true;

                            html.append("<tr>");
                            html.append(
                                    "<td class=\"text-center\"><input type=\"radio\" " +
                                    "name=\"pendienteSeleccionado\" value=\"" +
                                    rs.getInt("id_cliente") + "\"></td>"
                            );
                            html.append("<td class=\"text-center\">")
                                    .append(rs.getInt("id_cliente"))
                                    .append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(
                                            (rs.getString("nombre") == null ? "" : rs.getString("nombre")) +
                                            " " +
                                            (rs.getString("apellido") == null ? "" : rs.getString("apellido"))
                                    ))
                                    .append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(rs.getString("cedula")))
                                    .append("</td>");
                            html.append("<td>")
                                    .append(escaparHtml(rs.getString("barrio")))
                                    .append("</td>");
                            html.append("<td class=\"text-right\">")
                                    .append(formatearNumeroInforme(rs.getBigDecimal("total_facturado")))
                                    .append("</td>");
                            html.append("<td class=\"text-right\">")
                                    .append(formatearNumeroInforme(rs.getBigDecimal("total_pagado")))
                                    .append("</td>");
                            html.append("<td class=\"text-right font-weight-bold\">")
                                    .append(formatearNumeroInforme(rs.getBigDecimal("saldo_pendiente")))
                                    .append("</td>");
                            html.append(
                                    "<td class=\"text-center\"><span class=\"badge badge-warning\">PENDIENTE</span></td>"
                            );
                            html.append("</tr>");
                        }

                        html.append("</tbody></table></div>");

                        if (!hayResultados) {
                            html.setLength(0);
                            html.append(
                                    "<div class=\"alert alert-info\">" +
                                    "<i class=\"fas fa-info-circle mr-1\"></i>" +
                                    "No se encontraron clientes con saldo pendiente con los filtros seleccionados." +
                                    "</div>"
                            );
                        } else {
                            html.append(
                                    "<div class=\"text-right mt-3\"><button type=\"button\" " +
                                    "class=\"btn btn-primary\" onclick=\"generarInformeIndividualPendiente()\">" +
                                    "<i class=\"fas fa-file-pdf mr-1\"></i> Generar Informe Individual" +
                                    "</button></div>"
                            );
                        }

                        response.getWriter().write(html.toString());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    "<div class=\"alert alert-danger\"><h5>" +
                    "<i class=\"fas fa-exclamation-triangle\"></i> Error al realizar la búsqueda</h5>" +
                    "<p>" +
                    escaparHtml(e.getMessage() != null ? e.getMessage() : "Error desconocido.") +
                    "</p></div>"
            );
        }
    }

    // INFORME INDIVIDUAL DE PENDIENTE

    private void generarInformePendienteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idParametro = limpiar(request.getParameter("id_cliente"));
        if (idParametro.isEmpty()) {
            idParametro = limpiar(request.getParameter("idCliente"));
        }

        String fechaInicioTexto = limpiar(request.getParameter("fecha_inicio"));
        String fechaFinTexto = limpiar(request.getParameter("fecha_fin"));

        try {
            if (idParametro.isEmpty()) {
                throw new Exception("Debe seleccionar un cliente pendiente.");
            }

            int idCliente;
            try {
                idCliente = Integer.parseInt(idParametro);
            } catch (NumberFormatException e) {
                throw new Exception("El ID del cliente no es válido.");
            }

            if (idCliente <= 0) {
                throw new Exception("El ID del cliente debe ser mayor que cero.");
            }

            Date fechaInicio = null;
            Date fechaFin = null;

            if (!fechaInicioTexto.isEmpty()) {
                fechaInicio = Date.valueOf(fechaInicioTexto);
            }

            if (!fechaFinTexto.isEmpty()) {
                fechaFin = Date.valueOf(fechaFinTexto);
            }

            if (fechaInicio != null && fechaFin != null && fechaInicio.after(fechaFin)) {
                throw new Exception("La Fecha de Inicio no puede ser posterior a la Fecha de Fin.");
            }

            String rutaReporte =
                    "/WEB-INF/reportes/informe_pendiente_individual.jrxml";

            InputStream inputStream =
                    getServletContext().getResourceAsStream(rutaReporte);

            if (inputStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL ARCHIVO JRXML: " + rutaReporte
                );
            }

            JasperReport jasperReport;
            try (InputStream reporteStream = inputStream) {
                jasperReport =
                        JasperCompileManager.compileReport(reporteStream);
            }

            InputStream logoStream =
                    getServletContext().getResourceAsStream(
                            "/WEB-INF/reportes/logo.png"
                    );

            if (logoStream == null) {
                throw new Exception(
                        "NO SE ENCONTRÓ EL LOGO: /WEB-INF/reportes/logo.png"
                );
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_CLIENTE", idCliente);
            parametros.put("FECHA_INICIO", fechaInicio);
            parametros.put("FECHA_FIN", fechaFin);
            parametros.put("LOGO", logoStream);
            parametros.put(
                    "USUARIO_IMPRESION",
                    obtenerUsuarioSesion(request)
            );

            Conexion conexion = new Conexion();

            try (Connection con = conexion.getConexion()) {

                if (con == null) {
                    throw new Exception("La conexión a PostgreSQL es NULL.");
                }

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );

                response.reset();
                response.setContentType("application/pdf");
                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=informe_pendiente_" + idCliente + ".pdf"
                );

                JasperExportManager.exportReportToPdfStream(
                        jasperPrint,
                        response.getOutputStream()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(response, e);
        }
    }

    // USUARIO DE SESIÓN

    private String obtenerUsuarioSesion(
            HttpServletRequest request) {

        if (request.getSession().getAttribute(
                "usuario") != null) {

            return String.valueOf(
                    request.getSession().getAttribute(
                            "usuario"
                    )
            );
        }

        return "Sistema";
    }

    // CONVERTIR LISTA A JSON

    private String listaAJson(
            List<Map<String, String>> lista) {

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0;
             i < lista.size();
             i++) {

            Map<String, String> item =
                    lista.get(i);

            if (i > 0) {
                json.append(",");
            }

            json.append("{");

            json.append("\"id\":\"");

            json.append(
                    escaparJson(
                            item.get("id")
                    )
            );

            json.append("\",");

            json.append("\"nombre\":\"");

            json.append(
                    escaparJson(
                            item.get("nombre")
                    )
            );

            json.append("\"");

            json.append("}");
        }

        json.append("]");

        return json.toString();
    }

    // LIMPIAR PARÁMETRO

    private String limpiar(String valor) {

        if (valor == null) {
            return "";
        }

        return valor.trim();
    }

    // ESCAPAR JSON

    private String escaparJson(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
    }

    // ESCAPAR HTML

    private String escaparHtml(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // MOSTRAR ERROR

    private void mostrarError(
            HttpServletResponse response,
            Exception e)
            throws IOException {

        response.reset();

        response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        );

        response.setContentType(
                "text/plain;charset=UTF-8"
        );

        response.getWriter().println(
                "ERROR AL GENERAR EL INFORME"
        );

        response.getWriter().println();

        response.getWriter().println(
                "Mensaje:"
        );

        response.getWriter().println(
                e.getMessage()
        );

        Throwable causa =
                e.getCause();

        int nivel = 1;

        while (causa != null) {

            response.getWriter().println();

            response.getWriter().println(
                    "CAUSA " + nivel + ":"
            );

            response.getWriter().println(
                    causa.getClass().getName()
            );

            response.getWriter().println(
                    causa.getMessage()
            );

            causa = causa.getCause();

            nivel++;
        }
    }
}