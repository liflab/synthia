package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.random.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SetPickerTest
{
	@ Test
	public void output(){
		RandomInteger random_integer = new RandomInteger(0, 10);
		RandomFloat random_float = new RandomFloat();
		RandomBoolean random_boolean = new RandomBoolean();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float ,random_boolean,
				random_string};
		SetPicker set_picker = new SetPicker(random_pickers);
		List<Object> set_picker_res = set_picker.pick();
		String[] expected_types = new String[] {"Integer", "Float", "Boolean", "String"};
		for (int i = 0; i < set_picker_res.size(); i++)
		{
			Assertions.assertEquals(set_picker_res.get(i).getClass().getSimpleName(),expected_types[i]);
		}
	}

	@Test
	public void duplicateWithState()
	{
		RandomInteger random_integer = new RandomInteger(0, 10000);
		RandomFloat random_float = new RandomFloat();
		RandomBoolean random_boolean = new RandomBoolean();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float, random_string};
		SetPicker set_picker = new SetPicker(random_pickers);
		for (int i = 0; i < random_pickers.length; i++)
		{
			set_picker.pick();
		}
		SetPicker set_picker_copy = (SetPicker) set_picker.duplicate(true);
		List<Object> set_picker_res = set_picker.pick();
		List<Object> set_picker_copy_res = set_picker_copy.pick();
		for (int i = 0; i < set_picker_res.size(); i++)
		{
			Assertions.assertEquals(set_picker_res.get(i), set_picker_copy_res.get(i));
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		RandomInteger random_integer = new RandomInteger(0, 10000);
		RandomFloat random_float = new RandomFloat();
		RandomString random_string = new RandomString(10);
		Picker[] random_pickers= new Picker[] {random_integer, random_float, random_string};
		SetPicker set_picker = new SetPicker(random_pickers);
		for (int i = 0; i < random_pickers.length; i++)
		{
			set_picker.pick();
		}
		SetPicker set_picker_copy = (SetPicker) set_picker.duplicate(false);
		List<Object> set_picker_res = set_picker.pick();
		List<Object> set_picker_copy_res = set_picker_copy.pick();
		for (int i = 0; i < set_picker_res.size(); i++)
		{
			Assertions.assertNotEquals(set_picker_res.get(i), set_picker_copy_res.get(i));
		}
		set_picker.reset();
		set_picker_copy.reset();
		for (int i = 0; i < random_pickers.length; i++)
		{
			set_picker_res = set_picker.pick();
			set_picker_copy_res = set_picker_copy.pick();
			for (int j = 0; j < set_picker_res.size(); j++)
			{
				Assertions.assertEquals(set_picker_res.get(j), set_picker_copy_res.get(j));
			}
		}
	}
}
