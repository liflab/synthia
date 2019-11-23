package ca.uqac.lif.cep.synthia.grammar;

import java.util.List;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.bullwinkle.BnfRule;
import ca.uqac.lif.bullwinkle.EpsilonTerminalToken;
import ca.uqac.lif.bullwinkle.NonTerminalToken;
import ca.uqac.lif.bullwinkle.NumberTerminalToken;
import ca.uqac.lif.bullwinkle.RegexTerminalToken;
import ca.uqac.lif.bullwinkle.StringTerminalToken;
import ca.uqac.lif.bullwinkle.TerminalToken;
import ca.uqac.lif.bullwinkle.Token;
import ca.uqac.lif.bullwinkle.TokenString;
import ca.uqac.lif.synthia.IndexPicker;
import ca.uqac.lif.synthia.Picker;

public class GrammarSentence implements Picker<String>
{
	/**
	 * The character used to separate the tokens
	 */
	protected static final transient String SPACE = " ";
	
	/*@ non_null @*/ protected BnfParser m_parser;
	
	/*@ non_null @*/ protected IndexPicker m_indexPicker;
	
	public GrammarSentence(/*@ non_null @*/ BnfParser parser, /*@ non_null @*/ IndexPicker picker)
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
	
	protected String pickRecursive(BnfRule rule)
	{
		List<TokenString> alternatives = rule.getAlternatives();
		int chosen_index = m_indexPicker.setRange(alternatives.size()).pick();
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
		return new GrammarSentence(m_parser, m_indexPicker);
	}
}
