package sinonimatch.models;

import java.util.*;

/**
 * Clase que gestiona los niveles y preguntas del juego.
 */
public class Nivel {
    private final Map<Integer, List<Pregunta>> bancoPreguntas;
    private List<Pregunta> preguntasNivel;
    private int nivelActual;
    private int preguntaActual;

    /**
     * Constructor que inicializa el banco de preguntas.
     */
    public Nivel() {
        this.bancoPreguntas = new HashMap<>();
        inicializarBancoPreguntas();
        this.nivelActual = 1;
        this.preguntaActual = 0;
    }

    private void inicializarBancoPreguntas() {
        bancoPreguntas.put(1, Arrays.asList(
                new Pregunta("Feliz", new String[] { "Triste", "Enojado", "Contento", "Serio" }, 2)
        // ... otras preguntas del nivel 1
        ));

        bancoPreguntas.put(2, Arrays.asList(
                new Pregunta("Sabio", new String[] { "Ignorante", "Erudito", "Tonto", "Absurdo" }, 1)
        // ... otras preguntas del nivel 2
        ));

        bancoPreguntas.put(3, Arrays.asList(
                new Pregunta("Inquebrantable", new String[] { "Rígido", "Firme", "Flexible", "Débil" }, 1)
        // ... otras preguntas del nivel 3
        ));
    }

    /**
     * Inicia un nuevo nivel con preguntas aleatorias.
     */
    public void iniciarNivel() {
        List<Pregunta> todasPreguntas = new ArrayList<>(bancoPreguntas.get(nivelActual));
        Collections.shuffle(todasPreguntas);
        this.preguntasNivel = todasPreguntas.subList(0, Math.min(5, todasPreguntas.size()));
        this.preguntaActual = 0;
    }

    /**
     * Avanza al siguiente nivel.
     * 
     * @return true si hay más niveles, false si es el último nivel
     */
    public boolean siguienteNivel() {
        if (nivelActual < 3) {
            nivelActual++;
            iniciarNivel();
            return true;
        }
        return false;
    }

    // Getters
    public int getNivelActual() {
        return nivelActual;
    }

    public Pregunta getPreguntaActual() {
        return preguntasNivel.get(preguntaActual);
    }

    public boolean hayMasPreguntas() {
        return preguntaActual < preguntasNivel.size() - 1;
    }

    public void avanzarPregunta() {
        preguntaActual++;
    }

    public void reiniciarPreguntas() {
        Collections.shuffle(preguntasNivel);
        preguntaActual = 0;
    }
}