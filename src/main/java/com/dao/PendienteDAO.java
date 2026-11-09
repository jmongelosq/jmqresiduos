package com.dao;

import com.config.Conexion;
import com.model.Pendiente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PendienteDAO {

    private final Conexion conexion = new Conexion();


    // LISTAR PENDIENTES
    //
    // IMPORTANTE:
    //
    // NO se guarda ningún pendiente en la base de datos.
    //
    // El saldo se calcula automáticamente:
    //
    // TOTAL FACTURA - SUMA DE PAGOS PAGADOS
    //
    // Una factura puede tener varios pagos.

    public List<Pendiente> listar() {

        List<Pendiente> lista = new ArrayList<>();


        String sql =
                "SELECT "
                + "f.id_factura, "
                + "f.numero_factura, "
                + "f.fecha_emision, "
                + "f.condicion_venta, "
                + "f.periodo, "
                + "f.plazo_credito, "
                + "f.fecha_vencimiento, "
                + "f.total AS total_factura, "
                + "f.estado AS estado_factura, "
                + "f.id_cliente, "
                + "c.nombre AS nombre_cliente, "
                + "c.apellido AS apellido_cliente, "
                + "c.cedula AS cedula_cliente, "
                + "COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN p.estado = 'PAGADO' "
                + "THEN p.monto "
                + "ELSE 0 "
                + "END "
                + "), "
                + "0 "
                + ") AS total_pagado, "
                + "GREATEST( "
                + "f.total - "
                + "COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN p.estado = 'PAGADO' "
                + "THEN p.monto "
                + "ELSE 0 "
                + "END "
                + "), "
                + "0 "
                + "), "
                + "0 "
                + ") AS saldo_pendiente, "
                + "CASE "
                + "WHEN "
                + "GREATEST( "
                + "f.total - "
                + "COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN p.estado = 'PAGADO' "
                + "THEN p.monto "
                + "ELSE 0 "
                + "END "
                + "), "
                + "0 "
                + "), "
                + "0 "
                + ") = 0 "
                + "THEN 'PAGADO' "
                + "WHEN "
                + "f.fecha_vencimiento IS NOT NULL "
                + "AND f.fecha_vencimiento < CURRENT_DATE "
                + "THEN 'VENCIDO' "
                + "ELSE 'PENDIENTE' "
                + "END AS estado_pendiente, "
                + "CASE "
                + "WHEN "
                + "f.fecha_vencimiento IS NOT NULL "
                + "AND f.fecha_vencimiento < CURRENT_DATE "
                + "AND "
                + "GREATEST( "
                + "f.total - "
                + "COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN p.estado = 'PAGADO' "
                + "THEN p.monto "
                + "ELSE 0 "
                + "END "
                + "), "
                + "0 "
                + "), "
                + "0 "
                + ") > 0 "
                + "THEN "
                + "CURRENT_DATE - f.fecha_vencimiento "
                + "ELSE 0 "
                + "END AS dias_vencido "
                + "FROM factura f "
                + "INNER JOIN clientes c "
                + "ON f.id_cliente = c.id_cliente "
                + "LEFT JOIN pago p "
                + "ON f.id_factura = p.id_factura "
                + "WHERE "
                + "f.estado <> 'ANULADA' "
                + "GROUP BY "
                + "f.id_factura, "
                + "f.numero_factura, "
                + "f.fecha_emision, "
                + "f.condicion_venta, "
                + "f.periodo, "
                + "f.plazo_credito, "
                + "f.fecha_vencimiento, "
                + "f.total, "
                + "f.estado, "
                + "f.id_cliente, "
                + "c.nombre, "
                + "c.apellido, "
                + "c.cedula "
                + "ORDER BY "
                + "CASE "
                + "WHEN "
                + "GREATEST( "
                + "f.total - "
                + "COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN p.estado = 'PAGADO' "
                + "THEN p.monto "
                + "ELSE 0 "
                + "END "
                + "), "
                + "0 "
                + "), "
                + "0 "
                + ") > 0 "
                + "THEN 0 "
                + "ELSE 1 "
                + "END, "
                + "f.fecha_vencimiento ASC NULLS LAST, "
                + "f.id_factura DESC ";


        try (
            Connection con = conexion.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {


            while (rs.next()) {

                lista.add(
                    mapearPendiente(rs)
                );
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        return lista;
    }


    // BUSCAR PENDIENTE POR ID DE FACTURA

    public Pendiente buscarPorIdFactura(
            int idFactura
    ) {

        String sql =
                "SELECT "
                + "f.id_factura, "
                + "f.numero_factura, "
                + "f.fecha_emision, "
                + "f.condicion_venta, "
                + "f.periodo, "
                + "f.plazo_credito, "
                + "f.fecha_vencimiento, "
                + "f.total AS total_factura, "
                + "f.estado AS estado_factura, "
                + "f.id_cliente, "
                + "c.nombre AS nombre_cliente, "
                + "c.apellido AS apellido_cliente, "
                + "c.cedula AS cedula_cliente, "
                + "COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN p.estado = 'PAGADO' "
                + "THEN p.monto "
                + "ELSE 0 "
                + "END "
                + "), "
                + "0 "
                + ") AS total_pagado, "
                + "GREATEST( "
                + "f.total - "
                + "COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN p.estado = 'PAGADO' "
                + "THEN p.monto "
                + "ELSE 0 "
                + "END "
                + "), "
                + "0 "
                + "), "
                + "0 "
                + ") AS saldo_pendiente, "
                + "CASE "
                + "WHEN "
                + "GREATEST( "
                + "f.total - "
                + "COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN p.estado = 'PAGADO' "
                + "THEN p.monto "
                + "ELSE 0 "
                + "END "
                + "), "
                + "0 "
                + "), "
                + "0 "
                + ") = 0 "
                + "THEN 'PAGADO' "
                + "WHEN "
                + "f.fecha_vencimiento IS NOT NULL "
                + "AND f.fecha_vencimiento < CURRENT_DATE "
                + "THEN 'VENCIDO' "
                + "ELSE 'PENDIENTE' "
                + "END AS estado_pendiente, "
                + "CASE "
                + "WHEN "
                + "f.fecha_vencimiento IS NOT NULL "
                + "AND f.fecha_vencimiento < CURRENT_DATE "
                + "AND "
                + "GREATEST( "
                + "f.total - "
                + "COALESCE( "
                + "SUM( "
                + "CASE "
                + "WHEN p.estado = 'PAGADO' "
                + "THEN p.monto "
                + "ELSE 0 "
                + "END "
                + "), "
                + "0 "
                + "), "
                + "0 "
                + ") > 0 "
                + "THEN "
                + "CURRENT_DATE - f.fecha_vencimiento "
                + "ELSE 0 "
                + "END AS dias_vencido "
                + "FROM factura f "
                + "INNER JOIN clientes c "
                + "ON f.id_cliente = c.id_cliente "
                + "LEFT JOIN pago p "
                + "ON f.id_factura = p.id_factura "
                + "WHERE "
                + "f.id_factura = ? "
                + "AND "
                + "f.estado <> 'ANULADA' "
                + "GROUP BY "
                + "f.id_factura, "
                + "f.numero_factura, "
                + "f.fecha_emision, "
                + "f.condicion_venta, "
                + "f.periodo, "
                + "f.plazo_credito, "
                + "f.fecha_vencimiento, "
                + "f.total, "
                + "f.estado, "
                + "f.id_cliente, "
                + "c.nombre, "
                + "c.apellido, "
                + "c.cedula ";


        try (
            Connection con = conexion.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(
                1,
                idFactura
            );


            try (
                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {

                    return mapearPendiente(rs);
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }


    // TOTAL FACTURADO

    public BigDecimal totalFacturado() {

        String sql =
                "SELECT "
                + "COALESCE( "
                + "SUM(total), "
                + "0 "
                + ") AS total_facturado "
                + "FROM factura "
                + "WHERE estado <> 'ANULADA' ";


        return ejecutarTotal(
            sql,
            "total_facturado"
        );
    }


    // TOTAL PAGADO

    public BigDecimal totalPagado() {

        String sql =
                "SELECT "
                + "COALESCE( "
                + "SUM(monto), "
                + "0 "
                + ") AS total_pagado "
                + "FROM pago "
                + "WHERE estado = 'PAGADO' ";


        return ejecutarTotal(
            sql,
            "total_pagado"
        );
    }


    // TOTAL PENDIENTE
    //
    // TOTAL FACTURADO - TOTAL PAGADO
    //
    // Nunca devuelve un valor negativo.

    public BigDecimal totalSaldoPendiente() {

        String sql =
                "SELECT "
                + "COALESCE( "
                + "SUM( "
                + "GREATEST( "
                + "f.total - "
                + "COALESCE( "
                + "( "
                + "SELECT "
                + "SUM(p.monto) "
                + "FROM pago p "
                + "WHERE "
                + "p.id_factura = "
                + "f.id_factura "
                + "AND "
                + "p.estado = "
                + "'PAGADO' "
                + "), "
                + "0 "
                + "), "
                + "0 "
                + ") "
                + "), "
                + "0 "
                + ") AS total_saldo_pendiente "
                + "FROM factura f "
                + "WHERE "
                + "f.estado <> 'ANULADA' ";


        return ejecutarTotal(
            sql,
            "total_saldo_pendiente"
        );
    }


    // MAPEAR RESULTADO

    private Pendiente mapearPendiente(
            ResultSet rs
    ) throws Exception {

        Pendiente p =
                new Pendiente();


        // FACTURA

        p.setIdFactura(
            rs.getInt(
                "id_factura"
            )
        );


        p.setNumeroFactura(
            rs.getString(
                "numero_factura"
            )
        );


        p.setFechaEmision(
            rs.getDate(
                "fecha_emision"
            )
        );


        p.setCondicionVenta(
            rs.getString(
                "condicion_venta"
            )
        );


        p.setPeriodo(
            rs.getString(
                "periodo"
            )
        );


        p.setPlazoCredito(
            rs.getInt(
                "plazo_credito"
            )
        );


        p.setFechaVencimiento(
            rs.getDate(
                "fecha_vencimiento"
            )
        );


        p.setEstadoFactura(
            rs.getString(
                "estado_factura"
            )
        );


        // IMPORTES

        BigDecimal totalFactura =
                rs.getBigDecimal(
                    "total_factura"
                );


        BigDecimal totalPagado =
                rs.getBigDecimal(
                    "total_pagado"
                );


        BigDecimal saldoPendiente =
                rs.getBigDecimal(
                    "saldo_pendiente"
                );


        if (totalFactura == null) {

            totalFactura =
                    BigDecimal.ZERO;
        }


        if (totalPagado == null) {

            totalPagado =
                    BigDecimal.ZERO;
        }


        if (saldoPendiente == null) {

            saldoPendiente =
                    BigDecimal.ZERO;
        }


        p.setTotalFactura(
            totalFactura
        );


        p.setTotalPagado(
            totalPagado
        );


        p.setSaldoPendiente(
            saldoPendiente
        );


        // CLIENTE

        p.setIdCliente(
            rs.getInt(
                "id_cliente"
            )
        );


        p.setNombreCliente(
            rs.getString(
                "nombre_cliente"
            )
        );


        p.setApellidoCliente(
            rs.getString(
                "apellido_cliente"
            )
        );


        p.setCedulaCliente(
            rs.getString(
                "cedula_cliente"
            )
        );


        // ESTADO DEL PENDIENTE

        p.setEstadoPendiente(
            rs.getString(
                "estado_pendiente"
            )
        );


        // DÍAS VENCIDO

        p.setDiasVencido(
            rs.getInt(
                "dias_vencido"
            )
        );


        return p;
    }


    // EJECUTAR TOTAL

    private BigDecimal ejecutarTotal(
            String sql,
            String columna
    ) {

        try (
            Connection con =
                    conexion.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            if (rs.next()) {

                BigDecimal total =
                        rs.getBigDecimal(
                            columna
                        );


                if (total != null) {

                    return total;
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        return BigDecimal.ZERO;
    }
}