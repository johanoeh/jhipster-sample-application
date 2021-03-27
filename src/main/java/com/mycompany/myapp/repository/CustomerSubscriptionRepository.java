package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CustomerSubscription;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerSubscriptionRepository extends JpaRepository<CustomerSubscription, Long> {
}
