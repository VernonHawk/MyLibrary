package com.ukma.mylibrary.components;

import java.util.Date;

public class ReservedReaderItem extends AbstractItem {

    private String itemName;
    private Date itemReservationDate;

    // Constructor that is used to create an instance of the ReaderItem object
    public ReservedReaderItem(String itemName, Date itemReservationDate) {
        this.itemName = itemName;
        this.itemReservationDate = itemReservationDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getItemReservationDate() {
        return itemReservationDate;
    }

    public void setItemReservedDate(Date itemReservationDate) {
        this.itemReservationDate = itemReservationDate;
    }
}
