package com.model;

public class ClasificacionResiduo {

    private int id_clasificacion;
    private int id_recoleccion;
    private int id_tipo;

    private String fecha_recoleccion;
    private String nombre_tipo;

    private double cantidad_kg;

    private String observaciones;

    private boolean estado;


    // CONSTRUCTORES

    public ClasificacionResiduo() {
    }


    // GETTERS Y SETTERS

    public int getId_clasificacion() {
        return id_clasificacion;
    }

    public void setId_clasificacion(int id_clasificacion) {
        this.id_clasificacion = id_clasificacion;
    }


    public int getId_recoleccion() {
        return id_recoleccion;
    }

    public void setId_recoleccion(int id_recoleccion) {
        this.id_recoleccion = id_recoleccion;
    }


    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }


    public String getFecha_recoleccion() {
        return fecha_recoleccion;
    }

    public void setFecha_recoleccion(String fecha_recoleccion) {
        this.fecha_recoleccion = fecha_recoleccion;
    }


    public String getNombre_tipo() {
        return nombre_tipo;
    }

    public void setNombre_tipo(String nombre_tipo) {
        this.nombre_tipo = nombre_tipo;
    }


    public double getCantidad_kg() {
        return cantidad_kg;
    }

    public void setCantidad_kg(double cantidad_kg) {
        this.cantidad_kg = cantidad_kg;
    }


    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}