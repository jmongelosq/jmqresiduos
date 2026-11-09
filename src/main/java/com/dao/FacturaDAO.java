package com.dao;

import com.config.Conexion;
import com.model.Factura;
import com.model.DetalleFactura;
import com.model.Servicio;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    private Conexion cn = new Conexion();

    // LISTAR FACTURAS

    public List<Factura> listar() {

        List<Factura> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "f.id_factura, "
                + "f.id_cliente, "
                + "f.id_servicio, "
                + "f.fecha_emision, "
                + "f.timbrado, "
                + "f.fecha_inicio_timbrado, "
                + "f.fecha_fin_timbrado, "
                + "f.establecimiento, "
                + "f.punto_expedicion, "
                + "f.numero_factura, "
                + "f.condicion_venta, "
                + "f.plazo_credito, "
                + "f.fecha_vencimiento, "
                + "f.periodo, "
                + "f.subtotal, "
                + "f.iva_5, "
                + "f.iva_10, "
                + "f.total, "
                + "f.estado, "
                + "f.observaciones, "
                + "f.usuario_emision, "
                + "c.nombre AS nombre_cliente, "
                + "c.apellido AS apellido_cliente, "
                + "c.cedula AS cedula_cliente, "
                + "c.direccion AS direccion_cliente "
                + "FROM factura f "
                + "INNER JOIN clientes c "
                + "ON f.id_cliente = c.id_cliente "
                + "ORDER BY f.id_factura DESC";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Factura f = mapearFactura(rs);

                lista.add(f);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // BUSCAR FACTURA POR ID

    public Factura buscarPorId(int idFactura) {

        Factura factura = null;

        /*
         * IMPORTANTE:
         *
         * Para el reporte individual de la factura necesitamos
         * mostrar el RUC del cliente y no la cédula.
         *
         * Se mantiene el alias "cedula_cliente" para no romper
         * el reporte Jasper existente.
         */

        String sql =
                "SELECT "
                + "f.id_factura, "
                + "f.id_cliente, "
                + "f.id_servicio, "
                + "f.fecha_emision, "
                + "f.timbrado, "
                + "f.fecha_inicio_timbrado, "
                + "f.fecha_fin_timbrado, "
                + "f.establecimiento, "
                + "f.punto_expedicion, "
                + "f.numero_factura, "
                + "f.condicion_venta, "
                + "f.plazo_credito, "
                + "f.fecha_vencimiento, "
                + "f.periodo, "
                + "f.subtotal, "
                + "f.iva_5, "
                + "f.iva_10, "
                + "f.total, "
                + "f.estado, "
                + "f.observaciones, "
                + "f.usuario_emision, "
                + "c.nombre AS nombre_cliente, "
                + "c.apellido AS apellido_cliente, "
                + "COALESCE(NULLIF(c.ruc, ''), c.cedula) AS cedula_cliente, "
                + "c.direccion AS direccion_cliente "
                + "FROM factura f "
                + "INNER JOIN clientes c "
                + "ON f.id_cliente = c.id_cliente "
                + "WHERE f.id_factura = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idFactura);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    factura = mapearFactura(rs);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return factura;
    }


    // OBTENER DETALLES DE UNA FACTURA

    public List<DetalleFactura> listarDetalles(int idFactura) {

        List<DetalleFactura> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "d.id_detalle_factura, "
                + "d.id_factura, "
                + "d.id_tarifa, "
                + "d.descripcion, "
                + "d.cantidad, "
                + "d.precio_unitario, "
                + "d.porcentaje_iva, "
                + "d.subtotal, "
                + "d.monto_iva, "
                + "d.total, "
                + "t.nombre AS nombre_tarifa "
                + "FROM detalle_factura d "
                + "LEFT JOIN tarifa t "
                + "ON d.id_tarifa = t.id_tarifa "
                + "WHERE d.id_factura = ? "
                + "ORDER BY d.id_detalle_factura";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idFactura);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    DetalleFactura d = new DetalleFactura();

                    d.setId_detalle_factura(
                            rs.getInt("id_detalle_factura")
                    );

                    d.setId_factura(
                            rs.getInt("id_factura")
                    );

                    int idTarifa =
                            rs.getInt("id_tarifa");

                    if (rs.wasNull()) {

                        d.setId_tarifa(null);

                    } else {

                        d.setId_tarifa(idTarifa);
                    }

                    d.setDescripcion(
                            rs.getString("descripcion")
                    );

                    d.setCantidad(
                            rs.getBigDecimal("cantidad")
                    );

                    d.setPrecio_unitario(
                            rs.getBigDecimal("precio_unitario")
                    );

                    d.setPorcentaje_iva(
                            rs.getBigDecimal("porcentaje_iva")
                    );

                    d.setSubtotal(
                            rs.getBigDecimal("subtotal")
                    );

                    d.setMonto_iva(
                            rs.getBigDecimal("monto_iva")
                    );

                    d.setTotal(
                            rs.getBigDecimal("total")
                    );

                    d.setNombre_tarifa(
                            rs.getString("nombre_tarifa")
                    );

                    lista.add(d);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // LISTAR CLIENTES ACTIVOS

    public List<Factura> listarClientesActivos() {

        List<Factura> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "id_cliente, "
                + "nombre, "
                + "apellido, "
                + "cedula, "
                + "direccion "
                + "FROM clientes "
                + "WHERE estado = TRUE "
                + "ORDER BY nombre, apellido";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Factura f = new Factura();

                f.setId_cliente(
                        rs.getInt("id_cliente")
                );

                f.setNombre_cliente(
                        rs.getString("nombre")
                );

                f.setApellido_cliente(
                        rs.getString("apellido")
                );

                f.setCedula_cliente(
                        rs.getString("cedula")
                );

                f.setDireccion_cliente(
                        rs.getString("direccion")
                );

                lista.add(f);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // LISTAR TARIFAS ACTIVAS

    public List<DetalleFactura> listarTarifasActivas() {

        List<DetalleFactura> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "id_tarifa, "
                + "nombre, "
                + "descripcion, "
                + "monto, "
                + "periodicidad "
                + "FROM tarifa "
                + "WHERE estado = TRUE "
                + "ORDER BY nombre";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                DetalleFactura d = new DetalleFactura();

                d.setId_tarifa(
                        rs.getInt("id_tarifa")
                );

                d.setNombre_tarifa(
                        rs.getString("nombre")
                );

                d.setDescripcion(
                        rs.getString("descripcion")
                );

                d.setPrecio_unitario(
                        rs.getBigDecimal("monto")
                );

                lista.add(d);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // LISTAR SERVICIOS / ESTABLECIMIENTOS DE UN CLIENTE

    public List<Servicio> listarServiciosPorCliente(
            int idCliente
    ) {

        List<Servicio> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "s.id_servicio, "
                + "s.id_cliente, "
                + "c.nombre || ' ' || c.apellido AS nombre_cliente, "
                + "s.id_tarifa, "
                + "t.nombre AS nombre_tarifa, "
                + "t.monto AS monto_tarifa, "
                + "s.tipo_servicio, "
                + "s.nombre_establecimiento, "
                + "s.direccion_servicio, "
                + "s.id_barrio, "
                + "b.nombre AS nombre_barrio, "
                + "s.frecuencia, "
                + "s.fecha_inicio, "
                + "s.fecha_fin, "
                + "s.estado, "
                + "s.observaciones "
                + "FROM servicio s "
                + "INNER JOIN clientes c "
                + "ON s.id_cliente = c.id_cliente "
                + "LEFT JOIN tarifa t "
                + "ON s.id_tarifa = t.id_tarifa "
                + "LEFT JOIN barrio b "
                + "ON s.id_barrio = b.id_barrio "
                + "WHERE s.id_cliente = ? "
                + "AND s.estado = 'ACTIVO' "
                + "AND c.estado = TRUE "
                + "ORDER BY s.nombre_establecimiento";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Servicio s = mapearServicio(rs);

                    lista.add(s);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // BUSCAR SERVICIO POR ID

    public Servicio buscarServicioPorId(
            int idServicio
    ) {

        Servicio servicio = null;

        String sql =
                "SELECT "
                + "s.id_servicio, "
                + "s.id_cliente, "
                + "c.nombre || ' ' || c.apellido AS nombre_cliente, "
                + "s.id_tarifa, "
                + "t.nombre AS nombre_tarifa, "
                + "t.monto AS monto_tarifa, "
                + "s.tipo_servicio, "
                + "s.nombre_establecimiento, "
                + "s.direccion_servicio, "
                + "s.id_barrio, "
                + "b.nombre AS nombre_barrio, "
                + "s.frecuencia, "
                + "s.fecha_inicio, "
                + "s.fecha_fin, "
                + "s.estado, "
                + "s.observaciones "
                + "FROM servicio s "
                + "INNER JOIN clientes c "
                + "ON s.id_cliente = c.id_cliente "
                + "LEFT JOIN tarifa t "
                + "ON s.id_tarifa = t.id_tarifa "
                + "LEFT JOIN barrio b "
                + "ON s.id_barrio = b.id_barrio "
                + "WHERE s.id_servicio = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idServicio);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    servicio = mapearServicio(rs);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return servicio;
    }


    // SIGUIENTE NÚMERO DE FACTURA

    public String obtenerSiguienteNumeroFactura() {

        String sql =
                "SELECT "
                + "COALESCE("
                + "MAX(CAST(numero_factura AS INTEGER)), "
                + "0"
                + ") + 1 AS siguiente "
                + "FROM factura "
                + "WHERE establecimiento = '001' "
                + "AND punto_expedicion = '001'";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                int siguiente =
                        rs.getInt("siguiente");

                return String.format(
                        "%07d",
                        siguiente
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "0000001";
    }


    // INSERTAR FACTURA COMPLETA

    public int insertarFactura(
            Factura factura,
            List<DetalleFactura> detalles
    ) {

        String sqlFactura =
                "INSERT INTO factura ("
                + "id_cliente, "
                + "id_servicio, "
                + "fecha_emision, "
                + "timbrado, "
                + "fecha_inicio_timbrado, "
                + "fecha_fin_timbrado, "
                + "establecimiento, "
                + "punto_expedicion, "
                + "numero_factura, "
                + "condicion_venta, "
                + "plazo_credito, "
                + "fecha_vencimiento, "
                + "periodo, "
                + "subtotal, "
                + "iva_5, "
                + "iva_10, "
                + "total, "
                + "estado, "
                + "observaciones, "
                + "usuario_emision"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "RETURNING id_factura";

        String sqlDetalle =
                "INSERT INTO detalle_factura ("
                + "id_factura, "
                + "id_tarifa, "
                + "descripcion, "
                + "cantidad, "
                + "precio_unitario, "
                + "porcentaje_iva, "
                + "subtotal, "
                + "monto_iva, "
                + "total"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;

        try {

            con = cn.getConexion();

            con.setAutoCommit(false);

            int idFactura;

            // INSERTAR CABECERA

            try (
                    PreparedStatement ps =
                            con.prepareStatement(sqlFactura)
            ) {

                ps.setInt(
                        1,
                        factura.getId_cliente()
                );

                /*
                 * id_servicio puede ser 0 desde Java si no existe.
                 * En ese caso se guarda NULL para mantener compatibles
                 * las facturas antiguas y situaciones donde todavía
                 * no exista servicio asociado.
                 */
                if (factura.getId_servicio() <= 0) {

                    ps.setNull(
                            2,
                            java.sql.Types.INTEGER
                    );

                } else {

                    ps.setInt(
                            2,
                            factura.getId_servicio()
                    );
                }

                ps.setDate(
                        3,
                        factura.getFecha_emision()
                );

                ps.setString(
                        4,
                        factura.getTimbrado()
                );

                ps.setDate(
                        5,
                        factura.getFecha_inicio_timbrado()
                );

                ps.setDate(
                        6,
                        factura.getFecha_fin_timbrado()
                );

                ps.setString(
                        7,
                        factura.getEstablecimiento()
                );

                ps.setString(
                        8,
                        factura.getPunto_expedicion()
                );

                ps.setString(
                        9,
                        factura.getNumero_factura()
                );

                ps.setString(
                        10,
                        factura.getCondicion_venta()
                );

                /*
                 * PLAZO DE CRÉDITO
                 *
                 * CONTADO = 0
                 * CRÉDITO = cantidad de días acordada
                 */
                ps.setInt(
                        11,
                        factura.getPlazo_credito()
                );

                /*
                 * FECHA DE VENCIMIENTO
                 *
                 * Para CONTADO normalmente será igual a la fecha
                 * de emisión.
                 *
                 * Para CRÉDITO será la fecha calculada según
                 * el plazo.
                 */
                if (factura.getFecha_vencimiento() == null) {

                    ps.setNull(
                            12,
                            java.sql.Types.DATE
                    );

                } else {

                    ps.setDate(
                            12,
                            factura.getFecha_vencimiento()
                    );
                }

                ps.setString(
                        13,
                        factura.getPeriodo()
                );

                ps.setBigDecimal(
                        14,
                        factura.getSubtotal()
                );

                ps.setBigDecimal(
                        15,
                        factura.getIva_5()
                );

                ps.setBigDecimal(
                        16,
                        factura.getIva_10()
                );

                ps.setBigDecimal(
                        17,
                        factura.getTotal()
                );

                ps.setString(
                        18,
                        factura.getEstado()
                );

                ps.setString(
                        19,
                        factura.getObservaciones()
                );

                ps.setString(
                        20,
                        factura.getUsuario_emision()
                );

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {

                        con.rollback();

                        return -1;
                    }

                    idFactura =
                            rs.getInt(
                                    "id_factura"
                            );
                }
            }


            // INSERTAR DETALLES

            try (
                    PreparedStatement ps =
                            con.prepareStatement(sqlDetalle)
            ) {

                for (
                        DetalleFactura detalle
                        : detalles
                ) {

                    ps.setInt(
                            1,
                            idFactura
                    );

                    if (
                            detalle.getId_tarifa()
                            == null
                    ) {

                        ps.setNull(
                                2,
                                java.sql.Types.INTEGER
                        );

                    } else {

                        ps.setInt(
                                2,
                                detalle.getId_tarifa()
                        );
                    }

                    ps.setString(
                            3,
                            detalle.getDescripcion()
                    );

                    ps.setBigDecimal(
                            4,
                            detalle.getCantidad()
                    );

                    ps.setBigDecimal(
                            5,
                            detalle.getPrecio_unitario()
                    );

                    ps.setBigDecimal(
                            6,
                            detalle.getPorcentaje_iva()
                    );

                    ps.setBigDecimal(
                            7,
                            detalle.getSubtotal()
                    );

                    ps.setBigDecimal(
                            8,
                            detalle.getMonto_iva()
                    );

                    ps.setBigDecimal(
                            9,
                            detalle.getTotal()
                    );

                    ps.addBatch();
                }

                ps.executeBatch();
            }


            con.commit();

            return idFactura;


        } catch (Exception e) {

            e.printStackTrace();

            try {

                if (con != null) {

                    con.rollback();
                }

            } catch (Exception rollbackError) {

                rollbackError.printStackTrace();
            }

            return -1;


        } finally {

            try {

                if (con != null) {

                    con.setAutoCommit(true);

                    con.close();
                }

            } catch (Exception closeError) {

                closeError.printStackTrace();
            }
        }
    }


    // ANULAR FACTURA

    public boolean anular(int idFactura) {

        String sql =
                "UPDATE factura "
                + "SET estado = 'ANULADA' "
                + "WHERE id_factura = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    idFactura
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // MARCAR COMO PAGADA

    public boolean marcarPagada(int idFactura) {

        String sql =
                "UPDATE factura "
                + "SET estado = 'PAGADA' "
                + "WHERE id_factura = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    idFactura
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // MAPEAR FACTURA

    private Factura mapearFactura(
            ResultSet rs
    ) throws Exception {

        Factura f = new Factura();

        f.setId_factura(
                rs.getInt(
                        "id_factura"
                )
        );

        f.setId_cliente(
                rs.getInt(
                        "id_cliente"
                )
        );

        f.setId_servicio(
                rs.getInt(
                        "id_servicio"
                )
        );

        f.setFecha_emision(
                rs.getDate(
                        "fecha_emision"
                )
        );

        f.setTimbrado(
                rs.getString(
                        "timbrado"
                )
        );

        f.setFecha_inicio_timbrado(
                rs.getDate(
                        "fecha_inicio_timbrado"
                )
        );

        f.setFecha_fin_timbrado(
                rs.getDate(
                        "fecha_fin_timbrado"
                )
        );

        f.setEstablecimiento(
                rs.getString(
                        "establecimiento"
                )
        );

        f.setPunto_expedicion(
                rs.getString(
                        "punto_expedicion"
                )
        );

        f.setNumero_factura(
                rs.getString(
                        "numero_factura"
                )
        );

        f.setCondicion_venta(
                rs.getString(
                        "condicion_venta"
                )
        );

        /*
         * PLAZO DE CRÉDITO
         */
        f.setPlazo_credito(
                rs.getInt(
                        "plazo_credito"
                )
        );

        /*
         * FECHA DE VENCIMIENTO
         */
        f.setFecha_vencimiento(
                rs.getDate(
                        "fecha_vencimiento"
                )
        );

        f.setPeriodo(
                rs.getString(
                        "periodo"
                )
        );

        f.setSubtotal(
                rs.getBigDecimal(
                        "subtotal"
                )
        );

        f.setIva_5(
                rs.getBigDecimal(
                        "iva_5"
                )
        );

        f.setIva_10(
                rs.getBigDecimal(
                        "iva_10"
                )
        );

        f.setTotal(
                rs.getBigDecimal(
                        "total"
                )
        );

        f.setEstado(
                rs.getString(
                        "estado"
                )
        );

        f.setObservaciones(
                rs.getString(
                        "observaciones"
                )
        );

        f.setUsuario_emision(
                rs.getString(
                        "usuario_emision"
                )
        );

        f.setNombre_cliente(
                rs.getString(
                        "nombre_cliente"
                )
        );

        f.setApellido_cliente(
                rs.getString(
                        "apellido_cliente"
                )
        );

        f.setCedula_cliente(
                rs.getString(
                        "cedula_cliente"
                )
        );

        f.setDireccion_cliente(
                rs.getString(
                        "direccion_cliente"
                )
        );

        return f;
    }


    // MAPEAR SERVICIO

    private Servicio mapearServicio(
            ResultSet rs
    ) throws Exception {

        Servicio s = new Servicio();

        s.setId_servicio(
                rs.getInt(
                        "id_servicio"
                )
        );

        s.setId_cliente(
                rs.getInt(
                        "id_cliente"
                )
        );

        s.setNombre_cliente(
                rs.getString(
                        "nombre_cliente"
                )
        );

        s.setId_tarifa(
                rs.getInt(
                        "id_tarifa"
                )
        );

        s.setNombre_tarifa(
                rs.getString(
                        "nombre_tarifa"
                )
        );

        BigDecimal montoTarifa =
                rs.getBigDecimal(
                        "monto_tarifa"
                );

        if (montoTarifa != null) {

            s.setMonto_tarifa(
                    montoTarifa.doubleValue()
            );

        } else {

            s.setMonto_tarifa(0.0);
        }

        s.setTipo_servicio(
                rs.getString(
                        "tipo_servicio"
                )
        );

        s.setNombre_establecimiento(
                rs.getString(
                        "nombre_establecimiento"
                )
        );

        s.setDireccion_servicio(
                rs.getString(
                        "direccion_servicio"
                )
        );

        s.setId_barrio(
                rs.getInt(
                        "id_barrio"
                )
        );

        s.setNombre_barrio(
                rs.getString(
                        "nombre_barrio"
                )
        );

        s.setFrecuencia(
                rs.getString(
                        "frecuencia"
                )
        );

        s.setFecha_inicio(
                rs.getDate(
                        "fecha_inicio"
                )
        );

        s.setFecha_fin(
                rs.getDate(
                        "fecha_fin"
                )
        );

        s.setEstado(
                rs.getString(
                        "estado"
                )
        );

        s.setObservaciones(
                rs.getString(
                        "observaciones"
                )
        );

        return s;
    }
}