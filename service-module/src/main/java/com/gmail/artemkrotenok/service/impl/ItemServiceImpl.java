package com.gmail.artemkrotenok.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gmail.artemkrotenok.repository.ItemRepository;
import com.gmail.artemkrotenok.repository.model.Item;
import com.gmail.artemkrotenok.service.ItemService;
import com.gmail.artemkrotenok.service.model.ItemDTO;
import com.gmail.artemkrotenok.service.util.PaginationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(
            ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public ItemDTO add(ItemDTO itemDTO) {
        Item item = getObjectFromDTO(itemDTO);
        if (item.getUniqueNumber() == null) {
            item.setUniqueNumber(UUID.randomUUID().toString());
        }
        itemRepository.persist(item);
        return getDTOFromObject(item);
    }

    @Override
    @Transactional
    public Long getCountItem() {
        return itemRepository.getCount();
    }

    @Override
    @Transactional
    public List<ItemDTO> getItemsByPageSorted(Integer page) {
        int startPosition = PaginationUtil.getPositionByPage(page);
        List<Item> items = itemRepository.getItemsByPageSorted(startPosition, PaginationUtil.ITEMS_BY_PAGE);
        return convertItemsToItemsDTO(items);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Item item = itemRepository.findById(id);
        itemRepository.remove(item);
        return true;
    }

    @Override
    public ItemDTO findById(Long id) {
        Item item = itemRepository.findById(id);
        if (item == null) {
            return null;
        }
        return getDTOFromObject(item);
    }

    private List<ItemDTO> convertItemsToItemsDTO(List<Item> items) {
        return items.stream()
                .map(this::getDTOFromObject)
                .collect(Collectors.toList());
    }

    private ItemDTO getDTOFromObject(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setUniqueNumber(item.getUniqueNumber().toString());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setDescription(item.getDescription());
        return itemDTO;
    }

    private Item getObjectFromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setDescription(itemDTO.getDescription());
        return item;
    }

}
