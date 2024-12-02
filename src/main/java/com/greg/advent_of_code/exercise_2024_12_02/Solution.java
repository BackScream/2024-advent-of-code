package com.greg.advent_of_code.exercise_2024_12_02;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    private static final String INPUT_FILE = "/exercise_2024_12_02/input.txt";

    /*------------------------------------------------------------------------------------------------------------------
    * Core
    ------------------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {
        Path filePath = Optional.of(INPUT_FILE)
                .map(Solution.class::getResource)
                .map(URL::getPath)
                .map(Path::of)
                .orElseThrow(IllegalStateException::new);

        try (Stream<String> lines = Files.lines(filePath)) {
            long safeRows = lines.map(Solution::splitLine)
                    .map(Solution::convertRowValuesToIntegers)
                    .filter(Solution::isReportSafeWithTolerance)
                    .count();

            System.out.println(safeRows);
        }
    }

    /*------------------------------------------------------------------------------------------------------------------
    * Utils
    ------------------------------------------------------------------------------------------------------------------*/
    private static List<String> splitLine(String line) {
        return Optional.of(line).map(l -> l.split(" ")).map(Arrays::asList).orElseThrow(IllegalStateException::new);
    }

    private static List<Integer> convertRowValuesToIntegers(List<String> row) {
        return row.stream().map(Integer::parseInt).toList();
    }

    /*------------------------------------------------------------------------------------------------------------------
    * Logic
    ------------------------------------------------------------------------------------------------------------------*/
    private static Boolean isReportSafe(List<Integer> levels) {
        int ascCounter = 0;
        int descCounter = 0;
        for (int i = 0; i < levels.size() - 1; i++) {
            int valuesDifference = levels.get(i) - levels.get(i + 1);
            if (valuesDifference >= 1 && valuesDifference <= 3) {
                descCounter++;
            } else if (valuesDifference <= -1 && valuesDifference >= -3) {
                ascCounter++;
            }
        }

        return ascCounter == levels.size() - 1 || descCounter == levels.size() - 1;
    }

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    private static Boolean isReportSafeWithTolerance(List<Integer> levels) {
        if (isReportSafe(levels)) {
            return true;
        } else {
            for (int i = 0; i < levels.size(); i++) {
                List<Integer> tmpList = new ArrayList<>(levels);
                tmpList.remove(i);
                if (isReportSafe(tmpList)) {
                    return true;
                }
            }
            return false;
        }
    }
}
