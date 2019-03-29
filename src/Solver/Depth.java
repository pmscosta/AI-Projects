package Solver;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import Model.Klotski;

public class Depth {
    private Set<Klotski> visited = new HashSet<>();
    private Stack<Klotski> stack = new Stack<>();

    public Depth(Klotski map) {
        stack.add(map);
    }

    public Klotski solve() {

        int steps = 0;
        while (!stack.isEmpty()) {
            steps++;
            Klotski klotski = stack.pop();

            if (klotski.isSolution()) {
                System.out.println("\nSteps=" + steps + "\n");
                System.out.println("\nMoves=" + klotski.g + "\n");
                stack.clear();
                return klotski;
            }

            for (Klotski nextPuzzle : klotski.getNextBoards()) {
                nextPuzzle.g = klotski.g;
                nextPuzzle.g += 1;

                if (!visited.contains(nextPuzzle)) {
                    nextPuzzle.parent = klotski;
                    stack.push(nextPuzzle);
                    visited.add(nextPuzzle);
                }

            }

        }

        System.out.println("Found no solution with " + steps + " steps");
        return null;

    }
}
