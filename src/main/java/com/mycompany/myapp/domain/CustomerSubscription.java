package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CustomerSubscription.
 */
@Entity
@Table(name = "customer_subscription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emn_id")
    private String emnId;

    @Column(name = "type")
    private String type;

    @Column(name = "agreement")
    private String agreement;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "change_date")
    private LocalDate changeDate;

    @Column(name = "quarterly_fee")
    private String quarterlyFee;

    @Column(name = "one_time_fee")
    private String oneTimeFee;

    @Column(name = "invoice_text")
    private String invoiceText;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmnId() {
        return emnId;
    }

    public CustomerSubscription emnId(String emnId) {
        this.emnId = emnId;
        return this;
    }

    public void setEmnId(String emnId) {
        this.emnId = emnId;
    }

    public String getType() {
        return type;
    }

    public CustomerSubscription type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgreement() {
        return agreement;
    }

    public CustomerSubscription agreement(String agreement) {
        this.agreement = agreement;
        return this;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public CustomerSubscription startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CustomerSubscription endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getChangeDate() {
        return changeDate;
    }

    public CustomerSubscription changeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
        return this;
    }

    public void setChangeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
    }

    public String getQuarterlyFee() {
        return quarterlyFee;
    }

    public CustomerSubscription quarterlyFee(String quarterlyFee) {
        this.quarterlyFee = quarterlyFee;
        return this;
    }

    public void setQuarterlyFee(String quarterlyFee) {
        this.quarterlyFee = quarterlyFee;
    }

    public String getOneTimeFee() {
        return oneTimeFee;
    }

    public CustomerSubscription oneTimeFee(String oneTimeFee) {
        this.oneTimeFee = oneTimeFee;
        return this;
    }

    public void setOneTimeFee(String oneTimeFee) {
        this.oneTimeFee = oneTimeFee;
    }

    public String getInvoiceText() {
        return invoiceText;
    }

    public CustomerSubscription invoiceText(String invoiceText) {
        this.invoiceText = invoiceText;
        return this;
    }

    public void setInvoiceText(String invoiceText) {
        this.invoiceText = invoiceText;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerSubscription)) {
            return false;
        }
        return id != null && id.equals(((CustomerSubscription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerSubscription{" +
            "id=" + getId() +
            ", emnId='" + getEmnId() + "'" +
            ", type='" + getType() + "'" +
            ", agreement='" + getAgreement() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", changeDate='" + getChangeDate() + "'" +
            ", quarterlyFee='" + getQuarterlyFee() + "'" +
            ", oneTimeFee='" + getOneTimeFee() + "'" +
            ", invoiceText='" + getInvoiceText() + "'" +
            "}";
    }
}
