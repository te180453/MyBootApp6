package jp.te4a.spring.boot.myapp13;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jp.te4a.spring.boot.myapp13.bean.BookBean;

public class BookBean_Test {

    @Test
    public void bookBeanの作成_新たなBookBeanが返ってくる() {
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