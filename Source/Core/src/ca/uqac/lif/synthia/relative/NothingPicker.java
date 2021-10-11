package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.exception.NoMoreElementException;

/**
 * A {@link ca.uqac.lif.synthia.relative.RelativePicker} who only throw a
 * {@link ca.uqac.lif.synthia.exception.NoMoreElementException} when the {@link #pick()} method is
 * called.
 *
 * @param <T> The type of the picker(but not really important here).
 */
public class NothingPicker<T> implements Shrinkable<T>
{
	public NothingPicker()
	{
		super();
	}
	
	@Override
	public NothingPicker<T> shrink(T o)
	{
		return new NothingPicker<T>();
	}

	@Override
	public void reset()
	{
		//Nothing to do here.
	}

	@Override
	public T pick()
	{
		throw  new NoMoreElementException();
	}

	@Override
	public Picker<T> duplicate(boolean with_state)
	{
		return new NothingPicker<T>();
	}
}
