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
 * Interface implemented by pickers whose picking of objects can be altered by
 * external information. To this end, a reactive picker provides a method
 * called {@link #tell(Object) tell}, which is used to pass an object that the
 * picker may use to modify its internal state or the way the next value will
 * be produced.
 * <p>
 * A simple example of a reactive picker is {@link RandomInteger}; in this
 * case, method <tt>tell</tt> is used to modify the upper bound of its range
 * of possible values. {@link RandomBoolean} uses <tt>tell</tt> to modify the
 * probability of producing value <tt>true</tt>, and {@link PoissonInteger}
 * uses it to modify its parameter &lambda;.
 * <p>
 * Reactive pickers are especially useful in the context of testing of an
 * interactive component, such as a user interface.
 * 
 * @author Sylvain Hallé
 *
 * @param <T> The type of objects returned by the picker
 * @param <U> The type of objects used in a notification to the picker
 * @ingroup API
 */
public interface Reactive<U,T> extends Picker<T>
{
	/**
	 * Notifies a picker of some external information.
	 * @param u The information
	 */
	public void tell(U u);
}
