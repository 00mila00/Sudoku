package sudoku.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sudoku.board.BacktrackingSudokuSolver;
import sudoku.board.SudokuBoard;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.JdbcDaoException;


public class JdbcSudokuBardDao implements Dao<SudokuBoard> {

    private final String fileName;
    Connection connection;

    public JdbcSudokuBardDao(String fileName) throws JdbcDaoException {
        this.fileName = fileName;
        try {
            String url = "jdbc:sqlite:" + fileName;
            connection = connect(url);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }

    private Connection connect(String url) throws JdbcDaoException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return connection;
    }

    @Override
    public SudokuBoard read() throws DaoException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        ResultSet resultSet;
        String sql = "select value1, value2, value3, value4, "
                + "value5, value6, value7, value8, value9 \n"
                + "From Sudoku \n"
                + "left join SudokuRows using(SudokuID)\n"
                + "where id=?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < 9; i++) {
                preparedStatement.setString(1, String.valueOf(i + 1));
                resultSet = preparedStatement.executeQuery();
                for (int j = 0; j < 9; j++) {
                    board.set(i,j,resultSet.getInt(j + 1));
                }
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return  board;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {
        int id = obj.hashCode();
        String sqld = "Delete from Sudoku;"
                + "Delete from SudokuRows";

        String sqlc = "CREATE TABLE IF NOT EXISTS Sudoku(\n"
                + "SudokuID INTEGER KEY"
                + ");";

        String sqlcr = "CREATE TABLE IF NOT EXISTS SudokuRows(\n"
                + "id INTEGER,\n"
                + "value1 integer,\n"
                + "value2 integer,\n"
                + "value3 integer,\n"
                + "value4 integer,\n"
                + "value5 integer,\n"
                + "value6 integer,\n"
                + "value7 integer,\n"
                + "value8 integer,\n"
                + "value9 integer,\n"
                + "SudokuID INTEGER,\n "
                + "FOREIGN KEY (SudokuID)\n"
                + "references Sudoku (SudokuID)"
                + ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlc);
            statement.execute(sqlcr);
            statement.execute(sqld);
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }

        try (PreparedStatement preparedStatement = connection
                .prepareStatement("insert into Sudoku(SudokuID) values (?)")) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }

        String data = "insert into SudokuRows(id,SudokuID,value1,value2,value3,value4,"
                + "value5,value6,value7,value8,value9) "
                + "values (?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(data)) {
            for (int i = 0; i < 9; i++) {
                preparedStatement.setInt(1, i + 1);
                preparedStatement.setInt(2,id);
                for (int j = 0; j < 9; j++) {
                    preparedStatement.setInt(j + 3, obj.get(i,j));
                }
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
