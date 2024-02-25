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
    private Label hintLabel = new Label();

    private MainApp mainApp;
    private Button gameOverButton;
    private GameData gameData;
    private Scene scene;
    private int gridSize;
    private int gridNumber;
    private String difficultyLevel;
    private ArrayList<String[]> answerSheets = new ArrayList<>();

    ArrayList<GridPane> gridPanes = new ArrayList<>();

    public GridDisplayScene(int gridSize, int gridNumber, String difficultyLevel, MainApp mainApp) {
        this.mainApp = mainApp;
        this.gameData = new GameData("src/main/resources/game/puzzlegame/GameData0001.txt");
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


        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(createTab("Clues", String.join("\n", gameData.getClues()),false));
        tabPane.getTabs().add(createTab("Story", String.join("\n",gameData.getStory()), false));
        tabPane.getTabs().add(createTab("Notes", " ", true));

        sideBox.getChildren().add(tabPane);


        Button clearErrorsButton = new Button("Check Answers");
        clearErrorsButton.setOnAction(e -> clearErrors());

        Button hintButton = new Button("Hint");
        hintButton.setOnAction((e -> giveHint()));

        Button startOverButton = new Button("Start Over");
        startOverButton.setOnAction((e -> startOver()));

        gameOverButton = new Button("CORRECT!");
        gameOverButton.setOnAction((event -> onGameOver()));

        root.getChildren().add(hintLabel);
        hintLabel.setLayoutX(200);
        hintLabel.setLayoutY(620);

        root.getChildren().add(sideBox);
        sideBox.setLayoutX(625);
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

        root.getChildren().add(gameOverButton);
        gameOverButton.setPrefSize(150,150);
        gameOverButton.setLayoutX(740);
        gameOverButton.setLayoutY(500);
        gameOverButton.setVisible(false);

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
        if (hintLabel.getText().isEmpty()){
            hintLabel.setText(gameData.getHints());
        }else {
            hintLabel.setText("");
        }
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

    /**
     * This method will take the location of the correct answers from the GameData file
     * and add it to the answerSheets given in the parameter.
     * @param answerSheets
     * @Author Victor Serra
     */
    private void fillAnswers(ArrayList<String[]> answerSheets){
        for (int gridNum = 0; gridNum < gridNumber; gridNum++) {
            answerSheets.get(gridNum)[gameData.getGridAnswers().get(gridNum)[0]] = "O";
            answerSheets.get(gridNum)[gameData.getGridAnswers().get(gridNum)[1]] = "O";
            answerSheets.get(gridNum)[gameData.getGridAnswers().get(gridNum)[2]] = "O";
            answerSheets.get(gridNum)[gameData.getGridAnswers().get(gridNum)[3]] = "O";
        }
    }
    /**
     * called when clear errors button is clicked. for each button in the grid,
     * it compares its text value to the answerSheet array
     * and clears the answers if they are incorrect. Or, if all answers are correct,
     * it displays a button which restarts the game.
     * @Author Victor Serra
     */
    private void clearErrors() {
        int correctAnswers = 0;
        int gridNum = 0;
        for (GridPane grids: gridPanes){
            String[] answerSheet = answerSheets.get(gridNum);
            int pos = 0;
            for (Node node : grids.getChildren()) {
                if (node instanceof GameButton) {
                    GameButton button = (GameButton) node;
                    if (!button.getText().equals(answerSheet[pos])) {
                        button.setText(" ");
                        button.setDisable(false);
                    } else if(button.getText().equals(answerSheet[pos])) {
                        correctAnswers++;
                    }
                    pos++;
                }
            }
            gridNum++;
            if(correctAnswers == 48){
                gameOverButton.setVisible(true);
            }
        }
    }

    /**
     * This method is called when the gameOver button is pressed.
     * Restarts the game
     */
    private void onGameOver() {
        mainApp.showPuzzleCreatorScene();
        gameOverButton.setVisible(false);
    }

    private Tab createTab(String title, String content, boolean editable){
        Tab tab = new Tab(title);
        TextArea textArea = new TextArea();
        textArea.setEditable(editable);
        textArea.setPrefSize(350, 450);
        textArea.setText(content);
        tab.setContent(textArea);
        tab.setClosable(false);
        return tab;
    }
    private GridPane createGridPane(int gridIndex) {
        GridPane grid = new GridPane();

        String buttonStyle = "-fx-border-color: black; -fx-background-color: white; -fx-alignment: center;";

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                GameButton btn = new GameButton(row, col, grid);
                btn.setStyle(buttonStyle);
                btn.setOnAction(e -> handleButtonAction(btn, gridIndex));
                grid.add(btn, col, row);
            }
        }
        return grid;
    }
    private void positionLabelandGrid(Pane root, GridPane grid, int gridIndex) {
        String headerLabelStyle = "-fx-border-color: black; -fx-border-width: 2; -fx-padding: 4 4 4 4; -fx-background-color: white; -fx-alignment: center;";
        String labelStyle = "-fx-border-color: black; -fx-border-width: 2; -fx-padding: 4 4 4 4; -fx-background-color: white; -fx-alignment: center;";


        String [] header1 = gameData.getHeader1();
        String [] header2 = gameData.getHeader2();
        String [] header3and4 = gameData.getHeader3and4();

        addLabel(root, header1[0],200,0, headerLabelStyle, 200,50,0);
        addLabel(root, header3and4[0], 400, 0, headerLabelStyle, 200, 50, 0);
        addLabel(root, header2[0], -75, 275, headerLabelStyle, 200, 50, 270);
        addLabel(root, header3and4[0], -75, 475, headerLabelStyle, 200, 50, 270);


        // Depending on the gridIndex, we will set different labels
        switch (gridIndex) {
            case 0:
                // Grid One: Use header1 for columns and header2 for rows
                for (int i = 1; i < gameData.getHeader1().length; i++) {
                    // Column labels
                    addLabel(root,gameData.getHeader1()[i] ,(i*50)+100,100, labelStyle, 150,50,270);
                    // Row labels
                    addLabel(root,gameData.getHeader2()[i] ,50,(i*50)+150, labelStyle, 150,50,0);
                }
                break;
            case 1:
                // Grid Two: Use header3and4 for columns, no row labels
                for (int i = 1; i < gameData.getHeader3and4().length; i++) {
                    // Col labels
                    addLabel(root,gameData.getHeader3and4()[i] ,(i*50)+300,100, labelStyle, 150,50,270);
                }
                break;
            case 2:
                // Grid Three: Use header3and4 for rows, no column labels
                for (int i = 1; i < gameData.getHeader3and4().length; i++) {
                    // Row labels
                    addLabel(root,gameData.getHeader3and4()[i] ,50,(i*50)+350, labelStyle, 150,50,0);
                }
                break;
        }

        // Set grid layout based on index
        if (gridIndex == 0) {
            grid.setLayoutX(200);
            grid.setLayoutY(200);
        } else if (gridIndex == 1) {
            grid.setLayoutX(400);
            grid.setLayoutY(200);
        } else if (gridIndex == 2) {
            grid.setLayoutX(200);
            grid.setLayoutY(400);
        }

    }
    private void addLabel(Pane root, String text, double layoutX, double layoutY, String labelStyle, double prefWidth, double prefHeight, double rotate) {
        Label label = new Label(text);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setStyle(labelStyle);
        label.setPrefSize(prefWidth, prefHeight);
        label.setRotate(rotate);
        root.getChildren().add(label);
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