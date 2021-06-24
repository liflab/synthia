package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;

public interface RelativePicker<T> extends Picker<T>
{
	public T pick(T element);
}
