package ma.youcode.teleexpertise.repository;

import jakarta.persistence.EntityManager;
import ma.youcode.teleexpertise.config.JPAUtil;
import ma.youcode.teleexpertise.entity.DemandeExpertise;

public class DemandeExpertiseRepository {

    private final EntityManager entityManager;

    public DemandeExpertiseRepository() {
        this.entityManager = JPAUtil
                .getEntityManagerFactory()
                .createEntityManager();
    }

    public boolean specialisteExiste(Long specialisteId) {
        Long count = entityManager
                .createQuery(
                        "SELECT COUNT(s) FROM Specialiste s WHERE s.id = :id",
                        Long.class
                )
                .setParameter("id", specialisteId)
                .getSingleResult();

        return count > 0;
    }

    public boolean consultationExiste(Long consultationId) {
        Object result = entityManager
                .createNativeQuery(
                        "SELECT COUNT(*) FROM consultation WHERE id = :id"
                )
                .setParameter("id", consultationId)
                .getSingleResult();

        Number count = (Number) result;

        return count.longValue() > 0;
    }

    public DemandeExpertise save(DemandeExpertise demande) {
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(demande);

            entityManager.getTransaction().commit();

            return demande;

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            throw e;
        }
    }
}