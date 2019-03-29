package com.ukma.mylibrary.entities;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ukma.mylibrary.entities.filters.EmptyIdFilter;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScientificPublication implements Entity {
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = EmptyIdFilter.class)
    private long id;
    private String isbn;
    private String name;
    @JsonProperty("sc_type")
    private SCType scType;
    private int pages;
    @JsonProperty("publish_date")
    private Date publishDate;
    // TODO delete this and and change to integer field with name sciPubCopiesCount
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonProperty("sci_pub_copies")
    private List<SciPubCopy> sciPubCopies;

    public enum SCType {
        Book, Collection
    }

    public ScientificPublication(){
    }

    public ScientificPublication(String isbn, String name, SCType scType, int pages, Date publishDate) {
        this.isbn = isbn;
        this.name = name;
        this.scType = scType;
        this.pages = pages;
        this.publishDate = publishDate;
    }

    public ScientificPublication(long id, String isbn, String name, SCType scType, int pages, Date publishDate) {
        this.id = id;
        this.isbn = isbn;
        this.name = name;
        this.scType = scType;
        this.pages = pages;
        this.publishDate = publishDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SCType getScType() {
        return scType;
    }

    public void setScType(SCType scType) {
        this.scType = scType;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public List<SciPubCopy> getSciPubCopies() {
        return sciPubCopies;
    }

    public void setSciPubCopies(List<SciPubCopy> sciPubCopies) {
        this.sciPubCopies = sciPubCopies;
    }

    public int getSciPubCopiesCount() {
        return sciPubCopies.size();
    }

    public boolean isFree() {
        return getSciPubCopiesCount() > 0;
    }

    @Override
    public String toString() {
        return "ScientificPublication{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", scType=" + scType +
                ", pages=" + pages +
                ", publishDate=" + publishDate +
                ", sciPubCopies=" + sciPubCopies +
                '}';
    }
}
