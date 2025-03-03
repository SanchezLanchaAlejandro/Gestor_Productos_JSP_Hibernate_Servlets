package org.example.p10_alejandro_sanchez.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.p10_alejandro_sanchez.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class UsuarioDAO {
    private static final String PERSISTENCE_UNIT_NAME = "p10PersistenceUnit";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    /**
     * Actualiza el campoCalculado de un usuario con la suma de los precios de sus productos.
     */
    public void actualizarCampoCalculado(int usuarioId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, usuarioId);
            if (usuario != null) {
                // Sumar el precio de todos los productos del usuario
                TypedQuery<BigDecimal> query = em.createQuery(
                        "SELECT COALESCE(SUM(p.precio), 0) FROM Producto p WHERE p.usuario.id = :usuarioId", BigDecimal.class);
                query.setParameter("usuarioId", usuarioId);
                BigDecimal sumaPrecios = query.getSingleResult();

                usuario.setCampoCalculado(sumaPrecios);
                em.merge(usuario);
                System.out.println("🔄 Campo calculado actualizado para el usuario con ID " + usuarioId + ": " + sumaPrecios + "€");
            }
            em.getTransaction().commit();
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
}