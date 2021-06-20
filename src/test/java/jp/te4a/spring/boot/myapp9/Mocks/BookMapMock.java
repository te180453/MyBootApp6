package jp.te4a.spring.boot.myapp9.Mocks;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.ConcurrentMap;

import jp.te4a.spring.boot.myapp9.BookBean;

public class BookMapMock {

    public static ConcurrentMap<Integer,BookBean> create(ConcurrentMap<Integer,BookBean> bm){
        BookBean bb = mock(BookBean.class);
        BookBeanMock.create(bb);
        when(bm.put(anyInt(), (BookBean)anyObject())).thenReturn(bb);
        return bm;
    }
}
