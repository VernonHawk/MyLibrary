package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.CopyIssue;

import java.util.Date;

public class ActualReaderItem extends AbstractReaderItem {

    private final CopyIssue mCopyIssue;

    public ActualReaderItem(final CopyIssue copyIssue) {
        mCopyIssue = copyIssue;
    }

    public String getName() {
        return mCopyIssue.getSciPubName();
    }

    public Date getIssueDate() {
        return mCopyIssue.getIssueDate();
    }

    public Date getReturnDate() {
        return mCopyIssue.getExpectedReturnDate();
    }
}
