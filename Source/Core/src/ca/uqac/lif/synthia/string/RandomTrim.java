package ca.uqac.lif.synthia.string;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.NothingPicker;

/**
 * A combination of {@link RandomPrefix} and {@link RandomSuffix}. This
 * {@link ca.uqac.lif.synthia.Picker} first calculates a prefix of the string and on this prefix,
 * then calculates a suffix and returns the resulting string.
 * @author Marc-Antoine Plourde
 * @ingroup API
 */
public class RandomTrim extends RandomPrefix
{
	protected RandomTrim(String string, RandomInteger prefix_size)
	{
		super(string, prefix_size);
	}

	public RandomTrim(String string)
	{
		super(string);
	}

	@Override
	public Shrinkable<String> shrink(String element, Picker<Float> decision, float magnitude)
	{
		if(element.isEmpty())
		{
			return new NothingPicker<>();
		}
		return new RandomTrim(element, m_prefixSize.duplicate(true));
	}

	@Override
	public String pick()
	{
		String s = super.pick();
		m_prefixSize.setInterval(0, s.length() + 1);
		int start_index = m_prefixSize.pick();

		m_prefixSize.setInterval(0, m_string.length()+1); // reset index interval before next pick

		return s.substring(start_index, s.length());
	}

	@Override
	public RandomTrim duplicate(boolean with_state)
	{
		return new RandomTrim(m_string, m_prefixSize.duplicate(with_state));
	}
}
