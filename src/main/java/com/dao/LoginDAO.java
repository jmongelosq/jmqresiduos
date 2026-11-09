package com.dao;

import com.config.Conexion;
import com.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    private final Conexion cn = new Conexion();

    /**
     * Valida las credenciales del usuario.
     *
     * El usuario debe:
     * - Existir en la tabla usuarios.
     * - Tener la contraseña correcta.
     * - Estar activo.
     * - Tener un rol activo.
     */
    public Usuario validarUsuario(String nombreUsuario, String clave) {

        Usuario u = null;

        String sql =
                "SELECT " +
                "    u.id_usuario, " +
                "    u.nombre_usuario, " +
                "    u.nombre_completo, " +
                "    u.correo, " +
                "    u.clave, " +
                "    u.id_personal, " +
                "    u.id_rol, " +
                "    u.estado, " +
                "    r.nombre_rol " +
                "FROM usuarios u " +
                "INNER JOIN roles r " +
                "    ON u.id_rol = r.id_rol " +
                "WHERE u.nombre_usuario = ? " +
                "  AND u.clave = ? " +
                "  AND u.estado = TRUE " +
                "  AND r.estado = TRUE";

        try (Connection conn = cn.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    u = new Usuario();

                    // DATOS PRINCIPALES

                    u.setId_usuario(
                            rs.getInt("id_usuario")
                    );

                    u.setNombre_usuario(
                            rs.getString("nombre_usuario")
                    );

                    u.setNombre_completo(
                            rs.getString("nombre_completo")
                    );

                    u.setCorreo(
                            rs.getString("correo")
                    );

                    u.setClave(
                            rs.getString("clave")
                    );

                    // RELACIONES

                    u.setId_personal(
                            rs.getInt("id_personal")
                    );

                    u.setId_rol(
                            rs.getInt("id_rol")
                    );

                    // ROL

                    u.setNombre_rol(
                            rs.getString("nombre_rol")
                    );

                    // ESTADO

                    u.setEstado(
                            rs.getBoolean("estado")
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error en LoginDAO.validarUsuario: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }

        return u;
    }
}