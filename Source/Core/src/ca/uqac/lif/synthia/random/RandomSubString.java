package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Seedable;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.relative.NothingPicker;



/**
 * {@link RandomPicker} who produces random substrings from an original one.
 */
public class RandomSubString implements Shrinkable<String>, Seedable
{

	/**
	 * The string used to generate substrings.
	 */
	protected String m_string;

	/**
	 * A {@link RandomBoolean} picker to select if we pick a character or not.
	 */
	protected RandomBoolean m_charSelect;

	/**
	 * Private constructor used by the {@link #duplicate(boolean)} method.
	 *
	 * @param s The {@link #m_string} attribute.
	 * @param char_select The {@link #m_charSelect} attribute.
	 */
	private RandomSubString(String s, RandomBoolean char_select)
	{
		m_string = s;
		m_charSelect = char_select;
	}

	/**
	 * Public constructor used to create a new instance of the class.
	 *
	 * @param s The string used to generate substrings.
	 */
	public RandomSubString(String s)
	{
		m_string = s;
		m_charSelect = new RandomBoolean();
	}

	/**
	 * Create a new {@link RandomSubString} picker based on a given string. If this given string is
	 * empty, the method will return a {@link NothingPicker}. The new instance will also have the same
	 * internal states for the {@link #m_charSelect} attributes as the original one.
	 *
	 * @param element The string used by the new {@link RandomSubString} picker to produce substrings.
	 *
	 * @return The new instance of the class or a {@link NothingPicker}.
	 */
	@Override
	public Shrinkable<String> shrink(String element)
	{
		if (element.isEmpty())
		{
			return new NothingPicker<String>();
		}
		else
		{
			return new RandomSubString(element, m_charSelect.duplicate(true));
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
	public RandomSubString setSeed(int seed)
	{
		m_charSelect.setSeed(seed);
		return this;
	}
}
