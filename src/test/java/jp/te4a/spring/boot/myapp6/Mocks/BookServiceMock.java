package jp.te4a.spring.boot.myapp6.Mocks;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import jp.te4a.spring.boot.myapp6.BookBean;
import jp.te4a.spring.boot.myapp6.BookServise;

public class BookServiceMock {
    public static BookServise create(BookServise bs){
        //モック作成
        BookBean bb = mock(BookBean.class);
        BookBeanMock.create(bb);
        List<BookBean> bbList = new ArrayList<>();
        bbList.add(bb);
        when(bs.save((BookBean)anyObject())).thenReturn(bb);
        when(bs.findAll()).thenReturn(bbList);
        return bs;
    }
}
