package sudoku.board;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sudoku.elements.SudokuBox;
import sudoku.elements.SudokuColumn;
import sudoku.elements.SudokuRow;

public class SudokuBoard implements PropertyChangeListener, Serializable, Cloneable {

    private SudokuField[][] board = new SudokuField[9][9];
    private SudokuSolver sudokuSolver;
    private List<SudokuRow> rows = Arrays.asList(new SudokuRow[9]);
    private List<SudokuColumn> columns = Arrays.asList(new SudokuColumn[9]);
    private List<SudokuBox> boxes = Arrays.asList(new SudokuBox[9]);
    private boolean correct = false;

    public void propertyChange(PropertyChangeEvent evt) {
        this.correct = this.checkBoard();
    }

    public void solveGame() {
        fillZeros();
        sudokuSolver.solve(this);
    }

    public boolean isCorrect() {
        return correct;
    }

    public int get(int x, int y) {
        return this.board[x][y].getFieldValue();
    }

    public void set(int x, int y, int value) {
        this.board[x][y].setFieldValue(value);
    }

    private void fillZeros() {
         for (int i = 0; i < 9; i++) {
             for (int j = 0; j < 9; j++) {
                 this.board[i][j].setFieldValue(0);
             }
        }
    }

    private int whichBox(int x, int y) {
        int i = x - x % 3;
        int j = y / 3;
        return i + j;
    }


    private boolean checkBoard() {
        boolean checkRow;
        boolean checkColumn;
        boolean checkBox;
        SudokuRow tempRow;
        SudokuColumn tempColumn;
        SudokuBox tempBox;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tempBox = getBox(3 * i, 3 * j);
                checkBox = tempBox.verify();
                if (!checkBox) {
                    return false;
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            tempRow = this.getRow(i);
            tempColumn = this.getColumn(i);
            checkRow = tempRow.verify();
            checkColumn = tempColumn.verify();
            if (!checkColumn || !checkRow) {
                return false;
            }
        }
        return true;
    }

    public SudokuRow getRow(int x) {
        return rows.get(x);
    }

    public SudokuColumn getColumn(int y) {
        return columns.get(y);
    }

    public SudokuBox getBox(int x, int y) {
        int number = whichBox(x,y);
        return boxes.get(number);
    }

    @Override
    public String toString() {
        StringBuilder letters = new StringBuilder("\n");
        for (int i = 0; i < 9; i++) {
            if (i > 1 && i % 3 == 0) {
                letters.append("- - - - - - - - - - -\n");

            }
            for (int j = 0; j < 9; j++) {
                if (j > 1 && j % 3 == 0) {

                    letters.append("| ");

                }
                letters.append(this.get(i, j));
                letters.append(" ");
            }
            letters.append("\n");
        }
        return letters.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuBoard)) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder()
                .append(board, that.board)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(board)
                .toHashCode();
    }

    @Override
    public Object clone() {
        SudokuBoard exit = new SudokuBoard(this.sudokuSolver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                exit.set(i, j, this.board[i][j].getFieldValue());
            }
        }
        return exit;
    }

    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
                board[i][j].addPropertyChangeListener(this);
            }
        }
        for (int i = 0; i < 9; i++) {
            SudokuRow tempRow = new SudokuRow();
            SudokuColumn tempColumn = new SudokuColumn();
            SudokuBox tempBox = new SudokuBox();
            for (int j = 0; j < 9; j++) {
                tempRow.setField(this.board[i][j], j);
                tempColumn.setField(this.board[j][i], j);
            }
            rows.set(i, tempRow);
            columns.set(i, tempColumn);
            int j = i - i % 3;
            int k = i % 3 * 3;
            int pos = 0;
            for (int m = j; m < j + 3; m++) {
                for (int n = k; n < k + 3; n++) {
                    tempBox.setField(this.board[m][n],pos);
                    pos++;
                }
            }
            boxes.set(i, tempBox);
        }
    }
}
