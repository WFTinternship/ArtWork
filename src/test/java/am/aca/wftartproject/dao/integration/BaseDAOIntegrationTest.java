package am.aca.wftartproject.dao.integration;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author surik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:application-context.xml"})
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
