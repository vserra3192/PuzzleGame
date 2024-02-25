
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class PuzzleCreatorScene {
    private Scene scene;
    private MainApp mainApp;
    private Button createPuzzleButton;
    private ChoiceBox<String> gridSizeChoiceBox;
    private ChoiceBox<String> difficultyChoiceBox;

    public PuzzleCreatorScene(MainApp mainApp) {
        this.mainApp = mainApp;
        this.gridSizeChoiceBox = new ChoiceBox<>();
        this.difficultyChoiceBox =  new ChoiceBox<>();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15, 20, 15, 20));
        VBox centerBox = setupCenterBox();
        root.setCenter(centerBox);
        this.scene = new Scene(root, 600, 400);
    }

    private VBox setupCenterBox() {
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(20, 0, 20, 0));

        Label titleLabel = new Label("Select Your Puzzle Parameters");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        Label descriptionLabel = new Label("Select your grid size and difficulty range below and a puzzle will be initialized to match your preferences.");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setFont(new Font("Arial", 14));

        gridSizeChoiceBox.getItems().addAll("3x4", "3x5", "4x4", "4x5", "4x6", "4x7");
        gridSizeChoiceBox.setValue("3x4");

        difficultyChoiceBox.getItems().addAll("Easy", "Moderate", "Challenging");
        difficultyChoiceBox.setValue("Easy");

        createPuzzleButton = new Button("Create Puzzle >>");
        createPuzzleButton.setFont(new Font("Arial", 16));
        createPuzzleButton.setTextFill(Color.WHITE);
        createPuzzleButton.setStyle("-fx-background-color: #0078D7; -fx-padding: 10;");
        createPuzzleButton.setMinWidth(200);
        createPuzzleButton.setOnAction(e -> createPuzzle());

        centerBox.getChildren().addAll(titleLabel, descriptionLabel, gridSizeChoiceBox, difficultyChoiceBox, createPuzzleButton);
        return centerBox;
    }

    private void createPuzzle() {
        if ("3x4".equals(gridSizeChoiceBox.getValue()) && "Easy".equals(difficultyChoiceBox.getValue())) {
            // If the selected grid size and difficulty are as required, switch to the GridDisplayScene
            mainApp.showGridDisplayScene(gridSizeChoiceBox.getValue(), difficultyChoiceBox.getValue());
        } else {
            // If the conditions are not met, show an alert to the user
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Selection Error");
            alert.setHeaderText("Incorrect Puzzle Parameters");
            alert.setContentText("Please select '3x4 Grid' for grid size and 'Easy' for difficulty level. More options will be available in future updates.");
            alert.showAndWait();
        }
    }

    public Scene getScene() {
        return scene;
    }

    // Getter for the selected grid size
    public String getSelectedGridSize() {
        return gridSizeChoiceBox.getValue();
    }

    // Getter for the selected difficulty level
    public String getSelectedDifficultyLevel() {
        return difficultyChoiceBox.getValue();
    }
}
