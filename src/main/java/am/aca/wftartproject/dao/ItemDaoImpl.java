package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 27-May-17.
 */
public class ItemDaoImpl implements ItemDao {

    Connection conn = null;

    public ItemDaoImpl() throws SQLException, ClassNotFoundException {
        conn = new DBConnection().getDBConnection();
    }

//    private static volatile ItemDaoImpl itemDaoImplInstance;
//    private ItemDaoImpl() throws SQLException, ClassNotFoundException {
//        conn = new DBConnection().getDBConnection();
//    }
//    public static ItemDaoImpl getItemDaoImplInstance() throws SQLException, ClassNotFoundException {
//        if(itemDaoImplInstance == null){
//            synchronized (ItemDaoImpl.class) {
//                if(itemDaoImplInstance == null) {
//                    itemDaoImplInstance = new ItemDaoImpl();
//                }
//            }
//        }
//        return itemDaoImplInstance;
//    }


    @Override
    public void addItem(int artistID, Item item) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO item(title, description, photo_url, price, artist_id, status, item_type) VALUE (?,?,?,?,?,?,?)");

            ps.setString(1,item.getTitle());
            ps.setString(2,item.getDescription());
            ps.setString(3,item.getPhotoURL());
            ps.setDouble(4,item.getPrice());
            ps.setInt(5,artistID);
            ps.setBoolean(6,item.isStatus());
            ps.setString(7,item.getItemType());

            if(ps.executeUpdate()>0){
                System.out.println("The item was successfully inserted");
            }else {
                System.out.println("There is problem with item inserting");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }



    }

    @Override
    public void updateItem(int id, double price) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE TABLE item SET price=? WHERE id=?");
            ps.setDouble(1,price);
            ps.setInt(2,id);
            if(ps.executeUpdate()>0){
                System.out.println("The item info was successfully updated");
            }else{
                System.out.println("There is a problem with item info updating");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM item WHERE id=?");
            ps.setInt(1,id);
            if(ps.executeUpdate()>0){
                System.out.println("The item was successfully deleted");
            }else{
                System.out.println("There is a problem with item deleting");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item findItem(int id) {
        Item item = new Item();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM item WHERE id = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                item.setId(rs.getInt(1));
                item.setTitle(rs.getString(2));
                item.setDescription(rs.getString(3));
                item.setPhotoURL(rs.getString(4));
                item.setPrice(rs.getDouble(5));
                item.setStatus(rs.getBoolean(7));
                item.setItemType(rs.getString(8));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


    @Override
    public List<Item> getRecentlyAddedItems(int limit){
        List<Item> itemList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT it.* FROM item it order by 1 desc limit ?"))
        {
            ps.setInt(1,limit);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Item tempItem = new Item(rs.getString(2),
                                        rs.getString(3),
                                        rs.getString(4),
                                        rs.getDouble(5),
                                        rs.getBoolean(7),
                                        rs.getString(8));
                tempItem.setId(rs.getInt(1));
                itemList.add(tempItem);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return itemList;
    }





    @Override
    public List<Item> getItemsByTitle(String title) {
        return null;
    }

    @Override
    public List<Item> getItemsByType(String itemType) {
        return null;
    }

}
