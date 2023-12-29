package it.melo.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    private static final int maxRed = 12;
    private static final int maxGreen = 13;
    private static final int maxBlue = 14;

    public static void main(String[] args) throws IOException {
        var classloader = Thread.currentThread().getContextClassLoader();
        try (var is = classloader.getResourceAsStream("input.txt");
             var isr = new InputStreamReader(is);
             var br = new BufferedReader(isr);
             var lines = br.lines()) {

            // Convert stream to list to use it multiple times
            List<String> linesList = lines.toList();

            partOneResult(linesList.stream().map(Main::readGame));
            partTwoResult(linesList.stream().map(Main::readGame));
        }
    }

    private static int[][] readGame(String line) {
        var sets = new int[3][]; // { {reds}, {greens}, {blues} }

        var redRegex = Pattern.compile("(\\d+)\\sred");
        var greenRegex = Pattern.compile("(\\d+)\\sgreen");
        var blueRegex = Pattern.compile("(\\d+)\\sblue");

        var redMatcher = redRegex.matcher(line);
        var greenMatcher = greenRegex.matcher(line);
        var blueMatcher = blueRegex.matcher(line);

        sets[0] = redMatcher.results()
                .map(matchResult -> matchResult.group(1))
                .mapToInt(Integer::parseInt)
                .toArray();
        sets[1] = greenMatcher.results()
                .map(matchResult -> matchResult.group(1))
                .mapToInt(Integer::parseInt)
                .toArray();
        sets[2] = blueMatcher.results()
                .map(matchResult -> matchResult.group(1))
                .mapToInt(Integer::parseInt)
                .toArray();

        return sets;
    }

    private static void partOneResult(Stream<int[][]> games) {
        var sum = new AtomicInteger();
        var gameID = new AtomicInteger(1);
        games.forEach(game -> {
            for (int red : game[0]) {
                if (red > maxRed) {
                    gameID.getAndIncrement();
                    return;
                }
            }
            for (int green : game[1]) {
                if (green > maxGreen) {
                    gameID.getAndIncrement();
                    return;
                }
            }
            for (int blue : game[2]) {
                if (blue > maxBlue) {
                    gameID.getAndIncrement();
                    return;
                }
            }
            sum.addAndGet(gameID.get());
            gameID.getAndIncrement();
        });

        System.out.println("Part one result: " + sum.get());
    }

    private static void partTwoResult(Stream<int[][]> games) {
        int sum = games.map(sets -> {
                    // transforms a game into an array of { maxRed, maxGreen, maxBlue }
                    return new int[]{
                            Arrays.stream(sets[0]).max().orElse(0),
                            Arrays.stream(sets[1]).max().orElse(0),
                            Arrays.stream(sets[2]).max().orElse(0)
                    };
                })
                .map(minimums -> minimums[0] * minimums[1] * minimums[2])
                .mapToInt(n -> n)
                .sum();

        System.out.println("Part two result: " + sum);
    }

}