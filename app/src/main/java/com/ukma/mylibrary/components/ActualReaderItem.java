package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.CopyIssue;

import java.util.Date;

public class ActualReaderItem extends AbstractReaderItem {

    private String name;
    private Date issueDate;
    private Date returnDate;

    public ActualReaderItem(final CopyIssue copyIssue) {
        this.name = copyIssue.getSciPubName();
        this.issueDate = copyIssue.getIssueDate();
        this.returnDate = copyIssue.getExpectedReturnDate();
    }

    public String getName() {
        return name;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
