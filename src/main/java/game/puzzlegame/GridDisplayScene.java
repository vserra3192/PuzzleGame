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
        /*
                for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].setText(" ");
                buttons[i][j].setDisable(false);
            }
        }
         */
    }

    private void giveHint() {
    }

    private void clearErrors() {
        /*
            private void createAnswerSheet(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                answerSheet[i][j] = "X";
            }
        }
        answerSheet[0][0] = "O";
        answerSheet[1][1] = "O";
        answerSheet[2][2] = "O";
        answerSheet[3][3] = "O";
    }
    //for each button in the grid, it compares its value to the answer sheet "grid" (a 2d array with the answers)
    //and clears the answers if that are incorrect.
    private void onClearErrors() {
        createAnswerSheet();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(!buttons[i][j].getText().equals(answerSheet[i][j])){
                    buttons[i][j].setText(" ");
                    buttons[i][j].setDisable(false);
                }
            }
        }
    }
         */

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