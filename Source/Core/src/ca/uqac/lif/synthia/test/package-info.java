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

/**
 * Classes that enable Synthia to operate as a fuzz testing tool. The two
 * main classes in this package are:
 * <ul>
 * <li>{@link Assert}, which can fuzz-test a function given a Boolean oracle,
 * and then perform automated shrinking of any value that violates the
 * condition</li> 
 * <li>{@link Monkey}, which can do monkey testing on a reactive
 * component, and  and then perform automated shrinking of any sequence of
 * actions that causes an exception to be thrown</li> 
 * </ul>
 * @ingroup API
 */
package ca.uqac.lif.synthia.test;