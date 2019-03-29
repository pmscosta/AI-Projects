package Solver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MapExamples {

        public static int[][] starting_map = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 1, 1, 2 }, { 2, 1, 1, 2 },
                        { 1, 0, 0, 1 } };

        public static int[][] original_map = { { 2, 4, 4, 2 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 2, 1, 1, 2 },
                        { 1, 0, 0, 1 } };

        public static int[][] starting_easy = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 1, 1, 2 }, { 2, 1, 1, 2 },
                        { 1, 0, 0, 1 } };

        public static int[][] test_vertical = { { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 },
                        { 0, 0, 0, 0 } };

        public static int[][] test_square = { { 0, 4, 4, 0 }, { 0, 4, 4, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
                        { 0, 0, 0, 0 } };

        public static int[][] test_vr = { { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 },
                        { 0, 0, 0, 0 } };

        public static int[][] test_vh = { { 0, 3, 3, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
                        { 0, 0, 0, 0 } };

        public static int[][] hard = { { 1, 4, 4, 1 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 1, 3, 3, 1 }, { 0, 3, 3, 0 } }; // 120

        public static int[][] hard2 = { { 1, 4, 4, 1 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 1, 3, 3, 1 },
                        { 0, 3, 3, 0 } };

        public static int[][] hard3 = { { 1, 4, 4, 1 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 1, 3, 3, 1 },
                        { 0, 3, 3, 0 } };

        public static int[][] level3 = { { 1, 4, 4, 1 }, { 2, 4, 4, 1 }, { 2, 1, 1, 1 }, { 2, 1, 1, 2 },
                        { 2, 0, 0, 2 } };

        public static int[][] readMapFromFile(String filename) {

                int[][] result = new int[5][4];

                File file = new File(filename);
                try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String map = br.readLine();

                        String[] fields = map.split(",");

                        for (int i = 0; i < fields.length; i++) {
                                String e = fields[i];
                                e = e.trim().substring(1, e.length() - 2).trim();
                                result[i] = Arrays.stream(e.split(" ")).mapToInt(Integer::parseInt).toArray();

                        }

                        br.close();

                        return result;

                } catch (IOException e) {
                        System.out.println("File not found");
                        return null;
                }
        }

}