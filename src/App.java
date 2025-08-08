import controladores.ControladorCurso;
import controladores.ControladorEstudiante;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelos.Curso;
import modelos.Estudiante;
import vistas.VistaCurso;
import vistas.VistaEstudiante;
import vistas.VistaInscripcion;

public class App extends Application {

    private final ControladorEstudiante controladorEstudiante = new ControladorEstudiante();
    private final ControladorCurso controladorCurso = new ControladorCurso();
    private final ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();
    private final ObservableList<Curso> cursos = FXCollections.observableArrayList();
    private Stage escenario;

    @Override
    public void start(Stage escenarioPrincipal) {
        this.escenario = escenarioPrincipal;
        cargarDatos();
        escenario.setTitle("Sistema de Inscripción");
        mostrarMenu();
        escenario.show();
    }

    private void mostrarMenu() {
        Button botonEstudiantes = new Button("Estudiantes");
        botonEstudiantes.setOnAction(e -> mostrarEstudiantes());
        Button botonCursos = new Button("Cursos");
        botonCursos.setOnAction(e -> mostrarCursos());
        Button botonInscripciones = new Button("Inscripciones");
        botonInscripciones.setOnAction(e -> mostrarInscripciones());
        Button botonSalir = new Button("Salir");
        botonSalir.setOnAction(e -> escenario.close());

        VBox menu = new VBox(10, botonEstudiantes, botonCursos, botonInscripciones, botonSalir);
        menu.setPadding(new Insets(20));
        Scene escena = new Scene(menu, 800, 600);
        escena.getStylesheets().add(getClass().getResource("styles/main.css").toExternalForm());
        escenario.setScene(escena);
    }

    private HBox crearBarraNavegacion() {
        Button botonInicio = new Button("Inicio");
        botonInicio.setOnAction(e -> mostrarMenu());
        Button botonEstudiantes = new Button("Estudiantes");
        botonEstudiantes.setOnAction(e -> mostrarEstudiantes());
        Button botonCursos = new Button("Cursos");
        botonCursos.setOnAction(e -> mostrarCursos());
        Button botonInscripciones = new Button("Inscripciones");
        botonInscripciones.setOnAction(e -> mostrarInscripciones());
        Button botonSalir = new Button("Salir");
        botonSalir.setOnAction(e -> escenario.close());

        HBox barra = new HBox(5, botonInicio, botonEstudiantes, botonCursos, botonInscripciones, botonSalir);
        barra.setPadding(new Insets(10));
        return barra;
    }

    private void mostrarEstudiantes() {
        VistaEstudiante vista = new VistaEstudiante(controladorEstudiante, estudiantes, this::cargarDatos);
        BorderPane raiz = new BorderPane();
        raiz.setTop(crearBarraNavegacion());
        raiz.setCenter(vista.construir());
        Scene escena = new Scene(raiz, 800, 600);
        escena.getStylesheets().add(getClass().getResource("styles/main.css").toExternalForm());
        escenario.setScene(escena);
    }

    private void mostrarCursos() {
        VistaCurso vista = new VistaCurso(controladorCurso, cursos, this::cargarDatos);
        BorderPane raiz = new BorderPane();
        raiz.setTop(crearBarraNavegacion());
        raiz.setCenter(vista.construir());
        Scene escena = new Scene(raiz, 800, 600);
        escena.getStylesheets().add(getClass().getResource("styles/main.css").toExternalForm());
        escenario.setScene(escena);
    }

    private void mostrarInscripciones() {
        VistaInscripcion vista = new VistaInscripcion(controladorEstudiante, estudiantes, cursos, this::cargarDatos);
        BorderPane raiz = new BorderPane();
        raiz.setTop(crearBarraNavegacion());
        raiz.setCenter(vista.construir());
        Scene escena = new Scene(raiz, 800, 600);
        escena.getStylesheets().add(getClass().getResource("styles/main.css").toExternalForm());
        escenario.setScene(escena);
    }

    private void cargarDatos() {
        estudiantes.setAll(controladorEstudiante.buscarTodos());
        cursos.setAll(controladorCurso.buscarTodos());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

