package com.ukma.mylibrary.components;

import java.util.Date;

public class ActualReaderItem extends AbstractReaderItem {

    private String itemName;
    private Date itemIssueDate;
    private Date itemReturnDate;

    // Constructor that is used to create an instance of the ReaderItem object
    public ActualReaderItem(String itemName, Date itemIssueDate, Date itemReturnDate) {
        this.itemName = itemName;
        this.itemIssueDate = itemIssueDate;
        this.itemReturnDate = itemReturnDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getItemIssueDate() {
        return itemIssueDate;
    }

    public void setItemIssueDate(Date itemIssueDate) {
        this.itemIssueDate = itemIssueDate;
    }

    public Date getItemReturnDate() {
        return itemReturnDate;
    }

    public void setItemReturnDate(Date itemReturnDate) {
        this.itemReturnDate = itemReturnDate;
    }
}
