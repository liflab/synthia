package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomTrim;
import org.junit.Assert;
import org.junit.Test;

public class RandomTrimTest
{
	@Test
	public void property()
	{
		String s = "foobarbaz";
		RandomTrim random_trim = new RandomTrim(s);
		for (int i = 0; i < 10000; i++)
		{
			Assert.assertTrue(random_trim.pick().length() <= s.length());
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
			Assert.assertEquals(random_trim.pick(), random_trim_copy.pick());
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

		Assert.assertTrue(counter < 100000);

		random_trim.reset();
		random_trim_copy.reset();

		for (int i = 0; i < 100000; i++)
		{
			Assert.assertEquals(random_trim.pick(), random_trim_copy.pick());
		}
	}

	@Test
	public void getPicker()
	{
		String s = "foobarbaz";
		RandomTrim random_trim = new RandomTrim(s);

		for (int i = 0; i < 10000; i++)
		{
			String picked_string = random_trim.pick();
			RelativePicker relative_picker = random_trim.getPicker(picked_string);

			if(picked_string.isEmpty())
			{
				Assert.assertTrue(relative_picker.getClass().getSimpleName().equals("NothingPicker"));
			}
			else
			{
				Assert.assertTrue(relative_picker.getClass().getSimpleName().equals("RandomTrim"));
			}

		}
	}
}
