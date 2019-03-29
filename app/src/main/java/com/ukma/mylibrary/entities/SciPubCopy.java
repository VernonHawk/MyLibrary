package com.ukma.mylibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ukma.mylibrary.entities.filters.EmptyIdFilter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SciPubCopy implements Entity {
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = EmptyIdFilter.class)
    private long id;

    public SciPubCopy() {
    }

    public SciPubCopy(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SciPubCopy{" +
                "id=" + id +
                '}';
    }
}
