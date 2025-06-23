package sinonimatch.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;
import java.io.InputStream;

public class PantallaInicial {
    private final Stage stage;
    private final StackPane rootPane;

    public PantallaInicial(Stage primaryStage) {
        this.stage = primaryStage;
        this.rootPane = new StackPane();
        crearUI();
    }

    private void crearUI() {
        configurarFondo();
        StackPane contentPane = crearRecuadroTransparente();
        VBox contentBox = crearContenedorContenido();

        contentPane.getChildren().add(contentBox);
        rootPane.getChildren().add(contentPane);
    }

    private void configurarFondo() {
        try {
            // 1. Intenta cargar desde recursos del JAR
            InputStream is = getClass().getResourceAsStream("/assets/background.jpg");
            if (is != null) {
                Image backgroundImage = new Image(is);
                BackgroundImage bgImage = new BackgroundImage(
                        backgroundImage,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
                rootPane.setBackground(new Background(bgImage));
            } else {
                // 2. Intenta cargar desde sistema de archivos
                File file = new File("assets/background.jpg");
                if (file.exists()) {
                    Image backgroundImage = new Image(file.toURI().toString());
                    BackgroundImage bgImage = new BackgroundImage(
                            backgroundImage,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
                    rootPane.setBackground(new Background(bgImage));
                } else {
                    // 3. Fallback a color sólido con degradado
                    rootPane.setStyle("-fx-background-color: linear-gradient(to bottom, #2a364f, #1a2439);");
                    System.err.println("No se encontró background.jpg en ninguna ubicación");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar fondo: " + e.getMessage());
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom, #2a364f, #1a2439);");
        }
    }

    private StackPane crearRecuadroTransparente() {
        StackPane contentPane = new StackPane();
        contentPane.setMaxSize(400, 300);
        contentPane.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.4);" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 15;");
        return contentPane;
    }

    private VBox crearContenedorContenido() {
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));
        contentBox.setMaxWidth(350);

        Label titulo = new Label("SINONIMATCH");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titulo.setTextFill(Color.WHITE);
        titulo.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0.5, 0, 1);");

        Button btnIniciar = crearBotonInicio();
        contentBox.getChildren().addAll(titulo, btnIniciar);
        return contentBox;
    }

    private Button crearBotonInicio() {
        Button btnIniciar = new Button("INICIAR JUEGO");
        btnIniciar.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 40;" +
                        "-fx-background-color: rgba(74, 111, 165, 0.8);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 5;");

        // Efecto hover
        btnIniciar.setOnMouseEntered(e -> btnIniciar.setStyle(
                "-fx-background-color: rgba(94, 131, 185, 0.9);" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.3), 10, 0.5, 0, 0);" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 40;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 5;"));

        btnIniciar.setOnMouseExited(e -> btnIniciar.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 40;" +
                        "-fx-background-color: rgba(74, 111, 165, 0.8);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 5;" +
                        "-fx-effect: null;"));

        btnIniciar.setOnAction(e -> iniciarJuego());
        return btnIniciar;
    }

    private void iniciarJuego() {
        GameView gameView = new GameView(stage);
        gameView.mostrar();
    }

    public void mostrar() {
        Scene scene = new Scene(rootPane, 600, 500);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Sinonimatch - Inicio");
        stage.show();
    }
}