package Solver;

import java.util.HashSet;
import java.util.Set;
import Model.Utilities;
import java.util.Stack;

import Model.Klotski;

public class Depth {
    private Set<Klotski> visited = new HashSet<>();
    private Stack<Klotski> stack = new Stack<>();
    private Double memoryMax = -1.0;

    public Depth(Klotski map) {
        stack.add(map);
    }

    public Klotski solve() {

        int steps = 0;

        long initial = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        while (!stack.isEmpty()) {
            steps++;
            memoryMax = Double.max(memoryMax, Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            Klotski klotski = stack.pop();

            if (klotski.isSolution()) {
                System.out.println("Steps=" + steps);

                System.out.println(
                        "Memory usage= " + Utilities.humanReadableByteCount((long) (memoryMax - initial), true));
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
