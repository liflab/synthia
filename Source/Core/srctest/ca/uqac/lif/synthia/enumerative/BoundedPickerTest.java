/*
    Synthia, a data structure generator
    Copyright (C) 2019-2021 Laboratoire d'informatique formelle
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

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.Constant;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link Bound}.
 */
public class BoundedPickerTest
{
	@Test
	public void testIterationCount1()
	{
		RandomInteger ri = new RandomInteger(0, 10);
		Bound<Integer> b = new Bound<Integer>(ri, 15);
		for (int i = 0; i < 15; i++)
		{
			assertFalse(b.isDone());
			b.pick();
		}
		assertTrue(b.isDone());
	}

	@Test
	public void testIterationCount2()
	{
		RandomInteger ri = new RandomInteger(0, 10);
		Constant<Integer> c = new Constant<Integer>(15);
		Bound<Integer> b = new Bound<Integer>(ri, c);
		for (int i = 0; i < 15; i++)
		{
			assertFalse(b.isDone());
			b.pick();
		}
		assertTrue(b.isDone());
	}

	@Test public void sameValuesSameSeed()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 10000);
			RandomInteger random_integer1 = new RandomInteger(1, 10);
			RandomInteger random_integer2 = new RandomInteger(1, 10);
			RandomFloat random_float = new RandomFloat();
			RandomFloat random_float1 = new RandomFloat();
			int randomSeed = random_integer.pick();
			random_integer1.setSeed(randomSeed);
			random_integer2.setSeed(randomSeed);
			random_float.setSeed(randomSeed);
			random_float1.setSeed(randomSeed);
			Bound<? extends Number> bounded_picker = new Bound<Number>(random_float, random_integer1.pick());
			Bound<? extends Number> bounded_picker1 = new Bound<Number>(random_float1, random_integer2.pick());
			while (!bounded_picker.isDone())
			{
				Assertions.assertEquals(bounded_picker.pick(), bounded_picker1.pick());
			}
		}
	}

	@Test
	public void duplicateWithState()
	{
		int min = 50;
		int max = 10000;
		for (int i = 0; i < 1000; i++)
		{
			RandomInteger random_integer = new RandomInteger(min, max);
			RandomFloat random_float = new RandomFloat();
			Bound<Number> bounded_picker = new Bound<Number>(random_float, random_integer.pick());
			for (int j = 0; j < (min - 1) ; j++)
			{
				bounded_picker.pick();
			}
			Bound<Number> bounded_picker_copy = bounded_picker.duplicate(true);
			while (!bounded_picker.isDone())
			{
				Assertions.assertEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			}
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		int min = 50;
		int max = 10000;
		for (int i = 0; i < max; i++)
		{
			RandomInteger random_integer = new RandomInteger(min, max);
			RandomFloat random_float = new RandomFloat();
			Bound<Number> bounded_picker = new Bound<Number>(random_float, random_integer.pick());

			for (int j = 0; j < (min - 1); j++)
			{
				bounded_picker.pick();
			}

			Bound<Number> bounded_picker_copy = bounded_picker.duplicate(false);

			while (bounded_picker.isDone())
			{
				Assertions.assertNotEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			}


			bounded_picker.reset();
			bounded_picker_copy.reset();

			while (!bounded_picker.isDone())
			{
				Assertions.assertEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			}
		}
	}

	@Test
	public void isDone()
	{
		Bound<Number> bounded_picker = new Bound<Number>(new RandomInteger(0, 100), 100);

		for (int i = 0; i < 100; i++)
		{
			Assertions.assertEquals(false, bounded_picker.isDone());
			bounded_picker.pick();
		}

		Assertions.assertEquals(true, bounded_picker.isDone());

	}

	@Test
	public void noMoreElementException()
	{
		Bound<Number> bounded_picker = new Bound<Number>(new RandomInteger(0, 100), 100);

		for (int i = 0; i < 100; i++)
		{
			Assertions.assertEquals(false, bounded_picker.isDone());
			bounded_picker.pick();
		}
		assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override public void execute() throws Throwable
			{
				bounded_picker.pick();
			}
		});
	}
}