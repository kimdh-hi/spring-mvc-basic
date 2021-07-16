package com.typeconverter.config;

import com.typeconverter.converter.IntegerToStringConverter;
import com.typeconverter.converter.IpPortToStringConverter;
import com.typeconverter.converter.StringToIntegerConverter;
import com.typeconverter.converter.StringToIpPortConverter;
import com.typeconverter.type.IpPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new IpPortToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
    }
}
