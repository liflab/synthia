package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Seedable;
import ca.uqac.lif.synthia.relative.NothingPicker;
import ca.uqac.lif.synthia.relative.RelativePicker;

//TODO add javadoc comments
/**
 * {@link RandomPicker} who produces random substring from an original one
 */
public class RandomSubString implements Picker<String>, Seedable, RelativePicker<String>
{

	protected String m_string;

	protected RandomBoolean m_charSelect;

	private RandomSubString(String s, RandomBoolean char_select)
	{
		m_string = s;
		m_charSelect = char_select;
	}

	public RandomSubString(String s)
	{
		m_string = s;
		m_charSelect = new RandomBoolean();
	}

	@Override
	public RelativePicker<String> getPicker(String element)
	{
		if (element.isEmpty())
		{
			return new NothingPicker();
		}
		else
		{
			return new RandomSubString(element);
		}
	}

	@Override
	public void reset()
	{
		m_charSelect.reset();
	}

	@Override
	public String pick()
	{
		boolean initial_bool = m_charSelect.pick();
		boolean picked_bool = initial_bool;
		StringBuilder substring = new StringBuilder();
		int i =0;
		while ((initial_bool == picked_bool) && (i < m_string.length()))
		{
			if(initial_bool)
			{
				substring.append(m_string.charAt(i));
			}
			picked_bool = m_charSelect.pick();
			i++;
		}

		if(!initial_bool)
		{
			while ((picked_bool) && (i < m_string.length()))
			{
				substring.append(m_string.charAt(i));
				picked_bool = m_charSelect.pick();
				i++;
			}
		}

		return substring.toString();
	}

	@Override
	public Picker<String> duplicate(boolean with_state)
	{
		return new RandomSubString(m_string, m_charSelect.duplicate(with_state));
	}

	@Override
	public void setSeed(int seed)
	{
		m_charSelect.setSeed(seed);
	}
}
