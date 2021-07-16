package com.typeconverter.converter;

import com.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.support.SimpleTriggerContext;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    void StringToIpPortTest() {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        String source = "127.0.0.1:8080";
        IpPort result = converter.convert(source);

        Assertions.assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080));
    }

    @Test
    void IpPortToStringTest() {
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort source = new IpPort("127.0.0.1", 8080);
        String result = converter.convert(source);

        Assertions.assertThat(result).isEqualTo("127.0.0.1:8080");
    }

}