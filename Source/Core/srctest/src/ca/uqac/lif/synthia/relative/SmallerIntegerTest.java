package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmallerIntegerTest
{
	@Test
	public void superiorToMin()
	{
		int min = 0;
		int max = 101;
		int max2 = 51;
		RandomInteger random_int = new RandomInteger(min, max);
		RelativePicker relative_picker = random_int.getPicker(max2);

		Assertions.assertEquals(true,relative_picker.getClass().getSimpleName()
				.equals("RandomInteger"));

		RandomInteger smaller_random_int = (RandomInteger) relative_picker;


		for (int i = 0; i < 100; i++)
		{
			Assertions.assertTrue(smaller_random_int.pick() < max2);
		}

	}

	@Test
	public void inferiorAndEqualToMin()
	{
		int min = 25;
		int max = 101;
		int max2 = 12;
		RandomInteger random_int = new RandomInteger(min, max);

		Assertions.assertEquals(true, random_int.getPicker(max2).getClass().getSimpleName()
				.equals("NothingPicker"));

		Assertions.assertEquals(true, random_int.getPicker(min).getClass().getSimpleName()
				.equals("NothingPicker"));
	}
}
