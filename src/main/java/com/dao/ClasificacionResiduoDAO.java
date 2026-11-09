package com.dao;

import com.config.Conexion;
import com.model.ClasificacionResiduo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClasificacionResiduoDAO {

    private final Conexion conexion = new Conexion();

    // LISTAR CLASIFICACIONES

    public List<ClasificacionResiduo> listar() {

        List<ClasificacionResiduo> lista = new ArrayList<>();

        String sql =
                "SELECT " +
                "c.id_clasificacion, " +
                "c.id_recoleccion, " +
                "c.id_tipo, " +
                "r.fecha AS fecha_recoleccion, " +
                "t.nombre AS nombre_tipo, " +
                "c.cantidad_kg, " +
                "c.observaciones, " +
                "c.estado " +
                "FROM clasificacion_residuo c " +
                "INNER JOIN recoleccion r " +
                "ON c.id_recoleccion = r.id_recoleccion " +
                "INNER JOIN tipos_residuos t " +
                "ON c.id_tipo = t.id_tipo " +
                "ORDER BY c.id_clasificacion DESC";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ClasificacionResiduo c =
                        new ClasificacionResiduo();

                c.setId_clasificacion(
                        rs.getInt("id_clasificacion")
                );

                c.setId_recoleccion(
                        rs.getInt("id_recoleccion")
                );

                c.setId_tipo(
                        rs.getInt("id_tipo")
                );

                if (rs.getDate("fecha_recoleccion") != null) {

                    c.setFecha_recoleccion(
                            rs.getDate("fecha_recoleccion").toString()
                    );
                }

                c.setNombre_tipo(
                        rs.getString("nombre_tipo")
                );

                c.setCantidad_kg(
                        rs.getDouble("cantidad_kg")
                );

                c.setObservaciones(
                        rs.getString("observaciones")
                );

                c.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(c);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar clasificaciones"
            );

            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID

    public ClasificacionResiduo buscarPorId(int id) {

        ClasificacionResiduo c = null;

        String sql =
                "SELECT " +
                "c.id_clasificacion, " +
                "c.id_recoleccion, " +
                "c.id_tipo, " +
                "r.fecha AS fecha_recoleccion, " +
                "t.nombre AS nombre_tipo, " +
                "c.cantidad_kg, " +
                "c.observaciones, " +
                "c.estado " +
                "FROM clasificacion_residuo c " +
                "INNER JOIN recoleccion r " +
                "ON c.id_recoleccion = r.id_recoleccion " +
                "INNER JOIN tipos_residuos t " +
                "ON c.id_tipo = t.id_tipo " +
                "WHERE c.id_clasificacion = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    c = new ClasificacionResiduo();

                    c.setId_clasificacion(
                            rs.getInt("id_clasificacion")
                    );

                    c.setId_recoleccion(
                            rs.getInt("id_recoleccion")
                    );

                    c.setId_tipo(
                            rs.getInt("id_tipo")
                    );

                    if (rs.getDate("fecha_recoleccion") != null) {

                        c.setFecha_recoleccion(
                                rs.getDate("fecha_recoleccion").toString()
                        );
                    }

                    c.setNombre_tipo(
                            rs.getString("nombre_tipo")
                    );

                    c.setCantidad_kg(
                            rs.getDouble("cantidad_kg")
                    );

                    c.setObservaciones(
                            rs.getString("observaciones")
                    );

                    c.setEstado(
                            rs.getBoolean("estado")
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al buscar clasificación"
            );

            e.printStackTrace();
        }

        return c;
    }

    // INSERTAR

    public boolean insertar(ClasificacionResiduo c) {

        if (!validarCantidad(
                c.getId_recoleccion(),
                c.getCantidad_kg(),
                0
        )) {

            return false;
        }

        if (existeTipoEnRecoleccion(
                c.getId_recoleccion(),
                c.getId_tipo(),
                0
        )) {

            return false;
        }

        String sql =
                "INSERT INTO clasificacion_residuo " +
                "(id_recoleccion, id_tipo, cantidad_kg, " +
                "observaciones, estado) " +
                "VALUES (?, ?, ?, ?, TRUE)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    c.getId_recoleccion()
            );

            ps.setInt(
                    2,
                    c.getId_tipo()
            );

            ps.setDouble(
                    3,
                    c.getCantidad_kg()
            );

            ps.setString(
                    4,
                    c.getObservaciones()
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al insertar clasificación"
            );

            e.printStackTrace();
        }

        return false;
    }

    // ACTUALIZAR

    public boolean actualizar(ClasificacionResiduo c) {

        if (!validarCantidad(
                c.getId_recoleccion(),
                c.getCantidad_kg(),
                c.getId_clasificacion()
        )) {

            return false;
        }

        if (existeTipoEnRecoleccion(
                c.getId_recoleccion(),
                c.getId_tipo(),
                c.getId_clasificacion()
        )) {

            return false;
        }

        String sql =
                "UPDATE clasificacion_residuo SET " +
                "id_recoleccion = ?, " +
                "id_tipo = ?, " +
                "cantidad_kg = ?, " +
                "observaciones = ? " +
                "WHERE id_clasificacion = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    c.getId_recoleccion()
            );

            ps.setInt(
                    2,
                    c.getId_tipo()
            );

            ps.setDouble(
                    3,
                    c.getCantidad_kg()
            );

            ps.setString(
                    4,
                    c.getObservaciones()
            );

            ps.setInt(
                    5,
                    c.getId_clasificacion()
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al actualizar clasificación"
            );

            e.printStackTrace();
        }

        return false;
    }

    // DESACTIVAR

    public boolean eliminar(int id) {

        String sql =
                "UPDATE clasificacion_residuo " +
                "SET estado = FALSE " +
                "WHERE id_clasificacion = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al desactivar clasificación"
            );

            e.printStackTrace();
        }

        return false;
    }

    // ACTIVAR

    public boolean activar(int id) {

        String sql =
                "UPDATE clasificacion_residuo " +
                "SET estado = TRUE " +
                "WHERE id_clasificacion = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al activar clasificación"
            );

            e.printStackTrace();
        }

        return false;
    }

    // LISTAR RECOLECCIONES

    public List<ClasificacionResiduo> listarRecolecciones() {

        List<ClasificacionResiduo> lista =
                new ArrayList<>();

        String sql =
                "SELECT " +
                "r.id_recoleccion, " +
                "r.fecha, " +
                "r.cantidad_recolectada, " +
                "r.unidad_medida, " +
                "ru.nombre AS nombre_ruta " +
                "FROM recoleccion r " +
                "INNER JOIN asignacion a " +
                "ON r.id_asignacion = a.id_asignacion " +
                "INNER JOIN ruta ru " +
                "ON a.id_ruta = ru.id_ruta " +
                "WHERE r.estado = TRUE " +
                "ORDER BY r.fecha DESC, r.id_recoleccion DESC";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ClasificacionResiduo c =
                        new ClasificacionResiduo();

                c.setId_recoleccion(
                        rs.getInt("id_recoleccion")
                );

                if (rs.getDate("fecha") != null) {

                    c.setFecha_recoleccion(
                            rs.getDate("fecha").toString()
                    );
                }

                lista.add(c);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar recolecciones"
            );

            e.printStackTrace();
        }

        return lista;
    }

    // LISTAR TIPOS DE RESIDUOS

    public List<ClasificacionResiduo> listarTipos() {

        List<ClasificacionResiduo> lista =
                new ArrayList<>();

        String sql =
                "SELECT " +
                "id_tipo, " +
                "nombre " +
                "FROM tipos_residuos " +
                "WHERE estado = TRUE " +
                "ORDER BY nombre";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ClasificacionResiduo c =
                        new ClasificacionResiduo();

                c.setId_tipo(
                        rs.getInt("id_tipo")
                );

                c.setNombre_tipo(
                        rs.getString("nombre")
                );

                lista.add(c);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar tipos de residuos"
            );

            e.printStackTrace();
        }

        return lista;
    }

    // VALIDAR CANTIDAD

    private boolean validarCantidad(
            int idRecoleccion,
            double nuevaCantidad,
            int idClasificacion) {

        if (nuevaCantidad <= 0) {
            return false;
        }

        double totalRecoleccion = 0;

        String sqlTotal =
                "SELECT cantidad_recolectada, unidad_medida " +
                "FROM recoleccion " +
                "WHERE id_recoleccion = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps =
                     con.prepareStatement(sqlTotal)) {

            ps.setInt(
                    1,
                    idRecoleccion
            );

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return false;
                }

                totalRecoleccion =
                        rs.getDouble("cantidad_recolectada");

                String unidad =
                        rs.getString("unidad_medida");

                if (unidad != null &&
                    unidad.equalsIgnoreCase("TON")) {

                    totalRecoleccion =
                            totalRecoleccion * 1000;
                }
            }

            String sqlClasificado =
                    "SELECT COALESCE(SUM(cantidad_kg), 0) " +
                    "FROM clasificacion_residuo " +
                    "WHERE id_recoleccion = ? " +
                    "AND estado = TRUE " +
                    "AND id_clasificacion <> ?";

            try (PreparedStatement ps2 =
                         con.prepareStatement(sqlClasificado)) {

                ps2.setInt(
                        1,
                        idRecoleccion
                );

                ps2.setInt(
                        2,
                        idClasificacion
                );

                try (ResultSet rs2 =
                             ps2.executeQuery()) {

                    if (rs2.next()) {

                        double yaClasificado =
                                rs2.getDouble(1);

                        double total =
                                yaClasificado +
                                nuevaCantidad;

                        return total <=
                                totalRecoleccion;
                    }
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al validar cantidad de clasificación"
            );

            e.printStackTrace();
        }

        return false;
    }

    // VALIDAR TIPO REPETIDO

    private boolean existeTipoEnRecoleccion(
            int idRecoleccion,
            int idTipo,
            int idClasificacion) {

        String sql =
                "SELECT COUNT(*) " +
                "FROM clasificacion_residuo " +
                "WHERE id_recoleccion = ? " +
                "AND id_tipo = ? " +
                "AND id_clasificacion <> ? " +
                "AND estado = TRUE";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    idRecoleccion
            );

            ps.setInt(
                    2,
                    idTipo
            );

            ps.setInt(
                    3,
                    idClasificacion
            );

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al validar tipo repetido"
            );

            e.printStackTrace();
        }

        return false;
    }
}