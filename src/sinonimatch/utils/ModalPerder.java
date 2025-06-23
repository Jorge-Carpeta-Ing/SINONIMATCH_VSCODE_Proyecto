package sinonimatch.utils;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.InputStream;

public class ModalPerder {
    private static Stage modalStage;

    public static void mostrar(Stage ownerStage, Runnable onAceptar, Runnable onCerrar) {
        Platform.runLater(() -> {
            modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);
            modalStage.setResizable(false);

            // Panel principal
            VBox root = new VBox(20);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(30));
            root.setStyle(
                    "-fx-background-color: rgba(42, 54, 79, 0.9);" +
                            "-fx-background-radius: 15;" +
                            "-fx-border-color: rgba(255, 99, 71, 0.7);" + // Rojo tomate
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 15;");

            // Icono de advertencia (opcional)
            try {
                InputStream imageStream = ModalPerder.class.getResourceAsStream("/sinonimatch/assets/warning.png");
                if (imageStream != null) {
                    Image warningImage = new Image(imageStream);
                    ImageView warningView = new ImageView(warningImage);
                    warningView.setFitWidth(80);
                    warningView.setFitHeight(80);
                    warningView.setPreserveRatio(true);
                    root.getChildren().add(warningView);
                }
            } catch (Exception e) {
                // Si no hay imagen, usamos un emoji
                Label warningSymbol = new Label("\u26A0");
                warningSymbol.setStyle("-fx-font-size: 50px; -fx-text-fill: #FF6347;");
                root.getChildren().add(warningSymbol);
            }

            // Título
            Label titulo = new Label("¡PERDISTE!");
            titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
            titulo.setTextFill(Color.web("#FF6347")); // Rojo tomate

            // Mensaje (con wrap para texto largo)
            Label mensaje = new Label("¡Se acabó el tiempo o elegiste mal!\n¿Quieres intentarlo de nuevo?");
            mensaje.setFont(Font.font("Arial", 16));
            mensaje.setTextFill(Color.WHITE);
            mensaje.setWrapText(true);
            mensaje.setMaxWidth(350); // Ancho máximo para el texto

            // Botones
            HBox botonesBox = new HBox(15);
            botonesBox.setAlignment(Pos.CENTER);

            Button btnReiniciar = new Button("Reiniciar juego");
            btnReiniciar.setStyle(
                    "-fx-background-color: #FF6347;" + // Rojo tomate
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14px;" +
                            "-fx-padding: 8 20;" +
                            "-fx-background-radius: 5;");
            btnReiniciar.setOnAction(_ -> {
                onAceptar.run();
                modalStage.close();
            });

            // Button btnSalir = new Button("Salir");
            // btnSalir.setStyle(
            // "-fx-background-color: #6E7F80;" + // Gris azulado
            // "-fx-text-fill: white;" +
            // "-fx-font-weight: bold;" +
            // "-fx-font-size: 14px;" +
            // "-fx-padding: 8 20;" +
            // "-fx-background-radius: 5;");
            // btnSalir.setOnAction(e -> {
            // onCerrar.run();
            // modalStage.close();
            // });

            // Efectos hover para los botones
            setHoverEffect(btnReiniciar, "#FF4500", "#FF6347"); // Naranja rojizo más oscuro al hover
            // setHoverEffect(btnSalir, "#5F6D6E", "#6E7F80"); // Gris más oscuro al hover

            botonesBox.getChildren().addAll(btnReiniciar);
            root.getChildren().addAll(titulo, mensaje, botonesBox);

            // Configurar escena
            Scene scene = new Scene(root, 400, 300);
            scene.setFill(Color.TRANSPARENT);
            modalStage.setScene(scene);
            modalStage.show();
        });
    }

    private static void setHoverEffect(Button button, String hoverColor, String normalColor) {
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: " + hoverColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 8 20;" +
                        "-fx-background-radius: 5;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.3), 10, 0.5, 0, 0);"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + normalColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 8 20;" +
                        "-fx-background-radius: 5;" +
                        "-fx-effect: null;"));
    }

    public static void cerrar() {
        if (modalStage != null) {
            Platform.runLater(() -> modalStage.close());
        }
    }
}