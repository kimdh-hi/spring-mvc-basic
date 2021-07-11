package hello.itemservice.domain.item;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
// Object validation (복합필드 검증)
// 권장 ❌ (기능이 약하고 다른 bean과의 결합이 불가능) obejct 검증의 경우 직접 구현하자.
// @ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "상품 가격의 총 합이 10000원 이상이여야 합니다.")
public class Item {

    private Long id;

    @NotBlank(message = "공백 안됩니다 ❌")
    private String itemName;

    @NotNull
    @Range(min=1000, max=1000000)
    private Integer price;

    @NotNull
    @Range(max=9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
