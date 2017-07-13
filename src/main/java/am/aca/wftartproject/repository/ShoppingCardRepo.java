package am.aca.wftartproject.repository;

import am.aca.wftartproject.entity.ShoppingCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ASUS on 27-May-17
 */

public interface ShoppingCardRepo extends JpaRepository<ShoppingCard,Long> {

    /**
     * Adds shoppingCard to the database.
     *
     * @param shoppingCard
     */
    ShoppingCard saveAndFlush(ShoppingCard shoppingCard);

    /**
     * Gets shoppingCard from database.
     *
     * @param id
     * @return
     */
    ShoppingCard findByAbstractUser_Id(Long id);


    /**
     * Deletes shoppingCard by id.
     *
     * @param shoppingCard
     */
    void delete(ShoppingCard shoppingCard);

}
