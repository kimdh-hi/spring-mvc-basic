package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.web.validation.form.ItemSaveForm;
import hello.itemservice.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


/**
 * Validation 폼 객체 분리
 */
@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v4/addForm";
    }

    @PostMapping("/add")
    public String addItem(
            @Valid @ModelAttribute("item") ItemSaveForm item,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {

        if (item.getPrice() != null && item.getQuantity() != null) {
            int total = item.getPrice() * item.getQuantity();
            if (total <= 100000) {
                bindingResult.reject("totalPriceMin", new Object[]{100000, total}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "validation/v4/addForm";
        }

        Item itemParam = new Item();
        itemParam.setItemName(item.getItemName());
        itemParam.setPrice(item.getPrice());
        itemParam.setQuantity(item.getQuantity());

        Item savedItem = itemRepository.save(itemParam);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v4/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(
            @PathVariable Long itemId,
            @Validated @ModelAttribute("item") ItemUpdateForm item,
            BindingResult bindingResult) {

        if (item.getQuantity() != null && item.getPrice() != null) {
            int total = item.getQuantity() * item.getPrice();
            if (total <= 100000) {
                bindingResult.reject("totalPriceMin", new Object[]{100000, total}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("BindingResult={}", bindingResult);
            return "validation/v4/editForm";
        }

        Item itemParam = new Item();
        itemParam.setItemName(item.getItemName());
        itemParam.setPrice(item.getPrice());
        itemParam.setQuantity(item.getQuantity());

        itemRepository.update(itemId, itemParam);

        return "redirect:/validation/v4/items/{itemId}";
    }

}

