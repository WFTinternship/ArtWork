package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.entity.ItemType;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.Item;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;


@SuppressWarnings("ALL")
@Component
public class ItemDaoImpl extends BaseDaoImpl implements ItemDao {

    private static final Logger LOGGER = Logger.getLogger(ItemDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param item
     * @see ItemDao#addItem(Item)
     */
    @Override
    public void addItem(Item item) {

        try {
            item.setAdditionDate(getCurrentDateTime());
           entityManager.persist(item);
            entityManager.flush();
            LOGGER.info("Person saved successfully, Person Details=" + item);
        } catch (Exception e) {

            String error = "Failed to add Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

    }


    /**
     * @param id
     * @return
     * @see ItemDao#findItem(Long)
     */
    @Override
    public Item findItem(Long id) {
        
        try {

          Item  item = (Item) entityManager.find(Item.class, id);
            return item;
        } catch (Exception e) {
            String error = "Failed to get Item by id: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param limit
     * @return
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {


        List<Item> itemList = null;
        try {

            itemList = (List<Item>) entityManager.createQuery(
                    "SELECT c FROM Item c")
                    .getResultList();
            if(limit<itemList.size()-1){
                itemList = itemList.subList(itemList.size()-1 -limit,itemList.size());
            }

        } catch (Exception e) {

            String error = "Failed to get recently added items: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

        return itemList;

    }


    /**
     * @param title
     * @return
     * @see ItemDao#getItemsByTitle(String)
     */
    @Override
    public List<Item> getItemsByTitle(String title) {


        List<Item> itemList = null;
        try {

            itemList = (List<Item>) entityManager.createQuery(
                    "SELECT c FROM Item c where c.title = :title").setParameter("title", title).setMaxResults(100)
                    .getResultList();
        } catch (Exception e) {

            String error = "Failed to get Items by title: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

        return itemList;

    }


    /**
     * @param itemType
     * @return
     * @see ItemDao#getItemsByType(String)
     */
    @Override
    public List<Item> getItemsByType(ItemType itemType) {


        List<Item> itemList = null;
        try {

            itemList = (List<Item>) entityManager.createQuery(
                    "SELECT c FROM Item c where c.itemType = :itemType").setParameter("itemType", itemType).setMaxResults(100)
                    .getResultList();

        } catch (Exception e) {

            String error = "Failed to get Items by type: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

        return itemList;
    }


    /**
     * @param minPrice
     * @param maxPrice
     * @return
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     */
    @Override
    public List<Item> getItemsForGivenPriceRange(Double minPrice, Double maxPrice) {


        List<Item> itemList = null;
        try {

            itemList = (List<Item>) entityManager.createQuery(
                    "SELECT e FROM Item e WHERE e.price BETWEEN :minprice AND :maxprice").setParameter("minprice", minPrice).setParameter("maxprice",maxPrice).setMaxResults(100)
                    .getResultList();
        } catch (Exception e) {

            String error = "Failed to get Items by price: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

        return itemList;
    }

    /**
     * @param artistId
     * @return
     * @see ItemDao#getArtistItems(Long)
     */
    @Override
    public List<Item> getArtistItems(Long artistId) {
        List<Item> list = null;
        try{
            list =  (List<Item>) entityManager.createQuery(
                    "SELECT c FROM Item c WHERE c.artist_id = :artist_id")
                    .setParameter("artist_id", artistId)
                    .getResultList();
        }
        catch (Exception e) {

            String error = "Failed to get Items by type: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }



        return list;
    }


    /**
     * @param item
     * @see ItemDao#updateItem(Item)
     */
    @Override
    public Boolean updateItem(Item item) {

        Boolean result = false;
        try {
            entityManager.merge(item);
            entityManager.flush();
            result = true;
            LOGGER.info("Item updated successfully, Item Details=" + item);
        } catch (Exception e) {
            String error = "Failed to update Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        

        return result;
    }


    /**
     * @param id
     * @see ItemDao#deleteItem(Long)
     */
    @Override
    public Boolean deleteItem(Item item) {
        
        
        Boolean result = false;
        try {
            Item item1 = entityManager.find(Item.class,item.getId());
            entityManager.merge(item1);
            entityManager.remove(item1);
            result = true;
            LOGGER.info("Item deleted successfully");
        } catch (Exception e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        
        return result;
    }
}