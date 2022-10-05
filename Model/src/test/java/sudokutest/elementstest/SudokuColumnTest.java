package sudokutest.elementstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import sudoku.board.BacktrackingSudokuSolver;
import sudoku.board.SudokuField;
import sudoku.board.SudokuSolver;
import sudoku.elements.SudokuColumn;
import sudoku.exceptions.CloneException;

public class SudokuColumnTest {

    SudokuColumn sc = new SudokuColumn();
    SudokuColumn sc2 = new SudokuColumn();

    @Test
    void verifyColumnTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sc.setField(temp, i);
        }
        assertTrue(sc.verify());
    }

    @Test
    void getColumnFieldTest() {
        assertNull(sc.getField(1));
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sc.setField(temp, i);
            assertEquals(sc.getField(i),temp);
        }
    }

    @Test
    void equalsTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sc.setField(temp, i);
        }
        for (int i = 9; i > 0; i--) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i - 1);
            sc.setField(temp, i);
        }
        assertNotEquals(sc2, sc);

        for (int i = 0; i < 9; i++) {
            sc.setField(sc2.getField(i),i);
        }
        assertEquals(sc2, sc);

        sc = sc2;
        assertEquals(sc2, sc);

        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        assertNotEquals(sc, sudokuSolver);
    }

    @Test
    void hashCodeTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sc.setField(temp, i);
        }
        for (int i = 9; i > 0; i--) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i - 1);
            sc.setField(temp, i);
        }

        assertNotEquals(sc.hashCode(),sc2.hashCode());

        for (int i = 0; i < 9; i++) {
            sc.setField(sc2.getField(i),i);
        }
        assertEquals(sc.hashCode(),sc2.hashCode());

        sc = sc2;
        assertEquals(sc.hashCode(),sc2.hashCode());
    }

    @Test
    void toStringTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sc.setField(temp, i);
        }
        String temp = "Column elements: 1 2 3 4 5 6 7 8 9 ";
        assertEquals(sc.toString(),temp);
    }

    @Test
    void cloneTest() throws CloneException {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sc.setField(temp, i);
        }
        sc2 = (SudokuColumn) sc.clone();
        assertEquals(sc, sc2);
    }
}



