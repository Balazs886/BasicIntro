import java.util.Random;

public class BasicIntro {
    static final int GAME_LOOP_NUMBER = 1_000;
    static final int WIDTH = 30;
    static final int HEIGHT = 30;
    static final Random RANDOM = new Random(99L);


    public static void main(String[] args) {
        Level level;
        int counter = 0;
        do {
            level = new Level(RANDOM, HEIGHT, WIDTH);
            level.addRandomWalls();
            counter++;
        } while (!level.isPassable());

        System.out.println(counter + ". pálya átjárható.");
        level.isPassable(true);


        Coordinates playerCoordinates = getRandomStartingCoordinates(level);
        Entity player = new Entity("◌", playerCoordinates, level.getFarthestCorner(playerCoordinates), Direction.RIGHT);


        Coordinates enemyCoordinates = getRandomStartingCoordinatesAtLeastCertainDistanceFromGivenPoint(level, player.getCoordinates(), 10);
        Entity enemy = new Entity("Ꙫ", enemyCoordinates, level.getFarthestCorner(enemyCoordinates), Direction.LEFT);


        PowerUp powerUp = new PowerUp("*",getRandomStartingCoordinates(level),null,null,false,0,false,0);
        //String powerUpMark = "*";
        //Coordinates powerUpCoordinates = getRandomStartingCoordinates(level);
        //boolean powerUpPresentOnLevel = false;
        //int powerUpPresenceCounter = 0;
        //boolean powerUpActive = false;
        //int powerUpActiveCounter = 0;

        GameResult gameResult = GameResult.TIE;

        for (int iterationNumber = 1; iterationNumber <= GAME_LOOP_NUMBER; iterationNumber++) {
            // Játékos léptetése
            if (powerUp.isActive()) {
                player.setDirection(level.getShortestPath(player.getDirection(), player.getCoordinates(), enemy.getCoordinates()));
            } else {
                if (powerUp.isPresentOnLevel()) {
                    player.setDirection(level.getShortestPath(player.getDirection(), player.getCoordinates(), powerUp.getCoordinates()));
                } else {
                    if (iterationNumber % 100 == 0) {
                        player.setEscapeCoordinates(level.getFarthestCorner(player.getCoordinates()));
                    }
                    player.setDirection(level.getShortestPath(player.getDirection(), player.getCoordinates(), player.getEscapeCoordinates()));

                }
            }

            player.setCoordinates(makeMove(player.getDirection(), level, player.getCoordinates()));

            // Ellenfél léptetése
            if (powerUp.isActive()) {
                if (iterationNumber % 100 == 0) {
                    enemy.setEscapeCoordinates(level.getFarthestCorner(enemy.getCoordinates()));
                }
                enemy.setDirection(level.getShortestPath(enemy.getDirection(), enemy.getCoordinates(), enemy.getEscapeCoordinates()));
            } else {
                enemy.setDirection(level.getShortestPath(enemy.getDirection(), enemy.getCoordinates(), player.getCoordinates()));
            }

            if (iterationNumber % 2 == 0) {
                enemy.setCoordinates(makeMove(enemy.getDirection(), level, enemy.getCoordinates()));
            }

            // power-up frissítése
            if (powerUp.isActive()) {
                 powerUp.setActiveCounter();
            } else {
                powerUp.setPresenceCounter();
            }
            if (powerUp.getPresenceCounter() >= 60) {
                if (powerUp.isPresentOnLevel()) {
                    powerUp.setCoordinates(getRandomStartingCoordinates(level));
                }
                powerUp.setPresentOnLevel();
                powerUp.setPresenceCounter(0);
            }
            if (powerUp.getActiveCounter() >= 60) {
                powerUp.setActive(false);
                powerUp.setActiveCounter(0);
                powerUp.setCoordinates(getRandomStartingCoordinates(level));
                player.setEscapeCoordinates(level.getFarthestCorner(player.getCoordinates()));
            }
            // 24. rész
            // játékos-power-up interakció lekezelése
            if (powerUp.isPresentOnLevel() && player.getCoordinates().isSameAs(powerUp.getCoordinates())) {
                powerUp.setActive(true);
                powerUp.setPresentOnLevel(false);
                powerUp.setPresenceCounter(0);
                enemy.setEscapeCoordinates(level.getFarthestCorner(enemy.getCoordinates()));
            }

            draw(level, player.getMark(), player.getCoordinates(), enemy.getMark(), enemy.getCoordinates(), powerUp.getMark(), powerUp.getCoordinates(), powerUp.isPresentOnLevel(), powerUp.isActive());

            addSomeDelay(350L, iterationNumber);
            if (iterationNumber == GAME_LOOP_NUMBER) {
                System.out.println("The End!");
            }

            // Játékos és ellenfél azonos koordinátákon tartózkodik
            if (player.getCoordinates().isSameAs(enemy.getCoordinates())) {
                if (powerUp.isActive()) {
                    gameResult = GameResult.WIN;
                } else {
                    gameResult = GameResult.LOSE;
                }
                break;
            }
        }
        switch (gameResult) {
            case WIN:
                System.out.println("Gratulálok, győztél!");
                break;
            case LOSE:
                System.out.println("Sajnálom, vesztettél!");
                break;
            case TIE:
                System.out.println("A játék döntetlen!");
        }
        System.out.println("Játék vége!");
    }

    static Coordinates getRandomStartingCoordinates(Level level) {
        Coordinates randomCoordinates;
        do {
            randomCoordinates = new Coordinates(RANDOM.nextInt(HEIGHT), RANDOM.nextInt(WIDTH));
        } while (!level.isEmpty(randomCoordinates));
        return randomCoordinates;
    }

    static Coordinates getRandomStartingCoordinatesAtLeastCertainDistanceFromGivenPoint(Level level, Coordinates playerStartingCoordinates, int distance) {
        Coordinates randomCoordinates;
        int counter = 0;
        do {
            randomCoordinates = getRandomStartingCoordinates(level);
        } while(counter++ < 1_000 && randomCoordinates.distanceFrom(playerStartingCoordinates) < distance);
        return randomCoordinates;
    }

    private static void addSomeDelay(long timeout, int iterationNumber) {
        try {
            Thread.sleep(timeout);
            System.out.println("----- " + iterationNumber + " -----");
        } catch (Exception e) {

        }
    }

    static Coordinates makeMove(Direction direction, Level level, Coordinates oldCoordinates) {
        Coordinates newCoordinates = new Coordinates(oldCoordinates.getRow(), oldCoordinates.getColumn());
        switch (direction) {
            case UP:
                if (level.isEmpty(new Coordinates(oldCoordinates.getRow() - 1,oldCoordinates.getColumn()))) {
                    newCoordinates.setRow(oldCoordinates.getRow() - 1);
                }
                break;
            case DOWN:
                if (level.isEmpty(new Coordinates(oldCoordinates.getRow() + 1,oldCoordinates.getColumn()))) {
                    newCoordinates.setRow(oldCoordinates.getRow() + 1);
                }
                break;
            case LEFT:
                if (level.isEmpty(new Coordinates(oldCoordinates.getRow(),oldCoordinates.getColumn()-1))) {
                    newCoordinates.setColumn(oldCoordinates.getColumn() - 1);
                }
                break;
            case RIGHT:
                if (level.isEmpty(new Coordinates(oldCoordinates.getRow(),oldCoordinates.getColumn()+1))) {
                    newCoordinates.setColumn(oldCoordinates.getColumn() + 1);
                }
                break;
        }
        return newCoordinates;
    }

    static void draw(Level level, String playerMark, Coordinates playerCoordinates, String enemyMark, Coordinates enemyCoordinates,
                     String powerUpMark, Coordinates powerUpCoordinates, boolean powerUpPresentOnLevel, boolean powerUpActive) {
        for (int row = 0; row < HEIGHT; row++) {
            for (int column = 0; column < WIDTH; column++) {
                Coordinates coordinatesToDraw = new Coordinates(row, column);
                if (coordinatesToDraw.isSameAs(playerCoordinates)) {
                    System.out.print(playerMark);
                } else if (coordinatesToDraw.isSameAs(enemyCoordinates)) {
                    System.out.print(enemyMark);
                } else if (powerUpPresentOnLevel && coordinatesToDraw.isSameAs(powerUpCoordinates)) {
                    System.out.print(powerUpMark);
                } else {
                    System.out.print(level.getCell(coordinatesToDraw));
                }
            }
            System.out.println();
        }
        if (powerUpActive) {
            System.out.println("A power-up aktív.");
        }
    }
}