package com.dao;

import com.config.Conexion;
import com.model.Barrio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarrioDAO {

    private final Conexion cn = new Conexion();

    // LISTAR TODOS

    public List<Barrio> listar() {

        List<Barrio> lista = new ArrayList<>();

        String sql =
                "SELECT id_barrio, nombre, descripcion, estado " +
                "FROM barrio " +
                "ORDER BY id_barrio";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Barrio barrio = new Barrio();

                barrio.setId_barrio(
                        rs.getInt("id_barrio")
                );

                barrio.setNombre(
                        rs.getString("nombre")
                );

                barrio.setDescripcion(
                        rs.getString("descripcion")
                );

                barrio.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(barrio);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }


    // LISTAR SOLAMENTE ACTIVOS

    public List<Barrio> listarActivos() {

        List<Barrio> lista = new ArrayList<>();

        String sql =
                "SELECT id_barrio, nombre, descripcion, estado " +
                "FROM barrio " +
                "WHERE estado = TRUE " +
                "ORDER BY nombre";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Barrio barrio = new Barrio();

                barrio.setId_barrio(
                        rs.getInt("id_barrio")
                );

                barrio.setNombre(
                        rs.getString("nombre")
                );

                barrio.setDescripcion(
                        rs.getString("descripcion")
                );

                barrio.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(barrio);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }


    // BUSCAR POR ID

    public Barrio buscarPorId(int id) {

        Barrio barrio = null;

        String sql =
                "SELECT id_barrio, nombre, descripcion, estado " +
                "FROM barrio " +
                "WHERE id_barrio = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    barrio = new Barrio();

                    barrio.setId_barrio(
                            rs.getInt("id_barrio")
                    );

                    barrio.setNombre(
                            rs.getString("nombre")
                    );

                    barrio.setDescripcion(
                            rs.getString("descripcion")
                    );

                    barrio.setEstado(
                            rs.getBoolean("estado")
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return barrio;
    }


    // INSERTAR

    public boolean insertar(Barrio barrio) {

        String sql =
                "INSERT INTO barrio " +
                "(nombre, descripcion, estado) " +
                "VALUES (?, ?, TRUE)";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    barrio.getNombre()
            );

            ps.setString(
                    2,
                    barrio.getDescripcion()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // ACTUALIZAR

    public boolean actualizar(Barrio barrio) {

        String sql =
                "UPDATE barrio SET " +
                "nombre = ?, " +
                "descripcion = ?, " +
                "estado = ? " +
                "WHERE id_barrio = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    barrio.getNombre()
            );

            ps.setString(
                    2,
                    barrio.getDescripcion()
            );

            ps.setBoolean(
                    3,
                    barrio.isEstado()
            );

            ps.setInt(
                    4,
                    barrio.getId_barrio()
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
                "UPDATE barrio " +
                "SET estado = FALSE " +
                "WHERE id_barrio = ?";

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
                "UPDATE barrio " +
                "SET estado = TRUE " +
                "WHERE id_barrio = ?";

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