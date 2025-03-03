<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.p10_alejandro_sanchez.Usuario" %>
<%
    // Obtener el usuario de la sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    // Refrescar el usuario consultándolo de la base de datos
    usuario = new org.example.p10_alejandro_sanchez.dao.UsuarioDAO().buscarPorIdentificador(usuario.getIdentificador());
    session.setAttribute("usuario", usuario);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Inicio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

<!-- Contenido principal -->
<div class="container mt-4">
    <h2 class="text-center">Bienvenido, <%= usuario.getNombre() %> 👋</h2>
    <p class="text-center">Gestiona tus productos fácilmente.</p>

    <!-- Mostrar cantidad de productos del usuario -->
    <div class="alert alert-info text-center mt-4">
        🛒 Actualmente tienes <strong><%= usuario.getCampoCalculado() %></strong> productos en tu cuenta.
    </div>

    <div class="row mt-4">
        <div class="col-md-6">
            <a href="ProductoServlet?action=listar" class="btn btn-primary w-100">📊 Ver en Tablas</a>
        </div>
        <div class="col-md-6">
            <a href="ProductoServlet?action=verBox" class="btn btn-success w-100">📦 Ver en Box</a>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white text-center p-3 mt-4">
    <p>&copy; 2025 - Gestor de Productos | Desarrollado por Alejandro Sánchez</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>