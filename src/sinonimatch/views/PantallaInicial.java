package sinonimatch.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PantallaInicial {
    private Stage stage;
    private Scene scene;
    private VBox layout;

    public PantallaInicial(Stage primaryStage) {
        this.stage = primaryStage;
        crearUI();
    }

    private void crearUI() {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Configurar fondo (opcional)
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/assets/inicio-background.jpg"));
            BackgroundImage bgImage = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, false, true));
            layout.setBackground(new Background(bgImage));
        } catch (Exception e) {
            layout.setStyle("-fx-background-color: #2a364f;");
        }

        Button btnIniciar = new Button("Iniciar Juego");
        btnIniciar.setStyle(
                "-fx-font-size: 18px; -fx-padding: 10 30; -fx-background-color: #4a6fa5; -fx-text-fill: white;");
        btnIniciar.setOnAction(e -> iniciarJuego());

        layout.getChildren().add(btnIniciar);
        scene = new Scene(layout, 600, 500);
    }

    private void iniciarJuego() {
        GameView gameView = new GameView(stage);
        gameView.mostrar();
    }

    public void mostrar() {
        stage.setScene(scene);
        stage.setTitle("Sinonimatch - Inicio");
        stage.show();
    }

    public void volverAInicio() {
        mostrar();
    }
}
