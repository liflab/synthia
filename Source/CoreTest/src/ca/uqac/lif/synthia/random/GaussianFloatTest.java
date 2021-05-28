package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GaussianFloatTest {

        @Test
        public void sameValuesSameSeed(){
            for (int i = 0; i < 10000; i++) {
                RandomInteger randomInteger=new RandomInteger(0,1000000);
                int randomSeed=randomInteger.pick();
                GaussianFloat gaussianFloat=new GaussianFloat();
                GaussianFloat gaussianFloat1=new GaussianFloat();
                gaussianFloat.setSeed(randomSeed);
                gaussianFloat1.setSeed(randomSeed);
                for (int j = 0; j < 10000; j++) {
                    Assertions.assertEquals(gaussianFloat.pick(),gaussianFloat1.pick());
                }
            }
        }
}
