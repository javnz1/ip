package saitama;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private SaitamaSensei saitama;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/legoat.jpg"));
    private Image saitamaImage = new Image(this.getClass().getResourceAsStream("/images/saitamasensei.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Saitama instance */
    public void setSaitama(SaitamaSensei s) {
        saitama = s;

        dialogContainer.getChildren().addAll(
                DialogBox.getSaitamaDialog(saitama.getGreeting(), saitamaImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing SaitamaSensei's reply and then appends
     * them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        // 1. Immediately show the User's dialog
        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(input, userImage)
        );
        userInput.clear();

        // 2. Calculate the response beforehand so Saitama is "thinking"
        String response = saitama.getResponse(input);

        // 3. Create a delay (e.g., 0.8 seconds) before Saitama replies
        PauseTransition delay = new PauseTransition(Duration.seconds(0.8));
        delay.setOnFinished(event -> {
            dialogContainer.getChildren().add(
                    DialogBox.getSaitamaDialog(response, saitamaImage)
            );

            // 4. Handle the "bye" logic inside the delay so it doesn't close before the reply
            if (input.trim().equalsIgnoreCase("bye")) {
                PauseTransition exitDelay = new PauseTransition(Duration.seconds(1.5));
                exitDelay.setOnFinished(e -> Platform.exit());
                exitDelay.play();
            }
        });

        delay.play();
    }
}
