package sinonimatch.models;

/**
 * Clase que representa una pregunta del juego con sus opciones y respuesta
 * correcta.
 */
public class Pregunta {
    private final String palabra;
    private final String[] opciones;
    private final int indiceCorrecto;

    /**
     * Constructor para crear una nueva pregunta.
     * 
     * @param palabra        La palabra principal de la pregunta
     * @param opciones       Array de opciones de respuesta
     * @param indiceCorrecto Índice de la respuesta correcta en el array
     */
    public Pregunta(String palabra, String[] opciones, int indiceCorrecto) {
        this.palabra = palabra;
        this.opciones = opciones;
        this.indiceCorrecto = indiceCorrecto;
    }

    // Getters
    public String getPalabra() {
        return palabra;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public int getIndiceCorrecto() {
        return indiceCorrecto;
    }

    /**
     * Verifica si una respuesta es correcta.
     * 
     * @param indiceRespuesta Índice de la respuesta a verificar
     * @return true si la respuesta es correcta, false en caso contrario
     */
    public boolean esCorrecta(int indiceRespuesta) {
        return indiceRespuesta == indiceCorrecto;
    }
}