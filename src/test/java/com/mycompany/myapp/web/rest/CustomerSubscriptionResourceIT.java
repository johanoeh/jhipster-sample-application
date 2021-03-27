package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CustomerSubscription;
import com.mycompany.myapp.repository.CustomerSubscriptionRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerSubscriptionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerSubscriptionResourceIT {

    private static final String DEFAULT_EMN_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_AGREEMENT = "AAAAAAAAAA";
    private static final String UPDATED_AGREEMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CHANGE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHANGE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_QUARTERLY_FEE = "AAAAAAAAAA";
    private static final String UPDATED_QUARTERLY_FEE = "BBBBBBBBBB";

    private static final String DEFAULT_ONE_TIME_FEE = "AAAAAAAAAA";
    private static final String UPDATED_ONE_TIME_FEE = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_TEXT = "BBBBBBBBBB";

    @Autowired
    private CustomerSubscriptionRepository customerSubscriptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerSubscriptionMockMvc;

    private CustomerSubscription customerSubscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerSubscription createEntity(EntityManager em) {
        CustomerSubscription customerSubscription = new CustomerSubscription()
            .emnId(DEFAULT_EMN_ID)
            .type(DEFAULT_TYPE)
            .agreement(DEFAULT_AGREEMENT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .changeDate(DEFAULT_CHANGE_DATE)
            .quarterlyFee(DEFAULT_QUARTERLY_FEE)
            .oneTimeFee(DEFAULT_ONE_TIME_FEE)
            .invoiceText(DEFAULT_INVOICE_TEXT);
        return customerSubscription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerSubscription createUpdatedEntity(EntityManager em) {
        CustomerSubscription customerSubscription = new CustomerSubscription()
            .emnId(UPDATED_EMN_ID)
            .type(UPDATED_TYPE)
            .agreement(UPDATED_AGREEMENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .changeDate(UPDATED_CHANGE_DATE)
            .quarterlyFee(UPDATED_QUARTERLY_FEE)
            .oneTimeFee(UPDATED_ONE_TIME_FEE)
            .invoiceText(UPDATED_INVOICE_TEXT);
        return customerSubscription;
    }

    @BeforeEach
    public void initTest() {
        customerSubscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerSubscription() throws Exception {
        int databaseSizeBeforeCreate = customerSubscriptionRepository.findAll().size();
        // Create the CustomerSubscription
        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscription)))
            .andExpect(status().isCreated());

        // Validate the CustomerSubscription in the database
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerSubscription testCustomerSubscription = customerSubscriptionList.get(customerSubscriptionList.size() - 1);
        assertThat(testCustomerSubscription.getEmnId()).isEqualTo(DEFAULT_EMN_ID);
        assertThat(testCustomerSubscription.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustomerSubscription.getAgreement()).isEqualTo(DEFAULT_AGREEMENT);
        assertThat(testCustomerSubscription.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCustomerSubscription.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testCustomerSubscription.getChangeDate()).isEqualTo(DEFAULT_CHANGE_DATE);
        assertThat(testCustomerSubscription.getQuarterlyFee()).isEqualTo(DEFAULT_QUARTERLY_FEE);
        assertThat(testCustomerSubscription.getOneTimeFee()).isEqualTo(DEFAULT_ONE_TIME_FEE);
        assertThat(testCustomerSubscription.getInvoiceText()).isEqualTo(DEFAULT_INVOICE_TEXT);
    }

    @Test
    @Transactional
    public void createCustomerSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerSubscriptionRepository.findAll().size();

        // Create the CustomerSubscription with an existing ID
        customerSubscription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscription)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerSubscription in the database
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerSubscriptions() throws Exception {
        // Initialize the database
        customerSubscriptionRepository.saveAndFlush(customerSubscription);

        // Get all the customerSubscriptionList
        restCustomerSubscriptionMockMvc.perform(get("/api/customer-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].emnId").value(hasItem(DEFAULT_EMN_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].agreement").value(hasItem(DEFAULT_AGREEMENT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].changeDate").value(hasItem(DEFAULT_CHANGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].quarterlyFee").value(hasItem(DEFAULT_QUARTERLY_FEE)))
            .andExpect(jsonPath("$.[*].oneTimeFee").value(hasItem(DEFAULT_ONE_TIME_FEE)))
            .andExpect(jsonPath("$.[*].invoiceText").value(hasItem(DEFAULT_INVOICE_TEXT)));
    }
    
    @Test
    @Transactional
    public void getCustomerSubscription() throws Exception {
        // Initialize the database
        customerSubscriptionRepository.saveAndFlush(customerSubscription);

        // Get the customerSubscription
        restCustomerSubscriptionMockMvc.perform(get("/api/customer-subscriptions/{id}", customerSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerSubscription.getId().intValue()))
            .andExpect(jsonPath("$.emnId").value(DEFAULT_EMN_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.agreement").value(DEFAULT_AGREEMENT))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.changeDate").value(DEFAULT_CHANGE_DATE.toString()))
            .andExpect(jsonPath("$.quarterlyFee").value(DEFAULT_QUARTERLY_FEE))
            .andExpect(jsonPath("$.oneTimeFee").value(DEFAULT_ONE_TIME_FEE))
            .andExpect(jsonPath("$.invoiceText").value(DEFAULT_INVOICE_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerSubscription() throws Exception {
        // Get the customerSubscription
        restCustomerSubscriptionMockMvc.perform(get("/api/customer-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerSubscription() throws Exception {
        // Initialize the database
        customerSubscriptionRepository.saveAndFlush(customerSubscription);

        int databaseSizeBeforeUpdate = customerSubscriptionRepository.findAll().size();

        // Update the customerSubscription
        CustomerSubscription updatedCustomerSubscription = customerSubscriptionRepository.findById(customerSubscription.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerSubscription are not directly saved in db
        em.detach(updatedCustomerSubscription);
        updatedCustomerSubscription
            .emnId(UPDATED_EMN_ID)
            .type(UPDATED_TYPE)
            .agreement(UPDATED_AGREEMENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .changeDate(UPDATED_CHANGE_DATE)
            .quarterlyFee(UPDATED_QUARTERLY_FEE)
            .oneTimeFee(UPDATED_ONE_TIME_FEE)
            .invoiceText(UPDATED_INVOICE_TEXT);

        restCustomerSubscriptionMockMvc.perform(put("/api/customer-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerSubscription)))
            .andExpect(status().isOk());

        // Validate the CustomerSubscription in the database
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        CustomerSubscription testCustomerSubscription = customerSubscriptionList.get(customerSubscriptionList.size() - 1);
        assertThat(testCustomerSubscription.getEmnId()).isEqualTo(UPDATED_EMN_ID);
        assertThat(testCustomerSubscription.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustomerSubscription.getAgreement()).isEqualTo(UPDATED_AGREEMENT);
        assertThat(testCustomerSubscription.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCustomerSubscription.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testCustomerSubscription.getChangeDate()).isEqualTo(UPDATED_CHANGE_DATE);
        assertThat(testCustomerSubscription.getQuarterlyFee()).isEqualTo(UPDATED_QUARTERLY_FEE);
        assertThat(testCustomerSubscription.getOneTimeFee()).isEqualTo(UPDATED_ONE_TIME_FEE);
        assertThat(testCustomerSubscription.getInvoiceText()).isEqualTo(UPDATED_INVOICE_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerSubscription() throws Exception {
        int databaseSizeBeforeUpdate = customerSubscriptionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerSubscriptionMockMvc.perform(put("/api/customer-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscription)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerSubscription in the database
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerSubscription() throws Exception {
        // Initialize the database
        customerSubscriptionRepository.saveAndFlush(customerSubscription);

        int databaseSizeBeforeDelete = customerSubscriptionRepository.findAll().size();

        // Delete the customerSubscription
        restCustomerSubscriptionMockMvc.perform(delete("/api/customer-subscriptions/{id}", customerSubscription.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
