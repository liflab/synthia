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
package ca.uqac.lif.synthia.grammar;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.bullwinkle.BnfRule;
import ca.uqac.lif.bullwinkle.EpsilonTerminalToken;
import ca.uqac.lif.bullwinkle.NonTerminalToken;
import ca.uqac.lif.bullwinkle.TerminalToken;
import ca.uqac.lif.bullwinkle.Token;
import ca.uqac.lif.bullwinkle.TokenString;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Reactive;

/**
 * Produces a sequence of objects from a grammar.
 * @author Sylvain Hallé
 *
 * @param <T> The type of objects to produce
 */
public class GrammarSequence<T> implements Picker<List<T>>
{
	/**
	 * The parser whose grammar will be used to generate expressions
	 */
	/*@ non_null @*/ protected BnfParser m_parser;
	
	/**
	 * A picker used to choose the cases from the grammar rules
	 * to expand
	 */
	/*@ non_null @*/ protected Reactive<Integer,Integer> m_indexPicker;

	/**
	 * Creates a new instance of the picker
	 * @param parser The parser whose grammar will be used to generate expressions
	 * @param picker A picker used to choose the cases from the grammar rules
	 * to expand
	 */
	public GrammarSequence(/*@ non_null @*/ BnfParser parser, /*@ non_null @*/ Reactive<Integer,Integer> picker)
	{
		super();
		m_parser = parser;
		m_indexPicker = picker;
	}

	@Override
	public void reset() 
	{
		m_indexPicker.reset();
	}

	@Override
	public List<T> pick() 
	{
		return pickRecursive(m_parser.getStartRule());
	}
	
	/**
	 * Recursively expands a non-terminal symbol from the grammar. The
	 * recursion stops when it reaches a terminal symbol.
	 * @param rule The rule to expand
	 * @return The resulting expression
	 */
	protected List<T> pickRecursive(BnfRule rule)
	{
		List<TokenString> alternatives = rule.getAlternatives();
		m_indexPicker.tell(alternatives.size());
		int chosen_index = m_indexPicker.pick();
		TokenString chosen_string = alternatives.get(chosen_index);
		List<T> out_list = new ArrayList<T>();
		for (Token t : chosen_string)
		{
			if (t instanceof EpsilonTerminalToken)
			{
				// Nothing to add
			}
			else if (t instanceof TerminalToken)
			{
				out_list.add(get(t.getName()));
			}
			if (t instanceof NonTerminalToken)
			{
				BnfRule child_rule = m_parser.getRule(t.getName());
				if (child_rule != null)
				{
					out_list.addAll(pickRecursive(child_rule));
				}
			}
		}
		return out_list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GrammarSequence<T> duplicate(boolean with_state)
	{
		return new GrammarSequence<T>(m_parser, (Reactive<Integer, Integer>) m_indexPicker.duplicate(with_state));
	}
	
	/**
	 * Gets an object associated to a terminal token of the grammar. This object
	 * does not need to be the same every time the method is called for a given
	 * terminal token.
	 * @param token The string corresponding to the terminal token
	 * @return An object of type <tt>T</tt> that the picker associates
	 * to this terminal token.
	 */
	protected T get(String token)
	{
		return null;
	}

}
