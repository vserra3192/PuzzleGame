package game.puzzlegame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class PuzzleController {
    @FXML
    private Button StartButton = new Button();
    @FXML
    private ComboBox<String> comboBox = new ComboBox<String>();
    private CreateBoard board;

    public void initialize(){
        this.comboBox.setPromptText("3x4");
        this.comboBox.getItems().addAll("3x4", "3x5", "4x4");
        this.comboBox.setValue("3x4");
    }
    public void onStartButton(ActionEvent event) {
        String size = comboBox.getValue();
        board = new CreateBoard(size);
        System.out.println(size);
        System.out.println(board.getNodeGrid().getColumnCount());
        System.out.println(board.getNodeGrid().getRowCount());
    }
}