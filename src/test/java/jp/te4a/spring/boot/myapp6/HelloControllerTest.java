package jp.te4a.spring.boot.myapp6;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import jp.te4a.spring.boot.myapp6.Mocks.BookBeanMock;
import jp.te4a.spring.boot.myapp6.Mocks.BookServiceMock;

public class HelloControllerTest {

    @Mock
    private static BookBean bb;

    @Mock
    private static BookServise bs;

    @InjectMocks
    private static HelloController hc;


    @BeforeEach
    public void each() {
        MockitoAnnotations.openMocks(this);
        BookBeanMock.create(bb);
        BookServiceMock.create(bs);
    }

    @Test
    public void indexにアクセス_文字列indexが返る(){
        String actual = hc.index(new TestModel());
        String expected = "index";
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

    @Test
    public void postFormにアクセス_モデルが返る(){
        ModelAndView actual_ = hc.postForm("1", "A", "B", "C", "10");
        String actual = (String)actual_.getModel().get("msg");
        System.out.println(actual);
        assertEquals(actual,"<hr>ID:1<br>タイトル:A<br>著者:B<br>出版社:C<br>価格:1<br><hr>");
    }
}