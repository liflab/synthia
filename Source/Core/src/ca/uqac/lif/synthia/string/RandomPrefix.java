package ca.uqac.lif.synthia.string;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Seedable;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.NothingPicker;

/**
 * Like {@link RandomSubString}, but this time, the {@link Picker} returns a prefix of the original
 * strings of randomly selected length. The {@link RandomSubString#m_charSelect} is replaced by a
 * {@link RandomInteger}.
 * 
 * @ingroup API
 */
public class RandomPrefix implements Shrinkable<String>, Seedable
{

	/**
	 * {@link RandomInteger} picker to select the size of a generated prefix.
	 */
	protected RandomInteger m_prefixSize;

	/**
	 * The string used to generate prefixes.
	 */
	protected String m_string;

	/**
	 * Private constructor used by the {@link #duplicate(boolean)} method.
	 *
	 * @param string The {@link #m_string} attributes.
	 * @param prefix_size The {@link #m_prefixSize} attributes.
	 */
	protected RandomPrefix(String string, RandomInteger prefix_size)
	{
		m_string = string;
		m_prefixSize = prefix_size;
	}

	/**
	 * Public constructor to create a new instance of this {@link Picker}.
	 *
	 * @param string The string used to generate prefixes.
	 */
	public RandomPrefix(String string)
	{
		m_string = string;
		m_prefixSize = new RandomInteger(0, m_string.length()+1);
	}

	/**
	 * Create a new {@link RandomPrefix} picker based on a given string. If this given string is
	 * empty, the method will return a {@link NothingPicker}. The new instance will also have the same
	 * internal states for the {@link #m_prefixSize} attributes as the original one.
	 *
	 * @param element The string used by the new {@link RandomPrefix} picker to produce substrings.
	 *
	 * @return The new instance of the class or a {@link NothingPicker}.
	 */
	@Override
	public Shrinkable<String> shrink(String element, Picker<Float> decision, float magnitude)
	{
		if(element.isEmpty())
		{
			return new NothingPicker<>();
		}
		return new RandomPrefix(element, m_prefixSize.duplicate(true));
	}

	@Override
	public void reset()
	{
		m_prefixSize.reset();
	}

	@Override
	public String pick()
	{
		return m_string.substring(0, m_prefixSize.pick());
	}

	@Override
	public RandomPrefix duplicate(boolean with_state)
	{
		return new RandomPrefix(m_string, m_prefixSize.duplicate(with_state));
	}

	@Override
	public RandomPrefix setSeed(int seed)
	{
		m_prefixSize.setSeed(seed);
		return this;
	}

	@Override
	public Shrinkable<String> shrink(String o)
	{
		return shrink(o, RandomFloat.instance, 1);
	}
}
