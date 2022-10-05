package sudoku.board;

import java.util.Random;

public enum DifficultyLevel {
    Easy(3),
    Medium(4),
    Hard(5);

    private final int number;

    DifficultyLevel(int number) {
        this.number = number;
    }

    public SudokuBoard delete(SudokuBoard board) {
        SudokuBoard temp = (SudokuBoard) board.clone();
        Random generator = new Random();
        int y;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < this.number; j++) {
                do {
                    y = generator.nextInt(9);
                } while (temp.get(i,y) == 0);
                temp.set(i, y, 0);
            }
        }
        return temp;
    }
}
