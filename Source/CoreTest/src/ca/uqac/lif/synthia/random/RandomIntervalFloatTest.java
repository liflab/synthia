package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomIntervalFloatTest {
    @Test
    public void sameValuesSameSeed(){

        for (int i = 0; i < 10000; i++) {
            RandomInteger randomInteger=new RandomInteger(0,1000000);
            int randomSeed=randomInteger.pick();

            RandomFloat randomFloat=new RandomFloat();
            randomFloat.setSeed(randomSeed);
            float randomFloatVal=randomFloat.pick();
            int randomIntVal=randomInteger.pick();


            RandomIntervalFloat intervalFloat=new RandomIntervalFloat(0,randomIntVal*randomFloatVal);
            RandomIntervalFloat intervalFloat1=new RandomIntervalFloat(0,randomIntVal*randomFloatVal);




            intervalFloat.setSeed(randomSeed);
            intervalFloat1.setSeed(randomSeed);

            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(intervalFloat.pick(),intervalFloat1.pick());
            }
        }

    }

}
