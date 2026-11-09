package com.model;

public class Recoleccion {

    private int id_recoleccion;
    private int id_asignacion;

    private String fecha;
    private String hora_inicio;
    private String hora_fin;

    private double cantidad_recolectada;
    private String unidad_medida;

    private boolean estado;

    private String observaciones;

    // Datos descriptivos de la asignación
    private String nombre_ruta;
    private String chapa;
    private String nombre_personal;
    private String fecha_asignacion;

    // CONSTRUCTORES

    public Recoleccion() {
    }

    public Recoleccion(
            int id_recoleccion,
            int id_asignacion,
            String fecha,
            String hora_inicio,
            String hora_fin,
            double cantidad_recolectada,
            String unidad_medida,
            boolean estado,
            String observaciones) {

        this.id_recoleccion = id_recoleccion;
        this.id_asignacion = id_asignacion;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.cantidad_recolectada = cantidad_recolectada;
        this.unidad_medida = unidad_medida;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    // GETTERS Y SETTERS

    public int getId_recoleccion() {
        return id_recoleccion;
    }

    public void setId_recoleccion(int id_recoleccion) {
        this.id_recoleccion = id_recoleccion;
    }

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public double getCantidad_recolectada() {
        return cantidad_recolectada;
    }

    public void setCantidad_recolectada(double cantidad_recolectada) {
        this.cantidad_recolectada = cantidad_recolectada;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // DATOS DESCRIPTIVOS

    public String getNombre_ruta() {
        return nombre_ruta;
    }

    public void setNombre_ruta(String nombre_ruta) {
        this.nombre_ruta = nombre_ruta;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getNombre_personal() {
        return nombre_personal;
    }

    public void setNombre_personal(String nombre_personal) {
        this.nombre_personal = nombre_personal;
    }

    public String getFecha_asignacion() {
        return fecha_asignacion;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }
}