package ma.youcode.teleexpertise.repository;

import ma.youcode.teleexpertise.entity.DemandeExpertise;
import jakarta.persistence.EntityManager;
import ma.youcode.teleexpertise.config.JPAUtil;


public class DemandeExpertiseRepository{
    private final EntityManager entityManager;

    public DemandeExpertiseRepository(){
        this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    public DemandeExpertise save(DemandeExpertise demande){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(demande);
            entityManager.getTransaction().commit();
            return demande;
        } catch(Exception e){
            if (entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }
}