package jp.te4a.spring.boot.myapp6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import jp.te4a.spring.boot.myapp6.BookBean;
import jp.te4a.spring.boot.myapp6.BookRepository;
import jp.te4a.spring.boot.myapp6.HelloController;

public class HelloControllerTest {
    @Test
    public void indexにアクセス_文字列indexが返る(){
        HelloController hc = new HelloController();
        String actual = hc.index(new TestModel());
        String expected = "index";
        assertEquals(expected, actual);
    }

    @Test
    public void indexにアクセス_AttributeのMsgはthis_is_setting_message(){
        HelloController hc = new HelloController();
        TestModel testModel = new TestModel();
        hc.index(testModel);
        String actual = (String)testModel.getAttribute("msg");
        String expected = "this is setting message";
        assertEquals(expected, actual);
    }

    @Test
    public void postFormにアクセス_DIコンテナが動いてないからヌルポ(){
        HelloController hc = new HelloController();
        assertThrows(NullPointerException.class, () -> {
            hc.postForm("1", "testTitle", "testWritter", "testPub", "114");
        });
    }

    @Nested
    class BookBean_Test{

        @Test
        public void bookBeanの作成_新たなBookBeanが返ってくる(){
            Integer id = 1;
            String title = "テストタイトル";
            String writter = "テストライター";
            String publisher = "テスト出版社";
            Integer price = 100;
            BookBean bb = new BookBean(id, title, writter, publisher, price);
            assertEquals(title, bb.getTitle());
            assertEquals(writter, bb.getWritter());
            assertEquals(publisher, bb.getPublisher());
            assertEquals(price, bb.getPrice());
            assertEquals(id, bb.getId());
        }
    }

    @Nested
    class BookRepository_Test{
        @Test
        public void リポジトリにBookBeanを追加する_リポジトリにBookBeanが追加される(){
            BookRepository br = new BookRepository();
            Integer id = 1;
            String title = "テストタイトル";
            String writter = "テストライター";
            String publisher = "テスト出版社";
            Integer price = 100;
            BookBean bb = new BookBean(id, title, writter, publisher, price);
            br.save(bb);
            BookBean bb_ = br.findAll().get(0);
            assertEquals(title, bb_.getTitle());
            assertEquals(writter, bb_.getWritter());
            assertEquals(publisher, bb_.getPublisher());
            assertEquals(price, bb_.getPrice());
            assertEquals(id, bb_.getId());
        }

        @Test
        public void リポジトリからBookBeanを削除する_リポジトリが空になる(){
            BookRepository br = new BookRepository();
            Integer id = 1;
            String title = "テストタイトル";
            String writter = "テストライター";
            String publisher = "テスト出版社";
            Integer price = 100;
            BookBean bb = new BookBean(id, title, writter, publisher, price);
            br.save(bb);
            br.delete(bb.getId());
            List<BookBean> bbList = br.findAll();
            int expected = 0;
            int actual = bbList.size();
            assertEquals(expected, actual);
        }
    }
}

class TestModel implements Model{
    private Map<String, Object> map = new HashMap<>();

    @Override
    public Model addAttribute(String attributeName, Object attributeValue) {

        this.map.put(attributeName, attributeValue);
        return this;
    }

    @Override
    public Model addAttribute(Object attributeValue) {

        return null;
    }

    @Override
    public Model addAllAttributes(Collection<?> attributeValues) {

        return null;
    }

    @Override
    public Model addAllAttributes(Map<String, ?> attributes) {

        return null;
    }

    @Override
    public Model mergeAttributes(Map<String, ?> attributes) {

        return null;
    }

    @Override
    public boolean containsAttribute(String attributeName) {

        return false;
    }

    @Override
    public Object getAttribute(String attributeName) {

        return this.map.get(attributeName);
    }

    @Override
    public Map<String, Object> asMap() {

        return null;
    }

}