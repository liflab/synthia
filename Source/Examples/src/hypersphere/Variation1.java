package hypersphere;

import ca.uqac.lif.synthia.random.AffineTransform.AffineTransformFloat;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.vector.HyperspherePicker;

public class Variation1 
{

	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		RandomInteger radius = new RandomInteger(1, 3);
		RandomFloat r_float = new RandomFloat().setSeed(42);
		AffineTransformFloat angle = new AffineTransformFloat(r_float, 2 * Math.PI, 0);
		HyperspherePicker hp = new HyperspherePicker(radius, angle);
		for (int i = 0; i < 10; i++)
		{
			System.out.println(hp.pick());
		}

	}

}
