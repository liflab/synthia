package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import org.junit.Assert;
import org.junit.Test;

public class AllPickersTest
{


	private Object[] getResultsArray()
	{
		Object[] results = new Object[]{new Object[]{false, false, false}
		, new Object[]{true, false, false}, new Object[]{false, true, false}
		, new Object[]{true, true, false}, new Object[]{false, false, true}
		, new Object[]{true, false, true}, new Object[]{false, true, true}
		, new Object[]{true, true, true}};

		return results;
	}



	@Test
	public void property()
	{
		EnumerativePicker[] enum_picks = new EnumerativePicker[]{new AllBooleans(), new AllBooleans()
		, new AllBooleans()};
		AllPickers all_picks = new AllPickers(enum_picks);
		Object[] results = getResultsArray();
		int i = 0;

		while (!all_picks.isDone())
		{
			Object[] picked_results = all_picks.pick();
			Object[] expected_results = (Object[]) results[i];
			for (int j = 0; j < picked_results.length; j++)
			{
				Assert.assertEquals(expected_results[j], picked_results[j]);
			}
			i++;
		}
	}

	@Test
	public void DuplicateWithState()
	{
		EnumerativePicker[] enum_picks = new EnumerativePicker[]{new AllBooleans(), new AllBooleans()
				, new AllBooleans()};
		AllPickers all_picks = new AllPickers(enum_picks);

		all_picks.pick();
		all_picks.pick();

		AllPickers all_picks_copy = (AllPickers) all_picks.duplicate(true);


		for (int i = 0; i < 6; i++)
		{
			Object[] results = all_picks.pick();
			Object[] results_copy = all_picks_copy.pick();

			for (int j = 0; j < results.length; j++)
			{
				Assert.assertEquals(results[j], results_copy[j]);
			}

		}

	}

	@Test
	public void DuplicateWithoutState()
	{
		EnumerativePicker[] enum_picks = new EnumerativePicker[]{new AllBooleans(), new AllBooleans()
				, new AllBooleans()};
		AllPickers all_picks = new AllPickers(enum_picks);

		all_picks.pick();
		all_picks.pick();

		AllPickers all_picks_copy = (AllPickers) all_picks.duplicate(false);


		for (int i = 0; i < 6; i++)
		{
			Object[] results = all_picks.pick();
			Object[] results_copy = all_picks_copy.pick();

			for (int j = 0; j < results.length; j++)
			{
				int counter = 0;
				if (results[j] == results_copy[j])
				{
					counter++;
				}
				Assert.assertNotEquals(results.length, counter);
			}

		}
	}

	@Test
	public void isDone()
	{
		EnumerativePicker[] enum_picks = new EnumerativePicker[]{new AllBooleans(), new AllBooleans()
				, new AllBooleans()};
		AllPickers all_picks = new AllPickers(enum_picks);

		for (int i = 0; i < 8; i++)
		{
			Assert.assertEquals(false, all_picks.isDone());
			all_picks.pick();
		}

		Assert.assertEquals(true, all_picks.isDone());
	}

	@Test(expected = NoMoreElementException.class)
	public void NoMoreException()
	{
		EnumerativePicker[] enum_picks = new EnumerativePicker[]{new AllBooleans(), new AllBooleans()
				, new AllBooleans()};
		AllPickers all_picks = new AllPickers(enum_picks);

		for (int i = 0; i < 8; i++)
		{
			all_picks.pick();
		}

		all_picks.pick();
	}
}
