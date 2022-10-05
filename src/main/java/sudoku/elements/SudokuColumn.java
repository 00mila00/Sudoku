package sudoku.elements;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sudoku.board.SudokuField;
import sudoku.exceptions.CloneException;

public class SudokuColumn extends SudokuElement {

    private final List<SudokuField> column = Arrays.asList(new SudokuField[9]);

    public boolean verify() {
        return super.verify(column);
    }

    public SudokuField getField(int position) {
        return super.getField(position, column);
    }

    public void setField(SudokuField field, int position) {
        super.setField(field, position, column);
    }

    @Override
    public String toString() {
        return "Column elements: " + super.toString(column);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuColumn)) {
            return false;
        }

        SudokuColumn that = (SudokuColumn) o;

        return new EqualsBuilder()
                .append(column, that.column)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(column)
                .toHashCode();
    }

    @Override
    public Object clone() throws CloneException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            //logger.log(Level.WARNING,"Clone not supported");
            throw new CloneException();
        }
    }
}
