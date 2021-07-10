package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomFloat;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OnceTest
{
	//Returns AssertionFailedError if NoMoreElementException is not thrown.
	public void noMoreExceptionThrow(Once once)
	{
		NoMoreElementException exception = assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override
			public void execute() throws Throwable
			{
				once.pick();
			}
		});
	}

	@Test
	public void noMoreExceptionTest()
	{
		RandomFloat randomFloat = new RandomFloat();
		Once once = new Once(randomFloat);
		once.pick();
		noMoreExceptionThrow(once);
	}

	@Test
	public void duplicateWithState()
	{
		RandomFloat randomFloat = new RandomFloat();
		Once once = new Once(randomFloat);
		once.pick();
		Once once_copy = once.duplicate(true);
		noMoreExceptionThrow(once);
		noMoreExceptionThrow(once_copy);
	}

	@Test
	public void duplicateWhitoutState()
	{
		RandomFloat randomFloat = new RandomFloat();
		Once once = new Once(randomFloat);
		float original_value = (float) once.pick();
		Once once_copy = once.duplicate(false);
		float copy_value = (float) once_copy.pick();
		Assertions.assertEquals(original_value, copy_value);
		noMoreExceptionThrow(once);
		noMoreExceptionThrow(once_copy);
	}
}
