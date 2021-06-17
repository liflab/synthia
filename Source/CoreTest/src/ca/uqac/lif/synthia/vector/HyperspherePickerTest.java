package ca.uqac.lif.synthia.vector;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomIntervalFloat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HyperspherePickerTest
{
	@Test
	public void interval()
	{
		Picker[] pickers = new Picker[10];
		int min = 1;
		int max = 25;
		int modulus = 10;
		for (int i = 0; i < pickers.length; i++)
		{
			pickers[i] = new RandomIntervalFloat(min, max);
		}
		HyperspherePicker hypersphere_picker = new HyperspherePicker((float) modulus, pickers);
		for (int i = 0; i < 10000 ; i++)
		{
			float[] random_floats = hypersphere_picker.pick();
			double sum = 0;
			for (int j = 0; j < random_floats.length; j++)
			{
				sum = Math.pow((double) random_floats[j], (double) 2);
			}
			double modulus_result =  Math.sqrt(sum);
			Assertions.assertTrue(0 <= modulus_result && modulus_result <= modulus);
		}
	}

	@Test
	public void duplicateWithState()
	{
		Picker[] pickers = new Picker[10];
		int min = 1;
		int max = 25;
		int modulus = 10;
		for (int i = 0; i < pickers.length; i++)
		{
			pickers[i] = new RandomIntervalFloat(min, max);
		}
		HyperspherePicker hypersphere_picker = new HyperspherePicker((float) modulus, pickers);
		float[] random_floats = hypersphere_picker.pick();
		for (int i = 0; i < 10; i++)
		{
			random_floats = hypersphere_picker.pick();
		}
		HyperspherePicker hypersphere_picker_copy = hypersphere_picker.duplicate(true);
		float[] random_floats_copy = hypersphere_picker_copy.pick();
		random_floats = hypersphere_picker.pick();
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
		int modulus = 10;
		for (int i = 0; i < pickers.length; i++)
		{
			pickers[i] = new RandomIntervalFloat(min, max);
		}
		HyperspherePicker hypersphere_picker = new HyperspherePicker((float) modulus, pickers);
		float[] random_floats = hypersphere_picker.pick();
		for (int i = 0; i < 10; i++)
		{
			random_floats = hypersphere_picker.pick();
		}
		HyperspherePicker hypersphere_picker_copy = hypersphere_picker.duplicate(false);
		float[] random_floats_copy = hypersphere_picker_copy.pick();
		random_floats = hypersphere_picker.pick();
		for (int i = 0; i < random_floats.length; i++)
		{
			Assertions.assertNotEquals(random_floats[i], random_floats_copy[i]);
		}
		hypersphere_picker.reset();
		hypersphere_picker_copy.reset();
		random_floats = hypersphere_picker.pick();
		random_floats_copy = hypersphere_picker_copy.pick();
		for (int i = 0; i < random_floats.length; i++)
		{
			Assertions.assertEquals(random_floats[i], random_floats_copy[i]);
		}
	}
}
