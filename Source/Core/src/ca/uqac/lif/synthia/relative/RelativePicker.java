package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;

import java.util.Comparator;

/**
 * Interface to obtain an object in relation to another one produced by the picker.
 * @param <T> The object type produced by the picker.
 * @author Marc-Antoine Plourde
 */
public interface RelativePicker<T> extends Picker<T>
{
	public RelativePicker<T> getPicker(T element);

}
