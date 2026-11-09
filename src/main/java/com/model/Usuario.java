package com.model;

public class Usuario {

    // DATOS PRINCIPALES

    private int id_usuario;
    private String nombre_usuario;
    private String nombre_completo;
    private String correo;
    private String clave;

    // RELACIONES

    private int id_personal;
    private int id_rol;

    // Datos obtenidos mediante JOIN
    private String nombre_personal;
    private String nombre_rol;

    // ESTADO

    private boolean estado;

    // FECHA DE CREACIÓN

    private String fecha_creacion;


    // CONSTRUCTOR VACÍO

    public Usuario() {
    }


    // CONSTRUCTOR COMPLETO

    public Usuario(
            int id_usuario,
            String nombre_usuario,
            String nombre_completo,
            String correo,
            String clave,
            int id_personal,
            int id_rol,
            String nombre_personal,
            String nombre_rol,
            boolean estado,
            String fecha_creacion) {

        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.nombre_completo = nombre_completo;
        this.correo = correo;
        this.clave = clave;
        this.id_personal = id_personal;
        this.id_rol = id_rol;
        this.nombre_personal = nombre_personal;
        this.nombre_rol = nombre_rol;
        this.estado = estado;
        this.fecha_creacion = fecha_creacion;
    }


    // CONSTRUCTOR SOLO ID

    public Usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }


    // CONSTRUCTOR USUARIO + CLAVE

    public Usuario(String nombre_usuario, String clave) {
        this.nombre_usuario = nombre_usuario;
        this.clave = clave;
    }


    // ID USUARIO

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }


    // NOMBRE DE USUARIO

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }


    // COMPATIBILIDAD CON CÓDIGO ANTERIOR

    public String getUsuario() {
        return nombre_usuario;
    }

    public void setUsuario(String usuario) {
        this.nombre_usuario = usuario;
    }


    // NOMBRE COMPLETO

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }


    // COMPATIBILIDAD CON LOGIN ANTERIOR

    public String getNombre() {
        return nombre_completo;
    }

    public void setNombre(String nombre) {
        this.nombre_completo = nombre;
    }


    // CORREO

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    // CLAVE

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


    // ID PERSONAL

    public int getId_personal() {
        return id_personal;
    }

    public void setId_personal(int id_personal) {
        this.id_personal = id_personal;
    }


    // NOMBRE DEL PERSONAL

    public String getNombre_personal() {
        return nombre_personal;
    }

    public void setNombre_personal(String nombre_personal) {
        this.nombre_personal = nombre_personal;
    }


    // ID ROL

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }


    // NOMBRE DEL ROL

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }


    // COMPATIBILIDAD CON LOGIN ANTERIOR

    public String getRol() {
        return nombre_rol;
    }

    public void setRol(String rol) {
        this.nombre_rol = rol;
    }


    // ESTADO

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    // FECHA DE CREACIÓN

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }


    // TOSTRING

    @Override
    public String toString() {

        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", correo='" + correo + '\'' +
                ", id_personal=" + id_personal +
                ", id_rol=" + id_rol +
                ", nombre_personal='" + nombre_personal + '\'' +
                ", nombre_rol='" + nombre_rol + '\'' +
                ", estado=" + estado +
                ", fecha_creacion='" + fecha_creacion + '\'' +
                '}';
    }
}