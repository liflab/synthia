package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.SeedsForRandomGenerationTests;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SmallerFloatTest
{
	@Test
	public void superiorToMin()
	{
		float min = 0;
		float max = 101;
		float max2 = 51;
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();

		for (int i = 0; i < 10; i++)
		{
			RandomFloat random_float = new RandomFloat(min, max);
			random_float.setSeed(int_list.get(i));
			RelativePicker relative_picker = random_float.getPicker(max2);

			Assert.assertEquals(true,relative_picker.getClass().getSimpleName()
					.equals("RandomFloat"));

			RandomFloat smaller_random_float = (RandomFloat) relative_picker;


			for (int j = 0; j < 100; j++)
			{
				Assert.assertTrue(smaller_random_float.pick() < max2);
			}
		}


	}

	@Test
	public void inferiorAndEqualToMinAndNaN()
	{
		float min = 25;
		float max = 101;
		float max2 = 12;
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();

		for (int i = 0; i < 10; i++)
		{
			RandomFloat random_float = new RandomFloat(min, max);
			random_float.setSeed(int_list.get(i));
			Assert.assertEquals(true, random_float.getPicker(max2).getClass().getSimpleName()
					.equals("NothingPicker"));

			Assert.assertEquals(true, random_float.getPicker(min).getClass().getSimpleName()
					.equals("NothingPicker"));

			Assert.assertEquals(true, random_float.getPicker(Float.NaN).getClass()
					.getSimpleName().equals("NothingPicker"));
		}

	}
}
