/*
    Synthia, a data structure generator
    Copyright (C) 2019-2024 Laboratoire d'informatique formelle
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
package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomFloat;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Once} class.
 */
public class OnceTest
{
	//Returns AssertionFailedError if NoMoreElementException is not thrown.
	public void noMoreExceptionThrow(Once<?> once)
	{
		assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override
			public void execute() throws Throwable
			{
				once.pick();
			}
		});
	}

	@Test
	public void noMoreExceptionTest()
	{
		RandomFloat randomFloat = new RandomFloat();
		Once<Float> once = new Once<>(randomFloat);
		once.pick();
		noMoreExceptionThrow(once);
	}

	@Test
	public void duplicateWithState()
	{
		RandomFloat randomFloat = new RandomFloat();
		Once<Float> once = new Once<>(randomFloat);
		once.pick();
		Once<Float> once_copy = once.duplicate(true);
		noMoreExceptionThrow(once);
		noMoreExceptionThrow(once_copy);
	}

	@Test
	public void duplicateWhitoutState()
	{
		RandomFloat randomFloat = new RandomFloat();
		Once<Float> once = new Once<>(randomFloat);
		float original_value = (float) once.pick();
		Once<Float> once_copy = once.duplicate(false);
		float copy_value = (float) once_copy.pick();
		Assertions.assertEquals(original_value, copy_value);
		noMoreExceptionThrow(once);
		noMoreExceptionThrow(once_copy);
	}
}
