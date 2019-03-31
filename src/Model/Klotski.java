package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import Model.Block.*;

public class Klotski implements Comparable<Klotski> {

    public Klotski parent;
    public int[][] map;
    public HashMap<Point, Block> blocks;
    public ArrayList<Block> empty;
    public Boolean visited;
    public Block BigSquare;
    public int g = 0;

    public Klotski(int[][] map) {
        this.parent = null;
        this.map = Utilities.clone2D(map);
        this.blocks = new HashMap<>();
        this.empty = new ArrayList<>();
        this.createBlocks();
        this.visited = false;
    }

    public Klotski(Klotski klotski) {
        this.parent = klotski.parent;
        this.map = Utilities.clone2D(klotski.map);
        this.blocks = new HashMap<>(klotski.blocks);
        this.visited = klotski.visited;
        this.BigSquare = new Block(klotski.BigSquare);
        this.g = klotski.g;
    }

    public void printSolution() {

        Klotski klotski = this;
        int i = -1;
        LinkedList<Klotski> list = new LinkedList<>();

        while (klotski.parent != null) {
            list.addFirst(klotski);
            klotski = klotski.parent;
        }
        list.addFirst(klotski);

        for (Klotski elem : list) {
            System.out.println(++i);
            Utilities.printMap(elem.constructMap());
        }
    }

    public boolean isSolution() {
        return this.blocks.get(new Point(3, 2)).x == 3 && this.blocks.get(new Point(3, 1)).y == 1
                && this.blocks.get(new Point(3, 1)).type == 4;
    }

    public ArrayList<Klotski> getNextBoards() {

        Set<Block> eligibleBlocks = new HashSet<>();

        Block block;

        for (Block elem : empty) {
            for (Utilities.Direction direction : Utilities.DIRECTIONS) {
                block = this.blocks.get(new Point(elem.x + direction.x, elem.y + direction.y));
                if (block != null && block.type != 0) {
                    eligibleBlocks.add(block);
                }
            }
        }

        ArrayList<Klotski> games = new ArrayList<>();

        for (Block e : eligibleBlocks) {
            games.addAll(e.getNextBoards());
        }
        return games;
    }

    public void createBlocks() {

        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[i].length; j++) {

                int block_value = this.map[i][j];

                if (block_value >= 0) {

                    Block block = Block.createBlock(i, j, block_value, this);

                    if (block_value == 4) {
                        this.BigSquare = block;
                    } else if (block_value == 0) {
                        this.empty.add(block);
                    }

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
            this.map[i][j] = -2;
            this.map[i + 1][j] = -10;
            this.blocks.put(new Point(i + 1, j), block);
            return;
        case 3:
            this.map[i][j] = -3;
            this.map[i][j + 1] = -10;
            this.blocks.put(new Point(i, j + 1), block);
            return;
        case 4:
            this.map[i][j] = -4;
            this.map[i][j + 1] = -10;
            this.map[i + 1][j] = -10;
            this.map[i + 1][j + 1] = -10;
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

    public int averageNecessaryMoves(int type) {
        switch (type) {
        case -1:
            return 1;
        case -2:
            return 8;
        case -3:
            return 9;
        }
        return 0;
    }

    public int heuristic1() {
        int x = this.BigSquare.x;
        int y = this.BigSquare.y;
        int dx = this.map.length - x - 1;
        int dy = y - 1;
        int necessaryMoves = dx + Math.abs(dy);

        int spacedx = Math.abs(this.empty.get(0).x - this.empty.get(1).x);
        int spacedy = Math.abs(this.empty.get(0).y - this.empty.get(1).y);

        necessaryMoves += spacedx + spacedy - 1;

        if (dx != 0) {
            for (int i = x + 2; i < this.map.length; i++) {
                necessaryMoves += averageNecessaryMoves(map[i][y]) + averageNecessaryMoves(map[i][y + 1]);

            }
        }
        if (dy > 0) {
            necessaryMoves += averageNecessaryMoves(map[x][y - 1]) + averageNecessaryMoves(map[x + 1][y - 1]);
        } else if (dy < 0) {
            necessaryMoves += averageNecessaryMoves(map[x][y + 2]) + averageNecessaryMoves(map[x + 1][y + 2]);
        }
        return necessaryMoves;
    }

    public int heuristic2() {
        int x = this.BigSquare.x;
        int y = this.BigSquare.y;
        int dx = this.map.length - x - 2;
        int dy = y - 1;
        int necessaryMoves = dx + Math.abs(dy);

        if (dx != 0) {
            for (int i = x + 2; i < this.map.length; i++) {
                if (map[i][y] != 0) {
                    necessaryMoves++;
                }
                if (map[i][y + 1] != 0) {
                    necessaryMoves++;
                }
            }
        }

        if (dy > 0) {
            if (map[x][y - 1] != 0) {
                necessaryMoves++;
            }
            if (map[x + 1][y - 1] != 0) {
                necessaryMoves++;
            }
        } else if (dy < 0) {
            if (map[x][y + 2] != 0) {
                necessaryMoves++;
            }
            if (map[x + 1][y + 2] != 0) {
                necessaryMoves++;
            }
        }
        return necessaryMoves;

    }

    public int calculateEmptySpotsUnderBigSquare() {
        int x = this.BigSquare.x;
        int y = this.BigSquare.y;
        int emptySpots = x * 4 + 1;

        for (int i = x + 2; i < this.map.length; i++) {
            if (map[i][y] == 0)
                emptySpots += this.map.length - i + 1;
            if (map[i][y + 1] == 0)
                emptySpots += this.map.length - i + 1;
        }
        return emptySpots;

    }

    public int calculateManhattanDistance() {

        return manhattanDistance(this.BigSquare.x, this.BigSquare.y, 3, 1);

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