package com.dao;

import com.config.Conexion;
import com.model.Cargo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {

    private final Conexion cn = new Conexion();

    // LISTAR TODOS

    public List<Cargo> listar() {

        List<Cargo> lista = new ArrayList<>();

        String sql =
                "SELECT id_cargo, nombre_cargo, descripcion, estado " +
                "FROM cargo " +
                "ORDER BY id_cargo";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Cargo cargo = new Cargo();

                cargo.setId_cargo(
                        rs.getInt("id_cargo")
                );

                cargo.setNombre_cargo(
                        rs.getString("nombre_cargo")
                );

                cargo.setDescripcion(
                        rs.getString("descripcion")
                );

                cargo.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(cargo);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // LISTAR SOLAMENTE CARGOS ACTIVOS

    public List<Cargo> listarActivos() {

        List<Cargo> lista = new ArrayList<>();

        String sql =
                "SELECT id_cargo, nombre_cargo, descripcion, estado " +
                "FROM cargo " +
                "WHERE estado = TRUE " +
                "ORDER BY nombre_cargo";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Cargo cargo = new Cargo();

                cargo.setId_cargo(
                        rs.getInt("id_cargo")
                );

                cargo.setNombre_cargo(
                        rs.getString("nombre_cargo")
                );

                cargo.setDescripcion(
                        rs.getString("descripcion")
                );

                cargo.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(cargo);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID

    public Cargo buscarPorId(int id) {

        Cargo cargo = null;

        String sql =
                "SELECT id_cargo, nombre_cargo, descripcion, estado " +
                "FROM cargo " +
                "WHERE id_cargo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    cargo = new Cargo();

                    cargo.setId_cargo(
                            rs.getInt("id_cargo")
                    );

                    cargo.setNombre_cargo(
                            rs.getString("nombre_cargo")
                    );

                    cargo.setDescripcion(
                            rs.getString("descripcion")
                    );

                    cargo.setEstado(
                            rs.getBoolean("estado")
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return cargo;
    }

    // INSERTAR

    public boolean insertar(Cargo cargo) {

        String sql =
                "INSERT INTO cargo " +
                "(nombre_cargo, descripcion, estado) " +
                "VALUES (?, ?, TRUE)";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    cargo.getNombre_cargo()
            );

            ps.setString(
                    2,
                    cargo.getDescripcion()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // ACTUALIZAR

    public boolean actualizar(Cargo cargo) {

        String sql =
                "UPDATE cargo SET " +
                "nombre_cargo = ?, " +
                "descripcion = ?, " +
                "estado = ? " +
                "WHERE id_cargo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    cargo.getNombre_cargo()
            );

            ps.setString(
                    2,
                    cargo.getDescripcion()
            );

            ps.setBoolean(
                    3,
                    cargo.isEstado()
            );

            ps.setInt(
                    4,
                    cargo.getId_cargo()
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
                "UPDATE cargo " +
                "SET estado = FALSE " +
                "WHERE id_cargo = ?";

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
                "UPDATE cargo " +
                "SET estado = TRUE " +
                "WHERE id_cargo = ?";

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