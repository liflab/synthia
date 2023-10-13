/*
    Synthia, a data structure generator
    Copyright (C) 2019-2023 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.relative.BoundedPickIf;
import ca.uqac.lif.synthia.sequence.Playback;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

/**
 * Unit tests for {@link AllPickers}.
 */
public class AllPickersTest
{
	@Test
	public void test1()
	{
		AllPickers ap = new AllPickers(new Bounded<?>[] {new Playback<Object>(true).setLoop(false)});
		int i;
		for (i = 0; !ap.isDone(); i++)
		{
			ap.pick();
		}
		assertEquals(1, i);
	}
	
	@Test
	public void test2()
	{
		AllPickers ap = new AllPickers(new Bounded<?>[] {new BoundedPickIf<Object>(new Playback<Object>(0, Arrays.asList("a")).setLoop(false)), new Playback<Object>(0, Arrays.asList("b")).setLoop(false)});
		int i;
		for (i = 0; !ap.isDone(); i++)
		{
			Object[] ob = ap.pick();
			System.out.println(Arrays.toString(ob));
		}
		assertEquals(1, i);
	}
	
	private void throwNoMoreException(AllPickers picker)
	{
		assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override public void execute() throws Throwable
			{
				picker.pick();
			}
		});
	}

	private Object[] getResultsArray()
	{
		Object[] results = new Object[]{new Object[]{false, false, false}
		, new Object[]{true, false, false}, new Object[]{false, true, false}
		, new Object[]{true, true, false}, new Object[]{false, false, true}
		, new Object[]{true, false, true}, new Object[]{false, true, true}
		, new Object[]{true, true, true}};

		return results;
	}



	@SuppressWarnings("rawtypes")
	@Test
	public void property()
	{
		Bounded[] enum_picks = new Bounded[]{new AllBooleans(), new AllBooleans()
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
				Assertions.assertEquals(expected_results[j], picked_results[j]);
			}
			i++;
		}
	}

	@Test
	public void DuplicateWithState()
	{
		Bounded[] enum_picks = new Bounded[]{new AllBooleans(), new AllBooleans()
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
				Assertions.assertEquals(results[j], results_copy[j]);
			}

		}

	}

	@Test
	public void DuplicateWithoutState()
	{
		Bounded<?>[] enum_picks = new Bounded[]{new AllBooleans(), new AllBooleans()
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
				Assertions.assertNotEquals(results.length, counter);
			}

		}
	}

	@Test
	public void isDone()
	{
		AllPickers all_picks = new AllPickers(new Bounded[] {new AllBooleans(), new AllBooleans(), new AllBooleans()});
		for (int i = 0; i < 8; i++)
		{
			Assertions.assertEquals(false, all_picks.isDone());
			System.out.println(Arrays.toString(all_picks.pick()));
		}
		Assertions.assertEquals(true, all_picks.isDone());
	}

	@Test
	public void NoMoreException()
	{
		Bounded[] enum_picks = new Bounded[]{new AllBooleans(), new AllBooleans()
				, new AllBooleans()};
		AllPickers all_picks = new AllPickers(enum_picks);

		for (int i = 0; i < 8; i++)
		{
			all_picks.pick();
		}

		throwNoMoreException(all_picks);
	}
	
	@Test
	public void testEmpty()
	{
		AllPickers all_picks = new AllPickers(new Bounded[] {new AllBooleans(), new Playback<Object>()});
		Assertions.assertTrue(all_picks.isDone());
	}
}
