package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomBooleanTest {
    @Test
    public void sameValuesSameSeed()
    {
        for (int i = 0; i < 10000; i++) {
            RandomInteger random_seed = new RandomInteger(0, 1000000);
            RandomBoolean random_boolean = new RandomBoolean();
            RandomBoolean random_boolean1 = new RandomBoolean();
            int randomSeed = random_seed.pick();
            random_boolean.setSeed(randomSeed);
            random_boolean1.setSeed(randomSeed);
            for (int j = 0; j < 10000; j++)
            {
                Assertions.assertEquals(random_boolean.pick(), random_boolean1.pick());
            }
        }
    }

    @Test
    public void duplicateWithState()
    {
        for (int i = 0; i < 10000; i++)
        {
            RandomBoolean random_boolean = new RandomBoolean();
            for (int j = 0; j < 10000; j++)
            {
                random_boolean.pick();
            }
            RandomBoolean random_boolean_copy = random_boolean.duplicate(true);
            Assertions.assertEquals(random_boolean.pick(), random_boolean_copy.pick());
        }
    }

    @Test
    public void duplicateWithoutState()
    {
        for (int i = 0; i < 10000; i++)
        {
            RandomBoolean random_boolean = new RandomBoolean();
            for (int j = 0; j < 10000; j++)
            {
                random_boolean.pick();
            }
            RandomBoolean random_boolean_copy = random_boolean.duplicate(false);
            random_boolean.reset();
            random_boolean_copy.reset();
            Assertions.assertEquals(random_boolean.pick(), random_boolean_copy.pick());
        }
    }
}
