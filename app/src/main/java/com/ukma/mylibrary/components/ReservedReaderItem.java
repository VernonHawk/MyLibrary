package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.SciPubOrder;

import java.util.Date;

public class ReservedReaderItem extends AbstractReaderItem {

    private String name;
    private Date orderDate;

    // Constructor that is used to create an instance of the ReaderItem object
    public ReservedReaderItem(String name, Date orderDate) {
        this.name = name;
        this.orderDate = orderDate;
    }

    public ReservedReaderItem(final SciPubOrder order) {
        this.name = order.getScientificPublication().getName();
        this.orderDate = order.getOrderDate();
    }

    public String getName() {
        return name;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}
