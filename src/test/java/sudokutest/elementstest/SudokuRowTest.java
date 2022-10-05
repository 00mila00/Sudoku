package sudokutest.elementstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import sudoku.board.BacktrackingSudokuSolver;
import sudoku.board.SudokuField;
import sudoku.board.SudokuSolver;
import sudoku.elements.SudokuRow;
import sudoku.exceptions.CloneException;

public class SudokuRowTest {

    SudokuRow sr = new SudokuRow();
    SudokuRow sr2 = new SudokuRow();

    @Test
    void verifyRowTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sr.setField(temp, i);
        }
        assertTrue(sr.verify());
    }

    @Test
    void getRowFieldTest() {
        assertNull(sr.getField(1));
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sr.setField(temp, i);
            assertEquals(sr.getField(i),temp);
        }
    }


    @Test
    void equalsTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sr.setField(temp, i);
        }
        for (int i = 9; i > 0; i--) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i - 1);
            sr.setField(temp, i);
        }
        assertNotEquals(sr2, sr);

        for (int i = 0; i < 9; i++) {
            sr.setField(sr2.getField(i),i);
        }
        assertEquals(sr2, sr);

        sr = sr2;
        assertEquals(sr2, sr);
    }

    @Test
    void hashCodeTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sr.setField(temp, i);
        }
        for (int i = 9; i > 0; i--) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i - 1);
            sr.setField(temp, i);
        }

        assertNotEquals(sr.hashCode(),sr2.hashCode());

        for (int i = 0; i < 9; i++) {
            sr.setField(sr2.getField(i),i);
        }
        assertEquals(sr.hashCode(),sr2.hashCode());

        sr = sr2;
        assertEquals(sr.hashCode(),sr2.hashCode());
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        assertNotEquals(sr, sudokuSolver);
    }

    @Test
    void toStringTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sr.setField(temp, i);
        }
        String temp = "Row elements: 1 2 3 4 5 6 7 8 9 ";
        assertEquals(sr.toString(),temp);
    }

    @Test
    void cloneTest() throws CloneException {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sr.setField(temp, i);
        }
        sr2 = (SudokuRow) sr.clone();
        assertEquals(sr, sr2);
    }
}
