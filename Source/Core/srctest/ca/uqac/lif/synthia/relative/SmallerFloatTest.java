package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmallerFloatTest
{
	@Test
	public void superiorToMin()
	{
		float min = 0;
		float max = 101;
		float max2 = 51;
		RandomFloat random_float = new RandomFloat(min, max);
		Shrinkable<Float> relative_picker = random_float.shrink(max2);

		Assertions.assertEquals(true,relative_picker.getClass().getSimpleName()
				.equals("RandomFloat"));

		RandomFloat smaller_random_float = (RandomFloat) relative_picker;


		for (int i = 0; i < 10000; i++)
		{
			Assertions.assertTrue(smaller_random_float.pick() < max2);
		}

	}

	@Test
	public void inferiorAndEqualToMinAndNaN()
	{
		float min = 25;
		float max = 101;
		float max2 = 12;
		RandomFloat random_float = new RandomFloat(min, max);

		Assertions.assertEquals(true, random_float.shrink(max2).getClass().getSimpleName()
				.equals("NothingPicker"));

		Assertions.assertEquals(true, random_float.shrink(min).getClass().getSimpleName()
				.equals("NothingPicker"));

		Assertions.assertEquals(true, random_float.shrink(Float.NaN).getClass()
				.getSimpleName().equals("NothingPicker"));
	}
}
