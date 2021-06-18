package ca.uqac.lif.synthia;

import ca.uqac.lif.synthia.Picker;

/**
 * Interface who extends Picker. This interface will be used to signal
 * that the picker enumerates all values from a set.
 */
public interface EnumerativePicker<T> extends Picker<T>
{
	/**
	 * Signals if the picker enumerates all values from a set.
	 * @return true if the picker enumerates all the values and false if it's not the case.
	 */
 public boolean isDone();
}
