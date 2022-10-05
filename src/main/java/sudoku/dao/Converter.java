package sudoku.dao;

import javafx.util.StringConverter;

public class Converter extends StringConverter<Integer> {

    @Override
    public String toString(Integer object) {
        if (object < 0 || object > 9) {
            return null;
        }
        if (object.equals(0)) {
            return "";
        }
        return String.valueOf(object);
    }

    @Override
    public Integer fromString(String string) {
        if (!string.matches("[0-9]")) {
            return 0;
        }
        return Integer.valueOf(string);
    }
}
