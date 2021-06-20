package jp.te4a.spring.boot.myapp9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jp.te4a.spring.boot.myapp9.Mocks.BookBeanMock;
import jp.te4a.spring.boot.myapp9.Mocks.BookFormMock;
import jp.te4a.spring.boot.myapp9.Mocks.BookServiceMock;
import jp.te4a.spring.boot.myapp9.impls.TestModel;

public class BookController_Test {

    @Mock
    private static BookForm bf;

    @Mock
    private static BookBean bb;

    @Mock
    private static BookServise bs;

    @InjectMocks
    private static BookController bc;


    @BeforeEach
    public void each() {
        MockitoAnnotations.openMocks(this);
        BookBeanMock.create(bb);
        BookFormMock.create(bf);
        BookServiceMock.create(bs);
    }

    @Test
    public void setUpFormからBookFormを取得する() {

        BookForm bf = bc.setUpForm();
        assertEquals(BookForm.class, bf.getClass());
    }

    @Test
    public void listにアクセス_文字列books_listが返る() {
        String actual = bc.list(new TestModel());
        String expected = "books/list";
        assertEquals(expected, actual);
    }

    @Test
    public void createにアクセス_redirect_booksが返る() {
        String actual = bc.create(bf, new TestModel());
        String expected = "redirect:/books";
        assertEquals(expected, actual);
    }

    @Test
    public void editFormにアクセス_文字列books_editが返る() {
        String expected = "books/edit";
        String actual = bc.editForm(1, bf);
        assertEquals(expected, actual);
    }

    @Test
    public void editにアクセス_文字列books_booksが返る() {
        String expected = "redirect:/books";
        String actual = bc.edit(1, bf);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteにアクセス_文字列books_booksが返る() {
        String actual = bc.delete(1);
        String expected = "redirect:/books";
        assertEquals(expected,actual);
    }

    @Test
    public void GoToTopにアクセス_文字列books_booksが返る() {

        assertEquals("redirect:/books", bc.goToTop());
    }
}