package com.ukma.mylibrary.components;

import com.ukma.mylibrary.adapters.ItemUtils;
import com.ukma.mylibrary.entities.ScientificPublication;

public class LibraryItem extends AbstractReaderItem {

    private ScientificPublication scientificPublication;

    // Constructor that is used to create an instance of the LibraryItem object
    public LibraryItem(ScientificPublication scientificPublication) {
        this.scientificPublication = scientificPublication;
    }

    public long getId() {
        return scientificPublication.getId();
    }

    public String getIsbn() {
        return scientificPublication.getIsbn();
    }

    public String getName() {
        return scientificPublication.getName();
    }

    public ScientificPublication.SCType getScType() {
        return scientificPublication.getScType();
    }


    public int getTotalCopies() {
        return scientificPublication.getAllCopiesCount();
    }

    public ItemUtils.BookState getState() {
        return scientificPublication.getFreeCopiesCount() > 0 ? ItemUtils.BookState.FREE
                                                              : ItemUtils.BookState.RESERVED;
    }

    @Override
    public String toString() {
        return "LibraryItem{" +
                "scientificPublication=" + scientificPublication +
                '}';
    }
}
