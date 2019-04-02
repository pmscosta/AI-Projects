package Model;

public class Utilities {

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit)
            return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

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
        UP(-1, 0, "UP"), RIGHT(0, 1, "RIGHT"), DOWN(1, 0, "DOWN"), LEFT(0, -1, "LEFT"), UP2(-2, 0, "UP2"),
        DOWN2(2, 0, "DOWN2"), RIGHT2(0, 2, "RIGHT2"), LEFT2(0, -2, "LEFT2"), UP2LEFT(-1, -1, "UP2LEFT"),
        UP2RIGHT(-1, 1, "UP2RIGHT"), DOWN2LEFT(1, -1, "DOWN2LEFT"), DOWN2RIGHT(1, 1, "DOWN2RIGHT");
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

    public static int[][] clone2D(int[][] matrix) {
        int[][] myInt = new int[matrix.length][];

        for (int i = 0; i < matrix.length; i++) {
            int[] aMatrix = matrix[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }

        return myInt;
    }

}