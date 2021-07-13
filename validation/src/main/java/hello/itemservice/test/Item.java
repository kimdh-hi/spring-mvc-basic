package hello.itemservice.test;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Item {

    @NotEmpty(message = "상품명을 입력해주세요.")
    private String itemName;

    @NotNull
    @Range(min=1000, max=1000000)
    private Integer price;

    @NotNull
    @Range(min=100, groups = {ItemSaveRequest.class})
    private Integer quantity;
}
