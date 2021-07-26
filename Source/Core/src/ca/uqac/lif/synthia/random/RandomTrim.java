package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.relative.NothingPicker;
import ca.uqac.lif.synthia.relative.RelativePicker;

/**
 * A combination of {@link RandomPrefix} and {@link RandomSuffix}. This
 * {@link ca.uqac.lif.synthia.Picker} first calculates a prefix of the stringm and on this prefix,
 * then calculates a suffix and returns the resulting string.
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
	public RelativePicker<String> getPicker(String element)
	{
		if(element.isEmpty())
		{
			return new NothingPicker<String>();
		}
		return new RandomTrim(element, m_prefixSize.duplicate(true));
	}

	@Override
	public String pick()
	{
		String s = super.pick();
		m_prefixSize.setInterval(0, s.length() + 1);
		return s.substring(m_prefixSize.pick(), s.length());
	}

	@Override
	public RandomTrim duplicate(boolean with_state)
	{
		return new RandomTrim(m_string, m_prefixSize.duplicate(with_state));
	}
}
