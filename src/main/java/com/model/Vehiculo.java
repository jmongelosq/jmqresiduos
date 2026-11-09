package com.model;

public class Vehiculo {

    private int id_vehiculo;
    private String matricula;
    private String marca;
    private int anio;
    private double capacidad;
    private int id_modelo;
    private String nombre_modelo;
    private String estado;

    public Vehiculo() {
    }

    public Vehiculo(int id_vehiculo,
                    String matricula,
                    String marca,
                    int anio,
                    double capacidad,
                    int id_modelo,
                    String nombre_modelo,
                    String estado) {

        this.id_vehiculo = id_vehiculo;
        this.matricula = matricula;
        this.marca = marca;
        this.anio = anio;
        this.capacidad = capacidad;
        this.id_modelo = id_modelo;
        this.nombre_modelo = nombre_modelo;
        this.estado = estado;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public int getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
    }

    public String getNombre_modelo() {
        return nombre_modelo;
    }

    public void setNombre_modelo(String nombre_modelo) {
        this.nombre_modelo = nombre_modelo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}