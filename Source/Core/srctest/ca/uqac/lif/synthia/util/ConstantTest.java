package ca.uqac.lif.synthia.util;
import ca.uqac.lif.synthia.string.RandomString;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.Constant;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;


@RunWith(Parameterized.class)
public class ConstantTest<T> {
    private T m_constantValue;

    public ConstantTest(T cv){
        m_constantValue=cv;
    }

    @Parameterized.Parameters
    public static Collection dataSet(){
        RandomInteger randomInteger=new RandomInteger(0,100);

        return Arrays.asList(new Object[][]{
                {new RandomInteger(0,100)},
                {new RandomFloat()},
                {new RandomString(randomInteger.pick())},
                {true},
                {false}
        });
    }

    @Test
    public void test(){
        Constant constant=new Constant(m_constantValue);
        for(int i=0;i<100;i++){
            Assertions.assertEquals(constant.pick(),m_constantValue);
        }
    }
}

