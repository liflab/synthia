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
package ca.uqac.lif.synthia;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.petitpoucet.ComposedPart;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.function.NthInput;
import ca.uqac.lif.petitpoucet.function.NthOutput;
import ca.uqac.lif.petitpoucet.function.vector.NthElement;

/**
 * A {@link Part} pointing to the n-th output produced by a picker since its
 * last call to {@link Picker#reset() reset()}.
 */
public class NthSuccessiveOutput implements Part
{
	/**
	 * A static reference to an instance of the class with index = 0, thereby
	 * referring to the first output produced by a picker.
	 */
	public static final NthSuccessiveOutput FIRST = new NthSuccessiveOutput(0);
	
	/**
	 * The index in the sequence of the output since the last reset.
	 */
	public int m_index;
	
	/**
	 * Creates a new instance of the part.
	 * @param index The index in the sequence of the output since the last reset
	 */
	public NthSuccessiveOutput(int index)
	{
		super();
		m_index = index;
	}
	
	/**
	 * Returns the index in the sequence of the output since the last reset.
	 * @return The index
	 */
	public int getIndex()
	{
		return m_index;
	}
	
	@Override
	public String toString()
	{
		return "Output value " + m_index;
	}
	
	@Override
	public boolean appliesTo(Object o)
	{
		return o instanceof Picker;
	}

	@Override
	public Part head()
	{
		return this;
	}

	@Override
	public Part tail()
	{
		return null;
	}
	
	/**
	 * Given an arbitrary designator, replaces the first occurrence of
	 * {@link NthOutput} by an instance of {@link NthInput} with given index.
	 * @param from The original part
	 * @param to The part to replace it with
	 * @return The new designator. The input object is not modified if it does
	 * not contain {@code d}
	 */
	/*@ non_null @*/ public static Part replaceOutIndexBy(/*@ non_null @*/ Part from, int index)
	{
		Part to = FIRST;
		if (index > 0)
		{
			to = new NthSuccessiveOutput(index);
		}
		if (from instanceof NthSuccessiveOutput)
		{
			return to;
		}
		if (from instanceof ComposedPart)
		{
			ComposedPart cd = (ComposedPart) from;
			List<Part> desigs = new ArrayList<Part>();
			boolean replaced = false;
			for (int i = 0 ; i < cd.size(); i++)
			{
				Part in_d = cd.get(i);
				if (in_d instanceof NthSuccessiveOutput && !replaced)
				{
					desigs.add(to);
					replaced = true;
				}
				else
				{
					desigs.add(in_d);
				}
			}
			if (!replaced)
			{
				// Return input object if no replacement was done
				return from;
			}
			return new ComposedPart(desigs);
		}
		return from;
	}
	
	/**
	 * Retrieves the output sequence index mentioned in a designator.
	 * @param d The designator
	 * @return The output sequence index, or -1 if no output index is mentioned
	 */
	public static int mentionedOutput(Part d)
	{
		if (d instanceof NthSuccessiveOutput)
		{
			return ((NthSuccessiveOutput) d).getIndex();
		}
		if (d instanceof ComposedPart)
		{
			ComposedPart cd = (ComposedPart) d;
			for (int i = 0; i < cd.size(); i++)
			{
				Part p = cd.get(i);
				if (p instanceof NthSuccessiveOutput)
				{
					return ((NthSuccessiveOutput) p).getIndex();
				}
			}
		}
		return -1;
	}
	
	/**
	 * Removes the instance of NthElement that stands just before the first
	 * instance of NthSuccessiveOutput in a composed designator.
	 * @param from The original composed designator
	 * @return The transformed designator
	 */
	public static Part removeNthElement(Part from)
	{
		if (from instanceof NthElement || !(from instanceof ComposedPart))
		{
			return Part.nothing;
		}
		ComposedPart cd = (ComposedPart) from;
		List<Part> desigs = new ArrayList<Part>();
		boolean replaced = false;
		for (int i = 0 ; i < cd.size(); i++)
		{
			Part in_d = cd.get(i);
			if (in_d instanceof NthElement && !replaced && i < cd.size() - 1 && cd.get(i + 1) instanceof NthSuccessiveOutput)
			{
				// We skip this designator, which deletes in the output part
				replaced = true;
			}
			else
			{
				desigs.add(in_d);
			}
		}
		if (!replaced)
		{
			// Return input object if no replacement was done
			return from;
		}
		return new ComposedPart(desigs);
	}
}
