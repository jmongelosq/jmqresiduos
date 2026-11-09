package com.model;

public class TipoResiduo {

    private int id_tipo;
    private String nombre;
    private String descripcion;
    private boolean estado;

    // CONSTRUCTOR VACÍO

    public TipoResiduo() {
    }

    // CONSTRUCTOR COMPLETO

    public TipoResiduo(
            int id_tipo,
            String nombre,
            String descripcion,
            boolean estado) {

        this.id_tipo = id_tipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // CONSTRUCTOR PARA INSERTAR

    public TipoResiduo(
            String nombre,
            String descripcion) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = true;
    }

    // GETTERS Y SETTERS

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
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

        return "TipoResiduo{" +
                "id_tipo=" + id_tipo +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                '}';
    }
}