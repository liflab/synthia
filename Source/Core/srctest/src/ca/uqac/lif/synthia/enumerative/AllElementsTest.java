package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
				Assertions.assertEquals(list1.get(j), list2.get(j));
			}
		}
	}

	private void noMoreExceptionThrow(AllElements picker)
	{
		assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override
			public void execute() throws Throwable
			{
				picker.pick();
			}
		});
	}

	@Test
	public void loopNoScramble()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),false,true);

		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j <= 10; j++)
			{
				Assertions.assertEquals(j, all_elements.pick());
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

	@Test
	public void noLoopNoScramble()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),false,false);

		for (int i = 0; i <= 10; i++)
		{
			all_elements.pick();
		}

		noMoreExceptionThrow(all_elements);

	}

	@Test
	public void duplicateWithtState()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),false,false);
		for (int i = 0; i < 10; i++)
		{
			all_elements.pick();
		}

		AllElements<Integer> all_elements_copy = (AllElements<Integer>)
				all_elements.duplicate(true);

		Assertions.assertEquals(all_elements.pick(), all_elements_copy.pick());

		Assertions.assertEquals(true, all_elements.isDone());
		Assertions.assertEquals(all_elements.isDone(), all_elements_copy.isDone());
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

		Assertions.assertEquals(true, all_elements.isDone());
		Assertions.assertEquals(false, all_elements_copy.isDone());

		all_elements.reset();

		for (int i = 0; i <= 10 ; i++)
		{
			Assertions.assertEquals(all_elements.pick(), all_elements_copy.pick());
		}
	}

	@Test
	public void loopMethod()
	{
		AllElements<Integer> all_elements = new AllElements<Integer>(getList(),true,true);
		for (int i = 0; i < 11; i++)
		{
			all_elements.pick();
		}

		all_elements.setLoop(false);

		noMoreExceptionThrow(all_elements);
	}
}
