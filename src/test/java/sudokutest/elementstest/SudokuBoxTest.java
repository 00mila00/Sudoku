package sudokutest.elementstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import sudoku.board.BacktrackingSudokuSolver;
import sudoku.board.SudokuField;
import sudoku.board.SudokuSolver;
import sudoku.elements.SudokuBox;
import sudoku.exceptions.CloneException;

public class SudokuBoxTest {

    SudokuBox sb = new SudokuBox();
    SudokuBox sb2 = new SudokuBox();

    @Test
    void verifyBoxTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sb.setField(temp, i);
        }
        assertTrue(sb.verify());;
    }

    @Test
    void getBoxFieldTest() {
        assertNull(sb.getField(1));
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sb.setField(temp, i);
            assertEquals(sb.getField(i),temp);
        }
        assertNull(sb.getField(20));
        assertNull(sb.getField(-20));
    }

    @Test
    void equalsTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sb.setField(temp, i);
        }
        for (int i = 9; i > 0; i--) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i - 1);
            sb.setField(temp, i);
        }
        assertNotEquals(sb2, sb);

        for (int i = 0; i < 9; i++) {
            sb.setField(sb2.getField(i),i);
        }
        assertEquals(sb2, sb);

        sb = sb2;
        assertEquals(sb2, sb);

        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        assertNotEquals(sb, sudokuSolver);
    }

    @Test
    void hashCodeTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sb.setField(temp, i);
        }
        for (int i = 9; i > 0; i--) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i - 1);
            sb.setField(temp, i);
        }

        assertNotEquals(sb.hashCode(),sb2.hashCode());

        for (int i = 0; i < 9; i++) {
            sb.setField(sb2.getField(i),i);
        }
        assertEquals(sb.hashCode(),sb2.hashCode());

        sb = sb2;
        assertEquals(sb.hashCode(),sb2.hashCode());
    }

    @Test
    void toStringTest() {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sb.setField(temp, i);
        }
        String temp = "Box elements: 1 2 3 4 5 6 7 8 9 ";
        assertEquals(sb.toString(),temp);
    }

    @Test
    void cloneTest() throws CloneException {
        for (int i = 0; i < 9; i++) {
            SudokuField temp = new SudokuField();
            temp.setFieldValue(i + 1);
            sb.setField(temp, i);
        }
        sb2 = (SudokuBox) sb.clone();
        assertEquals(sb, sb2);
    }
}