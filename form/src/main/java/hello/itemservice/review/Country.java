package hello.itemservice.review;

import lombok.Getter;

@Getter
public enum Country {

    KOREA("대한민군"), USA("미국"), JAPAN("일본"), CHINA("중국"), SINGAPORE("싱가폴");

    private final String description;

    Country(String description) {
        this.description = description;
    }
}
