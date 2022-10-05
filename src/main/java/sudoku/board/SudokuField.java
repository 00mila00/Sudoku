package sudoku.board;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sudoku.exceptions.CloneException;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {


    private int value = 0;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value <= 9 && value >= 0) {
            int old = this.value;
            this.value = value;
            support.firePropertyChange("value", old, value);
        }
    }

    @Override
    public String toString() {
        return "SudokuField {\nValue: " + value + "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuField)) {
            return false;
        }

        SudokuField field = (SudokuField) o;

        return new EqualsBuilder()
                .append(value, field.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public int compareTo(SudokuField o) {
        return Integer.compare(o.getFieldValue(), value);
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
