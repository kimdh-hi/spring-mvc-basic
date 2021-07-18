package com.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * 동일한 타입 변환을 수행하는 컨버터가 있다면 컨버터가 우선적용 됨
 * (문자)1,000,000 -> (객체)1000000
 * (객체)1000000   -> (문자)1,000,000
 */
@Slf4j
public class MyNumberFormatter implements Formatter<Number> {

    /**
     * 문자를 객체로 변환
     */
    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("parse 호출 text=[{}], locale=[{}]", text, locale);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);
    }

    /**
     * 객체를 문자로 변환
     */
    @Override
    public String print(Number object, Locale locale) {
        log.info("print 호출 text=[{}], locale=[{}]", object, locale);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.format(object);
    }
}
