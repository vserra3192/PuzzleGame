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

    public void showGridDisplayScene(String gridDimension, String difficultyLevel) {
        String[] parts = gridDimension.split("x");

        int gridNumber;
        int gridSize = Integer.parseInt(parts[1]);

        if ("3".equals(parts[0])) {
            gridNumber = 3;
        } else if ("4".equals(parts[0])) {
            gridNumber = 6;
        } else {
            System.out.println("Unexpected value for parts[0]: " + parts[0]);
            gridNumber = -1;
        }

        GridDisplayScene gridDisplayScene = new GridDisplayScene(gridSize,gridNumber,difficultyLevel, this);
        primaryStage.setScene(gridDisplayScene.getScene());
        primaryStage.setTitle("Logic Puzzle Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}