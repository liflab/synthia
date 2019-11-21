package ca.uqac.lif.cep.synthia.random;

import org.apache.commons.lang3.RandomStringUtils;

import ca.uqac.lif.synthia.Picker;

public class RandomStringProvider implements Picker<String>
{
	protected int m_minLength;
	
	protected int m_maxLength;
	
	public RandomStringProvider(int min_length, int max_length)
	{
		super();
		m_minLength = min_length;
		m_maxLength = max_length;
	}
	
	@Override
	public void reset() 
	{
		// Nothing to do
	}

	@Override
	public String pick() 
	{
		return RandomStringUtils.randomAlphanumeric(m_minLength, m_maxLength);
	}

	@Override
	public Picker<String> duplicate(boolean with_state)
	{
		return new RandomStringProvider(m_minLength, m_maxLength);
	}
}
