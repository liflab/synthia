package ca.uqac.lif.synthia.random;

import java.util.concurrent.atomic.AtomicLong;
import ca.uqac.lif.synthia.random.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomTest
{
	@Test
	public void helloWorld()
	{
		Random random = new Random();
	}

	@Test
	public void atomic()
	{
		AtomicLong atomic = new AtomicLong(1);
		System.out.println(atomic.get());
		System.out.println(atomic.get());
		System.out.println(atomic.get());
		System.out.println(atomic.get());
		System.out.println(atomic.get());
	}

	@Test
	public void duplicationInt()
	{
		Random random = new Random(1);
		for (int i = 0; i < 10; i++)
		{
			random.nextInt();
		}
		Random random_copy = random.Duplicate();
		Assertions.assertEquals(random.nextInt(), random_copy.nextInt());
	}

	@Test
	public void duplicationFloat()
	{
		Random random = new Random(1);
		for (int i = 0; i < 10; i++)
		{
			random.nextFloat();
		}
		Random random_copy = random.Duplicate();
		Assertions.assertEquals(random.nextFloat(), random_copy.nextFloat());
	}

	@Test
	public void duplicationBoolean()
	{
		Random random = new Random(1);
		for (int i = 0; i < 10; i++)
		{
			random.nextBoolean();
		}
		Random random_copy = random.Duplicate();
		Assertions.assertEquals(random.nextBoolean(), random_copy.nextBoolean());
	}

	@Test
	public void duplicationGaussian()
	{
		Random random = new Random(1);
		for (int i = 0; i < 10; i++)
		{
			random.nextGaussian();
		}
		Random random_copy = random.Duplicate();
		Assertions.assertEquals(random.nextGaussian(), random_copy.nextGaussian());
	}
}
