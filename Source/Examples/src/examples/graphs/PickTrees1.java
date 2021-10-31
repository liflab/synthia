package examples.graphs;

import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.sequence.Playback;
import ca.uqac.lif.synthia.tree.ColoredNodePicker;
import ca.uqac.lif.synthia.tree.ColoredTreeRenderer;
import ca.uqac.lif.synthia.tree.Node;
import ca.uqac.lif.synthia.tree.TreePicker;
import ca.uqac.lif.synthia.util.Choice;
import ca.uqac.lif.synthia.util.Constant;

/**
 * 
 * @author Sylvain Hallé
 * @ingroup Examples
 */
public class PickTrees1 
{

	public static void main(String[] args) 
	{
		int seed = 1234591;
		RandomInteger height = new RandomInteger(2, 6);
		height.setSeed(seed);
		System.out.println(height.pick());
		RandomFloat color_float = new RandomFloat();
		color_float.setSeed(seed);
		Choice<String> color = new Choice<String>(color_float);
		color.add("blue", 0.33).add("red", 0.33).add("yellow", 0.34);
		ColoredNodePicker node = new ColoredNodePicker(color);
		//RandomInteger degree = new RandomInteger(0, 4);
		//Constant<Integer> degree = new Constant<Integer>(2);
		Playback<Integer> degree = new Playback<Integer>(0, new Integer[] {2,0});
		//degree.setSeed(seed);
		//RandomFloat child = new RandomFloat(0.4, 0.6);
		Constant<Float> child = new Constant<Float>(0.9f);
		//child.setSeed(seed);
		TreePicker<String> tp = new TreePicker<String>(node, height, degree, child);
		Node<String> root = tp.pick();
		ColoredTreeRenderer.treeToDot(System.out, root);
	}

}