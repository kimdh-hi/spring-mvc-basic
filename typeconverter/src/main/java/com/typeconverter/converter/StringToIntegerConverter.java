package com.typeconverter.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        /**
         * ๐
         * valueOf๋ wrapper ํ์์ ๋ฐํ
         * parseInt๋ primitive ํ์ ๋ฐํ
         */
        log.info("convert [String -> Integer] [{} -> ]", source);
        return Integer.valueOf(source);
    }
}
