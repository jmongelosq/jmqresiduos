package com.controller;

import com.dao.PagoDAO;
import com.model.Pago;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@WebServlet(
        name = "PagoController",
        urlPatterns = {"/PagoController"}
)
public class PagoController extends HttpServlet {

    private PagoDAO dao;

    @Override
    public void init() {
        dao = new PagoDAO();
    }


    // GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");


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


        // LISTADO

        listar(request, response);
    }


    // POST

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {

            if ("listar".equals(accion)) {

                listar(request, response);

            } else if ("buscarFactura".equals(accion)) {

                buscarFactura(request, response);

            } else if ("insertar".equals(accion)) {

                insertar(request, response);

            } else if ("actualizar".equals(accion)) {

                actualizar(request, response);

            } else if ("anular".equals(accion)) {

                anular(request, response);

            } else if ("activar".equals(accion)) {

                activar(request, response);

            } else {

                listar(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error interno del servidor"
            );
        }
    }


    // LISTAR PAGOS

    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Pago> pagos = dao.listar();

        request.setAttribute(
                "pagos",
                pagos
        );

        request.getRequestDispatcher(
                "/pagos.jsp"
        ).forward(request, response);
    }


    // BUSCAR FACTURA POR NÚMERO

    private void buscarFactura(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String numeroFactura =
                request.getParameter("numeroFactura");


        response.setContentType(
                "application/json;charset=UTF-8"
        );


        if (numeroFactura == null
                || numeroFactura.isBlank()) {

            response.getWriter().write(
                    "{\"ok\":false,\"mensaje\":\"Debe ingresar el número de factura.\"}"
            );

            return;
        }


        try {

            Pago factura =
                    dao.buscarFacturaPorNumero(
                            numeroFactura.trim()
                    );


            if (factura == null) {

                response.getWriter().write(
                        "{\"ok\":false,\"mensaje\":\"No se encontró la factura indicada.\"}"
                );

                return;
            }


            StringBuilder json =
                    new StringBuilder();


            json.append("{");

            json.append("\"ok\":true");


            json.append(",\"idFactura\":")
                    .append(factura.getIdFactura());


            json.append(",\"numeroFactura\":\"")
                    .append(
                            safe(
                                    factura.getNumeroFactura()
                            )
                    )
                    .append("\"");


            json.append(",\"totalFactura\":")
                    .append(
                            factura.getTotalFactura() != null
                                    ? factura.getTotalFactura().toPlainString()
                                    : "0"
                    );


            json.append(",\"periodoFactura\":\"")
                    .append(
                            safe(
                                    factura.getPeriodoFactura()
                            )
                    )
                    .append("\"");


            json.append(",\"estadoFactura\":\"")
                    .append(
                            safe(
                                    factura.getEstadoFactura()
                            )
                    )
                    .append("\"");


            json.append(",\"nombreCliente\":\"")
                    .append(
                            safe(
                                    factura.getNombreCliente()
                            )
                    )
                    .append("\"");


            json.append(",\"apellidoCliente\":\"")
                    .append(
                            safe(
                                    factura.getApellidoCliente()
                            )
                    )
                    .append("\"");


            json.append(",\"cedulaCliente\":\"")
                    .append(
                            safe(
                                    factura.getCedulaCliente()
                            )
                    )
                    .append("\"");


            json.append(",\"nombreTarifa\":\"")
                    .append(
                            safe(
                                    factura.getNombreTarifa()
                            )
                    )
                    .append("\"");


            json.append("");


            json.append("}");


            response.getWriter().write(
                    json.toString()
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().write(
                    "{\"ok\":false,\"mensaje\":\"Error al buscar la factura.\"}"
            );
        }
    }


    // INSERTAR

    private void insertar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Pago p =
                construirPago(request);


        // VALIDAR FACTURA

        if (p.getIdFactura() <= 0) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Debe seleccionar una factura válida."
            );

            return;
        }


        if (!dao.existeFactura(
                p.getIdFactura())) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "La factura indicada no existe."
            );

            return;
        }


        if (!dao.facturaPuedePagarse(
                p.getIdFactura())) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "La factura no puede recibir pagos porque se encuentra anulada."
            );

            return;
        }


        // VALIDAR MONTO

        if (p.getMonto() == null
                || p.getMonto().compareTo(
                        BigDecimal.ZERO
                ) <= 0) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El monto del pago debe ser mayor a cero."
            );

            return;
        }


        // GENERAR NÚMERO DE RECIBO AUTOMÁTICO

        HttpSession session =
                request.getSession(false);


        String rol = null;


        if (session != null) {

            Object atributoRol =
                    session.getAttribute("rol");


            if (atributoRol != null) {

                rol =
                        atributoRol.toString().trim();
            }
        }


        if (rol == null
                || rol.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "No se pudo determinar el rol del usuario que registra el pago."
            );

            return;
        }


        String numeroRecibo =
                dao.generarNumeroRecibo(
                        rol
                );


        if (numeroRecibo == null
                || numeroRecibo.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se pudo generar el número de recibo."
            );

            return;
        }


        p.setNumeroRecibo(
                numeroRecibo
        );


        // INSERTAR PAGO

        boolean insertado =
                dao.insertar(p);


        if (!insertado) {

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se pudo registrar el pago."
            );

            return;
        }


        volverAPagos(
                request,
                response
        );
    }


    // ACTUALIZAR

    private void actualizar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Pago p =
                construirPago(request);


        String id =
                request.getParameter("idPago");


        if (id == null
                || id.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de pago no válido."
            );

            return;
        }


        int idPago;


        try {

            idPago =
                    Integer.parseInt(id);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de pago no válido."
            );

            return;
        }


        p.setIdPago(idPago);


        // VALIDAR FACTURA

        if (p.getIdFactura() <= 0) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Debe seleccionar una factura válida."
            );

            return;
        }


        if (!dao.existeFactura(
                p.getIdFactura())) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "La factura indicada no existe."
            );

            return;
        }


        if (!dao.facturaPuedePagarse(
                p.getIdFactura())) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "La factura no puede recibir pagos porque se encuentra anulada."
            );

            return;
        }


        // VALIDAR MONTO

        if (p.getMonto() == null
                || p.getMonto().compareTo(
                        BigDecimal.ZERO
                ) <= 0) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El monto del pago debe ser mayor a cero."
            );

            return;
        }


        dao.actualizar(p);


        volverAPagos(
                request,
                response
        );
    }


    // ANULAR

    private void anular(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String id =
                request.getParameter("idPago");


        if (id == null
                || id.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de pago no válido."
            );

            return;
        }


        int idPago;


        try {

            idPago =
                    Integer.parseInt(id);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de pago no válido."
            );

            return;
        }


        dao.anular(idPago);


        volverAPagos(
                request,
                response
        );
    }


    // ACTIVAR

    private void activar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String id =
                request.getParameter("idPago");


        if (id == null
                || id.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de pago no válido."
            );

            return;
        }


        int idPago;


        try {

            idPago =
                    Integer.parseInt(id);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de pago no válido."
            );

            return;
        }


        dao.activar(idPago);


        volverAPagos(
                request,
                response
        );
    }


    // VOLVER AL DASHBOARD - PAGOS

    private void volverAPagos(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/dashboard.jsp?modulo=pagos"
        );
    }


    // CONSTRUIR PAGO

    private Pago construirPago(
            HttpServletRequest request) {

        Pago p =
                new Pago();


        // ID FACTURA

        String idFactura =
                request.getParameter("idFactura");


        if (idFactura != null
                && !idFactura.isBlank()) {

            p.setIdFactura(
                    Integer.parseInt(
                            idFactura
                    )
            );
        }


        // FECHA

        String fecha =
                request.getParameter("fechaPago");


        if (fecha != null
                && !fecha.isBlank()) {

            p.setFechaPago(
                    Date.valueOf(fecha)
            );

        } else {

            p.setFechaPago(
                    new Date(
                            System.currentTimeMillis()
                    )
            );
        }


        // PERÍODO

        p.setPeriodo(
                request.getParameter(
                        "periodo"
                )
        );


        // MONTO

        String monto =
                request.getParameter("monto");


        if (monto != null
                && !monto.isBlank()) {

            p.setMonto(
                    new BigDecimal(
                            monto.replace(",", ".")
                    )
            );

        } else {

            p.setMonto(
                    BigDecimal.ZERO
            );
        }


        // MÉTODO DE PAGO

        p.setMetodoPago(
                request.getParameter(
                        "metodoPago"
                )
        );


        // NÚMERO DE RECIBO
        //
        // Para INSERTAR:
        // se genera automáticamente en insertar().
        //
        // Para ACTUALIZAR:
        // se conserva el número existente enviado
        // desde el formulario.

        p.setNumeroRecibo(
                request.getParameter(
                        "numeroRecibo"
                )
        );


        // OBSERVACIONES

        p.setObservaciones(
                request.getParameter(
                        "observaciones"
                )
        );


        // ESTADO

        p.setEstado(
                "PAGADO"
        );


        return p;
    }


    // REPORTE GENERAL

    private void reporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            List<Pago> lista =
                    dao.listar();


            InputStream archivo =
                    getServletContext()
                            .getResourceAsStream(
                                    "/WEB-INF/reportes/pagos_listado.jrxml"
                            );


            if (archivo == null) {

                throw new IOException(
                        "No se encontró pagos_listado.jrxml"
                );
            }


            JasperReport reporte =
                    JasperCompileManager
                            .compileReport(
                                    archivo
                            );


            JRBeanCollectionDataSource fuente =
                    new JRBeanCollectionDataSource(
                            lista
                    );


            Map<String, Object> parametros =
                    new HashMap<>();


            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioImpresion(
                            request
                    )
            );


            JasperPrint print =
                    JasperFillManager.fillReport(
                            reporte,
                            parametros,
                            fuente
                    );


            response.setContentType(
                    "application/pdf"
            );


            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=pagos_listado.pdf"
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
                    "Error al generar reporte general de pagos"
            );
        }
    }


    // REPORTE INDIVIDUAL

    private void reporteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String id =
                    request.getParameter(
                            "idPago"
                    );


            if (id == null
                    || id.isBlank()) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "ID de pago no válido"
                );

                return;
            }


            int idPago =
                    Integer.parseInt(id);


            Pago pago =
                    dao.buscarPorId(
                            idPago
                    );


            if (pago == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Pago no encontrado"
                );

                return;
            }


            InputStream archivo =
                    getServletContext()
                            .getResourceAsStream(
                                    "/WEB-INF/reportes/pago_individual.jrxml"
                            );


            if (archivo == null) {

                throw new IOException(
                        "No se encontró pago_individual.jrxml"
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
                    "idPago",
                    idPago
            );


            parametros.put(
                    "usuarioImpresion",
                    obtenerUsuarioImpresion(
                            request
                    )
            );


            JRBeanCollectionDataSource fuente =
                    new JRBeanCollectionDataSource(
                            Collections.singletonList(
                                    pago
                            )
                    );


            JasperPrint print =
                    JasperFillManager.fillReport(
                            reporte,
                            parametros,
                            fuente
                    );


            response.setContentType(
                    "application/pdf"
            );


            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=pago_"
                    + idPago
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

        } catch (NumberFormatException e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de pago no válido"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al generar reporte individual de pago"
            );
        }
    }


    // USUARIO QUE GENERA EL REPORTE

    private String obtenerUsuarioImpresion(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);


        if (session != null) {

            Object usuario =
                    session.getAttribute(
                            "usuario"
                    );


            if (usuario != null
                    && !usuario.toString().isBlank()) {

                return usuario.toString();
            }
        }


        return "Sistema";
    }


    // SEGURIDAD JSON

    private String safe(
            String texto) {

        if (texto == null) {
            return "";
        }


        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", " ");
    }
}