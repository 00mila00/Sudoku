package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.board.BacktrackingSudokuSolver;
import sudoku.board.DifficultyLevel;
import sudoku.board.SudokuBoard;
import sudoku.dao.Converter;
import sudoku.dao.Dao;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.exceptions.BidirectionalException;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.LoaderException;


public class SudokuController {

    public AnchorPane background;
    public Button close;
    private final FileChooser fileChooser = new FileChooser();
    static BacktrackingSudokuSolver bss = new BacktrackingSudokuSolver();
    static SudokuBoard sb = new SudokuBoard(bss);
    static SudokuBoard deleted = null;
    static SudokuBoard temp = null;
    public Text win;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @FXML
    ArrayList<TextField> fields = new ArrayList<>();
    @FXML
    private GridPane gridSudoku;
    @FXML
    private MainWindowController mainWindowController;


    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @FXML
    public void previous(ActionEvent actionEvent) throws LoaderException {
        FXMLLoader loader = new FXMLLoader(this.getClass()
                .getResource("/StartWindow.fxml"),StartWindowController.bundle);
        AnchorPane anchorPane = null;
        try {
            anchorPane = loader.load();
            logger.info(StartWindowController.bundle.getString("_Back_to_menu"));
        } catch (IOException e) {
            throw new LoaderException(e);
        }
        Stage stage = (Stage) background.getScene().getWindow();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        temp = null;

    }

    @FXML
    private void initialize() throws BidirectionalException {
        if (temp == null) {
            sb.solveGame();
            deleted = getDiff().delete(sb);
            temp = (SudokuBoard) deleted.clone();
        }
        StringConverter converter = new Converter();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                HBox hb = new HBox();
                hb.setId("hb");
                hb.setMinSize(90, 90);
                hb.setAlignment(Pos.CENTER);
                gridSudoku.add(hb, i, j);
                TextField tf = new TextField();
                hb.getChildren().add(tf);
                tf.setMinHeight(80);
                tf.setMaxWidth(80);
                tf.setAlignment(Pos.CENTER);
                tf.setId("val");

                UnaryOperator<TextFormatter.Change> textFilter = c -> {
                    if (c.getText().matches("[1-9]")) {
                        c.setRange(0, tf.getText().length());
                        return c;
                    } else
                    if (c.getText().isEmpty()) {
                        return c;
                    }
                    return null;
                };

                TextFormatter<Integer> formatter
                        = new TextFormatter<Integer>(converter, 0, textFilter);
                tf.setTextFormatter(formatter);

                try {
                    JavaBeanIntegerPropertyBuilder builder
                            = JavaBeanIntegerPropertyBuilder.create();
                    JavaBeanIntegerProperty integerProperty
                            = builder.bean(temp.getRow(i).getField(j)).name("fieldValue").build();
                    tf.textProperty().bindBidirectional(integerProperty, converter);
                } catch (NoSuchMethodException e) {
                    logger.warn(StartWindowController.bundle.getString("_No_such_a_method"));
                    throw new BidirectionalException();
                }
                tf.setEditable(false);
                if (deleted.get(i,j) == 0) {
                    tf.setEditable(true);
                    tf.setText(String.valueOf(temp.get(i,j)));
                    tf.setId("tfid");
                }
                fields.add(tf);
            }
        }
    }

    private DifficultyLevel getDiff() {
        return StartWindowController.getLevel();
    }

    public void check(ActionEvent actionEvent) {
        logger.info(StartWindowController.bundle.getString("_Check_info"));
        int counter = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (fields.get(i * 9 + j).isEditable() && fields.get(i * 9 + j)
                        .getText().equals(String.valueOf(sb.get(i, j)))
                        && !fields.get(i * 9 + j).getText().equals("")) {
                    fields.get(i * 9 + j).setId("correct");
                    counter++;
                } else if (fields.get(i * 9 + j).isEditable() && !fields.get(i * 9 + j)
                        .getText().equals("")) {
                    fields.get(i * 9 + j).setId("incorrect");
                }
                fields.get(i * 9 + j).setEditable(false);
            }
        }
        if (getDiff() == DifficultyLevel.Easy) {
            if (counter == 27) {
                win();
            }
        }
        if (getDiff() == DifficultyLevel.Medium) {
            if (counter == 36) {
                win();
            }
        }
        if (getDiff() == DifficultyLevel.Hard) {
            if (counter == 45) {
                win();
            }
        }
    }

    public void continueGame(ActionEvent actionEvent) {
        logger.info(StartWindowController.bundle.getString("_Resumed_info"));
        win.setVisible(false);
        for (int i = 0; i < 81; i++) {
            if (!fields.get(i).getId().equals("val")) {
                fields.get(i).setEditable(true);
                fields.get(i).setId("tfid");
            }
        }
    }

    public void save(ActionEvent actionEvent) {
        fileChooser.setTitle(StartWindowController.bundle.getString("_Save_to_file"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            return;
        }
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory
                .getFileDao(file.getAbsolutePath());
             Dao<SudokuBoard> daotemp = SudokuBoardDaoFactory
                     .getFileDao(file.getAbsolutePath() + "temp");
             Dao<SudokuBoard> daodeleted = SudokuBoardDaoFactory
                     .getFileDao(file.getAbsolutePath() + "deleted")) {
            dao.write(sb);
            daotemp.write(temp);
            daodeleted.write(deleted);
            logger.info(StartWindowController.bundle.getString("_Saved_info"));
        } catch (Exception e) {
            logger.warn(StartWindowController.bundle.getString("_Save_ex"));
            Alert alert = new Alert(Alert.AlertType.WARNING);
            ((Stage) alert.getDialogPane().getScene().getWindow())
                    .getIcons().add(new Image("/Styles/sudo.png"));
            alert.setHeaderText(StartWindowController.bundle.getString("_Save_ex"));
            alert.setTitle(StartWindowController.bundle.getString("_Warning"));
            alert.showAndWait();
        }
    }

    public void win() {
        logger.info(StartWindowController.bundle.getString("_Finished_info"));
        win.setVisible(true);
        TranslateTransition trans = new TranslateTransition();
        trans.setDuration(Duration.seconds(5));
        trans.setFromY(500);
        trans.setToY(50);
        trans.setNode(win);
        trans.play();

    }

    public void saveDatabase(ActionEvent actionEvent) {
        fileChooser.setTitle(StartWindowController.bundle.getString("_Save_to_database"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            return;
        }
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory
                .getJdbcDao(file.getAbsolutePath());
             Dao<SudokuBoard> daoTemp = SudokuBoardDaoFactory
                     .getJdbcDao(file.getAbsolutePath() + "temp");
             Dao<SudokuBoard> daoDeleted = SudokuBoardDaoFactory
                     .getJdbcDao(file.getAbsolutePath() + "deleted")) {
            dao.write(sb);
            daoTemp.write(temp);
            daoDeleted.write(deleted);
            logger.info(StartWindowController.bundle.getString("_Saved_info"));
        } catch (Exception e) {
            logger.warn(StartWindowController.bundle.getString("_Save_to_database_ex"));
            Alert alert = new Alert(Alert.AlertType.WARNING);
            ((Stage) alert.getDialogPane().getScene().getWindow())
                    .getIcons().add(new Image("/Styles/sudo.png"));
            alert.setHeaderText(StartWindowController.bundle.getString("_Save_to_database_ex"));
            alert.setTitle(StartWindowController.bundle.getString("_Warning"));
            alert.showAndWait();
        }
    }
}








