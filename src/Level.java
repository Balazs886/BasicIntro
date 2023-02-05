import java.util.Random;

public class Level {
    private final int height;
    private final int width;
    private final String[][] level;
    private final Random RANDOM;

    Level(Random random, int height, int width) {
        this.RANDOM = random;
        this.width = width;
        this.height = height;
        this.level = new String[height][width];
        int lastRowIndex = height - 1;
        int lastColumnIndex = width - 1;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || row == lastRowIndex || col == 0 || col == lastColumnIndex) {
                    level[row][col] = "×";
                } else {
                    level[row][col] = " ";
                }
            }
        }
    }

    public void addRandomWalls() {
        addRandomWalls(10, 10);
    }

    public void addRandomWalls(int numberOfHorizontalWalls, int numberOfVerticalWalls) {
        for (int i = 0; i < numberOfHorizontalWalls; i++) {
            addHorizontalWall();
        }
        for (int i = 0; i < numberOfVerticalWalls; i++) {
            addVerticalWall();
        }
    }
    private void addHorizontalWall() {
        int wallWidth = RANDOM.nextInt(width - 3);
        int wallRow = RANDOM.nextInt(height - 2) + 1;
        int wallColumn = RANDOM.nextInt(width - 2 - wallWidth);
        for (int i = 0; i < wallWidth; i++) {
            level[wallRow][wallColumn + i] = "×";
        }
    }

    private void addVerticalWall() {
        int wallHeight = RANDOM.nextInt(height - 3);
        int wallColumn = RANDOM.nextInt(width - 2) + 1;
        int wallRow = RANDOM.nextInt(height - 2 - wallHeight);
        for (int i = 0; i < wallHeight; i++) {
            level[wallRow + i][wallColumn] = "×";
        }
    }

    public boolean isPassable() {
        return isPassable(false);
    }

    public boolean isPassable(boolean draw) {
        // pálya lemásolása
        String[][] levelCopy = copy();
        // első szóköz megkeresése és *-al történő helyettesítése
        outer:
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (" ".equals(levelCopy[row][column])) {
                    levelCopy[row][column] = "*";
                    break outer;
                }
            }
        }
        // *-ok terjesztése a szabad helyekre
        while (spreadAsterisks(levelCopy)) {
        }
        // pályamásolat vizsgálata, maradt-e szóköz valahol?
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (" ".equals(levelCopy[row][column])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean spreadAsterisks(String[][] levelCopy) {
        boolean changed = false;
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if ("*".equals(levelCopy[row][column])) {
                    if (" ".equals(levelCopy[row - 1][column])) {
                        levelCopy[row - 1][column] = "*";
                        changed = true;
                    }
                    if (" ".equals(levelCopy[row + 1][column])) {
                        levelCopy[row + 1][column] = "*";
                        changed = true;
                    }
                    if (" ".equals(levelCopy[row][column - 1])) {
                        levelCopy[row][column - 1] = "*";
                        changed = true;
                    }
                    if (" ".equals(levelCopy[row][column + 1])) {
                        levelCopy[row][column + 1] = "*";
                        changed = true;
                    }

                }
            }
        }
        return changed;
    }

    private String[][] copy() {
        String[][] copy = new String[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                copy[row][column] = level[row][column];
            }
        }
        return copy;
    }

    public boolean isEmpty(Coordinates coordinates) {
        return " ".equals(level[coordinates.getRow()][coordinates.getColumn()]);
    }

    public Coordinates getFarthestCorner(Coordinates from) {
        String[][] levelCopy = copy();
        //első csillag lehelyezése a célpontra
        levelCopy[from.getRow()][from.getColumn()] = "*";

        int farthestRow = 0;
        int farthestColumn = 0;

        while (spreadAsterisksWithCheck(levelCopy)) {
            outer:
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    if (" ".equals(levelCopy[row][column])) {
                        farthestRow = row;
                        farthestColumn = column;
                        break outer;
                    }
                }
            }
        }
        return new Coordinates(farthestRow, farthestColumn);
    }

    boolean spreadAsterisksWithCheck(String[][] levelCopy) {
        boolean[][] mask = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if ("*".equals(levelCopy[row][column])) {
                    mask[row][column] = true;
                }
            }
        }

        boolean changed = false;
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if ("*".equals(levelCopy[row][column]) && mask[row][column]) {
                    if (" ".equals(levelCopy[row - 1][column])) {
                        levelCopy[row - 1][column] = "*";
                        changed = true;
                    }
                    if (" ".equals(levelCopy[row + 1][column])) {
                        levelCopy[row + 1][column] = "*";
                        changed = true;
                    }
                    if (" ".equals(levelCopy[row][column - 1])) {
                        levelCopy[row][column - 1] = "*";
                        changed = true;
                    }
                    if (" ".equals(levelCopy[row][column + 1])) {
                        levelCopy[row][column + 1] = "*";
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    public Direction getShortestPath(Direction defaultDirection, Coordinates from, Coordinates to) {
        String[][] levelCopy = copy();
        //első csillag lehelyezése a célpontra
        levelCopy[to.getRow()][to.getColumn()] = "*";

        while (spreadAsterisksWithCheck(levelCopy)) {
            if ("*".equals(levelCopy[from.getRow() - 1][from.getColumn()])) {
                return Direction.UP;
            }
            if ("*".equals(levelCopy[from.getRow() + 1][from.getColumn()])) {
                return Direction.DOWN;
            }
            if ("*".equals(levelCopy[from.getRow()][from.getColumn() - 1])) {
                return Direction.LEFT;
            }
            if ("*".equals(levelCopy[from.getRow()][from.getColumn() + 1])) {
                return Direction.RIGHT;
            }
        }
        return defaultDirection;
    }

    public String getCell(Coordinates coordinates) {
        return level[coordinates.getRow()][coordinates.getColumn()];
    }
}
