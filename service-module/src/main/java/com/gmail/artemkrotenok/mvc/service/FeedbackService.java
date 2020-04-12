package com.gmail.artemkrotenok.mvc.service;

import com.gmail.artemkrotenok.mvc.service.model.FeedbackDTO;

import java.util.List;

public interface FeedbackService {

    void add(FeedbackDTO feedbackDTO);

    Long getCountFeedback();

    List<FeedbackDTO> getItemsByPage(int page);

    FeedbackDTO getFeedbackById(Long id);

    void deleteById(Long id);
}
