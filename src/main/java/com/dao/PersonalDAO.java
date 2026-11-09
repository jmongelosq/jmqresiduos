package com.dao;

import com.config.Conexion;
import com.model.Personal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonalDAO {

    Conexion cn = new Conexion();

    // LISTAR TODOS
    public List<Personal> listar() {

        List<Personal> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "p.id_personal, "
                + "p.nombre, "
                + "p.apellido, "
                + "p.cedula, "
                + "p.telefono, "
                + "p.direccion, "
                + "p.correo, "
                + "p.id_cargo, "
                + "p.id_barrio AS id_barrios, "
                + "CASE WHEN p.estado THEN 'ACTIVO' ELSE 'INACTIVO' END AS estado, "
                + "c.nombre_cargo, "
                + "b.nombre AS nombre_barrio "
                + "FROM personal p "
                + "LEFT JOIN cargo c "
                + "ON p.id_cargo = c.id_cargo "
                + "LEFT JOIN barrio b "
                + "ON p.id_barrio = b.id_barrio "
                + "ORDER BY p.id_personal";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Personal p = new Personal();

                p.setId_personal(
                        rs.getInt("id_personal")
                );

                p.setNombre(
                        rs.getString("nombre")
                );

                p.setApellido(
                        rs.getString("apellido")
                );

                p.setCedula(
                        rs.getString("cedula")
                );

                p.setTelefono(
                        rs.getString("telefono")
                );

                p.setDireccion(
                        rs.getString("direccion")
                );

                p.setCorreo(
                        rs.getString("correo")
                );

                p.setId_cargo(
                        rs.getInt("id_cargo")
                );

                p.setId_barrios(
                        rs.getInt("id_barrios")
                );

                p.setEstado(
                        rs.getString("estado")
                );

                p.setNombre_cargo(
                        rs.getString("nombre_cargo")
                );

                p.setNombre_barrio(
                        rs.getString("nombre_barrio")
                );

                lista.add(p);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // LISTAR ACTIVOS
    public List<Personal> listarActivos() {

        List<Personal> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "p.id_personal, "
                + "p.nombre, "
                + "p.apellido, "
                + "p.cedula, "
                + "p.telefono, "
                + "p.direccion, "
                + "p.correo, "
                + "p.id_cargo, "
                + "p.id_barrio AS id_barrios, "
                + "CASE WHEN p.estado THEN 'ACTIVO' ELSE 'INACTIVO' END AS estado, "
                + "c.nombre_cargo, "
                + "b.nombre AS nombre_barrio "
                + "FROM personal p "
                + "LEFT JOIN cargo c "
                + "ON p.id_cargo = c.id_cargo "
                + "LEFT JOIN barrio b "
                + "ON p.id_barrio = b.id_barrio "
                + "WHERE p.estado = TRUE "
                + "ORDER BY p.nombre, p.apellido";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Personal p = new Personal();

                p.setId_personal(
                        rs.getInt("id_personal")
                );

                p.setNombre(
                        rs.getString("nombre")
                );

                p.setApellido(
                        rs.getString("apellido")
                );

                p.setCedula(
                        rs.getString("cedula")
                );

                p.setTelefono(
                        rs.getString("telefono")
                );

                p.setDireccion(
                        rs.getString("direccion")
                );

                p.setCorreo(
                        rs.getString("correo")
                );

                p.setId_cargo(
                        rs.getInt("id_cargo")
                );

                p.setId_barrios(
                        rs.getInt("id_barrios")
                );

                p.setEstado(
                        rs.getString("estado")
                );

                p.setNombre_cargo(
                        rs.getString("nombre_cargo")
                );

                p.setNombre_barrio(
                        rs.getString("nombre_barrio")
                );

                lista.add(p);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // BUSCAR POR ID
    public Personal buscarPorId(int id) {

        Personal p = null;

        String sql =
                "SELECT "
                + "p.id_personal, "
                + "p.nombre, "
                + "p.apellido, "
                + "p.cedula, "
                + "p.telefono, "
                + "p.direccion, "
                + "p.correo, "
                + "p.id_cargo, "
                + "p.id_barrio AS id_barrios, "
                + "CASE WHEN p.estado THEN 'ACTIVO' ELSE 'INACTIVO' END AS estado, "
                + "c.nombre_cargo, "
                + "b.nombre AS nombre_barrio "
                + "FROM personal p "
                + "LEFT JOIN cargo c "
                + "ON p.id_cargo = c.id_cargo "
                + "LEFT JOIN barrio b "
                + "ON p.id_barrio = b.id_barrio "
                + "WHERE p.id_personal = ?";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    p = new Personal();

                    p.setId_personal(
                            rs.getInt("id_personal")
                    );

                    p.setNombre(
                            rs.getString("nombre")
                    );

                    p.setApellido(
                            rs.getString("apellido")
                    );

                    p.setCedula(
                            rs.getString("cedula")
                    );

                    p.setTelefono(
                            rs.getString("telefono")
                    );

                    p.setDireccion(
                            rs.getString("direccion")
                    );

                    p.setCorreo(
                            rs.getString("correo")
                    );

                    p.setId_cargo(
                            rs.getInt("id_cargo")
                    );

                    p.setId_barrios(
                            rs.getInt("id_barrios")
                    );

                    p.setEstado(
                            rs.getString("estado")
                    );

                    p.setNombre_cargo(
                            rs.getString("nombre_cargo")
                    );

                    p.setNombre_barrio(
                            rs.getString("nombre_barrio")
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return p;
    }


    // INSERTAR
    public boolean insertar(Personal p) {

        String sql =
                "INSERT INTO personal "
                + "(nombre, apellido, cedula, telefono, direccion, correo, "
                + "id_cargo, id_barrio, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getCedula());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getDireccion());
            ps.setString(6, p.getCorreo());
            ps.setInt(7, p.getId_cargo());
            if (p.getId_barrios() > 0) {
                ps.setInt(8, p.getId_barrios());
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }
            ps.setBoolean(9, estaActivo(p));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // ACTUALIZAR
    public boolean actualizar(Personal p) {

        String sql =
                "UPDATE personal SET "
                + "nombre = ?, "
                + "apellido = ?, "
                + "cedula = ?, "
                + "telefono = ?, "
                + "direccion = ?, "
                + "correo = ?, "
                + "id_cargo = ?, "
                + "id_barrio = ?, "
                + "estado = ? "
                + "WHERE id_personal = ?";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getCedula());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getDireccion());
            ps.setString(6, p.getCorreo());
            ps.setInt(7, p.getId_cargo());
            if (p.getId_barrios() > 0) {
                ps.setInt(8, p.getId_barrios());
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }
            ps.setBoolean(9, estaActivo(p));
            ps.setInt(10, p.getId_personal());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // BAJA LOGICA
    public boolean eliminar(int id) {

        String sql =
                "UPDATE personal "
                + "SET estado = FALSE "
                + "WHERE id_personal = ?";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

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
                "UPDATE personal "
                + "SET estado = TRUE "
                + "WHERE id_personal = ?";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    public boolean existeCedula(String cedula, int idExcluir) {

        String sql =
                "SELECT 1 FROM personal "
                + "WHERE TRIM(cedula) = TRIM(?) "
                + "AND id_personal <> ?";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ps.setInt(2, idExcluir);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    private boolean estaActivo(Personal p) {

        return p.getEstado() == null
                || !"INACTIVO".equalsIgnoreCase(p.getEstado().trim());
    }
}
