package com.flores;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import java.util.*;
import java.util.stream.Collectors;

public class App extends Application {

    private final Set<String> selectedFlowers = new LinkedHashSet<>(
            List.of("Rosas", "Girasoles", "Margaritas")
    );

    private final List<String> messages = new ArrayList<>(List.of(
            "TE QUIERO",
            "TE AMO",
            "ME ENCANTAS",
            "ERES ESPECIAL"
    ));

    private final Random random = new Random();
    private Pane garden;
    private Label messageLabel;
    private TextField customMessage;
    private ComboBox<String> messageCombo;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #090914, #171126);"
        );

        VBox top = createHeader();
        root.setTop(top);

        garden = new Pane();
        garden.setOnMouseMoved(e -> {
            spawnFlower(e.getX(), e.getY());
        });

        garden.setOnMouseClicked(e -> {
            spawnFlower(e.getX(), e.getY());
        });
        garden.setOnTouchPressed(e -> {
            spawnFlower(e.getTouchPoint().getX(), e.getTouchPoint().getY());
        });
        garden.setPrefSize(900, 600);
        garden.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #080812, #120d20);"
        );
        root.setCenter(garden);

        VBox controls = createControls();
        ScrollPane scroll = new ScrollPane(controls);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background-color: transparent;");
        root.setRight(scroll);

        Scene scene = new Scene(root, 1180, 720);
        stage.setTitle("Flores — Te Quiero");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();

        renderGarden();
    }
    private void spawnFlower(double x, double y) {

        if (selectedFlowers.isEmpty()) {
            return;
        }

        List<String> flowers = new ArrayList<>(selectedFlowers);

        String type = flowers.get(random.nextInt(flowers.size()));

        String emoji;

        switch (type) {
            case "Rosas":
                emoji = "🌹";
                break;

            case "Girasoles":
                emoji = "🌻";
                break;

            case "Margaritas":
                emoji = "🌼";
                break;

            default:
                emoji = "🌸";
        }

        Label flower = new Label(emoji);

        flower.setFont(Font.font(36 + random.nextInt(25)));

        flower.setLayoutX(x);
        flower.setLayoutY(y);

        garden.getChildren().add(flower);

        FadeTransition fade =
                new FadeTransition(Duration.millis(300), flower);

        fade.setFromValue(0);
        fade.setToValue(1);

        ScaleTransition scale =
                new ScaleTransition(Duration.millis(300), flower);

        scale.setFromX(0.2);
        scale.setFromY(0.2);
        scale.setToX(1);
        scale.setToY(1);

        ParallelTransition appear =
                new ParallelTransition(fade, scale);

        appear.play();

        PauseTransition pause =
                new PauseTransition(Duration.seconds(4));

        FadeTransition disappear =
                new FadeTransition(Duration.millis(800), flower);

        disappear.setToValue(0);

        disappear.setOnFinished(e ->
                garden.getChildren().remove(flower)
        );

        new SequentialTransition(
                pause,
                disappear
        ).play();
    }

    private VBox createHeader() {
        Label title = new Label("🌸 FLORES PARA TI 🌸");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#ff8fba"));

        Label subtitle = new Label("Elige las flores y el mensaje que quieres mostrar");
        subtitle.setFont(Font.font(15));
        subtitle.setTextFill(Color.web("#6f5361"));

        VBox box = new VBox(4, title, subtitle);
        box.setPadding(new Insets(18));
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox createControls() {
        VBox panel = new VBox(14);
        panel.setPadding(new Insets(18));
        panel.setPrefWidth(270);
        panel.setStyle("-fx-background-color: rgba(255,255,255,0.88);");

        Label flowersTitle = sectionTitle("FLORES");
        VBox flowers = new VBox(8);

        String[] names = {"Rosas", "Girasoles", "Margaritas", "Tulipanes"};
        for (String name : names) {
            CheckBox cb = new CheckBox(name);
            cb.setSelected(selectedFlowers.contains(name));
            cb.setFont(Font.font(14));
            cb.setOnAction(e -> {
                if (cb.isSelected()) selectedFlowers.add(name);
                else selectedFlowers.remove(name);
                renderGarden();
            });
            flowers.getChildren().add(cb);
        }

        Label msgTitle = sectionTitle("MENSAJE");
        messageCombo = new ComboBox<>();
        messageCombo.getItems().addAll(messages);
        messageCombo.setValue(messages.get(0));
        messageCombo.setMaxWidth(Double.MAX_VALUE);
        messageCombo.setOnAction(e -> {
            customMessage.clear();
            updateMessage(messageCombo.getValue());
        });

        customMessage = new TextField();
        customMessage.setPromptText("Escribe tu propio mensaje");
        customMessage.setOnAction(e -> {
            if (!customMessage.getText().isBlank()) {
                updateMessage(customMessage.getText().trim());
            }
        });

        Button apply = new Button("💗 Aplicar mensaje");
        apply.setMaxWidth(Double.MAX_VALUE);
        apply.setOnAction(e -> {
            String text = customMessage.getText().trim();
            updateMessage(text.isBlank() ? messageCombo.getValue() : text);
        });

        Button regenerate = new Button("🌷 Cambiar posiciones");
        regenerate.setMaxWidth(Double.MAX_VALUE);
        regenerate.setOnAction(e -> renderGarden());

        Label info = new Label(
                "Puedes marcar varias flores.\\n" +
                "El mensaje aparecerá sobre el jardín."
        );
        info.setWrapText(true);
        info.setTextFill(Color.web("#765d69"));

        panel.getChildren().addAll(
                flowersTitle, flowers,
                new Separator(),
                msgTitle, messageCombo, customMessage,
                apply, regenerate,
                new Separator(), info
        );
        return panel;
    }

    private Label sectionTitle(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 13));
        label.setTextFill(Color.web("#9b3566"));
        return label;
    }

    private void updateMessage(String text) {
        messageLabel.setText(text);
    }

    private void renderGarden() {
        garden.getChildren().clear();

        messageLabel = new Label(
                messageCombo == null ? "TE QUIERO" : messageCombo.getValue()
        );
        messageLabel.setFont(Font.font("System", FontWeight.BOLD, 34));
        messageLabel.setTextFill(Color.web("#b51f62"));
        messageLabel.setLayoutX(25);
        messageLabel.setLayoutY(25);
        garden.getChildren().add(messageLabel);

        if (selectedFlowers.isEmpty()) {
            Label empty = new Label("Selecciona al menos una flor 🌱");
            empty.setFont(Font.font(20));
            empty.setTextFill(Color.web("#78906e"));
            empty.layoutXProperty().bind(garden.widthProperty().subtract(empty.widthProperty()).divide(2));
            empty.layoutYProperty().bind(garden.heightProperty().subtract(30).divide(2));
            garden.getChildren().add(empty);
            return;
        }

        int count = Math.max(14, selectedFlowers.size() * 7);
        List<String> types = new ArrayList<>(selectedFlowers);

        for (int i = 0; i < count; i++) {
            String type = types.get(random.nextInt(types.size()));
            double x = 40 + random.nextDouble() * 800;
            double y = 130 + random.nextDouble() * 400;
            double scale = 0.65 + random.nextDouble() * 0.55;

            Node flower = createFlower(type, scale);
            flower.setLayoutX(x);
            flower.setLayoutY(y);
            flower.setRotate(random.nextDouble() * 20 - 10);

            FadeTransition fade = new FadeTransition(Duration.millis(900 + random.nextInt(1000)), flower);
            fade.setFromValue(0.25);
            fade.setToValue(1);
            fade.setAutoReverse(true);
            fade.setCycleCount(Animation.INDEFINITE);
            fade.setDelay(Duration.millis(random.nextInt(900)));
            fade.play();

            garden.getChildren().add(flower);
        }
    }

    private Node createFlower(String type, double scale) {
        Group g = new Group();
        Circle center;
        Color petal;
        int petals;

        switch (type) {
            case "Rosas" -> {
                petal = Color.web("#e85b7d");
                petals = 9;
                center = new Circle(0, 0, 11, Color.web("#b82752"));
            }
            case "Girasoles" -> {
                petal = Color.web("#ffd34e");
                petals = 14;
                center = new Circle(0, 0, 13, Color.web("#70421d"));
            }
            case "Margaritas" -> {
                petal = Color.WHITE;
                petals = 10;
                center = new Circle(0, 0, 9, Color.web("#f4c542"));
            }
            default -> {
                petal = Color.web("#c779e8");
                petals = 6;
                center = new Circle(0, 0, 10, Color.web("#8c3eb0"));
            }
        }

        for (int i = 0; i < petals; i++) {
            double angle = 360.0 * i / petals;
            Ellipse p = new Ellipse(0, -22, 10, 24);
            p.setFill(petal);
            p.setRotate(angle);
            g.getChildren().add(p);
        }

        g.getChildren().add(center);

        // tallo
        Line stem = new Line(0, 12, 0, 65);
        stem.setStroke(Color.web("#4d9b50"));
        stem.setStrokeWidth(4);
        stem.toBack();

        Ellipse leaf1 = new Ellipse(-10, 40, 14, 6);
        leaf1.setFill(Color.web("#62ad5e"));
        leaf1.setRotate(-25);

        Ellipse leaf2 = new Ellipse(10, 50, 14, 6);
        leaf2.setFill(Color.web("#62ad5e"));
        leaf2.setRotate(25);

        g.getChildren().addAll(stem, leaf1, leaf2);
        g.setScaleX(scale);
        g.setScaleY(scale);
        return g;
    }

    private void createFallingText() {

        String[] texts = {
                "TE AMO ❤️",
                "TE QUIERO 🌹",
                "ME ENCANTAS 💕",
                "ERES ESPECIAL 🌸",
                "MI AMOR ❤️",
                "PARA TI 🌻"
        };

        String text = texts[random.nextInt(texts.length)];

        Label label = new Label(text);

        label.setFont(
                Font.font(
                        "System",
                        FontWeight.BOLD,
                        20 + random.nextInt(15)
                )
        );

        label.setTextFill(Color.web("#ff8fba"));

        double x = random.nextDouble() *
                Math.max(1, garden.getWidth() - 150);

        label.setLayoutX(x);
        label.setLayoutY(-50);

        garden.getChildren().add(label);

        double distance = garden.getHeight() + 100;

        TranslateTransition fall =
                new TranslateTransition(
                        Duration.seconds(5 + random.nextDouble() * 4),
                        label
                );

        fall.setFromY(0);
        fall.setToY(distance);

        FadeTransition fade =
                new FadeTransition(
                        Duration.seconds(8),
                        label
                );

        fade.setFromValue(1);
        fade.setToValue(0);

        ParallelTransition animation =
                new ParallelTransition(fall, fade);

        animation.setOnFinished(e ->
                garden.getChildren().remove(label)
        );

        animation.play();
        Timeline fallingTexts = new Timeline(
                new KeyFrame(
                        Duration.millis(600),
                        e -> createFallingText()
                )
        );

        fallingTexts.setCycleCount(Timeline.INDEFINITE);
        fallingTexts.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
