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
package ca.uqac.lif.synthia.vector;

import ca.uqac.lif.petitpoucet.AndNode;
import ca.uqac.lif.petitpoucet.ComposedPart;
import ca.uqac.lif.petitpoucet.NodeFactory;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.ExplanationQueryable;
import ca.uqac.lif.petitpoucet.function.vector.NthElement;
import ca.uqac.lif.synthia.NthSuccessiveOutput;
import ca.uqac.lif.synthia.Picker;

/**
 * Generates a vector by independently picking a value for each of
 * its coordinates. If each of the pickers define a possible range of
 * values, this picker amounts to generating points inside a
 * multi-dimensional "prism" --hence its name.
 */
public class PrismPicker implements VectorPicker, ExplanationQueryable
{
	/**
	 * The pickers for each dimension of the vector
	 */
	protected Picker<Float>[] m_dimensions;
	
	/**
	 * Creates a new prism picker.
	 * @param dimensions The pickers used to generate values for
	 * each of the dimensions of the vector. If given <i>n</i>
	 * arguments, the resulting picker will produce <i>n</i>-dimensional
	 * vectors.
	 */
	@SuppressWarnings("unchecked")
	public PrismPicker(Picker<Float> ... dimensions)
	{
		super();
		m_dimensions = dimensions;
	}
	
	@Override
	public void reset()
	{
		for (Picker<Float> p : m_dimensions)
		{
			p.reset();
		}
	}

	@Override
	public float[] pick()
	{
		float[] v = new float[m_dimensions.length];
		for (int i = 0; i < m_dimensions.length; i++)
		{
			v[i] = m_dimensions[i].pick();
		}
		return v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PrismPicker duplicate(boolean with_state)
	{
		Picker<Float>[] dimensions = new Picker[m_dimensions.length];
		for (int i = 0; i < m_dimensions.length; i++)
		{
			dimensions[i] = m_dimensions[i].duplicate(with_state);
		}
		return new PrismPicker(dimensions);
	}
	
	@Override
	public int getDimension()
	{
		return m_dimensions.length;
	}

	@Override
	public PartNode getExplanation(Part p)
	{
		return getExplanation(p, NodeFactory.getFactory());
	}

	@Override
	public PartNode getExplanation(Part p, NodeFactory f)
	{
		PartNode root = f.getPartNode(p, this);
		int index = -1, part_index = -1;
		Part head = p.head();
		if (head instanceof NthElement)
		{
			part_index = ((NthElement) head).getIndex();
			Part tail = p.tail();
			if (tail == null)
			{
				return null; // Invalid part
			}
			head = tail.head();
		}
		if (!(head instanceof NthSuccessiveOutput))
		{
			return null; // Invalid part
		}
		index = ((NthSuccessiveOutput) head).getIndex();
		if (index < 0)
		{
			return null; // Invalid part
		}
		if (part_index < 0)
		{
			// p points at the whole vector
			AndNode and = f.getAndNode();
			NthSuccessiveOutput in_out = new NthSuccessiveOutput(index);
			for (int i = 0; i < m_dimensions.length; i++)
			{
				PartNode pn = f.getPartNode(in_out, m_dimensions[i]);
				and.addChild(pn);
			}
			root.addChild(and);
			return root;
		}
		// p points at a number inside the vector
		Part new_p = ComposedPart.compose(new NthElement(part_index), new NthSuccessiveOutput(index));
		root.addChild(f.getPartNode(new_p, m_dimensions[part_index]));
		return root;
	}
}