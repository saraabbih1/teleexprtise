package ma.youcode.teleexpertise.config;

public class TestDB {

    public static void main(String[] args) {
        JPAUtil.getEntityManagerFactory();
        System.out.println("Connexion à la base de données réussie !");
    }
}