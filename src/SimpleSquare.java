

public class SimpleSquare extends Block {

    public SimpleSquare(int x, int y, int type, Klotski klotski) {
        super(x, y, type, klotski);
    }

    public static Klotski getNextBoard(Utilities.Direction e, Point current_point, Klotski game) {

        int next_x = current_point.x + e.x;
        int next_y = current_point.y + e.y;

        Block current = game.blocks.get(current_point);

        Point next_point = new Point(next_x, next_y);

        Block next_block = game.blocks.get(next_point);

        if (next_block == null)
            return null;

        if (next_block.type == 0) {

            game.blocks.replace(next_point, current);
            game.blocks.replace(current_point, next_block);

            return new Klotski(game.constructMap());

        }

        return null;
    }

    @Override
    public Klotski getNextBoard(Utilities.Direction e) {
        int next_x = x + e.x;
        int next_y = y + e.y;

        Point current_point = new Point(x, y);
        Block current = game.blocks.get(current_point);

        Point next_point = new Point(next_x, next_y);
        Block next_block = game.blocks.get(next_point);

        if (next_block == null)
            return null;

        if (next_block.type == 0) {
            game.blocks.replace(next_point, current);
            game.blocks.replace(current_point, next_block);

            return new Klotski(game.constructMap());
        }

        return null;
    }
}