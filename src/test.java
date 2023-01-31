public class test {
    public static void main(String[] args) {
        Level level = new Level(20,20);
        level.initLevel();
        System.out.println(level.getLevel());
        System.out.println(level);

        int i = 0;
        int j = 0;
        for (String[][] elements : level.getLevel()) {
            i++;
            System.out.println(elements[i][j]);
        }
    }
}
