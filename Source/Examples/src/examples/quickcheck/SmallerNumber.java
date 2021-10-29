package examples.quickcheck;

import java.util.Comparator;

/**
 * @ingroup Examples
 * @author sylvain
 *
 * @param <T>
 */
public class SmallerNumber<T extends Number> implements Comparator<T>
{
	public static final transient SmallerNumber<Integer> instance = new SmallerNumber<Integer>();
	
	@Override
	public int compare(Number o1, Number o2)
	{
		if (o1.floatValue() == o2.floatValue())
		{
			return 0;
		}
		if (o1.floatValue() < o2.floatValue())
		{
			return -1;
		}
		return 1;
	}

}
