package org.example.p10_alejandro_sanchez.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.p10_alejandro_sanchez.Producto;
import org.example.p10_alejandro_sanchez.Usuario;
import org.example.p10_alejandro_sanchez.dao.ProductoDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/ProductoServlet")
@MultipartConfig(maxFileSize = 16177215) // 16MB máximo
public class ProductoServlet extends HttpServlet {
    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("DEBUG: ProductoServlet.doGet - Action: " + action);

        if ("listar".equals(action)) {
            listarProductos(request, response, "productos.jsp");
        } else if ("verBox".equals(action)) {
            listarProductos(request, response, "verEnBox.jsp");
        } else if ("verDetalle".equals(action)) {
            verDetalleProducto(request, response);
        } else if ("eliminar".equals(action)) {
            eliminarProducto(request, response);
        } else {
            response.sendRedirect("productos.jsp");
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response, String pagina)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            System.out.println("DEBUG: ProductoServlet.listarProductos - No hay usuario en sesión.");
            response.sendRedirect("index.jsp");
            return;
        }
        // Obtener solo los productos del usuario logueado
        List<Producto> productos = productoDAO.obtenerProductosPorUsuario(usuario.getId());
        System.out.println("DEBUG: ProductoServlet.listarProductos - Productos obtenidos para " + usuario.getNombre() + ": " + productos.size());
        request.setAttribute("productos", productos);
        request.getRequestDispatcher(pagina).forward(request, response);
    }

    private void verDetalleProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Producto producto = productoDAO.buscarPorId(id);
        String from = request.getParameter("from");
        if (from != null && !from.isEmpty()) {
            request.getSession().setAttribute("from", from);
        }
        if (producto != null) {
            request.setAttribute("producto", producto);
            request.getRequestDispatcher("detalleProducto.jsp").forward(request, response);
        } else {
            response.sendRedirect("ProductoServlet?action=listar");
        }
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productoDAO.eliminarProducto(id);
        response.sendRedirect("ProductoServlet?action=listar");
    }
}