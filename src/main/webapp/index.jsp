<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Si ya hay una sesión iniciada va al dashboard; si no, al login.
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
    } else {
        response.sendRedirect("dashboard.jsp");
    }
%>
