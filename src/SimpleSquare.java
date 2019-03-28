
public class SimpleSquare extends Block {

    public SimpleSquare(int x, int y, int type, Klotski klotski) {
        super(x, y, type, klotski);
    }

    @Override
    public Klotski getNextBoard(Utilities.Direction e) {
        int next_x = x + e.x;
        int next_y = y + e.y;

        if (next_x < 0 || next_x > 4 || next_y < 0 || next_y > 3)
            return null;

        Point current_point = new Point(x, y);
        Block current = game.blocks.get(current_point);

        Point next_point = new Point(next_x, next_y);
        Block next_block = game.blocks.get(next_point);

        if (next_block == null)
            return null;

        if (next_block.type == 0) {

            if (Math.abs(e.x) > 1) {

                int sign = Integer.signum(e.x);

                for (int i = 1; i <= Math.abs(e.x); i++) {

                    if (game.blocks.get(new Point(x + (i * sign), y)).type != 0)
                        return null;

                }

            } else if (Math.abs(e.y) > 1) {

                int sign = Integer.signum(e.y);

                for (int i = 1; i <= Math.abs(e.y); i++) {

                    if (game.blocks.get(new Point(x, y + (i * sign))).type != 0)
                        return null;

                }

            }

            game.blocks.replace(next_point, current);
            game.blocks.replace(current_point, next_block);

            return new Klotski(game.constructMap());
        }

        return null;
    }

}