package by.training.textparser.domain;

public class TextPart implements TextElement {

    private final String element;

    public TextPart(String element) {
        this.element = element;
    }

    @Override
    public void addTextElement(TextElement textElement) {
        textElement.addTextElement(this);
    }

    @Override
    public void removeTextElement(TextElement textElement) {
        textElement.removeTextElement(this);
    }

    @Override
    public String getStringOfElement() {

        return element;
    }

    @Override
    public TextElement getTextElement(int index) {
        return null;
    }

    @Override
    public void printElement() {
        System.out.print(this.element);
    }

    @Override
    public String toString() {
        return "TextComponent{" +
                "element='" + element + '\'' +
                '}';
    }
}
