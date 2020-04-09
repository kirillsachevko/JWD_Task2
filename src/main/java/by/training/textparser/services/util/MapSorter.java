package by.training.textparser.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

public class MapSorter {

    private static final Logger log = LoggerFactory.getLogger(MapSorter.class);

    public static Map<String, Integer> sortMapByValue(Map<String, Integer> map) {
        log.debug("sortMapByValue is invoked");

        Map<String, Integer> resultMap = new LinkedHashMap<>();

        Stream<Map.Entry<String, Integer>> mapStream = map.entrySet().stream();

        mapStream.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(ent -> resultMap.put(ent.getKey(), ent.getValue()));

        log.debug("Map sorted by value");

        return resultMap;
    }
}
