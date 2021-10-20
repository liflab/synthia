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
package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.petitpoucet.NodeFactory;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.ExplanationQueryable;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.exception.NoMoreElementException;

/**
 * A {@link RelativePicker} that only throws a {@link NoMoreElementException}
 * when the {@link #pick()} method is called.
 *
 * @param <T> The type of the picker (but not really important here).
 */
public class NothingPicker<T> implements Shrinkable<T>, ExplanationQueryable
{
	/**
	 * Creates a new instance of the picker.
	 */
	public NothingPicker()
	{
		super();
	}
	
	@Override
	public NothingPicker<T> shrink(T o)
	{
		return new NothingPicker<T>();
	}

	@Override
	public void reset()
	{
		//Nothing to do here.
	}

	@Override
	public T pick()
	{
		throw  new NoMoreElementException();
	}

	@Override
	public Picker<T> duplicate(boolean with_state)
	{
		return new NothingPicker<T>();
	}

	@Override
	public PartNode getExplanation(Part p)
	{
		return getExplanation(p, NodeFactory.getFactory());
	}

	@Override
	public PartNode getExplanation(Part p, NodeFactory arg1)
	{
		// Nothing is the end of the chain
		return null;
	}
}
