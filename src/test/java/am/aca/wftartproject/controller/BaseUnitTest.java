package am.aca.wftartproject.controller;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author surik
 */
public abstract class BaseUnitTest extends BaseTest {
    /**
     * Initialized mocks
     */
    @Before
    public void mockInit() {
        MockitoAnnotations.initMocks(this);
    }
}
