package com.controller;

import com.config.Conexion;
import com.dao.RutaDAO;
import com.model.Ruta;

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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.sql.Connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/RutaController")
public class RutaController extends HttpServlet {


    private final RutaDAO dao =
            new RutaDAO();


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String accion =
                request.getParameter("accion");


        // REPORTE GENERAL

        if (
                "reporteGeneral"
                .equals(accion)
        ) {

            generarReporteGeneral(
                    request,
                    response
            );

            return;
        }


        // REPORTE INDIVIDUAL

        if (
                "reporteIndividual"
                .equals(accion)
        ) {

            generarReporteIndividual(
                    request,
                    response
            );

            return;
        }


        // BORRAR

        if (
                "Borrar"
                .equals(accion)
        ) {

            borrar(
                    request,
                    response
            );

            return;
        }


        // ACTIVAR

        if (
                "activar"
                .equals(accion)
        ) {

            activar(
                    request,
                    response
            );

            return;
        }


        response.sendRedirect(
                "rutas.jsp"
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


        String accion =
                request.getParameter(
                        "accion"
                );


        // LISTAR

        if (
                "listar"
                .equals(accion)
        ) {

            listar(
                    request,
                    response
            );

            return;
        }


        // LISTAR OPTIONS

        if (
                "listarOptions"
                .equals(accion)
        ) {

            listarOptions(
                    request,
                    response
            );

            return;
        }


        // INSERTAR

        if (
                "insertar"
                .equals(accion)
        ) {

            insertar(
                    request,
                    response
            );

            return;
        }


        // ACTUALIZAR

        if (
                "actualizar"
                .equals(accion)
        ) {

            actualizar(
                    request,
                    response
            );

            return;
        }


        // BORRAR

        if (
                "Borrar"
                .equals(accion)
        ) {

            borrarAjax(
                    request,
                    response
            );

            return;
        }


        // ACTIVAR

        if (
                "activar"
                .equals(accion)
        ) {

            activarAjax(
                    request,
                    response
            );

            return;
        }


        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Acción no válida"
        );

    }


    // LISTAR

    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        List<Ruta> lista =
                dao.listar();


        PrintWriter out =
                response.getWriter();


        out.print("[");


        for (
                int i = 0;
                i < lista.size();
                i++
        ) {


            Ruta r =
                    lista.get(i);


            if (
                    i > 0
            ) {

                out.print(",");

            }


            out.print("{");


            out.print(
                    "\"id_ruta\":"
                    + r.getId_ruta()
                    + ","
            );


            out.print(
                    "\"nombre\":\""
                    + safeJS(
                            r.getNombre()
                    )
                    + "\","
            );


            out.print(
                    "\"descripcion\":\""
                    + safeJS(
                            r.getDescripcion()
                    )
                    + "\","
            );


            out.print(
                    "\"id_barrio\":"
                    + r.getId_barrio()
                    + ","
            );


            out.print(
                    "\"nombre_barrio\":\""
                    + safeJS(
                            r.getNombre_barrio()
                    )
                    + "\","
            );


            out.print(
                    "\"estado\":\""
                    + safeJS(
                            r.getEstado()
                    )
                    + "\""
            );


            out.print("}");

        }


        out.print("]");

    }


    // LISTAR OPTIONS

    private void listarOptions(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        List<Ruta> lista =
                dao.listarActivas();


        PrintWriter out =
                response.getWriter();


        out.print("[");


        for (
                int i = 0;
                i < lista.size();
                i++
        ) {


            Ruta r =
                    lista.get(i);


            if (
                    i > 0
            ) {

                out.print(",");

            }


            out.print("{");


            out.print(
                    "\"id_ruta\":"
                    + r.getId_ruta()
                    + ","
            );


            out.print(
                    "\"nombre\":\""
                    + safeJS(
                            r.getNombre()
                            + " - "
                            + r.getNombre_barrio()
                    )
                    + "\""
            );


            out.print("}");

        }


        out.print("]");

    }


    // INSERTAR

    private void insertar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        Ruta r =
                obtenerDatos(request);


        boolean resultado =
                dao.insertar(r);


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


            Ruta r =
                    obtenerDatos(request);


            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id_ruta"
                            )
                    );


            r.setId_ruta(id);


            boolean resultado =
                    dao.actualizar(r);


            /*
             * El DAO actualiza nombre,
             * descripción e id_barrio.
             *
             * El estado se mantiene mediante
             * la baja lógica, por lo que no
             * modificamos el estado aquí.
             */


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


    // BORRAR AJAX

    private void borrarAjax(
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


    // BORRAR GET

    private void borrar(
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


            response.sendRedirect(
                    "rutas.jsp?mensaje="
                    +
                    (
                        resultado
                        ? "eliminado"
                        : "error"
                    )
            );


        } catch (Exception e) {


            response.sendRedirect(
                    "rutas.jsp?mensaje=error"
            );

        }

    }


    // ACTIVAR AJAX

    private void activarAjax(
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


    // ACTIVAR GET

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


            response.sendRedirect(
                    "rutas.jsp?mensaje="
                    +
                    (
                        resultado
                        ? "activado"
                        : "error"
                    )
            );


        } catch (Exception e) {


            response.sendRedirect(
                    "rutas.jsp?mensaje=error"
            );

        }

    }


    // OBTENER DATOS

    private Ruta obtenerDatos(
            HttpServletRequest request) {


        Ruta r =
                new Ruta();


        r.setNombre(
                request.getParameter(
                        "nombre"
                )
        );


        r.setDescripcion(
                request.getParameter(
                        "descripcion"
                )
        );


        try {


            r.setId_barrio(
                    Integer.parseInt(
                            request.getParameter(
                                    "id_barrio"
                            )
                    )
            );


        } catch (Exception e) {


            r.setId_barrio(0);

        }


        return r;

    }


    // RESPUESTA AJAX

    private void respuesta(
            HttpServletResponse response,
            boolean resultado)
            throws IOException {


        response.setContentType(
                "text/plain;charset=UTF-8"
        );


        response.getWriter().print(
                resultado
                ? "success"
                : "error"
        );

    }


    // REPORTE GENERAL

    private void generarReporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        Connection con =
                null;


        try {


            Conexion conexion =
                    new Conexion();


            con =
                    conexion.getConexion();


            String ruta =
                    "/WEB-INF/reportes/rutas_listado.jrxml";


            InputStream input =
                    getServletContext()
                            .getResourceAsStream(
                                    ruta
                            );


            if (
                    input == null
            ) {

                throw new Exception(
                        "No se encontró el archivo rutas_listado.jrxml"
                );

            }


            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(

                            JasperCompileManager
                                    .compileReport(
                                            input
                                    ),

                            parametrosReporte(
                                    request
                            ),

                            con

                    );


            response.setContentType(
                    "application/pdf"
            );


            JasperExportManager
                    .exportReportToPdfStream(
                            jasperPrint,
                            response.getOutputStream()
                    );


        } catch (Exception e) {


            e.printStackTrace();


            response.setContentType(
                    "text/plain;charset=UTF-8"
            );


            response.getWriter().println(
                    "Error al generar el reporte: "
                    + e.getMessage()
            );


        } finally {


            if (
                    con != null
            ) {

                try {

                    con.close();

                } catch (Exception ignored) {

                }

            }

        }

    }


    // REPORTE INDIVIDUAL

    private void generarReporteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        Connection con =
                null;


        try {


            int idRuta =
                    Integer.parseInt(
                            request.getParameter(
                                    "id"
                            )
                    );


            Conexion conexion =
                    new Conexion();


            con =
                    conexion.getConexion();


            String ruta =
                    "/WEB-INF/reportes/ruta_individual.jrxml";


            InputStream input =
                    getServletContext()
                            .getResourceAsStream(
                                    ruta
                            );


            if (
                    input == null
            ) {

                throw new Exception(
                        "No se encontró el archivo ruta_individual.jrxml"
                );

            }


            Map<String, Object> parametros =
                    parametrosReporte(
                            request
                    );


            parametros.put(
                    "idRuta",
                    idRuta
            );


            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(

                            JasperCompileManager
                                    .compileReport(
                                            input
                                    ),

                            parametros,

                            con

                    );


            response.setContentType(
                    "application/pdf"
            );


            JasperExportManager
                    .exportReportToPdfStream(
                            jasperPrint,
                            response.getOutputStream()
                    );


        } catch (Exception e) {


            e.printStackTrace();


            response.setContentType(
                    "text/plain;charset=UTF-8"
            );


            response.getWriter().println(
                    "Error al generar el reporte: "
                    + e.getMessage()
            );


        } finally {


            if (
                    con != null
            ) {

                try {

                    con.close();

                } catch (Exception ignored) {

                }

            }

        }

    }


    // PARÁMETROS REPORTES

    private Map<String, Object> parametrosReporte(
            HttpServletRequest request) {


        Map<String, Object> parametros =
                new HashMap<>();


        parametros.put(
                "usuarioImpresion",
                obtenerUsuario(request)
        );


        /*
         * Se conserva porque tus JRXML
         * ya están funcionando.
         */

        String logoPath =
                getServletContext()
                        .getRealPath(
                                "/WEB-INF/reportes/logo.png"
                        );


        parametros.put(
                "logoPath",
                logoPath
        );


        return parametros;

    }


    // USUARIO DE SESIÓN

    private String obtenerUsuario(
            HttpServletRequest request) {


        HttpSession session =
                request.getSession(false);


        if (
                session != null
        ) {


            Object usuario =
                    session.getAttribute(
                            "usuario"
                    );


            if (
                    usuario != null
                    &&
                    !usuario
                            .toString()
                            .trim()
                            .isEmpty()
            ) {

                return usuario
                        .toString();

            }

        }


        return "Sistema";

    }


    // SAFE JS

    private String safeJS(
            String texto) {


        if (
                texto == null
        ) {

            return "";

        }


        return texto

                .replace(
                        "\\",
                        "\\\\"
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