package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.SciPubOrder;

import java.util.Date;

public class ReservedReaderItem extends AbstractItem {

    private final SciPubOrder mOrder;

    public ReservedReaderItem(final SciPubOrder order) {
        mOrder = order;
    }

    public long getId() {
        return mOrder.getId();
    }

    public String getName() {
        return mOrder.getScientificPublication().getName();
    }

    public Date getOrderDate() {
        return mOrder.getOrderDate();
    }
}
