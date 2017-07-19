package am.aca.wftartproject.repository;

import am.aca.wftartproject.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
@Repository
public interface PurchaseHistoryRepo extends JpaRepository<PurchaseHistory,Long> {

    /**
     * Gets all purchase items by userId
     *
     * @param userId
     * @return
     */
    List<PurchaseHistory> getAllByAbsUser_Id(Long userId);

    /**
     * find purchase by item id
     *
     * @param id
     * @return
     */
    PurchaseHistory findByPurchaseItem_Id (Long id);

}