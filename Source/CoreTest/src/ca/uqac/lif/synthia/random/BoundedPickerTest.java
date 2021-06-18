package ca.uqac.lif.synthia.random;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

// test with float random float
//useful?
public class BoundedPickerTest
{
	@Test public void sameValuesSameSeed()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 1000000);
			RandomInteger random_integer1 = new RandomInteger(0, 1000);
			RandomInteger random_integer2 = new RandomInteger(0, 1000);
			RandomFloat random_float = new RandomFloat();
			RandomFloat random_float1 = new RandomFloat();
			int randomSeed = random_integer.pick();
			random_integer1.setSeed(randomSeed);
			random_integer2.setSeed(randomSeed);
			random_float.setSeed(randomSeed);
			random_float1.setSeed(randomSeed);
			BoundedPicker bounded_picker = new BoundedPicker(random_float, random_integer1, 10000);
			BoundedPicker bounded_picker1 = new BoundedPicker(random_float1, random_integer2, 10000);
			for (int j = 0; j < 10000; j++)
			{
				Assertions.assertEquals(bounded_picker.pick(), bounded_picker1.pick());
			}
		}
	}

	@Test
  public void duplicateWithState()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 1000);
			RandomFloat random_float = new RandomFloat();
			BoundedPicker bounded_picker = new BoundedPicker(random_float, random_integer, 10000);
			for (int j = 0; j < 10000; j++)
			{
				bounded_picker.pick();
			}
			BoundedPicker bounded_picker_copy = bounded_picker.duplicate(true);
			Assertions.assertEquals(bounded_picker.pick(), bounded_picker_copy.pick());
		}
	}

	@Test
  public void duplicateWithoutState()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 1000);
			RandomFloat random_float = new RandomFloat();
			BoundedPicker bounded_picker = new BoundedPicker(random_float, random_integer, 10000);
			for (int j = 0; j < 10000; j++)
			{
				bounded_picker.pick();
			}
			BoundedPicker bounded_picker_copy = bounded_picker.duplicate(false);
			Assertions.assertNotEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			bounded_picker.reset();
			bounded_picker_copy.reset();
			Assertions.assertEquals(bounded_picker.pick(), bounded_picker_copy.pick());
		}
	}
}