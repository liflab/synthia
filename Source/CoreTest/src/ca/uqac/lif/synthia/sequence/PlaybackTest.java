package ca.uqac.lif.synthia.sequence;
import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.replay.Playback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Assertions;
public class PlaybackTest {

    @Test
    public void loop(){
        Integer [] intarray= new Integer[] {1,2,1};
        Playback intpb=new Playback<Integer>(intarray);

        for (int i = 0; i < 100; i++) {
            for (Integer integer : intarray) {
                Assertions.assertEquals(integer, intpb.pick());
            }
        }
    }

    @Test
    public void noMoreElementException(){

        Integer [] intarray= new Integer[] {1,2,1};
        Playback intpb=new Playback<Integer>(intarray);
        intpb.loop(false);
        for (int i = 0; i < intarray.length; i++) {
            intpb.pick();
        }
        assertThrows(NoMoreElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                intpb.pick();
            }
        });

    }

    @Test
    public void duplicate(){
        Integer [] intarray= new Integer[] {1,2,1};
        Playback intpb=new Playback<Integer>(intarray);
        intpb.loop(false);
        for (int i = 0; i < intarray.length; i++) {
            intpb.pick();
        }
        Playback intpb2=intpb.duplicate(true);
        assertThrows(NoMoreElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                intpb2.pick();
            }
        });
    }
    
  @Test
  public void isDone(){
      Integer[] int_array = new Integer[] { 1, 2, 1 };
      Playback int_pb = new Playback<Integer>(int_array);
      int_pb.loop(false);
      for (int i = 0; i < int_array.length; i++)
      {
          int_pb.pick();
      }
      Assertions.assertEquals(true, int_pb.isDone());
  }
}
