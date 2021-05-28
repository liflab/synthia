package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomIndexTest {
    @Test
    public void sameValuesSameSeed(){

        for (int i = 0; i < 10000; i++) {
            RandomInteger randomSeedGen=new RandomInteger(0,1000000);
            RandomIndex randomIndex=new RandomIndex(0,1000);
            RandomIndex randomIndex1=new RandomIndex(0,1000);

            int randomSeed=randomSeedGen.pick();

            randomIndex.setSeed(randomSeed);
            randomIndex1.setSeed(randomSeed);

            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(randomIndex.pick(),randomIndex1.pick());
            }
        }

    }
}
