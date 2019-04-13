package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.ScientificPublication;

import java.util.Date;

public class LibrarianReturnItem extends AbstractReaderItem {
    private ScientificPublication scientificPublication;
    private Date issueDate;
    private Date expectedDate;

    public LibrarianReturnItem(ScientificPublication scientificPublication, Date issueDate, Date expectedDate) {
        this.scientificPublication = scientificPublication;
        this.issueDate = issueDate;
        this.expectedDate = expectedDate;
    }

    public String getPublicationName() {
        return scientificPublication.getName();
    }

    public String getIsbn() {
        return scientificPublication.getIsbn();
    }

    public long getCopyId() {
        return scientificPublication.getId();
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }
}