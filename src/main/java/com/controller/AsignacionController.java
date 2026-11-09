package com.controller;

import com.config.Conexion;
import com.dao.AsignacionDAO;
import com.model.Asignacion;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;


@WebServlet(
        name = "AsignacionController",
        urlPatterns = {"/AsignacionController"}
)
public class AsignacionController
        extends HttpServlet {


    private final AsignacionDAO dao =
            new AsignacionDAO();


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter(
                        "action"
                );


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
                "asignaciones.jsp"
        );
    }


    // POST

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding(
                "UTF-8"
        );


        String action =
                request.getParameter(
                        "action"
                );


        if (action == null ||
            action.trim().isEmpty()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Acción no especificada"
            );

            return;
        }


        try {

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


                case "listarRutas":

                    listarRutas(
                            request,
                            response
                    );

                    break;


                case "listarVehiculos":

                    listarVehiculos(
                            request,
                            response
                    );

                    break;


                case "listarPersonal":

                    listarPersonal(
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

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.setContentType(
                    "text/plain;charset=UTF-8"
            );

            response.getWriter().print(
                    "error|Error interno del servidor."
            );
        }
    }


    // LISTAR

    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        List<Asignacion> lista =
                dao.listar();


        response.setContentType(
                "text/html;charset=UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        out.println(
                "<table id='tablaAsignaciones' " +
                "class='table table-bordered " +
                "table-striped table-hover'>"
        );


        out.println("<thead>");
        out.println("<tr>");

        out.println("<th>ID</th>");
        out.println("<th>Fecha</th>");
        out.println("<th>Hora salida</th>");
        out.println("<th>Hora regreso</th>");
        out.println("<th>Ruta</th>");
        out.println("<th>Vehículo</th>");
        out.println("<th>Responsable</th>");
        out.println("<th>Estado</th>");
        out.println("<th>Acciones</th>");

        out.println("</tr>");
        out.println("</thead>");


        out.println("<tbody>");


        for (Asignacion a : lista) {

            out.println("<tr>");


            // ID

            out.println(
                    "<td>" +
                    a.getId_asignacion() +
                    "</td>"
            );


            // FECHA

            out.println(
                    "<td>" +
                    formatearFecha(
                            a.getFecha()
                    ) +
                    "</td>"
            );


            // HORA SALIDA

            out.println(
                    "<td>" +
                    formatearHora(
                            a.getHora_salida()
                    ) +
                    "</td>"
            );


            // HORA REGRESO

            out.println(
                    "<td>" +
                    formatearHora(
                            a.getHora_regreso()
                    ) +
                    "</td>"
            );


            // RUTA

            out.println(
                    "<td>" +
                    safe(
                            a.getNombre_ruta()
                    ) +
                    "</td>"
            );


            // VEHÍCULO

            out.println(
                    "<td>" +
                    safe(
                            a.getChapa()
                    ) +
                    "</td>"
            );


            // RESPONSABLE

            out.println(
                    "<td>" +
                    safe(
                            a.getNombre_responsable()
                    ) +
                    "</td>"
            );


            // ESTADO

            String estado =
                    a.getEstado();


            if (estado == null ||
                estado.trim().isEmpty()) {

                estado = "PROGRAMADA";
            }


            String claseEstado =
                    "badge badge-secondary";


            if ("PROGRAMADA".equals(estado)) {

                claseEstado =
                        "badge badge-info";

            } else if ("EN_CURSO".equals(estado)) {

                claseEstado =
                        "badge badge-warning";

            } else if ("FINALIZADA".equals(estado)) {

                claseEstado =
                        "badge badge-success";

            } else if ("CANCELADA".equals(estado)) {

                claseEstado =
                        "badge badge-danger";
            }


            out.println(
                    "<td class='text-center'>" +
                    "<span class='" +
                    claseEstado +
                    "'>" +
                    safe(estado) +
                    "</span>" +
                    "</td>"
            );


            // ACCIONES

            out.println(
                    "<td class='text-center text-nowrap'>"
            );


            // MODIFICAR

            out.println(
                    "<button type='button' " +
                    "class='btn btn-sm btn-warning mr-1' " +
                    "onclick='editarAsignacion(" +
                    a.getId_asignacion() +
                    ")' " +
                    "title='Modificar'>" +
                    "<i class='fas fa-edit'></i>" +
                    "</button>"
            );


            // REPORTE INDIVIDUAL

            out.println(
                    "<a href='AsignacionController" +
                    "?action=reporteIndividual" +
                    "&id_asignacion=" +
                    a.getId_asignacion() +
                    "' " +
                    "target='_blank' " +
                    "class='btn btn-sm btn-info mr-1' " +
                    "title='Reporte Individual'>" +
                    "<i class='fas fa-eye'></i>" +
                    "</a>"
            );


            // CANCELAR / ACTIVAR

            if (!"CANCELADA".equals(estado)) {

                out.println(
                        "<button type='button' " +
                        "class='btn btn-sm btn-danger' " +
                        "onclick='desactivarAsignacion(" +
                        a.getId_asignacion() +
                        ")' " +
                        "title='Cancelar'>" +
                        "<i class='fas fa-ban'></i>" +
                        "</button>"
                );

            } else {

                out.println(
                        "<button type='button' " +
                        "class='btn btn-sm btn-success' " +
                        "onclick='activarAsignacion(" +
                        a.getId_asignacion() +
                        ")' " +
                        "title='Activar'>" +
                        "<i class='fas fa-check'></i>" +
                        "</button>"
                );
            }


            out.println("</td>");
            out.println("</tr>");
        }


        out.println("</tbody>");
        out.println("</table>");
    }


    // BUSCAR

    private void buscar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id =
                Integer.parseInt(
                        request.getParameter(
                                "id_asignacion"
                        )
                );


        Asignacion a =
                dao.buscarPorId(id);


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        if (a == null) {

            out.print(
                    "{\"success\":false}"
            );

            return;
        }


        out.print("{");


        out.print(
                "\"success\":true,"
        );


        out.print(
                "\"id_asignacion\":" +
                a.getId_asignacion() +
                ","
        );


        out.print(
                "\"id_ruta\":" +
                a.getId_ruta() +
                ","
        );


        out.print(
                "\"id_vehiculo\":" +
                a.getId_vehiculo() +
                ","
        );


        out.print(
                "\"id_personal\":" +
                a.getId_personal_responsable() +
                ","
        );


        out.print(
                "\"fecha\":\"" +
                safeJS(
                        a.getFecha()
                ) +
                "\","
        );


        out.print(
                "\"hora_salida\":\"" +
                safeJS(
                        a.getHora_salida()
                ) +
                "\","
        );


        out.print(
                "\"hora_regreso\":\"" +
                safeJS(
                        a.getHora_regreso()
                ) +
                "\","
        );


        out.print(
                "\"estado\":\"" +
                safeJS(
                        a.getEstado()
                ) +
                "\","
        );


        out.print(
                "\"observaciones\":\"" +
                safeJS(
                        a.getObservaciones()
                ) +
                "\""
        );


        out.print("}");
    }


    // LISTAR RUTAS

    private void listarRutas(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int idActual =
                obtenerIdActual(
                        request
                );


        List<Asignacion> lista =
                dao.listarRutas(
                        idActual
                );


        response.setContentType(
                "text/html;charset=UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        out.println(
                "<option value=''>" +
                "Seleccione una ruta" +
                "</option>"
        );


        for (Asignacion a : lista) {

            out.println(
                    "<option value='" +
                    a.getId_ruta() +
                    "'>" +
                    safe(
                            a.getNombre_ruta()
                    ) +
                    "</option>"
            );
        }
    }


    // LISTAR VEHÍCULOS

    private void listarVehiculos(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int idActual =
                obtenerIdActual(
                        request
                );


        List<Asignacion> lista =
                dao.listarVehiculos(
                        idActual
                );


        response.setContentType(
                "text/html;charset=UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        out.println(
                "<option value=''>" +
                "Seleccione un vehículo" +
                "</option>"
        );


        for (Asignacion a : lista) {

            out.println(
                    "<option value='" +
                    a.getId_vehiculo() +
                    "'>" +
                    safe(
                            a.getChapa()
                    ) +
                    "</option>"
            );
        }
    }


    // LISTAR PERSONAL

    private void listarPersonal(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int idActual =
                obtenerIdActual(
                        request
                );


        List<Asignacion> lista =
                dao.listarPersonal(
                        idActual
                );


        response.setContentType(
                "text/html;charset=UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        out.println(
                "<option value=''>" +
                "Seleccione el responsable" +
                "</option>"
        );


        for (Asignacion a : lista) {

            out.println(
                    "<option value='" +
                    a.getId_personal_responsable() +
                    "'>" +
                    safe(
                            a.getNombre_responsable()
                    ) +
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

            Asignacion a =
                    obtenerDatos(request);


            String validacion =
                    dao.validarConflictos(
                            a,
                            0
                    );


            if (validacion != null) {

                responderError(
                        response,
                        validacion
                );

                return;
            }


            boolean resultado =
                    dao.insertar(a);


            if (resultado) {

                responderOK(
                        response
                );

            } else {

                responderError(
                        response,
                        "No se pudo registrar la asignación."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            responderError(
                    response,
                    "Ocurrió un error al registrar la asignación."
            );
        }
    }


    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Asignacion a =
                    obtenerDatos(request);


            String idTexto =
                    request.getParameter(
                            "id_asignacion"
                    );


            if (idTexto == null ||
                idTexto.trim().isEmpty()) {

                responderError(
                        response,
                        "No se indicó la asignación a modificar."
                );

                return;
            }


            int id =
                    Integer.parseInt(
                            idTexto
                    );


            a.setId_asignacion(
                    id
            );


            String validacion =
                    dao.validarConflictos(
                            a,
                            id
                    );


            if (validacion != null) {

                responderError(
                        response,
                        validacion
                );

                return;
            }


            boolean resultado =
                    dao.actualizar(a);


            if (resultado) {

                responderOK(
                        response
                );

            } else {

                responderError(
                        response,
                        "No se pudo actualizar la asignación."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            responderError(
                    response,
                    "Ocurrió un error al actualizar la asignación."
            );
        }
    }


    // CANCELAR

    private void desactivar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id_asignacion"
                            )
                    );


            boolean resultado =
                    dao.cancelar(id);


            if (resultado) {

                responderOK(
                        response
                );

            } else {

                responderError(
                        response,
                        "No se pudo cancelar la asignación."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            responderError(
                    response,
                    "Ocurrió un error al cancelar la asignación."
            );
        }
    }


    // ACTIVAR

    private void activar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id_asignacion"
                            )
                    );


            Asignacion a =
                    dao.buscarPorId(id);


            if (a == null) {

                responderError(
                        response,
                        "No se encontró la asignación."
                );

                return;
            }


            a.setEstado(
                    "PROGRAMADA"
            );


            String validacion =
                    dao.validarConflictos(
                            a,
                            id
                    );


            if (validacion != null) {

                responderError(
                        response,
                        validacion
                );

                return;
            }


            boolean resultado =
                    dao.activar(id);


            if (resultado) {

                responderOK(
                        response
                );

            } else {

                responderError(
                        response,
                        "No se pudo activar la asignación."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            responderError(
                    response,
                    "Ocurrió un error al activar la asignación."
            );
        }
    }


    // OBTENER DATOS

    private Asignacion obtenerDatos(
            HttpServletRequest request) {

        Asignacion a =
                new Asignacion();


        // RUTA

        String idRuta =
                request.getParameter(
                        "id_ruta"
                );


        if (idRuta != null &&
            !idRuta.trim().isEmpty()) {

            a.setId_ruta(
                    Integer.parseInt(
                            idRuta
                    )
            );

        } else {

            a.setId_ruta(0);
        }


        // VEHÍCULO

        String idVehiculo =
                request.getParameter(
                        "id_vehiculo"
                );


        if (idVehiculo != null &&
            !idVehiculo.trim().isEmpty()) {

            a.setId_vehiculo(
                    Integer.parseInt(
                            idVehiculo
                    )
            );

        } else {

            a.setId_vehiculo(0);
        }


        // PERSONAL

        String idPersonal =
                request.getParameter(
                        "id_personal"
                );


        if (idPersonal != null &&
            !idPersonal.trim().isEmpty()) {

            a.setId_personal_responsable(
                    Integer.parseInt(
                            idPersonal
                    )
            );

        } else {

            a.setId_personal_responsable(0);
        }


        // FECHA

        a.setFecha(
                request.getParameter(
                        "fecha"
                )
        );


        // HORA SALIDA

        a.setHora_salida(
                request.getParameter(
                        "hora_salida"
                )
        );


        // HORA REGRESO

        a.setHora_regreso(
                request.getParameter(
                        "hora_regreso"
                )
        );


        // ESTADO

        String estado =
                request.getParameter(
                        "estado"
                );


        if (estado == null ||
            estado.trim().isEmpty()) {

            estado = "PROGRAMADA";
        }


        a.setEstado(
                estado
        );


        // OBSERVACIONES

        a.setObservaciones(
                request.getParameter(
                        "observaciones"
                )
        );


        return a;
    }


    // REPORTE GENERAL

    private void generarReporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String rutaReporte =
                    "/WEB-INF/reportes/asignaciones_listado.jrxml";


            InputStream inputStream =
                    getServletContext()
                            .getResourceAsStream(
                                    rutaReporte
                            );


            if (inputStream == null) {

                throw new Exception(
                        "No se encontró el archivo JRXML: "
                        + rutaReporte
                );
            }


            JasperReport jasperReport =
                    JasperCompileManager
                            .compileReport(
                                    inputStream
                            );


            Map<String, Object> parametros =
                    new HashMap<>();


            HttpSession session =
                    request.getSession(false);


            String usuarioImpresion =
                    "Sistema";


            if (session != null &&
                session.getAttribute(
                        "usuario"
                ) != null) {

                usuarioImpresion =
                        session.getAttribute(
                                "usuario"
                        ).toString();
            }


            parametros.put(
                    "usuarioImpresion",
                    usuarioImpresion
            );


            Conexion conexion =
                    new Conexion();


            try (Connection con =
                         conexion.getConexion()) {

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );


                response.setContentType(
                        "application/pdf"
                );


                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=asignaciones.pdf"
                );


                JasperExportManager
                        .exportReportToPdfStream(
                                jasperPrint,
                                response.getOutputStream()
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();


            response.setContentType(
                    "text/plain;charset=UTF-8"
            );


            response.getWriter().println(
                    "Error al generar el reporte de asignaciones."
            );


            response.getWriter().println(
                    e.getMessage()
            );
        }
    }


    // REPORTE INDIVIDUAL

    private void generarReporteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id_asignacion"
                            )
                    );


            String rutaReporte =
                    "/WEB-INF/reportes/asignacion_individual.jrxml";


            InputStream inputStream =
                    getServletContext()
                            .getResourceAsStream(
                                    rutaReporte
                            );


            if (inputStream == null) {

                throw new Exception(
                        "No se encontró el archivo JRXML: "
                        + rutaReporte
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
                    "idAsignacion",
                    id
            );


            HttpSession session =
                    request.getSession(false);


            String usuarioImpresion =
                    "Sistema";


            if (session != null &&
                session.getAttribute(
                        "usuario"
                ) != null) {

                usuarioImpresion =
                        session.getAttribute(
                                "usuario"
                        ).toString();
            }


            parametros.put(
                    "usuarioImpresion",
                    usuarioImpresion
            );


            Conexion conexion =
                    new Conexion();


            try (Connection con =
                         conexion.getConexion()) {

                JasperPrint jasperPrint =
                        JasperFillManager.fillReport(
                                jasperReport,
                                parametros,
                                con
                        );


                response.setContentType(
                        "application/pdf"
                );


                response.setHeader(
                        "Content-Disposition",
                        "inline; filename=asignacion_"
                        + id
                        + ".pdf"
                );


                JasperExportManager
                        .exportReportToPdfStream(
                                jasperPrint,
                                response.getOutputStream()
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();


            response.setContentType(
                    "text/plain;charset=UTF-8"
            );


            response.getWriter().println(
                    "Error al generar el reporte individual."
            );


            response.getWriter().println(
                    e.getMessage()
            );
        }
    }


    // RESPUESTA OK

    private void responderOK(
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "text/plain;charset=UTF-8"
        );

        response.getWriter().print(
                "ok"
        );
    }


    // RESPUESTA ERROR

    private void responderError(
            HttpServletResponse response,
            String mensaje)
            throws IOException {

        response.setContentType(
                "text/plain;charset=UTF-8"
        );

        response.getWriter().print(
                "error|"
                + mensaje
        );
    }


    // OBTENER ID ACTUAL

    private int obtenerIdActual(
            HttpServletRequest request) {

        try {

            String valor =
                    request.getParameter(
                            "id_actual"
                    );


            if (valor != null &&
                !valor.trim().isEmpty()) {

                return Integer.parseInt(
                        valor
                );
            }

        } catch (Exception e) {

            // Mantener 0.
        }


        return 0;
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

                    return partes[2]
                            + "/"
                            + partes[1]
                            + "/"
                            + partes[0];
                }
            }

        } catch (Exception e) {

            // Mantener valor original.
        }


        return safe(fecha);
    }


    // FORMATEAR HORA

    private String formatearHora(
            String hora) {

        if (hora == null ||
            hora.trim().isEmpty()) {

            return "";
        }


        if (hora.length() >= 5) {

            return safe(
                    hora.substring(
                            0,
                            5
                    )
            );
        }


        return safe(hora);
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


    // SEGURIDAD JAVASCRIPT / JSON

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