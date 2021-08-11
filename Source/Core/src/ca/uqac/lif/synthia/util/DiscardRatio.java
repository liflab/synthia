package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.exception.GiveUpException;

/**
 * A utility picker to calculate the discard ratio of a {@link ca.uqac.lif.synthia.PickIf} picker.
 */
public class DiscardRatio implements Picker<Float>
{
	/**
	 * Max rejected ratio of generated object autorized.
	 */
	protected final float m_maxRejectedRatio;

	/**
	 * Generated object counter.
	 */
	protected int m_generatedCounter;

	/**
	 * Discarted generated object counter.
	 */
	protected int m_discartedCounter;

	/**
	 * Private constructor used to duplicate de picker.
	 *
	 * @param ratio Maximal rejected ratio.
	 * @param generated_counter Generated object counter.
	 * @param discarted_counter Discarted generated object counter.
	 */
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

	/**
	 * Count the generated object as accepted.
	 */
	public void countAsAccepted()
	{
		m_generatedCounter++;
	}

	/**
	 * Count the generated object as discarted.
	 */
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

		if(ratio > m_maxRejectedRatio)
		{
			throw new GiveUpException();
		}

		return ratio;
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
