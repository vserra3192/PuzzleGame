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

    // previous Attributes
    /*
        getGameData();
        this.rows = rows;
        this.cols = cols;
        buttons = new GameButton[rows][cols];
        answerSheet = new String[rows][cols];
        startOverButton = new Button("Start Over");
        startOverButton.setOnAction(e -> onStartOver());
        clearErrorsButton = new Button("clear Errors");
        clearErrorsButton.setOnAction(e -> onClearErrors());
        initializeUI();
     */
    ArrayList<GridPane> gridPanes = new ArrayList<>();
    private Scene scene;
    private int gridSize;
    private int gridNumber;
    private String difficultyLevel;
    private ArrayList<String[]> answerSheets = new ArrayList<>();

    public GridDisplayScene(int gridSize, int gridNumber, String difficultyLevel) {
        this.gridSize = gridSize;
        this.gridNumber = gridNumber;
        this.difficultyLevel = difficultyLevel;
        initializeUI();
    }


    //getGameData method
    /*
        private void getGameData(){
        StringBuilder clueString = new StringBuilder("CLUES:\n");
        StringBuilder storyString = new StringBuilder("Story:\n");
        try {
            scan = new Scanner(file);
            String line = scan.nextLine();
            if (line.equals("clues")){
                while(!line.equals("story")){
                    line = scan.nextLine();
                    clueString.append(line).append("\n");
                }
                while (scan.hasNext()){
                    line = scan.nextLine();
                    storyString.append(line).append("\n");
                }
            }else{
                System.out.println("Not correct format");
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        cluesText = clueString.toString();
        storyText = storyString.toString();
    }
     */

    //getNotesText method
    /*
        private String getNotesText() {
        //write down notes
        StringBuilder nb = new StringBuilder();
        nb.append("Use this area to record notes that" +
                "\n may assist you in solving the puzzle. ");
        return  nb.toString();
    }
     */
    private void initializeUI() {

        //Previous version of initializeUI
        /*
            private void initializeUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15, 20, 15, 20));
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);

        cluesBox = new VBox();
        cluesBox.setPrefWidth(300); // Set preferred width
        cluesBox.setAlignment(Pos.TOP_CENTER);

        // Create tabs for each panel
        TabPane tabPane = new TabPane();
        Tab clueTab = new Tab("Clues");
        TextArea clueTextArea = new TextArea();
        clueTextArea.setText(cluesText);
        clueTab.setContent(clueTextArea);
        tabPane.getTabs().add(clueTab);
        clueTab.setClosable(false);

        Tab storyTab = new Tab("Story");
        TextArea storyTextArea = new TextArea();
        storyTextArea.setText(storyText);
        storyTab.setContent(storyTextArea);
        tabPane.getTabs().add(storyTab);
        storyTab.setClosable(false);

        Tab notesTab = new Tab("Notes ");
        TextArea notesTextArea = new TextArea();
        notesTextArea.setText(getNotesText());
        notesTab.setContent(notesTextArea);
        tabPane.getTabs().add(notesTab);
        notesTab.setClosable(false);

        Tab answerTab = new Tab("Answer");
        TextArea answerTextArea = new TextArea();
        answerTab.setContent(answerTextArea);
        tabPane.getTabs().add(answerTab);
        answerTab.setClosable(false);

        cluesBox.getChildren().add(tabPane);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                GameButton btn = new GameButton(row, col, this);
                grid.add(btn, col, row);
                buttons[row][col] = btn;
            }
        }
        HBox buttonsHolder = new HBox(startOverButton, clearErrorsButton);
        root.setBottom(buttonsHolder);
        root.setCenter(grid);
        root.setRight(cluesBox);
        scene = new Scene(root, 800, 400);
    }
         */
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
                rowLabel.setPrefSize(150, 50);
                rowLabel.setLayoutX(50); // Adjust this if needed
                rowLabel.setLayoutY(grid.getLayoutY() + i * buttonSize );
                rowLabel.setStyle(labelStyle);
                root.getChildren().add(rowLabel);
            }
            if (gridIndex == 0 || gridIndex == 1) {
                Label colLabel = new Label("Col " + (i + 1));
                colLabel.setPrefSize(200, 50);
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