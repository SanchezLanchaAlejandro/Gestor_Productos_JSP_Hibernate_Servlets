package org.example.p10_alejandro_sanchez.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.p10_alejandro_sanchez.Producto;
import org.example.p10_alejandro_sanchez.Usuario;

import java.util.List;

public class ProductoDAO {
    private static final String PERSISTENCE_UNIT_NAME = "p10PersistenceUnit";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Busca un producto por su ID.
     * @param id El ID del producto.
     * @return El producto encontrado o null si no existe.
     */
    public Producto buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        Producto producto = null;
        try {
            producto = em.find(Producto.class, id);
        } finally {
            em.close();
        }
        return producto;
    }

    /**
     * Obtiene todos los productos de un usuario en la base de datos.
     * @return Lista de productos.
     */
    public List<Producto> obtenerProductosPorUsuario(int usuarioId) {
        EntityManager em = emf.createEntityManager();
        List<Producto> productos = null;
        try {
            TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE p.usuario.id = :usuarioId", Producto.class);
            query.setParameter("usuarioId", usuarioId);
            productos = query.getResultList();
        } finally {
            em.close();
        }
        return productos;
    }

    /**
     * Elimina un producto de la base de datos y actualiza el campo calculado del usuario.
     * @param id El ID del producto a eliminar.
     */
    public void eliminarProducto(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            if (producto != null) {
                int usuarioId = producto.getUsuario().getId();
                em.remove(producto);
                System.out.println("✅ Producto eliminado correctamente.");
                em.getTransaction().commit();
                usuarioDAO.actualizarCampoCalculado(usuarioId);
            } else {
                System.out.println("⚠️ Producto no encontrado.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}