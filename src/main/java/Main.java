import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import saitama.SaitamaSensei;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private SaitamaSensei saitama = new SaitamaSensei("./data/saitama.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setTitle("Saitama Sensei: The Hero for Fun");
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/saitamasensei.jpg")));

            fxmlLoader.<MainWindow>getController().setSaitama(saitama); // inject the Saitama instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


