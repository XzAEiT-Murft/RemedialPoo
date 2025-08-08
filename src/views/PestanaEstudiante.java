package views;

import controllers.ControladorEstudiante;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Estudiante;

/**
 * Pestaña de interfaz para gestionar estudiantes.
 */
public class PestanaEstudiante {

    private final ControladorEstudiante controlador;
    private final ObservableList<Estudiante> estudiantes;
    private final Runnable actualizar;

public PestanaEstudiante(ControladorEstudiante controlador,
                             ObservableList<Estudiante> estudiantes,
                             Runnable actualizar) {
        this.controlador = controlador;
        this.estudiantes = estudiantes;
        this.actualizar = actualizar;
    }

    /**
     * Construye la pestaña JavaFX para manejar estudiantes.
     */
    public Tab construir() {
        TableView<Estudiante> tabla = new TableView<>(estudiantes);
        TableColumn<Estudiante, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        TableColumn<Estudiante, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        tabla.getColumns().addAll(colNombre, colCorreo);

        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Nombre");
        TextField campoCorreo = new TextField();
        campoCorreo.setPromptText("Correo");
        Button botonAgregar = new Button("Agregar");
        Button botonActualizar = new Button("Actualizar");
        Button botonEliminar = new Button("Eliminar");
        Label mensaje = new Label();
        mensaje.getStyleClass().add("message");

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, old, seleccionado) -> {
            if (seleccionado != null) {
                campoNombre.setText(seleccionado.getNombre());
                campoCorreo.setText(seleccionado.getCorreo());
            }
        });

        botonAgregar.setOnAction(e -> {
            if (campoNombre.getText().isBlank() || campoCorreo.getText().isBlank()) {
                mensaje.setText("Nombre y correo son obligatorios");
                mensaje.getStyleClass().add("error");
                return;
            }
            try {
                controlador.crearEstudiante(campoNombre.getText(), campoCorreo.getText());
                actualizar.run();
                mensaje.setText("Estudiante agregado");
                mensaje.getStyleClass().remove("error");
                campoNombre.clear();
                campoCorreo.clear();
            } catch (Exception ex) {
                mensaje.setText("Error: " + ex.getMessage());
                mensaje.getStyleClass().add("error");
            }
        });

        botonActualizar.setOnAction(e -> {
            Estudiante seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                try {
                    controlador.actualizarEstudiante(seleccionado.getId(),
                            campoNombre.getText(), campoCorreo.getText());
                    actualizar.run();
                    mensaje.setText("Estudiante actualizado");
                    mensaje.getStyleClass().remove("error");
                } catch (Exception ex) {
                    mensaje.setText("Error: " + ex.getMessage());
                    mensaje.getStyleClass().add("error");
                }
            }
        });

        botonEliminar.setOnAction(e -> {
            Estudiante seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                controlador.eliminarEstudiante(seleccionado.getId());
                actualizar.run();
                mensaje.setText("Estudiante eliminado");
                mensaje.getStyleClass().remove("error");
                campoNombre.clear();
                campoCorreo.clear();
            }
        });

        HBox formulario = new HBox(5, campoNombre, campoCorreo, botonAgregar, botonActualizar, botonEliminar);
        formulario.setPadding(new Insets(10));
        VBox vbox = new VBox(tabla, formulario, mensaje);
        Tab tab = new Tab("Estudiantes", vbox);
        tab.setClosable(false);
        return tab;
    }
}