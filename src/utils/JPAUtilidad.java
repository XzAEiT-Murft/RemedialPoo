package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtilidad {

    private static final EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("escuelaPU");

    public static EntityManager obtenerEntityManager() {
        return fabrica.createEntityManager();
    }
}