package ma.youcode.teleexpertise.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("cliniquePU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}