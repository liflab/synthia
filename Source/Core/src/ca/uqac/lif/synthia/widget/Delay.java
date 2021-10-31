package ca.uqac.lif.synthia.widget;

import ca.uqac.lif.synthia.Picker;

public class Delay implements Picker<Object>
{
	protected Picker<? extends Number> m_delay;
	
	public Delay(Picker<? extends Number> delay)
	{
		super();
		m_delay = delay;
	}

	@Override
	public void reset()
	{
		m_delay.reset();
	}

	@Override
	public Object pick()
	{
		wait(m_delay.pick().floatValue());
		return null;
	}

	@Override
	public Delay duplicate(boolean with_state)
	{
		return new Delay(m_delay.duplicate(with_state));
	}
	
	/**
	 * Waits for some time.
	 * @param duration The time to wait, in seconds
	 */
	public static void wait(float duration)
	{
		try
		{
			Thread.sleep((long) (duration * 1000));
		}
		catch (InterruptedException e)
		{
			// Do nothing
		}
	}
}
