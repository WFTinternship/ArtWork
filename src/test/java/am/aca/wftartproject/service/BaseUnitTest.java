package am.aca.wftartproject.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author surik
 */
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@EnableTransactionManagement
@Transactional
public abstract class BaseUnitTest extends BaseTest {
    @Before
    public void mockInit() {

    }
}
