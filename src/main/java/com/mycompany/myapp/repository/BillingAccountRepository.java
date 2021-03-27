package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BillingAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BillingAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingAccountRepository extends JpaRepository<BillingAccount, Long> {
}
