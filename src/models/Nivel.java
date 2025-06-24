package models;

import java.util.*;

/**
 * Clase que gestiona los niveles y preguntas del juego.
 */
public class Nivel {
    private final Map<Integer, List<Pregunta>> bancoPreguntas;
    private List<Pregunta> preguntasNivel;
    private int nivelActual;
    private int preguntaActual;
    private final int TOTAL_NIVELES = 3;
    private final int PREGUNTAS_POR_NIVEL = 5;

    /**
     * Constructor que inicializa el banco de preguntas.
     */
    public Nivel() {
        this.bancoPreguntas = new HashMap<>();
        this.nivelActual = 1;
        this.preguntaActual = 0;
        inicializarBancoPreguntas();
        iniciarNivel();
    }

    private void inicializarBancoPreguntas() {
        // Nivel 1 - Preguntas básicas
        bancoPreguntas.put(1, Arrays.asList(
                new Pregunta("Feliz", new String[] { "Contento", "Triste", "Serio", "Enojado" }, 0),
                new Pregunta("Rápido", new String[] { "Tardío", "Ágil", "Lento", "Pausado" }, 1),
                new Pregunta("Hermoso", new String[] { "Desagradable", "Horrible", "Bello", "Feo" }, 2),
                new Pregunta("Valiente", new String[] { "Tímido", "Cobarde", "Audaz", "Miedoso" }, 2),
                new Pregunta("Inteligente", new String[] { "Ignorante", "Necio", "Listo", "Tonto" }, 2),
                new Pregunta("Grande", new String[] { "Diminuto", "Reducido", "Enorme", "Pequeño" }, 2),
                new Pregunta("Fuerte", new String[] { "Vulnerable", "Robusto", "Frágil", "Débil" }, 1)));

        // Nivel 2 - Preguntas intermedias
        bancoPreguntas.put(2, Arrays.asList(
                new Pregunta("Sabio", new String[] { "Absurdo", "Erudito", "Ignorante", "Tonto" }, 1),
                new Pregunta("Generoso", new String[] { "Tacaño", "Avaro", "Dadivoso", "Egoísta" }, 2),
                new Pregunta("Pacífico", new String[] { "Belicoso", "Agresivo", "Tranquilo", "Violento" }, 2),
                new Pregunta("Honesto", new String[] { "Engañoso", "Íntegro", "Deshonesto", "Corrupto" }, 1),
                new Pregunta("Humilde", new String[] { "Engreído", "Modesto", "Presumido", "Arrogante" }, 1),
                new Pregunta("Optimista", new String[] { "Negativo", "Esperanzado", "Desalentador", "Pesimista" }, 1)));

        // Nivel 3 - Preguntas avanzadas
        bancoPreguntas.put(3, Arrays.asList(
                new Pregunta("Inquebrantable", new String[] { "Débil", "Firme", "Flexible", "Rígido" }, 1),
                new Pregunta("Efímero", new String[] { "Permanente", "Fugaz", "Eterno", "Duradero" }, 1),
                new Pregunta("Prudente", new String[] { "Arriesgado", "Temerario", "Cauto", "Imprudente" }, 2),
                new Pregunta("Meticuloso", new String[] { "Informal", "Detallista", "Negligente", "Descuidado" }, 1),
                new Pregunta("Resiliente", new String[] { "Vulnerable", "Resistente", "Débil", "Frágil" }, 1),
                new Pregunta("Sagaz", new String[] { "Inocente", "Astuto", "Crédulo", "Ingenuo" }, 1)));
    }

    /**
     * Inicia un nuevo nivel con preguntas aleatorias.
     */
    public void iniciarNivel() {
        List<Pregunta> todasPreguntas = new ArrayList<>(bancoPreguntas.get(nivelActual));
        Collections.shuffle(todasPreguntas);
        this.preguntasNivel = todasPreguntas.subList(0, Math.min(PREGUNTAS_POR_NIVEL, todasPreguntas.size()));
        this.preguntaActual = 0;
    }

    /**
     * Avanza al siguiente nivel.
     * 
     * @return true si hay más niveles, false si es el último nivel
     */
    public boolean siguienteNivel() {
        if (nivelActual < TOTAL_NIVELES) {
            nivelActual++;
            iniciarNivel();
            return true;
        }
        return false;
    }

    /**
     * Avanza a la siguiente pregunta en el nivel actual.
     */
    public void avanzarPregunta() {
        if (hayMasPreguntas()) {
            preguntaActual++;
        }
    }

    /**
     * Verifica si hay más preguntas en el nivel actual.
     */
    public boolean hayMasPreguntas() {
        return preguntaActual < preguntasNivel.size() - 1;
    }

    /**
     * Verifica si hay más niveles disponibles.
     */
    public boolean hayMasNiveles() {
        return nivelActual < TOTAL_NIVELES;
    }

    /**
     * Reinicia las preguntas del nivel actual mezclándolas nuevamente.
     */
    public void reiniciarPreguntas() {
        Collections.shuffle(preguntasNivel);
        preguntaActual = 0;
    }

    /**
     * Reinicia completamente el juego al primer nivel.
     */
    public void reiniciarJuego() {
        nivelActual = 1;
        iniciarNivel();
    }

    // Getters
    public int getNivelActual() {
        return nivelActual;
    }

    public Pregunta getPreguntaActual() {
        return preguntasNivel.get(preguntaActual);
    }

    public int getTotalPreguntasNivel() {
        return preguntasNivel.size();
    }

    public int getPreguntaActualIndice() {
        return preguntaActual;
    }
}