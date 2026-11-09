package com.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Tarifa {

    private int idTarifa;
    private String nombre;
    private String descripcion;
    private BigDecimal monto;
    private String periodicidad;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean estado;

    public Tarifa() {
    }

    public Tarifa(int idTarifa, String nombre, String descripcion,
            BigDecimal monto, String periodicidad,
            Date fechaInicio, Date fechaFin, boolean estado) {

        this.idTarifa = idTarifa;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.monto = monto;
        this.periodicidad = periodicidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}