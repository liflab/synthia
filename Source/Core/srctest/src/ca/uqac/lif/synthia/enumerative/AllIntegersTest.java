package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.exception.NoMoreElementException;

import ca.uqac.lif.synthia.random.RandomInteger;
import org.junit.Assert;
import org.junit.Test;

public class AllIntegersTest
{

	@Test(expected = NoMoreElementException.class)
	public void noMoreException()
	{
		int min = 0;
		int max = 10;
		AllIntegers all_int =  new AllIntegers(min, max);

		for (int i = 0; i <= max; i++)
		{
			all_int.pick();
		}

		all_int.pick();
	}

	@Test
	public void pick()
	{
		int min = 0;
		int max = 10;
		AllIntegers all_int =  new AllIntegers(min, max);

		for (int i = 0; i <= max; i++)
		{
			Assert.assertEquals(i, all_int.pick().intValue());
		}
	}

	@Test
	public void isDone()
	{
		int min = 0;
		int max = 10;
		AllIntegers all_int =  new AllIntegers(min, max);

		for (int i = 0; i <= max; i++)
		{
			Assert.assertEquals(false, all_int.isDone());
			all_int.pick();
		}

		Assert.assertEquals(true, all_int.isDone());
	}

	@Test
	public void duplicateWithState()
	{
		int min = 0;
		int max = 10;
		AllIntegers all_int =  new AllIntegers(min, max);

		for (int i = 0; i <= (max/2); i++)
		{
			all_int.pick();
		}

		AllIntegers all_int_copy = (AllIntegers) all_int.duplicate(true);

		for (int i = 0; i < (max/2); i++)
		{
			Assert.assertEquals(all_int.pick().intValue(), all_int_copy.pick().intValue());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		int min = 0;
		int max = 10;
		AllIntegers all_int =  new AllIntegers(min, max);

		for (int i = 0; i <= (max/2); i++)
		{
			all_int.pick();
		}

		AllIntegers all_int_copy = (AllIntegers) all_int.duplicate(false);

		for (int i = 0; i < (max/2); i++)
		{
			Assert.assertNotEquals(all_int.pick().intValue(), all_int_copy.pick().intValue());
		}

		all_int.reset();
		all_int_copy.reset();

		for (int i = 0; i <= max; i++)
		{
			Assert.assertEquals(all_int.pick().intValue(), all_int_copy.pick().intValue());
		}
	}

	@Test(expected = NoMoreElementException.class)
	public void noMoreExceptionScramble()
	{
		int min = 0;
		int max = 10;
		AllIntegers all_int =  new AllIntegers(min, max,true);

		for (int i = 0; i <= max; i++)
		{
			all_int.pick();
		}

		all_int.pick();
	}

	@Test
	public void isDoneScramble()
	{
		int min = 0;
		int max = 10;
		AllIntegers all_int =  new AllIntegers(min, max, true);

		for (int i = 0; i <= max; i++)
		{
			Assert.assertEquals(false, all_int.isDone());
			all_int.pick();
		}

		Assert.assertEquals(true, all_int.isDone());
	}

	@Test
	public void duplicateWithStateScramble()
	{
		int min = 0;
		int max = 10;
		RandomInteger seed = new RandomInteger(0, 100000);
		AllIntegers all_int =  new AllIntegers(min, max, true);
		all_int.setSeed(seed.pick());

		for (int i = 0; i <= (max/2); i++)
		{
			all_int.pick();
		}

		AllIntegers all_int_copy = (AllIntegers) all_int.duplicate(true);

		for (int i = 0; i < (max/2); i++)
		{
			Assert.assertEquals(all_int.pick().intValue(), all_int_copy.pick().intValue());
		}
	}

	@Test
	public void duplicateWithoutStateScramble()
	{
		int min = 0;
		int max = 10;
		RandomInteger seed = new RandomInteger(0, 100000);
		AllIntegers all_int =  new AllIntegers(min, max, true);
		all_int.setSeed(seed.pick());

		for (int i = 0; i <= (max/2); i++)
		{
			all_int.pick();
		}

		AllIntegers all_int_copy = (AllIntegers) all_int.duplicate(false);

		for (int i = 0; i < (max/2); i++)
		{
			Assert.assertNotEquals(all_int.pick().intValue(), all_int_copy.pick().intValue());
		}

		all_int.reset();
		all_int_copy.reset();

		for (int i = 0; i <= max; i++)
		{
			Assert.assertEquals(all_int.pick().intValue(), all_int_copy.pick().intValue());
		}
	}
}
