package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.exception.NoMoreElementException;

/**
 * A {@link ca.uqac.lif.synthia.Shrinkable} who only throw a
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

	



	/**
	 * Because this picker only return throws a
	 * {@link ca.uqac.lif.synthia.exception.NoMoreElementException}, the following method only returns
	 * a new instance of the class.
	 */
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


	@Override public T pick()

	{
		throw  new NoMoreElementException();
	}


	@Override
	public Picker<T> duplicate(boolean with_state)
	{
		return new NothingPicker<T>();
	}
}
