package vistas;

import controladores.ControladorEstudiante;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelos.Estudiante;

/**
 * Vista para gestionar estudiantes.
 */
public class VistaEstudiante {

    private final ControladorEstudiante controlador;
    private final ObservableList<Estudiante> estudiantes;
    private final Runnable actualizar;

    public VistaEstudiante(ControladorEstudiante controlador,
                             ObservableList<Estudiante> estudiantes,
                             Runnable actualizar) {
        this.controlador = controlador;
        this.estudiantes = estudiantes;
        this.actualizar = actualizar;
    }

    /**
     * Construye el contenedor JavaFX para manejar estudiantes.
     */
    public VBox construir() {
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
        Button botonEliminar = new Button("Eliminar");
        Label mensaje = new Label();
        mensaje.getStyleClass().add("message");

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

        botonEliminar.setOnAction(e -> {
            Estudiante seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                controlador.eliminarEstudiante(seleccionado.getId());
                actualizar.run();
            }
        });

        HBox formulario = new HBox(5, campoNombre, campoCorreo, botonAgregar, botonEliminar);
        formulario.setPadding(new Insets(10));
        return new VBox(tabla, formulario, mensaje);
    }
}

