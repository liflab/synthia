package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.relative.NothingPicker;

/**
 * Just like {@link RandomPrefix} but for suffixes.
 */
public class RandomSuffix extends RandomPrefix
{

	public RandomSuffix(String string)
	{
		super(string);
	}

	private RandomSuffix(String string, RandomInteger prefix_size)
	{
		super(string, prefix_size);
	}

	@Override
	public Shrinkable<String> shrink(String element)
	{
		if(element.isEmpty())
		{
			return new NothingPicker<String>();
		}
		return new RandomSuffix(element, m_prefixSize.duplicate(true));
	}

	@Override
	public String pick()
	{
		return m_string.substring(m_prefixSize.pick(), m_string.length());
	}

	@Override
	public RandomSuffix duplicate(boolean with_state)
	{
		return new RandomSuffix(m_string, m_prefixSize.duplicate(with_state));
	}

}
