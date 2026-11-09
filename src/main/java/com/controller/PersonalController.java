package com.controller;

import com.dao.PersonalDAO;
import com.model.Personal;

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

@WebServlet("/PersonalController")
public class PersonalController extends HttpServlet {

    private final PersonalDAO dao = new PersonalDAO();


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");


        // REPORTE GENERAL

        if ("reporteGeneral".equals(accion)) {

            generarReporteGeneral(
                    request,
                    response
            );

            return;
        }


        // REPORTE INDIVIDUAL

        if ("reporteIndividual".equals(accion)) {

            generarReporteIndividual(
                    request,
                    response
            );

            return;
        }


        response.sendRedirect("dashboard.jsp?modulo=personal");
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


        // LISTAR

        if ("listar".equals(accion)) {

            listar(
                    request,
                    response
            );

            return;
        }


        // LISTAR OPTIONS

        if ("listarOptions".equals(accion)) {

            listarOptions(
                    request,
                    response
            );

            return;
        }


        // INSERTAR

        if ("insertar".equals(accion)) {

            insertar(
                    request,
                    response
            );

            return;
        }


        // ACTUALIZAR

        if ("actualizar".equals(accion)) {

            actualizar(
                    request,
                    response
            );

            return;
        }


        // BORRAR / DESACTIVAR

        if ("Borrar".equals(accion)) {

            borrar(
                    request,
                    response
            );

            return;
        }


        // ACTIVAR

        if ("activar".equals(accion)) {

            activar(
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

        List<Personal> lista =
                dao.listar();

        PrintWriter out =
                response.getWriter();

        out.print("[");


        for (int i = 0; i < lista.size(); i++) {

            Personal p =
                    lista.get(i);


            if (i > 0) {

                out.print(",");
            }


            out.print("{");


            out.print(
                    "\"id_personal\":"
                    + p.getId_personal()
                    + ","
            );


            out.print(
                    "\"nombre\":\""
                    + safeJS(p.getNombre())
                    + "\","
            );


            out.print(
                    "\"apellido\":\""
                    + safeJS(p.getApellido())
                    + "\","
            );


            out.print(
                    "\"cedula\":\""
                    + safeJS(p.getCedula())
                    + "\","
            );


            out.print(
                    "\"telefono\":\""
                    + safeJS(p.getTelefono())
                    + "\","
            );


            out.print(
                    "\"direccion\":\""
                    + safeJS(p.getDireccion())
                    + "\","
            );


            out.print(
                    "\"correo\":\""
                    + safeJS(p.getCorreo())
                    + "\","
            );


            out.print(
                    "\"id_cargo\":"
                    + p.getId_cargo()
                    + ","
            );


            /*
             * El modelo Java continúa utilizando
             * id_barrios, pero en PostgreSQL la columna
             * correcta es id_barrio.
             */
            out.print(
                    "\"id_barrios\":"
                    + p.getId_barrios()
                    + ","
            );


            out.print(
                    "\"nombre_cargo\":\""
                    + safeJS(p.getNombre_cargo())
                    + "\","
            );


            out.print(
                    "\"nombre_barrio\":\""
                    + safeJS(p.getNombre_barrio())
                    + "\","
            );


            out.print(
                    "\"estado\":\""
                    + safeJS(p.getEstado())
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

        List<Personal> lista =
                dao.listarActivos();

        PrintWriter out =
                response.getWriter();

        out.print("[");


        for (int i = 0; i < lista.size(); i++) {

            Personal p =
                    lista.get(i);


            if (i > 0) {

                out.print(",");
            }


            out.print("{");


            out.print(
                    "\"id_personal\":"
                    + p.getId_personal()
                    + ","
            );


            String nombreCompleto =
                    safe(
                            p.getNombre()
                    )
                    + " "
                    + safe(
                            p.getApellido()
                    );


            out.print(
                    "\"nombre\":\""
                    + safeJS(nombreCompleto.trim())
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

        try {

            Personal p =
                    obtenerDatos(request);


            if (cedulaRepetida(response, p.getCedula(), 0)) {
                return;
            }


            boolean resultado =
                    dao.insertar(p);


            responder(response, resultado);

        } catch (Exception e) {

            e.printStackTrace();

            responder(response, false);
        }
    }


    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Personal p =
                    obtenerDatos(request);


            String id =
                    request.getParameter(
                            "id_personal"
                    );


            if (id == null ||
                id.trim().isEmpty()) {

                responder(response, false);

                return;
            }


            p.setId_personal(
                    Integer.parseInt(id)
            );


            if (cedulaRepetida(response, p.getCedula(), p.getId_personal())) {
                return;
            }


            boolean resultado =
                    dao.actualizar(p);


            responder(response, resultado);

        } catch (Exception e) {

            e.printStackTrace();

            responder(response, false);
        }
    }


    // BORRAR / DESACTIVAR

    private void borrar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String valorId =
                    request.getParameter("id");


            if (valorId == null ||
                valorId.trim().isEmpty()) {

                responder(response, false);

                return;
            }


            int id =
                    Integer.parseInt(valorId);


            boolean resultado =
                    dao.eliminar(id);


            responder(response, resultado);

        } catch (Exception e) {

            e.printStackTrace();

            responder(response, false);
        }
    }


    // ACTIVAR

    private void activar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String valorId =
                    request.getParameter("id");


            if (valorId == null ||
                valorId.trim().isEmpty()) {

                responder(response, false);

                return;
            }


            int id =
                    Integer.parseInt(valorId);


            boolean resultado =
                    dao.activar(id);


            responder(response, resultado);

        } catch (Exception e) {

            e.printStackTrace();

            responder(response, false);
        }
    }


    // DATOS DEL FORMULARIO

    private Personal obtenerDatos(
            HttpServletRequest request) {

        Personal p =
                new Personal();


        p.setNombre(
                request.getParameter("nombre")
        );


        p.setApellido(
                request.getParameter("apellido")
        );


        p.setCedula(
                request.getParameter("cedula")
        );


        p.setTelefono(
                request.getParameter("telefono")
        );


        p.setDireccion(
                request.getParameter("direccion")
        );


        p.setCorreo(
                request.getParameter("correo")
        );


        p.setEstado(
                request.getParameter("estado")
        );


        // CARGO

        try {

            p.setId_cargo(
                    Integer.parseInt(
                            request.getParameter(
                                    "id_cargo"
                            )
                    )
            );

        } catch (Exception e) {

            p.setId_cargo(0);
        }


        // BARRIO

        try {

            p.setId_barrios(
                    Integer.parseInt(
                            request.getParameter(
                                    "id_barrios"
                            )
                    )
            );

        } catch (Exception e) {

            p.setId_barrios(0);
        }


        return p;
    }


    // REPORTE GENERAL

    private void generarReporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Connection con = null;


        try {

            com.config.Conexion conexion =
                    new com.config.Conexion();


            con =
                    conexion.getConexion();


            String ruta =
                    "/WEB-INF/reportes/personal_listado.jrxml";


            InputStream input =
                    getServletContext()
                            .getResourceAsStream(ruta);


            if (input == null) {

                throw new Exception(
                        "No se encontró el archivo "
                        + "personal_listado.jrxml"
                );
            }


            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(

                            JasperCompileManager
                                    .compileReport(input),

                            parametrosReporte(request),

                            con
                    );


            response.setContentType(
                    "application/pdf"
            );


            response.setHeader(
                    "Content-Disposition",
                    "inline; "
                    + "filename=personal_listado.pdf"
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
                    "Error al generar el "
                    + "reporte general de personal:"
            );


            response.getWriter().println(
                    e.getMessage()
            );


        } finally {

            if (con != null) {

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

        Connection con = null;


        try {

            String valorId =
                    request.getParameter("id");


            if (valorId == null ||
                valorId.trim().isEmpty()) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "ID de personal no válido"
                );

                return;
            }


            int idPersonal =
                    Integer.parseInt(
                            valorId
                    );


            com.config.Conexion conexion =
                    new com.config.Conexion();


            con =
                    conexion.getConexion();


            String ruta =
                    "/WEB-INF/reportes/"
                    + "personal_individual.jrxml";


            InputStream input =
                    getServletContext()
                            .getResourceAsStream(ruta);


            if (input == null) {

                throw new Exception(
                        "No se encontró el archivo "
                        + "personal_individual.jrxml"
                );
            }


            Map<String, Object> parametros =
                    parametrosReporte(request);


            parametros.put(
                    "idPersonal",
                    idPersonal
            );


            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(

                            JasperCompileManager
                                    .compileReport(input),

                            parametros,

                            con
                    );


            response.setContentType(
                    "application/pdf"
            );


            response.setHeader(
                    "Content-Disposition",
                    "inline; "
                    + "filename=personal_"
                    + idPersonal
                    + ".pdf"
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
                    "Error al generar el "
                    + "reporte individual:"
            );


            response.getWriter().println(
                    e.getMessage()
            );


        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (Exception ignored) {
                }
            }
        }
    }


    // PARÁMETROS DE REPORTES

    private Map<String, Object> parametrosReporte(
            HttpServletRequest request) {

        Map<String, Object> parametros =
                new HashMap<>();


        // USUARIO DE LA SESIÓN

        String usuarioImpresion =
                "Sistema";


        HttpSession session =
                request.getSession(false);


        if (session != null &&
            session.getAttribute("usuario") != null) {

            usuarioImpresion =
                    session.getAttribute(
                            "usuario"
                    ).toString();
        }


        parametros.put(
                "usuarioImpresion",
                usuarioImpresion
        );


        // LOGO

        String logoPath =
                getServletContext()
                        .getRealPath(
                                "/img/logo.png"
                        );


        parametros.put(
                "logoPath",
                logoPath
        );


        return parametros;
    }


    // SAFE

    private String safe(String texto) {

        return texto == null
                ? ""
                : texto;
    }


    // SAFE JSON / JAVASCRIPT

    private String safeJS(String texto) {

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
                        " "
                );
    }


    private boolean cedulaRepetida(
            HttpServletResponse response,
            String cedula,
            int idExcluir)
            throws IOException {

        if (cedula == null || !dao.existeCedula(cedula, idExcluir)) {
            return false;
        }

        response.setContentType("text/plain;charset=UTF-8");
        response.setStatus(409);
        response.getWriter().write(
                "Ya existe un personal registrado con la cédula " + cedula.trim() + "."
        );

        return true;
    }


    private void responder(
            HttpServletResponse response,
            boolean resultado)
            throws IOException {

        response.setContentType("text/plain;charset=UTF-8");

        if (!resultado) {
            response.setStatus(500);
        }

        response.getWriter().write(resultado ? "ok" : "error");
    }
}
