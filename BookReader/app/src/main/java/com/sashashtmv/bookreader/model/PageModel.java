package com.sashashtmv.bookreader.model;

public class PageModel {

    private String numberPage;
    private String description;

    public PageModel(String numberPage, String description) {
        this.numberPage = numberPage;
        this.description = description;
    }

    public String getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(String numberPage) {
        this.numberPage = numberPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
