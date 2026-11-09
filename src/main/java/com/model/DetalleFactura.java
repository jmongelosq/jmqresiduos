package com.model;

import java.math.BigDecimal;

public class DetalleFactura {

    private int id_detalle_factura;
    private int id_factura;

    private Integer id_tarifa;

    private String descripcion;

    private BigDecimal cantidad;
    private BigDecimal precio_unitario;

    private BigDecimal porcentaje_iva;
    private BigDecimal subtotal;
    private BigDecimal monto_iva;
    private BigDecimal total;

    // Datos adicionales de la tarifa
    private String nombre_tarifa;


    // CONSTRUCTOR

    public DetalleFactura() {
    }


    // GETTERS Y SETTERS

    public int getId_detalle_factura() {
        return id_detalle_factura;
    }

    public void setId_detalle_factura(int id_detalle_factura) {
        this.id_detalle_factura = id_detalle_factura;
    }


    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }


    public Integer getId_tarifa() {
        return id_tarifa;
    }

    public void setId_tarifa(Integer id_tarifa) {
        this.id_tarifa = id_tarifa;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }


    public BigDecimal getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(BigDecimal precio_unitario) {
        this.precio_unitario = precio_unitario;
    }


    public BigDecimal getPorcentaje_iva() {
        return porcentaje_iva;
    }

    public void setPorcentaje_iva(BigDecimal porcentaje_iva) {
        this.porcentaje_iva = porcentaje_iva;
    }


    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }


    public BigDecimal getMonto_iva() {
        return monto_iva;
    }

    public void setMonto_iva(BigDecimal monto_iva) {
        this.monto_iva = monto_iva;
    }


    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    // DATOS DE TARIFA

    public String getNombre_tarifa() {
        return nombre_tarifa;
    }

    public void setNombre_tarifa(String nombre_tarifa) {
        this.nombre_tarifa = nombre_tarifa;
    }

}