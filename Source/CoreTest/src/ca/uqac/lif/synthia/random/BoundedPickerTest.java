package ca.uqac.lif.synthia.random;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

// test with float random float
//useful?
public class BoundedPickerTest {
    @Test
    public void sameValuesSameSeed(){
        for (int i = 0; i < 10000; i++) {
            RandomInteger randomInteger=new RandomInteger(0,1000000);
            RandomInteger randomInteger1=new RandomInteger(0,1000);
            RandomInteger randomInteger2=new RandomInteger(0,1000);
            RandomFloat   randomFloat=new RandomFloat();
            RandomFloat randomFloat1=new RandomFloat();
            int randomSeed=randomInteger.pick();
            randomInteger1.setSeed(randomSeed);
            randomInteger2.setSeed(randomSeed);
            randomFloat.setSeed(randomSeed);
            randomFloat1.setSeed(randomSeed);
            BoundedPicker boundedPicker=new BoundedPicker(randomFloat,randomInteger1,10000);
            BoundedPicker boundedPicker1=new BoundedPicker(randomFloat1,randomInteger2,10000);
            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(boundedPicker.pick(),boundedPicker1.pick());
            }
        }


    }
}
