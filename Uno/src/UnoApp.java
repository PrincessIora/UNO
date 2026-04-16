
import Controller.GameController;
import Model.*;
import View.SetupView;
import View.UnoView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UnoApp extends Application {

  private GameController controller = new GameController();

  @Override
  public void start(Stage stage) {

    SetupView setup = new SetupView(players ->{
      GameState state = new GameState();
      state.initializeGame(players);

      controller.setState(state);

      UnoView gameView = new UnoView(controller, state);
      controller.setView(gameView);

      Scene gameScene = new Scene(gameView.getRoot(), 1000, 700);
      stage.setScene(gameScene);

      gameView.refreshAll();
    });

Scene setupScene = new Scene(setup.getRoot(), 500, 400);
    stage.setTitle("UNO");
    stage.setScene(setupScene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}