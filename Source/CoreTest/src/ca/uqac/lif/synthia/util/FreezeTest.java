package ca.uqac.lif.synthia.util;


import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.random.RandomString;
import ca.uqac.lif.synthia.replay.Playback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class FreezeTest {

    @Test
    public void sameValueResetTest(){

        int arraySize=3;

        RandomInteger ri=new RandomInteger(0,100);
        RandomString rs=new RandomString(ri.pick());
        RandomFloat rf=new RandomFloat();
        RandomBoolean rb=new RandomBoolean();



        Integer[] ia=new Integer[arraySize];
        Float[] fa=new Float[arraySize];
        String[] sa=new String[arraySize];
        Boolean[] ba=new Boolean[arraySize];

        ia[0]=ri.pick();
        fa[0]=rf.pick();
        sa[0]=rs.pick();
        ba[0]=rb.pick();
        ia[1]=ri.pick();
        fa[1]=rf.pick();
        sa[1]=rs.pick();
        ba[1]=rb.pick();
        ia[2]=ri.pick();
        fa[2]=rf.pick();
        sa[2]=rs.pick();
        ba[2]=rb.pick();

        //fill the array
        /* don't work
        for(int i=0;i<arraySize;i++){
           ia[i]=ri.pick();
           fa[i]=rf.pick();
           sa[i]=rs.pick();
           ba[i]=rb.pick();

        }
        */

        Playback ip=new Playback<Integer>(ia);
        Freeze iF= new Freeze(ip);
        Playback sp=new Playback<String>(sa);
        Freeze sf=new Freeze(sp);
        Playback fp=new Playback<Float>(fa);
        Freeze ff=new Freeze(fp);
        Playback bp=new Playback<Boolean>(ba);
        Freeze bf=new Freeze(bp);

        int initialIntPick = 0;
        float initialFloatPick = 0;
        String initialStringPick = "";
        boolean initialBooleanPick = false;

        for(int i=0; i<3;i++){



            int intValue=(int) iF.pick();
            float floatValue=(float) ff.pick();
            String stringValue=(String) sf.pick();
            boolean booleanValue=(boolean) bf.pick();


            if(i==0){
                initialIntPick=intValue;
                initialFloatPick=floatValue;
                initialStringPick=stringValue;
                initialBooleanPick=booleanValue;
            }
            System.out.println(intValue);
            System.out.println(floatValue);
            System.out.println(stringValue);
            System.out.println(booleanValue);

            for(int j=0;j<10;j++){
                Assertions.assertEquals(intValue,(int)iF.pick());
                Assertions.assertEquals(floatValue,(float)ff.pick());
                Assertions.assertEquals(stringValue,(String)sf.pick());
                Assertions.assertEquals(initialBooleanPick,(boolean)bf.pick());


            }
            //to test that the reset method of the freeze picker reset the playback.
            iF.reset();
            ff.reset();
            sf.reset();
            bf.reset();

            Assertions.assertEquals(initialIntPick,(int) iF.pick());
            Assertions.assertEquals(initialFloatPick,(float) ff.pick());
            Assertions.assertEquals(initialStringPick,(String) sf.pick());
            Assertions.assertEquals(initialBooleanPick,(boolean) bf.pick());


        }

    }


}
