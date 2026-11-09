package com.controller;

import com.config.Conexion;
import com.dao.ServicioDAO;
import com.model.Servicio;

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
import java.sql.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;


@WebServlet(
        name = "ServicioController",
        urlPatterns = {"/ServicioController"}
)
public class ServicioController extends HttpServlet {

    private final ServicioDAO dao = new ServicioDAO();


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if (accion == null || accion.trim().isEmpty()) {
            accion = request.getParameter("action");
        }

        // REPORTE GENERAL

        if ("reporteGeneral".equals(accion)) {
            reporteGeneral(request, response);
            return;
        }

        // REPORTE INDIVIDUAL

        if ("reporteIndividual".equals(accion)) {
            reporteIndividual(request, response);
            return;
        }

        // VISTA PRINCIPAL

        request.getRequestDispatcher(
                "/WEB-INF/vistas/servicios.jsp"
        ).forward(request, response);
    }


    // POST

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if (accion == null || accion.trim().isEmpty()) {
            accion = request.getParameter("action");
        }

        if (accion == null || accion.trim().isEmpty()) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            response.setContentType(
                    "text/plain;charset=UTF-8"
            );

            response.getWriter().print(
                    "Acción no especificada."
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

            case "buscarClientes":
                buscarClientes(request, response);
                break;

            case "listarBarrios":
                listarBarrios(response);
                break;

            case "listarTarifas":
                listarTarifas(response);
                break;

            case "buscar":
                buscar(request, response);
                break;

            case "insertar":
                insertar(request, response);
                break;

            case "actualizar":
                actualizar(request, response);
                break;

            case "Borrar":
            case "eliminar":
                eliminar(request, response);
                break;

            case "suspender":
                suspender(request, response);
                break;

            case "activar":
                activar(request, response);
                break;

            default:

                response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST
                );

                response.setContentType(
                        "text/plain;charset=UTF-8"
                );

                response.getWriter().print(
                        "Acción no válida: " + accion
                );

                break;
        }
    }


    // LISTAR SERVICIOS

    private void listar(
            HttpServletResponse response)
            throws IOException {

        try {

            List<Servicio> lista = dao.listar();

            response.setContentType(
                    "application/json;charset=UTF-8"
            );

            StringBuilder json = new StringBuilder();

            json.append("[");

            for (int i = 0; i < lista.size(); i++) {

                if (i > 0) {
                    json.append(",");
                }

                json.append(
                        servicioJson(lista.get(i))
                );
            }

            json.append("]");

            response.getWriter().print(
                    json.toString()
            );

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    "Error al listar servicios: "
                    + e.getMessage()
            );
        }
    }


    // LISTAR CLIENTES ACTIVOS

    private void listarOptions(
            HttpServletResponse response)
            throws IOException {

        try {

            List<Map<String, Object>> lista =
                    dao.buscarClientes("", 0);

            response.setContentType(
                    "application/json;charset=UTF-8"
            );

            StringBuilder json = new StringBuilder();

            json.append("[");

            for (int i = 0; i < lista.size(); i++) {

                if (i > 0) {
                    json.append(",");
                }

                Map<String, Object> c = lista.get(i);

                json.append("{");

                json.append("\"id_cliente\":");
                json.append(
                        valorNumero(
                                c.get("id_cliente")
                        )
                );

                json.append(",");

                json.append("\"nombre\":\"");
                json.append(
                        safeJson(
                                valorTexto(
                                        c.get("nombre")
                                )
                        )
                );
                json.append("\"");

                json.append(",");

                json.append("\"cedula\":\"");
                json.append(
                        safeJson(
                                valorTexto(
                                        c.get("cedula")
                                )
                        )
                );
                json.append("\"");

                json.append(",");

                json.append("\"ruc\":\"");
                json.append(
                        safeJson(
                                valorTexto(
                                        c.get("ruc")
                                )
                        )
                );
                json.append("\"");

                json.append("}");
            }

            json.append("]");

            response.getWriter().print(
                    json.toString()
            );

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    "Error al cargar clientes: "
                    + e.getMessage()
            );
        }
    }


    // BUSCAR CLIENTES POR NOMBRE, CÉDULA O RUC

    private void buscarClientes(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String termino =
                    request.getParameter("termino");

            String idTexto =
                    request.getParameter("id_cliente");

            int idCliente = 0;

            if (idTexto != null
                    && !idTexto.trim().isEmpty()) {

                idCliente =
                        Integer.parseInt(
                                idTexto.trim()
                        );
            }

            if (idCliente <= 0
                    && (termino == null
                    || termino.trim().length() < 2)) {

                response.setContentType(
                        "application/json;charset=UTF-8"
                );

                response.getWriter().print("[]");

                return;
            }

            List<Map<String, Object>> lista =
                    dao.buscarClientes(
                            termino,
                            idCliente
                    );

            response.setContentType(
                    "application/json;charset=UTF-8"
            );

            StringBuilder json = new StringBuilder();

            json.append("[");

            for (int i = 0; i < lista.size(); i++) {

                if (i > 0) {
                    json.append(",");
                }

                Map<String, Object> c = lista.get(i);

                json.append("{");

                json.append("\"id_cliente\":");
                json.append(
                        valorNumero(
                                c.get("id_cliente")
                        )
                );

                json.append(",");

                json.append("\"nombre\":\"");
                json.append(
                        safeJson(
                                valorTexto(
                                        c.get("nombre")
                                )
                        )
                );
                json.append("\"");

                json.append(",");

                json.append("\"cedula\":\"");
                json.append(
                        safeJson(
                                valorTexto(
                                        c.get("cedula")
                                )
                        )
                );
                json.append("\"");

                json.append(",");

                json.append("\"ruc\":\"");
                json.append(
                        safeJson(
                                valorTexto(
                                        c.get("ruc")
                                )
                        )
                );
                json.append("\"");

                json.append("}");
            }

            json.append("]");

            response.getWriter().print(
                    json.toString()
            );

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    "Error al buscar clientes: "
                    + e.getMessage()
            );
        }
    }


    // LISTAR BARRIOS

    private void listarBarrios(
            HttpServletResponse response)
            throws IOException {

        try {

            List<Map<String, Object>> lista =
                    dao.listarBarrios();

            response.setContentType(
                    "application/json;charset=UTF-8"
            );

            StringBuilder json = new StringBuilder();

            json.append("[");

            for (int i = 0; i < lista.size(); i++) {

                if (i > 0) {
                    json.append(",");
                }

                Map<String, Object> b = lista.get(i);

                json.append("{");

                json.append("\"id_barrio\":");
                json.append(
                        valorNumero(
                                b.get("id_barrio")
                        )
                );

                json.append(",");

                json.append("\"nombre\":\"");
                json.append(
                        safeJson(
                                valorTexto(
                                        b.get("nombre")
                                )
                        )
                );
                json.append("\"");

                json.append("}");
            }

            json.append("]");

            response.getWriter().print(
                    json.toString()
            );

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    "Error al cargar barrios: "
                    + e.getMessage()
            );
        }
    }


    // LISTAR TARIFAS

    private void listarTarifas(
            HttpServletResponse response)
            throws IOException {

        try {

            List<Map<String, Object>> lista =
                    dao.listarTarifas();

            response.setContentType(
                    "application/json;charset=UTF-8"
            );

            StringBuilder json = new StringBuilder();

            json.append("[");

            for (int i = 0; i < lista.size(); i++) {

                if (i > 0) {
                    json.append(",");
                }

                Map<String, Object> t = lista.get(i);

                json.append("{");

                json.append("\"id_tarifa\":");
                json.append(
                        valorNumero(
                                t.get("id_tarifa")
                        )
                );

                json.append(",");

                json.append("\"nombre\":\"");
                json.append(
                        safeJson(
                                valorTexto(
                                        t.get("nombre")
                                )
                        )
                );
                json.append("\"");

                json.append(",");

                json.append("\"monto\":");
                json.append(
                        valorNumero(
                                t.get("monto")
                        )
                );

                json.append(",");

                json.append("\"periodicidad\":\"");
                json.append(
                        safeJson(
                                valorTexto(
                                        t.get("periodicidad")
                                )
                        )
                );
                json.append("\"");

                json.append("}");
            }

            json.append("]");

            response.getWriter().print(
                    json.toString()
            );

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    "Error al cargar tarifas: "
                    + e.getMessage()
            );
        }
    }


    // BUSCAR SERVICIO POR ID

    private void buscar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String idTexto =
                    request.getParameter("id");

            if (idTexto == null
                    || idTexto.trim().isEmpty()) {

                throw new Exception(
                        "ID no especificado."
                );
            }

            int id =
                    Integer.parseInt(
                            idTexto.trim()
                    );

            Servicio s =
                    dao.buscarPorId(id);

            response.setContentType(
                    "application/json;charset=UTF-8"
            );

            if (s == null) {

                response.getWriter().print("{}");

                return;
            }

            response.getWriter().print(
                    servicioJson(s)
            );

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    "Error al buscar servicio: "
                    + e.getMessage()
            );
        }
    }


    // INSERTAR

    private void insertar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Servicio s =
                    obtenerDatos(request);

            if (!dao.insertar(s)) {

                throw new Exception(
                        "No se pudo registrar el servicio."
                );
            }

            respuestaOK(response);

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    e.getMessage()
            );
        }
    }


    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Servicio s =
                    obtenerDatos(request);

            if (!dao.actualizar(s)) {

                throw new Exception(
                        "No se pudo actualizar el servicio."
                );
            }

            respuestaOK(response);

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    e.getMessage()
            );
        }
    }


    // CANCELAR

    private void eliminar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String idTexto =
                    request.getParameter("id");

            if (idTexto == null
                    || idTexto.trim().isEmpty()) {

                throw new Exception(
                        "No se recibió el ID del servicio."
                );
            }

            int id =
                    Integer.parseInt(
                            idTexto.trim()
                    );

            if (!dao.eliminar(id)) {

                throw new Exception(
                        "No se pudo cancelar el servicio."
                );
            }

            respuestaOK(response);

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    e.getMessage()
            );
        }
    }


    // SUSPENDER

    private void suspender(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String idTexto =
                    request.getParameter("id");

            if (idTexto == null
                    || idTexto.trim().isEmpty()) {

                throw new Exception(
                        "No se recibió el ID del servicio."
                );
            }

            int id =
                    Integer.parseInt(
                            idTexto.trim()
                    );

            if (!dao.suspender(id)) {

                throw new Exception(
                        "No se pudo suspender el servicio."
                );
            }

            respuestaOK(response);

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    e.getMessage()
            );
        }
    }


    // ACTIVAR

    private void activar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String idTexto =
                    request.getParameter("id");

            if (idTexto == null
                    || idTexto.trim().isEmpty()) {

                throw new Exception(
                        "No se recibió el ID del servicio."
                );
            }

            int id =
                    Integer.parseInt(
                            idTexto.trim()
                    );

            if (!dao.activar(id)) {

                throw new Exception(
                        "No se pudo activar el servicio."
                );
            }

            respuestaOK(response);

        } catch (Exception e) {

            e.printStackTrace();

            errorTexto(
                    response,
                    e.getMessage()
            );
        }
    }


    // OBTENER DATOS DEL FORMULARIO

    private Servicio obtenerDatos(
            HttpServletRequest request)
            throws Exception {

        Servicio s =
                new Servicio();


        // ID SERVICIO

        String id =
                request.getParameter(
                        "id_servicio"
                );

        if (id != null
                && !id.trim().isEmpty()) {

            s.setId_servicio(
                    Integer.parseInt(
                            id.trim()
                    )
            );
        }


        // CLIENTE

        String idCliente =
                request.getParameter(
                        "id_cliente"
                );

        if (idCliente == null
                || idCliente.trim().isEmpty()) {

            throw new Exception(
                    "Debe seleccionar un cliente."
            );
        }

        s.setId_cliente(
                Integer.parseInt(
                        idCliente.trim()
                )
        );


        // TARIFA

        String idTarifa =
                request.getParameter(
                        "id_tarifa"
                );

        if (idTarifa == null
                || idTarifa.trim().isEmpty()) {

            throw new Exception(
                    "Debe seleccionar una tarifa."
            );
        }

        s.setId_tarifa(
                Integer.parseInt(
                        idTarifa.trim()
                )
        );


        // TIPO DE SERVICIO

        String tipo =
                request.getParameter(
                        "tipo_servicio"
                );

        if (tipo == null
                || tipo.trim().isEmpty()) {

            throw new Exception(
                    "Debe seleccionar el tipo de servicio."
            );
        }

        s.setTipo_servicio(
                tipo.trim()
        );


        // ESTABLECIMIENTO

        String establecimiento =
                request.getParameter(
                        "nombre_establecimiento"
                );

        if (establecimiento == null
                || establecimiento.trim().isEmpty()) {

            throw new Exception(
                    "Debe ingresar el establecimiento."
            );
        }

        s.setNombre_establecimiento(
                establecimiento.trim()
        );


        // DIRECCIÓN

        String direccion =
                request.getParameter(
                        "direccion_servicio"
                );

        if (direccion == null
                || direccion.trim().isEmpty()) {

            throw new Exception(
                    "Debe ingresar la dirección del servicio."
            );
        }

        s.setDireccion_servicio(
                direccion.trim()
        );


        // BARRIO

        String idBarrio =
                request.getParameter(
                        "id_barrio"
                );

        if (idBarrio == null
                || idBarrio.trim().isEmpty()) {

            throw new Exception(
                    "Debe seleccionar un barrio."
            );
        }

        s.setId_barrio(
                Integer.parseInt(
                        idBarrio.trim()
                )
        );


        // FRECUENCIA

        String frecuencia =
                request.getParameter(
                        "frecuencia"
                );

        if (frecuencia == null
                || frecuencia.trim().isEmpty()) {

            frecuencia = "MENSUAL";
        }

        s.setFrecuencia(
                frecuencia.trim()
        );


        // FECHA INICIO

        String fechaInicio =
                request.getParameter(
                        "fecha_inicio"
                );

        if (fechaInicio == null
                || fechaInicio.trim().isEmpty()) {

            throw new Exception(
                    "Debe ingresar la fecha de inicio."
            );
        }

        s.setFecha_inicio(
                Date.valueOf(
                        fechaInicio.trim()
                )
        );


        // FECHA FIN

        String fechaFin =
                request.getParameter(
                        "fecha_fin"
                );

        if (fechaFin != null
                && !fechaFin.trim().isEmpty()) {

            s.setFecha_fin(
                    Date.valueOf(
                            fechaFin.trim()
                    )
            );

        } else {

            s.setFecha_fin(null);
        }


        // ESTADO

        String estado =
                request.getParameter(
                        "estado"
                );

        if (estado == null
                || estado.trim().isEmpty()) {

            estado = "ACTIVO";
        }

        s.setEstado(
                estado.trim()
        );


        // OBSERVACIONES

        String observaciones =
                request.getParameter(
                        "observaciones"
                );

        if (observaciones != null) {
            observaciones =
                    observaciones.trim();
        }

        s.setObservaciones(
                observaciones
        );


        return s;
    }


    // JSON DE SERVICIO

    private String servicioJson(
            Servicio s) {

        StringBuilder json =
                new StringBuilder();

        json.append("{");

        json.append("\"id_servicio\":");
        json.append(
                s.getId_servicio()
        );

        json.append(",");

        json.append("\"id_cliente\":");
        json.append(
                s.getId_cliente()
        );

        json.append(",");

        json.append("\"nombre_cliente\":\"");
        json.append(
                safeJson(
                        s.getNombre_cliente()
                )
        );
        json.append("\"");

        json.append(",");

        json.append("\"id_tarifa\":");
        json.append(
                s.getId_tarifa()
        );

        json.append(",");

        json.append("\"nombre_tarifa\":\"");
        json.append(
                safeJson(
                        s.getNombre_tarifa()
                )
        );
        json.append("\"");

        json.append(",");

        json.append("\"monto_tarifa\":");
        json.append(
                s.getMonto_tarifa()
        );

        json.append(",");

        json.append("\"tipo_servicio\":\"");
        json.append(
                safeJson(
                        s.getTipo_servicio()
                )
        );
        json.append("\"");

        json.append(",");

        json.append("\"nombre_establecimiento\":\"");
        json.append(
                safeJson(
                        s.getNombre_establecimiento()
                )
        );
        json.append("\"");

        json.append(",");

        json.append("\"direccion_servicio\":\"");
        json.append(
                safeJson(
                        s.getDireccion_servicio()
                )
        );
        json.append("\"");

        json.append(",");

        json.append("\"id_barrio\":");
        json.append(
                s.getId_barrio()
        );

        json.append(",");

        json.append("\"nombre_barrio\":\"");
        json.append(
                safeJson(
                        s.getNombre_barrio()
                )
        );
        json.append("\"");

        json.append(",");

        json.append("\"frecuencia\":\"");
        json.append(
                safeJson(
                        s.getFrecuencia()
                )
        );
        json.append("\"");

        json.append(",");

        json.append("\"fecha_inicio\":\"");

        if (s.getFecha_inicio() != null) {
            json.append(
                    s.getFecha_inicio().toString()
            );
        }

        json.append("\"");

        json.append(",");

        json.append("\"fecha_fin\":\"");

        if (s.getFecha_fin() != null) {
            json.append(
                    s.getFecha_fin().toString()
            );
        }

        json.append("\"");

        json.append(",");

        json.append("\"estado\":\"");
        json.append(
                safeJson(
                        s.getEstado()
                )
        );
        json.append("\"");

        json.append(",");

        json.append("\"observaciones\":\"");
        json.append(
                safeJson(
                        s.getObservaciones()
                )
        );
        json.append("\"");

        json.append("}");

        return json.toString();
    }


    // REPORTE GENERAL

    private void reporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String rutaReporte =
                "/WEB-INF/reportes/servicios_listado.jrxml";

        try {

            InputStream archivo =
                    getServletContext()
                            .getResourceAsStream(
                                    rutaReporte
                            );

            if (archivo == null) {

                throw new Exception(
                        "No se encontró el archivo JRXML: "
                        + rutaReporte
                );
            }

            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(
                                    archivo
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

                if (con == null) {

                    throw new Exception(
                            "No se pudo establecer conexión con PostgreSQL."
                    );
                }

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
                        "inline; filename=servicios_listado.pdf"
                );

                JasperExportManager
                        .exportReportToPdfStream(
                                print,
                                response.getOutputStream()
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    "REPORTE GENERAL DE SERVICIOS",
                    e
            );
        }
    }


    // REPORTE INDIVIDUAL

    private void reporteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String idTexto =
                    request.getParameter("id");

            if (idTexto == null
                    || idTexto.trim().isEmpty()) {

                idTexto =
                        request.getParameter(
                                "idServicio"
                        );
            }

            if (idTexto == null
                    || idTexto.trim().isEmpty()) {

                idTexto =
                        request.getParameter(
                                "id_servicio"
                        );
            }

            if (idTexto == null
                    || idTexto.trim().isEmpty()) {

                throw new Exception(
                        "No se recibió el ID del servicio."
                );
            }

            int idServicio =
                    Integer.parseInt(
                            idTexto.trim()
                    );

            String rutaReporte =
                    "/WEB-INF/reportes/"
                    + "servicio_individual.jrxml";

            InputStream archivo =
                    getServletContext()
                            .getResourceAsStream(
                                    rutaReporte
                            );

            if (archivo == null) {

                throw new Exception(
                        "No se encontró el archivo JRXML: "
                        + rutaReporte
                );
            }

            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(
                                    archivo
                            );

            Map<String, Object> parametros =
                    new HashMap<>();

            parametros.put(
                    "idServicio",
                    idServicio
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

                if (con == null) {

                    throw new Exception(
                            "No se pudo establecer conexión con PostgreSQL."
                    );
                }

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
                        "inline; filename=servicio_"
                        + idServicio
                        + ".pdf"
                );

                JasperExportManager
                        .exportReportToPdfStream(
                                print,
                                response.getOutputStream()
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    response,
                    "REPORTE INDIVIDUAL DE SERVICIO",
                    e
            );
        }
    }


    // USUARIO DE SESIÓN

    private String obtenerUsuarioSesion(
            HttpServletRequest request) {

        try {

            HttpSession session =
                    request.getSession(false);

            if (session == null) {
                return "admin";
            }

            Object usuario =
                    session.getAttribute("usuario");

            if (usuario == null) {
                usuario =
                        session.getAttribute(
                                "username"
                        );
            }

            if (usuario == null) {
                usuario =
                        session.getAttribute(
                                "nombreUsuario"
                        );
            }

            if (usuario == null) {
                return "admin";
            }

            return usuario.toString();

        } catch (Exception e) {

            return "admin";
        }
    }


    // RESPUESTA OK

    private void respuestaOK(
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "text/plain;charset=UTF-8"
        );

        response.getWriter().print("ok");
    }


    // ERROR TEXTO

    private void errorTexto(
            HttpServletResponse response,
            String mensaje)
            throws IOException {

        response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        );

        response.setContentType(
                "text/plain;charset=UTF-8"
        );

        response.getWriter().print(
                mensaje != null
                ? mensaje
                : "Error desconocido."
        );
    }


    // VALOR TEXTO

    private String valorTexto(
            Object valor) {

        return valor == null
                ? ""
                : String.valueOf(valor);
    }


    // VALOR NUMÉRICO

    private String valorNumero(
            Object valor) {

        if (valor == null) {
            return "0";
        }

        return String.valueOf(valor);
    }


    // ESCAPE JSON

    private String safeJson(
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


    // MOSTRAR ERROR DE REPORTE

    private void mostrarError(
            HttpServletResponse response,
            String titulo,
            Exception e)
            throws IOException {

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        PrintWriter out =
                response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println(
                "<title>"
                + escapeHtml(titulo)
                + "</title>"
        );
        out.println("</head>");

        out.println(
                "<body style='font-family:Arial;padding:30px;'>"
        );

        out.println(
                "<h2>"
                + escapeHtml(titulo)
                + "</h2>"
        );

        out.println(
                "<p>"
                + escapeHtml(
                        e.getMessage()
                )
                + "</p>"
        );

        out.println("</body>");
        out.println("</html>");
    }


    // ESCAPE HTML

    private String escapeHtml(
            String texto) {

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
}