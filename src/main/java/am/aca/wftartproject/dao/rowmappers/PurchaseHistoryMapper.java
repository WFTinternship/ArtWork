package am.aca.wftartproject.dao.rowmappers;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Armen on 6/16/2017
 */

public class PurchaseHistoryMapper implements RowMapper<PurchaseHistory>{
    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
    ItemService itemService = ctx.getBean("itemService", ItemServiceImpl.class);

    @Override
    public PurchaseHistory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PurchaseHistory purchasehistory = new PurchaseHistory();
        purchasehistory.setItemId(resultSet.getLong("item_id"))
                .setUserId(resultSet.getLong("user_id"))
                .setPurchaseDate(resultSet.getDate("purchase_date"))
              .setItem(itemService.findItem(resultSet.getLong("item_id")));
        return purchasehistory;
    }
}
