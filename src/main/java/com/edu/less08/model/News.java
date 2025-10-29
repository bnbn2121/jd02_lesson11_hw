package com.edu.less08.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class News implements Serializable {
    private int id;
    private String title;
    private String brief;
    private String content;
    private String imagePath;
    private LocalDate publishDate;
    private User publisher;
    private Status status;

    public News() {
    }

    private News(NewsBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.brief = builder.brief;
        this.content = builder.content;
        this.imagePath = builder.imagePath;
        this.publishDate = builder.publishDate;
        this.publisher = builder.publisher;
        this.status = builder.status;
    }

    public static class NewsBuilder {
        private int id;
        private String title;
        private String brief;
        private String content;
        private String imagePath;
        private LocalDate publishDate;
        private User publisher;
        private Status status;

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

        public NewsBuilder setContent(String content) {
            this.content = content;
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

        public NewsBuilder setPublisher(User publisher) {
            this.publisher = publisher;
            return this;
        }

        public NewsBuilder setStatus(Status status) {
            this.status = status;
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
            this.content = null;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        News news = (News) o;
        return id == news.id && Objects.equals(title, news.title) && Objects.equals(brief, news.brief) && Objects.equals(content, news.content) && Objects.equals(imagePath, news.imagePath) && Objects.equals(publishDate, news.publishDate) && Objects.equals(publisher, news.publisher) && status == news.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, brief, content, imagePath, publishDate, publisher, status);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", content='" + content + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", publishDate=" + publishDate +
                ", publisher=" + publisher +
                ", status=" + status +
                '}';
    }
}
