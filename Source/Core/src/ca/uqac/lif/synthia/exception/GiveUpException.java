package ca.uqac.lif.synthia.exception;

import ca.uqac.lif.synthia.relative.PickIf;

/**
 * An exception to throw to prevent the pick method of a {@link PickIf} picker
 * from falling into an infinite loop.
 */
public class GiveUpException extends PickerException
{
	private static final String m_message = "Maximum number of iterations reached. This exception was"
			+ " throwed to prevent a PickIf picker from falling into an infinite loop.";

	public GiveUpException() { super(m_message); }

	public GiveUpException(String message) { super(message); }

	public GiveUpException(Throwable t) { super(t); }
}
