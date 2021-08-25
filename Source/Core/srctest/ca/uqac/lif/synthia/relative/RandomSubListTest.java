package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomSubList;
import ca.uqac.lif.synthia.random.RandomSubString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RandomSubListTest
{
	private List<String> getList()
	{
		List<String> list = new ArrayList<String>();
		list.add("portez");
		list.add("ce");
		list.add("wisky");
		list.add("au");
		list.add("vieux");
		list.add("juge");

		return list;
	}

	private boolean compareList(List<String> list1, List<String> list2, boolean expected_result)
	{

		if (list1.size() == list2.size())
		{
			int counter = 0;
			for (int i = 0; i < list1.size(); i++)
			{
				if(Objects.equals(list1.get(i), list2.get(i)))
				{
					counter++;
				}
			}
			return (counter == list1.size());
		}
		return false;
	}

	@Test
	public void subList()
	{
		RandomSubList random_sublist = new RandomSubList(getList(), new RandomSubString(null));
		for (int i = 0; i < 10; i++)
		{
			System.out.println(random_sublist.pick());
		}

	}

	@Test
	public void duplicateWithState()
	{
		RandomSubList random_sublist = new RandomSubList(getList(), new RandomSubString(null));
		RandomSubList random_sublist_copy = (RandomSubList) random_sublist.duplicate(true);

		for (int i = 0; i < 1000; i++)
		{
			Assertions.assertTrue(compareList(random_sublist.pick(), random_sublist_copy.pick()
					, true));
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		RandomSubList random_sublist = new RandomSubList(getList(), new RandomSubString(null));


		for (int i = 0; i < 100; i++)
		{
			random_sublist.pick();
		}

		RandomSubList random_sublist_copy = (RandomSubList) random_sublist.duplicate(false);

		int counter = 0;
		for (int i = 0; i < 1000; i++)
		{
			if (compareList(random_sublist.pick(), random_sublist_copy.pick(), false))
			{
				counter++;
			}
		}

		Assertions.assertFalse(counter == 1000);
		counter = 0;
		random_sublist.reset();
		random_sublist_copy.reset();

		for (int i = 0; i < 10000; i++)
		{
			if (compareList(random_sublist.pick(), random_sublist_copy.pick(), true))
			{
				counter++;
			}
		}
		Assertions.assertTrue(counter == 10000);

	}

	@Test
	public void getPickerFromARandomSublistGeneratedList()
	{
		RandomSubList random_sublist = new RandomSubList(getList(), new RandomSubString(null));

		int cast_exception_counter = 0; // getpicker returns a NothingPicker
		int no_more_element_counter = 0;
		int no_error_counter = 0; // iteration ran without error
		for (int i = 0; i < 10000; i++)
		{

			try
			{
				RandomSubList random_sublist2 = (RandomSubList) random_sublist
						.getPicker(random_sublist.pick());
				random_sublist2.pick();
				no_error_counter ++;
			}
			catch (ClassCastException e)
			{
				cast_exception_counter++;
			}
			catch (NoMoreElementException e)
			{
				no_more_element_counter++;
			}

		}

		System.out.print("no_error_counter : ");
		System.out.print(no_error_counter);
		System.out.print("\n");
		System.out.print("cast_exception_counter : ");
		System.out.print(cast_exception_counter);
		System.out.print("\n");
		System.out.print("no_more_element_counter : ");
		System.out.print(no_more_element_counter);
		System.out.print("\n");
	}

}
