public class HorizontalRectangle {

    public static Klotski getNextBoard(Utilities.Direction e, Point current_point, Klotski game) {

        switch (e) {
        case UP: {

            Block current = game.blocks.get(current_point);

            Point up_point = new Point(current.x - 1, current.y);
            Point up_point_right = new Point(current.x - 1, current.y + 1);

            Block up = game.blocks.get(up_point);
            Block up_right = game.blocks.get(up_point_right);

            if (current.x < 1 || current.x > 4 || current.y < 0 || current.y > 3)
                break;

            if (up == null || up_right == null) {
                break;
            }

            // free space up
            if (up.type == 0 && up_right.type == 0) {

                --current.x;

                game.blocks.replace(new Point(current.x + 1, current.y + 1), up_right);
                game.blocks.replace(current_point, up);
                game.blocks.replace(up_point, current);
                game.blocks.replace(up_point_right, current);

                return new Klotski(game.constructMap());

            }
            break;
        }

        case DOWN: {

            Block current = game.blocks.get(current_point);

            if (current.x > 3 || current.x < 0 || current.y < 0 || current.y > 3)
                break;

            Point down_point = new Point(current.x + 1, current.y);
            Point down_point_right = new Point(current.x + 1, current.y + 1);

            Block down = game.blocks.get(down_point);
            Block down_right = game.blocks.get(down_point_right);

            if (down == null || down_point_right == null)
                break;

            // free space down
            if (down.type == 0 && down_right.type == 0) {

                ++current.x;

                game.blocks.replace(new Point(current.x - 1, current.y + 1), down_right);
                game.blocks.replace(current_point, down);
                game.blocks.replace(down_point, current);
                game.blocks.replace(down_point_right, current);

                return new Klotski(game.constructMap());

            }
            break;
        }

        // case LEFT2: {

        // Block current = game.blocks.get(current_point);

        // if (current.y < 2 || current.y > 3 || current.x < 0 || current.x > 4)
        // break;

        // Point left_point = new Point(current.x, current.y - 1);

        // Block left = game.blocks.get(left_point);

        // Point left_point_2 = new Point(current.x, current.y - 2);

        // Block left_2 = game.blocks.get(left_point_2);

        // if (left == null || left_2 == null)
        // break;

        // // free space left
        // if (left.type == 0 && left_2.type == 0) {

        // current.y = current.y - 2;

        // game.blocks.replace(left_point, current);
        // game.blocks.replace(left_point_2, current);
        // game.blocks.replace(current_point, left);
        // game.blocks.replace(new Point(current.x, current.y + 3), left_2);

        // return new Klotski(game.constructMap());
        // }
        // break;
        // }

        case LEFT: {

            Block current = game.blocks.get(current_point);

            if (current.y < 1 || current.y > 3 || current.x < 0 || current.x > 4)
                break;

            Point left_point = new Point(current.x, current.y - 1);

            Block left = game.blocks.get(left_point);

            if (left == null)
                break;

            // free space left
            if (left.type == 0) {

                --current.y;

                game.blocks.replace(left_point, current);

                game.blocks.replace(new Point(current.x, current.y + 2), left);

                return new Klotski(game.constructMap());
            }
            break;
        }

        // case RIGHT2: {

        // Block current = game.blocks.get(current_point);

        // if (current_point.y > 0 || current.y < 0 || current.x < 0 || current.x > 4)
        // break;

        // Point right_point = new Point(current.x, current.y + 2);

        // Block right = game.blocks.get(right_point);

        // Point right_point_2 = new Point(current.x, current.y + 3);

        // Block right_2 = game.blocks.get(right_point_2);

        // if (right == null || right_2 == null)
        // break;

        // // free space left
        // if (right.type == 0 && right_2.type == 0) {

        // current.y = current.y + 2;

        // game.blocks.replace(current_point, right);
        // game.blocks.replace(new Point(current.x, current.y - 1), right_2);
        // game.blocks.replace(right_point, current);
        // game.blocks.replace(right_point_2, current);

        // return new Klotski(game.constructMap());
        // }
        // break;
        // }

        case RIGHT: {

            Block current = game.blocks.get(current_point);

            if (current.y > 1 || current.y < 0 || current.x < 0 || current.x > 4)
                break;

            Point right_point = new Point(current.x, current.y + 2);

            Block right = game.blocks.get(right_point);

            if (right == null)
                break;

            // free space left
            if (right.type == 0) {

                ++current.y;

                game.blocks.replace(current_point, right);
                game.blocks.replace(right_point, current);

                return new Klotski(game.constructMap());
            }
            break;
        }

        default:
            break;

        }

        return null;
    }

}