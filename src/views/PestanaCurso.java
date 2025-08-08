package views;

import controllers.ControladorCurso;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Curso;

/**
 * Pestaña de interfaz para gestionar cursos.
 */
public class PestanaCurso {

    private final ControladorCurso controlador;
    private final ObservableList<Curso> cursos;
    private final Runnable actualizar;

    public PestanaCurso(ControladorCurso controlador,
                        ObservableList<Curso> cursos,
                        Runnable actualizar) {
        this.controlador = controlador;
        this.cursos = cursos;
        this.actualizar = actualizar;
    }

    /**
     * Construye la pestaña JavaFX para manejar cursos.
     */
    public Tab construir() {
        TableView<Curso> tabla = new TableView<>(cursos);
        TableColumn<Curso, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        TableColumn<Curso, String> colCreditos = new TableColumn<>("Créditos");
        colCreditos.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCreditos())));
        tabla.getColumns().addAll(colNombre, colCreditos);

        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Nombre");
        TextField campoCreditos = new TextField();
        campoCreditos.setPromptText("Créditos");
        Button botonAgregar = new Button("Agregar");
        Button botonActualizar = new Button("Actualizar");
        Button botonEliminar = new Button("Eliminar");
        Label mensaje = new Label();
        mensaje.getStyleClass().add("message");

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, old, seleccionado) -> {
            if (seleccionado != null) {
                campoNombre.setText(seleccionado.getNombre());
                campoCreditos.setText(String.valueOf(seleccionado.getCreditos()));
            }
        });

        botonAgregar.setOnAction(e -> {
            if (campoNombre.getText().isBlank() || campoCreditos.getText().isBlank()) {
                mensaje.setText("Nombre y créditos son obligatorios");
                mensaje.getStyleClass().add("error");
                return;
            }
            try {
                int creditos = Integer.parseInt(campoCreditos.getText());
                controlador.crearCurso(campoNombre.getText(), creditos);
                actualizar.run();
                mensaje.setText("Curso agregado");
                mensaje.getStyleClass().remove("error");
                campoNombre.clear();
                campoCreditos.clear();
            } catch (NumberFormatException ex) {
                mensaje.setText("Créditos inválidos");
                mensaje.getStyleClass().add("error");
            } catch (Exception ex) {
                mensaje.setText("Error: " + ex.getMessage());
                mensaje.getStyleClass().add("error");
            }
        });

        botonActualizar.setOnAction(e -> {
            Curso seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                try {
                    int creditos = Integer.parseInt(campoCreditos.getText());
                    controlador.actualizarCurso(seleccionado.getId(), campoNombre.getText(), creditos);
                    actualizar.run();
                    mensaje.setText("Curso actualizado");
                    mensaje.getStyleClass().remove("error");
                } catch (NumberFormatException ex) {
                    mensaje.setText("Créditos inválidos");
                    mensaje.getStyleClass().add("error");
                } catch (Exception ex) {
                    mensaje.setText("Error: " + ex.getMessage());
                    mensaje.getStyleClass().add("error");
                }
            }
        });

        botonEliminar.setOnAction(e -> {
            Curso seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                try {
                    controlador.eliminarCurso(seleccionado.getId());
                    actualizar.run();
                    mensaje.setText("Curso eliminado");
                    mensaje.getStyleClass().remove("error");
                    campoNombre.clear();
                    campoCreditos.clear();
                } catch (Exception ex) {
                    mensaje.setText("Error: " + ex.getMessage());
                    mensaje.getStyleClass().add("error");
                }
            }
        });

        HBox formulario = new HBox(5, campoNombre, campoCreditos, botonAgregar, botonActualizar, botonEliminar);
        formulario.setPadding(new Insets(10));
        VBox vbox = new VBox(tabla, formulario, mensaje);
        Tab tab = new Tab("Cursos", vbox);
        tab.setClosable(false);
        return tab;
    }
}