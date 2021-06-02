package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// don't work
//TODO issue #5
public class RandomStringTest {
    @Test
    public void sameValuesSameSeed(){

        for (int i = 0; i < 10000; i++) {
            RandomInteger randomSeedGen=new RandomInteger(0,10);
            int randomSeed=randomSeedGen.pick();

            RandomInteger randomSizeGenString=new RandomInteger(0,10);
            RandomInteger randomSizeGenString1=new RandomInteger(0,10);
            randomSizeGenString.setSeed(randomSeed);
            randomSizeGenString1.setSeed(randomSeed);

            RandomString randomString=new RandomString(randomSizeGenString.pick());
            RandomString randomString1=new RandomString(randomSizeGenString1.pick());
            String test=randomString.pick();
            String test1=randomString1.pick();


            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(randomString.pick(),randomString1.pick());
            }
        }

    }
}
