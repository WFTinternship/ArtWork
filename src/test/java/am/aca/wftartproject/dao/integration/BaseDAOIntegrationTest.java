package am.aca.wftartproject.dao.integration;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * @author surik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:BeanLocations.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
abstract class BaseDAOIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(BaseDAOIntegrationTest.class);

//    protected JdbcTemplate jdbcTemplate;
//
//    public JdbcTemplate getJdbcTemplate() {
//        return jdbcTemplate;
//    }
//
//    @Autowired
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//
//    DataSource dataSource;
//
//    DataSource getDataSource() {
//        return dataSource;
//    }
//
//    @Autowired
//    void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
}
