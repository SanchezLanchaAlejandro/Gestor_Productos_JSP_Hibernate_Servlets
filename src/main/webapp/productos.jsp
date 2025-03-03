<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.p10_alejandro_sanchez.Producto" %>
<%@ page import="org.example.p10_alejandro_sanchez.Usuario" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestión de Productos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container mt-4">
    <h2 class="text-center text-primary mb-4">Gestión de Productos</h2>

    <!-- Tabla de productos -->
    <div class="card shadow p-4">
        <h4 class="mb-3">Lista de Productos</h4>
        <table class="table table-hover table-striped">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Tipo</th>
                <th>Precio</th>
                <th>Fecha</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <% if (productos != null && !productos.isEmpty()) {
                for (Producto p : productos) { %>
            <tr>
                <td><%= p.getId() %>
                </td>
                <td><%= p.getNombre() %>
                </td>
                <td><%= p.getTipo() %>
                </td>
                <td><%= p.getPrecio() %>€</td>
                <td><%= p.getFecha() %>
                </td>
                <td>
                    <a href="ProductoServlet?action=verDetalle&id=<%= p.getId() %>&from=ProductoServlet?action=listar"
                       class="btn btn-info btn-sm">🔍 Ver Detalle</a>
                    <a href="ProductoServlet?action=eliminar&id=<%= p.getId() %>" class="btn btn-danger btn-sm">🗑️ Eliminar</a>
                </td>
            </tr>
            <% }
            } else { %>
            <tr>
                <td colspan="6" class="text-center text-muted">No hay productos registrados.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <div class="text-center mt-4">
        <a href="home.jsp" class="btn btn-secondary">🏠 Volver al inicio</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>