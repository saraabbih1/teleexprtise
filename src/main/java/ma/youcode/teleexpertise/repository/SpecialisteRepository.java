package ma.youcode.teleexpertise.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ma.youcode.teleexpertise.config.JPAUtil;
import ma.youcode.teleexpertise.entity.Specialiste;
import ma.youcode.teleexpertise.entity.Specialite;


public class SpecialisteRepository {

    public List<Specialiste> findBySpecialite(Specialite specialite) {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            TypedQuery<Specialiste> query = em.createQuery(
                    "SELECT s FROM Specialiste s WHERE s.specialite = :specialite ORDER BY s.tarif ASC",
                    Specialiste.class
            );

            query.setParameter("specialite", specialite);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public Specialiste findByUtilisateurId(Long utilisateurId) {

        EntityManager em = JPAUtil.getEntityManagerFactory()
            .createEntityManager();

        try {
            return em.createQuery(
                "SELECT s FROM Specialiste s WHERE s.utilisateurId = :utilisateurId",
                Specialiste.class
            )
            .setParameter("utilisateurId", utilisateurId)
            .getSingleResult();

        } catch (jakarta.persistence.NoResultException e) {
            return null;

        } finally {
            em.close();
        }
    }

    public Specialiste findByUsername(String username) {

        EntityManager em = JPAUtil.getEntityManagerFactory()
            .createEntityManager();

        try {
            return em.createQuery(
                "SELECT s FROM Specialiste s, Utilisateur u "
                + "WHERE s.utilisateurId = u.id "
                + "AND u.username = :username",
                Specialiste.class
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