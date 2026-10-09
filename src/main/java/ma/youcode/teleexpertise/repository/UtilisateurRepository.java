package ma.youcode.teleexpertise.repository;

import jakarta.persistence.EntityManager;
import ma.youcode.teleexpertise.config.JPAUtil;
import ma.youcode.teleexpertise.entity.Utilisateur;

public class UtilisateurRepository {

    public Utilisateur findByUsername(String username) {

        EntityManager em =
                JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.username = :username",
                    Utilisateur.class
            )
            .setParameter("username", username)
            .getResultStream()
            .findFirst()
            .orElse(null);

        } finally {
            em.close();
        }
    }
}