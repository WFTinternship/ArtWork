package am.aca.wftartproject.dao.integration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * @author surik
 */
abstract class BaseDAOIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(BaseDAOIntegrationTest.class);


    DataSource dataSource;

    DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
