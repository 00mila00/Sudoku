package sudoku.board;

import java.io.Serializable;

public interface SudokuSolver extends Serializable {
    boolean solve(SudokuBoard sudokuBoard);
}
