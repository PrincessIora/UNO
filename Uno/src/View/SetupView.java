package View;

import Model.Player;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SetupView {

    private VBox root;

    public SetupView(Consumer<List<Player>> onStartGame) {
        buildUI(onStartGame);
    }

    private void buildUI(Consumer<List<Player>> onStartGame) {

        root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("""
        -fx-background-color: radial-gradient(center 50% 50%, radius 80%, #14532d, #022c22);
    """);

        Label title = new Label("UNO: Setup");
        title.setStyle("""
        -fx-font-size: 28px;
        -fx-text-fill: gold;
    """);

        ComboBox<Integer> playerCount = new ComboBox<>();
        playerCount.getItems().addAll(2, 3, 4);
        playerCount.setValue(2);
        playerCount.setStyle("""
        -fx-background-radius: 10;
        -fx-padding: 5;
    """);

        VBox playerInputs = new VBox(10);
        playerInputs.setAlignment(Pos.CENTER);

        Button generate = new Button("Generate Players");
        Button start = new Button("Start Game");
        start.setDisable(true);

        List<TextField> nameFields = new ArrayList<>();
        List<CheckBox> aiBoxes = new ArrayList<>();

        generate.setOnAction(e -> {

            playerInputs.getChildren().clear();
            nameFields.clear();
            aiBoxes.clear();

            for (int i = 0; i < playerCount.getValue(); i++) {

                VBox card = new VBox(5);
                card.setAlignment(Pos.CENTER);

                TextField name = new TextField("Player " + (i + 1));
                CheckBox isAI = new CheckBox("AI Player");

                name.setMaxWidth(200);
                styleInput(name);

                nameFields.add(name);
                aiBoxes.add(isAI);

                card.getChildren().addAll(name, isAI);
                playerInputs.getChildren().add(card);
            }
            start.setDisable(false);
        });

        start.setOnAction(e -> {

            List<Player> players = new ArrayList<>();

            for (int i = 0; i < nameFields.size(); i++) {
                players.add(new Player(
                        nameFields.get(i).getText(),
                        aiBoxes.get(i).isSelected()
                ));
            }

            onStartGame.accept(players);
        });

        root.getChildren().addAll(title, playerCount, generate, playerInputs, start);
    }

    public Parent getRoot() {
        return root;
    }
    private void styleButton(Button b) {
        b.setStyle("""
        -fx-background-color: gold;
        -fx-text-fill: black;
        -fx-font-weight: bold;
        -fx-background-radius: 10;
        -fx-padding: 8 16 8 16;
    """);
    }

    private void styleInput(TextField t) {
        t.setStyle("""
        -fx-background-radius: 10;
        -fx-padding: 6;
    """);
    }
}