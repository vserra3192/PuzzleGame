package game.puzzlegame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;


public class GridDisplayScene {
    private GameButton[][] buttons;
    private String[][] answerSheet;
    private Scene scene;
    private int rows;
    private int cols;
    private Button clearErrorsButton;
    private Button startOverButton;
    private VBox cluesBox;



    public GridDisplayScene(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        buttons = new GameButton[rows][cols];
        answerSheet = new String[rows][cols];
        startOverButton = new Button("Start Over");
        startOverButton.setOnAction(e -> onStartOver());
        clearErrorsButton = new Button("clear Errors");
        clearErrorsButton.setOnAction(e -> onClearErrors());
        initializeUI();
    }
    private void onStartOver() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].setText(" ");
                buttons[i][j].setDisable(false);
            }
        }
    }
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
        clueTextArea.setText(getCluesText());
        clueTab.setContent(clueTextArea);
        tabPane.getTabs().add(clueTab);
        clueTab.setClosable(false);

        Tab storyTab = new Tab("Story");
        TextArea storyTextArea = new TextArea();
        storyTextArea.setText(getStoryText());
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
        HBox buttonsHolder = new HBox(startOverButton,clearErrorsButton);
        root.setBottom(buttonsHolder);
        root.setCenter(grid);
        root.setRight(cluesBox);
        scene = new Scene(root, 800, 400);
    }



    private String getCluesText() {
        //where clues are located
        StringBuilder cb = new StringBuilder();
        cb.append("Active Clues\n");
        cb.append("1. The one with 250 genes " +
                "\nwas sequenced by Dr. Garza.\n");
        cb.append("2. B. mangeris was either the one " +
                "\nwith 750 genes or the bacteria sequenced by Dr. Ingram.\n");
        cb.append("3. E. carolinus has 250 fewer genes than " +
                "\nthe organism sequenced by Dr. Ortiz.\n");
        cb.append("4. L. dyson has 500 genes.\n");
        cb.append("5. The one sequenced by Dr. Acosta " +
                "\nhas 250 fewer genes than B. mangeris.\n");
        return cb.toString();
    }
    private String getStoryText() {
        //story location
        StringBuilder sb = new StringBuilder();
        sb.append("Backstory and Goal \n");
        return sb.toString();
    }
    private String getNotesText() {
        //write down notes
        StringBuilder nb = new StringBuilder();
        nb.append("Use this area to record notes that" +
                "\n may assist you in solving the puzzle. ");
        return  nb.toString();
    }
    //creates an answer sheet used to clear errors and check if answers given are correct. TEMPORARY
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

    public void handleButtonAction(GameButton button) {
        int row = button.getRow();
        int col = button.getCol();

        // Toggle button state and update appearance
        if (!button.getText().equals("O")) {
            // If not currently "O", it can be toggled freely
            if (button.getText().equals(" ")) {
                button.setText("X");
                button.setTextFill(Color.RED);
            } else if (button.getText().equals("X")) {
                button.setText("O");
                button.setTextFill(Color.GREEN);
                // Mark all in row/col as X and disable
                toggleRowColButtons(row, col, true, button);
            }
        } else {
            // If currently "O", toggling back to initial state and re-enable all in row/col if applicable
            button.setText(" ");
            toggleRowColButtons(row, col, false, button); // Re-enable and clear Xs if not affected by another O
        }
    }

    private void toggleRowColButtons(int row, int col, boolean disable, GameButton originButton) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                GameButton btn = buttons[i][j];
                if (btn == originButton) continue; // Skip the origin button

                if (i == row || j == col) {
                    if (disable) {
                        if (!btn.getText().equals("O")) { // Don't disable if it's an "O"
                            btn.setText("X");
                            btn.setTextFill(Color.RED);
                            btn.setDisable(true);
                        }
                    } else {
                        // Re-enable if not in line with another "O"
                        if (!isInLineWithAnotherO(i, j, originButton)) {
                            btn.setText(" ");
                            btn.setDisable(false);
                        }
                    }
                }
            }
        }
    }

    private boolean isInLineWithAnotherO(int row, int col, GameButton originButton) {
        // Check the row and column for another "O", excluding the origin button
        for (int i = 0; i < rows; i++) {
            if (buttons[row][i].getText().equals("O") && buttons[row][i] != originButton) return true;
        }
        for (int j = 0; j < cols; j++) {
            if (buttons[j][col].getText().equals("O") && buttons[j][col] != originButton) return true;
        }
        return false;
    }


    public Scene getScene() {
        return scene;
    }


}