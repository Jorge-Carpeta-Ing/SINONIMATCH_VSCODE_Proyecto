package sinonimatch.utils;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalPerder {
    public static void mostrar(Stage ownerStage, Runnable onAceptar, Runnable onCerrar) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(ownerStage);
        modalStage.setTitle("¡Perdiste!");

        // Bandera para controlar si se hizo clic en el botón
        final boolean[] botonPresionado = { false };

        Label mensaje = new Label("¡Has perdido! ¿Quieres intentarlo de nuevo?");
        mensaje.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Button btnReiniciar = new Button("Volver a Empezar");
        btnReiniciar.setStyle("-fx-font-size: 14px; -fx-background-color: #4a6fa5; -fx-text-fill: white;");
        btnReiniciar.setOnAction(e -> {
            botonPresionado[0] = true;
            onAceptar.run();
            modalStage.close();
        });

        VBox layout = new VBox(20, mensaje, btnReiniciar);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2a364f; -fx-padding: 20;");

        Scene scene = new Scene(layout, 300, 200);
        modalStage.setScene(scene);

        // Manejar el evento de cierre de la ventana
        modalStage.setOnHidden(e -> {
            if (!botonPresionado[0]) {
                onCerrar.run();
            }
        });

        modalStage.showAndWait();
    }
}