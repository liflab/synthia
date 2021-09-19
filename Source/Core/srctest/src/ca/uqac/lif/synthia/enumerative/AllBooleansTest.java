package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import org.junit.Assert;
import org.junit.Test;



public class AllBooleansTest
{

	@Test(expected = NoMoreElementException.class)
	public void noMoreException()
	{
		AllBooleans all_bool =  new AllBooleans();

		for (int i = 0; i <2; i++)
		{
			all_bool.pick();
		}

		all_bool.pick();
	}

	@Test
	public void pick()
	{
		AllBooleans all_bool =  new AllBooleans();

		Assert.assertEquals(false, all_bool.pick());
		Assert.assertEquals(true, all_bool.pick());
	}

	@Test
	public void isDone()
	{
		AllBooleans all_bool =  new AllBooleans();

		for (int i = 0; i < 2; i++)
		{
			Assert.assertEquals(false, all_bool.isDone());
			all_bool.pick();
		}

		Assert.assertEquals(true, all_bool.isDone());
	}

	@Test
	public void duplicateWithState()
	{
		AllBooleans all_bool =  new AllBooleans();
		all_bool.pick();

		AllBooleans all_bool_copy = (AllBooleans) all_bool.duplicate(true);

		Assert.assertEquals(all_bool.pick(), all_bool_copy.pick());
	}

	@Test
	public void duplicateWithoutState()
	{
		AllBooleans all_bool =  new AllBooleans();
		all_bool.pick();

		AllBooleans all_bool_copy = (AllBooleans) all_bool.duplicate(false);

		Assert.assertNotEquals(all_bool.pick(), all_bool_copy.pick());

		all_bool.reset();
		all_bool_copy.reset();

		Assert.assertEquals(all_bool.pick(), all_bool_copy.pick());
		Assert.assertEquals(all_bool.pick(), all_bool_copy.pick());
	}

	@Test
	public void scrambleFalse()
	{
		AllBooleans all_bool =  new AllBooleans(false);

		Assert.assertEquals(false, all_bool.pick());
		Assert.assertEquals(true, all_bool.pick());
	}



	@Test(expected = NoMoreElementException.class)
	public void noMoreExceptionScramble()
	{
		AllBooleans all_bool =  new AllBooleans(true);

		for (int i = 0; i <2; i++)
		{
			all_bool.pick();
		}

		all_bool.pick();
	}

	@Test
	public void isDoneScramble()
	{
		AllBooleans all_bool =  new AllBooleans();

		for (int i = 0; i < 2; i++)
		{
			Assert.assertEquals(false, all_bool.isDone());
			all_bool.pick();
		}

		Assert.assertEquals(true, all_bool.isDone());
	}

	@Test
	public void duplicateWithStateScramble()
	{
		AllBooleans all_bool =  new AllBooleans(true);
		all_bool.pick();

		AllBooleans all_bool_copy = (AllBooleans) all_bool.duplicate(true);

		Assert.assertEquals(all_bool.pick(), all_bool_copy.pick());
	}

	@Test
	public void duplicateWithoutStateScramble()
	{
		AllBooleans all_bool =  new AllBooleans(true);
		all_bool.pick();

		AllBooleans all_bool_copy = (AllBooleans) all_bool.duplicate(false);

		Assert.assertNotEquals(all_bool.pick(), all_bool_copy.pick());

		all_bool.reset();
		all_bool_copy.reset();

		Assert.assertEquals(all_bool.pick(), all_bool_copy.pick());
		Assert.assertEquals(all_bool.pick(), all_bool_copy.pick());
	}
}
