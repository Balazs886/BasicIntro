public class Level {
    private int width;
    private int height;
    private String[][] level;

    Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String[][] getLevel() {
        return level;
    }

    public void setLevel(String[][] level) {
        this.level = level;
    }

    String[][] initLevel() {
        level = new String[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || row == height - 1 || col == 0 || col == width - 1) {
                    level[row][col] = "×";
                } else {
                    level[row][col] = " ";
                }
            }
        }
        System.out.println("Ez a pálya első eleme: " + level[0][0]);
        return level;
    }

}
