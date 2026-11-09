package com.controller;

import com.dao.LoginDAO;
import com.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    private final LoginDAO dao = new LoginDAO();

    // LOGIN

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String usuario =
                request.getParameter("usuario");

        String clave =
                request.getParameter("clave");

        // VALIDACIÓN BÁSICA

        if (usuario == null ||
            usuario.trim().isEmpty() ||
            clave == null ||
            clave.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Debe ingresar usuario y contraseña."
            );

            request.getRequestDispatcher(
                    "login.jsp"
            ).forward(request, response);

            return;
        }

        usuario = usuario.trim();

        // VALIDAR CREDENCIALES

        Usuario u =
                dao.validarUsuario(
                        usuario,
                        clave
                );

        // LOGIN CORRECTO

        if (u != null) {

            HttpSession sesion =
                    request.getSession(true);

            // DATOS PRINCIPALES

            sesion.setAttribute(
                    "id_usuario",
                    u.getId_usuario()
            );

            sesion.setAttribute(
                    "usuario",
                    u.getNombre_usuario()
            );

            sesion.setAttribute(
                    "nombre",
                    u.getNombre_completo()
            );

            // DATOS DEL NUEVO SISTEMA

            sesion.setAttribute(
                    "id_personal",
                    u.getId_personal()
            );

            sesion.setAttribute(
                    "id_rol",
                    u.getId_rol()
            );

            sesion.setAttribute(
                    "rol",
                    u.getNombre_rol()
            );

            sesion.setAttribute(
                    "nombre_personal",
                    u.getNombre_personal()
            );

            sesion.setAttribute(
                    "correo",
                    u.getCorreo()
            );

            // EVITAR CACHE

            response.setHeader(
                    "Cache-Control",
                    "no-cache, no-store, must-revalidate"
            );

            response.setHeader(
                    "Pragma",
                    "no-cache"
            );

            response.setDateHeader(
                    "Expires",
                    0
            );

            // IR AL DASHBOARD

            response.sendRedirect(
                    "dashboard.jsp"
            );

        } else {

            // LOGIN INCORRECTO

            request.setAttribute(
                    "error",
                    "Usuario o contraseña incorrectos, o el usuario se encuentra inactivo."
            );

            request.getRequestDispatcher(
                    "login.jsp"
            ).forward(
                    request,
                    response
            );
        }
    }
}