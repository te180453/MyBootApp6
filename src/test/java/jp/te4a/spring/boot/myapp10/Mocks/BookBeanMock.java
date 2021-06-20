package jp.te4a.spring.boot.myapp10.Mocks;

import static org.mockito.Mockito.*;

import jp.te4a.spring.boot.myapp10.BookBean;

public final class BookBeanMock {

    public static BookBean create(BookBean testBookBean){
        //BookBeanのモック作成
        when(testBookBean.getId()).thenReturn(1);
        when(testBookBean.getTitle()).thenReturn("A");
        when(testBookBean.getWritter()).thenReturn("B");
        when(testBookBean.getPublisher()).thenReturn("C");
        when(testBookBean.getPrice()).thenReturn(1);
        return testBookBean;
    }
}
