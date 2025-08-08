package controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import models.Curso;
import models.Estudiante;
import utils.JPAUtil;

import java.util.List;

public class ControladorCurso {

    public void crearCurso(String nombre, int creditos) {
        EntityManager gestor = JPAUtil.getEntityManager();
        EntityTransaction transaccion = gestor.getTransaction();
        transaccion.begin();
        Curso curso = new Curso(nombre, creditos);
        gestor.persist(curso);
        transaccion.commit();
        gestor.close();
    }

    public List<Curso> buscarTodos() {
        EntityManager gestor = JPAUtil.getEntityManager();
        TypedQuery<Curso> consulta = gestor.createQuery("SELECT c FROM Curso c", Curso.class);
        List<Curso> resultado = consulta.getResultList();
        gestor.close();
        return resultado;
    }

    public void actualizarCurso(Long id, String nombre, int creditos) {
        EntityManager gestor = JPAUtil.getEntityManager();
        EntityTransaction transaccion = gestor.getTransaction();
        transaccion.begin();
        Curso curso = gestor.find(Curso.class, id);
        if (curso != null) {
            curso.setNombre(nombre);
            curso.setCreditos(creditos);
        }
        transaccion.commit();
        gestor.close();
    }

    public void eliminarCurso(Long id) {
        EntityManager gestor = JPAUtil.getEntityManager();
        EntityTransaction transaccion = gestor.getTransaction();
        transaccion.begin();
        Curso curso = gestor.find(Curso.class, id);
        if (curso != null) {
            gestor.remove(curso);
        }
        transaccion.commit();
        gestor.close();
    }

    public List<Estudiante> estudiantesEnCurso(Long cursoId) {
        EntityManager gestor = JPAUtil.getEntityManager();
        Curso curso = gestor.find(Curso.class, cursoId);
        curso.getEstudiantes().size();
        List<Estudiante> estudiantes = List.copyOf(curso.getEstudiantes());
        gestor.close();
        return estudiantes;
    }

    public List<Curso> filtrar(String nombre, Integer creditos) {
        EntityManager gestor = JPAUtil.getEntityManager();
        String jpql = "SELECT c FROM Curso c WHERE 1=1";
        if (nombre != null && !nombre.isBlank()) {
            jpql += " AND LOWER(c.nombre) LIKE LOWER(:nombre)";
        }
        if (creditos != null) {
            jpql += " AND c.creditos = :creditos";
        }
        TypedQuery<Curso> consulta = gestor.createQuery(jpql, Curso.class);
        if (nombre != null && !nombre.isBlank()) {
            consulta.setParameter("nombre", "%" + nombre + "%");
        }
        if (creditos != null) {
            consulta.setParameter("creditos", creditos);
        }
        List<Curso> resultado = consulta.getResultList();
        gestor.close();
        return resultado;
    }
}