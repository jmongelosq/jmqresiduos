package com.dao;

import com.config.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

    private final Conexion conexion = new Conexion();

    // USUARIOS ACTIVOS
    public int contarUsuarios() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM usuarios "
                + "WHERE estado = TRUE ";

        return ejecutarConteo(sql);
    }

    // PERSONAL ACTIVO
    public int contarPersonal() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM personal "
                + "WHERE estado = TRUE ";

        return ejecutarConteo(sql);
    }

    // CLIENTES ACTIVOS
    public int contarClientes() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM clientes "
                + "WHERE estado = TRUE ";

        return ejecutarConteo(sql);
    }

    // VEHÍCULOS
    public int contarVehiculos() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM vehiculo ";

        return ejecutarConteo(sql);
    }

    // RUTAS ACTIVAS
    public int contarRutas() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM ruta "
                + "WHERE estado = TRUE ";

        return ejecutarConteo(sql);
    }

    // TIPOS DE RESIDUOS ACTIVOS
    public int contarTiposResiduos() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM tipos_residuos "
                + "WHERE estado = TRUE ";

        return ejecutarConteo(sql);
    }

    // ASIGNACIONES (SIN LAS CANCELADAS)
    public int contarAsignaciones() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM asignacion "
                + "WHERE estado <> 'CANCELADA' ";

        return ejecutarConteo(sql);
    }

    // RECOLECCIONES ACTIVAS
    public int contarRecolecciones() {

        String sql =
                "SELECT COUNT(*) "
                + "FROM recoleccion "
                + "WHERE estado = TRUE ";

        return ejecutarConteo(sql);
    }

    // TOTAL DE RESIDUOS RECOLECTADOS EN KG
    public double totalResiduosRecolectadosKg() {

        String sql =
                "SELECT COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN UPPER(TRIM(unidad_medida)) = 'TON' "
                + "THEN cantidad_recolectada * 1000 "
                + "WHEN UPPER(TRIM(unidad_medida)) = 'KG' "
                + "THEN cantidad_recolectada "
                + "ELSE cantidad_recolectada "
                + "END "
                + "), "
                + "0 "
                + ") "
                + "FROM recoleccion "
                + "WHERE estado = TRUE ";

        try (
            Connection con = conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // TOTAL DE RESIDUOS RECOLECTADOS EN TONELADAS
    public double totalResiduosRecolectadosTon() {

        return totalResiduosRecolectadosKg() / 1000.0;
    }

    // VEHÍCULOS POR ESTADO
    public int contarVehiculosPorEstado(String estado) {

        String sql =
                "SELECT COUNT(*) "
                + "FROM vehiculo "
                + "WHERE estado = ? ";

        try (
            Connection con = conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, estado);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // MÉTODO GENERAL PARA CONTADORES
    private int ejecutarConteo(String sql) {

        try (
            Connection con = conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}