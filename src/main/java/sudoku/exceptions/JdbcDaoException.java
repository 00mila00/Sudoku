package sudoku.exceptions;

public class JdbcDaoException extends DaoException {
    public JdbcDaoException(Throwable throwable) {
        super(throwable);
    }

    public JdbcDaoException() {
    }

    public JdbcDaoException(String message) {
        super(message);
    }

    public JdbcDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
