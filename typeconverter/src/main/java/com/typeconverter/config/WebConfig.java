package com.typeconverter.config;

import com.typeconverter.converter.IntegerToStringConverter;
import com.typeconverter.converter.IpPortToStringConverter;
import com.typeconverter.converter.StringToIntegerConverter;
import com.typeconverter.converter.StringToIpPortConverter;
import com.typeconverter.formatter.MyNumberFormatter;
import com.typeconverter.type.IpPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 포메터 적용 테스트를 위함 (포매터와 같은 타입을 처리하기 때문, 같은 타입의 경우 컨버터가 우선적용)
        //registry.addConverter(new StringToIntegerConverter());
        //registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new IpPortToStringConverter());
        registry.addConverter(new StringToIpPortConverter());

        registry.addFormatter(new MyNumberFormatter());
    }
}
