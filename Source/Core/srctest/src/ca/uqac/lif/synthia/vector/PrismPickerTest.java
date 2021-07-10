package ca.uqac.lif.synthia.vector;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomFloat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrismPickerTest
{
	@Test
	public void interval1()
	{
		Picker[] pickers = new Picker[10];
		for (int i = 0; i < pickers.length; i++)
		{
			pickers[i] = new RandomFloat();
		}
		PrismPicker prism_picker = new PrismPicker(pickers);
		for (int i = 0; i < 10000 ; i++)
		{
			float[] random_floats = prism_picker.pick();
			for (int j = 0; j < random_floats.length; j++)
			{
				Assertions.assertTrue(0 <= random_floats[j] && random_floats[j] <= 1);
			}
		}
	}

	@Test
	public void interval2()
	{
		Picker[] pickers = new Picker[10];
		int min = 1;
		int max = 25;
		for (int i = 0; i < pickers.length; i++)
		{
			pickers[i] = new RandomFloat(min, max);
		}
		PrismPicker prism_picker = new PrismPicker(pickers);
		for (int i = 0; i < 10000 ; i++)
		{
			float[] random_floats = prism_picker.pick();
			for (int j = 0; j < random_floats.length; j++)
			{
				Assertions.assertTrue(min <= random_floats[j] && random_floats[j] <= max);
			}
		}
	}

	@Test
	public void duplicateWithState()
	{
		Picker[] pickers = new Picker[10];
		int min = 1;
		int max = 25;
		for (int i = 0; i < pickers.length; i++)
		{
			pickers[i] = new RandomFloat(min, max);
		}
		PrismPicker prism_picker = new PrismPicker(pickers);
		float[] random_floats = prism_picker.pick();
		for (int i = 0; i < 10; i++)
		{
			random_floats = prism_picker.pick();
		}
		PrismPicker prism_picker_copy = prism_picker.duplicate(true);
		float[] random_floats_copy = prism_picker_copy.pick();
		random_floats = prism_picker.pick();
		for (int i = 0; i < random_floats.length; i++)
		{
			Assertions.assertEquals(random_floats[i], random_floats_copy[i]);
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		Picker[] pickers = new Picker[10];
		int min = 1;
		int max = 25;
		for (int i = 0; i < pickers.length; i++)
		{
			pickers[i] = new RandomFloat(min, max);
		}
		PrismPicker prism_picker = new PrismPicker(pickers);
		float[] random_floats = prism_picker.pick();
		for (int i = 0; i < 10; i++)
		{
			random_floats = prism_picker.pick();
		}
		PrismPicker prism_picker_copy = prism_picker.duplicate(false);
		float[] random_floats_copy = prism_picker_copy.pick();
		random_floats = prism_picker.pick();
		for (int i = 0; i < random_floats.length; i++)
		{
			Assertions.assertNotEquals(random_floats[i], random_floats_copy[i]);
		}
		prism_picker.reset();
		prism_picker_copy.reset();
		random_floats = prism_picker.pick();
		random_floats_copy = prism_picker_copy.pick();
		for (int i = 0; i < random_floats.length; i++)
		{
			Assertions.assertEquals(random_floats[i], random_floats_copy[i]);
		}
	}
}
