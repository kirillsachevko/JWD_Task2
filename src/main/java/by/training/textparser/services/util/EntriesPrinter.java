package by.training.textparser.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EntriesPrinter {

    private static final Logger log = LoggerFactory.getLogger(EntriesPrinter.class);

    public static void printSortedList(List<String> listOfPairs) {
        log.debug("printSortedList is invoked");

        listOfPairs.forEach(System.out::println);
    }
}
