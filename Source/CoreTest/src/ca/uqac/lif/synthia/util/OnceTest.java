package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.replay.Playback;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OnceTest {

    @Test
    public void noMoreExceptionTest(){
        RandomFloat randomFloat=new RandomFloat();
        Once once=new Once(randomFloat);
        once.pick();
        assertThrows(NoMoreElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                once.pick();
            }
        });
    }
}
