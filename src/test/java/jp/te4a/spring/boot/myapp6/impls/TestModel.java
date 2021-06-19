package jp.te4a.spring.boot.myapp6.impls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

public class TestModel implements Model {
    private Map<String, Object> map = new HashMap<>();

    @Override
    public Model addAttribute(String attributeName, Object attributeValue) {

        this.map.put(attributeName, attributeValue);
        return this;
    }

    @Override
    public Model addAttribute(Object attributeValue) {

        return null;
    }

    @Override
    public Model addAllAttributes(Collection<?> attributeValues) {

        return null;
    }

    @Override
    public Model addAllAttributes(Map<String, ?> attributes) {

        return null;
    }

    @Override
    public Model mergeAttributes(Map<String, ?> attributes) {

        return null;
    }

    @Override
    public boolean containsAttribute(String attributeName) {

        return false;
    }

    @Override
    public Object getAttribute(String attributeName) {

        return this.map.get(attributeName);
    }

    @Override
    public Map<String, Object> asMap() {

        return null;
    }

}