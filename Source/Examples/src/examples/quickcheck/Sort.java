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
package examples.quickcheck;

import java.util.Collections;
import java.util.List;

import ca.uqac.lif.synthia.collection.ComparableList;
import ca.uqac.lif.synthia.collection.ComposeList;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.test.Assert;
import ca.uqac.lif.synthia.test.Testable;

/**
 * Illustrates the shrinking process when testing a procedure that sorts lists
 * of numbers. In this example, a generator produces random lists of up to
 * 1,000 elements, each having a value between 0 and 999. Each list is passed
 * to {@link FaultySort#sort(List)}, and an assertion checks that the resulting
 * list is indeed sorted. The {@link FaultySort} class contains a deliberately
 * injected fault, such that any list where the sum of elements at indices 3
 * and 4 is greater than 500 is incorrectly sorted (the two elements are
 * swapped in the output).
 * <p>
 * After finding a first randomly generated list revealing the fault, the
 * {@link Assert} object will ask for a shrunk picker and repeat the process,
 * eventually finding shorter lists with smaller values that also reveal the
 * fault. You can try running the program multiple times; this will produce
 * different inputs. One can observe that the number of shrinking steps depends
 * on the starting seed. However, most of the time, the list that is found is
 * of minimal length (i.e. 5).
 * <p>
 * A typical output of the program looks like this:
 * <pre>
 * Assertion is false
 * [931, 520, 668, 369, 906, 823, 468, 145, 623, 702, 691, 52, 963, 339, 722, 434, 901, 590, 111, 734, 104, 694, 200, 871, 211, 311, 820, 856, 382, 724, 390] produces [52, 104, 111, 200, 145, 211, 311, 339, 369, 382, 390, 434, 468, 520, 590, 623, 668, 691, 694, 702, 722, 724, 734, 820, 823, 856, 871, 901, 906, 931, 963] and is not sorted
 * 25 shrinking steps
 * [0, 28, 35, 725, 419] produces [0, 28, 35, 725, 419] and is also not sorted
 * </pre>
 *  
 * @ingroup Examples
 * @author Sylvain Hallé
 */
public class Sort
{
	public static void main(String[] args)
	{
		FaultySort<Integer> fs = new FaultySort<Integer>();
		Assert<List<Integer>> a = new Assert<List<Integer>>(new IsSorted(),
				new ComposeList<Integer>(new RandomInteger(0, 1000), new RandomInteger(0, 2000)));
		if (!a.check())
		{
			System.out.println("Assertion is false");
			System.out.println(a.getInitial() + " produces " + fs.sort(a.getInitial()) + " and is not sorted");
			System.out.println(a.getIterations().size() + " shrinking steps");
			System.out.println(a.getShrunk() + " produces " + fs.sort(a.getShrunk()) + " and is also not sorted");
		}
	}
	
	protected static class IsSorted implements Testable
	{
		@Override
		@SuppressWarnings("unchecked")
		public boolean test(Object ... parameters)
		{
			FaultySort<Integer> fs = new FaultySort<Integer>();
			List<Integer> list = (List<Integer>) parameters[0];
			list = fs.sort(list);
			for (int i = 0; i < list.size() - 1; i++)
			{
				Object o1 = list.get(i);
				Object o2 = list.get(i + 1);
				if (o1 instanceof Comparable)
				{
					if (((Comparable<Object>) o1).compareTo(o2) > 0)
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * A deliberately faulty implementation of insertion sort.
	 *
	 * @param <T> The type of the elements to sort.
	 */
	protected static class FaultySort<T>
	{
		@SuppressWarnings("unchecked")
		public List<T> sort(List<T> list)
		{
			ComparableList<T> ini_list = new ComparableList<T>();
			ComparableList<T> new_list = new ComparableList<T>();
			ini_list.addAll(list);
			for (int i = 0; i < list.size(); i++)
			{
				int index_to_remove = -1;
				T to_insert = null;
				for (int j = 0; j < ini_list.size(); j++)
				{
					T e = ini_list.get(j);
					if (to_insert == null)
					{
						to_insert = e;
						index_to_remove = j;
					}
					else
					{
						if (to_insert instanceof Comparable)
						{
							int value = ((Comparable<T>) to_insert).compareTo(e);
							if (value > 0)
							{
								to_insert = e;
								index_to_remove = j;
							}
						}
					}
				}
				new_list.add(to_insert);
				ini_list.remove(index_to_remove);
			}
			// This part below is the injection of a fault in the sorting
			if (list.size() > 4 && ((int) list.get(3)) + ((int) list.get(4)) > 1000)
			{
				Collections.swap(new_list, 3, 4);
			}
			return new_list;
		}
	}
}
