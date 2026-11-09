package com.dao;

import com.config.Conexion;
import com.model.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    Conexion cn = new Conexion();

    // LISTAR TODOS

    public List<Rol> listar() {

        List<Rol> lista = new ArrayList<>();

        String sql =
                "SELECT id_rol, nombre_rol, descripcion, estado " +
                "FROM roles " +
                "ORDER BY id_rol";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Rol r = new Rol();

                r.setId_rol(
                        rs.getInt("id_rol")
                );

                r.setNombre_rol(
                        rs.getString("nombre_rol")
                );

                r.setDescripcion(
                        rs.getString("descripcion")
                );

                r.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(r);
            }

            System.out.println(
                    "Roles encontrados: " + lista.size()
            );

        } catch (SQLException e) {

            System.err.println(
                    "Error en RolDAO.listar(): "
                    + e.getMessage()
            );

            e.printStackTrace();
        }

        return lista;
    }

    // LISTAR ROLES ACTIVOS

    public List<Rol> listarActivos() {

        List<Rol> lista = new ArrayList<>();

        String sql =
                "SELECT id_rol, nombre_rol, descripcion, estado " +
                "FROM roles " +
                "WHERE estado = TRUE " +
                "ORDER BY nombre_rol";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Rol r = new Rol();

                r.setId_rol(
                        rs.getInt("id_rol")
                );

                r.setNombre_rol(
                        rs.getString("nombre_rol")
                );

                r.setDescripcion(
                        rs.getString("descripcion")
                );

                r.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(r);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error en RolDAO.listarActivos(): "
                    + e.getMessage()
            );

            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID

    public Rol buscarPorId(int id) {

        Rol r = null;

        String sql =
                "SELECT id_rol, nombre_rol, descripcion, estado " +
                "FROM roles " +
                "WHERE id_rol = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    r = new Rol();

                    r.setId_rol(
                            rs.getInt("id_rol")
                    );

                    r.setNombre_rol(
                            rs.getString("nombre_rol")
                    );

                    r.setDescripcion(
                            rs.getString("descripcion")
                    );

                    r.setEstado(
                            rs.getBoolean("estado")
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error en RolDAO.buscarPorId(): "
                    + e.getMessage()
            );

            e.printStackTrace();
        }

        return r;
    }

    // INSERTAR

    public boolean agregar(Rol r) {

        String sql =
                "INSERT INTO roles " +
                "(nombre_rol, descripcion, estado) " +
                "VALUES (?, ?, TRUE)";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    r.getNombre_rol()
            );

            ps.setString(
                    2,
                    r.getDescripcion()
            );

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error en RolDAO.agregar(): "
                    + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }

    // ACTUALIZAR

    public boolean actualizar(Rol r) {

        String sql =
                "UPDATE roles SET " +
                "nombre_rol = ?, " +
                "descripcion = ? " +
                "WHERE id_rol = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    r.getNombre_rol()
            );

            ps.setString(
                    2,
                    r.getDescripcion()
            );

            ps.setInt(
                    3,
                    r.getId_rol()
            );

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error en RolDAO.actualizar(): "
                    + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }

    // DESACTIVAR

    public boolean desactivar(int id) {

        String sql =
                "UPDATE roles " +
                "SET estado = FALSE " +
                "WHERE id_rol = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error en RolDAO.desactivar(): "
                    + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }

    // ACTIVAR

    public boolean activar(int id) {

        String sql =
                "UPDATE roles " +
                "SET estado = TRUE " +
                "WHERE id_rol = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error en RolDAO.activar(): "
                    + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }
}