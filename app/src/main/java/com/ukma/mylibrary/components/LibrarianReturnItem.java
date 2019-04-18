package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.CopyIssue;

import java.util.Date;

public class LibrarianReturnItem extends AbstractItem {
    private CopyIssue mCopyIssue;

    public LibrarianReturnItem(final CopyIssue copyIssue) {
        mCopyIssue = copyIssue;
    }

    public String getPublicationName() {
        return mCopyIssue.getSciPub().getName();
    }

    public String getIsbn() {
        return mCopyIssue.getSciPub().getIsbn();
    }

    public long getCopyId() {
        return mCopyIssue.getSciPubCopy().getId();
    }

    public Date getIssueDate() {
        return mCopyIssue.getIssueDate();
    }

    public Date getExpectedDate() {
        return mCopyIssue.getExpectedReturnDate();
    }
}