package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import org.junit.Test;
import org.junit.Assert;

public class NothingPickerTest
{

	@Test(expected = NoMoreElementException.class)
	public void pick()
	{
		NothingPicker picker = new NothingPicker();
		picker.pick();
	}

	@Test(expected = NoMoreElementException.class)
	public void getPicker()
	{
		int element = 2;
		NothingPicker picker = new NothingPicker();
		((NothingPicker) picker.getPicker(element)).pick();
	}
}
