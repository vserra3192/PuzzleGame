package game.puzzlegame;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
        initializeUI();
    }
    private void initializeUI() {
        Pane root = new Pane();

        for (int i = 0; i < gridNumber; i++) {
            GridPane grid = createGridPane(i);
            positionLabelandGrid(root, grid, i);
            root.getChildren().add(grid);
        }

        VBox sideBox = new VBox();
        sideBox.setPrefWidth(400);
        sideBox.setAlignment(Pos.TOP_LEFT);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(createTab("Clues", "cluesText"));
        tabPane.getTabs().add(createTab("Story", "storyText"));
        tabPane.getTabs().add(createTab("Notes", "getNotesText()"));
        tabPane.getTabs().add(createTab("Answer", ""));

        sideBox.getChildren().add(tabPane);


        Button clearErrorsButton = new Button("Clear Errors");
        clearErrorsButton.setOnAction(e -> clearErrors());

        Button hintButton = new Button("Hint");
        hintButton.setOnAction((e -> giveHint()));

        Button startOverButton = new Button("Start Over");
        hintButton.setOnAction((e -> startOver()));

        root.getChildren().add(sideBox);
        sideBox.setLayoutX(700);
        sideBox.setLayoutY(0);

        root.getChildren().add(startOverButton);
        startOverButton.setLayoutX(500);
        startOverButton.setLayoutY(650);

        root.getChildren().add(clearErrorsButton);
        clearErrorsButton.setLayoutX(335);
        clearErrorsButton.setLayoutY(650);

        root.getChildren().add(hintButton);
        hintButton.setLayoutX(200);
        hintButton.setLayoutY(650);




        scene = new Scene(root, 1000, 700);
    }

    private void startOver() {
    }

    private void giveHint() {
    }

    private void clearErrors() {

    }

    private Tab createTab(String title, String content){
        Tab tab = new Tab(title);
        TextArea textArea = new TextArea();
        textArea.setText(content);
        tab.setContent(textArea);
        tab.setClosable(false);
        return tab;
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
    private void positionLabelandGrid(Pane root, GridPane grid, int gridIndex) {
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

        double buttonSize = 50;
        double halfButtonSize = buttonSize / 2;
        String labelStyle = "-fx-border-color: black; -fx-border-width: 2; -fx-padding: 4 4 4 4; -fx-background-color: white; -fx-alignment: center;";

        for (int i = 0; i < 4; i++) {
            if (gridIndex == 0 || gridIndex == 2) {
                Label rowLabel = new Label("Row " + (i + 1));
                rowLabel.setLayoutX(grid.getLayoutX() - 50); // Adjust this if needed
                rowLabel.setLayoutY(grid.getLayoutY() + i * buttonSize + halfButtonSize - rowLabel.getHeight() / 2);
                rowLabel.setStyle(labelStyle);
                root.getChildren().add(rowLabel);
            }
            if (gridIndex == 0 || gridIndex == 1) {
                Label colLabel = new Label("Col " + (i + 1));
                colLabel.setLayoutX(grid.getLayoutX() + i * buttonSize + halfButtonSize - colLabel.getWidth() / 2);
                colLabel.setLayoutY(grid.getLayoutY() - 30); // Adjust this if needed
                colLabel.setStyle(labelStyle);
                colLabel.setRotate(90);
                root.getChildren().add(colLabel);
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