package am.aca.wftproject.dao;

import am.aca.wftproject.BaseTest;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

/**
 * @author surik
 */
public abstract class BaseDAOIntegrationTest extends BaseTest {

    private static final Logger LOGGER = Logger.getLogger(BaseDAOIntegrationTest.class);

    DataSource dataSource = null;

    DataSource getDataSource() {
        return dataSource;
    }

    void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
