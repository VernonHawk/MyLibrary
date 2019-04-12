package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.SciPubOrder;

import java.util.Date;

public class ReservedReaderItem extends AbstractReaderItem {

    private long id;
    private String name;
    private Date orderDate;

    public ReservedReaderItem(final SciPubOrder order) {
        this.id = order.getId();
        this.name = order.getScientificPublication().getName();
        this.orderDate = order.getOrderDate();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}
