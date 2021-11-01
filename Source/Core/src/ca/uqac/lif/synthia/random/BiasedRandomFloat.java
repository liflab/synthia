package ca.uqac.lif.synthia.random;

public class BiasedRandomFloat extends RandomFloat
{
	protected float m_bias;
	
	public BiasedRandomFloat(float bias)
	{
		super();
		m_bias = bias;
	}
	
	@Override
	public Float pick()
	{
		float f = super.pick();
		float biased_f = f * m_bias;
		if (biased_f < 0)
		{
			return 0f;
		}
		if (biased_f > 1)
		{
			return 1f - 0.000001f;
		}
		return biased_f;
	}
	
	@Override
	public BiasedRandomFloat duplicate(boolean with_state)
	{
		return new BiasedRandomFloat(m_bias);
	}
}
