package it.melo.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Scratchcard {

    int id;
    Set<String> winNumbers;
    Set<String> foundNumbers;
    int winners;
    List<Scratchcard> nextCards = new ArrayList<>();

    public Scratchcard(int id, Set<String> winNumbers, Set<String> foundNumbers) {
        this.id = id;
        this.winNumbers = winNumbers;
        this.foundNumbers = foundNumbers;
        this.winners = (int) this.winNumbers.stream()
                .filter(n -> this.foundNumbers.contains(n))
                .count();
    }

    public void addNext(List<Scratchcard> cards) {
        this.nextCards.addAll(cards);
    }

    public Stream<Scratchcard> flat() {
        return Stream.concat(Stream.of(this), this.nextCards.stream().flatMap(Scratchcard::flat));
    }

}
