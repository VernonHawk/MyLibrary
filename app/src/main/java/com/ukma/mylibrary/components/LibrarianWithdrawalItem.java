package com.ukma.mylibrary.components;

import java.util.Date;

public class LibrarianWithdrawalItem extends AbstractReaderItem {

    private String publicationName;

    private String isbn;

    private int copyId;

    private Date withdrawalDate;

    public LibrarianWithdrawalItem(String publicationName, String isbn, int copyId,
                                   Date withdrawalDate) {
        this.publicationName = publicationName;
        this.isbn = isbn;
        this.copyId = copyId;
        this.withdrawalDate = withdrawalDate;
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

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }
}
