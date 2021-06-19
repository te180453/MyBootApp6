package jp.te4a.spring.boot.myapp8.Mocks;

import static org.mockito.Mockito.*;

import jp.te4a.spring.boot.myapp8.BookForm;

public class BookFormMock {
    public static BookForm create(BookForm testBookForm){
        //BookBeanのモック作成
        when(testBookForm.getId()).thenReturn(1);
        when(testBookForm.getTitle()).thenReturn("A");
        when(testBookForm.getWritter()).thenReturn("B");
        when(testBookForm.getPublisher()).thenReturn("C");
        when(testBookForm.getPrice()).thenReturn(1);
        return testBookForm;
    }
}
