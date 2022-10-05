package sudoku.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CloneException extends CloneNotSupportedException {

    public CloneException() {
        super();
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.WARNING,"Clone not supported");
    }

    public CloneException(String s) {
        super(s);
    }

}
