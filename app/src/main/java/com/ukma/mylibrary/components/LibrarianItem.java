package com.ukma.mylibrary.components;

import com.ukma.mylibrary.entities.User;

public class LibrarianItem extends AbstractItem {

    private User mUser;

    // Constructor that is used to create an instance of the ReaderItem object
    public LibrarianItem(final User user) {
        this.mUser = user;
    }

    public User getUser() {
        return mUser;
    }
}
