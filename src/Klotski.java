import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Klotski implements Comparable<Klotski> {

    public HashMap<Point, Block> blocks;

    public Block BigSquare;

    public int[][] map;

    public ArrayList<int[][]> maps; 

    public Klotski(int[][] map) {
        this.map = map;
        this.blocks = new HashMap<>();
        this.createBlocks();

        this.blocks.get(new Point(4,0)).getPossibleMoves();
    }


    public void removeUsedBlocks(int i, int j, int value){

        switch(value){

            case 1: 
                this.map[i][j] = -1; 
                return;
            case 2: 
                this.map[i][j] = -1; 
                this.map[i][j+1] = -1;
                return;
            case 3: 
                this.map[i][j] = -1;
                this.map[i+1][j] = -1;
            case 4: 
                this.map[i][j] = -1;
                this.map[i][j+1] = -1;
                this.map[i+1][j] = -1;
                this.map[i+1][j +1] = -1;
            default:
                return;
        }
    }

    public void createBlocks() {

        for (int i = 0; i < this.map.length; i++) {

            for (int j = 0; j < this.map[i].length; j++) {

                int block_value = this.map[i][j];

                if(block_value != -1){

                    if(block_value == 4){
                        this.BigSquare = new Block(i, j, block_value, this);
                        this.blocks.put(new Point(i, j), BigSquare);
                    }else{
                        this.blocks.put(new Point(i, j), new Block(i, j, block_value, this) );
                    }

                removeUsedBlocks(i, j, block_value);

                }
            }
        }
    }

    public void addBlock(int[][] map, Block e){

        int i = e.x;
        int j = e.y;

        switch(e.type){
            case 1: 
                map[i][j] = e.type; 
                return;
            case 2: 
                map[i][j] = e.type; 
                map[i][j+1] = e.type;
                return;
            case 3: 
                map[i][j] = e.type;
                map[i+1][j] = e.type;
            case 4: 
                map[i][j] = e.type;
                map[i][j+1] = e.type;
                map[i+1][j] = e.type;
                map[i+1][j +1] = e.type;
            default:
                return;
        }
    }


    public int[][] constructMap(){

        int[][] map = new int[5][4];

        for(Block e : this.blocks.values()){
            addBlock(map, e);
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
		if (blocks == null) {
			if (other.blocks != null)
				return false;
		} else if (!blocks.equals(other.blocks))
			return false;
		if (!Arrays.deepEquals(map, other.map))
			return false;
		return true;
    }
    
    public int manhattanDistance(int x1, int y1, int x0, int y0){
        return Math.abs(x1-x0) + Math.abs(y1-y0);
    }

    

	@Override
	public int compareTo(Klotski o) {

        int distance1 = manhattanDistance(this.BigSquare.x, this.BigSquare.y, 3, 1);

        int distance2 = manhattanDistance(o.BigSquare.x, o.BigSquare.y, 3, 1);

        if(distance1 < distance2)
            return 1;
        else if(distance1 > distance2)
            return -1;
        else
            return 0;
    }
    


    public void printMap(int[][] map){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print("\t" + map[i][j]);
            }
            System.out.println();
        }
    }

    public void printHashMap(){

        for(Block b : this.blocks.values()){
            System.out.println(b);
        }

    }

}