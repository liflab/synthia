package ca.uqac.lif.synthia.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that extends Java's ArrayList class to implements the Comparable interface.
 *
 * @param <T> The type of the elements of the list.
 * @author Marc-Antoine Plourde
 * @ingroup API
 */
public class ComparableList<T> extends ArrayList<T> implements Comparable<ArrayList<T>>
{
	/**
	 * Dummy UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ComparableList()
	{
		super();
	}

	/**
	 * Constructor who takes a {@link ComparableList} as parameter.
	 *
	 * @param cl The reference list used to compare to an other list.
	 */
	public ComparableList(ComparableList<T> cl)
	{
		super((List<T>) cl);
	}

	/**
	 * Constructor who takes an <code>ArrayList&lt;T&gt;</code> as parameter.
	 *
	 * @param al The reference list used to compare to an other list.
	 */
	public ComparableList(ArrayList<T> al)
	{
		super((List<T>) al);
	}

	/**
	 * Constructor who takes a <code>List&lt;T&gt;</code> as parameter.
	 *
	 * @param l The reference list used to compare to an other list.
	 */
	public ComparableList(List<T> l)
	{
		super(l);
	}

	/**
	 * Public method to compare a list to the reference one.
	 *
	 * @param o An <code>ArrayList&lt;T&gt;</code> to compare with the reference one.
	 * @return <ul>
	 * 	  	<li>a negative integer if the reference list is considered smaller than the second one.</li>
	 * 	  	<li>0 if the reference list is considered equal to the second one.</li>
	 * 	  	<li>A posisitive integer supperior to 0 if the reference list is considered greater than the second one..</li>
	 * 	  <ul/>
	 */
	@Override public int compareTo(ArrayList<T> o)
	{
		if (this.size() < o.size())
		{
			return -1;
		}
		else if (this.size() > o.size())
		{
			return 1;
		}
		else
		{
			int answer = 0;
			int i = 0;

			while ((i < this.size()) && (answer == 0))
			{

				if (((this.get(i)) instanceof Comparable) && ((o.get(i)) instanceof Comparable))
				{

					try
					{

						answer = ((Comparable<T>) this.get(i)).compareTo(o.get(i));

					}
					catch (ClassCastException exception)
					{
						answer = 0;
					}

				}
				else if (((this.get(i)) instanceof List) && ((o.get(i)) instanceof List))
				{
					answer = new ComparableList<T>((List<T>) this.get(i)).compareTo((ArrayList<T>) o.get(i));
				}

				i++;
			}

			return answer;
		}

	}

}
