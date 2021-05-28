package ca.uqac.lif.synthia.sequence;

import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.replay.Record;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RecordTest
{
	@ Test
	public void propertiesValidation() {
		RandomInteger random_integer = new RandomInteger(0, 100);
		Record record = new Record(random_integer);
		List <Integer> results = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++)
		{
			results.add((int) record.pick());
		}
		List picked_value = record.getValues();

		// Check that the values it records are indeed those it receives
		for (int i = 0; i < results.size(); i++)
		{
			Assertions.assertEquals(results.get(i), picked_value.get(i));
		}

		// Check that getCount returns the expected value
		Assertions.assertEquals(results.size(), record.getValues().size());
		Assertions.assertEquals(results.size(), record.getCount());

		// Check that reset wipes the stored values and makes the recording start anew
		results.clear();
		record.reset();
		Assertions.assertEquals(results.isEmpty(), record.getValues().isEmpty());
	}
}
