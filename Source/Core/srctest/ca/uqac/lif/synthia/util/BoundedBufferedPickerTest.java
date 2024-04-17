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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.uqac.lif.synthia.Picker;

/**
 * Unit tests for the {@link BoundedBufferedPicker} class.
 */
public class BoundedBufferedPickerTest
{
	@Test
	public void test1()
	{
		TestBoundedBufferedPicker picker = new TestBoundedBufferedPicker();
		assertFalse(picker.isDone());
		assertEquals(1, picker.pick());
		assertEquals(2, picker.pick());
		assertEquals(3, picker.pick());
		assertTrue(picker.isDone());
	}
	
	protected static class TestBoundedBufferedPicker extends BoundedBufferedPicker<Integer>
	{
		boolean m_firstTime = true;
		
		@Override
		public void fillQueue()
		{
			if (m_firstTime)
			{
				m_queue.add(1);
				m_queue.add(2);
				m_queue.add(3);
				m_firstTime = false;
			}
		}

		@Override
		public Picker<Integer> duplicate(boolean with_state)
		{
			return null;
		}
	}
}
