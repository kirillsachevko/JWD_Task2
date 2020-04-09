package by.training.textparser.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class EntriesChecker {

    private static final Logger log = LoggerFactory.getLogger(EntriesChecker.class);

    public static List<String> getListOfPairsByEntriesNumber(Map<String, Integer> map) {
        log.debug("getListOfPairsByEntriesNumber is invoked");

        Map<String, Integer> resultMap = new TreeMap<>();
        List<Integer> values = new ArrayList<>(map.values());
        values = values.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        log.debug("List of values sorted and reversed");

        List<String> listOfPairsByNumberOfEntriesOfSymbol = new ArrayList<>();
        int maxEntries = values.get(0);

        for (Map.Entry<String, Integer> entry : map.entrySet()
        ) {
            if (maxEntries == entry.getValue()) {

                resultMap.put(entry.getKey(), maxEntries);
                log.debug("Entry with same value added to resultMap");
            } else {
                log.debug("Number of entries changed");
                resultMap.forEach((key, value)
                        -> listOfPairsByNumberOfEntriesOfSymbol.add("In word '" + key + "' " + value + " times"));
                log.debug("Result added to list");

                maxEntries = entry.getValue();
                resultMap.clear();
            }
        }

        resultMap.forEach((key, value)
                -> listOfPairsByNumberOfEntriesOfSymbol.add("In word '" + key + "' " + value + " times"));

        return listOfPairsByNumberOfEntriesOfSymbol;
    }
}
