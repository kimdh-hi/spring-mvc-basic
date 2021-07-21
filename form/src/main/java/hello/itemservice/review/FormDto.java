package hello.itemservice.review;

import lombok.Data;

import java.util.List;

@Data
public class FormDto {
    private String name;
    private boolean tnf;
    private List<String> hobbies; // multi-checkbox
}
