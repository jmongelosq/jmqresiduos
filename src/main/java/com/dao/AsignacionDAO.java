package com.dao;

import com.config.Conexion;
import com.model.Asignacion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDAO {

    private final Conexion conexion = new Conexion();


    // LISTAR TODAS LAS ASIGNACIONES

    public List<Asignacion> listar() {

        List<Asignacion> lista = new ArrayList<>();

        String sql =
                "SELECT " +
                "a.id_asignacion, " +
                "a.id_ruta, " +
                "a.id_vehiculo, " +
                "a.id_personal_responsable, " +
                "a.fecha, " +
                "a.hora_salida, " +
                "a.hora_regreso, " +
                "a.estado, " +
                "a.observaciones, " +
                "r.nombre AS nombre_ruta, " +
                "v.chapa, " +
                "p.nombre || ' ' || p.apellido AS nombre_responsable " +
                "FROM asignacion a " +
                "INNER JOIN ruta r ON a.id_ruta = r.id_ruta " +
                "INNER JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo " +
                "INNER JOIN personal p ON a.id_personal_responsable = p.id_personal " +
                "ORDER BY a.fecha DESC, " +
                "a.hora_salida DESC, " +
                "a.id_asignacion DESC";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(mapear(rs));
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar asignaciones:"
            );

            e.printStackTrace();
        }

        return lista;
    }


    // BUSCAR POR ID

    public Asignacion buscarPorId(int id) {

        Asignacion a = null;

        String sql =
                "SELECT " +
                "a.id_asignacion, " +
                "a.id_ruta, " +
                "a.id_vehiculo, " +
                "a.id_personal_responsable, " +
                "a.fecha, " +
                "a.hora_salida, " +
                "a.hora_regreso, " +
                "a.estado, " +
                "a.observaciones, " +
                "r.nombre AS nombre_ruta, " +
                "v.chapa, " +
                "p.nombre || ' ' || p.apellido AS nombre_responsable " +
                "FROM asignacion a " +
                "INNER JOIN ruta r ON a.id_ruta = r.id_ruta " +
                "INNER JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo " +
                "INNER JOIN personal p ON a.id_personal_responsable = p.id_personal " +
                "WHERE a.id_asignacion = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    id
            );

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    a = mapear(rs);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al buscar asignación:"
            );

            e.printStackTrace();
        }

        return a;
    }


    // INSERTAR

    public boolean insertar(Asignacion a) {

        String sql =
                "INSERT INTO asignacion " +
                "(id_ruta, id_vehiculo, id_personal_responsable, " +
                "fecha, hora_salida, hora_regreso, estado, observaciones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            cargarDatos(
                    ps,
                    a,
                    false
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al insertar asignación:"
            );

            e.printStackTrace();
        }

        return false;
    }


    // ACTUALIZAR

    public boolean actualizar(Asignacion a) {

        String sql =
                "UPDATE asignacion SET " +
                "id_ruta = ?, " +
                "id_vehiculo = ?, " +
                "id_personal_responsable = ?, " +
                "fecha = ?, " +
                "hora_salida = ?, " +
                "hora_regreso = ?, " +
                "estado = ?, " +
                "observaciones = ? " +
                "WHERE id_asignacion = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            cargarDatos(
                    ps,
                    a,
                    true
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al actualizar asignación:"
            );

            e.printStackTrace();
        }

        return false;
    }


    // DESACTIVAR

    public boolean desactivar(int id) {

        return cancelar(id);
    }


    // CANCELAR

    public boolean cancelar(int id) {

        String sql =
                "UPDATE asignacion " +
                "SET estado = 'CANCELADA' " +
                "WHERE id_asignacion = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    id
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al cancelar asignación:"
            );

            e.printStackTrace();
        }

        return false;
    }


    // ACTIVAR

    public boolean activar(int id) {

        String sql =
                "UPDATE asignacion " +
                "SET estado = 'PROGRAMADA' " +
                "WHERE id_asignacion = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    id
            );

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error al activar asignación:"
            );

            e.printStackTrace();
        }

        return false;
    }


    // VALIDAR CONFLICTOS

    public String validarConflictos(
            Asignacion a,
            int idExcluir) {

        if (a == null) {

            return "Datos de la asignación no válidos.";
        }


        if (a.getId_ruta() <= 0 ||
            a.getId_vehiculo() <= 0 ||
            a.getId_personal_responsable() <= 0) {

            return "Debe seleccionar la ruta, el vehículo y el personal responsable.";
        }


        if (a.getFecha() == null ||
            a.getFecha().trim().isEmpty()) {

            return "Debe indicar la fecha de la asignación.";
        }


        // VALIDAR HORARIOS

        boolean tieneSalida =
                a.getHora_salida() != null &&
                !a.getHora_salida().trim().isEmpty();

        boolean tieneRegreso =
                a.getHora_regreso() != null &&
                !a.getHora_regreso().trim().isEmpty();


        if (tieneRegreso && !tieneSalida) {

            return "Para indicar la hora de regreso primero debe indicar la hora de salida.";
        }


        if (tieneSalida && tieneRegreso) {

            try {

                LocalTime salida =
                        LocalTime.parse(
                                normalizarHora(
                                        a.getHora_salida()
                                )
                        );

                LocalTime regreso =
                        LocalTime.parse(
                                normalizarHora(
                                        a.getHora_regreso()
                                )
                        );


                if (!regreso.isAfter(salida)) {

                    return "La hora de regreso debe ser posterior a la hora de salida.";
                }

            } catch (Exception e) {

                return "Las horas indicadas no tienen un formato válido.";
            }
        }


        // VALIDAR RUTA

        String conflictoRuta =
                validarRuta(
                        a,
                        idExcluir
                );


        if (conflictoRuta != null) {

            return conflictoRuta;
        }


        /*
         * Una asignación CANCELADA no ocupa vehículo ni personal.
         * La ruta igualmente mantiene la restricción de fecha.
         */

        if ("CANCELADA".equals(
                a.getEstado())) {

            return null;
        }


        // VALIDAR VEHÍCULO

        String conflictoVehiculo =
                validarRecurso(
                        "id_vehiculo",
                        a.getId_vehiculo(),
                        a,
                        idExcluir,
                        "vehículo"
                );


        if (conflictoVehiculo != null) {

            return conflictoVehiculo;
        }


        // VALIDAR PERSONAL

        String conflictoPersonal =
                validarRecurso(
                        "id_personal_responsable",
                        a.getId_personal_responsable(),
                        a,
                        idExcluir,
                        "personal responsable"
                );


        if (conflictoPersonal != null) {

            return conflictoPersonal;
        }


        return null;
    }


    // VALIDAR RUTA

    private String validarRuta(
            Asignacion a,
            int idExcluir) {

        String sql =
                "SELECT id_asignacion " +
                "FROM asignacion " +
                "WHERE id_ruta = ? " +
                "AND fecha = ? " +
                "AND id_asignacion <> ? " +
                "LIMIT 1";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    a.getId_ruta()
            );

            ps.setDate(
                    2,
                    Date.valueOf(
                            a.getFecha()
                    )
            );

            ps.setInt(
                    3,
                    idExcluir
            );


            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    return "La ruta seleccionada ya tiene una asignación para esa fecha.";
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al validar la ruta:"
            );

            e.printStackTrace();

            return "No se pudo validar la disponibilidad de la ruta.";
        }

        return null;
    }


    // VALIDAR VEHÍCULO / PERSONAL

    private String validarRecurso(
            String campo,
            int valor,
            Asignacion nueva,
            int idExcluir,
            String nombreRecurso) {

        String sql =
                "SELECT " +
                "id_asignacion, " +
                "hora_salida, " +
                "hora_regreso " +
                "FROM asignacion " +
                "WHERE " + campo + " = ? " +
                "AND fecha = ? " +
                "AND estado <> 'CANCELADA' " +
                "AND id_asignacion <> ?";


        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    valor
            );

            ps.setDate(
                    2,
                    Date.valueOf(
                            nueva.getFecha()
                    )
            );

            ps.setInt(
                    3,
                    idExcluir
            );


            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    Time horaSalidaBD =
                            rs.getTime(
                                    "hora_salida"
                            );

                    Time horaRegresoBD =
                            rs.getTime(
                                    "hora_regreso"
                            );


                    if (hayConflictoHorario(
                            nueva.getHora_salida(),
                            nueva.getHora_regreso(),
                            horaSalidaBD,
                            horaRegresoBD)) {

                        return "El " +
                               nombreRecurso +
                               " seleccionado ya tiene una asignación que se superpone en esa fecha y horario.";
                    }
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al validar "
                    + nombreRecurso
                    + ":"
            );

            e.printStackTrace();

            return "No se pudo validar la disponibilidad del "
                    + nombreRecurso
                    + ".";
        }

        return null;
    }


    // COMPROBAR SUPERPOSICIÓN DE HORARIOS

    private boolean hayConflictoHorario(
            String nuevaSalida,
            String nuevaRegreso,
            Time salidaBD,
            Time regresoBD) {

        boolean nuevaTieneSalida =
                nuevaSalida != null &&
                !nuevaSalida.trim().isEmpty();

        boolean nuevaTieneRegreso =
                nuevaRegreso != null &&
                !nuevaRegreso.trim().isEmpty();

        boolean existenteTieneSalida =
                salidaBD != null;

        boolean existenteTieneRegreso =
                regresoBD != null;


        /*
         * Si alguna asignación no tiene horario completo,
         * se considera que el recurso está ocupado ese día.
         */

        if (!nuevaTieneSalida ||
            !nuevaTieneRegreso ||
            !existenteTieneSalida ||
            !existenteTieneRegreso) {

            return true;
        }


        LocalTime nuevaInicio =
                LocalTime.parse(
                        normalizarHora(
                                nuevaSalida
                        )
                );

        LocalTime nuevaFin =
                LocalTime.parse(
                        normalizarHora(
                                nuevaRegreso
                        )
                );

        LocalTime existenteInicio =
                salidaBD.toLocalTime();

        LocalTime existenteFin =
                regresoBD.toLocalTime();


        /*
         * Ejemplo:
         *
         * Existente: 08:00 - 12:00
         * Nueva:     09:00 - 11:00
         *
         * Hay conflicto.
         */

        return nuevaInicio.isBefore(
                    existenteFin
               )
               &&
               existenteInicio.isBefore(
                    nuevaFin
               );
    }


    // LISTAR RUTAS

    public List<Asignacion> listarRutas() {

        return listarRutas(0);
    }


    public List<Asignacion> listarRutas(
            int idActual) {

        List<Asignacion> lista =
                new ArrayList<>();


        String sql =
                "SELECT " +
                "id_ruta, " +
                "nombre " +
                "FROM ruta " +
                "WHERE estado = TRUE " +
                "OR id_ruta = ? " +
                "ORDER BY nombre";


        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    idActual
            );


            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    Asignacion a =
                            new Asignacion();

                    a.setId_ruta(
                            rs.getInt(
                                    "id_ruta"
                            )
                    );

                    a.setNombre_ruta(
                            rs.getString(
                                    "nombre"
                            )
                    );

                    lista.add(a);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar rutas:"
            );

            e.printStackTrace();
        }

        return lista;
    }


    // LISTAR VEHÍCULOS

    public List<Asignacion> listarVehiculos() {

        return listarVehiculos(0);
    }


    public List<Asignacion> listarVehiculos(
            int idActual) {

        List<Asignacion> lista =
                new ArrayList<>();


        String sql =
                "SELECT " +
                "v.id_vehiculo, " +
                "v.chapa " +
                "FROM vehiculo v " +
                "WHERE v.estado = 'DISPONIBLE' " +
                "OR v.id_vehiculo = ? " +
                "ORDER BY v.chapa";


        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    idActual
            );


            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    Asignacion a =
                            new Asignacion();

                    a.setId_vehiculo(
                            rs.getInt(
                                    "id_vehiculo"
                            )
                    );

                    a.setChapa(
                            rs.getString(
                                    "chapa"
                            )
                    );

                    lista.add(a);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar vehículos:"
            );

            e.printStackTrace();
        }

        return lista;
    }


    // LISTAR PERSONAL

    public List<Asignacion> listarPersonal() {

        return listarPersonal(0);
    }


    public List<Asignacion> listarPersonal(
            int idActual) {

        List<Asignacion> lista =
                new ArrayList<>();


        String sql =
                "SELECT " +
                "p.id_personal, " +
                "p.nombre, " +
                "p.apellido, " +
                "p.nombre || ' ' || p.apellido " +
                "AS nombre_responsable " +
                "FROM personal p " +
                "WHERE p.estado = TRUE " +
                "OR p.id_personal = ? " +
                "ORDER BY p.apellido, p.nombre";


        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    idActual
            );


            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    Asignacion a =
                            new Asignacion();

                    a.setId_personal_responsable(
                            rs.getInt(
                                    "id_personal"
                            )
                    );

                    a.setNombre_responsable(
                            rs.getString(
                                    "nombre_responsable"
                            )
                    );

                    lista.add(a);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar personal:"
            );

            e.printStackTrace();
        }

        return lista;
    }


    // MAPEAR RESULTADO

    private Asignacion mapear(
            ResultSet rs)
            throws Exception {

        Asignacion a =
                new Asignacion();


        a.setId_asignacion(
                rs.getInt(
                        "id_asignacion"
                )
        );

        a.setId_ruta(
                rs.getInt(
                        "id_ruta"
                )
        );

        a.setId_vehiculo(
                rs.getInt(
                        "id_vehiculo"
                )
        );

        a.setId_personal_responsable(
                rs.getInt(
                        "id_personal_responsable"
                )
        );


        if (rs.getDate("fecha") != null) {

            a.setFecha(
                    rs.getDate(
                            "fecha"
                    ).toString()
            );
        }


        if (rs.getTime("hora_salida") != null) {

            a.setHora_salida(
                    rs.getTime(
                            "hora_salida"
                    ).toString()
            );
        }


        if (rs.getTime("hora_regreso") != null) {

            a.setHora_regreso(
                    rs.getTime(
                            "hora_regreso"
                    ).toString()
            );
        }


        a.setEstado(
                rs.getString(
                        "estado"
                )
        );

        a.setObservaciones(
                rs.getString(
                        "observaciones"
                )
        );

        a.setNombre_ruta(
                rs.getString(
                        "nombre_ruta"
                )
        );

        a.setChapa(
                rs.getString(
                        "chapa"
                )
        );

        a.setNombre_responsable(
                rs.getString(
                        "nombre_responsable"
                )
        );


        return a;
    }


    // CARGAR DATOS PARA INSERTAR / ACTUALIZAR

    private void cargarDatos(
            PreparedStatement ps,
            Asignacion a,
            boolean actualizar)
            throws Exception {

        ps.setInt(
                1,
                a.getId_ruta()
        );

        ps.setInt(
                2,
                a.getId_vehiculo()
        );

        ps.setInt(
                3,
                a.getId_personal_responsable()
        );

        ps.setDate(
                4,
                Date.valueOf(
                        a.getFecha()
                )
        );


        if (a.getHora_salida() != null &&
            !a.getHora_salida().trim().isEmpty()) {

            ps.setTime(
                    5,
                    Time.valueOf(
                            normalizarHora(
                                    a.getHora_salida()
                            )
                    )
            );

        } else {

            ps.setNull(
                    5,
                    java.sql.Types.TIME
            );
        }


        if (a.getHora_regreso() != null &&
            !a.getHora_regreso().trim().isEmpty()) {

            ps.setTime(
                    6,
                    Time.valueOf(
                            normalizarHora(
                                    a.getHora_regreso()
                            )
                    )
            );

        } else {

            ps.setNull(
                    6,
                    java.sql.Types.TIME
            );
        }


        String estado =
                a.getEstado();


        if (estado == null ||
            estado.trim().isEmpty()) {

            estado = "PROGRAMADA";
        }


        ps.setString(
                7,
                estado
        );

        ps.setString(
                8,
                a.getObservaciones()
        );


        if (actualizar) {

            ps.setInt(
                    9,
                    a.getId_asignacion()
            );
        }
    }


    // NORMALIZAR HORA

    private String normalizarHora(
            String hora) {

        if (hora == null) {

            return null;
        }


        hora = hora.trim();


        if (hora.length() == 5) {

            return hora + ":00";
        }


        return hora;
    }
}