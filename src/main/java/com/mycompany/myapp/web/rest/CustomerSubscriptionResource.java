package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CustomerSubscription;
import com.mycompany.myapp.repository.CustomerSubscriptionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CustomerSubscription}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(CustomerSubscriptionResource.class);

    private static final String ENTITY_NAME = "customerSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerSubscriptionRepository customerSubscriptionRepository;

    public CustomerSubscriptionResource(CustomerSubscriptionRepository customerSubscriptionRepository) {
        this.customerSubscriptionRepository = customerSubscriptionRepository;
    }

    /**
     * {@code POST  /customer-subscriptions} : Create a new customerSubscription.
     *
     * @param customerSubscription the customerSubscription to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerSubscription, or with status {@code 400 (Bad Request)} if the customerSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-subscriptions")
    public ResponseEntity<CustomerSubscription> createCustomerSubscription(@RequestBody CustomerSubscription customerSubscription) throws URISyntaxException {
        log.debug("REST request to save CustomerSubscription : {}", customerSubscription);
        if (customerSubscription.getId() != null) {
            throw new BadRequestAlertException("A new customerSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerSubscription result = customerSubscriptionRepository.save(customerSubscription);
        return ResponseEntity.created(new URI("/api/customer-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-subscriptions} : Updates an existing customerSubscription.
     *
     * @param customerSubscription the customerSubscription to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerSubscription,
     * or with status {@code 400 (Bad Request)} if the customerSubscription is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerSubscription couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-subscriptions")
    public ResponseEntity<CustomerSubscription> updateCustomerSubscription(@RequestBody CustomerSubscription customerSubscription) throws URISyntaxException {
        log.debug("REST request to update CustomerSubscription : {}", customerSubscription);
        if (customerSubscription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerSubscription result = customerSubscriptionRepository.save(customerSubscription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerSubscription.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-subscriptions} : get all the customerSubscriptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerSubscriptions in body.
     */
    @GetMapping("/customer-subscriptions")
    public List<CustomerSubscription> getAllCustomerSubscriptions() {
        log.debug("REST request to get all CustomerSubscriptions");
        return customerSubscriptionRepository.findAll();
    }

    /**
     * {@code GET  /customer-subscriptions/:id} : get the "id" customerSubscription.
     *
     * @param id the id of the customerSubscription to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerSubscription, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-subscriptions/{id}")
    public ResponseEntity<CustomerSubscription> getCustomerSubscription(@PathVariable Long id) {
        log.debug("REST request to get CustomerSubscription : {}", id);
        Optional<CustomerSubscription> customerSubscription = customerSubscriptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerSubscription);
    }

    /**
     * {@code DELETE  /customer-subscriptions/:id} : delete the "id" customerSubscription.
     *
     * @param id the id of the customerSubscription to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-subscriptions/{id}")
    public ResponseEntity<Void> deleteCustomerSubscription(@PathVariable Long id) {
        log.debug("REST request to delete CustomerSubscription : {}", id);
        customerSubscriptionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
