package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void helloMessage() {
        String hello = messageSource.getMessage("hello",null ,null ,null );
        Assertions.assertThat(hello).isEqualTo("안녕");
        String hello1 = messageSource.getMessage("hello", null, null, Locale.ENGLISH);
        Assertions.assertThat(hello1).isEqualTo("hello");
    }

    @Test
    void helloArgumentMessage() {
        String message = messageSource.getMessage("hello.name", new Object[]{"spring"}, null, null);
        Assertions.assertThat(message).isEqualTo("안녕 spring");

        String message1 = messageSource.getMessage("hello.name", new Object[]{"spring"}, null, Locale.ENGLISH);
        Assertions.assertThat(message1).isEqualTo("hello spring");
    }

    @Test
    void helloDefaultMessage() {
        String message = messageSource.getMessage("error_code", null, "default", null);
        Assertions.assertThat(message).isEqualTo("default");
    }
}
