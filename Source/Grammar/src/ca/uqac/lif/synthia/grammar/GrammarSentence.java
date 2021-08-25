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

import ca.uqac.lif.bullwinkle.*;
import ca.uqac.lif.synthia.IndexPicker;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomInteger;

import java.util.List;

/**
 * Picker that generates sentences from a format grammar. The picker's constructor
 * takes as input an instance of a parser from the
 * <a href="https://github.com/sylvainhalle/Bullwinkle">Bullwinkle</a> library,
 * which is defined with respect to an underlying BNF grammar.
 * Each time its {@link #pick()} method is called, the picker starts from the
 * initial symbol of the grammar and randomly selects one of the rule's cases; it
 * recursively expands this case until the expression is only composed of terminal
 * symbols, and returns that expression.
 */
public class GrammarSentence implements Picker<String>
{
	/**
	 * The character used to separate the tokens
	 */
	protected static final transient String SPACE = " ";
	
	/**
	 * The parser whose grammar will be used to generate expressions
	 */
	/*@ non_null @*/ protected BnfParser m_parser;
	
	/**
	 * A picker used to choose the cases from the grammar rules
	 * to expand
	 */
	/*@ non_null @*/ protected RandomInteger m_indexPicker;
	
	/**
	 * Creates a new instance of the picker
	 * @param parser The parser whose grammar will be used to generate expressions
	 * @param picker A picker used to choose the cases from the grammar rules
	 * to expand
	 */
	public GrammarSentence(/*@ non_null @*/ BnfParser parser, /*@ non_null @*/ RandomInteger picker)
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
	public String pick() 
	{
		return pickRecursive(m_parser.getStartRule());
	}
	
	/**
	 * Recursively expands a non-terminal symbol from the grammar. The
	 * recursion stops when it reaches a terminal symbol.
	 * @param rule The rule to expand
	 * @return The resulting expression
	 */
	protected String pickRecursive(BnfRule rule)
	{
		List<TokenString> alternatives = rule.getAlternatives();
		int chosen_index = m_indexPicker.setInterval(0,alternatives.size()).pick();
		TokenString chosen_string = alternatives.get(chosen_index);
		StringBuilder out = new StringBuilder();
		for (Token t : chosen_string)
		{
			if (t instanceof StringTerminalToken)
			{
				out.append(t.getName()).append(SPACE);
			}
			else if (t instanceof NumberTerminalToken)
			{
				out.append(t.getName()).append(SPACE);
			}
			else if (t instanceof RegexTerminalToken)
			{
				out.append(t.getName());
			}
			else if (t instanceof EpsilonTerminalToken)
			{
				// Nothing to add
			}
			else if (t instanceof TerminalToken)
			{
				out.append(t.getName()).append(SPACE);
			}
			if (t instanceof NonTerminalToken)
			{
				BnfRule child_rule = m_parser.getRule(t.getName());
				if (child_rule != null)
				{
					out.append(pickRecursive(child_rule));
				}
			}
		}
		return out.toString();
	}

	@Override
	public GrammarSentence duplicate(boolean with_state) 
	{
		return new GrammarSentence(m_parser, m_indexPicker.duplicate(with_state));
	}
}
