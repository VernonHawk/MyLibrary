package com.ukma.mylibrary.components;

import com.ukma.mylibrary.adapters.ItemUtils;

public class LibraryItem extends AbstractReaderItem {

    private String itemName;
    private int totalCopies;
    private ItemUtils.BookState state;
    private ItemUtils.ItemType type;

    // Constructor that is used to create an instance of the LibraryItem object
    public LibraryItem(String itemName, int totalCopies, ItemUtils.BookState state, ItemUtils.ItemType type) {
        this.itemName = itemName;
        this.totalCopies = totalCopies;
        this.state = state;
        this.type = type;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public ItemUtils.BookState getState() {
        return state;
    }

    public void setState(ItemUtils.ItemType type) {
        this.type = type;
    }

    public void setState(ItemUtils.BookState state) {
        this.state = state;
    }

    public ItemUtils.ItemType getItemType() {
        return type;
    }
}
