package com.dao;

import com.config.Conexion;
import com.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    Conexion cn = new Conexion();


    // LISTAR TODOS LOS CLIENTES

    public List<Cliente> listar() {

        List<Cliente> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "c.id_cliente, "
                + "c.nombre, "
                + "c.apellido, "
                + "c.cedula, "
                + "c.ruc, "
                + "c.telefono, "
                + "c.direccion, "
                + "c.correo, "
                + "c.id_barrio, "
                + "c.estado, "
                + "b.nombre AS nombre_barrio "
                + "FROM clientes c "
                + "LEFT JOIN barrio b "
                + "ON c.id_barrio = b.id_barrio "
                + "ORDER BY c.id_cliente";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Cliente c = new Cliente();

                c.setId_cliente(
                        rs.getInt("id_cliente")
                );

                c.setNombre(
                        rs.getString("nombre")
                );

                c.setApellido(
                        rs.getString("apellido")
                );

                c.setCedula(
                        rs.getString("cedula")
                );

                c.setRuc(
                        rs.getString("ruc")
                );

                c.setTelefono(
                        rs.getString("telefono")
                );

                c.setDireccion(
                        rs.getString("direccion")
                );

                c.setCorreo(
                        rs.getString("correo")
                );

                c.setId_barrio(
                        rs.getInt("id_barrio")
                );

                c.setNombre_barrio(
                        rs.getString("nombre_barrio")
                );

                c.setEstado(
                        rs.getBoolean("estado")
                                ? "ACTIVO"
                                : "INACTIVO"
                );

                lista.add(c);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // LISTAR CLIENTES ACTIVOS

    public List<Cliente> listarActivos() {

        List<Cliente> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "c.id_cliente, "
                + "c.nombre, "
                + "c.apellido, "
                + "c.cedula, "
                + "c.ruc, "
                + "c.telefono, "
                + "c.direccion, "
                + "c.correo, "
                + "c.id_barrio, "
                + "c.estado, "
                + "b.nombre AS nombre_barrio "
                + "FROM clientes c "
                + "LEFT JOIN barrio b "
                + "ON c.id_barrio = b.id_barrio "
                + "WHERE c.estado = TRUE "
                + "ORDER BY c.nombre, c.apellido";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Cliente c = new Cliente();

                c.setId_cliente(
                        rs.getInt("id_cliente")
                );

                c.setNombre(
                        rs.getString("nombre")
                );

                c.setApellido(
                        rs.getString("apellido")
                );

                c.setCedula(
                        rs.getString("cedula")
                );

                c.setRuc(
                        rs.getString("ruc")
                );

                c.setTelefono(
                        rs.getString("telefono")
                );

                c.setDireccion(
                        rs.getString("direccion")
                );

                c.setCorreo(
                        rs.getString("correo")
                );

                c.setId_barrio(
                        rs.getInt("id_barrio")
                );

                c.setNombre_barrio(
                        rs.getString("nombre_barrio")
                );

                c.setEstado(
                        rs.getBoolean("estado")
                                ? "ACTIVO"
                                : "INACTIVO"
                );

                lista.add(c);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    // BUSCAR CLIENTE POR ID

    public Cliente buscarPorId(int id) {

        Cliente c = null;

        String sql =
                "SELECT "
                + "c.id_cliente, "
                + "c.nombre, "
                + "c.apellido, "
                + "c.cedula, "
                + "c.ruc, "
                + "c.telefono, "
                + "c.direccion, "
                + "c.correo, "
                + "c.id_barrio, "
                + "c.estado, "
                + "b.nombre AS nombre_barrio "
                + "FROM clientes c "
                + "LEFT JOIN barrio b "
                + "ON c.id_barrio = b.id_barrio "
                + "WHERE c.id_cliente = ?";

        try (
                Connection con = cn.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    c = new Cliente();

                    c.setId_cliente(
                            rs.getInt("id_cliente")
                    );

                    c.setNombre(
                            rs.getString("nombre")
                    );

                    c.setApellido(
                            rs.getString("apellido")
                    );

                    c.setCedula(
                            rs.getString("cedula")
                    );

                    c.setRuc(
                            rs.getString("ruc")
                    );

                    c.setTelefono(
                            rs.getString("telefono")
                    );

                    c.setDireccion(
                            rs.getString("direccion")
                    );

                    c.setCorreo(
                            rs.getString("correo")
                    );

                    c.setId_barrio(
                            rs.getInt("id_barrio")
                    );

                    c.setNombre_barrio(
                            rs.getString("nombre_barrio")
                    );

                    c.setEstado(
                            rs.getBoolean("estado")
                                    ? "ACTIVO"
                                    : "INACTIVO"
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return c;
    }


    // BUSCAR CLIENTES PARA FACTURACIÓN
    //
    // Permite buscar por:
    //
    // - Nombre
    // - Apellido
    // - Cédula
    // - RUC
    //
    // Características:
    //
    // - Solo clientes activos.
    // - No distingue mayúsculas/minúsculas.
    // - No distingue acentos.
    // - Permite coincidencias parciales.
    // - Máximo 20 resultados.
    //
    // Ejemplos:
    //
    // Gomez   -> José Gómez
    // gomez   -> José Gómez
    // GOMEZ   -> José Gómez
    // Gómez   -> José Gómez
    // gómez   -> José Gómez
    // Go      -> José Gómez
    // Lopez   -> María López
    // Ramirez -> Carlos Ramírez
    // Martinez -> Laura Martínez
    // Benitez -> Roberto Benítez
    // 800     -> clientes cuyo RUC/C.I./dato contenga 800
    //

    public List<Cliente> buscarParaFacturacion(
            String criterio) {

        List<Cliente> lista =
                new ArrayList<>();

        /*
         * Se utiliza TRANSLATE sobre los campos de PostgreSQL
         * para eliminar los principales caracteres acentuados.
         *
         * Ejemplo:
         *
         * Gómez  -> Gomez
         * López  -> Lopez
         * Ramírez -> Ramirez
         * Martínez -> Martinez
         * Benítez -> Benitez
         *
         * El criterio recibido también se normaliza
         * antes de enviarlo a PostgreSQL.
         */

        String sql =
                "SELECT "
                + "c.id_cliente, "
                + "c.nombre, "
                + "c.apellido, "
                + "c.cedula, "
                + "c.ruc, "
                + "c.telefono, "
                + "c.direccion, "
                + "c.correo, "
                + "c.id_barrio, "
                + "c.estado, "
                + "b.nombre AS nombre_barrio "
                + "FROM clientes c "
                + "LEFT JOIN barrio b "
                + "ON c.id_barrio = b.id_barrio "
                + "WHERE c.estado = TRUE "
                + "AND ("

                + "translate("
                + "lower(coalesce(c.nombre, '')), "
                + "'áéíóúüñ', "
                + "'aeiouun'"
                + ") LIKE ? "

                + "OR "

                + "translate("
                + "lower(coalesce(c.apellido, '')), "
                + "'áéíóúüñ', "
                + "'aeiouun'"
                + ") LIKE ? "

                + "OR "

                + "lower(coalesce(c.cedula, '')) "
                + "LIKE ? "

                + "OR "

                + "lower(coalesce(c.ruc, '')) "
                + "LIKE ?"

                + ") "

                + "ORDER BY "
                + "c.nombre, "
                + "c.apellido "

                + "LIMIT 20";


        try (
                Connection con = cn.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            /*
             * Normalizamos lo que escribe el usuario.
             *
             * Gomez  -> gomez
             * Gómez  -> gomez
             * GOMEZ  -> gomez
             * gómez  -> gomez
             */

            String busqueda =
                    normalizarTexto(criterio);


            String patron =
                    "%" + busqueda + "%";


            // Nombre
            ps.setString(
                    1,
                    patron
            );


            // Apellido
            ps.setString(
                    2,
                    patron
            );


            // Cédula
            ps.setString(
                    3,
                    patron
            );


            // RUC
            ps.setString(
                    4,
                    patron
            );


            try (ResultSet rs =
                    ps.executeQuery()) {

                while (rs.next()) {

                    Cliente c =
                            new Cliente();


                    c.setId_cliente(
                            rs.getInt(
                                    "id_cliente"
                            )
                    );


                    c.setNombre(
                            rs.getString(
                                    "nombre"
                            )
                    );


                    c.setApellido(
                            rs.getString(
                                    "apellido"
                            )
                    );


                    c.setCedula(
                            rs.getString(
                                    "cedula"
                            )
                    );


                    c.setRuc(
                            rs.getString(
                                    "ruc"
                            )
                    );


                    c.setTelefono(
                            rs.getString(
                                    "telefono"
                            )
                    );


                    c.setDireccion(
                            rs.getString(
                                    "direccion"
                            )
                    );


                    c.setCorreo(
                            rs.getString(
                                    "correo"
                            )
                    );


                    c.setId_barrio(
                            rs.getInt(
                                    "id_barrio"
                            )
                    );


                    c.setNombre_barrio(
                            rs.getString(
                                    "nombre_barrio"
                            )
                    );


                    c.setEstado(
                            rs.getBoolean(
                                    "estado"
                            )
                            ? "ACTIVO"
                            : "INACTIVO"
                    );


                    lista.add(c);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return lista;
    }


    // NORMALIZAR TEXTO
    //
    // Elimina acentos y convierte a minúsculas.
    //
    // Gómez  -> gomez
    // GÓMEZ  -> gomez
    // Gomez  -> gomez
    // gómez  -> gomez
    //

    private String normalizarTexto(
            String texto) {

        if (texto == null) {
            return "";
        }


        String normalizado =
                Normalizer.normalize(
                        texto.trim(),
                        Normalizer.Form.NFD
                );


        /*
         * Elimina marcas Unicode de acentuación.
         */

        normalizado =
                normalizado.replaceAll(
                        "\\p{M}",
                        ""
                );


        return normalizado.toLowerCase();
    }


    // INSERTAR

    public boolean insertar(Cliente c) {

        String sql =
                "INSERT INTO clientes "
                + "(nombre, apellido, cedula, ruc, "
                + "telefono, direccion, correo, "
                + "id_barrio, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE)";


        try (
                Connection con = cn.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    c.getNombre()
            );

            ps.setString(
                    2,
                    c.getApellido()
            );

            ps.setString(
                    3,
                    c.getCedula()
            );

            ps.setString(
                    4,
                    c.getRuc()
            );

            ps.setString(
                    5,
                    c.getTelefono()
            );

            ps.setString(
                    6,
                    c.getDireccion()
            );

            ps.setString(
                    7,
                    c.getCorreo()
            );

            ps.setInt(
                    8,
                    c.getId_barrio()
            );


            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ACTUALIZAR

    public boolean actualizar(Cliente c) {

        String sql =
                "UPDATE clientes SET "
                + "nombre = ?, "
                + "apellido = ?, "
                + "cedula = ?, "
                + "ruc = ?, "
                + "telefono = ?, "
                + "direccion = ?, "
                + "correo = ?, "
                + "id_barrio = ? "
                + "WHERE id_cliente = ?";


        try (
                Connection con = cn.getConexion();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    c.getNombre()
            );

            ps.setString(
                    2,
                    c.getApellido()
            );

            ps.setString(
                    3,
                    c.getCedula()
            );

            ps.setString(
                    4,
                    c.getRuc()
            );

            ps.setString(
                    5,
                    c.getTelefono()
            );

            ps.setString(
                    6,
                    c.getDireccion()
            );

            ps.setString(
                    7,
                    c.getCorreo()
            );

            ps.setInt(
                    8,
                    c.getId_barrio()
            );

            ps.setInt(
                    9,
                    c.getId_cliente()
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
                "UPDATE clientes "
                + "SET estado = FALSE "
                + "WHERE id_cliente = ?";


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


    // ACTIVAR CLIENTE

    public boolean activar(int id) {

        String sql =
                "UPDATE clientes "
                + "SET estado = TRUE "
                + "WHERE id_cliente = ?";


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