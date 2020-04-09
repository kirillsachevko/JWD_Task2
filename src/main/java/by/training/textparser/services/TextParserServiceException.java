package by.training.textparser.services;

public class TextParserServiceException extends Exception {

    public TextParserServiceException() {
    }

    public TextParserServiceException(String message) {
        super(message);
    }

    public TextParserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextParserServiceException(Throwable cause) {
        super(cause);
    }
}
