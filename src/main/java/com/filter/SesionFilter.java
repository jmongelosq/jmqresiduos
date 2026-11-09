package com.filter;

import com.config.Permisos;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class SesionFilter implements Filter {

    private static final String[] RUTAS_PUBLICAS = {
        "/login.jsp",
        "/index.jsp",
        "/favicon.ico",
        "/LoginController",
        "/LogoutController"
    };

    private static final String[] CARPETAS_PUBLICAS = {
        "/dist/",
        "/plugins/"
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String ruta = request.getRequestURI().substring(request.getContextPath().length());

        if (esPublica(ruta)) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession sesion = request.getSession(false);
        boolean logueado = sesion != null && sesion.getAttribute("usuario") != null;

        if (!logueado) {
            if (esAjax(request)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write("Sesión expirada");
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
            return;
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        if (!tienePermiso(request, sesion.getAttribute("rol"), ruta)) {
            denegar(request, response);
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean tienePermiso(HttpServletRequest request, Object rol, String ruta)
            throws IOException {

        String modulo = Permisos.moduloDeRuta(ruta);

        if (modulo == null || Permisos.puedeVer(rol, modulo)) {
            return true;
        }

        if (!Permisos.esControlador(ruta) || Permisos.esControladorRestringido(ruta)) {
            return false;
        }

        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = request.getParameter("action");
        }

        return !Permisos.esAccionDeEscritura(accion);
    }

    private void denegar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (esAjax(request) || Permisos.esControlador(request.getRequestURI())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("No tiene permiso para acceder a esta sección.");
        } else {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
        }
    }

    private boolean esAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    private boolean esPublica(String ruta) {
        if (ruta.isEmpty() || ruta.equals("/")) {
            return true;
        }
        for (String r : RUTAS_PUBLICAS) {
            if (ruta.equals(r)) {
                return true;
            }
        }
        for (String c : CARPETAS_PUBLICAS) {
            if (ruta.startsWith(c)) {
                return true;
            }
        }
        return false;
    }
}
