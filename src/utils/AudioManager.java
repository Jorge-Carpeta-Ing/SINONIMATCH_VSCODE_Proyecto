package utils;

import javafx.scene.media.AudioClip;
import java.io.File;

/**
 * Clase utilitaria para gestionar los efectos de sonido del juego.
 */
public class AudioManager {
    private final AudioClip sonidoCorrecto;
    private final AudioClip sonidoIncorrecto;
    private final AudioClip sonidoVictoria;

    /**
     * Constructor que carga los archivos de sonido.
     */
    public AudioManager() {
        this.sonidoCorrecto = new AudioClip(new File("assets/correcto.mp3").toURI().toString());
        this.sonidoIncorrecto = new AudioClip(new File("assets/incorrecto.mp3").toURI().toString());
        this.sonidoVictoria = new AudioClip(new File("assets/victoria.mp3").toURI().toString());
    }

    /**
     * Reproduce el sonido de respuesta correcta.
     */
    public void reproducirSonidoCorrecto() {
        sonidoCorrecto.play();
    }

    /**
     * Reproduce el sonido de respuesta incorrecta.
     */
    public void reproducirSonidoIncorrecto() {
        sonidoIncorrecto.play();
    }

    /**
     * Reproduce el sonido de victoria.
     */
    public void reproducirSonidoVictoria() {
        sonidoVictoria.play();
    }
}
