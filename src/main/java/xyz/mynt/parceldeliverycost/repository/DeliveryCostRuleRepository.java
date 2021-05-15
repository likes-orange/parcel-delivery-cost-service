package xyz.mynt.parceldeliverycost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.mynt.parceldeliverycost.entity.DeliveryCostRule;

import java.util.List;

public interface DeliveryCostRuleRepository extends JpaRepository<DeliveryCostRule, Long> {

    List<DeliveryCostRule> findAllByActiveTrueOrderBySortOrderAsc();

}