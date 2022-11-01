package com.telecom.b2b.ucinstance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telecom.b2b.ucinstance.entity.UCUser;

public interface UCUserRepository extends JpaRepository<UCUser,Long> {

}
