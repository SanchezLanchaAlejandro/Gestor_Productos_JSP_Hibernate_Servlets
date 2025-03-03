<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Barra de navegación -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand text-light" href="home.jsp" style="text-decoration: none;">Gestor de Productos</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item">
          <a class="nav-link" href="ProductoServlet?action=listar">📋 Ver Tabla</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="ProductoServlet?action=verBox">🗂️ Ver en Box</a>
        </li>
        <li class="nav-item">
          <a href="LogoutServlet" class="btn btn-danger">🚪 Cerrar Sesión</a>
        </li>
      </ul>
    </div>
  </div>
</nav>