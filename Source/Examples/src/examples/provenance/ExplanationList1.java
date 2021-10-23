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
 * Demonstration of Synthia's explainability features on a wiring of
 * pickers producing a list of points. The code in this example corresponds to
 * the following diagram:
 * <p>
 * <img src="{@docRoot}/doc-files/provenance/List.png" alt="Wiring of pickers" />
 * <p>
 * A chain of pickers is instructed to generate a list of four
 * two-dimensional points using a {@link PrismPicker}, with constraints on their
 * possible values. The x coordinate starts at $0$ and increments by a fixed
 * value that is initially chosen to be either 1 or 2; the y coordinate
 * repeatedly iterates through the values 0, 1 and -1; a {@link PickIf} removes
 * any point lying on the line x=y. A possible first output of this chain is
 * the list [(1,-1),(2,0),(3,1),(4,-1)].
 * <p>
 * One can then ask for a first list out of this chain, and then ask for an
 * explanation of the first point inside the list by passing to the static
 * method {@link Explanation#explain(ca.uqac.lif.petitpoucet.Part, ca.uqac.lif.synthia.Picker) Explanation.explain()}
 * a the appropriate <em>designator</em>. This has for effect of linking this
 * value to the output produced by other pickers, ultimately producing a graph
 * such as the one shown below.
 * <p>
 * <img src="{@docRoot}/doc-files/provenance/Explanation.png" alt="Explanation graph" />
 * <p>
 * In the source code, this graph is merely printed to the console in the DOT
 * format of the <a href="https://graphviz.org">Graphviz</a> graph rendering
 * tool. The picture above is a beautified version of that graph using
 * pictograms instead of text, and simplified by removing "and" nodes.
 * <p>
 * This graph is not a static template obtained from the wiring of pickers; to
 * illustrate this fact, the graph below shows an alternate explanation, which
 * is what one obtains when {@link RandomInteger} picks the value 2 instead of
 * 1; in that case the output list becomes [(2,1),(4,-1),(6,0),(8,1)].
 * <p>
 * <img src="{@docRoot}/doc-files/provenance/Explanation_alt.png"
 *   alt="Other explanation graph" />
 * <p>
 * In the source code, one can change the value of the seed given to the
 * {@link RandomInteger} <tt>r_int</tt> until this alternate graph is obtained
 * instead of the first one.
 */
public class ExplanationList1
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
		list.pick();
		list.pick();
		Utilities.println(System.out, l);
		
		/* Step 3: request the explanation graph for the first element of
		 * the first list produced by ComposeList. */
		PartNode tree = Explanation.explain(ComposedPart.compose(new NthElement(0), NthSuccessiveOutput.FIRST), list);
		LineageDotRenderer renderer = new LineageDotRenderer(tree);
		renderer.render(System.out);
	}
}
