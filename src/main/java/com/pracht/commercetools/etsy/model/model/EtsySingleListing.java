package com.pracht.commercetools.etsy.model.model;

public class EtsySingleListing {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String description;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EtsySingleListing withDescription(String description) {
        this.description = description;
        return this;
    }

    public EtsySingleListing withId(long id){
        this.id = id;
        return this;
    }

}
