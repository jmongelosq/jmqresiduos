package com.dao;

import com.config.Conexion;
import com.model.Pago;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {

    private final Conexion conexion = new Conexion();


    // LISTAR PAGOS

    public List<Pago> listar() {

        List<Pago> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "p.id_pago, "
                + "p.id_factura, "
                + "p.fecha_pago, "
                + "p.periodo, "
                + "p.monto, "
                + "p.metodo_pago, "
                + "p.numero_recibo, "
                + "p.estado, "
                + "p.observaciones, "
                + "f.numero_factura, "
                + "f.total AS total_factura, "
                + "f.periodo AS periodo_factura, "
                + "f.estado AS estado_factura, "
                + "c.nombre AS nombre_cliente, "
                + "c.apellido AS apellido_cliente, "
                + "c.ruc AS cedula_cliente, "
                + "STRING_AGG( "
                + "DISTINCT t.nombre, "
                + "', ' "
                + "ORDER BY t.nombre "
                + ") AS nombre_tarifa "
                + "FROM pago p "
                + "INNER JOIN factura f "
                + "ON p.id_factura = f.id_factura "
                + "INNER JOIN clientes c "
                + "ON f.id_cliente = c.id_cliente "
                + "LEFT JOIN detalle_factura df "
                + "ON f.id_factura = df.id_factura "
                + "LEFT JOIN tarifa t "
                + "ON df.id_tarifa = t.id_tarifa "
                + "GROUP BY "
                + "p.id_pago, "
                + "p.id_factura, "
                + "p.fecha_pago, "
                + "p.periodo, "
                + "p.monto, "
                + "p.metodo_pago, "
                + "p.numero_recibo, "
                + "p.estado, "
                + "p.observaciones, "
                + "f.numero_factura, "
                + "f.total, "
                + "f.periodo, "
                + "f.estado, "
                + "c.nombre, "
                + "c.apellido, "
                + "c.ruc "
                + "ORDER BY p.id_pago DESC ";


        try (
                Connection con = conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(
                        mapearPago(rs)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return lista;
    }


    // BUSCAR PAGO POR ID

    public Pago buscarPorId(int idPago) {

        String sql =
                "SELECT "
                + "p.id_pago, "
                + "p.id_factura, "
                + "p.fecha_pago, "
                + "p.periodo, "
                + "p.monto, "
                + "p.metodo_pago, "
                + "p.numero_recibo, "
                + "p.estado, "
                + "p.observaciones, "
                + "f.numero_factura, "
                + "f.total AS total_factura, "
                + "f.periodo AS periodo_factura, "
                + "f.estado AS estado_factura, "
                + "c.nombre AS nombre_cliente, "
                + "c.apellido AS apellido_cliente, "
                + "c.ruc AS cedula_cliente, "
                + "STRING_AGG( "
                + "DISTINCT t.nombre, "
                + "', ' "
                + "ORDER BY t.nombre "
                + ") AS nombre_tarifa "
                + "FROM pago p "
                + "INNER JOIN factura f "
                + "ON p.id_factura = f.id_factura "
                + "INNER JOIN clientes c "
                + "ON f.id_cliente = c.id_cliente "
                + "LEFT JOIN detalle_factura df "
                + "ON f.id_factura = df.id_factura "
                + "LEFT JOIN tarifa t "
                + "ON df.id_tarifa = t.id_tarifa "
                + "WHERE p.id_pago = ? "
                + "GROUP BY "
                + "p.id_pago, "
                + "p.id_factura, "
                + "p.fecha_pago, "
                + "p.periodo, "
                + "p.monto, "
                + "p.metodo_pago, "
                + "p.numero_recibo, "
                + "p.estado, "
                + "p.observaciones, "
                + "f.numero_factura, "
                + "f.total, "
                + "f.periodo, "
                + "f.estado, "
                + "c.nombre, "
                + "c.apellido, "
                + "c.ruc ";


        try (
                Connection con = conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    idPago
            );


            try (
                    ResultSet rs = ps.executeQuery()
            ) {

                if (rs.next()) {

                    return mapearPago(rs);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }


    // BUSCAR FACTURA POR NÚMERO
    //
    // Acepta:
    //
    // 4
    // 04
    // 004
    // 0004
    // 000004
    // 0000004
    //
    // o:
    //
    // 001-001-0000004
    //
    // tomando establecimiento y punto de expedición
    // directamente desde la factura.

    public Pago buscarFacturaPorNumero(
            String numeroFactura
    ) {

        if (
                numeroFactura == null
                || numeroFactura.isBlank()
        ) {

            return null;
        }


        String numeroBuscado =
                numeroFactura.trim();


        String sql =
                "SELECT "
                + "f.id_factura, "
                + "f.numero_factura, "
                + "f.total AS total_factura, "
                + "f.periodo AS periodo_factura, "
                + "f.estado AS estado_factura, "
                + "c.nombre AS nombre_cliente, "
                + "c.apellido AS apellido_cliente, "
                + "c.ruc AS cedula_cliente, "
                + "STRING_AGG( "
                + "DISTINCT t.nombre, "
                + "', ' "
                + "ORDER BY t.nombre "
                + ") AS nombre_tarifa "
                + "FROM factura f "
                + "INNER JOIN clientes c "
                + "ON f.id_cliente = c.id_cliente "
                + "LEFT JOIN detalle_factura df "
                + "ON f.id_factura = df.id_factura "
                + "LEFT JOIN tarifa t "
                + "ON df.id_tarifa = t.id_tarifa "
                + "WHERE "
                + "( "
                + "TRIM(f.numero_factura) = TRIM(?) "
                + "OR "
                + "regexp_replace( "
                + "TRIM(f.numero_factura), "
                + "'^0+', "
                + "'' "
                + ") = regexp_replace( "
                + "TRIM(?), "
                + "'^0+', "
                + "'' "
                + ") "
                + ") "
                + "OR "
                + "( "
                + "TRIM(f.establecimiento) "
                + "|| '-' "
                + "|| TRIM(f.punto_expedicion) "
                + "|| '-' "
                + "|| LPAD( "
                + "TRIM(f.numero_factura), "
                + "7, "
                + "'0' "
                + ") "
                + ") = TRIM(?) "
                + "GROUP BY "
                + "f.id_factura, "
                + "f.numero_factura, "
                + "f.total, "
                + "f.periodo, "
                + "f.estado, "
                + "c.nombre, "
                + "c.apellido, "
                + "c.ruc "
                + "LIMIT 1 ";


        try (
                Connection con = conexion.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            /*
             * Primer parámetro:
             *
             * Busca directamente lo almacenado.
             *
             * Ejemplo:
             * 0000001
             */

            ps.setString(
                    1,
                    numeroBuscado
            );


            /*
             * Segundo parámetro:
             *
             * Permite buscar el número sin depender
             * de la cantidad de ceros iniciales.
             *
             * Ejemplos válidos para 0000004:
             *
             * 4
             * 04
             * 004
             * 0004
             * 000004
             * 0000004
             */

            ps.setString(
                    2,
                    numeroBuscado
            );


            /*
             * Tercer parámetro:
             *
             * Busca el número completo visible.
             *
             * Ejemplo:
             * 001-001-0000001
             */

            ps.setString(
                    3,
                    numeroBuscado
            );


            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    Pago p = new Pago();


                    // DATOS DE FACTURA

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


                    p.setTotalFactura(
                            rs.getBigDecimal(
                                    "total_factura"
                            )
                    );


                    p.setPeriodoFactura(
                            rs.getString(
                                    "periodo_factura"
                            )
                    );


                    p.setEstadoFactura(
                            rs.getString(
                                    "estado_factura"
                            )
                    );


                    // DATOS DEL CLIENTE

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


                    // TARIFAS

                    p.setNombreTarifa(
                            rs.getString(
                                    "nombre_tarifa"
                            )
                    );


                    return p;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }


    // EXISTE FACTURA

    public boolean existeFactura(
            int idFactura
    ) {

        String sql =
                "SELECT 1 "
                + "FROM factura "
                + "WHERE id_factura = ? "
                + "LIMIT 1 ";


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

                return rs.next();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return false;
    }


    // VERIFICAR SI LA FACTURA PUEDE RECIBIR PAGO
    //
    // IMPORTANTE:
    //
    // NO exigimos que esté PAGADA.
    //
    // Una factura EMITIDA puede recibir un pago.
    //
    // Una factura ANULADA no puede recibirlo.

    public boolean facturaPuedePagarse(
            int idFactura
    ) {

        String sql =
                "SELECT estado "
                + "FROM factura "
                + "WHERE id_factura = ? "
                + "LIMIT 1 ";


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

                    String estado =
                            rs.getString(
                                    "estado"
                            );


                    if (estado == null) {

                        return true;
                    }


                    return !estado
                            .trim()
                            .equalsIgnoreCase(
                                    "ANULADA"
                            );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return false;
    }


    // GENERAR SIGUIENTE NÚMERO DE RECIBO
    //
    // FORMATO:
    //
    // ADMIN     -> REC-ADM-000001
    // CAJERO    -> REC-CAJ-000001
    // COBRADOR  -> REC-COB-000001
    //
    // Cada rol mantiene su propia numeración.
    //
    // IMPORTANTE:
    // Este método utiliza una transacción y bloquea la fila
    // correspondiente para evitar números duplicados.

    public String generarNumeroRecibo(
            String rol
    ) {

        if (
                rol == null
                || rol.isBlank()
        ) {

            return null;
        }


        String rolNormalizado =
                rol.trim().toUpperCase();


        String sql =
                "SELECT "
                + "prefijo, "
                + "ultimo_numero "
                + "FROM secuencia_recibo "
                + "WHERE UPPER(rol) = ? "
                + "FOR UPDATE ";


        String sqlActualizar =
                "UPDATE secuencia_recibo "
                + "SET ultimo_numero = ? "
                + "WHERE UPPER(rol) = ? ";


        Connection con = null;


        try {

            con = conexion.getConexion();

            /*
             * Iniciamos una transacción para que:
             *
             * 1. Se bloquee la fila del rol.
             * 2. Se obtenga el último número.
             * 3. Se incremente.
             * 4. Se actualice.
             *
             * Luego se confirma la transacción.
             */

            con.setAutoCommit(false);


            String prefijo = null;
            int ultimoNumero = 0;


            try (
                    PreparedStatement ps =
                            con.prepareStatement(sql)
            ) {

                ps.setString(
                        1,
                        rolNormalizado
                );


                try (
                        ResultSet rs =
                                ps.executeQuery()
                ) {

                    if (!rs.next()) {

                        con.rollback();

                        return null;
                    }


                    prefijo =
                            rs.getString(
                                    "prefijo"
                            );

                    ultimoNumero =
                            rs.getInt(
                                    "ultimo_numero"
                            );
                }
            }


            int nuevoNumero =
                    ultimoNumero + 1;


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    sqlActualizar
                            )
            ) {

                ps.setInt(
                        1,
                        nuevoNumero
                );

                ps.setString(
                        2,
                        rolNormalizado
                );


                int filasActualizadas =
                        ps.executeUpdate();


                if (filasActualizadas != 1) {

                    con.rollback();

                    return null;
                }
            }


            String numeroRecibo =
                    String.format(
                            "REC-%s-%06d",
                            prefijo,
                            nuevoNumero
                    );


            con.commit();


            return numeroRecibo;

        } catch (Exception e) {

            e.printStackTrace();


            if (con != null) {

                try {

                    con.rollback();

                } catch (Exception rollbackError) {

                    rollbackError.printStackTrace();
                }
            }


            return null;

        } finally {

            if (con != null) {

                try {

                    con.setAutoCommit(true);
                    con.close();

                } catch (Exception closeError) {

                    closeError.printStackTrace();
                }
            }
        }
    }


    // INSERTAR PAGO

    public boolean insertar(
            Pago p
    ) {

        String sql =
                "INSERT INTO pago "
                + "( "
                + "id_factura, "
                + "fecha_pago, "
                + "periodo, "
                + "monto, "
                + "metodo_pago, "
                + "numero_recibo, "
                + "estado, "
                + "observaciones "
                + ") "
                + "VALUES "
                + "( "
                + "?, "
                + "?, "
                + "?, "
                + "?, "
                + "?, "
                + "?, "
                + "'PAGADO', "
                + "? "
                + ") ";


        try (
                Connection con = conexion.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    p.getIdFactura()
            );


            if (
                    p.getFechaPago()
                    != null
            ) {

                ps.setDate(
                        2,
                        p.getFechaPago()
                );

            } else {

                ps.setDate(
                        2,
                        new Date(
                                System.currentTimeMillis()
                        )
                );
            }


            if (
                    p.getPeriodo()
                    != null
                    && !p.getPeriodo().isBlank()
            ) {

                ps.setString(
                        3,
                        p.getPeriodo()
                );

            } else {

                ps.setNull(
                        3,
                        java.sql.Types.VARCHAR
                );
            }


            if (
                    p.getMonto()
                    != null
            ) {

                ps.setBigDecimal(
                        4,
                        p.getMonto()
                );

            } else {

                ps.setBigDecimal(
                        4,
                        BigDecimal.ZERO
                );
            }


            ps.setString(
                    5,
                    p.getMetodoPago()
            );


            if (
                    p.getNumeroRecibo()
                    != null
                    && !p.getNumeroRecibo().isBlank()
            ) {

                ps.setString(
                        6,
                        p.getNumeroRecibo()
                );

            } else {

                ps.setNull(
                        6,
                        java.sql.Types.VARCHAR
                );
            }


            if (
                    p.getObservaciones()
                    != null
                    && !p.getObservaciones().isBlank()
            ) {

                ps.setString(
                        7,
                        p.getObservaciones()
                );

            } else {

                ps.setNull(
                        7,
                        java.sql.Types.VARCHAR
                );
            }


            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ACTUALIZAR PAGO

    public boolean actualizar(
            Pago p
    ) {

        String sql =
                "UPDATE pago "
                + "SET "
                + "id_factura = ?, "
                + "fecha_pago = ?, "
                + "periodo = ?, "
                + "monto = ?, "
                + "metodo_pago = ?, "
                + "numero_recibo = ?, "
                + "observaciones = ? "
                + "WHERE id_pago = ? ";


        try (
                Connection con = conexion.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    p.getIdFactura()
            );


            if (
                    p.getFechaPago()
                    != null
            ) {

                ps.setDate(
                        2,
                        p.getFechaPago()
                );

            } else {

                ps.setDate(
                        2,
                        new Date(
                                System.currentTimeMillis()
                        )
                );
            }


            if (
                    p.getPeriodo()
                    != null
                    && !p.getPeriodo().isBlank()
            ) {

                ps.setString(
                        3,
                        p.getPeriodo()
                );

            } else {

                ps.setNull(
                        3,
                        java.sql.Types.VARCHAR
                );
            }


            if (
                    p.getMonto()
                    != null
            ) {

                ps.setBigDecimal(
                        4,
                        p.getMonto()
                );

            } else {

                ps.setBigDecimal(
                        4,
                        BigDecimal.ZERO
                );
            }


            ps.setString(
                    5,
                    p.getMetodoPago()
            );


            if (
                    p.getNumeroRecibo()
                    != null
                    && !p.getNumeroRecibo().isBlank()
            ) {

                ps.setString(
                        6,
                        p.getNumeroRecibo()
                );

            } else {

                ps.setNull(
                        6,
                        java.sql.Types.VARCHAR
                );
            }


            if (
                    p.getObservaciones()
                    != null
                    && !p.getObservaciones().isBlank()
            ) {

                ps.setString(
                        7,
                        p.getObservaciones()
                );

            } else {

                ps.setNull(
                        7,
                        java.sql.Types.VARCHAR
                );
            }


            ps.setInt(
                    8,
                    p.getIdPago()
            );


            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ANULAR PAGO

    public boolean anular(
            int idPago
    ) {

        String sql =
                "UPDATE pago "
                + "SET estado = 'ANULADO' "
                + "WHERE id_pago = ? ";


        try (
                Connection con = conexion.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    idPago
            );


            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // REACTIVAR / MARCAR PAGADO

    public boolean activar(
            int idPago
    ) {

        String sql =
                "UPDATE pago "
                + "SET estado = 'PAGADO' "
                + "WHERE id_pago = ? ";


        try (
                Connection con = conexion.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    idPago
            );


            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // MAPEAR RESULTADO DE PAGO

    private Pago mapearPago(
            ResultSet rs
    ) throws Exception {

        Pago p = new Pago();


        // PAGO

        p.setIdPago(
                rs.getInt(
                        "id_pago"
                )
        );


        p.setIdFactura(
                rs.getInt(
                        "id_factura"
                )
        );


        p.setFechaPago(
                rs.getDate(
                        "fecha_pago"
                )
        );


        p.setPeriodo(
                rs.getString(
                        "periodo"
                )
        );


        p.setMonto(
                rs.getBigDecimal(
                        "monto"
                )
        );


        p.setMetodoPago(
                rs.getString(
                        "metodo_pago"
                )
        );


        p.setNumeroRecibo(
                rs.getString(
                        "numero_recibo"
                )
        );


        p.setEstado(
                rs.getString(
                        "estado"
                )
        );


        p.setObservaciones(
                rs.getString(
                        "observaciones"
                )
        );


        // FACTURA

        p.setNumeroFactura(
                rs.getString(
                        "numero_factura"
                )
        );


        p.setTotalFactura(
                rs.getBigDecimal(
                        "total_factura"
                )
        );


        p.setPeriodoFactura(
                rs.getString(
                        "periodo_factura"
                )
        );


        p.setEstadoFactura(
                rs.getString(
                        "estado_factura"
                )
        );


        // CLIENTE

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


        // TARIFAS

        p.setNombreTarifa(
                rs.getString(
                        "nombre_tarifa"
                )
        );


        return p;
    }

}