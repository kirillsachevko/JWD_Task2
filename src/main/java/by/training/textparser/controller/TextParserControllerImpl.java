package by.training.textparser.controller;

import by.training.textparser.domain.TextComposite;
import by.training.textparser.services.TextParserServiceException;
import by.training.textparser.services.TextParserServices;
import by.training.textparser.services.impl.TextParserServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParserControllerImpl implements TextParserController {

    private static final Logger log = LoggerFactory.getLogger(TextParserControllerImpl.class);
    private final static TextParserController instance = new TextParserControllerImpl();

    private final TextParserServices parserServices = TextParserServicesImpl.getInstance();
    private TextComposite wholeText;

    private TextParserControllerImpl() {

    }

    public static TextParserController getInstance() {
        return instance;
    }

    @Override
    public void parseText() throws TextParserControllerException {
        log.debug("parseText is invoked");

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String path = bufferedReader.readLine();
            Pattern pattern = Pattern.compile("\\w:(\\\\.*)\\.(.*)");
            Matcher matcher = pattern.matcher(path);

            if (!matcher.matches()) {
                log.debug("Wrong path inserted: " + path);
                System.out.println("Wrong path format! Try again, please:");
                parseText();
                return;
            }

            wholeText = parserServices.parseText(path);
            log.trace(wholeText.toString());

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserControllerException("Error in reading text path");
        } catch (TextParserServiceException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserControllerException("Error in parsing text");
        }
    }

    @Override
    public String countTextElements() throws TextParserControllerException {
        log.debug("countTextElements is invoked");

        int numberOfParagraph;
        int numberOfSentences;
        int numberOfWordsAndSigns;
        int numberOfSymbols;

        try {
            numberOfParagraph = parserServices.countParagraphsInText(wholeText);
            numberOfSentences = parserServices.countSentencesInText(wholeText);
            numberOfWordsAndSigns = parserServices.countWordsAndSignsInText(wholeText);
            numberOfSymbols = parserServices.countSymbolsInText(wholeText);


        } catch (TextParserServiceException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserControllerException("Error in counting text elements");
        }

        return "Text contains from: \n\n" + numberOfParagraph + " paragraphs, \n"
                + numberOfSentences + " sentences, \n"
                + numberOfWordsAndSigns + " words and signs, "
                + numberOfSymbols + " symbols. \n";
    }


    @Override
    public void checkEntriesOfSymbolInText() throws TextParserControllerException {
        log.debug("checkEntriesOfSymbolInText is invoked");

        List<String> listOfWordsAndEntriesNumber;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String symbol = reader.readLine();
            Pattern pattern = Pattern.compile("[A-Za-zА-Яа-я]");
            Matcher matcher = pattern.matcher(symbol);
            if (!(matcher.matches())) {
                log.debug("Wrong symbol is inserted");
                System.out.println("Wrong symbol! Try insert symbol again and press 'Enter':");
                checkEntriesOfSymbolInText();
                return;
            }
            String text = parserServices.getStringOfTextElement(wholeText);
            listOfWordsAndEntriesNumber = parserServices
                    .sortWordsInTextByNumberOfSymbolEntries(symbol, text);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserControllerException("Error with reading symbol");
        }

        parserServices.printSortedListOfWordsAndEntriesNumber(listOfWordsAndEntriesNumber);
    }
}
