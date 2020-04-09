import by.training.textparser.dao.TextParserDao;
import by.training.textparser.dao.TextParserDaoException;
import by.training.textparser.dao.impl.TextParserDaoImpl;
import by.training.textparser.domain.TextComposite;
import by.training.textparser.domain.TextElement;
import by.training.textparser.services.TextParserServices;
import by.training.textparser.services.impl.TextParserServicesImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTextTest {

    @Test
    public void shouldInitializeTextAndReturnString() throws TextParserDaoException {
        TextParserDao textParserDao = TextParserDaoImpl.getInstance();
        String  wholeText = textParserDao.textInitialization("C:\\Users\\ГУФПП\\IdeaProjects\\JWD Task2\\src\\test\\resources\\testeample.txt");

        String expect = "Архитектурный шаблон - это общее и повторяющееся решение часто возникающей проблемы архитектуры приложений в пределах заданного контекста. Архитектурные шаблоны схожи с шаблонами программного дизайна, однако имеют более широкий охват.\r\n" ;
       Assert.assertEquals(expect, wholeText);
    }

    @Test
    public void shouldConfirmParseOfText() throws TextParserDaoException {
        TextParserDao textParserDao = TextParserDaoImpl.getInstance();
        String wholeText = textParserDao.textInitialization("C:\\Users\\ГУФПП\\IdeaProjects\\JWD Task2\\src\\main\\resources\\example.txt");
        boolean result = textParserDao.parseTextToElements(wholeText) != null;

        Assert.assertTrue(result);
    }

    @Test
    public void shouldParseTextAndReturnNumberOfParagraphs() throws TextParserDaoException {
        TextParserDao textParserDao = TextParserDaoImpl.getInstance();
        String wholeText = textParserDao.textInitialization("C:\\Users\\ГУФПП\\IdeaProjects\\JWD Task2\\src\\main\\resources\\example.txt");
        TextComposite parsingText = textParserDao.parseTextToElements(wholeText);
        long result = parsingText.getComponentsList().size();

        Assert.assertEquals(23,result);
    }

    @Test
    public void shouldReturnSentenceFromTextComposite() throws TextParserDaoException {
        TextParserDao textParserDao = TextParserDaoImpl.getInstance();
        String wholeText = textParserDao.textInitialization("C:\\Users\\ГУФПП\\IdeaProjects\\JWD Task2\\src\\main\\resources\\example.txt");
        TextComposite parsingText = textParserDao.parseTextToElements(wholeText);
        List<TextElement> sentenceList = textParserDao.getListOfSentences(parsingText);
        TextComposite sentence = (TextComposite) sentenceList.get(2);
        String result = textParserDao.getStringOfElement(sentence);

        Assert.assertEquals("Архитектурные шаблоны схожи с шаблонами программного дизайна, однако имеют более широкий охват.\r\n", result);

    }

    @Test
    public void shouldReturnWordFromText() throws TextParserDaoException {
        TextParserDao textParserDao = TextParserDaoImpl.getInstance();
        String wholeText = textParserDao.textInitialization("C:\\Users\\ГУФПП\\IdeaProjects\\JWD Task2\\src\\main\\resources\\example.txt");
        List<String> wordList = textParserDao.getStringListOfWord(wholeText);
        String result = wordList.get(1) + ", " + wordList.get(12) + ", " + wordList.get(22);

        Assert.assertEquals("материалам, проблемы, с", result);
    }

    @Test
    public void shouldReturnNumberOfWordsInText() throws TextParserDaoException {
        TextParserDao textParserDao = TextParserDaoImpl.getInstance();
        String wholeText = textParserDao.textInitialization("C:\\Users\\ГУФПП\\IdeaProjects\\JWD Task2\\src\\main\\resources\\example.txt");
        long result = textParserDao.getStringListOfWord(wholeText).size();

        Assert.assertEquals(150, result);
    }

    @Test
    public void shouldReturnNumberOfUniqueWordsInText() throws TextParserDaoException {
        TextParserDao textParserDao = TextParserDaoImpl.getInstance();
        String wholeText = textParserDao.textInitialization("C:\\Users\\ГУФПП\\IdeaProjects\\JWD Task2\\src\\main\\resources\\example.txt");
        TextComposite parsingText = textParserDao.parseTextToElements(wholeText);
        String text = parsingText.getStringOfElement();
        List<String> wordlist = textParserDao.getStringListOfWord(text);

        Map<String, Integer> mapOfSymbolEntries = new HashMap<>();
        Pattern pattern = Pattern.compile("а", Pattern.CASE_INSENSITIVE);
        Collections.sort(wordlist);
        for (String word: wordlist
        ) {
            Matcher matcher = pattern.matcher(word);
            int counter = 0;
            while(matcher.find()){
                counter++;
            }
            mapOfSymbolEntries.put(word, counter);
        }

        long result = mapOfSymbolEntries.size();

        Assert.assertEquals(120, result);
    }

    @Test
    public void shouldReturnSortedListOfWordsWithEntriesNumberOfSymbol() throws TextParserDaoException {
        TextParserDao textParserDao = TextParserDaoImpl.getInstance();
        TextParserServices textParserServices = TextParserServicesImpl.getInstance();
        String wholeText = textParserDao.textInitialization("C:\\Users\\ГУФПП\\IdeaProjects\\JWD Task2\\src\\main\\resources\\example.txt");
        TextComposite parsingText = textParserDao.parseTextToElements(wholeText);
        String text = parsingText.getStringOfElement();
        String symbol = "п";

        String result = textParserServices.sortWordsInTextByNumberOfSymbolEntries(symbol, text).get(5)
                + ", " + textParserServices.sortWordsInTextByNumberOfSymbolEntries(symbol, text).get(10)
                + ", " + textParserServices.sortWordsInTextByNumberOfSymbolEntries(symbol, text).get(15)
                + ", " + textParserServices.sortWordsInTextByNumberOfSymbolEntries(symbol, text).get(25);


        Assert.assertEquals("In word 'десктопные' 1 times, In word 'плюсы' 1 times, In word 'пределах' 1 times, " +
                "In word 'commerce' 0 times", result);
    }

}
