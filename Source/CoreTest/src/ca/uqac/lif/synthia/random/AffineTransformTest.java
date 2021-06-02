package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.replay.Playback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AffineTransformTest {
    @ Test
    public void sameValuesSameSeedInteger() {
        for (int i = 0; i < 10000; i++) {
            RandomInteger random_integer = new RandomInteger(0, 1000000);
            int random_seed = random_integer.pick();
            int random_val = random_integer.pick();
            RandomInteger random_integer1 = new RandomInteger(0, 1000);
            RandomInteger random_integer2 = new RandomInteger(0, 1000);
            random_integer1.setSeed(random_seed);
            random_integer2.setSeed(random_seed);
            AffineTransform.AffineTransformInteger affine_transform_integer = new AffineTransform
                .AffineTransformInteger(random_integer1, random_val, 0);
            AffineTransform.AffineTransformInteger affine_transform_integer1 = new AffineTransform
                .AffineTransformInteger(random_integer2, random_val, 0);
            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(affine_transform_integer.pick(),
                    affine_transform_integer1.pick());
            }
        }
    }

    @ Test
    public void sameValuesSameSeedFloat() {
        for (int i = 0; i < 10000; i++) {
            RandomInteger random_integer = new RandomInteger(0, 1000000);
            int random_seed = random_integer.pick();
            int random_val = random_integer.pick();
            RandomFloat random_float = new RandomFloat();
            random_float.setSeed(random_seed);
            AffineTransform.AffineTransformFloat affine_transform_float = new AffineTransform
                    .AffineTransformFloat(random_float, random_val, 0);
            AffineTransform.AffineTransformFloat affine_transform_float1 = new AffineTransform
                    .AffineTransformFloat(random_float, random_val, 0);
            for (int j = 0; j < 10000; j++) {
                Assertions.assertEquals(affine_transform_float.pickFloat(), // don't work
                                        affine_transform_float1.pickFloat()); //TODO issue #5
            }
        }
    }

    @ Test
    public void outputTest() {
        int m = 2;
        float m1 = (float) 3.57;
        int b = 5;
        float b1 = (float) 10;
        Integer[] integers = new Integer[]{2, 4, 6, 8};
        Float[] floats = new Float[]{(float) 7, (float) 14, (float) 21, (float) 28};
        Integer[] affine_t_int_results = new Integer[]{9, 13, 17, 21};
        Float[] affine_t_float_results = new Float[]{(((float) 7 * (float) 3.57) + (float) 10),
                (((float) 14 * (float) 3.57) + (float) 10),
                (((float) 21 * (float) 3.57) + (float) 10),
                (((float) 28 * (float) 3.57) + (float) 10)};
        Playback playback_int = new Playback(integers);
        Playback playback_float = new Playback(floats);
        AffineTransform affine_transform_int = new AffineTransform
                .AffineTransformInteger(playback_int, m, b);
        AffineTransform affine_transform_float = new AffineTransform
                .AffineTransformFloat(playback_float, m1, b1);
        for (int i = 0; i < affine_t_int_results.length; i++) {
            Assertions.assertEquals(affine_t_int_results[i], affine_transform_int.pick());
            Assertions.assertEquals(affine_t_float_results[i], affine_transform_float.pickFloat());
        }
    }
}
