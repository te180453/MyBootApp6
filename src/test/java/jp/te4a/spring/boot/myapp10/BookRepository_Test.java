package jp.te4a.spring.boot.myapp10;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepository_Test {
    // テスト対象
    @Autowired
    private BookRepository br;
    
    // jpaRepositoryだけでテストするときに使う
    @Autowired
    private TestEntityManager testEntityManager;

    private BookBean sampleBb 
        = new BookBean(null,"title","writter","publisher",10);

    @BeforeEach
    public void each(){
        testEntityManager.clear();
    }

    @Test
    // データ挿入テスト
    public void insertTest(){
        testEntityManager.persist(sampleBb);
        BookBean actual 
            = testEntityManager.find(BookBean.class, sampleBb.getId());
        assertThat(sampleBb,samePropertyValuesAs(actual));
    }

    // test save
    @Test
    public void saveする_BookBeanが返る(){
        BookBean expected = sampleBb;
        br.save(expected);
        BookBean actual 
            = testEntityManager.find(BookBean.class, expected.getId());
        assertThat(expected,samePropertyValuesAs(actual));
    }

    // test delete
    @Test
    public void deleteする_対象が削除される(){
        testEntityManager.persist(sampleBb);
        br.delete(sampleBb);
        BookBean actual = br.findById(sampleBb.getId()).orElse(null);
        assertThat(actual, nullValue());
    }

    //test findAll
    @Test
    public void findAllする_BookBeanのListが返る(){
        BookBean sampleBb2 = new BookBean(null,"title2","writter","publisher",10);
        List<BookBean> expected = new ArrayList<BookBean>();
        expected.add(sampleBb);
        expected.add(sampleBb2);
        testEntityManager.persist(sampleBb2);
        testEntityManager.persist(sampleBb);
        List<BookBean> actual = br.findAll();
        boolean result = actual.containsAll(expected);
        assertThat(result, is(true));
    }

    // test findById
    @Test
    public void findByIdする_BookBeanが返る(){
        BookBean expected = testEntityManager.persist(sampleBb);
        BookBean actual = br.findById(expected.getId()).get();
        assertThat(actual,samePropertyValuesAs(sampleBb));
    }
}