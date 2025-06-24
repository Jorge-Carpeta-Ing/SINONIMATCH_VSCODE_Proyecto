package utils;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Clase utilitaria para gestionar animaciones en la interfaz.
 */
public class Animations {

    /**
     * Aplica una animación de escala a un nodo.
     * 
     * @param nodo El nodo a animar
     */
    public static void animarBoton(Node nodo) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), nodo);
        st.setByX(0.2);
        st.setByY(0.2);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }
}