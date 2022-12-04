package rest.autoservice.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.autoservice.model.Duty;

@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Duty d SET d.paymentStatus = :newStatus WHERE d.id = :id")
    void updateDutyStatusById(Long id, Duty.PaymentStatus newStatus);

    List<Duty> getDutyByMasterIdAndOrOrderId(Long masterId, Long orderId);
}
