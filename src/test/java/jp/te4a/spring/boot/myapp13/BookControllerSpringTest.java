package jp.te4a.spring.boot.myapp13;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.sql.DataSource;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;

import org.hamcrest.Matcher;
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
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import jp.te4a.spring.boot.myapp13.impls.ParamsMultiValueMap;
import jp.te4a.spring.boot.myapp13.repository.UserRepository;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

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
//ログイン状態を再現する
@WithMockUser()
public class BookControllerSpringTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

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

    public final static String[] userColumns = {"username", "password"};
    
    public static Object[] userValues = {"testuser", "ここの内容はハッシュ化したパスワードを入れる"};
    
    public static final Operation insertUserData 
        = Operations.insertInto("users")
        .columns(userColumns)
        .values(userValues).build();
    
    @BeforeAll
    public void テスト前処理(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    public void each(){
       dest = new DataSourceDestination(dataSource);
       new DbSetup(dest, deleteRecords).launch();
       new DbSetup(dest, 
        Operations.deleteAllFrom("users")
        ).launch();
    }

    //create user test
    @Test
    public void users_createにアクセス() throws Exception{
        mockMvc.perform(
            post("/users/create")
            .param("username","testuser")
            .param("password", "password")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/users"))
        .andDo(new ResultHandler(){
            public void handle(MvcResult result) throws Exception {
                assertThat(
                    userRepository.findById("testuser").isPresent(), 
                    is(true)
                );
            }
        })
        .andReturn();
    }

    // login test
    @Test
    public void loginにアクセス() throws Exception{
        new DbSetup(
            new DataSourceDestination(dataSource),
            Operations.insertInto("users")
            .columns("username","password")
            .values("testuser", new Pbkdf2PasswordEncoder().encode("password"))
            .build()
        ).launch();
        System.out.println(userRepository.findAll());
        ResultHandler handler = new ResultHandler(){
            @Override
            public void handle(MvcResult result) throws Exception {
                System.out.println("### : " + result.getResponse().getRedirectedUrl());
            }
        };

        mockMvc.perform(
            post("/login")
            .param("username","testuser")
            .param("password", "password")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            )
        .andDo(handler)
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/books"))
        .andReturn();
    }
    // logoutにアクセス
    @Test
    public void logoutにアクセス() throws Exception{
        mockMvc.perform(
            post("/logout")
            .with(SecurityMockMvcRequestPostProcessors.csrf())    
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/loginForm"))
        .andReturn();
    }
    // loginFormにアクセス
    @Test
    public void loginFormにアクセス() throws Exception{
        mockMvc.perform(get("/loginForm"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("ログインフォーム")))
        .andExpect(content().string(containsString("書籍管理システム")))
        .andReturn();
    }

    // booksにget
    @Test
    @WithMockUser()
    public void booksにアクセス() throws Exception{
        mockMvc.perform(get("/books"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("books/list"))
        .andExpect(content().string(containsString("書籍一覧画面")))
        .andReturn();
    }



    //----------!!! TEST PASSED !!!-------------
    // books/createにpost_正常値
    @Test
    public void  booksCreateにpostする正常値() throws Exception{
        MultiValueMap<String,String> params = new ParamsMultiValueMap();

        params.add("id", values1[0].toString());
        params.add("title", values1[1].toString());
        params.add("writter", values1[2].toString());
        params.add("publisher", values1[3].toString());
        params.add("price", values1[4].toString());
        System.out.println(params);
        mockMvc.perform(
            post("/books/create")
            .params(params)
            .with(SecurityMockMvcRequestPostProcessors.csrf()) 
            //これを追記したらテストが通るようになった
            // PostリクエストにCSRFトークンを付ける
            // CSRFトークンとはCSRF(クロスサイトリクエストフォージェリ)の対策に必要な情報 
            )
            .andExpect(status().is3xxRedirection())
            //.andExpect(view().name("redirect:/books"))
            .andReturn();
    }

    // books/createにpost_異常値
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
            .with(SecurityMockMvcRequestPostProcessors.csrf()) 
            )
            .andExpect(status().isOk())
            .andExpect(view().name("books/list"))
            .andExpect(content().string(containsString("3 から 2147483647 の間のサイズにしてください")))
            .andExpect(content().string(containsString("3 から 20 の間のサイズにしてください")))
            .andExpect(content().string(containsString("0 以上の値にしてください")))
            .andExpect(content().string(containsString("入力は「東北たろう」である必要があります")))
            .andReturn();
    }

    // books/editにポストする_editForm
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
            .with(SecurityMockMvcRequestPostProcessors.csrf()) 
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


    // books/editにポストする_edit_正常値
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
            .with(SecurityMockMvcRequestPostProcessors.csrf()) 
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/books"))
            .andReturn();
    }

    // books/editにポストする_edit_異常値
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
            .with(SecurityMockMvcRequestPostProcessors.csrf()) 
            )
            .andExpect(status().isOk())
            .andExpect(view().name("books/edit"))
            .andExpect(content().string(containsString("3 から 2147483647 の間のサイズにしてください")))
            .andExpect(content().string(containsString("3 から 20 の間のサイズにしてください")))
            .andExpect(content().string(containsString("0 以上の値にしてください")))
            .andReturn();
    }
        
    // books/deleteにポストする
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
            .with(SecurityMockMvcRequestPostProcessors.csrf()) 
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/books"))
            .andReturn();
    }

    // books/editにポストすする_goToTop
    @Test
    public void  books_editにポストする() throws Exception{
        MultiValueMap<String,String> params = new ParamsMultiValueMap();
        params.add("goToTop", null);
        mockMvc.perform(
            post("/books/edit")
            .params(params)
            .with(SecurityMockMvcRequestPostProcessors.csrf()) )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/books"))
            .andReturn();
    }
}