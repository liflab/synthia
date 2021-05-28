package ca.uqac.lif.synthia;

/**
 * An exception to throw when a picker can't pick an other element.
 */
public class NoMoreElementException extends PickerException{
    private static final String m_message = "No more element can be picked.";

    public NoMoreElementException() {
        super(m_message);
    }

    public NoMoreElementException(String message) {
        super(message);
    }

    public NoMoreElementException(Throwable t) {
        super(t);
    }
}
