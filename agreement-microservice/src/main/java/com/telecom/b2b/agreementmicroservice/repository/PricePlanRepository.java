package com.telecom.b2b.agreementmicroservice.repository;

import com.telecom.b2b.agreementmicroservice.entity.PricePlans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricePlanRepository extends JpaRepository<PricePlans,Long>  {
}
