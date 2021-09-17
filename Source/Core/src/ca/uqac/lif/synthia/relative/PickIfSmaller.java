package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.util.ComparableList;

import java.util.List;

/**
 * This is an abstract class that descends from {@link PickIf}.Its constructor takes a
 * {@link RelativePicker} in parameters and everytime {@link PickIfSmaller#pick()} is called,
 * it returns an element for which {@link PickIf#select(Object)} returns <tt>true</tt>, and which
 * is smaller than the previous one.
 *
 * @param <T> The type of the {@link RelativePicker} used.
 * @author Marc-Antoine Plourde
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
	protected T m_previousElement;

	/**
	 * Private constructor used by the {@link #duplicate(boolean)} method.
	 *
	 * @param picker The {@link #m_picker} attribute.
	 * @param max_iteration The {@link #m_maxIteration} attribute.
	 * @param previous_element The {@link #m_previousElement} attribute.
	 * @param original_picker The {@link #m_OriginalStatePicker} attribute.
	 */
	private PickIfSmaller(RelativePicker picker, int max_iteration, T previous_element
			, RelativePicker original_picker, Float ratio)
	{
		super(picker, max_iteration, ratio);
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
		m_OriginalStatePicker = (RelativePicker<T>) picker.duplicate(true);
	}

	/**
	 * Constructor who takes a {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 * @param max_iteration The maximum number of iterations the {@link #pick()} will try to generate
	 *                      an object before giving up.
	 */
	public PickIfSmaller(RelativePicker picker, int max_iteration, Float ratio)
	{
		super(picker, max_iteration, ratio);
		m_previousElement = null;
		m_OriginalStatePicker = (RelativePicker<T>) picker.duplicate(true);
	}

	@Override
	public T pick()
	{
		T e = super.pick();

		//RelativePicker temp_mpicker = (RelativePicker) m_picker.duplicate(true);
		m_picker = m_picker.getPicker(e);

		return e;
	}

	@Override
	public void reset()
	{
		m_previousElement = null;
		m_picker = (RelativePicker) m_OriginalStatePicker.duplicate(true); //overwrite the picker to his original state.
	}

	/**
	 * Abstract method to evaluate if an element is satisfying a condition.
	 *
	 * @param element The element to check
	 * @return <tt>true</tt> if the condition is satisfied and <tt>false</tt> if it's not the case.
	 */
	@Override
	protected boolean select(T element)
	{
		boolean anwser;

		if (m_previousElement == null)
		{
			anwser = true;
		}
		else
		{
			// Check if element is an instance of Comparable
			if(element instanceof Comparable)
			{
				anwser = ((Comparable<T>) element).compareTo(m_previousElement) < 0;
			}
			else
			{
				/* Check if element is an instance of List. If it's the case, an instance of ComparableList
				 * can be instantiated from an instance of the Java.util's List class.
				 */
				if (element instanceof List)
				{
					ComparableList<T> cl = new ComparableList<T>((List<T>) element);
					anwser = ((Comparable<T>) cl).compareTo(m_previousElement) < 0;
				}
				else
				{
					anwser = false;
				}

			}
		}

		if(anwser == true)
		{
			m_previousElement = element;
		}


		return anwser;
	}



	@Override
	public PickIfSmaller duplicate(boolean with_state)
	{
		if (with_state)
		{
			return new PickIfSmaller((RelativePicker) m_picker.duplicate(with_state), m_maxIteration
					, m_previousElement, (RelativePicker) m_OriginalStatePicker.duplicate(true),
					m_discardRatio.getMaxRejectedRatio());
		}
		else
		{
			return new PickIfSmaller((RelativePicker) m_OriginalStatePicker.duplicate(true)
					, m_maxIteration, null
					, (RelativePicker) m_OriginalStatePicker.duplicate(true)
					, m_discardRatio.getMaxRejectedRatio());
		}

	}



}
