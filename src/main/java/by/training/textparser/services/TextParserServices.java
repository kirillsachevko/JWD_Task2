package by.training.textparser.services;

import by.training.textparser.domain.TextComposite;

import java.util.List;

public interface TextParserServices {
    TextComposite parseText(String source) throws TextParserServiceException;

    void printWholeText(TextComposite text);

    int countParagraphsInText(TextComposite textComposite) throws TextParserServiceException;

    int countSentencesInText(TextComposite textComposite) throws TextParserServiceException;

    int countWordsAndSignsInText(TextComposite textComposite) throws TextParserServiceException;

    int countSymbolsInText(TextComposite textComposite) throws TextParserServiceException;

    List<String> sortWordsInTextByNumberOfSymbolEntries(String symbol, String text);

    String getStringOfTextElement(TextComposite textComposite);

    void printSortedListOfWordsAndEntriesNumber(List<String> listOfWordsAndEntriesNumber);
}
