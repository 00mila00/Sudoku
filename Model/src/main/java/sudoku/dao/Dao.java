package sudoku.dao;

import sudoku.exceptions.DaoClassException;
import sudoku.exceptions.DaoException;

public interface Dao<T> extends AutoCloseable {

    T read() throws DaoException, DaoClassException;

    void write(T obj) throws DaoException;
}
