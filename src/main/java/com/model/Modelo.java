package com.model;

public class Modelo {

    private int id_modelo;
    private String nombre;
    private String descripcion;
    private boolean estado;

    // CONSTRUCTOR VACÍO

    public Modelo() {
    }

    // CONSTRUCTOR COMPLETO

    public Modelo(
            int id_modelo,
            String nombre,
            String descripcion,
            boolean estado) {

        this.id_modelo = id_modelo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // CONSTRUCTOR PARA INSERTAR

    public Modelo(
            String nombre,
            String descripcion) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = true;
    }

    // GETTERS Y SETTERS

    public int getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
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

        return "Modelo{" +
                "id_modelo=" + id_modelo +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                '}';
    }
}