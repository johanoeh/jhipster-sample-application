package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BillingAccount;
import com.mycompany.myapp.repository.BillingAccountRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.BillingAccount}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BillingAccountResource {

    private final Logger log = LoggerFactory.getLogger(BillingAccountResource.class);

    private static final String ENTITY_NAME = "billingAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillingAccountRepository billingAccountRepository;

    public BillingAccountResource(BillingAccountRepository billingAccountRepository) {
        this.billingAccountRepository = billingAccountRepository;
    }

    /**
     * {@code POST  /billing-accounts} : Create a new billingAccount.
     *
     * @param billingAccount the billingAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billingAccount, or with status {@code 400 (Bad Request)} if the billingAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/billing-accounts")
    public ResponseEntity<BillingAccount> createBillingAccount(@RequestBody BillingAccount billingAccount) throws URISyntaxException {
        log.debug("REST request to save BillingAccount : {}", billingAccount);
        if (billingAccount.getId() != null) {
            throw new BadRequestAlertException("A new billingAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingAccount result = billingAccountRepository.save(billingAccount);
        return ResponseEntity.created(new URI("/api/billing-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /billing-accounts} : Updates an existing billingAccount.
     *
     * @param billingAccount the billingAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billingAccount,
     * or with status {@code 400 (Bad Request)} if the billingAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billingAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/billing-accounts")
    public ResponseEntity<BillingAccount> updateBillingAccount(@RequestBody BillingAccount billingAccount) throws URISyntaxException {
        log.debug("REST request to update BillingAccount : {}", billingAccount);
        if (billingAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingAccount result = billingAccountRepository.save(billingAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billingAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /billing-accounts} : get all the billingAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billingAccounts in body.
     */
    @GetMapping("/billing-accounts")
    public List<BillingAccount> getAllBillingAccounts() {
        log.debug("REST request to get all BillingAccounts");
        return billingAccountRepository.findAll();
    }

    /**
     * {@code GET  /billing-accounts/:id} : get the "id" billingAccount.
     *
     * @param id the id of the billingAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billingAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/billing-accounts/{id}")
    public ResponseEntity<BillingAccount> getBillingAccount(@PathVariable Long id) {
        log.debug("REST request to get BillingAccount : {}", id);
        Optional<BillingAccount> billingAccount = billingAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(billingAccount);
    }

    /**
     * {@code DELETE  /billing-accounts/:id} : delete the "id" billingAccount.
     *
     * @param id the id of the billingAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/billing-accounts/{id}")
    public ResponseEntity<Void> deleteBillingAccount(@PathVariable Long id) {
        log.debug("REST request to delete BillingAccount : {}", id);
        billingAccountRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
