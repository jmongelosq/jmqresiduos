package com.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Pendiente {

    // DATOS PRINCIPALES DE LA FACTURA

    private int idFactura;
    private String numeroFactura;
    private Date fechaEmision;
    private String condicionVenta;
    private String periodo;
    private int plazoCredito;
    private Date fechaVencimiento;

    // DATOS ECONÓMICOS

    private BigDecimal totalFactura;
    private BigDecimal totalPagado;
    private BigDecimal saldoPendiente;

    // DATOS DEL CLIENTE

    private int idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String cedulaCliente;
    private String direccionCliente;

    // ESTADOS

    private String estadoFactura;
    private String estadoPendiente;

    // DÍAS DE VENCIMIENTO

    private int diasVencido;

    // CONSTRUCTOR VACÍO

    public Pendiente() {
    }

    // CONSTRUCTOR COMPLETO

    public Pendiente(
            int idFactura,
            String numeroFactura,
            Date fechaEmision,
            String condicionVenta,
            String periodo,
            int plazoCredito,
            Date fechaVencimiento,
            BigDecimal totalFactura,
            BigDecimal totalPagado,
            BigDecimal saldoPendiente,
            int idCliente,
            String nombreCliente,
            String apellidoCliente,
            String cedulaCliente,
            String direccionCliente,
            String estadoFactura,
            String estadoPendiente,
            int diasVencido) {

        this.idFactura = idFactura;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.condicionVenta = condicionVenta;
        this.periodo = periodo;
        this.plazoCredito = plazoCredito;
        this.fechaVencimiento = fechaVencimiento;

        this.totalFactura = totalFactura;
        this.totalPagado = totalPagado;
        this.saldoPendiente = saldoPendiente;

        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.cedulaCliente = cedulaCliente;
        this.direccionCliente = direccionCliente;

        this.estadoFactura = estadoFactura;
        this.estadoPendiente = estadoPendiente;

        this.diasVencido = diasVencido;
    }

    // ID FACTURA

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    // NÚMERO DE FACTURA

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    // FECHA DE EMISIÓN

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    // CONDICIÓN DE VENTA

    public String getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(String condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    // PERÍODO

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    // PLAZO DE CRÉDITO

    public int getPlazoCredito() {
        return plazoCredito;
    }

    public void setPlazoCredito(int plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    // FECHA DE VENCIMIENTO

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    // TOTAL DE FACTURA

    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    // TOTAL PAGADO

    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }

    // SALDO PENDIENTE

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    // ID CLIENTE

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // NOMBRE CLIENTE

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    // APELLIDO CLIENTE

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    // CÉDULA / RUC CLIENTE

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    // DIRECCIÓN CLIENTE

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    // ESTADO DE FACTURA

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    // ESTADO DEL PENDIENTE

    public String getEstadoPendiente() {
        return estadoPendiente;
    }

    public void setEstadoPendiente(String estadoPendiente) {
        this.estadoPendiente = estadoPendiente;
    }

    // DÍAS VENCIDO

    public int getDiasVencido() {
        return diasVencido;
    }

    public void setDiasVencido(int diasVencido) {
        this.diasVencido = diasVencido;
    }
}