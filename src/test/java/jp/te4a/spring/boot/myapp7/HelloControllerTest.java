package jp.te4a.spring.boot.myapp7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class HelloControllerTest {

    @InjectMocks
    HelloController hc;

    @BeforeEach
    public void each(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void indexにアクセス_文字列books_listが返る(){
        String actual = hc.index(new TestModel());
        String expected = "books/list";
        assertEquals(expected, actual);
    }

    @Test
    public void indexにアクセス_AttributeのMsgはthis_is_setting_message(){
        TestModel testModel = new TestModel();
        hc.index(testModel);
        String actual = (String)testModel.getAttribute("msg");
        String expected = "this is setting message";
        assertEquals(expected, actual);
    }

    // FIXME モック化
    @Test
    public void postFormにアクセス_DIコンテナが動いてないからヌルポ(){
        assertThrows(NullPointerException.class, () -> {
            hc.postForm("1", "testTitle", "testWritter", "testPub", "114");
        });
    }
}