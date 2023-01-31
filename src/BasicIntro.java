//import java.util.Random;
//
//public class BasicIntro {
//    static final int GAME_LOOP_NUMBER = 1_000;
//    static final int WIDTH = 30;
//    static final int HEIGHT = 30;
//    static final Random RANDOM = new Random(99L);
//
//
//    public static void main(String[] args) {
//        Level level = new Level(WIDTH, HEIGHT);
//
////        String[][] level = new String[WIDTH][HEIGHT];
//        int counter = 0;
//        do {
//            initLevel(level);
//            addRandomWalls(level);
//            counter++;
//        } while (!isPassable(level));
//        System.out.println(counter + ". pálya átjárható.");
//
//
//        Coordinates playerCoordinates = getRandomStartingCoordinates(level);
//        Entity player = new Entity("◌", playerCoordinates, getFarthestCorner(level, playerCoordinates), Direction.RIGHT);
//
//
//        Coordinates enemyCoordinates = getRandomStartingCoordinatesAtLeastCertainDistanceFromGivenPoint(level, player.getCoordinates(), 10);
//        Entity enemy = new Entity("Ꙫ", enemyCoordinates, getFarthestCorner(level, enemyCoordinates), Direction.LEFT);
//
//
//        String powerUpMark = "*";
//        Coordinates powerUpCoordinates = getRandomStartingCoordinates(level);
//        boolean powerUpPresentOnLevel = false;
//        int powerUpPresenceCounter = 0;
//        boolean powerUpActive = false;
//        int powerUpActiveCounter = 0;
//
//        GameResult gameResult = GameResult.TIE;
//
//        for (int iterationNumber = 1; iterationNumber <= GAME_LOOP_NUMBER; iterationNumber++) {
//            // Játékos léptetése
//            if (powerUpActive) {
//                player.setDirection(getShortestPath(level, player.getDirection(), player.getCoordinates(), enemy.getCoordinates()));
//            } else {
//                if (powerUpPresentOnLevel) {
//                    player.setDirection(getShortestPath(level, player.getDirection(), player.getCoordinates(), powerUpCoordinates));
//                } else {
//                    if (iterationNumber % 100 == 0) {
//                        player.setEscapeCoordinates(getFarthestCorner(level, player.getCoordinates()));
//                    }
//                    player.setDirection(getShortestPath(level, player.getDirection(), player.getCoordinates(), player.getEscapeCoordinates()));
//
//                }
//            }
//
//            player.setCoordinates(makeMove(player.getDirection(), level, player.getCoordinates()));
//
//            // Ellenfél léptetése
//            if (powerUpActive) {
//                if (iterationNumber % 100 == 0) {
//                    enemy.setEscapeCoordinates(getFarthestCorner(level, enemy.getCoordinates()));
//                }
//                enemy.setDirection(getShortestPath(level, enemy.getDirection(), enemy.getCoordinates(), enemy.getEscapeCoordinates()));
//            } else {
//                enemy.setDirection(getShortestPath(level, enemy.getDirection(), enemy.getCoordinates(), player.getCoordinates()));
//            }
//
//            if (iterationNumber % 2 == 0) {
//                enemy.setCoordinates(makeMove(enemy.getDirection(), level, enemy.getCoordinates()));
//            }
//            // power-up frissítése
//            if (powerUpActive) {
//                powerUpActiveCounter++;
//            } else {
//                powerUpPresenceCounter++;
//            }
//            if (powerUpPresenceCounter >= 60) {
//                if (powerUpPresentOnLevel) {
//                    powerUpCoordinates = getRandomStartingCoordinates(level);
//                }
//                powerUpPresentOnLevel = !powerUpPresentOnLevel;
//                powerUpPresenceCounter = 0;
//            }
//            if (powerUpActiveCounter >= 60) {
//                powerUpActive = false;
//                powerUpActiveCounter = 0;
//                powerUpCoordinates = getRandomStartingCoordinates(level);
//                player.setEscapeCoordinates(getFarthestCorner(level, player.getCoordinates()));
//            }
//            // 24. rész
//            // játékos-power-up interakció lekezelése
//            if (powerUpPresentOnLevel && player.getCoordinates().isSameAs(powerUpCoordinates)) {
//                powerUpActive = true;
//                powerUpPresentOnLevel = false;
//                powerUpPresenceCounter = 0;
//                enemy.setEscapeCoordinates(getFarthestCorner(level, enemy.getCoordinates()));
//            }
//
//            draw(level, player.getMark(), player.getCoordinates(), enemy.getMark(), enemy.getCoordinates(), powerUpMark, powerUpCoordinates, powerUpPresentOnLevel, powerUpActive);
//
//            addSomeDelay(350L, iterationNumber);
//            if (iterationNumber == GAME_LOOP_NUMBER) {
//                System.out.println("The End!");
//            }
//
//            // Játékos és ellenfél azonos koordinátákon tartózkodik
//            if (player.getCoordinates().isSameAs(enemy.getCoordinates())) {
//                if (powerUpActive) {
//                    gameResult = GameResult.WIN;
//                } else {
//                    gameResult = GameResult.LOSE;
//                }
//                break;
//            }
//        }
//        switch (gameResult) {
//            case WIN:
//                System.out.println("Gratulálok, győztél!");
//                break;
//            case LOSE:
//                System.out.println("Sajnálom, vesztettél!");
//                break;
//            case TIE:
//                System.out.println("A játék döntetlen!");
//        }
//        System.out.println("Játék vége!");
//    }
//
//    static Coordinates getFarthestCorner(String[][] level, Coordinates from) {
//        String[][] levelCopy = copy(level);
//        //első csillag lehelyezése a célpontra
//        levelCopy[from.getRow()][from.getColumn()] = "*";
//
//        int farthestRow = 0;
//        int farthestColumn = 0;
//
//        while (spreadAsterisksWithCheck(levelCopy)) {
//            outer:
//            for (int row = 0; row < HEIGHT; row++) {
//                for (int column = 0; column < WIDTH; column++) {
//                    if (" ".equals(levelCopy[row][column])) {
//                        farthestRow = row;
//                        farthestColumn = column;
//                        break outer;
//                    }
//                }
//            }
//        }
//        return new Coordinates(farthestRow, farthestColumn);
//    }
//
//    static Direction getShortestPath(String[][] level, Direction defaultDirection, Coordinates from, Coordinates to) {
//        String[][] levelCopy = copy(level);
//        //első csillag lehelyezése a célpontra
//        levelCopy[to.getRow()][to.getColumn()] = "*";
//
//        while (spreadAsterisksWithCheck(levelCopy)) {
//            if ("*".equals(levelCopy[from.getRow() - 1][from.getColumn()])) {
//                return Direction.UP;
//            }
//            if ("*".equals(levelCopy[from.getRow() + 1][from.getColumn()])) {
//                return Direction.DOWN;
//            }
//            if ("*".equals(levelCopy[from.getRow()][from.getColumn() - 1])) {
//                return Direction.LEFT;
//            }
//            if ("*".equals(levelCopy[from.getRow()][from.getColumn() + 1])) {
//                return Direction.RIGHT;
//            }
//        }
//        return defaultDirection;
//    }
//
//    static boolean spreadAsterisksWithCheck(String[][] levelCopy) {
//        boolean[][] mask = new boolean[HEIGHT][WIDTH];
//        for (int row = 0; row < HEIGHT; row++) {
//            for (int column = 0; column < WIDTH; column++) {
//                if ("*".equals(levelCopy[row][column])) {
//                    mask[row][column] = true;
//                }
//            }
//        }
//
//        boolean changed = false;
//        for (int row = 0; row < HEIGHT; row++) {
//            for (int column = 0; column < WIDTH; column++) {
//                if ("*".equals(levelCopy[row][column]) && mask[row][column]) {
//                    if (" ".equals(levelCopy[row - 1][column])) {
//                        levelCopy[row - 1][column] = "*";
//                        changed = true;
//                    }
//                    if (" ".equals(levelCopy[row + 1][column])) {
//                        levelCopy[row + 1][column] = "*";
//                        changed = true;
//                    }
//                    if (" ".equals(levelCopy[row][column - 1])) {
//                        levelCopy[row][column - 1] = "*";
//                        changed = true;
//                    }
//                    if (" ".equals(levelCopy[row][column + 1])) {
//                        levelCopy[row][column + 1] = "*";
//                        changed = true;
//                    }
//                }
//            }
//        }
//        return changed;
//    }
//
//    static boolean isPassable(String[][] level) {
//        // pálya lemásolása
//        String[][] levelCopy = copy(level);
//        // első szóköz megkeresése és *-al történő helyettesítése
//        outer:
//        for (int row = 0; row < HEIGHT; row++) {
//            for (int column = 0; column < WIDTH; column++) {
//                if (" ".equals(levelCopy[row][column])) {
//                    levelCopy[row][column] = "*";
//                    break outer;
//                }
//            }
//        }
//        // *-ok terjesztése a szabad helyekre
//        while (spreadAsterisks(levelCopy)) {
//        }
//        // pályamásolat vizsgálata, maradt-e szóköz valahol?
//        for (int row = 0; row < HEIGHT; row++) {
//            for (int column = 0; column < WIDTH; column++) {
//                if (" ".equals(levelCopy[row][column])) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//
//    static boolean spreadAsterisks(String[][] levelCopy) {
//        boolean changed = false;
//        for (int row = 0; row < HEIGHT; row++) {
//            for (int column = 0; column < WIDTH; column++) {
//                if ("*".equals(levelCopy[row][column])) {
//                    if (" ".equals(levelCopy[row - 1][column])) {
//                        levelCopy[row - 1][column] = "*";
//                        changed = true;
//                    }
//                    if (" ".equals(levelCopy[row + 1][column])) {
//                        levelCopy[row + 1][column] = "*";
//                        changed = true;
//                    }
//                    if (" ".equals(levelCopy[row][column - 1])) {
//                        levelCopy[row][column - 1] = "*";
//                        changed = true;
//                    }
//                    if (" ".equals(levelCopy[row][column + 1])) {
//                        levelCopy[row][column + 1] = "*";
//                        changed = true;
//                    }
//
//                }
//            }
//        }
//        return changed;
//    }
//
//    static String[][] copy(String[][] level) {
//        String[][] copy = new String[HEIGHT][WIDTH];
//        for (int row = 0; row < HEIGHT; row++) {
//            for (int column = 0; column < WIDTH; column++) {
//                copy[row][column] = level[row][column];
//            }
//        }
//        return copy;
//    }
//
//    static Direction getEscapeDirection(String[][] level, int enemyRow, int enemyColumn, Direction directionTowardsPlayer) {
//        Direction escapeDirection = getOppositeDirection(directionTowardsPlayer);
//        switch (escapeDirection) {
//            case UP:
//                if (level[enemyRow - 1][enemyColumn].equals(" ")) {
//                    return Direction.UP;
//                } else if (level[enemyRow][enemyColumn - 1].equals(" ")) {
//                    return Direction.LEFT;
//                } else if (level[enemyRow][enemyColumn + 1].equals(" ")) {
//                    return Direction.RIGHT;
//                } else {
//                    return Direction.UP;
//                }
//            case DOWN:
//                if (level[enemyRow + 1][enemyColumn].equals(" ")) {
//                    return Direction.DOWN;
//                } else if (level[enemyRow][enemyColumn - 1].equals(" ")) {
//                    return Direction.LEFT;
//                } else if (level[enemyRow][enemyColumn + 1].equals(" ")) {
//                    return Direction.RIGHT;
//                } else {
//                    return Direction.DOWN;
//                }
//            case RIGHT:
//                if (level[enemyRow][enemyColumn + 1].equals(" ")) {
//                    return Direction.RIGHT;
//                } else if (level[enemyRow - 1][enemyColumn].equals(" ")) {
//                    return Direction.UP;
//                } else if (level[enemyRow + 1][enemyColumn].equals(" ")) {
//                    return Direction.DOWN;
//                } else {
//                    return Direction.RIGHT;
//                }
//            case LEFT:
//                if (level[enemyRow][enemyColumn - 1].equals(" ")) {
//                    return Direction.LEFT;
//                } else if (level[enemyRow - 1][enemyColumn].equals(" ")) {
//                    return Direction.UP;
//                } else if (level[enemyRow + 1][enemyColumn].equals(" ")) {
//                    return Direction.DOWN;
//                } else {
//                    return Direction.LEFT;
//                }
//            default:
//                return escapeDirection;
//        }
//    }
//
//    static Direction getOppositeDirection(Direction direction) {
//        switch (direction) {
//            case UP:
//                return Direction.DOWN;
//            case DOWN:
//                return Direction.UP;
//            case LEFT:
//                return Direction.RIGHT;
//            case RIGHT:
//                return Direction.LEFT;
//            default:
//                return direction;
//        }
//    }
//
//    // 24. rész
//    static Coordinates getRandomStartingCoordinatesAtLeastCertainDistanceFromGivenPoint(String[][] level, Coordinates playerStartingCoordinates, int distance) {
//        int playerStartingRow = playerStartingCoordinates.getRow();
//        int playerStartingColumn = playerStartingCoordinates.getColumn();
//        int randomRow;
//        int randomColumn;
//        int counter = 0;
//        do {
//            randomRow = RANDOM.nextInt(HEIGHT);
//            randomColumn = RANDOM.nextInt(WIDTH);
//        } while (counter++ < 1_000 && (!level[randomRow][randomColumn].equals(" ") || calculateDistance(randomRow, randomColumn, playerStartingRow, playerStartingColumn) < distance));
//        return new Coordinates(randomRow, randomColumn);
//    }
//
//    // 24. rész
//    static int calculateDistance(int row1, int col1, int row2, int col2) {
//        int rowDifference = Math.abs(row1 - row2);
//        int colDifference = Math.abs(col1 - col2);
//        return rowDifference + colDifference;
//    }
//
//    // 24. rész
//    static Coordinates getRandomStartingCoordinates(String[][] level) {
//        int randomRow;
//        int randomColumn;
//        do {
//            randomRow = RANDOM.nextInt(HEIGHT);
//            randomColumn = RANDOM.nextInt(WIDTH);
//        } while (!level[randomRow][randomColumn].equals(" "));
//        return new Coordinates(randomRow, randomColumn);
//    }
//
//    // 24. részben átnevezve changeEnemyDirection-ról
//    static Direction changeDirectionTowards(String[][] level, Direction originalEnemyDirection, int enemyRow, int enemyColumn, int playerRow, int playerColumn) {
//        if (playerRow < enemyRow && level[enemyRow - 1][enemyColumn].equals(" ")) {
//            return Direction.UP;
//        }
//        if (playerRow > enemyRow && level[enemyRow + 1][enemyColumn].equals(" ")) {
//            return Direction.DOWN;
//        }
//        if (playerColumn > enemyColumn && level[enemyRow][enemyColumn + 1].equals(" ")) {
//            return Direction.RIGHT;
//        }
//        if (playerColumn < enemyColumn && level[enemyRow][enemyColumn - 1].equals(" ")) {
//            return Direction.LEFT;
//        }
//        return originalEnemyDirection;
//    }
//
//    static void addRandomWalls(String[][] level) {
//        addRandomWalls(level, 10, 10);
//    }
//
//    static void addRandomWalls(String[][] level, int numberOfHorizontalWalls, int numberOfVerticalWalls) {
//        for (int i = 0; i < numberOfHorizontalWalls; i++) {
//            addHorizontalWall(level);
//        }
//        for (int i = 0; i < numberOfVerticalWalls; i++) {
//            addVerticalWall(level);
//        }
//    }
//
//    static void addHorizontalWall(String[][] level) {
//        int wallWidth = RANDOM.nextInt(WIDTH - 3);
//        int wallRow = RANDOM.nextInt(HEIGHT - 2) + 1;
//        int wallColumn = RANDOM.nextInt(WIDTH - 2 - wallWidth);
//        for (int i = 0; i < wallWidth; i++) {
//            level[wallRow][wallColumn + i] = "×";
//        }
//    }
//
//    static void addVerticalWall(String[][] level) {
//        int wallHeight = RANDOM.nextInt(HEIGHT - 3);
//        int wallColumn = RANDOM.nextInt(WIDTH - 2) + 1;
//        int wallRow = RANDOM.nextInt(HEIGHT - 2 - wallHeight);
//        for (int i = 0; i < wallHeight; i++) {
//            level[wallRow + i][wallColumn] = "×";
//        }
//    }
//
//    private static void addSomeDelay(long timeout, int iterationNumber) {
//        try {
//            Thread.sleep(timeout);
//            System.out.println("----- " + iterationNumber + " -----");
//        } catch (Exception e) {
//
//        }
//    }
//
//    static Coordinates makeMove(Direction direction, String[][] level, Coordinates oldCoordinates) {
//        Coordinates newCoordinates = new Coordinates(oldCoordinates.getRow(), oldCoordinates.getColumn());
//        switch (direction) {
//            case UP:
//                if (level[oldCoordinates.getRow() - 1][oldCoordinates.getColumn()].equals(" ")) {
//                    newCoordinates.setRow(oldCoordinates.getRow() - 1);
//                }
//                break;
//            case DOWN:
//                if (level[oldCoordinates.getRow() + 1][oldCoordinates.getColumn()].equals(" ")) {
//                    newCoordinates.setRow(oldCoordinates.getRow() + 1);
//                }
//                break;
//            case LEFT:
//                if (level[oldCoordinates.getRow()][oldCoordinates.getColumn() - 1].equals(" ")) {
//                    newCoordinates.setColumn(oldCoordinates.getColumn() - 1);
//                }
//                break;
//            case RIGHT:
//                if (level[oldCoordinates.getRow()][oldCoordinates.getColumn() + 1].equals(" ")) {
//                    newCoordinates.setColumn(oldCoordinates.getColumn() + 1);
//                }
//                break;
//        }
//        return newCoordinates;
//    }
//
//    static void initLevel(String[][] level) {
//        for (int row = 0; row < level.length; row++) {
//            for (int col = 0; col < level[row].length; col++) {
//                if (row == 0 || row == HEIGHT - 1 || col == 0 || col == WIDTH - 1) {
//                    level[row][col] = "×";
//                } else {
//                    level[row][col] = " ";
//                }
//            }
//        }
//    }
//
//    static Direction changeDirection(Direction direction) {
//        switch (direction) {
//            case UP:
//                direction = Direction.RIGHT;
//                break;
//            case RIGHT:
//                direction = Direction.DOWN;
//                break;
//            case DOWN:
//                direction = Direction.LEFT;
//                break;
//            case LEFT:
//                direction = Direction.UP;
//        }
//        return direction;
//    }
//
//    // 24. részben fejlesztve (powerUp bevezetése)
//    static void draw(String[][] level, String playerMark, Coordinates playerCoordinates, String enemyMark, Coordinates enemyCoordinates,
//                     String powerUpMark, Coordinates powerUpCoordinates, boolean powerUpPresentOnLevel, boolean powerUpActive) {
//        for (int row = 0; row < HEIGHT; row++) {
//            for (int column = 0; column < WIDTH; column++) {
//                Coordinates coordinatesToDraw = new Coordinates(row, column);
//                if (coordinatesToDraw.isSameAs(playerCoordinates)) {
//                    System.out.print(playerMark);
//                } else if (coordinatesToDraw.isSameAs(enemyCoordinates)) {
//                    System.out.print(enemyMark);
//                } else if (powerUpPresentOnLevel && coordinatesToDraw.isSameAs(powerUpCoordinates)) {
//                    System.out.print(powerUpMark);
//                } else {
//                    System.out.print(level[row][column]);
//                }
//            }
//            System.out.println();
//        }
//        if (powerUpActive) {
//            System.out.println("A power-up aktív.");
//        }
//    }
//}