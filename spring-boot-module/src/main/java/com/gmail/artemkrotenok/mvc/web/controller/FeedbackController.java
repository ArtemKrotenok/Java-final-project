package com.gmail.artemkrotenok.mvc.web.controller;

import com.gmail.artemkrotenok.mvc.service.FeedbackService;
import com.gmail.artemkrotenok.mvc.service.model.FeedbackDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping()
    public String getFirstFeedbackPage() {
        return "redirect:/feedback/page/1";
    }

    @GetMapping("/page/{numberPage}")
    public String getFeedbackPage(@PathVariable int numberPage, Model model) {
        Long countFeedbacks = feedbackService.getCountFeedback();
        model.addAttribute("countFeedbacks", countFeedbacks);
        model.addAttribute("page", numberPage);
        List<FeedbackDTO> feedbacksDTO = feedbackService.getItemsByPage(numberPage);
        model.addAttribute("feedbacks", feedbacksDTO);
        return "feedback";
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id, Model model) {
        feedbackService.deleteById(id);
        model.addAttribute("message", "Selected feedback was deleted successfully");
        return "message";
    }
}
