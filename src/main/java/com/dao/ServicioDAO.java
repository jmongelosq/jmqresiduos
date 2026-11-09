package com.dao;

import com.config.Conexion;
import com.model.Servicio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioDAO {

    private final Conexion conexion =
            new Conexion();


    // LISTAR

    public List<Servicio> listar() {

        List<Servicio> lista =
                new ArrayList<>();


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
                + "ORDER BY s.id_servicio ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(
                        mapearServicio(rs)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return lista;
    }


    // LISTAR ACTIVOS

    public List<Servicio> listarActivos() {

        List<Servicio> lista =
                new ArrayList<>();


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
                + "WHERE s.estado = 'ACTIVO' "
                + "AND c.estado = TRUE "
                + "ORDER BY s.id_servicio ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(
                        mapearServicio(rs)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return lista;
    }


    // BUSCAR POR ID

    public Servicio buscarPorId(int id) {

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
                + "WHERE s.id_servicio = ? ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    id
            );


            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    return mapearServicio(rs);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }


    // INSERTAR

    public boolean insertar(Servicio s)
            throws Exception {


        validarServicio(s);


        String sql =
                "INSERT INTO servicio "
                + "( "
                + "id_cliente, "
                + "id_tarifa, "
                + "tipo_servicio, "
                + "nombre_establecimiento, "
                + "direccion_servicio, "
                + "id_barrio, "
                + "frecuencia, "
                + "fecha_inicio, "
                + "fecha_fin, "
                + "estado, "
                + "observaciones "
                + ") "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    s.getId_cliente()
            );


            ps.setInt(
                    2,
                    s.getId_tarifa()
            );


            ps.setString(
                    3,
                    s.getTipo_servicio()
            );


            ps.setString(
                    4,
                    s.getNombre_establecimiento()
            );


            ps.setString(
                    5,
                    s.getDireccion_servicio()
            );


            ps.setInt(
                    6,
                    s.getId_barrio()
            );


            ps.setString(
                    7,
                    s.getFrecuencia()
            );


            ps.setDate(
                    8,
                    s.getFecha_inicio()
            );


            if (s.getFecha_fin() != null) {

                ps.setDate(
                        9,
                        s.getFecha_fin()
                );

            } else {

                ps.setNull(
                        9,
                        Types.DATE
                );
            }


            ps.setString(
                    10,
                    s.getEstado()
            );


            ps.setString(
                    11,
                    s.getObservaciones()
            );


            return ps.executeUpdate() > 0;
        }
    }


    // ACTUALIZAR

    public boolean actualizar(Servicio s)
            throws Exception {


        validarServicio(s);


        if (s.getId_servicio() <= 0) {

            throw new Exception(
                    "ID de servicio inválido."
            );
        }


        String sql =
                "UPDATE servicio "
                + "SET "
                + "id_cliente = ?, "
                + "id_tarifa = ?, "
                + "tipo_servicio = ?, "
                + "nombre_establecimiento = ?, "
                + "direccion_servicio = ?, "
                + "id_barrio = ?, "
                + "frecuencia = ?, "
                + "fecha_inicio = ?, "
                + "fecha_fin = ?, "
                + "estado = ?, "
                + "observaciones = ? "
                + "WHERE id_servicio = ? ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    s.getId_cliente()
            );


            ps.setInt(
                    2,
                    s.getId_tarifa()
            );


            ps.setString(
                    3,
                    s.getTipo_servicio()
            );


            ps.setString(
                    4,
                    s.getNombre_establecimiento()
            );


            ps.setString(
                    5,
                    s.getDireccion_servicio()
            );


            ps.setInt(
                    6,
                    s.getId_barrio()
            );


            ps.setString(
                    7,
                    s.getFrecuencia()
            );


            ps.setDate(
                    8,
                    s.getFecha_inicio()
            );


            if (s.getFecha_fin() != null) {

                ps.setDate(
                        9,
                        s.getFecha_fin()
                );

            } else {

                ps.setNull(
                        9,
                        Types.DATE
                );
            }


            ps.setString(
                    10,
                    s.getEstado()
            );


            ps.setString(
                    11,
                    s.getObservaciones()
            );


            ps.setInt(
                    12,
                    s.getId_servicio()
            );


            return ps.executeUpdate() > 0;
        }
    }


    // CANCELAR

    public boolean eliminar(int id)
            throws Exception {


        String sql =
                "UPDATE servicio "
                + "SET "
                + "estado = 'CANCELADO', "
                + "fecha_fin = CURRENT_DATE "
                + "WHERE id_servicio = ? ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    id
            );


            return ps.executeUpdate() > 0;
        }
    }


    // SUSPENDER

    public boolean suspender(int id)
            throws Exception {


        String sql =
                "UPDATE servicio "
                + "SET estado = 'SUSPENDIDO' "
                + "WHERE id_servicio = ? ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    id
            );


            return ps.executeUpdate() > 0;
        }
    }


    // ACTIVAR

    public boolean activar(int id)
            throws Exception {


        String sql =
                "UPDATE servicio "
                + "SET "
                + "estado = 'ACTIVO', "
                + "fecha_fin = NULL "
                + "WHERE id_servicio = ? ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    id
            );


            return ps.executeUpdate() > 0;
        }
    }


    // BUSCAR CLIENTES POR NOMBRE, CÉDULA O RUC
    // IGNORANDO MAYÚSCULAS, MINÚSCULAS Y ACENTOS
    //
    // TAMBIÉN PERMITE RECUPERAR UN CLIENTE POR ID

    public List<Map<String, Object>> buscarClientes(
            String termino,
            int idCliente) {

        List<Map<String, Object>> lista =
                new ArrayList<>();


        String texto =
                termino == null
                ? ""
                : termino.trim();


        boolean buscarPorId =
                idCliente > 0;


        String sql;


        // BUSCAR CLIENTE POR ID

        if (buscarPorId) {

            sql =
                    "SELECT "
                    + "id_cliente, "
                    + "nombre, "
                    + "apellido, "
                    + "cedula, "
                    + "ruc "
                    + "FROM clientes "
                    + "WHERE estado = TRUE "
                    + "AND id_cliente = ? "
                    + "ORDER BY nombre, apellido ";

        }


        // BUSCAR POR NOMBRE, CÉDULA O RUC
        // IGNORANDO ACENTOS Y MAYÚSCULAS

        else {

            sql =
                    "SELECT "
                    + "id_cliente, "
                    + "nombre, "
                    + "apellido, "
                    + "cedula, "
                    + "ruc "
                    + "FROM clientes "
                    + "WHERE estado = TRUE "
                    + "AND ( "
                    + "TRANSLATE( "
                    + "LOWER( "
                    + "COALESCE(nombre, '') "
                    + "|| ' ' || "
                    + "COALESCE(apellido, '') "
                    + "), "
                    + "'áéíóúüñ', "
                    + "'aeiouun' "
                    + ") LIKE "
                    + "TRANSLATE( "
                    + "LOWER(?), "
                    + "'áéíóúüñ', "
                    + "'aeiouun' "
                    + ") "
                    + "OR COALESCE( "
                    + "cedula, "
                    + "'' "
                    + ") ILIKE ? "
                    + "OR COALESCE( "
                    + "ruc, "
                    + "'' "
                    + ") ILIKE ? "
                    + ") "
                    + "ORDER BY nombre, apellido "
                    + "LIMIT 20 ";
        }


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            // BÚSQUEDA POR ID

            if (buscarPorId) {

                ps.setInt(
                        1,
                        idCliente
                );

            }


            // BÚSQUEDA POR NOMBRE / CI / RUC

            else {

                String patron =
                        "%"
                        + texto
                        + "%";


                /*
                 * Para nombre/apellido:
                 *
                 * El TRANSLATE del SQL convierte tanto el
                 * contenido de la base como el término buscado.
                 *
                 * Ejemplo:
                 *
                 * José -> jose
                 * Jose -> jose
                 *
                 * Gómez -> gomez
                 * Gomez -> gomez
                 */

                ps.setString(
                        1,
                        patron
                );


                /*
                 * Cédula
                 */

                ps.setString(
                        2,
                        patron
                );


                /*
                 * RUC
                 */

                ps.setString(
                        3,
                        patron
                );
            }


            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                while (rs.next()) {

                    Map<String, Object> cliente =
                            new HashMap<>();


                    cliente.put(
                            "id_cliente",
                            rs.getInt(
                                    "id_cliente"
                            )
                    );


                    String nombre =
                            rs.getString(
                                    "nombre"
                            );


                    String apellido =
                            rs.getString(
                                    "apellido"
                            );


                    String nombreCompleto =
                            (nombre != null
                                    ? nombre
                                    : "")
                            + " "
                            +
                            (apellido != null
                                    ? apellido
                                    : "");


                    cliente.put(
                            "nombre",
                            nombreCompleto.trim()
                    );


                    cliente.put(
                            "cedula",
                            rs.getString(
                                    "cedula"
                            )
                    );


                    cliente.put(
                            "ruc",
                            rs.getString(
                                    "ruc"
                            )
                    );


                    lista.add(
                            cliente
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return lista;
    }


    // LISTAR BARRIOS

    public List<Map<String, Object>> listarBarrios() {

        List<Map<String, Object>> lista =
                new ArrayList<>();


        String sql =
                "SELECT "
                + "id_barrio, "
                + "nombre "
                + "FROM barrio "
                + "WHERE estado = TRUE "
                + "ORDER BY nombre ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                Map<String, Object> barrio =
                        new HashMap<>();


                barrio.put(
                        "id_barrio",
                        rs.getInt(
                                "id_barrio"
                        )
                );


                barrio.put(
                        "nombre",
                        rs.getString(
                                "nombre"
                        )
                );


                lista.add(
                        barrio
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return lista;
    }


    // LISTAR TARIFAS

    public List<Map<String, Object>> listarTarifas() {

        List<Map<String, Object>> lista =
                new ArrayList<>();


        String sql =
                "SELECT "
                + "id_tarifa, "
                + "nombre, "
                + "monto, "
                + "periodicidad "
                + "FROM tarifa "
                + "WHERE estado = TRUE "
                + "AND ( "
                + "fecha_fin IS NULL "
                + "OR fecha_fin >= CURRENT_DATE "
                + ") "
                + "ORDER BY nombre ";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                Map<String, Object> tarifa =
                        new HashMap<>();


                tarifa.put(
                        "id_tarifa",
                        rs.getInt(
                                "id_tarifa"
                        )
                );


                tarifa.put(
                        "nombre",
                        rs.getString(
                                "nombre"
                        )
                );


                tarifa.put(
                        "monto",
                        rs.getBigDecimal(
                                "monto"
                        )
                );


                tarifa.put(
                        "periodicidad",
                        rs.getString(
                                "periodicidad"
                        )
                );


                lista.add(
                        tarifa
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return lista;
    }


    // MAPEAR SERVICIO

    private Servicio mapearServicio(
            ResultSet rs)
            throws Exception {


        Servicio s =
                new Servicio();


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


        if (rs.getBigDecimal(
                "monto_tarifa"
        ) != null) {

            s.setMonto_tarifa(
                    rs.getBigDecimal(
                            "monto_tarifa"
                    ).doubleValue()
            );

        } else {

            s.setMonto_tarifa(0);
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


    // VALIDAR SERVICIO

    private void validarServicio(
            Servicio s)
            throws Exception {


        if (s == null) {

            throw new Exception(
                    "Los datos del servicio son obligatorios."
            );
        }


        if (s.getId_cliente() <= 0) {

            throw new Exception(
                    "Debe seleccionar un cliente."
            );
        }


        if (s.getId_tarifa() <= 0) {

            throw new Exception(
                    "Debe seleccionar una tarifa."
            );
        }


        if (s.getTipo_servicio() == null
                || s.getTipo_servicio()
                        .trim()
                        .isEmpty()) {

            throw new Exception(
                    "Debe seleccionar el tipo de servicio."
            );
        }


        if (s.getNombre_establecimiento() == null
                || s.getNombre_establecimiento()
                        .trim()
                        .isEmpty()) {

            throw new Exception(
                    "Debe ingresar el establecimiento."
            );
        }


        if (s.getDireccion_servicio() == null
                || s.getDireccion_servicio()
                        .trim()
                        .isEmpty()) {

            throw new Exception(
                    "Debe ingresar la dirección del servicio."
            );
        }


        if (s.getId_barrio() <= 0) {

            throw new Exception(
                    "Debe seleccionar un barrio."
            );
        }


        if (s.getFrecuencia() == null
                || s.getFrecuencia()
                        .trim()
                        .isEmpty()) {

            throw new Exception(
                    "Debe seleccionar la frecuencia."
            );
        }


        if (s.getFecha_inicio() == null) {

            throw new Exception(
                    "Debe ingresar la fecha de inicio."
            );
        }


        if (s.getFecha_fin() != null
                && s.getFecha_fin()
                        .before(
                                s.getFecha_inicio()
                        )) {

            throw new Exception(
                    "La fecha de fin no puede ser anterior a la fecha de inicio."
            );
        }


        if (s.getEstado() == null
                || s.getEstado()
                        .trim()
                        .isEmpty()) {

            s.setEstado(
                    "ACTIVO"
            );
        }


        if (!s.getEstado().equals(
                "ACTIVO")
                && !s.getEstado().equals(
                        "SUSPENDIDO")
                && !s.getEstado().equals(
                        "CANCELADO")) {

            throw new Exception(
                    "Estado de servicio no válido."
            );
        }
    }

}