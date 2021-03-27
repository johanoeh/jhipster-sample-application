package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.BillingAccount;
import com.mycompany.myapp.repository.BillingAccountRepository;

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
 * Integration tests for the {@link BillingAccountResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BillingAccountResourceIT {

    private static final String DEFAULT_INVOICE_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_BILLING_PROFILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_BILLING_PROFILE_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXEMPT_FROM_VAT = false;
    private static final Boolean UPDATED_EXEMPT_FROM_VAT = true;

    private static final String DEFAULT_INVOICE_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_E_MAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_E_MAIL_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private BillingAccountRepository billingAccountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillingAccountMockMvc;

    private BillingAccount billingAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingAccount createEntity(EntityManager em) {
        BillingAccount billingAccount = new BillingAccount()
            .invoiceReference(DEFAULT_INVOICE_REFERENCE)
            .accountId(DEFAULT_ACCOUNT_ID)
            .primaryBillingProfileId(DEFAULT_PRIMARY_BILLING_PROFILE_ID)
            .exemptFromVat(DEFAULT_EXEMPT_FROM_VAT)
            .invoiceContactPhone(DEFAULT_INVOICE_CONTACT_PHONE)
            .invoiceMethod(DEFAULT_INVOICE_METHOD)
            .invoiceEMailAddress(DEFAULT_INVOICE_E_MAIL_ADDRESS);
        return billingAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingAccount createUpdatedEntity(EntityManager em) {
        BillingAccount billingAccount = new BillingAccount()
            .invoiceReference(UPDATED_INVOICE_REFERENCE)
            .accountId(UPDATED_ACCOUNT_ID)
            .primaryBillingProfileId(UPDATED_PRIMARY_BILLING_PROFILE_ID)
            .exemptFromVat(UPDATED_EXEMPT_FROM_VAT)
            .invoiceContactPhone(UPDATED_INVOICE_CONTACT_PHONE)
            .invoiceMethod(UPDATED_INVOICE_METHOD)
            .invoiceEMailAddress(UPDATED_INVOICE_E_MAIL_ADDRESS);
        return billingAccount;
    }

    @BeforeEach
    public void initTest() {
        billingAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingAccount() throws Exception {
        int databaseSizeBeforeCreate = billingAccountRepository.findAll().size();
        // Create the BillingAccount
        restBillingAccountMockMvc.perform(post("/api/billing-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingAccount)))
            .andExpect(status().isCreated());

        // Validate the BillingAccount in the database
        List<BillingAccount> billingAccountList = billingAccountRepository.findAll();
        assertThat(billingAccountList).hasSize(databaseSizeBeforeCreate + 1);
        BillingAccount testBillingAccount = billingAccountList.get(billingAccountList.size() - 1);
        assertThat(testBillingAccount.getInvoiceReference()).isEqualTo(DEFAULT_INVOICE_REFERENCE);
        assertThat(testBillingAccount.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testBillingAccount.getPrimaryBillingProfileId()).isEqualTo(DEFAULT_PRIMARY_BILLING_PROFILE_ID);
        assertThat(testBillingAccount.isExemptFromVat()).isEqualTo(DEFAULT_EXEMPT_FROM_VAT);
        assertThat(testBillingAccount.getInvoiceContactPhone()).isEqualTo(DEFAULT_INVOICE_CONTACT_PHONE);
        assertThat(testBillingAccount.getInvoiceMethod()).isEqualTo(DEFAULT_INVOICE_METHOD);
        assertThat(testBillingAccount.getInvoiceEMailAddress()).isEqualTo(DEFAULT_INVOICE_E_MAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void createBillingAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingAccountRepository.findAll().size();

        // Create the BillingAccount with an existing ID
        billingAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingAccountMockMvc.perform(post("/api/billing-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BillingAccount in the database
        List<BillingAccount> billingAccountList = billingAccountRepository.findAll();
        assertThat(billingAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBillingAccounts() throws Exception {
        // Initialize the database
        billingAccountRepository.saveAndFlush(billingAccount);

        // Get all the billingAccountList
        restBillingAccountMockMvc.perform(get("/api/billing-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceReference").value(hasItem(DEFAULT_INVOICE_REFERENCE)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].primaryBillingProfileId").value(hasItem(DEFAULT_PRIMARY_BILLING_PROFILE_ID)))
            .andExpect(jsonPath("$.[*].exemptFromVat").value(hasItem(DEFAULT_EXEMPT_FROM_VAT.booleanValue())))
            .andExpect(jsonPath("$.[*].invoiceContactPhone").value(hasItem(DEFAULT_INVOICE_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].invoiceMethod").value(hasItem(DEFAULT_INVOICE_METHOD)))
            .andExpect(jsonPath("$.[*].invoiceEMailAddress").value(hasItem(DEFAULT_INVOICE_E_MAIL_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getBillingAccount() throws Exception {
        // Initialize the database
        billingAccountRepository.saveAndFlush(billingAccount);

        // Get the billingAccount
        restBillingAccountMockMvc.perform(get("/api/billing-accounts/{id}", billingAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billingAccount.getId().intValue()))
            .andExpect(jsonPath("$.invoiceReference").value(DEFAULT_INVOICE_REFERENCE))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.primaryBillingProfileId").value(DEFAULT_PRIMARY_BILLING_PROFILE_ID))
            .andExpect(jsonPath("$.exemptFromVat").value(DEFAULT_EXEMPT_FROM_VAT.booleanValue()))
            .andExpect(jsonPath("$.invoiceContactPhone").value(DEFAULT_INVOICE_CONTACT_PHONE))
            .andExpect(jsonPath("$.invoiceMethod").value(DEFAULT_INVOICE_METHOD))
            .andExpect(jsonPath("$.invoiceEMailAddress").value(DEFAULT_INVOICE_E_MAIL_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingBillingAccount() throws Exception {
        // Get the billingAccount
        restBillingAccountMockMvc.perform(get("/api/billing-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingAccount() throws Exception {
        // Initialize the database
        billingAccountRepository.saveAndFlush(billingAccount);

        int databaseSizeBeforeUpdate = billingAccountRepository.findAll().size();

        // Update the billingAccount
        BillingAccount updatedBillingAccount = billingAccountRepository.findById(billingAccount.getId()).get();
        // Disconnect from session so that the updates on updatedBillingAccount are not directly saved in db
        em.detach(updatedBillingAccount);
        updatedBillingAccount
            .invoiceReference(UPDATED_INVOICE_REFERENCE)
            .accountId(UPDATED_ACCOUNT_ID)
            .primaryBillingProfileId(UPDATED_PRIMARY_BILLING_PROFILE_ID)
            .exemptFromVat(UPDATED_EXEMPT_FROM_VAT)
            .invoiceContactPhone(UPDATED_INVOICE_CONTACT_PHONE)
            .invoiceMethod(UPDATED_INVOICE_METHOD)
            .invoiceEMailAddress(UPDATED_INVOICE_E_MAIL_ADDRESS);

        restBillingAccountMockMvc.perform(put("/api/billing-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillingAccount)))
            .andExpect(status().isOk());

        // Validate the BillingAccount in the database
        List<BillingAccount> billingAccountList = billingAccountRepository.findAll();
        assertThat(billingAccountList).hasSize(databaseSizeBeforeUpdate);
        BillingAccount testBillingAccount = billingAccountList.get(billingAccountList.size() - 1);
        assertThat(testBillingAccount.getInvoiceReference()).isEqualTo(UPDATED_INVOICE_REFERENCE);
        assertThat(testBillingAccount.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testBillingAccount.getPrimaryBillingProfileId()).isEqualTo(UPDATED_PRIMARY_BILLING_PROFILE_ID);
        assertThat(testBillingAccount.isExemptFromVat()).isEqualTo(UPDATED_EXEMPT_FROM_VAT);
        assertThat(testBillingAccount.getInvoiceContactPhone()).isEqualTo(UPDATED_INVOICE_CONTACT_PHONE);
        assertThat(testBillingAccount.getInvoiceMethod()).isEqualTo(UPDATED_INVOICE_METHOD);
        assertThat(testBillingAccount.getInvoiceEMailAddress()).isEqualTo(UPDATED_INVOICE_E_MAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingAccount() throws Exception {
        int databaseSizeBeforeUpdate = billingAccountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingAccountMockMvc.perform(put("/api/billing-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BillingAccount in the database
        List<BillingAccount> billingAccountList = billingAccountRepository.findAll();
        assertThat(billingAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillingAccount() throws Exception {
        // Initialize the database
        billingAccountRepository.saveAndFlush(billingAccount);

        int databaseSizeBeforeDelete = billingAccountRepository.findAll().size();

        // Delete the billingAccount
        restBillingAccountMockMvc.perform(delete("/api/billing-accounts/{id}", billingAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillingAccount> billingAccountList = billingAccountRepository.findAll();
        assertThat(billingAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
