package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "legal_id")
    private String legalId;

    @Column(name = "tscid")
    private String tscid;

    @Column(name = "corporate_name")
    private String corporateName;

    @Column(name = "market_company")
    private Boolean marketCompany;

    @Column(name = "market_area")
    private String marketArea;

    @OneToOne
    @JoinColumn(unique = true)
    private BillingAccount billingAccount;

    @OneToOne
    @JoinColumn(unique = true)
    private CustomerContactInfo customerContactInfo;

    @OneToOne
    @JoinColumn(unique = true)
    private Address shippingAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private Address legalAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLegalId() {
        return legalId;
    }

    public Customer legalId(String legalId) {
        this.legalId = legalId;
        return this;
    }

    public void setLegalId(String legalId) {
        this.legalId = legalId;
    }

    public String getTscid() {
        return tscid;
    }

    public Customer tscid(String tscid) {
        this.tscid = tscid;
        return this;
    }

    public void setTscid(String tscid) {
        this.tscid = tscid;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public Customer corporateName(String corporateName) {
        this.corporateName = corporateName;
        return this;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public Boolean isMarketCompany() {
        return marketCompany;
    }

    public Customer marketCompany(Boolean marketCompany) {
        this.marketCompany = marketCompany;
        return this;
    }

    public void setMarketCompany(Boolean marketCompany) {
        this.marketCompany = marketCompany;
    }

    public String getMarketArea() {
        return marketArea;
    }

    public Customer marketArea(String marketArea) {
        this.marketArea = marketArea;
        return this;
    }

    public void setMarketArea(String marketArea) {
        this.marketArea = marketArea;
    }

    public BillingAccount getBillingAccount() {
        return billingAccount;
    }

    public Customer billingAccount(BillingAccount billingAccount) {
        this.billingAccount = billingAccount;
        return this;
    }

    public void setBillingAccount(BillingAccount billingAccount) {
        this.billingAccount = billingAccount;
    }

    public CustomerContactInfo getCustomerContactInfo() {
        return customerContactInfo;
    }

    public Customer customerContactInfo(CustomerContactInfo customerContactInfo) {
        this.customerContactInfo = customerContactInfo;
        return this;
    }

    public void setCustomerContactInfo(CustomerContactInfo customerContactInfo) {
        this.customerContactInfo = customerContactInfo;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public Customer shippingAddress(Address address) {
        this.shippingAddress = address;
        return this;
    }

    public void setShippingAddress(Address address) {
        this.shippingAddress = address;
    }

    public Address getLegalAddress() {
        return legalAddress;
    }

    public Customer legalAddress(Address address) {
        this.legalAddress = address;
        return this;
    }

    public void setLegalAddress(Address address) {
        this.legalAddress = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", legalId='" + getLegalId() + "'" +
            ", tscid='" + getTscid() + "'" +
            ", corporateName='" + getCorporateName() + "'" +
            ", marketCompany='" + isMarketCompany() + "'" +
            ", marketArea='" + getMarketArea() + "'" +
            "}";
    }
}
