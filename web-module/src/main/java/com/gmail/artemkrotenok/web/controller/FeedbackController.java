package com.gmail.artemkrotenok.web.controller;

import com.gmail.artemkrotenok.service.FeedbackService;
import com.gmail.artemkrotenok.service.model.FeedbackDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public String getFeedbackPage(
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    ) {
        if (page == null) {
            page = 1;
        }
        Long countFeedbacks = feedbackService.getCountFeedback();
        model.addAttribute("countFeedbacks", countFeedbacks);
        model.addAttribute("page", page);
        List<FeedbackDTO> feedbacksDTO = feedbackService.getItemsByPage(page);
        model.addAttribute("feedbacks", feedbacksDTO);
        return "feedbacks";
    }

    @PostMapping("/delete")
    public String deleteFeedback(
            @RequestParam(name = "feedbackId") Long feedbackId,
            Model model) {
        feedbackService.deleteById(feedbackId);
        model.addAttribute("message", "Selected feedback was deleted successfully");
        model.addAttribute("redirect", "/feedbacks");
        return "message";
    }
}
