package org.example.p10_alejandro_sanchez.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.p10_alejandro_sanchez.Usuario;
import org.example.p10_alejandro_sanchez.dao.UsuarioDAO;

import java.io.IOException;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            cerrarSesion(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            autenticarUsuario(request, response);
        } else if ("logout".equals(action)) {
            cerrarSesion(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    private void autenticarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String identificador = request.getParameter("identificador");
        String password = request.getParameter("password");

        Usuario usuario = usuarioDAO.buscarPorIdentificador(identificador);

        if (usuario != null && usuario.getPassword().equals(password)) {
            HttpSession session = request.getSession();

            // 🔹 Antes de guardar en sesión, actualizamos el Campo Calculado
            usuarioDAO.actualizarCampoCalculado(usuario.getId());

            // Volvemos a obtener el usuario actualizado con la nueva suma de precios
            usuario = usuarioDAO.buscarPorIdentificador(identificador);

            session.setAttribute("usuario", usuario);
            System.out.println("✅ Usuario autenticado: " + usuario.getNombre() + ". Campo Calculado: " + usuario.getCampoCalculado());

            response.sendRedirect("home.jsp");
        } else {
            request.setAttribute("error", "Credenciales incorrectas.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
}