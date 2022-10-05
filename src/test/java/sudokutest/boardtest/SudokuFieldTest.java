package sudokutest.boardtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import sudoku.board.SudokuField;
import sudoku.elements.SudokuColumn;

public class SudokuFieldTest {

    SudokuField sf = new SudokuField();
    SudokuField sf2 = new SudokuField();

    @Test
    void setFieldValueTest() {
        sf.setFieldValue(9);
        assertEquals(sf.getFieldValue(),9);
        sf.setFieldValue(10);
        assertNotEquals(sf.getFieldValue(),10);
        sf.setFieldValue(-2);
        assertNotEquals(sf.getFieldValue(),-2);
    }

    @Test
    void equalsTest() {
        assertEquals(sf2, sf);
        sf2.setFieldValue(sf.getFieldValue());
        assertEquals(sf2, sf);
        sf.setFieldValue(5);
        sf2.setFieldValue(1);
        assertNotEquals(sf2, sf);
        sf2 = sf;
        assertEquals(sf2, sf);
        SudokuColumn sc = new SudokuColumn();
        assertNotEquals(sf, sc);
    }

    @Test
    void hashCodeTest() {
        assertEquals(sf2.hashCode(),sf.hashCode());
        sf2.setFieldValue(sf.getFieldValue());
        assertEquals(sf2.hashCode(),sf.hashCode());
        sf.setFieldValue(4);
        sf2.setFieldValue(7);
        assertNotEquals(sf2.hashCode(),sf.hashCode());
        sf2 = sf;
        assertEquals(sf2.hashCode(),sf.hashCode());
    }

    @Test
    void toStringTest() {
        sf.setFieldValue(3);
        String temp = "SudokuField {\nValue: 3\n}";
        assertEquals(sf.toString(),temp);
    }

    @Test
    void compareToTest() {
        sf.setFieldValue(4);
        sf2.setFieldValue(1);
        assertEquals(-1,sf.compareTo(sf2));
        sf2.setFieldValue(4);
        assertEquals(0,sf.compareTo(sf2));
        sf2.setFieldValue(8);
        assertEquals(1,sf.compareTo(sf2));
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        sf.setFieldValue(1);
        sf2 = (SudokuField) sf.clone();
        assertEquals(sf, sf2);
        sf2.setFieldValue(2);
        assertNotEquals(sf, sf2);
    }
}





