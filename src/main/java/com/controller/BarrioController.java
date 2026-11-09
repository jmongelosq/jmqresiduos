package com.controller;

import com.config.Conexion;
import com.dao.BarrioDAO;
import com.model.Barrio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/BarrioController")
public class BarrioController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final BarrioDAO dao = new BarrioDAO();


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {

            response.sendRedirect("dashboard.jsp?modulo=barrio");

            return;
        }


        switch (action) {

            case "reporteGeneral":

                generarReporteGeneral(
                        request,
                        response
                );

                break;


            case "reporteIndividual":

                generarReporteIndividual(
                        request,
                        response
                );

                break;


            default:

                response.sendRedirect("dashboard.jsp?modulo=barrio");

                break;
        }
    }


    // POST

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        String accion =
                request.getParameter("accion");


        /*
         * Compatibilidad con el código anterior
         */

        if (accion == null || accion.trim().isEmpty()) {

            accion =
                    request.getParameter("action");
        }


        if (accion == null || accion.trim().isEmpty()) {

            response.setContentType(
                    "application/json;charset=UTF-8"
            );

            response.getWriter().write(
                    "{\"error\":true}"
            );

            return;
        }


        switch (accion) {

            case "listar":

                listar(response);

                break;


            case "listarOptions":

                listarOptions(response);

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


            case "Borrar":

                eliminar(
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

                response.setContentType(
                        "application/json;charset=UTF-8"
                );

                response.getWriter().write(
                        "{\"error\":true}"
                );

                break;
        }
    }


    // LISTAR

    private void listar(
            HttpServletResponse response)
            throws IOException {

        List<Barrio> lista =
                dao.listar();


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        StringBuilder json =
                new StringBuilder();

        json.append("[");


        for (int i = 0; i < lista.size(); i++) {

            Barrio barrio =
                    lista.get(i);


            if (i > 0) {

                json.append(",");

            }


            json.append("{");

            json.append("\"id_barrio\":")
                    .append(barrio.getId_barrio())
                    .append(",");


            json.append("\"nombre\":\"")
                    .append(safeJSON(
                            barrio.getNombre()
                    ))
                    .append("\",");


            json.append("\"descripcion\":\"")
                    .append(safeJSON(
                            barrio.getDescripcion()
                    ))
                    .append("\",");


            json.append("\"estado\":")
                    .append(barrio.isEstado());


            json.append("}");
        }


        json.append("]");


        response.getWriter().write(
                json.toString()
        );
    }


    // LISTAR OPCIONES

    private void listarOptions(
            HttpServletResponse response)
            throws IOException {

        List<Barrio> lista =
                dao.listarActivos();


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        StringBuilder json =
                new StringBuilder();

        json.append("[");


        for (int i = 0; i < lista.size(); i++) {

            Barrio barrio =
                    lista.get(i);


            if (i > 0) {

                json.append(",");

            }


            json.append("{");

            json.append("\"id_barrio\":")
                    .append(barrio.getId_barrio())
                    .append(",");


            json.append("\"nombre\":\"")
                    .append(safeJSON(
                            barrio.getNombre()
                    ))
                    .append("\"");


            json.append("}");
        }


        json.append("]");


        response.getWriter().write(
                json.toString()
        );
    }


    // INSERTAR

    private void insertar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String nombre =
                request.getParameter("nombre");


        String descripcion =
                request.getParameter("descripcion");


        if (
                nombre == null ||
                nombre.trim().isEmpty()
        ) {

            respuesta(response, false);

            return;
        }


        Barrio barrio =
                new Barrio();


        barrio.setNombre(
                nombre.trim()
        );


        barrio.setDescripcion(
                descripcion != null
                        ? descripcion.trim()
                        : ""
        );


        barrio.setEstado(true);


        boolean resultado =
                dao.insertar(barrio);


        respuesta(
                response,
                resultado
        );
    }


    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id_barrio"
                            )
                    );


            String nombre =
                    request.getParameter(
                            "nombre"
                    );


            String descripcion =
                    request.getParameter(
                            "descripcion"
                    );


            String estadoParametro =
                    request.getParameter(
                            "estado"
                    );


            boolean estado =
                    Boolean.parseBoolean(
                            estadoParametro
                    );


            if (
                    nombre == null ||
                    nombre.trim().isEmpty()
            ) {

                respuesta(response, false);

                return;
            }


            Barrio barrio =
                    new Barrio();


            barrio.setId_barrio(id);


            barrio.setNombre(
                    nombre.trim()
            );


            barrio.setDescripcion(
                    descripcion != null
                            ? descripcion.trim()
                            : ""
            );


            barrio.setEstado(
                    estado
            );


            boolean resultado =
                    dao.actualizar(barrio);


            respuesta(
                    response,
                    resultado
            );

        } catch (Exception e) {

            e.printStackTrace();

            respuesta(
                    response,
                    false
            );
        }
    }


    // BAJA LÓGICA

    private void eliminar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id"
                            )
                    );


            boolean resultado =
                    dao.eliminar(id);


            respuesta(
                    response,
                    resultado
            );

        } catch (Exception e) {

            e.printStackTrace();

            respuesta(
                    response,
                    false
            );
        }
    }


    // ACTIVAR

    /*
     * Se conserva en el Controller porque el DAO ya dispone
     * de esta operación y puede ser necesaria internamente.
     *
     * NO se mostrará como botón en el JSP.
     */

    private void activar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id"
                            )
                    );


            boolean resultado =
                    dao.activar(id);


            respuesta(
                    response,
                    resultado
            );

        } catch (Exception e) {

            e.printStackTrace();

            respuesta(
                    response,
                    false
            );
        }
    }


    // RESPUESTA

    private void respuesta(
            HttpServletResponse response,
            boolean resultado)
            throws IOException {

        response.setContentType(
                "application/json;charset=UTF-8"
        );


        response.getWriter().write(
                resultado
                        ? "{\"success\":true}"
                        : "{\"success\":false}"
        );
    }


    // REPORTE GENERAL

    private void generarReporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Connection con = null;

        try {

            String rutaJRXML =
                    getServletContext().getRealPath(
                            "/WEB-INF/reportes/barrios_listado.jrxml"
                    );


            String logoPath =
                    getServletContext().getRealPath(
                            "/WEB-INF/reportes/logo.png"
                    );


            JasperReport reporte =
                    JasperCompileManager.compileReport(
                            rutaJRXML
                    );


            Map<String, Object> parametros =
                    new HashMap<>();


            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuario(request)
            );


            parametros.put(
                    "logoPath",
                    logoPath
            );


            con =
                    new Conexion()
                            .getConexion();


            JasperPrint print =
                    JasperFillManager.fillReport(
                            reporte,
                            parametros,
                            con
                    );


            response.setContentType(
                    "application/pdf"
            );


            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=barrios_listado.pdf"
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
                    "No se pudo generar el reporte general de barrios."
            );

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }


    // REPORTE INDIVIDUAL

    private void generarReporteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Connection con = null;

        try {

            int idBarrio =
                    Integer.parseInt(
                            request.getParameter(
                                    "idBarrio"
                            )
                    );


            String rutaJRXML =
                    getServletContext().getRealPath(
                            "/WEB-INF/reportes/barrio_individual.jrxml"
                    );


            String logoPath =
                    getServletContext().getRealPath(
                            "/WEB-INF/reportes/logo.png"
                    );


            JasperReport reporte =
                    JasperCompileManager.compileReport(
                            rutaJRXML
                    );


            Map<String, Object> parametros =
                    new HashMap<>();


            parametros.put(
                    "idBarrio",
                    idBarrio
            );


            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuario(request)
            );


            parametros.put(
                    "logoPath",
                    logoPath
            );


            con =
                    new Conexion()
                            .getConexion();


            JasperPrint print =
                    JasperFillManager.fillReport(
                            reporte,
                            parametros,
                            con
                    );


            response.setContentType(
                    "application/pdf"
            );


            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=barrio_" +
                    idBarrio +
                    ".pdf"
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
                    "No se pudo generar el reporte individual del barrio."
            );

        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }


    // OBTENER USUARIO DE SESIÓN

    private String obtenerUsuario(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);


        if (session != null) {

            Object usuario =
                    session.getAttribute(
                            "usuario"
                    );


            if (
                    usuario != null &&
                    !usuario.toString()
                            .trim()
                            .isEmpty()
            ) {

                return usuario.toString();

            }
        }


        return "Sistema";
    }


    // ESCAPAR JSON

    private String safeJSON(
            String texto) {

        if (texto == null) {

            return "";

        }


        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
    }
}