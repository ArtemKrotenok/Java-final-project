package com.gmail.artemkrotenok.web.controller;

import com.gmail.artemkrotenok.service.NewsService;
import com.gmail.artemkrotenok.service.UserService;
import com.gmail.artemkrotenok.service.model.NewsDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")

public class APIController {

    private final NewsService newsService;
    private final UserService userService;

    public APIController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Object> addNewUser(@RequestBody @Valid UserDTO userDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(getFormattedTextErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.add(userDTO), HttpStatus.CREATED);
    }

    @GetMapping(value = "/articles")
    public List<NewsDTO> getNewsByPage(@RequestParam(name = "page") Integer page) {
        return newsService.getItemsByPageSorted(page);
    }

    @GetMapping(value = "/articles/{id}")
    public NewsDTO getNewsById(@PathVariable(name = "id") Long id) {
        return newsService.findById(id);
    }

    @PostMapping(value = "/articles")
    public ResponseEntity<Object> addNewNews(@RequestBody @Valid NewsDTO newsDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(getFormattedTextErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newsService.add(newsDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/articles/{id}")
    public boolean deleteItem(@PathVariable(name = "id") Long id) {
        return newsService.deleteById(id);
    }

    private Object getFormattedTextErrors(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> errors = new ArrayList<>();
        for (ObjectError error : allErrors) {
            errors.add(error.getCode() + " - " + error.getDefaultMessage());
        }
        return errors.toString();
    }
}
