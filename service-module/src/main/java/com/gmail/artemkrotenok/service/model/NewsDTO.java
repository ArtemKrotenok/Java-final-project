package com.gmail.artemkrotenok.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsDTO {

    private Long id;
    private LocalDateTime date;
    @NotNull(message = "The field 'title' must be filled")
    private String title;
    @NotNull(message = "The field 'description' must be filled")
    @Size(min = 1, max = 200, message = "'description' size must be between 1 and 200 characters")
    private String description;
    @NotNull(message = "The field 'content' must be filled")
    @Size(min = 1, max = 2000, message = "'content' size must be between 1 and 200 characters")
    private String content;
    private UserDTO userDTO;
    private List<CommentDTO> commentsDTO = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<CommentDTO> getCommentsDTO() {
        return commentsDTO;
    }

    public void setCommentsDTO(List<CommentDTO> commentsDTO) {
        this.commentsDTO = commentsDTO;
    }
}
