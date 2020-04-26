package com.gmail.artemkrotenok.web.controller;

import java.util.List;

import com.gmail.artemkrotenok.service.ItemService;
import com.gmail.artemkrotenok.service.model.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(
            ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String getItemPage(
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    ) {
        if (page == null) {
            page = 1;
        }
        Long countItems = itemService.getCountItem();
        model.addAttribute("countItems", countItems);
        model.addAttribute("page", page);
        List<ItemDTO> itemsDTO = itemService.getItemsByPageSorted(page);
        model.addAttribute("items", itemsDTO);
        return "items";
    }

    @GetMapping("/{id}")
    public String getItemPage(@PathVariable Long id, Model model) {
        ItemDTO itemDTO = itemService.findById(id);
        if (itemDTO == null) {
            model.addAttribute("message", "Item not found for id='" + id + "'");
            model.addAttribute("redirect", "/items");
            return "message";
        }
        model.addAttribute("item", itemDTO);
        return "item_details";
    }

}
