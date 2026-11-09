package com.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Pago {

    // DATOS PRINCIPALES DEL PAGO

    private int idPago;
    private int idFactura;

    private Date fechaPago;
    private String periodo;
    private BigDecimal monto;
    private String metodoPago;
    private String numeroRecibo;
    private String estado;
    private String observaciones;


    // DATOS RELACIONADOS CON LA FACTURA

    private String numeroFactura;
    private BigDecimal totalFactura;
    private String periodoFactura;
    private String estadoFactura;


    // DATOS RELACIONADOS CON EL CLIENTE
    // Se obtienen a través de la factura.

    private String nombreCliente;
    private String apellidoCliente;
    private String cedulaCliente;


    // DATOS RELACIONADOS CON LA TARIFA
    // Se obtienen desde detalle_factura.

    private String nombreTarifa;
    private BigDecimal montoTarifa;


    // CONSTRUCTOR VACÍO

    public Pago() {
    }


    // CONSTRUCTOR COMPLETO

    public Pago(
            int idPago,
            int idFactura,
            Date fechaPago,
            String periodo,
            BigDecimal monto,
            String metodoPago,
            String numeroRecibo,
            String estado,
            String observaciones) {

        this.idPago = idPago;
        this.idFactura = idFactura;
        this.fechaPago = fechaPago;
        this.periodo = periodo;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.numeroRecibo = numeroRecibo;
        this.estado = estado;
        this.observaciones = observaciones;
    }


    // ID PAGO

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }


    // ID FACTURA

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }


    // FECHA DE PAGO

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }


    // PERÍODO DEL PAGO

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }


    // MONTO

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }


    // MÉTODO DE PAGO

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }


    // NÚMERO DE RECIBO

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }


    // ESTADO

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    // OBSERVACIONES

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


    // NÚMERO DE FACTURA

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }


    // TOTAL DE LA FACTURA

    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }


    // PERÍODO DE LA FACTURA

    public String getPeriodoFactura() {
        return periodoFactura;
    }

    public void setPeriodoFactura(String periodoFactura) {
        this.periodoFactura = periodoFactura;
    }


    // ESTADO DE LA FACTURA

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }


    // NOMBRE DEL CLIENTE

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }


    // APELLIDO DEL CLIENTE

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }


    // CÉDULA DEL CLIENTE

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }


    // NOMBRE DE LA TARIFA

    public String getNombreTarifa() {
        return nombreTarifa;
    }

    public void setNombreTarifa(String nombreTarifa) {
        this.nombreTarifa = nombreTarifa;
    }


    // MONTO DE LA TARIFA

    public BigDecimal getMontoTarifa() {
        return montoTarifa;
    }

    public void setMontoTarifa(BigDecimal montoTarifa) {
        this.montoTarifa = montoTarifa;
    }


    // NOMBRE COMPLETO DEL CLIENTE

    public String getNombreCompletoCliente() {

        String nombre = nombreCliente != null
                ? nombreCliente
                : "";

        String apellido = apellidoCliente != null
                ? apellidoCliente
                : "";

        return (nombre + " " + apellido).trim();
    }
}