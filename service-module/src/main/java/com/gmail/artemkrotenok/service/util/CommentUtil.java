package com.gmail.artemkrotenok.service.util;

import com.gmail.artemkrotenok.repository.UserRepository;
import com.gmail.artemkrotenok.repository.model.Comment;
import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.service.model.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentUtil {

    private final UserUtil userUtil;
    private final UserRepository userRepository;

    public CommentUtil(UserUtil userUtil, UserRepository userRepository) {
        this.userUtil = userUtil;
        this.userRepository = userRepository;
    }

    public CommentDTO getDTOFromObject(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDate(comment.getDate());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUserDTO(userUtil.getDTOFromObject(comment.getUser()));
        commentDTO.setNewsId(comment.getNewsId());
        return commentDTO;
    }

    public Comment getObjectFromDTO(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setDate(commentDTO.getDate());
        comment.setContent(commentDTO.getContent());
        User user = userRepository.findById(commentDTO.getUserDTO().getId());
        comment.setUser(user);
        comment.setNewsId(commentDTO.getNewsId());
        return comment;
    }
}
