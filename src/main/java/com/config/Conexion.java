package com.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private Connection con;

    public Connection getConexion() {

        try {

            // CARGAR DRIVER DE POSTGRESQL

            Class.forName("org.postgresql.Driver");


            // CONEXIÓN A LA BASE DE DATOS JMQRESIDUOS

            con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jmqresiduos",
                "postgres",
                "1919365"
            );


            // MENSAJE DE CONFIRMACIÓN

            System.out.println(
                "Conectado a jmqresiduos correctamente"
            );


        } catch (Exception e) {

            System.out.println(
                "Error al conectar con la base de datos jmqresiduos"
            );

            e.printStackTrace();
        }

        return con;
    }
}