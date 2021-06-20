package jp.te4a.spring.boot.myapp12;

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

import javax.sql.DataSource;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import jp.te4a.spring.boot.myapp12.impls.ParamsMultiValueMap;

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
    @Autowired
    private DataSource dataSource;

    Destination dest;

    DbSetup dbSetup;

    public final static String[] columns = {"id", "title", "writter", "publisher", "price"};

    public final static Object[] errorValues2 = {1, "タ", "著", "出", -1};

    public final static Object[] values2 = {2, "タイトル2", "著者2", "出版社2", 200};

    public final static Object[] values1 = {3, "東北巡りハンドブック", "東北たろう", "出版社WX", 114514};

    public static final Operation insertData1 
        = Operations.insertInto("books")
        .columns(columns)
        .values(values1).build();

    public static final Operation insertData2
        = Operations.insertInto("books")
        .columns(columns)
        .values(values2).build();
    
    public static final Operation deleteRecords
        = Operations.deleteAllFrom("books");

    @BeforeAll
    public void テスト前処理(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    public void each(){
       dest = new DataSourceDestination(dataSource);
       dbSetup = new DbSetup(dest, deleteRecords);
       dbSetup.launch();
    }

    // booksにget
    @Test
    public void booksにアクセス() throws Exception{
        mockMvc.perform(get("/books"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("books/list"))
        .andExpect(content().string(containsString("書籍一覧画面")))
        .andReturn();
    }

    // TODO books/createにpost_正常値
    @Test
    public void  booksCreateにpostする正常値() throws Exception{
        MultiValueMap<String,String> params = new ParamsMultiValueMap();

        params.add("id", values1[0].toString());
        params.add("title", values1[1].toString());
        params.add("writter", values1[2].toString());
        params.add("publisher", values1[3].toString());
        params.add("price", values1[4].toString());

        mockMvc.perform(
            post("/books/create").
            params(params)
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/books"))
            .andReturn();
    }

    // TODO books/createにpost_異常値
    @Test
    public void  booksCreateにpostする異常値() throws Exception{
        MultiValueMap<String,String> params = new ParamsMultiValueMap();

        params.add("id", errorValues2[0].toString());
        params.add("title", errorValues2[1].toString());
        params.add("writter", errorValues2[2].toString());
        params.add("publisher",errorValues2[3].toString());
        params.add("price", errorValues2[4].toString());

        HttpHeaders header = new HttpHeaders();
        header.add("Accept-Language", "ja");

        mockMvc.perform(
            post("/books/create")
            .params(params)
            .headers(header)
            )
            .andExpect(status().isOk())
            .andExpect(view().name("books/list"))
            .andExpect(content().string(containsString("3 から 2147483647 の間のサイズにしてください")))
            .andExpect(content().string(containsString("3 から 20 の間のサイズにしてください")))
            .andExpect(content().string(containsString("0 以上の値にしてください")))
            .andExpect(content().string(containsString("入力は「東北たろう」である必要があります")))
            .andReturn();
    }

    // TODO books/editにポストする_editForm
    @Test
    public void  booksEditにpostする() throws Exception{
        //下ごしらえ
        dbSetup = new DbSetup(dest, insertData1);
        dbSetup.launch();
        // データを挿入した

        MultiValueMap<String,String> params 
            = new ParamsMultiValueMap();

        params.add("id", values1[0].toString());
        params.add("form", null);

        mockMvc.perform(
            post("/books/edit").
            params(params)
            )
            .andExpect(status().isOk())
            .andExpect(view().name("books/edit"))
            .andExpect(content().string(containsString(values1[0].toString())))
            .andExpect(content().string(containsString(values1[1].toString())))
            .andExpect(content().string(containsString(values1[2].toString())))
            .andExpect(content().string(containsString(values1[3].toString())))
            .andExpect(content().string(containsString(values1[4].toString())))
            .andReturn();
    }


    // TODO books/editにポストする_edit_正常値
    @Test
    public void  booksEditにpostするEdit() throws Exception{
        //下ごしらえ
        dbSetup = new DbSetup(dest, insertData1);
        dbSetup.launch();
        // データを挿入した
        MultiValueMap<String,String> params 
            = new ParamsMultiValueMap();

        params.add("id", values1[0].toString());
        params.add("title", values1[1].toString());
        params.add("writter", values1[2].toString());
        params.add("publisher", values1[3].toString());
        params.add("price", "1919");

        mockMvc.perform(
            post("/books/edit").
            params(params)
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/books"))
            .andReturn();
    }

    // TODO books/editにポストする_edit_異常値
    @Test
    public void  booksEditにpostするEdit異常値() throws Exception{
        //下ごしらえ
        dbSetup = new DbSetup(dest, insertData1);
        dbSetup.launch();
        // データを挿入した

        MultiValueMap<String,String> params = new ParamsMultiValueMap();

        params.add("id", values1[0].toString());
        params.add("title", errorValues2[1].toString());
        params.add("writter", errorValues2[2].toString());
        params.add("publisher",errorValues2[3].toString());
        params.add("price", errorValues2[4].toString());

        HttpHeaders header = new HttpHeaders();
        header.add("Accept-Language", "ja");

        mockMvc.perform(
            post("/books/edit")
            .params(params)
            .headers(header)
            )
            .andExpect(status().isOk())
            .andExpect(view().name("books/edit"))
            .andExpect(content().string(containsString("3 から 2147483647 の間のサイズにしてください")))
            .andExpect(content().string(containsString("3 から 20 の間のサイズにしてください")))
            .andExpect(content().string(containsString("0 以上の値にしてください")))
            .andReturn();
    }
        
    // TODO books/deleteにポストする
    @Test
    public void  booksDeleteにpostするDelete() throws Exception{
        //下ごしらえ
        dbSetup = new DbSetup(dest, insertData1);
        dbSetup.launch();
        // データを挿入した
        MultiValueMap<String,String> params 
            = new ParamsMultiValueMap();

        params.add("id", values1[0].toString());

        mockMvc.perform(
            post("/books/delete").
            params(params)
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/books"))
            .andReturn();
    }

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