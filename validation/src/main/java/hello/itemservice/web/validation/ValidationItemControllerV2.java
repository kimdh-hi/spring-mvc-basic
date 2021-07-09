package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    /**
     * BindingResult V1
     * .addError(FieldErroror ObjectError)
     * #fields.hasGlobalErrors
     * th:errorclass
     * th:errors
     */
//    @PostMapping("/add")
    public String addItemV1(
            @ModelAttribute Item item,
            BindingResult bindingResult, // 순서 중요 @ModelAttribute 다음에 위치하도록
            RedirectAttributes redirectAttributes, Model model) {

        // validation
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름을 입력해주세요."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "상품 가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() < 10 || item.getQuantity() > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", "상품 개수는 10 ~ 9999개 까지 허용합니다."));
        }

        /**
         * 필드 복합 에러 (Global Error)
         * 요구사항: 가격*수량이 10000을 넘어야 한다.
         * new ObjectError()
         **/
        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000) {
                bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량은 10000 이상이어야 합니다. 현재값 = " + result));
            }
        }

        if (bindingResult.hasErrors()) {
            // 알아서 error를 Model에 넣어준다.
            log.info("bindingResult = {}", bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    /**
     * BindingResult V2
     * 검증 실패시 값을 유지하도록 => rejectValue 설정
     * 바인딩 자체의 오류인지 검증의 문제인지 설정 => bindingFailure = false
     */
//    @PostMapping("/add")
    public String addItemV2(
            @ModelAttribute Item item,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {

        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(),
                    false, null, null, "상품 이름을 입력해주세요."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(),
                    false, null, null, "상품 가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() < 10 || item.getQuantity() > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(),
                    false, null, null, "상품 개수는 10 ~ 9999개 까지 허용합니다."));
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량은 10000 이상이어야 합니다. 현재값 = " + result));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    /**
     * BindingResult V3
     * BindingResult error message source ( errors.properties )
     * codes와 argument 데이터 타입에 유의
     * code를 배열로 받는 이유
     * 첫번째 code와 매핑되는 code를 찾지 못하면 다음 code와 매핑
     */
//    @PostMapping("/add")
    public String addItemV3(
            @ModelAttribute Item item,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {

        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError(
                    "item", "itemName", item.getItemName(), false,
                    new String[]{"required.item.itemName"}, null, null));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false,
                    new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }

        if (item.getQuantity() == null || item.getQuantity() < 10 || item.getQuantity() > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false,
                    new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"},
                        new Object[]{10000, result}, null));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    /**
     * BindingResult V4
     * rejectValue, reject 메서드 사용
     * @ModelAttribute 바로 다음에 BindingResult를 써줘서 BindingResult가 ObjectName을 알 수 있도록 한 것을 이용
     */
    @PostMapping("/add")
    public String addItemV4(
            @ModelAttribute Item item,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {

        if (!StringUtils.hasText(item.getItemName())) {
            /**
             * field에 대해서는 rejectValue 메서드를 사용
             * BindingResult는 이미 objectName을 알고 있다. (obejctName = item) => BindingResult Target
             * errorCode 규칙
             *   errorCode.objectName.fieldName=에러 메시지
             */
            bindingResult.rejectValue("itemName", "required", null, null);
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() < 10 || item.getQuantity() > 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000) {
                /**
                 * ObjectError의 경우 reject 메서드를 사용
                 * BindingResult는 이미 ObjectName을 알고있기 때문.
                 * ObjectName.errorCode
                 */
                bindingResult.reject("totalPriceMin", new Object[]{10000, result}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

