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
package ca.uqac.lif.synthia.test;

/**
 * Interface declaring that an object can be tested. It declares a single
 * method called {@link #test(Object...) test}, which an object like
 * {@link Assert} can use to automate fuzz testing.
 * @author Sylvain Hallé
 * @ingroup API
 */
public interface Testable
{
	/**
	 * Runs a test on the object.
	 * @param parameters The parameters given to the test.
	 * @return The value {@code true} if the test succeeds, {@code false}
	 * it it fails. Note that the test can also fail by throwing an
	 * exception.
	 * @throws RuntimeException An exception that can be thrown to indicate
	 * the failure of the test 
	 */
	public boolean test(Object ... parameters) throws RuntimeException;
}
