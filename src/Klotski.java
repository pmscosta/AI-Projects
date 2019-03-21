import java.util.HashMap;
import Node.*;

public class Klotski {

    private HashMap<Point, Block> blocks;

    private int[][] default_map = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 3, 3, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    public Klotski() {

        this.blocks = new HashMap<>();

    }

    public void solve() {

        for (int i = 0; i < this.default_map.length; i++) {

            for (int j = 0; j < this.default_map[i].length; j++) {

                this.blocks.put(new Point(i, j), )

            }

        }

    }

    public static void main(String[] args) {

    }

}