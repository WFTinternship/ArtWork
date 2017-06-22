package am.aca.wftartproject.dao.integration;

import org.apache.log4j.Logger;

import javax.sql.DataSource;

/**
 * @author surik
 */
abstract class BaseDAOIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(BaseDAOIntegrationTest.class);

    DataSource dataSource = null;

    DataSource getDataSource() {
        return dataSource;
    }

    void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
