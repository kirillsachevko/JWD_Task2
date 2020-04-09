package by.training.textparser.dao.impl;

import by.training.textparser.dao.TextParserDao;
import by.training.textparser.dao.TextParserDaoException;
import by.training.textparser.dao.util.Expression;
import by.training.textparser.dao.util.TextSplitter;
import by.training.textparser.domain.TextPart;
import by.training.textparser.domain.TextComposite;
import by.training.textparser.domain.TextElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParserDaoImpl implements TextParserDao {

    private static final Logger log = LoggerFactory.getLogger(TextParserDaoImpl.class);
    private static final TextParserDao instance = new TextParserDaoImpl();

    private TextParserDaoImpl() {

    }

    public static TextParserDao getInstance() {
        return instance;
    }

    @Override
    public String textInitialization(String path) throws TextParserDaoException {
        log.debug("textInitialization is invoked");

        String wholeText;

        try (FileInputStream reader = new FileInputStream(path)) {
            byte[] str = new byte[reader.available()];
            reader.read(str);
            wholeText = new String(str);
            log.debug("Text initialization complete");

        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserDaoException("File not found");
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new TextParserDaoException("Error in reading message");
        }

        return wholeText;
    }

    @Override
    public TextComposite parseTextToElements(String text) {
        log.debug("parseTextToElements is invoked");

        TextComposite wholeText = new TextComposite();
        wholeText = parseToParagraph(wholeText, text);
        log.trace(wholeText.toString());

        return wholeText;
    }

    private TextComposite parseToParagraph(TextComposite wholeText, String text) {
        log.debug("parseToParagraph is invoked");

        TextComposite paragraphList;
        TextElement paragraphElement;
        String paragraph;
        Matcher paragraphMatcher = TextSplitter.matchByPattern(Expression.REGEX_PARAGRAPH_WITH_LISTING.getExpression(), text);

        while (paragraphMatcher.find()) {
            paragraph = paragraphMatcher.group();
            paragraphList = new TextComposite();

            if (Pattern.matches(Expression.REGEX_LISTING.getExpression(), paragraph)) {
                paragraphElement = new TextPart(paragraph);
                wholeText.addTextElement(paragraphElement);
                log.trace("Find listing" + paragraphElement);
            } else {
                log.trace("No listing in text");
                wholeText.addTextElement(parseToSentences(paragraphList, paragraph));
                log.trace(paragraphList.toString());
            }
        }

        return wholeText;
    }


    private TextComposite parseToSentences(TextComposite paragraphList, String paragraph) {
        log.debug("parseToSentences is invoked");

        TextComposite sentenceList;
        String sentence;

        Matcher matcher = TextSplitter.matchByPattern(Expression.REGEX_SENTENCE.getExpression(), paragraph);

        while (matcher.find()) {
            sentence = matcher.group();
            sentenceList = new TextComposite();
            sentenceList = parseToWordsAndSigns(sentenceList, sentence);
            paragraphList.addTextElement(sentenceList);
            log.trace(sentenceList.toString());
        }

        return paragraphList;
    }

    public TextComposite parseToWordsAndSigns(TextComposite sentenceList, String sentence) {
        log.debug("parseToWordsAndSigns is invoked");

        TextComposite wordsAndSignsList;
        String wordWithPunct;

        Matcher matcher = TextSplitter.matchByPattern(Expression.REGEX_WORD_AND_SIGN.getExpression(), sentence);

        while (matcher.find()) {
            wordWithPunct = matcher.group();
            wordsAndSignsList = new TextComposite();
            wordsAndSignsList = parseToSymbols(wordsAndSignsList, wordWithPunct);
            sentenceList.addTextElement(wordsAndSignsList);
            log.trace(wordsAndSignsList.toString());
        }

        return sentenceList;
    }


    public TextComposite parseToSymbols(TextComposite wordsAndSignsList, String wordWithPunct) {
        log.debug("parseToSymbols is invoked");

        TextElement symbolList;
        String symbol;

        Matcher matcher = TextSplitter.matchByPattern(Expression.REGEX_SYMBOL.getExpression(), wordWithPunct);

        while (matcher.find()) {
            symbol = matcher.group();
            symbolList = new TextPart(symbol);
            wordsAndSignsList.addTextElement(symbolList);
        }
        return wordsAndSignsList;
    }

    @Override
    public void printText(TextComposite component) {
        log.debug("printText is invoked");
        component.printElement();
    }

    @Override
    public List<TextElement> getListOfParagraphs(TextComposite component) throws TextParserDaoException {
        log.debug("getListOfParagraphs is invoked");

        List<TextElement> listOfParagraphs;
        if (component != null) {
            listOfParagraphs = component.getComponentsList();
        } else {
            log.error("object is null");
            throw new TextParserDaoException("object is null");
        }
        return listOfParagraphs;
    }

    @Override
    public List<TextElement> getListOfSentences(TextComposite component) throws TextParserDaoException {
        log.debug("getListOfSentences is invoked");

        List<TextElement> listOfParagraphs = getListOfParagraphs(component);
        List<TextElement> listOfSentences = new ArrayList<>();
        listOfParagraphs.stream()
                .filter(paragraph -> !(paragraph instanceof TextPart))
                .forEach(paragraph -> {
                    log.trace("Paragraph is not listing");
                    listOfSentences.addAll(((TextComposite) paragraph).getComponentsList());
                });
        return listOfSentences;
    }

    @Override
    public List<TextElement> getListOfWordsAndSigns(TextComposite component) throws TextParserDaoException {
        log.debug("getListOfWordsAndSigns is invoked");

        List<TextElement> listOfSentences = getListOfSentences(component);
        List<TextElement> listOfWords = new ArrayList<>();
        listOfSentences.stream()
                .map(sentence -> ((TextComposite) sentence).getComponentsList())
                .forEach(listOfWords::addAll);
        return listOfWords;
    }

    public List<TextElement> getListOfSymbols(TextComposite component) throws TextParserDaoException {
        log.debug("getListOfSymbols is invoked");

        List<TextElement> listOfWordAndSigns = getListOfWordsAndSigns(component);
        List<TextElement> listOfSymbols = new ArrayList<>();
        listOfWordAndSigns.stream()
                .map(sentence -> ((TextComposite) sentence).getComponentsList())
                .forEach(listOfSymbols::addAll);
        return listOfSymbols;
    }

    @Override
    public List<String> getStringListOfWord(String text) {
        log.debug("getStringListOfWord is invoked");

        List<String> wordList = new ArrayList<>();

        Pattern p = Pattern.compile(Expression.REGEX_WORD.getExpression());
        Matcher m = p.matcher(text);
        while (m.find()) {
            wordList.add(m.group());
        }

        return wordList;
    }

    @Override
    public String getStringOfElement(TextComposite composite) {
        log.debug("getStringOfElement is invoked");

        return composite.getStringOfElement();
    }


}
