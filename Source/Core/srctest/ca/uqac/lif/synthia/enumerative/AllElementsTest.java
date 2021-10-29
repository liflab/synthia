package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.GiveUpException;
import ca.uqac.lif.synthia.NoMoreElementException;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;



public class AllElementsTest
{
	private List<Integer> getList()
	{
		List<Integer> data = new ArrayList<Integer>();

		for (int i = 0; i <= 10 ; i++)
		{
			data.add(i);
		}

		return data;
	}

	private void checkSameList(List<List<Integer>> all_list)
	{
		for (int i = 0; i < (all_list.size() - 1); i++)
		{
			List<Integer> list1 = all_list.get(i);
			List<Integer> list2 = all_list.get(i + 1);

			for (int j = 0; j < list1.size(); j++)
			{
				Assert.assertEquals(list1.get(j), list2.get(j));
			}
		}
	}

	@Test
	public void loopNoScramble()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),false,true);

		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j <= 10; j++)
			{
				Assert.assertEquals(j, (int) all_elements.pick());
			}
		}
	}

	@Test
	public void loopScramble()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),true,true);
		List<Integer> picked_values = new ArrayList<Integer>();
		List<List<Integer>> all_lists = new ArrayList<List<Integer>>();
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j <= 10; j++)
			{
				picked_values.add(all_elements.pick());
			}
			all_lists.add(new ArrayList<Integer>(picked_values));
			picked_values.clear();
			all_elements.reset();

		}
		checkSameList(all_lists);

	}

	@Test(expected = NoMoreElementException.class)
	public void noLoopNoScramble()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),false,false);

		for (int i = 0; i <= 10; i++)
		{
			all_elements.pick();
		}

		all_elements.pick();

	}

	@Test
	public void duplicateWithtState()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),false,false);
		for (int i = 0; i <= 10; i++)
		{
			all_elements.pick();
		}

		AllElements<Integer> all_elements_copy = (AllElements<Integer>)
				all_elements.duplicate(true);

		Assert.assertEquals(all_elements.pick(), all_elements_copy.pick());

		Assert.assertEquals(true, all_elements.isDone());
		Assert.assertEquals(all_elements.isDone(), all_elements_copy.isDone());
	}

	@Test
	public void duplicateWithoutState()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),true,false);
		for (int i = 0; i <= 10; i++)
		{
			all_elements.pick();
		}

		AllElements<Integer> all_elements_copy = (AllElements<Integer>)
				all_elements.duplicate(false);

		Assert.assertEquals(true, all_elements.isDone());
		Assert.assertEquals(false, all_elements_copy.isDone());

		all_elements.reset();

		for (int i = 0; i <= 100 ; i++)
		{
			Assert.assertEquals(all_elements.pick(), all_elements_copy.pick());
		}
	}

	@Test(expected = NoMoreElementException.class)
	public void loopMethod()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),true,true);
		for (int i = 0; i < 11; i++)
		{
			all_elements.pick();
		}

		all_elements.setLoop(false);

		all_elements.pick();
	}
}
