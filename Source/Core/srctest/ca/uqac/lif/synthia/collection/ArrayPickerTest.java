package ca.uqac.lif.synthia.collection;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.string.RandomString;
import ca.uqac.lif.synthia.util.ArrayPicker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayPickerTest
{
	@Test
	public void output()
	{
		RandomInteger random_integer = new RandomInteger(0, 10);
		RandomFloat random_float = new RandomFloat();
		RandomBoolean random_boolean = new RandomBoolean();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float ,random_boolean,
				random_string};
		ArrayPicker array_picker = new ArrayPicker(random_pickers);
		Object[] array_picker_res = array_picker.pick();
		String[] expected_types = new String[] {"Integer", "Float", "Boolean", "String"};
		for (int i = 0; i < array_picker_res.length; i++)
		{
			Assertions.assertEquals(array_picker_res[i].getClass().getSimpleName(),expected_types[i]);
		}
	}

	@Test
	public void duplicateWithState()
	{
		RandomInteger random_integer = new RandomInteger(0, 10);
		RandomFloat random_float = new RandomFloat();
		RandomBoolean random_boolean = new RandomBoolean();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float ,random_boolean,
				random_string};
		ArrayPicker array_picker = new ArrayPicker(random_pickers);
		for (int i = 0; i < random_pickers.length; i++)
		{
			array_picker.pick();
		}
		ArrayPicker array_picker_copy = (ArrayPicker) array_picker.duplicate(true);
		Object[] array_picker_res = array_picker.pick();
		Object[] array_picker_copy_res = array_picker_copy.pick();
		for (int i = 0; i < array_picker_res.length; i++)
		{
			Assertions.assertEquals(array_picker_res[i], array_picker_copy_res[i]);
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		RandomInteger random_integer = new RandomInteger(0, 10000);
		RandomFloat random_float = new RandomFloat();
		RandomBoolean random_boolean = new RandomBoolean();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float,
				random_string};
		ArrayPicker array_picker = new ArrayPicker(random_pickers);
		for (int i = 0; i < random_pickers.length; i++)
		{
			array_picker.pick();
		}
		ArrayPicker array_picker_copy = (ArrayPicker) array_picker.duplicate(false);
		Object[] array_picker_res = array_picker.pick();
		Object[] array_picker_copy_res = array_picker_copy.pick();
		for (int i = 0; i < array_picker_res.length; i++)
		{
			Assertions.assertNotEquals(array_picker_res[i], array_picker_copy_res[i]);
		}
		array_picker.reset();
		array_picker_copy.reset();
		for (int i = 0; i < random_pickers.length; i++)
		{
			array_picker_res = array_picker.pick();
			array_picker_copy_res = array_picker_copy.pick();
			for (int j = 0; j < array_picker_res.length; j++)
			{
				Assertions.assertEquals(array_picker_res[j], array_picker_copy_res[j]);
			}
		}
	}
}
