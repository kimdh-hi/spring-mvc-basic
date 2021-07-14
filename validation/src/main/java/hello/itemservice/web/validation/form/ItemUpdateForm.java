package hello.itemservice.web.validation.form;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;

    @NotBlank(message="상품명을 입력하세요.")
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 100000)
    private Integer price;

    @Range(min = 100)
    private Integer quantity;
}
