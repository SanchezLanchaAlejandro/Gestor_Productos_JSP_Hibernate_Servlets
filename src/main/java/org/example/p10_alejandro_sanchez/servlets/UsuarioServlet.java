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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            loginUsuario(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            logoutUsuario(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    private void loginUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String identificador = request.getParameter("identificador");
        String password = request.getParameter("password");

        Usuario usuario = usuarioDAO.buscarPorIdentificador(identificador);

        if (usuario != null && usuario.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            System.out.println("✅ Usuario autenticado: " + usuario.getNombre());
            response.sendRedirect("home.jsp");
        } else {
            System.out.println("❌ Credenciales incorrectas.");
            response.sendRedirect("validacionErronea.jsp");
        }
    }

    private void logoutUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            System.out.println("✅ Sesión cerrada correctamente.");
        }
        response.sendRedirect("index.jsp");
    }
}