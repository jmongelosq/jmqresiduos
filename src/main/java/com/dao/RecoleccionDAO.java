package com.dao;

import com.config.Conexion;
import com.model.ClasificacionResiduo;
import com.model.Recoleccion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class RecoleccionDAO {

    private final Conexion conexion =
            new Conexion();


    // LISTAR RECOLECCIONES

    public List<Recoleccion> listar() {

        List<Recoleccion> lista =
                new ArrayList<>();


        String sql =
                "SELECT " +
                "r.id_recoleccion, " +
                "r.id_asignacion, " +
                "r.fecha, " +
                "r.hora_inicio, " +
                "r.hora_fin, " +
                "r.cantidad_recolectada, " +
                "r.unidad_medida, " +
                "r.estado, " +
                "r.observaciones, " +
                "ru.nombre AS nombre_ruta, " +
                "v.chapa, " +
                "p.nombre || ' ' || p.apellido AS nombre_personal, " +
                "a.fecha AS fecha_asignacion " +

                "FROM recoleccion r " +

                "INNER JOIN asignacion a " +
                "ON r.id_asignacion = a.id_asignacion " +

                "INNER JOIN ruta ru " +
                "ON a.id_ruta = ru.id_ruta " +

                "INNER JOIN vehiculo v " +
                "ON a.id_vehiculo = v.id_vehiculo " +

                "INNER JOIN personal p " +
                "ON a.id_personal_responsable = p.id_personal " +

                "ORDER BY r.fecha DESC, " +
                "r.id_recoleccion DESC";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {


            while (rs.next()) {

                Recoleccion r =
                        new Recoleccion();


                r.setId_recoleccion(
                        rs.getInt(
                                "id_recoleccion"
                        )
                );


                r.setId_asignacion(
                        rs.getInt(
                                "id_asignacion"
                        )
                );


                if (rs.getDate("fecha") != null) {

                    r.setFecha(
                            rs.getDate(
                                    "fecha"
                            ).toString()
                    );
                }


                if (rs.getTime("hora_inicio") != null) {

                    r.setHora_inicio(
                            rs.getTime(
                                    "hora_inicio"
                            ).toString()
                    );
                }


                if (rs.getTime("hora_fin") != null) {

                    r.setHora_fin(
                            rs.getTime(
                                    "hora_fin"
                            ).toString()
                    );
                }


                r.setCantidad_recolectada(
                        rs.getDouble(
                                "cantidad_recolectada"
                        )
                );


                r.setUnidad_medida(
                        rs.getString(
                                "unidad_medida"
                        )
                );


                r.setEstado(
                        rs.getBoolean(
                                "estado"
                        )
                );


                r.setObservaciones(
                        rs.getString(
                                "observaciones"
                        )
                );


                r.setNombre_ruta(
                        rs.getString(
                                "nombre_ruta"
                        )
                );


                r.setChapa(
                        rs.getString(
                                "chapa"
                        )
                );


                r.setNombre_personal(
                        rs.getString(
                                "nombre_personal"
                        )
                );


                if (rs.getDate(
                        "fecha_asignacion"
                ) != null) {

                    r.setFecha_asignacion(
                            rs.getDate(
                                    "fecha_asignacion"
                            ).toString()
                    );
                }


                lista.add(r);
            }


        } catch (Exception e) {

            System.out.println(
                    "Error al listar recolecciones"
            );

            e.printStackTrace();
        }


        return lista;
    }


    // BUSCAR POR ID

    public Recoleccion buscarPorId(
            int id) {

        Recoleccion r = null;


        String sql =
                "SELECT " +
                "r.id_recoleccion, " +
                "r.id_asignacion, " +
                "r.fecha, " +
                "r.hora_inicio, " +
                "r.hora_fin, " +
                "r.cantidad_recolectada, " +
                "r.unidad_medida, " +
                "r.estado, " +
                "r.observaciones, " +
                "ru.nombre AS nombre_ruta, " +
                "v.chapa, " +
                "p.nombre || ' ' || p.apellido AS nombre_personal " +

                "FROM recoleccion r " +

                "INNER JOIN asignacion a " +
                "ON r.id_asignacion = a.id_asignacion " +

                "INNER JOIN ruta ru " +
                "ON a.id_ruta = ru.id_ruta " +

                "INNER JOIN vehiculo v " +
                "ON a.id_vehiculo = v.id_vehiculo " +

                "INNER JOIN personal p " +
                "ON a.id_personal_responsable = p.id_personal " +

                "WHERE r.id_recoleccion = ?";


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

                    r =
                            new Recoleccion();


                    r.setId_recoleccion(
                            rs.getInt(
                                    "id_recoleccion"
                            )
                    );


                    r.setId_asignacion(
                            rs.getInt(
                                    "id_asignacion"
                            )
                    );


                    if (rs.getDate("fecha") != null) {

                        r.setFecha(
                                rs.getDate(
                                        "fecha"
                                ).toString()
                        );
                    }


                    if (rs.getTime("hora_inicio") != null) {

                        r.setHora_inicio(
                                rs.getTime(
                                        "hora_inicio"
                                ).toString()
                        );
                    }


                    if (rs.getTime("hora_fin") != null) {

                        r.setHora_fin(
                                rs.getTime(
                                        "hora_fin"
                                ).toString()
                        );
                    }


                    r.setCantidad_recolectada(
                            rs.getDouble(
                                    "cantidad_recolectada"
                            )
                    );


                    r.setUnidad_medida(
                            rs.getString(
                                    "unidad_medida"
                            )
                    );


                    r.setEstado(
                            rs.getBoolean(
                                    "estado"
                            )
                    );


                    r.setObservaciones(
                            rs.getString(
                                    "observaciones"
                            )
                    );


                    r.setNombre_ruta(
                            rs.getString(
                                    "nombre_ruta"
                            )
                    );


                    r.setChapa(
                            rs.getString(
                                    "chapa"
                            )
                    );


                    r.setNombre_personal(
                            rs.getString(
                                    "nombre_personal"
                            )
                    );
                }
            }


        } catch (Exception e) {

            System.out.println(
                    "Error al buscar recolección"
            );

            e.printStackTrace();
        }


        return r;
    }


    // INSERTAR

    public boolean insertar(
            Recoleccion r) {

        String sql =
                SQL_INSERTAR;


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            cargarParametrosInsercion(ps, r);

            return ps.executeUpdate() > 0;


        } catch (Exception e) {

            System.out.println(
                    "ERROR AL INSERTAR RECOLECCIÓN"
            );

            e.printStackTrace();
        }


        return false;
    }


    private static final String SQL_INSERTAR =
            "INSERT INTO recoleccion " +
            "(id_asignacion, fecha, " +
            "hora_inicio, hora_fin, " +
            "cantidad_recolectada, " +
            "unidad_medida, estado, " +
            "observaciones) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


    private void cargarParametrosInsercion(
            PreparedStatement ps,
            Recoleccion r) throws SQLException {

        ps.setInt(
                1,
                r.getId_asignacion()
        );


        ps.setDate(
                2,
                Date.valueOf(
                        r.getFecha()
                )
        );


        if (r.getHora_inicio() != null &&
            !r.getHora_inicio().trim().isEmpty()) {

            ps.setTime(
                    3,
                    convertirHora(
                            r.getHora_inicio()
                    )
            );

        } else {

            ps.setNull(
                    3,
                    java.sql.Types.TIME
            );
        }


        if (r.getHora_fin() != null &&
            !r.getHora_fin().trim().isEmpty()) {

            ps.setTime(
                    4,
                    convertirHora(
                            r.getHora_fin()
                    )
            );

        } else {

            ps.setNull(
                    4,
                    java.sql.Types.TIME
            );
        }


        ps.setDouble(
                5,
                r.getCantidad_recolectada()
        );


        ps.setString(
                6,
                r.getUnidad_medida()
        );


        ps.setBoolean(
                7,
                true
        );


        ps.setString(
                8,
                r.getObservaciones()
        );
    }


    // Registrar recolección con sus clasificaciones (cabecera-detalle)
    //
    // La recolección (cabecera) y sus clasificaciones (detalle) se guardan
    // con la misma conexión dentro de una transacción: si falla cualquier
    // inserción se hace rollback y no queda ningún dato a medias.

    public boolean insertarConClasificaciones(
            Recoleccion r,
            List<ClasificacionResiduo> clasificaciones) {

        String sqlDetalle =
                "INSERT INTO clasificacion_residuo " +
                "(id_recoleccion, id_tipo, cantidad_kg, " +
                "observaciones, estado) " +
                "VALUES (?, ?, ?, ?, TRUE)";

        Connection con = null;

        try {

            con = conexion.getConexion();

            con.setAutoCommit(false);


            // 1. Cabecera: se obtiene el id generado
            int idRecoleccion;

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 SQL_INSERTAR + " RETURNING id_recoleccion"
                         )) {

                cargarParametrosInsercion(ps, r);

                try (ResultSet rs = ps.executeQuery()) {

                    rs.next();

                    idRecoleccion = rs.getInt(1);
                }
            }


            // 2. Detalle: todas las clasificaciones con el mismo id
            try (PreparedStatement ps =
                         con.prepareStatement(sqlDetalle)) {

                for (ClasificacionResiduo c : clasificaciones) {

                    ps.setInt(1, idRecoleccion);
                    ps.setInt(2, c.getId_tipo());
                    ps.setDouble(3, c.getCantidad_kg());
                    ps.setString(4, c.getObservaciones());

                    ps.addBatch();
                }

                ps.executeBatch();
            }


            // 3. Todo salió bien: se confirma
            con.commit();

            return true;


        } catch (Exception e) {

            System.out.println(
                    "ERROR AL REGISTRAR RECOLECCIÓN CON CLASIFICACIONES"
            );

            e.printStackTrace();

            if (con != null) {

                try {

                    con.rollback();

                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }

        } finally {

            if (con != null) {

                try {

                    con.setAutoCommit(true);

                    con.close();

                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }
        }


        return false;
    }


    // ACTUALIZAR

    public boolean actualizar(
            Recoleccion r) {

        String sql =
                "UPDATE recoleccion SET " +

                "id_asignacion = ?, " +
                "fecha = ?, " +
                "hora_inicio = ?, " +
                "hora_fin = ?, " +
                "cantidad_recolectada = ?, " +
                "unidad_medida = ?, " +
                "observaciones = ? " +

                "WHERE id_recoleccion = ?";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    r.getId_asignacion()
            );


            ps.setDate(
                    2,
                    Date.valueOf(
                            r.getFecha()
                    )
            );


            if (r.getHora_inicio() != null &&
                !r.getHora_inicio().trim().isEmpty()) {

                ps.setTime(
                        3,
                        convertirHora(
                                r.getHora_inicio()
                        )
                );

            } else {

                ps.setNull(
                        3,
                        java.sql.Types.TIME
                );
            }


            if (r.getHora_fin() != null &&
                !r.getHora_fin().trim().isEmpty()) {

                ps.setTime(
                        4,
                        convertirHora(
                                r.getHora_fin()
                        )
                );

            } else {

                ps.setNull(
                        4,
                        java.sql.Types.TIME
                );
            }


            ps.setDouble(
                    5,
                    r.getCantidad_recolectada()
            );


            ps.setString(
                    6,
                    r.getUnidad_medida()
            );


            ps.setString(
                    7,
                    r.getObservaciones()
            );


            ps.setInt(
                    8,
                    r.getId_recoleccion()
            );


            return ps.executeUpdate() > 0;


        } catch (Exception e) {

            System.out.println(
                    "ERROR AL ACTUALIZAR RECOLECCIÓN"
            );

            e.printStackTrace();
        }


        return false;
    }


    // DESACTIVAR

    public boolean desactivar(
            int id) {

        String sql =
                "UPDATE recoleccion " +
                "SET estado = FALSE " +
                "WHERE id_recoleccion = ?";


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


        } catch (Exception e) {

            System.out.println(
                    "Error al desactivar recolección"
            );

            e.printStackTrace();
        }


        return false;
    }


    // ACTIVAR

    public boolean activar(
            int id) {

        String sql =
                "UPDATE recoleccion " +
                "SET estado = TRUE " +
                "WHERE id_recoleccion = ?";


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


        } catch (Exception e) {

            System.out.println(
                    "Error al activar recolección"
            );

            e.printStackTrace();
        }


        return false;
    }


    // LISTAR ASIGNACIONES DISPONIBLES

    public List<Recoleccion> listarAsignaciones() {

        List<Recoleccion> lista =
                new ArrayList<>();


        String sql =
                "SELECT " +
                "a.id_asignacion, " +
                "a.fecha, " +
                "ru.nombre AS nombre_ruta, " +
                "v.chapa, " +
                "p.nombre || ' ' || p.apellido AS nombre_personal " +

                "FROM asignacion a " +

                "INNER JOIN ruta ru " +
                "ON a.id_ruta = ru.id_ruta " +

                "INNER JOIN vehiculo v " +
                "ON a.id_vehiculo = v.id_vehiculo " +

                "INNER JOIN personal p " +
                "ON a.id_personal_responsable = p.id_personal " +

                "WHERE a.estado IN " +
                "('PROGRAMADA', 'EN_CURSO', 'FINALIZADA') " +

                "ORDER BY a.fecha DESC, " +
                "a.id_asignacion DESC";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {


            while (rs.next()) {

                Recoleccion r =
                        new Recoleccion();


                r.setId_asignacion(
                        rs.getInt(
                                "id_asignacion"
                        )
                );


                if (rs.getDate("fecha") != null) {

                    r.setFecha_asignacion(
                            rs.getDate(
                                    "fecha"
                            ).toString()
                    );
                }


                r.setNombre_ruta(
                        rs.getString(
                                "nombre_ruta"
                        )
                );


                r.setChapa(
                        rs.getString(
                                "chapa"
                        )
                );


                r.setNombre_personal(
                        rs.getString(
                                "nombre_personal"
                        )
                );


                lista.add(r);
            }


        } catch (Exception e) {

            System.out.println(
                    "Error al listar asignaciones para recolecciones"
            );

            e.printStackTrace();
        }


        return lista;
    }


    // OBTENER FECHA DE ASIGNACIÓN

    public String obtenerFechaAsignacion(
            int idAsignacion) {

        String sql =
                "SELECT fecha " +
                "FROM asignacion " +
                "WHERE id_asignacion = ?";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    idAsignacion
            );


            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next() &&
                    rs.getDate("fecha") != null) {

                    return rs.getDate(
                            "fecha"
                    ).toString();
                }
            }


        } catch (Exception e) {

            System.out.println(
                    "Error al obtener fecha de asignación"
            );

            e.printStackTrace();
        }


        return null;
    }


    // VALIDAR ASIGNACIÓN

    public boolean asignacionValida(
            int idAsignacion) {

        String sql =
                "SELECT COUNT(*) " +
                "FROM asignacion " +
                "WHERE id_asignacion = ? " +
                "AND estado IN " +
                "('PROGRAMADA', 'EN_CURSO', 'FINALIZADA')";


        try (
                Connection con =
                        conexion.getConexion();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    idAsignacion
            );


            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    return rs.getInt(1) > 0;
                }
            }


        } catch (Exception e) {

            System.out.println(
                    "Error al validar asignación"
            );

            e.printStackTrace();
        }


        return false;
    }


    // CONVERTIR HORA

    private Time convertirHora(
            String hora) {

        if (hora == null ||
            hora.trim().isEmpty()) {

            return null;
        }


        String valor =
                hora.trim();


        if (valor.matches(
                "\\d{2}:\\d{2}"
        )) {

            valor =
                    valor + ":00";
        }


        return Time.valueOf(
                valor
        );
    }

}