package controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import models.Curso;
import models.Estudiante;
import utils.JPAUtil;

import java.util.List;

public class ControladorEstudiante {

    public void crearEstudiante(String nombre, String correo) {
        EntityManager gestor = JPAUtil.getEntityManager();
        EntityTransaction transaccion = gestor.getTransaction();
        transaccion.begin();
        Estudiante estudiante = new Estudiante(nombre, correo);
        gestor.persist(estudiante);
        transaccion.commit();
        gestor.close();
    }

    public List<Estudiante> buscarTodos() {
        EntityManager gestor = JPAUtil.getEntityManager();
        TypedQuery<Estudiante> consulta = gestor.createQuery("SELECT e FROM Estudiante e", Estudiante.class);
        List<Estudiante> resultado = consulta.getResultList();
        gestor.close();
        return resultado;
    }

    public void actualizarEstudiante(Long id, String nombre, String correo) {
        EntityManager gestor = JPAUtil.getEntityManager();
        EntityTransaction transaccion = gestor.getTransaction();
        transaccion.begin();
        Estudiante estudiante = gestor.find(Estudiante.class, id);
        if (estudiante != null) {
            estudiante.setNombre(nombre);
            estudiante.setCorreo(correo);
        }
        transaccion.commit();
        gestor.close();
    }

    public void eliminarEstudiante(Long id) {
        EntityManager gestor = JPAUtil.getEntityManager();
        EntityTransaction transaccion = gestor.getTransaction();
        transaccion.begin();
        Estudiante estudiante = gestor.find(Estudiante.class, id);
        if (estudiante != null) {
            gestor.remove(estudiante);
        }
        transaccion.commit();
        gestor.close();
    }

    public void inscribirEstudiante(Long estudianteId, Long cursoId) {
        EntityManager gestor = JPAUtil.getEntityManager();
        EntityTransaction transaccion = gestor.getTransaction();
        transaccion.begin();
        Estudiante estudiante = gestor.find(Estudiante.class, estudianteId);
        Curso curso = gestor.find(Curso.class, cursoId);
        if (estudiante != null && curso != null) {
            estudiante.getCursos().add(curso);
            curso.getEstudiantes().add(estudiante);
        }
        transaccion.commit();
        gestor.close();
    }

    public void removerInscripcion(Long estudianteId, Long cursoId) {
        EntityManager gestor = JPAUtil.getEntityManager();
        EntityTransaction transaccion = gestor.getTransaction();
        transaccion.begin();
        Estudiante estudiante = gestor.find(Estudiante.class, estudianteId);
        Curso curso = gestor.find(Curso.class, cursoId);
        if (estudiante != null && curso != null) {
            estudiante.getCursos().remove(curso);
            curso.getEstudiantes().remove(estudiante);
        }
        transaccion.commit();
        gestor.close();
    }

    public List<Curso> cursosDeEstudiante(Long estudianteId) {
        EntityManager gestor = JPAUtil.getEntityManager();
        Estudiante estudiante = gestor.find(Estudiante.class, estudianteId);
        estudiante.getCursos().size();
        List<Curso> cursos = List.copyOf(estudiante.getCursos());
        gestor.close();
        return cursos;
    }

    public List<Estudiante> buscar(String texto) {
        EntityManager gestor = JPAUtil.getEntityManager();
        TypedQuery<Estudiante> consulta = gestor.createQuery(
                "SELECT e FROM Estudiante e WHERE LOWER(e.nombre) LIKE LOWER(:txt) OR LOWER(e.correo) LIKE LOWER(:txt)",
                Estudiante.class);
        consulta.setParameter("txt", "%" + texto + "%");
        List<Estudiante> resultado = consulta.getResultList();
        gestor.close();
        return resultado;
    }
}