package jp.te4a.spring.boot.myapp7;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jp.te4a.spring.boot.myapp7.Mocks.BookBeanMock;
import jp.te4a.spring.boot.myapp7.Mocks.BookMapMock;

public class BookRepository_Test{
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
    @Test
    public void リポジトリにBookBeanを追加する_リポジトリにBookBeanが追加される(){
        BookBean bb = mock(BookBean.class);
        BookBeanMock.create(bb);
        br.save(bb);
        BookBean bb_ = br.findAll().get(0);
        assertEquals(bb.getTitle(), bb_.getTitle());
        assertEquals(bb.getWritter(), bb_.getWritter());
        assertEquals(bb.getPublisher(), bb_.getPublisher());
        assertEquals(bb.getPrice(), bb_.getPrice());
        assertEquals(bb.getId(), bb_.getId());
    }

    @Test
    public void リポジトリからBookBeanを削除する_リポジトリが空になる(){
        BookBean bb = mock(BookBean.class);
        BookBeanMock.create(bb);
        br.delete(bb.getId());
        List<BookBean> bbList = br.findAll();
        int expected = 0;
        int actual = bbList.size();
        assertEquals(expected, actual);
    }
}