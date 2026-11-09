package com.dao;

import com.config.Conexion;
import com.model.Tarifa;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TarifaDAO {

    private final Conexion conexion = new Conexion();

    // LISTAR TODAS LAS TARIFAS

    public List<Tarifa> listar() {

        List<Tarifa> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "id_tarifa, "
                + "nombre, "
                + "descripcion, "
                + "monto, "
                + "periodicidad, "
                + "fecha_inicio, "
                + "fecha_fin, "
                + "estado "
                + "FROM tarifa "
                + "ORDER BY id_tarifa ";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Tarifa t = new Tarifa();

                t.setIdTarifa(rs.getInt("id_tarifa"));
                t.setNombre(rs.getString("nombre"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setMonto(rs.getBigDecimal("monto"));
                t.setPeriodicidad(rs.getString("periodicidad"));
                t.setFechaInicio(rs.getDate("fecha_inicio"));
                t.setFechaFin(rs.getDate("fecha_fin"));
                t.setEstado(rs.getBoolean("estado"));

                lista.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // LISTAR TARIFAS ACTIVAS

    public List<Tarifa> listarActivas() {

        List<Tarifa> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "id_tarifa, "
                + "nombre, "
                + "descripcion, "
                + "monto, "
                + "periodicidad, "
                + "fecha_inicio, "
                + "fecha_fin, "
                + "estado "
                + "FROM tarifa "
                + "WHERE estado = TRUE "
                + "ORDER BY nombre ";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Tarifa t = new Tarifa();

                t.setIdTarifa(rs.getInt("id_tarifa"));
                t.setNombre(rs.getString("nombre"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setMonto(rs.getBigDecimal("monto"));
                t.setPeriodicidad(rs.getString("periodicidad"));
                t.setFechaInicio(rs.getDate("fecha_inicio"));
                t.setFechaFin(rs.getDate("fecha_fin"));
                t.setEstado(rs.getBoolean("estado"));

                lista.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID

    public Tarifa buscarPorId(int id) {

        Tarifa t = null;

        String sql =
                "SELECT "
                + "id_tarifa, "
                + "nombre, "
                + "descripcion, "
                + "monto, "
                + "periodicidad, "
                + "fecha_inicio, "
                + "fecha_fin, "
                + "estado "
                + "FROM tarifa "
                + "WHERE id_tarifa = ? ";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    t = new Tarifa();

                    t.setIdTarifa(rs.getInt("id_tarifa"));
                    t.setNombre(rs.getString("nombre"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setMonto(rs.getBigDecimal("monto"));
                    t.setPeriodicidad(rs.getString("periodicidad"));
                    t.setFechaInicio(rs.getDate("fecha_inicio"));
                    t.setFechaFin(rs.getDate("fecha_fin"));
                    t.setEstado(rs.getBoolean("estado"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    // INSERTAR

    public boolean insertar(Tarifa t) {

        String sql =
                "INSERT INTO tarifa "
                + "( "
                + "nombre, "
                + "descripcion, "
                + "monto, "
                + "periodicidad, "
                + "fecha_inicio, "
                + "fecha_fin, "
                + "estado "
                + ") "
                + "VALUES (?, ?, ?, ?, ?, ?, TRUE) ";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setBigDecimal(3, t.getMonto());
            ps.setString(4, t.getPeriodicidad());
            ps.setDate(5, t.getFechaInicio());

            if (t.getFechaFin() != null) {
                ps.setDate(6, t.getFechaFin());
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR

    public boolean actualizar(Tarifa t) {

        String sql =
                "UPDATE tarifa "
                + "SET "
                + "nombre = ?, "
                + "descripcion = ?, "
                + "monto = ?, "
                + "periodicidad = ?, "
                + "fecha_inicio = ?, "
                + "fecha_fin = ? "
                + "WHERE id_tarifa = ? ";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setBigDecimal(3, t.getMonto());
            ps.setString(4, t.getPeriodicidad());
            ps.setDate(5, t.getFechaInicio());

            if (t.getFechaFin() != null) {
                ps.setDate(6, t.getFechaFin());
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }

            ps.setInt(7, t.getIdTarifa());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // BAJA LÓGICA

    public boolean desactivar(int id) {

        String sql =
                "UPDATE tarifa "
                + "SET estado = FALSE "
                + "WHERE id_tarifa = ? ";

        try (Connection con = conexion.getConexion();
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
                "UPDATE tarifa "
                + "SET estado = TRUE "
                + "WHERE id_tarifa = ? ";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
