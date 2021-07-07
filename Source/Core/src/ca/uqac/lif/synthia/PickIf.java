package ca.uqac.lif.synthia;

/**
 * Returns object from a picker satisfying a condition.
 * @param <T> The object type returned by the picker.
 */
public abstract class PickIf<T>
{

	/**
	 * The picker used for the evaluations.
	 */
	protected Picker<T> m_picker;

	public PickIf(Picker<T> picker)
	{
		m_picker = picker;
	}

	/**
	 * Abstract method to evaluate if an element is satisfying a condition.
	 * @param element The element to check
	 * @return The element if the condition is satisfied and <tt>false</tt> if it's not the case
	 */
	protected abstract boolean select (T element);

	/**
	 * Method to pick the first object generated by the picker who satisfies the condition.
	 * WARNING: For now, this method can result in a infinite loop if the picker can't generate
	 * an object who satisfies the condition.
	 * @return The object.
	 */
	public Object pick()
	{
		T picked_value = m_picker.pick();

		while (!select(picked_value))
		{
			picked_value = m_picker.pick();

		}
		return picked_value;
	}

}