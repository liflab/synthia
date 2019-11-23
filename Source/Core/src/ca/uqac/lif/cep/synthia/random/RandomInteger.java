package ca.uqac.lif.cep.synthia.random;

public class RandomInteger extends UniformRandomPicker<Integer>
{
	protected int m_min;
	
	protected int m_max;
	
	public RandomInteger(int min, int max)
	{
		super();
		setInterval(min, max);
	}
	
	public void setInterval(int min, int max)
	{
		m_min = min;
		m_max = max;
	}
	
	@Override
	public Integer pick() 
	{
		return m_random.nextInt(m_max - m_min) + m_min;
	}
	
	@Override
	public RandomInteger duplicate(boolean with_state)
	{
		return new RandomInteger(m_min, m_max);
	}
}