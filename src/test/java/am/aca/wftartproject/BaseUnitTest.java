package am.aca.wftartproject;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author surik
 */
public abstract class BaseUnitTest extends BaseTest {
    @Before
    public void mockInit() {
        MockitoAnnotations.initMocks(this);
    }
}
