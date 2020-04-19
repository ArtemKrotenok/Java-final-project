package com.gmail.artemkrotenok.web.controller;

import com.gmail.artemkrotenok.service.NewsService;
import com.gmail.artemkrotenok.service.model.NewsDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public String getNewsPage(
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    ) {
        if (page == null) {
            page = 1;
        }
        Long countNews = newsService.getCountNews();
        model.addAttribute("countNews", countNews);
        model.addAttribute("page", page);
        List<NewsDTO> newsDTOList = newsService.getItemsByPageSorted(page);
        model.addAttribute("newsList", newsDTOList);
        return "news";
    }

    @GetMapping("/{id}")
    public String getNewsPage(@PathVariable Long id, Model model) {
        NewsDTO newsDTO = newsService.findById(id);
        model.addAttribute("news", newsDTO);
        return "news_details";
    }
}
