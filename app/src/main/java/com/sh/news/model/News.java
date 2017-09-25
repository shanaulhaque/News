package com.sh.news.model;

/**
 * Created by shanaulhaque on 12/09/17.
 */

public class News {
    private Integer id;
    private String title;
    private String url;
    private String publisher;
    private Character category;
    private String hostname;
    private Long timestamp;



    private Boolean isFav;

    public News(){}


    public News(Integer id, String title, String url, String publisher, Character category, String hostname, long timestamp) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.publisher = publisher;
        this.category = category;
        this.hostname = hostname;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Character getCategory() {
        return category;
    }

    public void setCategory(Character category) {
        this.category = category;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getFav() {
        if(isFav == null)
            return false;
        return isFav;
    }

    public void setFav(Boolean fav) {
        isFav = fav;
    }
}
