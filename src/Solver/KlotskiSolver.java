package Solver;

import java.util.InputMismatchException;
import java.util.Scanner;

import Model.Klotski;
import Model.Utilities;

public class KlotskiSolver {

    private static Scanner in = new Scanner(System.in);

    public int[][] selectMap() {

        System.out.println("\n\n***** Please choose the map *****");
        System.out.println("*****   1 - Level 1             *****");
        System.out.println("*****   2 - Level 17            *****");
        System.out.println("*****   3 - Level 30            *****");
        System.out.println("*****   3 - Level 48            *****");
        System.out.println("*****   5 - Level 50            *****");
        System.out.println("*****   6 - Read From File      *****");

        int option = 0;

        System.out.print("Select one of the above options: ");

        while (option < 1 || option > 6) {
            try {
                option = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please select one of the above options");
                option = 0;
            }
            in.nextLine();
        }

        int map[][] = convertOptionToMap(option);

        System.out.println("Initial Map:");
        Utilities.printMap(map);
        System.out.println();

        return map;

    }

    public Klotski start(int option) {

        int[][] map = selectMap();

        long startTime = System.currentTimeMillis();

        Klotski endNode = runAlgorithm(option, map);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution took: " + elapsedTime + " miliseconds.");

        return endNode;
    }

    public Klotski runAlgorithm(int option, int[][] map) {

        Klotski klotski = new Klotski(map);
        Klotski endNode = null;

        switch (option) {
        case 1:
            Breadth breadth = new Breadth(klotski);
            endNode = breadth.solve();
            break;

        case 2:
            Depth depth = new Depth(klotski);
            endNode = depth.solve();
            break;

        case 3:
            AStar aStar = new AStar(klotski);
            endNode = aStar.solve();
            break;

        case 4:
            Greedy greedy = new Greedy(klotski);
            endNode = greedy.solve();
            break;
        case 5:
            IterativeDepth iterativeDepth = new IterativeDepth(klotski);
            endNode = iterativeDepth.solve();
            break;
        }

        return endNode;

    }

    public int[][] convertOptionToMap(int option) {
        switch (option) {
        case 1:
            return Utilities.clone2D(MapExamples.map_level1);
        case 2:
            return Utilities.clone2D(MapExamples.map_level17);
        case 3:
            return Utilities.clone2D(MapExamples.map_level30);
        case 4:
            return Utilities.clone2D(MapExamples.map_level48);
        case 5:
            return Utilities.clone2D(MapExamples.map_level50);
        case 6:
            System.out.print("Please type the filename: ");
            int[][] result = MapExamples.readMapFromFile(in.nextLine());
            if (result != null)
                return result;
            else
                break;
        }
        return MapExamples.map_level30.clone();
    }

    public static void main(String[] args) {

        KlotskiSolver solver = new KlotskiSolver();

        System.out.println("*****    Klotski Solver    *****");
        System.out.println("*****  1 - Breadth Search  *****");
        System.out.println("*****  2 - Depth Search    *****");
        System.out.println("*****  3 - A Star Search   *****");
        System.out.println("*****  4 - Greedy Search   *****");
        System.out.println("*****  5 - Iterative Depth *****");

        int option = 0;

        System.out.print("Select one of the above options: ");

        while (option < 1 || option > 5) {
            try {
                option = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please select one of the above options");
                option = 0;
            }

            in.nextLine();
        }

        Klotski endNode = solver.start(option);
        System.out.println("Moves=" + endNode.g + "\n");

        System.out.println("** Do you want to see all the steps:");
        System.out.println("** 1 - Yes ");
        System.out.println("** 2 - No  ");

        int printMap = 0;

        while (printMap < 1 || printMap > 2) {

            System.out.print("Select one of the above options: ");

            try {
                printMap = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\rPlease select one of the above options");
            }

        }

        if (printMap == 1)
            endNode.printSolution();

        in.close();
    }

}