package com.gmail.artemkrotenok.mvc.repository.impl;

import com.gmail.artemkrotenok.mvc.repository.UserRepository;
import com.gmail.artemkrotenok.mvc.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM " + entityClass.getSimpleName() + " u WHERE u.email=:email";
        Query query = entityManager.createQuery(hql);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public List<User> getItemsByPageSorted(int startPosition, int itemsByPage) {
        String hql = "FROM " + entityClass.getName() + " u ORDER BY u.email ASC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(itemsByPage);
        return query.getResultList();
    }
}
