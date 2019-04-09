package com.ukma.mylibrary.components;

public class LibrarianItem extends AbstractReaderItem {

    private Long userId;

    // Constructor that is used to create an instance of the ReaderItem object
    public LibrarianItem(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

//    public void setUserId(Long userId) { this.userId = userId; }
}
