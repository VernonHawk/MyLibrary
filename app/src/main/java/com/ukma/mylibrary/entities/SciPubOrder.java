package com.ukma.mylibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ukma.mylibrary.entities.filters.EmptyIdFilter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SciPubOrder implements Entity {
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = EmptyIdFilter.class)
    private long id;

    @JsonProperty("sci_pub_copy")
    private SciPubCopy sciPubCopy;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonProperty("scientific_publication")
    private ScientificPublication scientificPublication;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private User user; // Currently guarantied to be null

    @JsonProperty("order_date")
    private Date orderDate;

    @JsonProperty("withdrawal_date")
    private Date withdrawalDate;

    public enum Status {
        Pending, Canceled, Given
    }

    public SciPubOrder() {}

    public SciPubOrder(
        final long id,
        final SciPubCopy sciPubCopy,
        final ScientificPublication scientificPublication,
        final User user,
        final Date orderDate,
        final Date withdrawalDate
    ) {
        this(sciPubCopy, scientificPublication, user, orderDate, withdrawalDate);
        this.id = id;
    }

    public SciPubOrder(
        final SciPubCopy sciPubCopy,
        final ScientificPublication scientificPublication,
        final User user,
        final Date orderDate,
        final Date withdrawalDate
    ) {
        this.sciPubCopy = sciPubCopy;
        this.scientificPublication = scientificPublication;
        this.user = user;
        this.orderDate = orderDate;
        this.withdrawalDate = withdrawalDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SciPubCopy getSciPubCopy() {
        return sciPubCopy;
    }

    public void setSciPubCopy(final SciPubCopy sciPubCopy) {
        this.sciPubCopy = sciPubCopy;
    }

    public ScientificPublication getScientificPublication() {
        return scientificPublication;
    }

    public void setScientificPublication(
        final ScientificPublication scientificPublication
    ) {
        this.scientificPublication = scientificPublication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(final Date withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    @Override public String toString() {
        return "SciPubOrder{" +
               "id=" + id +
               ", sciPubCopy=" + sciPubCopy +
               ", scientificPublication=" + scientificPublication +
               ", user=" + user +
               ", orderDate=" + orderDate +
               ", withdrawalDate=" + withdrawalDate +
               '}';
    }
}
