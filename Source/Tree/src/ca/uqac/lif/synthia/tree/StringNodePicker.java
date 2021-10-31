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
package ca.uqac.lif.synthia.tree;

import ca.uqac.lif.synthia.Picker;

public class StringNodePicker extends NodePicker<String>
{
	/**
	 * Creates a new instance of the picker.
	 * @param label_picker The picker for the nodes' labels
	 */
	public StringNodePicker(Picker<String> label_picker)
	{
		super(label_picker);
	}

	@Override
	public Node<String> pick()
	{
		return new Node<String>(m_labelPicker.pick());
	}

	@Override
	public StringNodePicker duplicate(boolean with_state)
	{
		return new StringNodePicker(m_labelPicker.duplicate(with_state));
	}
}