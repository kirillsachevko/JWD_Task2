package by.training.textparser.dao.util;

public enum Expression {

    REGEX_LISTING("Start listing([^\\t]+?)End listing(\\r\\n)*"),
    REGEX_PARAGRAPH_WITH_LISTING("Start listing([^\\t]+?)End listing(\\r\\n)*|\\s*(.+)\\s*"),
    REGEX_SENTENCE("(\\d+\\.)*\\s*([^(\\.|!|\\?)]+)(\\.|!|\\?|:;\\))*(\\r\\n)*"),
    REGEX_SYMBOL(".{1}|(\\r\\n)*"),
    REGEX_WORD_AND_SIGN("([^\\.,!\\?:;@\\)\\(\\s]+)\\s*|([\\.,!\\?:;@\\)\\(]{1})\\s*"),
    REGEX_WORD("([^\\.,\\-!\\?:;@\\s[0-9]\\)\\(]+)");

    public String getExpression() {
        return expression;
    }

    private final String expression;

    Expression(String expression) {
        this.expression = expression;
    }
}
