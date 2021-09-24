package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GaussianFloatTest {

        @Test
        public void sameValuesSameSeed(){
          SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
          List<Integer> int_list = seeds.getGeneralSeeds();
            for (int i = 0; i < 10; i++) {
                int random_seed=int_list.get(i);
                GaussianFloat gaussian_float=new GaussianFloat();
                GaussianFloat gaussian_float1=new GaussianFloat();
                gaussian_float.setSeed(random_seed);
                gaussian_float1.setSeed(random_seed);
                for (int j = 0; j < 100; j++)
                {
                    Assertions.assertEquals(gaussian_float.pick(), gaussian_float1.pick());
                }
            }
        }

  @Test
  public void duplicateWithState()
  {
    SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
    List<Integer> int_list = seeds.getGeneralSeeds();
    for (int i = 0; i < 10; i++) {
      int random_seed=int_list.get(i);
      GaussianFloat gaussian_float=new GaussianFloat();
      gaussian_float.setSeed(random_seed);
      for (int j = 0; j < 100; j++)
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
    SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
    List<Integer> int_list = seeds.getGeneralSeeds();
    for (int i = 0; i < 10; i++) {
      int random_seed=int_list.get(i);
      GaussianFloat gaussian_float=new GaussianFloat();
      gaussian_float.setSeed(random_seed);
      for (int j = 0; j < 100; j++)
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