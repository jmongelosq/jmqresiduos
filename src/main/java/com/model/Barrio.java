package com.model;

public class Barrio {

    private int id_barrio;
    private String nombre;
    private String descripcion;
    private boolean estado;

    // CONSTRUCTOR VACÍO

    public Barrio() {
    }

    // CONSTRUCTOR COMPLETO

    public Barrio(
            int id_barrio,
            String nombre,
            String descripcion,
            boolean estado) {

        this.id_barrio = id_barrio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // CONSTRUCTOR PARA INSERTAR

    public Barrio(
            String nombre,
            String descripcion) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = true;
    }

    // GETTERS Y SETTERS

    public int getId_barrio() {
        return id_barrio;
    }

    public void setId_barrio(int id_barrio) {
        this.id_barrio = id_barrio;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // TO STRING

    @Override
    public String toString() {

        return "Barrio{" +
                "id_barrio=" + id_barrio +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                '}';
    }
}