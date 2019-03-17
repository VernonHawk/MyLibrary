package com.ukma.mylibrary.entities.filters;

public class EmptyIdFilter {
    @Override
    public boolean equals(Object obj) {
        if (obj == null || (long) obj == 0) {
            return true;
        }
        return false;
    }
}
