package com.edu.less08.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class News implements Serializable {
    private int id;
    private String title;
    private String brief;
    private String contentPath;
    private String imagePath;
    private LocalDate publishDate;
    private int publisherId;
    private int statusId;

    public News() {
    }

    private News(NewsBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.brief = builder.brief;
        this.contentPath = builder.contentPath;
        this.imagePath = builder.imagePath;
        this.publishDate = builder.publishDate;
        this.publisherId = builder.publisherId;
        this.statusId = builder.statusId;
    }

    public static class NewsBuilder {
        private int id;
        private String title;
        private String brief;
        private String contentPath;
        private String imagePath;
        private LocalDate publishDate;
        private int publisherId;
        private int statusId;

        public NewsBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public NewsBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public NewsBuilder setBrief(String brief) {
            this.brief = brief;
            return this;
        }

        public NewsBuilder setContentPath(String contentPath) {
            this.contentPath = contentPath;
            return this;
        }

        public NewsBuilder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public NewsBuilder setPublishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public NewsBuilder setPublisherId(int publisherId) {
            this.publisherId = publisherId;
            return this;
        }

        public NewsBuilder setStatusId(int statusId) {
            this.statusId = statusId;
            return this;
        }

        public News build() {
            News news = new News(this);
            clearBuilder();
            return news;
        }

        public void clearBuilder() {
            this.id = 0;
            this.title = null;
            this.brief = null;
            this.contentPath = null;
            this.imagePath = null;
            this.publishDate = null;
        }
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

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        News news = (News) o;
        return id == news.id && publisherId == news.publisherId && statusId == news.statusId && Objects.equals(title, news.title) && Objects.equals(brief, news.brief) && Objects.equals(contentPath, news.contentPath) && Objects.equals(imagePath, news.imagePath) && Objects.equals(publishDate, news.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, brief, contentPath, imagePath, publishDate, publisherId, statusId);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", content='" + contentPath + '\'' +
                ", pathImage='" + imagePath + '\'' +
                ", publishDate=" + publishDate +
                ", publisherId=" + publisherId +
                ", statusId=" + statusId +
                '}';
    }
}
