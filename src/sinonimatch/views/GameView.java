package sinonimatch.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sinonimatch.controllers.GameController;
// import main.java.sinonimatch.controllers.GameController;
// import main.java.sinonimatch.models.Pregunta;
// import main.java.sinonimatch.utils.Animations;
import sinonimatch.models.Pregunta;
import sinonimatch.utils.Animations;

/**
 * Vista principal del juego que muestra las preguntas y opciones.
 */
public class GameView {
    private final Stage stage;
    private final GameController controller;
    private final VBox layout;
    private Label palabraLabel, nivelLabel, feedbackLabel, timerLabel;

    /**
     * Constructor que inicializa la vista del juego.
     * 
     * @param stage Escenario principal de la aplicación
     */
    public GameView(Stage stage) {
        this.stage = stage;
        this.controller = new GameController();
        this.layout = new VBox(20);
        this.layout.setAlignment(Pos.CENTER);
        inicializarUI();
    }

    private void inicializarUI() {
        // Configuración del fondo y elementos UI
        // ... (similar a tu código original pero usando el controller)

        // Configurar escena
        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);
        stage.setTitle("🎮 Sinonimatch - Versión Final");
    }

    /**
     * Muestra la vista en el escenario.
     */
    public void mostrar() {
        actualizarPregunta();
        stage.show();
    }

    private void actualizarPregunta() {
        Pregunta pregunta = controller.getPreguntaActual();
        palabraLabel.setText("¿Cuál es un sinónimo de: " + pregunta.getPalabra() + "?");
        nivelLabel.setText("Nivel: " + controller.getNivelActual());

        controller.iniciarTemporizador(
                () -> timerLabel.setText("🕒 Tiempo restante: " + controller.getTiempoRestante() + "s"),
                this::tiempoAgotado);
    }

    private void tiempoAgotado() {
        feedbackLabel.setText("⏰ ¡Tiempo agotado! Reiniciando nivel...");
        controller.reiniciarNivel();
        actualizarPregunta();
    }

    private void manejarRespuesta(int indice, Button boton) {
        controller.detenerTemporizador();
        if (controller.verificarRespuesta(indice)) {
            Animations.animarBoton(boton);
            if (controller.esUltimoNivel()) {
                new VictoryView(stage, controller.getPuntaje()).mostrar();
            } else {
                actualizarPregunta();
            }
        } else {
            feedbackLabel.setText("❌ Incorrecto. Reiniciando nivel...");
            actualizarPregunta();
        }
    }
}