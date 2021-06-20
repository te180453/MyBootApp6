package jp.te4a.spring.boot.myapp8;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jp.te4a.spring.boot.myapp8.Mocks.BookBeanMock;
import jp.te4a.spring.boot.myapp8.Mocks.BookFormMock;
import jp.te4a.spring.boot.myapp8.Mocks.BookRepositoryMock;

//BookServise_Testの実装
public class BookServise_Test{
    @Mock
    BookRepository br;

    @InjectMocks
    BookServise bs;

    @BeforeEach
    public void each(){
        MockitoAnnotations.openMocks(this);
        BookRepositoryMock.create(br);
    }

    // createメソッドのテスト
    @Test
    public void create_BookFormを渡す_bookFormが返る(){
        BookForm expected = mock(BookForm.class);
        BookFormMock.create(expected);
        BookForm actual =  bs.create(expected);
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getWritter(),actual.getWritter());
        assertEquals(expected.getPublisher(),actual.getPublisher());
        assertEquals(expected.getPrice(),actual.getPrice());
    }

    // saveメソッドのテスト
    @Test
    public void save_BookBeanを渡す_bookBeanが返る(){
        BookBean expected = mock(BookBean.class);
        BookBeanMock.create(expected);
        BookBean actual =  bs.save(expected);
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getWritter(),actual.getWritter());
        assertEquals(expected.getPublisher(),actual.getPublisher());
        assertEquals(expected.getPrice(),actual.getPrice());
    }

    // updateメソッドのテスト
    @Test
    public void update_BookBeanを渡す_bookFormが返る(){
        BookForm expected = mock(BookForm.class);
        BookFormMock.create(expected);
        BookForm actual =  bs.update(expected);
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getWritter(),actual.getWritter());
        assertEquals(expected.getPublisher(),actual.getPublisher());
        assertEquals(expected.getPrice(),actual.getPrice());
    }
    // findAllメソッドのテスト
    @Test
    public void findAll_何も渡さない_BookFormのリストが返る(){
        List<BookForm> expected_ = new ArrayList<>();
        BookForm bf = mock(BookForm.class);
        BookFormMock.create(bf);
        expected_.add(bf);
        List<BookForm> actual_ = bs.findAll();
        BookForm actual = actual_.get(0);
        BookForm expected = bf;
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getWritter(),actual.getWritter());
        assertEquals(expected.getPublisher(),actual.getPublisher());
        assertEquals(expected.getPrice(),actual.getPrice());
    }
    // findOneメソッドのテスト
    @Test
    public void findOne_1を渡す_bookFormが返る(){
        BookForm expected = mock(BookForm.class);
        BookFormMock.create(expected);
        BookForm actual =  bs.findOne(1);
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getWritter(),actual.getWritter());
        assertEquals(expected.getPublisher(),actual.getPublisher());
        assertEquals(expected.getPrice(),actual.getPrice());
    }
}