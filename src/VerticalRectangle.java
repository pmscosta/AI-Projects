public class VerticalRectangle {

    public static Klotski getNextBoard(Utilities.Direction e, Point current_point, Klotski game) {
        switch (e) {

        case UP: {

            Block current = game.blocks.get(current_point);

            Point up_point = new Point(current.x - 1, current.y);

            Block up = game.blocks.get(up_point);

            if (current.x < 1)
                break;

            if (up == null)
                break;

            // free space up
            if (up.type == 0) {

                --current.x;

                game.blocks.replace(up_point, current);

                game.blocks.replace(new Point(current.x + 2, current.y), up);

                return new Klotski(game.constructMap());

            }
            break;
        }

        case DOWN: {

            Block current = game.blocks.get(current_point);

            Point down_point = new Point(current.x + 2, current.y);

            Block down = game.blocks.get(down_point);

            if (current.x > 2)
                break;

            if (down == null)
                break;

            // free space down
            if (down.type == 0) {

                ++current.x;

                game.blocks.replace(current_point, down);
                game.blocks.replace(down_point, current);

                return new Klotski(game.constructMap());
            }
            break;
        }

        case LEFT: {

            Block current = game.blocks.get(current_point);
            Point left_point = new Point(current.x, current.y - 1);
            Point left_point_down = new Point(current.x + 1, current.y - 1);

            Block left = game.blocks.get(left_point);
            Block left_down = game.blocks.get(left_point_down);

            if (current.y < 1)
                break;

            if (left == null || left_down == null)
                break;

            // free space left
            if (left.type == 0 && left_down.type == 0) {

                --current.y;

                game.blocks.replace(new Point(current.x + 1, current.y + 1), left_down);
                game.blocks.replace(current_point, left);
                game.blocks.replace(left_point, current);
                game.blocks.replace(left_point_down, current);

                return new Klotski(game.constructMap());
            }

            break;
        }

        case RIGHT: {

            Block current = game.blocks.get(current_point);
            Point right_point = new Point(current.x, current.y + 1);
            Point right_point_down = new Point(current.x + 1, current.y + 1);

            Block right = game.blocks.get(right_point);
            Block right_down = game.blocks.get(right_point_down);

            if (current.y > 2) {
                break;
            }

            if (right == null || right_down == null) {
                break;
            }

            // free space left
            if (right.type == 0 && right_down.type == 0) {

                ++current.y;

                game.blocks.replace(new Point(current.x + 1, current.y - 1), right_down);
                game.blocks.replace(current_point, right);
                game.blocks.replace(right_point, current);
                game.blocks.replace(right_point_down, current);

                return new Klotski(game.constructMap());
            }

            break;
        }

        }

        return null;
    }

}