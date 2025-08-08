package vistas;

import controladores.ControladorCurso;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelos.Curso;

/**
 * Vista para gestionar cursos.
 */
public class VistaCurso {

    private final ControladorCurso controlador;
    private final ObservableList<Curso> cursos;
    private final Runnable actualizar;

    public VistaCurso(ControladorCurso controlador,
                        ObservableList<Curso> cursos,
                        Runnable actualizar) {
        this.controlador = controlador;
        this.cursos = cursos;
        this.actualizar = actualizar;
    }

    /**
     * Construye el contenedor JavaFX para manejar cursos.
     */
    public VBox construir() {
        TableView<Curso> tabla = new TableView<>(cursos);
        TableColumn<Curso, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        TableColumn<Curso, Number> colCreditos = new TableColumn<>("Créditos");
        colCreditos.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getCreditos()));
        tabla.getColumns().addAll(colNombre, colCreditos);

        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Nombre");
        TextField campoCreditos = new TextField();
        campoCreditos.setPromptText("Créditos");
        Button botonAgregar = new Button("Agregar");
        Button botonEliminar = new Button("Eliminar");
        Label mensaje = new Label();
        mensaje.getStyleClass().add("message");

        botonAgregar.setOnAction(e -> {
            if (campoNombre.getText().isBlank() || campoCreditos.getText().isBlank()) {
                mensaje.setText("Nombre y créditos son obligatorios");
                mensaje.getStyleClass().add("error");
                return;
            }
            try {
                int creditos = Integer.parseInt(campoCreditos.getText());
                if (creditos <= 0) {
                    mensaje.setText("Créditos deben ser positivos");
                    mensaje.getStyleClass().add("error");
                    return;
                }
                controlador.crearCurso(campoNombre.getText(), creditos);
                actualizar.run();
                mensaje.setText("Curso agregado");
                mensaje.getStyleClass().remove("error");
                campoNombre.clear();
                campoCreditos.clear();
            } catch (NumberFormatException nfe) {
                mensaje.setText("Créditos inválidos");
                mensaje.getStyleClass().add("error");
            } catch (Exception ex) {
                mensaje.setText("Error: " + ex.getMessage());
                mensaje.getStyleClass().add("error");
            }
        });

        botonEliminar.setOnAction(e -> {
            Curso seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                controlador.eliminarCurso(seleccionado.getId());
                actualizar.run();
            }
        });

        HBox formulario = new HBox(5, campoNombre, campoCreditos, botonAgregar, botonEliminar);
        formulario.setPadding(new Insets(10));
        return new VBox(tabla, formulario, mensaje);
    }
}

