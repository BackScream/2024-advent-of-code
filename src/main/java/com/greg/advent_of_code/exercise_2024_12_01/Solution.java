package com.greg.advent_of_code.exercise_2024_12_01;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Solution {

    private static final String INPUT_FILE = "/exercise_2024_12_01/input.txt";

    /*------------------------------------------------------------------------------------------------------------------
    * Core
    ------------------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {
        Path filePath = Optional.of(INPUT_FILE)
                .map(Solution.class::getResource)
                .map(URL::getPath)
                .map(Path::of)
                .orElseThrow(IllegalStateException::new);

        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.map(Solution::splitLine).forEach(pair -> {
                Optional.of(pair).map(Pair::left).map(Integer::parseInt).ifPresent(leftList::add);
                Optional.of(pair).map(Pair::right).map(Integer::parseInt).ifPresent(rightList::add);
            });
        }

        List<Integer> sortedLeftList = leftList.stream().sorted().toList();
        List<Integer> sortedRightList = rightList.stream().sorted().toList();

        Integer solution = calculateSolution(sortedLeftList, sortedRightList);
        System.out.println(solution);

        Integer similarityScore = calculateSimilarityScore(sortedLeftList, sortedRightList);
        System.out.println(similarityScore);
    }

    /*------------------------------------------------------------------------------------------------------------------
    * Utils
    ------------------------------------------------------------------------------------------------------------------*/
    private static Pair splitLine(String line) {
        return Optional.of(line)
                .map(l -> l.split(" {3}"))
                .map(splitLine ->
                        Pair.builder().left(splitLine[0]).right(splitLine[1]).build())
                .orElseThrow(IllegalStateException::new);
    }

    /*------------------------------------------------------------------------------------------------------------------
    * Logic
    ------------------------------------------------------------------------------------------------------------------*/
    private static Integer calculateSolution(List<Integer> left, List<Integer> right) {
        int sum = 0;
        for (int i = 0; i < left.size(); i++) {
            int pairDifference = left.get(i) - right.get(i);
            sum += Math.abs(pairDifference);
        }
        return sum;
    }

    private static Integer calculateSimilarityScore(List<Integer> left, List<Integer> right) {
        int sum = 0;
        int startingIndex = 0;

        for (Integer leftValue : left) {
            int occurrences = 0;
            int j = startingIndex;
            while (j < right.size()) {
                if (leftValue.equals(right.get(j))) {
                    occurrences++;
                } else if (leftValue < right.get(j)) {
                    startingIndex = j;
                    break;
                }
                j++;
            }
            sum += leftValue * occurrences;
        }

        return sum;
    }
}
