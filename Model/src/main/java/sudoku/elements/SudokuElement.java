package sudoku.elements;

import java.io.Serializable;
import java.util.List;
import sudoku.board.SudokuField;

public abstract class SudokuElement implements Serializable, Cloneable {

    public boolean verify(List<SudokuField> element) {
        for (int i = 0; i < 9; i++) {
            int temp = element.get(i).getFieldValue();
            if (temp != 0) {
                for (int j = i + 1; j < 9; j++) {
                    if (temp == element.get(j).getFieldValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void setField(SudokuField field, int position, List<SudokuField>  element) {
        if (position < 9 && position >= 0) {
            element.set(position, field);
        }
    }

    public SudokuField getField(int position, List<SudokuField>  element) {
        if (position < 9 && position >= 0) {
            return element.get(position);
        }
        return null;
    }

    public String toString(List<SudokuField> element) {
        StringBuilder letters = new StringBuilder();
        for (SudokuField sudokuField : element) {
            letters.append(sudokuField.getFieldValue());
            letters.append(" ");
        }
        return letters.toString();
    }
}
