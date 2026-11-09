package com.model;

public class Ruta {

    private int id_ruta;
    private String nombre;
    private String descripcion;
    private int id_barrio;
    private String nombre_barrio;
    private String estado;

    public Ruta() {
    }

    public Ruta(int id_ruta,
                String nombre,
                String descripcion,
                int id_barrio,
                String nombre_barrio,
                String estado) {

        this.id_ruta = id_ruta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_barrio = id_barrio;
        this.nombre_barrio = nombre_barrio;
        this.estado = estado;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}