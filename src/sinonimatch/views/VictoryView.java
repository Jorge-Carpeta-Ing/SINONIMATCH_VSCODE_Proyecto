package sinonimatch.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Vista que se muestra cuando el jugador completa todos los niveles.
 */
public class VictoryView {
    private final Stage stage;
    private final int puntaje;

    /**
     * Constructor que inicializa la vista de victoria.
     * 
     * @param stage   Escenario principal
     * @param puntaje Puntaje final del jugador
     */
    public VictoryView(Stage stage, int puntaje) {
        this.stage = stage;
        this.puntaje = puntaje;
    }

    /**
     * Muestra la vista de victoria.
     */
    public void mostrar() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Label mensaje = new Label("🎉 ¡Ganaste! Obtuviste " + puntaje + " puntos. ¡Felicidades!");
        mensaje.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button reiniciarBtn = new Button("🔁 Volver a jugar");
        reiniciarBtn.setStyle("-fx-font-size: 16px;");
        reiniciarBtn.setOnAction(e -> reiniciarJuego());

        layout.getChildren().addAll(mensaje, reiniciarBtn);

        stage.setScene(new Scene(layout, 600, 500));
    }

    private void reiniciarJuego() {
        new GameView(stage).mostrar();
    }
}