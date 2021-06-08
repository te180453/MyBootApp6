package jp.te4a.spring.boot.myapp7;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import jp.te4a.spring.boot.myapp7.BookBean;
import jp.te4a.spring.boot.myapp7.HelloController;

public class HelloControllerTest {
    @Test
    public void indexにアクセス_文字列indexが返る(){
        HelloController hc = new HelloController();
        String actual = hc.index(new TestModel());
        String expected = "books/list";
        assertEquals(expected, actual);
    }
}

class TestModel implements Model{
    private Map<String, Object> map = new HashMap<>();

    @Override
    public Model addAttribute(String attributeName, Object attributeValue) {
        // TODO Auto-generated method stub
        this.map.put(attributeName, attributeValue);
        return this;
    }

    @Override
    public Model addAttribute(Object attributeValue) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Model addAllAttributes(Collection<?> attributeValues) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Model addAllAttributes(Map<String, ?> attributes) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Model mergeAttributes(Map<String, ?> attributes) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean containsAttribute(String attributeName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object getAttribute(String attributeName) {
        // TODO Auto-generated method stub
        return this.map.get(attributeName);
    }

    @Override
    public Map<String, Object> asMap() {
        // TODO Auto-generated method stub
        return null;
    }

}