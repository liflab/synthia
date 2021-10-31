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

import ca.uqac.lif.synthia.relative.PickIf;

/**
 * An exception to throw to prevent the pick method of a {@link PickIf} picker
 * from falling into an infinite loop.
 * @ingroup API
 */
public class GiveUpException extends PickerException
{
	/**
	 * Dummy UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The default message associated to this exception.
	 */
	private static final String m_message = "Maximum number of iterations reached. This exception was"
			+ " thrown to prevent a PickIf picker from falling into an infinite loop.";

	/**
	 * Creates a new exception with the default message.
	 */
	public GiveUpException() { super(m_message); }

	/**
	 * Creates a new exception.
	 * @param message The message associated to this exception
	 */
	public GiveUpException(String message) { super(message); }

	/**
	 * Creates a new exception.
	 * @param t The throwable associated to this exception
	 */
	public GiveUpException(Throwable t) { super(t); }
}
