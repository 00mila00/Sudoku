package sudoku.exceptions;

public class DaoException extends Exception {
    public DaoException(Throwable throwable) {
        super(throwable);
    }

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
