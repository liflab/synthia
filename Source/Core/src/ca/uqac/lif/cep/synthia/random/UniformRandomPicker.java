package ca.uqac.lif.cep.synthia.random;

import java.util.Random;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Seedable;

public abstract class UniformRandomPicker<T> implements Picker<T>, Seedable
{
	/*@ non_null @*/ protected transient Random m_random;
	
	protected int m_seed;
	
	public UniformRandomPicker(int seed)
	{
		super();
		setSeed(seed);
	}
	
	public UniformRandomPicker()
	{
		this(0);
	}

	@Override
	public void setSeed(int seed) 
	{
		m_seed = seed;
		m_random = new Random(seed);
	}

	@Override
	public void reset() 
	{
		m_random = new Random(m_seed);
	}
}
