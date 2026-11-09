package com.controller;

import com.config.Conexion;
import com.dao.VehiculoDAO;
import com.model.Vehiculo;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/VehiculoController")
public class VehiculoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final VehiculoDAO dao = new VehiculoDAO();


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion =
                request.getParameter("accion");

        if ("reporteGeneral".equals(accion)) {

            generarReporteGeneral(
                    request,
                    response
            );

            return;
        }

        if ("reporteIndividual".equals(accion)) {

            generarReporteIndividual(
                    request,
                    response
            );

            return;
        }

        response.sendRedirect(
                "vehiculos.jsp"
        );
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

        if ("listar".equals(accion)) {

            listar(response);

            return;
        }

        if ("listarOptions".equals(accion)) {

            listarOptions(response);

            return;
        }

        if ("insertar".equals(accion)) {

            insertar(
                    request,
                    response
            );

            return;
        }

        if ("actualizar".equals(accion)) {

            actualizar(
                    request,
                    response
            );

            return;
        }

        if ("Borrar".equals(accion)) {

            borrar(
                    request,
                    response
            );

            return;
        }

        // Se conserva por compatibilidad.
        // No se muestra botón Activar en el JSP.

        if ("activar".equals(accion)) {

            activar(
                    request,
                    response
            );

            return;
        }

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        response.getWriter().write(
                "{\"success\":false}"
        );
    }


    // LISTAR TODOS

    private void listar(
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        List<Vehiculo> lista =
                dao.listar();

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0;
             i < lista.size();
             i++) {

            Vehiculo v =
                    lista.get(i);

            if (i > 0) {
                json.append(",");
            }

            json.append("{");

            json.append("\"id_vehiculo\":")
                    .append(v.getId_vehiculo())
                    .append(",");

            json.append("\"matricula\":\"")
                    .append(
                            safeJSON(
                                    v.getMatricula()
                            )
                    )
                    .append("\",");

            json.append("\"marca\":\"")
                    .append(
                            safeJSON(
                                    v.getMarca()
                            )
                    )
                    .append("\",");

            json.append("\"anio\":")
                    .append(v.getAnio())
                    .append(",");

            json.append("\"capacidad\":")
                    .append(v.getCapacidad())
                    .append(",");

            json.append("\"id_modelo\":")
                    .append(v.getId_modelo())
                    .append(",");

            json.append("\"nombre_modelo\":\"")
                    .append(
                            safeJSON(
                                    v.getNombre_modelo()
                            )
                    )
                    .append("\",");

            json.append("\"estado\":\"")
                    .append(
                            safeJSON(
                                    v.getEstado()
                            )
                    )
                    .append("\"");

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

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        List<Vehiculo> lista =
                dao.listarActivos();

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0;
             i < lista.size();
             i++) {

            Vehiculo v =
                    lista.get(i);

            if (i > 0) {
                json.append(",");
            }

            json.append("{");

            json.append("\"id_vehiculo\":")
                    .append(v.getId_vehiculo())
                    .append(",");

            json.append("\"nombre\":\"")
                    .append(
                            safeJSON(
                                    v.getMatricula()
                                    + " - "
                                    + v.getMarca()
                                    + " "
                                    + v.getNombre_modelo()
                            )
                    )
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

        try {

            Vehiculo v =
                    obtenerDatos(request);

            boolean resultado =
                    dao.insertar(v);

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


    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Vehiculo v =
                    obtenerDatos(request);

            v.setId_vehiculo(
                    Integer.parseInt(
                            request.getParameter(
                                    "id_vehiculo"
                            )
                    )
            );

            boolean resultado =
                    dao.actualizar(v);

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

    private void borrar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
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

    private void activar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
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


    // OBTENER DATOS

    private Vehiculo obtenerDatos(
            HttpServletRequest request) {

        Vehiculo v =
                new Vehiculo();

        v.setMatricula(
                request.getParameter(
                        "matricula"
                )
        );

        // La marca se obtiene del modelo.
        v.setMarca("");


        try {

            v.setAnio(
                    Integer.parseInt(
                            request.getParameter(
                                    "anio"
                            )
                    )
            );

        } catch (Exception e) {

            v.setAnio(0);
        }


        try {

            v.setCapacidad(
                    Double.parseDouble(
                            request.getParameter(
                                    "capacidad"
                            )
                    )
            );

        } catch (Exception e) {

            v.setCapacidad(0);
        }


        try {

            v.setId_modelo(
                    Integer.parseInt(
                            request.getParameter(
                                    "id_modelo"
                            )
                    )
            );

        } catch (Exception e) {

            v.setId_modelo(0);
        }


        /*
         * Si se está modificando, conservamos el estado
         * enviado desde el formulario.
         *
         * Si no existe estado, el DAO utilizará DISPONIBLE
         * para los nuevos registros.
         */

        String estado =
                request.getParameter("estado");

        if (estado == null ||
                estado.trim().isEmpty()) {

            estado = "DISPONIBLE";
        }

        v.setEstado(
                estado
        );

        return v;
    }


    // RESPUESTA JSON

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

        generarReporte(
                request,
                response,
                false,
                0
        );
    }


    // REPORTE INDIVIDUAL

    private void generarReporteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int idVehiculo =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            generarReporte(
                    request,
                    response,
                    true,
                    idVehiculo
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de vehículo inválido."
            );
        }
    }


    // GENERAR REPORTE

    private void generarReporte(
            HttpServletRequest request,
            HttpServletResponse response,
            boolean individual,
            int idVehiculo)
            throws IOException {

        ConnectionWrapper wrapper = null;

        try {

            String rutaJRXML;

            if (individual) {

                rutaJRXML =
                        getServletContext()
                                .getRealPath(
                                        "/WEB-INF/reportes/vehiculo_individual.jrxml"
                                );

            } else {

                rutaJRXML =
                        getServletContext()
                                .getRealPath(
                                        "/WEB-INF/reportes/vehiculos_listado.jrxml"
                                );
            }


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


            if (individual) {

                parametros.put(
                        "idVehiculo",
                        idVehiculo
                );
            }


            Conexion conexion =
                    new Conexion();

            wrapper =
                    new ConnectionWrapper(
                            conexion.getConexion()
                    );


            JasperPrint print =
                    JasperFillManager.fillReport(
                            reporte,
                            parametros,
                            wrapper.connection
                    );


            response.setContentType(
                    "application/pdf"
            );


            response.setHeader(
                    "Content-Disposition",
                    "inline; filename="
                    + (
                        individual
                        ? "vehiculo_" + idVehiculo
                        : "vehiculos_listado"
                    )
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
                    "No se pudo generar el reporte de vehículos."
            );

        } finally {

            if (wrapper != null) {
                wrapper.close();
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
                    session.getAttribute("usuario");

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


    // WRAPPER DE CONEXIÓN

    private static class ConnectionWrapper {

        private final java.sql.Connection connection;

        ConnectionWrapper(
                java.sql.Connection connection) {

            this.connection =
                    connection;
        }

        void close() {

            if (connection != null) {

                try {

                    connection.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }
}