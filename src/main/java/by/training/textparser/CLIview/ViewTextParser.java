package by.training.textparser.CLIview;

import by.training.textparser.controller.TextParserController;
import by.training.textparser.controller.TextParserControllerException;
import by.training.textparser.controller.TextParserControllerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewTextParser {

    private static final Logger log = LoggerFactory.getLogger(ViewTextParser.class);
    private static final TextParserController controller = TextParserControllerImpl.getInstance();

    public static void main(String[] args) throws TextParserControllerException {
        log.debug("Application started");

        System.out.println("Welcome to TextParser application! \n"
                + "to start parsing, please, insert the absolute path of your text and submit with 'Enter':");

        log.debug("Source path inserted");

        controller.parseText();

        System.out.println("Your text has been parsed successfully! \n");

        log.debug("Text is parsed");

        System.out.println(controller.countTextElements());


        System.out.println("For check entries of symbol, please insert it and submit with 'Enter':");

        log.debug("Symbol is inserted");

        controller.checkEntriesOfSymbolInText();

        System.out.println("\nSee you soon!");

        log.debug("Application shutdown");

    }
}
