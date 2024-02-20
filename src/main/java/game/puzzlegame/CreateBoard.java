package game.puzzlegame;

import javafx.scene.layout.GridPane;

public class CreateBoard {
    private GridPane nodeGrid;
    private int gridSize,numGrids;
    public CreateBoard(String boardSize){
        nodeGrid = new GridPane();
        numGrids = boardSize.charAt(0)-48;
        gridSize = boardSize.charAt(2)-48;
        for (int i = 0; i < gridSize; i++) {
            puzzleNode node = new puzzleNode();
            nodeGrid.addColumn(node.getPuzzleNode(), i);
            nodeGrid.addRow(node.getPuzzleNode(), i);
        }
    }

    public GridPane getNodeGrid() {
        return nodeGrid;
    }
}
