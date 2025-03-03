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
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    /**
     * Busca un producto por su ID.
     * @param id El ID del producto.
     * @return El producto encontrado o null si no existe.
     */
    public Producto buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los productos asociados a un usuario específico.
     * @param usuarioId El ID del usuario.
     * @return Lista de productos del usuario.
     */
    public List<Producto> obtenerProductosPorUsuario(int usuarioId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE p.usuario.id = :usuarioId", Producto.class);
            query.setParameter("usuarioId", usuarioId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza los datos de un producto en la base de datos y actualiza el campo calculado del usuario.
     * @param producto El producto con los datos actualizados.
     */
    public void actualizarProducto(Producto producto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();
            System.out.println("DEBUG: ProductoDAO.actualizarProducto - Producto actualizado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("ERROR en ProductoDAO.actualizarProducto: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        if (producto.getUsuario() != null) {
            new UsuarioDAO().actualizarCampoCalculado(producto.getUsuario().getId());
        }
    }

    /**
     * Elimina un producto de la base de datos y actualiza el campo calculado del usuario.
     * @param id El ID del producto a eliminar.
     */
    public void eliminarProducto(int id) {
        EntityManager em = emf.createEntityManager();
        int usuarioId = -1;
        try {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            if (producto != null) {
                if (producto.getUsuario() != null) {
                    usuarioId = producto.getUsuario().getId();
                }
                em.remove(producto);
                em.getTransaction().commit();
                System.out.println("DEBUG: ProductoDAO.eliminarProducto - Producto eliminado correctamente.");
            } else {
                System.out.println("⚠️ Producto no encontrado.");
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("ERROR en ProductoDAO.eliminarProducto: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        if (usuarioId != -1) {
            new UsuarioDAO().actualizarCampoCalculado(usuarioId);
        }
    }
}