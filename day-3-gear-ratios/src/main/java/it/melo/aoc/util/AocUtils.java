package it.melo.aoc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class AocUtils {

    public static char[][] readInput() throws IOException {
        var classloader = Thread.currentThread().getContextClassLoader();
        try (var is = classloader.getResourceAsStream("input.txt");
             var isr = new InputStreamReader(is);
             var br = new BufferedReader(isr);
             var lines = br.lines()) {

            return lines.map(String::toCharArray).toArray(char[][]::new);
        }
    }

}
