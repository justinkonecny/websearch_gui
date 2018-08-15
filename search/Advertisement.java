package search;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Advertisement {

    private String title;
    private String location;
    private String link;
    private String attributes;
    private int age;
    private List<Image> imageList;
    private String body;

    public Advertisement() {
        this.title = "title";
        this.location = "location";
        this.link = "link";
        this.attributes = "attributes";
        this.body = "";
        this.age = 0;
        this.imageList = new ArrayList<Image>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public void setImages(List<Image> imageList) {
        this.imageList = imageList;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLocation() {
        return this.location;
    }

    public String getLink() {
        return this.link;
    }

    public String getAttributes() {
        return this.attributes;
    }

    public int getAge() {
        return this.age;
    }

    public List<Image> getImages() {
        return this.imageList;
    }

    public String getBody() {
        return this.body;
    }
}
