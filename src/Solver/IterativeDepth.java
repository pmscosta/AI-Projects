package Solver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import Model.Klotski;
import Model.Utilities;

public class IterativeDepth {

    public static final int MAX_DEPTH = Integer.MAX_VALUE;

    public class IterativeKlotski {
        public Klotski klotski;
        public int depth;

        public IterativeKlotski(Klotski klo, int depth) {
            this.klotski = klo;
            this.depth = depth;
        }
    }

    private Map<Klotski, Integer> visited = new HashMap<>();
    private Stack<IterativeKlotski> stack = new Stack<>();
    private Klotski root;

    public IterativeDepth(Klotski map) {
        this.root = map;

    }

    public Klotski solve() {
        int i = 0;
        for (; i < MAX_DEPTH; i++) {
            stack.add(new IterativeKlotski(this.root, i));
            Klotski end = DepthIterative();
            if (end != null)
                return end;

            stack.clear();
            visited.clear();
        }

        System.out.println("Found no solution with " + i + " depth");

        return null;
    }

    public Klotski DepthIterative() {

        int steps = 0;
        while (!stack.isEmpty()) {
            steps++;
            IterativeKlotski joined = stack.pop();
            Klotski klotski = joined.klotski;
            int curr_depth = joined.depth;

            if (curr_depth == 0) {

                if (klotski.isSolution()) {
                    System.out.println("Steps=" + steps);
                    stack.clear();
                    return klotski;
                }

            } else if (curr_depth > 0) {

                for (Klotski nextPuzzle : klotski.getNextBoards()) {
                    nextPuzzle.g = klotski.g;
                    nextPuzzle.g += 1;

                    if (!visited.containsKey(nextPuzzle) || nextPuzzle.g < visited.get(nextPuzzle)) {
                        nextPuzzle.parent = klotski;
                        stack.push(new IterativeKlotski(nextPuzzle, curr_depth - 1));
                        visited.put(nextPuzzle, nextPuzzle.g);
                    }

                }
            } else {
                break;
            }

        }

        return null;

    }

}
