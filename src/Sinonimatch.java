import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.*;

public class Sinonimatch extends Application {
    private Stage window;
    private VBox layout;
    private Label palabraLabel, nivelLabel, feedbackLabel, timerLabel;
    private List<Button> opcionesButtons = new ArrayList<>();
    private List<Pregunta> preguntasNivel;
    private int nivel = 1;
    private int preguntaActual = 0;
    private int puntaje = 0;
    private Timeline timer;
    private int tiempoRestante = 10;
    private AudioClip sonidoCorrecto, sonidoIncorrecto, sonidoVictoria;

    private final Map<Integer, List<Pregunta>> bancoPreguntas = new HashMap<>() {{
        put(1, Arrays.asList(
            new Pregunta("Feliz", new String[]{"Triste", "Enojado", "Contento", "Serio"}, 2),
            new Pregunta("Rápido", new String[]{"Lento", "Ligero", "Tonto", "Duro"}, 1),
            new Pregunta("Grande", new String[]{"Pequeño", "Amplio", "Claro", "Estrecho"}, 1),
            new Pregunta("Valiente", new String[]{"Cobarde", "Temeroso", "Audaz", "Débil"}, 2),
            new Pregunta("Fácil", new String[]{"Complicado", "Simple", "Confuso", "Inútil"}, 1),
            new Pregunta("Cansado", new String[]{"Dormido", "Fatigado", "Activo", "Despierto"}, 1),
            new Pregunta("Contento", new String[]{"Molesto", "Alegre", "Triste", "Rabioso"}, 1),
            new Pregunta("Tranquilo", new String[]{"Calmado", "Nervioso", "Agitado", "Irritado"}, 0)
        ));
        put(2, Arrays.asList(
            new Pregunta("Sabio", new String[]{"Ignorante", "Erudito", "Tonto", "Absurdo"}, 1),
            new Pregunta("Agradable", new String[]{"Desagradable", "Encantador", "Áspero", "Cruel"}, 1),
            new Pregunta("Elegante", new String[]{"Rústico", "Hermoso", "Distinguido", "Tosco"}, 2),
            new Pregunta("Sincero", new String[]{"Mentiroso", "Honesto", "Tramposo", "Hipócrita"}, 1),
            new Pregunta("Hermético", new String[]{"Cerrado", "Abierto", "Claro", "Directo"}, 0),
            new Pregunta("Amable", new String[]{"Rudo", "Cortés", "Arrogante", "Molesto"}, 1),
            new Pregunta("Generoso", new String[]{"Egoísta", "Dadivoso", "Tacaño", "Avaro"}, 1),
            new Pregunta("Firme", new String[]{"Débil", "Constante", "Blando", "Tierno"}, 1)
        ));
        put(3, Arrays.asList(
            new Pregunta("Inquebrantable", new String[]{"Rígido", "Firme", "Flexible", "Débil"}, 1),
            new Pregunta("Perspicaz", new String[]{"Ingenuo", "Astuto", "Torpe", "Ciego"}, 1),
            new Pregunta("Exánime", new String[]{"Muerto", "Vivo", "Energético", "Animado"}, 0),
            new Pregunta("Efímero", new String[]{"Eterno", "Corto", "Fugaz", "Duradero"}, 2),
            new Pregunta("Melancolía", new String[]{"Tristeza", "Felicidad", "Rabia", "Dicha"}, 0),
            new Pregunta("Tenaz", new String[]{"Perseverante", "Perezoso", "Flojo", "Débil"}, 0),
            new Pregunta("Sagaz", new String[]{"Tonto", "Lento", "Agudo", "Torpe"}, 2),
            new Pregunta("Abrupto", new String[]{"Suave", "Repentino", "Leve", "Constante"}, 1)
        ));
    }};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("🎮 Sinonimatch - Versión Final");
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        BackgroundImage fondo = new BackgroundImage(
            new Image(new File("assets/background.jpg").toURI().toString(), 600, 500, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT);
        layout.setBackground(new Background(fondo));

        ImageView avatar = new ImageView(new Image(new File("assets/avatar.jpg").toURI().toString()));
        avatar.setFitHeight(100);
        avatar.setPreserveRatio(true);

        nivelLabel = new Label("Nivel: 1");
        nivelLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        palabraLabel = new Label();
        palabraLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 18));
        feedbackLabel = new Label();
        timerLabel = new Label();
        timerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        for (int i = 0; i < 4; i++) {
            Button btn = new Button();
            btn.setMaxWidth(300);
            btn.setStyle("-fx-background-color: #ffb347; -fx-font-size: 14pt;");
            int finalI = i;
            btn.setOnAction(e -> comprobarRespuesta(finalI, btn));
            opcionesButtons.add(btn);
        }

        layout.getChildren().addAll(avatar, nivelLabel, palabraLabel, timerLabel);
        layout.getChildren().addAll(opcionesButtons);
        layout.getChildren().add(feedbackLabel);

        cargarSonidos();
        iniciarNivel();

        Scene scene = new Scene(layout, 600, 500);
        window.setScene(scene);
        window.show();
    }

    private void cargarSonidos() {
        sonidoCorrecto = new AudioClip(new File("assets/correcto.mp3").toURI().toString());
        sonidoIncorrecto = new AudioClip(new File("assets/incorrecto.mp3").toURI().toString());
        sonidoVictoria = new AudioClip(new File("assets/victoria.mp3").toURI().toString());
    }

    private void iniciarNivel() {
        preguntasNivel = new ArrayList<>(bancoPreguntas.get(nivel));
        Collections.shuffle(preguntasNivel);
        preguntasNivel = preguntasNivel.subList(0, Math.min(5, preguntasNivel.size()));
        preguntaActual = 0;
        nivelLabel.setText("Nivel: " + nivel);
        siguientePregunta();
    }

    private void siguientePregunta() {
        if (preguntaActual >= preguntasNivel.size()) {
            if (nivel == 3) {
                mostrarVictoria();
            } else {
                nivel++;
                iniciarNivel();
            }
            return;
        }
        Pregunta p = preguntasNivel.get(preguntaActual);
        palabraLabel.setText("¿Cuál es un sinónimo de: " + p.palabra + "?");
        for (int i = 0; i < 4; i++) {
            opcionesButtons.get(i).setText(p.opciones[i]);
        }
        feedbackLabel.setText("");
        iniciarTemporizador();
    }

    private void iniciarTemporizador() {
        if (timer != null) timer.stop();
        tiempoRestante = 10;
        timerLabel.setText("🕒 Tiempo restante: " + tiempoRestante + "s");

        timer = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                tiempoRestante--;
                timerLabel.setText("🕒 Tiempo restante: " + tiempoRestante + "s");
                if (tiempoRestante <= 0) {
                    timer.stop();
                    feedbackLabel.setText("⏰ ¡Tiempo agotado! Reiniciando nivel...");
                    sonidoIncorrecto.play();
                    preguntaActual = 0;
                    Collections.shuffle(preguntasNivel);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> siguientePregunta());
                        }
                    }, 1500);
                }
            })
        );
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void comprobarRespuesta(int seleccion, Button btn) {
        if (timer != null) timer.stop();
        Pregunta p = preguntasNivel.get(preguntaActual);
        if (seleccion == p.correcta) {
            animarBoton(btn);
            sonidoCorrecto.play();
            puntaje += 10;
            preguntaActual++;
            siguientePregunta();
        } else {
            feedbackLabel.setText("❌ Incorrecto. Reiniciando nivel...");
            sonidoIncorrecto.play();
            preguntaActual = 0;
            Collections.shuffle(preguntasNivel);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> siguientePregunta());
                }
            }, 1500);
        }
    }

    private void animarBoton(Button btn) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
        st.setByX(0.2);
        st.setByY(0.2);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }

    private void mostrarVictoria() {
        layout.getChildren().clear();
        sonidoVictoria.play();

        Label victoria = new Label("🎉 ¡Ganaste! Obtuviste " + puntaje + " puntos. ¡Felicidades!");
        victoria.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Button reiniciarBtn = new Button("🔁 Volver a jugar");
        reiniciarBtn.setStyle("-fx-font-size: 16px; -fx-background-color: #87cefa;");
        reiniciarBtn.setOnAction(e -> reiniciarJuego());

        layout.getChildren().addAll(victoria, reiniciarBtn);
    }

    private void reiniciarJuego() {
        nivel = 1;
        puntaje = 0;
        layout.getChildren().clear();
        start(window);
    }
}

class Pregunta {
    String palabra;
    String[] opciones;
    int correcta;

    public Pregunta(String palabra, String[] opciones, int correcta) {
        this.palabra = palabra;
        this.opciones = opciones;
        this.correcta = correcta;
    }
}
