package by.training.textparser.services.impl;

import by.training.textparser.dao.TextParserDao;
import by.training.textparser.dao.TextParserDaoException;
import by.training.textparser.dao.impl.TextParserDaoImpl;
import by.training.textparser.domain.TextComposite;
import by.training.textparser.services.TextParserServiceException;
import by.training.textparser.services.TextParserServices;
import by.training.textparser.services.util.EntriesChecker;
import by.training.textparser.services.util.EntriesPrinter;
import by.training.textparser.services.util.MapSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParserServicesImpl implements TextParserServices {

    private static final Logger log = LoggerFactory.getLogger(TextParserServicesImpl.class);
    private static final TextParserServices instance = new TextParserServicesImpl();

    private final TextParserDao parserDao = TextParserDaoImpl.getInstance();

    private TextParserServicesImpl() {

    }

    public static TextParserServices getInstance() {
        return instance;
    }

    @Override
    public TextComposite parseText(String source) throws TextParserServiceException {
        log.debug("parseText is invoked");

        TextComposite wholeText;
        try {
            wholeText = parserDao.parseTextToElements(parserDao.textInitialization(source));
            log.trace(wholeText.toString());
        } catch (TextParserDaoException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserServiceException("Error with parsing the text");
        }

        return wholeText;
    }

    @Override
    public void printWholeText(TextComposite text) {
        log.debug("printWholeText is invoked");

        parserDao.printText(text);
    }

    @Override
    public int countSentencesInText(TextComposite textComposite) throws TextParserServiceException {
        log.debug("countSentencesInText is invoked");

        try {
            return parserDao.getListOfSentences(textComposite).size();
        } catch (TextParserDaoException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserServiceException("Error with counting sentences in text");
        }
    }

    @Override
    public int countWordsAndSignsInText(TextComposite textComposite) throws TextParserServiceException {
        log.debug("countWordsAndSignsInText is invoked");

        try {
            return parserDao.getListOfWordsAndSigns(textComposite).size();
        } catch (TextParserDaoException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserServiceException("Error with counting words and signs in text");
        }
    }

    @Override
    public int countSymbolsInText(TextComposite textComposite) throws TextParserServiceException {
        log.debug("countSymbolsInText is invoked");

        try {
            return parserDao.getListOfSymbols(textComposite).size();
        } catch (TextParserDaoException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserServiceException("Error with counting symbols in text");
        }
    }

    @Override
    public int countParagraphsInText(TextComposite textComposite) throws TextParserServiceException {
        log.debug("countParagraphsInText is invoked");

        try {
            return parserDao.getListOfParagraphs(textComposite).size();
        } catch (TextParserDaoException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserServiceException("Error with counting paragraphs in text");
        }
    }

    @Override
    public List<String> sortWordsInTextByNumberOfSymbolEntries(String symbol, String text) {
        log.debug("sortWordsInTextByNumberOfSymbolEntries is invoked");

        List<String> wordlist = parserDao.getStringListOfWord(text);

        Map<String, Integer> mapOfSymbolEntries = new HashMap<>();
        log.debug("mapOfSymbolEntries is created");
        Pattern pattern = Pattern.compile(symbol, Pattern.CASE_INSENSITIVE);

        Collections.sort(wordlist);
        log.debug("wordlist is sorted");

        for (String word : wordlist
        ) {
            Matcher matcher = pattern.matcher(word);
            int counter = 0;
            while (matcher.find()) {
                counter++;
                log.trace("Symbol entries is counted");
            }
            mapOfSymbolEntries.put(word, counter);
            log.debug("Word and number of symbol entries added to map");
        }

        mapOfSymbolEntries = MapSorter.sortMapByValue(mapOfSymbolEntries);
        log.debug("mapOfSymbolEntries is sorted by value");

        return EntriesChecker.getListOfPairsByEntriesNumber(mapOfSymbolEntries);
    }

    @Override
    public String getStringOfTextElement(TextComposite textComposite) {
        log.debug("getStringOfTextElement is invoked");

        return parserDao.getStringOfElement(textComposite);
    }

    @Override
    public void printSortedListOfWordsAndEntriesNumber(List<String> listOfWordsAndEntriesNumber) {
        log.debug("printSortedListOfWordsAndEntriesNumber is  invoked");

        EntriesPrinter.printSortedList(listOfWordsAndEntriesNumber);
    }
}
