package com.model;

public class Personal {

    private int id_personal;
    private String nombre;
    private String apellido;
    private String cedula;
    private String telefono;
    private String direccion;
    private String correo;
    private int id_cargo;
    private int id_barrios;
    private String nombre_cargo;
    private String nombre_barrio;
    private String estado;

    public Personal() {
    }

    public Personal(int id_personal, String nombre, String apellido,
                    String cedula, String telefono, String direccion,
                    String correo, int id_cargo, int id_barrios,
                    String nombre_cargo, String nombre_barrio,
                    String estado) {

        this.id_personal = id_personal;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.id_cargo = id_cargo;
        this.id_barrios = id_barrios;
        this.nombre_cargo = nombre_cargo;
        this.nombre_barrio = nombre_barrio;
        this.estado = estado;
    }

    public int getId_personal() {
        return id_personal;
    }

    public void setId_personal(int id_personal) {
        this.id_personal = id_personal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public int getId_barrios() {
        return id_barrios;
    }

    public void setId_barrios(int id_barrios) {
        this.id_barrios = id_barrios;
    }

    public String getNombre_cargo() {
        return nombre_cargo;
    }

    public void setNombre_cargo(String nombre_cargo) {
        this.nombre_cargo = nombre_cargo;
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