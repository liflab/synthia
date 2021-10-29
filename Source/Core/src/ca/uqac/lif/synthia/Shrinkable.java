package ca.uqac.lif.synthia;

public interface Shrinkable<T> extends Picker<T>
{
	/*@ non_null @*/ public Shrinkable<T> shrink(T o, Picker<Float> d);
	
	/*@ non_null @*/ public Shrinkable<T> shrink(T o);
}
