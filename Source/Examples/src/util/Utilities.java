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
package util;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Object providing a few utility methods to simplify the examples in this
 * project.
 */
public class Utilities
{
	/**
	 * Prints an object to a print stream.
	 * @param ps The print stream to print to
	 * @param o The object
	 */
	public static void print(PrintStream ps, Object o)
	{
		if (o == null)
		{
			ps.print("null");
		}
		else if (o.getClass().isArray())
		{
			ps.print("[");
			if (o.getClass().getComponentType().isPrimitive()) 
			{
				int length = Array.getLength(o);
				for (int i = 0; i < length; i++) 
				{
					Object obj = Array.get(o, i);
					if (i > 0)
					{
						ps.print(",");
					}
					print(ps, obj);
				}
			}
			else 
			{
				Object[] objects = (Object[]) o;
				for (int i = 0; i < objects.length; i++)
				{
					if (i > 0)
					{
						ps.print(",");
					}
					print(ps, objects[i]);
				}
			}
			ps.print("]");
		}
		else if (o instanceof Collection)
		{
			boolean first = true;
			Collection<?> array = (Collection<?>) o;
			ps.print("[");
			for (Object e : array)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					ps.print(",");
				}
				print(ps, e);
			}
			ps.print("]");
		}
		else if (o instanceof Number)
		{
			Number n = (Number) o;
			if (n.intValue() == n.floatValue())
			{
				ps.print(n.intValue());
			}
			else
			{
				ps.print(n.floatValue());
			}
		}
	}

	/**
	 * Calls {@link #print(PrintStream, Object)} and appends a new line.
	 * @param ps The print stream to print to
	 * @param o The object
	 */
	public static void println(PrintStream ps, Object o)
	{
		print(ps, o);
		ps.println();
	}
}
