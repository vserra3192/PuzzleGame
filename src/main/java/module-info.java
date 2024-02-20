module game.puzzlegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens game.puzzlegame to javafx.fxml;
    exports game.puzzlegame;
}