package com.gmail.artemkrotenok.service.impl;

import com.gmail.artemkrotenok.repository.NewsRepository;
import com.gmail.artemkrotenok.repository.UserRepository;
import com.gmail.artemkrotenok.repository.model.Comment;
import com.gmail.artemkrotenok.repository.model.News;
import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.service.NewsService;
import com.gmail.artemkrotenok.service.constants.PageConstants;
import com.gmail.artemkrotenok.service.model.CommentDTO;
import com.gmail.artemkrotenok.service.model.NewsDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;
import com.gmail.artemkrotenok.service.util.CommentUtil;
import com.gmail.artemkrotenok.service.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final CommentUtil commentUtil;

    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository, UserUtil userUtil, CommentUtil commentUtil) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.commentUtil = commentUtil;
    }

    @Override
    @Transactional
    public NewsDTO add(NewsDTO newsDTO) {
        News news = getObjectFromDTO(newsDTO);
        newsRepository.persist(news);
        return getDTOFromObject(news);
    }

    @Override
    @Transactional
    public List<NewsDTO> findAll() {
        List<News> newsList = newsRepository.findAll();
        return getNewsDTOFromObject(newsList);
    }

    @Override
    @Transactional
    public Long getCountNews() {
        return newsRepository.getCount();
    }

    @Override
    @Transactional
    public List<NewsDTO> getItemsByPageSorted(Integer page) {
        int startPosition = ((page - 1) * PageConstants.ITEMS_BY_PAGE + 1) - 1;
        List<News> news = newsRepository.getItemsByPageSorted(startPosition, PageConstants.ITEMS_BY_PAGE);
        return getNewsDTOFromObject(news);
    }

    @Override
    @Transactional
    public NewsDTO findById(Long id) {
        News news = newsRepository.findById(id);
        List<Comment> comments = news.getComments();
        comments.sort(new Comparator<Comment>() {
            public int compare(Comment o1, Comment o2) {
                return o2.getDate().toString().compareTo(o1.getDate().toString());
            }
        });
        news.setComments(comments);
        return getDTOFromObject(news);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        News news = newsRepository.findById(id);
        newsRepository.remove(news);
        return true;
    }


    private List<NewsDTO> getNewsDTOFromObject(List<News> newsList) {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for (News news : newsList) {
            newsDTOList.add(getDTOFromObject(news));
        }
        return newsDTOList;
    }

    private NewsDTO getDTOFromObject(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setDate(news.getDate());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setDescription(news.getDescription());
        newsDTO.setContent(news.getContent());
        List<CommentDTO> commentsDTO = getCommentsDTOFromObject(news.getComments());
        newsDTO.setCommentsDTO(commentsDTO);
        UserDTO userDTO = userUtil.getDTOFromObject(news.getUser());
        newsDTO.setUserDTO(userDTO);
        return newsDTO;
    }

    private List<CommentDTO> getCommentsDTOFromObject(List<Comment> comments) {
        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : comments) {
            commentsDTO.add(commentUtil.getDTOFromObject(comment));
        }
        return commentsDTO;
    }

    private News getObjectFromDTO(NewsDTO newsDTO) {
        News news = new News();
        news.setDate(newsDTO.getDate());
        news.setTitle(newsDTO.getTitle());
        news.setDescription(newsDTO.getDescription());
        news.setContent(newsDTO.getContent());
        User user = userRepository.findById(newsDTO.getUserDTO().getId());
        news.setUser(user);
        return news;
    }
}
