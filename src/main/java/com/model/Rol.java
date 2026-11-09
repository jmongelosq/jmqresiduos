package com.model;

public class Rol {

    private int id_rol;
    private String nombre_rol;
    private String descripcion;
    private boolean estado;

    // CONSTRUCTOR VACÍO

    public Rol() {
    }

    // CONSTRUCTOR CON ID

    public Rol(int id_rol) {
        this.id_rol = id_rol;
    }

    // CONSTRUCTOR COMPLETO

    public Rol(
            int id_rol,
            String nombre_rol,
            String descripcion,
            boolean estado) {

        this.id_rol = id_rol;
        this.nombre_rol = nombre_rol;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // GETTERS Y SETTERS

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
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

    // TOSTRING

    @Override
    public String toString() {

        return "Rol{" +
                "id_rol=" + id_rol +
                ", nombre_rol='" + nombre_rol + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                '}';
    }
}