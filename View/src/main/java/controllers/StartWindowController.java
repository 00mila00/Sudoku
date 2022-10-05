package controllers;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.board.DifficultyLevel;
import sudoku.board.SudokuBoard;
import sudoku.dao.Dao;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.LoaderException;

public class StartWindowController {
    @FXML
    public ToggleButton easyButton;
    public ToggleButton normalButton;
    public ToggleButton hardButton;
    public Button plButton;
    public Button enButton;
    public AnchorPane anchor;
    FileChooser fileChooser = new FileChooser();
    public static ResourceBundle bundle;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @FXML
    private MainWindowController mainWindowContr;
    @FXML
    private static DifficultyLevel difficulty = DifficultyLevel.Easy;


    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowContr = mainWindowController;
    }

    public void loadSudoku() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Sudoku.fxml"),bundle);
        AnchorPane anchorPane = loader.load();
        Stage stage = (Stage) anchor.getScene().getWindow();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        SudokuController sudokuContr = loader.getController();
        sudokuContr.setMainWindowController(mainWindowContr);
        stage.setTitle(bundle.getString("_Title"));
        logger.info(bundle.getString("_Start_game_info"));
        stage.show();
    }

    public static DifficultyLevel getLevel() {
    return difficulty;
    }

    public void setEasyLevel(ActionEvent actionEvent) {
        difficulty = DifficultyLevel.Easy;
        logger.info(easyButton.getText() + " " + bundle.getString("_Level_info"));
    }

    public void setNormalLevel(ActionEvent actionEvent) {
        difficulty = DifficultyLevel.Medium;
        logger.info(normalButton.getText() + " " + bundle.getString("_Level_info"));
    }

    public void setHardLevel(ActionEvent actionEvent) {
        difficulty = DifficultyLevel.Hard;
        logger.info(hardButton.getText() + " " + bundle.getString("_Level_info"));
    }

    public void setPolish(ActionEvent actionEvent) {
        Locale locale = new Locale("pl");
        try {
            reloadWindow(locale);
            logger.info(bundle.getString("_Change_lan_info") + locale.getLanguage());
        } catch (LoaderException e) {
            logger.warn(bundle.getString("_Reload_ex"));
        }
    }

    public void setEnglish(ActionEvent actionEvent) {
        Locale locale = new Locale("en");
        try {
            reloadWindow(locale);
            logger.info(bundle.getString("_Change_lan_info") + locale.getLanguage());
        } catch (LoaderException e) {
            logger.warn(bundle.getString("_Reload_ex"));
        }
    }

    public void setRussian(ActionEvent actionEvent) {
        Locale locale = new Locale("ru");
        try {
            reloadWindow(locale);
            logger.info(bundle.getString("_Change_lan_info") + locale.getLanguage());
        } catch (LoaderException e) {
            logger.warn(bundle.getString("_Reload_ex"));
        }
    }

    public void reloadWindow(Locale locale) throws LoaderException {
        bundle = ResourceBundle.getBundle("Language",locale);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/StartWindow.fxml"),bundle);
        AnchorPane anchorPane;
        try {
            anchorPane = loader.load();
        } catch (IOException e) {
            logger.warn(bundle.getString("_Load_window_ex"));
            throw new LoaderException(e);
        }
        Stage stage = (Stage) anchor.getScene().getWindow();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        StartWindowController startContr = loader.getController();
        startContr.setMainWindowController(mainWindowContr);
        stage.setTitle(bundle.getString("_Title"));
        stage.show();
    }

    public void loadAuthors(ActionEvent actionEvent) {
        ResourceBundle listBundle = ResourceBundle
                .getBundle("controllers.listresource.Authors",bundle.getLocale());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                .add(new Image("/Styles/sudo.png"));
        alert.setHeaderText((String) listBundle.getObject("Title"));
        alert.setTitle((String) listBundle.getObject("Title"));
        alert.setContentText((listBundle.getObject("Autor1")
                + "\n" + listBundle.getObject("Autor2")));
        alert.showAndWait();
    }

    public void load(ActionEvent actionEvent) throws LoaderException {
        fileChooser.setTitle(bundle.getString("_Choose_file"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(null);
        SudokuBoard temp = null;
        SudokuBoard deleted = null;
        SudokuBoard sb = null;
        if (file == null) {
            return;
        }
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory
                .getFileDao(file.getAbsolutePath());
        Dao<SudokuBoard> daotemp = SudokuBoardDaoFactory
                .getFileDao(file.getAbsolutePath() + "temp");
        Dao<SudokuBoard> daodeleted = SudokuBoardDaoFactory
                .getFileDao(file.getAbsolutePath() + "deleted")) {
            sb = dao.read();
            temp = daotemp.read();
            deleted = daodeleted.read();
            logger.info(bundle.getString("_Load_from_file_info"));
        } catch (Exception e) {
            logger.warn(bundle.getString("_Read_ex"));

            Alert alert = new Alert(Alert.AlertType.WARNING);
            ((Stage) alert.getDialogPane().getScene()
                    .getWindow()).getIcons().add(new Image("/Styles/sudo.png"));
            alert.setHeaderText(bundle.getString("_Read_ex"));
            alert.setTitle(bundle.getString("_Warning"));
            alert.showAndWait();
        }
        SudokuController.temp = temp;
        SudokuController.deleted = deleted;
        SudokuController.sb = sb;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Sudoku.fxml"),bundle);
        AnchorPane anchorPane;
        try {
            anchorPane = loader.load();
            logger.info(bundle.getString("_Load_board"));
        } catch (IOException e) {
            logger.warn(bundle.getString("_Load_window_ex"));
            throw new LoaderException(e);
        }
        Stage stage = (Stage) anchor.getScene().getWindow();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setTitle(bundle.getString("_Title"));
        SudokuController sudokuContr = loader.getController();
        sudokuContr.setMainWindowController(mainWindowContr);
        stage.show();
    }

    public void openDataBase(ActionEvent actionEvent) throws LoaderException {
        fileChooser.setTitle(bundle.getString("_Choose_base"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(null);
        SudokuBoard temp = null;
        SudokuBoard deleted = null;
        SudokuBoard sb = null;
        if (file == null) {
            return;
        }
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory
                .getJdbcDao(file.getAbsolutePath());
             Dao<SudokuBoard> daotemp = SudokuBoardDaoFactory
                     .getJdbcDao(file.getAbsolutePath() + "temp");
             Dao<SudokuBoard> daodeleted = SudokuBoardDaoFactory
                     .getJdbcDao(file.getAbsolutePath() + "deleted")) {
            sb = dao.read();
            temp = daotemp.read();
            deleted = daodeleted.read();
            logger.info(bundle.getString("_Load_from_base_info"));
        } catch (Exception e) {
            logger.warn(bundle.getString("_Load_base_ex"));

            Alert alert = new Alert(Alert.AlertType.WARNING);
            ((Stage) alert.getDialogPane().getScene()
                    .getWindow()).getIcons().add(new Image("/Styles/sudo.png"));
            alert.setHeaderText(bundle.getString("_Load_base_ex"));
            alert.setTitle(bundle.getString("_Warning"));
            alert.showAndWait();
        }
        SudokuController.temp = temp;
        SudokuController.deleted = deleted;
        SudokuController.sb = sb;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Sudoku.fxml"),bundle);
        AnchorPane anchorPane;
        try {
            anchorPane = loader.load();
            logger.info(bundle.getString("_Load_board"));
        } catch (IOException e) {
            logger.warn(bundle.getString("_Load_window_ex"));
            throw new LoaderException(e);
        }
        Stage stage = (Stage) anchor.getScene().getWindow();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setTitle(bundle.getString("_Title"));
        SudokuController sudokuContr = loader.getController();
        sudokuContr.setMainWindowController(mainWindowContr);
        stage.show();
    }
}

