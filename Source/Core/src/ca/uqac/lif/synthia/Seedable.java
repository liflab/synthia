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
 * Interface implemented by objects that can be seeded. The <em>seed</em>
 * is an integer value that is used to determine the internal state of an
 * object. It is principally used for {@link Picker} instances that rely
 * on an underlying random number generator.
 * 
 * @ingroup API
 */
public interface Seedable
{
	/**
	 * Set the seed of the random generator.
	 * @param seed The seed for the random generator.
	 */
	public Seedable setSeed(int seed);
}
