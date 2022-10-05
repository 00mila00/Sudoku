package sudokutest.boardtest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import sudoku.board.BacktrackingSudokuSolver;
import sudoku.board.SudokuBoard;

class BacktrackingSudokuSolverTest {

    @Test
    void solve() {
        BacktrackingSudokuSolver bss = new BacktrackingSudokuSolver();
        SudokuBoard sb = new SudokuBoard(bss);
        assertTrue(bss.solve(sb));
        assertTrue(sb.isCorrect());
    }
}