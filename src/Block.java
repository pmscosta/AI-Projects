import java.util.ArrayList;

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

	public ArrayList<Klotski> getNextBoards() {

		ArrayList<Klotski> moves = new ArrayList<>();

		for (Utilities.Direction e : Utilities.DIRECTIONS) {

			Klotski nextBoard = null;

			int[][] keep_map = this.game.constructMap();

			int keep_x = this.x;

			int keep_y = this.y;

			Point currentPoint = new Point(this.x, this.y);

			switch (this.type) {
			case 1:
				nextBoard = SimpleSquare.getNextBoard(e, currentPoint, this.game);
				if (nextBoard != null) {
					moves.add(nextBoard);
				}
				break;
			case 2:
				nextBoard = VerticalRectangle.getNextBoard(e, currentPoint, this.game);
				if (nextBoard != null) {
					moves.add(nextBoard);
				}
				break;
			case 3:
				nextBoard = HorizontalRectangle.getNextBoard(e, currentPoint, this.game);
				if (nextBoard != null) {
					moves.add(nextBoard);
				}
				break;
			case 4:
				nextBoard = Square.getNextBoard(e, currentPoint, this.game);
				if (nextBoard != null) {
					moves.add(nextBoard);
				}
				break;
			}

			this.x = keep_x;
			this.y = keep_y;
			this.game.map = keep_map;
			this.game.createBlocks();
		}
		return moves;
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