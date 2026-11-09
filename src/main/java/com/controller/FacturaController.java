package com.controller;

import com.dao.ClienteDAO;
import com.dao.FacturaDAO;
import com.model.Cliente;
import com.model.DetalleFactura;
import com.model.Factura;
import com.model.Servicio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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
import java.math.RoundingMode;

import java.sql.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig
@WebServlet(
        name = "FacturaController",
        urlPatterns = {"/FacturaController"}
)
public class FacturaController extends HttpServlet {

    private final FacturaDAO facturaDAO = new FacturaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null || accion.trim().isEmpty()) {
            accion = "listar";
        }

        try {

            switch (accion) {

                case "listar":
                    listar(response);
                    break;

                case "clientesActivos":
                    listarClientesActivos(response);
                    break;

                case "buscarClientes":
                    buscarClientes(request, response);
                    break;

                case "tarifasActivas":
                    listarTarifasActivas(response);
                    break;

                case "serviciosPorCliente":
                    listarServiciosPorCliente(request, response);
                    break;

                case "siguienteNumero":
                    siguienteNumero(response);
                    break;

                case "buscar":
                    buscar(request, response);
                    break;

                case "reporteIndividual":
                    reporteIndividual(request, response);
                    break;

                default:
                    enviarError(
                            response,
                            "Acción no reconocida: " + accion
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

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null || accion.trim().isEmpty()) {

            enviarError(
                    response,
                    "No se especificó ninguna acción."
            );

            return;
        }

        try {

            switch (accion) {

                case "guardar":
                    guardar(request, response);
                    break;

                case "anular":
                    anular(request, response);
                    break;

                case "marcarPagada":
                    marcarPagada(request, response);
                    break;

                default:
                    enviarError(
                            response,
                            "Acción no reconocida: " + accion
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

    private void listar(
            HttpServletResponse response)
            throws Exception {

        List<Factura> lista = facturaDAO.listar();

        StringBuilder json = new StringBuilder();

        json.append("[");

        for (int i = 0; i < lista.size(); i++) {

            if (i > 0) {
                json.append(",");
            }

            json.append(
                    facturaToJson(lista.get(i))
            );
        }

        json.append("]");

        enviarJson(
                response,
                json.toString()
        );
    }

    private void listarClientesActivos(
            HttpServletResponse response)
            throws Exception {

        List<Factura> lista =
                facturaDAO.listarClientesActivos();

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0; i < lista.size(); i++) {

            if (i > 0) {
                json.append(",");
            }

            Factura f = lista.get(i);

            json.append("{");

            json.append("\"id_cliente\":")
                    .append(f.getId_cliente())
                    .append(",");

            json.append("\"nombre\":\"")
                    .append(
                            escaparJson(
                                    f.getNombre_cliente()
                            )
                    )
                    .append("\",");

            json.append("\"apellido\":\"")
                    .append(
                            escaparJson(
                                    f.getApellido_cliente()
                            )
                    )
                    .append("\",");

            json.append("\"cedula\":\"")
                    .append(
                            escaparJson(
                                    f.getCedula_cliente()
                            )
                    )
                    .append("\",");

            json.append("\"direccion\":\"")
                    .append(
                            escaparJson(
                                    f.getDireccion_cliente()
                            )
                    )
                    .append("\"");

            json.append("}");
        }

        json.append("]");

        enviarJson(
                response,
                json.toString()
        );
    }

    private void buscarClientes(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String criterio =
                obtenerParametro(
                        request,
                        "criterio"
                );

        List<Cliente> lista =
                clienteDAO.buscarParaFacturacion(
                        criterio
                );

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0; i < lista.size(); i++) {

            if (i > 0) {
                json.append(",");
            }

            Cliente c = lista.get(i);

            json.append("{");

            json.append("\"id_cliente\":")
                    .append(c.getId_cliente())
                    .append(",");

            json.append("\"nombre\":\"")
                    .append(
                            escaparJson(
                                    c.getNombre()
                            )
                    )
                    .append("\",");

            json.append("\"apellido\":\"")
                    .append(
                            escaparJson(
                                    c.getApellido()
                            )
                    )
                    .append("\",");

            json.append("\"cedula\":\"")
                    .append(
                            escaparJson(
                                    c.getCedula()
                            )
                    )
                    .append("\",");

            json.append("\"direccion\":\"")
                    .append(
                            escaparJson(
                                    c.getDireccion()
                            )
                    )
                    .append("\"");

            json.append("}");
        }

        json.append("]");

        enviarJson(
                response,
                json.toString()
        );
    }

    private void listarTarifasActivas(
            HttpServletResponse response)
            throws Exception {

        List<DetalleFactura> lista =
                facturaDAO.listarTarifasActivas();

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0; i < lista.size(); i++) {

            if (i > 0) {
                json.append(",");
            }

            DetalleFactura d = lista.get(i);

            json.append("{");

            json.append("\"id_tarifa\":")
                    .append((int) d.getId_tarifa())
                    .append(",");

            json.append("\"nombre\":\"")
                    .append(
                            escaparJson(
                                    d.getNombre_tarifa()
                            )
                    )
                    .append("\",");

            json.append("\"descripcion\":\"")
                    .append(
                            escaparJson(
                                    d.getDescripcion()
                            )
                    )
                    .append("\",");

            json.append("\"monto\":")
                    .append(
                            decimalJson(
                                    d.getPrecio_unitario()
                            )
                    );

            json.append("}");
        }

        json.append("]");

        enviarJson(
                response,
                json.toString()
        );
    }

    private void listarServiciosPorCliente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String idClienteTexto =
                obtenerParametro(
                        request,
                        "id_cliente"
                );

        if (idClienteTexto.isEmpty()) {

            enviarError(
                    response,
                    "Debe seleccionar un cliente para consultar sus establecimientos."
            );

            return;
        }

        int idCliente;

        try {

            idCliente =
                    Integer.parseInt(
                            idClienteTexto
                    );

        } catch (NumberFormatException e) {

            enviarError(
                    response,
                    "El ID del cliente no es válido."
            );

            return;
        }

        List<Servicio> lista =
                facturaDAO.listarServiciosPorCliente(
                        idCliente
                );

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0; i < lista.size(); i++) {

            if (i > 0) {
                json.append(",");
            }

            Servicio s = lista.get(i);

            json.append("{");

            json.append("\"id_servicio\":")
                    .append(s.getId_servicio())
                    .append(",");

            json.append("\"id_cliente\":")
                    .append(s.getId_cliente())
                    .append(",");

            json.append("\"nombre_establecimiento\":\"")
                    .append(
                            escaparJson(
                                    s.getNombre_establecimiento()
                            )
                    )
                    .append("\",");

            json.append("\"direccion_servicio\":\"")
                    .append(
                            escaparJson(
                                    s.getDireccion_servicio()
                            )
                    )
                    .append("\",");

            json.append("\"nombre_barrio\":\"")
                    .append(
                            escaparJson(
                                    s.getNombre_barrio()
                            )
                    )
                    .append("\",");

            json.append("\"tipo_servicio\":\"")
                    .append(
                            escaparJson(
                                    s.getTipo_servicio()
                            )
                    )
                    .append("\",");

            json.append("\"frecuencia\":\"")
                    .append(
                            escaparJson(
                                    s.getFrecuencia()
                            )
                    )
                    .append("\",");

            json.append("\"id_tarifa\":")
                    .append(s.getId_tarifa())
                    .append(",");

            json.append("\"nombre_tarifa\":\"")
                    .append(
                            escaparJson(
                                    s.getNombre_tarifa()
                            )
                    )
                    .append("\",");

            json.append("\"monto_tarifa\":")
                    .append(
                            decimalJson(
                                    BigDecimal.valueOf(
                                            s.getMonto_tarifa()
                                    )
                            )
                    )
                    .append(",");

            json.append("\"estado\":\"")
                    .append(
                            escaparJson(
                                    s.getEstado()
                            )
                    )
                    .append("\"");

            json.append("}");
        }

        json.append("]");

        enviarJson(
                response,
                json.toString()
        );
    }

    private void siguienteNumero(
            HttpServletResponse response)
            throws Exception {

        String numero =
                facturaDAO.obtenerSiguienteNumeroFactura();

        StringBuilder json =
                new StringBuilder();

        json.append("{");

        json.append("\"success\":true,");

        json.append("\"numero_factura\":\"")
                .append(
                        escaparJson(numero)
                )
                .append("\"");

        json.append("}");

        enviarJson(
                response,
                json.toString()
        );
    }

    private void buscar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        int idFactura =
                obtenerIdFactura(request);

        Factura factura =
                facturaDAO.buscarPorId(idFactura);

        if (factura == null) {

            enviarError(
                    response,
                    "No se encontró la factura solicitada."
            );

            return;
        }

        List<DetalleFactura> detalles =
                facturaDAO.listarDetalles(idFactura);

        StringBuilder json =
                new StringBuilder();

        json.append("{");

        json.append("\"success\":true,");

        json.append("\"factura\":")
                .append(
                        facturaToJson(factura)
                )
                .append(",");

        json.append("\"detalles\":[");

        for (int i = 0; i < detalles.size(); i++) {

            if (i > 0) {
                json.append(",");
            }

            json.append(
                    detalleToJson(
                            detalles.get(i)
                    )
            );
        }

        json.append("]");

        json.append("}");

        enviarJson(
                response,
                json.toString()
        );
    }

    private void guardar(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String idClienteTexto =
                obtenerParametro(
                        request,
                        "id_cliente"
                );

        if (idClienteTexto.isEmpty()) {

            enviarError(
                    response,
                    "Debe seleccionar un cliente."
            );

            return;
        }

        int idCliente =
                Integer.parseInt(
                        idClienteTexto
                );

        String idServicioTexto =
                obtenerParametro(
                        request,
                        "id_servicio"
                );

        if (idServicioTexto.isEmpty()) {

            enviarError(
                    response,
                    "Debe seleccionar un establecimiento del cliente."
            );

            return;
        }

        int idServicio;

        try {

            idServicio =
                    Integer.parseInt(
                            idServicioTexto
                    );

        } catch (NumberFormatException e) {

            enviarError(
                    response,
                    "El establecimiento seleccionado no es válido."
            );

            return;
        }

        Servicio servicio =
                facturaDAO.buscarServicioPorId(
                        idServicio
                );

        if (servicio == null
                || servicio.getId_cliente() != idCliente
                || !"ACTIVO".equalsIgnoreCase(
                        servicio.getEstado()
                )) {

            enviarError(
                    response,
                    "El establecimiento seleccionado no pertenece al cliente o no está activo."
            );

            return;
        }

        String timbrado =
                obtenerParametro(
                        request,
                        "timbrado"
                );

        if (timbrado.isEmpty()) {

            enviarError(
                    response,
                    "Debe ingresar el número de timbrado."
            );

            return;
        }


        // El timbrado en Paraguay es un número de 8 dígitos
        if (!timbrado.matches("\\d{8}")) {

            enviarError(
                    response,
                    "El número de timbrado debe tener 8 dígitos."
            );

            return;
        }

        String fechaInicioTimbrado =
                obtenerParametro(
                        request,
                        "fecha_inicio_timbrado"
                );

        String fechaFinTimbrado =
                obtenerParametro(
                        request,
                        "fecha_fin_timbrado"
                );

        if (fechaInicioTimbrado.isEmpty()
                || fechaFinTimbrado.isEmpty()) {

            enviarError(
                    response,
                    "Debe completar las fechas de vigencia del timbrado."
            );

            return;
        }

        Date fechaInicio;

        Date fechaFin;

        try {

            fechaInicio =
                    Date.valueOf(
                            fechaInicioTimbrado
                    );

            fechaFin =
                    Date.valueOf(
                            fechaFinTimbrado
                    );

        } catch (IllegalArgumentException e) {

            enviarError(
                    response,
                    "Las fechas de vigencia del timbrado no son válidas."
            );

            return;
        }


        // VALIDAR VIGENCIA DEL TIMBRADO
        //
        // La factura se emite con la fecha de hoy, y en Paraguay solo se
        // puede emitir dentro del período de vigencia del timbrado.

        String validacionTimbrado =
                validarVigenciaTimbrado(
                        fechaInicio.toLocalDate(),
                        fechaFin.toLocalDate(),
                        java.time.LocalDate.now()
                );

        if (validacionTimbrado != null) {

            enviarError(
                    response,
                    validacionTimbrado
            );

            return;
        }

        String establecimiento =
                obtenerParametro(
                        request,
                        "establecimiento"
                );

        if (establecimiento.isEmpty()) {
            establecimiento = "001";
        }

        String puntoExpedicion =
                obtenerParametro(
                        request,
                        "punto_expedicion"
                );

        if (puntoExpedicion.isEmpty()) {
            puntoExpedicion = "001";
        }

        String numeroFactura =
                obtenerParametro(
                        request,
                        "numero_factura"
                );

        if (numeroFactura.isEmpty()) {

            numeroFactura =
                    facturaDAO
                            .obtenerSiguienteNumeroFactura();
        }

        String condicionVenta =
                obtenerParametro(
                        request,
                        "condicion_venta"
                );

        if (condicionVenta.isEmpty()) {
            condicionVenta = "CREDITO";
        }

        // DATOS DE CRÉDITO

        String plazoCreditoTexto =
                obtenerParametro(
                        request,
                        "plazo_credito"
                );

        int plazoCredito = 0;

        if (!plazoCreditoTexto.isEmpty()) {

            try {

                plazoCredito =
                        Integer.parseInt(
                                plazoCreditoTexto
                        );

            } catch (NumberFormatException e) {

                enviarError(
                        response,
                        "El plazo de crédito no es válido."
                );

                return;
            }
        }

        if (plazoCredito < 0) {
            plazoCredito = 0;
        }

        Date fechaVencimiento;

        Date fechaEmision =
                new Date(
                        System.currentTimeMillis()
                );

        if ("CREDITO".equalsIgnoreCase(
                condicionVenta
        )) {

            if (plazoCredito <= 0) {

                enviarError(
                        response,
                        "Para una factura a crédito debe ingresar un plazo mayor a 0 días."
                );

                return;
            }

            /*
             * El servidor calcula nuevamente la fecha de vencimiento.
             * De esta forma no dependemos únicamente del valor enviado
             * desde el navegador.
             */
            fechaVencimiento =
                    Date.valueOf(
                            fechaEmision
                                    .toLocalDate()
                                    .plusDays(
                                            plazoCredito
                                    )
                    );

        } else {

            condicionVenta = "CONTADO";
            plazoCredito = 0;

            /*
             * Para contado la fecha de vencimiento coincide con
             * la fecha de emisión.
             */
            fechaVencimiento =
                    fechaEmision;
        }

        String periodo =
                obtenerParametro(
                        request,
                        "periodo"
                );

        String observaciones =
                obtenerParametro(
                        request,
                        "observaciones"
                );

        Factura factura =
                new Factura();

        factura.setId_cliente(
                idCliente
        );

        factura.setId_servicio(
                idServicio
        );

        factura.setFecha_emision(
                fechaEmision
        );

        factura.setPlazo_credito(
                plazoCredito
        );

        factura.setFecha_vencimiento(
                fechaVencimiento
        );

        factura.setTimbrado(
                timbrado
        );

        factura.setFecha_inicio_timbrado(
                fechaInicio
        );

        factura.setFecha_fin_timbrado(
                fechaFin
        );

        factura.setEstablecimiento(
                establecimiento
        );

        factura.setPunto_expedicion(
                puntoExpedicion
        );

        factura.setNumero_factura(
                numeroFactura
        );

        factura.setCondicion_venta(
                condicionVenta
        );

        factura.setPeriodo(
                periodo
        );

        factura.setObservaciones(
                observaciones
        );

        factura.setEstado(
                "EMITIDA"
        );

        factura.setUsuario_emision(
                obtenerUsuario(request)
        );

        String[] idsTarifa =
                obtenerArray(
                        request,
                        "id_tarifa[]"
                );

        String[] descripciones =
                obtenerArray(
                        request,
                        "descripcion[]"
                );

        String[] cantidades =
                obtenerArray(
                        request,
                        "cantidad[]"
                );

        String[] precios =
                obtenerArray(
                        request,
                        "precio_unitario[]"
                );

        String[] porcentajesIva =
                obtenerArray(
                        request,
                        "porcentaje_iva[]"
                );

        if (descripciones.length == 0) {

            enviarError(
                    response,
                    "Debe agregar al menos un detalle a la factura."
            );

            return;
        }

        ArrayList<DetalleFactura> detalles =
                new ArrayList<>();

        BigDecimal subtotalGeneral =
                BigDecimal.ZERO;

        BigDecimal iva5General =
                BigDecimal.ZERO;

        BigDecimal iva10General =
                BigDecimal.ZERO;

        BigDecimal totalGeneral =
                BigDecimal.ZERO;

        for (int i = 0;
             i < descripciones.length;
             i++) {

            String descripcion =
                    descripciones[i];

            if (descripcion == null
                    || descripcion.trim().isEmpty()) {

                continue;
            }

            BigDecimal cantidad =
                    convertirDecimal(
                            i < cantidades.length
                                    ? cantidades[i]
                                    : "1"
                    );

            BigDecimal precioUnitario =
                    convertirDecimal(
                            i < precios.length
                                    ? precios[i]
                                    : "0"
                    );

            BigDecimal porcentajeIva =
                    convertirDecimal(
                            i < porcentajesIva.length
                                    ? porcentajesIva[i]
                                    : "10"
                    );

            if (cantidad.compareTo(
                    BigDecimal.ZERO
            ) <= 0) {

                cantidad =
                        BigDecimal.ONE;
            }

            if (precioUnitario.compareTo(
                    BigDecimal.ZERO
            ) < 0) {

                precioUnitario =
                        BigDecimal.ZERO;
            }

            BigDecimal totalLinea =
                    cantidad
                            .multiply(
                                    precioUnitario
                            )
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP
                            );

            BigDecimal montoIva =
                    BigDecimal.ZERO;

            BigDecimal subtotalLinea =
                    totalLinea;
                        if (porcentajeIva.compareTo(
                    new BigDecimal("5")
            ) == 0) {

                montoIva =
                        totalLinea
                                .multiply(
                                        new BigDecimal("5")
                                )
                                .divide(
                                        new BigDecimal("105"),
                                        2,
                                        RoundingMode.HALF_UP
                                );

                subtotalLinea =
                        totalLinea
                                .subtract(
                                        montoIva
                                )
                                .setScale(
                                        2,
                                        RoundingMode.HALF_UP
                                );

                iva5General =
                        iva5General.add(
                                montoIva
                        );

            } else if (porcentajeIva.compareTo(
                    new BigDecimal("10")
            ) == 0) {

                montoIva =
                        totalLinea
                                .multiply(
                                        new BigDecimal("10")
                                )
                                .divide(
                                        new BigDecimal("110"),
                                        2,
                                        RoundingMode.HALF_UP
                                );

                subtotalLinea =
                        totalLinea
                                .subtract(
                                        montoIva
                                )
                                .setScale(
                                        2,
                                        RoundingMode.HALF_UP
                                );

                iva10General =
                        iva10General.add(
                                montoIva
                        );

            } else {

                montoIva =
                        BigDecimal.ZERO;

                subtotalLinea =
                        totalLinea;
            }

            Integer idTarifa = null;

            if (i < idsTarifa.length
                    && idsTarifa[i] != null
                    && !idsTarifa[i]
                            .trim()
                            .isEmpty()) {

                try {

                    int id =
                            Integer.parseInt(
                                    idsTarifa[i]
                            );

                    if (id > 0) {
                        idTarifa = id;
                    }

                } catch (NumberFormatException e) {

                    idTarifa = null;
                }
            }

            DetalleFactura detalle =
                    new DetalleFactura();

            detalle.setId_tarifa(
                    idTarifa
            );

            detalle.setDescripcion(
                    descripcion.trim()
            );

            detalle.setCantidad(
                    cantidad
            );

            detalle.setPrecio_unitario(
                    precioUnitario
            );

            detalle.setPorcentaje_iva(
                    porcentajeIva
            );

            detalle.setSubtotal(
                    subtotalLinea
            );

            detalle.setMonto_iva(
                    montoIva
            );

            detalle.setTotal(
                    totalLinea
            );

            detalles.add(
                    detalle
            );

            subtotalGeneral =
                    subtotalGeneral.add(
                            subtotalLinea
                    );

            totalGeneral =
                    totalGeneral.add(
                            totalLinea
                    );
        }

        if (detalles.isEmpty()) {

            enviarError(
                    response,
                    "No se encontró ningún detalle válido para guardar."
            );

            return;
        }

        subtotalGeneral =
                subtotalGeneral.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        iva5General =
                iva5General.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        iva10General =
                iva10General.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        totalGeneral =
                totalGeneral.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        factura.setSubtotal(
                subtotalGeneral
        );

        factura.setIva_5(
                iva5General
        );

        factura.setIva_10(
                iva10General
        );

        factura.setTotal(
                totalGeneral
        );

        int idFactura =
                facturaDAO.insertarFactura(
                        factura,
                        detalles
                );

        if (idFactura > 0) {

            StringBuilder json =
                    new StringBuilder();

            json.append("{");

            json.append("\"success\":true,");

            json.append("\"mensaje\":\"")
                    .append(
                            escaparJson(
                                    "Factura guardada correctamente."
                            )
                    )
                    .append("\",");

            json.append("\"id_factura\":")
                    .append(idFactura)
                    .append(",");

            json.append("\"numero_factura\":\"")
                    .append(
                            escaparJson(
                                    numeroFactura
                            )
                    )
                    .append("\"");

            json.append("}");

            enviarJson(
                    response,
                    json.toString()
            );

        } else {

            enviarError(
                    response,
                    "No se pudo guardar la factura."
            );
        }
    }

    private void anular(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        int idFactura =
                obtenerIdFactura(request);

        boolean resultado =
                facturaDAO.anular(
                        idFactura
                );

        if (resultado) {

            enviarExito(
                    response,
                    "La factura fue anulada correctamente."
            );

        } else {

            enviarError(
                    response,
                    "No se pudo anular la factura."
            );
        }
    }

    private void marcarPagada(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        int idFactura =
                obtenerIdFactura(request);

        boolean resultado =
                facturaDAO.marcarPagada(
                        idFactura
                );

        if (resultado) {

            enviarExito(
                    response,
                    "La factura fue marcada como PAGADA correctamente."
            );

        } else {

            enviarError(
                    response,
                    "No se pudo marcar la factura como PAGADA."
            );
        }
    }

    // Devuelve un mensaje de error si la fecha de emisión está fuera de la
    // vigencia del timbrado, o null si se puede emitir.

    static String validarVigenciaTimbrado(
            java.time.LocalDate inicio,
            java.time.LocalDate fin,
            java.time.LocalDate emision) {

        java.time.format.DateTimeFormatter formato =
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (fin.isBefore(inicio)) {
            return "La fecha de fin de vigencia del timbrado no puede ser anterior a la fecha de inicio.";
        }

        if (emision.isBefore(inicio)) {
            return "El timbrado todavía no está vigente: su vigencia comienza el "
                    + inicio.format(formato) + ".";
        }

        if (emision.isAfter(fin)) {
            return "El timbrado está vencido: su vigencia terminó el "
                    + fin.format(formato) + ". No se puede emitir la factura.";
        }

        return null;
    }


    private void reporteIndividual(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        int idFactura =
                obtenerIdFactura(request);

        Factura factura =
                facturaDAO.buscarPorId(
                        idFactura
                );

        if (factura == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "No se encontró la factura."
            );

            return;
        }

        List<DetalleFactura> detalles =
                facturaDAO.listarDetalles(
                        idFactura
                );

        List<Map<String, ?>> datos =
                new ArrayList<>();

        for (DetalleFactura detalle : detalles) {

            Map<String, Object> fila =
                    new HashMap<>();

            fila.put(
                    "id_factura",
                    factura.getId_factura()
            );

            fila.put(
                    "fecha_emision",
                    factura.getFecha_emision()
            );

            fila.put(
                    "timbrado",
                    factura.getTimbrado()
            );

            fila.put(
                    "fecha_inicio_timbrado",
                    factura.getFecha_inicio_timbrado()
            );

            fila.put(
                    "fecha_fin_timbrado",
                    factura.getFecha_fin_timbrado()
            );

            fila.put(
                    "establecimiento",
                    factura.getEstablecimiento()
            );

            fila.put(
                    "punto_expedicion",
                    factura.getPunto_expedicion()
            );

            fila.put(
                    "numero_factura",
                    factura.getNumero_factura()
            );

            fila.put(
                    "condicion_venta",
                    factura.getCondicion_venta()
            );

            fila.put(
                    "plazo_credito",
                    factura.getPlazo_credito()
            );

            fila.put(
                    "fecha_vencimiento",
                    factura.getFecha_vencimiento()
            );

            fila.put(
                    "periodo",
                    factura.getPeriodo()
            );

            fila.put(
                    "estado",
                    factura.getEstado()
            );

            fila.put(
                    "observaciones",
                    factura.getObservaciones()
            );

            fila.put(
                    "usuario_emision",
                    factura.getUsuario_emision()
            );

            fila.put(
                    "nombre_cliente",
                    factura.getNombre_cliente()
            );

            fila.put(
                    "apellido_cliente",
                    factura.getApellido_cliente()
            );

            fila.put(
                    "cedula_cliente",
                    factura.getCedula_cliente()
            );

            fila.put(
                    "direccion_cliente",
                    factura.getDireccion_cliente()
            );

            fila.put(
                    "descripcion",
                    detalle.getDescripcion()
            );

            fila.put(
                    "cantidad",
                    detalle.getCantidad()
            );

            fila.put(
                    "precio_unitario",
                    detalle.getPrecio_unitario()
            );

            fila.put(
                    "porcentaje_iva",
                    detalle.getPorcentaje_iva()
            );

            fila.put(
                    "subtotal",
                    detalle.getSubtotal()
            );

            fila.put(
                    "monto_iva",
                    detalle.getMonto_iva()
            );

            fila.put(
                    "total",
                    detalle.getTotal()
            );

            fila.put(
                    "subtotal_factura",
                    factura.getSubtotal()
            );

            fila.put(
                    "iva_5",
                    factura.getIva_5()
            );

            fila.put(
                    "iva_10",
                    factura.getIva_10()
            );

            fila.put(
                    "total_factura",
                    factura.getTotal()
            );

            datos.add(
                    fila
            );
        }

        if (datos.isEmpty()) {

            Map<String, Object> fila =
                    new HashMap<>();

            fila.put(
                    "id_factura",
                    factura.getId_factura()
            );

            fila.put(
                    "fecha_emision",
                    factura.getFecha_emision()
            );

            fila.put(
                    "timbrado",
                    factura.getTimbrado()
            );

            fila.put(
                    "fecha_inicio_timbrado",
                    factura.getFecha_inicio_timbrado()
            );

            fila.put(
                    "fecha_fin_timbrado",
                    factura.getFecha_fin_timbrado()
            );

            fila.put(
                    "establecimiento",
                    factura.getEstablecimiento()
            );

            fila.put(
                    "punto_expedicion",
                    factura.getPunto_expedicion()
            );

            fila.put(
                    "numero_factura",
                    factura.getNumero_factura()
            );

            fila.put(
                    "condicion_venta",
                    factura.getCondicion_venta()
            );

            fila.put(
                    "plazo_credito",
                    factura.getPlazo_credito()
            );

            fila.put(
                    "fecha_vencimiento",
                    factura.getFecha_vencimiento()
            );

            fila.put(
                    "periodo",
                    factura.getPeriodo()
            );

            fila.put(
                    "estado",
                    factura.getEstado()
            );

            fila.put(
                    "observaciones",
                    factura.getObservaciones()
            );

            fila.put(
                    "usuario_emision",
                    factura.getUsuario_emision()
            );

            fila.put(
                    "nombre_cliente",
                    factura.getNombre_cliente()
            );

            fila.put(
                    "apellido_cliente",
                    factura.getApellido_cliente()
            );

            fila.put(
                    "cedula_cliente",
                    factura.getCedula_cliente()
            );

            fila.put(
                    "direccion_cliente",
                    factura.getDireccion_cliente()
            );

            fila.put(
                    "descripcion",
                    ""
            );

            fila.put(
                    "cantidad",
                    BigDecimal.ZERO
            );

            fila.put(
                    "precio_unitario",
                    BigDecimal.ZERO
            );

            fila.put(
                    "porcentaje_iva",
                    BigDecimal.ZERO
            );

            fila.put(
                    "subtotal",
                    BigDecimal.ZERO
            );

            fila.put(
                    "monto_iva",
                    BigDecimal.ZERO
            );

            fila.put(
                    "total",
                    BigDecimal.ZERO
            );

            fila.put(
                    "subtotal_factura",
                    factura.getSubtotal()
            );

            fila.put(
                    "iva_5",
                    factura.getIva_5()
            );

            fila.put(
                    "iva_10",
                    factura.getIva_10()
            );

            fila.put(
                    "total_factura",
                    factura.getTotal()
            );

            datos.add(
                    fila
            );
        }

        Map<String, Object> parametros =
                new HashMap<>();

        parametros.put(
                "ID_FACTURA",
                factura.getId_factura()
        );

        parametros.put(
                "USUARIO_IMPRESION",
                obtenerUsuario(request)
        );

        InputStream logo =
                getServletContext()
                        .getResourceAsStream(
                                "/WEB-INF/reportes/logo.png"
                        );

        parametros.put(
                "LOGO",
                logo
        );

        parametros.put(
                "TOTAL_LETRAS",
                convertirMontoLetras(
                        factura.getTotal()
                )
        );

        InputStream jrxml =
                getServletContext()
                        .getResourceAsStream(
                                "/WEB-INF/reportes/factura_individual.jrxml"
                        );

        if (jrxml == null) {

            throw new Exception(
                    "No se encontró el archivo factura_individual.jrxml."
            );
        }

        JasperReport reporte =
                JasperCompileManager.compileReport(
                        jrxml
                );

        JRMapCollectionDataSource dataSource =
                new JRMapCollectionDataSource(
                        datos
                );

        byte[] pdf =
                JasperExportManager.exportReportToPdf(
                        JasperFillManager.fillReport(
                                reporte,
                                parametros,
                                dataSource
                        )
                );

        response.setContentType(
                "application/pdf"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );

        response.setHeader(
                "Content-Disposition",
                "inline; filename=\"factura_"
                        + factura.getNumero_factura()
                        + ".pdf\""
        );

        response.setContentLength(
                pdf.length
        );

        response.getOutputStream().write(
                pdf
        );

        response.getOutputStream().flush();

        try {

            if (logo != null) {
                logo.close();
            }

            jrxml.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private int obtenerIdFactura(
            HttpServletRequest request)
            throws Exception {

        String id =
                request.getParameter("id");

        if (id == null
                || id.trim().isEmpty()) {

            id =
                    request.getParameter(
                            "id_factura"
                    );
        }

        if (id == null
                || id.trim().isEmpty()) {

            throw new Exception(
                    "No se especificó el ID de la factura."
            );
        }

        try {

            return Integer.parseInt(
                    id.trim()
            );

        } catch (NumberFormatException e) {

            throw new Exception(
                    "El ID de la factura no es válido."
            );
        }
    }

    private String obtenerUsuario(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        if (session != null) {

            Object usuario =
                    session.getAttribute(
                            "usuario"
                    );

            if (usuario != null
                    && !usuario
                            .toString()
                            .trim()
                            .isEmpty()) {

                return usuario.toString();
            }
        }

        return "Sistema";
    }
        private String obtenerParametro(
            HttpServletRequest request,
            String nombre) {

        String valor =
                request.getParameter(nombre);

        if (valor == null) {
            return "";
        }

        return valor.trim();
    }

    private String[] obtenerArray(
            HttpServletRequest request,
            String nombre) {

        String[] valores =
                request.getParameterValues(nombre);

        if (valores == null) {
            return new String[0];
        }

        return valores;
    }

    private BigDecimal convertirDecimal(
            String valor) {

        if (valor == null
                || valor.trim().isEmpty()) {

            return BigDecimal.ZERO;
        }

        try {

            String texto =
                    valor
                            .trim()
                            .replace(",", ".");

            return new BigDecimal(texto);

        } catch (NumberFormatException e) {

            return BigDecimal.ZERO;
        }
    }

    private String facturaToJson(
            Factura f) {

        StringBuilder json =
                new StringBuilder();

        json.append("{");

        json.append("\"id_factura\":")
                .append(
                        f.getId_factura()
                )
                .append(",");

        json.append("\"id_cliente\":")
                .append(
                        f.getId_cliente()
                )
                .append(",");

        json.append("\"id_servicio\":")
                .append(
                        f.getId_servicio()
                )
                .append(",");

        json.append("\"fecha_emision\":\"")
                .append(
                        escaparJson(
                                f.getFecha_emision() != null
                                        ? f.getFecha_emision().toString()
                                        : ""
                        )
                )
                .append("\",");

        json.append("\"timbrado\":\"")
                .append(
                        escaparJson(
                                f.getTimbrado()
                        )
                )
                .append("\",");

        json.append("\"fecha_inicio_timbrado\":\"")
                .append(
                        escaparJson(
                                f.getFecha_inicio_timbrado() != null
                                        ? f.getFecha_inicio_timbrado().toString()
                                        : ""
                        )
                )
                .append("\",");

        json.append("\"fecha_fin_timbrado\":\"")
                .append(
                        escaparJson(
                                f.getFecha_fin_timbrado() != null
                                        ? f.getFecha_fin_timbrado().toString()
                                        : ""
                        )
                )
                .append("\",");

        json.append("\"establecimiento\":\"")
                .append(
                        escaparJson(
                                f.getEstablecimiento()
                        )
                )
                .append("\",");

        json.append("\"punto_expedicion\":\"")
                .append(
                        escaparJson(
                                f.getPunto_expedicion()
                        )
                )
                .append("\",");

        json.append("\"numero_factura\":\"")
                .append(
                        escaparJson(
                                f.getNumero_factura()
                        )
                )
                .append("\",");

        json.append("\"condicion_venta\":\"")
                .append(
                        escaparJson(
                                f.getCondicion_venta()
                        )
                )
                .append("\",");

        json.append("\"plazo_credito\":")
                .append(
                        f.getPlazo_credito()
                )
                .append(",");

        json.append("\"fecha_vencimiento\":\"")
                .append(
                        escaparJson(
                                f.getFecha_vencimiento() != null
                                        ? f.getFecha_vencimiento().toString()
                                        : ""
                        )
                )
                .append("\",");

        json.append("\"periodo\":\"")
                .append(
                        escaparJson(
                                f.getPeriodo()
                        )
                )
                .append("\",");

        json.append("\"subtotal\":")
                .append(
                        decimalJson(
                                f.getSubtotal()
                        )
                )
                .append(",");

        json.append("\"iva_5\":")
                .append(
                        decimalJson(
                                f.getIva_5()
                        )
                )
                .append(",");

        json.append("\"iva_10\":")
                .append(
                        decimalJson(
                                f.getIva_10()
                        )
                )
                .append(",");

        json.append("\"total\":")
                .append(
                        decimalJson(
                                f.getTotal()
                        )
                )
                .append(",");

        json.append("\"estado\":\"")
                .append(
                        escaparJson(
                                f.getEstado()
                        )
                )
                .append("\",");

        json.append("\"observaciones\":\"")
                .append(
                        escaparJson(
                                f.getObservaciones()
                        )
                )
                .append("\",");

        json.append("\"usuario_emision\":\"")
                .append(
                        escaparJson(
                                f.getUsuario_emision()
                        )
                )
                .append("\",");

        json.append("\"nombre_cliente\":\"")
                .append(
                        escaparJson(
                                f.getNombre_cliente()
                        )
                )
                .append("\",");

        json.append("\"apellido_cliente\":\"")
                .append(
                        escaparJson(
                                f.getApellido_cliente()
                        )
                )
                .append("\",");

        json.append("\"cedula_cliente\":\"")
                .append(
                        escaparJson(
                                f.getCedula_cliente()
                        )
                )
                .append("\",");

        json.append("\"direccion_cliente\":\"")
                .append(
                        escaparJson(
                                f.getDireccion_cliente()
                        )
                )
                .append("\"");

        json.append("}");

        return json.toString();
    }

    private String detalleToJson(
            DetalleFactura d) {

        StringBuilder json =
                new StringBuilder();

        json.append("{");

        json.append("\"id_detalle_factura\":")
                .append(
                        d.getId_detalle_factura()
                )
                .append(",");

        json.append("\"id_factura\":")
                .append(
                        d.getId_factura()
                )
                .append(",");

        json.append("\"id_tarifa\":")
                .append((int) d.getId_tarifa())
                .append(",");

        json.append("\"descripcion\":\"")
                .append(
                        escaparJson(
                                d.getDescripcion()
                        )
                )
                .append("\",");

        json.append("\"cantidad\":")
                .append(
                        decimalJson(
                                d.getCantidad()
                        )
                )
                .append(",");

        json.append("\"precio_unitario\":")
                .append(
                        decimalJson(
                                d.getPrecio_unitario()
                        )
                )
                .append(",");

        json.append("\"porcentaje_iva\":")
                .append(
                        decimalJson(
                                d.getPorcentaje_iva()
                        )
                )
                .append(",");

        json.append("\"subtotal\":")
                .append(
                        decimalJson(
                                d.getSubtotal()
                        )
                )
                .append(",");

        json.append("\"monto_iva\":")
                .append(
                        decimalJson(
                                d.getMonto_iva()
                        )
                )
                .append(",");

        json.append("\"total\":")
                .append(
                        decimalJson(
                                d.getTotal()
                        )
                )
                .append(",");

        json.append("\"nombre_tarifa\":\"")
                .append(
                        escaparJson(
                                d.getNombre_tarifa()
                        )
                )
                .append("\"");

        json.append("}");

        return json.toString();
    }

    private String decimalJson(
            BigDecimal valor) {

        if (valor == null) {
            return "0";
        }

        return valor
                .setScale(
                        2,
                        RoundingMode.HALF_UP
                )
                .toPlainString();
    }

    private String escaparJson(
            String valor) {

        if (valor == null) {
            return "";
        }

        return valor
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
    }

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

    private void enviarExito(
            HttpServletResponse response,
            String mensaje)
            throws IOException {

        StringBuilder json =
                new StringBuilder();

        json.append("{");

        json.append("\"success\":true,");

        json.append("\"mensaje\":\"")
                .append(
                        escaparJson(mensaje)
                )
                .append("\"");

        json.append("}");

        enviarJson(
                response,
                json.toString()
        );
    }

    private void enviarError(
            HttpServletResponse response,
            String mensaje)
            throws IOException {

        response.setStatus(
                HttpServletResponse.SC_BAD_REQUEST
        );

        StringBuilder json =
                new StringBuilder();

        json.append("{");

        json.append("\"success\":false,");

        json.append("\"mensaje\":\"")
                .append(
                        escaparJson(mensaje)
                )
                .append("\"");

        json.append("}");

        enviarJson(
                response,
                json.toString()
        );
    }

    private String obtenerMensajeError(
            Exception e) {

        if (e == null) {
            return "Ocurrió un error desconocido.";
        }

        Throwable causa = e;

        while (causa.getCause() != null) {
            causa = causa.getCause();
        }

        String mensaje =
                causa.getMessage();

        if (mensaje == null
                || mensaje.trim().isEmpty()) {

            mensaje =
                    e.getMessage();
        }

        if (mensaje == null
                || mensaje.trim().isEmpty()) {

            return "Ocurrió un error al procesar la operación.";
        }

        return mensaje;
    }

    private String convertirMontoLetras(
            BigDecimal monto) {

        if (monto == null) {
            return "CERO GUARANÍES";
        }

        BigDecimal valor =
                monto.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        long entero =
                valor.longValue();

        int centavos =
                valor
                        .remainder(
                                BigDecimal.ONE
                        )
                        .movePointRight(2)
                        .abs()
                        .intValue();

        String resultado =
                apocopar(numeroALetras(entero));

        resultado =
                resultado.toUpperCase();

        if (centavos > 0) {

            resultado +=
                    " CON "
                    + String.format(
                            "%02d",
                            centavos
                    )
                    + "/100";
        }

        return resultado
                + " GUARANÍES";
    }

    private String apocopar(String texto) {

        if (texto.endsWith("VEINTIUNO")) {
            return texto.substring(0, texto.length() - 9) + "VEINTIÚN";
        }

        if (texto.equals("UNO") || texto.endsWith(" UNO")) {
            return texto.substring(0, texto.length() - 1);
        }

        return texto;
    }

    private String numeroALetras(
            long numero) {

        if (numero == 0) {
            return "CERO";
        }

        if (numero < 0) {

            return "MENOS "
                    + numeroALetras(
                            -numero
                    );
        }

        if (numero < 1000) {

            return centenas(numero);
        }

        if (numero < 1000000) {

            long miles =
                    numero / 1000;

            long resto =
                    numero % 1000;

            String resultado;

            if (miles == 1) {

                resultado = "MIL";

            } else {

                resultado =
                        apocopar(numeroALetras(miles))
                                + " MIL";
            }

            if (resto > 0) {

                resultado +=
                        " "
                        + numeroALetras(resto);
            }

            return resultado;
        }

        if (numero < 1000000000) {

            long millones =
                    numero / 1000000;

            long resto =
                    numero % 1000000;

            String resultado;

            if (millones == 1) {

                resultado =
                        "UN MILLÓN";

            } else {

                resultado =
                        apocopar(numeroALetras(millones))
                                + " MILLONES";
            }

            if (resto > 0) {

                resultado +=
                        " "
                        + numeroALetras(resto);
            }

            return resultado;
        }

        long milesMillones =
                numero / 1000000000;

        long resto =
                numero % 1000000000;

        String resultado =
                apocopar(numeroALetras(
                        milesMillones
                ))
                + " MIL MILLONES";

        if (resto > 0) {

            resultado +=
                    " "
                    + numeroALetras(resto);
        }

        return resultado;
    }

    private String centenas(
            long numero) {

        String[] unidades = {
            "",
            "UNO",
            "DOS",
            "TRES",
            "CUATRO",
            "CINCO",
            "SEIS",
            "SIETE",
            "OCHO",
            "NUEVE"
        };

        String[] especiales = {
            "DIEZ",
            "ONCE",
            "DOCE",
            "TRECE",
            "CATORCE",
            "QUINCE",
            "DIECISÉIS",
            "DIECISIETE",
            "DIECIOCHO",
            "DIECINUEVE"
        };

        String[] decenas = {
            "",
            "",
            "VEINTE",
            "TREINTA",
            "CUARENTA",
            "CINCUENTA",
            "SESENTA",
            "SETENTA",
            "OCHENTA",
            "NOVENTA"
        };

        if (numero < 10) {

            return unidades[
                    (int) numero
            ];
        }

        if (numero < 20) {

            return especiales[
                    (int) numero - 10
            ];
        }

        if (numero < 100) {

            int decena =
                    (int) numero / 10;

            int unidad =
                    (int) numero % 10;

            if (decena == 2
                    && unidad > 0) {

                return "VEINTI"
                        + unidades[unidad];
            }

            if (unidad == 0) {

                return decenas[decena];
            }

            return decenas[decena]
                    + " Y "
                    + unidades[unidad];
        }

        if (numero < 200) {

            if (numero == 100) {
                return "CIEN";
            }

            return "CIENTO "
                    + centenas(
                            numero - 100
                    );
        }

        if (numero < 300) {

            return "DOSCIENTOS "
                    + centenas(
                            numero - 200
                    );
        }

        if (numero < 400) {

            return "TRESCIENTOS "
                    + centenas(
                            numero - 300
                    );
        }

        if (numero < 500) {

            return "CUATROCIENTOS "
                    + centenas(
                            numero - 400
                    );
        }

        if (numero < 600) {

            return "QUINIENTOS "
                    + centenas(
                            numero - 500
                    );
        }

        if (numero < 700) {

            return "SEISCIENTOS "
                    + centenas(
                            numero - 600
                    );
        }

        if (numero < 800) {

            return "SETECIENTOS "
                    + centenas(
                            numero - 700
                    );
        }

        if (numero < 900) {

            return "OCHOCIENTOS "
                    + centenas(
                            numero - 800
                    );
        }

        if (numero < 1000) {

            return "NOVECIENTOS "
                    + centenas(
                            numero - 900
                    );
        }

        return "";
    }
}
