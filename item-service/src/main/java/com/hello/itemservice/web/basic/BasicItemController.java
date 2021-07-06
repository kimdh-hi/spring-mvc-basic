package com.hello.itemservice.web.basic;

import com.hello.itemservice.domain.item.Item;
import com.hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping()
    public String itemList(Model model) {
        List<Item> findItems = itemRepository.findAll();
        model.addAttribute("items", findItems);
        return "basic/items";
    }

    @PostConstruct
    private void init() {
        itemRepository.save(new Item("ItemA", 1000, 100));
        itemRepository.save(new Item("ItemB", 2000, 200));
        itemRepository.save(new Item("ItemC", 3000, 300));
    }

}
