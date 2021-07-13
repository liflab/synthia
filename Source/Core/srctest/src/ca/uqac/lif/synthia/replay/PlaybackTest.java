package ca.uqac.lif.synthia.replay;

import ca.uqac.lif.synthia.NoMoreElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlaybackTest
{

	@Test
  public void loop()
	{
		Integer[] int_array = new Integer[] { 1, 2, 1 };
		Playback int_pb = new Playback<Integer>(int_array);

		for (int i = 0; i < 100; i++)
		{
			for (Integer integer : int_array)
			{
				Assertions.assertEquals(integer, int_pb.pick());
			}
		}
	}

	@Test
  public void noMoreElementException()
	{

		Integer[] int_array = new Integer[] { 1, 2, 1 };
		Playback int_pb = new Playback<Integer>(int_array);
		int_pb.setLoop(false);
		for (int i = 0; i < int_array.length; i++)
		{
			int_pb.pick();
		}
		assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override public void execute() throws Throwable
			{
				int_pb.pick();
			}
		});

	}

	@Test
  public void duplicate()
	{
		Integer[] intarray = new Integer[] { 1, 2, 1 };
		Playback intpb = new Playback<Integer>(intarray);
		intpb.setLoop(false);
		for (int i = 0; i < intarray.length; i++)
		{
			intpb.pick();
		}
		Playback intpb2 = intpb.duplicate(true);
		assertThrows(NoMoreElementException.class, new Executable()
		{
			@Override public void execute() throws Throwable
			{
				intpb2.pick();
			}
		});
	}

	@Test
  public void isDone(){
      Integer[] int_array = new Integer[] { 1, 2, 1 };
      Playback int_pb = new Playback<Integer>(int_array);
      int_pb.setLoop(false);
      for (int i = 0; i < int_array.length; i++)
      {
      		Assertions.assertEquals(false,int_pb.isDone());
          int_pb.pick();
      }
      Assertions.assertEquals(true, int_pb.isDone());
  }

}
