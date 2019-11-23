package ca.uqac.lif.cep.synthia.random;

public class RandomIntervalFloat extends UniformRandomPicker<Float>
{
	protected float m_min;
	
	protected float m_max;
	
	public RandomIntervalFloat(Number min, Number max)
	{
		super();
		m_min = min.floatValue();
		m_max = max.floatValue();
	}
	
	@Override
	public Float pick() 
	{
		return m_random.nextFloat() * (m_max - m_min) + m_min;
	}
	
	@Override
	public RandomIntervalFloat duplicate(boolean with_state)
	{
		return new RandomIntervalFloat(m_min, m_max);
	}
}