package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.random.RandomString;
import ca.uqac.lif.synthia.replay.Playback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ListPickerTest
{
	@ Test
	public void output(){
		RandomInteger random_integer = new RandomInteger(0, 10);
		RandomFloat random_float = new RandomFloat();
		RandomBoolean random_boolean = new RandomBoolean();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float ,random_boolean,
				random_string};
		ListPicker list_picker = new ListPicker(random_pickers);
		List<Object> list_picker_res = list_picker.pick();
		String[] expected_types = new String[] {"Integer", "Float", "Boolean", "String"};
		for (int i = 0; i < list_picker_res.size(); i++)
		{
			Assertions.assertEquals(list_picker_res.get(i).getClass().getSimpleName(),expected_types[i]);
		}
	}

	@Test
	public void duplicateWithState()
	{
		RandomInteger random_integer = new RandomInteger(0, 10000);
		RandomFloat random_float = new RandomFloat();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float, random_string};
		ListPicker list_picker = new ListPicker(random_pickers);
		for (int i = 0; i < random_pickers.length; i++)
		{
			list_picker.pick();
		}
		ListPicker list_picker_copy = (ListPicker) list_picker.duplicate(true);
		List<Object> list_picker_res = list_picker.pick();
		List<Object> list_picker_copy_res = list_picker_copy.pick();
		for (int i = 0; i < list_picker_res.size(); i++)
		{
			Assertions.assertEquals(list_picker_res.get(i), list_picker_copy_res.get(i));
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		RandomInteger random_integer = new RandomInteger(0, 10000);
		RandomFloat random_float = new RandomFloat();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float, random_string};
		ListPicker list_picker = new ListPicker(random_pickers);
		for (int i = 0; i < random_pickers.length; i++)
		{
			list_picker.pick();
		}
		ListPicker list_picker_copy = list_picker.duplicate(false);
		List<Object> list_picker_res = list_picker.pick();
		List<Object> list_picker_copy_res = list_picker_copy.pick();
		for (int i = 0; i < list_picker_res.size(); i++)
		{
			Assertions.assertNotEquals(list_picker_res.get(i), list_picker_copy_res.get(i));
		}
		list_picker.reset();
		list_picker_copy.reset();
		for (int i = 0; i < random_pickers.length; i++)
		{
			list_picker_res = list_picker.pick();
			list_picker_copy_res = list_picker_copy.pick();
			for (int j = 0; j < list_picker_res.size(); j++)
			{
				Assertions.assertEquals(list_picker_res.get(j), list_picker_copy_res.get(j));
			}
		}
	}

	@Test
	public void pickerLenghtConstructor()
	{
		RandomInteger random_integer = new RandomInteger(0, 10000);
		RandomFloat random_float = new RandomFloat();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float, random_string};

		Integer[] int_array = new Integer[] { 3, 6, 7 };
		Playback size_picker = new Playback(int_array);
		size_picker.setLoop(false);

		ListPicker list_picker = new ListPicker(size_picker, random_pickers);

		for (int i = 0; i < 3; i++)
		{
			List<Object> picked_values = list_picker.pick();
			Assertions.assertEquals(int_array[i], picked_values.size());
		}
	}

}
