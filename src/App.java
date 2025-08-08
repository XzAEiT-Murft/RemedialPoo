import javafx.application.Application;
import javafx.stage.Stage;
import views.MainView;
import utils.JPAUtil;

/**
 * Punto de entrada de la aplicación JavaFX.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        MainView.mostrar(stage);
    }

    public static void main(String[] args) {
        launch(args);
        // Inicializa la fábrica de EntityManager al final de la ejecución
        JPAUtil.getEntityManagerFactory();
    }
}
