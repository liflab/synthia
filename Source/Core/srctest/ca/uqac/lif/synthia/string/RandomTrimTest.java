package ca.uqac.lif.synthia.string;

import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomTrimTest
{
	@Test
	public void property()
	{
		String s = "foobarbaz";
		RandomTrim random_trim = new RandomTrim(s);
		for (int i = 0; i < 10000; i++)
		{
			Assertions.assertTrue(random_trim.pick().length() <= s.length());
		}
	}

	@Test
	public void duplicateWithState()
	{
		String s = "foobarbaz";
		RandomTrim random_trim = new RandomTrim(s);
		for (int i = 0; i < 10000; i++)
		{
			random_trim.pick();
		}
		RandomTrim random_trim_copy = random_trim.duplicate(true);

		for (int i = 0; i < 10000; i++)
		{
			Assertions.assertEquals(random_trim.pick(), random_trim_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		RandomTrim random_trim = new RandomTrim("foobarbaz");
		for (int i = 0; i < 1000; i++)
		{
			random_trim.pick();
		}

		RandomTrim random_trim_copy = random_trim.duplicate(false);
		int counter = 0;

		for (int i = 0; i < 100000; i++)
		{
			if(random_trim.pick().equals(random_trim_copy.pick()))
			{
				counter ++;
			}
		}

		Assertions.assertTrue(counter < 100000);

		random_trim.reset();
		random_trim_copy.reset();

		for (int i = 0; i < 100000; i++)
		{
			Assertions.assertEquals(random_trim.pick(), random_trim_copy.pick());
		}
	}

	@Test
	public void shrink()
	{
		String s = "foobarbaz";
		RandomTrim random_trim = new RandomTrim(s);

		for (int i = 0; i < 10000; i++)
		{
			String picked_string = random_trim.pick();
			Shrinkable<String> relative_picker = random_trim.shrink(picked_string);

			if(picked_string.isEmpty())
			{
				Assertions.assertTrue(relative_picker.getClass().getSimpleName().equals("NothingPicker"));
			}
			else
			{
				Assertions.assertTrue(relative_picker.getClass().getSimpleName().equals("RandomTrim"));
			}

		}
	}
}
