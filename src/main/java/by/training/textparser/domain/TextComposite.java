package by.training.textparser.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextComposite implements TextElement {

    private List<TextElement> componentsList = new ArrayList<>();

    public List<TextElement> getComponentsList() {
        return componentsList;
    }

    @Override
    public void addTextElement(TextElement textElement) {
        componentsList.add(textElement);
    }

    @Override
    public void removeTextElement(TextElement textElement) {
        componentsList.remove(textElement);
    }

    @Override
    public TextElement getTextElement(int index) {
        return componentsList.get(index);
    }

    @Override
    public void printElement() {
        componentsList.forEach(TextElement::printElement);
    }

    @Override
    public String getStringOfElement() {
        String text = componentsList.stream()
                .map(TextElement::getStringOfElement).collect(Collectors.joining());

        return text;
    }

    @Override
    public String toString() {
        return "TextComposite{" +
                "componentsList=" + componentsList +
                '}';
    }
}
