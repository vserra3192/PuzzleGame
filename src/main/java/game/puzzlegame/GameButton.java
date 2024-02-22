package game.puzzlegame;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameButton extends Button {
    private int row;
    private int col;
    private GridDisplayScene gridDisplayScene; // This will manage the grid

    public GameButton(int row, int col, GridDisplayScene gridDisplayScene) {
        super(" ");
        this.row = row;
        this.col = col;
        this.gridDisplayScene = gridDisplayScene;
        setPrefSize(50, 50);
        setFont(Font.font("Arial", FontWeight.BOLD, 14));
        setOnAction(e -> gridDisplayScene.handleButtonAction(this));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}