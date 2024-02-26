package game.puzzlegame;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.GridPane;

import java.util.Objects;

/**
 * This GameButton Class is what populates each grid within the game. It extends
 * JavaFXs Button class and takes in 3 parameters. The row and column it will be
 * placed in and which grid it will be placed in. And default text as " ".
 * @Author Victor, Ramiz, Jin
 */

public class GameButton extends Button {
    private int row;
    private int col;
    private GridPane grid;


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

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameButton that = (GameButton) o;

        if (row != that.row) return false;
        if (col != that.col) return false;
        return Objects.equals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        result = 31 * result + (grid != null ? grid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GameButton{" +
                "row=" + row +
                ", col=" + col +
                ", grid=" + grid +
                '}';
    }
}
