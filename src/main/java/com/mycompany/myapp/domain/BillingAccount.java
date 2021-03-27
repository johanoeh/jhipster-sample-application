package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A BillingAccount.
 */
@Entity
@Table(name = "billing_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_reference")
    private String invoiceReference;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "primary_billing_profile_id")
    private String primaryBillingProfileId;

    @Column(name = "exempt_from_vat")
    private Boolean exemptFromVat;

    @Column(name = "invoice_contact_phone")
    private String invoiceContactPhone;

    @Column(name = "invoice_method")
    private String invoiceMethod;

    @Column(name = "invoice_e_mail_address")
    private String invoiceEMailAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private CustomerSubscription customerSubscription;

    @OneToOne
    @JoinColumn(unique = true)
    private Address invoiceAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public BillingAccount invoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
        return this;
    }

    public void setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
    }

    public String getAccountId() {
        return accountId;
    }

    public BillingAccount accountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPrimaryBillingProfileId() {
        return primaryBillingProfileId;
    }

    public BillingAccount primaryBillingProfileId(String primaryBillingProfileId) {
        this.primaryBillingProfileId = primaryBillingProfileId;
        return this;
    }

    public void setPrimaryBillingProfileId(String primaryBillingProfileId) {
        this.primaryBillingProfileId = primaryBillingProfileId;
    }

    public Boolean isExemptFromVat() {
        return exemptFromVat;
    }

    public BillingAccount exemptFromVat(Boolean exemptFromVat) {
        this.exemptFromVat = exemptFromVat;
        return this;
    }

    public void setExemptFromVat(Boolean exemptFromVat) {
        this.exemptFromVat = exemptFromVat;
    }

    public String getInvoiceContactPhone() {
        return invoiceContactPhone;
    }

    public BillingAccount invoiceContactPhone(String invoiceContactPhone) {
        this.invoiceContactPhone = invoiceContactPhone;
        return this;
    }

    public void setInvoiceContactPhone(String invoiceContactPhone) {
        this.invoiceContactPhone = invoiceContactPhone;
    }

    public String getInvoiceMethod() {
        return invoiceMethod;
    }

    public BillingAccount invoiceMethod(String invoiceMethod) {
        this.invoiceMethod = invoiceMethod;
        return this;
    }

    public void setInvoiceMethod(String invoiceMethod) {
        this.invoiceMethod = invoiceMethod;
    }

    public String getInvoiceEMailAddress() {
        return invoiceEMailAddress;
    }

    public BillingAccount invoiceEMailAddress(String invoiceEMailAddress) {
        this.invoiceEMailAddress = invoiceEMailAddress;
        return this;
    }

    public void setInvoiceEMailAddress(String invoiceEMailAddress) {
        this.invoiceEMailAddress = invoiceEMailAddress;
    }

    public CustomerSubscription getCustomerSubscription() {
        return customerSubscription;
    }

    public BillingAccount customerSubscription(CustomerSubscription customerSubscription) {
        this.customerSubscription = customerSubscription;
        return this;
    }

    public void setCustomerSubscription(CustomerSubscription customerSubscription) {
        this.customerSubscription = customerSubscription;
    }

    public Address getInvoiceAddress() {
        return invoiceAddress;
    }

    public BillingAccount invoiceAddress(Address address) {
        this.invoiceAddress = address;
        return this;
    }

    public void setInvoiceAddress(Address address) {
        this.invoiceAddress = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillingAccount)) {
            return false;
        }
        return id != null && id.equals(((BillingAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillingAccount{" +
            "id=" + getId() +
            ", invoiceReference='" + getInvoiceReference() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", primaryBillingProfileId='" + getPrimaryBillingProfileId() + "'" +
            ", exemptFromVat='" + isExemptFromVat() + "'" +
            ", invoiceContactPhone='" + getInvoiceContactPhone() + "'" +
            ", invoiceMethod='" + getInvoiceMethod() + "'" +
            ", invoiceEMailAddress='" + getInvoiceEMailAddress() + "'" +
            "}";
    }
}
