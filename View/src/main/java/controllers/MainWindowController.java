package controllers;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.LoaderException;


public class MainWindowController {

    @FXML
    public AnchorPane mainAnchorPane;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @FXML
    public void initialize() {
        try {
            loadScreen();
        } catch (LoaderException e) {
            logger.warn(ResourceBundle.getBundle("Language").getString("_Load_window_ex"));
        }
    }

    public void loadScreen() throws LoaderException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/StartWindow.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("Language");
        StartWindowController.bundle = bundle;
        loader.setResources(bundle);
        Pane pane;
        try {
            pane = loader.load();
        } catch (IOException e) {
            logger.warn(ResourceBundle.getBundle("Language").getString("_Load_window_ex"));
            throw new LoaderException(e);
        }
        StartWindowController startContr = loader.getController();
        startContr.setMainWindowController(this);

        setScreen(pane);
    }

    public void setScreen(Pane pane) {
        mainAnchorPane.getChildren().clear();
        mainAnchorPane.getChildren().add(pane);
    }

}
