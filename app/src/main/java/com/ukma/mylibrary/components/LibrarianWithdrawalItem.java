package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.SciPubOrder;

import java.util.Date;

public class LibrarianWithdrawalItem extends AbstractItem {

    private final SciPubOrder mOrder;

    public LibrarianWithdrawalItem(final SciPubOrder order) {
        mOrder = order;
    }

    public long getOrderId() {
        return mOrder.getId();
    }

    public String getPublicationName() {
        return mOrder.getScientificPublication().getName();
    }

    public String getIsbn() {
        return mOrder.getScientificPublication().getIsbn();
    }

    public long getCopyId() {
        return mOrder.getSciPubCopy().getId();
    }

    public Date getOrderDate() {
        return mOrder.getOrderDate();
    }
}
