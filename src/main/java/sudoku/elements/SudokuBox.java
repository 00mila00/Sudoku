package sudoku.elements;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sudoku.board.SudokuField;
import sudoku.exceptions.CloneException;

public class SudokuBox extends SudokuElement {

    private final List<SudokuField> box = Arrays.asList(new SudokuField[9]);

    public boolean verify() {
        return super.verify(box);
    }

    public SudokuField getField(int position) {
        return super.getField(position, box);
    }

    public void setField(SudokuField field, int position) {
        super.setField(field, position, box);
    }

    @Override
    public String toString() {
        return "Box elements: " + super.toString(box);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuBox)) {
            return false;
        }

        SudokuBox box1 = (SudokuBox) o;

        return new EqualsBuilder()
                .append(box, box1.box)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(box)
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
