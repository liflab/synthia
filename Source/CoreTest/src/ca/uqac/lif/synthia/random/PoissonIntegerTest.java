package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// don't work
//TODO issue #5
public class PoissonIntegerTest {
    @Test
    public void sameValuesSameSeed(){

        for (int i = 0; i < 10000; i++) {
            RandomInteger randomInteger=new RandomInteger(0,1000000);
            int randomSeed=randomInteger.pick();
            int randomSeed1=randomInteger.pick();
            PoissonInteger smallPoissonInteger=new PoissonInteger(25);
            PoissonInteger smallPoissonInteger1=new PoissonInteger(25);
            PoissonInteger bigPoissonInteger=new PoissonInteger(1000);
            PoissonInteger bigPoissonInteger1=new PoissonInteger(1000);
            smallPoissonInteger.setSeed(randomSeed);
            smallPoissonInteger.setSeed(randomSeed);
            bigPoissonInteger.setSeed(randomSeed1);
            bigPoissonInteger1.setSeed(randomSeed1);
            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(smallPoissonInteger.pick(),smallPoissonInteger1.pick());
                Assertions.assertEquals(bigPoissonInteger.pick(),bigPoissonInteger1.pick());
            }
        }

    }
}
