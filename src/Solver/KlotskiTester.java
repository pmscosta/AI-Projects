package Solver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import Model.*;

public class KlotskiTester {

    private KlotskiSolver solver;

    private static final short NUM_OF_ALGS = 5;

    private static final short NUM_OF_MAPS = 3;

    private static final short NUM_OF_TIMES = 5;

    private PrintWriter file;

    public static int[][] deepCopyIntMatrix(int[][] input) {
        if (input == null)
            return null;
        int[][] result = new int[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }

    public KlotskiTester() {

        this.solver = new KlotskiSolver();
        try {
            this.file = new PrintWriter("results.txt", "UTF-8");
            this.file.println("\n");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        KlotskiTester tester = new KlotskiTester();

        if (args.length == 2) {
            tester.runTests(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        } else {

            for (int i = 1; i <= NUM_OF_ALGS; i++) {
                for (int j = 1; j <= NUM_OF_MAPS; j++) {
                    tester.runTests(i, j);
                }
                tester.file.write("\n");
            }
        }
        tester.file.close();
    }

    public void runTests(int alg, int mapOpt) {

        System.out.println("running test for alg: " + alg + " map: " + mapOpt);

        long total = 0;
        long avg = 0;
        for (int i = 0; i < NUM_OF_TIMES; i++) {
            int[][] map = deepCopyIntMatrix(solver.convertOptionToMap(mapOpt));
            total += start(alg, map.clone());
        }

        avg = total / (long) NUM_OF_TIMES;

        this.file.println("Algorithm: " + getAlgName(alg) + "\tmap: " + getMapName(mapOpt) + "\taverage: " + avg);

    }

    public String getAlgName(int alg) {

        switch (alg) {
        case 1:
            return "Breadth";
        case 2:
            return "Depth";
        case 3:
            return "AStar";
        case 4:
            return "Greedy";
        case 5:
            return "IterativeDepth";
        default:
            return "Error";
        }

    }

    public String getMapName(int alg) {

        switch (alg) {
        case 1:
            return "Easy";
        case 2:
            return "Medium";
        case 3:
            return "Hard";
        default:
            return "Error";
        }

    }

    /**
     * Test purposes only
     * 
     * @param option
     * @param map
     * @return long - time of execution
     */
    public long start(int option, int[][] map) {

        long startTime = System.currentTimeMillis();

        Klotski end = solver.runAlgorithm(option, map);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution took: " + elapsedTime + " miliseconds" + " Moves: " + end.g);

        return elapsedTime;
    }

}