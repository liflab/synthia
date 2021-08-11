package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;


/**
 * Interface to obtain an object in relation to another one produced by the picker.
 * @param <T> The object type produced by the picker.
 */
public interface RelativePicker<T> extends Picker<T>
{
	public RelativePicker<T> getPicker(T element);


}
