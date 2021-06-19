package jp.te4a.spring.boot.myapp7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

public class BookRepository_Test{
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