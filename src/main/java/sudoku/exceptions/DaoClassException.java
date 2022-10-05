package sudoku.exceptions;

public class DaoClassException extends DaoException {
    public DaoClassException(Throwable throwable) {
        super(throwable);
    }

    public DaoClassException() {
    }

    public DaoClassException(String message) {
        super(message);
    }

    public DaoClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
