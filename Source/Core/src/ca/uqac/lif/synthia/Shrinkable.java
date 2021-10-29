package ca.uqac.lif.synthia;

public interface Shrinkable<T> extends Picker<T>
{
	/*@ non_null @*/ public Shrinkable<T> shrink(T o, Picker<Float> d, float m);
	
	/*@ non_null @*/ public Shrinkable<T> shrink(T o);
}
