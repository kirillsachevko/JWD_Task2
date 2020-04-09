package by.training.textparser.dao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSplitter {

    private  static final Logger log = LoggerFactory.getLogger(TextSplitter.class);

    public static Matcher matchByPattern(String regex, String textComponent) {
        log.trace("matchByPattern is invoked");

        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(textComponent);
    }

}
