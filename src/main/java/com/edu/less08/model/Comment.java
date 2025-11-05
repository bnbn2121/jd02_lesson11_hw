package com.edu.less08.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Comment implements Serializable {
    private int id;
    private String text;
    private User author;
    private LocalDateTime publishDate;
    private Status status;

    public Comment() {
    }

    public Comment(int id, String text, User author, LocalDateTime publishDate, Status status) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.publishDate = publishDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
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
        Comment comment = (Comment) o;
        return id == comment.id && Objects.equals(text, comment.text) && Objects.equals(author, comment.author) && Objects.equals(publishDate, comment.publishDate) && status == comment.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, author, publishDate, status);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", publishDate=" + publishDate +
                ", status=" + status +
                '}';
    }
}
