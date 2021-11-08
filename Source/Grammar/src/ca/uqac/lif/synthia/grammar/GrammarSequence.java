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
import ca.uqac.lif.bullwinkle.ParseNode;
import ca.uqac.lif.bullwinkle.TerminalToken;
import ca.uqac.lif.bullwinkle.Token;
import ca.uqac.lif.bullwinkle.TokenString;
import ca.uqac.lif.bullwinkle.BnfParser.InvalidGrammarException;
import ca.uqac.lif.bullwinkle.BnfParser.ParseException;
import ca.uqac.lif.synthia.CannotShrinkException;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.PickerException;
import ca.uqac.lif.synthia.Reactive;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;

/**
 * Produces a sequence of objects from a grammar.
 * @author Sylvain Hallé
 *
 * @param <T> The type of objects in the lists to produce
 */
public class GrammarSequence<T> extends ParserPicker<T> implements Shrinkable<List<T>>
{
	/**
	 * A string corresponding to the last sequence derived by this picker.
	 */
	protected String m_reference;

	/**
	 * Creates a new instance of the picker.
	 * @param parser The parser whose grammar will be used to generate expressions
	 * @param picker A picker used to choose the cases from the grammar rules
	 * to expand
	 */
	public GrammarSequence(/*@ non_null @*/ BnfParser parser, /*@ non_null @*/ Reactive<Integer,Integer> picker)
	{
		super(parser, picker);
		m_reference = null;
	}

	@Override
	public List<T> pick() 
	{
		StringBuilder out_string = new StringBuilder();
		List<T> list = pickRecursive(m_parser.getStartRule(), out_string);
		m_reference = out_string.toString();
		return list;
	}

	/**
	 * Recursively expands a non-terminal symbol from the grammar. The
	 * recursion stops when it reaches a terminal symbol.
	 * @param rule The rule to expand
	 * @return The resulting expression
	 */
	protected List<T> pickRecursive(BnfRule rule, StringBuilder out_string)
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
				out_list.add(getObject(t.getName()));
				out_string.append(t.getName()).append(" ");
			}
			if (t instanceof NonTerminalToken)
			{
				BnfRule child_rule = m_parser.getRule(t.getName());
				if (child_rule != null)
				{
					out_list.addAll(pickRecursive(child_rule, out_string));
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
	protected T getObject(String token)
	{
		return null;
	}

	/**
	 * Gets the terminal token from the grammar that produces a given output object.
	 * @param object The object
	 * @return The terminal token
	 */
	protected String getToken(T object)
	{
		return null;
	}

	protected String rebuildString(List<T> list)
	{
		StringBuilder s = new StringBuilder();
		for (T t : list)
		{
			String token = getToken(t);
			if (token != null)
			{
				s.append(token).append(" ");
			}
		}
		return s.toString().trim();
	}

	@Override
	public Shrinkable<List<T>> shrink(List<T> o, Picker<Float> d, float m)
	{
		if (m_reference == null)
		{
			throw new CannotShrinkException("No reference string to shrink");
		}
		return new GrammarSequenceRelative(d, o, m);
	}

	@Override
	public Shrinkable<List<T>> shrink(List<T> o)
	{
		return shrink(o, RandomFloat.instance, 1f);
	}

	/**
	 * Produces a sequence of objects from a grammar that is a subsequence of
	 * another one.
	 * @author Sylvain Hallé
	 */
	public class GrammarSequenceRelative implements Shrinkable<List<T>>
	{
		protected ParseNode m_tree;

		protected float m_magnitude;

		protected List<T> m_reference;

		protected Picker<Float> m_decision;

		public GrammarSequenceRelative(Picker<Float> decision, List<T> reference, float magnitude)
		{
			super();
			m_reference = reference;
			String s_ref = rebuildString(reference);
			try
			{
				m_tree = m_parser.parse(s_ref);
			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_decision = decision;
			m_magnitude = magnitude;
		}

		@Override
		public List<T> pick()
		{
			ParseNode trimmed = m_tree.duplicate();
			trimTree(trimmed);
			List<T> list = new ArrayList<T>();
			getTrimmedString(trimmed, list);
			return list;
		}

		/**
		 * Applies shrinking to the parse tree of a given string.
		 * @param node The root of the tree where shrinking should occur
		 * @return {@code true} if the tree has been shrunk, {@code false} if it
		 * remained intact
		 */
		protected boolean trimTree(ParseNode node)
		{
			if (node.getChildren().isEmpty())
			{
				// Terminal token, must keep it
				return false;
			}
			boolean shrunk = false;
			try
			{
				List<Integer[]> alts = getSubAlternatives(node);
				if (!alts.isEmpty() && shrinkDecide())
				{
					int alt_index = (int) Math.floor(alts.size() * m_decision.pick());
					Integer[] alt = alts.get(alt_index);
					deleteChildren(node, alt);
					shrunk = true;
				}
			}
			catch (InvalidGrammarException e)
			{
				throw new PickerException(e);
			}
			if (!shrunk || shrinkDecide())
			{
				for (ParseNode child : node.getChildren())
				{
					trimTree(child);
				}
			}
			return shrunk;
		}

		/**
		 * Deletes all children of a parse node except those occurring at specific
		 * positions.
		 * @param node The node
		 * @param positions The array of positions to be <em>kept</em>
		 */
		protected void deleteChildren(ParseNode node, Integer[] positions)
		{
			if (positions.length == 0)
			{
				for (int i = node.getChildren().size() - 1; i >= 0; i--)
				{
					node.deleteChild(i);
				}
			}
			else
			{
				int index = positions.length - 1;
				for (int i = node.getChildren().size() - 1; i >= 0; i--)
				{
					if (i != positions[index])
					{
						node.deleteChild(i);
					}
					else
					{
						index--;
					}
				}
			}
		}

		/**
		 * Decides if a shrinking operation should take place based on a coin toss.
		 * @return {@code true} if shrinking should occur, {@code false} otherwise
		 */
		protected boolean shrinkDecide()
		{
			return m_decision.pick() * m_magnitude < 0.5;
		}

		protected void getTrimmedString(ParseNode tree, List<T> list)
		{
			if (tree.getChildren().size() > 0 || tree.getToken().startsWith("<")) // non-terminal
			{
				for (ParseNode child : tree.getChildren())
				{
					getTrimmedString(child, list);
				}
			}
			else // terminal
			{
				if (!tree.getToken().isEmpty())
				{
					T t = getObject(tree.getToken());
					if (t != null)
					{
						list.add(t);
					}
				}
			}
		}

		/**
		 * For a given alternative of a parsing rule, returns a list containing all
		 * other alternatives of that rule that are sub-strings of the alternative.
		 * For example, consider the following rule:
		 * <pre>
		 * &lt;A&gt; := &lt;B&gt; foo &lt;B&gt; bar baz &lt;C&gt; | foo &lt;B&gt; bar baz &lt;C&gt; | &lt;B&gt; &lt;C&gt;
		 * </pre>
		 * Suppose that the current node corresponds to the first alternative
		 * (&lt;B&gt; foo &lt;B&gt; bar baz &lt;C&gt;). This method would return a
		 * list of two integer arrays, namely [1, 2, 3, 4, 5] and [0, 5]. These
		 * correspond to the positions in the current string to keep, in order to
		 * obtain two valid ways of trimming the current alternative and obtain
		 * another valid alternative from the rule.
		 * @param node The parse node, which should correspond to a non-terminal
		 * token.
		 * @return A list of integer arrays, each giving a valid way of creating a
		 * substring out of the current alternative.
		 * @throws InvalidGrammarException
		 */
		protected List<Integer[]> getSubAlternatives(ParseNode node) throws InvalidGrammarException
		{
			StringBuilder chosen = new StringBuilder();
			chosen.append(node.getToken()).append(" := ");
			if (node.getChildren().isEmpty())
			{
				chosen.append("ε");
			}
			else
			{
				for (ParseNode child : node.getChildren())
				{
					if (child.getToken().isEmpty())
					{
						chosen.append("ε").append(" ");
					}
					else
					{
						chosen.append(child.getToken()).append(" ");
					}
				}
			}
			chosen.append(";");
			TokenString current_tokenstring = BnfParser.getRules(chosen.toString()).get(0).getAlternatives().get(0);
			List<TokenString> alternatives = m_parser.getRule(node.getValue()).getAlternatives();
			List<Integer[]> sub_alternatives = new ArrayList<Integer[]>();
			for (TokenString alt : alternatives)
			{
				Integer[] matches = TokenString.match(alt, current_tokenstring);
				if (matches != null)
				{
					sub_alternatives.add(matches);
				}
			}
			return sub_alternatives;
		}

		@Override
		public GrammarSequenceRelative duplicate(boolean with_state)
		{
			return new GrammarSequenceRelative(m_decision.duplicate(with_state), m_reference, m_magnitude);
		}

		@Override
		public Shrinkable<List<T>> shrink(List<T> o, Picker<Float> d, float m)
		{
			return new GrammarSequenceRelative(d, o, m);
		}

		@Override
		public Shrinkable<List<T>> shrink(List<T> o)
		{
			return shrink(o, RandomFloat.instance, 1f);
		}

		@Override
		public void reset()
		{
			// Nothing to do
		}
	}
}