
package sinonimatch.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import sinonimatch.models.Pregunta;
import sinonimatch.models.Nivel;
import sinonimatch.utils.AudioManager;

/**
 * Controlador principal que maneja la lógica del juego.
 */
public class GameController {
    private final Nivel nivel;
    private final AudioManager audioManager;
    private int puntaje;
    private Timeline temporizador;
    private int tiempoRestante;

    /**
     * Constructor que inicializa el controlador del juego.
     */
    public GameController() {
        this.nivel = new Nivel();
        this.audioManager = new AudioManager();
        this.puntaje = 0;
        this.tiempoRestante = 10;
        nivel.iniciarNivel();
    }

    /**
     * Verifica una respuesta del jugador.
     * 
     * @param indiceRespuesta Índice de la respuesta seleccionada
     * @return true si la respuesta es correcta, false en caso contrario
     */
    public boolean verificarRespuesta(int indiceRespuesta) {
        Pregunta pregunta = nivel.getPreguntaActual();
        if (pregunta.esCorrecta(indiceRespuesta)) {
            puntaje += 10;
            audioManager.reproducirSonidoCorrecto();
            if (nivel.hayMasPreguntas()) {
                nivel.avanzarPregunta();
            }
            return true;
        } else {
            audioManager.reproducirSonidoIncorrecto();
            nivel.reiniciarPreguntas();
            return false;
        }
    }

    /**
     * Inicia el temporizador para la pregunta actual.
     * 
     * @param onTick   Acción a ejecutar en cada tick del temporizador
     * @param onFinish Acción a ejecutar cuando el tiempo se agota
     */
    public void iniciarTemporizador(Runnable onTick, Runnable onFinish) {
        if (temporizador != null) {
            temporizador.stop();
        }

        tiempoRestante = 10;
        temporizador = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    tiempoRestante--;
                    onTick.run();
                    if (tiempoRestante <= 0) {
                        onFinish.run();
                    }
                }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
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

    public void reiniciarNivel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reiniciarNivel'");
    }
}