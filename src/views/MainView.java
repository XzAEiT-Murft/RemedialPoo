package views;

import controllers.ControladorCurso;
import controllers.ControladorEstudiante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.Curso;
import models.Estudiante;

/**
 * Vista principal de la aplicación con botones para navegar entre pantallas.
 */
public class MainView {

    /**
     * Muestra la ventana principal.
     */
    public static void mostrar(Stage stage) {
        ControladorEstudiante ctrlEst = new ControladorEstudiante();
        ControladorCurso ctrlCurso = new ControladorCurso();

        ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();
        ObservableList<Curso> cursos = FXCollections.observableArrayList();
        try {
            estudiantes.addAll(ctrlEst.buscarTodos());
            cursos.addAll(ctrlCurso.buscarTodos());
        } catch (Exception e) {
            System.err.println("\u26a0\ufe0f No se pudieron cargar los datos iniciales: " + e.getMessage());
        }

        Runnable actualizar = () -> {
            try {
                estudiantes.setAll(ctrlEst.buscarTodos());
                cursos.setAll(ctrlCurso.buscarTodos());
            } catch (Exception e) {
                System.err.println("\u26a0\ufe0f No se pudo actualizar la información: " + e.getMessage());
            }
        };

        PestanaEstudiante pestEst =
                new PestanaEstudiante(ctrlEst, estudiantes, actualizar);
        PestanaCurso pestCur =
                new PestanaCurso(ctrlCurso, cursos, actualizar);
        PestanaInscripcion pestIns =
                new PestanaInscripcion(ctrlEst, estudiantes, cursos, actualizar);

        Tab tabEst = pestEst.construir();
        Tab tabCur = pestCur.construir();
        Tab tabIns = pestIns.construir();

        StackPane contenedor = new StackPane(tabEst.getContent());

        Button btnEst = new Button("Estudiantes");
        btnEst.setOnAction(e -> contenedor.getChildren().setAll(tabEst.getContent()));
        Button btnCur = new Button("Cursos");
        btnCur.setOnAction(e -> contenedor.getChildren().setAll(tabCur.getContent()));
        Button btnIns = new Button("Inscripciones");
        btnIns.setOnAction(e -> contenedor.getChildren().setAll(tabIns.getContent()));
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction(e -> stage.close());

        HBox menu = new HBox(10, btnEst, btnCur, btnIns, btnSalir);
        menu.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(menu);
        root.setCenter(contenedor);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(
                MainView.class.getResource("/styles/main.css").toExternalForm());
        stage.setTitle("Gestión de Escuela");
        stage.setScene(scene);
        stage.show();
    }
}