
import javafx.application.Application;
import javafx.stage.Stage;
import views.PantallaInicial;

/**
 * Clase principal que inicia la aplicación Sinonimatch.
 * Extiende de Application para crear una aplicación JavaFX.
 */
public class Main extends Application {

    /**
     * Método principal que lanza la aplicación.
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Método start llamado cuando la aplicación está lista para iniciar.
     * 
     * @param primaryStage El escenario principal de la aplicación
     */
    @Override
    public void start(Stage primaryStage) {
        // GameView gameView = new GameView(primaryStage);
        // gameView.mostrar();
        PantallaInicial pantallaInicial = new PantallaInicial(primaryStage);
        pantallaInicial.mostrar();

    }
}