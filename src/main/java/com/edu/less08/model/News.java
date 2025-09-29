package com.edu.less08.model;

import java.time.LocalDate;
import java.util.Objects;

public class News {
    private int id;
    private String title;
    private String brief;
    private String content;
    private String pathImage;
    private LocalDate publishDate;

    public News() {
    }

    public News(int id, String title, String brief, String content, String pathImage, LocalDate publishDate) {
        this.id = id;
        this.title = title;
        this.brief = brief;
        this.content = content;
        this.pathImage = pathImage;
        this.publishDate = publishDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        News news = (News) o;
        return id == news.id && Objects.equals(title, news.title) && Objects.equals(brief, news.brief) && Objects.equals(content, news.content) && Objects.equals(pathImage, news.pathImage) && Objects.equals(publishDate, news.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, brief, content, pathImage, publishDate);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", content='" + content + '\'' +
                ", pathImage='" + pathImage + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}
