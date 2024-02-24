package game.puzzlegame;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class GridDisplayScene {
    private Scene scene;
    private int gridSize;
    private int gridNumber;
    private String difficultyLevel;

    public GridDisplayScene(int gridSize, int gridNumber, String difficultyLevel) {
        this.gridSize = gridSize;
        this.gridNumber = gridNumber;
        this.difficultyLevel = difficultyLevel;
        initializeGrids();
    }
    private void initializeGrids() {
        Pane pane = new Pane();

        for (int i = 0; i < gridNumber; i++) {
            GridPane grid = createGridPane(i);
            positionAndLabelGrid(pane, grid, i);
            pane.getChildren().add(grid);
        }

        scene = new Scene(pane, 800, 600);
    }
    private GridPane createGridPane(int gridIndex) {
        GridPane grid = new GridPane();

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                GameButton btn = new GameButton(row, col, grid);
                btn.setOnAction(e -> handleButtonAction(btn, gridIndex));
                grid.add(btn, col, row);
            }
        }
        return grid;
    }
    private void positionAndLabelGrid(Pane pane, GridPane grid, int gridIndex) {
        // Existing positioning logic
        switch (gridIndex) {
            case 0:
                grid.setLayoutX(150);
                grid.setLayoutY(150);
                break;
            case 1:
                grid.setLayoutX(351);
                grid.setLayoutY(150);
                break;
            case 2:
                grid.setLayoutX(150);
                grid.setLayoutY(351);
                break;
        }

        // Labeling logic
        for (int i = 0; i < 4; i++) {
            if (gridIndex == 0 || gridIndex == 2) {
                Label rowLabel = new Label("Row " + (i + 1));
                rowLabel.setLayoutX(grid.getLayoutX() - 50);
                rowLabel.setLayoutY(grid.getLayoutY() + i * 50 + 25);
                pane.getChildren().add(rowLabel);
            }
            if (gridIndex == 0 || gridIndex == 1) {
                Label colLabel = new Label("Col " + (i + 1));
                colLabel.setLayoutX(grid.getLayoutX() + i * 50 + 15);
                colLabel.setLayoutY(grid.getLayoutY() - 30);
                pane.getChildren().add(colLabel);
            }
        }
    }
    private void handleButtonAction(GameButton button, int gridIndex) {
        // Toggle button state
        if (button.getText().equals(" ")) {
            button.setText("X");
            button.setTextFill(Color.RED);
        } else if (button.getText().equals("X")) {
            button.setText("O");
            button.setTextFill(Color.GREEN);
            toggleRowColButtons(button.getGridPane(), button.getRow(), button.getCol(), true, button);
        } else {
            button.setText(" ");
            button.setTextFill(Color.BLACK);
            toggleRowColButtons(button.getGridPane(), button.getRow(), button.getCol(), false, button);
        }
    }
    private void toggleRowColButtons(GridPane grid, int row, int col, boolean disable, GameButton originButton) {
        for (Node node : grid.getChildren()) {
            if (node instanceof GameButton) {
                GameButton btn = (GameButton) node;
                if (btn == originButton) continue; // Skip the origin button

                int btnRow = GridPane.getRowIndex(btn);
                int btnCol = GridPane.getColumnIndex(btn);

                if (btnRow == row || btnCol == col) {
                    if (disable) {
                        if (!btn.getText().equals("O")) { // Don't disable if it's an "O"
                            btn.setText("X");
                            btn.setTextFill(Color.RED);
                            btn.setDisable(true);
                        }
                    } else {
                        // Only re-enable if not in line with another "O"
                        if (!isInLineWithAnotherO(grid, btnRow, btnCol, originButton)) {
                            btn.setText(" ");
                            btn.setDisable(false);
                        }
                    }
                }
            }
        }
    }
    private boolean isInLineWithAnotherO(GridPane grid, int row, int col, GameButton originButton) {
        for (Node node : grid.getChildren()) {
            if (node instanceof GameButton) {
                GameButton btn = (GameButton) node;
                int btnRow = GridPane.getRowIndex(btn);
                int btnCol = GridPane.getColumnIndex(btn);

                if ((btnRow == row || btnCol == col) && btn.getText().equals("O") && btn != originButton) {
                    return true;
                }
            }
        }
        return false;
    }

    public Scene getScene() {
        return scene;
    }
}