package com.gmail.artemkrotenok.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

@Service
public class NewsServiceImpl implements NewsService {

    public static final int MAX_LENGTH_DESCRIPTION_NEWS = 200;
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd";
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final CommentUtil commentUtil;

    public NewsServiceImpl(
            NewsRepository newsRepository,
            UserRepository userRepository,
            UserUtil userUtil,
            CommentUtil commentUtil) {
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
        if (news == null) {
            return null;
        }
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

    @Override
    @Transactional
    public NewsDTO update(NewsDTO newsDTO) {
        News news = newsRepository.findById(newsDTO.getId());
        news.setDate(LocalDate.now());
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        newsRepository.merge(news);
        return getDTOFromObject(news);
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
        newsDTO.setDate(news.getDate().toString());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setDescription(getDescription(news));
        newsDTO.setContent(news.getContent());
        List<CommentDTO> commentsDTO = getCommentsDTOFromObject(news.getComments());
        newsDTO.setCommentsDTO(commentsDTO);
        UserDTO userDTO = userUtil.getDTOFromObject(news.getUser());
        newsDTO.setUserDTO(userDTO);
        return newsDTO;
    }

    private String getDescription(News news) {
        String content = news.getContent();
        String descriptionContent;
        if (content.length() <= MAX_LENGTH_DESCRIPTION_NEWS) {
            return content;
        }
        descriptionContent = content.substring(0, MAX_LENGTH_DESCRIPTION_NEWS - 1);
        int lengthDescription = descriptionContent.lastIndexOf(' ');
        descriptionContent = descriptionContent.substring(0, lengthDescription);
        return descriptionContent;
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
        String stringDate = newsDTO.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDate date = LocalDate.parse(stringDate, formatter);
        news.setDate(date);
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        User user = userRepository.findById(newsDTO.getUserDTO().getId());
        news.setUser(user);
        return news;
    }

}
