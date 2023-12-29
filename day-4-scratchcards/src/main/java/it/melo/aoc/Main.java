package it.melo.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {
        var lines = readInput();

        List<Scratchcard> cards = lines.stream().map(line -> {
            String[] idSplit = line.split(":")[0].split("\\s");
            int id = Integer.parseInt(idSplit[idSplit.length - 1]);

            String[] numbers = line.substring(line.indexOf(":") + 1).split("\\|");

            Set<String> winNumbers = Arrays.stream(numbers[0].trim().split("\\s+"))
                    .map(String::trim)
                    .collect(Collectors.toSet());
            Set<String> foundNumbers = Arrays.stream(numbers[1].trim().split("\\s+"))
                    .map(String::trim)
                    .collect(Collectors.toSet());

            return new Scratchcard(id, winNumbers, foundNumbers);
        }).toList();

        partOneResult(cards);
        partTwoResult(cards);
    }

    private static List<String> readInput() throws IOException {
        var classloader = Thread.currentThread().getContextClassLoader();
        try (var is = classloader.getResourceAsStream("input.txt");
             var isr = new InputStreamReader(is);
             var br = new BufferedReader(isr);
             var lines = br.lines()) {

            return lines.toList();
        }
    }

    private static void partOneResult(List<Scratchcard> cards) {
        double result = cards.stream()
                .filter(card -> card.winners > 0)
                .mapToDouble(card -> Math.pow(2, card.winners - 1.0))
                .sum();

        System.out.println("Part one result: " + result);
    }

    private static void partTwoResult(List<Scratchcard> cards) {
        long result = IntStream.iterate(cards.size() - 1, i -> i >= 0, i -> i - 1)
                .mapToObj(cards::get)
                .peek(card -> card.addNext(cards.subList(card.id, card.id + card.winners)))
                .flatMap(Scratchcard::flat)
                .count();

        System.out.println("Part two result: " + result);

    }

}