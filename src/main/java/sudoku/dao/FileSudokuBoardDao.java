package sudoku.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;
import sudoku.board.SudokuBoard;
import sudoku.exceptions.DaoClassException;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.FileDaoException;


public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws DaoException {
        SudokuBoard sudokuBoard = null;
            try (FileInputStream fileInputStream = new FileInputStream(fileName);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                sudokuBoard = (SudokuBoard) objectInputStream.readObject();
            } catch (IOException e) {
                throw new FileDaoException(e);
            } catch (ClassNotFoundException e) {
                throw new DaoClassException(e);
            }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws FileDaoException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            throw new FileDaoException(e);
        }
    }

    @Override
    public void close() {

    }

}
