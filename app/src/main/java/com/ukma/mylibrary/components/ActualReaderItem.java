package com.ukma.mylibrary.components;

public class ActualReaderItem {

    private String itemName;
    private String itemIssueDate;
    private String itemReturnDate;

    // Constructor that is used to create an instance of the Movie object
    public ActualReaderItem(String itemName, String itemIssueDate, String itemReturnDate) {
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

    public String getItemIssueDate() {
        return itemIssueDate;
    }

    public void setItemIssueDate(String itemIssueDate) {
        this.itemIssueDate = itemIssueDate;
    }

    public String getItemReturnDate() {
        return itemReturnDate;
    }

    public void setItemReturnDate(String itemReturnDate) {
        this.itemReturnDate = itemReturnDate;
    }
}
