package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;

import java.util.Comparator;

/**
 * Interface to obtain an object in relation to another one produced by the picker.
 * @param <T> The object type produced by the picker.
 */
public interface RelativePicker<T> extends Picker<T>, Comparator
{
	public RelativePicker<T> getPicker(T element);

	/**
	 * Compare two values to determine the value relation between them.
	 *
	 * @warning THIS METHOD WILL ONLY SUPPORT THE TYPE OF OBJECT RETURNED BY THE PICKER THE {@link Picker}.
	 *
	 * @param old_value The old value.
	 * @param new_value The new value.
	 *
	 * @return -1 if old > new, 0 if old = new and 1 if old < new.
	 */
	public int compare (Object old_value, Object new_value);

}
