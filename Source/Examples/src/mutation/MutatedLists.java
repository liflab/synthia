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
package mutation;

import java.util.List;

import ca.uqac.lif.synthia.Mutator;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.ComposeList;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Freeze;
import ca.uqac.lif.synthia.util.Mutate;
import ca.uqac.lif.synthia.util.Swap;
import ca.uqac.lif.synthia.util.Tick;

public class MutatedLists
{

	public static void main(String[] args)
	{
		RandomFloat rf = new RandomFloat();
		RandomInteger start = new RandomInteger(0, 100);
		Tick t = new Tick(start, new Constant<Integer>(1));
		ComposeList<Number> lists = new ComposeList<Number>(t, new RandomInteger(2, 10));
		Swap<Number> swap = new Swap<Number>(lists, rf, rf);
		Mutate<List<Number>> m = new Mutate<List<Number>>(new Freeze<List<Number>>(lists), new Constant<Mutator<List<Number>>>(swap));
		for (int i = 0; i < 10; i++)
		{
			System.out.println(m.pick());	
		}
		
	}

}
