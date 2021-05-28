package ca.uqac.lif.synthia.random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class RandomIntegerTest {

    @Test
    public void sameValuesSameSeed(){

        for (int i = 0; i < 10000; i++) {
            RandomInteger randomSeedGen=new RandomInteger(0,1000000);
            RandomInteger randomInteger1=new RandomInteger(0,1000);
            RandomInteger randomInteger2=new RandomInteger(0,1000);

            int randomSeed=randomSeedGen.pick();

            randomInteger1.setSeed(randomSeed);
            randomInteger2.setSeed(randomSeed);

            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(randomInteger1.pick(),randomInteger2.pick());
            }
        }

    }
}
