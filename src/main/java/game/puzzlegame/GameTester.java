package game.puzzlegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameTester extends Application {
    private static final int SIZE = 4; // Grid size
    private Button[][] buttons1 = new Button[SIZE][SIZE]; // 2D array for the first grid
    private Button[][] buttons2 = new Button[SIZE][SIZE]; // 2D array for the second grid
    private Button[][] buttons3 = new Button[SIZE][SIZE]; // 2D array for the third grid

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainLayout = new BorderPane();

        GridPane grid1 = createGrid(true, true, buttons1);
        GridPane grid2 = createGrid(true, false, buttons2);
        GridPane grid3 = createGrid(false, true, buttons3);

        mainLayout.setCenter(grid1);
        mainLayout.setRight(grid2);

        HBox bottomContainer = new HBox();
        bottomContainer.getChildren().add(grid3);
        bottomContainer.setAlignment(Pos.CENTER_LEFT);
        mainLayout.setBottom(bottomContainer);

        Scene scene = new Scene(mainLayout);
        primaryStage.setTitle("Custom Grids Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGrid(boolean includeColumnLabels, boolean includeRowLabels, Button[][] buttons) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        if (includeColumnLabels) {
            for (int i = 0; i < SIZE; i++) {
                Label columnHeader = new Label("Col " + (i + 1));
                columnHeader.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                grid.add(columnHeader, i + 1, 0);
            }
        }

        if (includeRowLabels) {
            for (int i = 0; i < SIZE; i++) {
                Label rowHeader = new Label("Row " + (i + 1));
                rowHeader.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                grid.add(rowHeader, 0, i + 1);
            }
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button btn = createButton(row, col, buttons);
                grid.add(btn, col + 1, row + 1);
            }
        }

        return grid;
    }

    private Button createButton(int row, int col, Button[][] buttons) {
        Button btn = new Button(" ");
        btn.setPrefSize(50, 50);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btn.setOnAction(e -> {
            if (" ".equals(btn.getText())) {
                btn.setText("O");
                btn.setTextFill(Color.GREEN);
                updateRowAndCol(row, col, buttons, true); // Set to "X"
            } else if ("O".equals(btn.getText())) {
                btn.setText(" ");
                btn.setTextFill(Color.BLACK);
                updateRowAndCol(row, col, buttons, false); // Clear "X"
            }
        });
        buttons[row][col] = btn;
        return btn;
    }

    private void updateRowAndCol(int row, int col, Button[][] buttons, boolean set) {
        for (int i = 0; i < SIZE; i++) {
            // Update or clear the entire row except the current button
            if (i != col) {
                buttons[row][i].setText(set ? "X" : " ");
                buttons[row][i].setTextFill(set ? Color.RED : Color.BLACK);
            }
            // Update or clear the entire column except the current button
            if (i != row) {
                buttons[i][col].setText(set ? "X" : " ");
                buttons[i][col].setTextFill(set ? Color.RED : Color.BLACK);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}