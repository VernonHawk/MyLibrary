package com.ukma.mylibrary.components;

import com.ukma.mylibrary.adapters.ItemUtils;
import com.ukma.mylibrary.entities.SCType;
import com.ukma.mylibrary.entities.ScientificPublication;

import java.util.HashMap;
import java.util.Map;

public class LibraryItem extends AbstractReaderItem {

    private ScientificPublication scientificPublication;
    private Map<SCType, ItemUtils.ItemType> scTypeToItemType = new HashMap<SCType, ItemUtils.ItemType>() {{
        put(SCType.Book, ItemUtils.ItemType.BOOK);
        put(SCType.Collection, ItemUtils.ItemType.COLLECTION);
    }};
    private Map<ItemUtils.ItemType, SCType> itemTypeToScType = new HashMap<ItemUtils.ItemType, SCType>() {{
        put(ItemUtils.ItemType.BOOK, SCType.Book);
        put(ItemUtils.ItemType.COLLECTION, SCType.Collection);
    }};

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

    public String getItemName() {
        return scientificPublication.getName();
    }

    public void setItemName(String name) {
        scientificPublication.setName(name);
    }

    public SCType getScType() {
        return scientificPublication.getScType();
    }

    public int getTotalCopies() {
        return scientificPublication.getSciPubCopiesCount();
    }

    public ItemUtils.BookState getState() {
        return scientificPublication.isFree() ? ItemUtils.BookState.FREE : ItemUtils.BookState.RESERVED;
    }

    public ItemUtils.ItemType getItemType() {
        return scTypeToItemType.get(scientificPublication.getScType());
    }

    public void setItemType(ItemUtils.ItemType type) {
        scientificPublication.setScType(itemTypeToScType.get(type));
    }

    @Override
    public String toString() {
        return "LibraryItem{" +
                "scientificPublication=" + scientificPublication +
                '}';
    }
}
