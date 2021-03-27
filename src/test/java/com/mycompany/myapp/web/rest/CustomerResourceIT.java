package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Customer;
import com.mycompany.myapp.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerResourceIT {

    private static final String DEFAULT_LEGAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TSCID = "AAAAAAAAAA";
    private static final String UPDATED_TSCID = "BBBBBBBBBB";

    private static final String DEFAULT_CORPORATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORPORATE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MARKET_COMPANY = false;
    private static final Boolean UPDATED_MARKET_COMPANY = true;

    private static final String DEFAULT_MARKET_AREA = "AAAAAAAAAA";
    private static final String UPDATED_MARKET_AREA = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .legalId(DEFAULT_LEGAL_ID)
            .tscid(DEFAULT_TSCID)
            .corporateName(DEFAULT_CORPORATE_NAME)
            .marketCompany(DEFAULT_MARKET_COMPANY)
            .marketArea(DEFAULT_MARKET_AREA);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .legalId(UPDATED_LEGAL_ID)
            .tscid(UPDATED_TSCID)
            .corporateName(UPDATED_CORPORATE_NAME)
            .marketCompany(UPDATED_MARKET_COMPANY)
            .marketArea(UPDATED_MARKET_AREA);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();
        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getLegalId()).isEqualTo(DEFAULT_LEGAL_ID);
        assertThat(testCustomer.getTscid()).isEqualTo(DEFAULT_TSCID);
        assertThat(testCustomer.getCorporateName()).isEqualTo(DEFAULT_CORPORATE_NAME);
        assertThat(testCustomer.isMarketCompany()).isEqualTo(DEFAULT_MARKET_COMPANY);
        assertThat(testCustomer.getMarketArea()).isEqualTo(DEFAULT_MARKET_AREA);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].legalId").value(hasItem(DEFAULT_LEGAL_ID)))
            .andExpect(jsonPath("$.[*].tscid").value(hasItem(DEFAULT_TSCID)))
            .andExpect(jsonPath("$.[*].corporateName").value(hasItem(DEFAULT_CORPORATE_NAME)))
            .andExpect(jsonPath("$.[*].marketCompany").value(hasItem(DEFAULT_MARKET_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].marketArea").value(hasItem(DEFAULT_MARKET_AREA)));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.legalId").value(DEFAULT_LEGAL_ID))
            .andExpect(jsonPath("$.tscid").value(DEFAULT_TSCID))
            .andExpect(jsonPath("$.corporateName").value(DEFAULT_CORPORATE_NAME))
            .andExpect(jsonPath("$.marketCompany").value(DEFAULT_MARKET_COMPANY.booleanValue()))
            .andExpect(jsonPath("$.marketArea").value(DEFAULT_MARKET_AREA));
    }
    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .legalId(UPDATED_LEGAL_ID)
            .tscid(UPDATED_TSCID)
            .corporateName(UPDATED_CORPORATE_NAME)
            .marketCompany(UPDATED_MARKET_COMPANY)
            .marketArea(UPDATED_MARKET_AREA);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getLegalId()).isEqualTo(UPDATED_LEGAL_ID);
        assertThat(testCustomer.getTscid()).isEqualTo(UPDATED_TSCID);
        assertThat(testCustomer.getCorporateName()).isEqualTo(UPDATED_CORPORATE_NAME);
        assertThat(testCustomer.isMarketCompany()).isEqualTo(UPDATED_MARKET_COMPANY);
        assertThat(testCustomer.getMarketArea()).isEqualTo(UPDATED_MARKET_AREA);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
