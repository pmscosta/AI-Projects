package Solver;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import Model.Klotski;

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

    private Set<Klotski> visited = new HashSet<>();
    private Stack<IterativeKlotski> stack = new Stack<>();
    private Klotski root;

    public IterativeDepth(Klotski map) {
        this.root = map;

    }

    public Klotski solve() {
        int i = 0;
        for (; i < MAX_DEPTH; i += 100) {

            System.out.println(i);
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

            if (klotski.isSolution()) {
                System.out.println("\nSteps=" + steps + "\n");
                System.out.println("\nMoves=" + klotski.g + "\n");
                stack.clear();
                return klotski;
            }

            if (curr_depth <= 0)
                break;

            for (Klotski nextPuzzle : klotski.getNextBoards()) {
                nextPuzzle.g = klotski.g;
                nextPuzzle.g += 1;

                if (!visited.contains(nextPuzzle)) {
                    nextPuzzle.parent = klotski;
                    stack.push(new IterativeKlotski(nextPuzzle, curr_depth - 1));
                    visited.add(nextPuzzle);
                }

            }

        }

        return null;

    }

}
