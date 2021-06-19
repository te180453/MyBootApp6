package jp.te4a.spring.boot.myapp7.Mocks;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;

import jp.te4a.spring.boot.myapp7.BookBean;
import jp.te4a.spring.boot.myapp7.BookRepository;

public class BookRepositoryMock {

    public static BookRepository create(BookRepository br){
        //モック作成
        BookBean bb = mock(BookBean.class);
        BookBeanMock.create(bb);
        List<BookBean> bbList = new ArrayList<BookBean>();
        bbList.add(bb);
        when(br.save((BookBean)anyObject())).thenReturn(bb);
        when(br.findAll()).thenReturn(bbList);
        return br;
    }
}
