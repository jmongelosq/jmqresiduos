package com.dao;

import com.config.Conexion;
import com.model.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    private final Conexion cn = new Conexion();

    // LISTAR TODOS

    public List<Vehiculo> listar() {

        List<Vehiculo> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "v.id_vehiculo, "
                + "v.chapa AS matricula, "
                + "m.marca AS marca, "
                + "v.anio, "
                + "v.capacidad, "
                + "v.id_modelo, "
                + "v.estado, "
                + "m.nombre_modelo AS nombre_modelo "
                + "FROM vehiculo v "
                + "LEFT JOIN modelo m "
                + "ON v.id_modelo = m.id_modelo "
                + "ORDER BY v.id_vehiculo";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Vehiculo v = new Vehiculo();

                v.setId_vehiculo(
                        rs.getInt("id_vehiculo")
                );

                v.setMatricula(
                        rs.getString("matricula")
                );

                v.setMarca(
                        rs.getString("marca")
                );

                v.setAnio(
                        rs.getInt("anio")
                );

                v.setCapacidad(
                        rs.getDouble("capacidad")
                );

                v.setId_modelo(
                        rs.getInt("id_modelo")
                );

                v.setNombre_modelo(
                        rs.getString("nombre_modelo")
                );

                v.setEstado(
                        rs.getString("estado")
                );

                lista.add(v);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // LISTAR VEHÍCULOS DISPONIBLES

    public List<Vehiculo> listarActivos() {

        List<Vehiculo> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "v.id_vehiculo, "
                + "v.chapa AS matricula, "
                + "m.marca AS marca, "
                + "v.anio, "
                + "v.capacidad, "
                + "v.id_modelo, "
                + "v.estado, "
                + "m.nombre_modelo AS nombre_modelo "
                + "FROM vehiculo v "
                + "LEFT JOIN modelo m "
                + "ON v.id_modelo = m.id_modelo "
                + "WHERE v.estado = 'DISPONIBLE' "
                + "ORDER BY v.chapa";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Vehiculo v = new Vehiculo();

                v.setId_vehiculo(
                        rs.getInt("id_vehiculo")
                );

                v.setMatricula(
                        rs.getString("matricula")
                );

                v.setMarca(
                        rs.getString("marca")
                );

                v.setAnio(
                        rs.getInt("anio")
                );

                v.setCapacidad(
                        rs.getDouble("capacidad")
                );

                v.setId_modelo(
                        rs.getInt("id_modelo")
                );

                v.setNombre_modelo(
                        rs.getString("nombre_modelo")
                );

                v.setEstado(
                        rs.getString("estado")
                );

                lista.add(v);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // BUSCAR POR ID

    public Vehiculo buscarPorId(int id) {

        Vehiculo v = null;

        String sql =
                "SELECT "
                + "v.id_vehiculo, "
                + "v.chapa AS matricula, "
                + "m.marca AS marca, "
                + "v.anio, "
                + "v.capacidad, "
                + "v.id_modelo, "
                + "v.estado, "
                + "m.nombre_modelo AS nombre_modelo "
                + "FROM vehiculo v "
                + "LEFT JOIN modelo m "
                + "ON v.id_modelo = m.id_modelo "
                + "WHERE v.id_vehiculo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    v = new Vehiculo();

                    v.setId_vehiculo(
                            rs.getInt("id_vehiculo")
                    );

                    v.setMatricula(
                            rs.getString("matricula")
                    );

                    v.setMarca(
                            rs.getString("marca")
                    );

                    v.setAnio(
                            rs.getInt("anio")
                    );

                    v.setCapacidad(
                            rs.getDouble("capacidad")
                    );

                    v.setId_modelo(
                            rs.getInt("id_modelo")
                    );

                    v.setNombre_modelo(
                            rs.getString("nombre_modelo")
                    );

                    v.setEstado(
                            rs.getString("estado")
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return v;
    }


    // INSERTAR

    public boolean insertar(Vehiculo v) {

        String sql =
                "INSERT INTO vehiculo "
                + "(chapa, id_modelo, anio, capacidad, estado) "
                + "VALUES (?, ?, ?, ?, 'DISPONIBLE')";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    v.getMatricula()
            );

            ps.setInt(
                    2,
                    v.getId_modelo()
            );

            ps.setInt(
                    3,
                    v.getAnio()
            );

            ps.setDouble(
                    4,
                    v.getCapacidad()
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ACTUALIZAR

    public boolean actualizar(Vehiculo v) {

        String sql =
                "UPDATE vehiculo SET "
                + "chapa = ?, "
                + "anio = ?, "
                + "capacidad = ?, "
                + "id_modelo = ?, "
                + "estado = ? "
                + "WHERE id_vehiculo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    v.getMatricula()
            );

            ps.setInt(
                    2,
                    v.getAnio()
            );

            ps.setDouble(
                    3,
                    v.getCapacidad()
            );

            ps.setInt(
                    4,
                    v.getId_modelo()
            );

            ps.setString(
                    5,
                    v.getEstado()
            );

            ps.setInt(
                    6,
                    v.getId_vehiculo()
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // BAJA LÓGICA

    public boolean eliminar(int id) {

        String sql =
                "UPDATE vehiculo "
                + "SET estado = 'INACTIVO' "
                + "WHERE id_vehiculo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ACTIVAR

    public boolean activar(int id) {

        String sql =
                "UPDATE vehiculo "
                + "SET estado = 'DISPONIBLE' "
                + "WHERE id_vehiculo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}