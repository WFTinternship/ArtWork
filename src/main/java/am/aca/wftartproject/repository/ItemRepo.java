package am.aca.wftartproject.repository;

import am.aca.wftartproject.entity.Item;
import am.aca.wftartproject.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Lob;
import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public interface ItemRepo extends JpaRepository<Item,Long> {

    /**
     * Gets recently added items
     *
     * @param
     * @return
     */

    List<Item> findFirst10ByOrderByAdditionDate();


    /**
     * Gets all items with the following title.
     *
     * @param title
     * @return
     */
    List<Item> getAllByTitle(String title);


    /**
     * Gets all items with the following type.
     *
     * @param itemType
     * @return
     */
    List<Item> getAllByItemType(ItemType itemType);


    /**
     * Gets all items for the given price range.
     *
     * @param minPrice
     * @param maxPrice
     * @return
     */
    List<Item> getAllByPriceBetween(Double minPrice, Double maxPrice);


    /**
     * Gets artist items for the given limit.
     *
     * @param artistId
     * @return
     */
    List<Item> getAllByArtistId(Long artistId);

}
