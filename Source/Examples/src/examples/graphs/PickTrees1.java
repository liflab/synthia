/*
    Synthia, a data structure generator
    Copyright (C) 2019-2020 Laboratoire d'informatique formelle
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
