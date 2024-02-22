package game.puzzlegame;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showPuzzleCreatorScene();
        primaryStage.setResizable(false);
    }

    public void showPuzzleCreatorScene() {
        PuzzleCreatorScene puzzleCreatorScene = new PuzzleCreatorScene(this);
        primaryStage.setScene(puzzleCreatorScene.getScene());
        primaryStage.setTitle("Logic Puzzle Game");
        primaryStage.show();
    }

    public void showGridDisplayScene(String gridSize, String difficultyLevel) {
        GridDisplayScene gridDisplayScene = new GridDisplayScene(4,4 );
        primaryStage.setScene(gridDisplayScene.getScene());
        primaryStage.setTitle("Enjoy the game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}