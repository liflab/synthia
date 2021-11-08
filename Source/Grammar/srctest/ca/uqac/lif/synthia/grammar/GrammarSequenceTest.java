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
package ca.uqac.lif.synthia.grammar;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.bullwinkle.BnfParser.InvalidGrammarException;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;

public class GrammarSequenceTest
{
	@Test
	public void testShrink1() throws InvalidGrammarException
	{
		BnfParser b_parser = new BnfParser(GrammarSentenceTest.class.getResourceAsStream("grammar.bnf"));
		b_parser.setStartRule("<S>");
		RandomInteger ri = new RandomInteger().setSeed(0);
		GrammarSequence<String> gs = new GrammarSequence<String>(b_parser, ri) {
			public String getObject(String s) {
				return s;
			}
			public String getToken(String s) {
				return s;
			}
		};
		List<String> original = gs.pick();
		Shrinkable<List<String>> gs_s = gs.shrink(original, RandomFloat.instance, 0.5f);
		List<String> shrunk = gs_s.pick();
		assertTrue(isSubList(shrunk, original));
	}
	
	/**
	 * Determines if a list is a sublist of another.
	 * @param shrunk The first list
	 * @param original The list used as a reference
	 * @return <tt>true</tt> if shrunk is a sublist of original,
	 * <tt>false</tt> otherwise
	 */
	protected static boolean isSubList(List<? extends Object> shrunk, List<? extends Object> original)
	{
		int pos = 0;
		for (Object o : shrunk)
		{
			pos = indexOf(original, o, pos);
			if (pos < 0)
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gets the first position of an element in a list, if it occurs after some
	 * given starting position.
	 * @param list The list
	 * @param o The element to look for
	 * @param position The starting position
	 * @return The position of the element, or -1 if the element does not occur
	 * after the starting position
	 */
	protected static int indexOf(List<? extends Object> list, Object o, int position)
	{
		for (int i = position; i < list.size(); i++)
		{
			if (list.get(i).equals(o))
			{
				return i;
			}
		}
		return -1;
	}
}
