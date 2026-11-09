package com.controller;

import com.config.Conexion;
import com.dao.RolDAO;
import com.model.Rol;

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
        name = "RolController",
        urlPatterns = {"/RolController"}
)
public class RolController extends HttpServlet {

    private final RolDAO dao = new RolDAO();

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

        } else if ("reporteIndividual".equals(action)) {

            generarReporteIndividual(
                    request,
                    response
            );

        } else {

            response.sendRedirect(
                    "dashboard.jsp"
            );
        }
    }

    // POST

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        String action =
                request.getParameter("action");

        try {

            // LISTAR

            if ("listar".equals(action)) {

                listar(response);

            // LISTAR ACTIVOS

            } else if ("listarOptions".equals(action)) {

                listarOptions(response);

            // BUSCAR

            } else if ("buscar".equals(action)) {

                buscar(request, response);

            // INSERTAR

            } else if ("insertar".equals(action)) {

                insertar(request, response);

            // ACTUALIZAR

            } else if ("actualizar".equals(action)) {

                actualizar(request, response);

            // DESACTIVAR

            } else if ("desactivar".equals(action)) {

                int id =
                        Integer.parseInt(
                                request.getParameter(
                                        "id_rol"
                                )
                        );

                boolean resultado =
                        dao.desactivar(id);

                response.getWriter().print(
                        resultado ? "OK" : "ERROR"
                );

            // ACTIVAR

            } else if ("activar".equals(action)) {

                int id =
                        Integer.parseInt(
                                request.getParameter(
                                        "id_rol"
                                )
                        );

                boolean resultado =
                        dao.activar(id);

                response.getWriter().print(
                        resultado ? "OK" : "ERROR"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().print(
                    "ERROR"
            );
        }
    }

    // LISTAR

    private void listar(
            HttpServletResponse response)
            throws IOException {

        List<Rol> lista =
                dao.listar();

        StringBuilder sb =
                new StringBuilder();

        sb.append(
                "<table id='tablaRoles' "
                + "class='table table-bordered table-striped'>"
        );

        sb.append("<thead>");
        sb.append("<tr>");

        sb.append("<th>ID</th>");
        sb.append("<th>Rol</th>");
        sb.append("<th>Descripción</th>");
        sb.append("<th>Estado</th>");
        sb.append("<th>Acciones</th>");

        sb.append("</tr>");
        sb.append("</thead>");

        sb.append("<tbody>");

        for (Rol r : lista) {

            sb.append("<tr>");

            sb.append("<td>")
              .append(r.getId_rol())
              .append("</td>");

            sb.append("<td>")
              .append(safe(r.getNombre_rol()))
              .append("</td>");

            sb.append("<td>")
              .append(safe(r.getDescripcion()))
              .append("</td>");

            if (r.isEstado()) {

                sb.append("<td>")
                  .append(
                          "<span class='badge badge-success'>"
                  )
                  .append("Activo")
                  .append("</span>")
                  .append("</td>");

            } else {

                sb.append("<td>")
                  .append(
                          "<span class='badge badge-secondary'>"
                  )
                  .append("Inactivo")
                  .append("</span>")
                  .append("</td>");
            }

            sb.append("<td>");

            // EDITAR

            sb.append("<button ")
              .append("class='btn btn-warning btn-sm mr-1' ")
              .append("title='Modificar rol' ")
              .append("onclick='editarRol(")
              .append(r.getId_rol())
              .append(")'>")
              .append("<i class='fas fa-edit'></i>")
              .append("</button>");

            // REPORTE INDIVIDUAL

            sb.append("<a ")
              .append(
                      "href='RolController?action="
                      + "reporteIndividual&id_rol="
              )
              .append(r.getId_rol())
              .append("' ")
              .append("target='_blank' ")
              .append(
                      "class='btn btn-info btn-sm mr-1' "
              )
              .append("title='Reporte individual'>")
              .append(
                      "<i class='fas fa-eye'></i>"
              )
              .append("</a>");

            // ACTIVAR / DESACTIVAR

            if (r.isEstado()) {

                sb.append("<button ")
                  .append(
                          "class='btn btn-danger btn-sm' "
                  )
                  .append(
                          "title='Desactivar rol' "
                  )
                  .append(
                          "onclick='desactivarRol("
                  )
                  .append(r.getId_rol())
                  .append(")'>")
                  .append(
                          "<i class='fas fa-ban'></i>"
                  )
                  .append("</button>");

            } else {

                sb.append("<button ")
                  .append(
                          "class='btn btn-success btn-sm' "
                  )
                  .append(
                          "title='Activar rol' "
                  )
                  .append(
                          "onclick='activarRol("
                  )
                  .append(r.getId_rol())
                  .append(")'>")
                  .append(
                          "<i class='fas fa-check'></i>"
                  )
                  .append("</button>");
            }

            sb.append("</td>");

            sb.append("</tr>");
        }

        sb.append("</tbody>");
        sb.append("</table>");

        response.getWriter().print(
                sb.toString()
        );
    }

    // BUSCAR

    private void buscar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id =
                Integer.parseInt(
                        request.getParameter(
                                "id_rol"
                        )
                );

        Rol r =
                dao.buscarPorId(id);

        if (r == null) {

            response.getWriter().print(
                    "ERROR"
            );

            return;
        }

        StringBuilder json =
                new StringBuilder();

        json.append("{");

        json.append("\"id_rol\":")
            .append(r.getId_rol())
            .append(",");

        json.append("\"nombre_rol\":\"")
            .append(
                    safeJSON(
                            r.getNombre_rol()
                    )
            )
            .append("\",");

        json.append("\"descripcion\":\"")
            .append(
                    safeJSON(
                            r.getDescripcion()
                    )
            )
            .append("\",");

        json.append("\"estado\":")
            .append(r.isEstado());

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

        String nombre =
                request.getParameter(
                        "nombre_rol"
                );

        String descripcion =
                request.getParameter(
                        "descripcion"
                );

        Rol r = new Rol();

        r.setNombre_rol(nombre);
        r.setDescripcion(descripcion);

        boolean resultado =
                dao.agregar(r);

        response.getWriter().print(
                resultado ? "OK" : "ERROR"
        );
    }

    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id =
                Integer.parseInt(
                        request.getParameter(
                                "id_rol"
                        )
                );

        String nombre =
                request.getParameter(
                        "nombre_rol"
                );

        String descripcion =
                request.getParameter(
                        "descripcion"
                );

        Rol r = new Rol();

        r.setId_rol(id);
        r.setNombre_rol(nombre);
        r.setDescripcion(descripcion);

        boolean resultado =
                dao.actualizar(r);

        response.getWriter().print(
                resultado ? "OK" : "ERROR"
        );
    }

    // LISTAR OPCIONES

    private void listarOptions(
            HttpServletResponse response)
            throws IOException {

        List<Rol> lista =
                dao.listarActivos();

        StringBuilder sb =
                new StringBuilder();

        sb.append(
                "<option value='0'>"
        );

        sb.append(
                "Seleccione un rol"
        );

        sb.append(
                "</option>"
        );

        for (Rol r : lista) {

            sb.append(
                    "<option value='"
            )
            .append(r.getId_rol())
            .append("'>")
            .append(
                    safe(r.getNombre_rol())
            )
            .append(
                    "</option>"
            );
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

        try (
                Connection conn =
                        new Conexion().getConexion()
        ) {

            String path =
                    getServletContext()
                            .getRealPath(
                                    "/WEB-INF/reportes/"
                                    + "roles_listado.jrxml"
                            );

            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(path);

            Map<String, Object> params =
                    new HashMap<>();

            String usuarioLogueado =
                    (String) request
                            .getSession()
                            .getAttribute(
                                    "usuario"
                            );

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
                    "inline; filename=roles.pdf"
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

        try (
                Connection conn =
                        new Conexion().getConexion()
        ) {

            int id =
                    Integer.parseInt(
                            request.getParameter(
                                    "id_rol"
                            )
                    );

            String path =
                    getServletContext()
                            .getRealPath(
                                    "/WEB-INF/reportes/"
                                    + "rol_individual.jrxml"
                            );

            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(path);

            Map<String, Object> params =
                    new HashMap<>();

            params.put(
                    "idRol",
                    id
            );

            String usuarioLogueado =
                    (String) request
                            .getSession()
                            .getAttribute(
                                    "usuario"
                            );

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
                    "inline; filename=rol_"
                    + id
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