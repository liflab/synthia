package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.random.SeedsForRandomGenerationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SmallerIntegerTest
{
	@Test
	public void superiorToMin()
	{
		int min = 0;
		int max = 101;
		int max2 = 51;
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();


		for (int i = 0; i <10 ; i++)
		{
			RandomInteger random_int = new RandomInteger(min, max);
			random_int.setSeed(int_list.get(i));
			RelativePicker relative_picker = random_int.getPicker(max2);

			Assertions.assertEquals(true,relative_picker.getClass().getSimpleName()
					.equals("RandomInteger"));

			RandomInteger smaller_random_int = (RandomInteger) relative_picker;


			for (int j = 0; j < 100; j++)
			{
				Assertions.assertTrue(smaller_random_int.pick() < max2);
			}
		}


	}

	@Test
	public void inferiorAndEqualToMin()
	{
		int min = 25;
		int max = 101;
		int max2 = 12;
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomInteger random_int = new RandomInteger(min, max);
			random_int.setSeed(int_list.get(i));

			Assertions.assertEquals(true, random_int.getPicker(max2).getClass().getSimpleName()
					.equals("NothingPicker"));

			Assertions.assertEquals(true, random_int.getPicker(min).getClass().getSimpleName()
					.equals("NothingPicker"));
		}


	}
}
