package game.puzzlegame;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.GridPane;

public class GameButton extends Button {
    private int row;
    private int col;
    private GridPane grid;

    /**
     * This GameButton Class is what populates each grid within the game. It extends
     * JavaFXs Button class and takes in 3 parameters. The row and column it will be
     * placed in and which grid it will be placed in. And default text as " ".
     * @param row
     * @param col
     * @param grid
     * @Author Ramiz
     */

    public GameButton(int row, int col, GridPane grid) {
        super(" ");
        this.row = row;
        this.col = col;
        this.grid = grid;
        setPrefSize(50, 50);
        setFont(Font.font("Arial", FontWeight.BOLD, 14));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public GridPane getGridPane() {
        return grid;
    }
}
