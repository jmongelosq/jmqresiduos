package com.model;

public class Cargo {

    private int id_cargo;
    private String nombre_cargo;
    private String descripcion;
    private boolean estado;

    // CONSTRUCTOR VACÍO

    public Cargo() {
    }

    // CONSTRUCTOR COMPLETO

    public Cargo(
            int id_cargo,
            String nombre_cargo,
            String descripcion,
            boolean estado) {

        this.id_cargo = id_cargo;
        this.nombre_cargo = nombre_cargo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // CONSTRUCTOR PARA INSERTAR

    public Cargo(
            String nombre_cargo,
            String descripcion) {

        this.nombre_cargo = nombre_cargo;
        this.descripcion = descripcion;
        this.estado = true;
    }

    // GETTERS Y SETTERS

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public String getNombre_cargo() {
        return nombre_cargo;
    }

    public void setNombre_cargo(String nombre_cargo) {
        this.nombre_cargo = nombre_cargo;
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

        return "Cargo{" +
                "id_cargo=" + id_cargo +
                ", nombre_cargo='" + nombre_cargo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                '}';
    }
}