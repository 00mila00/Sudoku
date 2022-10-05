package sudoku.exceptions;

import java.io.IOException;

public class LoaderException extends IOException {
    public LoaderException() {
    }

    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoaderException(Throwable cause) {
        super(cause);
    }
}
