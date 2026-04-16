package View;

import Controller.GameController;
import Model.*;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class UnoView {

    private final GameController controller;
    private GameState state;
    private ImageView discardImage;
    private ImageView drawPileImage;
    private Label turnLabel;


    private BorderPane root;

    Button unoButton = new Button("UNO!");

    private HBox opponentArea;
    private HBox playerHandArea;
    private VBox centerArea;

    public UnoView(GameController controller, GameState state) {
        this.controller = controller;
        this.state = state;

        buildUI();
    }

    private void buildUI() {
        root = new BorderPane();
        rootStack = new StackPane(root);

        buildOpponentArea();
        buildCenterArea();
        buildPlayerHandArea();
        buildBottomControls();

        root.setTop(opponentArea);
        root.setCenter(centerArea);
        root.setStyle("""
    -fx-background-color: radial-gradient(center 50% 50%, radius 80%, #14532d, #022c22);
""");
    }

    public Parent getRoot() {
        return rootStack;
    }

    private void buildOpponentArea() {
        opponentArea = new HBox(20);
        opponentArea.setAlignment(Pos.CENTER);

        Label opponent1 = new Label("Opponent 1 (7 cards)");
        Label opponent2 = new Label("Opponent 2 (7 cards)");

        opponentArea.getChildren().addAll(opponent1, opponent2);
    }

    private void buildCenterArea() {
        centerArea = new VBox(20);
        centerArea.setAlignment(Pos.CENTER);

        discardImage = new ImageView();
        discardImage.setFitWidth(100);
        discardImage.setFitHeight(150);

        drawPileImage = new ImageView(loadImage("/Resources/card_back.png"));
        drawPileImage.setFitWidth(100);
        drawPileImage.setFitHeight(150);

        drawPileImage.setOnMouseClicked(e -> {
            controller.drawCard();
            refreshAll();
        });

        turnLabel = new Label();
        turnLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        centerArea.getChildren().add(turnLabel);

        centerArea.getChildren().addAll(drawPileImage, discardImage);
    }

    private void buildPlayerHandArea() {
        playerHandArea = new HBox(10);
        playerHandArea.setAlignment(Pos.CENTER);
        playerHandArea.setStyle("""
    -fx-background-color: rgba(255,255,255,0.05);
    -fx-padding: 15;
    -fx-background-radius: 20;
""");
        refreshPlayerHand();
    }

    public void refreshPlayerHand() {

        playerHandArea.getChildren().clear();

        Player current = state.getCurrentPlayer();

        if (current == null) {
            playerHandArea.getChildren().add(new Label("No player loaded"));
            return;
        }

        for (Card card : current.getHand()) {

            Image img = loadImage(card.getImagePath());

            ImageView view = new ImageView(img);
            view.setFitWidth(60);
            view.setFitHeight(90);

            Button cardButton = new Button();
            if (state.getCurrentPlayer().isAI()) {
                cardButton.setDisable(true);
            }

            boolean playable = GameLogic.isValidMove(card, state.getTopCard(), state.getActiveColor());

            if (playable) {
                cardButton.setStyle("""
        -fx-effect: dropshadow(gaussian, gold, 15, 0.5, 0, 0);
    """);
            } else {
                cardButton.setOpacity(0.6);
            }
            cardButton.setGraphic(view);

            cardButton.setOnAction(e -> {
                controller.onCardClicked(card);
                refreshAll();
            });
            cardButton.setOnMouseEntered(e -> {
                cardButton.setTranslateY(-10);
            });

            cardButton.setOnMouseExited(e -> {
                cardButton.setTranslateY(0);
            });

            playerHandArea.getChildren().add(cardButton);
        }
    }

    private void buildBottomControls() {

        playerHandArea.setSpacing(10);
        playerHandArea.setAlignment(Pos.CENTER);

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> {
            rootStack.getChildren().remove(winOverlay);
            controller.restartGame(state.getPlayers());
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> controller.exitGame());

        controls.getChildren().addAll(unoButton, restartButton, exitButton);

        VBox bottom = new VBox(10, playerHandArea, controls);
        bottom.setAlignment(Pos.CENTER);

        unoButton.setOnAction(e -> {
            controller.callUno();
        });

        bottom.setStyle("""
        -fx-padding: 10;
        -fx-background-color: rgba(0,0,0,0.3);
    """);

        root.setBottom(bottom);
    }

    public void refreshAll() {
        refreshPlayerHand();
        refreshDiscardPile();
        refreshOpponents();
        turnLabel.setText("Current: " + state.getCurrentPlayer().getName());
    }

    private void refreshDiscardPile() {
        if (state.getTopCard() == null) return;
        ScaleTransition st = new ScaleTransition(Duration.millis(200), discardImage);
        st.setFromX(0.8);
        st.setFromY(0.8);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
        Image img = loadImage(state.getTopCard().getImagePath());
        discardImage.setImage(img);
    }

    private void refreshOpponents() {
        opponentArea.getChildren().clear();

        for (Player p : state.getPlayers()) {


            if (p == state.getCurrentPlayer()) continue;

            VBox box = new VBox(5);
            box.setAlignment(Pos.CENTER);
            if (p == state.getCurrentPlayer()) {
                box.setStyle("""
        -fx-border-color: gold;
        -fx-border-width: 3;
        -fx-background-color: rgba(255,215,0,0.1);
    """);
            }
            Label name = new Label(p.getName());

            HBox cards = new HBox(2);

            for (int i = 0; i < p.getHand().size(); i++) {
                ImageView back = new ImageView(loadImage("/Resources/card_back.png"));
                back.setFitWidth(30);
                back.setFitHeight(45);
                cards.getChildren().add(back);
            }

            box.getChildren().addAll(name, cards);
            opponentArea.getChildren().add(box);
        }
    }

    private Image loadImage(String path) {
        java.io.InputStream stream = getClass().getResourceAsStream(path);

        if (stream == null) {
            System.out.println("❌ Missing resource: " + path);
            stream = getClass().getResourceAsStream("/Resources/card_back.png");
        }

        return new Image(stream);
    }

    public void promptWildColor(Player player) {

        ChoiceDialog<Card.Color> dialog = new ChoiceDialog<>(
                Card.Color.RED,
                Card.Color.RED,
                Card.Color.BLUE,
                Card.Color.GREEN,
                Card.Color.YELLOW
        );

        dialog.setTitle("Wild Card");
        dialog.setHeaderText(player.getName() + " choose a color");

        dialog.showAndWait().ifPresent(color -> {
            state.setActiveColor(color);
            refreshAll();
        });
    }

    private StackPane rootStack;
    private VBox winOverlay;
    public void showWinScreen(Player winner) {

        winOverlay = new VBox(20);
        winOverlay.setAlignment(Pos.CENTER);

        winOverlay.setStyle("""
        -fx-background-color: rgba(0,0,0,0.7);
    """);

        Label title = new Label("👑 VICTORY 👑");
        title.setStyle("-fx-font-size: 40px; -fx-text-fill: gold;");

        Label name = new Label(winner.getName() + " Wins!");
        name.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        VBox scoreboard = buildScoreboard();

        Button restart = new Button("Play Again");
        restart.setOnAction(e -> {
            rootStack.getChildren().remove(winOverlay);
            controller.restartGame(state.getPlayers());
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> controller.exitGame());

        winOverlay.getChildren().addAll(title, name, scoreboard, restart);

        rootStack.getChildren().add(winOverlay);

        playCrownAnimation(title);
    }

    private VBox buildScoreboard() {

        VBox board = new VBox(10);
        board.setAlignment(Pos.CENTER);

        Label title = new Label("Scoreboard");
        title.setStyle("-fx-font-size: 20px; -fx-text-fill: gold;");

        board.getChildren().add(title);

        for (Player p : state.getPlayers()) {
            Label entry = new Label(p.getName() + " : " + p.getScore());
            entry.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            board.getChildren().add(entry);
        }

        return board;
    }

    private void playCrownAnimation(Label title) {

        javafx.animation.ScaleTransition scale = new javafx.animation.ScaleTransition(
                javafx.util.Duration.seconds(1),
                title
        );

        scale.setFromX(0.5);
        scale.setFromY(0.5);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);

        javafx.animation.RotateTransition rotate = new javafx.animation.RotateTransition(
                javafx.util.Duration.seconds(2),
                title
        );

        rotate.setByAngle(360);

        javafx.animation.ParallelTransition animation =
                new javafx.animation.ParallelTransition(scale, rotate);

        animation.play();
    }
    public void enableUnoButton(boolean enabled) {
        unoButton.setDisable(!enabled);
    }

    public void bindState(GameState state) {
        this.state = state;
    }
}