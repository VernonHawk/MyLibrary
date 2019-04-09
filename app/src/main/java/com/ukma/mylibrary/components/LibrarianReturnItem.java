package com.ukma.mylibrary.components;

import java.util.Date;

public class LibrarianReturnItem extends AbstractReaderItem {

    private String publicationName;

    private String isbn;

    private int copyId;

    private Date issueDate;

    private Date expectedDate;

    public LibrarianReturnItem(String publicationName, String isbn, int copyId,
        Date issueDate, Date expectedDate) {
        this.publicationName = publicationName;
        this.isbn = isbn;
        this.copyId = copyId;
        this.issueDate = issueDate;
        this.expectedDate = expectedDate;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getCopyId() {
        return copyId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }
}
