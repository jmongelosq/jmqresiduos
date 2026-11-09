package com.dao;

import com.config.Conexion;
import com.model.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloDAO {

    private final Conexion cn = new Conexion();

    // LISTAR TODOS

    public List<Modelo> listar() {

        List<Modelo> lista = new ArrayList<>();

        String sql =
                "SELECT id_modelo, " +
                "nombre_modelo AS nombre, " +
                "descripcion, " +
                "estado " +
                "FROM modelo " +
                "ORDER BY id_modelo";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Modelo modelo = new Modelo();

                modelo.setId_modelo(
                        rs.getInt("id_modelo")
                );

                modelo.setNombre(
                        rs.getString("nombre")
                );

                modelo.setDescripcion(
                        rs.getString("descripcion")
                );

                modelo.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(modelo);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // LISTAR ACTIVOS

    public List<Modelo> listarActivos() {

        List<Modelo> lista = new ArrayList<>();

        String sql =
                "SELECT id_modelo, " +
                "nombre_modelo AS nombre, " +
                "descripcion, " +
                "estado " +
                "FROM modelo " +
                "WHERE estado = TRUE " +
                "ORDER BY nombre_modelo";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Modelo modelo = new Modelo();

                modelo.setId_modelo(
                        rs.getInt("id_modelo")
                );

                modelo.setNombre(
                        rs.getString("nombre")
                );

                modelo.setDescripcion(
                        rs.getString("descripcion")
                );

                modelo.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(modelo);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID

    public Modelo buscarPorId(int id) {

        Modelo modelo = null;

        String sql =
                "SELECT id_modelo, " +
                "nombre_modelo AS nombre, " +
                "descripcion, " +
                "estado " +
                "FROM modelo " +
                "WHERE id_modelo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    modelo = new Modelo();

                    modelo.setId_modelo(
                            rs.getInt("id_modelo")
                    );

                    modelo.setNombre(
                            rs.getString("nombre")
                    );

                    modelo.setDescripcion(
                            rs.getString("descripcion")
                    );

                    modelo.setEstado(
                            rs.getBoolean("estado")
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return modelo;
    }

    // INSERTAR

    public boolean insertar(Modelo modelo) {

        String sql =
                "INSERT INTO modelo " +
                "(nombre_modelo, descripcion, estado) " +
                "VALUES (?, ?, TRUE)";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    modelo.getNombre()
            );

            ps.setString(
                    2,
                    modelo.getDescripcion()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // ACTUALIZAR

    public boolean actualizar(Modelo modelo) {

        String sql =
                "UPDATE modelo SET " +
                "nombre_modelo = ?, " +
                "descripcion = ?, " +
                "estado = ? " +
                "WHERE id_modelo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    modelo.getNombre()
            );

            ps.setString(
                    2,
                    modelo.getDescripcion()
            );

            ps.setBoolean(
                    3,
                    modelo.isEstado()
            );

            ps.setInt(
                    4,
                    modelo.getId_modelo()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // BAJA LÓGICA

    public boolean eliminar(int id) {

        String sql =
                "UPDATE modelo " +
                "SET estado = FALSE " +
                "WHERE id_modelo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // ACTIVAR

    public boolean activar(int id) {

        String sql =
                "UPDATE modelo " +
                "SET estado = TRUE " +
                "WHERE id_modelo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
}