
// package sinonimatch.controllers;

// import javafx.animation.KeyFrame;
// import javafx.animation.Timeline;
// import javafx.util.Duration;
// import sinonimatch.models.Pregunta;
// import sinonimatch.models.Nivel;
// import sinonimatch.utils.AudioManager;

// /**
//  * Controlador principal que maneja la lógica del juego.
//  */
// public class GameController {
//     private final Nivel nivel;
//     private final AudioManager audioManager;
//     private int puntaje;
//     private Timeline temporizador;
//     private int tiempoRestante;

//     /**
//      * Constructor que inicializa el controlador del juego.
//      */
//     public GameController() {
//         this.nivel = new Nivel();
//         this.audioManager = new AudioManager();
//         this.puntaje = 0;
//         this.tiempoRestante = 10;
//         nivel.iniciarNivel();
//     }

//     /**
//      * Verifica una respuesta del jugador.
//      * 
//      * @param indiceRespuesta Índice de la respuesta seleccionada
//      * @return true si la respuesta es correcta, false en caso contrario
//      */
//     public boolean verificarRespuesta(int indiceRespuesta) {
//         Pregunta pregunta = nivel.getPreguntaActual();
//         if (pregunta.esCorrecta(indiceRespuesta)) {
//             puntaje += 10;
//             audioManager.reproducirSonidoCorrecto();
//             if (nivel.hayMasPreguntas()) {
//                 nivel.avanzarPregunta();
//             }
//             return true;
//         } else {
//             audioManager.reproducirSonidoIncorrecto();
//             nivel.reiniciarPreguntas();
//             return false;
//         }
//     }

//     /**
//      * Inicia el temporizador para la pregunta actual.
//      * 
//      * @param onTick   Acción a ejecutar en cada tick del temporizador
//      * @param onFinish Acción a ejecutar cuando el tiempo se agota
//      */
//     public void iniciarTemporizador(Runnable onTick, Runnable onFinish) {
//         if (temporizador != null) {
//             temporizador.stop();
//         }

//         tiempoRestante = 10;
//         temporizador = new Timeline(
//                 new KeyFrame(Duration.seconds(1), e -> {
//                     tiempoRestante--;
//                     onTick.run();
//                     if (tiempoRestante <= 0) {
//                         onFinish.run();
//                     }
//                 }));
//         temporizador.setCycleCount(Timeline.INDEFINITE);
//         temporizador.play();
//     }

//     // Getters
//     public int getPuntaje() {
//         return puntaje;
//     }

//     public int getTiempoRestante() {
//         return tiempoRestante;
//     }

//     public Pregunta getPreguntaActual() {
//         return nivel.getPreguntaActual();
//     }

//     public int getNivelActual() {
//         return nivel.getNivelActual();
//     }

//     public boolean esUltimoNivel() {
//         return nivel.getNivelActual() == 3 && !nivel.hayMasPreguntas();
//     }

//     public void detenerTemporizador() {
//         if (temporizador != null) {
//             temporizador.stop();
//         }
//     }

//     public void reiniciarNivel() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'reiniciarNivel'");
//     }
// }

package sinonimatch.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import sinonimatch.models.Pregunta;
import sinonimatch.models.Nivel;
import sinonimatch.utils.AudioManager;

import java.util.Collections;
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
    private Runnable onTiempoAgotado;

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
            reiniciarNivel();
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
        this.onTiempoAgotado = onFinish;

        if (temporizador != null) {
            temporizador.stop();
        }

        tiempoRestante = 10;
        temporizador = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    tiempoRestante--;
                    onTick.run();
                    if (tiempoRestante <= 0) {
                        tiempoAgotado();
                    }
                }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
    }

    /**
     * Maneja la lógica cuando el tiempo se agota
     */
    private void tiempoAgotado() {
        detenerTemporizador();
        audioManager.reproducirSonidoIncorrecto();
        reiniciarNivel();

        // Ejecutar el callback después de un breve retraso
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

    /**
     * Reinicia el nivel actual
     */
    public void reiniciarNivel() {
        nivel.reiniciarPreguntas();
        puntaje = Math.max(0, puntaje - 5); // Penalización por reinicio
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

    public boolean hayMasPreguntasEnNivel() {
        return nivel.hayMasPreguntas();
    }

    public void avanzarPregunta() {
        nivel.avanzarPregunta();
    }

    public void reiniciarJuegoCompleto() {
        nivel.reiniciarJuego();
        puntaje = 0;
        tiempoRestante = 10;
    }
}