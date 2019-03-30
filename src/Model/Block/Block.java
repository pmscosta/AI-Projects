package Model.Block;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Klotski;
import Model.Point;
import Model.Utilities;

public class Block {

	public int x;
	public int y;
	public int type;
	public Klotski game;

	public Block(int x, int y, int type, Klotski klotski) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.game = klotski;
	}

	public Block(Block block){
		this.x = block.x;
		this.y = block.y;
		this.type = block.type;
		this.game = block.game;
	}

	public Klotski getNextBoard(Utilities.Direction e){
		return null;
	}

	public ArrayList<Klotski> getNextBoards() {
		
		int keep_x = this.x;
		int keep_y = this.y;
		HashMap<Point, Block> keep_blocks = new HashMap<>(this.game.blocks);
	
		ArrayList<Klotski> moves = new ArrayList<>();

		for (Utilities.Direction e : Utilities.DIRECTIONS) {

			Klotski nextBoard = this.getNextBoard(e);

			if (nextBoard != null) {
				moves.add(nextBoard);
			}
				
			this.x = keep_x;
			this.y = keep_y;
			this.game.blocks = new HashMap<>(keep_blocks);
		}
		return moves;
	}

	public static Block createBlock(int x, int y, int type, Klotski klotski) {
		switch(type){
			case 0: return new Block(x, y, type, klotski);
			case 1: return new SimpleSquare(x, y, type, klotski);
			case 2: return new VerticalRectangle(x, y, type, klotski);
			case 3: return new HorizontalRectangle(x, y, type, klotski);
			case 4: return new Square(x, y, type, klotski);
		}
		return null;		
	}

	@Override
	public String toString() {
		return "Block: x:" + Integer.toString(this.x) + " y: " + Integer.toString(this.y) + " type: "
				+ Integer.toString(this.type);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type;
		result = prime * result + x;
		result = prime * result + y;
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
		Block other = (Block) obj;
		if (type != other.type)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}