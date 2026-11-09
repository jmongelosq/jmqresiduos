package com.controller;

import com.config.Conexion;
import com.dao.RecoleccionDAO;
import com.model.ClasificacionResiduo;
import com.model.Recoleccion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@WebServlet(
        name = "RecoleccionController",
        urlPatterns = {"/RecoleccionController"}
)
public class RecoleccionController extends HttpServlet {

    private final RecoleccionDAO dao =
            new RecoleccionDAO();


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action =
                request.getParameter("action");


        if ("reporteGeneral".equals(action)) {

            generarReporteGeneral(
                    request,
                    response
            );

            return;
        }


        if ("reporteIndividual".equals(action)) {

            generarReporteIndividual(
                    request,
                    response
            );

            return;
        }


        response.sendRedirect(
                "recolecciones.jsp"
        );
    }


    // POST

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action =
                request.getParameter("action");


        if (action == null ||
            action.trim().isEmpty()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Acción no especificada"
            );

            return;
        }


        switch (action) {

            case "listar":

                listar(
                        request,
                        response
                );

                break;


            case "buscar":

                buscar(
                        request,
                        response
                );

                break;


            case "listarAsignaciones":

                listarAsignaciones(
                        request,
                        response
                );

                break;


            case "insertar":

                insertar(
                        request,
                        response
                );

                break;


            case "actualizar":

                actualizar(
                        request,
                        response
                );

                break;


            case "desactivar":

                desactivar(
                        request,
                        response
                );

                break;


            case "activar":

                activar(
                        request,
                        response
                );

                break;


            default:

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Acción no válida"
                );

                break;
        }
    }


    // LISTAR

    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        List<Recoleccion> lista =
                dao.listar();


        response.setContentType(
                "text/html;charset=UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        out.println(
                "<table id='tablaRecolecciones' " +
                "class='table table-bordered " +
                "table-hover table-recolecciones'>"
        );


        out.println("<colgroup>");

        out.println("<col class='col-id'>");
        out.println("<col class='col-fecha'>");
        out.println("<col class='col-hora'>");
        out.println("<col class='col-hora'>");
        out.println("<col class='col-ruta'>");
        out.println("<col class='col-vehiculo'>");
        out.println("<col class='col-personal'>");
        out.println("<col class='col-cantidad'>");
        out.println("<col class='col-unidad'>");
        out.println("<col class='col-estado'>");
        out.println("<col class='col-acciones'>");

        out.println("</colgroup>");


        out.println("<thead>");
        out.println("<tr>");

        out.println("<th>ID</th>");
        out.println("<th>Fecha</th>");
        out.println("<th>Hora Inicio</th>");
        out.println("<th>Hora Fin</th>");
        out.println("<th>Ruta</th>");
        out.println("<th>Vehículo</th>");
        out.println("<th>Personal</th>");
        out.println("<th>Cantidad</th>");
        out.println("<th>Unidad</th>");
        out.println("<th>Estado</th>");
        out.println("<th>Acciones</th>");

        out.println("</tr>");
        out.println("</thead>");


        out.println("<tbody>");


        for (Recoleccion r : lista) {

            out.println("<tr>");


            out.println(
                    "<td class='cell-center'>" +
                    r.getId_recoleccion() +
                    "</td>"
            );


            out.println(
                    "<td class='cell-center cell-nowrap'>" +
                    formatearFecha(r.getFecha()) +
                    "</td>"
            );


            out.println(
                    "<td class='cell-center cell-nowrap'>" +
                    formatearHora(r.getHora_inicio()) +
                    "</td>"
            );


            out.println(
                    "<td class='cell-center cell-nowrap'>" +
                    formatearHora(r.getHora_fin()) +
                    "</td>"
            );


            out.println(
                    "<td class='cell-wrap'>" +
                    safe(r.getNombre_ruta()) +
                    "</td>"
            );


            out.println(
                    "<td class='cell-center cell-nowrap'>" +
                    safe(r.getChapa()) +
                    "</td>"
            );


            out.println(
                    "<td class='cell-wrap'>" +
                    safe(r.getNombre_personal()) +
                    "</td>"
            );


            String cantidad =
                    new java.text.DecimalFormat(
                            "0.##",
                            java.text.DecimalFormatSymbols
                                    .getInstance(
                                            java.util.Locale.US
                                    )
                    ).format(
                            r.getCantidad_recolectada()
                    );


            out.println(
                    "<td class='cell-center cell-nowrap'>" +
                    cantidad +
                    "</td>"
            );


            String unidad =
                    r.getUnidad_medida();

            if (unidad == null) {
                unidad = "";
            }


            out.println(
                    "<td class='cell-center cell-nowrap'>" +
                    safe(unidad) +
                    "</td>"
            );


            if (r.isEstado()) {

                out.println(
                        "<td class='cell-center'>" +

                        "<span class='badge " +
                        "badge-success badge-estado'>" +

                        "ACTIVO" +

                        "</span>" +

                        "</td>"
                );

            } else {

                out.println(
                        "<td class='cell-center'>" +

                        "<span class='badge " +
                        "badge-secondary badge-estado'>" +

                        "INACTIVO" +

                        "</span>" +

                        "</td>"
                );
            }


            out.println(
                    "<td class='acciones-recoleccion'>"
            );


            // MODIFICAR

            out.println(
                    "<button type='button' " +
                    "class='btn btn-warning btn-action' " +
                    "onclick='editarRecoleccion(" +
                    r.getId_recoleccion() +
                    ")' " +
                    "title='Modificar'>" +

                    "<i class='fas fa-edit'></i>" +

                    "</button>"
            );


            // REPORTE INDIVIDUAL

            out.println(
                    "<a href='" +
                    "RecoleccionController" +
                    "?action=reporteIndividual" +
                    "&id_recoleccion=" +
                    r.getId_recoleccion() +
                    "' " +
                    "target='_blank' " +
                    "class='btn btn-info btn-action' " +
                    "title='Ver reporte'>" +

                    "<i class='fas fa-eye'></i>" +

                    "</a>"
            );


            // ACTIVAR / DESACTIVAR

            if (r.isEstado()) {

                out.println(
                        "<button type='button' " +
                        "class='btn btn-danger btn-action' " +
                        "onclick='desactivarRecoleccion(" +
                        r.getId_recoleccion() +
                        ")' " +
                        "title='Desactivar'>" +

                        "<i class='fas fa-trash'></i>" +

                        "</button>"
                );

            } else {

                out.println(
                        "<button type='button' " +
                        "class='btn btn-success btn-action' " +
                        "onclick='activarRecoleccion(" +
                        r.getId_recoleccion() +
                        ")' " +
                        "title='Activar'>" +

                        "<i class='fas fa-check'></i>" +

                        "</button>"
                );
            }


            out.println("</td>");
            out.println("</tr");
        }


        out.println("</tbody>");
        out.println("</table>");
    }


    // BUSCAR POR ID

    private void buscar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String parametro =
                request.getParameter(
                        "id_recoleccion"
                );


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        if (parametro == null ||
            parametro.trim().isEmpty()) {

            response.getWriter().print(
                    "{\"success\":false}"
            );

            return;
        }


        int id;


        try {

            id =
                    Integer.parseInt(
                            parametro
                    );

        } catch (NumberFormatException e) {

            response.getWriter().print(
                    "{\"success\":false}"
            );

            return;
        }


        Recoleccion r =
                dao.buscarPorId(id);


        if (r == null) {

            response.getWriter().print(
                    "{\"success\":false}"
            );

            return;
        }


        PrintWriter out =
                response.getWriter();


        out.print("{");

        out.print(
                "\"success\":true,"
        );

        out.print(
                "\"id_recoleccion\":" +
                r.getId_recoleccion() +
                ","
        );

        out.print(
                "\"id_asignacion\":" +
                r.getId_asignacion() +
                ","
        );

        out.print(
                "\"fecha\":\"" +
                safeJS(r.getFecha()) +
                "\","
        );

        out.print(
                "\"hora_inicio\":\"" +
                safeJS(r.getHora_inicio()) +
                "\","
        );

        out.print(
                "\"hora_fin\":\"" +
                safeJS(r.getHora_fin()) +
                "\","
        );

        out.print(
                "\"cantidad_recolectada\":" +
                r.getCantidad_recolectada() +
                ","
        );

        out.print(
                "\"unidad_medida\":\"" +
                safeJS(r.getUnidad_medida()) +
                "\","
        );

        out.print(
                "\"observaciones\":\"" +
                safeJS(r.getObservaciones()) +
                "\""
        );

        out.print("}");
    }


    // LISTAR ASIGNACIONES

    private void listarAsignaciones(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        List<Recoleccion> lista =
                dao.listarAsignaciones();


        response.setContentType(
                "text/html;charset=UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        out.println(
                "<option value='0'>" +
                "Seleccione una asignación" +
                "</option>"
        );


        for (Recoleccion r : lista) {

            String fecha =
                    r.getFecha_asignacion();


            String texto =
                    "Asignación #" +
                    r.getId_asignacion() +
                    " - " +
                    r.getNombre_ruta() +
                    " - " +
                    r.getChapa() +
                    " - " +
                    formatearFecha(fecha);


            out.println(
                    "<option value='" +
                    r.getId_asignacion() +
                    "' data-fecha='" +
                    safe(fecha) +
                    "'>" +
                    safe(texto) +
                    "</option>"
            );
        }
    }


    // INSERTAR

    private void insertar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Recoleccion r =
                    obtenerDatos(request);


            String validacion =
                    validarRecoleccion(r);


            if (validacion != null) {

                responder(
                        response,
                        validacion
                );

                return;
            }


            List<ClasificacionResiduo> clasificaciones =
                    obtenerClasificaciones(request);


            String validacionDetalle =
                    validarClasificaciones(r, clasificaciones);


            if (validacionDetalle != null) {

                responder(
                        response,
                        validacionDetalle
                );

                return;
            }


            // Cabecera y detalle se guardan juntos (transacción en el DAO)
            boolean resultado =
                    dao.insertarConClasificaciones(r, clasificaciones);


            responder(
                    response,
                    resultado
                    ? "ok"
                    : "No se pudo registrar la recolección. No se guardó ningún dato."
            );


        } catch (NumberFormatException e) {

            responder(
                    response,
                    "La cantidad ingresada no es válida."
            );

        } catch (Exception e) {

            e.printStackTrace();

            responder(
                    response,
                    "Ocurrió un error al registrar la recolección."
            );
        }
    }


    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Recoleccion r =
                    obtenerDatos(request);


            String idParametro =
                    request.getParameter(
                            "id_recoleccion"
                    );


            if (idParametro == null ||
                idParametro.trim().isEmpty()) {

                responder(
                        response,
                        "No se recibió el ID de la recolección."
                );

                return;
            }


            r.setId_recoleccion(
                    Integer.parseInt(
                            idParametro
                    )
            );


            String validacion =
                    validarRecoleccion(r);


            if (validacion != null) {

                responder(
                        response,
                        validacion
                );

                return;
            }


            boolean resultado =
                    dao.actualizar(r);


            responder(
                    response,
                    resultado
                    ? "ok"
                    : "No se pudo actualizar la recolección."
            );


        } catch (NumberFormatException e) {

            responder(
                    response,
                    "El ID o la cantidad ingresada no es válida."
            );

        } catch (Exception e) {

            e.printStackTrace();

            responder(
                    response,
                    "Ocurrió un error al actualizar la recolección."
            );
        }
    }


    // DESACTIVAR

    private void desactivar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String parametro =
                    request.getParameter(
                            "id_recoleccion"
                    );


            if (parametro == null ||
                parametro.trim().isEmpty()) {

                responder(
                        response,
                        "No se recibió el ID de la recolección."
                );

                return;
            }


            int id =
                    Integer.parseInt(
                            parametro
                    );


            boolean resultado =
                    dao.desactivar(id);


            responder(
                    response,
                    resultado
                    ? "ok"
                    : "No se pudo desactivar la recolección."
            );


        } catch (Exception e) {

            e.printStackTrace();

            responder(
                    response,
                    "Ocurrió un error al desactivar la recolección."
            );
        }
    }


    // ACTIVAR

    private void activar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String parametro =
                    request.getParameter(
                            "id_recoleccion"
                    );


            if (parametro == null ||
                parametro.trim().isEmpty()) {

                responder(
                        response,
                        "No se recibió el ID de la recolección."
                );

                return;
            }


            int id =
                    Integer.parseInt(
                            parametro
                    );


            boolean resultado =
                    dao.activar(id);


            responder(
                    response,
                    resultado
                    ? "ok"
                    : "No se pudo activar la recolección."
            );


        } catch (Exception e) {

            e.printStackTrace();

            responder(
                    response,
                    "Ocurrió un error al activar la recolección."
            );
        }
    }


    // OBTENER DATOS

    private Recoleccion obtenerDatos(
            HttpServletRequest request) {

        Recoleccion r =
                new Recoleccion();


        String idAsignacion =
                request.getParameter(
                        "id_asignacion"
                );


        if (idAsignacion != null &&
            !idAsignacion.trim().isEmpty()) {

            r.setId_asignacion(
                    Integer.parseInt(
                            idAsignacion
                    )
            );

        } else {

            r.setId_asignacion(0);
        }


        r.setFecha(
                request.getParameter(
                        "fecha"
                )
        );


        r.setHora_inicio(
                request.getParameter(
                        "hora_inicio"
                )
        );


        r.setHora_fin(
                request.getParameter(
                        "hora_fin"
                )
        );


        String cantidad =
                request.getParameter(
                        "cantidad_recolectada"
                );


        if (cantidad != null &&
            !cantidad.trim().isEmpty()) {

            r.setCantidad_recolectada(
                    Double.parseDouble(
                            cantidad
                    )
            );

        } else {

            r.setCantidad_recolectada(0);
        }


        String unidad =
                request.getParameter(
                        "unidad_medida"
                );


        if (unidad == null ||
            unidad.trim().isEmpty()) {

            unidad = "kg";
        }


        r.setUnidad_medida(
                unidad.trim().toLowerCase()
        );


        r.setObservaciones(
                request.getParameter(
                        "observaciones"
                )
        );


        return r;
    }


    // Obtener clasificaciones enviadas desde el formulario

    private List<ClasificacionResiduo> obtenerClasificaciones(
            HttpServletRequest request) {

        List<ClasificacionResiduo> lista =
                new ArrayList<>();

        String[] tipos =
                request.getParameterValues("detalle_tipo");

        String[] cantidades =
                request.getParameterValues("detalle_cantidad");

        String[] observaciones =
                request.getParameterValues("detalle_observaciones");

        if (tipos == null || cantidades == null) {
            return lista;
        }

        for (int i = 0; i < tipos.length; i++) {

            ClasificacionResiduo c =
                    new ClasificacionResiduo();

            c.setId_tipo(
                    Integer.parseInt(tipos[i].trim())
            );

            c.setCantidad_kg(
                    i < cantidades.length
                    ? Double.parseDouble(cantidades[i].trim())
                    : 0
            );

            String obs =
                    observaciones != null && i < observaciones.length
                    ? observaciones[i].trim()
                    : "";

            c.setObservaciones(
                    obs.isEmpty() ? null : obs
            );

            lista.add(c);
        }

        return lista;
    }


    // Validar clasificaciones
    //
    // Mismas reglas que el módulo de Clasificación de Residuos:
    // cantidad mayor a cero, sin tipos repetidos y el total clasificado
    // no puede superar lo recolectado (convertido a kg).

    private String validarClasificaciones(
            Recoleccion r,
            List<ClasificacionResiduo> clasificaciones) {

        if (clasificaciones.isEmpty()) {
            return "Debe agregar al menos una clasificación de residuos.";
        }

        Set<Integer> tipos =
                new HashSet<>();

        double totalKg = 0;

        for (ClasificacionResiduo c : clasificaciones) {

            if (c.getId_tipo() <= 0) {
                return "Hay una clasificación sin tipo de residuo.";
            }

            if (c.getCantidad_kg() <= 0) {
                return "La cantidad de cada clasificación debe ser mayor que cero.";
            }

            if (!tipos.add(c.getId_tipo())) {
                return "No se puede repetir el mismo tipo de residuo en una recolección.";
            }

            totalKg += c.getCantidad_kg();
        }

        double recolectadoKg =
                "ton".equalsIgnoreCase(r.getUnidad_medida())
                ? r.getCantidad_recolectada() * 1000
                : r.getCantidad_recolectada();

        if (totalKg > recolectadoKg + 0.001) {
            return "El total clasificado (" + totalKg + " kg) supera la cantidad recolectada ("
                    + recolectadoKg + " kg).";
        }

        return null;
    }


    // VALIDAR RECOLECCIÓN

    private String validarRecoleccion(
            Recoleccion r) {

        if (r.getId_asignacion() <= 0) {

            return "Debe seleccionar una asignación.";
        }


        if (r.getFecha() == null ||
            r.getFecha().trim().isEmpty()) {

            return "La fecha es obligatoria.";
        }


        if (!r.getFecha().matches(
                "\\d{4}-\\d{2}-\\d{2}"
        )) {

            return "La fecha ingresada no es válida.";
        }


        if (r.getCantidad_recolectada() <= 0) {

            return "La cantidad recolectada debe ser mayor que cero.";
        }


        if (!"kg".equalsIgnoreCase(
                r.getUnidad_medida()
        ) &&
            !"ton".equalsIgnoreCase(
                r.getUnidad_medida()
        )) {

            return "La unidad de medida seleccionada no es válida.";
        }


        String fechaAsignacion =
                dao.obtenerFechaAsignacion(
                        r.getId_asignacion()
                );


        if (fechaAsignacion == null) {

            return "La asignación seleccionada no existe.";
        }


        if (!r.getFecha().equals(
                fechaAsignacion
        )) {

            return
                    "La fecha de la recolección debe coincidir " +
                    "con la fecha de la asignación.";
        }


        if (!dao.asignacionValida(
                r.getId_asignacion()
        )) {

            return
                    "La asignación seleccionada está cancelada " +
                    "o no se encuentra disponible.";
        }


        String inicio =
                normalizarHora(
                        r.getHora_inicio()
                );

        String fin =
                normalizarHora(
                        r.getHora_fin()
                );


        if (inicio != null &&
            fin != null &&
            fin.compareTo(inicio) < 0) {

            return
                    "La hora de finalización no puede ser " +
                    "anterior a la hora de inicio.";
        }


        return null;
    }


    // NORMALIZAR HORA

    private String normalizarHora(
            String hora) {

        if (hora == null ||
            hora.trim().isEmpty()) {

            return null;
        }


        String valor =
                hora.trim();


        if (valor.matches(
                "\\d{2}:\\d{2}"
        )) {

            return valor;
        }


        if (valor.matches(
                "\\d{2}:\\d{2}:\\d{2}"
        )) {

            return valor.substring(
                    0,
                    5
            );
        }


        return valor;
    }


    // RESPONDER

    private void responder(
            HttpServletResponse response,
            String mensaje)
            throws IOException {

        response.setContentType(
                "text/plain;charset=UTF-8"
        );

        response.getWriter().print(
                mensaje
        );
    }


    // REPORTE GENERAL

    private void generarReporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String rutaReporte =
                "/WEB-INF/reportes/" +
                "recolecciones_listado.jrxml";


        try {

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


            JasperReport jasperReport =
                    JasperCompileManager
                            .compileReport(
                                    inputStream
                            );


            Map<String, Object> parametros =
                    new HashMap<>();


            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(
                            request
                    )
            );


            Conexion conexion =
                    new Conexion();


            try (
                    Connection con =
                            conexion.getConexion()
            ) {

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
                        "inline; filename=recolecciones.pdf"
                );


                JasperExportManager
                        .exportReportToPdfStream(
                                jasperPrint,
                                response.getOutputStream()
                        );
            }


        } catch (Exception e) {

            e.printStackTrace();

            mostrarErrorReporte(
                    response,
                    "REPORTE GENERAL DE RECOLECCIONES",
                    e
            );
        }
    }


    // REPORTE INDIVIDUAL

    private void generarReporteIndividual(
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
                        "No se recibió el ID de la recolección."
                );
            }


            int id =
                    Integer.parseInt(
                            idParametro
                    );


            String rutaReporte =
                    "/WEB-INF/reportes/" +
                    "reporte_recolecciones_individual.jrxml";


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


            JasperReport jasperReport =
                    JasperCompileManager
                            .compileReport(
                                    inputStream
                            );


            Map<String, Object> parametros =
                    new HashMap<>();


            parametros.put(
                    "idRecoleccion",
                    id
            );


            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(
                            request
                    )
            );


            Conexion conexion =
                    new Conexion();


            try (
                    Connection con =
                            conexion.getConexion()
            ) {

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
                        "inline; filename=recoleccion_" +
                        id +
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

            mostrarErrorReporte(
                    response,
                    "REPORTE INDIVIDUAL DE RECOLECCION",
                    e
            );
        }
    }


    // ERROR DE REPORTE

    private void mostrarErrorReporte(
            HttpServletResponse response,
            String nombreReporte,
            Exception e)
            throws IOException {

        response.reset();

        response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        );

        response.setContentType(
                "text/plain;charset=UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        out.println(
                "ERROR AL GENERAR " +
                nombreReporte
        );


        out.println();

        out.println(
                "MENSAJE PRINCIPAL:"
        );

        out.println(
                e.getMessage()
        );


        Throwable causa =
                e.getCause();


        int nivel = 1;


        while (causa != null) {

            out.println();

            out.println(
                    "CAUSA " + nivel + ":"
            );

            out.println(
                    causa.getClass().getName()
            );

            out.println(
                    causa.getMessage()
            );


            causa =
                    causa.getCause();

            nivel++;
        }


        out.println();

        out.println(
                "Revisar también la consola " +
                "Output de NetBeans/Tomcat."
        );
    }


    // USUARIO DE SESIÓN

    private String obtenerUsuarioSesion(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);


        if (session == null) {

            return "Sistema";
        }


        String[] nombresAtributos = {

            "usuario",
            "usuarioLogueado",
            "usuarioSesion",
            "username",
            "user",
            "login"

        };


        for (String nombre :
                nombresAtributos) {

            Object valor =
                    session.getAttribute(
                            nombre
                    );


            if (valor == null) {

                continue;
            }


            try {

                if (valor instanceof String) {

                    String texto =
                            (String) valor;


                    if (!texto.trim().isEmpty()) {

                        return texto;
                    }


                } else {

                    try {

                        java.lang.reflect.Method metodo =
                                valor.getClass()
                                        .getMethod(
                                                "getUsuario"
                                        );


                        Object resultado =
                                metodo.invoke(
                                        valor
                                );


                        if (resultado != null) {

                            String texto =
                                    resultado.toString();


                            if (!texto.trim().isEmpty()) {

                                return texto;
                            }
                        }


                    } catch (Exception ignored) {

                        String texto =
                                valor.toString();


                        if (!texto.trim().isEmpty()) {

                            return texto;
                        }
                    }
                }


            } catch (Exception ignored) {

            }
        }


        return "Sistema";
    }


    // FORMATEAR FECHA

    private String formatearFecha(
            String fecha) {

        if (fecha == null ||
            fecha.trim().isEmpty()) {

            return "";
        }


        try {

            if (fecha.length() >= 10) {

                String[] partes =
                        fecha.substring(
                                0,
                                10
                        ).split("-");


                if (partes.length == 3) {

                    return
                            partes[2] +
                            "-" +
                            partes[1] +
                            "-" +
                            partes[0];
                }
            }


        } catch (Exception ignored) {

        }


        return fecha;
    }


    // FORMATEAR HORA

    private String formatearHora(
            String hora) {

        if (hora == null ||
            hora.trim().isEmpty()) {

            return "";
        }


        try {

            if (hora.length() >= 5) {

                return hora.substring(
                        0,
                        5
                );
            }


        } catch (Exception ignored) {

        }


        return hora;
    }


    // SEGURIDAD HTML

    private String safe(
            String texto) {

        if (texto == null) {

            return "";
        }


        return texto

                .replace(
                        "&",
                        "&amp;"
                )

                .replace(
                        "<",
                        "&lt;"
                )

                .replace(
                        ">",
                        "&gt;"
                )

                .replace(
                        "\"",
                        "&quot;"
                )

                .replace(
                        "'",
                        "&#39;"
                );
    }


    // SEGURIDAD JAVASCRIPT

    private String safeJS(
            String texto) {

        if (texto == null) {

            return "";
        }


        return texto

                .replace(
                        "\\",
                        "\\\\"
                )

                .replace(
                        "'",
                        "\\'"
                )

                .replace(
                        "\"",
                        "\\\""
                )

                .replace(
                        "\r",
                        ""
                )

                .replace(
                        "\n",
                        " "
                );
    }

}