package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utilidad para gestionar la fábrica de EntityManager de JPA.
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "escuelaPU";
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            // Intentar crear un EntityManager para validar la conexión real
            EntityManager em = emf.createEntityManager();
            em.close();
            System.out.println("\u2705 Conexión a la base de datos establecida correctamente.");
        } catch (Exception e) {
            emf = null; // Asegura que no se use una fábrica inválida
            String msg = e.getMessage();
            if (msg != null && msg.contains("com.mysql.cj.jdbc.Driver")) {
                System.err.println(
                        "\u274c No se encontró el driver JDBC de MySQL. Agrega la dependencia correspondiente.");
            } else {
                System.err.println("\u274c Error al establecer la conexión con la base de datos: " + msg);
            }
        }
    }

    /** Obtiene un nuevo EntityManager. */
    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("La fábrica de EntityManager no está inicializada.");
        }
        return emf.createEntityManager();
    }

    /** Devuelve la fábrica subyacente. */
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}