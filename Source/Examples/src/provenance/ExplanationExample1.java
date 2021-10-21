package provenance;

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

public class ExplanationExample1
{

	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		RandomInteger r_int = new RandomInteger(1, 3);
		r_int.setSeed(42);
		Freeze<Integer> fr_int = new Freeze<Integer>(r_int);
		Constant<Integer> zero = new Constant<Integer>(0);
		Tick t = new Tick(zero, fr_int);
		Playback<Integer> pb = new Playback<Integer>(0, new Integer[] {0, -1, 1}).setLoop(true);
		PrismPicker hp = new PrismPicker(t, pb);
		PickIf<float[]> filter = new PickIf<float[]>(hp) {
			protected boolean select(float[] p) { System.out.println(p[0] + "," + p[1]); return p[0] != p[1]; }
		};
		Constant<Integer> four = new Constant<Integer>(4);
		ComposeList<float[]> list = new ComposeList<float[]>(filter, four);
		System.out.println(list.pick());
		PartNode tree = Explanation.explain(ComposedPart.compose(new NthElement(0), NthSuccessiveOutput.FIRST), list);
		LineageDotRenderer renderer = new LineageDotRenderer(tree);
		renderer.render(System.out);
	}

}
