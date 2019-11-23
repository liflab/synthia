package ca.uqac.lif.cep.synthia.random;

import org.apache.commons.lang3.RandomStringUtils;

import ca.uqac.lif.synthia.Picker;

public class RandomString implements Picker<String>
{
	protected Picker<Integer> m_lengthPicker;
	
	public RandomString(Picker<Integer> length)
	{
		super();
		m_lengthPicker = length;
	}
	
	@Override
	public void reset() 
	{
		// Nothing to do
	}

	@Override
	public String pick() 
	{
		int len = m_lengthPicker.pick();
		return RandomStringUtils.randomAlphanumeric(len, len);
	}

	@Override
	public Picker<String> duplicate(boolean with_state)
	{
		return new RandomString(m_lengthPicker);
	}
}
