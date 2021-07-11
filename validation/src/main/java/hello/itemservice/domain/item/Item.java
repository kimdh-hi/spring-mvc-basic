package hello.itemservice.domain.item;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


// Object validation (복합필드 검증)
// 권장 ❌ (기능이 약하고 다른 bean과의 결합이 불가능) obejct 검증의 경우 직접 구현하자.
// @ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "상품 가격의 총 합이 10000원 이상이여야 합니다.")
/**
 * 요구사항의 변경 (groups)
 * 등록시에는 id를 입력받지 않아도 되지만 수정시에는 id를 반드시 입력받아야 한다.
 * 등록시에는 수량을 9999까지만 입력받을 수 있지만 수정시에는 9999개를 초과하여 입력받을 수 있다.
 * 코드를 보면 중복이 많고 조금 복잡.. 실무에서는 잘 사용하지 않음
 * 실무에서는 폼마다 객체를 분리하여 데이터를 받고 검증을 수행한다.
 */
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public Item() {}

    //    @Builder(builderClassName = "withoutId", builderMethodName = "withoutId")
//    public Item(String itemName, Integer price, Integer quantity) {
//        this.itemName = itemName;
//        this.price = price;
//        this.quantity = quantity;
//    }
//
//    @Builder(builderClassName = "withId",builderMethodName = "withId")
//    public Item(Long id, String itemName, Integer price, Integer quantity) {
//        this.itemName = itemName;
//        this.price = price;
//        this.quantity = quantity;
//    }
}
