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
package ca.uqac.lif.synthia.collection;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ca.uqac.lif.synthia.random.RandomInteger;

public class ComposeShrunkListTest
{
	@Test
	public void test1()
	{
		RandomInteger dice = new RandomInteger(0, 10);
		RandomInteger length = new RandomInteger(1, 4);
		ComparableList<Integer> list = new ComparableList<Integer>();
		list.add(4);
		list.add(6);
		list.add(8);
		ComposeShrunkList<Integer> csl = new ComposeShrunkList<Integer>(dice, length, list);
		for (int i = 0; i < 5; i++)
		{
			ComparableList<Integer> other_list = new ComparableList<Integer>(csl.pick());
			System.out.println(other_list);
			assertTrue(list.compareTo(other_list) > 0);
		}
	}
}
