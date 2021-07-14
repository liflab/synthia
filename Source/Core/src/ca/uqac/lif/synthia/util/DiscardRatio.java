package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.exception.GiveUpException;

/**
 * A utility picker to calculate the discard ratio of a {@link ca.uqac.lif.synthia.PickIf} picker.
 */
public class DiscardRatio implements Picker<Float>
{
	protected final float m_maxRejectedRatio;

	protected int m_generatedCounter;

	protected int m_discartedCounter;


	private DiscardRatio(float ratio, int generated_counter, int discarted_counter)
	{
		m_maxRejectedRatio = ratio;
		m_generatedCounter = generated_counter;
		m_discartedCounter = discarted_counter;
	}

	public DiscardRatio(float ratio)
	{
		m_maxRejectedRatio = ratio;
		m_generatedCounter = 0;
		m_discartedCounter = 0;
	}

	public void countAsAccepted()
	{
		m_generatedCounter++;
	}

	public void countAsDiscarted()
	{
		m_generatedCounter++;
		m_discartedCounter++;
	}

	@Override
	public void reset()
	{
		m_generatedCounter = 0;
		m_discartedCounter = 0;
	}

	@Override
	public Float pick()
	{
		float ratio = (float) m_discartedCounter / (float) m_generatedCounter;

		if(checkRatio(ratio))
		{
			throw new GiveUpException();
		}
		return ratio;
	}

	private boolean checkRatio(float ratio)
	{
		return ratio > m_maxRejectedRatio;
	}

	@Override
	public DiscardRatio duplicate(boolean with_state)
	{
		DiscardRatio copy = new DiscardRatio(m_maxRejectedRatio, m_generatedCounter, m_discartedCounter);
		if(!with_state)
		{
			copy.reset();
		}
		return copy;
	}
	
}
