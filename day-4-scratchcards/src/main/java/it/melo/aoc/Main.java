package it.melo.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        var lines = readInput();

        long start = System.currentTimeMillis();
        List<Scratchcard> cards = lines.stream().map(line -> {
            String[] idSplit = line.split(":")[0].split("\\s");
            int id = Integer.parseInt(idSplit[idSplit.length - 1]);

            String[] numbers = line.substring(line.indexOf(":") + 1).split("\\|");

            List<String> winNumbers = Arrays.stream(numbers[0].trim().split("\\s+"))
                    .map(String::trim)
                    .toList();
            List<String> foundNumbers = Arrays.stream(numbers[1].trim().split("\\s+"))
                    .map(String::trim)
                    .toList();

            return new Scratchcard(id, winNumbers, foundNumbers);
        }).toList();

        partOneResult(cards);

        long finish = System.currentTimeMillis();

        System.out.println("Execution time (ms): " + (finish - start));
    }

    private static List<String> readInput() throws IOException {
        var classloader = Thread.currentThread().getContextClassLoader();
        try (var is = classloader.getResourceAsStream("input.txt");
             var isr = new InputStreamReader(is);
             var br = new BufferedReader(isr);
             var lines = br.lines()) {

            return lines.collect(Collectors.toList());
        }
    }

    private static void partOneResult(List<Scratchcard> cards) {
        double result = cards.stream()
                .map(card -> card.winNumbers().stream()
                        .filter(n -> card.foundNumbers().contains(n))
                        .count())
                .filter(winners -> winners > 0)
                .mapToDouble(winners -> Math.pow(2, winners - 1))
                .sum();

        System.out.println("Part one result: " + result);
    }

}