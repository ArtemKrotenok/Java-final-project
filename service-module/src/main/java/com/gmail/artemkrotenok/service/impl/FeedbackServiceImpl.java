package com.gmail.artemkrotenok.service.impl;

import com.gmail.artemkrotenok.repository.FeedbackRepository;
import com.gmail.artemkrotenok.repository.model.Feedback;
import com.gmail.artemkrotenok.service.FeedbackService;
import com.gmail.artemkrotenok.service.model.FeedbackDTO;
import com.gmail.artemkrotenok.service.util.PaginationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(
            FeedbackRepository feedbackRepository) {
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
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<Feedback> feedbacks = feedbackRepository.getItemsByPage(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return convertItemsToItemsDTO(feedbacks);
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
        feedbackDTO.setContent(feedback.getContent());
        feedbackDTO.setDate(feedback.getDate());
        feedbackDTO.setVisible(feedback.getVisible());
        return feedbackDTO;
    }

    private Feedback getObjectFromDTO(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setCustomerName(feedbackDTO.getCustomerName());
        feedback.setContent(feedbackDTO.getContent());
        feedback.setDate(feedbackDTO.getDate());
        feedback.setVisible(feedbackDTO.getVisible());
        return feedback;
    }

}
