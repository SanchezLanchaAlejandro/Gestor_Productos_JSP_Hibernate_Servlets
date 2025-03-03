<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.p10_alejandro_sanchez.Producto" %>
<%
  Producto producto = (Producto) request.getAttribute("producto");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Detalle del Producto</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container mt-4">
  <h2 class="text-center text-primary mb-4">Detalle del Producto</h2>

  <% if (producto != null) { %>
  <div class="card shadow p-4">
    <h4 class="mb-3">Información del Producto</h4>
    <ul class="list-group">
      <li class="list-group-item"><strong>📌 ID:</strong> <%= producto.getId() %></li>
      <li class="list-group-item"><strong>📌 Nombre:</strong> <%= producto.getNombre() %></li>
      <li class="list-group-item"><strong>📌 Tipo:</strong> <%= producto.getTipo() %></li>
      <li class="list-group-item"><strong>📌 Precio:</strong> <%= producto.getPrecio() %>€</li>
      <li class="list-group-item"><strong>📌 Fecha:</strong> <%= producto.getFecha() %></li>
    </ul>
    <div class="text-center mt-4">
      <a href="ProductoServlet?action=listar" class="btn btn-secondary">📋 Ver Tabla</a>
      <a href="ProductoServlet?action=verBox" class="btn btn-primary">📦 Ver Box</a>
    </div>
  </div>
  <% } else { %>
  <div class="alert alert-danger text-center">
    <strong>⚠️ Producto no encontrado.</strong>
  </div>
  <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>