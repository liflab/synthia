package ca.uqac.lif.synthia;

public class NoMoreElementException extends PickerException{
    private static final String message = "No more element can be picked.";
    public NoMoreElementException() {
        super(message);
    }
}
