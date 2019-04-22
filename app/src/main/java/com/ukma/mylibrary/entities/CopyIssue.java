package com.ukma.mylibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ukma.mylibrary.entities.filters.EmptyIdFilter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CopyIssue implements Entity {
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = EmptyIdFilter.class)
    private long id;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonProperty("sci_pub_copy")
    private SciPubCopy sciPubCopy;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private User user;

    @JsonProperty("issue_date")
    private Date issueDate;

    @JsonProperty("expected_return_date")
    private Date expectedReturnDate;

    @JsonProperty("actual_return_date")
    private Date actualReturnDate; // Currently guarantied to be null

    public CopyIssue() {}

    public CopyIssue(
        final long id,
        final SciPubCopy sciPubCopy,
        final User user,
        final Date issueDate,
        final Date expectedReturnDate,
        final Date actualReturnDate
    ) {
        this(sciPubCopy, user, issueDate, expectedReturnDate, actualReturnDate);
        this.id = id;
    }

    public CopyIssue(
        final SciPubCopy sciPubCopy,
        final User user,
        final Date issueDate,
        final Date expectedReturnDate,
        final Date actualReturnDate
    ) {
        this.sciPubCopy = sciPubCopy;
        this.user = user;
        this.issueDate = issueDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public ScientificPublication getSciPub() {
        return getSciPubCopy().getScientificPublication();
    }

    // region Getters & Setters
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

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(final Date expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public Date getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(final Date actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }
    // endregion

    @Override public String toString() {
        return "CopyIssue{" +
               "id=" + id +
               ", sciPubCopy=" + sciPubCopy +
               ", user=" + user +
               ", issueDate=" + issueDate +
               ", expectedReturnDate=" + expectedReturnDate +
               ", actualReturnDate=" + actualReturnDate +
               '}';
    }
}
