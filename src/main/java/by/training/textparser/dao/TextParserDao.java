package by.training.textparser.dao;

import by.training.textparser.domain.TextComposite;
import by.training.textparser.domain.TextElement;

import java.util.List;

public interface TextParserDao {

    String textInitialization(String path) throws TextParserDaoException;

    TextComposite parseTextToElements(String text);

    void printText(TextComposite component);

    List<TextElement> getListOfParagraphs(TextComposite component) throws TextParserDaoException;

    List<TextElement> getListOfSentences(TextComposite component) throws TextParserDaoException;

    List<TextElement> getListOfWordsAndSigns(TextComposite component) throws TextParserDaoException;

    List<TextElement> getListOfSymbols(TextComposite component) throws TextParserDaoException;

    List<String> getStringListOfWord(String text);

    String getStringOfElement(TextComposite composite);


}
