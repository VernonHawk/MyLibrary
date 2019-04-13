package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.ScientificPublication;

import java.util.Date;

public class LibrarianWithdrawalItem extends AbstractReaderItem {

    private ScientificPublication scientificPublication;
    private Date withdrawalDate;

    public LibrarianWithdrawalItem(ScientificPublication scientificPublication, Date withdrawalDate) {
        this.scientificPublication = scientificPublication;
        this.withdrawalDate = withdrawalDate;
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

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }
}
