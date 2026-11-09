package com.controller;

import com.dao.ClasificacionResiduoDAO;
import com.model.ClasificacionResiduo;
import com.config.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ClasificacionResiduoController")
public class ClasificacionResiduoController
        extends HttpServlet {


    private final ClasificacionResiduoDAO dao =
            new ClasificacionResiduoDAO();


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


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


        request.getRequestDispatcher(
                "/WEB-INF/vistas/clasificacion_residuos.jsp"
        ).forward(
                request,
                response
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


        response.setCharacterEncoding(
                "UTF-8"
        );


        String action =
                request.getParameter("action");


        if (action == null) {

            response.getWriter().print(
                    "error"
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


            case "listarRecolecciones":

                listarRecolecciones(
                        response
                );

                break;


            case "listarTipos":

                listarTipos(
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


            case "Borrar":
            case "eliminar":

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

                response.getWriter().print(
                        "error"
                );

                break;
        }
    }


    // LISTAR

    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        List<ClasificacionResiduo> lista =
                dao.listar();


        PrintWriter out =
                response.getWriter();


        out.print(

                "<table id='tablaClasificaciones' " +
                "class='table table-bordered table-hover table-clasificaciones'>" +

                "<thead>" +

                "<tr>" +

                "<th>ID</th>" +

                "<th>Recolección</th>" +

                "<th>Fecha</th>" +

                "<th>Tipo de Residuo</th>" +

                "<th>Cantidad</th>" +

                "<th>Observaciones</th>" +

                "<th>Estado</th>" +

                "<th>Acciones</th>" +

                "</tr>" +

                "</thead>" +

                "<tbody>"

        );


        for (ClasificacionResiduo c : lista) {


            String estado;


            if (c.isEstado()) {

                estado =
                        "<span class='badge badge-success'>" +
                        "ACTIVO" +
                        "</span>";

            } else {

                estado =
                        "<span class='badge badge-danger'>" +
                        "INACTIVO" +
                        "</span>";
            }


            String observaciones =
                    c.getObservaciones() == null
                    ? ""
                    : escapeHtml(
                            c.getObservaciones()
                    );


            String cantidad =
                    formatearCantidad(
                            c.getCantidad_kg()
                    );


            out.print(

                    "<tr>" +

                    "<td>" +
                    c.getId_clasificacion() +
                    "</td>" +

                    "<td>" +
                    c.getId_recoleccion() +
                    "</td>" +

                    "<td>" +
                    formatearFecha(
                            c.getFecha_recoleccion()
                    ) +
                    "</td>" +

                    "<td>" +
                    escapeHtml(
                            c.getNombre_tipo()
                    ) +
                    "</td>" +

                    "<td>" +
                    cantidad +
                    " kg" +
                    "</td>" +

                    "<td>" +
                    observaciones +
                    "</td>" +

                    "<td>" +
                    estado +
                    "</td>" +

                    "<td class='text-center acciones-clasificacion'>"

            );


            // MODIFICAR

            out.print(

                    "<button " +

                    "class='btn btn-warning btn-sm mr-1 btn-action' " +

                    "title='Modificar' " +

                    "onclick=\"editarClasificacion(" +

                    c.getId_clasificacion() + "," +

                    c.getId_recoleccion() + "," +

                    c.getId_tipo() + "," +

                    c.getCantidad_kg() + "," +

                    "'" +

                    escapeJs(
                            c.getObservaciones()
                    ) +

                    "'" +

                    ")\">" +

                    "<i class='fas fa-edit'></i>" +

                    "</button>"

            );


            // VER / REPORTE INDIVIDUAL

            out.print(

                    "<a " +

                    "href='" +
                    request.getContextPath() +
                    "/ClasificacionResiduoController?action=reporteIndividual&id_clasificacion=" +
                    c.getId_clasificacion() +
                    "' " +

                    "target='_blank' " +

                    "class='btn btn-info btn-sm mr-1 btn-action' " +

                    "title='Ver reporte individual'>" +

                    "<i class='fas fa-eye'></i>" +

                    "</a>"

            );


            // ACTIVAR / DESACTIVAR

            if (c.isEstado()) {


                out.print(

                        "<button " +

                        "class='btn btn-danger btn-sm btn-action' " +

                        "title='Desactivar' " +

                        "onclick=\"eliminarClasificacion(" +

                        c.getId_clasificacion() +

                        ")\">" +

                        "<i class='fas fa-ban'></i>" +

                        "</button>"

                );


            } else {


                out.print(

                        "<button " +

                        "class='btn btn-success btn-sm btn-action' " +

                        "title='Activar' " +

                        "onclick=\"activarClasificacion(" +

                        c.getId_clasificacion() +

                        ")\">" +

                        "<i class='fas fa-check'></i>" +

                        "</button>"

                );
            }


            out.print(

                    "</td>" +

                    "</tr>"

            );
        }


        out.print(

                "</tbody>" +

                "</table>"

        );
    }


    // BUSCAR

    private void buscar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        int id =
                parseInt(
                        request.getParameter(
                                "id_clasificacion"
                        )
                );


        ClasificacionResiduo c =
                dao.buscarPorId(id);


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        if (c == null) {

            out.print(
                    "{\"error\":true}"
            );

            return;
        }


        out.print(

                "{" +

                "\"id_clasificacion\":" +
                c.getId_clasificacion() +

                "," +

                "\"id_recoleccion\":" +
                c.getId_recoleccion() +

                "," +

                "\"id_tipo\":" +
                c.getId_tipo() +

                "," +

                "\"cantidad_kg\":" +
                c.getCantidad_kg() +

                "," +

                "\"observaciones\":\"" +

                escapeJson(
                        c.getObservaciones()
                ) +

                "\"" +

                "}"

        );
    }


    // LISTAR RECOLECCIONES

    private void listarRecolecciones(
            HttpServletResponse response)
            throws IOException {


        List<ClasificacionResiduo> lista =
                dao.listarRecolecciones();


        PrintWriter out =
                response.getWriter();


        out.print(

                "<option value='0'>" +

                "Seleccione una recolección" +

                "</option>"

        );


        for (ClasificacionResiduo c : lista) {


            out.print(

                    "<option value='" +

                    c.getId_recoleccion() +

                    "'>" +

                    "Recolección #" +

                    c.getId_recoleccion() +

                    " - " +

                    formatearFecha(
                            c.getFecha_recoleccion()
                    ) +

                    "</option>"

            );
        }
    }


    // LISTAR TIPOS

    private void listarTipos(
            HttpServletResponse response)
            throws IOException {


        List<ClasificacionResiduo> lista =
                dao.listarTipos();


        PrintWriter out =
                response.getWriter();


        out.print(

                "<option value='0'>" +

                "Seleccione un tipo de residuo" +

                "</option>"

        );


        for (ClasificacionResiduo c : lista) {


            out.print(

                    "<option value='" +

                    c.getId_tipo() +

                    "'>" +

                    escapeHtml(
                            c.getNombre_tipo()
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


        ClasificacionResiduo c =
                construirModelo(request);


        boolean resultado =
                dao.insertar(c);


        response.getWriter().print(

                resultado
                ? "ok"
                : "error"

        );
    }


    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        ClasificacionResiduo c =
                construirModelo(request);


        c.setId_clasificacion(

                parseInt(

                        request.getParameter(
                                "id_clasificacion"
                        )

                )

        );


        boolean resultado =
                dao.actualizar(c);


        response.getWriter().print(

                resultado
                ? "ok"
                : "error"

        );
    }


    // ELIMINAR

    private void eliminar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        int id =
                parseInt(

                        request.getParameter(
                                "id_clasificacion"
                        )

                );


        boolean resultado =
                dao.eliminar(id);


        response.getWriter().print(

                resultado
                ? "ok"
                : "error"

        );
    }


    // ACTIVAR

    private void activar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        int id =
                parseInt(

                        request.getParameter(
                                "id_clasificacion"
                        )

                );


        boolean resultado =
                dao.activar(id);


        response.getWriter().print(

                resultado
                ? "ok"
                : "error"

        );
    }


    // REPORTE GENERAL

    private void generarReporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {


        String rutaReporte =
                "/WEB-INF/reportes/" +
                "clasificaciones_listado.jrxml";


        try {


            InputStream inputStream =
                    getServletContext()
                            .getResourceAsStream(
                                    rutaReporte
                            );


            if (inputStream == null) {

                throw new Exception(

                        "No se encontró el archivo JRXML: " +
                        rutaReporte

                );
            }


            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(
                                    inputStream
                            );


            Map<String, Object> parametros =
                    new HashMap<>();


            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(request)
            );


            Conexion conexion =
                    new Conexion();


            try (
                    Connection con =
                            conexion.getConexion()
            ) {


                JasperPrint print =
                        JasperFillManager.fillReport(

                                reporte,

                                parametros,

                                con

                        );


                response.reset();


                response.setContentType(
                        "application/pdf"
                );


                response.setHeader(

                        "Content-Disposition",

                        "inline; filename=clasificaciones.pdf"

                );


                JasperExportManager
                        .exportReportToPdfStream(

                                print,

                                response.getOutputStream()

                        );
            }


        } catch (Exception e) {


            e.printStackTrace();


            mostrarErrorReporte(

                    response,

                    "REPORTE GENERAL DE CLASIFICACIONES",

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
                        "id_clasificacion"
                );


        try {


            if (
                    idParametro == null ||
                    idParametro.trim().isEmpty()
            ) {

                throw new Exception(
                        "No se recibió el ID de la clasificación."
                );
            }


            int id =
                    Integer.parseInt(
                            idParametro
                    );


            String rutaReporte =
                    "/WEB-INF/reportes/" +
                    "clasificacion_individual.jrxml";


            InputStream inputStream =
                    getServletContext()
                            .getResourceAsStream(
                                    rutaReporte
                            );


            if (inputStream == null) {

                throw new Exception(

                        "No se encontró el archivo JRXML: " +
                        rutaReporte

                );
            }


            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(
                                    inputStream
                            );


            Map<String, Object> parametros =
                    new HashMap<>();


            parametros.put(
                    "idClasificacion",
                    id
            );


            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioSesion(request)
            );


            Conexion conexion =
                    new Conexion();


            try (
                    Connection con =
                            conexion.getConexion()
            ) {


                JasperPrint print =
                        JasperFillManager.fillReport(

                                reporte,

                                parametros,

                                con

                        );


                response.reset();


                response.setContentType(
                        "application/pdf"
                );


                response.setHeader(

                        "Content-Disposition",

                        "inline; filename=clasificacion_" +
                        id +
                        ".pdf"

                );


                JasperExportManager
                        .exportReportToPdfStream(

                                print,

                                response.getOutputStream()

                        );
            }


        } catch (Exception e) {


            e.printStackTrace();


            mostrarErrorReporte(

                    response,

                    "REPORTE INDIVIDUAL DE CLASIFICACIÓN",

                    e

            );
        }
    }


    // USUARIO DE SESIÓN

    private String obtenerUsuarioSesion(
            HttpServletRequest request) {


        Object usuario =
                request.getSession()
                        .getAttribute(
                                "usuario"
                        );


        if (usuario == null) {

            return "Usuario del sistema";
        }


        String nombre =
                usuario.toString();


        if (
                nombre.trim().isEmpty()
        ) {

            return "Usuario del sistema";
        }


        return nombre;
    }


    // ERROR DE REPORTE

    private void mostrarErrorReporte(
            HttpServletResponse response,
            String titulo,
            Exception e)
            throws IOException {


        response.reset();


        response.setContentType(
                "text/plain;charset=UTF-8"
        );


        response.getWriter().println(
                "Error al generar " +
                titulo
        );


        response.getWriter().println();


        response.getWriter().println(
                e.getMessage()
        );
    }


    // CONSTRUIR MODELO

    private ClasificacionResiduo construirModelo(
            HttpServletRequest request) {


        ClasificacionResiduo c =
                new ClasificacionResiduo();


        c.setId_recoleccion(

                parseInt(

                        request.getParameter(
                                "id_recoleccion"
                        )

                )

        );


        c.setId_tipo(

                parseInt(

                        request.getParameter(
                                "id_tipo"
                        )

                )

        );


        c.setCantidad_kg(

                parseDouble(

                        request.getParameter(
                                "cantidad_kg"
                        )

                )

        );


        c.setObservaciones(

                request.getParameter(
                        "observaciones"
                )

        );


        return c;
    }


    // PARSE INT

    private int parseInt(
            String valor) {


        try {

            return Integer.parseInt(
                    valor
            );

        } catch (Exception e) {

            return 0;
        }
    }


    // PARSE DOUBLE

    private double parseDouble(
            String valor) {


        try {


            if (valor == null) {

                return 0;
            }


            valor =
                    valor.replace(
                            ",",
                            "."
                    );


            return Double.parseDouble(
                    valor
            );


        } catch (Exception e) {

            return 0;
        }
    }


    // FORMATEAR FECHA

    private String formatearFecha(
            String fecha) {


        if (
                fecha == null ||
                fecha.trim().isEmpty()
        ) {

            return "-";
        }


        try {


            String[] partes =
                    fecha.split("-");


            if (partes.length == 3) {


                return partes[2] +
                       "-" +
                       partes[1] +
                       "-" +
                       partes[0];

            }


        } catch (Exception e) {

            // continuar
        }


        return fecha;
    }


    // FORMATEAR CANTIDAD

    private String formatearCantidad(
            double cantidad) {


        if (
                cantidad ==
                Math.floor(cantidad)
        ) {


            return String.valueOf(

                    (long) cantidad

            );

        }


        return String.valueOf(
                cantidad
        );
    }


    // ESCAPE HTML

    private String escapeHtml(
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


    // ESCAPE JAVASCRIPT

    private String escapeJs(
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
                        "\\n"
                );
    }


    // ESCAPE JSON

    private String escapeJson(
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
                        "\"",
                        "\\\""
                )

                .replace(
                        "\r",
                        ""
                )

                .replace(
                        "\n",
                        "\\n"
                );
    }

}