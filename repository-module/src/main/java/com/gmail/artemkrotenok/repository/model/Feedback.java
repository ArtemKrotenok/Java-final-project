package com.gmail.artemkrotenok.repository.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "is_visible")
    private Boolean isVisible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) &&
                Objects.equals(customerName, feedback.customerName) &&
                Objects.equals(content, feedback.content) &&
                Objects.equals(date, feedback.date) &&
                Objects.equals(isVisible, feedback.isVisible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, content, date, isVisible);
    }
}