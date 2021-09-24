package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.exception.GiveUpException;
import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.random.RandomSubString;
import ca.uqac.lif.synthia.random.SeedsForRandomGenerationTests;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PickIfSmallerTest
{
	//could fail or not if the seed is random.
	@Test
	public void property()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomInteger random_int = new RandomInteger(0, 10000000);
			random_int.setSeed(int_list.get(i));
			PickIfSmaller pi_small = new PickIfSmaller(random_int, 2, (float) -1);

			int old_value;
			int new_value;
			old_value = (int) pi_small.pick();

			for (int j = 0; j < 9; j++)
			{
				new_value = (int) pi_small.pick();
				Assert.assertTrue(old_value > new_value);
				old_value = new_value;
			}
		}

	}

 @Test
	public void duplicateWithState()
 {
	 SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
	 List<Integer> int_list = seeds.getGeneralSeeds();
	 for (int i = 0; i < 10; i++)
	 {
		 RandomInteger random_int = new RandomInteger(0, 10000000);
		 random_int.setSeed(int_list.get(i));
		 PickIfSmaller pi_small = new PickIfSmaller(random_int, 2, (float) -1);

		 for (int j = 0; j < 2; j++)
		 {
			 pi_small.pick();
		 }

		 PickIfSmaller pi_small_copy = pi_small.duplicate(true);

		 for (int j = 0; j < 2; j++)
		 {
			 Assert.assertEquals(pi_small.pick(), pi_small_copy.pick());
		 }


	 }


 }

	@Test
	public void duplicateWithoutState()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomInteger random_int = new RandomInteger(0, 10000000);
			random_int.setSeed(int_list.get(i));
			PickIfSmaller pi_small = new PickIfSmaller(random_int, 2, (float) -1);

			for (int j = 0; j < 2; j++)
			{
				pi_small.pick();
			}

			PickIfSmaller pi_small_copy = pi_small.duplicate(false);

			for (int j = 0; j < 2; j++)
			{
				Assert.assertNotEquals(pi_small.pick(), pi_small_copy.pick());
			}


			pi_small.reset();
			pi_small_copy.reset();

			for (int j = 0; j < 4; j++)
			{
				Assert.assertEquals(pi_small.pick(), pi_small_copy.pick());
			}

		}


	}

	// Force give up because the picker failed to pick a valid value under the max iteration limit.
	@Test(expected = GiveUpException.class)
	public void giveUp()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomSubString s = new RandomSubString("abcdefg");
			s.setSeed(int_list.get(i));
			PickIfSmaller pi_small = new PickIfSmaller( s,1, (float) -1);

			boolean giveup_exception_triggered = false;

			while(true)
			{
				try
				{
					pi_small.pick();
				}
				catch (NoMoreElementException e)
				{
					// overwrite the picker with a new one if the NoMoreElementException is thrown.
					pi_small = new PickIfSmaller( new RandomSubString("abcdefg"),1, (float) -1);
				}

			}
		}

	}

}
