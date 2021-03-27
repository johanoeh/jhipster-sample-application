package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CustomerContactInfo;
import com.mycompany.myapp.repository.CustomerContactInfoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CustomerContactInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerContactInfoResource {

    private final Logger log = LoggerFactory.getLogger(CustomerContactInfoResource.class);

    private static final String ENTITY_NAME = "customerContactInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerContactInfoRepository customerContactInfoRepository;

    public CustomerContactInfoResource(CustomerContactInfoRepository customerContactInfoRepository) {
        this.customerContactInfoRepository = customerContactInfoRepository;
    }

    /**
     * {@code POST  /customer-contact-infos} : Create a new customerContactInfo.
     *
     * @param customerContactInfo the customerContactInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerContactInfo, or with status {@code 400 (Bad Request)} if the customerContactInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-contact-infos")
    public ResponseEntity<CustomerContactInfo> createCustomerContactInfo(@RequestBody CustomerContactInfo customerContactInfo) throws URISyntaxException {
        log.debug("REST request to save CustomerContactInfo : {}", customerContactInfo);
        if (customerContactInfo.getId() != null) {
            throw new BadRequestAlertException("A new customerContactInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerContactInfo result = customerContactInfoRepository.save(customerContactInfo);
        return ResponseEntity.created(new URI("/api/customer-contact-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-contact-infos} : Updates an existing customerContactInfo.
     *
     * @param customerContactInfo the customerContactInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerContactInfo,
     * or with status {@code 400 (Bad Request)} if the customerContactInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerContactInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-contact-infos")
    public ResponseEntity<CustomerContactInfo> updateCustomerContactInfo(@RequestBody CustomerContactInfo customerContactInfo) throws URISyntaxException {
        log.debug("REST request to update CustomerContactInfo : {}", customerContactInfo);
        if (customerContactInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerContactInfo result = customerContactInfoRepository.save(customerContactInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerContactInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-contact-infos} : get all the customerContactInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerContactInfos in body.
     */
    @GetMapping("/customer-contact-infos")
    public List<CustomerContactInfo> getAllCustomerContactInfos() {
        log.debug("REST request to get all CustomerContactInfos");
        return customerContactInfoRepository.findAll();
    }

    /**
     * {@code GET  /customer-contact-infos/:id} : get the "id" customerContactInfo.
     *
     * @param id the id of the customerContactInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerContactInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-contact-infos/{id}")
    public ResponseEntity<CustomerContactInfo> getCustomerContactInfo(@PathVariable Long id) {
        log.debug("REST request to get CustomerContactInfo : {}", id);
        Optional<CustomerContactInfo> customerContactInfo = customerContactInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerContactInfo);
    }

    /**
     * {@code DELETE  /customer-contact-infos/:id} : delete the "id" customerContactInfo.
     *
     * @param id the id of the customerContactInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-contact-infos/{id}")
    public ResponseEntity<Void> deleteCustomerContactInfo(@PathVariable Long id) {
        log.debug("REST request to delete CustomerContactInfo : {}", id);
        customerContactInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
