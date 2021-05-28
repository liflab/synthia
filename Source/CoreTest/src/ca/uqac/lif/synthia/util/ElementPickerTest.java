package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.random.RandomFloat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ElementPickerTest
{
	//Partially test
	@ Test
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
}
