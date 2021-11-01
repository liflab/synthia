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

import java.util.List;

/**
 * Signals that a picker can shrink the sequence of values it has produced
 * since its last reset. Note that this is different from shrinking a
 * particular <em>value</em>, which corresponds to the {@link Shrinkable}
 * interface.
 * <p>
 * A call to {@link #shrink(Picker, float)} is expected to return a picker
 * producing a <em>finite</em> subsequence of that produced by the original
 * picker so far. For example, if calls to {@link #pick()} on a picker has
 * returned the numbers 3, 1, 4, 1, 5, a call to {@link #shrink(Picker, float)
 * shrink} at this point will produce a picker that can output any
 * subsequence of [3, 1, 4, 1, 5]. Successive calls to {@link
 * #shrink(Picker, float) shrink} may produce different picker instances
 * returning different sub-sequences. This is controlled by the
 * <tt>Picker&lt;Float&gt;</tt> passed to the method.
 * <p>
 * Similar to {@link Shrinkable}
 * 
 * @author Sylvain Hallé
 *
 * @param <T> The type of the objects to return
 * @see Shrinkable
 * @ingroup API
 */
public interface SequenceShrinkable<T> extends Bounded<T>
{
	/**
	 * Shrinks a picker. The method has two arguments: a source of randomness
	 * and a magnitude parameter.
	 * A picker may use the magnitude to determine how "aggressive" is the
	 * shrinking process. A value of 1 is expected to produce a picker with
	 * the broadest set, and may return any subsequence of its past values.
	 * Lower values (all the way down to 0) instruct
	 * the picker to exclude further elements, and typically to only produce
	 * sub-sequences that are relatively "much smaller" than the reference.
	 * 
	 * @param d A source of randomness. Some pickers must make choices when
	 * producing shrunk sequences, and this parameter is used as an external
	 * source for these choices.
	 * @param magnitude A magnitude parameter, which must be between 0 and 1.
	 * @return The returned picker instance. This object may be of a different
	 * class from the object on which the method is called.
	 */
	public SequenceShrinkable<T> shrink(Picker<Float> d, float magnitude);
	
	/**
	 * Gets the sequence of values that the picker has produced so far.
	 * @return The sequence of values
	 */
	public List<T> getSequence();
}
