package com.controller;

import com.dao.PendienteDAO;
import com.model.Pendiente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * PENDIENTE CONTROLLER
 *
 * Módulo de consulta de facturas:
 *
 * - Pendientes
 * - Vencidas
 * - Pagadas
 *
 * No realiza ABM.
 *
 * Los datos se calculan a partir de:
 *
 * FACTURA + PAGO + CLIENTES
 *
 */
@WebServlet(
        name = "PendienteController",
        urlPatterns = {"/PendienteController"}
)
public class PendienteController extends HttpServlet {


    private final PendienteDAO pendienteDAO =
            new PendienteDAO();


    // DO GET

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        String accion =
                request.getParameter("accion");


        if (
                accion == null
                || accion.trim().isEmpty()
        ) {

            accion = "listar";
        }


        try {

            switch (accion) {


                // LISTAR

                case "listar":

                    listar(
                            request,
                            response
                    );

                    break;


                // BUSCAR

                case "buscar":

                    buscar(
                            request,
                            response
                    );

                    break;


                // TOTALES

                case "totales":

                    totales(
                            request,
                            response
                    );

                    break;


                // REPORTE GENERAL

                case "reporteGeneral":

                    reporteGeneral(
                            request,
                            response
                    );

                    break;


                // ACCIÓN NO RECONOCIDA

                default:

                    enviarError(
                            response,
                            "Acción no reconocida: "
                            + accion
                    );

                    break;
            }


        } catch (Exception e) {


            e.printStackTrace();


            enviarError(
                    response,
                    obtenerMensajeError(e)
            );
        }
    }


    // DO POST

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        /*
         * Pendientes es un módulo de consulta.
         *
         * No se realizan operaciones de:
         *
         * - insertar
         * - modificar
         * - eliminar
         *
         */


        enviarError(
                response,
                "El módulo Pendientes es únicamente de consulta."
        );
    }


    // LISTAR

    private void listar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        try {


            List<Pendiente> lista =
                    pendienteDAO.listar();


            BigDecimal totalFacturado =
                    pendienteDAO.totalFacturado();


            BigDecimal totalPagado =
                    pendienteDAO.totalPagado();


            BigDecimal totalSaldoPendiente =
                    pendienteDAO.totalSaldoPendiente();


            /*
             * IMPORTANTE:
             *
             * Si la lista está vacía no significa
             * necesariamente que exista un error.
             *
             * Puede simplemente no haber registros.
             *
             * Por eso se devuelve success=true.
             */


            StringBuilder json =
                    new StringBuilder();


            json.append("{");


            json.append(
                    "\"success\":true,"
            );


            json.append(
                    "\"totalFacturado\":"
            );

            json.append(
                    numeroJson(
                            totalFacturado
                    )
            );

            json.append(",");


            json.append(
                    "\"totalPagado\":"
            );

            json.append(
                    numeroJson(
                            totalPagado
                    )
            );

            json.append(",");


            json.append(
                    "\"totalSaldoPendiente\":"
            );

            json.append(
                    numeroJson(
                            totalSaldoPendiente
                    )
            );

            json.append(",");


            json.append(
                    "\"data\":["
            );


            for (
                    int i = 0;
                    i < lista.size();
                    i++
            ) {


                if (i > 0) {

                    json.append(",");
                }


                json.append(
                        convertirJson(
                                lista.get(i)
                        )
                );
            }


            json.append("]");


            json.append("}");


            enviarJson(
                    response,
                    json.toString()
            );


        } catch (Exception e) {


            e.printStackTrace();


            throw new Exception(
                    "Error al listar pendientes: "
                    + obtenerMensajeError(e),
                    e
            );
        }
    }


    // BUSCAR POR ID DE FACTURA

    private void buscar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        String idTexto =
                request.getParameter(
                        "idFactura"
                );


        if (
                idTexto == null
                || idTexto.trim().isEmpty()
        ) {


            enviarError(
                    response,
                    "No se especificó el ID de la factura."
            );


            return;
        }


        int idFactura;


        try {


            idFactura =
                    Integer.parseInt(
                            idTexto.trim()
                    );


        } catch (NumberFormatException e) {


            enviarError(
                    response,
                    "El ID de la factura no es válido."
            );


            return;
        }


        try {


            Pendiente pendiente =
                    pendienteDAO.buscarPorIdFactura(
                            idFactura
                    );


            if (pendiente == null) {


                enviarError(
                        response,
                        "No se encontró la factura solicitada."
                );


                return;
            }


            StringBuilder json =
                    new StringBuilder();


            json.append("{");


            json.append(
                    "\"success\":true,"
            );


            json.append(
                    "\"data\":"
            );


            json.append(
                    convertirJson(
                            pendiente
                    )
            );


            json.append("}");


            enviarJson(
                    response,
                    json.toString()
            );


        } catch (Exception e) {


            e.printStackTrace();


            throw new Exception(
                    "Error al buscar la factura: "
                    + obtenerMensajeError(e),
                    e
            );
        }
    }


    // TOTALES

    private void totales(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        try {


            BigDecimal totalFacturado =
                    pendienteDAO.totalFacturado();


            BigDecimal totalPagado =
                    pendienteDAO.totalPagado();


            BigDecimal totalSaldoPendiente =
                    pendienteDAO.totalSaldoPendiente();


            StringBuilder json =
                    new StringBuilder();


            json.append("{");


            json.append(
                    "\"success\":true,"
            );


            json.append(
                    "\"totalFacturado\":"
            );

            json.append(
                    numeroJson(
                            totalFacturado
                    )
            );

            json.append(",");


            json.append(
                    "\"totalPagado\":"
            );

            json.append(
                    numeroJson(
                            totalPagado
                    )
            );

            json.append(",");


            json.append(
                    "\"totalSaldoPendiente\":"
            );

            json.append(
                    numeroJson(
                            totalSaldoPendiente
                    )
            );


            json.append("}");


            enviarJson(
                    response,
                    json.toString()
            );


        } catch (Exception e) {


            e.printStackTrace();


            throw new Exception(
                    "Error al obtener los totales: "
                    + obtenerMensajeError(e),
                    e
            );
        }
    }


    // REPORTE GENERAL

    private void reporteGeneral(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        List<Pendiente> lista =
                pendienteDAO.listar();


        /*
         * DATOS PARA JASPER
         */

        List<Map<String, ?>> datos =
                new ArrayList<>();


        for (
                Pendiente pendiente :
                lista
        ) {


            Map<String, Object> fila =
                    new HashMap<>();


            // FACTURA

            fila.put(
                    "id_factura",
                    pendiente.getIdFactura()
            );


            fila.put(
                    "numero_factura",
                    pendiente.getNumeroFactura()
            );


            fila.put(
                    "fecha_emision",
                    pendiente.getFechaEmision()
            );


            fila.put(
                    "condicion_venta",
                    pendiente.getCondicionVenta()
            );


            fila.put(
                    "periodo",
                    pendiente.getPeriodo()
            );


            fila.put(
                    "plazo_credito",
                    pendiente.getPlazoCredito()
            );


            fila.put(
                    "fecha_vencimiento",
                    pendiente.getFechaVencimiento()
            );


            fila.put(
                    "estado_factura",
                    pendiente.getEstadoFactura()
            );


            // CLIENTE

            fila.put(
                    "id_cliente",
                    pendiente.getIdCliente()
            );


            fila.put(
                    "nombre_cliente",
                    pendiente.getNombreCliente()
            );


            fila.put(
                    "apellido_cliente",
                    pendiente.getApellidoCliente()
            );


            fila.put(
                    "cedula_cliente",
                    pendiente.getCedulaCliente()
            );


            // IMPORTES

            fila.put(
                    "total_factura",
                    pendiente.getTotalFactura()
            );


            fila.put(
                    "total_pagado",
                    pendiente.getTotalPagado()
            );


            fila.put(
                    "saldo_pendiente",
                    pendiente.getSaldoPendiente()
            );


            // ESTADO

            fila.put(
                    "estado_pendiente",
                    pendiente.getEstadoPendiente()
            );


            fila.put(
                    "dias_vencido",
                    pendiente.getDiasVencido()
            );


            datos.add(
                    fila
            );
        }


        /*
         * PARÁMETROS
         */

        Map<String, Object> parametros =
                new HashMap<>();


        parametros.put(
                "usuarioImpresion",
                obtenerUsuario(request)
        );


        parametros.put(
                "USUARIO_IMPRESION",
                obtenerUsuario(request)
        );


        /*
         * LOGO
         */

        InputStream logo =
                getServletContext()
                        .getResourceAsStream(
                                "/WEB-INF/reportes/logo.png"
                        );


        if (logo != null) {


            parametros.put(
                    "LOGO",
                    logo
            );
        }


        /*
         * JRXML
         */

        InputStream jrxml =
                getServletContext()
                        .getResourceAsStream(
                                "/WEB-INF/reportes/pendientes_listado.jrxml"
                        );


        if (jrxml == null) {


            throw new Exception(
                    "No se encontró el archivo "
                    + "pendientes_listado.jrxml."
            );
        }


        /*
         * COMPILAR
         */

        JasperReport reporte =
                JasperCompileManager.compileReport(
                        jrxml
                );


        /*
         * DATASOURCE
         */

        JRMapCollectionDataSource dataSource =
                new JRMapCollectionDataSource(
                        datos
                );


        /*
         * GENERAR PDF
         */

        byte[] pdf =
                JasperExportManager.exportReportToPdf(
                        JasperFillManager.fillReport(
                                reporte,
                                parametros,
                                dataSource
                        )
                );


        /*
         * RESPUESTA
         */

        response.setContentType(
                "application/pdf"
        );


        response.setCharacterEncoding(
                "UTF-8"
        );


        response.setHeader(
                "Content-Disposition",
                "inline; filename=\"pendientes_general.pdf\""
        );


        response.setContentLength(
                pdf.length
        );


        response.getOutputStream().write(
                pdf
        );


        response.getOutputStream().flush();


        /*
         * CERRAR RECURSOS
         */

        try {


            if (logo != null) {

                logo.close();
            }


            if (jrxml != null) {

                jrxml.close();
            }


        } catch (IOException e) {


            e.printStackTrace();
        }
    }


    // CONVERTIR PENDIENTE A JSON

    private String convertirJson(
            Pendiente p) {


        StringBuilder json =
                new StringBuilder();


        json.append("{");


        // FACTURA

        json.append(
                "\"idFactura\":"
        );

        json.append(
                p.getIdFactura()
        );

        json.append(",");


        json.append(
                "\"numeroFactura\":"
        );

        json.append(
                textoJson(
                        p.getNumeroFactura()
                )
        );

        json.append(",");


        json.append(
                "\"fechaEmision\":"
        );

        json.append(
                fechaJson(
                        p.getFechaEmision()
                )
        );

        json.append(",");


        json.append(
                "\"condicionVenta\":"
        );

        json.append(
                textoJson(
                        p.getCondicionVenta()
                )
        );

        json.append(",");


        json.append(
                "\"periodo\":"
        );

        json.append(
                textoJson(
                        p.getPeriodo()
                )
        );

        json.append(",");


        json.append(
                "\"plazoCredito\":"
        );

        json.append(
                p.getPlazoCredito()
        );

        json.append(",");


        json.append(
                "\"fechaVencimiento\":"
        );

        json.append(
                fechaJson(
                        p.getFechaVencimiento()
                )
        );

        json.append(",");


        // CLIENTE

        json.append(
                "\"idCliente\":"
        );

        json.append(
                p.getIdCliente()
        );

        json.append(",");


        json.append(
                "\"nombreCliente\":"
        );

        json.append(
                textoJson(
                        p.getNombreCliente()
                )
        );

        json.append(",");


        json.append(
                "\"apellidoCliente\":"
        );

        json.append(
                textoJson(
                        p.getApellidoCliente()
                )
        );

        json.append(",");


        json.append(
                "\"cedulaCliente\":"
        );

        json.append(
                textoJson(
                        p.getCedulaCliente()
                )
        );

        json.append(",");


        json.append(
                "\"direccionCliente\":"
        );

        json.append(
                textoJson(
                        p.getDireccionCliente()
                )
        );

        json.append(",");


        // IMPORTES

        json.append(
                "\"totalFactura\":"
        );

        json.append(
                numeroJson(
                        p.getTotalFactura()
                )
        );

        json.append(",");


        json.append(
                "\"totalPagado\":"
        );

        json.append(
                numeroJson(
                        p.getTotalPagado()
                )
        );

        json.append(",");


        json.append(
                "\"saldoPendiente\":"
        );

        json.append(
                numeroJson(
                        p.getSaldoPendiente()
                )
        );

        json.append(",");


        // ESTADOS

        json.append(
                "\"estadoFactura\":"
        );

        json.append(
                textoJson(
                        p.getEstadoFactura()
                )
        );

        json.append(",");


        json.append(
                "\"estadoPendiente\":"
        );

        json.append(
                textoJson(
                        p.getEstadoPendiente()
                )
        );

        json.append(",");


        json.append(
                "\"diasVencido\":"
        );

        json.append(
                p.getDiasVencido()
        );


        json.append("}");


        return json.toString();
    }


    // TEXTO JSON

    private String textoJson(
            String texto) {


        if (texto == null) {

            return "null";
        }


        String valor =
                texto
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
                                "\\r"
                        )
                        .replace(
                                "\n",
                                "\\n"
                        );


        return "\"" + valor + "\"";
    }


    // FECHA JSON

    private String fechaJson(
            java.sql.Date fecha) {


        if (fecha == null) {

            return "null";
        }


        return textoJson(
                fecha.toString()
        );
    }


    // NÚMERO JSON

    private String numeroJson(
            BigDecimal numero) {


        if (numero == null) {

            return "0";
        }


        return numero
                .stripTrailingZeros()
                .toPlainString();
    }


    // OBTENER USUARIO

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
                    usuario != null
                    && !usuario
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


    // ENVIAR JSON

    private void enviarJson(
            HttpServletResponse response,
            String json)
            throws IOException {


        response.setContentType(
                "application/json"
        );


        response.setCharacterEncoding(
                "UTF-8"
        );


        PrintWriter out =
                response.getWriter();


        out.print(json);

        out.flush();
    }


    // ENVIAR ERROR

    private void enviarError(
            HttpServletResponse response,
            String mensaje)
            throws IOException {


        response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        );


        response.setContentType(
                "application/json"
        );


        response.setCharacterEncoding(
                "UTF-8"
        );


        String mensajeSeguro =
                mensaje == null
                        ? "Error desconocido."
                        : mensaje;


        StringBuilder json =
                new StringBuilder();


        json.append("{");


        json.append(
                "\"success\":false,"
        );


        json.append(
                "\"message\":"
        );


        json.append(
                textoJson(
                        mensajeSeguro
                )
        );


        json.append("}");


        PrintWriter out =
                response.getWriter();


        out.print(
                json.toString()
        );


        out.flush();
    }


    // OBTENER MENSAJE REAL DEL ERROR

    private String obtenerMensajeError(
            Exception e) {


        if (e == null) {

            return "Error desconocido.";
        }


        Throwable causa =
                e;


        /*
         * Buscamos la causa más profunda
         * para obtener el mensaje real.
         */

        while (
                causa.getCause() != null
        ) {

            causa =
                    causa.getCause();
        }


        String mensaje =
                causa.getMessage();


        if (
                mensaje == null
                || mensaje.trim().isEmpty()
        ) {

            mensaje =
                    e.getMessage();
        }


        if (
                mensaje == null
                || mensaje.trim().isEmpty()
        ) {

            mensaje =
                    e.getClass()
                            .getSimpleName();
        }


        return mensaje;
    }
}