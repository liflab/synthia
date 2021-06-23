package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.Picker;

public class AllBooleans implements EnumerativePicker<Boolean>
{

	protected AllIntegers m_picker;

	private AllBooleans(AllIntegers picker)
	{
		m_picker = picker;
	}

	public AllBooleans()
	{
		m_picker = new AllIntegers(0, 1);
	}

	@Override public void reset()
	{
		m_picker.reset();
	}

	@Override
	public Boolean pick()
	{
		return m_picker.pick() == 1;
	}

	@Override public Picker<Boolean> duplicate(boolean with_state)
	{
		return new AllBooleans((AllIntegers) m_picker.duplicate(with_state));
	}

	@Override public boolean isDone()
	{
		return m_picker.isDone();
	}
}
