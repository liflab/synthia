package ca.uqac.lif.cep.synthia.random;

public class RandomFloat extends UniformRandomPicker<Float>
{
	public RandomFloat()
	{
		super();
	}
	
	@Override
	public Float pick() 
	{
		return m_random.nextFloat();
	}
	
	@Override
	public RandomFloat duplicate(boolean with_state)
	{
		return new RandomFloat();
	}
}