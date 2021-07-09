package com.hello.itemservice.web.basic;

import com.hello.itemservice.domain.item.Item;
import com.hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

    /**
     * 전체 아이템 목록
     */
    @GetMapping()
    public String itemList(Model model) {
        List<Item> findItems = itemRepository.findAll();
        model.addAttribute("items", findItems);
        return "items";
    }

    /**
     * 단건 아이템 상세
     */
    @GetMapping("/{itemId}")
    public String itemDetail(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "item";
    }

    /**
     * 아이템 등록 폼
     */
    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }

    /**
     * 아이템 등록
     * @ModelAttribute
     *   1. 지정한 이름을 key값으로 하여 전달된 객체를 그대로 Model에 넣어준다.
     *   2. 이름 지정까지 생략 -> default naming rule: (Item -> item) : 클래스명의 맨 앞을 대문자로 바꿔서 key값으로 사용
     *   3. default naming rule을 따른다면 @ModelAttribute까지 생략 가능
     *
     * 현재 아이템 등록의 치명적 결함
     * - 클라이언트는 GET으로 상품 등록 폼을 요청함 (GET)
     * - 서버는 GET요청을 받아 해당하는 폼을 랜더링
     * - 클라이언트는 전달받은 폼에 값을 입력하여 서버로 전송함 (POST)
     * - 값을 전송받은 서버는 로직을 수행하고 View를 랜더링 함 (문제 발생)
     * - View를 랜더링한 시점의 URL은 그대로 POST를 요청한 URL임
     * - 클라이언트가 새로고침을 한다면? 계속해서 이전의 POST요청이 서버로 전달됨
     *
     * 해결 PRG (Post-Redirect-GET)
     * - 서버는 로직을 처리한 후 View를 랜더링하는 것이 아니고 Redirect로 URL을 변경하여 GET요청을 통해 View를 뿌려줘야 함
     * - 새로고침을 하여도 해당 View가 리랜더링되는 것 뿐 값을 변경하거나 추가하는 로직이 수행되지 않음 (오류를 발생시키지 않음)
     */
//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute("item") Item item, Model model) {
        Item savedItem = itemRepository.save(item);
        //model.addAttribute("item", savedItem); // @ModelAttribute가 model에 "item"의 이름으로 값을 담아줌
        return "item";
    }

    /**
     * PRG 패턴을 적용한 아이템 등록
     */
//    @PostMapping("/add")
    public String addItemV2(Item item) {
        itemRepository.save(item);

        return "redirect:/items/" +  item.getId();
    }

    /**
     * RedirectionAttributes를 이용한 PRG 패턴 아이템 등록
     */
    @PostMapping("/add")
    public String addItemV3(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("success",true);
        return "redirect:/items/{itemId}"; // localhost:8080/basic/items/3?success=true
    }

    /**
     * 아이템 수정 폼
     */
    @GetMapping("/edit/{itemId}")
    public String editItemForm(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);

        return "editForm";
    }

    /**
     * 아이템 수정
     */
    @PostMapping("/edit/{itemId}")
    public String edit(@ModelAttribute("item") Item item, @PathVariable Long itemId) {
        itemRepository.update(itemId, item);
        Item editedItem = itemRepository.findById(itemId);
        return "redirect:/items/{itemId}";
    }

    /**
     * 아이템 삭제
     */
    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId) {
        log.warn("******** itemID = {} delete", itemId);
        itemRepository.deleteById(itemId);

        return "redirect:/items";
    }

    /**
     * 초기 데이터 세팅
     */
    @PostConstruct
    private void init() {
        itemRepository.save(new Item("ItemA", 1000, 100));
        itemRepository.save(new Item("ItemB", 2000, 200));
        itemRepository.save(new Item("ItemC", 3000, 300));
    }

}
