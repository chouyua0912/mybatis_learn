package z.learn.spring;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * UserService Tester.
 *
 * @author chouyua
 * @version 1.0
 * @since <pre>Jan 27, 2019</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:beans.xml"})
public class UserServiceTest {

    @Before
    public void before() throws Exception {
    }

    /**
     * Method: getUser(int id)
     */
    @Test
    public void testGetUser() throws Exception {
        example.getUser(1);
    }


    @Resource
    private UserService example;
} 
