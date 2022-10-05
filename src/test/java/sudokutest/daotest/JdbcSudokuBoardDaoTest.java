package sudokutest.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import sudoku.board.BacktrackingSudokuSolver;
import sudoku.board.SudokuBoard;
import sudoku.dao.Dao;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.JdbcDaoException;

public class JdbcSudokuBoardDaoTest {
    @Test
    void baseTest() throws DaoException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoard board2;
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getJdbcDao("base")) {
            dao.write(board);
            board2 = dao.read();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        assertEquals(board,board2);
    }

    @Test
    void exceptionTest() throws Exception {
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getJdbcDao("blad");) {
            assertThrows(JdbcDaoException.class, () -> {
                dao.read();
            });
        }
    }

}

