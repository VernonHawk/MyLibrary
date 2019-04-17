package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.SciPubCopy;

import java.util.Date;

public class LibrarianReturnItem extends AbstractItem {
    private SciPubCopy sciPubCopy;
    private Date issueDate;
    private Date expectedDate;

    public LibrarianReturnItem(SciPubCopy sciPubCopy, Date issueDate, Date expectedDate) {
        this.sciPubCopy = sciPubCopy;
        this.issueDate = issueDate;
        this.expectedDate = expectedDate;
    }

    public String getPublicationName() {
        return sciPubCopy.getScientificPublication().getName();
    }

    public String getIsbn() {
        return sciPubCopy.getScientificPublication().getIsbn();
    }

    public long getCopyId() {
        return sciPubCopy.getScientificPublication().getId();
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }
}