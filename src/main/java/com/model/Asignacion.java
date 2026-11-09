package com.model;

public class Asignacion {

    private int id_asignacion;

    private int id_ruta;
    private String nombre_ruta;

    private int id_vehiculo;
    private String chapa;

    private int id_personal_responsable;
    private String nombre_responsable;

    private String fecha;
    private String hora_salida;
    private String hora_regreso;

    private String estado;
    private String observaciones;


    // CONSTRUCTOR

    public Asignacion() {
    }


    // GETTERS Y SETTERS

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }


    public int getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
    }


    public String getNombre_ruta() {
        return nombre_ruta;
    }

    public void setNombre_ruta(String nombre_ruta) {
        this.nombre_ruta = nombre_ruta;
    }


    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }


    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }


    public int getId_personal_responsable() {
        return id_personal_responsable;
    }

    public void setId_personal_responsable(
            int id_personal_responsable) {

        this.id_personal_responsable =
                id_personal_responsable;
    }


    public String getNombre_responsable() {
        return nombre_responsable;
    }

    public void setNombre_responsable(
            String nombre_responsable) {

        this.nombre_responsable =
                nombre_responsable;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }


    public String getHora_regreso() {
        return hora_regreso;
    }

    public void setHora_regreso(String hora_regreso) {
        this.hora_regreso = hora_regreso;
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