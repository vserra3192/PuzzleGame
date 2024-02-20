package game.puzzlegame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class puzzleNode {
    private Rectangle puzzleNode;
    public puzzleNode(){
        puzzleNode = new Rectangle(10,10, Color.GRAY);
    }

    public Rectangle getPuzzleNode() {
        return puzzleNode;
    }
}
