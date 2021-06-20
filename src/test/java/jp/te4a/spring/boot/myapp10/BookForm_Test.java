package jp.te4a.spring.boot.myapp10;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// BookForm_Testの実装
public class BookForm_Test{
    @Test
    public void bookFormを作る(){
        BookForm bf = new BookForm();
        assertTrue(bf.getClass() == BookForm.class);
    }
}