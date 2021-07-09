package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GaussianFloatTest {

        @Test
        public void sameValuesSameSeed(){
            for (int i = 0; i < 10000; i++) {
                RandomInteger random_integer=new RandomInteger(0, 1000000);
                int random_seed=random_integer.pick();
                GaussianFloat gaussian_float=new GaussianFloat();
                GaussianFloat gaussian_float1=new GaussianFloat();
                gaussian_float.setSeed(random_seed);
                gaussian_float1.setSeed(random_seed);
                for (int j = 0; j < 10000; j++) {
                    Assertions.assertEquals(gaussian_float.pick(), gaussian_float1.pick());
                }
            }
        }

  @Test
  public void duplicateWithState()
  {
    for (int i = 0; i < 10000; i++)
    {
      GaussianFloat gaussian_float=new GaussianFloat();
      for (int j = 0; j < 10000; j++)
      {
        gaussian_float.pick();
      }
      GaussianFloat gaussian_float_copy = (GaussianFloat) gaussian_float.duplicate(true);
      Assertions.assertEquals(gaussian_float.pick(), gaussian_float_copy.pick());
    }
  }

  @Test
  public void duplicateWithoutState()
  {
    for (int i = 0; i < 10000; i++)
    {
      GaussianFloat gaussian_float=new GaussianFloat();
      for (int j = 0; j < 10000; j++)
      {
        gaussian_float.pick();
      }
      GaussianFloat gaussian_float_copy = (GaussianFloat) gaussian_float.duplicate(false);
      Assertions.assertNotEquals(gaussian_float.pick(), gaussian_float_copy.pick());
      gaussian_float.reset();
      gaussian_float_copy.reset();
      Assertions.assertEquals(gaussian_float.pick(), gaussian_float_copy.pick());
    }
  }
}