package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BalanceWheel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BalanceWheel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalanceWheelRepository extends JpaRepository<BalanceWheel, Long> {

}
