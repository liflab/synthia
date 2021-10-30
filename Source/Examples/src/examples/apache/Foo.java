package examples.apache;

import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.string.AsString;
import ca.uqac.lif.synthia.string.StringPattern;
import ca.uqac.lif.synthia.util.Choice;

public class Foo
{

	public static void main(String[] args)
	{
		StringPattern ip = new StringPattern("{$0}.{$1}.{$2}.{$3}",
				new Choice<String>(RandomFloat.instance)
				.add("10", 1/3).add("11", 1/2).add(new AsString(new RandomInteger(20, 61)), 1/6),
				new AsString(new RandomInteger(0, 256)), new AsString(new RandomInteger(0, 256)), new AsString(new RandomInteger(0, 256))
				);
	}

}
