package jp.te4a.spring.boot.myapp8;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import jp.te4a.spring.boot.myapp8.impls.ParamsMultiValueMap;

//SpringBootの起動クラスを指定
@ContextConfiguration(classes = MyBookApp7Application.class)
//クラス内の全メソッドにおいて、実行前にDIコンテナの中身を破棄する設定
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//テストランナー：各テストケース（テストメソッド）を制御する：DIする場合は必須かも
@ExtendWith(SpringExtension.class)
//MockおよびWACの自動ロードサーブレット環境を自動作成する
@AutoConfigureMockMvc
// テスト時に起動するSpringBootプロジェクトの使用ポート番号を設定する。
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//クラス単位でインスタンスを作成
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

// BookControllerSpringTestの実装
public class BookControllerSpringTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext wac;
    
    @BeforeAll
    public void テスト前処理(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    // booksにget
    @Test
    public void indexにアクセス() throws Exception{
        mockMvc.perform(get("/books"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("books/list"))
        .andExpect(content().string(containsString("書籍一覧画面")))
        .andReturn();
    }

    // TODO books/createにpost
    @Test
    public void  booksにpostする() throws Exception{
        MultiValueMap<String,String> params = new ParamsMultiValueMap();

        params.add("id", "1");
        params.add("title", "testTitle");
        params.add("writter", "testWritter");
        params.add("publisher", "testPublisher");
        params.add("price", "114");

        mockMvc.perform(
            post("/books/create").
            params(params)
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/books"))
            .andReturn();
    }

    // TODO books/editにポストする_editForm
    // FIXME bookMapのモックできないからできない？

    // TODO books/editにポストする_edit
    // FIXME bookMapのモックできないからできない？

    // TODO books/deleteにポストする
    // FIXME bookMapのモックできないからできない？

    // TODO books/editにポストすする_goToTop
    @Test
    public void  books_editにポストする() throws Exception{
        MultiValueMap<String,String> params = new ParamsMultiValueMap();
        params.add("goToTop", null);
        mockMvc.perform(
            post("/books/edit")
            .params(params))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/books"))
            .andReturn();
    }
}