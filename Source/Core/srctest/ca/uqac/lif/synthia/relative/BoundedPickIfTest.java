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
package ca.uqac.lif.synthia.relative;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ca.uqac.lif.synthia.sequence.Playback;

/**
 * Unit tests for {@link BoundedPickIf}.
 */
public class BoundedPickIfTest
{
	@Test
	public void test1()
	{
		BoundedPickIf<Integer> pif = new BoundedPickIf<Integer>(
				new Playback<Integer>(0, Arrays.asList(0, 1, 2, 3, 4, 5)).setLoop(false)) {
			@Override
			protected boolean select(Integer i) {
				return i.intValue() % 2 == 0;
			}
		};
		assertFalse(pif.isDone());
		assertEquals(0, pif.pick());
		assertFalse(pif.isDone());
		assertEquals(2, pif.pick());
		assertFalse(pif.isDone());
		assertEquals(4, pif.pick());
		assertTrue(pif.isDone());
	}
	
	@Test
	public void test2()
	{
		BoundedPickIf<Integer> pif = new BoundedPickIf<Integer>(
				new Playback<Integer>(0, Arrays.asList(0, 1, 2, 3, 4, 5)).setLoop(false)) {
			@Override
			protected boolean select(Integer i) {
				return i.intValue() > 10;
			}
		};
		assertTrue(pif.isDone());
	}
}
