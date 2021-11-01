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

/**
 * Interface signaling that a picker can be shrunk. In the context,
 * <em>shrinking</em> means that given an element e, the picker produces a
 * new picker instance producing only values that are "smaller" than e.
 * <p>
 * The interface defines two versions of <tt>shrink</tt>: the first takes
 * only a reference object, while the second also accepts a source of
 * randomness and a "magnitude" parameter.
 * 
 * @author Sylvain Hallé
 *
 * @param <T> The type of the objects to return
 * @see SequenceShrinkable
 * @ingroup API
 */
public interface Shrinkable<T> extends Picker<T>
{
	/**
	 * Shrinks a picker. The method adds two arguments with respect to
	 * {@link #shrink(Object)}: a source of randomness and a magnitude parameter.
	 * A picker may use the magnitude to determine how "aggressive" is the
	 * shrinking process. A value of 1 is expected to produce a picker with
	 * the broadest set, and may return all possible objects that are smaller
	 * than the reference. Lower values (all the way down to 0) instruct
	 * the picker to exclude further objects, and typically to only produce
	 * objects that are relatively "much smaller" than the reference.
	 * 
	 * @param o The reference object. The picker must guarantee that the returned
	 * picker instance only produces objects that are smaller than o, according
	 * to an implicit ordering relation that is specific to each object type and
	 * each picker.
	 * @param d A source of randomness. Some pickers must make choices when
	 * producing shrunk objects, and this parameter is used as an external source
	 * for these choices.
	 * @param m A magnitude parameter, which must be between 0 and 1.
	 * @return The returned picker instance. This object may be of a different
	 * class from the object on which the method is called.
	 */
	/*@ non_null @*/ public Shrinkable<T> shrink(T o, Picker<Float> d, float m);
	
	/**
	 * Shrinks a picker with default parameters. For any picker <tt>p</tt> that
	 * implements the {@link Shrinkable} interface, a call to <tt>p.shrink(o)</tt>
	 * <em>should</em> be the same as a call to
	 * <tt>p.shrink(o, RandomFloat.instance, 1)</tt>.
	 * 
	 * @param o The reference object. The picker must guarantee that the returned
	 * picker instance only produces objects that are smaller than o, according
	 * to an implicit ordering relation that is specific to each object type and
	 * each picker.
	 * @return The returned picker instance. This object may be of a different
	 * class from the object on which the method is called.
	 * @see #shrink(Object, Picker, float)
	 */
	/*@ non_null @*/ public Shrinkable<T> shrink(T o);
}
