package ca.uqac.lif.synthia;


public abstract class PickIf<T>
{

	protected Picker<T> m_picker;

	public PickIf(Picker<T> picker)
	{
		m_picker = picker;
	}

	protected abstract boolean select (T element);
	
	public Object pick()
	{
		T picked_value = m_picker.pick();

		if (select(picked_value))
		{
			return picked_value;
		}
		return false;
	}

}
