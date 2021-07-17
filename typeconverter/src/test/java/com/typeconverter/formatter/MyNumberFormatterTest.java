package com.typeconverter.formatter;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    void parse() throws ParseException {
        Number result = formatter.parse("1,000,000", Locale.KOREA);
        assertThat(result).isEqualTo(1000000L);

        Number result1 = formatter.parse("1,000,000", Locale.ENGLISH);
        System.out.println("result1 = " + result1);
    }

    @Test
    void print() {
        String result = formatter.print(1000000, Locale.KOREA);
        assertThat(result).isEqualTo("1,000,000");
    }
}