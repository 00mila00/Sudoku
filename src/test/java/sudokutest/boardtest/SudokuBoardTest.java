package sudokutest.boardtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import sudoku.board.BacktrackingSudokuSolver;
import sudoku.board.DifficultyLevel;
import sudoku.board.SudokuBoard;
import sudoku.board.SudokuSolver;
import sudoku.exceptions.CloneException;

public class SudokuBoardTest {

    SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
    SudokuBoard boards = new SudokuBoard(sudokuSolver);
    SudokuBoard boards2 = new SudokuBoard(sudokuSolver);

    @Test
    void ifZeroTest() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(boards.get(i, j), 0);
            }
        }
    }

    @Test
    void afterFillTest() {
        boards.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(boards.get(i, j), 0);
            }
        }
    }

    //If both of boards are different

    @Test
    void ifEqualBoardTest() {
        boards.solveGame();
        boards2.solveGame();
        int c = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (boards.get(i,j) != boards2.get(i,j)) {
                    return;
                }

                if (boards.get(i,j) == boards2.get(i,j)) {
                    c++;
                }
            }
        }
       assertNotEquals(c,81, "These boards are the same");
    }

    //Checking if every element in the board is correct
    @Test
    void listenerTest() {
        boards.solveGame();
        assertTrue(boards.isCorrect());
        boards.set(1,1,1);
        boards.set(1,6,1);
        assertFalse(boards.isCorrect());
        boards.solveGame();
        assertTrue(boards.isCorrect());
        boards.set(1,1,1);
        boards.set(7,1,1);
        assertFalse(boards.isCorrect());
        boards.solveGame();
        assertTrue(boards.isCorrect());
        boards.set(0,2,1);
        boards.set(2,0,1);
        assertFalse(boards.isCorrect());
    }

    @Test
    void equalsTest() {
        assertEquals(boards2, boards);
        boards.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boards.set(i,j,boards2.get(i,j));
            }
        }
        assertEquals(boards2, boards);
        boards.set(1,1,1);
        boards2.set(1,1,2);
        assertNotEquals(boards2, boards);
        boards = boards2;
        assertEquals(boards2, boards);
        assertNotEquals(boards,sudokuSolver);
    }


    @Test
    void hashCodeTest() {
        assertEquals(boards.hashCode(),boards2.hashCode());
        boards.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boards.set(i,j,boards2.get(i,j));
            }
        }
        assertEquals(boards.hashCode(),boards2.hashCode());
        boards.set(1,1,1);
        boards2.set(1,1,2);
        assertNotEquals(boards.hashCode(),boards2.hashCode());
        boards = boards2;
        assertEquals(boards.hashCode(),boards2.hashCode());
    }

    @Test
    void toStringTest() {

        String template = "\n"
               + "0 0 0 | 0 0 0 | 0 0 0 \n"
               + "0 0 0 | 0 0 0 | 0 0 0 \n"
               + "0 0 0 | 0 0 0 | 0 0 0 \n"
                + "- - - - - - - - - - -\n"
               + "0 0 0 | 0 0 0 | 0 0 0 \n"
               + "0 0 0 | 0 0 0 | 0 0 0 \n"
               + "0 0 0 | 0 0 0 | 0 0 0 \n"
               + "- - - - - - - - - - -\n"
               + "0 0 0 | 0 0 0 | 0 0 0 \n"
               + "0 0 0 | 0 0 0 | 0 0 0 \n"
               + "0 0 0 | 0 0 0 | 0 0 0 \n";
        assertEquals(boards.toString(),template);
    }

    @Test
    void cloneTest() throws CloneException {
        boards.solveGame();
        boards2 = (SudokuBoard) boards.clone();
        assertEquals(boards, boards2);
        boards2.solveGame();
        assertNotEquals(boards, boards2);
    }

    @Test
    void difficultyTest() throws CloneException {
        boards.solveGame();
        boards2 = DifficultyLevel.Easy.delete(boards);
        assertNotEquals(boards, boards2);
    }
}


