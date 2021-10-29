package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.GiveUpException;
import ca.uqac.lif.synthia.NoMoreElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DiscardPickerTest
{
	@Test
	public void ratio()
	{
		DiscardRatio ratio = new DiscardRatio((float) 0.5);
		ratio.countAsAccepted();
		ratio.countAsAccepted();
		ratio.countAsDiscarted();

		Assertions.assertEquals(true,(((float)0.5) > ratio.pick()));
	}

	@Test
	public void reset()
	{
		DiscardRatio ratio = new DiscardRatio((float) 0.5);
		ratio.countAsAccepted();
		ratio.countAsAccepted();
		ratio.countAsDiscarted();

		ratio.reset();

		float nan = 0f / 0f;

		Assertions.assertEquals(nan, ratio.pick());
	}

	@Test
	public void giveUp()
	{
		DiscardRatio ratio = new DiscardRatio((float) 0.5);
		ratio.countAsAccepted();
		ratio.countAsAccepted();
		ratio.countAsDiscarted();
		ratio.countAsDiscarted();
		ratio.countAsDiscarted();

		assertThrows(GiveUpException.class, new Executable()
		{
			@Override public void execute() throws Throwable
			{
				ratio.pick();
			}
		});
	}

	@Test
	public void duplicateWithState()
	{
		DiscardRatio ratio = new DiscardRatio((float) 0.5);
		ratio.countAsAccepted();
		ratio.countAsAccepted();
		ratio.countAsDiscarted();

		DiscardRatio ratio_copy = ratio.duplicate(true);

		Assertions.assertEquals(ratio.pick(), ratio_copy.pick());
	}

	@Test
	public void duplicateWithoutState()
	{
		DiscardRatio ratio = new DiscardRatio((float) 0.5);
		ratio.countAsAccepted();
		ratio.countAsAccepted();
		ratio.countAsDiscarted();

		DiscardRatio ratio_copy = ratio.duplicate(false);

		Assertions.assertNotEquals(ratio.pick(), ratio_copy.pick());

		ratio_copy.countAsAccepted();
		ratio_copy.countAsAccepted();
		ratio_copy.countAsDiscarted();

		Assertions.assertEquals(ratio.pick(), ratio_copy.pick());

	}

}
