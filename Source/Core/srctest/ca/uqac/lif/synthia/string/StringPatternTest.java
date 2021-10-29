package ca.uqac.lif.synthia.string;

import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.random.RandomString;
import ca.uqac.lif.synthia.sequence.Playback;
import ca.uqac.lif.synthia.string.StringPattern;

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




	@Test
	public void interval()
	{
		int min = 1;
		int max = 25;
		RandomInteger random_integer = new RandomInteger(min, max);
		RandomString random_string = new RandomString(random_integer);
		StringPattern string_pattern = new StringPattern("Hello {$0}!", random_string);
		int pattern_lenght = 7;
		for (int i = 0; i < 10000; i++)
		{
			String random_val = string_pattern.pick();
			Assertions.assertTrue(
					((min + pattern_lenght) <= random_val.length()) && (random_val.length() <= (pattern_lenght
							+ max)));
		}
	}

	@Test
	public void lenght()
	{
		int min = 1;
		int max = 25;
		RandomInteger random_integer = new RandomInteger(min, max);
		int elementlenght = random_integer.pick();
		RandomString random_string = new RandomString(elementlenght);
		StringPattern string_pattern = new StringPattern("Hello {$0}!", random_string);
		int pattern_lenght = 7 + elementlenght;
		for (int i = 0; i < 10000; i++)
		{
			Assertions.assertEquals(pattern_lenght, string_pattern.pick().length());
		}
	}

	@Test
	public void duplicateWithState()
	{
		String[] names = new String[] { "Sylvain", "Raphael", "Fabio", "Sebastien" };
		Playback names_playback = new Playback(names);
		StringPattern string_patern = new StringPattern("Hello {$0}!", names_playback);
		string_patern.pick();
		string_patern.pick();
		StringPattern string_patern_copy = (StringPattern) string_patern.duplicate(true);
		for (int i = 0; i < 10; i++)
		{
			Assertions.assertEquals(string_patern.pick(), string_patern_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		String[] names = new String[] { "Sylvain", "Raphael", "Fabio", "Sebastien" };
		Playback names_playback = new Playback(names);
		StringPattern string_patern = new StringPattern("Hello {$0}!", names_playback);
		string_patern.pick();
		string_patern.pick();
		StringPattern string_patern_copy = (StringPattern) string_patern.duplicate(false);
		Assertions.assertNotEquals(string_patern.pick(), string_patern_copy.pick());
		string_patern.reset();
		string_patern_copy.reset();
		for (int i = 0; i < 10; i++)
		{
			Assertions.assertEquals(string_patern.pick(), string_patern_copy.pick());
		}
	}
}

