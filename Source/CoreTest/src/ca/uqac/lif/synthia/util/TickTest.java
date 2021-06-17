package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.util.Tick;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class TickTest {


    @Test
    void incrementTest(){
        int intTemp;
        int intStart=0;
        int intIncrement=1;
        Tick intTick=new Tick(intStart,intIncrement);

        float floatStart=(float)1.5;
        float floatTemp;
        float floatIncrement=(float) 0.5;
        Tick floatTiok=new Tick(floatStart,floatIncrement);

        double doubleStart=1.75;
        double doubleIncrement=1.5;
        double doubleTemp;
        Tick doubleTick=new Tick(doubleStart,doubleIncrement);

        for(int i=0; i<100;i++){
            intTemp=intTick.pick().intValue();
            doubleTemp=doubleTick.pick().doubleValue();
            floatTemp=floatTiok.pick().floatValue();

            Assertions.assertEquals(intIncrement,intTemp-intStart);
            Assertions.assertEquals(doubleIncrement,doubleTemp-doubleStart);
            Assertions.assertEquals(floatIncrement,floatTemp-floatStart);

            intStart=intTemp;
            doubleStart=doubleTemp;
            floatStart=floatTemp;
        }



    }


	@Test
  public void duplicateWithState()
  {
      int start = 0;
      int increment = 1;
      Tick tick = new Tick(start, increment);
      tick.pick();
      tick.pick();
      Tick tick_copy = tick.duplicate(true);
      Assertions.assertEquals(tick.pick(), tick_copy.pick());
  }

    @Test
    public void duplicateWithoutState()
    {
        int start = 0;
        int increment = 1;
        Tick tick = new Tick(start, increment);
        tick.pick();
        tick.pick();
        Tick tick_copy = tick.duplicate(false);
        Assertions.assertNotEquals(tick.pick(), tick_copy.pick());
    }

    @Test
    public void duplicateStartValue()
    {
        int start = 0;
        int increment = 1;
        Tick tick = new Tick(start, increment);
        Tick tick_copy = tick.duplicate((float) 2);
        tick.pick();
        tick.pick();
        Assertions.assertEquals(tick.pick(), tick_copy.pick());
    }
}

