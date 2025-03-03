package org.example.p10_alejandro_sanchez.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.p10_alejandro_sanchez.Usuario;

import java.util.List;

public class UsuarioDAO {
    private static final String PERSISTENCE_UNIT_NAME = "p10PersistenceUnit";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    /**
     * Guarda un usuario en la base de datos.
     * @param usuario El usuario a guardar.
     */
    public void guardarUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            System.out.println("✅ Usuario guardado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza el campoCalculado de un usuario con la cantidad de productos que tiene.
     */
    public void actualizarCampoCalculado(int usuarioId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, usuarioId);
            if (usuario != null) {
                // 🔹 Contar cuántos productos tiene el usuario
                TypedQuery<Long> query = em.createQuery(
                        "SELECT COUNT(p) FROM Producto p WHERE p.usuario.id = :usuarioId", Long.class);
                query.setParameter("usuarioId", usuarioId);
                long cantidadProductos = query.getSingleResult();

                usuario.setCampoCalculado((int) cantidadProductos);
                em.merge(usuario);
            }
            em.getTransaction().commit();
            System.out.println("🔄 Campo calculado actualizado para el usuario con ID " + usuarioId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Busca un usuario por su identificador único.
     * @param identificador El identificador del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuario buscarPorIdentificador(String identificador) {
        EntityManager em = emf.createEntityManager();
        Usuario usuario = null;
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.identificador = :identificador", Usuario.class);
            query.setParameter("identificador", identificador);
            List<Usuario> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                usuario = resultados.get(0);
            }
        } finally {
            em.close();
        }
        return usuario;
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     * @return Lista de usuarios.
     */
    public List<Usuario> obtenerTodosLosUsuarios() {
        EntityManager em = emf.createEntityManager();
        List<Usuario> usuarios = null;
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            usuarios = query.getResultList();
        } finally {
            em.close();
        }
        return usuarios;
    }

    /**
     * Actualiza los datos de un usuario en la base de datos.
     * @param usuario El usuario con los datos actualizados.
     */
    public void actualizarUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
            System.out.println("✅ Usuario actualizado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     * @param id El ID del usuario a eliminar.
     */
    public void eliminarUsuario(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
                System.out.println("✅ Usuario eliminado correctamente.");
            } else {
                System.out.println("⚠️ Usuario no encontrado.");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}