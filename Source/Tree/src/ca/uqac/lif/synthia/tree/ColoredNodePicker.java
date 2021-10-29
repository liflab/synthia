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
package ca.uqac.lif.synthia.tree;

import ca.uqac.lif.synthia.Picker;

/**
 * 
 * @author Sylvain Hallé
 * @ingroup API
 */
public class ColoredNodePicker implements Picker<Node<String>>
{
	/**
	 * A picker to select the color for a node.
	 */
	protected Picker<String> m_colorPicker;

	/**
	 * Creates a new colored node picker.
	 * @param color_picker A picker to select the color for a node
	 */
	public ColoredNodePicker(Picker<String> color_picker)
	{
		super();
		m_colorPicker = color_picker;
	}
	
	@Override
	public void reset() 
	{
		m_colorPicker.reset();
	}

	@Override
	public Node<String> pick() 
	{
		return new Node<String>(m_colorPicker.pick());
	}

	@Override
	public ColoredNodePicker duplicate(boolean with_state) 
	{
		return new ColoredNodePicker(m_colorPicker.duplicate(with_state));
	}
}
