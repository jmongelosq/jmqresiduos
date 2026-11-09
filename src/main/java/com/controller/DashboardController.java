package com.controller;

import com.dao.DashboardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "DashboardController",
        urlPatterns = {"/DashboardController"}
)
public class DashboardController extends HttpServlet {

    private DashboardDAO dao;

    @Override
    public void init() {

        dao = new DashboardDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        cargarDashboard(request, response);
    }

    private void cargarDashboard(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // INDICADORES PRINCIPALES

        request.setAttribute(
                "totalUsuarios",
                dao.contarUsuarios());

        request.setAttribute(
                "totalPersonal",
                dao.contarPersonal());

        request.setAttribute(
                "totalClientes",
                dao.contarClientes());

        request.setAttribute(
                "totalVehiculos",
                dao.contarVehiculos());

        request.setAttribute(
                "totalRutas",
                dao.contarRutas());

        request.setAttribute(
                "totalTiposResiduos",
                dao.contarTiposResiduos());

        request.setAttribute(
                "totalAsignaciones",
                dao.contarAsignaciones());

        request.setAttribute(
                "totalRecolecciones",
                dao.contarRecolecciones());


        // RESIDUOS RECOLECTADOS

        request.setAttribute(
                "totalResiduosKg",
                dao.totalResiduosRecolectadosKg());


        // ESTADOS DE VEHÍCULOS

        request.setAttribute(
                "vehiculosDisponibles",
                dao.contarVehiculosPorEstado("DISPONIBLE"));

        request.setAttribute(
                "vehiculosAsignados",
                dao.contarVehiculosPorEstado("ASIGNADO"));

        request.setAttribute(
                "vehiculosMantenimiento",
                dao.contarVehiculosPorEstado("MANTENIMIENTO"));

        request.setAttribute(
                "vehiculosFueraServicio",
                dao.contarVehiculosPorEstado("FUERA_SERVICIO"));


        // CARGAR VISTA

        request.getRequestDispatcher(
                "/dashboardHome.jsp")
                .forward(request, response);
    }
}