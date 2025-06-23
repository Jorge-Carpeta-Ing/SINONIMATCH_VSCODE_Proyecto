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
                new Pregunta("Feliz", new String[] { "Triste", "Enojado", "Contento", "Serio" }, 2),
                new Pregunta("Rápido", new String[] { "Lento", "Ágil", "Pausado", "Tardío" }, 1),
                new Pregunta("Hermoso", new String[] { "Feo", "Bello", "Horrible", "Desagradable" }, 1),
                new Pregunta("Valiente", new String[] { "Cobarde", "Audaz", "Miedoso", "Tímido" }, 1),
                new Pregunta("Inteligente", new String[] { "Tonto", "Listo", "Necio", "Ignorante" }, 1),
                new Pregunta("Grande", new String[] { "Pequeño", "Enorme", "Diminuto", "Reducido" }, 1),
                new Pregunta("Fuerte", new String[] { "Débil", "Robusto", "Frágil", "Vulnerable" }, 1)));

        // Nivel 2 - Preguntas intermedias
        bancoPreguntas.put(2, Arrays.asList(
                new Pregunta("Sabio", new String[] { "Ignorante", "Erudito", "Tonto", "Absurdo" }, 1),
                new Pregunta("Generoso", new String[] { "Egoísta", "Dadivoso", "Avaro", "Tacaño" }, 1),
                new Pregunta("Pacífico", new String[] { "Violento", "Tranquilo", "Agresivo", "Belicoso" }, 1),
                new Pregunta("Honesto", new String[] { "Corrupto", "Íntegro", "Deshonesto", "Engañoso" }, 1),
                new Pregunta("Humilde", new String[] { "Arrogante", "Modesto", "Presumido", "Engreído" }, 1),
                new Pregunta("Optimista", new String[] { "Pesimista", "Esperanzado", "Negativo", "Desalentador" }, 1)));

        // Nivel 3 - Preguntas avanzadas
        bancoPreguntas.put(3, Arrays.asList(
                new Pregunta("Inquebrantable", new String[] { "Rígido", "Firme", "Flexible", "Débil" }, 1),
                new Pregunta("Efímero", new String[] { "Duradero", "Fugaz", "Permanente", "Eterno" }, 1),
                new Pregunta("Prudente", new String[] { "Imprudente", "Cauto", "Temerario", "Arriesgado" }, 1),
                new Pregunta("Meticuloso", new String[] { "Descuidado", "Detallista", "Negligente", "Informal" }, 1),
                new Pregunta("Resiliente", new String[] { "Frágil", "Resistente", "Débil", "Vulnerable" }, 1),
                new Pregunta("Sagaz", new String[] { "Ingenuo", "Astuto", "Crédulo", "Inocente" }, 1)));
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