package jp.te4a.spring.boot.myapp11;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

// BookForm_Testの実装
public class BookForm_Test{
    @Test
    public void bookFormを作る(){
        BookForm bf = new BookForm();
        assertTrue(bf.getClass() == BookForm.class);
    }

    @Test
    public void bookFormのTitleにnull_制約エラー(){
        BookForm bf = new BookForm();
        bf.setTitle(null);
        // 1. create validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        // 3. validate
        Set<ConstraintViolation<BookForm>> constraintViolations 
            = validator
            .validate(bf);
        List<String> errorMessages = new ArrayList<String>();
        constraintViolations.forEach(e -> {
            if(e.getPropertyPath().toString().equals("title"))
                errorMessages.add(e.getMessage());
        });
        boolean result = Arrays.asList(
            new String[]{"must not be null","input other than  abc."}
        ).containsAll(errorMessages);
        assertThat(result , is(true));
    }
    @Test
    public void bookFormのTitleにtitle_(){
        BookForm bf = new BookForm();
        bf.setTitle("title");
        // 1. create validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        // 3. validate
        Set<ConstraintViolation<BookForm>> constraintViolations 
            = validator
            .validate(bf);
        List<String> errorMessages = new ArrayList<String>();
        constraintViolations.forEach(e -> {
            if(e.getPropertyPath().toString().equals("title"))
                errorMessages.add(e.getMessage());
        });
        boolean result = 
            errorMessages.size() == 0;
        assertThat(result , is(true));
    }

    @Test
    public void bookFormのwritterに空文字_長さ制約エラー(){
        BookForm bf = new BookForm();
        bf.setWritter("");
        // 1. create validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        // 3. validate
        Set<ConstraintViolation<BookForm>> constraintViolations 
            = validator
            .validate(bf);
        List<String> errorMessages = new ArrayList<String>();
        constraintViolations.forEach(e -> {
            if(e.getPropertyPath().toString().equals("writter"))
                errorMessages.add(e.getMessage());
        });
        System.out.println(errorMessages);
        boolean result = 
            errorMessages.containsAll(Arrays.asList(new String[]{"size must be between 3 and 20"}));
        assertThat(result , is(true));
    }

    @Test
    public void bookFormのwritterに012345678901234567891_長さ制約エラー(){
        BookForm bf = new BookForm();
        bf.setWritter("012345678901234567891");
        // 1. create validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        // 3. validate
        Set<ConstraintViolation<BookForm>> constraintViolations 
            = validator
            .validate(bf);
        List<String> errorMessages = new ArrayList<String>();
        constraintViolations.forEach(e -> {
            if(e.getPropertyPath().toString().equals("writter"))
                errorMessages.add(e.getMessage());
        });
        // System.out.println(constraintViolations);
        boolean result = 
            errorMessages.containsAll(Arrays.asList(new String[]{"size must be between 3 and 20"}));
        assertThat(result , is(true));
    }

    @Test
    public void bookFormのwritterにmyself_(){
        BookForm bf = new BookForm();
        bf.setWritter("myself");
        // 1. create validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        // 3. validate
        Set<ConstraintViolation<BookForm>> constraintViolations 
            = validator
            .validate(bf);
        List<String> errorMessages = new ArrayList<String>();
        constraintViolations.forEach(e -> {
            if(e.getPropertyPath().toString().equals("writter"))
                errorMessages.add(e.getMessage());
        });
        // System.out.println(constraintViolations);
        boolean result = 
            errorMessages.size() == 0;
        assertThat(result , is(true));
    }

    @Test
    public void bookFormのpriceにー1_数値範囲制約エラー(){
        BookForm bf = new BookForm();
        bf.setPrice(-1);
        // 1. create validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        // 3. validate
        Set<ConstraintViolation<BookForm>> constraintViolations 
            = validator
            .validate(bf);
        List<String> errorMessages = new ArrayList<String>();
        constraintViolations.forEach(e -> {
            if(e.getPropertyPath().toString().equals("price"))
                errorMessages.add(e.getMessage());
        });
        boolean result = 
            errorMessages.containsAll(Arrays.asList(new String[]{"must be greater than or equal to 0"}));
        assertThat(result , is(true));
    }

    @Test
    public void bookFormのpriceに0_(){
        BookForm bf = new BookForm();
        bf.setPrice(0);
        // 1. create validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        // 3. validate
        Set<ConstraintViolation<BookForm>> constraintViolations 
            = validator
            .validate(bf);
        List<String> errorMessages = new ArrayList<String>();
        constraintViolations.forEach(e -> {
            if(e.getPropertyPath().toString().equals("price"))
                errorMessages.add(e.getMessage());
        });
        boolean result = 
            errorMessages.size() == 0;
        assertThat(result , is(true));
    }
}