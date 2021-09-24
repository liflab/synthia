package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RandomBooleanTest {
    @Test
    public void sameValuesSameSeed()
    {
        SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
        List<Integer> int_list = seeds.getGeneralSeeds();
        for (int i = 0; i < 10; i++) {

            int random_seed = int_list.get(i);
            RandomBoolean random_boolean = new RandomBoolean();
            RandomBoolean random_boolean1 = new RandomBoolean();
            random_boolean.setSeed(random_seed);
            random_boolean1.setSeed(random_seed);
            for (int j = 0; j < 100; j++)
            {
                Assertions.assertEquals(random_boolean.pick(), random_boolean1.pick());
            }
        }
    }

    @Test
    public void duplicateWithState()
    {
        SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
        List<Integer> int_list = seeds.getGeneralSeeds();
        for (int i = 0; i < 10; i++)
        {
            RandomBoolean random_boolean = new RandomBoolean();
            random_boolean.setSeed(int_list.get(i));
            for (int j = 0; j < 100; j++)
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
        SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
        List<Integer> int_list = seeds.getGeneralSeeds();
        for (int i = 0; i < 10; i++)
        {
            RandomBoolean random_boolean = new RandomBoolean();
            random_boolean.setSeed(int_list.get(i));
            for (int j = 0; j < 100; j++)
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
