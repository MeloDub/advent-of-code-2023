package it.melo.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, String> wordDigits = Stream.of(new String[][]{
                {"one", "1"},
                {"two", "2"},
                {"three", "3"},
                {"four", "4"},
                {"five", "5"},
                {"six", "6"},
                {"seven", "7"},
                {"eight", "8"},
                {"nine", "9"}
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classloader.getResourceAsStream("input.txt");
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr);
             Stream<String> lines = br.lines()) {

            Pattern digitRegex = Pattern.compile("\\d|(?=(one|two|three|four|five|six|seven|eight|nine)).");
            AtomicInteger sum = new AtomicInteger();
            lines.forEach(line -> {
                Matcher digitMatcher = digitRegex.matcher(line);
                String firstDigit = null, lastDigit = null;
                while (digitMatcher.find()) {
                    if (firstDigit == null) {
                        firstDigit = digitMatcher.group(1) != null ? digitMatcher.group(1) : digitMatcher.group();
                        lastDigit = firstDigit;
                    } else {
                        lastDigit = digitMatcher.group(1) != null ? digitMatcher.group(1) : digitMatcher.group();
                    }
                }

                if (firstDigit != null && firstDigit.length() > 1) {
                    firstDigit = wordDigits.get(firstDigit);
                }
                if (lastDigit != null && lastDigit.length() > 1) {
                    lastDigit = wordDigits.get(lastDigit);
                }

                System.out.println(line + ": " + firstDigit + lastDigit);

                sum.addAndGet(Integer.parseInt(firstDigit + lastDigit));
            });

            System.out.println("Result is: " + sum);
        }
    }

}