package game.puzzlegame;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class GridDisplayScene {
    private GameData gameData;
    private Scene scene;
    private int gridSize;
    private int gridNumber;
    private String difficultyLevel;
    private ArrayList<String[]> answerSheets = new ArrayList<>();

    ArrayList<GridPane> gridPanes = new ArrayList<>();

    public GridDisplayScene(int gridSize, int gridNumber, String difficultyLevel) {
        this.gameData = new GameData("src/GameData0001.txt");
        this.gridSize = gridSize;
        this.gridNumber = gridNumber;
        this.difficultyLevel = difficultyLevel;
        initializeUI();
    }

    private void initializeUI() {

        createAnswerSheets(gridNumber);


        Pane root = new Pane();

        for (int i = 0; i < gridNumber; i++) {
            GridPane grid = createGridPane(i);
            positionLabelandGrid(root, grid, i);
            gridPanes.add(grid);
            root.getChildren().add(grid);
        }

        VBox sideBox = new VBox();
        sideBox.setPrefWidth(400);
        sideBox.setAlignment(Pos.TOP_LEFT);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(createTab("Clues", String.join("\n", gameData.getClues())));
        tabPane.getTabs().add(createTab("Story", gameData.getStory()));
        tabPane.getTabs().add(createTab("Notes", " "));
        tabPane.getTabs().add(createTab("Answer", ""));

        sideBox.getChildren().add(tabPane);


        Button clearErrorsButton = new Button("Clear Errors");
        clearErrorsButton.setOnAction(e -> clearErrors());

        Button hintButton = new Button("Hint");
        hintButton.setOnAction((e -> giveHint()));

        Button startOverButton = new Button("Start Over");
        startOverButton.setOnAction((e -> startOver()));

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

    /**
     * Method is called when Start Over Button is clicked. Iterates through each GameButton in
     * each grid and clears the text and makes them clickable.
     * @Author Victor Serra
     */
    private void startOver() {
        for (GridPane grids: gridPanes){
            for (Node node : grids.getChildren()) {
                if (node instanceof GameButton) {
                    GameButton button = (GameButton) node;
                    button.setText(" ");
                    button.setDisable(false);
                }
            }
        }
    }

    private void giveHint() {
    }

    /**
     * Creates the number of answer sheets that is needed to compare user given answers to
     * the correct answers.
     * @param gridNumber
     * @Author Victor Serra
     */

    private void createAnswerSheets(int gridNumber){
        for (int numGrids = 0; numGrids < gridNumber; numGrids++) {
            answerSheets.add(new String[gridSize*gridSize]);
        }
        for (int gridNum = 0; gridNum < gridNumber; gridNum++) {
            for (int pos = 0; pos < gridSize*gridSize; pos++) {
                answerSheets.get(gridNum)[pos] = "X";
            }
        }
        fillAnswers(answerSheets);
    }
    /*
    0123
    4567
    8901
    2345
     */

    /**
     * This method will take the location of the correct answers from the Game.json file
     * and add it to the answerSheets given in the parameter.
     * @param answerSheets
     * @Author Victor Serra
     */
    private void fillAnswers(ArrayList<String[]> answerSheets){
        for (int gridNum = 0; gridNum < gridNumber; gridNum++) {
            answerSheets.get(gridNum)[0] = "O";
            answerSheets.get(gridNum)[5] = "O";
            answerSheets.get(gridNum)[10] = "O";
            answerSheets.get(gridNum)[15] = "O";
            for (int j = 0; j < gridSize; j++) {
            }
        }
    }
    /**
     * called when clear errors button is clicked. for each button in the grid,
     * it compares its text value to the answerSheet array
     * and clears the answers if they are incorrect.
     * @Author Victor Serra
     */
    private void clearErrors() {
        int gridNum = 0;
        for (GridPane grids: gridPanes){
            String[] answerSheet = answerSheets.get(gridNum); // Get answer sheet for current grid
            int pos = 0; // Position in the answer sheet

            for (Node node : grids.getChildren()) {
                if (node instanceof GameButton) {
                    GameButton button = (GameButton) node;
                    if (!button.getText().equals(answerSheet[pos])) {
                        button.setText(" ");
                        button.setDisable(false);
                    }
                    pos++; // Move to the next position in the answer sheet
                }
            }

            gridNum++; // Move to the next grid
        }
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
        String labelStyle = "-fx-border-color: black; -fx-border-width: 2; -fx-padding: 4 4 4 4; -fx-background-color: white; -fx-alignment: center;";


        String [] header1 = gameData.getHeader1();
        String [] header2 = gameData.getHeader2();
        String [] header3and4 = gameData.getHeader3and4();

        Label header1Label = new Label(header1[0]);
        Label header2Label = new Label(header2[0]);
        Label header3Label = new Label(header3and4[0]);
        Label header4Label = new Label(header3and4[0]);


        //dates
        header1Label.setLayoutX(200);
        header1Label.setLayoutY(0);
        header1Label.setStyle(labelStyle);
        header1Label.setPrefSize(200, 50);
        root.getChildren().add(header1Label);

        //Profession
        header3Label.setLayoutX(401);
        header3Label.setLayoutY(0);
        header3Label.setStyle(labelStyle);
        header3Label.setPrefSize(200, 50);
        root.getChildren().add(header3Label);

        //Ages
        header2Label.setLayoutX(-75);
        header2Label.setLayoutY(275);
        header2Label.setStyle(labelStyle);
        header2Label.setPrefSize(200, 50);
        header2Label.setRotate(90);
        root.getChildren().add(header2Label);

        //Profession
        header4Label.setLayoutX(-75);
        header4Label.setLayoutY(475);
        header4Label.setStyle(labelStyle);
        header4Label.setPrefSize(200, 50);
        header4Label.setRotate(90);
        root.getChildren().add(header4Label);


        switch (gridIndex) {
            case 0:
                grid.setLayoutX(200);
                grid.setLayoutY(200);
                break;
            case 1:
                grid.setLayoutX(401);
                grid.setLayoutY(200);
                break;
            case 2:
                grid.setLayoutX(200);
                grid.setLayoutY(401);
                break;
        }




        for (int i = 0; i < 4; i++) {
            if (gridIndex == 0 || gridIndex == 2) {
                Label rowLabel = new Label("Row " + (i + 1));
                rowLabel.setPrefSize(150, 50);

                rowLabel.setLayoutX(50);
                rowLabel.setLayoutY(grid.getLayoutY() + i * 50);

                rowLabel.setStyle(labelStyle);
                root.getChildren().add(rowLabel);
            }
            if (gridIndex == 0 || gridIndex == 1) {
                Label colLabel = new Label("Col " + (i + 1));
                colLabel.setPrefSize(150, 50);

                colLabel.setLayoutX(grid.getLayoutX() + (i * 50)-50);
                colLabel.setLayoutY(100);

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