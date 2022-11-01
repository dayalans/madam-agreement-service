package com.tele.orderengine;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tele.orderengine.entity.OrderMap;


public interface OrderEngineMapRepository extends JpaRepository<OrderMap,Long>{

}
