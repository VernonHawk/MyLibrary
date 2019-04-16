package com.ukma.mylibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ukma.mylibrary.entities.filters.EmptyIdFilter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SciPubCopy implements Entity {
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = EmptyIdFilter.class)
    private long id;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonProperty("scientific_publication")
    private ScientificPublication scientificPublication;

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

    public ScientificPublication getScientificPublication() {
        return scientificPublication;
    }

    public void setScientificPublication(
        final ScientificPublication scientificPublication
    ) {
        this.scientificPublication = scientificPublication;
    }

    @Override
    public String toString() {
        return "SciPubCopy{" +
                "id=" + id +
                '}';
    }
}
