// package sinonimatch.utils;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.image.Image;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.stage.Modality;
// import javafx.stage.Stage;
// import javafx.stage.StageStyle;

// public class ModalGanar {
//     private static Stage modalStage;

//     public static void mostrar(Stage ownerStage, int puntajeFinal, Runnable onAceptar) {
//         modalStage = new Stage();
//         modalStage.initOwner(ownerStage);
//         modalStage.initModality(Modality.APPLICATION_MODAL);
//         modalStage.initStyle(StageStyle.TRANSPARENT);
//         modalStage.setResizable(false);

//         // Panel principal
//         VBox root = new VBox(20);
//         root.setAlignment(Pos.CENTER);
//         root.setPadding(new Insets(30));
//         root.setStyle(
//                 "-fx-background-color: rgba(42, 54, 79, 0.9);" +
//                         "-fx-background-radius: 15;" +
//                         "-fx-border-color: rgba(255, 215, 0, 0.7);" +
//                         "-fx-border-width: 2;" +
//                         "-fx-border-radius: 15;");

//         // Imagen de trofeo (opcional)
//         try {
//             Image trophyImage = new Image(ModalGanar.class.getResourceAsStream("assets/trophy.png"));
//             BackgroundImage bgImage = new BackgroundImage(
//                     trophyImage,
//                     BackgroundRepeat.NO_REPEAT,
//                     BackgroundRepeat.NO_REPEAT,
//                     BackgroundPosition.CENTER,
//                     new BackgroundSize(100, 100, false, false, false, false));
//             root.setBackground(new Background(bgImage));
//         } catch (Exception e) {
//             System.out.println("No se pudo cargar la imagen del trofeo");
//         }

//         // Título
//         Label titulo = new Label("¡VICTORIA!");
//         titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
//         titulo.setTextFill(Color.GOLD);

//         // Mensaje
//         Label mensaje = new Label("Has completado todos los niveles");
//         mensaje.setFont(Font.font("Arial", 18));
//         mensaje.setTextFill(Color.WHITE);

//         // Puntaje
//         Label puntajeLabel = new Label("Puntaje final: " + puntajeFinal);
//         puntajeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//         puntajeLabel.setTextFill(Color.web("#FFD700"));

//         // Botón
//         Button btnAceptar = new Button("¡Jugar de nuevo!");
//         btnAceptar.setStyle(
//                 "-fx-background-color: #4CAF50;" +
//                         "-fx-text-fill: white;" +
//                         "-fx-font-weight: bold;" +
//                         "-fx-font-size: 14px;" +
//                         "-fx-padding: 8 20;" +
//                         "-fx-background-radius: 5;");
//         btnAceptar.setOnAction(_ -> {
//             onAceptar.run();
//             modalStage.close();
//         });

//         // Efecto hover para el botón
//         btnAceptar.setOnMouseEntered(_ -> btnAceptar.setStyle(
//                 "-fx-background-color: #45a049;" +
//                         "-fx-text-fill: white;" +
//                         "-fx-font-weight: bold;" +
//                         "-fx-font-size: 14px;" +
//                         "-fx-padding: 8 20;" +
//                         "-fx-background-radius: 5;" +
//                         "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.3), 10, 0.5, 0, 0);"));
//         btnAceptar.setOnMouseExited(_ -> btnAceptar.setStyle(
//                 "-fx-background-color: #4CAF50;" +
//                         "-fx-text-fill: white;" +
//                         "-fx-font-weight: bold;" +
//                         "-fx-font-size: 14px;" +
//                         "-fx-padding: 8 20;" +
//                         "-fx-background-radius: 5;" +
//                         "-fx-effect: null;"));

//         root.getChildren().addAll(titulo, mensaje, puntajeLabel, btnAceptar);

//         // Configurar escena
//         Scene scene = new Scene(root, 400, 300);
//         scene.setFill(Color.TRANSPARENT);
//         modalStage.setScene(scene);
//         modalStage.showAndWait();
//     }

//     public static void cerrar() {
//         if (modalStage != null) {
//             modalStage.close();
//         }
//     }
// }

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

public class ModalGanar {
    private static Stage modalStage;

    public static void mostrar(Stage ownerStage, int puntajeFinal, Runnable onAceptar) {
        Platform.runLater(() -> {
            modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);
            modalStage.setResizable(false);

            VBox root = new VBox(20);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(30));
            root.setStyle(
                    "-fx-background-color: rgba(42, 54, 79, 0.9);" +
                            "-fx-background-radius: 15;" +
                            "-fx-border-color: rgba(255, 215, 0, 0.7);" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 15;");

            // Imagen de trofeo (versión mejorada)
            try {
                InputStream imageStream = ModalGanar.class.getResourceAsStream("/sinonimatch/assets/trophy.png");
                if (imageStream != null) {
                    Image trophyImage = new Image(imageStream);
                    ImageView trophyView = new ImageView(trophyImage);
                    trophyView.setFitWidth(100);
                    trophyView.setFitHeight(100);
                    trophyView.setPreserveRatio(true);
                    root.getChildren().add(trophyView);
                }
            } catch (Exception e) {
                System.out.println("No se pudo cargar la imagen del trofeo: " + e.getMessage());
                // Imagen de trofeo por defecto usando un símbolo Unicode
                Label trophySymbol = new Label("\uD83C\uDFC6");
                trophySymbol.setStyle("-fx-font-size: 50px; -fx-text-fill: gold;");
                root.getChildren().add(trophySymbol);
            }

            Label titulo = new Label("¡VICTORIA!");
            titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
            titulo.setTextFill(Color.GOLD);

            Label mensaje = new Label("Has completado todos los niveles");
            mensaje.setFont(Font.font("Arial", 18));
            mensaje.setTextFill(Color.WHITE);

            Label puntajeLabel = new Label("Puntaje final: " + puntajeFinal);
            puntajeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            puntajeLabel.setTextFill(Color.web("#FFD700"));

            Button btnAceptar = new Button("¡Jugar de nuevo!");
            btnAceptar.setStyle(
                    "-fx-background-color: #4CAF50;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14px;" +
                            "-fx-padding: 8 20;" +
                            "-fx-background-radius: 5;");
            btnAceptar.setOnAction(_ -> {
                onAceptar.run();
                modalStage.close();
            });

            root.getChildren().addAll(titulo, mensaje, puntajeLabel, btnAceptar);

            Scene scene = new Scene(root, 400, 300);
            scene.setFill(Color.TRANSPARENT);
            modalStage.setScene(scene);
            modalStage.show();
        });
    }

    public static void cerrar() {
        if (modalStage != null) {
            Platform.runLater(() -> modalStage.close());
        }
    }
}