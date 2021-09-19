package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomFloat;
import org.junit.Assert;
import org.junit.Test;

public class SmallerFloatTest
{
	@Test
	public void superiorToMin()
	{
		float min = 0;
		float max = 101;
		float max2 = 51;
		RandomFloat random_float = new RandomFloat(min, max);
		RelativePicker relative_picker = random_float.getPicker(max2);

		Assert.assertEquals(true,relative_picker.getClass().getSimpleName()
				.equals("RandomFloat"));

		RandomFloat smaller_random_float = (RandomFloat) relative_picker;


		for (int i = 0; i < 100; i++)
		{
			Assert.assertTrue(smaller_random_float.pick() < max2);
		}

	}

	@Test
	public void inferiorAndEqualToMinAndNaN()
	{
		float min = 25;
		float max = 101;
		float max2 = 12;
		RandomFloat random_float = new RandomFloat(min, max);

		Assert.assertEquals(true, random_float.getPicker(max2).getClass().getSimpleName()
				.equals("NothingPicker"));

		Assert.assertEquals(true, random_float.getPicker(min).getClass().getSimpleName()
				.equals("NothingPicker"));

		Assert.assertEquals(true, random_float.getPicker(Float.NaN).getClass()
				.getSimpleName().equals("NothingPicker"));
	}
}
