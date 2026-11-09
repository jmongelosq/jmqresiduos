package com.controller;

import com.config.Conexion;
import com.dao.ClienteDAO;
import com.model.Cliente;

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

@WebServlet("/ClienteController")
public class ClienteController extends HttpServlet {

    private final ClienteDAO dao = new ClienteDAO();

    // GET
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("reporteGeneral".equals(accion)) {

            generarReporteGeneral(request, response);
            return;
        }

        if ("reporteIndividual".equals(accion)) {

            generarReporteIndividual(request, response);
            return;
        }

        if ("Borrar".equals(accion)) {

            borrar(request, response);
            return;
        }

        response.sendRedirect("dashboard.jsp?modulo=clientes");
    }

    // POST
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if ("listar".equals(accion)) {

            listar(request, response);
            return;
        }

        if ("listarOptions".equals(accion)) {

            listarOptions(request, response);
            return;
        }

        if ("insertar".equals(accion)) {

            insertar(request, response);
            return;
        }

        if ("actualizar".equals(accion)) {

            actualizar(request, response);
            return;
        }

        if ("Borrar".equals(accion)) {

            borrar(request, response);
            return;
        }

        if ("activar".equals(accion)) {

            activar(request, response);
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

        List<Cliente> lista = dao.listar();

        PrintWriter out = response.getWriter();

        out.print("[");

        for (int i = 0; i < lista.size(); i++) {

            Cliente c = lista.get(i);

            if (i > 0) {
                out.print(",");
            }

            out.print("{");

            out.print(
                    "\"id_cliente\":"
                    + c.getId_cliente()
                    + ","
            );

            out.print(
                    "\"nombre\":\""
                    + safeJS(c.getNombre())
                    + "\","
            );

            out.print(
                    "\"apellido\":\""
                    + safeJS(c.getApellido())
                    + "\","
            );

            out.print(
                    "\"cedula\":\""
                    + safeJS(c.getCedula())
                    + "\","
            );

            out.print(
                    "\"ruc\":\""
                    + safeJS(c.getRuc())
                    + "\","
            );

            out.print(
                    "\"telefono\":\""
                    + safeJS(c.getTelefono())
                    + "\","
            );

            out.print(
                    "\"direccion\":\""
                    + safeJS(c.getDireccion())
                    + "\","
            );

            out.print(
                    "\"correo\":\""
                    + safeJS(c.getCorreo())
                    + "\","
            );

            out.print(
                    "\"id_barrio\":"
                    + c.getId_barrio()
                    + ","
            );

            out.print(
                    "\"nombre_barrio\":\""
                    + safeJS(c.getNombre_barrio())
                    + "\","
            );

            out.print(
                    "\"estado\":\""
                    + safeJS(c.getEstado())
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

        List<Cliente> lista = dao.listarActivos();

        PrintWriter out = response.getWriter();

        out.print("[");

        for (int i = 0; i < lista.size(); i++) {

            Cliente c = lista.get(i);

            if (i > 0) {
                out.print(",");
            }

            out.print("{");

            out.print(
                    "\"id_cliente\":"
                    + c.getId_cliente()
                    + ","
            );

            out.print(
                    "\"nombre\":\""
                    + safeJS(
                            c.getNombre()
                            + " "
                            + c.getApellido()
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

        Cliente c = obtenerDatos(request);

        boolean resultado = dao.insertar(c);

        responder(response, resultado);
    }

    // ACTUALIZAR
    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Cliente c = obtenerDatos(request);

        try {

            c.setId_cliente(
                    Integer.parseInt(
                            request.getParameter("id_cliente")
                    )
            );

        } catch (Exception e) {

            responder(response, false);

            return;
        }

        boolean resultado = dao.actualizar(c);

        responder(response, resultado);
    }

    // BORRAR - BAJA LÓGICA
    private void borrar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            boolean resultado = dao.eliminar(id);

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

            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            boolean resultado = dao.activar(id);

            responder(response, resultado);

        } catch (Exception e) {

            e.printStackTrace();

            responder(response, false);
        }
    }

    // OBTENER DATOS
    private Cliente obtenerDatos(
            HttpServletRequest request) {

        Cliente c = new Cliente();

        c.setNombre(
                request.getParameter("nombre")
        );

        c.setApellido(
                request.getParameter("apellido")
        );

        c.setCedula(
                request.getParameter("cedula")
        );

        c.setRuc(
                request.getParameter("ruc")
        );

        c.setTelefono(
                request.getParameter("telefono")
        );

        c.setDireccion(
                request.getParameter("direccion")
        );

        c.setCorreo(
                request.getParameter("correo")
        );

        try {

            c.setId_barrio(
                    Integer.parseInt(
                            request.getParameter("id_barrio")
                    )
            );

        } catch (Exception e) {

            c.setId_barrio(0);
        }

        return c;
    }

    // REPORTE GENERAL
    private void generarReporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Connection con = null;

        try {

            Conexion conexion = new Conexion();

            con = conexion.getConexion();

            String ruta =
                    "/WEB-INF/reportes/clientes_listado.jrxml";

            InputStream input =
                    getServletContext()
                            .getResourceAsStream(ruta);

            if (input == null) {

                throw new Exception(
                        "No se encontró el archivo clientes_listado.jrxml"
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

            int idCliente =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            Conexion conexion = new Conexion();

            con = conexion.getConexion();

            String ruta =
                    "/WEB-INF/reportes/cliente_individual.jrxml";

            InputStream input =
                    getServletContext()
                            .getResourceAsStream(ruta);

            if (input == null) {

                throw new Exception(
                        "No se encontró el archivo cliente_individual.jrxml"
                );
            }

            Map<String, Object> parametros =
                    parametrosReporte(request);

            parametros.put(
                    "idCliente",
                    idCliente
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

            if (con != null) {

                try {
                    con.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    // PARÁMETROS DE REPORTE
    private Map<String, Object> parametrosReporte(
            HttpServletRequest request) {

        Map<String, Object> parametros =
                new HashMap<>();

        parametros.put(
                "usuarioImpresion",
                obtenerUsuario(request)
        );

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

    // USUARIO DE SESIÓN
    private String obtenerUsuario(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        if (session != null) {

            Object usuario =
                    session.getAttribute("usuario");

            if (usuario != null
                    && !usuario.toString()
                    .trim()
                    .isEmpty()) {

                return usuario.toString();
            }
        }

        return "Sistema";
    }

    // SAFE
    private String safe(String texto) {

        return texto == null
                ? ""
                : texto;
    }

    // SAFE JSON
    private String safeJS(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", " ");
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
