package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.replay.Playback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringPatternTest
{
	@ Test
	public void output(){
		String[] names = new String[] { "Sylvain", "Raphael", "Fabio", "Sebastien" };
		String[] result_strings = new String[] { "Hello Sylvain!", "Hello Raphael!", "Hello Fabio!",
				"Hello Sebastien!" };
		Playback names_playback = new Playback(names);
		StringPattern spatern = new StringPattern("Hello {$0}!", names_playback);
		for (String result_string : result_strings)
		{
			Assertions.assertEquals(result_string, spatern.pick());
		}
	}

}
