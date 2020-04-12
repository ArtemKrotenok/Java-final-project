package com.gmail.artemkrotenok.mvc.service.impl;

import com.gmail.artemkrotenok.mvc.repository.FeedbackRepository;
import com.gmail.artemkrotenok.mvc.repository.model.Feedback;
import com.gmail.artemkrotenok.mvc.service.FeedbackService;
import com.gmail.artemkrotenok.mvc.service.model.FeedbackDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.artemkrotenok.mvc.service.constants.PageConstants.ITEMS_BY_PAGE;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    @Transactional
    public void add(FeedbackDTO feedbackDTO) {
        Feedback feedback = getObjectFromDTO(feedbackDTO);
        feedbackRepository.persist(feedback);
    }

    @Override
    @Transactional
    public Long getCountFeedback() {
        return feedbackRepository.getCount();
    }

    @Override
    @Transactional
    public List<FeedbackDTO> getItemsByPage(int page) {
        int startPosition = ((page - 1) * ITEMS_BY_PAGE + 1) - 1;
        List<Feedback> feedbacks = feedbackRepository.getItemsByPage(startPosition, ITEMS_BY_PAGE);
        return convertItemsToItemsDTO(feedbacks);
    }

    @Override
    @Transactional
    public FeedbackDTO getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id);
        return getDTOFromObject(feedback);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Feedback feedback = feedbackRepository.findById(id);
        feedbackRepository.remove(feedback);
    }

    private List<FeedbackDTO> convertItemsToItemsDTO(List<Feedback> items) {
        return items.stream()
                .map(this::getDTOFromObject)
                .collect(Collectors.toList());
    }

    private FeedbackDTO getDTOFromObject(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setCustomerName(feedback.getCustomerName());
        feedbackDTO.setText(feedback.getText());
        feedbackDTO.setDate(feedback.getDate());
        feedbackDTO.setVisible(feedback.getVisible());
        return feedbackDTO;
    }

    private Feedback getObjectFromDTO(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setCustomerName(feedbackDTO.getCustomerName());
        feedback.setText(feedbackDTO.getText());
        feedback.setDate(feedbackDTO.getDate());
        feedback.setVisible(feedbackDTO.getVisible());
        return feedback;
    }
}
