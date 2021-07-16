package com.typeconverter.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        /**
         * 📌
         * valueOf는 wrapper 타입을 반환
         * parseInt는 primitive 타입 반환
         */
        log.info("convert [String -> Integer] [{} -> ]", source);
        return Integer.valueOf(source);
    }
}
