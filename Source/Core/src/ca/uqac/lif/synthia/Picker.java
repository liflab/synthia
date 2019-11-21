package ca.uqac.lif.synthia;

public interface Picker<T>
{
	public void reset();
	
	public T pick();
	
	public Picker<T> duplicate(boolean with_state);
}
