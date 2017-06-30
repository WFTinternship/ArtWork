package am.aca.wftartproject.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ASUS on 08-Jun-17
 */
public abstract class BaseDaoImpl {

    private static final Logger LOGGER = Logger.getLogger(BaseDaoImpl.class);

    protected JdbcTemplate jdbcTemplate;


    void closeResources(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }

        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    void closeResources(Statement st, Connection conn) {
        closeResources(null, st, conn);
    }

    void closeResources(ResultSet rs, Statement st) {
        closeResources(rs, st, null);
    }

    void closeResources(Connection conn) {
        closeResources(null, conn);
    }

    void closeResources(Statement st) {
        closeResources(st, null);
    }


    Timestamp getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        return new Timestamp(cal.getTimeInMillis());
    }

    double getRandomBalance() {
        return ThreadLocalRandom.current().nextInt(1000, 100000 + 1);
    }
}
