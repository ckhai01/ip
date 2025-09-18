package lebot.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lebot.LeBotGui;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final LeBotGui leBot = new LeBotGui();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setMinHeight(600);
            stage.setMinWidth(417);
            stage.setTitle("LeBot James");
            Image image = new Image(this.getClass().getResourceAsStream("/images/LeBron.png"));
            stage.getIcons().add(image);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLebot(leBot);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

