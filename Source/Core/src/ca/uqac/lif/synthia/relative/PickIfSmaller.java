package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;

import java.util.List;

//TODO talk to Sylvain about the duplicate(false) problem

/**
 * This is an abstract class that descends from {@link PickIf}.Its constructor takes a
 * {@link RelativePicker} in parameters and everytime {@link PickIfSmaller#pick()} is called,
 * it returns an element for which {@link PickIf#select(Object)} returns <tt>true</tt>, and which
 * is smaller than the previous one.
 *
 * @param <T> The type of the {@link RelativePicker} used.
 */
public class PickIfSmaller<T> extends PickIf
{
	/**
	 * Initial state of {@link PickIf#m_picker}. Used to reinitialize it to his original state.
	 */
	protected Picker<T> m_OriginalStatePicker;// Fix the duplicate(false) problem.

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
	private PickIfSmaller(RelativePicker picker, int max_iteration, Object previous_element
			, RelativePicker original_picker)
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
	public PickIfSmaller(RelativePicker picker)
	{
		super(picker);
		m_previousElement = null;
		m_OriginalStatePicker = picker.duplicate(true);
	}

	/**
	 * Constructor who takes a {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 * @param max_iteration The maximum number of iterations the {@link #pick()} will try to generate
	 *                      an object before giving up.
	 */
	public PickIfSmaller(RelativePicker picker, int max_iteration)
	{
		super(picker, max_iteration);
		m_previousElement = null;
		m_OriginalStatePicker = picker.duplicate(true);
	}

	@Override
	public Object pick()
	{
		Object e = super.pick();
		RelativePicker temp_mpicker = (RelativePicker) m_picker.duplicate(true);
		m_picker = temp_mpicker.getPicker(e);

		return e;
	}

	@Override
	public void reset()
	{
		m_previousElement = null;
		m_picker = m_OriginalStatePicker.duplicate(true); //overwrite the picker to his original state.
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
		if (m_previousElement == null)
		{
			m_previousElement = element;
			return true;
		}
		else
		{
			//TODO talk to Sylvain about that
			boolean return_bool = false;
			if(!element.getClass().getSimpleName().equals(m_previousElement.getClass().getSimpleName()))
			{
				return_bool = false;
			}
			else
			{
				if(m_previousElement.getClass().getSimpleName().equals("Integer") )
				{
					Integer previous_element_int = (Integer) m_previousElement;
					return_bool = ((Integer) element < previous_element_int);
				}
				else
				{
					if(m_previousElement.getClass().getSimpleName().equals("Float"))
					{
						Float previous_element_float = (Float) m_previousElement;
						return_bool = ((Float) element < previous_element_float);
					}
					else
					{
						if (m_previousElement.getClass().getSimpleName().equals("String"))
						{
							String previous_element_string = (String) m_previousElement;
							return_bool = ( ((String) element).length() < previous_element_string.length());
						}
						else
						{
							if(m_previousElement.getClass().getSimpleName().equals(("ArrayList")))
							{
								List<Object> previous_element_list = (List<Object>) m_previousElement;
								return_bool = (((List<Object>) element).size() < previous_element_list.size());
							}
						}
					}
				}
			}

			m_previousElement = element;
			return return_bool;
		}

	}


	@Override
	public PickIfSmaller duplicate(boolean with_state)
	{
		if (with_state)
		{
			return new PickIfSmaller((RelativePicker) m_picker.duplicate(with_state), m_maxIteration
					, m_previousElement, (RelativePicker) m_OriginalStatePicker.duplicate(true));
		}
		else
		{
			return new PickIfSmaller((RelativePicker) m_OriginalStatePicker.duplicate(true)
					, m_maxIteration, null
					, (RelativePicker) m_OriginalStatePicker.duplicate(true));
		}

	}
}
