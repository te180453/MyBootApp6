package jp.te4a.spring.boot.myapp6;

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


//SpringBootの起動クラスを指定
@ContextConfiguration(classes = App.class)
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
public class HelloControllerSpringTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext wac;
    
    @BeforeAll
    public void テスト前処理(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void indexにアクセス() throws Exception{
        mockMvc.perform(get("/"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("msg"))
        .andExpect(model().attribute("msg", "this is setting message"))
        .andReturn();
    }

    @Test
    public void postする() throws Exception{
        MultiValueMap<String,String> params = new MultiValueMap<String,String>(){
            private final Map<String,List<String>> map = new HashMap<>();
            @Override
            public void clear() {
                map.clear();
            }

            @Override
            public boolean containsKey(Object key) {
                return map.containsKey(key);
            }

            @Override
            public boolean containsValue(Object value) {
                return map.containsValue(value);
            }

            @Override
            public Set<Entry<String, List<String>>> entrySet() {
                return map.entrySet();
            }

            @Override
            public List<String> get(Object key) {
                return map.get(key);
            }

            @Override
            public boolean isEmpty() {
                return map.isEmpty();
            }

            @Override
            public Set<String> keySet() {
                return map.keySet();
            }

            @Override
            public List<String> put(String arg0, List<String> arg1) {
                return map.put(arg0, arg1);
            }

            @Override
            public void putAll(Map<? extends String, ? extends List<String>> m) {
                map.putAll(m);
            }

            @Override
            public List<String> remove(Object key) {
                return map.remove(key);
            }

            @Override
            public int size() {
                return map.size();
            }

            @Override
            public Collection<List<String>> values() {
                return map.values();
            }

            @Override
            public String getFirst(String key) {
                return null;
            }

            @Override
            public void add(String key, String value) {
                List<String> t = new ArrayList<String>();
                t.add(value);
                map.put(key,t);
            }

            @Override
            public void addAll(String key, List<? extends String> values) {}

            @Override
            public void addAll(MultiValueMap<String, String> values) {}

            @Override
            public void set(String key, String value) {}

            @Override
            public void setAll(Map<String, String> values) {}

            @Override
            public Map<String, String> toSingleValueMap() {return null;}
            
        };

        params.add("id", "1");
        params.add("title", "testTitle");
        params.add("writter", "testWritter");
        params.add("publisher", "testPublisher");
        params.add("price", "114");

        mockMvc.perform(
            post("/post").
            params(params)
            )
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("msg"))
            .andExpect(
                model().attribute(
                    "msg", 
                    String.format(
                        "<hr>ID:%s<br>タイトル:%s<br>著者:%s<br>出版社:%s<br>価格:%s<br><hr>",
                        params.get("id").get(0),
                        params.get("title").get(0),
                        params.get("writter").get(0),
                        params.get("publisher").get(0),
                        params.get("price").get(0)
                    ) 
                    ))
            .andExpect(view().name("index"))
            .andExpect(content().string(containsString(
                String.format("this is sample page for Spring Boot!") 
                )))
            .andReturn();
    }
}