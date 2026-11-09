package com.controller;

import com.config.Conexion;
import com.dao.ModeloDAO;
import com.model.Modelo;

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

@WebServlet("/ModeloController")
public class ModeloController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ModeloDAO dao =
            new ModeloDAO();


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");


        if (action == null ||
                action.trim().isEmpty()) {

            response.sendRedirect(
                    "modelo.jsp"
            );

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

                response.sendRedirect(
                        "modelo.jsp"
                );

                break;
        }
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

        response.setContentType(
                "text/html;charset=UTF-8"
        );


        String action =
                request.getParameter("action");


        if (action == null) {

            response.getWriter()
                    .write("error");

            return;
        }


        switch (action) {

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


            /*
             * Se conserva por compatibilidad.
             * No se muestra botón Activar en el JSP.
             */

            case "activar":

                activar(
                        request,
                        response
                );

                break;


            default:

                response.getWriter()
                        .write("error");

                break;
        }
    }


    // LISTAR

    private void listar(
            HttpServletResponse response)
            throws IOException {

        List<Modelo> lista =
                dao.listar();


        StringBuilder html =
                new StringBuilder();


        for (Modelo modelo : lista) {

            html.append("<tr>");


            // ID

            html.append("<td>")
                    .append(
                            modelo.getId_modelo()
                    )
                    .append("</td>");


            // NOMBRE

            html.append("<td>")
                    .append(
                            safe(
                                    modelo.getNombre()
                            )
                    )
                    .append("</td>");


            // DESCRIPCIÓN

            html.append("<td>")
                    .append(
                            safe(
                                    modelo.getDescripcion()
                            )
                    )
                    .append("</td>");


            // ESTADO

            html.append(
                    "<td class='text-center'>"
            );


            if (modelo.isEstado()) {

                html.append(
                        "<span class='badge badge-success'>"
                        + "ACTIVO"
                        + "</span>"
                );

            } else {

                html.append(
                        "<span class='badge badge-secondary'>"
                        + "INACTIVO"
                        + "</span>"
                );
            }


            html.append("</td>");


            // ACCIONES

            html.append(
                    "<td class='text-center'>"
            );


            // MODIFICAR

            html.append(

                    "<button type='button' "

                    + "class='btn btn-warning btn-sm mr-1' "

                    + "title='Modificar' "

                    + "onclick=\"editarModelo("

                    + modelo.getId_modelo()

                    + ", '"

                    + safeJS(
                            modelo.getNombre()
                    )

                    + "', '"

                    + safeJS(
                            modelo.getDescripcion()
                    )

                    + "', "

                    + modelo.isEstado()

                    + ")\">"

                    + "<i class='fas fa-edit'></i>"

                    + "</button>"
            );


            // VER

            html.append(

                    "<button type='button' "

                    + "class='btn btn-info btn-sm mr-1' "

                    + "title='Ver' "

                    + "onclick=\"reporteIndividual("

                    + modelo.getId_modelo()

                    + ")\">"

                    + "<i class='fas fa-eye'></i>"

                    + "</button>"
            );


            // ELIMINAR

            html.append(

                    "<button type='button' "

                    + "class='btn btn-danger btn-sm' "

                    + "title='Eliminar' "

                    + "onclick=\"eliminarModelo("

                    + modelo.getId_modelo()

                    + ")\">"

                    + "<i class='fas fa-trash'></i>"

                    + "</button>"
            );


            html.append("</td>");

            html.append("</tr>");
        }


        response.getWriter()
                .write(
                        html.toString()
                );
    }


    // LISTAR OPCIONES

    private void listarOptions(
            HttpServletResponse response)
            throws IOException {

        List<Modelo> lista =
                dao.listarActivos();


        StringBuilder html =
                new StringBuilder();


        html.append(
                "<option value=''>"
                + "Seleccione un modelo"
                + "</option>"
        );


        for (Modelo modelo : lista) {

            html.append(

                    "<option value='"

                    + modelo.getId_modelo()

                    + "'>"

                    + safe(
                            modelo.getNombre()
                    )

                    + "</option>"
            );
        }


        response.getWriter()
                .write(
                        html.toString()
                );
    }


    // INSERTAR

    private void insertar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String nombre =
                request.getParameter(
                        "nombre"
                );


        String descripcion =
                request.getParameter(
                        "descripcion"
                );


        if (nombre == null ||
                nombre.trim().isEmpty()) {

            response.getWriter()
                    .write("error");

            return;
        }


        Modelo modelo =
                new Modelo();


        modelo.setNombre(
                nombre.trim()
        );


        modelo.setDescripcion(
                descripcion != null
                        ? descripcion.trim()
                        : ""
        );


        /*
         * Los nuevos modelos se crean activos.
         */

        modelo.setEstado(true);


        boolean resultado =
                dao.insertar(
                        modelo
                );


        response.getWriter()
                .write(
                        resultado
                                ? "success"
                                : "error"
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
                                    "id_modelo"
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


            boolean estado =
                    Boolean.parseBoolean(
                            request.getParameter(
                                    "estado"
                            )
                    );


            if (nombre == null ||
                    nombre.trim().isEmpty()) {

                response.getWriter()
                        .write("error");

                return;
            }


            Modelo modelo =
                    new Modelo();


            modelo.setId_modelo(id);


            modelo.setNombre(
                    nombre.trim()
            );


            modelo.setDescripcion(
                    descripcion != null
                            ? descripcion.trim()
                            : ""
            );


            modelo.setEstado(
                    estado
            );


            boolean resultado =
                    dao.actualizar(
                            modelo
                    );


            response.getWriter()
                    .write(
                            resultado
                                    ? "success"
                                    : "error"
                    );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter()
                    .write("error");
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


            response.getWriter()
                    .write(
                            resultado
                                    ? "success"
                                    : "error"
                    );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter()
                    .write("error");
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
                                    "id"
                            )
                    );


            boolean resultado =
                    dao.activar(id);


            response.getWriter()
                    .write(
                            resultado
                                    ? "success"
                                    : "error"
                    );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter()
                    .write("error");
        }
    }


    // REPORTE GENERAL

    private void generarReporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Connection con = null;


        try {

            String rutaJRXML =
                    getServletContext()
                            .getRealPath(
                                    "/WEB-INF/reportes/modelos_listado.jrxml"
                            );


            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(
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
                    getServletContext()
                            .getRealPath(
                                    "/WEB-INF/reportes/logo.png"
                            )
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
                    "inline; filename=modelos_listado.pdf"
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
                    "No se pudo generar el reporte general de modelos."
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

            int idModelo =
                    Integer.parseInt(
                            request.getParameter(
                                    "idModelo"
                            )
                    );


            String rutaJRXML =
                    getServletContext()
                            .getRealPath(
                                    "/WEB-INF/reportes/modelo_individual.jrxml"
                            );


            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(
                                    rutaJRXML
                            );


            Map<String, Object> parametros =
                    new HashMap<>();


            parametros.put(
                    "idModelo",
                    idModelo
            );


            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuario(request)
            );


            parametros.put(
                    "logoPath",
                    getServletContext()
                            .getRealPath(
                                    "/WEB-INF/reportes/logo.png"
                            )
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
                    "inline; filename=modelo_"
                    + idModelo
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
                    "No se pudo generar el reporte individual del modelo."
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


    // USUARIO DE SESIÓN

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


    // ESCAPAR HTML

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


    // ESCAPAR JAVASCRIPT

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