package ru.innopolis.uni.course2;

/**
 * Created by olymp on 11.11.2016.
 */
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import ru.innopolis.streams.StreamWriter;

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by d.sapaev on 11.10.2016.
 */

public class HelloWorldTest {

    private static Logger log = LoggerFactory.getLogger(HelloWorldTest.class);

    private HelloWorld helloWorld;
    //private Mockery context;

    @BeforeClass
    public static void beforeTest(){
        log.info("This is @BeforeClass method");
    }

    @Before
    public void before(){
        log.info("This is @Before method");
        this.helloWorld = new HelloWorld();
        //this.context = new JUnit4Mockery();
    }

    @Ignore
    @Test
    public void sumTest() {
        int sum = this.helloWorld.summ(2,2);
        assertTrue("sum is incorrect",sum == 4);
//        assertTrue("sum is incorrect",sum == 44);
    }

    @Ignore
    @Test(expected = Exception.class)
    public void testDoSome() throws Exception {
        log.info("This is testHandle method");
        this.helloWorld.doSome(4);
        /*final StreamWriter streamWriter= context.mock(StreamWriter.class);

        context.checking(new Expectations() {{
            oneOf(streamWriter).write("Tatarstan");
            will(returnValue(new Long(16)));
        }});

        helloWorld.setStreamWriter(streamWriter);
        assertEquals(new Long(16), helloWorld.handle("Tatarstan"));*/

        StreamReader streamReader = mock(StreamReader.class);
        when(streamReader.read(5)).thenReturn("Object");
        this.mock.setStreamReader(streamReader);

    }

    @After
    public void after(){
        log.info("This is @After method");
    }

    @AfterClass
    public static void afterTest(){
        log.info("This is @AfterClass method");
    }
}