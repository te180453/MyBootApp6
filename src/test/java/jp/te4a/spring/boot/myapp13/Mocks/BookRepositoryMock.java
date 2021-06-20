package jp.te4a.spring.boot.myapp13.Mocks;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.Mock;

import jp.te4a.spring.boot.myapp13.bean.BookBean;
import jp.te4a.spring.boot.myapp13.repository.BookRepository;

public class BookRepositoryMock {

    public static BookRepository create(BookRepository br){
        //モック作成
        BookBean bb = mock(BookBean.class);
        BookBeanMock.create(bb);
        List<BookBean> bbList = new ArrayList<BookBean>();
        bbList.add(bb);
        Optional<BookBean> optbb = Optional.ofNullable(bb);
        when(br.save((BookBean)anyObject())).thenReturn(bb);
        when(br.findAll()).thenReturn(bbList);
        when(br.findById(anyInt())).thenReturn(optbb);
        return br;
    }
}
