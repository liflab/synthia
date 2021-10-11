package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PickSmallerComparableTest
{
	//could fail or not
	@Test
	public void property()
	{
		RandomInteger random_int = new RandomInteger(0, 100000);
		int old_value = 1000;
		PickSmallerComparable<Integer> pi_small = new PickSmallerComparable<Integer>(random_int, old_value);

		int new_value;
		for (int i = 0; i < 9; i++)
		{
			new_value = (int) pi_small.pick();
			Assertions.assertTrue(old_value > new_value);
		}
	}

 @Test
	public void duplicateWithState()
 {
	 RandomInteger random_int = new RandomInteger(0, 100000);
	 PickSmallerComparable<Integer> pi_small = new PickSmallerComparable<Integer>(random_int, 2);

	 for (int i = 0; i < 2; i++)
	 {
		 pi_small.pick();
	 }

	 PickSmallerComparable<Integer> pi_small_copy = pi_small.duplicate(true);

	 for (int i = 0; i < 2; i++)
	 {
		 Assertions.assertEquals(pi_small.pick(), pi_small_copy.pick());
	 }
 }

	@Test
	public void duplicateWithoutState()
	{
		RandomInteger random_int = new RandomInteger(0, 100000);
		PickSmallerComparable<Integer> pi_small = new PickSmallerComparable<Integer>(random_int, 2);

		for (int i = 0; i < 2; i++)
		{
			pi_small.pick();
		}

		PickSmallerComparable<Integer> pi_small_copy = pi_small.duplicate(false);

		for (int i = 0; i < 2; i++)
		{
			Assertions.assertNotEquals(pi_small.pick(), pi_small_copy.pick());
		}

		System.out.println();
		pi_small.reset();
		pi_small_copy.reset();

		for (int i = 0; i < 4; i++)
		{
			Assertions.assertEquals(pi_small.pick(), pi_small_copy.pick());
		}
	}


}
