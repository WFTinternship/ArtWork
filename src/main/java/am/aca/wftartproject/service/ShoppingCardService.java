package am.aca.wftartproject.service;

import am.aca.wftartproject.entity.ShoppingCard;
import org.springframework.stereotype.Repository;


@Repository
public interface ShoppingCardService {

    /**
     * Adds shoppingCard to the database.
     *
     * @param shoppingCard *
     */
    void addShoppingCard(ShoppingCard shoppingCard);

    /**
     * Gets shoppingCard from database.
     *
     * @param id *
     * @return ShoppingCard
     */
    ShoppingCard getShoppingCard(Long id);

    /**
     * Updates shoppingCard in database.
     * @param shoppingCard *
     */
    void updateShoppingCard(ShoppingCard shoppingCard);

    /**
     * Debits balance for item buying
     *
     * @param buyerId *
     * @param itemPrice *
     */
    void debitBalanceForItemBuying(Long buyerId, Double itemPrice);

    /**
     * Deletes shoppingCard by id.
     *
     * @param shoppingCard *
     */
    void deleteShoppingCard(ShoppingCard shoppingCard);

    /*
     * Deletes shoppingCard
     *
     * @param buyerId *
     */
//    void deleteShoppingCardByBuyerId(Long buyerId);
}
