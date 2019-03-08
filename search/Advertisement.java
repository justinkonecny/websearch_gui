package search;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents one vehicle listing. A listing includes a title, location, url, description, age (days since posting),
 * list of images, and attributes (descriptors).
 */
public class Advertisement {
    //the listing title
    private String title;
    //the listing location (e.g. "newyork")
    private String location;
    //the url to the online advertisement
    private String link;
    //the description of the vehicles attributes (e.g. miles, vin, color)
    private String attributes;
    //the number of days since posted
    private int age;
    //the listings list of images
    private List<Image> imageList;
    //the listing's provided description
    private String body;
    //the listing's price
    private int price;

    /**
     * Constructs an Advertisement and initializes the fields (i.e. title = "title").
     */
    public Advertisement() {
        this.title = "title";
        this.location = "location";
        this.link = "link";
        this.attributes = "attributes";
        this.body = "";
        this.age = 0;
        this.price = 0;
        this.imageList = new ArrayList<Image>();
    }

    /**
     * Set's this Advertisment's title to the given value.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets this Advertisement's location to the given value.
     *
     * @param location the new location
     */
    public void setLocation(String location) {
        if (location.equals("newjersey")) {
            this.location = "New Jersey";
        } else {
            this.location = location;
        }
    }

    /**
     * Sets this Advertisement's age to the given value.
     *
     * @param age the number of days since posted
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Sets this Advertisement's URL to the given value.
     *
     * @param link the new URL
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Sets this Advertisements attribute description to the given value.
     *
     * @param attributes the new description of attributes
     */
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    /**
     * Sets this Advertisement's list of images to the given list.
     *
     * @param imageList the new list of images
     */
    public void setImages(List<Image> imageList) {
        this.imageList = imageList;
    }

    /**
     * Set's this Advertisement's description to the given value.
     *
     * @param body the new body/description
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Set's this Advertisement's price to the given value.
     *
     * @param price the new price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Returns this Advertisement's title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns this Advertisement's location.
     *
     * @return the location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Returns this advertisement's URL.
     *
     * @return the URL
     */
    public String getLink() {
        return this.link;
    }

    /**
     * Returns this Advertisement's attribute description.
     *
     * @return the attributes
     */
    public String getAttributes() {
        return this.attributes;
    }

    /**
     * Returns the number of days since this Advertisement was posted.
     *
     * @return the age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Returns this Advertisement's list of images.
     *
     * @return the list of images
     */
    public List<Image> getImages() {
        return this.imageList;
    }

    /**
     * Returns this Advertisements's description.
     *
     * @return the body/description
     */
    public String getBody() {
        return this.body;
    }

    /**
     * Returns this Advertisement's price.
     *
     * @return the price
     */
    public int getPrice() {
        return this.price;
    }
}
