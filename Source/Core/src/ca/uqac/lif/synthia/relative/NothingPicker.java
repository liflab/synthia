package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.exception.NoMoreElementException;

/**
 * A {@link ca.uqac.lif.synthia.relative.RelativePicker} who only throw a
 * {@link ca.uqac.lif.synthia.exception.NoMoreElementException} when the {@link #pick()} method is
 * called.
 *
 * @param <T> The type of the picker(but not really important here).
 */
public class NothingPicker<T> implements Picker, RelativePicker
{
	public NothingPicker(){};

	/**
	 * Because this picker only return throws a
	 * {@link ca.uqac.lif.synthia.exception.NoMoreElementException}, the following method only returns
	 * a new instance of the class.
	 */
	@Override
	public RelativePicker getPicker(Object element)
	{
		return duplicate(true);
	}

	/**
	 *
	 * @warning ALWAYS RETURN 0.
	 * @param old_value
	 * @param new_value
	 *
	 *
	 * @return
	 */
	@Override
	public int compare(Object old_value, Object new_value)
	{
		return 0;
	}

	@Override
	public void reset()
	{
		//Nothing to do here.
	}

	@Override
	public Object pick()
	{
		throw  new NoMoreElementException();
	}

	@Override
	public RelativePicker duplicate(boolean with_state)
	{
		return new NothingPicker();
	}
}
