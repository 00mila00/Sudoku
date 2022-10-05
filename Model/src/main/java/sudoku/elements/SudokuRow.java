package sudoku.elements;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sudoku.board.SudokuField;
import sudoku.exceptions.CloneException;

public class SudokuRow extends SudokuElement {
    private final List<SudokuField> row = Arrays.asList(new SudokuField[9]);

    public boolean verify() {
        return super.verify(row);
    }

    public SudokuField getField(int position) {
        return super.getField(position, row);
    }

    public void setField(SudokuField field, int position) {
        super.setField(field, position, row);
    }

    @Override
    public String toString() {
        return "Row elements: " + super.toString(row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuRow)) {
            return false;
        }

        SudokuRow row1 = (SudokuRow) o;

        return new EqualsBuilder()
                .append(row, row1.row)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(row)
                .toHashCode();
    }

    @Override
    public Object clone() throws CloneException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneException();
        }
    }
}
