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
package ca.uqac.lif.synthia;

/**
 * Picks an object. This is the interface that most classes in this library
 * implement.
 * <ul>
 * <li>Objects of a given type <tt>T</tt> are obtained by calling the
 * {@link #pick()} method.</li>
 * <li>The picker can be put back into its initial state by calling its
 * {@link #reset()} method.</li>
 * <li>Copies of the picker can be made by calling its {@link #duplicate(boolean)}
 * method. One can ask whether the copied is put in the initial state, or
 * in the same state as the picker it is copied from.</li>
 * </ul>
 * @param <T> The type of object that the picker picks
 * @ingroup API
 */
public interface Picker<T>
{
	/**
	 * Puts the picker back into its initial state. This means that the
	 * sequence of calls to {@link #pick()} will produce the same values
	 * as when the object was instantiated.
	 */
	public void reset();
	
	/**
	 * Picks an object. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The object
	 */
	/*@ null @*/ public T pick();
	
	/**
	 * Creates a copy of the picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the picker
	 */
	/*@ non_null @*/ public Picker<T> duplicate(boolean with_state);	
}
