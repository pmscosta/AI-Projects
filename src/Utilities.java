public class Utilities {

    public static final boolean DEBUG = true;

    public static void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print("\t" + map[i][j]);
            }
            System.out.println();
        }
    }

    public static final Direction[] DIRECTIONS = Direction.values();

    public enum Direction {
        UP(1, 0, "UP"), RIGHT(0, 1, "RIGHT"), DOWN(-1, 0, "DOWN"), LEFT(0, -1, "LEFT"), UP2(2, 0, "UP2"),
        DOWN2(-2, 0, "DOWN2"), RIGHT2(0, 2, "RIGHT2"), LEFT2(0, -2, "LEFT2");
        public final int x;
        public final int y;
        public final String text;

        Direction(int x, int y, String text) {
            this.x = x;
            this.y = y;
            this.text = text;
        }

        public String toString() {
            return this.text;
        }
    }

}