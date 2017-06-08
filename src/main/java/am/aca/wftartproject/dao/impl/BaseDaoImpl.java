package am.aca.wftartproject.dao.impl;

import javax.sql.DataSource;

/**
 * Created by ASUS on 08-Jun-17
 */
public abstract class BaseDaoImpl {

    protected DataSource dataSource = null;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}
