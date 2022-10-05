package controllers;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.LoaderException;

public class Main extends Application {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void start(Stage primaryStage) throws LoaderException {

        Locale.setDefault(new Locale("en"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/MainWindow.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("Language");
        primaryStage.getIcons().add(new Image("/Styles/sudo.png"));
        loader.setResources(bundle);
        primaryStage.setTitle(bundle.getString("_Title"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = loader.load();
        } catch (IOException e) {
            logger.warn(ResourceBundle.getBundle("Language").getString("_Load_window_ex"));
            throw new LoaderException(e);
        }
        Scene scene = new Scene(anchorPane);
        scene.getStylesheets().add("/Styles/Style.css");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
