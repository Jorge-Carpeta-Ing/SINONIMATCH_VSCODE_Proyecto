package views;

import javafx.animation.PauseTransition;
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
import javafx.util.Duration;
import controllers.GameController;
import utils.ModalGanar;
import utils.ModalPerder;
import models.Pregunta;

import java.io.File;
import java.io.InputStream;

/**
 * Vista principal del juego que muestra las preguntas y opciones.
 */
public class GameView {
    private final Stage stage;
    private final GameController controller;
    private final VBox layout;
    private final Label palabraLabel;
    private final Label nivelLabel;
    private final Label feedbackLabel;
    private final Label timerLabel;
    private final Button[] opcionesButtons;

    public GameView(Stage stage) {
        this.stage = stage;
        this.controller = new GameController();
        this.layout = new VBox(20);
        this.layout.setAlignment(Pos.CENTER);

        this.palabraLabel = new Label();
        this.nivelLabel = new Label();
        this.feedbackLabel = new Label();
        this.timerLabel = new Label();
        this.opcionesButtons = new Button[4];

        inicializarUI();
    }

    private void inicializarUI() {
        configurarLayoutPrincipal();
        StackPane contentPane = crearRecuadroTransparente();
        VBox contentBox = crearContenedorContenido();

        contentPane.getChildren().add(contentBox);
        layout.getChildren().add(contentPane);
        configurarEscena();
    }

    private void configurarLayoutPrincipal() {
        try {
            InputStream is = getClass().getResourceAsStream("/assets/background.jpg");
            if (is != null) {
                Image backgroundImage = new Image(is);
                BackgroundImage bgImage = new BackgroundImage(
                        backgroundImage,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
                layout.setBackground(new Background(bgImage));
            } else {
                File file = new File("assets/background.jpg");
                if (file.exists()) {
                    Image backgroundImage = new Image(file.toURI().toString());
                    BackgroundImage bgImage = new BackgroundImage(
                            backgroundImage,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
                    layout.setBackground(new Background(bgImage));
                } else {
                    layout.setStyle("-fx-background-color: #2a364f;");
                }
            }
        } catch (Exception e) {
            layout.setStyle("-fx-background-color: #2a364f;");
        }
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
    }

    private StackPane crearRecuadroTransparente() {
        StackPane contentPane = new StackPane();
        contentPane.setMaxSize(500, 400);
        contentPane.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.4);" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: rgba(0, 0, 0, 0.5);" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 15;");
        return contentPane;
    }

    private VBox crearContenedorContenido() {
        configurarEtiquetas();
        configurarBotonesOpciones();

        VBox contentBox = new VBox(15);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));
        contentBox.setMaxWidth(400);

        contentBox.getChildren().addAll(
                nivelLabel,
                palabraLabel,
                timerLabel,
                crearGridOpciones(),
                feedbackLabel);

        return contentBox;
    }

    private void configurarEscena() {
        Scene scene = new Scene(layout, 600, 500);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("🎮 Sinonimatch - Versión Final");
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    private void configurarEtiquetas() {
        palabraLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        palabraLabel.setTextFill(Color.WHITE);
        palabraLabel.setWrapText(true);

        nivelLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        nivelLabel.setTextFill(Color.WHITE);

        timerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        timerLabel.setTextFill(Color.GOLD);

        feedbackLabel.setFont(Font.font("Arial", 14));
        feedbackLabel.setTextFill(Color.web("#ffcc00"));
    }

    private void configurarBotonesOpciones() {
        for (int i = 0; i < 4; i++) {
            opcionesButtons[i] = new Button();
            opcionesButtons[i].setMinSize(200, 50);
            opcionesButtons[i].setFont(Font.font("Arial", FontWeight.BOLD, 14));
            opcionesButtons[i].setStyle(
                    "-fx-background-color: rgba(74, 111, 165, 0.7);" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-padding: 8 15;");

            final int index = i;
            opcionesButtons[i].setOnMouseEntered(_ -> opcionesButtons[index].setStyle(
                    "-fx-background-color: rgba(94, 131, 185, 0.9);" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-padding: 8 15;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.3), 10, 0.5, 0, 0);"));

            opcionesButtons[i].setOnMouseExited(_ -> opcionesButtons[index].setStyle(
                    "-fx-background-color: rgba(74, 111, 165, 0.7);" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-padding: 8 15;" +
                            "-fx-effect: null;"));

            opcionesButtons[i].setOnAction(_ -> manejarRespuesta(index));
        }
    }

    private GridPane crearGridOpciones() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20, 0, 0, 0));

        for (int i = 0; i < 4; i++) {
            grid.add(opcionesButtons[i], i % 2, i / 2);
        }

        return grid;
    }

    public void mostrar() {
        actualizarPregunta();
        stage.show();
    }

    private void actualizarPregunta() {
        controller.detenerTemporizador();

        Pregunta pregunta = controller.getPreguntaActual();
        palabraLabel.setText("¿Cuál es un sinónimo de: " + pregunta.getPalabra() + "?");
        nivelLabel.setText("Nivel: " + controller.getNivelActual());

        String[] opciones = pregunta.getOpciones();
        for (int i = 0; i < 4; i++) {
            opcionesButtons[i].setText(opciones[i]);
            opcionesButtons[i].setStyle(
                    "-fx-background-color: rgba(74, 111, 165, 0.7);" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-padding: 8 15;");
        }

        feedbackLabel.setText("");

        controller.iniciarTemporizador(
                () -> timerLabel.setText("🕒 Tiempo restante: " + controller.getTiempoRestante() + "s"),
                this::tiempoAgotado);
    }

    private void mostrarModalPerder() {
        // Mostrar modal siempre que se falle, independientemente de los intentos
        ModalPerder.mostrar(
                stage,
                controller.getIntentosRestantes(),
                controller.getPuntaje(),
                controller.getNivelActual(),
                () -> {
                    // Opción: Continuar con intentos restantes
                    if (controller.getIntentosRestantes() <= 0) {
                        controller.reiniciarNivel();
                    }
                    actualizarPregunta();
                },
                () -> {
                    // Opción: Reiniciar juego completamente
                    controller.reiniciarJuegoCompleto();
                    actualizarPregunta();
                });
    }

    private void tiempoAgotado() {
        feedbackLabel.setText("⏰ ¡Tiempo agotado!");
        controller.detenerTemporizador();
        mostrarModalPerder();
    }

    private void manejarRespuesta(int indice) {
        controller.detenerTemporizador();
        if (controller.verificarRespuesta(indice)) {
            opcionesButtons[indice].setStyle(
                    "-fx-background-color: #4CAF50;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-padding: 8 15;");
            feedbackLabel.setText("✅ ¡Correcto! +10 puntos");

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(_ -> {
                if (controller.avanzarPregunta()) {
                    actualizarPregunta();
                } else {
                    if (controller.esJuegoCompletado()) {
                        feedbackLabel.setText("🏆 ¡Felicidades! Has completado TODOS los niveles");
                        // Mostrar modal de victoria y reiniciar
                        PauseTransition finalPause = new PauseTransition(Duration.seconds(2));
                        finalPause.setOnFinished(_ -> {
                            ModalGanar.mostrar(stage, controller.getPuntaje(), () -> {
                                controller.reiniciarJuegoCompleto();
                                actualizarPregunta();
                            });
                        });
                        finalPause.play();
                    } else {
                        // No hay más preguntas pero no es el último nivel
                        feedbackLabel.setText("🎉 ¡Nivel completado! Preparando siguiente nivel...");
                        PauseTransition nivelPause = new PauseTransition(Duration.seconds(1.5));
                        nivelPause.setOnFinished(_ -> actualizarPregunta());
                        nivelPause.play();
                    }
                }
            });
            pause.play();
        } else {
            opcionesButtons[indice].setStyle(
                    "-fx-background-color: #F44336;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 5;" +
                            "-fx-padding: 8 15;");
            feedbackLabel.setText("❌ Incorrecto -5 puntos");
            mostrarModalPerder();
        }
    }

}