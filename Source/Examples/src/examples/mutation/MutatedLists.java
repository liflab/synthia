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
package examples.mutation;

import java.util.List;

import ca.uqac.lif.synthia.Mutator;
import ca.uqac.lif.synthia.Replace;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.Choice;
import ca.uqac.lif.synthia.util.ComposeList;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.DeleteElement;
import ca.uqac.lif.synthia.util.Freeze;
import ca.uqac.lif.synthia.util.Mutate;
import ca.uqac.lif.synthia.util.MutateElement;
import ca.uqac.lif.synthia.util.Offset;
import ca.uqac.lif.synthia.util.Swap;
import ca.uqac.lif.synthia.util.Tick;

/**
 * Illustration of mutation operations on lists and list elements. The program
 * generates lists and then applies various {@link Mutator Mutators} on them.
 * The wiring of pickers in this program is illustrated by the following
 * diagram:
 * <p>
 * <img src="{@docRoot}/doc-files/mutation/Mutators.png" alt="Picker wiring diagram" />
 * <p>
 * The diagram culminates into a {@link Mutate} picker (box A) that receives as
 * one of its inputs an arbitrary list of numbers (not shown). The picker that
 * provides a mutator for each list is a {@link Choice} (box B), which may pick
 * with equal probability one of three possible mutators, represented by boxes
 * 1-3. The first is an instance of {@link MutateElement}, whose mutator picker
 * is another {@link Choice} (box a), selecting either an {@link Offset} (box
 * b) or a {@link Replace} feeding the constant value 0 (box c). The other two
 * list mutators are instances of {@link Swap} (box 2) and
 * {@link DeleteElement} (box 3). All these pickers use a distinct
 * {@link Random\-Float} as their source of randomness for making their
 * respective choices. The end result is that, for every list given to the
 * picker of box A, three equally probable outcomes are possible:
 * <ol>
 * <li>the first element is swapped with another one</li>
 * <li>one element is deleted</li>
 * <li>one element is chosen, and, with equal probability, is either offset by a random value in [0,1], or replaced with 0.</li>
 * </ol>
 * The program takes a single list and repeatedly passes it to {@link Mutate}.
 * It produces the following output:
 * <pre>
 * [56.0, 55.0, 57.0]
 * [55.0, 56.78632, 57.0]
 * [56.0, 57.0]
 * [56.0, 57.0]
 * [55.0, 57.0]
 * [55.135525, 56.0, 57.0]
 * [55.93449, 56.0, 57.0]
 * [56.0, 55.0, 57.0]
 * [55.0, 56.0, 57.367645]
 * [0, 56.0, 57.0]
 * </pre>
 * One can see how the original list [55, 56, 57] is modified in different
 * ways:
 * <ul>
 * <li>The first two elements are swapped (lists 1, 8)</li>
 * <li>The second element is offset from a random value (list 2)</li>
 * <li>The first element is deleted (lists 3-4)</li>
 * <li>The second element is deleted (list 5)</li>
 * <li>The first element is offset from a random value (lists 6-7)</li>
 * <li>The third element is offset from a random value (list 9)</li>
 * <li>The first element is replaced by 0 (list 10)</li>
 * </ul>
 * <p>
 * Note that although the diagram looks complex, the part of the program
 * that builds the wiring of pickers only contains 13 lines.
 */
public class MutatedLists
{
	public static void main(String[] args)
	{
		/* A random float. For the sake of simplicity, we slightly diverge from the
		 * diagram and reuse the same RandomFloat instance everywhere it is needed. */
		RandomFloat rf = new RandomFloat();
		
		/* A picker that will generate lists of integers whose values increment .*/
		RandomInteger start = new RandomInteger(0, 100);
		Tick t = new Tick(start, new Constant<Integer>(1));
		ComposeList<Number> lists = new ComposeList<Number>(t, new RandomInteger(2, 10));
		
		/* A mutator for list elements that will choose between
		 * offsetting an element or replacing it with 0.  */
		Choice<Mutator<? extends Number>> e_choices = new Choice<Mutator<? extends Number>>(rf);
		e_choices.add(new Offset(null, rf), 0.5);
		e_choices.add(new Replace<Number>(null, new Constant<Number>(0)), 0.5);
		MutateElement<Number> me = new MutateElement<Number>(null, rf, e_choices);
		
		/* A mutator for lists that chooses between mutating an element,
		 * swapping two elements or deleting one. We use a Freeze picker so that the
		 * same list is mutated every time, to see the effect. */
		Choice<Mutator<List<Number>>> l_choices = new Choice<Mutator<List<Number>>>(rf);
		l_choices.add(me, 0.33);
		l_choices.add(new Swap<Number>(null, rf, rf), 0.33);
		l_choices.add(new DeleteElement<Number>(lists, rf), 0.34);		
		Mutate<List<Number>> m = new Mutate<List<Number>>(new Freeze<List<Number>>(lists), l_choices);
		
		/* Iterate a few times over the picker. */
		for (int i = 0; i < 10; i++)
		{
			System.out.println(m.pick());	
		}
	}
}
