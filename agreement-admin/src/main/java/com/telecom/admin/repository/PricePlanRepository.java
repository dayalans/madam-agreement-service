package com.telecom.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telecom.admin.entity.PricePlan;


public interface PricePlanRepository extends JpaRepository<PricePlan,Long> {
	 @Query(value = "SELECT * FROM PRICE_PLAN WHERE PRICE_PLAN_END_DATE >CURRENT_DATE AND PRICEPLAN_STATUS='ACTIVE'", nativeQuery = true)
	 List<PricePlan> findAllActivePricePlans();

}
