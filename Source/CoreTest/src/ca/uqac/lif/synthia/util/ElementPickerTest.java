
package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.random.RandomFloat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ElementPickerTest
{

	//Partially test
	@Test
	public void pickProbability() {
		ElementPicker element_picker = new ElementPicker(new RandomFloat());
		element_picker.add("A", 0);
		element_picker.add("B", 0);
		element_picker.add("C", 0);
		element_picker.add("D", 1);
		element_picker.add("E", 0);
		element_picker.add("F", 0);
		for (int i = 0; i < element_picker.m_choices.size(); i++)
		{
			Assertions.assertEquals("D", element_picker.pick());
		}
	}

	@Test
	public void duplicateWithState() {
		ElementPicker element_picker = new ElementPicker(new RandomFloat());
		element_picker.add("A", 0);
		element_picker.add("B", 0);
		element_picker.add("C", 0);
		element_picker.add("D", 0.5);
		element_picker.add("E", 0.5);
		element_picker.add("F", 0);
		for (int i = 0; i < element_picker.m_choices.size(); i++)
		{
			element_picker.pick();
		}
		ElementPicker element_picker_copy = element_picker.duplicate(true);
		Assertions.assertEquals(element_picker.pick(), element_picker_copy.pick());
	}

	//TODO fix this
	@Test
	public void duplicateWithoutState() {
		ElementPicker element_picker = new ElementPicker(new RandomFloat());
		element_picker.add("A", 0);
		element_picker.add("B", 0);
		element_picker.add("C", 0);
		element_picker.add("D", 0.5);
		element_picker.add("E", 0.5);
		element_picker.add("F", 0);
		for (int i = 0; i < element_picker.m_choices.size(); i++)
		{
			element_picker.pick();
		}
		ElementPicker element_picker_copy = element_picker.duplicate(false);
		Assertions.assertNotEquals(element_picker.pick(), element_picker_copy.pick());
		element_picker.reset();
		element_picker_copy.reset();
		for (int i = 0; i < element_picker.m_choices.size(); i++)
		{
			Assertions.assertEquals(element_picker.pick(), element_picker_copy.pick());
		}
	}
}

