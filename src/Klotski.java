import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Klotski implements Comparable<Klotski> {

    public HashMap<Point, Block> blocks;

    public int g=0;

    public Block BigSquare;

    public int[][] map;

    public Klotski(int[][] map) {
        this.map = map.clone();
        this.blocks = new HashMap<>();
        this.createBlocks();
    }

    public boolean isSolution() {
        return this.blocks.get(new Point(3, 2)).x == 3 && this.blocks.get(new Point(3, 1)).y == 1
                && this.blocks.get(new Point(3, 1)).type == 4;
    }

    public ArrayList<Klotski> getNextBoards() {

        ArrayList<Klotski> games = new ArrayList<>();

        for (Block e : this.blocks.values()) {
            if (e.type != 0)
                games.addAll(e.getNextBoards());
        }

        return games;

    }

    public void createBlocks() {

        for (int i = 0; i < this.map.length; i++) {

            for (int j = 0; j < this.map[i].length; j++) {

                int block_value = this.map[i][j];

                if (block_value != -1) {

                    Block block = new Block(i, j, block_value, this);

                    if (block_value == 4)
                        this.BigSquare = block;

                    this.blocks.put(new Point(i, j), block);

                    removeUsedBlocks(i, j, block);

                }
            }
        }
    }

    public void removeUsedBlocks(int i, int j, Block block) {

        switch (block.type) {

        case 1:
            this.map[i][j] = -1;
            return;
        case 2:
            this.map[i][j] = -1;
            this.map[i + 1][j] = -1;
            this.blocks.put(new Point(i + 1, j), block);
            return;
        case 3:
            this.map[i][j] = -1;
            this.map[i][j + 1] = -1;
            this.blocks.put(new Point(i, j + 1), block);
            return;
        case 4:
            this.map[i][j] = -1;
            this.map[i][j + 1] = -1;
            this.map[i + 1][j] = -1;
            this.map[i + 1][j + 1] = -1;
            this.blocks.put(new Point(i, j + 1), block);
            this.blocks.put(new Point(i + 1, j), block);
            this.blocks.put(new Point(i + 1, j + 1), block);
            return;
        default:
            return;
        }
    }

    public int[][] constructMap() {

        int[][] map = new int[5][4];

        for (Point p : this.blocks.keySet()) {

            Block e = this.blocks.get(p);
            map[p.x][p.y] = e.type;

        }

        return map;
    }

    // OVERRIDE METHODS FOR PRIORITY QUEUE

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
        result = prime * result + Arrays.deepHashCode(map);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Klotski other = (Klotski) obj;

        int[][] lhs_map = this.constructMap();
        int[][] rhs_map = other.constructMap();

        if (!Arrays.deepEquals(lhs_map, rhs_map))
            return false;

        return true;
    }

    public int manhattanDistance(int x1, int y1, int x0, int y0) {
        return Math.abs(x1 - x0) + Math.abs(y1 - y0);
    }


    public int calculateEmptySpotsUnderBigSquareMinimizing(){
        int x = this.BigSquare.x;
        int y = this.BigSquare.y;
        int emptySpots= (int) ((this.map.length - x)*4.5);

        for(int i = x+2; i<this.map.length;i++){
            if(map[i][y] == 0)
                emptySpots+=i +1;
            if(map[i][y+1] == 0)
                emptySpots+=i +1;
        }
        return emptySpots;

    }

    public int calculateEmptySpotsUnderBigSquare(){
        int x = this.BigSquare.x;
        int y = this.BigSquare.y;
        int emptySpots=x*8 + 1;

        for(int i = x+2; i<this.map.length;i++){
            if(map[i][y] == 0)
                emptySpots+=this.map.length-i +1;
            if(map[i][y+1] == 0)
                emptySpots+=this.map.length-i +1;
        }
        return emptySpots;

    }

    public int calculateH(){
        int numberOfEmptySpotsUnderBigSquare = calculateEmptySpotsUnderBigSquare();

        return numberOfEmptySpotsUnderBigSquare;
    }

    public int calculateHMinimizing(){
        int numberOfEmptySpotsUnderBigSquare = calculateEmptySpotsUnderBigSquareMinimizing();

        return numberOfEmptySpotsUnderBigSquare;
    }



    @Override
    public int compareTo(Klotski that) {

        int distance1 = manhattanDistance(this.BigSquare.x, this.BigSquare.y, 3, 1);

        int distance2 = manhattanDistance(that.BigSquare.x, that.BigSquare.y, 3, 1);

        if (distance1 < distance2)
            return 1;
        else if (distance1 > distance2)
            return -1;
        else
            return 0;

    }

    public void printHashMap() {

        for (Point p : this.blocks.keySet()) {

            System.out.print(p);
            System.out.print('\t');
            System.out.println(this.blocks.get(p));

        }

    }

}