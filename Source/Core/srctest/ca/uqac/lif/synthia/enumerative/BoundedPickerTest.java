package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoundedPickerTest
{
	@Test public void sameValuesSameSeed()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 10000);
			RandomInteger random_integer1 = new RandomInteger(1, 1000);
			RandomInteger random_integer2 = new RandomInteger(1, 1000);
			RandomFloat random_float = new RandomFloat();
			RandomFloat random_float1 = new RandomFloat();
			int randomSeed = random_integer.pick();
			random_integer1.setSeed(randomSeed);
			random_integer2.setSeed(randomSeed);
			random_float.setSeed(randomSeed);
			random_float1.setSeed(randomSeed);
			Bound bounded_picker = new Bound(random_float, random_integer1.pick());
			Bound bounded_picker1 = new Bound(random_float1, random_integer2.pick());
			while (!bounded_picker.isDone())
			{
				Assertions.assertEquals(bounded_picker.pick(), bounded_picker1.pick());
			}
		}
	}

	@Test
  public void duplicateWithState()
	{
		int min = 50;
		int max = 10000;
		for (int i = 0; i < 1000; i++)
		{
			RandomInteger random_integer = new RandomInteger(min, max);
			RandomFloat random_float = new RandomFloat();
			Bound bounded_picker = new Bound(random_float, random_integer.pick());

			for (int j = 0; j < (min - 1) ; j++)
			{
				bounded_picker.pick();
			}

			Bound bounded_picker_copy = bounded_picker.duplicate(true);

			while (!bounded_picker.isDone())
			{
				Assertions.assertEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			}

		}
	}

	@Test
  public void duplicateWithoutState()
	{
		int min = 50;
		int max = 10000;
		for (int i = 0; i < max; i++)
		{
			RandomInteger random_integer = new RandomInteger(min, max);
			RandomFloat random_float = new RandomFloat();
			Bound bounded_picker = new Bound(random_float, random_integer.pick());

			for (int j = 0; j < (min - 1); j++)
			{
				bounded_picker.pick();
			}

			Bound bounded_picker_copy = bounded_picker.duplicate(false);

			while (bounded_picker.isDone())
			{
				Assertions.assertNotEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			}


			bounded_picker.reset();
			bounded_picker_copy.reset();

			while (!bounded_picker.isDone())
			{
				Assertions.assertEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			}
		}
	}

	@Test
	public void isDone()
	{
		Bound bounded_picker = new Bound(new RandomInteger(0, 100), 100);

		for (int i = 0; i < 100; i++)
		{
			Assertions.assertEquals(false, bounded_picker.isDone());
			bounded_picker.pick();
		}

		Assertions.assertEquals(true, bounded_picker.isDone());

	}

	@Test
	public void noMoreElementException()
	{
		Bound bounded_picker = new Bound(new RandomInteger(0, 100), 100);

		for (int i = 0; i < 100; i++)
		{
			Assertions.assertEquals(false, bounded_picker.isDone());
			bounded_picker.pick();
		}
		assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override public void execute() throws Throwable
			{
				bounded_picker.pick();
			}
		});
	}
}