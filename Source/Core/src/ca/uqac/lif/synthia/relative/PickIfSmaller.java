package ca.uqac.lif.synthia.relative;



/**
 * This is an abstract class that descends from {@link PickIf}.Its constructor takes a
 * {@link RelativePicker} in parameters and everytime {@link PickIfSmaller#pick()} is called,
 * it returns an element for which {@link PickIf#select(Object)} returns <tt>true</tt>, and which
 * is smaller than the previous one.
 *
 * @param <T> The type of the {@link RelativePicker} used.
 */
public class PickIfSmaller<T> extends PickIf<T>
{
	/**
	 * Initial state of {@link PickIf#m_picker}. Used to reinitialize it to his original state.
	 */
	protected RelativePicker<T> m_OriginalStatePicker;// Fix the duplicate(false) problem.

	/**
	 * The last object produced.
	 */
	protected Object m_previousElement;

	/**
	 * Private constructor used by the {@link #duplicate(boolean)} method.
	 *
	 * @param picker The {@link #m_picker} attribute.
	 * @param max_iteration The {@link #m_maxIteration} attribute.
	 * @param previous_element The {@link #m_previousElement} attribute.
	 * @param original_picker The {@link #m_OriginalStatePicker} attribute.
	 */
	private PickIfSmaller(RelativePicker<T> picker, int max_iteration, Object previous_element
			, RelativePicker<T> original_picker)
	{
		super(picker, max_iteration);
		m_previousElement = previous_element;
		m_OriginalStatePicker = original_picker;
	}

	/**
	 * Constructor with default {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 */
	public PickIfSmaller(RelativePicker<T> picker)
	{
		super(picker);
		m_previousElement = null;
		m_OriginalStatePicker = (RelativePicker<T>) picker.duplicate(true);
	}

	/**
	 * Constructor who takes a {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 * @param max_iteration The maximum number of iterations the {@link #pick()} will try to generate
	 *                      an object before giving up.
	 */
	public PickIfSmaller(RelativePicker<T> picker, int max_iteration)
	{
		super(picker, max_iteration);
		m_previousElement = null;
		m_OriginalStatePicker = (RelativePicker<T>) picker.duplicate(true);
	}

	@Override
	public T pick()
	{
		T e = super.pick();
		RelativePicker<T> temp_mpicker = (RelativePicker<T>) m_picker.duplicate(true);
		m_picker = temp_mpicker.getPicker(e);
		return e;
	}

	@Override
	public void reset()
	{
		m_previousElement = null;
		m_picker = (RelativePicker<T>) m_OriginalStatePicker.duplicate(true); //overwrite the picker to his original state.
	}

	/**
	 * Abstract method to evaluate if an element is satisfying a condition.
	 *
	 * @param element The element to check
	 * @return <tt>true</tt> if the condition is satisfied and <tt>false</tt> if it's not the case.
	 */
	@Override
	protected boolean select(Object element)
	{
		boolean anwser;

		if (m_previousElement == null)
		{
			anwser = true;
		}
		else
		{
			anwser = m_picker.compare(m_previousElement, element) < 0;
		}

		m_previousElement = element;

		return anwser;
	}



	@Override
	public PickIfSmaller<T> duplicate(boolean with_state)
	{
		if (with_state)
		{
			return new PickIfSmaller<T>((RelativePicker<T>) m_picker.duplicate(with_state), m_maxIteration
					, m_previousElement, (RelativePicker<T>) m_OriginalStatePicker.duplicate(true));
		}
		else
		{
			return new PickIfSmaller<T>((RelativePicker<T>) m_OriginalStatePicker.duplicate(true)
					, m_maxIteration, null
					, (RelativePicker<T>) m_OriginalStatePicker.duplicate(true));
		}

	}



}
