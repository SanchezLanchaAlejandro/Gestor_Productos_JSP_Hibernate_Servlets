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
<% List<Producto> productos = (List<Producto>) request.getAttribute("productos"); %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ver Productos en Box</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>

<%@ include file="navbar.jsp" %>

<!-- Contenido -->
<div class="container mt-4">
    <h2 class="text-center text-primary">📦 Productos en Box 📦</h2>
    <p class="text-center">Explora los productos de una forma visual.</p>

    <div class="row">
        <% if (productos != null && !productos.isEmpty()) {
            for (Producto p : productos) { %>
        <div class="col-md-4 mb-4">
            <div class="card shadow">
                <img src="<%= (p.getImagen() != null && !p.getImagen().trim().equals("")) ? p.getImagen() : "img/default-producto.jpg" %>"
                     class="card-img-top" alt="Imagen de producto">
                <div class="card-body">
                    <h5 class="card-title"><%= p.getNombre() %>
                    </h5>
                    <p class="card-text"><strong>Tipo:</strong> <%= p.getTipo() %>
                    </p>
                    <p class="card-text"><strong>Precio:</strong> <%= p.getPrecio() %>€</p>
                    <a href="ProductoServlet?action=verDetalle&id=<%= p.getId() %>&from=ProductoServlet?action=verBox"
                       class="btn btn-primary">🔍 Ver Detalle</a>
                </div>
            </div>
        </div>
        <% }
        } else { %>
        <div class="col-12 text-center text-muted">
            <p>No hay productos disponibles.</p>
        </div>
        <% } %>
    </div>
    <div class="text-center mt-4">
        <a href="home.jsp" class="btn btn-secondary">🏠 Volver al inicio</a>
    </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white text-center p-3 mt-4">
    <p>&copy; 2025 - Gestor de Productos | Desarrollado por Alejandro Sánchez</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>