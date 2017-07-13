//package am.aca.wftartproject.repository.rowmappers;
//
//import am.aca.wftartproject.entity.PurchaseHistory;
//import am.aca.wftartproject.service.ItemService;
//import am.aca.wftartproject.service.impl.ItemServiceImpl;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * Created by Armen on 6/16/2017
// */
//
//public class PurchaseHistoryMapper implements RowMapper<PurchaseHistory>{
//    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
//    ItemService itemService = ctx.getBean("itemService", ItemServiceImpl.class);
//
//    @Override
//    public PurchaseHistory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//        PurchaseHistory purchasehistory = new PurchaseHistory();
//        purchasehistory.setItemId(resultSet.getLong("item_id"))
//                .setUserId(resultSet.getLong("user_id"))
//                .setPurchaseDate(resultSet.getDate("purchase_date"))
//              .setItem(itemService.findItem(resultSet.getLong("item_id")));
//        return purchasehistory;
//    }
//}
