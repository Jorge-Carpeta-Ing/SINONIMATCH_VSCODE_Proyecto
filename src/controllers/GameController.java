package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import models.Pregunta;
import models.Nivel;
import utils.AudioManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Controlador principal que maneja la lógica del juego.
 */
public class GameController {
    private final Nivel nivel;
    private final AudioManager audioManager;
    private int puntaje;
    private Timeline temporizador;
    private int tiempoRestante;
    private int intentosRestantes = 4; // Intentos restantes del jugador
    private Runnable onTiempoAgotado;

    public GameController() {
        this.nivel = new Nivel();
        this.audioManager = new AudioManager();
        this.puntaje = 0;
        this.tiempoRestante = 10;
        nivel.iniciarNivel();
    }

    /**
     * Verifica una respuesta del jugador.
     */
    public boolean verificarRespuesta(int indiceRespuesta) {
        Pregunta pregunta = nivel.getPreguntaActual();
        if (pregunta.esCorrecta(indiceRespuesta)) {
            puntaje += 10;
            audioManager.reproducirSonidoCorrecto();
            return true;
        } else {

            audioManager.reproducirSonidoIncorrecto();
            perderIntento();
            // reiniciarNivel();
            return false;
        }
    }

    /**
     * Avanza a la siguiente pregunta o al siguiente nivel si no hay más preguntas
     * 
     * @return true si avanzó (ya sea pregunta o nivel), false si no hay más
     *         preguntas ni niveles
     */
    public boolean avanzarPregunta() {
        if (nivel.hayMasPreguntas()) {
            nivel.avanzarPregunta();
            return true;
        } else {
            return avanzarNivel(); // Intenta avanzar de nivel si no hay más preguntas
        }
    }

    public void iniciarTemporizador(Runnable onTick, Runnable onFinish) {
        this.onTiempoAgotado = onFinish;

        if (temporizador != null) {
            temporizador.stop();
        }

        tiempoRestante = 10;
        temporizador = new Timeline(
                new KeyFrame(Duration.seconds(1), _ -> {
                    tiempoRestante--;
                    onTick.run();
                    if (tiempoRestante <= 0) {
                        tiempoAgotado();
                    }
                }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
    }

    private void tiempoAgotado() {
        detenerTemporizador();
        audioManager.reproducirSonidoIncorrecto();
        reiniciarNivel();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (onTiempoAgotado != null) {
                        onTiempoAgotado.run();
                    }
                });
            }
        }, 1500);
    }

    public void reiniciarNivel() {
        nivel.reiniciarPreguntas();
        puntaje = Math.max(0, puntaje - 5);
    }

    // Getters
    public int getPuntaje() {
        return puntaje;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public Pregunta getPreguntaActual() {
        return nivel.getPreguntaActual();
    }

    public int getNivelActual() {
        return nivel.getNivelActual();
    }

    public boolean esUltimoNivel() {
        return nivel.getNivelActual() == 3 && !nivel.hayMasPreguntas();
    }

    public void detenerTemporizador() {
        if (temporizador != null) {
            temporizador.stop();
        }
    }

    public boolean hayMasPreguntas() {
        return nivel.hayMasPreguntas();
    }

    public void reiniciarJuegoCompleto() {
        nivel.reiniciarJuego();
        puntaje = 0;
        tiempoRestante = 10;
    }

    /**
     * Verifica si el juego está completamente terminado (última pregunta del último
     * nivel)
     * 
     * @return true si el juego está completo, false si no
     */
    public boolean esJuegoCompletado() {
        return nivel.getNivelActual() == 3 && !nivel.hayMasPreguntas();
    }

    /**
     * Avanza al siguiente nivel si es posible
     * 
     * @return true si avanzó al siguiente nivel, false si no hay más niveles
     */
    public boolean avanzarNivel() {
        if (nivel.getNivelActual() < 3) { // Asumiendo que tienes 3 niveles
            nivel.siguienteNivel();
            return true;
        }
        return false;
    }

    // Nuevo getter para los intentos restantes
    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public void resetearIntentos() {
        intentosRestantes = 4; // Reinicia los intentos a 4
    }

    private void perderIntento() {
        intentosRestantes--; // Reducción del contador

        if (intentosRestantes <= 0) {
            reiniciarNivel(); // Si se acaban los intentos, reinicia el nivel
        }
    }
}