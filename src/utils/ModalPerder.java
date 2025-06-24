package utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalPerder {
        public static void mostrar(Stage ownerStage, int intentosRestantes, int puntaje,
                        int nivelActual, Runnable onContinuar, Runnable onReiniciar) {
                Stage modalStage = new Stage();
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.initOwner(ownerStage);
                modalStage.initStyle(StageStyle.TRANSPARENT);

                // Panel principal
                VBox root = new VBox(20);
                root.setAlignment(Pos.CENTER);
                root.setPadding(new Insets(30));
                root.setStyle(
                                "-fx-background-color: rgba(42, 54, 79, 0.95);" +
                                                "-fx-background-radius: 15;" +
                                                "-fx-border-color: rgba(255, 99, 71, 0.7);" +
                                                "-fx-border-width: 2;" +
                                                "-fx-border-radius: 15;");

                // Icono de advertencia
                Label icono = new Label("⚠️");
                icono.setStyle("-fx-font-size: 50px;");

                // Título dinámico según intentos
                Label titulo = new Label(
                                intentosRestantes > 0 ? "¡RESPUESTA INCORRECTA!" : "¡SE ACABARON LOS INTENTOS!");
                titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                titulo.setTextFill(Color.web("#FF6347"));

                // Mensaje informativo
                Label mensaje = new Label(
                                intentosRestantes > 0
                                                ? "Te quedan " + intentosRestantes + " intentos\nen el nivel "
                                                                + nivelActual
                                                : "Has agotado tus 3 intentos\nen el nivel " + nivelActual);
                mensaje.setTextFill(Color.WHITE);
                mensaje.setStyle("-fx-font-size: 16px; -fx-alignment: center;");

                // Puntaje
                Label puntajeLabel = new Label("Puntaje actual: " + puntaje);
                puntajeLabel.setTextFill(Color.WHITE);
                puntajeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                // Botones
                HBox botonesBox = new HBox(15);
                botonesBox.setAlignment(Pos.CENTER);

                Button btnContinuar = crearBoton(
                                intentosRestantes > 0 ? "Continuar" : "Reintentar Nivel",
                                intentosRestantes > 0 ? "#4a6fa5" : "#FF6347",
                                onContinuar,
                                modalStage);

                Button btnReiniciar = crearBoton("Nuevo Juego", "#6E7F80", onReiniciar, modalStage);

                botonesBox.getChildren().addAll(btnContinuar, btnReiniciar);
                root.getChildren().addAll(icono, titulo, mensaje, puntajeLabel, botonesBox);

                Scene scene = new Scene(root, 400, 300);
                scene.setFill(Color.TRANSPARENT);
                modalStage.setScene(scene);
                modalStage.showAndWait();
        }

        private static Button crearBoton(String texto, String color, Runnable accion, Stage modal) {
                Button btn = new Button(texto);
                btn.setStyle(
                                "-fx-background-color: " + color + ";" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-padding: 8 20;" +
                                                "-fx-background-radius: 5;");

                // Efecto hover
                btn.setOnMouseEntered(_ -> btn.setStyle(
                                "-fx-background-color: derive(" + color + ", -20%);" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-padding: 8 20;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.3), 10, 0.5, 0, 0);"));

                btn.setOnMouseExited(_ -> btn.setStyle(
                                "-fx-background-color: " + color + ";" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-padding: 8 20;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-effect: null;"));

                btn.setOnAction(_ -> {
                        accion.run();
                        modal.close();
                });

                return btn;
        }
}