package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.exception.GiveUpException;
import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.random.RandomSubString;
import org.junit.Assert;
import org.junit.Test;



public class PickIfSmallerTest
{
	//could fail or not if the seed is random.
	@Test
	public void property()
	{
		RandomInteger random_int = new RandomInteger(0, 10000000);
		random_int.setSeed(-279941427);
		PickIfSmaller pi_small = new PickIfSmaller(random_int, 2, (float) -1);

		int old_value;
		int new_value;
		old_value = (int) pi_small.pick();

		for (int i = 0; i < 9; i++)
		{
			new_value = (int) pi_small.pick();
			Assert.assertTrue(old_value > new_value);
			old_value = new_value;
		}
	}

 @Test
	public void duplicateWithState()
 {
	 RandomInteger random_int = new RandomInteger(0, 100000);
	 PickIfSmaller pi_small = new PickIfSmaller(random_int, 2, (float) -1);

	 for (int i = 0; i < 2; i++)
	 {
		 pi_small.pick();
	 }

	 PickIfSmaller pi_small_copy = pi_small.duplicate(true);

	 for (int i = 0; i < 2; i++)
	 {
		 Assert.assertEquals(pi_small.pick(), pi_small_copy.pick());
	 }
 }

	@Test
	public void duplicateWithoutState()
	{
		RandomInteger random_int = new RandomInteger(0, 100000);
		PickIfSmaller pi_small = new PickIfSmaller(random_int, 2, (float) -1);

		for (int i = 0; i < 2; i++)
		{
			pi_small.pick();
		}

		PickIfSmaller pi_small_copy = pi_small.duplicate(false);

		for (int i = 0; i < 2; i++)
		{
			Assert.assertNotEquals(pi_small.pick(), pi_small_copy.pick());
		}


		pi_small.reset();
		pi_small_copy.reset();

		for (int i = 0; i < 4; i++)
		{
			Assert.assertEquals(pi_small.pick(), pi_small_copy.pick());
		}
	}

	// Force give up because the picker failed to pick a valid value under the max iteration limit.
	@Test(expected = GiveUpException.class)
	public void giveUp()
	{
		PickIfSmaller pi_small = new PickIfSmaller( new RandomSubString("abcdefg"),1, (float) -1);
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
