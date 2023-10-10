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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.uqac.lif.synthia.sequence.Playback;
import ca.uqac.lif.synthia.util.NothingPicker;

/**
 * Unit tests for {@link Merge}.
 */
public class MergeTest
{
	@SuppressWarnings("unchecked")
	@Test
	public void test1()
	{
		Merge<String> merge = new Merge<String>(new Playback<String>("a").setLoop(false));
		assertFalse(merge.isDone());
		assertEquals("a", merge.pick());
		assertTrue(merge.isDone());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test2()
	{
		Merge<String> merge = new Merge<String>(new Playback<String>("a").setLoop(false), new Playback<String>("b", "c").setLoop(false));
		assertFalse(merge.isDone());
		assertEquals("a", merge.pick());
		assertFalse(merge.isDone());
		assertEquals("b", merge.pick());
		assertFalse(merge.isDone());
		assertEquals("c", merge.pick());
		assertTrue(merge.isDone());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test3()
	{
		Merge<String> merge = new Merge<String>(new NothingPicker<String>());
		assertTrue(merge.isDone());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test4()
	{
		Merge<String> merge = new Merge<String>(new Playback<String>("a").setLoop(false), new NothingPicker<String>(), new Playback<String>("b").setLoop(false));
		assertFalse(merge.isDone());
		assertEquals("a", merge.pick());
		assertFalse(merge.isDone());
		assertEquals("b", merge.pick());
		assertTrue(merge.isDone());
	}
}
