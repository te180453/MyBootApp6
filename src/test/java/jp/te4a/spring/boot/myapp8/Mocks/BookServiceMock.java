package jp.te4a.spring.boot.myapp8.Mocks;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import jp.te4a.spring.boot.myapp8.BookBean;
import jp.te4a.spring.boot.myapp8.BookForm;
import jp.te4a.spring.boot.myapp8.BookServise;

public class BookServiceMock {
    public static BookServise create(BookServise bs){
        //モック作成
        BookForm bf = mock(BookForm.class);
        BookBean bb = mock(BookBean.class);
        BookFormMock.create(bf);
        BookBeanMock.create(bb);
        List<BookForm> bfList = new ArrayList<BookForm>();
        bfList.add(bf);
        when(bs.save((BookBean)anyObject())).thenReturn(bb);
        when(bs.findAll()).thenReturn(bfList);
        when(bs.create((BookForm)anyObject())).thenReturn(bf);
        when(bs.update((BookForm)anyObject())).thenReturn(bf);
        when(bs.findOne(anyInt())).thenReturn(bf);
        return bs;
    }
}
