package com.gmail.artemkrotenok.mvc.repository;

import com.gmail.artemkrotenok.mvc.repository.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {

    User getUserByEmail(String email);

    List<User> getItemsByPageSorted(int startPosition, int itemsByPage);
}
