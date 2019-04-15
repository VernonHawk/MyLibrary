package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.User;

public class LibrarianItem extends AbstractItem {


    private User mUser;

    // Constructor that is used to create an instance of the ReaderItem object
    public LibrarianItem(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
    
}
