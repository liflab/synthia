package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.exception.GiveUpException;

/**
 * Returns object from a picker satisfying a condition.
 *
 * @param <T> The object type returned by the picker.
 */
public abstract class PickIf<T> implements Picker
{

	/**
	 * The picker used for the evaluations.
	 */
	protected Picker<T> m_picker;

	/**
	 * The maximal number of iteration that the while loop of the {@link #pick()} method can do.
	 * If the value is negative, there will be no maximum number of iterations.
	 */
	protected final int m_maxIteration;

	/**
	 * Constructor with default {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 */
	public PickIf(Picker<T> picker)

	{
		m_picker = picker;
		m_maxIteration = 100;

	}

	/**
	 * Constructor who takes a {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 * @param max_iteration The maximum number of iterations the {@link #pick()} will try to generate
	 *                      an object before giving up.
	 */
	public PickIf(Picker<T> picker, int max_iteration)

	{
		m_picker = picker;
		m_maxIteration = max_iteration;

	}

	/**
	 * Abstract method to evaluate if an element is satisfying a condition.
	 *
	 * @param element The element to check
	 * @return <tt>true</tt> if the condition is satisfied and <tt>false</tt> if it's not the case.
	 */
	protected abstract boolean select(T element);

	/**
	 * Method to pick the first object generated by the picker who satisfies the condition.
	 * WARNING: For now, this method can result in a infinite loop if the picker can't generate
	 * an object who satisfies the condition.
	 *
	 * @return The object.
	 */
	public T pick()
	{
		int iteration_counter = 0;
		T picked_value = m_picker.pick();


		while (!select(picked_value))
		{
			if (checkIfInfiniteLoop(iteration_counter))
			{
				throw new GiveUpException();
			}

			picked_value = m_picker.pick();
			iteration_counter++;

		}
		return picked_value;
	}

	/**
	 * Protected method to check if the instance of the class detects that the while loop of
	 * the {@link #pick()} method fell into an infinite loop by supposing that if the loop exceed
	 * a maximal number of iteration, it will cause an infinite loop.
	 *
	 * @warning If {@link #m_maxIteration} is negative, the {@link #pick} method will dont check if
	 * the iteration counter has exceeded the value of {@link #m_maxIteration} and this
	 * method will always return false. This means that it's possible that calling {@link #pick}
	 * causes an infinite loop. Only use a negative value for {@link #m_maxIteration} if you assume
	 * that calling {@link #pick} could cause an infinite loop.
	 *
	 * @return <tt>true</tt> if infinite loop is detected and <tt>false</tt> if it's not the case.
	 */
	protected boolean checkIfInfiniteLoop(int iteration_counter)
	{
		if (m_maxIteration < 0)
		{
			return false;
		}
		else
		{
			return !(iteration_counter < m_maxIteration);
		}
	}

	@Override
	public void reset()
	{
		m_picker.reset();
	}

	public abstract Picker duplicate(boolean with_state);

}