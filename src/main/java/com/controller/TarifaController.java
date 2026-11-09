package com.controller;

import com.dao.TarifaDAO;
import com.model.Tarifa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@WebServlet(name = "TarifaController", urlPatterns = {"/TarifaController"})
public class TarifaController extends HttpServlet {

    private TarifaDAO dao;

    @Override
    public void init() {
        dao = new TarifaDAO();
    }

    // GET

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("reporteGeneral".equals(accion)) {
            reporteGeneral(request, response);
            return;
        }

        if ("reporteIndividual".equals(accion)) {
            reporteIndividual(request, response);
            return;
        }

        listar(request, response);
    }

    // POST

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {

            switch (accion) {

                case "listar":
                    listar(request, response);
                    break;

                case "listarOptions":
                    listarOptions(request, response);
                    break;

                case "insertar":
                    insertar(request, response);
                    break;

                case "actualizar":
                    actualizar(request, response);
                    break;

                case "desactivar":
                    desactivar(request, response);
                    break;

                case "activar":
                    activar(request, response);
                    break;

                default:
                    listar(request, response);
                    break;
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(
                    "{\"success\":false,\"mensaje\":\"Error interno del servidor\"}"
            );
        }
    }

    // LISTAR

    private void listar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Tarifa> lista = dao.listar();

        request.setAttribute("tarifas", lista);

        request.getRequestDispatcher("/tarifas.jsp")
                .forward(request, response);
    }

    // LISTAR OPTIONS

    private void listarOptions(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        List<Tarifa> lista = dao.listarActivas();

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        StringBuilder json = new StringBuilder();

        json.append("[");

        for (int i = 0; i < lista.size(); i++) {

            Tarifa t = lista.get(i);

            if (i > 0) {
                json.append(",");
            }

            json.append("{")
                    .append("\"id\":")
                    .append(t.getIdTarifa())
                    .append(",")

                    .append("\"nombre\":\"")
                    .append(safe(t.getNombre()))
                    .append("\",")

                    .append("\"monto\":")
                    .append(
                            t.getMonto() != null
                                    ? t.getMonto().toPlainString()
                                    : "0"
                    )

                    .append("}");
        }

        json.append("]");

        response.getWriter().write(
                json.toString()
        );
    }

    // INSERTAR

    private void insertar(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Tarifa t = construirTarifa(request);

        dao.insertar(t);

        response.sendRedirect(
                request.getContextPath()
                + "/dashboard.jsp?modulo=tarifas"
        );
    }

    // ACTUALIZAR

    private void actualizar(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Tarifa t = construirTarifa(request);

        String id = request.getParameter("idTarifa");

        if (id != null && !id.isBlank()) {

            t.setIdTarifa(
                    Integer.parseInt(id)
            );
        }

        dao.actualizar(t);

        response.sendRedirect(
                request.getContextPath()
                + "/dashboard.jsp?modulo=tarifas"
        );
    }

    // DESACTIVAR

    private void desactivar(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(
                request.getParameter("idTarifa")
        );

        dao.desactivar(id);

        response.sendRedirect(
                request.getContextPath()
                + "/dashboard.jsp?modulo=tarifas"
        );
    }

    // ACTIVAR

    private void activar(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(
                request.getParameter("idTarifa")
        );

        dao.activar(id);

        response.sendRedirect(
                request.getContextPath()
                + "/dashboard.jsp?modulo=tarifas"
        );
    }

    // CONSTRUIR OBJETO

    private Tarifa construirTarifa(HttpServletRequest request) {

        Tarifa t = new Tarifa();

        t.setNombre(
                request.getParameter("nombre")
        );

        t.setDescripcion(
                request.getParameter("descripcion")
        );

        String monto =
                request.getParameter("monto");

        if (monto != null && !monto.isBlank()) {

            t.setMonto(
                    new BigDecimal(monto)
            );

        } else {

            t.setMonto(
                    BigDecimal.ZERO
            );
        }

        t.setPeriodicidad(
                request.getParameter("periodicidad")
        );

        String fechaInicio =
                request.getParameter("fechaInicio");

        if (fechaInicio != null
                && !fechaInicio.isBlank()) {

            t.setFechaInicio(
                    Date.valueOf(fechaInicio)
            );
        }

        String fechaFin =
                request.getParameter("fechaFin");

        if (fechaFin != null
                && !fechaFin.isBlank()) {

            t.setFechaFin(
                    Date.valueOf(fechaFin)
            );

        } else {

            t.setFechaFin(null);
        }

        t.setEstado(true);

        return t;
    }

    // OBTENER USUARIO DE SESIÓN

    private String obtenerUsuarioImpresion(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        if (session != null) {

            Object usuario =
                    session.getAttribute("usuario");

            if (usuario != null) {

                String nombre =
                        usuario.toString().trim();

                if (!nombre.isEmpty()) {
                    return nombre;
                }
            }
        }

        return "Sistema";
    }

    // REPORTE GENERAL

    private void reporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            List<Tarifa> lista =
                    dao.listar();

            InputStream archivo =
                    getServletContext()
                            .getResourceAsStream(
                                    "/WEB-INF/reportes/tarifas_listado.jrxml"
                            );

            if (archivo == null) {

                throw new IOException(
                        "No se encontró tarifas_listado.jrxml"
                );
            }

            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(archivo);

            JRBeanCollectionDataSource fuente =
                    new JRBeanCollectionDataSource(
                            lista
                    );

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "titulo",
                    "REPORTE GENERAL DE TARIFAS"
            );

            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioImpresion(request)
            );

            JasperPrint print =
                    JasperFillManager.fillReport(
                            reporte,
                            parametros,
                            fuente
                    );

            response.setContentType(
                    "application/pdf"
            );

            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=tarifas_listado.pdf"
            );

            OutputStream out =
                    response.getOutputStream();

            JasperExportManager
                    .exportReportToPdfStream(
                            print,
                            out
                    );

            out.flush();

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al generar el reporte general"
            );
        }
    }

    // REPORTE INDIVIDUAL

    private void reporteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String id =
                    request.getParameter("idTarifa");

            if (id == null || id.isBlank()) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "ID de tarifa no válido"
                );

                return;
            }

            int idTarifa =
                    Integer.parseInt(id);

            Tarifa tarifa =
                    dao.buscarPorId(idTarifa);

            if (tarifa == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Tarifa no encontrada"
                );

                return;
            }

            InputStream archivo =
                    getServletContext()
                            .getResourceAsStream(
                                    "/WEB-INF/reportes/tarifa_individual.jrxml"
                            );

            if (archivo == null) {

                throw new IOException(
                        "No se encontró tarifa_individual.jrxml"
                );
            }

            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(archivo);

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "idTarifa",
                    idTarifa
            );

            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioImpresion(request)
            );

            JRBeanCollectionDataSource fuente =
                    new JRBeanCollectionDataSource(
                            List.of(tarifa)
                    );

            JasperPrint print =
                    JasperFillManager.fillReport(
                            reporte,
                            parametros,
                            fuente
                    );

            response.setContentType(
                    "application/pdf"
            );

            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=tarifa_"
                    + idTarifa
                    + ".pdf"
            );

            OutputStream out =
                    response.getOutputStream();

            JasperExportManager
                    .exportReportToPdfStream(
                            print,
                            out
                    );

            out.flush();

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al generar el reporte individual"
            );
        }
    }

    // SEGURIDAD JSON

    private String safe(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", " ");
    }
}