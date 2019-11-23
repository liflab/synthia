package ca.uqac.lif.cep.synthia.random;

public class RandomBoolean extends UniformRandomPicker<Boolean>
{

	protected float m_trueProbability;
	
	public RandomBoolean(/*@ non_null @*/ Number true_probability)
	{
		super();
		m_trueProbability = true_probability.floatValue();
	}
	
	public RandomBoolean()
	{
		this(0.5);
	}
	
	@Override
	public Boolean pick()
	{
		return m_random.nextFloat() <= m_trueProbability;
	}

	@Override
	public RandomBoolean duplicate(boolean with_state) 
	{
		return new RandomBoolean(m_trueProbability);
	}
}
