package sudoku.board;


import java.util.ArrayList;
import java.util.Collections;
import sudoku.elements.SudokuBox;
import sudoku.elements.SudokuColumn;
import sudoku.elements.SudokuRow;

public class BacktrackingSudokuSolver implements SudokuSolver {

    public boolean solve(SudokuBoard sudokuBoard) {
        int[] empty = findZero(sudokuBoard);
        if (empty == null) {
            return true;
        }
        ArrayList<Integer> numbers = fillList();
        Collections.shuffle(numbers);
        for (int i : numbers) {
            sudokuBoard.set(empty[0], empty[1], i);
            if (canBe(empty[0], empty[1], sudokuBoard)) {
                if (solve(sudokuBoard)) {
                    return true;
                }
            }
            sudokuBoard.set(empty[0], empty[1], 0);
        }

        return false;
    }

    private boolean canBe(int x, int y, SudokuBoard board) {
        SudokuRow row = board.getRow(x);
        SudokuColumn column = board.getColumn(y);
        SudokuBox box = board.getBox(x, y);
        return row.verify() && column.verify() && box.verify();
    }

    private ArrayList<Integer> fillList() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    private int[] findZero(SudokuBoard sudokuBoard) {
        int[] empty = new int[2];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard.get(i, j) == 0) {
                    empty[0] = i;
                    empty[1] = j;
                    return empty;
                }
            }
        }
        return null;
    }
}
