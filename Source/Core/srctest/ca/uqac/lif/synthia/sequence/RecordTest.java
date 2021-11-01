package ca.uqac.lif.synthia.sequence;

import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.sequence.Record;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RecordTest
{
	@Test
	public void propertiesValidation()
	{
		RandomInteger random_integer = new RandomInteger(0, 100);
		Record record = new Record(random_integer);
		List<Integer> results = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++)
		{
			results.add((int) record.pick());
		}
		List picked_value = record.getSequence();

		// Check that the values it records are indeed those it receives
		for (int i = 0; i < results.size(); i++)
		{
			Assertions.assertEquals(results.get(i), picked_value.get(i));
		}

		// Check that getCount returns the expected value
		Assertions.assertEquals(results.size(), record.getSequence().size());
		Assertions.assertEquals(results.size(), record.getCount());

		// Check that reset wipes the stored values and makes the recording start anew
		results.clear();
		record.reset();
		Assertions.assertEquals(results.isEmpty(), record.getSequence().isEmpty());
	}

	@Test
	public void duplicateWithState()
	{
		RandomInteger random_integer = new RandomInteger(0, 100);
		Record record = new Record(random_integer);
		for (int i = 0; i < 10; i++)
		{
			record.pick();
		}
		Record record_copy = (Record) record.duplicate(true);
		Assertions.assertEquals(record.getCount(), record_copy.getCount());
		List<Integer> record_list = record.getSequence();
		List<Integer> record_copy_list = record_copy.getSequence();
		for (int i = 0; i < record_list.size(); i++)
		{
			Assertions.assertEquals(record_list.get(i), record_copy_list.get(i));
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		RandomInteger random_integer = new RandomInteger(0, 100);
		Record record = new Record(random_integer);
		for (int i = 0; i < 10; i++)
		{
			record.pick();
		}
		Record record_copy = record.duplicate(false);
		int a = record.getCount();
		int b = record_copy.getCount();
		Assertions.assertNotEquals(record.getCount(), record_copy.getCount());
		for (int i = 0; i < 10; i++)
		{
			record_copy.pick();
		}
		List<Integer> record_list = record.getSequence();
		List<Integer> record_copy_list = record_copy.getSequence();
		Assertions.assertEquals(record.getCount(), record_copy.getCount());
		for (int i = 0; i < record_list.size(); i++)
		{
			Assertions.assertEquals(record_list.get(i), record_copy_list.get(i));
		}
	}
}
