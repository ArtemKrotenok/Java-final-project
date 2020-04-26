package com.gmail.artemkrotenok.service.model;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class FeedbackDTO {

    private Long id;
    @NotNull(message = "The field 'castomer name' must be filled")
    private String customerName;
    @NotNull(message = "The field 'content' must be filled")
    private String content;
    @NotNull(message = "The field 'date' must be filled")
    private LocalDate date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

}
