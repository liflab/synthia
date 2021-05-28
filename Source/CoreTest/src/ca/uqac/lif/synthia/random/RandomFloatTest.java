package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomFloatTest {
    @Test
    public void SameValuesSameSeed(){

        for (int i = 0; i < 10000; i++) {
            RandomInteger randomSeedGen=new RandomInteger(0,1000000);
            RandomFloat randomFloat=new RandomFloat();
            RandomFloat randomFloat1=new RandomFloat();

            int randomSeed=randomSeedGen.pick();

            randomFloat.setSeed(randomSeed);
            randomFloat1.setSeed(randomSeed);


            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(randomFloat.pick(),randomFloat1.pick());
            }
        }

    }
}
