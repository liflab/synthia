package ca.uqac.lif.synthia;

import java.util.List;

public interface SequenceShrinkable<T> extends Bounded<T>
{
	public SequenceShrinkable<T> shrink(Picker<Float> d, float magnitude);
	
	public List<T> getSequence();
}
