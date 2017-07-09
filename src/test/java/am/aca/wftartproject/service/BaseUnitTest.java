package am.aca.wftartproject.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author surik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring-config.xml"})
public abstract class BaseUnitTest extends BaseTest {
    @Before
    public void mockInit() {
        MockitoAnnotations.initMocks(this);
    }
}
