package com.Devbypranjal.skyeapp;

public class The_Slide_Items_Model_Class {
    private int featured_image;
    private String the_caption_Title;
    private String the_caption_description;

    public The_Slide_Items_Model_Class(int hero, String title, String description) {
        this.featured_image = hero;
        this.the_caption_Title = title;
        this.the_caption_description = description;
    }

    public int getFeatured_image() {
        return featured_image;
    }

    public String getThe_caption_Title() {
        return the_caption_Title;
    }

    public void setFeatured_image(int featured_image) {
        this.featured_image = featured_image;
    }

    public String getThe_caption_description() {
        return the_caption_description;
    }

    public void setThe_caption_description(String the_caption_description) {
        this.the_caption_description = the_caption_description;
    }

    public void setThe_caption_Title(String the_caption_Title) {
        this.the_caption_Title = the_caption_Title;
    }
}
