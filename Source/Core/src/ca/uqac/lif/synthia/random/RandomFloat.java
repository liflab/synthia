package ca.uqac.lif.synthia.random;

public class RandomFloat extends RandomPicker<Float>
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