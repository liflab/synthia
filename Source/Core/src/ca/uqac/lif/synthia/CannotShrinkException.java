package ca.uqac.lif.synthia;

/**
 * An exception to throw when a picker can't pick an other element.
 * @ingroup API
 */
public class CannotShrinkException extends PickerException
{
	/**
	 * Dummy UID.
	 */
	private static final long serialVersionUID = 1L;

	private static final String m_message = "This picker is not shrinkable";

	public CannotShrinkException(Picker<?> picker) 
	{
		super(m_message + ": " + picker.getClass().getSimpleName());
	}

	public CannotShrinkException(String message)
	{
		super(message);
	}

	public CannotShrinkException(Throwable t)
	{
		super(t);
	}
}
