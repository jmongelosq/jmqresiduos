package com.dao;

import com.config.Conexion;
import com.model.TipoResiduo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoResiduoDAO {

    private final Conexion cn = new Conexion();

    // LISTAR TODOS

    public List<TipoResiduo> listar() {

        List<TipoResiduo> lista = new ArrayList<>();

        String sql =
                "SELECT id_tipo, nombre, descripcion, estado " +
                "FROM tipos_residuos " +
                "ORDER BY id_tipo";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                TipoResiduo tipo = new TipoResiduo();

                tipo.setId_tipo(
                        rs.getInt("id_tipo")
                );

                tipo.setNombre(
                        rs.getString("nombre")
                );

                tipo.setDescripcion(
                        rs.getString("descripcion")
                );

                tipo.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(tipo);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // LISTAR ACTIVOS

    public List<TipoResiduo> listarActivos() {

        List<TipoResiduo> lista = new ArrayList<>();

        String sql =
                "SELECT id_tipo, nombre, descripcion, estado " +
                "FROM tipos_residuos " +
                "WHERE estado = TRUE " +
                "ORDER BY nombre";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                TipoResiduo tipo = new TipoResiduo();

                tipo.setId_tipo(
                        rs.getInt("id_tipo")
                );

                tipo.setNombre(
                        rs.getString("nombre")
                );

                tipo.setDescripcion(
                        rs.getString("descripcion")
                );

                tipo.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(tipo);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID

    public TipoResiduo buscarPorId(int id) {

        TipoResiduo tipo = null;

        String sql =
                "SELECT id_tipo, nombre, descripcion, estado " +
                "FROM tipos_residuos " +
                "WHERE id_tipo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    tipo = new TipoResiduo();

                    tipo.setId_tipo(
                            rs.getInt("id_tipo")
                    );

                    tipo.setNombre(
                            rs.getString("nombre")
                    );

                    tipo.setDescripcion(
                            rs.getString("descripcion")
                    );

                    tipo.setEstado(
                            rs.getBoolean("estado")
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return tipo;
    }

    // INSERTAR

    public boolean insertar(TipoResiduo tipo) {

        String sql =
                "INSERT INTO tipos_residuos " +
                "(nombre, descripcion, estado) " +
                "VALUES (?, ?, TRUE)";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    tipo.getNombre()
            );

            ps.setString(
                    2,
                    tipo.getDescripcion()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // ACTUALIZAR

    public boolean actualizar(TipoResiduo tipo) {

        String sql =
                "UPDATE tipos_residuos SET " +
                "nombre = ?, " +
                "descripcion = ?, " +
                "estado = ? " +
                "WHERE id_tipo = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    tipo.getNombre()
            );

            ps.setString(
                    2,
                    tipo.getDescripcion()
            );

            ps.setBoolean(
                    3,
                    tipo.isEstado()
            );

            ps.setInt(
                    4,
                    tipo.getId_tipo()
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
                "UPDATE tipos_residuos " +
                "SET estado = FALSE " +
                "WHERE id_tipo = ?";

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
                "UPDATE tipos_residuos " +
                "SET estado = TRUE " +
                "WHERE id_tipo = ?";

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