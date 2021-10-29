package examples.quickcheck;

import java.util.List;

import ca.uqac.lif.synthia.collection.ComparableList;

public class Sort
{
	public static void main(String[] args)
	{
		
	}
	
	/**
	 * A deliberately faulty implementation of insertion sort.
	 *
	 * @param <T>
	 */
	protected static class FaultySort<T>
	{
		@SuppressWarnings("unchecked")
		public List<T> sort(List<T> list)
		{
			ComparableList<T> ini_list = new ComparableList<T>();
			ComparableList<T> new_list = new ComparableList<T>();
			ini_list.addAll(list);
			for (int i = 0; i < list.size(); i++)
			{
				T to_insert = null;
				for (int j = 0; j < ini_list.size(); j++)
				{
					T e = ini_list.get(j);
					if (to_insert == null)
					{
						to_insert = e;
					}
					else
					{
						if (to_insert instanceof Comparable)
						{
							int value = ((Comparable<T>) to_insert).compareTo(e);
							if (value > 0)
							{
								to_insert = e;
							}
						}
					}
				}
				new_list.add(to_insert);
			}
			return new_list;
		}
	}
}
