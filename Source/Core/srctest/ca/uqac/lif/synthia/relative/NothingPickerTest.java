package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.util.NothingPicker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NothingPickerTest
{
	private void noMoreElementThrow(NothingPicker picker)
	{assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override
			public void execute() throws Throwable
			{
				picker.pick();
			}
		});
	}

	@Test                                                                                              
	public void pick()
	{
		NothingPicker picker = new NothingPicker();
		noMoreElementThrow(picker);
	}

	@Test
	public void shrink()
	{
		int element = 2;
		NothingPicker picker = new NothingPicker();
		noMoreElementThrow((NothingPicker) picker.shrink(element, new RandomFloat()));
	}
}
