package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomBooleanTest {
    @Test
    public void sameValuesSameSeed(){

        for (int i = 0; i < 10000; i++) {
            RandomInteger randomSeedGen=new RandomInteger(0,1000000);
            RandomBoolean randomBoolean=new RandomBoolean();
            RandomBoolean randomBoolean1=new RandomBoolean();

            int randomSeed=randomSeedGen.pick();

            randomBoolean.setSeed(randomSeed);
            randomBoolean1.setSeed(randomSeed);

            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(randomBoolean.pick(),randomBoolean1.pick());
            }
        }

    }
}
