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
package examples.provenance;

import java.util.List;

import ca.uqac.lif.petitpoucet.ComposedPart;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.LineageDotRenderer;
import ca.uqac.lif.petitpoucet.function.vector.NthElement;
import ca.uqac.lif.synthia.Explanation;
import ca.uqac.lif.synthia.NthSuccessiveOutput;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.relative.PickIf;
import ca.uqac.lif.synthia.replay.Playback;
import ca.uqac.lif.synthia.util.ComposeList;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Freeze;
import ca.uqac.lif.synthia.util.Tick;
import ca.uqac.lif.synthia.vector.PrismPicker;
import examples.util.Utilities;

/**
 * A variation of {@link ExplanationList1} where a different explanation is
 * requested, showing that fine-grained parts of the output can be tracked.
 * <p>
 * This example contains the same code as {@link ExplanationList1}, with the
 * exception of the call to
 * {@link Explanation#explain(ca.uqac.lif.petitpoucet.Part, ca.uqac.lif.synthia.Picker) Explanation.explain()}.
 * This time, the part of the output for which an explanation is requested is
 * the first coordinate of the second point inside the first list output by
 * {@link ComposeList}. Given that the picker produces the list
 * [[1,-1],[<b><u>2</u></b>,1],[3,0],[4,-1]], this corresponds to asking the
 * provenance of number 2 (in bold).
 * 
 * @ingroup Examples
 */
public class ExplanationList2
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		/* Step 1: create the pickers and connect them. These lines of code create
		 * exactly the pipeline shown in the picture above. */
		RandomInteger r_int = new RandomInteger(1, 3);
		r_int.setSeed(40002); // Modify this seed to make r_int pick another value
		Freeze<Integer> fr_int = new Freeze<Integer>(r_int);
		Constant<Integer> zero = new Constant<Integer>(0);
		Tick t = new Tick(zero, fr_int);
		Playback<Integer> pb = new Playback<Integer>(0, new Integer[] {0, -1, 1}).setLoop(true);
		PrismPicker hp = new PrismPicker(t, pb);
		PickIf<float[]> filter = new PickIf<float[]>(hp) {
			protected boolean select(float[] p) { return p[0] != p[1]; }
		};
		Constant<Integer> four = new Constant<Integer>(4);
		ComposeList<float[]> list = new ComposeList<float[]>(filter, four);
		
		/* Step 2: ask for a first list out of ComposeList and display it */
		List<float[]> l = list.pick();
		Utilities.println(System.out, l);
		
		/* Step 3: request the explanation graph for the first element of
		 * the first list produced by ComposeList. */
		PartNode tree = Explanation.explain(ComposedPart.compose(new NthElement(0), new NthElement(2), NthSuccessiveOutput.FIRST), list);
		LineageDotRenderer renderer = new LineageDotRenderer(tree);
		renderer.render(System.out);
	}
}
