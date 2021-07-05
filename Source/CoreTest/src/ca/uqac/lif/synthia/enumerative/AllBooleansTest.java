package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AllBooleansTest
{
	private void noMoreExceptionThrow(AllBooleans picker)
	{
		assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override public void execute() throws Throwable
			{
				picker.pick();
			}
		});
	}


	@Test
	public void noMoreException()
	{
		AllBooleans all_bool =  new AllBooleans();

		for (int i = 0; i <2; i++)
		{
			all_bool.pick();
		}

		noMoreExceptionThrow(all_bool);
	}

	@Test
	public void pick()
	{
		AllBooleans all_bool =  new AllBooleans();

		Assertions.assertEquals(false, all_bool.pick());
		Assertions.assertEquals(true, all_bool.pick());
	}

	@Test
	public void isDone()
	{
		AllBooleans all_bool =  new AllBooleans();

		for (int i = 0; i < 2; i++)
		{
			Assertions.assertEquals(false, all_bool.isDone());
			all_bool.pick();
		}

		Assertions.assertEquals(true, all_bool.isDone());
	}

	@Test
	public void duplicateWithState()
	{
		AllBooleans all_bool =  new AllBooleans();
		all_bool.pick();

		AllBooleans all_bool_copy = (AllBooleans) all_bool.duplicate(true);

		Assertions.assertEquals(all_bool.pick(), all_bool_copy.pick());
	}

	@Test
	public void duplicateWithoutState()
	{
		AllBooleans all_bool =  new AllBooleans();
		all_bool.pick();

		AllBooleans all_bool_copy = (AllBooleans) all_bool.duplicate(false);

		Assertions.assertNotEquals(all_bool.pick(), all_bool_copy.pick());

		all_bool.reset();
		all_bool_copy.reset();

		Assertions.assertEquals(all_bool.pick(), all_bool_copy.pick());
		Assertions.assertEquals(all_bool.pick(), all_bool_copy.pick());
	}
}
