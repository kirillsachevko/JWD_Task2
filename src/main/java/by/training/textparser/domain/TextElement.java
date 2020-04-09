package by.training.textparser.domain;

public interface TextElement {

    void addTextElement(TextElement textElement);

    void removeTextElement(TextElement textElement);

    TextElement getTextElement(int index);

    void printElement();

    String getStringOfElement();
}
