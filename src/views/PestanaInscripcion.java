package views;

import controllers.ControladorEstudiante;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Curso;
import models.Estudiante;

/**
 * Pestaña de interfaz para gestionar inscripciones de estudiantes en cursos.
 */
public class PestanaInscripcion {

    private final ControladorEstudiante controlador;
    private final ObservableList<Estudiante> estudiantes;
    private final ObservableList<Curso> cursos;
    private final Runnable actualizar;

    public PestanaInscripcion(ControladorEstudiante controlador,
                              ObservableList<Estudiante> estudiantes,
                              ObservableList<Curso> cursos,
                              Runnable actualizar) {
        this.controlador = controlador;
        this.estudiantes = estudiantes;
        this.cursos = cursos;
        this.actualizar = actualizar;
    }

    /**
     * Construye la pestaña JavaFX para inscripciones.
     */
    public Tab construir() {
        ListView<Estudiante> listaEstudiantes = new ListView<>(estudiantes);
        ListView<Curso> listaCursos = new ListView<>(cursos);
        Button botonInscribir = new Button("Inscribir");
        Button botonRemover = new Button("Remover");
        Label mensaje = new Label();
        mensaje.getStyleClass().add("message");

        botonInscribir.setOnAction(e -> {
            Estudiante estudiante = listaEstudiantes.getSelectionModel().getSelectedItem();
            Curso curso = listaCursos.getSelectionModel().getSelectedItem();
            if (estudiante != null && curso != null) {
                try {
                    controlador.inscribirEstudiante(estudiante.getId(), curso.getId());
                    actualizar.run();
                    mensaje.setText("Inscripción realizada");
                    mensaje.getStyleClass().remove("error");
                } catch (Exception ex) {
                    mensaje.setText("Error: " + ex.getMessage());
                    mensaje.getStyleClass().add("error");
                }
            }
        });

        botonRemover.setOnAction(e -> {
            Estudiante estudiante = listaEstudiantes.getSelectionModel().getSelectedItem();
            Curso curso = listaCursos.getSelectionModel().getSelectedItem();
            if (estudiante != null && curso != null) {
                controlador.removerInscripcion(estudiante.getId(), curso.getId());
                actualizar.run();
                mensaje.setText("Inscripción eliminada");
                mensaje.getStyleClass().remove("error");
            }
        });

        HBox listas = new HBox(10, listaEstudiantes, listaCursos);
        HBox botones = new HBox(10, botonInscribir, botonRemover);
        VBox vbox = new VBox(listas, botones, mensaje);
        vbox.setPadding(new Insets(10));
        Tab tab = new Tab("Inscripciones", vbox);
        tab.setClosable(false);
        return tab;
    }
}
