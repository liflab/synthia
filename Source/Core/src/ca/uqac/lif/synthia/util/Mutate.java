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
package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.Picker;

/**
 * Transforms an object from a picker by selecting a mutator and applying it
 * to an object. Concretely, this picker is instantiated by
 * providing two things:
 * <ol>
 * <li>An arbitrary {@link Picker} producing the objects of type <tt>T</tt>
 * to transform</li>
 * <li>Another picker providing a {@link Mutator} for objects of type
 * <tt>T</tt></li>
 * </ol>
 * On each call to {@link Picker#pick() pick()}, an object and a mutator
 * are obtained, and the mutator is applied to the object. The resulting
 * "mutated" object is then returned.
 * <p>
 * As with any other picker, both sources are arbitrary. For example, one
 * could use a {@link Choice} picker to provide a randomly-selected mutation
 * from a set of choices to apply to each object.
 * 
 * @param <T> The type of the objects transformed by this picker
 * @ingroup API
 */
public class Mutate<T> extends Mutator<T>
{
	/**
	 * Mutates input objects
	 */
	/*@ non_null @*/ protected Picker<Mutator<T>> m_mutations;

	public Mutate(/*@ non_null @*/ Picker<? extends T> picker, /*@ non_null @*/ Picker<Mutator<T>> mutations)
	{
		super(picker);
		m_mutations = mutations;
	}

	@Override
	public T pick()
	{
		Mutator<T> mutator = m_mutations.pick();
		mutator.setPicker(m_picker);
		return mutator.pick();
	}

	@Override
	public Picker<T> duplicate(boolean with_state)
	{
		return new Mutate<T>(m_picker.duplicate(with_state), m_mutations.duplicate(with_state));
	}
}
