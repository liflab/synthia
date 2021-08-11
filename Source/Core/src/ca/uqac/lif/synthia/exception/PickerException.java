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
package ca.uqac.lif.synthia.exception;

import ca.uqac.lif.synthia.Picker;

/**
 * Generic runtime exception thrown by the {@link Picker}
 * interface.
 */
public class PickerException extends RuntimeException
{
	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new picker exception from a throwable
	 * @param t The throwable
	 */
	public PickerException(Throwable t)
	{
		super(t);
	}
	
	/**
	 * Creates a new picker exception from a message
	 * @param message The message
	 */
	public PickerException(String message)
	{
		super(message);
	}
}