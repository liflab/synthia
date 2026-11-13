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
package ca.uqac.lif.synthia.widget;

import java.awt.event.ActionEvent;
import java.util.Set;

import ca.uqac.lif.synthia.Reactive;

public class ChooseAction implements Reactive<Set<Object>,ActionEvent>
{
	/**
	 * The set of objects on which actions can be made.
	 */
	Set<Object> m_contents;
	
	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionEvent pick()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChooseAction duplicate(boolean with_state)
	{
		return new ChooseAction();
	}

	@Override
	public void tell(Set<Object> objects)
	{
		m_contents = objects;
	}
}
