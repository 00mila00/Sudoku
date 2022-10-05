package sudoku.dao;

import sudoku.board.SudokuBoard;
import sudoku.exceptions.JdbcDaoException;

public abstract class SudokuBoardDaoFactory {
    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static Dao<SudokuBoard> getJdbcDao(String fileName) throws JdbcDaoException {
        return new JdbcSudokuBardDao(fileName);
    }

    private SudokuBoardDaoFactory() {
    }
}
