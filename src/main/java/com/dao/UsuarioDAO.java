package com.dao;

import com.config.Conexion;
import com.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    Conexion cn = new Conexion();

    // LISTAR TODOS LOS USUARIOS

    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();

        String sql =
                "SELECT " +
                "u.id_usuario, " +
                "u.nombre_usuario, " +
                "u.nombre_completo, " +
                "u.correo, " +
                "u.clave, " +
                "u.id_personal, " +
                "u.id_rol, " +
                "u.estado, " +
                "u.fecha_creacion, " +
                "p.nombre || ' ' || p.apellido AS nombre_personal, " +
                "r.nombre_rol " +
                "FROM usuarios u " +
                "LEFT JOIN personal p ON u.id_personal = p.id_personal " +
                "LEFT JOIN roles r ON u.id_rol = r.id_rol " +
                "ORDER BY u.id_usuario";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre_usuario(rs.getString("nombre_usuario"));
                u.setNombre_completo(rs.getString("nombre_completo"));
                u.setCorreo(rs.getString("correo"));

                u.setId_personal(rs.getInt("id_personal"));
                u.setId_rol(rs.getInt("id_rol"));

                u.setNombre_personal(rs.getString("nombre_personal"));
                u.setNombre_rol(rs.getString("nombre_rol"));

                u.setEstado(rs.getBoolean("estado"));

                if (rs.getTimestamp("fecha_creacion") != null) {
                    u.setFecha_creacion(
                            rs.getTimestamp("fecha_creacion").toString()
                    );
                }

                lista.add(u);
            }

            System.out.println("Usuarios encontrados: " + lista.size());

        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAO.listar(): "
                    + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR USUARIO POR ID

    public Usuario buscarPorId(int id) {

        Usuario u = null;

        String sql =
                "SELECT " +
                "u.id_usuario, " +
                "u.nombre_usuario, " +
                "u.nombre_completo, " +
                "u.correo, " +
                "u.clave, " +
                "u.id_personal, " +
                "u.id_rol, " +
                "u.estado, " +
                "u.fecha_creacion, " +
                "p.nombre || ' ' || p.apellido AS nombre_personal, " +
                "r.nombre_rol " +
                "FROM usuarios u " +
                "LEFT JOIN personal p ON u.id_personal = p.id_personal " +
                "LEFT JOIN roles r ON u.id_rol = r.id_rol " +
                "WHERE u.id_usuario = ?";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    u = new Usuario();

                    u.setId_usuario(rs.getInt("id_usuario"));
                    u.setNombre_usuario(rs.getString("nombre_usuario"));
                    u.setNombre_completo(rs.getString("nombre_completo"));
                    u.setCorreo(rs.getString("correo"));
                    u.setClave(rs.getString("clave"));

                    u.setId_personal(rs.getInt("id_personal"));
                    u.setId_rol(rs.getInt("id_rol"));

                    u.setNombre_personal(
                            rs.getString("nombre_personal")
                    );

                    u.setNombre_rol(
                            rs.getString("nombre_rol")
                    );

                    u.setEstado(rs.getBoolean("estado"));

                    if (rs.getTimestamp("fecha_creacion") != null) {
                        u.setFecha_creacion(
                                rs.getTimestamp("fecha_creacion").toString()
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error en UsuarioDAO.buscarPorId(): "
                    + e.getMessage()
            );
            e.printStackTrace();
        }

        return u;
    }

    // INSERTAR USUARIO

    public boolean agregar(Usuario u) {

        String sql =
                "INSERT INTO usuarios " +
                "(nombre_usuario, nombre_completo, correo, clave, " +
                "id_personal, id_rol, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre_usuario());
            ps.setString(2, u.getNombre_completo());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getClave());

            if (u.getId_personal() > 0) {
                ps.setInt(5, u.getId_personal());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setInt(6, u.getId_rol());
            ps.setBoolean(7, true);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error en UsuarioDAO.agregar(): "
                    + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }

    // ACTUALIZAR USUARIO

    public boolean actualizar(Usuario u) {

        String sql;

        boolean cambiarClave =
                u.getClave() != null &&
                !u.getClave().trim().isEmpty();

        if (cambiarClave) {

            sql =
                    "UPDATE usuarios SET " +
                    "nombre_usuario=?, " +
                    "nombre_completo=?, " +
                    "correo=?, " +
                    "clave=?, " +
                    "id_personal=?, " +
                    "id_rol=? " +
                    "WHERE id_usuario=?";

        } else {

            sql =
                    "UPDATE usuarios SET " +
                    "nombre_usuario=?, " +
                    "nombre_completo=?, " +
                    "correo=?, " +
                    "id_personal=?, " +
                    "id_rol=? " +
                    "WHERE id_usuario=?";
        }

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int index = 1;

            ps.setString(index++, u.getNombre_usuario());
            ps.setString(index++, u.getNombre_completo());
            ps.setString(index++, u.getCorreo());

            if (cambiarClave) {
                ps.setString(index++, u.getClave());
            }

            if (u.getId_personal() > 0) {
                ps.setInt(index++, u.getId_personal());
            } else {
                ps.setNull(index++, Types.INTEGER);
            }

            ps.setInt(index++, u.getId_rol());

            ps.setInt(index, u.getId_usuario());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error en UsuarioDAO.actualizar(): "
                    + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }

    // DESACTIVAR USUARIO

    public boolean desactivar(int id) {

        String sql =
                "UPDATE usuarios " +
                "SET estado = FALSE " +
                "WHERE id_usuario = ?";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error en UsuarioDAO.desactivar(): "
                    + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }

    // ACTIVAR USUARIO

    public boolean activar(int id) {

        String sql =
                "UPDATE usuarios " +
                "SET estado = TRUE " +
                "WHERE id_usuario = ?";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error en UsuarioDAO.activar(): "
                    + e.getMessage()
            );

            e.printStackTrace();

            return false;
        }
    }

    // LISTAR PERSONAL ACTIVO

    public List<Usuario> listarPersonal() {

        List<Usuario> lista = new ArrayList<>();

        String sql =
                "SELECT id_personal, nombre, apellido " +
                "FROM personal " +
                "WHERE estado = TRUE " +
                "ORDER BY nombre, apellido";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId_personal(
                        rs.getInt("id_personal")
                );

                u.setNombre_personal(
                        rs.getString("nombre") +
                        " " +
                        rs.getString("apellido")
                );

                lista.add(u);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error en listarPersonal(): "
                    + e.getMessage()
            );

            e.printStackTrace();
        }

        return lista;
    }

    // LISTAR ROLES ACTIVOS

    public List<Usuario> listarRoles() {

        List<Usuario> lista = new ArrayList<>();

        String sql =
                "SELECT id_rol, nombre_rol " +
                "FROM roles " +
                "WHERE estado = TRUE " +
                "ORDER BY nombre_rol";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId_rol(
                        rs.getInt("id_rol")
                );

                u.setNombre_rol(
                        rs.getString("nombre_rol")
                );

                lista.add(u);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error en listarRoles(): "
                    + e.getMessage()
            );

            e.printStackTrace();
        }

        return lista;
    }
}