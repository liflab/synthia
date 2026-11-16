package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.sequence.Playback;
import ca.uqac.lif.synthia.string.RandomString;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FreezeTest
{

	@Test public void sameValueResetTest()
	{
		int arraySize = 3;
		RandomInteger ri = new RandomInteger(0, 100);
		RandomString rs = new RandomString(ri.pick());
		RandomFloat rf = new RandomFloat();
		RandomBoolean rb = new RandomBoolean();

		Integer[] ia = new Integer[arraySize];
		Float[] fa = new Float[arraySize];
		String[] sa = new String[arraySize];
		Boolean[] ba = new Boolean[arraySize];

		for (int i = 0; i < arraySize; i++)
		{
			ia[i] = ri.pick();
			fa[i] = rf.pick();
			sa[i] = rs.pick();
			ba[i] = rb.pick();
		}

		Playback<Integer> ip = new Playback<Integer>(ia);
		Freeze<Integer> iF = new Freeze<Integer>(ip);
		Playback<String> sp = new Playback<String>(sa);
		Freeze<String> sf = new Freeze<String>(sp);
		Playback<Float> fp = new Playback<Float>(fa);
		Freeze<Float> ff = new Freeze<Float>(fp);
		Playback<Boolean> bp = new Playback<Boolean>(ba);
		Freeze<Boolean> bf = new Freeze<Boolean>(bp);

		int initialIntPick = 0;
		float initialFloatPick = 0;
		String initialStringPick = "";
		boolean initialBooleanPick = false;

		for (int i = 0; i < 3; i++)
		{
			int intValue = iF.pick();
			float floatValue = ff.pick();
			String stringValue = sf.pick();
			boolean booleanValue = bf.pick();
			if (i == 0)
			{
				initialIntPick = intValue;
				initialFloatPick = floatValue;
				initialStringPick = stringValue;
				initialBooleanPick = booleanValue;
			}

			System.out.println(intValue);
			System.out.println(floatValue);
			System.out.println(stringValue);
			System.out.println(booleanValue);

			for (int j = 0; j < 10; j++)
			{
				Assertions.assertEquals(intValue, (int) iF.pick());
				Assertions.assertEquals(floatValue, (float) ff.pick());
				Assertions.assertEquals(stringValue, sf.pick());
				Assertions.assertEquals(initialBooleanPick, (boolean) bf.pick());

			}

			//to test that the reset method of the freeze picker reset the playback.
			iF.reset();
			ff.reset();
			sf.reset();
			bf.reset();

			Assertions.assertEquals(initialIntPick, (int) iF.pick());
			Assertions.assertEquals(initialFloatPick, (float) ff.pick());
			Assertions.assertEquals(initialStringPick, sf.pick());
			Assertions.assertEquals(initialBooleanPick, (boolean) bf.pick());
		}
	}

	@Test
	public void duplicateWithState()
	{
		RandomFloat random_float = new RandomFloat();
		Freeze<Float> freeze = new Freeze<Float>(random_float);
		freeze.pick();
		Freeze<Float> freeze_copy = freeze.duplicate(true);
		Assertions.assertEquals(freeze.pick(), freeze_copy.pick());
	}

	// TODO fix this
	@Test
	public void duplicateWithoutState()
	{
		RandomFloat random_float = new RandomFloat();
		Freeze<Float> freeze = new Freeze<Float>(random_float);
		freeze.pick();
		Freeze<Float> freeze_copy = freeze.duplicate(false);
		freeze.reset();
		freeze_copy.reset();
		Assertions.assertEquals(freeze.pick(), freeze_copy.pick());
	}
}

