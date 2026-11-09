package com.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Factura {

    private int id_factura;
    private int id_cliente;
    private int id_servicio;

    private Date fecha_emision;

    private String timbrado;
    private Date fecha_inicio_timbrado;
    private Date fecha_fin_timbrado;

    private String establecimiento;
    private String punto_expedicion;
    private String numero_factura;

    private String condicion_venta;

    private String periodo;

    // CRÉDITO

    private int plazo_credito;
    private Date fecha_vencimiento;

    // IMPORTES

    private BigDecimal subtotal;
    private BigDecimal iva_5;
    private BigDecimal iva_10;
    private BigDecimal total;

    private String estado;
    private String observaciones;
    private String usuario_emision;

    // DATOS DEL CLIENTE

    private String nombre_cliente;
    private String apellido_cliente;
    private String cedula_cliente;
    private String direccion_cliente;

    // DATOS DEL SERVICIO

    private String nombre_establecimiento;
    private String direccion_servicio;
    private String nombre_barrio;
    private String tipo_servicio;
    private String frecuencia;

    // CONSTRUCTORES

    public Factura() {
    }

    // GETTERS Y SETTERS

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(String timbrado) {
        this.timbrado = timbrado;
    }

    public Date getFecha_inicio_timbrado() {
        return fecha_inicio_timbrado;
    }

    public void setFecha_inicio_timbrado(Date fecha_inicio_timbrado) {
        this.fecha_inicio_timbrado = fecha_inicio_timbrado;
    }

    public Date getFecha_fin_timbrado() {
        return fecha_fin_timbrado;
    }

    public void setFecha_fin_timbrado(Date fecha_fin_timbrado) {
        this.fecha_fin_timbrado = fecha_fin_timbrado;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getPunto_expedicion() {
        return punto_expedicion;
    }

    public void setPunto_expedicion(String punto_expedicion) {
        this.punto_expedicion = punto_expedicion;
    }

    public String getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(String numero_factura) {
        this.numero_factura = numero_factura;
    }

    public String getCondicion_venta() {
        return condicion_venta;
    }

    public void setCondicion_venta(String condicion_venta) {
        this.condicion_venta = condicion_venta;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getPlazo_credito() {
        return plazo_credito;
    }

    public void setPlazo_credito(int plazo_credito) {
        this.plazo_credito = plazo_credito;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva_5() {
        return iva_5;
    }

    public void setIva_5(BigDecimal iva_5) {
        this.iva_5 = iva_5;
    }

    public BigDecimal getIva_10() {
        return iva_10;
    }

    public void setIva_10(BigDecimal iva_10) {
        this.iva_10 = iva_10;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getUsuario_emision() {
        return usuario_emision;
    }

    public void setUsuario_emision(String usuario_emision) {
        this.usuario_emision = usuario_emision;
    }

    // CLIENTE

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getApellido_cliente() {
        return apellido_cliente;
    }

    public void setApellido_cliente(String apellido_cliente) {
        this.apellido_cliente = apellido_cliente;
    }

    public String getCedula_cliente() {
        return cedula_cliente;
    }

    public void setCedula_cliente(String cedula_cliente) {
        this.cedula_cliente = cedula_cliente;
    }

    public String getDireccion_cliente() {
        return direccion_cliente;
    }

    public void setDireccion_cliente(String direccion_cliente) {
        this.direccion_cliente = direccion_cliente;
    }

    // SERVICIO

    public String getNombre_establecimiento() {
        return nombre_establecimiento;
    }

    public void setNombre_establecimiento(String nombre_establecimiento) {
        this.nombre_establecimiento = nombre_establecimiento;
    }

    public String getDireccion_servicio() {
        return direccion_servicio;
    }

    public void setDireccion_servicio(String direccion_servicio) {
        this.direccion_servicio = direccion_servicio;
    }

    public String getNombre_barrio() {
        return nombre_barrio;
    }

    public void setNombre_barrio(String nombre_barrio) {
        this.nombre_barrio = nombre_barrio;
    }

    public String getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    // NOMBRE COMPLETO CLIENTE

    public String getNombreCompletoCliente() {

        String nombre =
                nombre_cliente != null
                        ? nombre_cliente
                        : "";

        String apellido =
                apellido_cliente != null
                        ? apellido_cliente
                        : "";

        return (nombre + " " + apellido).trim();
    }
}