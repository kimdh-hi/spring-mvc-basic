package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    @PostMapping("/add")
    public String addItem(
            @Validated @ModelAttribute Item item,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {

        /**
         * ObjectError (복합필드 에러)의 경우 Bean에서 @ScriptAssert도 사용 가능히지만 권장 X
         * @ScriptAssert보다는 아래처럼 직접 코드로 작성하는 것을 권장
         */
        if (item.getPrice() != null && item.getQuantity() != null) {
            int total = item.getPrice() * item.getQuantity();
            if (total <= 100000) {
                bindingResult.reject("totalPriceMin", new Object[]{100000, total}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.warn("bindingResult = {}", bindingResult);
            return "validation/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v3/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(
            @PathVariable Long itemId,
            @Validated @ModelAttribute Item item,
            BindingResult bindingResult) {

        if (item.getQuantity() != null && item.getPrice() != null) {
            int total = item.getQuantity() * item.getPrice();
            if (total <= 100000) {
                bindingResult.reject("totalPriceMin", new Object[]{100000, total}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.warn("BindingResult={}", bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);

        return "redirect:/validation/v3/items/{itemId}";
    }

}

