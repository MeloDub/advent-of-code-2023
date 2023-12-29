package it.melo.aoc;

import it.melo.aoc.util.AocUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    private static char[][] blueprint;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        blueprint = AocUtils.readInput();
        partOneResult();

        blueprint = AocUtils.readInput();
        partTwoResult();

        long finish = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (finish - start));
    }

    private static void partOneResult() {
        int sum = 0;
        for (int row = 0; row < blueprint.length; row++) {
            for (int col = 0; col < blueprint[row].length; col++) {
                if (isSymbol(blueprint[row][col])) {
                    sum += findAdjacentNumbers(row, col);
                }
            }
        }

        System.out.println("Part one result is: " + sum);
    }

    private static void partTwoResult() {
        int gearRatiosSum = 0;
        for (int row = 0; row < blueprint.length; row++) {
            for (int col = 0; col < blueprint[row].length; col++) {
                if (blueprint[row][col] == '*') {
                    gearRatiosSum += findAdjacentRatio(row, col);
                }
            }
        }

        System.out.println("Part two result is: " + gearRatiosSum);
    }

    private static boolean isSymbol(char c) {
        return !Character.isDigit(c) && !Character.valueOf(c).equals('.');
    }

    private static int findAdjacentNumbers(int row, int col) {
        return findUp(row, col) +
                findDown(row, col) +
                findLeft(row, col) +
                findRight(row, col) +
                findTopLeft(row, col) +
                findTopRight(row, col) +
                findDownLeft(row, col) +
                findDownRight(row, col);
    }

    private static int findAdjacentRatio(int row, int col) {
        int ratio = 0;

        var resultStream = Stream.of(
                findUp(row, col),
                findDown(row, col),
                findLeft(row, col),
                findRight(row, col),
                findTopLeft(row, col),
                findTopRight(row, col),
                findDownLeft(row, col),
                findDownRight(row, col)
        );

        Map<Boolean, List<Integer>> resultsMap = resultStream.collect(Collectors.partitioningBy(n -> n > 0));
        if (resultsMap.get(true).size() > 1) {
            ratio = resultsMap.get(true).stream()
                    .reduce((a, b) -> a * b)
                    .orElse(0);
        }

        return ratio;
    }

    private static int findUp(int row, int col) {
        if (row > 0 && Character.isDigit(blueprint[row - 1][col])) {
            return Integer.parseInt(getCompleteDigit(row - 1, col));
        }
        return 0;
    }

    private static int findDown(int row, int col) {
        if (row < blueprint.length - 1 && Character.isDigit(blueprint[row + 1][col])) {
            return Integer.parseInt(getCompleteDigit(row + 1, col));
        }
        return 0;
    }

    private static int findLeft(int row, int col) {
        if (col > 0 && Character.isDigit(blueprint[row][col - 1])) {
            return Integer.parseInt(getCompleteDigit(row, col - 1));
        }
        return 0;
    }

    private static int findRight(int row, int col) {
        if (col < blueprint.length - 1 && Character.isDigit(blueprint[row][col + 1])) {
            return Integer.parseInt(getCompleteDigit(row, col + 1));
        }
        return 0;
    }

    private static int findTopLeft(int row, int col) {
        if (row > 0 && col > 0 && Character.isDigit(blueprint[row - 1][col - 1])) {
            return Integer.parseInt(getCompleteDigit(row - 1, col - 1)); // top-left
        }
        return 0;
    }

    private static int findTopRight(int row, int col) {
        if (row > 0 && col < blueprint.length - 1 && Character.isDigit(blueprint[row - 1][col + 1])) {
            return Integer.parseInt(getCompleteDigit(row - 1, col + 1)); // top-right
        }
        return 0;
    }

    private static int findDownLeft(int row, int col) {
        if (row < blueprint.length - 1 && col > 0 && Character.isDigit(blueprint[row + 1][col - 1])) {
            return Integer.parseInt(getCompleteDigit(row + 1, col - 1)); // bottom-left
        }
        return 0;
    }

    private static int findDownRight(int row, int col) {
        if (row < blueprint.length - 1 && col < blueprint.length - 1 && Character.isDigit(blueprint[row + 1][col + 1])) {
            return Integer.parseInt(getCompleteDigit(row + 1, col + 1)); // bottom-right
        }
        return 0;
    }

    private static String getCompleteDigit(int x, int y) {
        if (x < 0 || x >= blueprint.length || y < 0 || y >= blueprint.length || !Character.isDigit(blueprint[x][y])) {
            return "";
        }
        String number = String.valueOf(blueprint[x][y]);
        blueprint[x][y] = '.';
        number = getCompleteDigit(x, y - 1) + number + getCompleteDigit(x, y + 1);
        return number;
    }
}