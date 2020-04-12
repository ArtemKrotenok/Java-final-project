package com.gmail.artemkrotenok.mvc.repository.impl;

import com.gmail.artemkrotenok.mvc.repository.FeedbackRepository;
import com.gmail.artemkrotenok.mvc.repository.model.Feedback;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackRepositoryImpl extends GenericRepositoryImpl<Long, Feedback> implements FeedbackRepository {

}
