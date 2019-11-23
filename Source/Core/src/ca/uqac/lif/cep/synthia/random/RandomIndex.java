package ca.uqac.lif.cep.synthia.random;

import ca.uqac.lif.synthia.IndexPicker;

public class RandomIndex extends RandomInteger implements IndexPicker
{
	public RandomIndex()
	{
		this(0, 1);
	}
	
	public RandomIndex(int min, int max) 
	{
		super(min, max);
	}

	@Override
	public RandomIndex setRange(int size)
	{
		setInterval(0, size);
		return this;
	}
}
