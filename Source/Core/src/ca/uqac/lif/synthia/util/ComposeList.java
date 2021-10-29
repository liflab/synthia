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
package ca.uqac.lif.synthia.util;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.petitpoucet.AndNode;
import ca.uqac.lif.petitpoucet.NodeFactory;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.ExplanationQueryable;
import ca.uqac.lif.petitpoucet.function.vector.NthElement;
import ca.uqac.lif.synthia.NthSuccessiveOutput;
import ca.uqac.lif.synthia.Picker;

/**
 * 
 * @author Sylvain Hallé
 *
 * @param <T>
 * @ingroup API
 */
public class ComposeList<T> implements Picker<List<T>>, ExplanationQueryable
{
	/**
	 * The picker providing elements for the list.
	 */
	/*@ non_null @*/ protected Picker<T> m_elements;

	/**
	 * The picker deciding on the length of the list.
	 */
	/*@ non_null @*/ protected Picker<Integer> m_length;

	/**
	 * A list keeping track of the length of each list produced by the picker.
	 * This list is used to answer calls to
	 * {@link #getExplanation(Part, NodeFactory) getExplanation()}.
	 */
	/*@ non_null @*/ protected List<Integer> m_lengths;

	/**
	 * Creates a new instance of the picker.
	 * @param elements The picker providing elements for the list
	 * @param length The picker deciding on the length of the list
	 */
	public ComposeList(Picker<T> elements, Picker<Integer> length)
	{
		super();
		m_elements = elements;
		m_length = length;
		m_lengths = new ArrayList<Integer>();
	}
	
	/**
	 * Creates a new instance of the picker.
	 * @param elements The picker providing elements for the list
	 * @param length The fixed length of each list
	 */
	public ComposeList(Picker<T> elements, int length)
	{
		this(elements, new Constant<Integer>(length));
	}

	@Override
	public void reset()
	{
		m_length.reset();
		m_elements.reset();
	}

	@Override
	public List<T> pick()
	{
		int len = m_length.pick();
		m_lengths.add(len);
		List<T> list = new ArrayList<T>(len);
		for (int i = 0; i < len; i++)
		{
			list.add(m_elements.pick());
		}
		return list;
	}

	@Override
	public ComposeList<T> duplicate(boolean with_state)
	{
		ComposeList<T> cl = new ComposeList<T>(m_elements.duplicate(with_state), m_length.duplicate(with_state));
		if (with_state)
		{
			cl.m_lengths.addAll(m_lengths);
		}
		return cl;
	}

	@Override
	public PartNode getExplanation(Part p)
	{
		return getExplanation(p, NodeFactory.getFactory());
	}
	
	@Override
	public String toString()
	{
		return "ComposeList";
	}

	@Override
	public PartNode getExplanation(Part p, NodeFactory f)
	{
		PartNode root = f.getPartNode(p, this);
		AndNode and = f.getAndNode();
		root.addChild(and);
		int index = NthSuccessiveOutput.mentionedOutput(p), part_index = NthElement.mentionedElement(p);
		if (index < 0 || index >= m_lengths.size())
		{
			return null; // Invalid part
		}
		int offset = 0;
		for (int i = 0; i < index; i++)
		{
			offset += m_lengths.size();
		}
		if (part_index < 0)
		{
			// p points at the whole list
			for (int i = 0; i < m_lengths.get(index); i++)
			{
				Part new_p = NthSuccessiveOutput.replaceOutIndexBy(p, offset + i);
				and.addChild(f.getPartNode(new_p, m_elements));
			}
			return root;
		}
		// p points at an element of the list
		if (part_index >= m_lengths.get(index))
		{
			// Nonexistent element
			return root;
		}
		Part new_p = NthSuccessiveOutput.removeNthElement(p);
		new_p = NthSuccessiveOutput.replaceOutIndexBy(new_p, offset + part_index);
		and.addChild(f.getPartNode(new_p, m_elements));
		return root;
	}
}
