<%@page contentType="application/pdf" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="com.config.Conexion,com.config.Permisos,jakarta.servlet.ServletContext,java.io.InputStream,java.io.OutputStream,java.sql.Connection,java.util.HashMap,java.util.Map,net.sf.jasperreports.engine.JasperCompileManager,net.sf.jasperreports.engine.JasperExportManager,net.sf.jasperreports.engine.JasperFillManager,net.sf.jasperreports.engine.JasperPrint,net.sf.jasperreports.engine.JasperReport"%>
<%
    Map<String, String> reportes = new HashMap<>();
    reportes.put("asignaciones", "asignaciones_listado");
    reportes.put("barrio", "barrios_listado");
    reportes.put("cargo", "cargos_listado");
    reportes.put("clasificacion_residuos", "clasificaciones_listado");
    reportes.put("clientes", "clientes_listado");
    reportes.put("modelo", "modelos_listado");
    reportes.put("pendientes", "pendientes_listado");
    reportes.put("personal", "personal_listado");
    reportes.put("recolecciones", "recolecciones_listado");
    reportes.put("roles", "roles_listado");
    reportes.put("rutas", "rutas_listado");
    reportes.put("servicios", "servicios_listado");
    reportes.put("tipos_residuos", "tipos_residuos_listado");
    reportes.put("usuarios", "usuarios_listado");
    reportes.put("vehiculos", "vehiculos_listado");

    String modulo = request.getParameter("modulo");
    String archivo = modulo == null ? null : reportes.get(modulo);

    if (archivo == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Reporte no encontrado.");
        return;
    }

    if (!Permisos.puedeVer(session.getAttribute("rol"), modulo)) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tiene permiso para este reporte.");
        return;
    }

    ServletContext contexto = request.getServletContext();
    String usuarioImpresion = String.valueOf(session.getAttribute("usuario"));

    try (InputStream jrxml = contexto.getResourceAsStream("/WEB-INF/reportes/" + archivo + ".jrxml");
         InputStream logo = contexto.getResourceAsStream("/WEB-INF/reportes/logo.png");
         Connection con = new Conexion().getConexion()) {

        if (jrxml == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró " + archivo + ".jrxml");
            return;
        }

        JasperReport reporte = JasperCompileManager.compileReport(jrxml);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuarioImpresion", usuarioImpresion);
        parametros.put("USUARIO_IMPRESION", usuarioImpresion);

        String logoPath = contexto.getRealPath("/WEB-INF/reportes/logo.png");
        if (logoPath != null) {
            parametros.put("logoPath", logoPath);
        }
        if (logo != null) {
            parametros.put("LOGO", logo);
        }

        JasperPrint impresion = JasperFillManager.fillReport(reporte, parametros, con);
        byte[] pdf = JasperExportManager.exportReportToPdf(impresion);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + archivo + ".pdf");
        response.setContentLength(pdf.length);

        OutputStream salida = response.getOutputStream();
        salida.write(pdf);
        salida.flush();

    } catch (Exception e) {
        contexto.log("No se pudo generar el reporte " + archivo, e);
        if (!response.isCommitted()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo generar el reporte.");
        }
    }
%>