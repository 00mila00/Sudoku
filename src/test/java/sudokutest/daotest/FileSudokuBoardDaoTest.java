package sudokutest.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import sudoku.board.BacktrackingSudokuSolver;
import sudoku.board.SudokuBoard;
import sudoku.dao.Dao;
import sudoku.dao.SudokuBoardDaoFactory;
import sudoku.exceptions.DaoException;


public class FileSudokuBoardDaoTest {


    @Test
    void fileTest() throws DaoException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoard board2 = null;
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao("plik.txt")) {
            dao.write(board);
            board2 = dao.read();
        } catch (Exception e) {
            throw new DaoException(e);
        }

        assertEquals(board,board2);
    }

    @Test
    void exceptionTest() throws Exception {
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao("blad.txt")) {
            assertThrows(DaoException.class, () -> {
                dao.read();
            });
        }
    }
}
