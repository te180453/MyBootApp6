package jp.te4a.spring.boot.myapp8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jp.te4a.spring.boot.myapp8.Mocks.BookBeanMock;
import jp.te4a.spring.boot.myapp8.Mocks.BookMapMock;

// TODO テストの実装
// FIXME: mapのモック化ができない。どうすればいいかな
@Disabled
public class BookRepository_Test {
    //mapのモック
    @Mock
    private static ConcurrentMap<Integer,BookBean> bookMap;

    // テスト対象のモック
    @InjectMocks
    private static BookRepository br;

    // モックの初期化
    @BeforeEach
    public void each(){
        MockitoAnnotations.openMocks(this);
        BookMapMock.create(bookMap);
    }

    // getBookIdTestのテスト
    @Test
    public void getBookIdTest(){
        for(int i=1;i<100;i++){
            int expected = i;
            int actual = br.getBookId();
            assertEquals(expected, actual);
        }
    }

    // TODO save
    @Test
    public void リポジトリにBookBeanを追加する_リポジトリにBookBeanが追加される() {
        BookBean expected = mock(BookBean.class);
        BookBeanMock.create(expected);
        BookBean actual = br.save(expected);
        assertEquals(expected.getId(), actual.getId());

    }

    // TODO delete
    @Test
    @Disabled
    public void リポジトリからBookBeanを削除する_リポジトリが空になる() {
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

    // TODO findAll

    //TODO create

    // TODO update
    
    //TODO findOne
}