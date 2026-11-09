package com.dao;

import com.config.Conexion;
import com.model.Ruta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class RutaDAO {

    Conexion cn = new Conexion();


    // LISTAR TODAS

    public List<Ruta> listar() {

        List<Ruta> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "r.id_ruta, "
                + "r.nombre, "
                + "r.descripcion, "
                + "r.id_barrio, "
                + "r.estado, "
                + "b.nombre AS nombre_barrio "
                + "FROM ruta r "
                + "LEFT JOIN barrio b "
                + "ON r.id_barrio = b.id_barrio "
                + "ORDER BY r.id_ruta";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Ruta r = new Ruta();


                r.setId_ruta(
                        rs.getInt("id_ruta")
                );


                r.setNombre(
                        rs.getString("nombre")
                );


                r.setDescripcion(
                        rs.getString("descripcion")
                );


                r.setId_barrio(
                        rs.getInt("id_barrio")
                );


                r.setNombre_barrio(
                        rs.getString("nombre_barrio")
                );


                /*
                 * IMPORTANTE:
                 *
                 * PostgreSQL tiene estado como BOOLEAN.
                 *
                 * true  = ACTIVO
                 * false = INACTIVO
                 *
                 * El modelo Ruta trabaja con String,
                 * por eso convertimos aquí.
                 */

                boolean estado =
                        rs.getBoolean("estado");


                if (estado) {

                    r.setEstado("ACTIVO");

                } else {

                    r.setEstado("INACTIVO");

                }


                lista.add(r);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return lista;
    }


    // LISTAR ACTIVAS

    public List<Ruta> listarActivas() {

        List<Ruta> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "r.id_ruta, "
                + "r.nombre, "
                + "r.descripcion, "
                + "r.id_barrio, "
                + "r.estado, "
                + "b.nombre AS nombre_barrio "
                + "FROM ruta r "
                + "LEFT JOIN barrio b "
                + "ON r.id_barrio = b.id_barrio "
                + "WHERE r.estado = TRUE "
                + "ORDER BY r.nombre";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Ruta r = new Ruta();


                r.setId_ruta(
                        rs.getInt("id_ruta")
                );


                r.setNombre(
                        rs.getString("nombre")
                );


                r.setDescripcion(
                        rs.getString("descripcion")
                );


                r.setId_barrio(
                        rs.getInt("id_barrio")
                );


                r.setNombre_barrio(
                        rs.getString("nombre_barrio")
                );


                boolean estado =
                        rs.getBoolean("estado");


                if (estado) {

                    r.setEstado("ACTIVO");

                } else {

                    r.setEstado("INACTIVO");

                }


                lista.add(r);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return lista;
    }


    // BUSCAR POR ID

    public Ruta buscarPorId(int id) {

        Ruta r = null;

        String sql =
                "SELECT "
                + "r.id_ruta, "
                + "r.nombre, "
                + "r.descripcion, "
                + "r.id_barrio, "
                + "r.estado, "
                + "b.nombre AS nombre_barrio "
                + "FROM ruta r "
                + "LEFT JOIN barrio b "
                + "ON r.id_barrio = b.id_barrio "
                + "WHERE r.id_ruta = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
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

                    r = new Ruta();


                    r.setId_ruta(
                            rs.getInt("id_ruta")
                    );


                    r.setNombre(
                            rs.getString("nombre")
                    );


                    r.setDescripcion(
                            rs.getString("descripcion")
                    );


                    r.setId_barrio(
                            rs.getInt("id_barrio")
                    );


                    r.setNombre_barrio(
                            rs.getString("nombre_barrio")
                    );


                    boolean estado =
                            rs.getBoolean("estado");


                    if (estado) {

                        r.setEstado("ACTIVO");

                    } else {

                        r.setEstado("INACTIVO");

                    }

                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return r;
    }


    // INSERTAR

    public boolean insertar(Ruta r) {

        String sql =
                "INSERT INTO ruta "
                + "(nombre, descripcion, id_barrio, estado) "
                + "VALUES (?, ?, ?, TRUE)";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    r.getNombre()
            );


            ps.setString(
                    2,
                    r.getDescripcion()
            );


            ps.setInt(
                    3,
                    r.getId_barrio()
            );


            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ACTUALIZAR

    public boolean actualizar(Ruta r) {

        String sql =
                "UPDATE ruta SET "
                + "nombre = ?, "
                + "descripcion = ?, "
                + "id_barrio = ? "
                + "WHERE id_ruta = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    r.getNombre()
            );


            ps.setString(
                    2,
                    r.getDescripcion()
            );


            ps.setInt(
                    3,
                    r.getId_barrio()
            );


            ps.setInt(
                    4,
                    r.getId_ruta()
            );


            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // BAJA LÓGICA

    public boolean eliminar(int id) {

        String sql =
                "UPDATE ruta "
                + "SET estado = FALSE "
                + "WHERE id_ruta = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    id
            );


            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ACTIVAR

    public boolean activar(int id) {

        String sql =
                "UPDATE ruta "
                + "SET estado = TRUE "
                + "WHERE id_ruta = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    id
            );


            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

}