package by.training.textparser.controller;

public interface TextParserController {

    void parseText() throws TextParserControllerException;

    String countTextElements() throws TextParserControllerException;

    void checkEntriesOfSymbolInText() throws TextParserControllerException;
}
