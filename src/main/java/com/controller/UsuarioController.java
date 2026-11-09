package com.controller;

import com.config.Conexion;
import com.dao.UsuarioDAO;
import com.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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
        name = "UsuarioController",
        urlPatterns = {"/UsuarioController"}
)
public class UsuarioController extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();

    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // REPORTE GENERAL

        if ("reporteGeneral".equals(action)) {

            generarReporteGeneral(request, response);

        // REPORTE INDIVIDUAL

        } else if ("reporteIndividual".equals(action)) {

            generarReporteIndividual(request, response);

        } else {

            response.sendRedirect("dashboard.jsp");
        }
    }

    // POST

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");

        try {

            // LISTAR

            if ("listar".equals(action)) {

                listarUsuarios(response);

            // BUSCAR

            } else if ("buscar".equals(action)) {

                buscarUsuario(request, response);

            // LISTAR PERSONAL

            } else if ("listarPersonal".equals(action)) {

                listarPersonal(response);

            // LISTAR ROLES

            } else if ("listarRoles".equals(action)) {

                listarRoles(response);

            // INSERTAR

            } else if ("insertar".equals(action)) {

                insertar(request, response);

            // ACTUALIZAR

            } else if ("actualizar".equals(action)) {

                actualizar(request, response);

            // DESACTIVAR

            } else if ("desactivar".equals(action)) {

                int id = Integer.parseInt(
                        request.getParameter("id_usuario")
                );

                boolean resultado = dao.desactivar(id);

                response.getWriter().print(
                        resultado ? "OK" : "ERROR"
                );

            // ACTIVAR

            } else if ("activar".equals(action)) {

                int id = Integer.parseInt(
                        request.getParameter("id_usuario")
                );

                boolean resultado = dao.activar(id);

                response.getWriter().print(
                        resultado ? "OK" : "ERROR"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print("ERROR");
        }
    }

    // LISTAR USUARIOS

    private void listarUsuarios(
            HttpServletResponse response)
            throws IOException {

        List<Usuario> lista = dao.listar();

        StringBuilder sb = new StringBuilder();

        sb.append("<table id='tablaUsuarios' ")
          .append("class='table table-bordered table-striped'>");

        sb.append("<thead>");
        sb.append("<tr>");

        sb.append("<th>ID</th>");
        sb.append("<th>Usuario</th>");
        sb.append("<th>Nombre completo</th>");
        sb.append("<th>Correo</th>");
        sb.append("<th>Personal</th>");
        sb.append("<th>Rol</th>");
        sb.append("<th>Estado</th>");
        sb.append("<th>Acciones</th>");

        sb.append("</tr>");
        sb.append("</thead>");

        sb.append("<tbody>");

        for (Usuario u : lista) {

            sb.append("<tr>");

            sb.append("<td>")
              .append(u.getId_usuario())
              .append("</td>");

            sb.append("<td>")
              .append(safe(u.getNombre_usuario()))
              .append("</td>");

            sb.append("<td>")
              .append(safe(u.getNombre_completo()))
              .append("</td>");

            sb.append("<td>")
              .append(safe(u.getCorreo()))
              .append("</td>");

            String personal = u.getNombre_personal();

            if (personal == null ||
                personal.trim().isEmpty()) {

                personal = "Sin asignar";
            }

            sb.append("<td>")
              .append(safe(personal))
              .append("</td>");

            String rol = u.getNombre_rol();

            if (rol == null ||
                rol.trim().isEmpty()) {

                rol = "Sin rol";
            }

            sb.append("<td>")
              .append(safe(rol))
              .append("</td>");

            // Estado

            if (u.isEstado()) {

                sb.append("<td>")
                  .append("<span class='badge badge-success'>")
                  .append("Activo")
                  .append("</span>")
                  .append("</td>");

            } else {

                sb.append("<td>")
                  .append("<span class='badge badge-secondary'>")
                  .append("Inactivo")
                  .append("</span>")
                  .append("</td>");
            }

            // Acciones

            sb.append("<td>");

            // Editar

            sb.append("<button ")
              .append("class='btn btn-warning btn-sm mr-1' ")
              .append("title='Modificar usuario' ")
              .append("onclick=\"editarUsuario(")
              .append(u.getId_usuario())
              .append(")\">")
              .append("<i class='fas fa-edit'></i>")
              .append("</button>");

            // Reporte individual

            sb.append("<a ")
              .append("href='UsuarioController?action=reporteIndividual&id_usuario=")
              .append(u.getId_usuario())
              .append("' ")
              .append("target='_blank' ")
              .append("class='btn btn-info btn-sm mr-1' ")
              .append("title='Reporte individual'>")
              .append("<i class='fas fa-eye'></i>")
              .append("</a>");

            // Activar / Desactivar

            if (u.isEstado()) {

                sb.append("<button ")
                  .append("class='btn btn-danger btn-sm' ")
                  .append("title='Desactivar usuario' ")
                  .append("onclick=\"desactivarUsuario(")
                  .append(u.getId_usuario())
                  .append(")\">")
                  .append("<i class='fas fa-user-slash'></i>")
                  .append("</button>");

            } else {

                sb.append("<button ")
                  .append("class='btn btn-success btn-sm' ")
                  .append("title='Activar usuario' ")
                  .append("onclick=\"activarUsuario(")
                  .append(u.getId_usuario())
                  .append(")\">")
                  .append("<i class='fas fa-user-check'></i>")
                  .append("</button>");
            }

            sb.append("</td>");

            sb.append("</tr>");
        }

        sb.append("</tbody>");
        sb.append("</table>");

        response.getWriter().print(sb.toString());
    }

    // BUSCAR USUARIO

    private void buscarUsuario(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id_usuario")
        );

        Usuario u = dao.buscarPorId(id);

        if (u == null) {

            response.getWriter().print("ERROR");
            return;
        }

        StringBuilder json = new StringBuilder();

        json.append("{");

        json.append("\"id_usuario\":")
            .append(u.getId_usuario())
            .append(",");

        json.append("\"nombre_usuario\":\"")
            .append(safeJSON(u.getNombre_usuario()))
            .append("\",");

        json.append("\"nombre_completo\":\"")
            .append(safeJSON(u.getNombre_completo()))
            .append("\",");

        json.append("\"correo\":\"")
            .append(safeJSON(u.getCorreo()))
            .append("\",");

        json.append("\"id_personal\":")
            .append(u.getId_personal())
            .append(",");

        json.append("\"id_rol\":")
            .append(u.getId_rol())
            .append(",");

        json.append("\"estado\":")
            .append(u.isEstado());

        json.append("}");

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        response.getWriter().print(
                json.toString()
        );
    }

    // INSERTAR

    private void insertar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String nombreUsuario =
                request.getParameter("nombre_usuario");

        String nombreCompleto =
                request.getParameter("nombre_completo");

        String correo =
                request.getParameter("correo");

        String clave =
                request.getParameter("clave");

        int idPersonal =
                Integer.parseInt(
                        request.getParameter("id_personal")
                );

        int idRol =
                Integer.parseInt(
                        request.getParameter("id_rol")
                );

        Usuario u = new Usuario();

        u.setNombre_usuario(nombreUsuario);
        u.setNombre_completo(nombreCompleto);
        u.setCorreo(correo);
        u.setClave(clave);
        u.setId_personal(idPersonal);
        u.setId_rol(idRol);

        boolean resultado = dao.agregar(u);

        response.getWriter().print(
                resultado ? "OK" : "ERROR"
        );
    }

    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int idUsuario =
                Integer.parseInt(
                        request.getParameter("id_usuario")
                );

        String nombreUsuario =
                request.getParameter("nombre_usuario");

        String nombreCompleto =
                request.getParameter("nombre_completo");

        String correo =
                request.getParameter("correo");

        String clave =
                request.getParameter("clave");

        int idPersonal =
                Integer.parseInt(
                        request.getParameter("id_personal")
                );

        int idRol =
                Integer.parseInt(
                        request.getParameter("id_rol")
                );

        Usuario u = new Usuario();

        u.setId_usuario(idUsuario);
        u.setNombre_usuario(nombreUsuario);
        u.setNombre_completo(nombreCompleto);
        u.setCorreo(correo);
        u.setClave(clave);
        u.setId_personal(idPersonal);
        u.setId_rol(idRol);

        boolean resultado = dao.actualizar(u);

        response.getWriter().print(
                resultado ? "OK" : "ERROR"
        );
    }

    // LISTAR PERSONAL

    private void listarPersonal(
            HttpServletResponse response)
            throws IOException {

        List<Usuario> lista =
                dao.listarPersonal();

        StringBuilder sb =
                new StringBuilder();

        sb.append("<option value='0'>")
          .append("Sin asignar")
          .append("</option>");

        for (Usuario u : lista) {

            sb.append("<option value='")
              .append(u.getId_personal())
              .append("'>")
              .append(safe(u.getNombre_personal()))
              .append("</option>");
        }

        response.getWriter().print(
                sb.toString()
        );
    }

    // LISTAR ROLES

    private void listarRoles(
            HttpServletResponse response)
            throws IOException {

        List<Usuario> lista =
                dao.listarRoles();

        StringBuilder sb =
                new StringBuilder();

        sb.append("<option value='0'>")
          .append("Seleccione un rol")
          .append("</option>");

        for (Usuario u : lista) {

            sb.append("<option value='")
              .append(u.getId_rol())
              .append("'>")
              .append(safe(u.getNombre_rol()))
              .append("</option>");
        }

        response.getWriter().print(
                sb.toString()
        );
    }

    // REPORTE GENERAL

    private void generarReporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try (Connection conn =
                     new Conexion().getConexion()) {

            String path =
                    getServletContext().getRealPath(
                            "/WEB-INF/reportes/usuarios_listado.jrxml"
                    );

            JasperReport reporte =
                    JasperCompileManager.compileReport(path);

            Map<String, Object> params =
                    new HashMap<>();

            String usuarioLogueado =
                    (String) request
                            .getSession()
                            .getAttribute("usuario");

            params.put(
                    "usuarioImpresion",
                    usuarioLogueado
            );

            JasperPrint print =
                    JasperFillManager.fillReport(
                            reporte,
                            params,
                            conn
                    );

            response.setContentType(
                    "application/pdf"
            );

            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=usuarios.pdf"
            );

            JasperExportManager
                    .exportReportToPdfStream(
                            print,
                            response.getOutputStream()
                    );

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "text/plain;charset=UTF-8"
            );

            response.getWriter().print(
                    "Error al generar reporte: "
                    + e.getMessage()
            );
        }
    }

    // REPORTE INDIVIDUAL

    private void generarReporteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try (Connection conn =
                     new Conexion().getConexion()) {

            int idUsuario =
                    Integer.parseInt(
                            request.getParameter(
                                    "id_usuario"
                            )
                    );

            String path =
                    getServletContext().getRealPath(
                            "/WEB-INF/reportes/usuario_individual.jrxml"
                    );

            JasperReport reporte =
                    JasperCompileManager.compileReport(path);

            Map<String, Object> params =
                    new HashMap<>();

            params.put(
                    "idUsuario",
                    idUsuario
            );

            String usuarioLogueado =
                    (String) request
                            .getSession()
                            .getAttribute("usuario");

            params.put(
                    "usuarioImpresion",
                    usuarioLogueado
            );

            JasperPrint print =
                    JasperFillManager.fillReport(
                            reporte,
                            params,
                            conn
                    );

            response.setContentType(
                    "application/pdf"
            );

            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=usuario_"
                    + idUsuario
                    + ".pdf"
            );

            JasperExportManager
                    .exportReportToPdfStream(
                            print,
                            response.getOutputStream()
                    );

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "text/plain;charset=UTF-8"
            );

            response.getWriter().print(
                    "Error al generar reporte: "
                    + e.getMessage()
            );
        }
    }

    // SEGURIDAD HTML

    private String safe(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // SEGURIDAD JSON

    private String safeJSON(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "\\n");
    }
}