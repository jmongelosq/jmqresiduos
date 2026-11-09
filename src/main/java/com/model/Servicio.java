package com.model;

import java.sql.Date;

public class Servicio {

    // DATOS PRINCIPALES

    private int id_servicio;
    private int id_cliente;

    private String nombre_cliente;

    private int id_tarifa;

    private String nombre_tarifa;
    private double monto_tarifa;

    private String tipo_servicio;

    private String nombre_establecimiento;

    private String direccion_servicio;

    private int id_barrio;

    private String nombre_barrio;

    private String frecuencia;

    private Date fecha_inicio;

    private Date fecha_fin;

    private String estado;

    private String observaciones;


    // CONSTRUCTOR VACÍO

    public Servicio() {
    }


    // CONSTRUCTOR COMPLETO

    public Servicio(
            int id_servicio,
            int id_cliente,
            String nombre_cliente,
            int id_tarifa,
            String nombre_tarifa,
            double monto_tarifa,
            String tipo_servicio,
            String nombre_establecimiento,
            String direccion_servicio,
            int id_barrio,
            String nombre_barrio,
            String frecuencia,
            Date fecha_inicio,
            Date fecha_fin,
            String estado,
            String observaciones) {

        this.id_servicio = id_servicio;
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;

        this.id_tarifa = id_tarifa;
        this.nombre_tarifa = nombre_tarifa;
        this.monto_tarifa = monto_tarifa;

        this.tipo_servicio = tipo_servicio;

        this.nombre_establecimiento =
                nombre_establecimiento;

        this.direccion_servicio =
                direccion_servicio;

        this.id_barrio = id_barrio;

        this.nombre_barrio =
                nombre_barrio;

        this.frecuencia = frecuencia;

        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;

        this.estado = estado;

        this.observaciones = observaciones;
    }


    // GETTERS Y SETTERS

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }


    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }


    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }


    public int getId_tarifa() {
        return id_tarifa;
    }

    public void setId_tarifa(int id_tarifa) {
        this.id_tarifa = id_tarifa;
    }


    public String getNombre_tarifa() {
        return nombre_tarifa;
    }

    public void setNombre_tarifa(String nombre_tarifa) {
        this.nombre_tarifa = nombre_tarifa;
    }


    public double getMonto_tarifa() {
        return monto_tarifa;
    }

    public void setMonto_tarifa(double monto_tarifa) {
        this.monto_tarifa = monto_tarifa;
    }


    public String getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }


    public String getNombre_establecimiento() {
        return nombre_establecimiento;
    }

    public void setNombre_establecimiento(
            String nombre_establecimiento) {

        this.nombre_establecimiento =
                nombre_establecimiento;
    }


    public String getDireccion_servicio() {
        return direccion_servicio;
    }

    public void setDireccion_servicio(
            String direccion_servicio) {

        this.direccion_servicio =
                direccion_servicio;
    }


    public int getId_barrio() {
        return id_barrio;
    }

    public void setId_barrio(int id_barrio) {
        this.id_barrio = id_barrio;
    }


    public String getNombre_barrio() {
        return nombre_barrio;
    }

    public void setNombre_barrio(String nombre_barrio) {
        this.nombre_barrio = nombre_barrio;
    }


    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }


    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }


    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
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

}